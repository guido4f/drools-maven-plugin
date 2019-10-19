package com.guido4f.maven.drools;


import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.WithoutMojo;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ExtractDrlFromDecisionTableTest {
    @Rule
    public MojoRule rule = new MojoRule() {
        @Override
        protected void before() throws Throwable {
        }

        @Override
        protected void after() {
        }
    };

    /**
     * @throws Exception if any
     */
    @Test
    public void testDrlDefaultSheet()
            throws Exception {
        File pom = new File("target/test-classes/drools-extract/");
        assertNotNull(pom);
        assertTrue(pom.exists());

        ExtractDrlFromDecisionTable extractDrlFromDecisionTable = (ExtractDrlFromDecisionTable) rule.lookupConfiguredMojo(pom,
                                                                                                                          "drl");
        assertNotNull(extractDrlFromDecisionTable);
        extractDrlFromDecisionTable.execute();

        File outputDirectory = (File) rule.getVariableValueFromObject(extractDrlFromDecisionTable,
                                                                      "outputDirectory");
        assertNotNull(outputDirectory);
        assertTrue(outputDirectory.exists());

        File testFile = new File(outputDirectory,
                                 "TestFile.drl");
        assertTrue(testFile.exists());

        File discountFile  = new File(outputDirectory,
                                 "TestFile.drl");
        assertTrue(discountFile.exists());

    }

    /**
     * Do not need the MojoRule.
     */
    @WithoutMojo
//    @Test
    public void testSomethingWhichDoesNotNeedTheMojoAndProbablyShouldBeExtractedIntoANewClassOfItsOwn() {
        assertTrue(true);
    }

}

