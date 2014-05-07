package org.flyJenkins.analysis.strategy;

import java.util.HashMap;

import org.flyJenkins.analysis.model.FileAnalysisDto;

public abstract class FileAnalysisStrategy {

	public abstract HashMap<String, Object> getResultAnalisysInfo(FileAnalysisDto fileAnalysisDto);
}