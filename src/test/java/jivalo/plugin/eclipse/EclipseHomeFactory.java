/**
 * 
 */
package jivalo.plugin.eclipse;

import java.io.File;

/**
 * @author markku
 * 
 */
public abstract class EclipseHomeFactory
{

    // /**
    // *
    // */
    // public EclipseHomeFactory()
    // {
    // super();
    // }

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
