/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.koneksiDB;
import java.awt.HeadlessException;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JOptionPane;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author adlyhidayat
 */
public class BpjsApiBackend {

    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private BPJSApi api = new BPJSApi();
    private String link = "", URL = "";
    private final Properties prop = new Properties();

    public BpjsApiBackend() {
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            link = prop.getProperty("URLAPIBPJS");
        } catch (IOException e) {
            System.out.println("E : " + e);
        }
    }

    public JsonNode refDiagnosa() {
        try {
            URL = link + "/referensi/diagnosaprb";
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("X-Cons-ID", koneksiDB.ConsIdBpjs());
            headers.add("X-Timestamp", String.valueOf(api.GetUTCdatetimeAsString()));
            headers.add("X-Signature", api.getHmac());
            headers.add("user_key", koneksiDB.UserKeyBpjs());
            requestEntity = new HttpEntity(headers);
            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            nameNode = root.path("metaData");
            if (nameNode.path("code").asText().equals("200")) {

                JsonNode res1 = root.path("response");
                String res = api.decrypt(res1.asText());
                String lz = api.lzDecrypt(res);
                response = mapper.readTree(lz);

            } else {
                JOptionPane.showMessageDialog(null, nameNode.path("message").asText());
            }
        } catch (HeadlessException | IOException | InvalidAlgorithmParameterException | InvalidKeyException | KeyManagementException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException | RestClientException ex) {
            System.out.println("Notifikasi Peserta : " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(null, "Koneksi ke server BPJS terputus...!");
            }
        }
        return response;
    }

    public JsonNode refObat(String namaobat) {
        try {
            URL = link + "/referensi/obatprb/" + namaobat;
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("X-Cons-ID", koneksiDB.ConsIdBpjs());
            headers.add("X-Timestamp", String.valueOf(api.GetUTCdatetimeAsString()));
            headers.add("X-Signature", api.getHmac());
            headers.add("user_key", koneksiDB.UserKeyBpjs());
            requestEntity = new HttpEntity(headers);
            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            nameNode = root.path("metaData");
            if (nameNode.path("code").asText().equals("200")) {

                JsonNode res1 = root.path("response");
                String res = api.decrypt(res1.asText());
                String lz = api.lzDecrypt(res);
                response = mapper.readTree(lz);

            } else {
                JOptionPane.showMessageDialog(null, nameNode.path("message").asText());
            }
        } catch (HeadlessException | IOException | InvalidAlgorithmParameterException | InvalidKeyException | KeyManagementException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException | RestClientException ex) {
            System.out.println("Notifikasi Peserta : " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(null, "Koneksi ke server BPJS terputus...!");
            }
        }
        return response;
    }
}
