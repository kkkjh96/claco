package com.academyinfo.Files.freeboard.handler;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.academyinfo.Files.freeboard.domain.entity.FreeboardFilesEntity;
import com.academyinfo.Files.freeboard.dto.FreeboardFilesRequestDto;
import com.academyinfo.Files.freeboard.service.FreeboardFilesService;
import com.academyinfo.board.freeboard.domain.entity.BoardEntity;
import com.academyinfo.board.freeboard.domain.repository.BoardRepository;

@Component
public class FreeboardFilesHandlerImpl implements FreeboardFilesHandler {
	private FreeboardFilesService fbfService;
	private BoardRepository boardRepository;
	
	public FreeboardFilesHandlerImpl(FreeboardFilesService fbfService) {
		this.fbfService = fbfService;
	}
	
	public List<FreeboardFilesEntity> parseFileInfo (BoardEntity boardEntity, List<MultipartFile> multipartFiles) throws Exception {
		List<FreeboardFilesEntity> fileList = new ArrayList<>();
		
		// 전달된 파일이 있을 때
		if (!CollectionUtils.isEmpty(multipartFiles)) {
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			String current_date = now.format(dateTimeFormatter);
			
			// 프로젝트 디렉토리 내 저장을 위한 경로 설정
			// 경로 구분자 : File.separator
			String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;
			
			// 파일 저장할 세부 경로 지정
			String path = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "img" + File.separator + current_date;
			File file = new File(path);
			
			// 디렉토리가 없을 경우
			if (!file.exists()) {
				boolean Successed = file.mkdirs();
				
				// 디렉토리 생성 실패 시
				if (!Successed) {
					System.out.println("file : Making directory was not successful");
				}
			}
				
			// 다중 파일 처리
			for (MultipartFile multipartFile : multipartFiles) {
				// 확장자 추출
				String originalFileExtension;
				String contentType = multipartFile.getContentType();
				
				// 확장자명이 존재하지 않으면 처리하지 않음
				if (ObjectUtils.isEmpty(contentType)) {
					break;
				} else { // 확장자가 jpeg, png인 파일만 처리
					if (contentType.contains("image/jpeg")) {
						originalFileExtension = ".jpg";
					} else if (contentType.contains("image/png")) {
						originalFileExtension = ".png";
					} else {
						break;
					}
				}
				
				// 파일명 중복 피하기(현재 시간을 얻어와 지정)
				String new_file_name = System.nanoTime() + originalFileExtension;
				
				// 파일의 DTO 생성
				FreeboardFilesRequestDto fbfDto = new FreeboardFilesRequestDto();
				fbfDto.setOriginalFilename(multipartFile.getOriginalFilename());
				fbfDto.setPath(path + File.separator + new_file_name);
				fbfDto.setSize((int) multipartFile.getSize());
				
				// DTO 이용 Entity 생성
				FreeboardFilesEntity fbfEntity = fbfDto.toEntity();
				
				
				// 게시글에 존재하지 않으면 사진 정보를 저장(수정 시)
				if (boardEntity != null) {
					fbfEntity.setFbindex(boardEntity);
				}
				
				// 리스트에 추가
				fileList.add(fbfEntity);
				
				// 업로드한 파일 데이터를 지정 파일에 저장
				file = new File(absolutePath + path + File.separator + new_file_name);
				multipartFile.transferTo(file);
				
				// 파일 권한 설정
				file.setWritable(true);
				file.setReadable(true);
				
			}
		}
			
		return fileList;
	}
}
