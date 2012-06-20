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
 * @goal installDependenciesScript
 * @author Markku Saarela
 *
 */
public class InstallJars2RepoMojo extends EclipseJars2RepoMojo
{

    /**
     * Default constructor.
     */
    public InstallJars2RepoMojo()
    {
        super();
    }

    /**
     * @param targetFolder
     * @param dependencyJarNames
     */
    public InstallJars2RepoMojo( File targetFolder, List< String > dependencyJarNames )
    {
        super( targetFolder, dependencyJarNames );
    }

    @Override
    protected String getScriptBaseFileName()
    {
        return "install-eclipse-deps";
    }

    @Override
    protected CharSequence getRepoScriptCommand( EclipseOsgiJarFile eclipseOsgiJarFile )
    {
        StringBuilder command = new StringBuilder("mvn install:install-file");

        command.append( " -Dfile=" ).append( eclipseOsgiJarFile.getDependencyFile().getAbsolutePath() );
        command.append( " -DgroupId=" ).append( eclipseOsgiJarFile.getDependencyName() );
        command.append( " -DartifactId=" ).append( eclipseOsgiJarFile.getDependencyName() );
        command.append( " -Dversion=" ).append( eclipseOsgiJarFile.getVersion() );
        command.append( " -Dpackaging=" ).append( eclipseOsgiJarFile.getType() );

        return command;
    }

}
