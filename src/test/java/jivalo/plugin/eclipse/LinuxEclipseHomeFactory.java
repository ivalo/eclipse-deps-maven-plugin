/**
 * 
 */
package jivalo.plugin.eclipse;

import java.io.File;

/**
 * @author markku
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
