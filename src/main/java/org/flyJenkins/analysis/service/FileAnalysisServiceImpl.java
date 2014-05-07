package org.flyJenkins.analysis.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.flyJenkins.analysis.model.FileAnalysisDto;
import org.flyJenkins.analysis.strategy.FileAnalysisStrategy;
import org.flyJenkins.common.util.CommonClassUtil;

public class FileAnalysisServiceImpl implements FileAnalysisService {

	/**
	 * 파일 소스 분석 결과 리턴
	 */
	@Override
	public HashMap<String, Object> getFileAnalisysResult(List<FileAnalysisDto> fileAnalysisDtoList) {
		HashMap<String, Object> projectAnalisysInfo = new HashMap<String, Object>();

		StringBuffer sbFileClassName 	  = new StringBuffer();
		StringBuffer sbExtClassName 	  = new StringBuffer();

		String extStrategyClassName 	  = "";
		String fileStrategyClassName 	  = "";
		String strategyClassName 		  = "";

		// 파일 리스트 만큼 분석 처리
		Iterator<FileAnalysisDto> fileAnalysisIterator = fileAnalysisDtoList.iterator();

		while(fileAnalysisIterator.hasNext()) {
			FileAnalysisDto fileAnalysisDto = fileAnalysisIterator.next();

			sbFileClassName.append("org.flyJenkins.analysis.strategy.");
			sbFileClassName.append(fileAnalysisDto.getFileName().toUpperCase());
			sbFileClassName.append("AnalysisStrategy");

			sbExtClassName.append("org.flyJenkins.analysis.strategy.");
			sbExtClassName.append(fileAnalysisDto.getFileExtension().toUpperCase());
			sbExtClassName.append("AnalysisStrategy");

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
					FileAnalysisStrategy fileAanalisysStrategy = (FileAnalysisStrategy) strategyClass.newInstance();
					HashMap<String, Object> fileAanalisysResult = fileAanalisysStrategy.getResultAnalisysInfo(fileAnalysisDto);
					projectAnalisysInfo.putAll(fileAanalisysResult);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// StringBuffer 초기화
			sbFileClassName.delete(0, sbFileClassName.capacity());
			sbExtClassName.delete(0, sbExtClassName.capacity());
		}

		return projectAnalisysInfo;
	}
}