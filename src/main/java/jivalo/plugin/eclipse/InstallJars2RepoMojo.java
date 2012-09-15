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

/**
 * @author Markku Saarela
 * 
 */
@Mojo( name = "install-dependencies-script", threadSafe = true )
public class InstallJars2RepoMojo extends EclipseJars2RepoMojo
{

    /**
     * Default constructor.
     */
    public InstallJars2RepoMojo()
    {
        super();
    }

    @Override
    protected String getScriptBaseFileName()
    {
        return "install-eclipse-deps";
    }

    @Override
    protected CharSequence getRepoScriptCommand( EclipseOsgiJarFile eclipseOsgiJarFile )
    {
        StringBuilder command = new StringBuilder( "mvn install:install-file" );
        command = appendParameter( command, "file", eclipseOsgiJarFile.getDependencyFile().getAbsolutePath() );
        command = appendParameter( command, "groupId", eclipseOsgiJarFile.getDependencyName() );
        command = appendParameter( command, "artifactId", eclipseOsgiJarFile.getDependencyName() );
        command = appendParameter( command, "version", eclipseOsgiJarFile.getVersion() );
        return appendParameter( command, "packaging", eclipseOsgiJarFile.getType() );
    }
}
