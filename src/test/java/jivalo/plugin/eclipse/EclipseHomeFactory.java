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

/**
 * @author Markku Saarela
 * 
 */
public abstract class EclipseHomeFactory
{

    public static EclipseHomeFactory newFactory()
    {
        switch ( OS.platformOs() )
        {
            case LINUX:
                return new LinuxEclipseHomeFactory();
            case WINDOWS:
                return new WindowsEclipseHomeFactory();
            default:
                break;
        }
        return null;
    }

    public abstract File resolveEclipseHome();

    protected File lookupEclipseInstallationFolder( File root )
    {
        File installationFolder = null;
        File[] listFiles = root.listFiles();
        for ( File file : listFiles )
        {
            if ( file.isDirectory() && file.getName().startsWith( "eclipse" ) )
            {
                installationFolder = file;
            }
        }
        return installationFolder;
    }
}
