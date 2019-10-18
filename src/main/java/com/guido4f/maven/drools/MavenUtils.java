package com.guido4f.maven.drools;

import org.codehaus.plexus.util.DirectoryScanner;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class MavenUtils {
    private MavenUtils() {
        //DO NOT INSTANTIATE
    }

    static List<String> findFiles(final File srcDirectory, final String[] excludes, final String[] includes) {
        DirectoryScanner ds = new DirectoryScanner();
        ds.setBasedir(srcDirectory);
        ds.setIncludes(includes);
        ds.setExcludes(excludes);
        ds.scan();

        return Arrays.asList(ds.getIncludedFiles());
    }

    static String extractSubPath(final File srcDirectory, final File sourceFile) {
        return sourceFile.getParent()
                         .replace(srcDirectory.getAbsolutePath(),
                                  "");
    }
}
