import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import java.io.File;
import java.util.Arrays;
import java.nio.file.Files;
import java.io.IOException;

/**
 * SubmissionTest.java
 */

/**
 * Class to test submission class.
 *
 * @author Kara Beason
 * @author Cydney Caldwell
 * @author Michelle Melton
 * @version Spr 2017
 */
public class SubmissionTest
{
    private static File directory;
    private static File expectedDir;
    private static File[] sb2s;
    private static Submission[] submissions;
    
    /**
     * Set up for tests.
     */
    @BeforeClass
    public static void setUp()
    {
        // Set up expected files.
        directory = new File("submissions");
        sb2s = directory.listFiles();
        Arrays.sort(sb2s);
        
        // Create actual Submission files.
        submissions = new Submission[sb2s.length];
        for (int i = 0; i < submissions.length; i++)
        {
            submissions[i] = new Submission(sb2s[i]);
        }
    }
    
    /**
     * Test constructor of Submission object.
     */
    @Test
    public void testSubmissionConstructor()
    {
        assertNotNull("should not be null", submissions);
    }

    /**
     * Test getName.
     */
    @Test
    public void testGetName()
    {
        // Set up expected filenames.
        String[] expected = new String[sb2s.length];
        for (int i = 0; i < expected.length; i++)
        {
            expected[i] = sb2s[i].getName();
        }
        
        // Set up actual filenames.
        String[] actual = new String[submissions.length];
        for (int i = 0; i < actual.length; i++)
        {
            actual[i] = submissions[i].getName();
        }
        
        assertArrayEquals("should be same", expected, actual);
    }

    /**
     * Test valid .sb2s.
     */
    @Test
    public void testValid()
    {
        // Check each expected file validity.
        boolean[] expected = new boolean[sb2s.length];
        for (int i = 0; i < expected.length; i++)
        {
            String sb2Name = sb2s[i].getName();
            int len = sb2Name.length();
            String ext = sb2Name.substring(len - 4);
            expected[i] = ext.equals(".sb2") && sb2s[i].isFile();
        }
        
        // Check each actaul file validity.
        boolean[] actual = new boolean[submissions.length];
        for (int i = 0; i < actual.length; i++)
        {
            actual[i] = submissions[i].isValid();
        }
        
        assertTrue("should be same", Arrays.equals(expected, actual));
    }

    /**
     * Test conversion to .zip.
     *
     * @throws IOException 
     */
    @Test
    public void testZip() throws IOException
    {
        // Copy and convert expected files to .zip.
        expectedDir = new File("expected");
        if (!expectedDir.exists())
        {
            expectedDir.mkdir();
        }
        for (int i = 0; i < sb2s.length; i++)
        {
            String sb2Name = sb2s[i].getName();
            int len = sb2Name.length();
            if (sb2Name.substring(len - 4).equals(".sb2") && sb2s[i].isFile())
            {
                String zipName = sb2Name.substring(0, len - 4) + ".zip";
                File copy = new File(expectedDir, zipName);
                if (!copy.exists())
                {
                    Files.copy(sb2s[i].toPath(), copy.toPath());
                }
            }
        }
        
        // Get expected filenames.
        File[] expectedZips = expectedDir.listFiles();
        Arrays.sort(expectedZips);
        String[] expected = new String[expectedZips.length];
        for (int i = 0; i < expected.length; i++)
        {
            expected[i] = expectedZips[i].getName();
        }
        
        // Get actual Submission filenames.
        File actualDir = new File("zips");
        File[] actualZips = actualDir.listFiles();
        Arrays.sort(actualZips);
        String[] actual = new String[actualZips.length];
        for (int i = 0; i < actual.length; i++)
        {
            actual[i] = actualZips[i].getName();
        }

        assertArrayEquals("should be same", expected, actual);
    }

    /**
     * Test unzip of files.
     * 
     * @throws IOException 
     */
    @Test
    public void testUnzip() throws IOException
    {
        // Get list of expected dir names.
        expectedDir = new File("expected");
        File[] expectedZips = expectedDir.listFiles(); 
        Arrays.sort(expectedZips);
        String[] expected = new String[expectedZips.length]; 
        for (int i = 0; i < expected.length; i++)
        {
            String zipName = expectedZips[i].getName();
            int len = zipName.length();
            expected[i] = zipName.substring(0, len - 4);
        }

        // Get list of new zip dirs.
        File zipsDir = new File("unzips");
        File[] zipDirs = zipsDir.listFiles();
        Arrays.sort(zipDirs);
        String[] actual = new String[zipDirs.length];
        for (int i = 0; i < actual.length; i++)
        {
            // Only track directories with more than one file.
            // Tests more than original zip file exists (unzip success).
            if (zipDirs[i].isDirectory() 
                && zipDirs[i].listFiles().length > 1)
            {
                actual[i] = zipDirs[i].getName();
            }
        }

        assertArrayEquals("should be same", expected, actual);
    }

    /**
     * Test parsing valid JSON file.
     */
    @Test
    public void testParseValidJSON()
    {
        assertNotNull("Should not be null", 
            (Object) submissions[1].getJSONObject());
    }

    /**
     * Test parsing invalid JSON file.
     */
    public void testParseInvalidJSON()
    {
        assertNull(submissions[1].getJSONObject());
    } 

    /**
     * Test getSpriteCount method, valid.
     */
    @Test
    public void testGetSpriteCountValid()
    {
        int expected = 2;
        int actual = submissions[1].getSpriteCount();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getSpriteCount method, valid - empty.
     */
    @Test
    public void testGetSpriteCountEmpty()
    {
        int expected = 0;
        int actual = submissions[2].getSpriteCount();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getScriptCount method, valid.
     */
    @Test
    public void testGetScriptCountValid()
    {
        int expected = 8;
        int actual = submissions[1].getScriptCount();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getScriptCount method, valid - empty.
     */
    @Test
    public void testGetScriptCountEmpty()
    {
        int expected = 0;
        int actual = submissions[2].getScriptCount();
        assertEquals("should be equal", expected, actual);
    }

    /**
     * Test getScriptCountForStage, valid.
     */
    @Test
    public void testGetScriptCountForStageValid()
    {
        int expected = 6;
        int actual = submissions[1].getScriptCountForStage();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getScriptCountForStage, valid - stage has no scripts.
     */
    @Test
    public void testGetScriptCountForStageEmpty()
    {
        int expected = 0;
        int actual = submissions[0].getScriptCountForStage();
        assertEquals("should be equal", expected, actual);
    }

    /**
     * Test getVariableCountForStage, valid.
     */
    @Test
    public void testGetVariableCountForStageValid()
    {
        int expected = 3;
        int actual = submissions[1].getVariableCountForStage();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getVariableCountForStage, valid - stage has no variables.
     */
    @Test
    public void testGetVariableCountForStageEmpty()
    {
        int expected = 0;
        int actual = submissions[0].getVariableCountForStage();
        assertEquals("should be equal", expected, actual);
    }

    /**
     * Test getListCountForStage, valid.
     */
    @Test
    public void testGetListCountForStageValid()
    {
        int expected = 1;
        int actual = submissions[1].getListCountForStage();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getListCountForStage, valid - stage has no lists.
     */
    @Test
    public void testGetListCountForStageEmpty()
    {
        int expected = 0;
        int actual = submissions[0].getListCountForStage();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getScriptCommentCountForStage, valid.
     */
    @Test
    public void testGetScriptCommentCountForStageValid()
    {
        int expected = 2;
        int actual = submissions[1].getScriptCommentCountForStage();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getScriptCommentCountForStage, valid - stage has no script comments.
     */
    @Test
    public void testGetScriptCommentCountForStageEmpty()
    {
        int expected = 0;
        int actual = submissions[0].getScriptCommentCountForStage();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getSoundCountForStage, valid.
     */
    @Test
    public void testGetSoundCountForStageValid()
    {
        int expected = 1;
        int actual = submissions[1].getSoundCountForStage();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getSoundCountForStage, valid - stage has no sounds.
     */
    @Test
    public void testGetSoundCountForStageEmpty()
    {
        int expected = 0;
        int actual = submissions[2].getSoundCountForStage();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getCostumeCountForStage, valid.
     */
    @Test
    public void testGetCostumeCountForStageValid()
    {
        int expected = 2;
        int actual = submissions[1].getCostumeCountForStage();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getControlBlocksForStage method.
     */
    @Test
    public void testGetControlBlocksForStage()
    {
        int expected = 1;
        int actual = submissions[1].getControlBlocksForStage();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getControlBlocksForStage method - empty.
     */
    @Test
    public void testGetControlBlocksForStageEmpty()
    {
        int expected = 0;
        int actual = submissions[2].getControlBlocksForStage();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getDataBlocksForStage method.
     */
    @Test
    public void testGetDataBlocksForStage()
    {
        int expected = 2;
        int actual = submissions[1].getDataBlocksForStage();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getDataBlocksForStage method - empty.
     */
    @Test
    public void testGetDataBlocksForStageEmpty()
    {
        int expected = 0;
        int actual = submissions[2].getDataBlocksForStage();
        assertEquals("should be equal", expected, actual);
    }

    /**
     * Test getEventsBlocksForStage method.
     */
    @Test
    public void testGetEventsBlocksForStage()
    {
        int expected = 1;
        int actual = submissions[1].getEventsBlocksForStage();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getEventsBlocksForStage method - empty.
     */
    @Test
    public void testGetEventsBlocksForStageEmpty()
    {
        int expected = 0;
        int actual = submissions[2].getEventsBlocksForStage();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getLooksBlocksForStage method.
     */
    @Test
    public void testGetLooksBlocksForStage()
    {
        int expected = 1;
        int actual = submissions[1].getLooksBlocksForStage();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getLooksBlocksForStage method - empty.
     */
    @Test
    public void testGetLooksBlocksForStageEmpty()
    {
        int expected = 0;
        int actual = submissions[2].getLooksBlocksForStage();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getMoreBlocksBlocksForStage method.
     */
    @Test
    public void testGetMoreBlocksBlocksForStage()
    {
        int expected = 2;
        int actual = submissions[1].getMoreBlocksBlocksForStage();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getMoreBlocksBlocksForStage method - empty.
     */
    @Test
    public void testGetMoreBlocksBlocksForStageEmpty()
    {
        int expected = 0;
        int actual = submissions[2].getMoreBlocksBlocksForStage();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getMotionBlocksForStage method - empty.
     */
    @Test
    public void testGetMotionBlocksForStageEmpty()
    {
        int expected = 0;
        int actual = submissions[1].getMotionBlocksForStage();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getOperatorsBlocksForStage method.
     */
    @Test
    public void testGetOperatorsBlocksForStage()
    {
        int expected = 1;
        int actual = submissions[1].getOperatorsBlocksForStage();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getOperatorsBlocksForStage method - empty.
     */
    @Test
    public void testGetOperatorsBlocksForStageEmpty()
    {
        int expected = 0;
        int actual = submissions[2].getOperatorsBlocksForStage();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getPenBlocksForStage method.
     */
    @Test
    public void testGetPenBlocksForStage()
    {
        int expected = 1;
        int actual = submissions[1].getPenBlocksForStage();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getPenBlocksForStage method - empty.
     */
    @Test
    public void testGetPenBlocksForStageEmpty()
    {
        int expected = 0;
        int actual = submissions[2].getPenBlocksForStage();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getSensingBlocksForStage method.
     */
    @Test
    public void testGetSensingBlocksForStage()
    {
        int expected = 1;
        int actual = submissions[1].getSensingBlocksForStage();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getSensingBlocksForStage method - empty.
     */
    @Test
    public void testGetSensingBlocksForStageEmpty()
    {
        int expected = 0;
        int actual = submissions[2].getSensingBlocksForStage();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getSoundBlocksForStage method.
     */
    @Test
    public void testGetSoundBlocksForStage()
    {
        int expected = 2;
        int actual = submissions[1].getSoundBlocksForStage();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getSoundBlocksForStage method - empty.
     */
    @Test
    public void testGetSoundBlocksForStageEmpty()
    {
        int expected = 0;
        int actual = submissions[2].getSoundBlocksForStage();
        assertEquals("should be equal", expected, actual);
    }

    /**
     * Test getVariableCountForProgram method.
     */
    @Test
    public void testGetVariableCountForProgram()
    {
        int expected = 5;
        int actual = submissions[1].getVariableCountForProgram();
        assertEquals("should be equal", expected, actual);
    }

    /**
     * Test getVariableCountForProgram method - empty.
     */
    @Test
    public void testGetVariableCountForProgramEmpty()
    {
        int expected = 0;
        int actual = submissions[2].getVariableCountForProgram();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getListCountForProgram method.
     */
    @Test
    public void testGetListCountForProgram()
    {
        int expected = 2;
        int actual = submissions[1].getListCountForProgram();
        assertEquals("should be equal", expected, actual);
    }

    /**
     * Test getListCountForProgram method - empty.
     */
    @Test
    public void testGetListCountForProgramEmpty()
    {
        int expected = 0;
        int actual = submissions[2].getListCountForProgram();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getScriptCommentCountForProgram method.
     */
    @Test
    public void testGetScriptCommentCountForProgram()
    {
        int expected = 4;
        int actual = submissions[1].getScriptCommentCountForProgram();
        assertEquals("should be equal", expected, actual);
    }

    /**
     * Test getScriptCommentCountForProgram method - empty.
     */
    @Test
    public void testGetScriptCommentCountForProgramEmpty()
    {
        int expected = 0;
        int actual = submissions[2].getScriptCommentCountForProgram();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getSoundCountForProgram method.
     */
    @Test
    public void testGetSoundCountForProgram()
    {
        int expected = 3;
        int actual = submissions[1].getSoundCountForProgram();
        assertEquals("should be equal", expected, actual);
    }

    /**
     * Test getSoundCountForProgram method - empty.
     */
    @Test
    public void testGetSoundCountForProgramEmpty()
    {
        int expected = 0;
        int actual = submissions[2].getSoundCountForProgram();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getCostumeCountForProgram method.
     */
    @Test
    public void testGetCostumeCountForProgram()
    {
        int expected = 6;
        int actual = submissions[1].getCostumeCountForProgram();
        assertEquals("should be equal", expected, actual);
    }

    /**
     * Test getControlBlocksForProgram method.
     */
    @Test
    public void testGetControlBlocksForProgram()
    {
        int expected = 6;
        int actual = submissions[1].getControlBlocksForProgram();
        assertEquals("should be equal", expected, actual);
    }

    /**
     * Test getControlBlocksForProgram method - empty.
     */
    @Test
    public void testGetControlBlocksForProgramEmpty()
    {
        int expected = 0;
        int actual = submissions[2].getControlBlocksForProgram();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getDataBlocksForProgram method.
     */
    @Test
    public void testGetDataBlocksForProgram()
    {
        int expected = 9;
        int actual = submissions[1].getDataBlocksForProgram();
        assertEquals("should be equal", expected, actual);
    }

    /**
     * Test getDataBlocksForProgram method - empty.
     */
    @Test
    public void testGetDataBlocksForProgramEmpty()
    {
        int expected = 0;
        int actual = submissions[2].getDataBlocksForProgram();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getEventsBlocksForProgram method.
     */
    @Test
    public void testGetEventsBlocksForProgram()
    {
        int expected = 5;
        int actual = submissions[1].getEventsBlocksForProgram();
        assertEquals("should be equal", expected, actual);
    }

    /**
     * Test getEventsBlocksForProgram method - empty.
     */
    @Test
    public void testGetEventsBlocksForProgramEmpty()
    {
        int expected = 0;
        int actual = submissions[2].getEventsBlocksForProgram();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getLooksBlocksForProgram method.
     */
    @Test
    public void testGetLooksBlocksForProgram()
    {
        int expected = 12;
        int actual = submissions[1].getLooksBlocksForProgram();
        assertEquals("should be equal", expected, actual);
    }

    /**
     * Test getLooksBlocksForProgram method - empty.
     */
    @Test
    public void testGetLooksBlocksForProgramEmpty()
    {
        int expected = 0;
        int actual = submissions[2].getLooksBlocksForProgram();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getMoreBlocksBlocksForProgram method.
     */
    @Test
    public void testGetMoreBlocksBlocksForProgram()
    {
        int expected = 5;
        int actual = submissions[1].getMoreBlocksBlocksForProgram();
        assertEquals("should be equal", expected, actual);
    }

    /**
     * Test getMoreBlocksBlocksForProgram method - empty.
     */
    @Test
    public void testGetMoreBlocksBlocksForProgramEmpty()
    {
        int expected = 0;
        int actual = submissions[2].getMoreBlocksBlocksForProgram();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getMotionBlocksForProgram method.
     */
    @Test
    public void testGetMotionBlocksForProgram()
    {
        int expected = 6;
        int actual = submissions[1].getMotionBlocksForProgram();
        assertEquals("should be equal", expected, actual);
    }

    /**
     * Test getMotionBlocksForProgram method - empty.
     */
    @Test
    public void testGetMotionBlocksForProgramEmpty()
    {
        int expected = 0;
        int actual = submissions[2].getMotionBlocksForProgram();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getOperatorsBlocksForProgram method.
     */
    @Test
    public void testGetOperatorsBlocksForProgram()
    {
        int expected = 3;
        int actual = submissions[1].getOperatorsBlocksForProgram();
        assertEquals("should be equal", expected, actual);
    }

    /**
     * Test getOperatorsBlocksForProgram method - empty.
     */
    @Test
    public void testGetOperatorsBlocksForProgramEmpty()
    {
        int expected = 0;
        int actual = submissions[2].getOperatorsBlocksForProgram();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getPenBlocksForProgram method.
     */
    @Test
    public void testGetPenBlocksForProgram()
    {
        int expected = 3;
        int actual = submissions[1].getPenBlocksForProgram();
        assertEquals("should be equal", expected, actual);
    }

    /**
     * Test getPenBlocksForProgram method - empty.
     */
    @Test
    public void testGetPenBlocksForProgramEmpty()
    {
        int expected = 0;
        int actual = submissions[2].getPenBlocksForProgram();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getSensingBlocksForProgram method.
     */
    @Test
    public void testGetSensingBlocksForProgram()
    {
        int expected = 5;
        int actual = submissions[1].getSensingBlocksForProgram();
        assertEquals("should be equal", expected, actual);
    }

    /**
     * Test getSensingBlocksForProgram method - empty.
     */
    @Test
    public void testGetSensingBlocksForProgramEmpty()
    {
        int expected = 0;
        int actual = submissions[2].getSensingBlocksForProgram();
        assertEquals("should be equal", expected, actual);
    }
    
    /**
     * Test getSoundBlocksForProgram method.
     */
    @Test
    public void testGetSoundBlocksForProgram()
    {
        int expected = 4;
        int actual = submissions[1].getSoundBlocksForProgram();
        assertEquals("should be equal", expected, actual);
    }

    /**
     * Test getSoundBlocksForProgram method - empty.
     */
    @Test
    public void testGetSoundBlocksForProgramEmpty()
    {
        int expected = 0;
        int actual = submissions[2].getSoundBlocksForProgram();
        assertEquals("should be equal", expected, actual);
    }

    /**
     * Test populateGlobalVariables method - valid.
     */
    @Test
    public void testPopulateGlobalVariables()
    {
        String[] expected = {"name", "weather", "sprite1AllVariables"};
        String[] actual = submissions[1].getGlobalVariables();
        assertArrayEquals("should be equal", expected, actual);
    }

    /**
     * Test populateGlobalVariables - empty.
     */
    @Test
    public void testPopulateGlobalVariablesEmpty()
    {
        String[] expected = new String[0];
        String[] actual = submissions[2].getGlobalVariables();
        assertArrayEquals("should be equal", expected, actual);
    }

    /**
     * Test populateGlobalLists method - valid.
     */
    @Test
    public void testPopulateGlobalLists()
    {
        String[] expected = {"listStage"};
        String[] actual = submissions[1].getGlobalLists();
        assertArrayEquals("should be equal", expected, actual);
    }

    /**
     * Test populateGlobalLists - empty.
     */
    @Test
    public void testPopulateGlobalListsEmpty()
    {
        String[] expected = new String[0];
        String[] actual = submissions[2].getGlobalLists();
        assertArrayEquals("should be same", expected, actual);
    }

    /**
     * Test getStageVariableUsageCount method - valid.
     */
    @Test
    public void testGetStageVariableUsageCount()
    {
        int expected = 1;
        int actual = 
            submissions[1].getStageVariableUsageCount("sprite1AllVariables");
        assertEquals("should be equal", expected, actual);
    }

    /**
     * Test getStageVariableUsageCount - empty.
     */
    @Test
    public void testGetStageVariableUsageCountEmpty()
    {
        int expected = 0;
        int actual =
            submissions[2].getStageVariableUsageCount("sprite1AllVariables");
        assertEquals("should be equal", expected, actual);
    }

    /**
     * Test getStageListUsageCount method - valid.
     */
    @Test
    public void testGetStageListUsageCount()
    {
        int expected = 1;
        int actual =
            submissions[1].getStageListUsageCount("listStage");
        assertEquals("should be equal", expected, actual);
    }

    /**
     * Test getStageListUsageCount - empty.
     */
    @Test
    public void testGetStageListUsageEmpty()
    {
        int expected = 0;
        int actual =
            submissions[2].getStageListUsageCount("listStage");
        assertEquals("should be same", expected, actual);
    }

    /**
     * Test get total variable usage count method - valid.
     */
    @Test
    public void testGetProgramVariableUsageCount()
    {
        int expected = 4;
        int actual =
            submissions[1].getProgramVariableUsageCount("name");
        assertEquals("should be same", expected, actual);
    }

    /**
     * Test get total variable usage count - empty.
     */
    @Test
    public void testGetProgramVariableUsageCountEmpty()
    {
        int expected = 0;
        int actual =
            submissions[2].getProgramVariableUsageCount("name");
        assertEquals("should be same", expected, actual);
    }

    /**
     * Test get total list usage count method - valid.
     */
    @Test
    public void testGetProgramListUsageCount()
    {
        int expected = 2;
        int actual = 
            submissions[1].getProgramListUsageCount("listStage");
        assertEquals("should be same", expected, actual);
    }

    /**
     * Test get total list usage count - empty.
     */
    @Test
    public void testGetProgramListUsageCountEmpty()
    {
        int expected = 0;
        int actual =
            submissions[2].getProgramListUsageCount("listStage");
        assertEquals("shoudl be same", expected, actual);
    }

    /**
     * Tear down after tests.
     */
    @AfterClass
    public static void tearDown()
    {
        // Set arrays to null.
        sb2s = null;
        submissions = null;
    }
}
