package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.koneksiDB;
import java.awt.HeadlessException;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Properties;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import javax.swing.JOptionPane;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class MyLimsApi {        
    private static final Properties prop = new Properties();
    private String Key,pass , url , token ,requestJson;
    private HttpHeaders headers ;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    
    public MyLimsApi(){
        pass = koneksiDB.PassSirs();
    }
    public String getHmac() {        
        try {                    
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashInBytes = md.digest(pass.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
            Key=sb.toString();            
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
	return Key;
    }

    
    public RestTemplate getRest() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("SSL");
        javax.net.ssl.TrustManager[] trustManagers= {
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {return null;}
                public void checkServerTrusted(X509Certificate[] arg0, String arg1)throws CertificateException {}
                public void checkClientTrusted(X509Certificate[] arg0, String arg1)throws CertificateException {}
            }
        };
        sslContext.init(null,trustManagers , new SecureRandom());
        SSLSocketFactory sslFactory=new SSLSocketFactory(sslContext,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Scheme scheme=new Scheme("https",443,sslFactory);
        HttpComponentsClientHttpRequestFactory factory=new HttpComponentsClientHttpRequestFactory();
        factory.getHttpClient().getConnectionManager().getSchemeRegistry().register(scheme);
        return new RestTemplate(factory);
    }
    
    public Object apiKu(){
        try {
            token = "NMjdBfX3WAHU44DMSZwKl8T5WEixAkRoKu97vUm4aPgNDWqDJbMzrcXS0UF1";
            headers = new HttpHeaders();
            headers.add("accept","application/json");
	    headers.add("Content-Type","application/json");
            headers.add("Authorization", "Bearer " + token);
            requestJson = "{"
                        + "\"method\":\"" + koneksiDB.IdSirs() + "\","
                        + "\"password\":\"" + koneksiDB.PassSirs()+ "\""
                        + "}";
            System.out.println("JSON : " + requestJson);
            requestEntity = new HttpEntity(requestJson, headers);
            url = koneksiDB.UrlSirs()+"/api/rslogin";	
            root = mapper.readTree(getRest().exchange(url, HttpMethod.POST, requestEntity, String.class).getBody());
            System.out.println(root);
            token = root.path("message").asText();
            nameNode = root.path("data");            
            if(root.path("status").asText().equals("true")){ 
                token = nameNode.path("access_token").asText();
            }else {
                JOptionPane.showMessageDialog(null,nameNode.path("message").asText());                
            }   
        } catch (HeadlessException | IOException | KeyManagementException | NoSuchAlgorithmException | RestClientException ex) {
            System.out.println("Notifikasi : "+ex);
            System.out.println(url);
            if(ex.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server Kemenkes terputus...!");
            }
        }
        return token;
    }

}
