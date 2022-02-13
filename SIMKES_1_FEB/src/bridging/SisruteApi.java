package bridging;

import fungsi.koneksiDB;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Properties;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.security.crypto.codec.Base64;
//import org.apache.commons.codec.binary.Base64;
import java.util.Base64;
import org.springframework.web.client.RestTemplate;

public class SisruteApi {        
    private static final Properties prop = new Properties();
    private String Key,Consid,pass;
    public SisruteApi(){
        try {                       
            pass = koneksiDB.PassSisrute();
            Consid = koneksiDB.ConsIdSisrute();
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            byte[] hashInBytes = md.digest(pass.getBytes(StandardCharsets.UTF_8));
//
            String idpass = Consid + pass;
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            byte[] hashInBytes = md.digest(idpass.getBytes(StandardCharsets.UTF_8));
//            StringBuilder sb = new StringBuilder();
//            for (byte b : hashInBytes) {
//                sb.append(String.format("%02x", b));
//            }
//            Key=sb.toString();    
        String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(idpass); 
        Key = sha256hex;
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
    }
    public String getHmac() {        
        long GetUTCdatetimeAsString = GetUTCdatetimeAsString();        
        String salt = Consid +"&"+String.valueOf(GetUTCdatetimeAsString);
	String generateHmacSHA256Signature = null;
	try {
	    generateHmacSHA256Signature = generateHmacSHA256Signature(salt,Key);
	} catch (GeneralSecurityException e) {
	    // TODO Auto-generated catch block
            System.out.println("Error Signature : "+e);
	    e.printStackTrace();
	}
	return generateHmacSHA256Signature;
    }

    public String generateHmacSHA256Signature(String data, String key)throws GeneralSecurityException {
	byte[] hmacData = null;

	try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"),"HmacSHA256");
	    Mac mac = Mac.getInstance("HmacSHA256");
	    mac.init(secretKey);
	    hmacData = mac.doFinal(data.getBytes("UTF-8"));
//	    return new String(Base64.encode(hmacData), "UTF-8");
            String signature = Base64.getEncoder().encodeToString(hmacData);
            return signature;
//            return Base64.encodeBase64String(mac.doFinal(data.getBytes("UTF-8")));
	} catch (UnsupportedEncodingException e) {
            System.out.println("Error Generate HMac: e");
	    throw new GeneralSecurityException(e);
	}
    }
        
    public long GetUTCdatetimeAsString(){    
        long millis = System.currentTimeMillis();   
        return millis/1000;
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

}
