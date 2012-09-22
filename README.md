jIvalo eclipse-deps-maven-plugin is Maven plugin for importing Eclipse OSGI JAR files into Maven repository.

Create new directory and install / deploy jivalo.plugins:eclipse-deps-maven-plugin for creating 
Maven dependencies definitions for you project and scripts for deploying / installing those dependencies.

This example is for Acceleo Maven plugin:

Create directory for ex. acceleo-deps and put following Maven pom.xml in it (These are all needed dependencies for jIvalo acceleo-maven-plugin):

    <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
      <modelVersion>4.0.0</modelVersion>
      <!-- The Basics -->
      <groupId>jivalo</groupId>
      <artifactId>acceleo-deps</artifactId>
      <version>0.1-SNAPSHOT</version>
      <packaging>pom</packaging>   
      <!-- Build Settings -->
      <build>
        <plugins>
          <plugin>
            <groupId>jivalo.plugins</groupId>
            <artifactId>eclipse-deps-maven-plugin</artifactId>
            <version>1.0</version>
            <executions>
              <execution>
                <id>generate-sources</id>
                <goals>
                  <goal>generate-dependencies</goal>
                </goals>
              </execution>
              <execution>
                <id>default-cli</id>
                <goals>
                  <goal>deploy-dependencies-script</goal>
                </goals>
              </execution>
            </executions>
            <configuration>
              <eclipseInstallationFolder>/home/ivalo/eclipse</eclipseInstallationFolder>
              <repositoryId>local.nexus</repositoryId>
              <repositoryIUrl>http://localhost:8081/nexus/content/repositories/releases</repositoryIUrl>
              <dependencyJarNames>
                <param>com.google.collect</param>
                <param>lpg.runtime.java</param>
                <param>org.eclipse.acceleo.common</param>
                <param>org.eclipse.acceleo.engine</param>
                <param>org.eclipse.acceleo.model</param>
                <param>org.eclipse.acceleo.parser</param>
                <param>org.eclipse.core.contenttype</param>
                <param>org.eclipse.core.jobs</param>
                <param>org.eclipse.core.resources</param>
                <param>org.eclipse.core.runtime</param>
                <param>org.eclipse.emf.codegen.ecore</param>
                <param>org.eclipse.emf.common</param>
                <param>org.eclipse.emf.ecore.xmi</param>
                <param>org.eclipse.emf.ecore</param>
                <param>org.eclipse.equinox.common</param>
                <param>org.eclipse.equinox.preferences</param>
                <param>org.eclipse.equinox.registry</param>
                <param>org.eclipse.jdt.core</param>
                <param>org.eclipse.ocl.ecore</param>
                <param>org.eclipse.ocl</param>
                <param>org.eclipse.osgi</param>
                <param>org.eclipse.uml2.common</param>
                <param>org.eclipse.uml2.uml</param>
                <param>org.eclipse.uml2.uml</param>
                <param>org.eclipse.uml2.types</param>
              </dependencyJarNames>
            </configuration>
          </plugin>
        </plugins>    
      </build>
      <!-- More Project Information -->
      <name>Acceleo Maven Plugin dependencies generation</name>
    </project>
    
Run following Maven command in order to create Maven dependencies snipped for including into pom.xml:
    
    mvn eclipse-deps:generate-dependencies

Run following Maven command in order to create script for deploying Maven dependencies into defined Maven repository:

    mvn eclipse-deps:deploy-dependencies-script
    
Results are created under project's target directory
    