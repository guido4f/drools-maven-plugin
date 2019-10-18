package guido4f.plugin;


import com.guido4f.maven.drools.ExtractDrlFromDecisionTable;
import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.WithoutMojo;

import org.junit.Rule;
import static org.junit.Assert.*;

import java.io.File;

public class ExtractDrlFromDecisionTableTest
{
    @Rule
    public MojoRule rule = new MojoRule()
    {
        @Override
        protected void before() throws Throwable 
        {
        }

        @Override
        protected void after()
        {
        }
    };

    /**
     * @throws Exception if any
     */
//    @Test
    public void testSomething()
            throws Exception
    {
        File pom = new File( "target/test-classes/project-to-test/" );
        assertNotNull( pom );
        assertTrue( pom.exists() );

        ExtractDrlFromDecisionTable extractDrlFromDecisionTable = (ExtractDrlFromDecisionTable) rule.lookupConfiguredMojo(pom, "touch");
        assertNotNull(extractDrlFromDecisionTable);
        extractDrlFromDecisionTable.execute();

        File outputDirectory = ( File ) rule.getVariableValueFromObject(extractDrlFromDecisionTable, "outputDirectory");
        assertNotNull( outputDirectory );
        assertTrue( outputDirectory.exists() );

        File touch = new File( outputDirectory, "touch.txt" );
        assertTrue( touch.exists() );

    }

    /** Do not need the MojoRule. */
    @WithoutMojo
//    @Test
    public void testSomethingWhichDoesNotNeedTheMojoAndProbablyShouldBeExtractedIntoANewClassOfItsOwn()
    {
        assertTrue( true );
    }

}

