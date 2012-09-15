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

import org.apache.maven.plugin.testing.AbstractMojoTestCase;

/**
 * @author Markku Saarela
 * 
 */
public class InstallJars2RepoMojoTest extends AbstractMojoTestCase
{

    private InstallJars2RepoMojo mojo;

    /**
     * Default constructor.
     */
    public InstallJars2RepoMojoTest()
    {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setUp() throws Exception
    {
        // required for mojo lookups to work
        super.setUp();
        File testPom = new File( getBasedir(), "src/test/resources/unit/plugin-config.xml" );
        mojo = (InstallJars2RepoMojo) lookupMojo( "install-dependencies-script", testPom );
    }

    public void testExecute() throws Exception
    {
        setVariableValueToObject( mojo, "eclipseInstallationFolder", EclipseHomeFactory.newFactory()
                .resolveEclipseHome() );
        setVariableValueToObject( mojo, "targetFolder", new File( getBasedir(), "/target" ) );
        ArrayList< String > dependencyJarNames = new ArrayList< String >( 2 );
        dependencyJarNames.add( "org.eclipse.core.runtime" );
        dependencyJarNames.add( "org.eclipse.core.variables" );
        setVariableValueToObject( mojo, "dependencyJarNames", dependencyJarNames );
        mojo.execute();
    }
}
