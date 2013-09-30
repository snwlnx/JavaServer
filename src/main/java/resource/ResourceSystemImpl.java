package resource;

import base.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;

public class ResourceSystemImpl implements ResourceSystem {

    private static  final String resourcePath  = "src/main/resources";
    private VFS  vfs             =  new VFS();
    private HashSet<base.Resource> gameResource          = new HashSet<base.Resource>();

    private Map<Class<?>, Resource> resources = new HashMap<Class<?>, Resource>();

    public HashSet<base.Resource> getGameResource(){
        if(!gameResource.isEmpty()) {
            return this.gameResource;
        }
        return null;
    }

    public ResourceSystemImpl(){
        try {

            SAXParserFactory  factory    =  SAXParserFactory.newInstance();
            SAXParser         saxParser  =  factory.newSAXParser();
            SaxEmptyHandler   handler    =  new SaxEmptyHandler();
            Iterator          iterator   =  vfs.getIterator(resourcePath);


            String filePath;


            while ((filePath = iterator.getFile())!= null){
                System.out.println(filePath);
                saxParser.parse( filePath,handler );
                Object obj = handler.getObject();
                if(obj instanceof Resource){
                    gameResource.add((Resource)obj);
                    resources.put(obj.getClass(), (Resource)obj);
                }

                iterator.moveToNextFile();
            }
            String file;
            int i = 0;
            i++;
            System.out.println(i);

        }  catch (Exception ex){
            ex.printStackTrace();

        }
    }

    public Resource getResource(Class<?> className) {
        return resources.get(className);
    }

    public void globalInit() {
        FightResource fResource = (FightResource) getResource(FightResource.class);
        if(fResource != null){
            Player.setHealthMax(fResource.healthMax);
            Player.setSize(fResource.playerWidth, fResource.playerHeight);

            Fireball.setDamage(fResource.fireballDamage);
        }


    }
}
