/**
 * 
 */
package jivalo.plugin.eclipse;

import java.io.File;

/**
 * @author markku
 * 
 */
public class WindowsEclipseHomeFactory extends EclipseHomeFactory
{

    /**
     * Default constructor.
     */
    public WindowsEclipseHomeFactory()
    {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public File resolveEclipseHome()
    {
        return lookupEclipseInstallationFolder();
    }

    private File lookupEclipseInstallationFolder()
    {
        File[] listRoots = File.listRoots();
        for ( File file : listRoots )
        {
            File installationFolder = lookupEclipseInstallationFolder( file );
            if ( installationFolder != null )
            {
                return installationFolder;
            }
        }
        return null;
    }
}
