package org.flyJenkins.analysis.service;

import java.util.List;

import org.flyJenkins.analysis.model.FileAnalysisDto;
import org.flyJenkins.analysis.model.RepoAnalysisDto;
import org.tmatesoft.svn.core.io.SVNRepository;

public interface RepoAnalysisService {

	public List<FileAnalysisDto> getRepoAnalisysFileList(RepoAnalysisDto repoAnalysisDto);

	public void repositoryDirectoryLoading(SVNRepository repository, String path);
}
