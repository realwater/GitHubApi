package com.flyJoin.gitHubApi.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class Test {

	private SVNURL svnUrl;
	
	public void getListOff() {
		//SVNRepositoryFactoryImpl.setup();
		
		try {
			this.svnUrl = SVNURL.parseURIEncoded("https://github.com/realwater/GitHubApi");		
			SVNRepository repository = SVNRepositoryFactory.create(svnUrl, null);			
			
			// 인증 처리
			//ISVNAuthenticationManager authenticationManager = SVNWCUtil.createDefaultAuthenticationManager("realwater", "3184love");
			//repository.setAuthenticationManager(authenticationManager);
			
			getRepoList(repository, "/trunk");			
		} catch (SVNException e) {
			e.printStackTrace();
		}	
	}
	
	public void getRepoList(SVNRepository repository, String path) {
		// 리파지토리 DIR 정보 가져오기
		Collection<?> entries;
		try {
			entries = repository.getDir(path, -1, null, (Collection<?>) null);
			Iterator<?> iterator = entries.iterator();
			
			while (iterator.hasNext()) {
				SVNDirEntry entry = (SVNDirEntry) iterator.next();
				System.out.println( "/" + (path.equals( "" ) ? "" : path + "/" ) + entry.getName( ) + 
						                                   " ( author: '" + entry.getAuthor( ) + "'; revision: " + entry.getRevision( ) + 
						                                   "; date: " + entry.getDate( ) + ")" );
				if (entry.getKind() == SVNNodeKind.DIR) { // 하위 리스트가 디렉토리일 경우
					getRepoList( repository, ( path.equals( "" ) ) ? entry.getName( ) : path + "/" + entry.getName( ) );
				} else { // file 일 경우
					//System.out.println(entry.getName());
					
					// 파일 소스 정보 가져오기
//					String fileUrl = entry.getURL().toString();					
//					fileUrl = fileUrl.replaceAll(this.svnUrl.toString(), "");					
//					ByteArrayOutputStream baos = new ByteArrayOutputStream( );			
//					repository.getFile( fileUrl , -1 , null , baos );
//					System.out.println(baos.toString());
				}
				
			}
		} catch (SVNException e) {
			e.printStackTrace();
		}		
	}
		
	public static void main(String args[]) {
		
		Test test = new Test();
		test.getListOff();
		
	}
}
