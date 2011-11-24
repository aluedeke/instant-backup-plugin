package org.testobject.jenkins;

import hudson.Plugin;
import hudson.model.Hudson;

import java.util.logging.Logger;

import org.testobject.jenkins.git.GitService;

public class InstantBackupPlugin extends Plugin {
	
	private static final Logger LOGGER = Logger.getLogger(InstantBackupPlugin.class.getName());
	
	private String committerName;
	private String committerMail;
	
	private String commitMessage;
	
	private String remoteUri;
	
	@Override
	public void start() throws Exception {
		GitService.getInstance().init(Hudson.getInstance().getRootDir());
		
		LOGGER.info("Instant Backup Plugin started");
	}
	
	@Override
	public void stop() throws Exception {
		LOGGER.info("Instant Backup Plugin stopped");
	}

	public String getCommitterName() {
		return committerName;
	}

	public String getCommitterMail() {
		return committerMail;
	}

	public String getCommitMessage() {
		return commitMessage;
	}

	public String getRemoteUri() {
		return remoteUri;
	}

	public void setCommitterName(String committerName) {
		this.committerName = committerName;
	}

	public void setCommitterMail(String committerMail) {
		this.committerMail = committerMail;
	}

	public void setCommitMessage(String commitMessage) {
		this.commitMessage = commitMessage;
	}

	public void setRemoteUri(String remoteUri) {
		this.remoteUri = remoteUri;
	}
	
}
