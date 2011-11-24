package org.testobject.jenkins;

import hudson.Extension;
import hudson.model.ManagementLink;
import hudson.model.Hudson;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.testobject.jenkins.git.GitService;

@Extension
public class InstantBackupManagementLink extends ManagementLink {
	
	public static final Logger LOGGER = Logger.getLogger(InstantBackupManagementLink.class.getName());
	
	private static final String URL_NAME = "InstantBackup";
	private static final String DISPLAY_NAME = "Instant Backup";
	
	private static final String ICON = "package.gif";
	
	private String committerName;
	private String committerEmail;
	
	private String commitMessage;
	
	private String remoteUri;
	
	private final InstantBackupPlugin instantBackupPlugin = Hudson.getInstance().getPlugin(InstantBackupPlugin.class);
	
	public void doBackup(final StaplerRequest res, final StaplerResponse rsp){
		LOGGER.log(Level.INFO, "backup started");
		
		GitService git = GitService.getInstance();
		git.addAll();
		git.commit(committerName, committerEmail, commitMessage);
		git.push(remoteUri);
		
		LOGGER.log(Level.INFO, "backup finished");
	}
	
	public void doRestore(final StaplerRequest res, final StaplerResponse rsp){
		LOGGER.log(Level.INFO, "restore started");
		
		GitService git = GitService.getInstance();
		git.fetch();
		
		git.
		
		LOGGER.log(Level.INFO, "restore finished");
	}
	
	public void doSaveSettings(
			@QueryParameter("committerName") String committerName, 
			@QueryParameter("committerMail") String committerMail, 
			@QueryParameter("commitMessage") String commitMessage, 
			@QueryParameter("remoteUri") String remoteUri) throws IOException{
		instantBackupPlugin.setCommitterName(committerName);
		instantBackupPlugin.setCommitterMail(committerMail);
		instantBackupPlugin.setCommitMessage(commitMessage);
		instantBackupPlugin.setRemoteUri(remoteUri);
		
		instantBackupPlugin.save();
	}
	
	public String getDisplayName() {
		return DISPLAY_NAME;
	}
	
	@Override
	public String getUrlName() {
		return URL_NAME;
	}
	
	@Override
	public String getIconFileName() {
		return ICON;
	}
	
	public InstantBackupPlugin getConfiguration(){
		return instantBackupPlugin;
	}
	
	

}
