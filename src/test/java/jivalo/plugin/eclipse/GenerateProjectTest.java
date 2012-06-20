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

import org.junit.Test;

/**
 * @author Markku Saarela
 * 
 */
public class GenerateProjectTest
{

    @Test
    public void execute() throws Exception
    {

        File eclipseFolder = AbstractEclipse2MavenMojo.lookupEclipseInstallationFolder();

        System.out.println( eclipseFolder );

        String userDir = System.getProperty( "user.dir" );

        File parent = new File( userDir );

        parent = new File( parent, "target" );

        System.out.println( parent );

        ArrayList< String > dependencyJarNames = new ArrayList< String >( 3 );

        dependencyJarNames.add( "org.eclipse.acceleo.common" );
        dependencyJarNames.add( "org.eclipse.acceleo.compatibility" );
        dependencyJarNames.add( "org.eclipse.acceleo.compatibility.ui" );

        GenerateEclipseDependenciesMojo generateProject = new GenerateEclipseDependenciesMojo( parent,
                dependencyJarNames, "eclipse-dependencies.xml" );

        generateProject.execute();
    }

}
