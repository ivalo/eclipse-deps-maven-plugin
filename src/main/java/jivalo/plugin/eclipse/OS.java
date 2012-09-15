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

/**
 * @author Markku Saarela
 * 
 */
public enum OS
{
    LINUX( "sh", null, null ), WINDOWS( "bat", "call", "echo on" );

    private final String scriptFileExtension;
    private final String subScriptCall;
    private final String enableEcho;

    /**
     * @param scriptFileExtension
     */
    private OS( String scriptFileExtension, String subScriptCall, String enableEcho )
    {
        this.scriptFileExtension = scriptFileExtension;
        this.subScriptCall = subScriptCall;
        this.enableEcho = enableEcho;
    }

    /**
     * @return the scriptFileExtension
     */
    public String getScriptFileExtension()
    {
        return scriptFileExtension;
    }

    /**
     * @return the subScriptCall
     */
    public String getSubScriptCall()
    {
        return subScriptCall;
    }

    /**
     * @return the enableEcho
     */
    public String getEnableEcho()
    {
        return enableEcho;
    }

    /**
     * 
     * @param osName
     *            The Operating system name
     * @return
     */
    public static OS platformOs()
    {

        String osName = System.getProperty( "os.name" );
        if ( osName != null && osName.startsWith( "Windows" ) )
        {
            return WINDOWS;
        }

        return LINUX;
    }
}
