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
import java.io.FileFilter;
import java.util.List;

/**
 * @author Markku Saarela
 *
 */
public class DependencyFileFilter implements FileFilter
{

    private final List< String > dependencyJarNames;

    
    /**
     * @param dependencyJarNames
     */
    public DependencyFileFilter( List< String > dependencyJarNames )
    {
        super();
        this.dependencyJarNames = dependencyJarNames;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean accept( File pathname )
    {
        if (pathname.isDirectory()) {
            return false;
        }

        String osgiFileNamePrefix = getOsgiFileName( pathname );
        
        return this.dependencyJarNames.contains( osgiFileNamePrefix );
    }
    
    private String getOsgiFileName(File file) {
        
        return getOsgiFileName( file.getName() );
    }

    static String getOsgiFileName(String fileName) {
        
        return fileName.substring( 0, fileName.indexOf( "_" ) );
    }

}
