package org.flyJenkins.analisys.service;

import java.util.List;

import org.flyJenkins.analisys.model.FileAnalisysDto;
import org.flyJenkins.analisys.model.RepoAnalisysDto;
import org.tmatesoft.svn.core.io.SVNRepository;

public interface RepoAnalisysService {

	public List<FileAnalisysDto> getRepoAnalisysFileList(RepoAnalisysDto repoAnalisysCommand);
	
	public void repositoryDirectoryLoading(SVNRepository repository, String path);
}
