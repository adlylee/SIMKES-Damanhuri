package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.EnkripsiAES;
import fungsi.sekuel;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.JOptionPane;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class BridgingWA {

    private static final Properties prop = new Properties();
    private sekuel Sequel = new sekuel();
    private String Key, pass, url, token = Sequel.cariIsi("SELECT value FROM mlite_settings WHERE module='wagateway' AND field = 'token'"), requestJson, urlApi = Sequel.cariIsi("SELECT value FROM mlite_settings WHERE module='wagateway' AND field = 'server'"), sender = "", number = "", message = "", reurn = "";
    private final String USER_AGENT = "Mozilla/5.0",moduleserver = "wagateway",fieldserver = "server",fieldtoken="token",fieldphone = "phonenumber";
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private SSLContext sslContext;
    private SSLSocketFactory sslFactory;
    private SecretKeySpec secretKey;
    private Scheme scheme;
    private HttpComponentsClientHttpRequestFactory factory;

    public RestTemplate getRest() throws NoSuchAlgorithmException, KeyManagementException {
        sslContext = SSLContext.getInstance("SSL");
        TrustManager[] trustManagers= {
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {return null;}
                public void checkServerTrusted(X509Certificate[] arg0, String arg1)throws CertificateException {}
                public void checkClientTrusted(X509Certificate[] arg0, String arg1)throws CertificateException {}
            }
        };
        sslContext.init(null,trustManagers , new SecureRandom());
        sslFactory=new SSLSocketFactory(sslContext,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        scheme=new Scheme("https",443,sslFactory);
        factory=new HttpComponentsClientHttpRequestFactory();
        factory.getHttpClient().getConnectionManager().getSchemeRegistry().register(scheme);
        return new RestTemplate(factory);
    }

    public void sendWa(String no_rkm_medis, String nama, String tanggal, String poli) {
        try {
            message = "Assalamualaikum " + nama + ". \n\nUlun RSHD SIAP WA Bot dari Rumah Sakit H. Damanhuri Barabai.\n\n"
                    + "Handak behabar dan meingatakan pian, kalau nya BESOK tanggal " + tanggal + " pian ada JADWAL PERIKSA ke " + poli + " di Rumah Sakit H. Damanhuri Barabai.\n\nJam pendaftaran & cetak SEP bisa dilayani mulai dari jam: \nPOLI PAGI : 07.30 - 11.00 wita. \n\nPOLI SORE : 14.00 - 18.00 wita. \nDokternya ada antara jam 16:00:00 sampai 17:00:00. "
                    + "\n\nPian bisa datang LANGSUNG ke ANJUNGAN PASIEN MANDIRI (APM).\n\n"
                    + "Pastikan RUJUKAN BPJS pian masih berlaku. Jika sudah habis, maka mintalah rujukan kembali untuk berobat ke Rumah Sakit. Terima kasih \nWassalamualaikum\n\n"
                    + "Daftar Online Tanpa Antri via Apam Barabai Klik Disini >>> https://play.google.com/store/apps/details?id=com.rshdbarabai.apam&hl=in&gl=US \n\nDaftar Online Tanpa Antri via JKN Mobile Klik Disini >>> https://play.google.com/store/apps/details?id=app.bpjs.mobile \n\n*Jam pelayanan sewaktu waktu dapat berubah*";
            number = Sequel.cariIsi("SELECT no_tlp FROM pasien WHERE no_rkm_medis = " + no_rkm_medis);
            urlApi = Sequel.cariIsi("SELECT value FROM mlite_settings WHERE module='" + moduleserver + "' AND field = '" + fieldserver + "'") + "/wagateway/kirimpesan";
            sender = Sequel.cariIsi("SELECT value FROM mlite_settings WHERE module='" + moduleserver + "' AND field = '" + fieldphone + "'");
            token = Sequel.cariIsi("SELECT value FROM mlite_settings WHERE module='" + moduleserver + "' AND field = '" + fieldtoken + "'");
            String encodedMessage = URLEncoder.encode(message, "UTF-8");
            requestJson = "type=text&sender=" + sender + "&number=" + number + "&message=" + encodedMessage + "&api_key=" + token;
//            System.out.println("PostField : " + requestJson);
            System.out.println("PostField : "+no_rkm_medis+ " "+nama +" &api_key=" + token );
            if (number.equals("")) {
                System.out.println("Nomor telepon kosong !!!");
            } else {
                URL obj = new URL(urlApi);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", USER_AGENT);
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                con.setRequestProperty("charset", "utf-8");

                // For POST only - START
                con.setDoOutput(true);
                OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
                os.write(requestJson);
                os.flush();
                os.close();
                // For POST only - END

                int responseCode = con.getResponseCode();
                System.out.println("POST Response Code :: " + responseCode);

                if (responseCode == HttpURLConnection.HTTP_OK) { //success
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    // print result
                    System.out.println(response.toString());
                } else {
                    System.out.println("POST request not worked");
                }
            }
        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
            System.out.println(urlApi);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(null, "Koneksi ke server WA terputus...!");
            }
        }
    }

    public void sendWaBatal(String no_rkm_medis, String nama, String tanggal, String polidari, String polike) {
        try {
            message = "Assalamualaikum " + nama + ". \n\nUlun RSHD SIAP WA Bot dari Rumah Sakit H. Damanhuri Barabai.\n\nHandak mahabar akan kalaunya JADWAL PERIKSA ke " + polidari + " sebelumnya dibatalkan, karena Dokter berhalangan hadir. Dan dipindah jadi tanggal " + tanggal + " ke " + polike + ". \n\nTerkait dengan habar di atas, kami ucapkan permohonan maaf dan terima kasih atas kepercayaan pian berobat di RSUD H. Damanhuri. \nTerima kasih \nWassalamualaikum \n\nDaftar Online Tanpa Antri via Apam Barabai Klik Disini >>> https://play.google.com/store/apps/details?id=com.rshdbarabai.apam&hl=in&gl=US \n\nDaftar Online Tanpa Antri via JKN Mobile Klik Disini >>> https://play.google.com/store/apps/details?id=app.bpjs.mobile \n\n*Jam pelayanan sewaktu waktu dapat berubah*";
            number = Sequel.cariIsi("SELECT no_tlp FROM pasien WHERE no_rkm_medis = " + no_rkm_medis);
            urlApi = Sequel.cariIsi("SELECT value FROM mlite_settings WHERE module='" + moduleserver + "' AND field = '" + fieldserver + "'") + "/wagateway/kirimpesan";
            sender = Sequel.cariIsi("SELECT value FROM mlite_settings WHERE module='" + moduleserver + "' AND field = '" + fieldphone + "'");
            token = Sequel.cariIsi("SELECT value FROM mlite_settings WHERE module='" + moduleserver + "' AND field = '" + fieldtoken + "'");
            requestJson = "type=text&sender=" + sender + "&number=" + number + "&message=" + message + "&api_key=" + token;
            String encodedMessage = URLEncoder.encode(message, "UTF-8");
            requestJson = "type=text&sender=" + sender + "&number=" + number + "&message=" + encodedMessage + "&api_key=" + token;
//            System.out.println("PostField : " + requestJson);
            System.out.println("PostField : "+no_rkm_medis+ " "+nama +" &api_key=" + token );
            if (number.equals("")) {
                System.out.println("Nomor telepon kosong !!!");
            } else {
                URL obj = new URL(urlApi);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", USER_AGENT);
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                con.setRequestProperty("charset", "utf-8");

                // For POST only - START
                con.setDoOutput(true);
                OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
                os.write(requestJson);
                os.flush();
                os.close();
                // For POST only - END

                int responseCode = con.getResponseCode();
                System.out.println("POST Response Code :: " + responseCode);

                if (responseCode == HttpURLConnection.HTTP_OK) { //success
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    // print result
                    System.out.println(response.toString());
                } else {
                    System.out.println("POST request not worked");
                }
            }
        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
            System.out.println(urlApi);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(null, "Koneksi ke server WA terputus...!");
            }
        }
//        try {            
//            reurn = "";
//            message = "Assalamualaikum " + nama + ". \nUlun RSHD SIAP WA Bot dari Rumah Sakit H. Damanhuri Barabai .\nHandak mahabar akan kalaunya JADWAL PERIKSA ke " + polidari + " sebelumnya dibatalkan, karena Dokter berhalangan hadir. Dan dipindah jadi tanggal " + tanggal + " ke " + polike + ". \nTerkait dengan habar di atas, kami ucapkan permohonan maaf dan terima kasih atas kepercayaan pian berobat di RSUD H. Damanhuri. \nTerima kasih \nWassalamualaikum \nDaftar Online Tanpa Antri via Apam Barabai Klik Disini >>> https://play.google.com/store/apps/details?id=com.rshdbarabai.apam&hl=in&gl=US \nDaftar Online Tanpa Antri via JKN Mobile Klik Disini >>> https://play.google.com/store/apps/details?id=app.bpjs.mobile";
//            number = Sequel.cariIsi("SELECT no_tlp FROM pasien WHERE no_rkm_medis = " + no_rkm_medis);
//            if (number.equals("")) {
//                System.out.println("Nomor telepon kosong !!!");
//            } else {
//                number = number.replaceFirst("0", "62");
//                Map<String,String> mss=new HashMap<>();
//                mss.put("number", number);
//                mss.put("body", message);
//
//                JSONObject j=new JSONObject(mss);
//                reurn = waGw(j.toString(),nama);
//                System.out.println(reurn);
//                }
//        } catch (IOException ex) {
//            System.out.println("Notifikasi : " + ex);
//            System.out.println(url);
//            if (ex.toString().contains("UnknownHostException")) {
//                JOptionPane.showMessageDialog(null, "Koneksi ke server WA terputus...!");
//            }
//        }
    }

    public void sendwaUTD(String nama, String no_telp, String tanggal) {
        try {
            message = "Assalamualaikum wr.wb " + nama + ". \nKami dari Unit Transfusi Darah RSUD H.DAMANHURI BARABAI\n"
                    + "Mengingatkan bahwa Bapak/Ibu telah melakukan donor darah pada tanggal " + tanggal + " dan sudah dapat melakukan donor darah "
                    + "kembali karena waktu untuk donor darah sudah sampai. \nKami tunggu ya kedatangannya.\nTerima kasih. Wassalamualaikum";

            urlApi = Sequel.cariIsi("SELECT value FROM mlite_settings WHERE module='"+moduleserver+"' AND field = '"+fieldserver+"'") + "/wagateway/kirimpesan";
            sender = Sequel.cariIsi("SELECT value FROM mlite_settings WHERE module='"+moduleserver+"' AND field = '"+fieldphone+"'");
            token = Sequel.cariIsi("SELECT value FROM mlite_settings WHERE module='"+moduleserver+"' AND field = '"+fieldtoken+"'");
            requestJson = "type=text&sender=" + sender + "&number=" + no_telp + "&message=" + message+"&api_key="+token;
            System.out.println("PostField : " + requestJson);

            URL obj = new URL(urlApi);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("charset", "utf-8");

            // For POST only - START
            con.setDoOutput(true);
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
            os.write(requestJson);
            os.flush();
            os.close();
            // For POST only - END

            int responseCode = con.getResponseCode();
            System.out.println("POST Response Code :: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
 
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // print result
                System.out.println(response.toString());
            } else {
                System.out.println("POST request not worked");
            }
        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
            System.out.println(urlApi);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(null, "Koneksi ke server WA terputus...!");
            }
        }
    }

    public void sendwaKerohanian(String nama, String tanggal, String kamar) {
        try {
            message = "Pemberitahuan Permintaan Kerohanian.\n\n"
                    + "Pasien atas nama " + nama + " di ruang " + kamar + " pada tanggal " + tanggal + "";
            number = Sequel.cariIsi("SELECT no_telp FROM petugas WHERE nip='198011042005012011'");            
            urlApi = Sequel.cariIsi("SELECT value FROM mlite_settings WHERE module='" + moduleserver + "' AND field = '" + fieldserver + "'") + "/wagateway/kirimpesan";
            sender = Sequel.cariIsi("SELECT value FROM mlite_settings WHERE module='" + moduleserver + "' AND field = '" + fieldphone + "'");
            token = Sequel.cariIsi("SELECT value FROM mlite_settings WHERE module='" + moduleserver + "' AND field = '" + fieldtoken + "'");
            requestJson = "type=text&sender=" + sender + "&number=" + number + "&message=" + message + "&api_key=" + token;
            System.out.println("PostField : " + requestJson);
            if (number.equals("")) {
                System.out.println("Nomor telepon kosong !!!");
            } else {
                URL obj = new URL(urlApi);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", USER_AGENT);
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                con.setRequestProperty("charset", "utf-8");

                // For POST only - START
                con.setDoOutput(true);
                OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
                os.write(requestJson);
                os.flush();
                os.close();
                // For POST only - END

                int responseCode = con.getResponseCode();
                System.out.println("POST Response Code :: " + responseCode);

                if (responseCode == HttpURLConnection.HTTP_OK) { //success
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    // print result
                    System.out.println(response.toString());
                } else {
                    System.out.println("POST request not worked");
                }
            }
        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
            System.out.println(urlApi);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(null, "Koneksi ke server WA terputus...!");
            }
        }
    }

        public void sendwaMPP(String nama, String tanggal, String kamar) {
            try {
            message = "Pemberitahuan pasien perlu penanganan MPP lebih lanjut.\n\nPasien atas nama " + nama + " di ruang " + kamar + " pada tanggal " + tanggal + "";
            number = Sequel.cariIsi("SELECT no_telp FROM petugas WHERE nip='198011042005012011'");
            urlApi = Sequel.cariIsi("SELECT value FROM mlite_settings WHERE module='" + moduleserver + "' AND field = '" + fieldserver + "'") + "/wagateway/kirimpesan";
            sender = Sequel.cariIsi("SELECT value FROM mlite_settings WHERE module='" + moduleserver + "' AND field = '" + fieldphone + "'");
            token = Sequel.cariIsi("SELECT value FROM mlite_settings WHERE module='" + moduleserver + "' AND field = '" + fieldtoken + "'");
            requestJson = "type=text&sender=" + sender + "&number=" + number + "&message=" + message + "&api_key=" + token;            
            System.out.println("PostField : " + requestJson);
            if (number.equals("")) {
                System.out.println("Nomor telepon kosong !!!");
            } else {
                URL obj = new URL(urlApi);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", USER_AGENT);
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                con.setRequestProperty("charset", "utf-8");

                // For POST only - START
                con.setDoOutput(true);
                OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
                os.write(requestJson);
                os.flush();
                os.close();
                // For POST only - END

                int responseCode = con.getResponseCode();
                System.out.println("POST Response Code :: " + responseCode);

                if (responseCode == HttpURLConnection.HTTP_OK) { //success
                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    // print result
                    System.out.println(response.toString());
                } else {
                    System.out.println("POST request not worked");
                }
            }
        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
            System.out.println(urlApi);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(null, "Koneksi ke server WA terputus...!");
            }
        }
//        try {
//            reurn = "";
//            message = "Pemberitahuan pasien perlu penanganan MPP lebih lanjut.\n\nPasien atas nama " + nama + " di ruang " + kamar + " pada tanggal " + tanggal + "";
//            number = Sequel.cariIsi("SELECT no_telp FROM petugas WHERE nip='198011042005012011'");
//            if (number.equals("")) {
//                System.out.println("Nomor telepon kosong !!!");
//            } else {
//                number = number.replaceFirst("0", "62");
//                Map<String,String> mss=new HashMap<>();
//                mss.put("number", number);
//                mss.put("body", message);
//
//                JSONObject j=new JSONObject(mss);
//                reurn = waGw(j.toString(),nama);
//                System.out.println(reurn);
//            }
//        } catch (IOException ex) {
//            System.out.println("Notifikasi : " + ex);
//            System.out.println(url);
//            if (ex.toString().contains("UnknownHostException")) {
//                JOptionPane.showMessageDialog(null, "Koneksi ke server WA terputus...!");
//            }
//        }
    }
    
    public String waGw(String j,String nama) throws IOException{
        URL url = new URL(urlApi);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        //  CURLOPT_POST
        con.setRequestMethod("POST");

        // CURLOPT_FOLLOWLOCATION
        con.setInstanceFollowRedirects(true);

        String postData = j;
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Authorization", "Bearer "+token);

        con.setDoOutput(true);
        con.setDoInput(true);

        DataOutputStream output = new DataOutputStream(con.getOutputStream());
        output.writeBytes(postData);
        output.close();

        // "Post data send ... waiting for reply");
        int code = con.getResponseCode(); // 200 = HTTP_OK
        System.out.println("Response    (Code):" + code);
        System.out.println("Response (Message):" + con.getResponseMessage());
        System.out.println("Response (Nama):" + nama);

        // read the response
        DataInputStream input = new DataInputStream(con.getInputStream());
        int c;
        StringBuilder resultBuf = new StringBuilder();
        while ( (c = input.read()) != -1) {
            resultBuf.append((char) c);
        }
        input.close();
        return resultBuf.toString();
    }
}
