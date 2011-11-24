package org.testobject.jenkins.git;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.errors.UnmergedPathException;
import org.eclipse.jgit.transport.RefSpec;

public class GitService {
	
	private static final GitService INSTANCE = new GitService();
	
	public static GitService getInstance(){
		return INSTANCE;
	}
	
	public static final Logger LOGGER = Logger.getLogger(GitService.class.getName());
	
	private Git git;
	
	private GitService() {
	}
	
	public void init(File directory){
		try {
			git = Git.open(directory);
			LOGGER.log(Level.INFO, "Loaded Git Repo from: " + directory.getAbsolutePath());
		} catch (IOException e) {
			git = Git.init().setDirectory(directory).call();
			LOGGER.log(Level.INFO, "Created Git Repo in: " + directory.getAbsolutePath());
		}
	}
	
	public void addAll(){
		try {
			DirCache dirCache = git.add().addFilepattern(".").call();
			for (int i = 0; i < dirCache.getEntryCount(); i++) {
				LOGGER.log(Level.INFO, "File: " + dirCache.getEntry(i).getPathString() + " available in DirCache");
			}
		} catch (NoFilepatternException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void commit(String username, String email, String message){
		try {
			git.commit().setCommitter(username, email).setMessage(message).call();
		} catch (GitAPIException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		} catch (UnmergedPathException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void push(String remote){
		try {
			git.push().setRemote(remote).setPushAll().call();
		} catch (InvalidRemoteException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void fetch(){
		try {
			git.fetch().setRefSpecs(new RefSpec("refs/heads/master")).setRemote("git@github.com:aluedeke/jenkins-backup.git").call();
		} catch (GitAPIException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		}
	}
	

}
