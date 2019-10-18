package com.guido4f.maven.drools;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.guido4f.maven.drools.DroolsUtils.getDRL;
import static com.guido4f.maven.drools.MavenUtils.extractSubPath;
import static com.guido4f.maven.drools.MavenUtils.findFiles;

/**
 * Goal which touches a timestamp file.
 */
@Mojo(name = "drl", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class ExtractDrlFromDecisionTable
        extends AbstractMojo {

    public static final String DRL_FILE = ".drl";
    /**
     * worksheet to extract
     */
    @Parameter(defaultValue = "Rules", property = "worksheet", required = false)
    private String worksheet;


    /**
     * Location of the fileoutput files.
     */
    @Parameter(defaultValue = "${project.build.directory}/rules", property = "outputDir", required = false)
    private File outputDirectory;

    /**
     * Location of the file.
     */
    @Parameter(defaultValue = "${project.basedir}/src/main/drools/", property = "sourceDir", required = false)
    private File srcDirectory;

    /**
     * Pattern of files to find
     */
    @Parameter(defaultValue = "**/*.xls", property = "includes", required = false)
    private String[] includes;

    /**
     * Pattern of files to exclude
     */
    @Parameter(defaultValue = "", property = "excludes", required = false)
    private String[] excludes;


    public void execute()
            throws MojoExecutionException {

        final File srcDirectory = this.srcDirectory;
        final String[] excludes = this.excludes;
        final String[] includes = this.includes;
        final File outputDirectory = this.outputDirectory;

        List<String> files = findFiles(srcDirectory,
                                       excludes,
                                       includes);
        for (String file : files) {
            final File sourceFile = new File(srcDirectory,
                                             file);
            final String filename = FilenameUtils.removeExtension(sourceFile.getName());
            getLog().info("Source file " + sourceFile.getAbsolutePath());

            final String drlPath = extractSubPath(srcDirectory,
                                                  sourceFile);
            getLog().info("DRL Isolated Path  " + drlPath);
            final File drlFile = new File(combinePaths(outputDirectory,
                                                       drlPath),
                                          filename + DRL_FILE);
            getLog().info("DRL File output Path  " + drlFile.getAbsolutePath());

            final File parentFile = drlFile.getParentFile();
            writeDrl(drlFile,
                     extractDrl(sourceFile),
                     parentFile);

        }

    }

    public String extractDrl(final File sourceFile) throws MojoExecutionException {
        final String drl;
        try {
            drl = getDRL(new FileInputStream(sourceFile));
        } catch (FileNotFoundException e) {
            throw new MojoExecutionException("Failed to get DRL from Source Decision Table :" + sourceFile,
                                             e);
        }
        return drl;
    }

    public void writeDrl(final File drlFile, final String drl, final File parentFile) throws MojoExecutionException {
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            FileUtils.write(drlFile.getAbsoluteFile(),
                            drl,
                            StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new MojoExecutionException("Failed to write DRL to Drl File :" + drlFile,
                                             e);
        }
    }

    public File combinePaths(final File outputDirectory, final String drlPath) {
        final File parent;
        if (null != drlPath) {
            parent = new File(outputDirectory,
                              drlPath);
        } else {
            parent = outputDirectory;
        }
        return parent;
    }


}
