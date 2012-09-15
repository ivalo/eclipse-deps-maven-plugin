/**
 * Copyright (c) 2011 - 2012 Markku Saarela.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Markku Saarela - initial implementation
 */
package jivalo.plugin.eclipse;

import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * 
 * @author Markku Saarela
 * 
 */
@Mojo( name = "deploy-dependencies-script", threadSafe = true )
public class DeployJars2RepoMojo extends EclipseJars2RepoMojo
{

    /**
     * 
     */
    @Parameter( property = "repositoryId", required = true )
    private String repositoryId;

    /**
     * 
     */
    @Parameter( property = "repositoryIUrl", required = true )
    private String repositoryIUrl;

    /**
     * Default constructor.
     */
    public DeployJars2RepoMojo()
    {
        super();
    }

    @Override
    protected String getScriptBaseFileName()
    {
        return "deploy-eclipse-deps";
    }

    @Override
    protected CharSequence getRepoScriptCommand( EclipseOsgiJarFile eclipseOsgiJarFile )
    {
        StringBuilder command = new StringBuilder( "mvn deploy:deploy-file" );
        command = appendParameter( command, "groupId", eclipseOsgiJarFile.getDependencyName() );
        command = appendParameter( command, "artifactId", eclipseOsgiJarFile.getDependencyName() );
        command = appendParameter( command, "version", eclipseOsgiJarFile.getVersion() );
        command = appendParameter( command, "packaging", eclipseOsgiJarFile.getType() );
        command = appendParameter( command, "file", eclipseOsgiJarFile.getDependencyFile().getAbsolutePath() );
        command = appendParameter( command, "repositoryId", this.repositoryId );
        return appendParameter( command, "url", this.repositoryIUrl );
    }

}
