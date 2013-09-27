package base;

import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 16.04.13
 * Time: 22:53
 * To change this template use File | Settings | File Templates.
 */
public interface ResourceSystem {
    public Resource getResource(Class<?> className);
    public void globalInit();
}
