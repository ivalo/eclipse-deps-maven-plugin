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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @goal generateDependencies
 * @author Markku Saarela
 * 
 */
public class GenerateEclipseDependenciesMojo extends AbstractEclipse2MavenMojo
{

    /**
     * @parameter default-value="eclipse-dependencies.xml"
     * @required
     */
    private String dependenciesFileName;

    /**
     * Default contructor.
     */
    public GenerateEclipseDependenciesMojo()
    {
        super();
    }

    /**
     * Testing constructor.
     * 
     * @param eclipseInstallationFolder
     * @param dependencyJarName
     * @param dependenciesFileName
     */
    GenerateEclipseDependenciesMojo( File targetFolder, List< String > dependencyJarName, String dependenciesFileName )
    {
        super( targetFolder, dependencyJarName );
        this.dependenciesFileName = dependenciesFileName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException
    {
        List< EclipseOsgiJarFile > eclipseDependencies = getEclipseDependencies();
        System.out.println( eclipseDependencies );
        writeXml( eclipseDependencies );
    }

    private void writeXml( List< EclipseOsgiJarFile > eclipseDependencies )
    {
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = null;
        try
        {

            writer = factory.createXMLStreamWriter( getOutputStream(), "UTF-8" );

            int numberOfOpenStartedElements = 0;
            writer.writeStartDocument( "1.0" );
            writer.writeCharacters( getFormat( numberOfOpenStartedElements ) );

            writer.writeStartElement( "dependencies" );
            numberOfOpenStartedElements++;
            for ( EclipseOsgiJarFile eclipseOsgiJarFile : eclipseDependencies )
            {
                writeDependency( eclipseOsgiJarFile, writer, numberOfOpenStartedElements );
            }
            numberOfOpenStartedElements--;
            writer.writeCharacters( getFormat( numberOfOpenStartedElements ) );

            writer.writeEndElement();
            writer.writeEndDocument();

            writer.flush();
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            if ( writer != null )
            {
                try
                {
                    writer.close();
                }
                catch ( XMLStreamException e )
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    private OutputStream getOutputStream() throws IOException
    {
        return new FileOutputStream( createDepsXmlFile() );
    }

    private File createDepsXmlFile() throws IOException
    {
        targetFolder.mkdirs();

        File file = new File( targetFolder, dependenciesFileName );
        
        return new File(file.getAbsolutePath());
    }

    private void writeDependency( EclipseOsgiJarFile eclipseOsgiJarFile, XMLStreamWriter writer,
            int numberOfOpenStartedElements ) throws XMLStreamException
    {
        writer.writeCharacters( getFormat( numberOfOpenStartedElements ) );
        writer.writeStartElement( "dependency" );
        numberOfOpenStartedElements++;
        writer.writeCharacters( getFormat( numberOfOpenStartedElements ) );

        writer.writeStartElement( "groupId" );
        writer.writeCharacters( eclipseOsgiJarFile.getDependencyName() );
        writer.writeEndElement();
        writer.writeCharacters( getFormat( numberOfOpenStartedElements ) );

        writer.writeStartElement( "artifactId" );
        writer.writeCharacters( eclipseOsgiJarFile.getDependencyName() );
        writer.writeEndElement();
        writer.writeCharacters( getFormat( numberOfOpenStartedElements ) );

        writer.writeStartElement( "type" );
        writer.writeCharacters( eclipseOsgiJarFile.getType() );
        writer.writeEndElement();
        writer.writeCharacters( getFormat( numberOfOpenStartedElements ) );

        writer.writeStartElement( "version" );
        writer.writeCharacters( eclipseOsgiJarFile.getVersion() );
        writer.writeEndElement();
        numberOfOpenStartedElements--;
        writer.writeCharacters( getFormat( numberOfOpenStartedElements ) );
        writer.writeEndElement();
    }

    private String getFormat( int numberOfOpenStartedElements )
    {
        return new StringBuilder( LINE_BREAK ).append( getIndent( numberOfOpenStartedElements ) ).toString();
    }

    private CharSequence getIndent( int numberOfOpenStartedElements )
    {
        StringBuilder indent = new StringBuilder( "  " );

        for ( int i = 0; i < numberOfOpenStartedElements; i++ )
        {
            indent.append( "  " );
        }
        return indent;
    }

}
