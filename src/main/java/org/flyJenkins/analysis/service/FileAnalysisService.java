package org.flyJenkins.analysis.service;

import java.util.HashMap;
import java.util.List;

import org.flyJenkins.analysis.model.FileAnalysisDto;

public interface FileAnalysisService {

	public HashMap<String, Object> getFileAnalisysResult(List<FileAnalysisDto> fileAnalisysDto);
}
