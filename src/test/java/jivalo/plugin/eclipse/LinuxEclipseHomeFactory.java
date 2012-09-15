/**
 * Copyright (c) 2012 Markku Saarela.
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
public class LinuxEclipseHomeFactory extends EclipseHomeFactory
{

    /**
     * Default constructor.
     */
    public LinuxEclipseHomeFactory()
    {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public File resolveEclipseHome()
    {
        return lookupEclipseInstallationFolder( getUserHome() );
    }

    private File getUserHome()
    {
        return new File( System.getProperty( "user.home" ) );
    }
}
