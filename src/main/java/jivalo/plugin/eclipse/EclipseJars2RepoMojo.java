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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @author Markku Saarela
 * 
 */
public abstract class EclipseJars2RepoMojo extends AbstractEclipse2MavenMojo
{

    protected final OperatingSystem os;

    /**
     * Default constructor.
     */
    public EclipseJars2RepoMojo()
    {
        super();
        this.os = OperatingSystem.valueOfOsName( System.getProperty( "os.name" ) );
    }

    /**
     * @param dependencyJarNames
     */
    public EclipseJars2RepoMojo( File targetFolder, List< String > dependencyJarNames )
    {
        super( targetFolder, dependencyJarNames );
        this.os = OperatingSystem.valueOfOsName( System.getProperty( "os.name" ) );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException
    {
        List< EclipseOsgiJarFile > eclipseDependencies = getEclipseDependencies();

        StringBuilder script = new StringBuilder( startScript() );

        for ( EclipseOsgiJarFile eclipseOsgiJarFile : eclipseDependencies )
        {
            script.append( getRepoScriptCommandLine( eclipseOsgiJarFile ) ).append( LINE_BREAK );
        }

        script.append( endScript() );

        try
        {
            createScriptFile( script );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
            throw new MojoExecutionException( "Creating script " + getScriptFile().getAbsolutePath()
                    + " failed for reason " + e.getMessage(), e );
        }
    }

    private void createScriptFile( StringBuilder script ) throws IOException
    {
        BufferedWriter out = new BufferedWriter( new FileWriter( getScriptFile() ) );
        out.write( script.toString() );
        out.close();
    }

    protected abstract String getScriptBaseFileName();

    private File getScriptFile()
    {

        targetFolder.mkdirs();

        return new File( targetFolder, getScriptFileName() );
    }

    private String getScriptFileName()
    {
        return new StringBuilder( getScriptBaseFileName() ).append( "." ).append( getScriptFileExtension() ).toString();
    }

    private String getScriptFileExtension()
    {
        return this.os.getScriptFileExtension();
    }

    private CharSequence startScript()
    {
        return new StringBuilder( os.getEnableEcho() ).append( LINE_BREAK );
    }

    private CharSequence endScript()
    {
        return new StringBuilder( LINE_BREAK );
    }

    private CharSequence getRepoScriptCommandLine( EclipseOsgiJarFile eclipseOsgiJarFile )
    {
        StringBuilder stringBuilder = new StringBuilder( os.getSubScriptCall() );

        stringBuilder.append( " " ).append( getRepoScriptCommand( eclipseOsgiJarFile ) );
        return stringBuilder;
    }

    protected abstract CharSequence getRepoScriptCommand( EclipseOsgiJarFile eclipseOsgiJarFile );

}
