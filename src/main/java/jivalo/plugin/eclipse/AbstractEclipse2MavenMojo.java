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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * @author Markku Saarela
 * 
 */
public abstract class AbstractEclipse2MavenMojo extends AbstractMojo
{

    protected static final String LINE_BREAK = System.getProperty( "line.separator" );

    private static final String PLUGINS = "plugins";

    /**
     * 
     */
    @Parameter( property = "targetFolder", defaultValue = "${project.build.directory}", required = true )
    protected File targetFolder;

    /**
     * 
     */
    @Parameter( property = "eclipseInstallationFolder", required = true )
    protected File eclipseInstallationFolder;

    /**
     * list of dependecies to process. Name is jar name without OSGi version number and .jar file extension. If there is
     * more than one version of that dependency latest is selected.
     * 
     * @parameter
     */
    @Parameter( property = "dependencyJarNames", required = false )
    protected List< String > dependencyJarNames;

    /**
     * Default constructor.
     */
    public AbstractEclipse2MavenMojo()
    {
        super();
    }

    public List< String > getDependencyJarNames()
    {
        if ( this.dependencyJarNames != null )
        {
            return dependencyJarNames;
        }
        return new ArrayList< String >( 0 );
    }

    public List< EclipseOsgiJarFile > getEclipseDependencies()
    {
        DependencyFileFilter dependencyFileFilter = new DependencyFileFilter( getDependencyJarNames() );

        ArrayList< EclipseOsgiJarFile > list = new ArrayList< EclipseOsgiJarFile >();

        File pluginsDirectory = resolvePluginsDirectory();

        File[] listFiles = pluginsDirectory.listFiles( dependencyFileFilter );

        HashMap< String, EclipseOsgiJarFile > map = new HashMap< String, EclipseOsgiJarFile >();

        for ( File file : listFiles )
        {
            EclipseOsgiJarFile eclipseOsgiJarFile = new EclipseOsgiJarFile( file );
            map.put( eclipseOsgiJarFile.getDependencyName(), eclipseOsgiJarFile );
        }

        for ( Entry< String, EclipseOsgiJarFile > entry : map.entrySet() )
        {
            list.add( entry.getValue() );
        }

        return list;
    }

    private File resolvePluginsDirectory()
    {
        return new File( this.eclipseInstallationFolder, PLUGINS );
    }

    /**
     * 
     * @author Markku Saarela
     * 
     */
    public static class EclipseOsgiJarFile
    {
        private final String dependencyName;
        private final String version;
        private final File dependencyFile;
        private final String type;

        /**
         * @param jarName
         * @param version
         */
        public EclipseOsgiJarFile( File dependencyFile )
        {
            super();
            this.dependencyFile = dependencyFile;
            this.dependencyName = parseDependencyName( dependencyFile );
            this.version = parseVersion( dependencyFile );
            this.type = parseType( dependencyFile );
        }

        /**
         * @return the dependencyName
         */
        public String getDependencyName()
        {
            return dependencyName;
        }

        /**
         * @return the version
         */
        public String getVersion()
        {
            return version;
        }

        /**
         * @return the dependencyFile
         */
        public File getDependencyFile()
        {
            return dependencyFile;
        }

        /**
         * @return the type
         */
        public String getType()
        {
            return type;
        }

        private String parseVersion( File dependencyFile )
        {
            return dependencyFile.getName().substring( dependencyFile.getName().indexOf( "_" ) + 1,
                    dependencyFile.getName().lastIndexOf( "." ) );
        }

        private String parseDependencyName( File dependencyFile )
        {
            return DependencyFileFilter.getOsgiFileName( dependencyFile.getName() );
        }

        private String parseType( File dependencyFile )
        {
            return dependencyFile.getName().substring( dependencyFile.getName().lastIndexOf( "." ) + 1 );
        }

        @Override
        public String toString()
        {
            StringBuilder sb = new StringBuilder( dependencyName );
            sb.append( " " ).append( version );
            sb.append( " " ).append( type );
            return sb.toString();
        }

    }

}
