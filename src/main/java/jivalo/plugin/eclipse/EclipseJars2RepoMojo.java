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

    /**
     * Default constructor.
     */
    public EclipseJars2RepoMojo()
    {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException
    {
        List< EclipseOsgiJarFile > eclipseDependencies = getEclipseDependencies();

        if ( eclipseDependencies != null && !eclipseDependencies.isEmpty() )
        {
            try
            {
                createScriptFile( buildScript( eclipseDependencies ) );
            }
            catch ( IOException e )
            {
                e.printStackTrace();
                throw new MojoExecutionException( "Creating script " + getScriptFile().getAbsolutePath()
                        + " failed for reason " + e.getMessage(), e );
            }
        }
        else
        {
            getLog().info( "Eclipse Dependencies list is empty" );
        }
    }

    protected abstract String getScriptBaseFileName();

    protected abstract CharSequence getRepoScriptCommand( EclipseOsgiJarFile eclipseOsgiJarFile );

    protected StringBuilder appendParameter( StringBuilder command, CharSequence parameterName,
            CharSequence parameterValue )
    {
        return command.append( " -D" ).append( parameterName ).append( "=" ).append( parameterValue );
    }

    private StringBuilder buildScript( List< EclipseOsgiJarFile > eclipseDependencies )
    {
        StringBuilder script = new StringBuilder( startScript() );
        for ( EclipseOsgiJarFile eclipseOsgiJarFile : eclipseDependencies )
        {
            script.append( getRepoScriptCommandLine( eclipseOsgiJarFile ) ).append( LINE_BREAK );
        }
        script.append( endScript() );
        return script;
    }

    private void createScriptFile( StringBuilder script ) throws IOException
    {
        BufferedWriter out = new BufferedWriter( new FileWriter( getScriptFile() ) );
        out.write( script.toString() );
        out.close();
    }

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
        return OS.platformOs().getScriptFileExtension();
    }

    private CharSequence startScript()
    {
        String enableEcho = OS.platformOs().getEnableEcho();
        if ( enableEcho == null )
        {
            return new StringBuilder();
        }
        return new StringBuilder( enableEcho ).append( LINE_BREAK );
    }

    private CharSequence endScript()
    {
        return new StringBuilder( LINE_BREAK );
    }

    private CharSequence getRepoScriptCommandLine( EclipseOsgiJarFile eclipseOsgiJarFile )
    {
        String subScriptCall = OS.platformOs().getSubScriptCall();
        StringBuilder sb = new StringBuilder();
        if ( subScriptCall != null )
        {
            sb = sb.append( subScriptCall ).append( " " );
        }
        return sb.append( getRepoScriptCommand( eclipseOsgiJarFile ) );
    }
}
