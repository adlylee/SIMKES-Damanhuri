package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.sekuel;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
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
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class BridgingWA {

    private static final Properties prop = new Properties();
    private sekuel Sequel = new sekuel();
    private String Key, pass, url, token = "", requestJson, urlApi = "", sender = "", number = "", message = "", reurn = "";
    private final String USER_AGENT = "Mozilla/5.0",moduleserver = "wagateway",fieldserver = "server",fieldtoken="token",fieldphone = "phonenumber";
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;

    public RestTemplate getRest() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("SSL");
        javax.net.ssl.TrustManager[] trustManagers = {
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                }

                public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                }
            }
        };
        sslContext.init(null, trustManagers, new SecureRandom());
        SSLSocketFactory sslFactory = new SSLSocketFactory(sslContext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Scheme scheme = new Scheme("https", 443, sslFactory);
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(2000);
        factory.getHttpClient().getConnectionManager().getSchemeRegistry().register(scheme);
        return new RestTemplate(factory);
    }

    public void sendWa(String no_rkm_medis, String nama, String tanggal, String poli) {
        try {
            message = "Assalamualaikum " + nama + ". \nUlun RSHD SIAP WA Bot dari Rumah Sakit H. Damanhuri Barabai .\n"
                    + " Handak behabar dan meingatakan pian, kalau nya BESOK tanggal " + tanggal + " pian ada JADWAL PERIKSA ke " + poli + " di Rumah Sakit H. Damanhuri Barabai . Pian bisa datang LANGSUNG ke ANJUNGAN PASIEN MANDIRI (APM) ."
                    + " Pastikan RUJUKAN BPJS pian masih berlaku. Jika sudah habis, maka mintalah rujukan kembali untuk berobat ke Rumah Sakit.Terima kasih \n \nWassalamualaikum\n"
                    + " Daftar Online Tanpa Antri via Apam Barabai Klik Disini >>> https://play.google.com/store/apps/details?id=com.rshdbarabai.apam&hl=in&gl=US";
            number = Sequel.cariIsi("SELECT no_tlp FROM pasien WHERE no_rkm_medis = " + no_rkm_medis);
            urlApi = Sequel.cariIsi("SELECT value FROM mlite_settings WHERE module='"+moduleserver+"' AND field = '"+fieldserver+"'") + "/wagateway/kirimpesan";
            sender = Sequel.cariIsi("SELECT value FROM mlite_settings WHERE module='"+moduleserver+"' AND field = '"+fieldphone+"'");
            token = Sequel.cariIsi("SELECT value FROM mlite_settings WHERE module='"+moduleserver+"' AND field = '"+fieldtoken+"'");
            requestJson = "type=text&sender=" + sender + "&number=" + number + "&message=" + message+"&api_key="+token;
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
            System.out.println(url);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(null, "Koneksi ke server WA terputus...!");
            }
        }
    }

    public void sendWaBatal(String no_rkm_medis, String nama, String tanggal, String polidari, String polike) {
        try {
            message = "Assalamualaikum " + nama + ". \nUlun RSHD SIAP WA Bot dari Rumah Sakit H. Damanhuri Barabai .\n"
                    + " Handak mahabar akan kalaunya JADWAL PERIKSA ke " + polidari + " sebelumnya dibatalkan, karena Dokter berhalangan hadir. "
                    + " Dan dipindah jadi tanggal " + tanggal + " ke " + polike + ". \n"
                    + " Terkait dengan habar di atas, kami ucapkan permohonan maaf dan terima kasih atas kepercayaan pian berobat di RSUD H. Damanhuri. \nTerima kasih \n \nWassalamualaikum\n"
                    + " Daftar Online Tanpa Antri via Apam Barabai Klik Disini >>> https://play.google.com/store/apps/details?id=com.rshdbarabai.apam&hl=in&gl=US";
            number = Sequel.cariIsi("SELECT no_tlp FROM pasien WHERE no_rkm_medis = " + no_rkm_medis);
            urlApi = Sequel.cariIsi("SELECT value FROM mlite_settings WHERE module='"+moduleserver+"' AND field = '"+fieldserver+"'") + "/wagateway/kirimpesan";
            sender = Sequel.cariIsi("SELECT value FROM mlite_settings WHERE module='"+moduleserver+"' AND field = '"+fieldphone+"'");
            token = Sequel.cariIsi("SELECT value FROM mlite_settings WHERE module='"+moduleserver+"' AND field = '"+fieldtoken+"'");

            if (number.equals("")) {
                System.out.println("Nomor telepon kosong !!!");
            } else {
                    
                requestJson = 
                        "{"
                    + "\"number\":\"" + number + "\","
                    + "\"body\":\"" + message + "\","
                    + "}";
//                "type=text&sender=" + sender + "&number=" + number + "&message=" + message+"&api_key="+token;
                System.out.println("PostField : " + requestJson);
                System.out.println("                  \n                 ");
                System.out.println("Mengirim Pesan ............");

                URL obj = new URL(urlApi);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", USER_AGENT);
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestProperty("Authorization", "Bearer 9928cda0-7397-4bb8-8fa0-6da8025e6871");
                con.setRequestProperty("charset", "utf-8");
//                "Accept: application/json",
//                "Authorization: Bearer 9928cda0-7397-4bb8-8fa0-6da8025e6871",


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
                    System.out.println("\n");
                    System.out.println("Pesan Berhasil Dikirim . ");
                } else {
                    System.out.println("POST request not worked");
                }
                System.out.println("----------------- -------------------");
            }
        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
            System.out.println(url);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(null, "Koneksi ke server WA terputus...!");
            }
        }
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
            message = "Pemberitahuan Permintaan Form MPP.\n\n"
                    + "Pasien atas nama " + nama + " di ruang " + kamar + " pada tanggal " + tanggal + "";
            number = Sequel.cariIsi("SELECT no_telp FROM petugas WHERE nip='198011042005012011'");
            urlApi = Sequel.cariIsi("SELECT value FROM mlite_settings WHERE module='"+moduleserver+"' AND field = '"+fieldserver+"'") + "/wagateway/kirimpesan";
            sender = Sequel.cariIsi("SELECT value FROM mlite_settings WHERE module='"+moduleserver+"' AND field = '"+fieldphone+"'");
            token = Sequel.cariIsi("SELECT value FROM mlite_settings WHERE module='"+moduleserver+"' AND field = '"+fieldtoken+"'");
            requestJson = "type=text&sender=" + sender + "&number=" + number + "&message=" + message+"&api_key="+token;
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
            System.out.println(url);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(null, "Koneksi ke server WA terputus...!");
            }
        }
    }
    public void sendwaMPP(String nama, String tanggal, String kamar) {
        try {
            message = "Pemberitahuan Permintaan Kerohanian.\n\n"
                    + "Pasien atas nama " + nama + " di ruang " + kamar + " pada tanggal " + tanggal + "";
            number = Sequel.cariIsi("SELECT no_telp FROM petugas WHERE nip='198011042005012011'");
            urlApi = Sequel.cariIsi("SELECT value FROM mlite_settings WHERE module='"+moduleserver+"' AND field = '"+fieldserver+"'") + "/wagateway/kirimpesan";
            sender = Sequel.cariIsi("SELECT value FROM mlite_settings WHERE module='"+moduleserver+"' AND field = '"+fieldphone+"'");
            token = Sequel.cariIsi("SELECT value FROM mlite_settings WHERE module='"+moduleserver+"' AND field = '"+fieldtoken+"'");
            requestJson = "type=text&sender=" + sender + "&number=" + number + "&message=" + message+"&api_key="+token;
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
            System.out.println(url);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(null, "Koneksi ke server WA terputus...!");
            }
        }
    }
}
