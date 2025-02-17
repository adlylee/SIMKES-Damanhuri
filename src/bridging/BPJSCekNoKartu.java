/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.koneksiDB;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.swing.JOptionPane;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

/**
 *
 * @author khanzasoft
 */
public class BPJSCekNoKartu {
    public String cobnmAsuransi="",cobnoAsuransi="",cobtglTAT="",cobtglTMT="",
            hakKelasketerangan="",hakKelaskode="",informasidinsos="",informasinoSKTM="",
            informasiprolanisPRB="",jenisPesertaketerangan="",jenisPesertakode="",
            mrnoMR="",mrnoTelepon="",nama="",nik="",noKartu="",pisa="",
            provUmumkdProvider="",provUmumnmProvider="",sex="",statusPesertaketerangan="",
            statusPesertakode="",tglCetakKartu="",tglLahir="",tglTAT="",
            tglTMT="",umurumurSaatPelayanan="",umurumurSekarang="",informasi="";
    private final Properties prop = new Properties();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    private BPJSApi api=new BPJSApi();
    private String URL="";
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private HttpHeaders headers;
    private JsonNode res1;
        
    public BPJSCekNoKartu(){
        super();
        try {
            URL = koneksiDB.UrlBpjs()+"/Peserta/nokartu/";	
        } catch (Exception e) {
            System.out.println("E : "+e);
        }
    }
    
    public void tampil(String nokartu) {
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("X-Cons-ID",koneksiDB.ConsIdBpjs());
	    headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));
	    headers.add("X-Signature",api.getHmac());
            headers.add("user_key", koneksiDB.UserKeyBpjs());
	    requestEntity = new HttpEntity(headers);
            root = mapper.readTree(api.getRest().exchange(URL+nokartu+"/tglSEP/"+dateFormat.format(date), HttpMethod.GET, requestEntity, String.class).getBody());
            nameNode = root.path("metaData");
            System.out.println("code : "+nameNode.path("code").asText());
            System.out.println("message : "+nameNode.path("message").asText());
            informasi=nameNode.path("message").asText();
            if(nameNode.path("code").asText().equals("200")){
                if(koneksiDB.UrlBpjs().contains("apijkn")){
                    res1 = root.path("response");
                    String res = api.decrypt(res1.asText());
                    String lz = api.lzDecrypt(res);
                    response = mapper.readTree(lz);
                }else{
                    response = root.path("response");
                }
//                response = root.path("response");
                nik=response.path("peserta").path("nik").asText();
                nama=response.path("peserta").path("nama").asText();
                cobnmAsuransi=response.path("peserta").path("cob").path("nmAsuransi").asText();
                cobnoAsuransi=response.path("peserta").path("cob").path("noAsuransi").asText();
                cobtglTAT=response.path("peserta").path("cob").path("tglTAT").asText();
                cobtglTMT=response.path("peserta").path("cob").path("tglTMT").asText();
                hakKelasketerangan=response.path("peserta").path("hakKelas").path("keterangan").asText();
                hakKelaskode=response.path("peserta").path("hakKelas").path("kode").asText();
                informasidinsos=response.path("peserta").path("informasi").path("dinsos").asText();
                informasinoSKTM=response.path("peserta").path("informasi").path("noSKTM").asText();
                informasiprolanisPRB=response.path("peserta").path("informasi").path("prolanisPRB").asText();
                jenisPesertaketerangan=response.path("peserta").path("jenisPeserta").path("keterangan").asText();
                jenisPesertakode=response.path("peserta").path("jenisPeserta").path("kode").asText();
                mrnoMR=response.path("peserta").path("mr").path("noMR").asText();
                mrnoTelepon=response.path("peserta").path("mr").path("noTelepon").asText();
                nama=response.path("peserta").path("nama").asText();
                nik=response.path("peserta").path("nik").asText();
                noKartu=response.path("peserta").path("noKartu").asText();
                pisa=response.path("peserta").path("pisa").asText();
                provUmumkdProvider=response.path("peserta").path("provUmum").path("kdProvider").asText();
                provUmumnmProvider=response.path("peserta").path("provUmum").path("nmProvider").asText();
                sex=response.path("peserta").path("sex").asText();
                statusPesertaketerangan=response.path("peserta").path("statusPeserta").path("keterangan").asText();
                statusPesertakode=response.path("peserta").path("statusPeserta").path("kode").asText();
                tglCetakKartu=response.path("peserta").path("tglCetakKartu").asText();
                tglLahir=response.path("peserta").path("tglLahir").asText();
                tglTAT=response.path("peserta").path("tglTAT").asText();
                tglTMT=response.path("peserta").path("tglTMT").asText();
                umurumurSaatPelayanan=response.path("peserta").path("umur").path("umurSaatPelayanan").asText();
                umurumurSekarang=response.path("peserta").path("umur").path("umurSekarang").asText();
            }else {
                JOptionPane.showMessageDialog(null,nameNode.path("message").asText());                
            }   
        } catch (Exception ex) {
            System.out.println("Notifikasi Peserta : "+ex);
            if(ex.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server BPJS terputus...!");
            }
        }
    }
    
}
