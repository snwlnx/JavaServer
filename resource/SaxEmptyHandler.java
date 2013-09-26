package resource;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;



/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 16.04.13
 * Time: 20:00
 * To change this template use File | Settings | File Templates.
 */
public class SaxEmptyHandler extends DefaultHandler{

    private static  String CLASSNAME = "class";
    private boolean        inElement = false;
    private String         element   = null;
    private Object         object    = null;

    public Object getObject (){
        return object;
    }

    public void startElement(String uri,String localName, String qName, Attributes attributes){
        if(qName != CLASSNAME){
            element = qName;
        }else {
            String className = attributes.getValue(0);
            System.out.println("Class name:"+className);
            object = ReflectionHelper.createInstance(className);
        }
    }

    public void endElement(String uri, String localName, String qName){
        element = null;
    }

    public void characters(char[] ch, int start, int length){
        if(element != null){
            String value = new String(ch,start,length);
            System.out.println(element + " = " + value);
            ReflectionHelper.setFieldValue(object,element,value);
        }
    }
}
