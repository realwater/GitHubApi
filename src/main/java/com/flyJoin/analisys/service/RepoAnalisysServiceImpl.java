package com.flyJoin.analisys.service;

import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.Iterator;

import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.flyJoin.analisys.command.RepoAnalisysCommand;

public class RepoAnalisysServiceImpl {
	
	
	public void analisysStart(RepoAnalisysCommand repoAnalisysCommand) {		
		try {
			SVNURL svnUrl = SVNURL.parseURIEncoded(repoAnalisysCommand.getRepoUrl());		
			SVNRepository repository = SVNRepositoryFactory.create(svnUrl, null);			
			
			// 인증 처리
			ISVNAuthenticationManager authenticationManager = SVNWCUtil.createDefaultAuthenticationManager(repoAnalisysCommand.getId(), repoAnalisysCommand.getPassword());
			repository.setAuthenticationManager(authenticationManager);
			
			repositoryAnalisysInfo(repository, repoAnalisysCommand.getRepoPath());
		} catch (SVNException e) {
			e.printStackTrace();
		}		
	}
	
	public void repositoryAnalisysInfo(SVNRepository repository, String path) {
		// 리파지토리 DIR 정보 가져오기
		Collection<?> entries;
		try {
			entries = repository.getDir(path, -1, null, (Collection<?>) null);
			Iterator<?> iterator = entries.iterator();
			
			while (iterator.hasNext()) {
				SVNDirEntry entry = (SVNDirEntry) iterator.next();
				
				if (entry.getKind() == SVNNodeKind.DIR) { // 하위 리스트가 디렉토리일 경우	
					repositoryAnalisysInfo(repository, ( path.equals( "" ) ) ? entry.getName() : path + "/" + entry.getName());
				} else { // file 일 경우					
					// 파일 소스 정보 가져오기
					String fileUrl = entry.getURL().toString();					
					fileUrl = fileUrl.replaceAll(repository.getLocation().toString(), "");					
					ByteArrayOutputStream baos = new ByteArrayOutputStream( );			
					repository.getFile( fileUrl , -1 , null , baos );
					System.out.println(baos.toString());
				}				
			}
		} catch (SVNException e) {
			e.printStackTrace();
		}		
	}	
}
