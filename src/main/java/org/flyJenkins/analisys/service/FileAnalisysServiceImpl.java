package org.flyJenkins.analisys.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.flyJenkins.analisys.model.FileAnalisysDto;
import org.flyJenkins.analisys.strategy.FileAnalisysStrategy;
import org.flyJenkins.common.util.CommonClassUtil;
import org.flyJenkins.gitHub.model.ProjectCommonDto;

public class FileAnalisysServiceImpl implements FileAnalisysService {

	/**
	 * 파일 소스 분석 결과 리턴
	 */
	@Override
	public ProjectCommonDto getFileAnalisysResult(List<FileAnalisysDto> fileAnalisysDtoList) {		
		// 초기화
		ProjectCommonDto projectCommonDto = new ProjectCommonDto();
		StringBuffer sbFileClassName 	  = new StringBuffer();
		StringBuffer sbExtClassName 	  = new StringBuffer();
		String extStrategyClassName 	  = "";
		String fileStrategyClassName 	  = "";
		String strategyClassName 		  = "";
		
		// 파일 리스트 만큼 분석 처리
		Iterator<FileAnalisysDto> fileAnalisysIterator = fileAnalisysDtoList.iterator();
		
		while(fileAnalisysIterator.hasNext()) {
			FileAnalisysDto fileAnalisysDto = fileAnalisysIterator.next();
			
			sbFileClassName.append("com.flyJoin.analisys.strategy.");
			sbFileClassName.append(fileAnalisysDto.getFileName().toUpperCase());
			sbFileClassName.append("AnalisysStrategy");
			
			sbExtClassName.append("com.flyJoin.analisys.strategy.");
			sbExtClassName.append(fileAnalisysDto.getFileExtension().toUpperCase());
			sbExtClassName.append("AnalisysStrategy");
						
			fileStrategyClassName = sbFileClassName.toString();		
			extStrategyClassName  = sbExtClassName.toString();
			
			// File 명 또는 확장자 명으로 전략 클래스가 있는지 체크
			strategyClassName = "";
			if(CommonClassUtil.isClassExist(fileStrategyClassName)) {
				strategyClassName = fileStrategyClassName;
			} else if (CommonClassUtil.isClassExist(extStrategyClassName)) {
				strategyClassName = extStrategyClassName;
			}
			
			// 맞는 분석 클래스가 있을 경우 분석 처리
			if (!strategyClassName.isEmpty()) {
				Class<?> strategyClass;
				try {
					strategyClass = Class.forName(strategyClassName);
					FileAnalisysStrategy fileAanalisysStrategy = (FileAnalisysStrategy) strategyClass.newInstance();
					HashMap<String, Object> fileAanalisysResult = fileAanalisysStrategy.getResultAnalisysInfo(fileAnalisysDto);
					projectCommonDto.setAnalisysInfo(fileAanalisysResult);					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			// StringBuffer 초기화
			sbFileClassName.delete(0, sbFileClassName.capacity());
			sbExtClassName.delete(0, sbExtClassName.capacity());
		}
		
		return projectCommonDto;
	}
}