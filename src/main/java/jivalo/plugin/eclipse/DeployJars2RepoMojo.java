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

import java.io.File;
import java.util.List;

/**
 * @goal deployDependenciesScript
 * @author Markku Saarela
 * 
 */
public class DeployJars2RepoMojo extends EclipseJars2RepoMojo
{

    /**
     * @parameter
     * @required
     */
    private String repositoryId;

    /**
     * @parameter
     * @required
     */
    private String repositoryIUrl;

    /**
     * Default constructor.
     */
    public DeployJars2RepoMojo()
    {
        super();
    }

    /**
     * @param repositoryId
     * @param repositoryIUrl
     */
    DeployJars2RepoMojo(  File targetFolder, List< String > dependencyJarNames, String repositoryId, String repositoryIUrl )
    {
        super( targetFolder, dependencyJarNames );
        this.repositoryId = repositoryId;
        this.repositoryIUrl = repositoryIUrl;
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

        command.append( " -DgroupId=" ).append( eclipseOsgiJarFile.getDependencyName() );
        command.append( " -DartifactId=" ).append( eclipseOsgiJarFile.getDependencyName() );
        command.append( " -Dversion=" ).append( eclipseOsgiJarFile.getVersion() );
        command.append( " -Dpackaging=" ).append( eclipseOsgiJarFile.getType() );
        command.append( " -Dfile=" ).append( eclipseOsgiJarFile.getDependencyFile().getAbsolutePath() );
        command.append( " -DrepositoryId=" ).append( this.repositoryId );
        command.append( " -Durl=" ).append( this.repositoryIUrl );

        // mvn deploy:deploy-file -DgroupId=<group-id> \
        // -DartifactId=<artifact-id> \
        // -Dversion=<version> \
        // -Dpackaging=<type-of-packaging> \
        // -Dfile=<path-to-file> \
        // -DrepositoryId=<id-to-map-on-server-section-of-settings.xml> \
        // -Durl=<url-of-the-repository-to-deploy>
        return command;
    }

}
