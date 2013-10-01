package resource;
import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 13.04.13
 * Time: 23:59
 * To change this template use File | Settings | File Templates.
 */
public class VFS {

    public boolean   isExist(String path){
        return new File(path).exists();
    }
    public boolean   isDirectory(String path){
        return new File(path).isDirectory();

    }
    public String    getAbsolutePath(String file){
        return new File(file).getAbsolutePath();
    }
    public byte[]    getBytes(String path){
        StringBuilder stringBuff = new StringBuilder();
        String str = "";
        try{
            File file                    = new File(path);
            BufferedReader buffInStream  = new BufferedReader(new FileReader(file) );

            while ((str = buffInStream.readLine()) != null){
                stringBuff.append(str+"\r\n");
            }

        }catch (FileNotFoundException ex){
            ex.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return stringBuff.toString().getBytes();
    }



    public String    getUTF8Text(String path){
        StringBuilder stringBuilder = new StringBuilder(path);
        try{
            File file = new File(path);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file), "UTF8"));

            String str;

            while ((str = in.readLine()) != null) {
                stringBuilder.append(str);
            }

            in.close();
        }
        catch (UnsupportedEncodingException e)
        {
            System.out.println(e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return stringBuilder.toString();
    }


    public Iterator  getIterator(String startDir){
        return new Iterator(startDir);

    }

}
