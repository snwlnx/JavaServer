package resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: korolkov
 * Date: 10/1/13
 * Time: 10:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class IteratorTest extends Assert {

    private static  final String   resourcePath   = "src/main/resources";
    Iterator iterator;
    VFS vfs;

    @Before
    public void setUp() throws Exception {
        vfs             =  new VFS();
        iterator        =  vfs.getIterator(resourcePath);

    }
    @Test
    public void iteratorGetStartFile() {
        assertNotNull(iterator.getFile());
	    iterator.getFile();
//	    assertNull(iterator.getFile());
    }

    @Test
    public void iteratorTraverseAllFiles() {
        while (iterator.hasNext())
            assertNotNull(iterator.getFile());

    }

    @Test
    public void next(){
        assertNotNull(iterator.next());
    }
}
