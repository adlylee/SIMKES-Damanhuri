package simrskhanza;

import fungsi.koneksiDB;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
/**
 *
 * @author Thomas Otero H3R3T1C
 */
public class Update {
    private static String URL ;
    public static String version , value;
    private static final Properties prop = new Properties();
    
    public static String getLatestVersion() throws Exception
    {
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));

        } catch (Exception e) {
            System.out.println("Notif Setting : "+e);
        }
        URL= koneksiDB.UrlAutoUpdate()+"/changelog.txt";
        String data = getData(URL);
        version = prop.getProperty("VERSION");
        if (!"201".equals(data)) {
            value = data.substring(data.indexOf("[version]")+9,data.indexOf("[/version]"));
        } else {
            value = version;
        }
        return value;
    }
    public static String getWhatsNew() throws Exception
    {
        URL= koneksiDB.UrlAutoUpdate()+"/changelog.txt";
        String data = getData(URL);
        return data.substring(data.indexOf("[history]")+9,data.indexOf("[/history]"));
    }
    private static String getData(String address) throws IOException
    {
        StringBuffer buffer = new StringBuffer("");
        try{
            URL url = new URL(address);
        
            InputStream html = null;

            html = url.openStream();

            int c = 0;

            while(c != -1) {
                c = html.read();
                buffer.append((char)c);
            }
            
        } catch(MalformedURLException e){
            buffer.append("201");
        }
        return buffer.toString();
    }
}
