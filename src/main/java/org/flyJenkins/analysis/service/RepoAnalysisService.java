package org.flyJenkins.analysis.service;

import java.util.List;

import org.flyJenkins.analysis.model.FileAnalysisDto;
import org.flyJenkins.analysis.model.RepoAnalysisDto;

public interface RepoAnalysisService {

	public List<FileAnalysisDto> getRepoAnalisysFileList(RepoAnalysisDto repoAnalysisDto);
}
