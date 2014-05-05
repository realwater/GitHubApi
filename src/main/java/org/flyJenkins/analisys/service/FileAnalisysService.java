package org.flyJenkins.analisys.service;

import java.util.List;

import org.flyJenkins.analisys.model.FileAnalisysDto;
import org.flyJenkins.gitHub.model.ProjectCommonDto;

public interface FileAnalisysService {

	public ProjectCommonDto getFileAnalisysResult(List<FileAnalisysDto> fileAnalisysDto);
}
