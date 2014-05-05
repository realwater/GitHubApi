package org.flyJenkins.analisys.service;

import java.util.HashMap;
import java.util.List;

import org.flyJenkins.analisys.model.FileAnalisysDto;

public interface FileAnalisysService {

	public HashMap<String, Object> getFileAnalisysResult(List<FileAnalisysDto> fileAnalisysDto);
}
