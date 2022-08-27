/*
  Dilarang keras menggandakan/mengcopy/menyebarkan/membajak/mendecompile
  Software ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */
package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URI;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import simrskhanza.DlgKamarInap;
import simrskhanza.DlgResumePerawatan;

/**
 *
 * @author perpustakaan
 */
public final class BPJSDataSEP extends javax.swing.JDialog {

    private DefaultTableModel tabMode, tabMode1, tabMode2, tabMode3, tabMode4,tabMode5, tabModeDiagnosa, tabModeProsedur, tabModeInternal;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0, pilihan = 1, reply = 0;
    private final Properties prop = new Properties();
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
    private BPJSApi api = new BPJSApi();
    private BPJSCekReferensiFaskes faskes = new BPJSCekReferensiFaskes(null, false);
    private BPJSCekReferensiPenyakit penyakit = new BPJSCekReferensiPenyakit(null, false);
    private BPJSCekReferensiPoli poli = new BPJSCekReferensiPoli(null, false);
    private BPJSCekNoKartu cekViaBPJSKartu = new BPJSCekNoKartu();
    private BPJSCekReferensiDokterDPJP dokter = new BPJSCekReferensiDokterDPJP(null, false);
    private BPJSSuratKontrol skdp = new BPJSSuratKontrol(null, false);
    private BPJSSPRI skdp2 = new BPJSSPRI(null, false);
    private BPJSCekReferensiPropinsi propinsi = new BPJSCekReferensiPropinsi(null, false);
    private BPJSCekReferensiKabupaten kabupaten = new BPJSCekReferensiKabupaten(null, false);
    private BPJSCekReferensiKecamatan kecamatan = new BPJSCekReferensiKecamatan(null, false);
    private String prb = "", no_peserta = "", link = "", requestJson, URL = "", jkel = "", duplikat = "",
            user = "", penjamin = "", jasaraharja = "", BPJS = "", Taspen = "", Asabri = "", kddokter = "", dpjplayananbpjs = "",
            tglkkl = "0000-00-00", antrian = "", klsRawat = "", dpjlayan = "", sep2tambah = "", penjaminan = "", kdppkrujukan = "", nmppkrujukan = "";
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private JsonNode res1;

    /**
     * Creates new form DlgRujuk
     *
     * @param parent
     * @param modal
     */
    public BPJSDataSEP(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8, 1);

        WindowUpdatePulang.setSize(630, 80);
        WindowRujukan.setSize(810, 192);
        WindowCariSEP.setSize(410, 115);
        WindowRujukKhusus.setSize(600, 360);
        WindowCariNoRujuk.setSize(874, 250);

        tabMode = new DefaultTableModel(null, new Object[]{
            "No.SEP", "No.Rawat", "No.RM", "Nama Pasien", "Tgl.SEP", "Tgl.Rujukan",
            "No.Rujukan", "Kode PPK Rujukan", "Nama PPK Rujukan", "Kode PPK Pelayanan",
            "Nama PPK Pelayanan", "Jenis Layanan", "Catatan", "Kode Diagnosa",
            "Nama Diagnosa", "Kode Poli", "Nama Poli", "Kelas Rawat", "Laka Lantas",
            "User Input", "Tgl.Lahir", "Peserta", "J.K", "No.Kartu", "Tanggal Pulang",
            "Asal Rujukan", "Eksekutif", "COB", "Penjamin", "No.Telp", "Katarak",
            "Tanggal KKL", "Keterangan KKL", "Suplesi", "No.SEP Suplesi", "Kd Prop",
            "Propinsi", "Kd Kab", "Kabupaten", "Kd Kec", "Kecamatan", "No.SKDP",
            "Kd DPJP", "DPJP"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 44; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(125);
            } else if (i == 1) {
                column.setPreferredWidth(105);
            } else if (i == 2) {
                column.setPreferredWidth(80);
            } else if (i == 3) {
                column.setPreferredWidth(200);
            } else if (i == 4) {
                column.setPreferredWidth(70);
            } else if (i == 5) {
                column.setPreferredWidth(70);
            } else if (i == 6) {
                column.setPreferredWidth(110);
            } else if (i == 7) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 8) {
                column.setPreferredWidth(150);
            } else if (i == 9) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 10) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 11) {
                column.setPreferredWidth(90);
            } else if (i == 12) {
                column.setPreferredWidth(150);
            } else if (i == 13) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 14) {
                column.setPreferredWidth(180);
            } else if (i == 15) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 16) {
                column.setPreferredWidth(125);
            } else if (i == 17) {
                column.setPreferredWidth(80);
            } else if (i == 18) {
                column.setPreferredWidth(80);
            } else if (i == 19) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 20) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 21) {
                column.setPreferredWidth(80);
            } else if (i == 22) {
                column.setPreferredWidth(25);
            } else if (i == 23) {
                column.setPreferredWidth(90);
            } else if (i == 24) {
                column.setPreferredWidth(120);
            } else if (i == 25) {
                column.setPreferredWidth(80);
            } else if (i == 26) {
                column.setPreferredWidth(60);
            } else if (i == 27) {
                column.setPreferredWidth(60);
            } else if (i == 28) {
                column.setPreferredWidth(130);
            } else if (i == 29) {
                column.setPreferredWidth(85);
            } else if (i == 30) {
                column.setPreferredWidth(55);
            } else if (i == 31) {
                column.setPreferredWidth(70);
            } else if (i == 32) {
                column.setPreferredWidth(150);
            } else if (i == 33) {
                column.setPreferredWidth(55);
            } else if (i == 34) {
                column.setPreferredWidth(120);
            } else if (i == 35) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 36) {
                column.setPreferredWidth(135);
            } else if (i == 37) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 38) {
                column.setPreferredWidth(135);
            } else if (i == 39) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 40) {
                column.setPreferredWidth(135);
            } else if (i == 41) {
                column.setPreferredWidth(60);
            } else if (i == 42) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 43) {
                column.setPreferredWidth(135);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeInternal = new DefaultTableModel(null, new Object[]{
            "No.SEP", "No.Rawat", "No.RM", "Nama Pasien", "Tgl.SEP", "Tgl.Rujukan",
            "No.Rujukan", "Kode PPK Rujukan", "Nama PPK Rujukan", "Kode PPK Pelayanan",
            "Nama PPK Pelayanan", "Jenis Layanan", "Catatan", "Kode Diagnosa",
            "Nama Diagnosa", "Kode Poli", "Nama Poli", "Kelas Rawat", "Laka Lantas",
            "User Input", "Tgl.Lahir", "Peserta", "J.K", "No.Kartu", "Tanggal Pulang",
            "Asal Rujukan", "Eksekutif", "COB", "No.Telp", "Katarak",
            "Tanggal KKL", "Keterangan KKL", "Suplesi", "No.SEP Suplesi", "Kd Prop",
            "Propinsi", "Kd Kab", "Kabupaten", "Kd Kec", "Kecamatan", "No.SKDP",
            "Kd DPJP", "DPJP"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbObat1.setModel(tabModeInternal);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat1.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbObat1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 43; i++) {
            TableColumn column = tbObat1.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(125);
            } else if (i == 1) {
                column.setPreferredWidth(105);
            } else if (i == 2) {
                column.setPreferredWidth(80);
            } else if (i == 3) {
                column.setPreferredWidth(200);
            } else if (i == 4) {
                column.setPreferredWidth(70);
            } else if (i == 5) {
                column.setPreferredWidth(70);
            } else if (i == 6) {
                column.setPreferredWidth(110);
            } else if (i == 7) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 8) {
                column.setPreferredWidth(150);
            } else if (i == 9) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 10) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 11) {
                column.setPreferredWidth(90);
            } else if (i == 12) {
                column.setPreferredWidth(150);
            } else if (i == 13) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 14) {
                column.setPreferredWidth(180);
            } else if (i == 15) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 16) {
                column.setPreferredWidth(125);
            } else if (i == 17) {
                column.setPreferredWidth(80);
            } else if (i == 18) {
                column.setPreferredWidth(80);
            } else if (i == 19) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 20) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 21) {
                column.setPreferredWidth(80);
            } else if (i == 22) {
                column.setPreferredWidth(25);
            } else if (i == 23) {
                column.setPreferredWidth(90);
            } else if (i == 24) {
                column.setPreferredWidth(120);
            } else if (i == 25) {
                column.setPreferredWidth(80);
            } else if (i == 26) {
                column.setPreferredWidth(60);
            } else if (i == 27) {
                column.setPreferredWidth(60);
            } else if (i == 28) {
                column.setPreferredWidth(130);
            } else if (i == 29) {
                column.setPreferredWidth(85);
            } else if (i == 30) {
                column.setPreferredWidth(55);
            } else if (i == 31) {
                column.setPreferredWidth(70);
            } else if (i == 32) {
                column.setPreferredWidth(150);
            } else if (i == 33) {
                column.setPreferredWidth(55);
            } else if (i == 34) {
                column.setPreferredWidth(120);
            } else if (i == 35) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 36) {
                column.setPreferredWidth(135);
            } else if (i == 37) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 38) {
                column.setPreferredWidth(135);
            } else if (i == 39) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 40) {
                column.setPreferredWidth(135);
            } else if (i == 41) {
                column.setPreferredWidth(60);
            } else if (i == 42) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbObat1.setDefaultRenderer(Object.class, new WarnaTable());

        //tabel riwayat rujukan faskes 1
        tabMode1 = new DefaultTableModel(null, new String[]{"No.", "No. Rujukan", "Tgl. Rujukan",
            "kode_ppk", "Nama PPK Rujukan", "Poli Rujukan", "kode_icd", "Diagnosa", "Keluhan", "kode_poli",
            "kode_ply", "Jns. Pelayn."}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbFaskes1.setModel(tabMode1);
        tbFaskes1.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbFaskes1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 12; i++) {
            TableColumn column = tbFaskes1.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(35);
            } else if (i == 1) {
                column.setPreferredWidth(125);
            } else if (i == 2) {
                column.setPreferredWidth(90);
            } else if (i == 3) {//sembunyi
                //column.setPreferredWidth(200);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 4) {
                column.setPreferredWidth(200);
            } else if (i == 5) {
                column.setPreferredWidth(200);
            } else if (i == 6) {//sembunyi
                //column.setPreferredWidth(200);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 7) {
                column.setPreferredWidth(200);
            } else if (i == 8) {
                column.setPreferredWidth(200);
            } else if (i == 9) {//sembunyi
                //column.setPreferredWidth(200);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 10) {//sembunyi
                //column.setPreferredWidth(200);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 11) {
                column.setPreferredWidth(150);
            }
        }
        tbFaskes1.setDefaultRenderer(Object.class, new WarnaTable());

        //tabel riwayat rujukan faskes 2
        tabMode2 = new DefaultTableModel(null, new String[]{"No.", "No. Rujukan", "Tgl. Rujukan",
            "kode_ppk", "Nama PPK Rujukan", "Poli Rujukan", "kode_icd", "Diagnosa", "Keluhan", "kode_poli",
            "kode_ply", "Jns. Pelayn."}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbFaskes2.setModel(tabMode2);
        tbFaskes2.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbFaskes2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 12; i++) {
            TableColumn column = tbFaskes2.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(35);
            } else if (i == 1) {
                column.setPreferredWidth(125);
            } else if (i == 2) {
                column.setPreferredWidth(90);
            } else if (i == 3) {//sembunyi
                //column.setPreferredWidth(200);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 4) {
                column.setPreferredWidth(200);
            } else if (i == 5) {
                column.setPreferredWidth(200);
            } else if (i == 6) {//sembunyi
                //column.setPreferredWidth(200);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 7) {
                column.setPreferredWidth(200);
            } else if (i == 8) {
                column.setPreferredWidth(200);
            } else if (i == 9) {//sembunyi
                //column.setPreferredWidth(200);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 10) {//sembunyi
                //column.setPreferredWidth(200);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 11) {
                column.setPreferredWidth(150);
            }
        }
        tbFaskes2.setDefaultRenderer(Object.class, new WarnaTable());

        //tabel riwayat rujukan faskes 1 banyak
        tabMode3 = new DefaultTableModel(null, new String[]{"No.", "No. Rujukan", "Tgl. Rujukan",
            "kode_ppk", "Nama PPK Rujukan", "Poli Rujukan", "kode_icd", "Diagnosa", "Keluhan", "kode_poli",
            "kode_ply", "Jns. Pelayn."}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbFaskes3.setModel(tabMode3);
        tbFaskes3.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbFaskes3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 12; i++) {
            TableColumn column = tbFaskes3.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(35);
            } else if (i == 1) {
                column.setPreferredWidth(125);
            } else if (i == 2) {
                column.setPreferredWidth(90);
            } else if (i == 3) {//sembunyi
                //column.setPreferredWidth(200);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 4) {
                column.setPreferredWidth(200);
            } else if (i == 5) {
                column.setPreferredWidth(200);
            } else if (i == 6) {//sembunyi
                //column.setPreferredWidth(200);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 7) {
                column.setPreferredWidth(200);
            } else if (i == 8) {
                column.setPreferredWidth(200);
            } else if (i == 9) {//sembunyi
                //column.setPreferredWidth(200);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 10) {//sembunyi
                //column.setPreferredWidth(200);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 11) {
                column.setPreferredWidth(150);
            }
        }
        tbFaskes3.setDefaultRenderer(Object.class, new WarnaTable());

        //tabel riwayat rujukan faskes 2 banyak
        tabMode4 = new DefaultTableModel(null, new String[]{"No.", "No. Rujukan", "Tgl. Rujukan",
            "kode_ppk", "Nama PPK Rujukan", "Poli Rujukan", "kode_icd", "Diagnosa", "Keluhan", "kode_poli",
            "kode_ply", "Jns. Pelayn."}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbFaskes4.setModel(tabMode4);
        tbFaskes4.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbFaskes4.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 12; i++) {
            TableColumn column = tbFaskes4.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(35);
            } else if (i == 1) {
                column.setPreferredWidth(125);
            } else if (i == 2) {
                column.setPreferredWidth(90);
            } else if (i == 3) {//sembunyi
                //column.setPreferredWidth(200);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 4) {
                column.setPreferredWidth(200);
            } else if (i == 5) {
                column.setPreferredWidth(200);
            } else if (i == 6) {//sembunyi
                //column.setPreferredWidth(200);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 7) {
                column.setPreferredWidth(200);
            } else if (i == 8) {
                column.setPreferredWidth(200);
            } else if (i == 9) {//sembunyi
                //column.setPreferredWidth(200);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 10) {//sembunyi
                //column.setPreferredWidth(200);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 11) {
                column.setPreferredWidth(150);
            }
        }
        tbFaskes4.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode5 = new DefaultTableModel(null, new String[]{"No.", "Id. Rujuk", "No. Rujukan",
            "No Kepesertaan", "Nama Peserta", "Diagnosa", "Tgl Awal Rujukan", "Tgl Awal Rujukan"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbFaskes5.setModel(tabMode5);
        tbFaskes5.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbFaskes5.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 8; i++) {
            TableColumn column = tbFaskes5.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(35);
            } else if (i == 1) {
                column.setPreferredWidth(50);
            } else if (i == 2) {
                column.setPreferredWidth(130);
            } else if (i == 3) {//sembunyi
                column.setPreferredWidth(90);
//                column.setMinWidth(0);
//                column.setMaxWidth(0);
            } else if (i == 4) {
                column.setPreferredWidth(150);
            } else if (i == 5) {
                column.setPreferredWidth(100);
            } else if (i == 6) {//sembunyi
                column.setPreferredWidth(100);
//                column.setMinWidth(0);
//                column.setMaxWidth(0);
            } else if (i == 7) {
                column.setPreferredWidth(100);
            }
        }
        tbFaskes5.setDefaultRenderer(Object.class, new WarnaTable());

        Object[] row2 = {"P", "S", "Kode Diagnosa", "Nama Diagnosa"};
        tabModeDiagnosa = new DefaultTableModel(null, row2) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0 || colIndex == 1) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbDiagnosa.setModel(tabModeDiagnosa);
        //tbDokter.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbDokter.getBackground()));
        tbDiagnosa.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbDiagnosa.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 4; i++) {
            TableColumn column = tbDiagnosa.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(20);
            } else if (i == 2) {
                column.setPreferredWidth(80);
            } else if (i == 3) {
                column.setPreferredWidth(300);
            }
        }
        tbDiagnosa.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeProsedur = new DefaultTableModel(null, new Object[]{
            "P", "Kode", "Deskripsi Panjang"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbProsedur.setModel(tabModeProsedur);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbProsedur.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbProsedur.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbProsedur.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(50);
            } else if (i == 2) {
                column.setPreferredWidth(350);
            }
        }
        tbProsedur.setDefaultRenderer(Object.class, new WarnaTable());

        TCari.setDocument(new batasInput((byte) 100).getKata(TCari));
        NoRujukan.setDocument(new batasInput((byte) 40).getKata(NoRujukan));
        NoSKDP.setDocument(new batasInput((byte) 20).getKata(NoSKDP));
        NoSEPSuplesi.setDocument(new batasInput((byte) 40).getKata(NoSEPSuplesi));
        Catatan.setDocument(new batasInput((byte) 50).getKata(Catatan));
        Keterangan.setDocument(new batasInput((byte) 50).getKata(Keterangan));
        Catatan1.setDocument(new batasInput((byte) 50).getKata(Catatan1));

        if (koneksiDB.cariCepat().equals("aktif")) {
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }
            });
        }

        faskes.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (faskes.getTable().getSelectedRow() != -1) {
                    if (pilihan == 1) {
                        KdPpkRujukan.setText(faskes.getTable().getValueAt(faskes.getTable().getSelectedRow(), 1).toString());
                        NmPpkRujukan.setText(faskes.getTable().getValueAt(faskes.getTable().getSelectedRow(), 2).toString());
                        KdPpkRujukan.requestFocus();
                    } else if (pilihan == 2) {
                        KdPpkRujukan1.setText(faskes.getTable().getValueAt(faskes.getTable().getSelectedRow(), 1).toString());
                        NmPpkRujukan1.setText(faskes.getTable().getValueAt(faskes.getTable().getSelectedRow(), 2).toString());
                        KdPpkRujukan1.requestFocus();
                    }
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        faskes.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    faskes.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        penyakit.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (penyakit.getTable().getSelectedRow() != -1) {
                    if (pilihan == 1) {
                        KdPenyakit.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(), 1).toString());
                        NmPenyakit.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(), 2).toString());
                        KdPenyakit.requestFocus();
                    } else if (pilihan == 2) {
                        KdPenyakit1.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(), 1).toString());
                        NmPenyakit1.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(), 2).toString());
                        KdPenyakit1.requestFocus();
                    }
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        penyakit.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    penyakit.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (poli.getTable().getSelectedRow() != -1) {
                    if (pilihan == 1) {
                        KdPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(), 1).toString());
                        NmPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(), 2).toString());
                        KdPoli.requestFocus();
                    } else if (pilihan == 2) {
                        KdPoli1.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(), 1).toString());
                        NmPoli1.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(), 2).toString());
                        KdPoli1.requestFocus();
                    }
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        poli.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    poli.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (dokter.getTable().getSelectedRow() != -1) {
                    KdDPJP.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                    NmDPJP.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 2).toString());
                    KdDPJP.requestFocus();
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        dokter.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    dokter.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        propinsi.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (propinsi.getTable().getSelectedRow() != -1) {
                    KdPropinsi.setText(propinsi.getTable().getValueAt(propinsi.getTable().getSelectedRow(), 1).toString());
                    NmPropinsi.setText(propinsi.getTable().getValueAt(propinsi.getTable().getSelectedRow(), 2).toString());
                    KdPropinsi.requestFocus();
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        propinsi.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    propinsi.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        kabupaten.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (kabupaten.getTable().getSelectedRow() != -1) {
                    KdKabupaten.setText(kabupaten.getTable().getValueAt(kabupaten.getTable().getSelectedRow(), 1).toString());
                    NmKabupaten.setText(kabupaten.getTable().getValueAt(kabupaten.getTable().getSelectedRow(), 2).toString());
                    KdKabupaten.requestFocus();
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        kabupaten.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    kabupaten.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        kecamatan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (kecamatan.getTable().getSelectedRow() != -1) {
                    KdKecamatan.setText(kecamatan.getTable().getValueAt(kecamatan.getTable().getSelectedRow(), 1).toString());
                    NmKecamatan.setText(kecamatan.getTable().getValueAt(kecamatan.getTable().getSelectedRow(), 2).toString());
                    KdKecamatan.requestFocus();
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        kecamatan.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    kecamatan.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        skdp.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (skdp.getTable().getSelectedRow() != -1) {
                    NoSKDP.setText(skdp.getTable().getValueAt(skdp.getTable().getSelectedRow(), 9).toString());
                    if (!NoRujukan.getText().equals("")) {
                        cmbKunjungan.setSelectedIndex(2);
                        cmbAsesment.setSelectedIndex(5);
                    }
                    KdDPJP.setText(skdp.getTable().getValueAt(skdp.getTable().getSelectedRow(), 11).toString());
                    NmDPJP.setText(skdp.getTable().getValueAt(skdp.getTable().getSelectedRow(), 12).toString());
//                    dpjplayananbpjs = skdp.getTable().getValueAt(skdp.getTable().getSelectedRow(), 11).toString();
                    NoSKDP.requestFocus();
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        skdp.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    skdp.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        skdp2.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (skdp2.getTable().getSelectedRow() != -1) {
                    NoSKDP.setText(skdp2.getTable().getValueAt(skdp2.getTable().getSelectedRow(), 8).toString());
                    Valid.SetTgl(TanggalRujuk, skdp2.getTable().getValueAt(skdp2.getTable().getSelectedRow(), 7).toString());
                    Valid.SetTgl(TanggalSEP, skdp2.getTable().getValueAt(skdp2.getTable().getSelectedRow(), 7).toString());
                    KdDPJP.setText(skdp2.getTable().getValueAt(skdp2.getTable().getSelectedRow(), 10).toString());
                    NmDPJP.setText(skdp2.getTable().getValueAt(skdp2.getTable().getSelectedRow(), 11).toString());
                    KdDPJPLayan.setText(skdp2.getTable().getValueAt(skdp2.getTable().getSelectedRow(), 10).toString());
                    NmDPJPLayan.setText(skdp2.getTable().getValueAt(skdp2.getTable().getSelectedRow(), 11).toString());
                    String kd_diagnosa = skdp2.getTable().getValueAt(skdp2.getTable().getSelectedRow(), 6).toString();
                    int position = kd_diagnosa.indexOf(" - ");
                    System.out.println(position);
                    KdPenyakit.setText(kd_diagnosa.substring(0,position));
                    NmPenyakit.setText(kd_diagnosa.substring(position + 3));
                    NoSKDP.requestFocus();
                    String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                    NoRujukan.setText(timeStamp);
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        skdp2.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    skdp2.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        try {
            KdPPK.setText(Sequel.cariIsi("select kode_ppk from setting"));
            NmPPK.setText(Sequel.cariIsi("select nama_instansi from setting"));
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            user = var.getkode().replace(" ", "").substring(0, 9);
        } catch (Exception e) {
            user = var.getkode();
        }
        jLabel38.setVisible(false);
        TanggalKKL.setVisible(false);
        jLabel36.setVisible(false);
        Keterangan.setVisible(false);
        jLabel40.setVisible(false);
        Suplesi.setVisible(false);
        jLabel41.setVisible(false);
        NoSEPSuplesi.setVisible(false);
        LabelPoli3.setVisible(false);
        KdPropinsi.setVisible(false);
        NmPropinsi.setVisible(false);
        LabelPoli4.setVisible(false);
        KdKabupaten.setVisible(false);
        NmKabupaten.setVisible(false);
        LabelPoli5.setVisible(false);
        KdKecamatan.setVisible(false);
        NmKecamatan.setVisible(false);
        jLabel28.setVisible(false);
        ChkAsa.setVisible(false);
        ChkBPJSTenaga.setVisible(false);
        ChkJasaRaharja.setVisible(false);
        ChkTaspen.setVisible(false);
        btnPropinsi.setVisible(false);
        btnKabupaten.setVisible(false);
        btnKecamatan.setVisible(false);
        jLabel28.setVisible(false);
        link = koneksiDB.UrlBpjs();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Popup = new javax.swing.JPopupMenu();
        ppSEP = new javax.swing.JMenuItem();
        ppSEP1 = new javax.swing.JMenuItem();
        ppSEP2 = new javax.swing.JMenuItem();
        ppSEP3 = new javax.swing.JMenuItem();
        ppSEP4 = new javax.swing.JMenuItem();
        ppPulang = new javax.swing.JMenuItem();
        ppDetailSEPPeserta = new javax.swing.JMenuItem();
        ppRujukan = new javax.swing.JMenuItem();
        ppSuratKontrol = new javax.swing.JMenuItem();
        ppRiwayatPerawatan = new javax.swing.JMenuItem();
        WindowUpdatePulang = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        BtnCloseIn4 = new widget.Button();
        BtnSimpan4 = new widget.Button();
        jLabel26 = new widget.Label();
        TanggalPulang = new widget.Tanggal();
        NoBalasan = new widget.TextBox();
        WindowRujukan = new javax.swing.JDialog();
        internalFrame6 = new widget.InternalFrame();
        BtnCloseIn5 = new widget.Button();
        BtnSimpan5 = new widget.Button();
        jLabel30 = new widget.Label();
        TanggalRujukKeluar = new widget.Tanggal();
        jLabel12 = new widget.Label();
        KdPpkRujukan1 = new widget.TextBox();
        NmPpkRujukan1 = new widget.TextBox();
        btnPPKRujukan1 = new widget.Button();
        jLabel31 = new widget.Label();
        JenisPelayanan1 = new widget.ComboBox();
        jLabel32 = new widget.Label();
        KdPenyakit1 = new widget.TextBox();
        NmPenyakit1 = new widget.TextBox();
        btnDiagnosa1 = new widget.Button();
        LabelPoli1 = new widget.Label();
        KdPoli1 = new widget.TextBox();
        NmPoli1 = new widget.TextBox();
        btnPoli1 = new widget.Button();
        jLabel33 = new widget.Label();
        TipeRujukan = new widget.ComboBox();
        jLabel34 = new widget.Label();
        Catatan1 = new widget.TextBox();
        jLabel42 = new widget.Label();
        TanggalKunjungan = new widget.Tanggal();
        Popup1 = new javax.swing.JPopupMenu();
        ppPengajuan = new javax.swing.JMenuItem();
        ppPengajuan1 = new javax.swing.JMenuItem();
        ppAmbilSep = new javax.swing.JMenuItem();
        ppRujukKhusus = new javax.swing.JMenuItem();
        WindowRujukKhusus = new javax.swing.JDialog();
        internalFrame13 = new widget.InternalFrame();
        BtnCloseIn8 = new widget.Button();
        BtnSimpan8 = new widget.Button();
        jLabel43 = new widget.Label();
        Diagnosa = new widget.TextBox();
        Scroll3 = new widget.ScrollPane();
        tbDiagnosa = new widget.Table();
        BtnCariPenyakit1 = new widget.Button();
        Prosedur = new widget.TextBox();
        Scroll2 = new widget.ScrollPane();
        tbProsedur = new widget.Table();
        jLabel17 = new widget.Label();
        BtnCariProsedur = new widget.Button();
        WindowCariSEP = new javax.swing.JDialog();
        internalFrame7 = new widget.InternalFrame();
        BtnCloseIn6 = new widget.Button();
        BtnSimpan6 = new widget.Button();
        jLabel35 = new widget.Label();
        NoSEP = new widget.TextBox();
        BtnCari1 = new widget.Button();
        WindowCariNoRujuk = new javax.swing.JDialog();
        internalFrame8 = new widget.InternalFrame();
        TabRujukan = new javax.swing.JTabbedPane();
        internalFrame3 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbFaskes1 = new widget.Table();
        internalFrame9 = new widget.InternalFrame();
        scrollPane2 = new widget.ScrollPane();
        tbFaskes2 = new widget.Table();
        internalFrame10 = new widget.InternalFrame();
        scrollPane3 = new widget.ScrollPane();
        tbFaskes3 = new widget.Table();
        internalFrame11 = new widget.InternalFrame();
        scrollPane4 = new widget.ScrollPane();
        tbFaskes4 = new widget.Table();
        internalFrame14 = new widget.InternalFrame();
        scrollPane5 = new widget.ScrollPane();
        tbFaskes5 = new widget.Table();
        bulanRujukKhusus = new widget.ComboBox();
        BtnCariRujukKhusus = new widget.Button();
        panelisi1 = new widget.panelisi();
        BtnKeluar1 = new widget.Button();
        Popup2 = new javax.swing.JPopupMenu();
        ppRujukKhusus1 = new javax.swing.JMenuItem();
        Popup3 = new javax.swing.JPopupMenu();
        ppSEP9 = new javax.swing.JMenuItem();
        ppPulang1 = new javax.swing.JMenuItem();
        ppDetailSEPPeserta1 = new javax.swing.JMenuItem();
        ppRujukan1 = new javax.swing.JMenuItem();
        ppSuratKontrol1 = new javax.swing.JMenuItem();
        ppRiwayatPerawatan1 = new javax.swing.JMenuItem();
        ppHapus = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel5 = new widget.Label();
        NoKartu = new widget.TextBox();
        jLabel20 = new widget.Label();
        TanggalSEP = new widget.Tanggal();
        jLabel22 = new widget.Label();
        TanggalRujuk = new widget.Tanggal();
        jLabel23 = new widget.Label();
        NoRujukan = new widget.TextBox();
        jLabel9 = new widget.Label();
        KdPPK = new widget.TextBox();
        NmPPK = new widget.TextBox();
        btnPPKRujukan = new widget.Button();
        jLabel10 = new widget.Label();
        KdPpkRujukan = new widget.TextBox();
        NmPpkRujukan = new widget.TextBox();
        jLabel11 = new widget.Label();
        KdPenyakit = new widget.TextBox();
        NmPenyakit = new widget.TextBox();
        btnDiagnosa = new widget.Button();
        btnPoli = new widget.Button();
        NmPoli = new widget.TextBox();
        KdPoli = new widget.TextBox();
        LabelPoli = new widget.Label();
        jLabel13 = new widget.Label();
        jLabel14 = new widget.Label();
        Catatan = new widget.TextBox();
        JenisPelayanan = new widget.ComboBox();
        LabelKelas = new widget.Label();
        Kelas = new widget.ComboBox();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel18 = new widget.Label();
        JK = new widget.TextBox();
        jLabel24 = new widget.Label();
        JenisPeserta = new widget.TextBox();
        jLabel25 = new widget.Label();
        Status = new widget.TextBox();
        jLabel27 = new widget.Label();
        AsalRujukan = new widget.ComboBox();
        jLabel39 = new widget.Label();
        NoSKDP = new widget.TextBox();
        btnSKDP = new widget.Button();
        LabelPoli2 = new widget.Label();
        KdDPJP = new widget.TextBox();
        NmDPJP = new widget.TextBox();
        btnDPJP = new widget.Button();
        jLabel29 = new widget.Label();
        NoTelp = new widget.TextBox();
        LabelKelas1 = new widget.Label();
        COB = new widget.ComboBox();
        jLabel15 = new widget.Label();
        Eksekutif = new widget.ComboBox();
        Katarak = new widget.ComboBox();
        jLabel37 = new widget.Label();
        jLabel16 = new widget.Label();
        LakaLantas = new widget.ComboBox();
        jLabel28 = new widget.Label();
        ChkAsa = new widget.CekBox();
        ChkJasaRaharja = new widget.CekBox();
        ChkBPJSTenaga = new widget.CekBox();
        ChkTaspen = new widget.CekBox();
        jLabel38 = new widget.Label();
        TanggalKKL = new widget.Tanggal();
        jLabel36 = new widget.Label();
        Keterangan = new widget.TextBox();
        jLabel40 = new widget.Label();
        Suplesi = new widget.ComboBox();
        NoSEPSuplesi = new widget.TextBox();
        jLabel41 = new widget.Label();
        LabelPoli3 = new widget.Label();
        KdPropinsi = new widget.TextBox();
        NmPropinsi = new widget.TextBox();
        btnPropinsi = new widget.Button();
        LabelPoli4 = new widget.Label();
        KdKabupaten = new widget.TextBox();
        NmKabupaten = new widget.TextBox();
        btnKabupaten = new widget.Button();
        LabelPoli5 = new widget.Label();
        KdKecamatan = new widget.TextBox();
        NmKecamatan = new widget.TextBox();
        btnKecamatan = new widget.Button();
        btnRujukan = new widget.Button();
        LabelKelas2 = new widget.Label();
        Kelas1 = new widget.ComboBox();
        lblPembiayaan = new widget.Label();
        cmbPembiayaan = new widget.ComboBox();
        tjKunjungan = new widget.Label();
        cmbKunjungan = new widget.ComboBox();
        lblProcedure = new widget.Label();
        cmbProcedure = new widget.ComboBox();
        lblPenunjang = new widget.Label();
        cmbPenunjang = new widget.ComboBox();
        cmbAsesment = new widget.ComboBox();
        lblAsesment = new widget.Label();
        pnj = new widget.TextBox();
        btnSPRI = new widget.Button();
        KdDPJPLayan = new widget.TextBox();
        LabelPoli6 = new widget.Label();
        NmDPJPLayan = new widget.TextBox();
        btnDPJP1 = new widget.Button();
        internalFrame4 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        jLabel48 = new widget.Label();
        cmbHlm = new widget.ComboBox();
        internalFrame12 = new widget.InternalFrame();
        Scroll4 = new widget.ScrollPane();
        tbObat1 = new widget.Table();
        panelGlass10 = new widget.panelisi();
        jLabel44 = new widget.Label();
        DTPCari3 = new widget.Tanggal();
        jLabel45 = new widget.Label();
        DTPCari4 = new widget.Tanggal();
        jLabel46 = new widget.Label();
        TCari1 = new widget.TextBox();
        BtnCari2 = new widget.Button();
        jLabel47 = new widget.Label();
        LCount1 = new widget.Label();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnEdit = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();

        Popup.setName("Popup"); // NOI18N

        ppSEP.setBackground(new java.awt.Color(255, 255, 254));
        ppSEP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSEP.setForeground(new java.awt.Color(70, 70, 70));
        ppSEP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSEP.setText("Print SEP Model 1");
        ppSEP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSEP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSEP.setIconTextGap(8);
        ppSEP.setName("ppSEP"); // NOI18N
        ppSEP.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSEP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSEPBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSEP);

        ppSEP1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSEP1.setForeground(new java.awt.Color(70, 70, 70));
        ppSEP1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSEP1.setText("Print SEP Model 2");
        ppSEP1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSEP1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSEP1.setIconTextGap(8);
        ppSEP1.setName("ppSEP1"); // NOI18N
        ppSEP1.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSEP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSEP1BtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSEP1);

        ppSEP2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSEP2.setForeground(new java.awt.Color(70, 70, 70));
        ppSEP2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSEP2.setText("Print SEP Model 3");
        ppSEP2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSEP2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSEP2.setIconTextGap(8);
        ppSEP2.setName("ppSEP2"); // NOI18N
        ppSEP2.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSEP2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSEP2BtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSEP2);

        ppSEP3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSEP3.setForeground(new java.awt.Color(70, 70, 70));
        ppSEP3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSEP3.setText("Print SEP Model 4");
        ppSEP3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSEP3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSEP3.setIconTextGap(8);
        ppSEP3.setName("ppSEP3"); // NOI18N
        ppSEP3.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSEP3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSEP3BtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSEP3);

        ppSEP4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSEP4.setForeground(new java.awt.Color(70, 70, 70));
        ppSEP4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSEP4.setText("Print SEP Model 5");
        ppSEP4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSEP4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSEP4.setIconTextGap(8);
        ppSEP4.setName("ppSEP4"); // NOI18N
        ppSEP4.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSEP4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSEP4BtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSEP4);

        ppPulang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPulang.setForeground(new java.awt.Color(70, 70, 70));
        ppPulang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPulang.setText("Update Tanggal Pulang");
        ppPulang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPulang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPulang.setIconTextGap(8);
        ppPulang.setName("ppPulang"); // NOI18N
        ppPulang.setPreferredSize(new java.awt.Dimension(200, 25));
        ppPulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPulangBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppPulang);

        ppDetailSEPPeserta.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppDetailSEPPeserta.setForeground(new java.awt.Color(70, 70, 70));
        ppDetailSEPPeserta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppDetailSEPPeserta.setText("Detail SEP Peserta");
        ppDetailSEPPeserta.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDetailSEPPeserta.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDetailSEPPeserta.setIconTextGap(8);
        ppDetailSEPPeserta.setName("ppDetailSEPPeserta"); // NOI18N
        ppDetailSEPPeserta.setPreferredSize(new java.awt.Dimension(200, 25));
        ppDetailSEPPeserta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDetailSEPPesertaBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppDetailSEPPeserta);

        ppRujukan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRujukan.setForeground(new java.awt.Color(70, 70, 70));
        ppRujukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRujukan.setText("Buat Rujukan Keluar");
        ppRujukan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRujukan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRujukan.setIconTextGap(8);
        ppRujukan.setName("ppRujukan"); // NOI18N
        ppRujukan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppRujukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRujukanBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppRujukan);

        ppSuratKontrol.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSuratKontrol.setForeground(new java.awt.Color(70, 70, 70));
        ppSuratKontrol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSuratKontrol.setText("Rencana Kontrol BPJS");
        ppSuratKontrol.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSuratKontrol.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSuratKontrol.setIconTextGap(8);
        ppSuratKontrol.setName("ppSuratKontrol"); // NOI18N
        ppSuratKontrol.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSuratKontrol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSuratKontrolBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppSuratKontrol);

        ppRiwayatPerawatan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRiwayatPerawatan.setForeground(new java.awt.Color(70, 70, 70));
        ppRiwayatPerawatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRiwayatPerawatan.setText("Riwayat Perawatan");
        ppRiwayatPerawatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRiwayatPerawatan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRiwayatPerawatan.setIconTextGap(8);
        ppRiwayatPerawatan.setName("ppRiwayatPerawatan"); // NOI18N
        ppRiwayatPerawatan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppRiwayatPerawatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRiwayatPerawatanBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppRiwayatPerawatan);

        WindowUpdatePulang.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowUpdatePulang.setName("WindowUpdatePulang"); // NOI18N
        WindowUpdatePulang.setUndecorated(true);
        WindowUpdatePulang.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Update Tanggal Pulang ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 13), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(null);

        BtnCloseIn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn4.setMnemonic('U');
        BtnCloseIn4.setText("Tutup");
        BtnCloseIn4.setToolTipText("Alt+U");
        BtnCloseIn4.setName("BtnCloseIn4"); // NOI18N
        BtnCloseIn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn4ActionPerformed(evt);
            }
        });
        internalFrame5.add(BtnCloseIn4);
        BtnCloseIn4.setBounds(510, 30, 100, 30);

        BtnSimpan4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan4.setMnemonic('S');
        BtnSimpan4.setText("Simpan");
        BtnSimpan4.setToolTipText("Alt+S");
        BtnSimpan4.setName("BtnSimpan4"); // NOI18N
        BtnSimpan4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan4ActionPerformed(evt);
            }
        });
        internalFrame5.add(BtnSimpan4);
        BtnSimpan4.setBounds(405, 30, 100, 30);

        jLabel26.setText("Tanggal Pulang :");
        jLabel26.setName("jLabel26"); // NOI18N
        internalFrame5.add(jLabel26);
        jLabel26.setBounds(6, 32, 100, 23);

        TanggalPulang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-08-2022 08:36:04" }));
        TanggalPulang.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalPulang.setName("TanggalPulang"); // NOI18N
        TanggalPulang.setOpaque(false);
        TanggalPulang.setPreferredSize(new java.awt.Dimension(95, 23));
        internalFrame5.add(TanggalPulang);
        TanggalPulang.setBounds(110, 32, 220, 23);

        WindowUpdatePulang.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        NoBalasan.setHighlighter(null);
        NoBalasan.setName("NoBalasan"); // NOI18N

        WindowRujukan.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowRujukan.setName("WindowRujukan"); // NOI18N
        WindowRujukan.setUndecorated(true);
        WindowRujukan.setResizable(false);

        internalFrame6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Buat Rujukan Keluar VClaim ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 13), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setLayout(null);

        BtnCloseIn5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn5.setMnemonic('U');
        BtnCloseIn5.setText("Tutup");
        BtnCloseIn5.setToolTipText("Alt+U");
        BtnCloseIn5.setName("BtnCloseIn5"); // NOI18N
        BtnCloseIn5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn5ActionPerformed(evt);
            }
        });
        internalFrame6.add(BtnCloseIn5);
        BtnCloseIn5.setBounds(692, 145, 100, 30);

        BtnSimpan5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan5.setMnemonic('S');
        BtnSimpan5.setText("Simpan");
        BtnSimpan5.setToolTipText("Alt+S");
        BtnSimpan5.setName("BtnSimpan5"); // NOI18N
        BtnSimpan5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan5ActionPerformed(evt);
            }
        });
        internalFrame6.add(BtnSimpan5);
        BtnSimpan5.setBounds(20, 145, 100, 30);

        jLabel30.setText("Tanggal Rujukan :");
        jLabel30.setName("jLabel30"); // NOI18N
        internalFrame6.add(jLabel30);
        jLabel30.setBounds(0, 25, 102, 23);

        TanggalRujukKeluar.setForeground(new java.awt.Color(50, 70, 50));
        TanggalRujukKeluar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-08-2022" }));
        TanggalRujukKeluar.setDisplayFormat("dd-MM-yyyy");
        TanggalRujukKeluar.setName("TanggalRujukKeluar"); // NOI18N
        TanggalRujukKeluar.setOpaque(false);
        TanggalRujukKeluar.setPreferredSize(new java.awt.Dimension(95, 23));
        internalFrame6.add(TanggalRujukKeluar);
        TanggalRujukKeluar.setBounds(105, 25, 90, 23);

        jLabel12.setText("PPK Rujukan :");
        jLabel12.setName("jLabel12"); // NOI18N
        internalFrame6.add(jLabel12);
        jLabel12.setBounds(0, 55, 102, 23);

        KdPpkRujukan1.setEditable(false);
        KdPpkRujukan1.setBackground(new java.awt.Color(245, 250, 240));
        KdPpkRujukan1.setHighlighter(null);
        KdPpkRujukan1.setName("KdPpkRujukan1"); // NOI18N
        internalFrame6.add(KdPpkRujukan1);
        KdPpkRujukan1.setBounds(105, 55, 75, 23);

        NmPpkRujukan1.setEditable(false);
        NmPpkRujukan1.setBackground(new java.awt.Color(245, 250, 240));
        NmPpkRujukan1.setHighlighter(null);
        NmPpkRujukan1.setName("NmPpkRujukan1"); // NOI18N
        internalFrame6.add(NmPpkRujukan1);
        NmPpkRujukan1.setBounds(182, 55, 200, 23);

        btnPPKRujukan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPPKRujukan1.setMnemonic('X');
        btnPPKRujukan1.setToolTipText("Alt+X");
        btnPPKRujukan1.setName("btnPPKRujukan1"); // NOI18N
        btnPPKRujukan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPPKRujukan1ActionPerformed(evt);
            }
        });
        btnPPKRujukan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPPKRujukan1KeyPressed(evt);
            }
        });
        internalFrame6.add(btnPPKRujukan1);
        btnPPKRujukan1.setBounds(385, 55, 28, 23);

        jLabel31.setText("Jns.Pelayanan :");
        jLabel31.setName("jLabel31"); // NOI18N
        internalFrame6.add(jLabel31);
        jLabel31.setBounds(425, 55, 85, 23);

        JenisPelayanan1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Rawat Inap", "2. Rawat Jalan" }));
        JenisPelayanan1.setSelectedIndex(1);
        JenisPelayanan1.setName("JenisPelayanan1"); // NOI18N
        JenisPelayanan1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JenisPelayanan1ItemStateChanged(evt);
            }
        });
        JenisPelayanan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisPelayanan1KeyPressed(evt);
            }
        });
        internalFrame6.add(JenisPelayanan1);
        JenisPelayanan1.setBounds(514, 55, 278, 23);

        jLabel32.setText("Diagnosa Rujuk :");
        jLabel32.setName("jLabel32"); // NOI18N
        internalFrame6.add(jLabel32);
        jLabel32.setBounds(0, 85, 102, 23);

        KdPenyakit1.setEditable(false);
        KdPenyakit1.setBackground(new java.awt.Color(245, 250, 240));
        KdPenyakit1.setHighlighter(null);
        KdPenyakit1.setName("KdPenyakit1"); // NOI18N
        internalFrame6.add(KdPenyakit1);
        KdPenyakit1.setBounds(105, 85, 75, 23);

        NmPenyakit1.setEditable(false);
        NmPenyakit1.setBackground(new java.awt.Color(245, 250, 240));
        NmPenyakit1.setHighlighter(null);
        NmPenyakit1.setName("NmPenyakit1"); // NOI18N
        internalFrame6.add(NmPenyakit1);
        NmPenyakit1.setBounds(182, 85, 200, 23);

        btnDiagnosa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDiagnosa1.setMnemonic('X');
        btnDiagnosa1.setToolTipText("Alt+X");
        btnDiagnosa1.setName("btnDiagnosa1"); // NOI18N
        btnDiagnosa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiagnosa1ActionPerformed(evt);
            }
        });
        btnDiagnosa1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDiagnosa1KeyPressed(evt);
            }
        });
        internalFrame6.add(btnDiagnosa1);
        btnDiagnosa1.setBounds(385, 85, 28, 23);

        LabelPoli1.setText("Poli Tujuan :");
        LabelPoli1.setName("LabelPoli1"); // NOI18N
        internalFrame6.add(LabelPoli1);
        LabelPoli1.setBounds(425, 85, 85, 23);

        KdPoli1.setEditable(false);
        KdPoli1.setBackground(new java.awt.Color(245, 250, 240));
        KdPoli1.setHighlighter(null);
        KdPoli1.setName("KdPoli1"); // NOI18N
        internalFrame6.add(KdPoli1);
        KdPoli1.setBounds(514, 85, 65, 23);

        NmPoli1.setEditable(false);
        NmPoli1.setBackground(new java.awt.Color(245, 250, 240));
        NmPoli1.setHighlighter(null);
        NmPoli1.setName("NmPoli1"); // NOI18N
        internalFrame6.add(NmPoli1);
        NmPoli1.setBounds(581, 85, 180, 23);

        btnPoli1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPoli1.setMnemonic('X');
        btnPoli1.setToolTipText("Alt+X");
        btnPoli1.setName("btnPoli1"); // NOI18N
        btnPoli1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPoli1ActionPerformed(evt);
            }
        });
        btnPoli1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPoli1KeyPressed(evt);
            }
        });
        internalFrame6.add(btnPoli1);
        btnPoli1.setBounds(764, 85, 28, 23);

        jLabel33.setText("Tipe Rujukan :");
        jLabel33.setName("jLabel33"); // NOI18N
        internalFrame6.add(jLabel33);
        jLabel33.setBounds(430, 25, 80, 23);

        TipeRujukan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Penuh", "1. Partial", "2. Rujuk Balik (PRB)" }));
        TipeRujukan.setName("TipeRujukan"); // NOI18N
        TipeRujukan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TipeRujukanItemStateChanged(evt);
            }
        });
        TipeRujukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TipeRujukanActionPerformed(evt);
            }
        });
        TipeRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TipeRujukanKeyPressed(evt);
            }
        });
        internalFrame6.add(TipeRujukan);
        TipeRujukan.setBounds(510, 25, 130, 23);

        jLabel34.setText("Catatan :");
        jLabel34.setName("jLabel34"); // NOI18N
        internalFrame6.add(jLabel34);
        jLabel34.setBounds(18, 115, 85, 23);

        Catatan1.setHighlighter(null);
        Catatan1.setName("Catatan1"); // NOI18N
        Catatan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Catatan1KeyPressed(evt);
            }
        });
        internalFrame6.add(Catatan1);
        Catatan1.setBounds(105, 115, 278, 23);

        jLabel42.setText("Tanggal Kunjungan :");
        jLabel42.setName("jLabel42"); // NOI18N
        internalFrame6.add(jLabel42);
        jLabel42.setBounds(215, 25, 102, 23);

        TanggalKunjungan.setForeground(new java.awt.Color(50, 70, 50));
        TanggalKunjungan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-08-2022" }));
        TanggalKunjungan.setDisplayFormat("dd-MM-yyyy");
        TanggalKunjungan.setName("TanggalKunjungan"); // NOI18N
        TanggalKunjungan.setOpaque(false);
        TanggalKunjungan.setPreferredSize(new java.awt.Dimension(95, 23));
        internalFrame6.add(TanggalKunjungan);
        TanggalKunjungan.setBounds(320, 25, 90, 23);

        WindowRujukan.getContentPane().add(internalFrame6, java.awt.BorderLayout.CENTER);

        Popup1.setName("Popup1"); // NOI18N

        ppPengajuan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPengajuan.setForeground(new java.awt.Color(70, 70, 70));
        ppPengajuan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPengajuan.setText("Pengajuan SEP");
        ppPengajuan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPengajuan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPengajuan.setIconTextGap(8);
        ppPengajuan.setName("ppPengajuan"); // NOI18N
        ppPengajuan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppPengajuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPengajuanBtnPrintActionPerformed(evt);
            }
        });
        Popup1.add(ppPengajuan);

        ppPengajuan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPengajuan1.setForeground(new java.awt.Color(70, 70, 70));
        ppPengajuan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPengajuan1.setText("Aproval SEP");
        ppPengajuan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPengajuan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPengajuan1.setIconTextGap(8);
        ppPengajuan1.setName("ppPengajuan1"); // NOI18N
        ppPengajuan1.setPreferredSize(new java.awt.Dimension(200, 25));
        ppPengajuan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPengajuan1BtnPrintActionPerformed(evt);
            }
        });
        Popup1.add(ppPengajuan1);

        ppAmbilSep.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppAmbilSep.setForeground(new java.awt.Color(70, 70, 70));
        ppAmbilSep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppAmbilSep.setText("Ambil SEP di VClaim");
        ppAmbilSep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppAmbilSep.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppAmbilSep.setIconTextGap(8);
        ppAmbilSep.setName("ppAmbilSep"); // NOI18N
        ppAmbilSep.setPreferredSize(new java.awt.Dimension(200, 25));
        ppAmbilSep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppAmbilSepBtnPrintActionPerformed(evt);
            }
        });
        Popup1.add(ppAmbilSep);

        ppRujukKhusus.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        ppRujukKhusus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRujukKhusus.setText("Rujukan Khusus");
        ppRujukKhusus.setIconTextGap(8);
        ppRujukKhusus.setName("ppRujukKhusus"); // NOI18N
        ppRujukKhusus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRujukKhususActionPerformed(evt);
            }
        });
        Popup1.add(ppRujukKhusus);

        WindowRujukKhusus.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowRujukKhusus.setName("WindowRujukKhusus"); // NOI18N
        WindowRujukKhusus.setUndecorated(true);
        WindowRujukKhusus.setResizable(false);

        internalFrame13.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rujukan Khusus VClaim ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 13), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame13.setName("internalFrame13"); // NOI18N
        internalFrame13.setLayout(null);

        BtnCloseIn8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn8.setMnemonic('U');
        BtnCloseIn8.setText("Tutup");
        BtnCloseIn8.setToolTipText("Alt+U");
        BtnCloseIn8.setName("BtnCloseIn8"); // NOI18N
        BtnCloseIn8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn8ActionPerformed(evt);
            }
        });
        internalFrame13.add(BtnCloseIn8);
        BtnCloseIn8.setBounds(140, 320, 100, 30);

        BtnSimpan8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan8.setMnemonic('S');
        BtnSimpan8.setText("Simpan");
        BtnSimpan8.setToolTipText("Alt+S");
        BtnSimpan8.setName("BtnSimpan8"); // NOI18N
        BtnSimpan8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan8ActionPerformed(evt);
            }
        });
        internalFrame13.add(BtnSimpan8);
        BtnSimpan8.setBounds(30, 320, 100, 30);

        jLabel43.setText("Diagnosa :");
        jLabel43.setName("jLabel43"); // NOI18N
        internalFrame13.add(jLabel43);
        jLabel43.setBounds(20, 30, 68, 23);

        Diagnosa.setHighlighter(null);
        Diagnosa.setName("Diagnosa"); // NOI18N
        Diagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaKeyPressed(evt);
            }
        });
        internalFrame13.add(Diagnosa);
        Diagnosa.setBounds(90, 30, 400, 23);

        Scroll3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll3.setName("Scroll3"); // NOI18N

        tbDiagnosa.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDiagnosa.setName("tbDiagnosa"); // NOI18N
        tbDiagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDiagnosaKeyPressed(evt);
            }
        });
        Scroll3.setViewportView(tbDiagnosa);

        internalFrame13.add(Scroll3);
        Scroll3.setBounds(100, 60, 420, 90);

        BtnCariPenyakit1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariPenyakit1.setMnemonic('1');
        BtnCariPenyakit1.setToolTipText("Alt+1");
        BtnCariPenyakit1.setName("BtnCariPenyakit1"); // NOI18N
        BtnCariPenyakit1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariPenyakit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariPenyakit1ActionPerformed(evt);
            }
        });
        internalFrame13.add(BtnCariPenyakit1);
        BtnCariPenyakit1.setBounds(490, 30, 28, 23);

        Prosedur.setHighlighter(null);
        Prosedur.setName("Prosedur"); // NOI18N
        Prosedur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProsedurKeyPressed(evt);
            }
        });
        internalFrame13.add(Prosedur);
        Prosedur.setBounds(90, 170, 400, 23);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbProsedur.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbProsedur.setName("tbProsedur"); // NOI18N
        Scroll2.setViewportView(tbProsedur);

        internalFrame13.add(Scroll2);
        Scroll2.setBounds(100, 200, 420, 110);

        jLabel17.setText("Prosedur :");
        jLabel17.setName("jLabel17"); // NOI18N
        internalFrame13.add(jLabel17);
        jLabel17.setBounds(20, 170, 68, 23);

        BtnCariProsedur.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariProsedur.setMnemonic('1');
        BtnCariProsedur.setToolTipText("Alt+1");
        BtnCariProsedur.setName("BtnCariProsedur"); // NOI18N
        BtnCariProsedur.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariProsedur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariProsedurActionPerformed(evt);
            }
        });
        internalFrame13.add(BtnCariProsedur);
        BtnCariProsedur.setBounds(490, 170, 28, 23);

        WindowRujukKhusus.getContentPane().add(internalFrame13, java.awt.BorderLayout.CENTER);

        WindowCariSEP.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowCariSEP.setName("WindowCariSEP"); // NOI18N
        WindowCariSEP.setUndecorated(true);
        WindowCariSEP.setResizable(false);

        internalFrame7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ambil SEP di VClaim ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 13), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame7.setName("internalFrame7"); // NOI18N
        internalFrame7.setLayout(null);

        BtnCloseIn6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn6.setMnemonic('U');
        BtnCloseIn6.setText("Tutup");
        BtnCloseIn6.setToolTipText("Alt+U");
        BtnCloseIn6.setName("BtnCloseIn6"); // NOI18N
        BtnCloseIn6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn6ActionPerformed(evt);
            }
        });
        internalFrame7.add(BtnCloseIn6);
        BtnCloseIn6.setBounds(290, 70, 100, 30);

        BtnSimpan6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan6.setMnemonic('S');
        BtnSimpan6.setText("Simpan");
        BtnSimpan6.setToolTipText("Alt+S");
        BtnSimpan6.setName("BtnSimpan6"); // NOI18N
        BtnSimpan6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan6ActionPerformed(evt);
            }
        });
        internalFrame7.add(BtnSimpan6);
        BtnSimpan6.setBounds(20, 70, 100, 30);

        jLabel35.setText("No.SEP VClaim :");
        jLabel35.setName("jLabel35"); // NOI18N
        internalFrame7.add(jLabel35);
        jLabel35.setBounds(0, 25, 102, 23);

        NoSEP.setHighlighter(null);
        NoSEP.setName("NoSEP"); // NOI18N
        NoSEP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoSEPKeyPressed(evt);
            }
        });
        internalFrame7.add(NoSEP);
        NoSEP.setBounds(106, 25, 240, 23);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('3');
        BtnCari1.setToolTipText("Alt+3");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari1ActionPerformed(evt);
            }
        });
        BtnCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari1KeyPressed(evt);
            }
        });
        internalFrame7.add(BtnCari1);
        BtnCari1.setBounds(350, 25, 28, 23);

        WindowCariSEP.getContentPane().add(internalFrame7, java.awt.BorderLayout.CENTER);

        WindowCariNoRujuk.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowCariNoRujuk.setName("WindowCariNoRujuk"); // NOI18N
        WindowCariNoRujuk.setUndecorated(true);
        WindowCariNoRujuk.setResizable(false);
        WindowCariNoRujuk.getContentPane().setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Cek Riwayat Rujukan Faskes ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        internalFrame8.setName("internalFrame8"); // NOI18N
        internalFrame8.setWarnaBawah(new java.awt.Color(245, 250, 240));
        internalFrame8.setLayout(new java.awt.BorderLayout(1, 1));

        TabRujukan.setToolTipText("");
        TabRujukan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        TabRujukan.setName("TabRujukan"); // NOI18N
        TabRujukan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRujukanMouseClicked(evt);
            }
        });

        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbFaskes1.setToolTipText("Klik data di tabel");
        tbFaskes1.setName("tbFaskes1"); // NOI18N
        tbFaskes1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbFaskes1MouseClicked(evt);
            }
        });
        tbFaskes1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbFaskes1KeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbFaskes1);

        internalFrame3.add(scrollPane1, java.awt.BorderLayout.CENTER);

        TabRujukan.addTab(".: Faskes 1", internalFrame3);

        internalFrame9.setName("internalFrame9"); // NOI18N
        internalFrame9.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setOpaque(true);

        tbFaskes2.setToolTipText("Klik data di tabel");
        tbFaskes2.setName("tbFaskes2"); // NOI18N
        tbFaskes2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbFaskes2MouseClicked(evt);
            }
        });
        tbFaskes2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbFaskes2KeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(tbFaskes2);

        internalFrame9.add(scrollPane2, java.awt.BorderLayout.CENTER);

        TabRujukan.addTab(".: Faskes 2 (RS)", internalFrame9);

        internalFrame10.setName("internalFrame10"); // NOI18N
        internalFrame10.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane3.setName("scrollPane3"); // NOI18N
        scrollPane3.setOpaque(true);

        tbFaskes3.setToolTipText("Klik data di tabel");
        tbFaskes3.setName("tbFaskes3"); // NOI18N
        tbFaskes3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbFaskes3MouseClicked(evt);
            }
        });
        tbFaskes3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbFaskes3KeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(tbFaskes3);

        internalFrame10.add(scrollPane3, java.awt.BorderLayout.CENTER);

        TabRujukan.addTab(".: Faskes 1 (banyak)", internalFrame10);

        internalFrame11.setName("internalFrame11"); // NOI18N
        internalFrame11.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane4.setName("scrollPane4"); // NOI18N
        scrollPane4.setOpaque(true);

        tbFaskes4.setToolTipText("Klik data di tabel");
        tbFaskes4.setName("tbFaskes4"); // NOI18N
        tbFaskes4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbFaskes4MouseClicked(evt);
            }
        });
        tbFaskes4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbFaskes4KeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(tbFaskes4);

        internalFrame11.add(scrollPane4, java.awt.BorderLayout.CENTER);

        TabRujukan.addTab(".: Faskes 2 (RS) banyak", internalFrame11);

        internalFrame14.setName("internalFrame14"); // NOI18N
        internalFrame14.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane5.setName("scrollPane5"); // NOI18N
        scrollPane5.setOpaque(true);

        tbFaskes5.setToolTipText("Klik data di tabel");
        tbFaskes5.setName("tbFaskes5"); // NOI18N
        tbFaskes5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbFaskes5MouseClicked(evt);
            }
        });
        tbFaskes5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbFaskes5KeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(tbFaskes5);

        internalFrame14.add(scrollPane5, java.awt.BorderLayout.CENTER);

        bulanRujukKhusus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Bulan 1", "Bulan 2", "Bulan 3", "Bulan 4", "Bulan 5", "Bulan 6", "Bulan 7", "Bulan 8", "Bulan 9", "Bulan 10", "Bulan 11", "Bulan 12" }));
        bulanRujukKhusus.setName("bulanRujukKhusus"); // NOI18N
        bulanRujukKhusus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                bulanRujukKhususKeyPressed(evt);
            }
        });
        internalFrame14.add(bulanRujukKhusus, java.awt.BorderLayout.PAGE_START);

        BtnCariRujukKhusus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariRujukKhusus.setMnemonic('3');
        BtnCariRujukKhusus.setToolTipText("Alt+3");
        BtnCariRujukKhusus.setName("BtnCariRujukKhusus"); // NOI18N
        BtnCariRujukKhusus.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariRujukKhusus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariRujukKhususActionPerformed(evt);
            }
        });
        BtnCariRujukKhusus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariRujukKhususKeyPressed(evt);
            }
        });
        internalFrame14.add(BtnCariRujukKhusus, java.awt.BorderLayout.LINE_END);

        TabRujukan.addTab(".: Rujukan Khusus", internalFrame14);

        internalFrame8.add(TabRujukan, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(44, 54));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnKeluar1.setForeground(new java.awt.Color(0, 0, 0));
        BtnKeluar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar1.setMnemonic('K');
        BtnKeluar1.setText("Keluar");
        BtnKeluar1.setToolTipText("Alt+K");
        BtnKeluar1.setName("BtnKeluar1"); // NOI18N
        BtnKeluar1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar1ActionPerformed(evt);
            }
        });
        BtnKeluar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluar1KeyPressed(evt);
            }
        });
        panelisi1.add(BtnKeluar1);

        internalFrame8.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        WindowCariNoRujuk.getContentPane().add(internalFrame8, java.awt.BorderLayout.CENTER);

        Popup2.setName("Popup2"); // NOI18N

        ppRujukKhusus1.setFont(new java.awt.Font("Lucida Grande", 0, 11)); // NOI18N
        ppRujukKhusus1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRujukKhusus1.setText("Rujukan Khusus");
        ppRujukKhusus1.setIconTextGap(8);
        ppRujukKhusus1.setName("ppRujukKhusus1"); // NOI18N
        ppRujukKhusus1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRujukKhusus1ActionPerformed(evt);
            }
        });
        Popup2.add(ppRujukKhusus1);

        Popup3.setName("Popup3"); // NOI18N

        ppSEP9.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSEP9.setForeground(new java.awt.Color(70, 70, 70));
        ppSEP9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSEP9.setText("Print SEP ");
        ppSEP9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSEP9.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSEP9.setIconTextGap(8);
        ppSEP9.setName("ppSEP9"); // NOI18N
        ppSEP9.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSEP9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSEP9BtnPrintActionPerformed(evt);
            }
        });
        Popup3.add(ppSEP9);

        ppPulang1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPulang1.setForeground(new java.awt.Color(70, 70, 70));
        ppPulang1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPulang1.setText("Update Tanggal Pulang");
        ppPulang1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPulang1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPulang1.setIconTextGap(8);
        ppPulang1.setName("ppPulang1"); // NOI18N
        ppPulang1.setPreferredSize(new java.awt.Dimension(200, 25));
        ppPulang1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPulang1BtnPrintActionPerformed(evt);
            }
        });
        Popup3.add(ppPulang1);

        ppDetailSEPPeserta1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppDetailSEPPeserta1.setForeground(new java.awt.Color(70, 70, 70));
        ppDetailSEPPeserta1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppDetailSEPPeserta1.setText("Detail SEP Peserta");
        ppDetailSEPPeserta1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDetailSEPPeserta1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDetailSEPPeserta1.setIconTextGap(8);
        ppDetailSEPPeserta1.setName("ppDetailSEPPeserta1"); // NOI18N
        ppDetailSEPPeserta1.setPreferredSize(new java.awt.Dimension(200, 25));
        ppDetailSEPPeserta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDetailSEPPeserta1BtnPrintActionPerformed(evt);
            }
        });
        Popup3.add(ppDetailSEPPeserta1);

        ppRujukan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRujukan1.setForeground(new java.awt.Color(70, 70, 70));
        ppRujukan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRujukan1.setText("Buat Rujukan Keluar");
        ppRujukan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRujukan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRujukan1.setIconTextGap(8);
        ppRujukan1.setName("ppRujukan1"); // NOI18N
        ppRujukan1.setPreferredSize(new java.awt.Dimension(200, 25));
        ppRujukan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRujukan1BtnPrintActionPerformed(evt);
            }
        });
        Popup3.add(ppRujukan1);

        ppSuratKontrol1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSuratKontrol1.setForeground(new java.awt.Color(70, 70, 70));
        ppSuratKontrol1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSuratKontrol1.setText("Rencana Kontrol BPJS");
        ppSuratKontrol1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSuratKontrol1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSuratKontrol1.setIconTextGap(8);
        ppSuratKontrol1.setName("ppSuratKontrol1"); // NOI18N
        ppSuratKontrol1.setPreferredSize(new java.awt.Dimension(200, 25));
        ppSuratKontrol1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSuratKontrol1BtnPrintActionPerformed(evt);
            }
        });
        Popup3.add(ppSuratKontrol1);

        ppRiwayatPerawatan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRiwayatPerawatan1.setForeground(new java.awt.Color(70, 70, 70));
        ppRiwayatPerawatan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRiwayatPerawatan1.setText("Riwayat Perawatan");
        ppRiwayatPerawatan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRiwayatPerawatan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRiwayatPerawatan1.setIconTextGap(8);
        ppRiwayatPerawatan1.setName("ppRiwayatPerawatan1"); // NOI18N
        ppRiwayatPerawatan1.setPreferredSize(new java.awt.Dimension(200, 25));
        ppRiwayatPerawatan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRiwayatPerawatan1BtnPrintActionPerformed(evt);
            }
        });
        Popup3.add(ppRiwayatPerawatan1);

        ppHapus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapus.setForeground(new java.awt.Color(70, 70, 70));
        ppHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppHapus.setText("Hapus SEP Internal");
        ppHapus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapus.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapus.setIconTextGap(8);
        ppHapus.setName("ppHapus"); // NOI18N
        ppHapus.setPreferredSize(new java.awt.Dimension(200, 25));
        ppHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHapusBtnPrintActionPerformed(evt);
            }
        });
        Popup3.add(ppHapus);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Bridging SEP BPJS ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        TabRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabRawat.setForeground(new java.awt.Color(70, 70, 70));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 257));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 90, 23);

        TNoRw.setBackground(new java.awt.Color(245, 250, 240));
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        FormInput.add(TNoRw);
        TNoRw.setBounds(93, 10, 152, 23);

        TPasien.setEditable(false);
        TPasien.setBackground(new java.awt.Color(245, 250, 240));
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(359, 10, 392, 23);

        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TNoRMActionPerformed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(247, 10, 110, 23);

        jLabel5.setText("No.Kartu :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 70, 90, 23);

        NoKartu.setEditable(false);
        NoKartu.setBackground(new java.awt.Color(245, 250, 240));
        NoKartu.setComponentPopupMenu(Popup1);
        NoKartu.setHighlighter(null);
        NoKartu.setName("NoKartu"); // NOI18N
        FormInput.add(NoKartu);
        NoKartu.setBounds(93, 70, 152, 23);

        jLabel20.setText("Tgl.SEP :");
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel20);
        jLabel20.setBounds(187, 102, 65, 23);

        TanggalSEP.setForeground(new java.awt.Color(50, 70, 50));
        TanggalSEP.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-08-2022" }));
        TanggalSEP.setDisplayFormat("dd-MM-yyyy");
        TanggalSEP.setName("TanggalSEP"); // NOI18N
        TanggalSEP.setOpaque(false);
        TanggalSEP.setPreferredSize(new java.awt.Dimension(95, 23));
        TanggalSEP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalSEPKeyPressed(evt);
            }
        });
        FormInput.add(TanggalSEP);
        TanggalSEP.setBounds(255, 100, 95, 23);

        jLabel22.setText("Tgl.Rujuk :");
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel22);
        jLabel22.setBounds(0, 100, 90, 23);

        TanggalRujuk.setForeground(new java.awt.Color(50, 70, 50));
        TanggalRujuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-08-2022" }));
        TanggalRujuk.setDisplayFormat("dd-MM-yyyy");
        TanggalRujuk.setName("TanggalRujuk"); // NOI18N
        TanggalRujuk.setOpaque(false);
        TanggalRujuk.setPreferredSize(new java.awt.Dimension(95, 23));
        TanggalRujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalRujukKeyPressed(evt);
            }
        });
        FormInput.add(TanggalRujuk);
        TanggalRujuk.setBounds(93, 100, 95, 23);

        jLabel23.setText("No.Rujukan :");
        jLabel23.setName("jLabel23"); // NOI18N
        jLabel23.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel23);
        jLabel23.setBounds(480, 70, 70, 23);

        NoRujukan.setHighlighter(null);
        NoRujukan.setComponentPopupMenu(Popup2);
        NoRujukan.setName("NoRujukan"); // NOI18N
        NoRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRujukanKeyPressed(evt);
            }
        });
        FormInput.add(NoRujukan);
        NoRujukan.setBounds(557, 70, 170, 23);

        jLabel9.setText("PPK Pelayanan :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(375, 190, 90, 23);

        KdPPK.setEditable(false);
        KdPPK.setBackground(new java.awt.Color(245, 250, 240));
        KdPPK.setHighlighter(null);
        KdPPK.setName("KdPPK"); // NOI18N
        FormInput.add(KdPPK);
        KdPPK.setBounds(470, 190, 75, 23);

        NmPPK.setEditable(false);
        NmPPK.setBackground(new java.awt.Color(245, 250, 240));
        NmPPK.setHighlighter(null);
        NmPPK.setName("NmPPK"); // NOI18N
        FormInput.add(NmPPK);
        NmPPK.setBounds(550, 190, 180, 23);

        btnPPKRujukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPPKRujukan.setMnemonic('X');
        btnPPKRujukan.setToolTipText("Alt+X");
        btnPPKRujukan.setName("btnPPKRujukan"); // NOI18N
        btnPPKRujukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPPKRujukanActionPerformed(evt);
            }
        });
        btnPPKRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPPKRujukanKeyPressed(evt);
            }
        });
        FormInput.add(btnPPKRujukan);
        btnPPKRujukan.setBounds(730, 130, 28, 23);

        jLabel10.setText("PPK Rujukan :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(375, 130, 90, 23);

        KdPpkRujukan.setEditable(false);
        KdPpkRujukan.setBackground(new java.awt.Color(245, 250, 240));
        KdPpkRujukan.setHighlighter(null);
        KdPpkRujukan.setName("KdPpkRujukan"); // NOI18N
        FormInput.add(KdPpkRujukan);
        KdPpkRujukan.setBounds(470, 130, 75, 23);

        NmPpkRujukan.setEditable(false);
        NmPpkRujukan.setBackground(new java.awt.Color(245, 250, 240));
        NmPpkRujukan.setHighlighter(null);
        NmPpkRujukan.setName("NmPpkRujukan"); // NOI18N
        FormInput.add(NmPpkRujukan);
        NmPpkRujukan.setBounds(550, 130, 180, 23);

        jLabel11.setText("Diagnosa Awal :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(0, 160, 90, 23);

        KdPenyakit.setEditable(false);
        KdPenyakit.setBackground(new java.awt.Color(245, 250, 240));
        KdPenyakit.setHighlighter(null);
        KdPenyakit.setName("KdPenyakit"); // NOI18N
        FormInput.add(KdPenyakit);
        KdPenyakit.setBounds(93, 160, 75, 23);

        NmPenyakit.setEditable(false);
        NmPenyakit.setBackground(new java.awt.Color(245, 250, 240));
        NmPenyakit.setHighlighter(null);
        NmPenyakit.setName("NmPenyakit"); // NOI18N
        FormInput.add(NmPenyakit);
        NmPenyakit.setBounds(170, 160, 180, 23);

        btnDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDiagnosa.setMnemonic('X');
        btnDiagnosa.setToolTipText("Alt+X");
        btnDiagnosa.setName("btnDiagnosa"); // NOI18N
        btnDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiagnosaActionPerformed(evt);
            }
        });
        btnDiagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDiagnosaKeyPressed(evt);
            }
        });
        FormInput.add(btnDiagnosa);
        btnDiagnosa.setBounds(350, 160, 28, 23);

        btnPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPoli.setMnemonic('X');
        btnPoli.setToolTipText("Alt+X");
        btnPoli.setName("btnPoli"); // NOI18N
        btnPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPoliActionPerformed(evt);
            }
        });
        btnPoli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPoliKeyPressed(evt);
            }
        });
        FormInput.add(btnPoli);
        btnPoli.setBounds(730, 160, 28, 23);

        NmPoli.setEditable(false);
        NmPoli.setBackground(new java.awt.Color(245, 250, 240));
        NmPoli.setHighlighter(null);
        NmPoli.setName("NmPoli"); // NOI18N
        FormInput.add(NmPoli);
        NmPoli.setBounds(550, 160, 180, 23);

        KdPoli.setEditable(false);
        KdPoli.setBackground(new java.awt.Color(245, 250, 240));
        KdPoli.setHighlighter(null);
        KdPoli.setName("KdPoli"); // NOI18N
        FormInput.add(KdPoli);
        KdPoli.setBounds(470, 160, 75, 23);

        LabelPoli.setText("Poli Tujuan :");
        LabelPoli.setName("LabelPoli"); // NOI18N
        FormInput.add(LabelPoli);
        LabelPoli.setBounds(375, 160, 90, 23);

        jLabel13.setText("Jns.Pelayanan :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(570, 100, 90, 23);

        jLabel14.setText("Catatan :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(385, 280, 80, 23);

        Catatan.setHighlighter(null);
        Catatan.setName("Catatan"); // NOI18N
        Catatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CatatanKeyPressed(evt);
            }
        });
        FormInput.add(Catatan);
        Catatan.setBounds(470, 280, 257, 23);

        JenisPelayanan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Ranap", "2. Ralan" }));
        JenisPelayanan.setSelectedIndex(1);
        JenisPelayanan.setName("JenisPelayanan"); // NOI18N
        JenisPelayanan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JenisPelayananItemStateChanged(evt);
            }
        });
        JenisPelayanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisPelayananKeyPressed(evt);
            }
        });
        FormInput.add(JenisPelayanan);
        JenisPelayanan.setBounds(660, 100, 100, 23);

        LabelKelas.setText("Kelas :");
        LabelKelas.setName("LabelKelas"); // NOI18N
        FormInput.add(LabelKelas);
        LabelKelas.setBounds(50, 220, 40, 23);

        Kelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Kelas 1", "2. Kelas 2", "3. Kelas 3" }));
        Kelas.setSelectedIndex(2);
        Kelas.setName("Kelas"); // NOI18N
        Kelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelasKeyPressed(evt);
            }
        });
        FormInput.add(Kelas);
        Kelas.setBounds(90, 220, 100, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 40, 90, 23);

        TglLahir.setEditable(false);
        TglLahir.setBackground(new java.awt.Color(245, 250, 240));
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(93, 40, 152, 23);

        jLabel18.setText("J.K.:");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(440, 40, 70, 23);

        JK.setEditable(false);
        JK.setBackground(new java.awt.Color(245, 250, 240));
        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N
        FormInput.add(JK);
        JK.setBounds(516, 40, 60, 23);

        jLabel24.setText("Peserta :");
        jLabel24.setName("jLabel24"); // NOI18N
        jLabel24.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel24);
        jLabel24.setBounds(250, 40, 60, 23);

        JenisPeserta.setEditable(false);
        JenisPeserta.setBackground(new java.awt.Color(245, 250, 240));
        JenisPeserta.setHighlighter(null);
        JenisPeserta.setName("JenisPeserta"); // NOI18N
        FormInput.add(JenisPeserta);
        JenisPeserta.setBounds(316, 40, 150, 23);

        jLabel25.setText("Status :");
        jLabel25.setName("jLabel25"); // NOI18N
        jLabel25.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel25);
        jLabel25.setBounds(270, 70, 40, 23);

        Status.setEditable(false);
        Status.setBackground(new java.awt.Color(245, 250, 240));
        Status.setHighlighter(null);
        Status.setName("Status"); // NOI18N
        FormInput.add(Status);
        Status.setBounds(316, 70, 150, 23);

        jLabel27.setText("Asal Rujukan :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(360, 100, 90, 23);

        AsalRujukan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Faskes 1", "2. Faskes 2(RS)" }));
        AsalRujukan.setName("AsalRujukan"); // NOI18N
        AsalRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsalRujukanKeyPressed(evt);
            }
        });
        FormInput.add(AsalRujukan);
        AsalRujukan.setBounds(450, 100, 130, 23);

        jLabel39.setText("No.SKDP :");
        jLabel39.setName("jLabel39"); // NOI18N
        jLabel39.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel39);
        jLabel39.setBounds(20, 130, 70, 23);

        NoSKDP.setHighlighter(null);
        NoSKDP.setName("NoSKDP"); // NOI18N
        NoSKDP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NoSKDPMouseClicked(evt);
            }
        });
        NoSKDP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoSKDPKeyPressed(evt);
            }
        });
        FormInput.add(NoSKDP);
        NoSKDP.setBounds(93, 130, 220, 23);

        btnSKDP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSKDP.setMnemonic('X');
        btnSKDP.setToolTipText("Alt+X");
        btnSKDP.setName("btnSKDP"); // NOI18N
        btnSKDP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSKDPActionPerformed(evt);
            }
        });
        FormInput.add(btnSKDP);
        btnSKDP.setBounds(320, 130, 28, 23);

        LabelPoli2.setText("Dokter DPJP :");
        LabelPoli2.setName("LabelPoli2"); // NOI18N
        FormInput.add(LabelPoli2);
        LabelPoli2.setBounds(0, 190, 90, 23);

        KdDPJP.setEditable(false);
        KdDPJP.setBackground(new java.awt.Color(245, 250, 240));
        KdDPJP.setHighlighter(null);
        KdDPJP.setName("KdDPJP"); // NOI18N
        FormInput.add(KdDPJP);
        KdDPJP.setBounds(93, 190, 75, 23);

        NmDPJP.setEditable(false);
        NmDPJP.setBackground(new java.awt.Color(245, 250, 240));
        NmDPJP.setHighlighter(null);
        NmDPJP.setName("NmDPJP"); // NOI18N
        FormInput.add(NmDPJP);
        NmDPJP.setBounds(170, 190, 180, 23);

        btnDPJP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDPJP.setMnemonic('X');
        btnDPJP.setToolTipText("Alt+X");
        btnDPJP.setName("btnDPJP"); // NOI18N
        btnDPJP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDPJPActionPerformed(evt);
            }
        });
        btnDPJP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDPJPKeyPressed(evt);
            }
        });
        FormInput.add(btnDPJP);
        btnDPJP.setBounds(350, 190, 28, 23);

        jLabel29.setText("No.Telp :");
        jLabel29.setName("jLabel29"); // NOI18N
        jLabel29.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel29);
        jLabel29.setBounds(586, 40, 58, 23);

        NoTelp.setHighlighter(null);
        NoTelp.setName("NoTelp"); // NOI18N
        NoTelp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoTelpKeyPressed(evt);
            }
        });
        FormInput.add(NoTelp);
        NoTelp.setBounds(650, 40, 100, 23);

        LabelKelas1.setText("COB :");
        LabelKelas1.setName("LabelKelas1"); // NOI18N
        FormInput.add(LabelKelas1);
        LabelKelas1.setBounds(230, 250, 40, 23);

        COB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak ", "1.Ya" }));
        COB.setName("COB"); // NOI18N
        COB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                COBKeyPressed(evt);
            }
        });
        FormInput.add(COB);
        COB.setBounds(270, 250, 100, 23);

        jLabel15.setText("Eksekutif :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(415, 250, 51, 23);

        Eksekutif.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak", "1.Ya" }));
        Eksekutif.setName("Eksekutif"); // NOI18N
        Eksekutif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EksekutifKeyPressed(evt);
            }
        });
        FormInput.add(Eksekutif);
        Eksekutif.setBounds(465, 250, 100, 23);

        Katarak.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak", "1.Ya" }));
        Katarak.setName("Katarak"); // NOI18N
        Katarak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KatarakKeyPressed(evt);
            }
        });
        FormInput.add(Katarak);
        Katarak.setBounds(650, 250, 100, 23);

        jLabel37.setText("Katarak :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(605, 250, 44, 23);

        jLabel16.setText("Laka Lantas :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 380, 87, 23);

        LakaLantas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Bukan KLL", "1. KLL dan Bukan KK", "2. KLL dan KK", "3. KK" }));
        LakaLantas.setName("LakaLantas"); // NOI18N
        LakaLantas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                LakaLantasItemStateChanged(evt);
            }
        });
        LakaLantas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LakaLantasKeyPressed(evt);
            }
        });
        FormInput.add(LakaLantas);
        LakaLantas.setBounds(90, 380, 140, 23);

        jLabel28.setText("Penjamin Laka :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(0, 440, 87, 23);

        ChkAsa.setText("ASABRI PT");
        ChkAsa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkAsa.setName("ChkAsa"); // NOI18N
        ChkAsa.setOpaque(false);
        ChkAsa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAsaActionPerformed(evt);
            }
        });
        FormInput.add(ChkAsa);
        ChkAsa.setBounds(200, 470, 140, 23);

        ChkJasaRaharja.setText("Jasa raharja PT");
        ChkJasaRaharja.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkJasaRaharja.setName("ChkJasaRaharja"); // NOI18N
        ChkJasaRaharja.setOpaque(false);
        ChkJasaRaharja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJasaRaharjaActionPerformed(evt);
            }
        });
        FormInput.add(ChkJasaRaharja);
        ChkJasaRaharja.setBounds(90, 440, 110, 23);

        ChkBPJSTenaga.setText("BPJS Ketenagakerjaan");
        ChkBPJSTenaga.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkBPJSTenaga.setName("ChkBPJSTenaga"); // NOI18N
        ChkBPJSTenaga.setOpaque(false);
        ChkBPJSTenaga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkBPJSTenagaActionPerformed(evt);
            }
        });
        FormInput.add(ChkBPJSTenaga);
        ChkBPJSTenaga.setBounds(200, 440, 140, 23);

        ChkTaspen.setText("TASPEN PT");
        ChkTaspen.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkTaspen.setName("ChkTaspen"); // NOI18N
        ChkTaspen.setOpaque(false);
        ChkTaspen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkTaspenActionPerformed(evt);
            }
        });
        FormInput.add(ChkTaspen);
        ChkTaspen.setBounds(90, 470, 80, 23);

        jLabel38.setText("Tgl.KLL :");
        jLabel38.setName("jLabel38"); // NOI18N
        jLabel38.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel38);
        jLabel38.setBounds(230, 380, 58, 23);

        TanggalKKL.setForeground(new java.awt.Color(50, 70, 50));
        TanggalKKL.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-08-2022" }));
        TanggalKKL.setDisplayFormat("dd-MM-yyyy");
        TanggalKKL.setName("TanggalKKL"); // NOI18N
        TanggalKKL.setOpaque(false);
        TanggalKKL.setPreferredSize(new java.awt.Dimension(95, 23));
        TanggalKKL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKKLKeyPressed(evt);
            }
        });
        FormInput.add(TanggalKKL);
        TanggalKKL.setBounds(290, 380, 95, 23);

        jLabel36.setText("Keterangan :");
        jLabel36.setName("jLabel36"); // NOI18N
        jLabel36.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel36);
        jLabel36.setBounds(385, 380, 87, 23);

        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormInput.add(Keterangan);
        Keterangan.setBounds(490, 380, 257, 23);

        jLabel40.setText("Suplesi :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(0, 410, 87, 23);

        Suplesi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak", "1.Ya" }));
        Suplesi.setName("Suplesi"); // NOI18N
        Suplesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuplesiKeyPressed(evt);
            }
        });
        FormInput.add(Suplesi);
        Suplesi.setBounds(90, 410, 100, 23);

        NoSEPSuplesi.setHighlighter(null);
        NoSEPSuplesi.setName("NoSEPSuplesi"); // NOI18N
        NoSEPSuplesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoSEPSuplesiKeyPressed(evt);
            }
        });
        FormInput.add(NoSEPSuplesi);
        NoSEPSuplesi.setBounds(290, 410, 95, 23);

        jLabel41.setText("SEP Suplesi :");
        jLabel41.setName("jLabel41"); // NOI18N
        jLabel41.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel41);
        jLabel41.setBounds(220, 410, 68, 23);

        LabelPoli3.setText("Propinsi KLL :");
        LabelPoli3.setName("LabelPoli3"); // NOI18N
        FormInput.add(LabelPoli3);
        LabelPoli3.setBounds(385, 410, 87, 23);

        KdPropinsi.setEditable(false);
        KdPropinsi.setBackground(new java.awt.Color(245, 250, 240));
        KdPropinsi.setHighlighter(null);
        KdPropinsi.setName("KdPropinsi"); // NOI18N
        FormInput.add(KdPropinsi);
        KdPropinsi.setBounds(490, 410, 75, 23);

        NmPropinsi.setEditable(false);
        NmPropinsi.setBackground(new java.awt.Color(245, 250, 240));
        NmPropinsi.setHighlighter(null);
        NmPropinsi.setName("NmPropinsi"); // NOI18N
        FormInput.add(NmPropinsi);
        NmPropinsi.setBounds(570, 410, 180, 23);

        btnPropinsi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPropinsi.setMnemonic('X');
        btnPropinsi.setToolTipText("Alt+X");
        btnPropinsi.setName("btnPropinsi"); // NOI18N
        btnPropinsi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPropinsiActionPerformed(evt);
            }
        });
        btnPropinsi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPropinsiKeyPressed(evt);
            }
        });
        FormInput.add(btnPropinsi);
        btnPropinsi.setBounds(750, 410, 28, 23);

        LabelPoli4.setText("Kabupaten KLL :");
        LabelPoli4.setName("LabelPoli4"); // NOI18N
        FormInput.add(LabelPoli4);
        LabelPoli4.setBounds(385, 440, 87, 23);

        KdKabupaten.setEditable(false);
        KdKabupaten.setBackground(new java.awt.Color(245, 250, 240));
        KdKabupaten.setHighlighter(null);
        KdKabupaten.setName("KdKabupaten"); // NOI18N
        FormInput.add(KdKabupaten);
        KdKabupaten.setBounds(490, 440, 75, 23);

        NmKabupaten.setEditable(false);
        NmKabupaten.setBackground(new java.awt.Color(245, 250, 240));
        NmKabupaten.setHighlighter(null);
        NmKabupaten.setName("NmKabupaten"); // NOI18N
        FormInput.add(NmKabupaten);
        NmKabupaten.setBounds(570, 440, 180, 23);

        btnKabupaten.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKabupaten.setMnemonic('X');
        btnKabupaten.setToolTipText("Alt+X");
        btnKabupaten.setName("btnKabupaten"); // NOI18N
        btnKabupaten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKabupatenActionPerformed(evt);
            }
        });
        btnKabupaten.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnKabupatenKeyPressed(evt);
            }
        });
        FormInput.add(btnKabupaten);
        btnKabupaten.setBounds(750, 440, 28, 23);

        LabelPoli5.setText("Kecamatan KLL :");
        LabelPoli5.setName("LabelPoli5"); // NOI18N
        FormInput.add(LabelPoli5);
        LabelPoli5.setBounds(385, 470, 87, 23);

        KdKecamatan.setEditable(false);
        KdKecamatan.setBackground(new java.awt.Color(245, 250, 240));
        KdKecamatan.setHighlighter(null);
        KdKecamatan.setName("KdKecamatan"); // NOI18N
        FormInput.add(KdKecamatan);
        KdKecamatan.setBounds(490, 470, 75, 23);

        NmKecamatan.setEditable(false);
        NmKecamatan.setBackground(new java.awt.Color(245, 250, 240));
        NmKecamatan.setHighlighter(null);
        NmKecamatan.setName("NmKecamatan"); // NOI18N
        FormInput.add(NmKecamatan);
        NmKecamatan.setBounds(570, 470, 180, 23);

        btnKecamatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKecamatan.setMnemonic('X');
        btnKecamatan.setToolTipText("Alt+X");
        btnKecamatan.setName("btnKecamatan"); // NOI18N
        btnKecamatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKecamatanActionPerformed(evt);
            }
        });
        btnKecamatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnKecamatanKeyPressed(evt);
            }
        });
        FormInput.add(btnKecamatan);
        btnKecamatan.setBounds(750, 470, 28, 23);

        btnRujukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnRujukan.setMnemonic('X');
        btnRujukan.setToolTipText("Alt+X");
        btnRujukan.setName("btnRujukan"); // NOI18N
        btnRujukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRujukanActionPerformed(evt);
            }
        });
        btnRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnRujukanKeyPressed(evt);
            }
        });
        FormInput.add(btnRujukan);
        btnRujukan.setBounds(730, 70, 28, 23);

        LabelKelas2.setText("Naik Kelas :");
        LabelKelas2.setName("LabelKelas2"); // NOI18N
        FormInput.add(LabelKelas2);
        LabelKelas2.setBounds(200, 220, 70, 23);

        Kelas1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "1. VVIP", "2. VIP", "3. Kelas I", "4. Kelas II", "5. Kelas III", "6. ICCU", "7. ICU" }));
        Kelas1.setName("Kelas1"); // NOI18N
        FormInput.add(Kelas1);
        Kelas1.setBounds(270, 220, 100, 23);

        lblPembiayaan.setText("Pembiayaan :");
        lblPembiayaan.setName("lblPembiayaan"); // NOI18N
        FormInput.add(lblPembiayaan);
        lblPembiayaan.setBounds(395, 220, 70, 23);

        cmbPembiayaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "1. Pribadi", "2. Pemberi Kerja", "3. Asuransi Kesehatan Tambahan" }));
        cmbPembiayaan.setName("cmbPembiayaan"); // NOI18N
        cmbPembiayaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPembiayaanActionPerformed(evt);
            }
        });
        FormInput.add(cmbPembiayaan);
        cmbPembiayaan.setBounds(465, 220, 100, 23);

        tjKunjungan.setText("Kunjungan :");
        tjKunjungan.setName("tjKunjungan"); // NOI18N
        FormInput.add(tjKunjungan);
        tjKunjungan.setBounds(10, 250, 80, 23);

        cmbKunjungan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Normal", "1. Prosedur", "2. Konsul Dokter" }));
        cmbKunjungan.setName("cmbKunjungan"); // NOI18N
        cmbKunjungan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbKunjunganItemStateChanged(evt);
            }
        });
        FormInput.add(cmbKunjungan);
        cmbKunjungan.setBounds(90, 250, 140, 23);

        lblProcedure.setText("Prosedur :");
        lblProcedure.setName("lblProcedure"); // NOI18N
        FormInput.add(lblProcedure);
        lblProcedure.setBounds(10, 280, 80, 23);

        cmbProcedure.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "0. Prosedur Tidak Berkelanjutan", "1. Prosedur dan Terapi Berkelanjutan" }));
        cmbProcedure.setName("cmbProcedure"); // NOI18N
        FormInput.add(cmbProcedure);
        cmbProcedure.setBounds(90, 280, 280, 23);

        lblPenunjang.setText("Penunjang :");
        lblPenunjang.setName("lblPenunjang"); // NOI18N
        FormInput.add(lblPenunjang);
        lblPenunjang.setBounds(10, 310, 80, 23);

        cmbPenunjang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "1. Radioterapi", "2. Kemoterapi", "3. Rehabilitasi Medik", "4. Rehabilitasi Psikososial", "5. Transfusi Darah", "6. Pelayanan Gigi", "7. Laboratorium", "8. USG", "9. Farmasi", "10. Lain-Lain", "11. MRI", "12. HEMODIALISA" }));
        cmbPenunjang.setName("cmbPenunjang"); // NOI18N
        FormInput.add(cmbPenunjang);
        cmbPenunjang.setBounds(90, 310, 280, 23);

        cmbAsesment.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "1. Poli spesialis tidak tersedia pada hari sebelumnya", "2. Jam Poli telah berakhir pada hari sebelumnya", "3. Dokter Spesialis yang dimaksud tidak praktek pada hari sebelumnya", "4. Atas Instruksi RS", "5. Tujuan Kontrol" }));
        cmbAsesment.setName("cmbAsesment"); // NOI18N
        FormInput.add(cmbAsesment);
        cmbAsesment.setBounds(90, 340, 280, 23);

        lblAsesment.setText("Asesmen :");
        lblAsesment.setName("lblAsesment"); // NOI18N
        FormInput.add(lblAsesment);
        lblAsesment.setBounds(10, 340, 80, 23);

        pnj.setEditable(false);
        pnj.setHighlighter(null);
        pnj.setName("pnj"); // NOI18N
        FormInput.add(pnj);
        pnj.setBounds(570, 220, 180, 23);

        btnSPRI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSPRI.setMnemonic('X');
        btnSPRI.setToolTipText("Alt+X");
        btnSPRI.setName("btnSPRI"); // NOI18N
        btnSPRI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSPRIActionPerformed(evt);
            }
        });
        FormInput.add(btnSPRI);
        btnSPRI.setBounds(352, 130, 28, 23);

        KdDPJPLayan.setEditable(false);
        KdDPJPLayan.setBackground(new java.awt.Color(245, 250, 240));
        KdDPJPLayan.setHighlighter(null);
        KdDPJPLayan.setName("KdDPJPLayan"); // NOI18N
        FormInput.add(KdDPJPLayan);
        KdDPJPLayan.setBounds(470, 310, 75, 23);

        LabelPoli6.setText("DPJP Layan :");
        LabelPoli6.setName("LabelPoli6"); // NOI18N
        FormInput.add(LabelPoli6);
        LabelPoli6.setBounds(385, 310, 80, 23);

        NmDPJPLayan.setEditable(false);
        NmDPJPLayan.setBackground(new java.awt.Color(245, 250, 240));
        NmDPJPLayan.setHighlighter(null);
        NmDPJPLayan.setName("NmDPJPLayan"); // NOI18N
        FormInput.add(NmDPJPLayan);
        NmDPJPLayan.setBounds(550, 310, 180, 23);

        btnDPJP1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDPJP1.setMnemonic('X');
        btnDPJP1.setToolTipText("Alt+X");
        btnDPJP1.setName("btnDPJP1"); // NOI18N
        btnDPJP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDPJP1ActionPerformed(evt);
            }
        });
        btnDPJP1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDPJP1KeyPressed(evt);
            }
        });
        FormInput.add(btnDPJP1);
        btnDPJP1.setBounds(730, 310, 28, 23);

        Scroll1.setViewportView(FormInput);

        internalFrame2.add(Scroll1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input SEP", internalFrame2);

        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setComponentPopupMenu(Popup1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setAutoCreateRowSorter(true);
        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(Popup);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame4.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setBorder(null);
        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl. SEP :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-08-2022" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-08-2022" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(205, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelGlass9.add(BtnCari);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(LCount);

        jLabel48.setText("Limit Data :");
        jLabel48.setName("jLabel48"); // NOI18N
        jLabel48.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel48);

        cmbHlm.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "3", "5", "7", "10", "15" }));
        cmbHlm.setSelectedIndex(1);
        cmbHlm.setName("cmbHlm"); // NOI18N
        cmbHlm.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(cmbHlm);

        internalFrame4.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        TabRawat.addTab("Data SEP", internalFrame4);

        internalFrame12.setBorder(null);
        internalFrame12.setName("internalFrame12"); // NOI18N
        internalFrame12.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll4.setComponentPopupMenu(Popup1);
        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);

        tbObat1.setAutoCreateRowSorter(true);
        tbObat1.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat1.setComponentPopupMenu(Popup3);
        tbObat1.setName("tbObat1"); // NOI18N
        tbObat1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObat1MouseClicked(evt);
            }
        });
        tbObat1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObat1KeyPressed(evt);
            }
        });
        Scroll4.setViewportView(tbObat1);

        internalFrame12.add(Scroll4, java.awt.BorderLayout.CENTER);

        panelGlass10.setBorder(null);
        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel44.setText("Tgl. SEP :");
        jLabel44.setName("jLabel44"); // NOI18N
        jLabel44.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass10.add(jLabel44);

        DTPCari3.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-08-2022" }));
        DTPCari3.setDisplayFormat("dd-MM-yyyy");
        DTPCari3.setName("DTPCari3"); // NOI18N
        DTPCari3.setOpaque(false);
        DTPCari3.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass10.add(DTPCari3);

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("s.d.");
        jLabel45.setName("jLabel45"); // NOI18N
        jLabel45.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass10.add(jLabel45);

        DTPCari4.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-08-2022" }));
        DTPCari4.setDisplayFormat("dd-MM-yyyy");
        DTPCari4.setName("DTPCari4"); // NOI18N
        DTPCari4.setOpaque(false);
        DTPCari4.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass10.add(DTPCari4);

        jLabel46.setText("Key Word :");
        jLabel46.setName("jLabel46"); // NOI18N
        jLabel46.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass10.add(jLabel46);

        TCari1.setName("TCari1"); // NOI18N
        TCari1.setPreferredSize(new java.awt.Dimension(205, 23));
        TCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCari1KeyPressed(evt);
            }
        });
        panelGlass10.add(TCari1);

        BtnCari2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari2.setMnemonic('3');
        BtnCari2.setToolTipText("Alt+3");
        BtnCari2.setName("BtnCari2"); // NOI18N
        BtnCari2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari2ActionPerformed(evt);
            }
        });
        BtnCari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari2KeyPressed(evt);
            }
        });
        panelGlass10.add(BtnCari2);

        jLabel47.setText("Record :");
        jLabel47.setName("jLabel47"); // NOI18N
        jLabel47.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass10.add(jLabel47);

        LCount1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount1.setText("0");
        LCount1.setName("LCount1"); // NOI18N
        LCount1.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass10.add(LCount1);

        internalFrame12.add(panelGlass10, java.awt.BorderLayout.PAGE_END);

        TabRawat.addTab("Data SEP Internal", internalFrame12);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnBatal);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnHapus);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnPrint);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnAll);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnKeluar);

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
//        antrian = NoSKDP.getText() == null ? "" : NoSKDP.getText();
        String nomer = "";
        if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Pasien");
        } else if (NoKartu.getText().trim().equals("")) {
            Valid.textKosong(NoKartu, "Nomor Kartu");
        } else if (KdPpkRujukan.getText().trim().equals("") || NmPpkRujukan.getText().trim().equals("")) {
            Valid.textKosong(KdPpkRujukan, "PPK Rujukan");
        } else if (KdPPK.getText().trim().equals("") || NmPPK.getText().trim().equals("")) {
            Valid.textKosong(KdPPK, "PPK Pelayanan");
        } else if (KdPenyakit.getText().trim().equals("") || NmPenyakit.getText().trim().equals("")) {
            Valid.textKosong(KdPenyakit, "Diagnosa");
        } else if (Catatan.getText().trim().equals("")) {
            Valid.textKosong(Catatan, "Catatan");
        } else if ((JenisPelayanan.getSelectedIndex() == 1) && (KdPoli.getText().trim().equals("") || NmPoli.getText().trim().equals(""))) {
            Valid.textKosong(KdPoli, "Poli Tujuan");
        } else if ((LakaLantas.getSelectedIndex() == 1) && Keterangan.getText().equals("")) {
            Valid.textKosong(Keterangan, "Keterangan");
        } else if ((LakaLantas.getSelectedIndex() == 1) && NmPropinsi.getText().equals("")) {
            Valid.textKosong(btnPropinsi, "Propinsi");
        } else if ((LakaLantas.getSelectedIndex() == 1) && NmKabupaten.getText().equals("")) {
            Valid.textKosong(btnKabupaten, "Kabupaten");
        } else if ((LakaLantas.getSelectedIndex() == 1) && NmKecamatan.getText().equals("")) {
            Valid.textKosong(btnKecamatan, "Kecamatan");
        } else if (KdDPJPLayan.getText().trim().equals("") || NmDPJPLayan.getText().trim().equals("")) {
            Valid.textKosong(KdDPJPLayan, "DPJP");
        } else {
            if (JenisPelayanan.getSelectedIndex() == 0) {
                insertSEP();
            } else if (JenisPelayanan.getSelectedIndex() == 1) {
                if (NmPoli.getText().toLowerCase().contains("darurat")) {
                    if (Sequel.cariInteger("select count(no_kartu) from bridging_sep where "
                            + "no_kartu='" + no_peserta + "' and jnspelayanan='" + JenisPelayanan.getSelectedItem().toString().substring(0, 1) + "' "
                            + "and tglsep like '%" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "%' and "
                            + "nmpolitujuan like '%darurat%'") >= 3) {
                        JOptionPane.showMessageDialog(null, "Maaf, sebelumnya sudah dilakukan 3x pembuatan SEP di jenis pelayanan yang sama..!!");
                        TCari.requestFocus();
                    } else {
                        insertSEP();
                    }
                } else if (!NmPoli.getText().toLowerCase().contains("darurat")) {
                    if (Sequel.cariInteger("select count(no_kartu) from bridging_sep where "
                            + "no_kartu='" + no_peserta + "' and jnspelayanan='" + JenisPelayanan.getSelectedItem().toString().substring(0, 1) + "' "
                            + "and tglsep like '%" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "%' and kdpolitujuan = '" + KdPoli.getText() + "' AND "
                            + "nmpolitujuan not like '%darurat%'") >= 1) {
                        JOptionPane.showMessageDialog(null, "Maaf, sebelumnya sudah dilakukan pembuatan SEP di jenis pelayanan yang sama..!!");
                        TCari.requestFocus();
                    } else {
                        insertSEP();
                    }
                }
            }

        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, btnKecamatan, BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            emptTeks();
        } else {
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if (tbObat.getSelectedRow() != -1) {
            try {
                bodyWithDeleteRequest();
            } catch (Exception ex) {
                System.out.println("Notifikasi Bridging : " + ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Silahkan pilih dulu data yang mau dihapus..!!");
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if (tbObat.getSelectedRow() != -1) {
            if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
                Valid.textKosong(TNoRw, "Pasien");
            } else if (NoKartu.getText().trim().equals("")) {
                Valid.textKosong(NoKartu, "Nomor Kartu");
            } else if (NoRujukan.getText().trim().equals("")) {
                Valid.textKosong(NoRujukan, "Nomor Rujukan");
            } else if (KdPpkRujukan.getText().trim().equals("") || NmPpkRujukan.getText().trim().equals("")) {
                Valid.textKosong(KdPpkRujukan, "PPK Rujukan");
            } else if (KdPPK.getText().trim().equals("") || NmPPK.getText().trim().equals("")) {
                Valid.textKosong(KdPPK, "PPK Pelayanan");
            } else if (KdPenyakit.getText().trim().equals("") || NmPenyakit.getText().trim().equals("")) {
                Valid.textKosong(KdPenyakit, "Diagnosa");
            } else if (Catatan.getText().trim().equals("")) {
                Valid.textKosong(Catatan, "Catatan");
            } else if ((LakaLantas.getSelectedIndex() == 1) && Keterangan.getText().equals("")) {
                Valid.textKosong(Keterangan, "Keterangan");
            } else if ((LakaLantas.getSelectedIndex() == 1) && NmPropinsi.getText().equals("")) {
                Valid.textKosong(btnPropinsi, "Propinsi");
            } else if ((LakaLantas.getSelectedIndex() == 1) && NmKabupaten.getText().equals("")) {
                Valid.textKosong(btnKabupaten, "Kabupaten");
            } else if ((LakaLantas.getSelectedIndex() == 1) && NmKecamatan.getText().equals("")) {
                Valid.textKosong(btnKecamatan, "Kecamatan");
            } else if (KdDPJP.getText().trim().equals("") || NmDPJP.getText().trim().equals("")) {
                Valid.textKosong(KdDPJP, "DPJP");
            } else if (NoSKDP.getText().trim().equals("")) {
                Valid.textKosong(NoSKDP, "No.SKDP");
            } else {
                try {
                    jasaraharja = "";
                    BPJS = "";
                    Taspen = "";
                    Asabri = "";
                    penjamin = "";
                    String penunjang = "";
                    if (ChkJasaRaharja.isSelected() == true) {
                        jasaraharja = "1,";
                    }
                    if (ChkBPJSTenaga.isSelected() == true) {
                        BPJS = "2,";
                    }
                    if (ChkTaspen.isSelected() == true) {
                        Taspen = "3,";
                    }
                    if (ChkAsa.isSelected() == true) {
                        Asabri = "4,";
                    }

                    if ((ChkJasaRaharja.isSelected() == true) || (ChkBPJSTenaga.isSelected() == true) || (ChkTaspen.isSelected() == true) || (ChkAsa.isSelected() == true)) {
                        penjamin = jasaraharja + BPJS + Taspen + Asabri + penjamin;
                    } else {
                        penjamin = "";
                    }

                    if (penjamin.endsWith(",")) {
                        penjamin = penjamin.substring(0, penjamin.length() - 1);
                    }

                    tglkkl = "0000-00-00";
                    if (LakaLantas.getSelectedIndex() == 1) {
                        tglkkl = Valid.SetTgl(TanggalKKL.getSelectedItem() + "");
                    }
                    if (cmbPenunjang.getSelectedIndex() == 0) {
                        penunjang = "";
                    } else if (cmbPenunjang.getSelectedIndex() > 9) {
                        penunjang = cmbPenunjang.getSelectedItem().toString().substring(0, 2);
                    } else {
                        penunjang = cmbPenunjang.getSelectedItem().toString().substring(0, 1);
                    };
                    klsRawat = "\"klsRawat\": {"
                            + "\"klsRawatHak\":\"" + Kelas.getSelectedItem().toString().substring(0, 1) + "\","
                            + "\"klsRawatNaik\":\"" + (Kelas1.getSelectedIndex() > 0 ? Kelas1.getSelectedItem().toString().substring(0, 1) : "") + "\","
                            + "\"pembiayaan\":\"" + (cmbPembiayaan.getSelectedIndex() > 0 ? cmbPembiayaan.getSelectedItem().toString().substring(0, 1) : "") + "\","
                            + "\"penanggungJawab\":\"" + (pnj.getText().equals("") ? "" : pnj.getText()) + "\""
                            + "},";
                    if (JenisPelayanan.getSelectedIndex() == 1) {
                        dpjlayan = "\"dpjpLayan\":\"" + KdDPJP.getText() + "\",";
                    } else {
                        dpjlayan = "\"dpjpLayan\":\"\",";
                    }

                    penjaminan = "";
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    headers.add("X-Cons-ID", koneksiDB.ConsIdBpjs());
                    headers.add("X-Timestamp", String.valueOf(api.GetUTCdatetimeAsString()));
                    headers.add("X-Signature", api.getHmac());
                    headers.add("user_key", koneksiDB.UserKeyBpjs());
                    URL = koneksiDB.UrlBpjs() + "/SEP/2.0/update";
//                    URL = link + "/SEP/1.1/Update";
                    requestJson = "{"
                            + "\"request\":"
                            + "{"
                            + "\"t_sep\":"
                            + "{"
                            + "\"noSep\":\"" + tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString() + "\","
                            + klsRawat
                            + "\"noMR\":\"" + TNoRM.getText() + "\","
                            + "\"catatan\":\"" + Catatan.getText() + "\","
                            + "\"diagAwal\":\"" + KdPenyakit.getText() + "\","
                            + "\"poli\": {"
                            + "\"tujuan\": \"" + KdPoli.getText() + "\","
                            + "\"eksekutif\": \"" + Eksekutif.getSelectedItem().toString().substring(0, 1) + "\""
                            + "},"
                            + "\"cob\": {"
                            + "\"cob\": \"" + COB.getSelectedItem().toString().substring(0, 1) + "\""
                            + "},"
                            + "\"katarak\": {"
                            + "\"katarak\": \"" + Katarak.getSelectedItem().toString().substring(0, 1) + "\""
                            + "},"
                            + "\"jaminan\": {"
                            + "\"lakaLantas\":\"" + LakaLantas.getSelectedItem().toString().substring(0, 1) + "\","
                            + "\"penjamin\": {"
                            + penjaminan
                            + "\"tglKejadian\": \"" + tglkkl + "\","
                            + "\"keterangan\": \"" + Keterangan.getText() + "\","
                            + "\"suplesi\": {"
                            + "\"suplesi\": \"" + Suplesi.getSelectedItem().toString().substring(0, 1) + "\","
                            + "\"noSepSuplesi\": \"" + NoSEPSuplesi.getText() + "\","
                            + "\"lokasiLaka\": {"
                            + "\"kdPropinsi\": \"" + KdPropinsi.getText() + "\","
                            + "\"kdKabupaten\": \"" + KdKabupaten.getText() + "\","
                            + "\"kdKecamatan\": \"" + KdKecamatan.getText() + "\""
                            + "}"
                            + "}"
                            + "}"
                            + "},"
                            + dpjlayan
                            + "\"noTelp\": \"" + NoTelp.getText() + "\","
                            + "\"user\":\"" + user + "\""
                            + "}"
                            + "}"
                            + "}";
                    requestEntity = new HttpEntity(requestJson, headers);
                    root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.PUT, requestEntity, String.class).getBody());
                    nameNode = root.path("metaData");
                    System.out.println("code : " + nameNode.path("code").asText());
                    System.out.println("message : " + nameNode.path("message").asText());
                    if (koneksiDB.UrlBpjs().contains("apijkn")) {
                        JsonNode res1 = root.path("response");
                        String res = api.decrypt(res1.asText());
                        String lz = api.lzDecrypt(res);
                        response = mapper.readTree(lz);
                    } else {
                        response = root.path("response");
                    }
                    if (nameNode.path("code").asText().equals("200")) {
                        Sequel.mengedit("bridging_sep",
                                "no_sep=?", "no_sep=?,no_rawat=?,tglrujukan=?,no_rujukan=?,kdppkrujukan=?,"
                                + "nmppkrujukan=?,kdppkpelayanan=?,nmppkpelayanan=?,catatan=?,diagawal=?,"
                                + "nmdiagnosaawal=?,klsrawat=?,lakalantas=?,user=?,nomr=?,nama_pasien=?,"
                                + "tanggal_lahir=?,peserta=?,jkel=?,no_kartu=?,asal_rujukan=?,eksekutif=?,"
                                + "cob=?,penjamin=?,notelep=?,katarak=?,tglkkl=?,keterangankkl=?,suplesi=?,"
                                + "no_sep_suplesi=?,kdprop=?,nmprop=?,kdkab=?,nmkab=?,kdkec=?,nmkec=?,noskdp=?,"
                                + "kddpjp=?,nmdpdjp=?", 40, new String[]{
                                    response.asText(), TNoRw.getText(), Valid.SetTgl(TanggalRujuk.getSelectedItem() + ""),
                                    NoRujukan.getText(), KdPpkRujukan.getText(), NmPpkRujukan.getText(), KdPPK.getText(),
                                    NmPPK.getText(), Catatan.getText(), KdPenyakit.getText(),
                                    NmPenyakit.getText(), Kelas.getSelectedItem().toString().substring(0, 1),
                                    LakaLantas.getSelectedItem().toString().substring(0, 1), user,
                                    TNoRM.getText(), TPasien.getText(), TglLahir.getText(), JenisPeserta.getText(), JK.getText(), NoKartu.getText(),
                                    AsalRujukan.getSelectedItem().toString(), Eksekutif.getSelectedItem().toString(),
                                    COB.getSelectedItem().toString(), penjamin, NoTelp.getText(), Katarak.getSelectedItem().toString(),
                                    tglkkl, Keterangan.getText(), Suplesi.getSelectedItem().toString(),
                                    NoSEPSuplesi.getText(), KdPropinsi.getText(), NmPropinsi.getText(), KdKabupaten.getText(), NmKabupaten.getText(),
                                    KdKecamatan.getText(), NmKecamatan.getText(), NoSKDP.getText(), KdDPJP.getText(), NmDPJP.getText(),
                                    tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()
                                });
                        Sequel.mengedit("rujuk_masuk", "no_rawat=?", "no_rawat=?,perujuk=?,no_rujuk=?", 4, new String[]{
                            TNoRw.getText(), NmPpkRujukan.getText(), NoRujukan.getText(),
                            tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString()
                        });
                        emptTeks();
                        TabRawat.setSelectedIndex(1);
                        tampil();
                    } else {
                        JOptionPane.showMessageDialog(null, nameNode.path("message").asText());
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi Bridging : " + ex);
                    if (ex.toString().contains("UnknownHostException")) {
                        JOptionPane.showMessageDialog(null, "Koneksi ke server BPJS terputus...!");
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Silahkan pilih dulu data yang mau diganti..!!");
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        WindowRujukan.dispose();
        WindowUpdatePulang.dispose();
        WindowCariSEP.dispose();
        WindowRujukKhusus.dispose();
        faskes.dispose();
        penyakit.dispose();
        skdp.dispose();
        propinsi.dispose();
        kabupaten.dispose();
        kecamatan.dispose();
        dokter.dispose();
        poli.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnKeluarActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, TCari);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptBridgingDaftar.jrxml", "report", "::[ Data Bridging SEP ]::",
                    "select bridging_sep.no_sep, bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tglsep,"
                    + "bridging_sep.tglrujukan,bridging_sep.no_rujukan,bridging_sep.kdppkrujukan,"
                    + "bridging_sep.nmppkrujukan,bridging_sep.kdppkpelayanan,bridging_sep.nmppkpelayanan,"
                    + "if(bridging_sep.jnspelayanan='1','1. Ranap','2. Ralan'),bridging_sep.catatan,bridging_sep.diagawal,"
                    + "bridging_sep.nmdiagnosaawal,bridging_sep.kdpolitujuan,bridging_sep.nmpolitujuan,"
                    + "if(bridging_sep.klsrawat='1','1. Kelas 1',if(bridging_sep.klsrawat='2','2. Kelas 2','3. Kelas 3')),"
                    + "if(bridging_sep.lakalantas='0','0. Tidak','1. Ya'),bridging_sep.user, "
                    + "bridging_sep.tanggal_lahir,bridging_sep.peserta,bridging_sep.jkel,bridging_sep.no_kartu,bridging_sep.tglpulang,"
                    + "bridging_sep.asal_rujukan,bridging_sep.eksekutif,bridging_sep.cob,bridging_sep.penjamin,bridging_sep.notelep,"
                    + "bridging_sep.katarak,bridging_sep.tglkkl,bridging_sep.keterangankkl,"
                    + "bridging_sep.suplesi,bridging_sep.no_sep_suplesi,bridging_sep.kdprop,"
                    + "bridging_sep.nmprop,bridging_sep.kdkab,"
                    + "bridging_sep.nmkab,bridging_sep.kdkec,bridging_sep.nmkec,bridging_sep.noskdp,"
                    + "bridging_sep.kddpjp,bridging_sep.nmdpdjp from bridging_sep where "
                    + "bridging_sep.tglsep between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and bridging_sep.nomr like '%" + TCari.getText().trim() + "%' or "
                    + "bridging_sep.tglsep between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and bridging_sep.nama_pasien like '%" + TCari.getText().trim() + "%' or "
                    + "bridging_sep.tglsep between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and bridging_sep.nmppkrujukan like '%" + TCari.getText().trim() + "%' or "
                    + "bridging_sep.tglsep between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and bridging_sep.diagawal like '%" + TCari.getText().trim() + "%' or "
                    + "bridging_sep.tglsep between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and bridging_sep.nmdiagnosaawal like '%" + TCari.getText().trim() + "%' or "
                    + "bridging_sep.tglsep between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and bridging_sep.no_rawat like '%" + TCari.getText().trim() + "%' or "
                    + "bridging_sep.tglsep between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and bridging_sep.nmpolitujuan like '%" + TCari.getText().trim() + "%' order by bridging_sep.tglsep", param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        if (TCari.getText().equals("")) {
            tampil();
//            tampil_internal();
        } else if (TCari.getText().length() == 6) {
            tampilPasien();
//            tampil_internal();
        } else {
            tampil();
        }
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
//        tampil_internal();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            TCari.setText("");
            tampil();
//            tampil_internal();
        } else {
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
//                getData();
            } catch (java.lang.NullPointerException e) {
            }
            if (evt.getClickCount() == 2) {
                i = tbObat.getSelectedColumn();
                if (i == 0) {
                    ppSEPBtnPrintActionPerformed(null);
                } else if (i == 1) {
                    ppPulangBtnPrintActionPerformed(null);
                } else if (i == 2) {
                    ppDetailSEPPesertaBtnPrintActionPerformed(null);
                } else {
                    TabRawat.setSelectedIndex(0);
                    getData();
                }
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
                i = tbObat.getSelectedColumn();
                if (i == 0) {
                    ppSEPBtnPrintActionPerformed(null);
                } else if (i == 1) {
                    ppPulangBtnPrintActionPerformed(null);
                } else if (i == 2) {
                    ppDetailSEPPesertaBtnPrintActionPerformed(null);
                }
            }

        }
}//GEN-LAST:event_tbObatKeyPressed

    private void NoRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRujukanKeyPressed
        Valid.pindah(evt, TCari, TanggalRujuk);
    }//GEN-LAST:event_NoRujukanKeyPressed

    private void btnPPKRujukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPPKRujukanActionPerformed
        pilihan = 1;
        faskes.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        faskes.setLocationRelativeTo(internalFrame1);
        faskes.setVisible(true);
    }//GEN-LAST:event_btnPPKRujukanActionPerformed

    private void btnPPKRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPPKRujukanKeyPressed
        Valid.pindah(evt, NoSKDP, btnDiagnosa);
    }//GEN-LAST:event_btnPPKRujukanKeyPressed

    private void btnDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiagnosaActionPerformed
        pilihan = 1;
        penyakit.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        penyakit.setLocationRelativeTo(internalFrame1);
        penyakit.setVisible(true);
    }//GEN-LAST:event_btnDiagnosaActionPerformed

    private void btnDiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDiagnosaKeyPressed
        Valid.pindah(evt, btnPPKRujukan, btnPoli);
    }//GEN-LAST:event_btnDiagnosaKeyPressed

    private void btnPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoliActionPerformed
        pilihan = 1;
        poli.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setVisible(true);
    }//GEN-LAST:event_btnPoliActionPerformed

    private void btnPoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPoliKeyPressed
        Valid.pindah(evt, btnDiagnosa, btnDPJP);
    }//GEN-LAST:event_btnPoliKeyPressed

    private void CatatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CatatanKeyPressed
        Valid.pindah(evt, COB, Katarak);
    }//GEN-LAST:event_CatatanKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if (var.getform().equals("DlgReg") || var.getform().equals("DlgIGD") || var.getform().equals("DlgKamarInap")) {
            no_peserta = Sequel.cariIsi("select no_peserta from pasien where no_rkm_medis=?", TNoRM.getText());
            if (no_peserta.trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Pasien tidak mempunyai kepesertaan BPJS");
                dispose();
            } else {
                cekViaBPJSKartu.tampil(no_peserta);
                if (cekViaBPJSKartu.informasi.equals("OK")) {
                    if (cekViaBPJSKartu.statusPesertaketerangan.equals("AKTIF")) {
                        TPasien.setText(cekViaBPJSKartu.nama);
                        TglLahir.setText(cekViaBPJSKartu.tglLahir);
                        JK.setText(cekViaBPJSKartu.sex);
                        NoKartu.setText(no_peserta);
                        JenisPeserta.setText(cekViaBPJSKartu.jenisPesertaketerangan);
                        Status.setText(cekViaBPJSKartu.statusPesertaketerangan);
                        KdPpkRujukan.setText(cekViaBPJSKartu.provUmumkdProvider);
                        kdppkrujukan = cekViaBPJSKartu.provUmumkdProvider;
                        nmppkrujukan = cekViaBPJSKartu.provUmumnmProvider;
                        NmPpkRujukan.setText(cekViaBPJSKartu.provUmumnmProvider);
                        if (cekViaBPJSKartu.hakKelaskode.equals("1")) {
                            Kelas.setSelectedIndex(0);
                        } else if (cekViaBPJSKartu.hakKelaskode.equals("2")) {
                            Kelas.setSelectedIndex(1);
                        } else if (cekViaBPJSKartu.hakKelaskode.equals("3")) {
                            Kelas.setSelectedIndex(2);
                        }

                        if (cekViaBPJSKartu.mrnoTelepon == null) {
                            NoTelp.setText(cekViaBPJSKartu.mrnoTelepon);
                        } else {
                            Sequel.cariIsi("select no_tlp from pasien where no_rkm_medis=? ", NoTelp, TNoRM.getText());
                        }
                        prb = cekViaBPJSKartu.informasiprolanisPRB.replaceAll("null", "");
                        NoRujukan.requestFocus();
                    } else {
                        JOptionPane.showMessageDialog(null, "Status kepesertaan tidak aktif..!!");
                        dispose();
                    }
                } else {
                    dispose();
                }
            }
        }
    }//GEN-LAST:event_formWindowOpened

    private void ppPulangBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPulangBtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tbObat.getSelectedRow() != -1) {
            WindowUpdatePulang.setLocationRelativeTo(internalFrame1);
            WindowUpdatePulang.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data SEP yang mau diupdate tanggal pulangnya...!!!!");
            BtnBatal.requestFocus();
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppPulangBtnPrintActionPerformed

    private void BtnCloseIn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn4ActionPerformed
        WindowUpdatePulang.dispose();
    }//GEN-LAST:event_BtnCloseIn4ActionPerformed

    private void BtnSimpan4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan4ActionPerformed
        if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Pasien");
        } else if (NoKartu.getText().trim().equals("")) {
            Valid.textKosong(NoKartu, "Nomor Kartu");
//        } else if (KdPpkRujukan.getText().trim().equals("") || NmPpkRujukan.getText().trim().equals("")) {
//            Valid.textKosong(KdPpkRujukan, "PPK Rujukan");
        } else if (KdPPK.getText().trim().equals("") || NmPPK.getText().trim().equals("")) {
            Valid.textKosong(KdPPK, "PPK Pelayanan");
        } else if (Catatan.getText().trim().equals("")) {
            Valid.textKosong(Catatan, "Catatan");
        } else {
            try {
                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.add("X-Cons-ID", koneksiDB.ConsIdBpjs());
                headers.add("X-Timestamp", String.valueOf(api.GetUTCdatetimeAsString()));
                headers.add("X-Signature", api.getHmac());
                URL = koneksiDB.UrlBpjs() + "/SEP/2.0/updtglplg";
                headers.add("user_key", koneksiDB.UserKeyBpjs());
                requestJson = "{"
                        + "\"request\":"
                        + "{"
                        + "\"t_sep\":"
                        + "{"
                        + "\"noSep\":\"" + tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString() + "\","
                        + "\"statusPulang\":\"1\","
                        + "\"noSuratMeninggal\":\"\","
                        + "\"tglMeninggal\":\"\","
                        + "\"tglPulang\":\"" + Valid.SetTgl(TanggalPulang.getSelectedItem().toString().substring(0, 10) + "") + "\","
                        + "\"noLPManual\":\"\","
                        + "\"user\":\"" + user + "\""
                        + "}"
                        + "}"
                        + "}";

                System.out.println("JSON : " + requestJson);
                requestEntity = new HttpEntity(requestJson, headers);
                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.PUT, requestEntity, String.class).getBody());
                nameNode = root.path("metaData");
                System.out.println("code : " + nameNode.path("code").asText());
                System.out.println("message : " + nameNode.path("message").asText());
                response = root.path("response");
                if (nameNode.path("code").asText().equals("200")) {
                    Sequel.mengedit("bridging_sep", "no_sep=?", "tglpulang=?", 2, new String[]{
                        Valid.SetTgl(TanggalPulang.getSelectedItem() + "") + " " + TanggalPulang.getSelectedItem().toString().substring(11, 19),
                        tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()
                    });
//                    emptTeks();
                    tampil();
                    reply = JOptionPane.showConfirmDialog(rootPane, "Proses update pulang di BPJS selesai.\nApakah mau skalian mengupdate data kamar inap..?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        var.setstatus(false);
                        DlgKamarInap kamarinap = new DlgKamarInap(null, false);
                        if (Sequel.cariRegistrasi(TNoRw.getText()) > 0) {
                            JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi..!!");
                        } else {
                            kamarinap.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                            kamarinap.setLocationRelativeTo(internalFrame1);
                            kamarinap.emptTeks();
                            kamarinap.isCek();
                            kamarinap.setNoRm(TNoRw.getText());
                            kamarinap.setVisible(true);
                        }
                    }
                    WindowUpdatePulang.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, nameNode.path("message").asText());
                }
            } catch (Exception ex) {
                System.out.println("Notifikasi Bridging : " + ex);
                if (ex.toString().contains("UnknownHostException")) {
                    JOptionPane.showMessageDialog(null, "Koneksi ke server BPJS terputus...!");
                }
            }
        }
    }//GEN-LAST:event_BtnSimpan4ActionPerformed

    private void TanggalRujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalRujukKeyPressed
        Valid.pindah(evt, NoRujukan, TanggalSEP);
    }//GEN-LAST:event_TanggalRujukKeyPressed

    private void TanggalSEPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalSEPKeyPressed
        Valid.pindah(evt, TanggalRujuk, AsalRujukan);
    }//GEN-LAST:event_TanggalSEPKeyPressed

    private void LakaLantasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LakaLantasKeyPressed
        Valid.pindah(evt, NoTelp, TanggalKKL);
    }//GEN-LAST:event_LakaLantasKeyPressed

    private void JenisPelayananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisPelayananKeyPressed
        Valid.pindah(evt, btnDPJP, Kelas);
    }//GEN-LAST:event_JenisPelayananKeyPressed

    private void KelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelasKeyPressed
        Valid.pindah(evt, JenisPelayanan, Eksekutif);
    }//GEN-LAST:event_KelasKeyPressed

    private void ppPengajuanBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPengajuanBtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (!NoKartu.getText().equals("")) {
            try {
                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.add("X-Cons-ID", koneksiDB.ConsIdBpjs());
                headers.add("X-Timestamp", String.valueOf(api.GetUTCdatetimeAsString()));
                headers.add("X-Signature", api.getHmac());
                URL = link + "/Sep/pengajuanSEP";
                if (koneksiDB.UrlBpjs().contains("apijkn")) {
                    headers.add("user_key", koneksiDB.UserKeyBpjs());
                    requestJson = " {"
                            + "\"request\": {"
                            + "\"t_sep\": {"
                            + "\"noKartu\": \"" + NoKartu.getText() + "\","
                            + "\"tglSep\": \"" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "\","
                            + "\"jnsPelayanan\": \"" + JenisPelayanan.getSelectedItem().toString().substring(0, 1) + "\","
                            + "\"jnsPengajuan\": \"1\","
                            + "\"keterangan\": \"" + Catatan.getText() + "\","
                            + "\"user\": \"" + user + "\""
                            + "}"
                            + "}"
                            + "}";
                } else {
                    requestJson = " {"
                            + "\"request\": {"
                            + "\"t_sep\": {"
                            + "\"noKartu\": \"" + NoKartu.getText() + "\","
                            + "\"tglSep\": \"" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "\","
                            + "\"jnsPelayanan\": \"" + JenisPelayanan.getSelectedItem().toString().substring(0, 1) + "\","
                            + "\"keterangan\": \"" + Catatan.getText() + "\","
                            + "\"user\": \"" + user + "\""
                            + "}"
                            + "}"
                            + "}";
                }

                requestEntity = new HttpEntity(requestJson, headers);
                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                nameNode = root.path("metaData");
                System.out.println("code : " + nameNode.path("code").asText());
                System.out.println("message : " + nameNode.path("message").asText());
                if (nameNode.path("code").asText().equals("200")) {
                    JOptionPane.showMessageDialog(null, "Proses mapping selesai, data nomor rawat berhasil dikirim ke SEP..!!");
                } else {
                    JOptionPane.showMessageDialog(null, nameNode.path("message").asText());
                }
            } catch (Exception ex) {
                System.out.println("Notifikasi Bridging : " + ex);
                if (ex.toString().contains("UnknownHostException")) {
                    JOptionPane.showMessageDialog(null, "Koneksi ke server BPJS terputus...!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data peserta yang mau dimapping transaksinya...!!!!");
            BtnBatal.requestFocus();
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppPengajuanBtnPrintActionPerformed

    private void ppDetailSEPPesertaBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppDetailSEPPesertaBtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tbObat.getSelectedRow() != -1) {
            BPJSCekDetailSEP detail = new BPJSCekDetailSEP(null, true);
            detail.tampil(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
            detail.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            detail.setLocationRelativeTo(internalFrame1);
            detail.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data SEP ...!!!!");
            BtnBatal.requestFocus();
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppDetailSEPPesertaBtnPrintActionPerformed

    private void JenisPelayananItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JenisPelayananItemStateChanged
        if (JenisPelayanan.getSelectedIndex() == 0) {
            KdPoli.setText("");
            NmPoli.setText("");
            LabelPoli.setVisible(false);
            KdPoli.setVisible(false);
            NmPoli.setVisible(false);
            btnPoli.setVisible(false);
            btnSPRI.setEnabled(true);
            btnSPRI.setVisible(true);
            btnSKDP.setEnabled(false);
            btnSKDP.setVisible(false);
            Kelas1.setEnabled(true);
            cmbPembiayaan.setEnabled(true);
            KdPpkRujukan.setText(Sequel.cariIsi("select kode_ppk from setting"));
            NmPpkRujukan.setText(Sequel.cariIsi("select nama_instansi from setting"));
            AsalRujukan.setSelectedIndex(1);
            KdPoli.setText("");
            NmPoli.setText("");
            KdDPJP.setText("");
            NmDPJP.setText("");
            KdPenyakit.setText("");
            NmPenyakit.setText("");
        } else if (JenisPelayanan.getSelectedIndex() == 1) {
            LabelPoli.setVisible(true);
            KdPoli.setVisible(true);
            NmPoli.setVisible(true);
            btnPoli.setVisible(true);
            btnSPRI.setEnabled(false);
            btnSPRI.setVisible(false);
            btnSKDP.setEnabled(true);
            btnSKDP.setVisible(true);
            Kelas1.setEnabled(false);
            cmbPembiayaan.setEnabled(false);
            KdPpkRujukan.setText(kdppkrujukan);
            NmPpkRujukan.setText(nmppkrujukan);
            AsalRujukan.setSelectedIndex(0);
        }
    }//GEN-LAST:event_JenisPelayananItemStateChanged

    private void AsalRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsalRujukanKeyPressed
        Valid.pindah(evt, TanggalSEP, NoSKDP);
    }//GEN-LAST:event_AsalRujukanKeyPressed

    private void EksekutifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EksekutifKeyPressed
        Valid.pindah(evt, Kelas, COB);
    }//GEN-LAST:event_EksekutifKeyPressed

    private void COBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_COBKeyPressed
        Valid.pindah(evt, Eksekutif, Catatan);
    }//GEN-LAST:event_COBKeyPressed

    private void ChkAsaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAsaActionPerformed

    }//GEN-LAST:event_ChkAsaActionPerformed

    private void ChkJasaRaharjaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJasaRaharjaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkJasaRaharjaActionPerformed

    private void ChkBPJSTenagaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkBPJSTenagaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkBPJSTenagaActionPerformed

    private void ChkTaspenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkTaspenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkTaspenActionPerformed

    private void NoTelpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoTelpKeyPressed
        Valid.pindah(evt, Katarak, LakaLantas);
    }//GEN-LAST:event_NoTelpKeyPressed

    private void ppPengajuan1BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPengajuan1BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (!NoKartu.getText().equals("")) {
            try {
                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.add("X-Cons-ID", koneksiDB.ConsIdBpjs());
                headers.add("X-Timestamp", String.valueOf(api.GetUTCdatetimeAsString()));
                headers.add("X-Signature", api.getHmac());
                URL = link + "/Sep/aprovalSEP";
                if (koneksiDB.UrlBpjs().contains("apijkn")) {
                    headers.add("user_key", koneksiDB.UserKeyBpjs());
                    requestJson = " {"
                            + "\"request\": {"
                            + "\"t_sep\": {"
                            + "\"noKartu\": \"" + NoKartu.getText() + "\","
                            + "\"tglSep\": \"" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "\","
                            + "\"jnsPelayanan\": \"" + JenisPelayanan.getSelectedItem().toString().substring(0, 1) + "\","
                            + "\"jnsPengajuan\": \"1\","
                            + "\"keterangan\": \"" + Catatan.getText() + "\","
                            + "\"user\": \"" + user + "\""
                            + "}"
                            + "}"
                            + "}";
                } else {
                    requestJson = " {"
                            + "\"request\": {"
                            + "\"t_sep\": {"
                            + "\"noKartu\": \"" + NoKartu.getText() + "\","
                            + "\"tglSep\": \"" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "\","
                            + "\"jnsPelayanan\": \"" + JenisPelayanan.getSelectedItem().toString().substring(0, 1) + "\","
                            + "\"keterangan\": \"" + Catatan.getText() + "\","
                            + "\"user\": \"" + user + "\""
                            + "}"
                            + "}"
                            + "}";
                }
                requestEntity = new HttpEntity(requestJson, headers);
                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                nameNode = root.path("metaData");
                System.out.println("code : " + nameNode.path("code").asText());
                System.out.println("message : " + nameNode.path("message").asText());
                if (nameNode.path("code").asText().equals("200")) {
                    JOptionPane.showMessageDialog(null, "Proses mapping selesai, data nomor rawat berhasil dikirim ke SEP..!!");
                } else {
                    JOptionPane.showMessageDialog(null, nameNode.path("message").asText());
                }
            } catch (Exception ex) {
                System.out.println("Notifikasi Bridging : " + ex);
                if (ex.toString().contains("UnknownHostException")) {
                    JOptionPane.showMessageDialog(null, "Koneksi ke server BPJS terputus...!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data peserta yang mau dimapping transaksinya...!!!!");
            BtnBatal.requestFocus();
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppPengajuan1BtnPrintActionPerformed

    private void TNoRMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TNoRMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoRMActionPerformed

    private void ppRujukanBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppRujukanBtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tbObat.getSelectedRow() != -1) {
            WindowRujukan.setLocationRelativeTo(internalFrame1);
            WindowRujukan.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data SEP yang mau dibuatkan rujukan...!!!!");
            BtnBatal.requestFocus();
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppRujukanBtnPrintActionPerformed

    private void BtnCloseIn5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn5ActionPerformed
        WindowRujukan.dispose();
    }//GEN-LAST:event_BtnCloseIn5ActionPerformed

    private void BtnSimpan5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan5ActionPerformed
        if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Pasien");
        } else if (NoKartu.getText().trim().equals("")) {
            Valid.textKosong(NoKartu, "Nomor Kartu");
        } else if (KdPpkRujukan1.getText().trim().equals("") || NmPpkRujukan1.getText().trim().equals("")) {
            Valid.textKosong(KdPpkRujukan1, "PPK Rujukan");
        } else if (KdPPK.getText().trim().equals("") || NmPPK.getText().trim().equals("")) {
            Valid.textKosong(KdPPK, "PPK Pelayanan");
        } else if (KdPenyakit1.getText().trim().equals("") || NmPenyakit1.getText().trim().equals("")) {
            Valid.textKosong(KdPenyakit1, "Diagnosa");
        } else if (Catatan1.getText().trim().equals("")) {
            Valid.textKosong(Catatan1, "Catatan");
        } else if (NmPoli1.getText().trim().equals("") && (!TipeRujukan.getSelectedItem().toString().equals("2. Rujuk Balik"))) {
            Valid.textKosong(KdPoli1, "Poli Tujuan");
        } else {
            try {
                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.add("X-Cons-ID", koneksiDB.ConsIdBpjs());
                headers.add("X-Timestamp", String.valueOf(api.GetUTCdatetimeAsString()));
                headers.add("X-Signature", api.getHmac());
                headers.add("user_key", koneksiDB.UserKeyBpjs());
                URL = link + "/Rujukan/2.0/insert";
                requestJson = "{"
                        + "\"request\": {"
                        + "\"t_rujukan\": {"
                        + "\"noSep\": \"" + tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString() + "\","
                        + "\"tglRujukan\": \"" + Valid.SetTgl(TanggalRujukKeluar.getSelectedItem() + "") + "\","
                        + "\"tglRencanaKunjungan\": \"" + Valid.SetTgl(TanggalKunjungan.getSelectedItem() + "") + "\","
                        + "\"ppkDirujuk\": \"" + KdPpkRujukan1.getText() + "\","
                        + "\"jnsPelayanan\": \"" + JenisPelayanan1.getSelectedItem().toString().substring(0, 1) + "\","
                        + "\"catatan\": \"" + Catatan1.getText() + "\","
                        + "\"diagRujukan\": \"" + KdPenyakit1.getText() + "\","
                        + "\"tipeRujukan\": \"" + TipeRujukan.getSelectedItem().toString().substring(0, 1) + "\","
                        + "\"poliRujukan\": \"" + KdPoli1.getText() + "\","
                        + "\"user\": \"" + user + "\""
                        + "}"
                        + "}"
                        + "}";
                requestEntity = new HttpEntity(requestJson, headers);
                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                nameNode = root.path("metaData");
                System.out.println("code : " + nameNode.path("code").asText());
                System.out.println("message : " + nameNode.path("message").asText());
//                response = root.path("response");
                if (nameNode.path("code").asText().equals("200")) {
                    if (koneksiDB.UrlBpjs().contains("apijkn")) {
                        JsonNode res1 = root.path("response");
                        String res = api.decrypt(res1.asText());
                        String lz = api.lzDecrypt(res);
                        response = mapper.readTree(lz);
                    } else {
                        response = root.path("response");
                    }
                    if (Sequel.menyimpantf2("bridging_rujukan_bpjs", "?,?,?,?,?,?,?,?,?,?,?,?,?,?", "No.Rujukan", 14, new String[]{
                        tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString(), Valid.SetTgl(TanggalRujukKeluar.getSelectedItem() + ""), Valid.SetTgl(TanggalKunjungan.getSelectedItem() + ""),
                        KdPpkRujukan1.getText(), NmPpkRujukan1.getText(), JenisPelayanan1.getSelectedItem().toString().substring(0, 1),
                        Catatan1.getText(), KdPenyakit1.getText(), NmPenyakit1.getText(),
                        TipeRujukan.getSelectedItem().toString(), KdPoli1.getText(),
                        NmPoli1.getText(), response.path("rujukan").path("noRujukan").asText(),
                        user
                    }) == true) {
                        Sequel.menyimpan("rujuk", "'" + response.path("rujukan").path("noRujukan").asText() + "','"
                                + TNoRw.getText() + "','" + NmPpkRujukan1.getText() + "','"
                                + Valid.SetTgl(TanggalRujukKeluar.getSelectedItem() + "") + "','"
                                + NmPenyakit1.getText() + "','" + Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?", TNoRw.getText())
                                + "','-','-','" + Catatan1.getText() + "','12:00:01'", "No.Rujuk");
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        Map<String, Object> param = new HashMap<>();
                        param.put("namars", var.getnamars());
                        param.put("alamatrs", var.getalamatrs());
                        param.put("kotars", var.getkabupatenrs());
                        param.put("propinsirs", var.getpropinsirs());
                        param.put("kontakrs", var.getkontakrs());
                        param.put("norujuk", response.path("rujukan").path("noRujukan").asText());
                        param.put("logo", Sequel.cariGambar("select bpjs from gambar"));
                        Valid.MyReport("rptBridgingRujukanBPJS.jrxml", param, "::[ Surat Rujukan Keluar VClaim ]::");
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, nameNode.path("message").asText());
                }
            } catch (Exception ex) {
                System.out.println("Notifikasi Bridging : " + ex);
                if (ex.toString().contains("UnknownHostException")) {
                    JOptionPane.showMessageDialog(null, "Koneksi ke server BPJS terputus...!");
                }
            }
        }
    }//GEN-LAST:event_BtnSimpan5ActionPerformed

    private void btnPPKRujukan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPPKRujukan1ActionPerformed
        pilihan = 2;
        faskes.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        faskes.setLocationRelativeTo(internalFrame1);
        faskes.setVisible(true);
    }//GEN-LAST:event_btnPPKRujukan1ActionPerformed

    private void btnPPKRujukan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPPKRujukan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPPKRujukan1KeyPressed

    private void JenisPelayanan1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JenisPelayanan1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_JenisPelayanan1ItemStateChanged

    private void JenisPelayanan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisPelayanan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JenisPelayanan1KeyPressed

    private void btnDiagnosa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiagnosa1ActionPerformed
        pilihan = 2;
        penyakit.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        penyakit.setLocationRelativeTo(internalFrame1);
        penyakit.setVisible(true);
    }//GEN-LAST:event_btnDiagnosa1ActionPerformed

    private void btnDiagnosa1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDiagnosa1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDiagnosa1KeyPressed

    private void btnPoli1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoli1ActionPerformed
        pilihan = 2;
        poli.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setVisible(true);
    }//GEN-LAST:event_btnPoli1ActionPerformed

    private void btnPoli1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPoli1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPoli1KeyPressed

    private void TipeRujukanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TipeRujukanItemStateChanged
        if (TipeRujukan.getSelectedItem().equals("2. Rujuk Balik")) {
            KdPpkRujukan1.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString());
            NmPpkRujukan1.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString());
        }
    }//GEN-LAST:event_TipeRujukanItemStateChanged

    private void TipeRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TipeRujukanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TipeRujukanKeyPressed

    private void Catatan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Catatan1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Catatan1KeyPressed

    private void TipeRujukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TipeRujukanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TipeRujukanActionPerformed

    private void ppSEP1BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSEP1BtnPrintActionPerformed
        if (tbObat.getSelectedRow() != -1) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("logo", Sequel.cariGambar("select bpjs from gambar"));
            param.put("prb", Sequel.cariIsi("select prb from bpjs_prb where no_sep=?", tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()));
            if (JenisPelayanan.getSelectedIndex() == 0) {
                Valid.MyReport("rptBridgingSEP3.jrxml", "report", "::[ Cetak SEP ]::", "select bridging_sep.no_sep, bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tglsep,"
                        + "bridging_sep.tglrujukan,bridging_sep.no_rujukan,bridging_sep.kdppkrujukan,"
                        + "bridging_sep.nmppkrujukan,bridging_sep.kdppkpelayanan,bridging_sep.nmppkpelayanan,"
                        + "if(bridging_sep.jnspelayanan='1','Rawat Inap','Rawat Jalan'),bridging_sep.catatan,bridging_sep.diagawal,"
                        + "bridging_sep.nmdiagnosaawal,bridging_sep.kdpolitujuan,bridging_sep.nmpolitujuan,"
                        + "if(bridging_sep.klsrawat='1','Kelas 1',if(bridging_sep.klsrawat='2','Kelas 2','Kelas 3')),"
                        + "if(bridging_sep.lakalantas='0','Kasus Kecelakaan','Bukan Kasus Kecelakaan'),concat(bridging_sep.nmkec,', ',bridging_sep.nmkab,', ',bridging_sep.nmprop) as lokasilaka,bridging_sep.user, "
                        + "bridging_sep.tanggal_lahir,bridging_sep.peserta,bridging_sep.jkel,bridging_sep.no_kartu,bridging_sep.asal_rujukan,bridging_sep.eksekutif,bridging_sep.cob,bridging_sep.penjamin,bridging_sep.notelep from bridging_sep where no_sep='" + tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString() + "'", param);
            } else {
                Valid.MyReport("rptBridgingSEP4.jrxml", "report", "::[ Cetak SEP ]::", "select bridging_sep.no_sep, bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tglsep,"
                        + "bridging_sep.tglrujukan,bridging_sep.no_rujukan,bridging_sep.kdppkrujukan, adddate(bridging_sep.tglrujukan, 85) as tgl_akhir, "
                        + "bridging_sep.nmppkrujukan,bridging_sep.kdppkpelayanan,bridging_sep.nmppkpelayanan,"
                        + "if(bridging_sep.jnspelayanan='1','Rawat Inap','Rawat Jalan'),bridging_sep.catatan,bridging_sep.diagawal,"
                        + "bridging_sep.nmdiagnosaawal,bridging_sep.kdpolitujuan,bridging_sep.nmpolitujuan,"
                        + "if(bridging_sep.klsrawat='1','Kelas 1',if(bridging_sep.klsrawat='2','Kelas 2','Kelas 3')),"
                        + "if(bridging_sep.lakalantas='0','Kasus Kecelakaan','Bukan Kasus Kecelakaan'),concat(bridging_sep.nmkec,', ',bridging_sep.nmkab,', ',bridging_sep.nmprop) as lokasilaka,bridging_sep.user, "
                        + "bridging_sep.tanggal_lahir,bridging_sep.peserta,bridging_sep.jkel,bridging_sep.no_kartu,bridging_sep.asal_rujukan,bridging_sep.eksekutif,bridging_sep.cob,bridging_sep.penjamin,bridging_sep.notelep from bridging_sep where no_sep='" + tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString() + "'", param);
            }
            this.setCursor(Cursor.getDefaultCursor());
        } else {
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data SEP yang mau dicetak...!!!!");
            BtnBatal.requestFocus();
        }
    }//GEN-LAST:event_ppSEP1BtnPrintActionPerformed

    private void ppSEP2BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSEP2BtnPrintActionPerformed
        if (tbObat.getSelectedRow() != -1) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("norawat", TNoRw.getText());
            param.put("prb", Sequel.cariIsi("select prb from bpjs_prb where no_sep=?", tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()));
            param.put("noreg", Sequel.cariIsi("select no_reg from reg_periksa where no_rawat=?", TNoRw.getText()));
            param.put("logo", Sequel.cariGambar("select bpjs from gambar"));
            if (JenisPelayanan.getSelectedIndex() == 0) {
                Valid.MyReport("rptBridgingSEP5.jrxml", "report", "::[ Cetak SEP ]::", "select bridging_sep.no_sep, bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tglsep,"
                        + "bridging_sep.tglrujukan,bridging_sep.no_rujukan,bridging_sep.kdppkrujukan,"
                        + "bridging_sep.nmppkrujukan,bridging_sep.kdppkpelayanan,bridging_sep.nmppkpelayanan,"
                        + "if(bridging_sep.jnspelayanan='1','Rawat Inap','Rawat Jalan'),bridging_sep.catatan,bridging_sep.diagawal,"
                        + "bridging_sep.nmdiagnosaawal,bridging_sep.kdpolitujuan,bridging_sep.nmpolitujuan,"
                        + "if(bridging_sep.klsrawat='1','Kelas 1',if(bridging_sep.klsrawat='2','Kelas 2','Kelas 3')),"
                        + "if(bridging_sep.lakalantas='0','Kasus Kecelakaan','Bukan Kasus Kecelakaan'),concat(bridging_sep.nmkec,', ',bridging_sep.nmkab,', ',bridging_sep.nmprop) as lokasilaka,bridging_sep.user, "
                        + "bridging_sep.tanggal_lahir,bridging_sep.peserta,bridging_sep.jkel,bridging_sep.no_kartu,bridging_sep.asal_rujukan,bridging_sep.eksekutif,bridging_sep.cob,bridging_sep.penjamin,bridging_sep.notelep from bridging_sep where no_sep='" + tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString() + "'", param);
            } else {
                Valid.MyReport("rptBridgingSEP6.jrxml", "report", "::[ Cetak SEP ]::", "select bridging_sep.no_sep, bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tglsep,"
                        + "bridging_sep.tglrujukan,bridging_sep.no_rujukan,bridging_sep.kdppkrujukan,"
                        + "bridging_sep.nmppkrujukan,bridging_sep.kdppkpelayanan,bridging_sep.nmppkpelayanan,"
                        + "if(bridging_sep.jnspelayanan='1','Rawat Inap','Rawat Jalan'),bridging_sep.catatan,bridging_sep.diagawal,"
                        + "bridging_sep.nmdiagnosaawal,bridging_sep.kdpolitujuan,bridging_sep.nmpolitujuan,"
                        + "if(bridging_sep.klsrawat='1','Kelas 1',if(bridging_sep.klsrawat='2','Kelas 2','Kelas 3')),"
                        + "if(bridging_sep.lakalantas='0','Kasus Kecelakaan','Bukan Kasus Kecelakaan'),concat(bridging_sep.nmkec,', ',bridging_sep.nmkab,', ',bridging_sep.nmprop) as lokasilaka,bridging_sep.user, "
                        + "bridging_sep.tanggal_lahir,bridging_sep.peserta,bridging_sep.jkel,bridging_sep.no_kartu,bridging_sep.asal_rujukan,bridging_sep.eksekutif,bridging_sep.cob,bridging_sep.penjamin,bridging_sep.notelep from bridging_sep where no_sep='" + tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString() + "'", param);
            }
            this.setCursor(Cursor.getDefaultCursor());
        } else {
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data SEP yang mau dicetak...!!!!");
            BtnBatal.requestFocus();
        }
    }//GEN-LAST:event_ppSEP2BtnPrintActionPerformed

    private void ppSEP3BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSEP3BtnPrintActionPerformed
        if (tbObat.getSelectedRow() != -1) {
            simpanBerkas();
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            kddokter = Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?", TNoRw.getText());
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("norawat", TNoRw.getText());
            param.put("prb", Sequel.cariIsi("select prb from bpjs_prb where no_sep=?", tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()));
            param.put("dokter", Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", kddokter));
            param.put("noreg", Sequel.cariIsi("select no_reg from reg_periksa where no_rawat=?", TNoRw.getText()));
            param.put("logo", Sequel.cariGambar("select bpjs from gambar"));
            if (JenisPelayanan.getSelectedIndex() == 0) {
                Valid.MyReport("rptBridgingSEP7.jrxml", "report", "::[ Cetak SEP ]::", "select bridging_sep.no_sep, bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tglsep,"
                        + "bridging_sep.tglrujukan,bridging_sep.no_rujukan,bridging_sep.kdppkrujukan,"
                        + "bridging_sep.nmppkrujukan,bridging_sep.kdppkpelayanan,bridging_sep.nmppkpelayanan,"
                        + "if(bridging_sep.jnspelayanan='1','Rawat Inap','Rawat Jalan'),bridging_sep.catatan,bridging_sep.diagawal,"
                        + "bridging_sep.nmdiagnosaawal,bridging_sep.kdpolitujuan,bridging_sep.nmpolitujuan,"
                        + "if(bridging_sep.klsrawat='1','Kelas 1',if(bridging_sep.klsrawat='2','Kelas 2','Kelas 3')),"
                        + "if(bridging_sep.lakalantas='0','Kasus Kecelakaan','Bukan Kasus Kecelakaan'),concat(bridging_sep.nmkec,', ',bridging_sep.nmkab,', ',bridging_sep.nmprop) as lokasilaka,bridging_sep.user, "
                        + "bridging_sep.tanggal_lahir,bridging_sep.peserta,bridging_sep.jkel,bridging_sep.no_kartu,bridging_sep.asal_rujukan,bridging_sep.eksekutif,bridging_sep.cob,bridging_sep.penjamin,bridging_sep.notelep from bridging_sep where no_sep='" + tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString() + "'", param);
            } else {
                Valid.MyReport("rptBridgingSEP8.jrxml", "report", "::[ Cetak SEP ]::", "select bridging_sep.no_sep, adddate(bridging_sep.tglrujukan, 85) as tgl_akhir,bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tglsep,"
                        + "bridging_sep.tglrujukan,bridging_sep.no_rujukan,bridging_sep.kdppkrujukan,"
                        + "bridging_sep.nmppkrujukan,bridging_sep.kdppkpelayanan,bridging_sep.nmppkpelayanan,"
                        + "if(bridging_sep.jnspelayanan='1','Rawat Inap','Rawat Jalan'),bridging_sep.catatan,bridging_sep.diagawal,"
                        + "bridging_sep.nmdiagnosaawal,bridging_sep.kdpolitujuan,bridging_sep.nmpolitujuan,"
                        + "if(bridging_sep.klsrawat='1','Kelas 1',if(bridging_sep.klsrawat='2','Kelas 2','Kelas 3')),"
                        + "if(bridging_sep.lakalantas='0','Kasus Kecelakaan','Bukan Kasus Kecelakaan'),concat(bridging_sep.nmkec,', ',bridging_sep.nmkab,', ',bridging_sep.nmprop) as lokasilaka,bridging_sep.user, "
                        + "bridging_sep.tanggal_lahir,bridging_sep.peserta,bridging_sep.jkel,bridging_sep.no_kartu,bridging_sep.asal_rujukan,bridging_sep.eksekutif,bridging_sep.cob,bridging_sep.penjamin,bridging_sep.notelep from bridging_sep where no_sep='" + tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString() + "'", param);
            }
            this.setCursor(Cursor.getDefaultCursor());
        } else {
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data SEP yang mau dicetak...!!!!");
            BtnBatal.requestFocus();
        }
    }//GEN-LAST:event_ppSEP3BtnPrintActionPerformed

    private void BtnCloseIn6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn6ActionPerformed
        WindowCariSEP.dispose();
    }//GEN-LAST:event_BtnCloseIn6ActionPerformed

    private void BtnSimpan6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan6ActionPerformed
        if (!NoSEP.getText().equals("")) {
            if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
                Valid.textKosong(TNoRw, "Pasien");
            } else if (NoKartu.getText().trim().equals("")) {
                Valid.textKosong(NoKartu, "Nomor Kartu");
            } else if (NoRujukan.getText().trim().equals("")) {
                Valid.textKosong(NoRujukan, "Nomor Rujukan");
            } else if (KdPpkRujukan.getText().trim().equals("") || NmPpkRujukan.getText().trim().equals("")) {
                Valid.textKosong(KdPpkRujukan, "PPK Rujukan");
            } else if (KdPPK.getText().trim().equals("") || NmPPK.getText().trim().equals("")) {
                Valid.textKosong(KdPPK, "PPK Pelayanan");
            } else if (KdPenyakit.getText().trim().equals("") || NmPenyakit.getText().trim().equals("")) {
                Valid.textKosong(KdPenyakit, "Diagnosa");
            } else if ((JenisPelayanan.getSelectedIndex() == 1) && (KdPoli.getText().trim().equals("") || NmPoli.getText().trim().equals(""))) {
                Valid.textKosong(KdPoli, "Poli Tujuan");
            } else if ((LakaLantas.getSelectedIndex() == 1) && Keterangan.getText().equals("")) {
                Valid.textKosong(Keterangan, "Keterangan");
            } else if ((LakaLantas.getSelectedIndex() == 1) && NmPropinsi.getText().equals("")) {
                Valid.textKosong(btnPropinsi, "Propinsi");
            } else if ((LakaLantas.getSelectedIndex() == 1) && NmKabupaten.getText().equals("")) {
                Valid.textKosong(btnKabupaten, "Kabupaten");
            } else if ((LakaLantas.getSelectedIndex() == 1) && NmKecamatan.getText().equals("")) {
                Valid.textKosong(btnKecamatan, "Kecamatan");
            } else {
                jasaraharja = "";
                BPJS = "";
                Taspen = "";
                Asabri = "";
                penjamin = "";
                if (ChkJasaRaharja.isSelected() == true) {
                    jasaraharja = "1,";
                }
                if (ChkBPJSTenaga.isSelected() == true) {
                    BPJS = "2,";
                }
                if (ChkTaspen.isSelected() == true) {
                    Taspen = "3,";
                }
                if (ChkAsa.isSelected() == true) {
                    Asabri = "4,";
                }

                if ((ChkJasaRaharja.isSelected() == true) || (ChkBPJSTenaga.isSelected() == true) || (ChkTaspen.isSelected() == true) || (ChkAsa.isSelected() == true)) {
                    penjamin = jasaraharja + BPJS + Taspen + Asabri + penjamin;
                } else {
                    penjamin = "";
                }

                if (penjamin.endsWith(",")) {
                    penjamin = penjamin.substring(0, penjamin.length() - 1);
                }

                URL = link + "/SEP/2.0/insert";

                tglkkl = "0000-00-00";
                if (LakaLantas.getSelectedIndex() == 1) {
                    tglkkl = Valid.SetTgl(TanggalKKL.getSelectedItem() + "");
                }

                if (Sequel.menyimpantf("bridging_sep", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "SEP", 44, new String[]{
                    NoSEP.getText(), TNoRw.getText(), Valid.SetTgl(TanggalSEP.getSelectedItem() + ""), Valid.SetTgl(TanggalRujuk.getSelectedItem() + ""),
                    NoRujukan.getText(), KdPpkRujukan.getText(), NmPpkRujukan.getText(), KdPPK.getText(), NmPPK.getText(),
                    JenisPelayanan.getSelectedItem().toString().substring(0, 1), Catatan.getText(), KdPenyakit.getText(),
                    NmPenyakit.getText(), KdPoli.getText(), NmPoli.getText(), Kelas.getSelectedItem().toString().substring(0, 1),
                    LakaLantas.getSelectedItem().toString().substring(0, 1), user, TNoRM.getText(), TPasien.getText(),
                    TglLahir.getText(), JenisPeserta.getText(), JK.getText(), NoKartu.getText(),
                    "0000-00-00 00:00:00", AsalRujukan.getSelectedItem().toString(), Eksekutif.getSelectedItem().toString(),
                    COB.getSelectedItem().toString(), penjamin, NoTelp.getText(), Katarak.getSelectedItem().toString().substring(0, 1),
                    tglkkl, Keterangan.getText(), Suplesi.getSelectedItem().toString().substring(0, 1),
                    NoSEPSuplesi.getText(), KdPropinsi.getText(), NmPropinsi.getText(), KdKabupaten.getText(), NmKabupaten.getText(),
                    KdKecamatan.getText(), NmKecamatan.getText(), NoSKDP.getText(), KdDPJP.getText(), NmDPJP.getText()
                }) == true) {
                    tampil();
                    WindowCariSEP.dispose();
                }
            }
        }
    }//GEN-LAST:event_BtnSimpan6ActionPerformed

    private void NoSEPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoSEPKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoSEPKeyPressed

    private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        if (!NoSEP.getText().equals("")) {
            try {
                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.add("X-Cons-ID", koneksiDB.ConsIdBpjs());
                headers.add("X-Timestamp", String.valueOf(api.GetUTCdatetimeAsString()));
                headers.add("X-Signature", api.getHmac());
                headers.add("user_key", koneksiDB.UserKeyBpjs());
                URL = link + "/SEP/" + NoSEP.getText();
                requestEntity = new HttpEntity(headers);
                //System.out.println(rest.exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
                nameNode = root.path("metaData");
                System.out.println("code : " + nameNode.path("code").asText());
                System.out.println("message : " + nameNode.path("message").asText());
                if (nameNode.path("message").asText().equals("Sukses")) {
                    if (koneksiDB.UrlBpjs().contains("apijkn")) {
                        JsonNode res1 = root.path("response");
                        String res = api.decrypt(res1.asText());
                        String lz = api.lzDecrypt(res);
                        response = mapper.readTree(lz);
                    } else {
                        response = root.path("response");
                    }
                    Catatan.setText(response.path("catatan").asText());
                    NmPenyakit.setText(response.path("diagnosa").asText());
                    if (response.path("jnsPelayanan").asText().toLowerCase().contains("inap")) {
                        JenisPelayanan.setSelectedIndex(0);
                    } else {
                        JenisPelayanan.setSelectedIndex(1);
                    }

                    if (response.path("kelasRawat").asText().toLowerCase().equals("1")) {
                        Kelas.setSelectedIndex(0);
                    } else if (response.path("kelasRawat").asText().toLowerCase().equals("2")) {
                        Kelas.setSelectedIndex(1);
                    } else if (response.path("kelasRawat").asText().toLowerCase().equals("3")) {
                        Kelas.setSelectedIndex(2);
                    }

                    if (response.path("poliEksekutif").asText().equals("0")) {
                        Eksekutif.setSelectedIndex(0);
                    } else {
                        Eksekutif.setSelectedIndex(1);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, nameNode.path("message").asText());
                }
            } catch (Exception ex) {
                System.out.println("Notifikasi Peserta : " + ex);
                if (ex.toString().contains("UnknownHostException")) {
                    JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
                    dispose();
                }
            }
        }
    }//GEN-LAST:event_BtnCari1ActionPerformed

    private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCari1KeyPressed

    private void ppAmbilSepBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppAmbilSepBtnPrintActionPerformed
        if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Pasien");
        } else if (NoKartu.getText().trim().equals("")) {
            Valid.textKosong(NoKartu, "Nomor Kartu");
        } else if (NoRujukan.getText().trim().equals("")) {
            Valid.textKosong(NoRujukan, "Nomor Rujukan");
        } else if (KdPpkRujukan.getText().trim().equals("") || NmPpkRujukan.getText().trim().equals("")) {
            Valid.textKosong(KdPpkRujukan, "PPK Rujukan");
        } else if (KdPPK.getText().trim().equals("") || NmPPK.getText().trim().equals("")) {
            Valid.textKosong(KdPPK, "PPK Pelayanan");
        } else if (KdPenyakit.getText().trim().equals("") || NmPenyakit.getText().trim().equals("")) {
            Valid.textKosong(KdPenyakit, "Diagnosa");
        } else if ((JenisPelayanan.getSelectedIndex() == 1) && (KdPoli.getText().trim().equals("") || NmPoli.getText().trim().equals(""))) {
            Valid.textKosong(KdPoli, "Poli Tujuan");
        } else {
            WindowCariSEP.setLocationRelativeTo(internalFrame1);
            WindowCariSEP.setVisible(true);
        }
    }//GEN-LAST:event_ppAmbilSepBtnPrintActionPerformed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if (TabRawat.getSelectedIndex() == 1) {
            tampil();
        } else {
            tampil_internal();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        if (this.getHeight() < 530) {
            Scroll1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            FormInput.setPreferredSize(new Dimension(FormInput.WIDTH, 410));
            if (this.getWidth() < 780) {
                Scroll1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                FormInput.setPreferredSize(new Dimension(770, 410));
            } else {
                Scroll1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            }
        } else {
            Scroll1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
            if (this.getWidth() < 780) {
                Scroll1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                FormInput.setPreferredSize(new Dimension(770, FormInput.HEIGHT));
            } else {
                Scroll1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            }
        }
    }//GEN-LAST:event_formWindowActivated

    private void KatarakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KatarakKeyPressed
        Valid.pindah(evt, Catatan, NoTelp);
    }//GEN-LAST:event_KatarakKeyPressed

    private void TanggalKKLKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKKLKeyPressed
        Valid.pindah(evt, LakaLantas, Keterangan);
    }//GEN-LAST:event_TanggalKKLKeyPressed

    private void NoSKDPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoSKDPKeyPressed
        Valid.pindah(evt, AsalRujukan, btnPPKRujukan);
    }//GEN-LAST:event_NoSKDPKeyPressed

    private void btnSKDPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSKDPActionPerformed
        skdp.setNoRm(NoKartu.getText());
        skdp.setTanggal();
        skdp.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        skdp.setLocationRelativeTo(internalFrame1);
        skdp.setVisible(true);
    }//GEN-LAST:event_btnSKDPActionPerformed

    private void btnDPJPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDPJPActionPerformed
        dokter.setPoli(KdPoli.getText(), NmPoli.getText());
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDPJPActionPerformed

    private void btnDPJPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDPJPKeyPressed
        if (btnPoli.isVisible() == true) {
            Valid.pindah(evt, btnPoli, JenisPelayanan);
        } else {
            Valid.pindah(evt, btnDiagnosa, JenisPelayanan);
        }
    }//GEN-LAST:event_btnDPJPKeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt, TanggalKKL, Suplesi);
    }//GEN-LAST:event_KeteranganKeyPressed

    private void SuplesiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuplesiKeyPressed
        Valid.pindah(evt, Keterangan, NoSEPSuplesi);
    }//GEN-LAST:event_SuplesiKeyPressed

    private void NoSEPSuplesiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoSEPSuplesiKeyPressed
        Valid.pindah(evt, Suplesi, btnPropinsi);
    }//GEN-LAST:event_NoSEPSuplesiKeyPressed

    private void btnPropinsiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPropinsiActionPerformed
        propinsi.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        propinsi.setLocationRelativeTo(internalFrame1);
        propinsi.setVisible(true);
    }//GEN-LAST:event_btnPropinsiActionPerformed

    private void btnPropinsiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPropinsiKeyPressed
        Valid.pindah(evt, NoSEPSuplesi, btnKabupaten);
    }//GEN-LAST:event_btnPropinsiKeyPressed

    private void btnKabupatenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKabupatenActionPerformed
        if (KdPropinsi.getText().trim().equals("") || NmPropinsi.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Silahkan pilih propinsi dulu..!!");
            btnPropinsi.requestFocus();
        } else {
            kabupaten.setPropinsi(KdPropinsi.getText(), NmPropinsi.getText());
            kabupaten.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            kabupaten.setLocationRelativeTo(internalFrame1);
            kabupaten.setVisible(true);
        }
    }//GEN-LAST:event_btnKabupatenActionPerformed

    private void btnKabupatenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnKabupatenKeyPressed
        Valid.pindah(evt, btnPropinsi, btnKecamatan);
    }//GEN-LAST:event_btnKabupatenKeyPressed

    private void btnKecamatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKecamatanActionPerformed
        if (KdKabupaten.getText().trim().equals("") || NmKabupaten.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Silahkan pilih kabupaten dulu..!!");
            btnKabupaten.requestFocus();
        } else {
            kecamatan.setPropinsi(KdKabupaten.getText(), NmKabupaten.getText());
            kecamatan.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            kecamatan.setLocationRelativeTo(internalFrame1);
            kecamatan.setVisible(true);
        }
    }//GEN-LAST:event_btnKecamatanActionPerformed

    private void btnKecamatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnKecamatanKeyPressed
        Valid.pindah(evt, btnKabupaten, BtnSimpan);
    }//GEN-LAST:event_btnKecamatanKeyPressed

    private void ppRiwayatPerawatanBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppRiwayatPerawatanBtnPrintActionPerformed
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        } else {
            if (tbObat.getSelectedRow() != -1) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgResumePerawatan resume = new DlgResumePerawatan(null, true);
                resume.setNoRm(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString(), tbObat.getValueAt(tbObat.getSelectedRow(), 3).toString());
                resume.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                resume.setLocationRelativeTo(internalFrame1);
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_ppRiwayatPerawatanBtnPrintActionPerformed

    private void ppSEPBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSEPBtnPrintActionPerformed
        if (tbObat.getSelectedRow() != -1) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("logo", Sequel.cariGambar("select bpjs from gambar"));
            param.put("prb", Sequel.cariIsi("select prb from bpjs_prb where no_sep=?", tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()));
            if (JenisPelayanan.getSelectedIndex() == 0) {
                Valid.MyReport("rptBridgingSEP.jrxml", "report", "::[ Cetak SEP ]::", "select bridging_sep.no_sep, bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tglsep,"
                        + "bridging_sep.tglrujukan,bridging_sep.no_rujukan,bridging_sep.kdppkrujukan,"
                        + "bridging_sep.nmppkrujukan,bridging_sep.kdppkpelayanan,bridging_sep.nmppkpelayanan,"
                        + "if(bridging_sep.jnspelayanan='1','Rawat Inap','Rawat Jalan'),bridging_sep.catatan,bridging_sep.diagawal,"
                        + "bridging_sep.nmdiagnosaawal,bridging_sep.kdpolitujuan,bridging_sep.nmpolitujuan,"
                        + "if(bridging_sep.klsrawat='1','Kelas 1',if(bridging_sep.klsrawat='2','Kelas 2','Kelas 3')),"
                        + "if(bridging_sep.lakalantas='0','Kasus Kecelakaan','Bukan Kasus Kecelakaan'),"
                        + "concat(bridging_sep.nmkec,', ',bridging_sep.nmkab,', ',bridging_sep.nmprop) as lokasilaka,bridging_sep.user, "
                        + "bridging_sep.tanggal_lahir,bridging_sep.peserta,bridging_sep.jkel,bridging_sep.no_kartu,bridging_sep.asal_rujukan,bridging_sep.eksekutif,bridging_sep.cob,bridging_sep.penjamin,bridging_sep.notelep,petugas.nip,petugas.nama from bridging_sep inner join petugas on bridging_sep.user=petugas.nip where no_sep='" + tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString() + "'", param);
            } else {
                Valid.MyReport("rptBridgingSEP2.jrxml", "report", "::[ Cetak SEP ]::", "select bridging_sep.no_sep, bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tglsep,"
                        + "bridging_sep.tglrujukan,bridging_sep.no_rujukan,bridging_sep.kdppkrujukan,"
                        + "bridging_sep.nmppkrujukan,bridging_sep.kdppkpelayanan,bridging_sep.nmppkpelayanan,"
                        + "if(bridging_sep.jnspelayanan='1','Rawat Inap','Rawat Jalan'),bridging_sep.catatan,bridging_sep.diagawal,"
                        + "bridging_sep.nmdiagnosaawal,bridging_sep.kdpolitujuan,bridging_sep.nmpolitujuan,"
                        + "if(bridging_sep.klsrawat='1','Kelas 1',if(bridging_sep.klsrawat='2','Kelas 2','Kelas 3')),"
                        + "if(bridging_sep.lakalantas='0','Kasus Kecelakaan','Bukan Kasus Kecelakaan'),concat(bridging_sep.nmkec,', ',bridging_sep.nmkab,', ',bridging_sep.nmprop) as lokasilaka,bridging_sep.user, "
                        + "bridging_sep.tanggal_lahir,bridging_sep.peserta,bridging_sep.jkel,bridging_sep.no_kartu,bridging_sep.asal_rujukan,bridging_sep.eksekutif,bridging_sep.cob,bridging_sep.penjamin,bridging_sep.notelep,dokter.nm_dokter "
                        + "from bridging_sep inner join reg_periksa INNER JOIN dokter on bridging_sep.no_rawat=reg_periksa.no_rawat AND dokter.kd_dokter=reg_periksa.kd_dokter AND reg_periksa.kd_dokter NOT IN ('-') where no_sep='" + tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString() + "'", param);
            }
            this.setCursor(Cursor.getDefaultCursor());
        } else {
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data SEP yang mau dicetak...!!!!");
            BtnBatal.requestFocus();
        }
    }//GEN-LAST:event_ppSEPBtnPrintActionPerformed


    private void btnRujukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRujukanActionPerformed
        if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Pasien");
        } else if (NoKartu.getText().trim().equals("")) {
            Valid.textKosong(NoKartu, "Nomor Kartu");
        } else {
            WindowCariNoRujuk.setLocationRelativeTo(internalFrame1);
            WindowCariNoRujuk.setVisible(true);
            tampilFaskes1();
        }
    }//GEN-LAST:event_btnRujukanActionPerformed

    private void btnRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnRujukanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRujukanKeyPressed

    private void tbFaskes1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbFaskes1MouseClicked
        if (tabMode1.getRowCount() != 0) {
            try {
                getDataFK1();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbFaskes1MouseClicked

    private void tbFaskes1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbFaskes1KeyPressed
        if (tabMode1.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataFK1();
                } catch (java.lang.NullPointerException e) {
                }
            }
            WindowCariNoRujuk.dispose();
        }
    }//GEN-LAST:event_tbFaskes1KeyPressed

    private void tbFaskes2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbFaskes2MouseClicked
        if (tabMode2.getRowCount() != 0) {
            try {
                getDataFK2();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbFaskes2MouseClicked

    private void tbFaskes2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbFaskes2KeyPressed
        if (tabMode2.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataFK2();
                } catch (java.lang.NullPointerException e) {
                }
            }
            WindowCariNoRujuk.dispose();
        }
    }//GEN-LAST:event_tbFaskes2KeyPressed

    private void tbFaskes3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbFaskes3MouseClicked
        if (tabMode3.getRowCount() != 0) {
            try {
                getDataFK1byk();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbFaskes3MouseClicked

    private void tbFaskes3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbFaskes3KeyPressed
        if (tabMode3.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataFK1byk();
                } catch (java.lang.NullPointerException e) {
                }
            }
            WindowCariNoRujuk.dispose();
        }
    }//GEN-LAST:event_tbFaskes3KeyPressed

    private void tbFaskes4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbFaskes4MouseClicked
        if (tabMode4.getRowCount() != 0) {
            try {
                getDataFK2byk();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbFaskes4MouseClicked

    private void tbFaskes4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbFaskes4KeyPressed
        if (tabMode4.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataFK2byk();
                } catch (java.lang.NullPointerException e) {
                }
            }
            WindowCariNoRujuk.dispose();
        }
    }//GEN-LAST:event_tbFaskes4KeyPressed

    private void TabRujukanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRujukanMouseClicked
        if (TabRujukan.getSelectedIndex() == 0) {
            tampilFaskes1();
        } else if (TabRujukan.getSelectedIndex() == 1) {
            tampilFaskes2();
        } else if (TabRujukan.getSelectedIndex() == 2) {
            tampilFaskes1BYK();
        } else if (TabRujukan.getSelectedIndex() == 3) {
            tampilFaskes2BYK();
        } else if (TabRujukan.getSelectedIndex() == 4) {
            String month = "";
            if (bulanRujukKhusus.getSelectedIndex() == 9 || bulanRujukKhusus.getSelectedIndex() == 10 || bulanRujukKhusus.getSelectedIndex() == 11) {
                month = bulanRujukKhusus.getSelectedItem().toString().substring(6, 8);
            } else {
                month = bulanRujukKhusus.getSelectedItem().toString().substring(6, 7);
            }
            tampilRujukKhusus(month);
        }
    }//GEN-LAST:event_TabRujukanMouseClicked

    private void BtnKeluar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar1ActionPerformed
        WindowCariNoRujuk.dispose();
    }//GEN-LAST:event_BtnKeluar1ActionPerformed

    private void BtnKeluar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluar1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnKeluar1KeyPressed

    private void ppSEP4BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSEP4BtnPrintActionPerformed
        if (tbObat.getSelectedRow() != -1) {
            simpanBerkas();
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("norawat", tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString());
            param.put("prb", Sequel.cariIsi("select prb from bpjs_prb where no_sep=?", tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()));
            param.put("dokter", Sequel.cariIsi("select nmdpdjp from bridging_sep where no_sep=?", tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()));
            param.put("noreg", Sequel.cariIsi("select no_reg from reg_periksa where no_rawat=?", tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString()));
            param.put("logo", Sequel.cariGambar("select bpjs from gambar"));
            param.put("katarak", Sequel.cariIsi("select katarak from bridging_sep where no_rawat=?", tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString()));
            /* Tambahan source ulun (Ridho Alfian [ICT RSHD])*/
            param.put("diagnosa1", Sequel.cariIsi("select b.nm_penyakit FROM diagnosa_pasien as a, penyakit as b, "
                    + "reg_periksa as c,pasien as d WHERE a.no_rawat = c.no_rawat AND "
                    + "c.no_rkm_medis = d.no_rkm_medis AND a.kd_penyakit = b.kd_penyakit AND a.prioritas = '1' "
                    + "AND a.no_rawat =?", tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString()));
            param.put("diagnosa2", Sequel.cariIsi("select b.nm_penyakit FROM diagnosa_pasien as a, penyakit as b, "
                    + "reg_periksa as c,pasien as d WHERE a.no_rawat = c.no_rawat AND "
                    + "c.no_rkm_medis = d.no_rkm_medis AND a.kd_penyakit = b.kd_penyakit AND a.prioritas = '2' "
                    + "AND a.no_rawat =?", tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString()));
            param.put("kd_diagnosa1", Sequel.cariIsi("select b.kd_penyakit FROM diagnosa_pasien as a, penyakit as b, "
                    + "reg_periksa as c,pasien as d WHERE a.no_rawat = c.no_rawat AND "
                    + "c.no_rkm_medis = d.no_rkm_medis AND a.kd_penyakit = b.kd_penyakit AND a.prioritas = '1' "
                    + "AND a.no_rawat =?", tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString()));
            param.put("kd_diagnosa2", Sequel.cariIsi("select b.kd_penyakit FROM diagnosa_pasien as a, penyakit as b, "
                    + "reg_periksa as c,pasien as d WHERE a.no_rawat = c.no_rawat AND "
                    + "c.no_rkm_medis = d.no_rkm_medis AND a.kd_penyakit = b.kd_penyakit AND a.prioritas = '2' "
                    + "AND a.no_rawat =?", tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString()));
            param.put("kd_icd9_1", Sequel.cariIsi("select b.kode FROM prosedur_pasien as a, icd9 as b, "
                    + "reg_periksa as c,pasien as d WHERE a.no_rawat = c.no_rawat AND "
                    + "c.no_rkm_medis = d.no_rkm_medis AND a.kode = b.kode AND a.prioritas = '1' "
                    + "AND a.no_rawat =?", tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString()));
            param.put("kd_icd9_2", Sequel.cariIsi("select b.kode FROM prosedur_pasien as a, icd9 as b, "
                    + "reg_periksa as c,pasien as d WHERE a.no_rawat = c.no_rawat AND "
                    + "c.no_rkm_medis = d.no_rkm_medis AND a.kode = b.kode AND a.prioritas = '2' "
                    + "AND a.no_rawat =?", tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString()));
            param.put("icd9_1", Sequel.cariIsi("select b.deskripsi_pendek FROM prosedur_pasien as a, icd9 as b, "
                    + "reg_periksa as c,pasien as d WHERE a.no_rawat = c.no_rawat AND "
                    + "c.no_rkm_medis = d.no_rkm_medis AND a.kode = b.kode AND a.prioritas = '1' "
                    + "AND a.no_rawat =?", tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString()));
            param.put("icd9_2", Sequel.cariIsi("select b.deskripsi_pendek FROM prosedur_pasien as a, icd9 as b, "
                    + "reg_periksa as c,pasien as d WHERE a.no_rawat = c.no_rawat AND "
                    + "c.no_rkm_medis = d.no_rkm_medis AND a.kode = b.kode AND a.prioritas = '2' "
                    + "AND a.no_rawat =?", tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString()));
            param.put("pemeriksaan", Sequel.cariIsi("select pemeriksaan from pemeriksaan_ralan where no_rawat=?", tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString()));
            param.put("keluhan", Sequel.cariIsi("select keluhan from pemeriksaan_ralan where no_rawat=?", tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString()));
            param.put("tensi", Sequel.cariIsi("select tensi from pemeriksaan_ralan where no_rawat=?", tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString()));
            param.put("nadi", Sequel.cariIsi("select nadi from pemeriksaan_ralan where no_rawat=?", tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString()));
            param.put("respirasi", Sequel.cariIsi("select respirasi from pemeriksaan_ralan where no_rawat=?", tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString()));

            Valid.MyReport("rptBridgingSEP9.jrxml", "report", "::[ Cetak SEP ]::", "select bridging_sep.no_sep, adddate(bridging_sep.tglrujukan, 85) as tgl_akhir,bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tglsep,"
                    + "bridging_sep.tglrujukan,bridging_sep.no_rujukan,bridging_sep.kdppkrujukan,"
                    + "bridging_sep.nmppkrujukan,bridging_sep.kdppkpelayanan,bridging_sep.nmppkpelayanan,"
                    + "if(bridging_sep.jnspelayanan='1','Rawat Inap','Rawat Jalan'),bridging_sep.catatan,bridging_sep.diagawal,"
                    + "bridging_sep.nmdiagnosaawal,bridging_sep.kdpolitujuan,bridging_sep.nmpolitujuan,"
                    + "if(bridging_sep.klsrawat='1','Kelas 1',if(bridging_sep.klsrawat='2','Kelas 2','Kelas 3')),"
                    + "if(bridging_sep.lakalantas='0','Kasus Kecelakaan','Bukan Kasus Kecelakaan'),concat(bridging_sep.nmkec,', ',bridging_sep.nmkab,', ',bridging_sep.nmprop) as lokasilaka,bridging_sep.user, "
                    + "bridging_sep.tanggal_lahir,bridging_sep.peserta,bridging_sep.jkel,bridging_sep.no_kartu,bridging_sep.asal_rujukan,bridging_sep.eksekutif,bridging_sep.cob,bridging_sep.penjamin,bridging_sep.notelep from bridging_sep where no_sep='" + tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString() + "'", param);

            this.setCursor(Cursor.getDefaultCursor());
        } else {
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data SEP yang mau dicetak...!!!!");
            BtnBatal.requestFocus();
        }
    }//GEN-LAST:event_ppSEP4BtnPrintActionPerformed

    private void cmbPembiayaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPembiayaanActionPerformed
        if (null != cmbPembiayaan.getSelectedItem().toString().substring(0, 1)) // TODO add your handling code here:
            switch (cmbPembiayaan.getSelectedItem().toString().substring(0, 1)) {
                case "1":
                    pnj.setText("Pribadi");
                    break;
                case "2":
                    pnj.setText("Pemberi Kerja");
                    break;
                case "3":
                    pnj.setText("Asuransi Kesehatan Tambahan");
                    break;
                default:
                    pnj.setText("");
                    break;
            }
    }//GEN-LAST:event_cmbPembiayaanActionPerformed

    private void btnSPRIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSPRIActionPerformed
        skdp2.setNoRm(NoKartu.getText());
        skdp2.isCek();
        skdp2.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        skdp2.setLocationRelativeTo(internalFrame1);
        skdp2.setVisible(true);
    }//GEN-LAST:event_btnSPRIActionPerformed

    private void cmbKunjunganItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbKunjunganItemStateChanged
        if (cmbKunjungan.getSelectedIndex() == 2) {
            cmbProcedure.setEnabled(false);
            cmbPenunjang.setEnabled(false);
        } else {
            cmbProcedure.setEnabled(true);
            cmbPenunjang.setEnabled(true);
        }
    }//GEN-LAST:event_cmbKunjunganItemStateChanged

    private void ppRujukKhususActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppRujukKhususActionPerformed
        if (NoRujukan.getText().trim().equals("")) {
            Valid.textKosong(NoRujukan, "Nomor Rujukan");
        } else {
            WindowRujukKhusus.setLocationRelativeTo(internalFrame1);
            WindowRujukKhusus.setVisible(true);
        }
    }//GEN-LAST:event_ppRujukKhususActionPerformed

    private void BtnCloseIn8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn8ActionPerformed
        // TODO add your handling code here:
        WindowRujukKhusus.dispose();
    }//GEN-LAST:event_BtnCloseIn8ActionPerformed

    private void BtnSimpan8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan8ActionPerformed
        int i = 0, z = 0, x = 0;
        String obat = "", prosedur = "";
        if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Pasien");
        } else if (NoKartu.getText().trim().equals("")) {
            Valid.textKosong(NoKartu, "Nomor Kartu");
        } else if (NoRujukan.getText().trim().equals("")) {
            Valid.textKosong(NoRujukan, "Nomor Rujukan");
        } else if (!KdPoli.getText().trim().equals("HDL")) {
            System.out.println("Rujukan Khusus Hanya Untuk Hemodialisis");
        } else {
            try {
                obat = "";
                z = 0;
                for (i = 0; i < tbDiagnosa.getRowCount(); i++) {
                    if (tbDiagnosa.getValueAt(i, 0).toString().equals("true")) {
                        obat = obat + "{\"kode\":\"P;" + tbDiagnosa.getValueAt(i, 2).toString() + "\"},";
                        z++;
                    } else if (tbDiagnosa.getValueAt(i, 1).toString().equals("true")) {
                        obat = obat + "{\"kode\":\"S;" + tbDiagnosa.getValueAt(i, 2).toString() + "\"},";
                        z++;
                    }
                }
                if (z > 0) {
                    obat = "[" + obat.substring(0, obat.length() - 1) + "]";
                } else {
                    obat = "[]";
                }

                prosedur = "";
                x = 0;
                for (i = 0; i < tbProsedur.getRowCount(); i++) {
                    if (tbProsedur.getValueAt(i, 0).toString().equals("true")) {
                        prosedur = prosedur + "{\"kode\":\"" + tbProsedur.getValueAt(i, 1).toString() + "\"},";
                        x++;
                    }
                }
                if (x > 0) {
                    prosedur = "[" + prosedur.substring(0, prosedur.length() - 1) + "]";
                } else {
                    prosedur = "[]";
                }

                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.add("X-Cons-ID", koneksiDB.ConsIdBpjs());
                headers.add("X-Timestamp", String.valueOf(api.GetUTCdatetimeAsString()));
                headers.add("X-Signature", api.getHmac());
                headers.add("user_key", koneksiDB.UserKeyBpjs());
                URL = link + "/Rujukan/Khusus/insert";
                requestJson = "{"
                        + "\"noRujukan\": \"" + NoRujukan.getText() + "\","
                        + "\"diagnosa\": " + obat + ","
                        + "\"procedure\": " + prosedur + ","
                        + "\"user\": \"" + user + "\""
                        + "}";
                System.out.println(requestJson);
                requestEntity = new HttpEntity(requestJson, headers);
                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                nameNode = root.path("metaData");
                System.out.println("code : " + nameNode.path("code").asText());
                System.out.println("message : " + nameNode.path("message").asText());
//                response = root.path("response");
                if (nameNode.path("code").asText().equals("200")) {
                    if (koneksiDB.UrlBpjs().contains("apijkn")) {
                        JsonNode res1 = root.path("response");
                        String res = api.decrypt(res1.asText());
                        String lz = api.lzDecrypt(res);
                        response = mapper.readTree(lz);
                    } else {
                        response = root.path("response");
                    }
                    JOptionPane.showMessageDialog(null, nameNode.path("message").asText());
                    System.out.println(response);
                } else {
                    JOptionPane.showMessageDialog(null, nameNode.path("message").asText());
                }
            } catch (Exception ex) {
                System.out.println("Notifikasi Bridging : " + ex);
                if (ex.toString().contains("UnknownHostException")) {
                    JOptionPane.showMessageDialog(null, "Koneksi ke server BPJS terputus...!");
                }
            }
        }
    }//GEN-LAST:event_BtnSimpan8ActionPerformed

    private void DiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosaKeyPressed

    private void tbDiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDiagnosaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDiagnosaKeyPressed

    private void BtnCariPenyakit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariPenyakit1ActionPerformed
        // TODO add your handling code here:
        tampildiagnosa();
    }//GEN-LAST:event_BtnCariPenyakit1ActionPerformed

    private void ProsedurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProsedurKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            tampilprosedure();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            if (var.geticd9() == true) {
//                btnTambahProsedurActionPerformed(null);
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            tbProsedur.requestFocus();
        }
    }//GEN-LAST:event_ProsedurKeyPressed

    private void BtnCariProsedurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariProsedurActionPerformed
        tampilprosedure();
    }//GEN-LAST:event_BtnCariProsedurActionPerformed

    private void ppSuratKontrolBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSuratKontrolBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbObat.requestFocus();
        } else {
            if (tbObat.getSelectedRow() != -1) {
                try {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    BPJSSuratKontrol form = new BPJSSuratKontrol(null, false);
                    form.setNoRm(tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString(), tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString(), tbObat.getValueAt(tbObat.getSelectedRow(), 23).toString(), tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString(), tbObat.getValueAt(tbObat.getSelectedRow(), 3).toString(), tbObat.getValueAt(tbObat.getSelectedRow(), 20).toString(), tbObat.getValueAt(tbObat.getSelectedRow(), 22).toString(), tbObat.getValueAt(tbObat.getSelectedRow(), 14).toString(), tbObat.getValueAt(tbObat.getSelectedRow(), 15).toString(), tbObat.getValueAt(tbObat.getSelectedRow(), 4).toString());
                    form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    form.setLocationRelativeTo(internalFrame1);
                    form.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                } catch (Exception e) {
                    System.out.println("Notif : " + e);
                }
            }
        }
    }//GEN-LAST:event_ppSuratKontrolBtnPrintActionPerformed

    private void ppRujukKhusus1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppRujukKhusus1ActionPerformed
        if (NoRujukan.getText().trim().equals("")) {
            Valid.textKosong(NoRujukan, "Nomor Rujukan");
        } else if (!KdPoli.getText().trim().equals("HDL")) {
            JOptionPane.showMessageDialog(null, "Hanya Untuk Hemodialisis");
        } else {
            WindowRujukKhusus.setLocationRelativeTo(internalFrame1);
            WindowRujukKhusus.setVisible(true);
        }
    }//GEN-LAST:event_ppRujukKhusus1ActionPerformed

    private void NoSKDPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NoSKDPMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON3) {
            if ((NoRujukan.getText().equals("")) && (JenisPelayanan.getSelectedIndex() == 1)) {
                try {
                    Sequel.cariIsi("SELECT no_sep FROM bridging_surat_kontrol_bpjs WHERE no_surat = ?", NoRujukan, NoSKDP.getText());
                } catch (Exception e) {
                    System.out.println("Gagal GET " + e);
                }
            } else if ((NoRujukan.getText().equals("")) && (JenisPelayanan.getSelectedIndex() == 0)) {
                String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                NoRujukan.setText(timeStamp);
            }
        }
    }//GEN-LAST:event_NoSKDPMouseClicked

    private void tbObat1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObat1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbObat1MouseClicked

    private void tbObat1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObat1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbObat1KeyPressed

    private void TCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCari1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TCari1KeyPressed

    private void BtnCari2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari2ActionPerformed
        tampil_internal();
    }//GEN-LAST:event_BtnCari2ActionPerformed

    private void BtnCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCari2KeyPressed

    private void ppSEP9BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSEP9BtnPrintActionPerformed
        if (tbObat1.getSelectedRow() != -1) {
            simpanBerkas();
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("norawat", tbObat1.getValueAt(tbObat1.getSelectedRow(), 1).toString());
            param.put("prb", Sequel.cariIsi("select prb from bpjs_prb where no_sep=?", tbObat1.getValueAt(tbObat1.getSelectedRow(), 0).toString()));
            param.put("dokter", Sequel.cariIsi("select nmdpdjp from bridging_sep where no_sep=?", tbObat1.getValueAt(tbObat1.getSelectedRow(), 0).toString()));
            param.put("noreg", Sequel.cariIsi("select no_reg from reg_periksa where no_rawat=?", tbObat1.getValueAt(tbObat1.getSelectedRow(), 1).toString()));
            param.put("logo", Sequel.cariGambar("select bpjs from gambar"));
            param.put("katarak", Sequel.cariIsi("select katarak from bridging_sep_internal where no_rawat=?", tbObat1.getValueAt(tbObat1.getSelectedRow(), 1).toString()));
            /* Tambahan source ulun (Ridho Alfian [ICT RSHD])*/
            param.put("diagnosa1", Sequel.cariIsi("select b.nm_penyakit FROM diagnosa_pasien as a, penyakit as b, "
                    + "reg_periksa as c,pasien as d WHERE a.no_rawat = c.no_rawat AND "
                    + "c.no_rkm_medis = d.no_rkm_medis AND a.kd_penyakit = b.kd_penyakit AND a.prioritas = '1' "
                    + "AND a.no_rawat =?", tbObat1.getValueAt(tbObat1.getSelectedRow(), 1).toString()));
            param.put("diagnosa2", Sequel.cariIsi("select b.nm_penyakit FROM diagnosa_pasien as a, penyakit as b, "
                    + "reg_periksa as c,pasien as d WHERE a.no_rawat = c.no_rawat AND "
                    + "c.no_rkm_medis = d.no_rkm_medis AND a.kd_penyakit = b.kd_penyakit AND a.prioritas = '2' "
                    + "AND a.no_rawat =?", tbObat1.getValueAt(tbObat1.getSelectedRow(), 1).toString()));
            param.put("kd_diagnosa1", Sequel.cariIsi("select b.kd_penyakit FROM diagnosa_pasien as a, penyakit as b, "
                    + "reg_periksa as c,pasien as d WHERE a.no_rawat = c.no_rawat AND "
                    + "c.no_rkm_medis = d.no_rkm_medis AND a.kd_penyakit = b.kd_penyakit AND a.prioritas = '1' "
                    + "AND a.no_rawat =?", tbObat1.getValueAt(tbObat1.getSelectedRow(), 1).toString()));
            param.put("kd_diagnosa2", Sequel.cariIsi("select b.kd_penyakit FROM diagnosa_pasien as a, penyakit as b, "
                    + "reg_periksa as c,pasien as d WHERE a.no_rawat = c.no_rawat AND "
                    + "c.no_rkm_medis = d.no_rkm_medis AND a.kd_penyakit = b.kd_penyakit AND a.prioritas = '2' "
                    + "AND a.no_rawat =?", tbObat1.getValueAt(tbObat1.getSelectedRow(), 1).toString()));
            param.put("kd_icd9_1", Sequel.cariIsi("select b.kode FROM prosedur_pasien as a, icd9 as b, "
                    + "reg_periksa as c,pasien as d WHERE a.no_rawat = c.no_rawat AND "
                    + "c.no_rkm_medis = d.no_rkm_medis AND a.kode = b.kode AND a.prioritas = '1' "
                    + "AND a.no_rawat =?", tbObat1.getValueAt(tbObat1.getSelectedRow(), 1).toString()));
            param.put("kd_icd9_2", Sequel.cariIsi("select b.kode FROM prosedur_pasien as a, icd9 as b, "
                    + "reg_periksa as c,pasien as d WHERE a.no_rawat = c.no_rawat AND "
                    + "c.no_rkm_medis = d.no_rkm_medis AND a.kode = b.kode AND a.prioritas = '2' "
                    + "AND a.no_rawat =?", tbObat1.getValueAt(tbObat1.getSelectedRow(), 1).toString()));
            param.put("icd9_1", Sequel.cariIsi("select b.deskripsi_pendek FROM prosedur_pasien as a, icd9 as b, "
                    + "reg_periksa as c,pasien as d WHERE a.no_rawat = c.no_rawat AND "
                    + "c.no_rkm_medis = d.no_rkm_medis AND a.kode = b.kode AND a.prioritas = '1' "
                    + "AND a.no_rawat =?", tbObat1.getValueAt(tbObat1.getSelectedRow(), 1).toString()));
            param.put("icd9_2", Sequel.cariIsi("select b.deskripsi_pendek FROM prosedur_pasien as a, icd9 as b, "
                    + "reg_periksa as c,pasien as d WHERE a.no_rawat = c.no_rawat AND "
                    + "c.no_rkm_medis = d.no_rkm_medis AND a.kode = b.kode AND a.prioritas = '2' "
                    + "AND a.no_rawat =?", tbObat1.getValueAt(tbObat1.getSelectedRow(), 1).toString()));
            param.put("pemeriksaan", Sequel.cariIsi("select pemeriksaan from pemeriksaan_ralan where no_rawat=?", tbObat1.getValueAt(tbObat1.getSelectedRow(), 1).toString()));
            param.put("keluhan", Sequel.cariIsi("select keluhan from pemeriksaan_ralan where no_rawat=?", tbObat1.getValueAt(tbObat1.getSelectedRow(), 1).toString()));
            param.put("tensi", Sequel.cariIsi("select tensi from pemeriksaan_ralan where no_rawat=?", tbObat1.getValueAt(tbObat1.getSelectedRow(), 1).toString()));
            param.put("nadi", Sequel.cariIsi("select nadi from pemeriksaan_ralan where no_rawat=?", tbObat1.getValueAt(tbObat1.getSelectedRow(), 1).toString()));
            param.put("respirasi", Sequel.cariIsi("select respirasi from pemeriksaan_ralan where no_rawat=?", tbObat1.getValueAt(tbObat1.getSelectedRow(), 1).toString()));

            Valid.MyReport("rptBridgingSEP10.jrxml", "report", "::[ Cetak SEP ]::", "select bridging_sep_internal.no_sep, adddate(bridging_sep_internal.tglrujukan, 85) as tgl_akhir,bridging_sep_internal.no_rawat,bridging_sep_internal.nomr,bridging_sep_internal.nama_pasien,bridging_sep_internal.tglsep,"
                    + "bridging_sep_internal.tglrujukan,bridging_sep_internal.no_rujukan,bridging_sep_internal.kdppkrujukan,"
                    + "bridging_sep_internal.nmppkrujukan,bridging_sep_internal.kdppkpelayanan,bridging_sep_internal.nmppkpelayanan,"
                    + "if(bridging_sep_internal.jnspelayanan='1','Rawat Inap','Rawat Jalan'),bridging_sep_internal.catatan,bridging_sep_internal.diagawal,"
                    + "bridging_sep_internal.nmdiagnosaawal,bridging_sep_internal.kdpolitujuan,bridging_sep_internal.nmpolitujuan,"
                    + "if(bridging_sep_internal.klsrawat='1','Kelas 1',if(bridging_sep_internal.klsrawat='2','Kelas 2','Kelas 3')),"
                    + "if(bridging_sep_internal.lakalantas='0','Kasus Kecelakaan','Bukan Kasus Kecelakaan'),concat(bridging_sep_internal.nmkec,', ',bridging_sep_internal.nmkab,', ',bridging_sep_internal.nmprop) as lokasilaka,bridging_sep_internal.user, "
                    + "bridging_sep_internal.tanggal_lahir,bridging_sep_internal.peserta,bridging_sep_internal.jkel,bridging_sep_internal.no_kartu,bridging_sep_internal.asal_rujukan,bridging_sep_internal.eksekutif,bridging_sep_internal.cob,bridging_sep_internal.notelep from bridging_sep_internal where no_sep='" + tbObat1.getValueAt(tbObat1.getSelectedRow(), 0).toString() + "'", param);

            this.setCursor(Cursor.getDefaultCursor());
        } else {
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data SEP yang mau dicetak...!!!!");
            BtnBatal.requestFocus();
        }
    }//GEN-LAST:event_ppSEP9BtnPrintActionPerformed

    private void ppPulang1BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPulang1BtnPrintActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ppPulang1BtnPrintActionPerformed

    private void ppDetailSEPPeserta1BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppDetailSEPPeserta1BtnPrintActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ppDetailSEPPeserta1BtnPrintActionPerformed

    private void ppRujukan1BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppRujukan1BtnPrintActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ppRujukan1BtnPrintActionPerformed

    private void ppSuratKontrol1BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSuratKontrol1BtnPrintActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ppSuratKontrol1BtnPrintActionPerformed

    private void ppRiwayatPerawatan1BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppRiwayatPerawatan1BtnPrintActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ppRiwayatPerawatan1BtnPrintActionPerformed

    private void ppHapusBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusBtnPrintActionPerformed
        if (tbObat1.getSelectedRow() != -1) {
            try {
                bodyWithDeleteRequestInternal();
            } catch (Exception ex) {
                System.out.println("Notifikasi Bridging : " + ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Silahkan pilih dulu data yang mau dihapus..!!");
        }
    }//GEN-LAST:event_ppHapusBtnPrintActionPerformed

    private void LakaLantasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_LakaLantasItemStateChanged
        if (LakaLantas.getSelectedIndex() == 0) {
            jLabel38.setVisible(false);
            TanggalKKL.setVisible(false);
            jLabel36.setVisible(false);
            Keterangan.setVisible(false);
            jLabel40.setVisible(false);
            Suplesi.setVisible(false);
            jLabel41.setVisible(false);
            NoSEPSuplesi.setVisible(false);
            LabelPoli3.setVisible(false);
            KdPropinsi.setVisible(false);
            NmPropinsi.setVisible(false);
            LabelPoli4.setVisible(false);
            KdKabupaten.setVisible(false);
            NmKabupaten.setVisible(false);
            LabelPoli5.setVisible(false);
            KdKecamatan.setVisible(false);
            NmKecamatan.setVisible(false);
            jLabel28.setVisible(false);
            ChkAsa.setVisible(false);
            ChkBPJSTenaga.setVisible(false);
            ChkJasaRaharja.setVisible(false);
            ChkTaspen.setVisible(false);
            btnPropinsi.setVisible(false);
            btnKabupaten.setVisible(false);
            btnKecamatan.setVisible(false);
        } else {
            jLabel38.setVisible(true);
            TanggalKKL.setVisible(true);
            jLabel36.setVisible(true);
            Keterangan.setVisible(true);
            jLabel40.setVisible(true);
            Suplesi.setVisible(true);
            jLabel41.setVisible(true);
            NoSEPSuplesi.setVisible(true);
            LabelPoli3.setVisible(true);
            KdPropinsi.setVisible(true);
            NmPropinsi.setVisible(true);
            LabelPoli4.setVisible(true);
            KdKabupaten.setVisible(true);
            NmKabupaten.setVisible(true);
            LabelPoli5.setVisible(true);
            KdKecamatan.setVisible(true);
            NmKecamatan.setVisible(true);
            btnPropinsi.setVisible(true);
            btnKabupaten.setVisible(true);
            btnKecamatan.setVisible(true);
            jLabel28.setVisible(true);
        }
    }//GEN-LAST:event_LakaLantasItemStateChanged

    private void tbFaskes5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbFaskes5MouseClicked
    if (tabMode5.getRowCount() != 0) {
            try {
                getDataRujukKhusus();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbFaskes5MouseClicked

    private void tbFaskes5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbFaskes5KeyPressed
        if (tabMode5.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataRujukKhusus();
                } catch (java.lang.NullPointerException e) {
                }
            }
            WindowCariNoRujuk.dispose();
        }
    }//GEN-LAST:event_tbFaskes5KeyPressed

    private void bulanRujukKhususKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bulanRujukKhususKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_bulanRujukKhususKeyPressed

    private void BtnCariRujukKhususActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariRujukKhususActionPerformed
        String month = "";
        if (bulanRujukKhusus.getSelectedIndex() == 9 || bulanRujukKhusus.getSelectedIndex() == 10 || bulanRujukKhusus.getSelectedIndex() == 11) {
            month = bulanRujukKhusus.getSelectedItem().toString().substring(6, 8);
        } else {
            month = bulanRujukKhusus.getSelectedItem().toString().substring(6, 7);
        }
        tampilRujukKhusus(month);
    }//GEN-LAST:event_BtnCariRujukKhususActionPerformed

    private void BtnCariRujukKhususKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariRujukKhususKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCariRujukKhususKeyPressed

    private void btnDPJP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDPJP1ActionPerformed
        dokter.setPoli(KdPoli.getText(), NmPoli.getText());
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDPJP1ActionPerformed

    private void btnDPJP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDPJP1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDPJP1KeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            BPJSDataSEP dialog = new BPJSDataSEP(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.ComboBox AsalRujukan;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnCari2;
    private widget.Button BtnCariPenyakit1;
    private widget.Button BtnCariProsedur;
    private widget.Button BtnCariRujukKhusus;
    private widget.Button BtnCloseIn4;
    private widget.Button BtnCloseIn5;
    private widget.Button BtnCloseIn6;
    private widget.Button BtnCloseIn8;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluar1;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpan4;
    private widget.Button BtnSimpan5;
    private widget.Button BtnSimpan6;
    private widget.Button BtnSimpan8;
    private widget.ComboBox COB;
    private widget.TextBox Catatan;
    private widget.TextBox Catatan1;
    private widget.CekBox ChkAsa;
    private widget.CekBox ChkBPJSTenaga;
    private widget.CekBox ChkJasaRaharja;
    private widget.CekBox ChkTaspen;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPCari3;
    private widget.Tanggal DTPCari4;
    public widget.TextBox Diagnosa;
    private widget.ComboBox Eksekutif;
    private widget.PanelBiasa FormInput;
    private widget.TextBox JK;
    private widget.ComboBox JenisPelayanan;
    private widget.ComboBox JenisPelayanan1;
    private widget.TextBox JenisPeserta;
    private widget.ComboBox Katarak;
    private widget.TextBox KdDPJP;
    private widget.TextBox KdDPJPLayan;
    private widget.TextBox KdKabupaten;
    private widget.TextBox KdKecamatan;
    private widget.TextBox KdPPK;
    private widget.TextBox KdPenyakit;
    private widget.TextBox KdPenyakit1;
    private widget.TextBox KdPoli;
    private widget.TextBox KdPoli1;
    private widget.TextBox KdPpkRujukan;
    private widget.TextBox KdPpkRujukan1;
    private widget.TextBox KdPropinsi;
    private widget.ComboBox Kelas;
    private widget.ComboBox Kelas1;
    private widget.TextBox Keterangan;
    private widget.Label LCount;
    private widget.Label LCount1;
    private widget.Label LabelKelas;
    private widget.Label LabelKelas1;
    private widget.Label LabelKelas2;
    private widget.Label LabelPoli;
    private widget.Label LabelPoli1;
    private widget.Label LabelPoli2;
    private widget.Label LabelPoli3;
    private widget.Label LabelPoli4;
    private widget.Label LabelPoli5;
    private widget.Label LabelPoli6;
    private widget.ComboBox LakaLantas;
    private widget.TextBox NmDPJP;
    private widget.TextBox NmDPJPLayan;
    private widget.TextBox NmKabupaten;
    private widget.TextBox NmKecamatan;
    private widget.TextBox NmPPK;
    private widget.TextBox NmPenyakit;
    private widget.TextBox NmPenyakit1;
    private widget.TextBox NmPoli;
    private widget.TextBox NmPoli1;
    private widget.TextBox NmPpkRujukan;
    private widget.TextBox NmPpkRujukan1;
    private widget.TextBox NmPropinsi;
    private widget.TextBox NoBalasan;
    private widget.TextBox NoKartu;
    private widget.TextBox NoRujukan;
    private widget.TextBox NoSEP;
    private widget.TextBox NoSEPSuplesi;
    private widget.TextBox NoSKDP;
    private widget.TextBox NoTelp;
    private javax.swing.JPopupMenu Popup;
    private javax.swing.JPopupMenu Popup1;
    private javax.swing.JPopupMenu Popup2;
    private javax.swing.JPopupMenu Popup3;
    private widget.TextBox Prosedur;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.TextBox Status;
    private widget.ComboBox Suplesi;
    private widget.TextBox TCari;
    private widget.TextBox TCari1;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private javax.swing.JTabbedPane TabRujukan;
    private widget.Tanggal TanggalKKL;
    private widget.Tanggal TanggalKunjungan;
    private widget.Tanggal TanggalPulang;
    private widget.Tanggal TanggalRujuk;
    private widget.Tanggal TanggalRujukKeluar;
    private widget.Tanggal TanggalSEP;
    private widget.TextBox TglLahir;
    private widget.ComboBox TipeRujukan;
    private javax.swing.JDialog WindowCariNoRujuk;
    private javax.swing.JDialog WindowCariSEP;
    private javax.swing.JDialog WindowRujukKhusus;
    private javax.swing.JDialog WindowRujukan;
    private javax.swing.JDialog WindowUpdatePulang;
    private widget.Button btnDPJP;
    private widget.Button btnDPJP1;
    private widget.Button btnDiagnosa;
    private widget.Button btnDiagnosa1;
    private widget.Button btnKabupaten;
    private widget.Button btnKecamatan;
    private widget.Button btnPPKRujukan;
    private widget.Button btnPPKRujukan1;
    private widget.Button btnPoli;
    private widget.Button btnPoli1;
    private widget.Button btnPropinsi;
    private widget.Button btnRujukan;
    private widget.Button btnSKDP;
    private widget.Button btnSPRI;
    private widget.ComboBox bulanRujukKhusus;
    private widget.ComboBox cmbAsesment;
    private widget.ComboBox cmbHlm;
    private widget.ComboBox cmbKunjungan;
    private widget.ComboBox cmbPembiayaan;
    private widget.ComboBox cmbPenunjang;
    private widget.ComboBox cmbProcedure;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame10;
    private widget.InternalFrame internalFrame11;
    private widget.InternalFrame internalFrame12;
    private widget.InternalFrame internalFrame13;
    private widget.InternalFrame internalFrame14;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
    private widget.InternalFrame internalFrame7;
    private widget.InternalFrame internalFrame8;
    private widget.InternalFrame internalFrame9;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel4;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private widget.Label lblAsesment;
    private widget.Label lblPembiayaan;
    private widget.Label lblPenunjang;
    private widget.Label lblProcedure;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.panelisi panelisi1;
    private widget.TextBox pnj;
    private javax.swing.JMenuItem ppAmbilSep;
    private javax.swing.JMenuItem ppDetailSEPPeserta;
    private javax.swing.JMenuItem ppDetailSEPPeserta1;
    private javax.swing.JMenuItem ppHapus;
    private javax.swing.JMenuItem ppPengajuan;
    private javax.swing.JMenuItem ppPengajuan1;
    private javax.swing.JMenuItem ppPulang;
    private javax.swing.JMenuItem ppPulang1;
    private javax.swing.JMenuItem ppRiwayatPerawatan;
    private javax.swing.JMenuItem ppRiwayatPerawatan1;
    private javax.swing.JMenuItem ppRujukKhusus;
    private javax.swing.JMenuItem ppRujukKhusus1;
    private javax.swing.JMenuItem ppRujukan;
    private javax.swing.JMenuItem ppRujukan1;
    private javax.swing.JMenuItem ppSEP;
    private javax.swing.JMenuItem ppSEP1;
    private javax.swing.JMenuItem ppSEP2;
    private javax.swing.JMenuItem ppSEP3;
    private javax.swing.JMenuItem ppSEP4;
    private javax.swing.JMenuItem ppSEP9;
    private javax.swing.JMenuItem ppSuratKontrol;
    private javax.swing.JMenuItem ppSuratKontrol1;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.Table tbDiagnosa;
    private widget.Table tbFaskes1;
    private widget.Table tbFaskes2;
    private widget.Table tbFaskes3;
    private widget.Table tbFaskes4;
    private widget.Table tbFaskes5;
    private widget.Table tbObat;
    private widget.Table tbObat1;
    public widget.Table tbProsedur;
    private widget.Label tjKunjungan;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement(
                    "select bridging_sep.no_sep, bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tglsep,"
                    + "bridging_sep.tglrujukan,bridging_sep.no_rujukan,bridging_sep.kdppkrujukan,"
                    + "bridging_sep.nmppkrujukan,bridging_sep.kdppkpelayanan,bridging_sep.nmppkpelayanan,"
                    + "if(bridging_sep.jnspelayanan='1','1. Ranap','2. Ralan'),bridging_sep.catatan,bridging_sep.diagawal,"
                    + "bridging_sep.nmdiagnosaawal,bridging_sep.kdpolitujuan,bridging_sep.nmpolitujuan,"
                    + "if(bridging_sep.klsrawat='1','1. Kelas 1',if(bridging_sep.klsrawat='2','2. Kelas 2','3. Kelas 3')),"
                    + "if(bridging_sep.lakalantas='0','0. Tidak','1. Ya'),bridging_sep.user, "
                    + "bridging_sep.tanggal_lahir,bridging_sep.peserta,bridging_sep.jkel,bridging_sep.no_kartu,bridging_sep.tglpulang,"
                    + "bridging_sep.asal_rujukan,bridging_sep.eksekutif,bridging_sep.cob,bridging_sep.penjamin,bridging_sep.notelep,"
                    + "bridging_sep.katarak,bridging_sep.tglkkl,bridging_sep.keterangankkl,"
                    + "bridging_sep.suplesi,bridging_sep.no_sep_suplesi,bridging_sep.kdprop,"
                    + "bridging_sep.nmprop,bridging_sep.kdkab,"
                    + "bridging_sep.nmkab,bridging_sep.kdkec,bridging_sep.nmkec,bridging_sep.noskdp,"
                    + "bridging_sep.kddpjp,bridging_sep.nmdpdjp from bridging_sep where "
                    + "bridging_sep.tglsep between ? and ? and bridging_sep.no_sep like ? or "
                    + "bridging_sep.tglsep between ? and ? and bridging_sep.nomr like ? or "
                    + "bridging_sep.tglsep between ? and ? and bridging_sep.nama_pasien like ? or "
                    + "bridging_sep.tglsep between ? and ? and bridging_sep.nmppkrujukan like ? or "
                    + "bridging_sep.tglsep between ? and ? and bridging_sep.diagawal like ? or "
                    + "bridging_sep.tglsep between ? and ? and bridging_sep.nmdiagnosaawal like ? or "
                    + "bridging_sep.tglsep between ? and ? and bridging_sep.no_rawat like ? or "
                    + "bridging_sep.tglsep between ? and ? and bridging_sep.no_kartu like ? or "
                    + "bridging_sep.tglsep between ? and ? and bridging_sep.nmprop like ? or "
                    + "bridging_sep.tglsep between ? and ? and bridging_sep.nmkab like ? or "
                    + "bridging_sep.tglsep between ? and ? and bridging_sep.nmkec like ? or "
                    + "bridging_sep.tglsep between ? and ? and bridging_sep.nmdpdjp like ? or "
                    + "bridging_sep.tglsep between ? and ? and bridging_sep.asal_rujukan like ? or "
                    + "bridging_sep.tglsep between ? and ? and bridging_sep.notelep like ? or "
                    + "bridging_sep.tglsep between ? and ? and bridging_sep.nmpolitujuan like ? order by bridging_sep.tglsep");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, "%" + TCari.getText().trim() + "%");
                ps.setString(4, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(5, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(6, "%" + TCari.getText().trim() + "%");
                ps.setString(7, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(8, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(9, "%" + TCari.getText().trim() + "%");
                ps.setString(10, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(11, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(12, "%" + TCari.getText().trim() + "%");
                ps.setString(13, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(14, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(15, "%" + TCari.getText().trim() + "%");
                ps.setString(16, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(17, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(18, "%" + TCari.getText().trim() + "%");
                ps.setString(19, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(20, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(21, "%" + TCari.getText().trim() + "%");
                ps.setString(22, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(23, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(24, "%" + TCari.getText().trim() + "%");
                ps.setString(22, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(23, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(24, "%" + TCari.getText().trim() + "%");
                ps.setString(25, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(26, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(27, "%" + TCari.getText().trim() + "%");
                ps.setString(28, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(29, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(30, "%" + TCari.getText().trim() + "%");
                ps.setString(31, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(32, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(33, "%" + TCari.getText().trim() + "%");
                ps.setString(34, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(35, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(36, "%" + TCari.getText().trim() + "%");
                ps.setString(37, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(38, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(39, "%" + TCari.getText().trim() + "%");
                ps.setString(40, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(41, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(42, "%" + TCari.getText().trim() + "%");
                ps.setString(43, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(44, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(45, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new Object[]{
                        rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
                        rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12),
                        rs.getString(13), rs.getString(14), rs.getString(14) + " " + rs.getString(15), rs.getString(16),
                        rs.getString(17), rs.getString(18), rs.getString(19), rs.getString(20),
                        rs.getString(21), rs.getString(22), rs.getString(23), rs.getString(24),
                        rs.getString(25), rs.getString(26), rs.getString(27), rs.getString(28),
                        rs.getString(29).replaceAll("1", "1.Jasa Raharja").replaceAll("2", "2.BPJS").replaceAll("3", "3.Taspen").replaceAll("4", "4.Asabri"),
                        rs.getString(30), rs.getString(31), rs.getString(32),
                        rs.getString(33), rs.getString(34), rs.getString(35), rs.getString(36),
                        rs.getString(37), rs.getString(38), rs.getString(39), rs.getString(40),
                        rs.getString(41), rs.getString(42), rs.getString(43), rs.getString(44)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabMode.getRowCount());
    }

    public void tampil_internal() {
        Valid.tabelKosong(tabModeInternal);
        try {
            ps = koneksi.prepareStatement(
                    "select bridging_sep_internal.no_sep, bridging_sep_internal.no_rawat,bridging_sep_internal.nomr,bridging_sep_internal.nama_pasien,bridging_sep_internal.tglsep,"
                    + "bridging_sep_internal.tglrujukan,bridging_sep_internal.no_rujukan,bridging_sep_internal.kdppkrujukan,"
                    + "bridging_sep_internal.nmppkrujukan,bridging_sep_internal.kdppkpelayanan,bridging_sep_internal.nmppkpelayanan,"
                    + "if(bridging_sep_internal.jnspelayanan='1','1. Ranap','2. Ralan'),bridging_sep_internal.catatan,bridging_sep_internal.diagawal,"
                    + "bridging_sep_internal.nmdiagnosaawal,bridging_sep_internal.kdpolitujuan,bridging_sep_internal.nmpolitujuan,"
                    + "if(bridging_sep_internal.klsrawat='1','1. Kelas 1',if(bridging_sep_internal.klsrawat='2','2. Kelas 2','3. Kelas 3')),"
                    + "if(bridging_sep_internal.lakalantas='0','0. Tidak','1. Ya'),bridging_sep_internal.user, "
                    + "bridging_sep_internal.tanggal_lahir,bridging_sep_internal.peserta,bridging_sep_internal.jkel,bridging_sep_internal.no_kartu,bridging_sep_internal.tglpulang,"
                    + "bridging_sep_internal.asal_rujukan,bridging_sep_internal.eksekutif,bridging_sep_internal.cob,bridging_sep_internal.notelep,"
                    + "bridging_sep_internal.katarak,bridging_sep_internal.tglkkl,bridging_sep_internal.keterangankkl,"
                    + "bridging_sep_internal.suplesi,bridging_sep_internal.no_sep_suplesi,bridging_sep_internal.kdprop,"
                    + "bridging_sep_internal.nmprop,bridging_sep_internal.kdkab,"
                    + "bridging_sep_internal.nmkab,bridging_sep_internal.kdkec,bridging_sep_internal.nmkec,bridging_sep_internal.noskdp,"
                    + "bridging_sep_internal.kddpjp,bridging_sep_internal.nmdpdjp from bridging_sep_internal where "
                    + "bridging_sep_internal.tglsep between ? and ? and (bridging_sep_internal.no_sep like ? or "
                    + "bridging_sep_internal.nomr like ? or "
                    + "bridging_sep_internal.nama_pasien like ? or "
                    + "bridging_sep_internal.nmppkrujukan like ? or "
                    + "bridging_sep_internal.diagawal like ? or "
                    + "bridging_sep_internal.nmdiagnosaawal like ? or "
                    + "bridging_sep_internal.no_rawat like ? or "
                    + "bridging_sep_internal.no_kartu like ? or "
                    + "bridging_sep_internal.nmprop like ? or "
                    + "bridging_sep_internal.nmkab like ? or "
                    + "bridging_sep_internal.nmkec like ? or "
                    + "bridging_sep_internal.nmdpdjp like ? or "
                    + "bridging_sep_internal.asal_rujukan like ? or "
                    + "bridging_sep_internal.notelep like ? or "
                    + "bridging_sep_internal.nmpolitujuan like ?) order by bridging_sep_internal.tglsep");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, "%" + TCari1.getText().trim() + "%");
                ps.setString(4, "%" + TCari1.getText().trim() + "%");
                ps.setString(5, "%" + TCari1.getText().trim() + "%");
                ps.setString(6, "%" + TCari1.getText().trim() + "%");
                ps.setString(7, "%" + TCari1.getText().trim() + "%");
                ps.setString(8, "%" + TCari1.getText().trim() + "%");
                ps.setString(9, "%" + TCari1.getText().trim() + "%");
                ps.setString(10, "%" + TCari1.getText().trim() + "%");
                ps.setString(11, "%" + TCari1.getText().trim() + "%");
                ps.setString(12, "%" + TCari1.getText().trim() + "%");
                ps.setString(13, "%" + TCari1.getText().trim() + "%");
                ps.setString(14, "%" + TCari1.getText().trim() + "%");
                ps.setString(15, "%" + TCari1.getText().trim() + "%");
                ps.setString(16, "%" + TCari1.getText().trim() + "%");
                ps.setString(17, "%" + TCari1.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeInternal.addRow(new Object[]{
                        rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
                        rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12),
                        rs.getString(13), rs.getString(14), rs.getString(14) + " " + rs.getString(15), rs.getString(16),
                        rs.getString(17), rs.getString(18), rs.getString(19), rs.getString(20),
                        rs.getString(21), rs.getString(22), rs.getString(23), rs.getString(24),
                        rs.getString(25), rs.getString(26), rs.getString(27), rs.getString(28),
                        rs.getString(29).replaceAll("1", "1.Jasa Raharja").replaceAll("2", "2.BPJS").replaceAll("3", "3.Taspen").replaceAll("4", "4.Asabri"),
                        rs.getString(30), rs.getString(31), rs.getString(32),
                        rs.getString(33), rs.getString(34), rs.getString(35), rs.getString(36),
                        rs.getString(37), rs.getString(38), rs.getString(39), rs.getString(40),
                        rs.getString(41), rs.getString(42)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabModeInternal.getRowCount());
    }

    public void tampilPasien() {
        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement(
                    "select bridging_sep.no_sep, bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tglsep,"
                    + "bridging_sep.tglrujukan,bridging_sep.no_rujukan,bridging_sep.kdppkrujukan,"
                    + "bridging_sep.nmppkrujukan,bridging_sep.kdppkpelayanan,bridging_sep.nmppkpelayanan,"
                    + "if(bridging_sep.jnspelayanan='1','1. Ranap','2. Ralan'),bridging_sep.catatan,bridging_sep.diagawal,"
                    + "bridging_sep.nmdiagnosaawal,bridging_sep.kdpolitujuan,bridging_sep.nmpolitujuan,"
                    + "if(bridging_sep.klsrawat='1','1. Kelas 1',if(bridging_sep.klsrawat='2','2. Kelas 2','3. Kelas 3')),"
                    + "if(bridging_sep.lakalantas='0','0. Tidak','1. Ya'),bridging_sep.user, "
                    + "bridging_sep.tanggal_lahir,bridging_sep.peserta,bridging_sep.jkel,bridging_sep.no_kartu,bridging_sep.tglpulang,"
                    + "bridging_sep.asal_rujukan,bridging_sep.eksekutif,bridging_sep.cob,bridging_sep.penjamin,bridging_sep.notelep,"
                    + "bridging_sep.katarak,bridging_sep.tglkkl,bridging_sep.keterangankkl,"
                    + "bridging_sep.suplesi,bridging_sep.no_sep_suplesi,bridging_sep.kdprop,"
                    + "bridging_sep.nmprop,bridging_sep.kdkab,"
                    + "bridging_sep.nmkab,bridging_sep.kdkec,bridging_sep.nmkec,bridging_sep.noskdp,"
                    + "bridging_sep.kddpjp,bridging_sep.nmdpdjp from bridging_sep where "
                    + "bridging_sep.nomr like ? "
                    + "order by bridging_sep.tglsep DESC LIMIT " + cmbHlm.getSelectedItem());
            try {
                ps.setString(1, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new Object[]{
                        rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
                        rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12),
                        rs.getString(13), rs.getString(14), rs.getString(14) + " " + rs.getString(15), rs.getString(16),
                        rs.getString(17), rs.getString(18), rs.getString(19), rs.getString(20),
                        rs.getString(21), rs.getString(22), rs.getString(23), rs.getString(24),
                        rs.getString(25), rs.getString(26), rs.getString(27), rs.getString(28),
                        rs.getString(29).replaceAll("1", "1.Jasa Raharja").replaceAll("2", "2.BPJS").replaceAll("3", "3.Taspen").replaceAll("4", "4.Asabri"),
                        rs.getString(30), rs.getString(31), rs.getString(32),
                        rs.getString(33), rs.getString(34), rs.getString(35), rs.getString(36),
                        rs.getString(37), rs.getString(38), rs.getString(39), rs.getString(40),
                        rs.getString(41), rs.getString(42), rs.getString(43), rs.getString(44)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabMode.getRowCount());
    }

    private void isRawat() {
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ", TNoRM, TNoRw.getText());
        Sequel.cariIsi("SELECT tgl_registrasi FROM reg_periksa WHERE no_rawat='"+TNoRw.getText()+"'",TanggalSEP);
        Catatan.setText("-");
    }
    
    private void cekDiagnosa(){
        Sequel.cariIsi("SELECT kd_penyakit FROM diagnosa_pasien WHERE no_rawat = ? AND prioritas = 1 AND status = 'Ralan' LIMIT 1",KdPenyakit,TNoRw.getText());
        Sequel.cariIsi("SELECT nm_penyakit FROM penyakit WHERE kd_penyakit = ? LIMIT 1",NmPenyakit,KdPenyakit.getText());
    }

    private void emptTeks() {
        TNoRw.setText("");
        TPasien.setText("");
        TanggalSEP.setDate(new Date());
        TanggalRujuk.setDate(new Date());
        TglLahir.setText("");
        NoKartu.setText("");
        JenisPeserta.setText("");
        Status.setText("");
        JK.setText("");
        NoRujukan.setText("");
        KdPpkRujukan.setText("");
        NmPpkRujukan.setText("");
        JenisPelayanan.setSelectedIndex(1);
        Catatan.setText("");
        KdPenyakit.setText("");
        NmPenyakit.setText("");
        KdPoli.setText("");
        NmPoli.setText("");
        Kelas.setSelectedIndex(2);
        LakaLantas.setSelectedIndex(0);
        TNoRM.setText("");
        NoSKDP.setText("");
        KdDPJP.setText("");
        NmDPJP.setText("");
        Keterangan.setText("");
        NoSEPSuplesi.setText("");
        KdPropinsi.setText("");
        NmPropinsi.setText("");
        KdKabupaten.setText("");
        NmKabupaten.setText("");
        KdKecamatan.setText("");
        NmKecamatan.setText("");
        Katarak.setSelectedIndex(0);
        Suplesi.setSelectedIndex(0);
        TanggalKKL.setDate(new Date());
        ChkAsa.setSelected(false);
        ChkBPJSTenaga.setSelected(false);
        ChkJasaRaharja.setSelected(false);
        ChkTaspen.setSelected(false);
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(rujuk_masuk.no_rawat,4),signed)),0) from reg_periksa inner join rujuk_masuk on reg_periksa.no_rawat=rujuk_masuk.no_rawat where reg_periksa.tgl_registrasi='" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "' ", "BR/" + dateformat.format(TanggalSEP.getDate()) + "/", 4, NoBalasan);
        NoRujukan.requestFocus();
    }

    public void setNoRm(String norwt, Date tgl1, String status, String kdpoli, String namapoli) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        TCari1.setText(norwt);
        KdPoli.setText(Sequel.cariIsi("select kd_poli_bpjs from maping_poli_bpjs where kd_poli_rs=?", kdpoli));
        NmPoli.setText(Sequel.cariIsi("select nm_poli_bpjs from maping_poli_bpjs where kd_poli_bpjs=?", KdPoli.getText()));
        JenisPelayanan.setSelectedItem(status);
        JenisPelayananItemStateChanged(null);
        isRawat();
    }

    public void setNoRm2(String norwt, Date tgl1, String status, String kdpoli, String namapoli, String kddokter) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        TCari1.setText(norwt);
        KdPoli.setText(Sequel.cariIsi("select kd_poli_bpjs from maping_poli_bpjs where kd_poli_rs=?", kdpoli));
        NmPoli.setText(Sequel.cariIsi("select nm_poli_bpjs from maping_poli_bpjs where kd_poli_bpjs=?", KdPoli.getText()));
        KdDPJPLayan.setText(Sequel.cariIsi("select kd_dokter_bpjs from maping_dokter_dpjpvclaim where kd_dokter=?", kddokter));
        NmDPJPLayan.setText(Sequel.cariIsi("select nm_dokter_bpjs from maping_dokter_dpjpvclaim where kd_dokter=?", kddokter));
        JenisPelayanan.setSelectedItem(status);
        JenisPelayananItemStateChanged(null);
        isRawat();
        if(kdpoli == "IGDK"){
            cekDiagnosa();
        }
    }

    public void isCek() {
        BtnSimpan.setEnabled(var.getbpjs_sep());
//        System.out.println(var.getbpjs_sep());
        BtnHapus.setEnabled(var.getbpjs_sep());
        BtnPrint.setEnabled(var.getbpjs_sep());
        BtnEdit.setEnabled(var.getbpjs_sep());
        ppDetailSEPPeserta.setEnabled(var.getbpjs_sep());
        ppPengajuan.setEnabled(var.getbpjs_sep());
        ppPengajuan1.setEnabled(var.getbpjs_sep());
        ppPulang.setEnabled(var.getbpjs_sep());
        ppSEP.setEnabled(var.getbpjs_sep());
        ppRiwayatPerawatan.setEnabled(var.getresume_pasien());
        ppRujukan.setEnabled(var.getbpjs_rujukan_keluar());
    }

    public void tutupInput() {
        TabRawat.setSelectedIndex(1);
    }

    private void getData() {
        if (tbObat.getSelectedRow() != -1) {
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 3).toString());
            NoRujukan.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString());
            KdPpkRujukan.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString());
            NmPpkRujukan.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString());
            KdPPK.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString());
            NmPPK.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 10).toString());
            JenisPelayanan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString());
            Catatan.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 12).toString());
            KdPenyakit.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString());
            NmPenyakit.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 14).toString().replaceAll(KdPenyakit.getText(), ""));
            KdPoli.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 15).toString());
            NmPoli.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 16).toString());
            Kelas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 17).toString());
            LakaLantas.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 18).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 20).toString());
            JenisPeserta.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 21).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 22).toString());
            NoKartu.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 23).toString());
            AsalRujukan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 25).toString());
            Eksekutif.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 26).toString());
            COB.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 27).toString());
            if (tbObat.getValueAt(tbObat.getSelectedRow(), 28).toString().contains("1")) {
                ChkJasaRaharja.setSelected(true);
            } else {
                ChkJasaRaharja.setSelected(false);
            }
            if (tbObat.getValueAt(tbObat.getSelectedRow(), 28).toString().contains("2")) {
                ChkBPJSTenaga.setSelected(true);
            } else {
                ChkBPJSTenaga.setSelected(false);
            }
            if (tbObat.getValueAt(tbObat.getSelectedRow(), 28).toString().contains("3")) {
                ChkTaspen.setSelected(true);
            } else {
                ChkTaspen.setSelected(false);
            }
            if (tbObat.getValueAt(tbObat.getSelectedRow(), 28).toString().contains("3")) {
                ChkAsa.setSelected(true);
            } else {
                ChkAsa.setSelected(false);
            }
            NoTelp.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 29).toString());
            Katarak.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 30).toString());
            Keterangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 32).toString());
            Suplesi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 33).toString());
            NoSEPSuplesi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 34).toString());
            KdPropinsi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 35).toString());
            NmPropinsi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 36).toString());
            KdKabupaten.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 37).toString());
            NmKabupaten.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 38).toString());
            KdKecamatan.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 39).toString());
            NmKecamatan.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 40).toString());
            NoSKDP.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 41).toString());
            KdDPJP.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 42).toString());
            NmDPJP.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 43).toString());

            Valid.SetTgl(TanggalSEP, tbObat.getValueAt(tbObat.getSelectedRow(), 4).toString());
            Valid.SetTgl(TanggalRujuk, tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString());
            Valid.SetTgl(TanggalKKL, tbObat.getValueAt(tbObat.getSelectedRow(), 31).toString());
            Status.setText("AKTIF");

        }
    }

    public static class HttpEntityEnclosingDeleteRequest extends HttpEntityEnclosingRequestBase {

        public HttpEntityEnclosingDeleteRequest(final URI uri) {
            super();
            setURI(uri);
        }

        @Override
        public String getMethod() {
            return "DELETE";
        }
    }

    @Test
    public void bodyWithDeleteRequest() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
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

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory() {
            @Override
            protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
                if (HttpMethod.DELETE == httpMethod) {
                    return new HttpEntityEnclosingDeleteRequest(uri);
                }
                return super.createHttpUriRequest(httpMethod, uri);
            }
        };
        factory.getHttpClient().getConnectionManager().getSchemeRegistry().register(scheme);
        restTemplate.setRequestFactory(factory);

        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("X-Cons-ID", koneksiDB.ConsIdBpjs());
            headers.add("X-Timestamp", String.valueOf(api.GetUTCdatetimeAsString()));
            headers.add("X-Signature", api.getHmac());
            if (koneksiDB.UrlBpjs().contains("apijkn")) {
                headers.add("user_key", koneksiDB.UserKeyBpjs());
                URL = link + "/SEP/2.0/delete";
            } else {
                URL = link + "/SEP/Delete";
            }

            requestJson = "{\"request\":{\"t_sep\":{\"noSep\":\"" + tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString() + "\",\"user\":\"" + user + "\"}}}";
            requestEntity = new HttpEntity(requestJson, headers);
            root = mapper.readTree(restTemplate.exchange(URL, HttpMethod.DELETE, requestEntity, String.class).getBody());
            nameNode = root.path("metaData");
            System.out.println("code : " + nameNode.path("code").asText());
            System.out.println("message : " + nameNode.path("message").asText());
            if (nameNode.path("code").asText().equals("200")) {
                JOptionPane.showMessageDialog(null, nameNode.path("message").asText());
                Sequel.meghapus("bridging_sep", "no_sep", tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
                tampil();
                emptTeks();
            } else {
                JOptionPane.showMessageDialog(null, nameNode.path("message").asText());
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
            if (e.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(null, "Koneksi ke server BPJS terputus...!");
            }
        }
    }

    @Test
    public void bodyWithDeleteRequestInternal() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
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

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory() {
            @Override
            protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
                if (HttpMethod.DELETE == httpMethod) {
                    return new HttpEntityEnclosingDeleteRequest(uri);
                }
                return super.createHttpUriRequest(httpMethod, uri);
            }
        };
        factory.getHttpClient().getConnectionManager().getSchemeRegistry().register(scheme);
        restTemplate.setRequestFactory(factory);

        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("X-Cons-ID", koneksiDB.ConsIdBpjs());
            headers.add("X-Timestamp", String.valueOf(api.GetUTCdatetimeAsString()));
            headers.add("X-Signature", api.getHmac());
            headers.add("user_key", koneksiDB.UserKeyBpjs());
            URL = link + "/SEP/2.0/delete";

            requestJson = "{\"request\":{\"t_sep\":{\"noSep\":\"" + tbObat1.getValueAt(tbObat1.getSelectedRow(), 0).toString() + "\",\"user\":\"" + user + "\"}}}";
            requestEntity = new HttpEntity(requestJson, headers);
            root = mapper.readTree(restTemplate.exchange(URL, HttpMethod.DELETE, requestEntity, String.class).getBody());
            nameNode = root.path("metaData");
            System.out.println("code : " + nameNode.path("code").asText());
            System.out.println("message : " + nameNode.path("message").asText());
            if (nameNode.path("code").asText().equals("200")) {
                JOptionPane.showMessageDialog(null, nameNode.path("message").asText());
                Sequel.meghapus("bridging_sep_internal", "no_sep", tbObat1.getValueAt(tbObat1.getSelectedRow(), 0).toString());
                tampil();
                emptTeks();
            } else {
                JOptionPane.showMessageDialog(null, nameNode.path("message").asText());
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
            if (e.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(null, "Koneksi ke server BPJS terputus...!");
            }
        }
    }

    private void insertSEP() {
        try {
            jasaraharja = "";
            BPJS = "";
            Taspen = "";
            Asabri = "";
            penjamin = "";
            String penunjang = "";
            if (ChkJasaRaharja.isSelected() == true) {
                jasaraharja = "1,";
            }
            if (ChkBPJSTenaga.isSelected() == true) {
                BPJS = "2,";
            }
            if (ChkTaspen.isSelected() == true) {
                Taspen = "3,";
            }
            if (ChkAsa.isSelected() == true) {
                Asabri = "4,";
            }

            if ((ChkJasaRaharja.isSelected() == true) || (ChkBPJSTenaga.isSelected() == true) || (ChkTaspen.isSelected() == true) || (ChkAsa.isSelected() == true)) {
                penjamin = jasaraharja + BPJS + Taspen + Asabri + penjamin;
            } else {
                penjamin = "";
            }

            if (penjamin.endsWith(",")) {
                penjamin = penjamin.substring(0, penjamin.length() - 1);
            }

            tglkkl = "0000-00-00";
            if (LakaLantas.getSelectedIndex() == 1) {
                tglkkl = Valid.SetTgl(TanggalKKL.getSelectedItem() + "");
            }

            if (cmbPenunjang.getSelectedIndex() == 0) {
                penunjang = "";
            } else if (cmbPenunjang.getSelectedIndex() > 9) {
                penunjang = cmbPenunjang.getSelectedItem().toString().substring(0, 2);
            } else {
                penunjang = cmbPenunjang.getSelectedItem().toString().substring(0, 1);
            };
            
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("X-Cons-ID", koneksiDB.ConsIdBpjs());
            headers.add("X-Timestamp", String.valueOf(api.GetUTCdatetimeAsString()));
            headers.add("X-Signature", api.getHmac());
            headers.add("user_key", koneksiDB.UserKeyBpjs());
            URL = koneksiDB.UrlBpjs() + "/SEP/2.0/insert";
            klsRawat = "\"klsRawat\": {"
                    + "\"klsRawatHak\":\"" + Kelas.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"klsRawatNaik\":\"" + (Kelas1.getSelectedIndex() > 0 ? Kelas1.getSelectedItem().toString().substring(0, 1) : "") + "\","
                    + "\"pembiayaan\":\"" + (cmbPembiayaan.getSelectedIndex() > 0 ? cmbPembiayaan.getSelectedItem().toString().substring(0, 1) : "") + "\","
                    + "\"penanggungJawab\":\"" + (pnj.getText().equals("") ? "" : pnj.getText()) + "\""
                    + "},";
            sep2tambah = "\"tujuanKunj\":\"" + cmbKunjungan.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"flagProcedure\":\"" + (cmbProcedure.getSelectedIndex() > 0 ? cmbProcedure.getSelectedItem().toString().substring(0, 1) : "") + "\","
                    + "\"kdPenunjang\":\"" + penunjang + "\","
                    + "\"assesmentPel\":\"" + (cmbAsesment.getSelectedIndex() > 0 ? cmbAsesment.getSelectedItem().toString().substring(0, 1) : "") + "\",";
            if (JenisPelayanan.getSelectedIndex() == 1) {
                dpjlayan = "\"dpjpLayan\":\"" + KdDPJPLayan.getText() + "\",";
            } else {
                dpjlayan = "\"dpjpLayan\":\"\",";
            }

            penjaminan = "";

            requestJson = "{"
                    + "\"request\":"
                    + "{"
                    + "\"t_sep\":"
                    + "{"
                    + "\"noKartu\":\"" + NoKartu.getText() + "\","
                    + "\"tglSep\":\"" + Valid.SetTgl(TanggalSEP.getSelectedItem() + "") + "\","
                    + "\"ppkPelayanan\":\"" + KdPPK.getText() + "\","
                    + "\"jnsPelayanan\":\"" + JenisPelayanan.getSelectedItem().toString().substring(0, 1) + "\","
                    + klsRawat
                    + "\"noMR\":\"" + TNoRM.getText() + "\","
                    + "\"rujukan\": {"
                    + "\"asalRujukan\":\"" + AsalRujukan.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"tglRujukan\":\"" + Valid.SetTgl(TanggalRujuk.getSelectedItem() + "") + "\","
                    + "\"noRujukan\":\"" + NoRujukan.getText() + "\","
                    + "\"ppkRujukan\":\"" + KdPpkRujukan.getText() + "\""
                    + "},"
                    + "\"catatan\":\"" + Catatan.getText() + "\","
                    + "\"diagAwal\":\"" + KdPenyakit.getText() + "\","
                    + "\"poli\": {"
                    + "\"tujuan\": \"" + KdPoli.getText() + "\","
                    + "\"eksekutif\": \"" + Eksekutif.getSelectedItem().toString().substring(0, 1) + "\""
                    + "},"
                    + "\"cob\": {"
                    + "\"cob\": \"" + COB.getSelectedItem().toString().substring(0, 1) + "\""
                    + "},"
                    + "\"katarak\": {"
                    + "\"katarak\": \"" + Katarak.getSelectedItem().toString().substring(0, 1) + "\""
                    + "},"
                    + "\"jaminan\": {"
                    + "\"lakaLantas\":\"" + LakaLantas.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"penjamin\": {"
                    + penjaminan
                    + "\"tglKejadian\": \"" + tglkkl + "\","
                    + "\"keterangan\": \"" + Keterangan.getText() + "\","
                    + "\"suplesi\": {"
                    + "\"suplesi\": \"" + Suplesi.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"noSepSuplesi\": \"" + NoSEPSuplesi.getText() + "\","
                    + "\"lokasiLaka\": {"
                    + "\"kdPropinsi\": \"" + KdPropinsi.getText() + "\","
                    + "\"kdKabupaten\": \"" + KdKabupaten.getText() + "\","
                    + "\"kdKecamatan\": \"" + KdKecamatan.getText() + "\""
                    + "}"
                    + "}"
                    + "}"
                    + "},"
                    + sep2tambah
                    + "\"skdp\": {"
                    + "\"noSurat\": \"" + NoSKDP.getText() + "\","
                    + "\"kodeDPJP\": \"" + KdDPJP.getText() + "\""
                    + "},"
                    + dpjlayan
                    + "\"noTelp\": \"" + NoTelp.getText() + "\","
                    + "\"user\":\"" + user + "\""
                    + "}"
                    + "}"
                    + "}";
            System.out.println("JSON : " + requestJson);
            requestEntity = new HttpEntity(requestJson, headers);
            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
            nameNode = root.path("metaData");
            System.out.println("code : " + nameNode.path("code").asText());
            System.out.println("message : " + nameNode.path("message").asText());
            if (nameNode.path("code").asText().equals("200")) {
                if (koneksiDB.UrlBpjs().contains("apijkn")) {
                    JsonNode res1 = root.path("response");
                    String res = api.decrypt(res1.asText());
                    String lz = api.lzDecrypt(res);
                    response = mapper.readTree(lz);
                } else {
                    response = root.path("response");
                }
                response = response.path("sep").path("noSep");
                System.out.println("SEP : " + response.asText() + "'");
                if (Sequel.menyimpantf("bridging_sep", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "SEP", 44, new String[]{
                    response.asText(), TNoRw.getText(), Valid.SetTgl(TanggalSEP.getSelectedItem() + ""), Valid.SetTgl(TanggalRujuk.getSelectedItem() + ""),
                    NoRujukan.getText(), KdPpkRujukan.getText(), NmPpkRujukan.getText(), KdPPK.getText(), NmPPK.getText(),
                    JenisPelayanan.getSelectedItem().toString().substring(0, 1), Catatan.getText(), KdPenyakit.getText(),
                    NmPenyakit.getText(), KdPoli.getText(), NmPoli.getText(), Kelas.getSelectedItem().toString().substring(0, 1),
                    LakaLantas.getSelectedItem().toString().substring(0, 1), user, TNoRM.getText(), TPasien.getText(),
                    TglLahir.getText(), JenisPeserta.getText(), JK.getText(), NoKartu.getText(),
                    "0000-00-00 00:00:00", AsalRujukan.getSelectedItem().toString(), Eksekutif.getSelectedItem().toString(),
                    COB.getSelectedItem().toString(), penjamin, NoTelp.getText(), Katarak.getSelectedItem().toString(),
                    tglkkl, Keterangan.getText(), Suplesi.getSelectedItem().toString(),
                    NoSEPSuplesi.getText(), KdPropinsi.getText(), NmPropinsi.getText(), KdKabupaten.getText(), NmKabupaten.getText(),
                    KdKecamatan.getText(), NmKecamatan.getText(), NoSKDP.getText(), KdDPJPLayan.getText(), NmDPJPLayan.getText()
                }) == true) {
                    Sequel.menyimpan("rujuk_masuk", "?,?,?,?,?,?,?,?,?,?", 10, new String[]{
                        TNoRw.getText(), NmPpkRujukan.getText(), "-", NoRujukan.getText(), "0", NmPpkRujukan.getText(), KdPenyakit.getText(), "-",
                        "-", NoBalasan.getText()
                    });
                    if (!prb.equals("")) {
                        if (Sequel.menyimpantf("bpjs_prb", "?,?", "PRB", 2, new String[]{response.asText(), prb}) == true) {
                            prb = "";
                        }
                    }
                    emptTeks();
                    TabRawat.setSelectedIndex(1);
                    tampil();
                } else {
                    if (Sequel.menyimpantf("bridging_sep_internal", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "SEP", 52, new String[]{
                        response.asText(), TNoRw.getText(), Valid.SetTgl(TanggalSEP.getSelectedItem() + ""), Valid.SetTgl(TanggalRujuk.getSelectedItem() + ""), NoRujukan.getText(), KdPpkRujukan.getText(),
                        NmPpkRujukan.getText(), KdPPK.getText(), NmPPK.getText(), JenisPelayanan.getSelectedItem().toString().substring(0, 1), Catatan.getText(), KdPenyakit.getText(), NmPenyakit.getText(),
                        KdPoli.getText(), NmPoli.getText(), Kelas.getSelectedItem().toString().substring(0, 1), (Kelas1.getSelectedIndex() > 0 ? Kelas1.getSelectedItem().toString().substring(0, 1) : ""),
                        (cmbPembiayaan.getSelectedIndex() > 0 ? cmbPembiayaan.getSelectedItem().toString().substring(0, 1) : ""), (pnj.getText().equals("") ? "" : pnj.getText()),
                        LakaLantas.getSelectedItem().toString().substring(0, 1), user, TNoRM.getText(), TPasien.getText(), TglLahir.getText(), JenisPeserta.getText(), JK.getText(), NoKartu.getText(),
                        "0000-00-00 00:00:00", AsalRujukan.getSelectedItem().toString(), Eksekutif.getSelectedItem().toString(), COB.getSelectedItem().toString(), NoTelp.getText(), Katarak.getSelectedItem().toString(),
                        tglkkl, Keterangan.getText(), Suplesi.getSelectedItem().toString(), NoSEPSuplesi.getText(), KdPropinsi.getText(), NmPropinsi.getText(), KdKabupaten.getText(), NmKabupaten.getText(),
                        KdKecamatan.getText(), NmKecamatan.getText(), NoSKDP.getText(), KdDPJP.getText(), NmDPJP.getText(), cmbKunjungan.getSelectedItem().toString().substring(0, 1),
                        (cmbProcedure.getSelectedIndex() > 0 ? cmbProcedure.getSelectedItem().toString().substring(0, 1) : ""), (cmbPenunjang.getSelectedIndex() > 0 ? cmbPenunjang.getSelectedIndex() + "" : ""),
                        (cmbAsesment.getSelectedIndex() > 0 ? cmbAsesment.getSelectedItem().toString().substring(0, 1) : ""), KdDPJPLayan.getText(), NmDPJPLayan.getText()
                    }) == true) {
                        Sequel.menyimpan("rujuk_masuk", "?,?,?,?,?,?,?,?,?,?", 10, new String[]{
                            TNoRw.getText(), NmPpkRujukan.getText(), "-", NoRujukan.getText(), "0", NmPpkRujukan.getText(), KdPenyakit.getText(), "-",
                            "-", NoBalasan.getText()
                        });
                        if (!prb.equals("")) {
                            if (Sequel.menyimpantf("bpjs_prb", "?,?", "PRB", 2, new String[]{response.asText(), prb}) == true) {
                                prb = "";
                            }
                        }

                        emptTeks();
                        TabRawat.setSelectedIndex(2);
                        tampil_internal();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, nameNode.path("message").asText());
            }
        } catch (Exception ex) {
            System.out.println("Notifikasi Bridging : " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(null, "Koneksi ke server BPJS terputus...!");
            }
        }
    }

    public void tampilFaskes1() {
        try {
            String URL = koneksiDB.UrlBpjs() + "/Rujukan/Peserta/" + NoKartu.getText();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("X-Cons-ID", koneksiDB.ConsIdBpjs());
            headers.add("X-Timestamp", String.valueOf(api.GetUTCdatetimeAsString()));
            headers.add("X-Signature", api.getHmac());
            headers.add("user_key", koneksiDB.UserKeyBpjs());

            HttpEntity requestEntity = new HttpEntity(headers);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            JsonNode nameNode = root.path("metaData");
            if (nameNode.path("code").asText().equals("200")) {
                Valid.tabelKosong(tabMode1);
                if (koneksiDB.UrlBpjs().contains("apijkn")) {
                    res1 = root.path("response");
                    String res = api.decrypt(res1.asText());
                    String lz = api.lzDecrypt(res);
                    response = mapper.readTree(lz);
                } else {
                    response = root.path("response");
                }
                response = response.path("rujukan");
                tabMode1.addRow(new Object[]{
                    "1", response.path("noKunjungan").asText(),
                    response.path("tglKunjungan").asText(),
                    response.path("provPerujuk").path("kode").asText(),
                    response.path("provPerujuk").path("nama").asText(),
                    response.path("poliRujukan").path("nama").asText(),
                    response.path("diagnosa").path("kode").asText(),
                    response.path("diagnosa").path("nama").asText(),
                    response.path("keluhan").asText(),
                    response.path("poliRujukan").path("kode").asText(),
                    response.path("pelayanan").path("kode").asText(),
                    response.path("pelayanan").path("nama").asText()
                });
            } else {
                JOptionPane.showMessageDialog(null, nameNode.path("message").asText() + " di Faskes 1.");
            }
        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
            }
        }
    }

    public void tampilFaskes2() {
        try {

            String URL = koneksiDB.UrlBpjs() + "/Rujukan/RS/Peserta/" + NoKartu.getText();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("X-Cons-ID", koneksiDB.ConsIdBpjs());
            headers.add("X-Timestamp", String.valueOf(api.GetUTCdatetimeAsString()));
            headers.add("X-Signature", api.getHmac());
            headers.add("user_key", koneksiDB.UserKeyBpjs());

            HttpEntity requestEntity = new HttpEntity(headers);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            JsonNode nameNode = root.path("metaData");
            if (nameNode.path("code").asText().equals("200")) {
                Valid.tabelKosong(tabMode2);
                if (koneksiDB.UrlBpjs().contains("apijkn")) {
                    res1 = root.path("response");
                    String res = api.decrypt(res1.asText());
                    String lz = api.lzDecrypt(res);
                    response = mapper.readTree(lz);
                } else {
                    response = root.path("response");
                }
                response = response.path("rujukan");
                tabMode2.addRow(new Object[]{
                    "1", response.path("noKunjungan").asText(),
                    response.path("tglKunjungan").asText(),
                    response.path("provPerujuk").path("kode").asText(),
                    response.path("provPerujuk").path("nama").asText(),
                    response.path("poliRujukan").path("nama").asText(),
                    response.path("diagnosa").path("kode").asText(),
                    response.path("diagnosa").path("nama").asText(),
                    response.path("keluhan").asText(),
                    response.path("poliRujukan").path("kode").asText(),
                    response.path("pelayanan").path("kode").asText(),
                    response.path("pelayanan").path("nama").asText()
                });
            } else {
                JOptionPane.showMessageDialog(null, nameNode.path("message").asText() + " di Faskes 2.");
            }
        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
            }
        }
    }

    public void tampilFaskes1BYK() {
        try {
            String URL = koneksiDB.UrlBpjs() + "/Rujukan/List/Peserta/" + NoKartu.getText();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("X-Cons-ID", koneksiDB.ConsIdBpjs());
            headers.add("X-Timestamp", String.valueOf(api.GetUTCdatetimeAsString()));
            headers.add("X-Signature", api.getHmac());
            headers.add("user_key", koneksiDB.UserKeyBpjs());

            HttpEntity requestEntity = new HttpEntity(headers);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            JsonNode nameNode = root.path("metaData");
            if (nameNode.path("code").asText().equals("200")) {
                Valid.tabelKosong(tabMode3);
                if (koneksiDB.UrlBpjs().contains("apijkn")) {
                    res1 = root.path("response");
                    String res = api.decrypt(res1.asText());
                    String lz = api.lzDecrypt(res);
                    response = mapper.readTree(lz);
                } else {
                    response = root.path("response");
                }
//                JsonNode response = root.path("response");
                if (response.path("rujukan").isArray()) {
                    i = 1;
                    for (JsonNode list : response.path("rujukan")) {
                        tabMode3.addRow(new Object[]{
                            i + ".",
                            list.path("noKunjungan").asText(),
                            list.path("tglKunjungan").asText(),
                            list.path("provPerujuk").path("kode").asText(),
                            list.path("provPerujuk").path("nama").asText(),
                            list.path("poliRujukan").path("nama").asText(),
                            list.path("diagnosa").path("kode").asText(),
                            list.path("diagnosa").path("nama").asText(),
                            list.path("keluhan").asText(),
                            list.path("poliRujukan").path("kode").asText(),
                            list.path("pelayanan").path("kode").asText(),
                            list.path("pelayanan").path("nama").asText()
                        });
                        i++;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, nameNode.path("message").asText() + " di Faskes 1 data rujukan banyak.");
            }
        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
            }
        }
    }

    public void tampilFaskes2BYK() {
        try {
            String URL = koneksiDB.UrlBpjs() + "/Rujukan/RS/List/Peserta/" + NoKartu.getText();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("X-Cons-ID", koneksiDB.ConsIdBpjs());
            headers.add("X-Timestamp", String.valueOf(api.GetUTCdatetimeAsString()));
            headers.add("X-Signature", api.getHmac());
            headers.add("user_key", koneksiDB.UserKeyBpjs());

            HttpEntity requestEntity = new HttpEntity(headers);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            JsonNode nameNode = root.path("metaData");
            if (nameNode.path("code").asText().equals("200")) {
                Valid.tabelKosong(tabMode4);
                if (koneksiDB.UrlBpjs().contains("apijkn")) {
                    res1 = root.path("response");
                    String res = api.decrypt(res1.asText());
                    String lz = api.lzDecrypt(res);
                    response = mapper.readTree(lz);
                } else {
                    response = root.path("response");
                }
//                JsonNode response = root.path("response");
                if (response.path("rujukan").isArray()) {
                    i = 1;
                    for (JsonNode list : response.path("rujukan")) {
                        tabMode4.addRow(new Object[]{
                            i + ".",
                            list.path("noKunjungan").asText(),
                            list.path("tglKunjungan").asText(),
                            list.path("provPerujuk").path("kode").asText(),
                            list.path("provPerujuk").path("nama").asText(),
                            list.path("poliRujukan").path("nama").asText(),
                            list.path("diagnosa").path("kode").asText(),
                            list.path("diagnosa").path("nama").asText(),
                            list.path("keluhan").asText(),
                            list.path("poliRujukan").path("kode").asText(),
                            list.path("pelayanan").path("kode").asText(),
                            list.path("pelayanan").path("nama").asText()
                        });
                        i++;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, nameNode.path("message").asText() + " di Faskes 2 data rujukan banyak.");
            }
        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
            }
        }
    }
    
    public void tampilRujukKhusus(String bulan) {
        try {
            String URL = koneksiDB.UrlBpjs() + "/Rujukan/Khusus/List/Bulan/"+ bulan +"/Tahun/2022";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("X-Cons-ID", koneksiDB.ConsIdBpjs());
            headers.add("X-Timestamp", String.valueOf(api.GetUTCdatetimeAsString()));
            headers.add("X-Signature", api.getHmac());
            headers.add("user_key", koneksiDB.UserKeyBpjs());

            HttpEntity requestEntity = new HttpEntity(headers);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            JsonNode nameNode = root.path("metaData");
            if (nameNode.path("code").asText().equals("200")) {
                Valid.tabelKosong(tabMode5);
                res1 = root.path("response");
                String res = api.decrypt(res1.asText());
                String lz = api.lzDecrypt(res);
                response = mapper.readTree(lz);
                response = response.path("rujukan");
//                System.out.println(response);
                if (response.isArray()) {
                    i = 1;
                    for (JsonNode list : response) {
                        tabMode5.addRow(new Object[]{
                            i + ".",
                            list.path("idrujukan").asText(),
                            list.path("norujukan").asText(),
                            list.path("nokapst").asText(),
                            list.path("nmpst").asText(),
                            list.path("diagppk").asText(),
                            list.path("tglrujukan_awal").asText(),
                            list.path("tglrujukan_berakhir").asText(),
                        });
                        i++;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, nameNode.path("message").asText() + " di Rujukan Khusus.");
            }
        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
            }
        }
    }
    
    public void cariByNoRujuk(String noRujuk) {
        try {
            String URL = koneksiDB.UrlBpjs() + "/Rujukan/"+ noRujuk;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("X-Cons-ID", koneksiDB.ConsIdBpjs());
            headers.add("X-Timestamp", String.valueOf(api.GetUTCdatetimeAsString()));
            headers.add("X-Signature", api.getHmac());
            headers.add("user_key", koneksiDB.UserKeyBpjs());

            HttpEntity requestEntity = new HttpEntity(headers);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            JsonNode nameNode = root.path("metaData");
            if (nameNode.path("code").asText().equals("200")) {
                res1 = root.path("response");
                String res = api.decrypt(res1.asText());
                String lz = api.lzDecrypt(res);
                response = mapper.readTree(lz);
                response = response.path("rujukan");
//                System.out.println(response);
                KdPenyakit.setText(response.path("diagnosa").path("kode").asText());
                NmPenyakit.setText(response.path("diagnosa").path("nama").asText());
            } else {
                JOptionPane.showMessageDialog(null, nameNode.path("message").asText() + " di Rujukan Khusus.");
            }
        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
            }
        }
    }

    private void getDataFK1() {
        if (tbFaskes1.getSelectedRow() != -1) {
            AsalRujukan.setSelectedIndex(0);
            NoRujukan.setText(tbFaskes1.getValueAt(tbFaskes1.getSelectedRow(), 1).toString());
            Valid.SetTgl(TanggalRujuk, tbFaskes1.getValueAt(tbFaskes1.getSelectedRow(), 2).toString());
            KdPpkRujukan.setText(tbFaskes1.getValueAt(tbFaskes1.getSelectedRow(), 3).toString());
//            rujukanSEP.setText(tbFaskes1.getValueAt(tbFaskes1.getSelectedRow(), 4).toString());
//            Sequel.cariIsi("select nama_rujukan from master_nama_rujukan where kode_faskes_bpjs=? ", NmPpkRujukan, KdPpkRujukan.getText());
            NmPpkRujukan.setText(tbFaskes1.getValueAt(tbFaskes1.getSelectedRow(), 4).toString());
            KdPenyakit.setText(tbFaskes1.getValueAt(tbFaskes1.getSelectedRow(), 6).toString());
            NmPenyakit.setText(tbFaskes1.getValueAt(tbFaskes1.getSelectedRow(), 7).toString());
            KdPoli.setText(tbFaskes1.getValueAt(tbFaskes1.getSelectedRow(), 9).toString());
            NmPoli.setText(tbFaskes1.getValueAt(tbFaskes1.getSelectedRow(), 5).toString());

            if (tbFaskes1.getValueAt(tbFaskes1.getSelectedRow(), 10).toString().contains("2")) {
                JenisPelayanan.setSelectedIndex(1);
            } else {
                JenisPelayanan.setSelectedIndex(0);
            }
        }
    }

    private void getDataFK2() {
        if (tbFaskes2.getSelectedRow() != -1) {
            AsalRujukan.setSelectedIndex(1);
            NoRujukan.setText(tbFaskes2.getValueAt(tbFaskes2.getSelectedRow(), 1).toString());
            Valid.SetTgl(TanggalRujuk, tbFaskes2.getValueAt(tbFaskes2.getSelectedRow(), 2).toString());
            KdPpkRujukan.setText(tbFaskes2.getValueAt(tbFaskes2.getSelectedRow(), 3).toString());
//            rujukanSEP.setText(tbFaskes2.getValueAt(tbFaskes2.getSelectedRow(), 4).toString());
//            Sequel.cariIsi("select nama_rujukan from master_nama_rujukan where kode_faskes_bpjs=? ", NmPpkRujukan, KdPpkRujukan.getText());
            NmPpkRujukan.setText(tbFaskes2.getValueAt(tbFaskes2.getSelectedRow(), 4).toString());
            KdPenyakit.setText(tbFaskes2.getValueAt(tbFaskes2.getSelectedRow(), 6).toString());
            NmPenyakit.setText(tbFaskes2.getValueAt(tbFaskes2.getSelectedRow(), 7).toString());
            KdPoli.setText(tbFaskes2.getValueAt(tbFaskes2.getSelectedRow(), 9).toString());
            NmPoli.setText(tbFaskes2.getValueAt(tbFaskes2.getSelectedRow(), 5).toString());

            if (tbFaskes2.getValueAt(tbFaskes2.getSelectedRow(), 10).toString().contains("2")) {
                JenisPelayanan.setSelectedIndex(1);
            } else {
                JenisPelayanan.setSelectedIndex(0);
            }
        }
    }

    private void getDataFK1byk() {
        if (tbFaskes3.getSelectedRow() != -1) {
            AsalRujukan.setSelectedIndex(0);
            NoRujukan.setText(tbFaskes3.getValueAt(tbFaskes3.getSelectedRow(), 1).toString());
            Valid.SetTgl(TanggalRujuk, tbFaskes3.getValueAt(tbFaskes3.getSelectedRow(), 2).toString());
            KdPpkRujukan.setText(tbFaskes3.getValueAt(tbFaskes3.getSelectedRow(), 3).toString());
//            rujukanSEP.setText(tbFaskes3.getValueAt(tbFaskes3.getSelectedRow(), 4).toString());
//            Sequel.cariIsi("select nama_rujukan from master_nama_rujukan where kode_faskes_bpjs=? ", NmPpkRujukan, KdPpkRujukan.getText());
            NmPpkRujukan.setText(tbFaskes3.getValueAt(tbFaskes3.getSelectedRow(), 4).toString());
            KdPenyakit.setText(tbFaskes3.getValueAt(tbFaskes3.getSelectedRow(), 6).toString());
            NmPenyakit.setText(tbFaskes3.getValueAt(tbFaskes3.getSelectedRow(), 7).toString());
            KdPoli.setText(tbFaskes3.getValueAt(tbFaskes3.getSelectedRow(), 9).toString());
            NmPoli.setText(tbFaskes3.getValueAt(tbFaskes3.getSelectedRow(), 5).toString());

            if (tbFaskes3.getValueAt(tbFaskes3.getSelectedRow(), 10).toString().contains("2")) {
                JenisPelayanan.setSelectedIndex(1);
            } else {
                JenisPelayanan.setSelectedIndex(0);
            }
        }
    }

    private void getDataFK2byk() {
        if (tbFaskes4.getSelectedRow() != -1) {
            AsalRujukan.setSelectedIndex(1);
            NoRujukan.setText(tbFaskes4.getValueAt(tbFaskes4.getSelectedRow(), 1).toString());
            Valid.SetTgl(TanggalRujuk, tbFaskes4.getValueAt(tbFaskes4.getSelectedRow(), 2).toString());
            KdPpkRujukan.setText(tbFaskes4.getValueAt(tbFaskes4.getSelectedRow(), 3).toString());
//            rujukanSEP.setText(tbFaskes4.getValueAt(tbFaskes4.getSelectedRow(), 4).toString());
//            Sequel.cariIsi("select nama_rujukan from master_nama_rujukan where kode_faskes_bpjs=? ", NmPpkRujukan, KdPpkRujukan.getText());
            NmPpkRujukan.setText(tbFaskes4.getValueAt(tbFaskes4.getSelectedRow(), 4).toString());
            KdPenyakit.setText(tbFaskes4.getValueAt(tbFaskes4.getSelectedRow(), 6).toString());
            NmPenyakit.setText(tbFaskes4.getValueAt(tbFaskes4.getSelectedRow(), 7).toString());
            KdPoli.setText(tbFaskes4.getValueAt(tbFaskes4.getSelectedRow(), 9).toString());
            NmPoli.setText(tbFaskes4.getValueAt(tbFaskes4.getSelectedRow(), 5).toString());

            if (tbFaskes4.getValueAt(tbFaskes4.getSelectedRow(), 10).toString().contains("2")) {
                JenisPelayanan.setSelectedIndex(1);
            } else {
                JenisPelayanan.setSelectedIndex(0);
            }
        }
    }
    
    private void getDataRujukKhusus() {
        if (tbFaskes5.getSelectedRow() != -1) {
            AsalRujukan.setSelectedIndex(0);
            NoRujukan.setText(tbFaskes5.getValueAt(tbFaskes5.getSelectedRow(), 2).toString());
            cariByNoRujuk(tbFaskes5.getValueAt(tbFaskes5.getSelectedRow(), 2).toString());
            Valid.SetTgl(TanggalRujuk, tbFaskes5.getValueAt(tbFaskes5.getSelectedRow(), 6).toString());
        }
    }

    private void tampildiagnosa() {
        int jml = 0, i = 0, index = 0, index2 = 0, i2 = 0, jml2 = 0;
        String[] kode, nama;
        boolean[] pilih, pilih2;
        PreparedStatement pspenyakit, psdiagnosapasien, psprosedur, pstindakanpasien;
        try {
            jml = 0;
            for (i = 0; i < tbDiagnosa.getRowCount(); i++) {
                if (tbDiagnosa.getValueAt(i, 0).toString().equals("true")) {
                    jml++;
                }
            }

            jml2 = 0;
            for (i2 = 0; i2 < tbDiagnosa.getRowCount(); i2++) {
                if (tbDiagnosa.getValueAt(i2, 1).toString().equals("true")) {
                    jml2++;
                }
            }
            pilih = null;
            pilih = new boolean[jml];
            pilih2 = null;
            pilih2 = new boolean[jml];
            kode = null;
            kode = new String[jml];
            nama = null;
            nama = new String[jml];

            index = 0;
            for (i = 0; i < tbDiagnosa.getRowCount(); i++) {
                if (tbDiagnosa.getValueAt(i, 0).toString().equals("true")) {
                    pilih[index] = true;
                    pilih2[index] = false;
                    kode[index] = tbDiagnosa.getValueAt(i, 2).toString();
                    nama[index] = tbDiagnosa.getValueAt(i, 3).toString();
                    index++;
                }
            }

            index2 = 0;
            for (i2 = 0; i2 < tbDiagnosa.getRowCount(); i2++) {
                if (tbDiagnosa.getValueAt(i, 1).toString().equals("true")) {
                    pilih[index] = false;
                    pilih2[index] = true;
                    kode[index] = tbDiagnosa.getValueAt(i2, 2).toString();
                    nama[index] = tbDiagnosa.getValueAt(i2, 3).toString();
                    index2++;
                }
            }
            Valid.tabelKosong(tabModeDiagnosa);
            for (i = 0; i < jml; i++) {
                tabModeDiagnosa.addRow(new Object[]{pilih[i], pilih2[i], kode[i], nama[i]});
            }

            pspenyakit = koneksi.prepareStatement("select penyakit.kd_penyakit,penyakit.nm_penyakit "
                    + "from kategori_penyakit inner join penyakit "
                    + "on penyakit.kd_ktg=kategori_penyakit.kd_ktg where"
                    + " penyakit.kd_penyakit like ? or "
                    + " penyakit.nm_penyakit like ? or "
                    + " penyakit.ciri_ciri like ? or "
                    + " penyakit.keterangan like ? or "
                    + " kategori_penyakit.nm_kategori like ? or "
                    + " kategori_penyakit.ciri_umum like ? "
                    + "order by penyakit.kd_penyakit LIMIT 10");
            try {
                pspenyakit.setString(1, "%" + Diagnosa.getText().trim() + "%");
                pspenyakit.setString(2, "%" + Diagnosa.getText().trim() + "%");
                pspenyakit.setString(3, "%" + Diagnosa.getText().trim() + "%");
                pspenyakit.setString(4, "%" + Diagnosa.getText().trim() + "%");
                pspenyakit.setString(5, "%" + Diagnosa.getText().trim() + "%");
                pspenyakit.setString(6, "%" + Diagnosa.getText().trim() + "%");
                rs = pspenyakit.executeQuery();
                while (rs.next()) {
                    tabModeDiagnosa.addRow(new Object[]{false, false, rs.getString(1),
                        rs.getString(2)});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (pspenyakit != null) {
                    pspenyakit.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    private void tampilprosedure() {
        int jml = 0, i = 0, index = 0, index2 = 0, i2 = 0, jml2 = 0;
        String[] kode, nama, kode2, panjang, pendek;
        boolean[] pilih, pilih2;
        PreparedStatement pspenyakit, psdiagnosapasien, psprosedur, pstindakanpasien;
        try {
            jml = 0;
            for (i = 0; i < tbProsedur.getRowCount(); i++) {
                if (tbProsedur.getValueAt(i, 0).toString().equals("true")) {
                    jml++;
                }
            }

            pilih = null;
            pilih = new boolean[jml];
            kode2 = null;
            kode2 = new String[jml];
            panjang = null;
            panjang = new String[jml];

            index = 0;
            for (i = 0; i < tbProsedur.getRowCount(); i++) {
                if (tbProsedur.getValueAt(i, 0).toString().equals("true")) {
                    pilih[index] = true;
                    kode2[index] = tbProsedur.getValueAt(i, 1).toString();
                    panjang[index] = tbProsedur.getValueAt(i, 2).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabModeProsedur);
            for (i = 0; i < jml; i++) {
                tabModeProsedur.addRow(new Object[]{pilih[i], kode2[i], panjang[i]});
            }

            psprosedur = koneksi.prepareStatement("select * from icd9 where kode like ? or "
                    + " deskripsi_panjang like ? or  deskripsi_pendek like ? order by kode");
            try {
                psprosedur.setString(1, "%" + Prosedur.getText().trim() + "%");
                psprosedur.setString(2, "%" + Prosedur.getText().trim() + "%");
                psprosedur.setString(3, "%" + Prosedur.getText().trim() + "%");
                rs = psprosedur.executeQuery();
                while (rs.next()) {
                    tabModeProsedur.addRow(new Object[]{
                        false, rs.getString(1), rs.getString(2)});
                }
            } catch (Exception ex) {
                System.out.println(ex);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (psprosedur != null) {
                    psprosedur.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    private void simpanBerkas() {
        String jam = "0000-00-00 00:00:00";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String no_rawat = "";
        if (TabRawat.getSelectedIndex() == 1) {
            no_rawat = tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString();
        } else {
            no_rawat = tbObat1.getValueAt(tbObat1.getSelectedRow(), 1).toString();
        }
        try {
            Sequel.menyimpan("mutasi_berkas", "?,?,?,?,?,?,?", 7, new String[]{
                no_rawat, "Sudah Dikirim", dtf.format(now), jam, jam, jam, jam
            });
        } catch (Exception e) {
            System.out.println("Gagal menyimpan " + e);
        }
    }
    
    public String cariDpjp(String nosep){
        String dpjp = "";
        dpjp = Sequel.cariIsi("SELECT kddpjp FROM bridging_sep WHERE no_sep = ?", nosep);
        return dpjp;
    }
}
