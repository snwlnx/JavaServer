package resource;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 16.04.13
 * Time: 19:25
 * To change this template use File | Settings | File Templates.
 */
public class ObjectFactory {

    public static Object readObject(String path){

        try{
            FileInputStream   fileOut = new FileInputStream(path);
            ObjectInputStream in      = new ObjectInputStream(fileOut);
            Object object  = in.readObject();
            in.close();
            return object;

        }catch (Exception ex){

        }
        return null;
    }

}
