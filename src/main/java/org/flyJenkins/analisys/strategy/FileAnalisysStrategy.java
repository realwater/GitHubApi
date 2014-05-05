package org.flyJenkins.analisys.strategy;

import java.util.HashMap;

import org.flyJenkins.analisys.model.FileAnalisysDto;
import org.flyJenkins.gitHub.model.ProjectDto;

public abstract class FileAnalisysStrategy {
	
	public abstract HashMap<String, Object> getResultAnalisysInfo(FileAnalisysDto fileAnalisysDto);
}