package org.flyJenkins.analisys.service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.flyJenkins.analisys.model.FileAnalisysDto;
import org.flyJenkins.analisys.model.RepoAnalisysDto;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class RepoAnalisysServiceImpl implements RepoAnalisysService {
			
	private List<FileAnalisysDto> fileInfoList;
	
	/**
	 * 저장소 분석 시작 
	 */
	@Override
	public List<FileAnalisysDto> getRepoAnalisysFileList(RepoAnalisysDto repoAnalisysCommand) {		
		fileInfoList = new ArrayList<FileAnalisysDto>();	
		
		try {
			SVNURL svnUrl = SVNURL.parseURIEncoded(repoAnalisysCommand.getRepoUrl());		
			SVNRepository repository = SVNRepositoryFactory.create(svnUrl, null);			
			
			// 인증 처리
			ISVNAuthenticationManager authenticationManager = SVNWCUtil.createDefaultAuthenticationManager(repoAnalisysCommand.getId(), repoAnalisysCommand.getPassword());
			repository.setAuthenticationManager(authenticationManager);
			
			// 파일 목록 로드
			repositoryDirectoryLoading(repository, repoAnalisysCommand.getRepoPath());			
		} catch (SVNException e) {
			e.printStackTrace();
		}
		
		return fileInfoList;
	}
	
	/**
	 * 저장소 디렉토리 하위 목록 로드
	 */
	@Override
	public void repositoryDirectoryLoading(SVNRepository repository, String path) {
		
		Collection<?> entries;
		
		try {
			entries = repository.getDir(path, -1, null, (Collection<?>) null);
			Iterator<?> iterator = entries.iterator();
			
			while (iterator.hasNext()) {
				SVNDirEntry entry = (SVNDirEntry) iterator.next();
				
				if (entry.getKind() == SVNNodeKind.DIR) { // 하위 리스트가 디렉토리일 경우
					repositoryDirectoryLoading(repository, ( path.equals( "" ) ) ? entry.getName() : path + "/" + entry.getName());
				} else { // File 일 경우 File 정보를 분석한다.					
					// File 이름, 확장자 정보 추출
					String fileName = entry.getName();
					int lastExtPos = fileName.lastIndexOf(".");					
					String fileExtension = fileName.substring( lastExtPos + 1 );
					fileName = fileName.substring(0, lastExtPos);
					
					// 파일 소스 정보 가져오기
					String fileUrl = entry.getURL().toString();					
					fileUrl = fileUrl.replaceAll(repository.getLocation().toString(), "");
					ByteArrayOutputStream byteStream = new ByteArrayOutputStream( );
					repository.getFile( fileUrl , -1 , null , byteStream );
					String sourceData = byteStream.toString();
					
					// 파일 분석 Service로 보내기
					FileAnalisysDto fileAnalisysDto = new FileAnalisysDto();
					fileAnalisysDto.setFileName(fileName);
					fileAnalisysDto.setFileExtension(fileExtension);
					fileAnalisysDto.setSourceData(sourceData);
					
					fileInfoList.add(fileAnalisysDto);
				}				
			}
		} catch (SVNException e) {
			e.printStackTrace();
		}		
	}
}
