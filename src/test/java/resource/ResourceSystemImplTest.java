package resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: korolkov
 * Date: 10/1/13
 * Time: 8:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResourceSystemImplTest extends Assert {

    ResourceSystemImpl resourceSystem;

    @Before
    public void setUp() throws Exception {
        resourceSystem  =  new ResourceSystemImpl();
    }
    @Test
    public void testLoadFightResource() {
        HashSet resourceSet = resourceSystem.getGameResource();
        assertFalse(resourceSet.isEmpty());
        java.util.Iterator resourceIterator =  resourceSet.iterator();
        Object resource = null;

        while (resourceIterator.hasNext()) {
            resource = resourceIterator.next();
            if (resource instanceof FightResource) break;

        }
        assertNotNull(resource);
        assertTrue(resource instanceof FightResource);
        FightResource fightResource = (FightResource) resource;
        assertNotEquals(fightResource.healthMax,     0);
        assertNotEquals(fightResource.fireballDamage,0);
        assertNotEquals(fightResource.playerHeight,  0);
        assertNotEquals(fightResource.playerWidth,   0);
    }

    @Test
    public void testLoadMapResource() {
        HashSet resourceSet = resourceSystem.getGameResource();
        assertFalse(resourceSet.isEmpty());
        java.util.Iterator resourceIterator =  resourceSet.iterator();
        Object resource = null;

        while (resourceIterator.hasNext()) {
            resource = resourceIterator.next();
            if (resource instanceof MapResource) break;

        }
        assertNotNull(resource);
        assertTrue(resource instanceof MapResource);
        MapResource mapResource = (MapResource) resource;
        assertNotEquals(mapResource.height,     0);
        assertNotEquals(mapResource.width,      0);
        assertNotEquals(mapResource.name,      "");
    }

}
