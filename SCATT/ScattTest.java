import org.junit.Test;
import java.io.File;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.After;

/**
 * ScattTest.java
 */

/**
 * Class to test Scatt class.
 *
 * @author Kara Beason
 * @author Cydney Caldwell
 * @author Michelle Melton
 * @version Spr 2017
 */
public class ScattTest
{
    private Scatt testScatt;
    private File testDir;

    /**
     * Set up before tests.
     */
    @Before
    public void setUp()
    {
        testScatt = new Scatt();
    }

    /**
     * Test Valid Folder.
     */
    @Test
    public void testValidFolder()
    {
        testDir = new File("submissions");
        assertTrue(FileUtils.readValidDirectory(testDir));
    }

    /**
     * Test Invalid Folder - Not Directory.
     */
    @Test
    public void testInvalidFolderNotDirectory()
    {
        testDir = new File("test.txt");
        assertFalse(FileUtils.readValidDirectory(testDir));
    }

    /**
     * Test Invalid Folder - Empty.
     */
    @Test
    public void testInvalidFolderEmpty()
    {
        testDir = new File("empty");
        assertFalse(FileUtils.readValidDirectory(testDir));
    }

    /**
     * Tear down after tests.
     */
    @After
    public void tearDown()
    {
        testDir.delete();
        testScatt = null;
    }
}
