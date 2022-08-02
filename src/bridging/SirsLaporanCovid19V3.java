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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import simrskhanza.DlgResumePerawatan;

/**
 *
 * @author perpustakaan
 */
public final class SirsLaporanCovid19V3 extends javax.swing.JDialog {

    private DefaultTableModel tabMode, tabMode1, tabMode2, tabMode3, tabMode4, tabModeDiagnosa, tabModeProsedur;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0, pilihan = 1, reply = 0;
    private final Properties prop = new Properties();
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
    private SirsApi api = new SirsApi();
    private SirsCekReferensiKewarganegaraan faskes = new SirsCekReferensiKewarganegaraan(null, false);
    private SirsCekReferensiAsalPasien penyakit = new SirsCekReferensiAsalPasien(null, false);
    private SirsCekReferensiPekerjaan pekerjaan = new SirsCekReferensiPekerjaan(null, false);
    private SirsCekReferensiJenisPasien poli = new SirsCekReferensiJenisPasien(null, false);
    private SirsCekReferensiVarian varian = new SirsCekReferensiVarian(null, false);
    private SirsCekReferensiStatusPasien sttsPasien = new SirsCekReferensiStatusPasien(null, false);
    private SirsCekReferensiStatusRawat sttsRawat = new SirsCekReferensiStatusRawat(null, false);
    private SirsCekReferensiKecamatan kecamatan = new SirsCekReferensiKecamatan(null, false);
    private BPJSSPRI skdp2 = new BPJSSPRI(null, false);
    private SirsCekReferensiProvinsi propinsi = new SirsCekReferensiProvinsi(null, false);
    private SirsCekReferensiKabupaten kabupaten = new SirsCekReferensiKabupaten(null, false);
    private String kode_kab = "", kode_prov = "", requestJson = "", link, nm_kecamatan, kd_kecamatan, URL = "", jkel = "", duplikat = "", user = "", penjamin = "", id_laporan_covid = "", token = "", Taspen = "", Asabri = "", kddokter = "", tglkkl = "0000-00-00", antrian = "", klsRawat = "", dpjlayan = "", sep2tambah = "", penjaminan = "";
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
    public SirsLaporanCovid19V3(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8, 1);

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

        TCari.setDocument(new batasInput((byte) 100).getKata(TCari));

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
                        KdWarga.setText(faskes.getTable().getValueAt(faskes.getTable().getSelectedRow(), 1).toString());
                        NmWarga.setText(faskes.getTable().getValueAt(faskes.getTable().getSelectedRow(), 2).toString());
                        KdWarga.requestFocus();
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

        varian.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (varian.getTable().getSelectedRow() != -1) {
                    if (pilihan == 1) {
                        KdVarian.setText(varian.getTable().getValueAt(varian.getTable().getSelectedRow(), 1).toString());
                        NmVarian.setText(varian.getTable().getValueAt(varian.getTable().getSelectedRow(), 2).toString());
                        KdVarian.requestFocus();
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

        varian.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    varian.dispose();
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
                    KdKec.setText(kecamatan.getTable().getValueAt(kecamatan.getTable().getSelectedRow(), 1).toString());
                    NmKec.setText(kecamatan.getTable().getValueAt(kecamatan.getTable().getSelectedRow(), 2).toString());
                    kode_kab = kecamatan.getTable().getValueAt(kecamatan.getTable().getSelectedRow(), 3).toString();
                    KdKec.requestFocus();
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
                    KdProv.setText(propinsi.getTable().getValueAt(propinsi.getTable().getSelectedRow(), 1).toString());
                    NmProv.setText(propinsi.getTable().getValueAt(propinsi.getTable().getSelectedRow(), 2).toString());
                    KdProv.requestFocus();
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
                    KdKab.setText(kabupaten.getTable().getValueAt(kabupaten.getTable().getSelectedRow(), 1).toString());
                    NmKab.setText(kabupaten.getTable().getValueAt(kabupaten.getTable().getSelectedRow(), 2).toString());
                    kode_prov = kabupaten.getTable().getValueAt(kabupaten.getTable().getSelectedRow(), 3).toString();
                    KdKab.requestFocus();
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

        try {
            user = var.getkode().replace(" ", "").substring(0, 9);
        } catch (Exception e) {
            user = var.getkode();
        }
        
        cmbOksigen.setVisible(false);
        LabelPoli4.setVisible(false);
        idDemam.setVisible(false);
        idDiare.setVisible(false);
        idAnosmia.setVisible(false);
        idBatuk.setVisible(false);
        idFrekNafas.setVisible(false);
        idLainnya.setVisible(false);
        idLemas.setVisible(false);
        idMual.setVisible(false);
        idNafasBerat.setVisible(false);
        idNafasCepat.setVisible(false);
        idNyeriOtot.setVisible(false);
        idPilek.setVisible(false);
        idSakitTenggorokan.setVisible(false);
        idSesakNafas.setVisible(false);
        LabelKelas1.setVisible(false);
        LabelKelas2.setVisible(false);
        LabelKelas3.setVisible(false);
        LabelKelas4.setVisible(false);
        LabelKelas5.setVisible(false);
        jLabel15.setVisible(false);
        jLabel16.setVisible(false);
        jLabel17.setVisible(false);
        jLabel26.setVisible(false);
        jLabel37.setVisible(false);
        jLabel38.setVisible(false);
        jLabel40.setVisible(false);
        jLabel28.setVisible(false);
        jLabel41.setVisible(false);
        link = koneksiDB.UrlSirs();

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
        ppRiwayatPerawatan = new javax.swing.JMenuItem();
        NoBalasan = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel20 = new widget.Label();
        TanggalOnset = new widget.Tanggal();
        jLabel22 = new widget.Label();
        TanggalMasuk = new widget.Tanggal();
        btnPPKRujukan = new widget.Button();
        KdWarga = new widget.TextBox();
        jLabel10 = new widget.Label();
        NmWarga = new widget.TextBox();
        jLabel11 = new widget.Label();
        LabelPoli = new widget.Label();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel18 = new widget.Label();
        JK = new widget.TextBox();
        LabelPoli2 = new widget.Label();
        KdKec = new widget.TextBox();
        NmKec = new widget.TextBox();
        btnDPJP = new widget.Button();
        jLabel29 = new widget.Label();
        NoTelp = new widget.TextBox();
        Nik = new widget.TextBox();
        jLabel5 = new widget.Label();
        jLabel24 = new widget.Label();
        email = new widget.TextBox();
        jLabel25 = new widget.Label();
        noPassport = new widget.TextBox();
        LabelKelas1 = new widget.Label();
        idDemam = new widget.ComboBox();
        jLabel15 = new widget.Label();
        idBatuk = new widget.ComboBox();
        idPilek = new widget.ComboBox();
        jLabel37 = new widget.Label();
        LabelKelas2 = new widget.Label();
        idSakitTenggorokan = new widget.ComboBox();
        jLabel16 = new widget.Label();
        idSesakNafas = new widget.ComboBox();
        jLabel38 = new widget.Label();
        idLemas = new widget.ComboBox();
        LabelKelas3 = new widget.Label();
        idNyeriOtot = new widget.ComboBox();
        LabelKelas4 = new widget.Label();
        idAnosmia = new widget.ComboBox();
        jLabel17 = new widget.Label();
        idMual = new widget.ComboBox();
        jLabel26 = new widget.Label();
        idNafasCepat = new widget.ComboBox();
        jLabel40 = new widget.Label();
        idDiare = new widget.ComboBox();
        jLabel41 = new widget.Label();
        idFrekNafas = new widget.ComboBox();
        LabelKelas5 = new widget.Label();
        idNafasBerat = new widget.ComboBox();
        jLabel28 = new widget.Label();
        idLainnya = new widget.ComboBox();
        jLabel30 = new widget.Label();
        Inisial = new widget.TextBox();
        LabelPoli3 = new widget.Label();
        KdKab = new widget.TextBox();
        NmKab = new widget.TextBox();
        btnDPJP1 = new widget.Button();
        jLabel12 = new widget.Label();
        jLabel39 = new widget.Label();
        idPenyintas = new widget.ComboBox();
        jLabel42 = new widget.Label();
        idStatusCo = new widget.ComboBox();
        jLabel13 = new widget.Label();
        jLabel14 = new widget.Label();
        KdProv = new widget.TextBox();
        NmProv = new widget.TextBox();
        btnPPKRujukan2 = new widget.Button();
        KdVarian = new widget.TextBox();
        NmVarian = new widget.TextBox();
        btnPPKRujukan3 = new widget.Button();
        jLabel23 = new widget.Label();
        LabelPoli1 = new widget.Label();
        LabelPoli4 = new widget.Label();
        LabelPoli5 = new widget.Label();
        cmbGejala = new widget.ComboBox();
        cmbOksigen = new widget.ComboBox();
        cmbPekerjaan = new widget.ComboBox();
        cmbAsalPasien = new widget.ComboBox();
        cmbJenisRawat = new widget.ComboBox();
        cmbStatusPasien = new widget.ComboBox();
        cmbStatusRawat = new widget.ComboBox();
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

        NoBalasan.setHighlighter(null);
        NoBalasan.setName("NoBalasan"); // NOI18N

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Bridging Covid 19 Versi 3 ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
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

        jLabel20.setText("Tgl.Onset Gejala:");
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel20);
        jLabel20.setBounds(207, 102, 100, 23);

        TanggalOnset.setForeground(new java.awt.Color(50, 70, 50));
        TanggalOnset.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-02-2022" }));
        TanggalOnset.setDisplayFormat("dd-MM-yyyy");
        TanggalOnset.setName("TanggalOnset"); // NOI18N
        TanggalOnset.setOpaque(false);
        TanggalOnset.setPreferredSize(new java.awt.Dimension(95, 23));
        TanggalOnset.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalOnsetKeyPressed(evt);
            }
        });
        FormInput.add(TanggalOnset);
        TanggalOnset.setBounds(315, 102, 95, 23);

        jLabel22.setText("Tgl.Masuk :");
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel22);
        jLabel22.setBounds(0, 100, 90, 23);

        TanggalMasuk.setForeground(new java.awt.Color(50, 70, 50));
        TanggalMasuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-02-2022" }));
        TanggalMasuk.setDisplayFormat("dd-MM-yyyy");
        TanggalMasuk.setName("TanggalMasuk"); // NOI18N
        TanggalMasuk.setOpaque(false);
        TanggalMasuk.setPreferredSize(new java.awt.Dimension(95, 23));
        TanggalMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalMasukKeyPressed(evt);
            }
        });
        FormInput.add(TanggalMasuk);
        TanggalMasuk.setBounds(98, 100, 95, 23);

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
        btnPPKRujukan.setBounds(350, 130, 28, 23);

        KdWarga.setEditable(false);
        KdWarga.setBackground(new java.awt.Color(245, 250, 240));
        KdWarga.setHighlighter(null);
        KdWarga.setName("KdWarga"); // NOI18N
        FormInput.add(KdWarga);
        KdWarga.setBounds(95, 130, 75, 23);

        jLabel10.setText("Warga Negara :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(10, 130, 80, 23);

        NmWarga.setEditable(false);
        NmWarga.setBackground(new java.awt.Color(245, 250, 240));
        NmWarga.setHighlighter(null);
        NmWarga.setName("NmWarga"); // NOI18N
        FormInput.add(NmWarga);
        NmWarga.setBounds(170, 130, 180, 23);

        jLabel11.setText("Asal Pasien :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(0, 190, 90, 23);

        LabelPoli.setText("Jenis Pasien :");
        LabelPoli.setName("LabelPoli"); // NOI18N
        FormInput.add(LabelPoli);
        LabelPoli.setBounds(380, 190, 90, 23);

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

        LabelPoli2.setText("Domisili Kec :");
        LabelPoli2.setName("LabelPoli2"); // NOI18N
        FormInput.add(LabelPoli2);
        LabelPoli2.setBounds(390, 130, 90, 23);

        KdKec.setEditable(false);
        KdKec.setBackground(new java.awt.Color(245, 250, 240));
        KdKec.setHighlighter(null);
        KdKec.setName("KdKec"); // NOI18N
        FormInput.add(KdKec);
        KdKec.setBounds(485, 130, 75, 23);

        NmKec.setEditable(false);
        NmKec.setBackground(new java.awt.Color(245, 250, 240));
        NmKec.setHighlighter(null);
        NmKec.setName("NmKec"); // NOI18N
        FormInput.add(NmKec);
        NmKec.setBounds(560, 130, 180, 23);

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
        btnDPJP.setBounds(740, 130, 28, 23);

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

        Nik.setEditable(false);
        Nik.setBackground(new java.awt.Color(245, 250, 240));
        Nik.setHighlighter(null);
        Nik.setName("Nik"); // NOI18N
        FormInput.add(Nik);
        Nik.setBounds(93, 70, 152, 23);

        jLabel5.setText("NIK :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 70, 90, 23);

        jLabel24.setText("Email :");
        jLabel24.setName("jLabel24"); // NOI18N
        jLabel24.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel24);
        jLabel24.setBounds(250, 40, 60, 23);

        email.setBackground(new java.awt.Color(245, 250, 240));
        email.setHighlighter(null);
        email.setName("email"); // NOI18N
        FormInput.add(email);
        email.setBounds(316, 40, 150, 23);

        jLabel25.setText("No.Passport :");
        jLabel25.setName("jLabel25"); // NOI18N
        jLabel25.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel25);
        jLabel25.setBounds(260, 70, 70, 23);

        noPassport.setBackground(new java.awt.Color(245, 250, 240));
        noPassport.setHighlighter(null);
        noPassport.setName("noPassport"); // NOI18N
        FormInput.add(noPassport);
        noPassport.setBounds(337, 70, 150, 23);

        LabelKelas1.setText("Demam :");
        LabelKelas1.setName("LabelKelas1"); // NOI18N
        FormInput.add(LabelKelas1);
        LabelKelas1.setBounds(140, 340, 70, 23);

        idDemam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak ", "1.Ya" }));
        idDemam.setName("idDemam"); // NOI18N
        idDemam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idDemamKeyPressed(evt);
            }
        });
        FormInput.add(idDemam);
        idDemam.setBounds(210, 340, 100, 23);

        jLabel15.setText("Batuk :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(330, 340, 70, 23);

        idBatuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak", "1.Ya" }));
        idBatuk.setName("idBatuk"); // NOI18N
        idBatuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idBatukKeyPressed(evt);
            }
        });
        FormInput.add(idBatuk);
        idBatuk.setBounds(400, 340, 100, 23);

        idPilek.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak", "1.Ya" }));
        idPilek.setName("idPilek"); // NOI18N
        idPilek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idPilekKeyPressed(evt);
            }
        });
        FormInput.add(idPilek);
        idPilek.setBounds(580, 340, 100, 23);

        jLabel37.setText("Pilek :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(510, 340, 70, 23);

        LabelKelas2.setText("Sakit Tenggorokan :");
        LabelKelas2.setName("LabelKelas2"); // NOI18N
        FormInput.add(LabelKelas2);
        LabelKelas2.setBounds(100, 370, 110, 23);

        idSakitTenggorokan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak ", "1.Ya" }));
        idSakitTenggorokan.setName("idSakitTenggorokan"); // NOI18N
        idSakitTenggorokan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idSakitTenggorokanKeyPressed(evt);
            }
        });
        FormInput.add(idSakitTenggorokan);
        idSakitTenggorokan.setBounds(210, 370, 100, 23);

        jLabel16.setText("Sesak Nafas :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(330, 370, 70, 23);

        idSesakNafas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak", "1.Ya" }));
        idSesakNafas.setName("idSesakNafas"); // NOI18N
        idSesakNafas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idSesakNafasKeyPressed(evt);
            }
        });
        FormInput.add(idSesakNafas);
        idSesakNafas.setBounds(400, 370, 100, 23);

        jLabel38.setText("Lemas :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(510, 370, 70, 23);

        idLemas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak", "1.Ya" }));
        idLemas.setName("idLemas"); // NOI18N
        idLemas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idLemasKeyPressed(evt);
            }
        });
        FormInput.add(idLemas);
        idLemas.setBounds(580, 370, 100, 23);

        LabelKelas3.setText("Nyeri Otot :");
        LabelKelas3.setName("LabelKelas3"); // NOI18N
        FormInput.add(LabelKelas3);
        LabelKelas3.setBounds(130, 400, 80, 23);

        idNyeriOtot.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak ", "1.Ya" }));
        idNyeriOtot.setName("idNyeriOtot"); // NOI18N
        idNyeriOtot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idNyeriOtotKeyPressed(evt);
            }
        });
        FormInput.add(idNyeriOtot);
        idNyeriOtot.setBounds(210, 400, 100, 23);

        LabelKelas4.setText("Anosmia :");
        LabelKelas4.setName("LabelKelas4"); // NOI18N
        FormInput.add(LabelKelas4);
        LabelKelas4.setBounds(130, 430, 80, 23);

        idAnosmia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak ", "1.Ya" }));
        idAnosmia.setName("idAnosmia"); // NOI18N
        idAnosmia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idAnosmiaKeyPressed(evt);
            }
        });
        FormInput.add(idAnosmia);
        idAnosmia.setBounds(210, 430, 100, 23);

        jLabel17.setText("Mual Muntah :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(330, 400, 70, 23);

        idMual.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak", "1.Ya" }));
        idMual.setName("idMual"); // NOI18N
        idMual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idMualKeyPressed(evt);
            }
        });
        FormInput.add(idMual);
        idMual.setBounds(400, 400, 100, 23);

        jLabel26.setText("Nafas Cepat :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(330, 430, 70, 23);

        idNafasCepat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak", "1.Ya" }));
        idNafasCepat.setName("idNafasCepat"); // NOI18N
        idNafasCepat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idNafasCepatKeyPressed(evt);
            }
        });
        FormInput.add(idNafasCepat);
        idNafasCepat.setBounds(400, 430, 100, 23);

        jLabel40.setText("Diare :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(510, 400, 70, 23);

        idDiare.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak", "1.Ya" }));
        idDiare.setName("idDiare"); // NOI18N
        idDiare.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idDiareKeyPressed(evt);
            }
        });
        FormInput.add(idDiare);
        idDiare.setBounds(580, 400, 100, 23);

        jLabel41.setText("Frek Nafas 30 Kali Per Menit :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(380, 460, 144, 23);

        idFrekNafas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak", "1.Ya" }));
        idFrekNafas.setName("idFrekNafas"); // NOI18N
        idFrekNafas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idFrekNafasKeyPressed(evt);
            }
        });
        FormInput.add(idFrekNafas);
        idFrekNafas.setBounds(530, 460, 100, 23);

        LabelKelas5.setText("Distress Pernafasan Berat :");
        LabelKelas5.setName("LabelKelas5"); // NOI18N
        FormInput.add(LabelKelas5);
        LabelKelas5.setBounds(100, 460, 160, 23);

        idNafasBerat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak ", "1.Ya" }));
        idNafasBerat.setName("idNafasBerat"); // NOI18N
        idNafasBerat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idNafasBeratKeyPressed(evt);
            }
        });
        FormInput.add(idNafasBerat);
        idNafasBerat.setBounds(260, 460, 100, 23);

        jLabel28.setText("Lainnya  :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(510, 430, 70, 23);

        idLainnya.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak", "1.Ya" }));
        idLainnya.setName("idLainnya"); // NOI18N
        idLainnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idLainnyaKeyPressed(evt);
            }
        });
        FormInput.add(idLainnya);
        idLainnya.setBounds(580, 430, 100, 23);

        jLabel30.setText("Inisial :");
        jLabel30.setName("jLabel30"); // NOI18N
        jLabel30.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel30);
        jLabel30.setBounds(520, 70, 70, 23);

        Inisial.setEditable(false);
        Inisial.setBackground(new java.awt.Color(245, 250, 240));
        Inisial.setHighlighter(null);
        Inisial.setName("Inisial"); // NOI18N
        FormInput.add(Inisial);
        Inisial.setBounds(600, 70, 150, 23);

        LabelPoli3.setText("Domisili Kab :");
        LabelPoli3.setName("LabelPoli3"); // NOI18N
        FormInput.add(LabelPoli3);
        LabelPoli3.setBounds(0, 160, 90, 23);

        KdKab.setEditable(false);
        KdKab.setBackground(new java.awt.Color(245, 250, 240));
        KdKab.setHighlighter(null);
        KdKab.setName("KdKab"); // NOI18N
        FormInput.add(KdKab);
        KdKab.setBounds(95, 160, 75, 23);

        NmKab.setEditable(false);
        NmKab.setBackground(new java.awt.Color(245, 250, 240));
        NmKab.setHighlighter(null);
        NmKab.setName("NmKab"); // NOI18N
        FormInput.add(NmKab);
        NmKab.setBounds(170, 160, 180, 23);

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
        btnDPJP1.setBounds(350, 160, 28, 23);

        jLabel12.setText("Pekerjaan :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 250, 90, 23);

        jLabel39.setText("Penyintas :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(20, 310, 70, 23);

        idPenyintas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak", "1.Ya" }));
        idPenyintas.setName("idPenyintas"); // NOI18N
        idPenyintas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idPenyintasKeyPressed(evt);
            }
        });
        FormInput.add(idPenyintas);
        idPenyintas.setBounds(90, 310, 100, 23);

        jLabel42.setText("Status Co Insiden :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(180, 310, 110, 23);

        idStatusCo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Tidak", "1.Ya" }));
        idStatusCo.setName("idStatusCo"); // NOI18N
        idStatusCo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idStatusCoKeyPressed(evt);
            }
        });
        FormInput.add(idStatusCo);
        idStatusCo.setBounds(290, 310, 100, 23);

        jLabel13.setText("Varian Covid :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(10, 280, 80, 23);

        jLabel14.setText("Domisili Prov :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(390, 160, 90, 23);

        KdProv.setEditable(false);
        KdProv.setBackground(new java.awt.Color(245, 250, 240));
        KdProv.setHighlighter(null);
        KdProv.setName("KdProv"); // NOI18N
        FormInput.add(KdProv);
        KdProv.setBounds(485, 160, 75, 23);

        NmProv.setEditable(false);
        NmProv.setBackground(new java.awt.Color(245, 250, 240));
        NmProv.setHighlighter(null);
        NmProv.setName("NmProv"); // NOI18N
        FormInput.add(NmProv);
        NmProv.setBounds(560, 160, 180, 23);

        btnPPKRujukan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPPKRujukan2.setMnemonic('X');
        btnPPKRujukan2.setToolTipText("Alt+X");
        btnPPKRujukan2.setName("btnPPKRujukan2"); // NOI18N
        btnPPKRujukan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPPKRujukan2ActionPerformed(evt);
            }
        });
        btnPPKRujukan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPPKRujukan2KeyPressed(evt);
            }
        });
        FormInput.add(btnPPKRujukan2);
        btnPPKRujukan2.setBounds(740, 160, 28, 23);

        KdVarian.setEditable(false);
        KdVarian.setBackground(new java.awt.Color(245, 250, 240));
        KdVarian.setHighlighter(null);
        KdVarian.setName("KdVarian"); // NOI18N
        FormInput.add(KdVarian);
        KdVarian.setBounds(95, 280, 75, 23);

        NmVarian.setEditable(false);
        NmVarian.setBackground(new java.awt.Color(245, 250, 240));
        NmVarian.setHighlighter(null);
        NmVarian.setName("NmVarian"); // NOI18N
        FormInput.add(NmVarian);
        NmVarian.setBounds(170, 280, 180, 23);

        btnPPKRujukan3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPPKRujukan3.setMnemonic('X');
        btnPPKRujukan3.setToolTipText("Alt+X");
        btnPPKRujukan3.setName("btnPPKRujukan3"); // NOI18N
        btnPPKRujukan3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPPKRujukan3ActionPerformed(evt);
            }
        });
        btnPPKRujukan3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPPKRujukan3KeyPressed(evt);
            }
        });
        FormInput.add(btnPPKRujukan3);
        btnPPKRujukan3.setBounds(350, 280, 28, 23);

        jLabel23.setText("Status Pasien :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(0, 220, 90, 23);

        LabelPoli1.setText("Status Rawat :");
        LabelPoli1.setName("LabelPoli1"); // NOI18N
        FormInput.add(LabelPoli1);
        LabelPoli1.setBounds(380, 220, 90, 23);

        LabelPoli4.setText("Alat Oksigen :");
        LabelPoli4.setName("LabelPoli4"); // NOI18N
        FormInput.add(LabelPoli4);
        LabelPoli4.setBounds(380, 280, 90, 23);

        LabelPoli5.setText("Kelompok Gejala :");
        LabelPoli5.setName("LabelPoli5"); // NOI18N
        FormInput.add(LabelPoli5);
        LabelPoli5.setBounds(380, 250, 90, 23);

        cmbGejala.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Tanpa Gejala", "2. Bergejala, tanpa klinis pneumonia", "3. Bergejala, dengan tanda klinis pneumonia", "4. Bergejala, dengan tanda klinis pneumonia berat" }));
        cmbGejala.setName("cmbGejala"); // NOI18N
        cmbGejala.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbGejalaItemStateChanged(evt);
            }
        });
        cmbGejala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbGejalaKeyPressed(evt);
            }
        });
        FormInput.add(cmbGejala);
        cmbGejala.setBounds(470, 250, 270, 23);

        cmbOksigen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "1. Nasal Kanul", "2. Simple Mask", "3. HFNC", "4. Ventilator" }));
        cmbOksigen.setName("cmbOksigen"); // NOI18N
        cmbOksigen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbOksigenKeyPressed(evt);
            }
        });
        FormInput.add(cmbOksigen);
        cmbOksigen.setBounds(470, 280, 270, 23);

        cmbPekerjaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Non Kesehatan", "2. Dokter", "3. Perawat", "4. Petugas Kesehatan Lainnya" }));
        cmbPekerjaan.setName("cmbPekerjaan"); // NOI18N
        cmbPekerjaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbPekerjaanKeyPressed(evt);
            }
        });
        FormInput.add(cmbPekerjaan);
        cmbPekerjaan.setBounds(90, 250, 270, 23);

        cmbAsalPasien.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0. Non PPLN/PMI", "1. PPLN (Pelaku Perjalanan Luar Negeri)", "2. PMI (Pekerja Migran Indonesia)" }));
        cmbAsalPasien.setName("cmbAsalPasien"); // NOI18N
        cmbAsalPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbAsalPasienKeyPressed(evt);
            }
        });
        FormInput.add(cmbAsalPasien);
        cmbAsalPasien.setBounds(90, 190, 270, 23);

        cmbJenisRawat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Rawat Jalan", "2. IGD", "3. Rawat Inap" }));
        cmbJenisRawat.setSelectedIndex(2);
        cmbJenisRawat.setName("cmbJenisRawat"); // NOI18N
        cmbJenisRawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJenisRawatKeyPressed(evt);
            }
        });
        FormInput.add(cmbJenisRawat);
        cmbJenisRawat.setBounds(470, 190, 270, 23);

        cmbStatusPasien.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Suspek", "3. Konfirmasi" }));
        cmbStatusPasien.setSelectedIndex(1);
        cmbStatusPasien.setName("cmbStatusPasien"); // NOI18N
        cmbStatusPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbStatusPasienKeyPressed(evt);
            }
        });
        FormInput.add(cmbStatusPasien);
        cmbStatusPasien.setBounds(90, 220, 270, 23);

        cmbStatusRawat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24. ICU Tekanan Negatif dengan Ventilator", "25. ICU Tekanan Negatif tanpa Ventilator", "26. ICU Tanpa Tekanan Negatif Dengan Ventilator", "27. ICU Tanpa Tekanan Negatif Tanpa Ventilator", "28. Isolasi Tekanan Negatif", "29. Isolasi Tanpa Tekanan Negatif", "30. NICU Khusus Covid", "31. PICU Khusus Covid", "32. IGD Khusus Covid", "33. VK (Ibu Melahirkan) Khusus Covid" }));
        cmbStatusRawat.setSelectedIndex(5);
        cmbStatusRawat.setName("cmbStatusRawat"); // NOI18N
        cmbStatusRawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbStatusRawatKeyPressed(evt);
            }
        });
        FormInput.add(cmbStatusRawat);
        cmbStatusRawat.setBounds(470, 220, 270, 23);

        Scroll1.setViewportView(FormInput);

        internalFrame2.add(Scroll1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Laporan Covid", internalFrame2);

        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-02-2022" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-02-2022" }));
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

        internalFrame4.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        TabRawat.addTab("Data Laporan Covid", internalFrame4);

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
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (KdVarian.getText().equals("")) {
            Valid.textKosong(KdVarian, "Varian");
        } else if (KdWarga.getText().equals("")) {
            Valid.textKosong(KdWarga, "Warga Negara");
        } else {
            insertBridging(token);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
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

}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed

}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        faskes.dispose();
        penyakit.dispose();
        varian.dispose();
        propinsi.dispose();
        kabupaten.dispose();
        kecamatan.dispose();
        poli.dispose();
        pekerjaan.dispose();
        sttsPasien.dispose();
        sttsRawat.dispose();
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
        tampil();
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
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            TCari.setText("");
            tampil();
        } else {
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
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

    private void btnPPKRujukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPPKRujukanActionPerformed
        pilihan = 1;
        faskes.setToken(token);
        faskes.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        faskes.setLocationRelativeTo(internalFrame1);
        faskes.setVisible(true);
    }//GEN-LAST:event_btnPPKRujukanActionPerformed

    private void btnPPKRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPPKRujukanKeyPressed

    }//GEN-LAST:event_btnPPKRujukanKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

    }//GEN-LAST:event_formWindowOpened

    private void ppPulangBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPulangBtnPrintActionPerformed

    }//GEN-LAST:event_ppPulangBtnPrintActionPerformed

    private void TanggalMasukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalMasukKeyPressed

    }//GEN-LAST:event_TanggalMasukKeyPressed

    private void TanggalOnsetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalOnsetKeyPressed

    }//GEN-LAST:event_TanggalOnsetKeyPressed

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

    private void idBatukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idBatukKeyPressed

    }//GEN-LAST:event_idBatukKeyPressed

    private void idDemamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idDemamKeyPressed

    }//GEN-LAST:event_idDemamKeyPressed

    private void NoTelpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoTelpKeyPressed

    }//GEN-LAST:event_NoTelpKeyPressed

    private void TNoRMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TNoRMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoRMActionPerformed

    private void ppRujukanBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppRujukanBtnPrintActionPerformed

    }//GEN-LAST:event_ppRujukanBtnPrintActionPerformed

    private void ppSEP1BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSEP1BtnPrintActionPerformed

    }//GEN-LAST:event_ppSEP1BtnPrintActionPerformed

    private void ppSEP2BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSEP2BtnPrintActionPerformed

    }//GEN-LAST:event_ppSEP2BtnPrintActionPerformed

    private void ppSEP3BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSEP3BtnPrintActionPerformed

    }//GEN-LAST:event_ppSEP3BtnPrintActionPerformed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if (TabRawat.getSelectedIndex() == 1) {
            tampil();
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

    private void idPilekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idPilekKeyPressed

    }//GEN-LAST:event_idPilekKeyPressed

    private void btnDPJPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDPJPActionPerformed
        kecamatan.setToken(token);
        kecamatan.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        kecamatan.setLocationRelativeTo(internalFrame1);
        kecamatan.setVisible(true);
    }//GEN-LAST:event_btnDPJPActionPerformed

    private void btnDPJPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDPJPKeyPressed

    }//GEN-LAST:event_btnDPJPKeyPressed

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
//        if (tbObat.getSelectedRow() != -1) {
//            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//            Map<String, Object> param = new HashMap<>();
//            param.put("namars", var.getnamars());
//            param.put("alamatrs", var.getalamatrs());
//            param.put("kotars", var.getkabupatenrs());
//            param.put("propinsirs", var.getpropinsirs());
//            param.put("kontakrs", var.getkontakrs());
//            param.put("logo", Sequel.cariGambar("select bpjs from gambar"));
//            if (JenisPelayanan.getSelectedIndex() == 0) {
//                Valid.MyReport("rptBridgingSEP.jrxml", "report", "::[ Cetak SEP ]::", "select bridging_sep.no_sep, bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tglsep,"
//                        + "bridging_sep.tglrujukan,bridging_sep.no_rujukan,bridging_sep.kdppkrujukan,"
//                        + "bridging_sep.nmppkrujukan,bridging_sep.kdppkpelayanan,bridging_sep.nmppkpelayanan,"
//                        + "if(bridging_sep.jnspelayanan='1','Rawat Inap','Rawat Jalan'),bridging_sep.catatan,bridging_sep.diagawal,"
//                        + "bridging_sep.nmdiagnosaawal,bridging_sep.kdpolitujuan,bridging_sep.nmpolitujuan,"
//                        + "if(bridging_sep.klsrawat='1','Kelas 1',if(bridging_sep.klsrawat='2','Kelas 2','Kelas 3')),"
//                        + "if(bridging_sep.lakalantas='0','Kasus Kecelakaan','Bukan Kasus Kecelakaan'),"
//                        + "concat(bridging_sep.nmkec,', ',bridging_sep.nmkab,', ',bridging_sep.nmprop) as lokasilaka,bridging_sep.user, "
//                        + "bridging_sep.tanggal_lahir,bridging_sep.peserta,bridging_sep.jkel,bridging_sep.no_kartu,bridging_sep.asal_rujukan,bridging_sep.eksekutif,bridging_sep.cob,bridging_sep.penjamin,bridging_sep.notelep,petugas.nip,petugas.nama from bridging_sep inner join petugas on bridging_sep.user=petugas.nip where no_sep='" + tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString() + "'", param);
//            } else {
//                Valid.MyReport("rptBridgingSEP2.jrxml", "report", "::[ Cetak SEP ]::", "select bridging_sep.no_sep, bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tglsep,"
//                        + "bridging_sep.tglrujukan,bridging_sep.no_rujukan,bridging_sep.kdppkrujukan,"
//                        + "bridging_sep.nmppkrujukan,bridging_sep.kdppkpelayanan,bridging_sep.nmppkpelayanan,"
//                        + "if(bridging_sep.jnspelayanan='1','Rawat Inap','Rawat Jalan'),bridging_sep.catatan,bridging_sep.diagawal,"
//                        + "bridging_sep.nmdiagnosaawal,bridging_sep.kdpolitujuan,bridging_sep.nmpolitujuan,"
//                        + "if(bridging_sep.klsrawat='1','Kelas 1',if(bridging_sep.klsrawat='2','Kelas 2','Kelas 3')),"
//                        + "if(bridging_sep.lakalantas='0','Kasus Kecelakaan','Bukan Kasus Kecelakaan'),concat(bridging_sep.nmkec,', ',bridging_sep.nmkab,', ',bridging_sep.nmprop) as lokasilaka,bridging_sep.user, "
//                        + "bridging_sep.tanggal_lahir,bridging_sep.peserta,bridging_sep.jkel,bridging_sep.no_kartu,bridging_sep.asal_rujukan,bridging_sep.eksekutif,bridging_sep.cob,bridging_sep.penjamin,bridging_sep.notelep,dokter.nm_dokter "
//                        + "from bridging_sep inner join reg_periksa INNER JOIN dokter on bridging_sep.no_rawat=reg_periksa.no_rawat AND dokter.kd_dokter=reg_periksa.kd_dokter AND reg_periksa.kd_dokter NOT IN ('-') where no_sep='" + tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString() + "'", param);
//            }
//            this.setCursor(Cursor.getDefaultCursor());
//        } else {
//            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data SEP yang mau dicetak...!!!!");
//            BtnBatal.requestFocus();
//        }
    }//GEN-LAST:event_ppSEPBtnPrintActionPerformed


    private void ppSEP4BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSEP4BtnPrintActionPerformed
        if (tbObat.getSelectedRow() != -1) {
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
            /* Tambahan source ulun (Ridho Alfian [ICT RSHD])*/
            param.put("diagnosa1", Sequel.cariIsi("select b.nm_penyakit FROM diagnosa_pasien as a, penyakit as b, "
                    + "reg_periksa as c,pasien as d WHERE a.no_rawat = c.no_rawat AND "
                    + "c.no_rkm_medis = d.no_rkm_medis AND a.kd_penyakit = b.kd_penyakit AND a.prioritas = '1' "
                    + "AND a.no_rawat =?", TNoRw.getText()));
            param.put("diagnosa2", Sequel.cariIsi("select b.nm_penyakit FROM diagnosa_pasien as a, penyakit as b, "
                    + "reg_periksa as c,pasien as d WHERE a.no_rawat = c.no_rawat AND "
                    + "c.no_rkm_medis = d.no_rkm_medis AND a.kd_penyakit = b.kd_penyakit AND a.prioritas = '2' "
                    + "AND a.no_rawat =?", TNoRw.getText()));
            param.put("kd_diagnosa1", Sequel.cariIsi("select b.kd_penyakit FROM diagnosa_pasien as a, penyakit as b, "
                    + "reg_periksa as c,pasien as d WHERE a.no_rawat = c.no_rawat AND "
                    + "c.no_rkm_medis = d.no_rkm_medis AND a.kd_penyakit = b.kd_penyakit AND a.prioritas = '1' "
                    + "AND a.no_rawat =?", TNoRw.getText()));
            param.put("kd_diagnosa2", Sequel.cariIsi("select b.kd_penyakit FROM diagnosa_pasien as a, penyakit as b, "
                    + "reg_periksa as c,pasien as d WHERE a.no_rawat = c.no_rawat AND "
                    + "c.no_rkm_medis = d.no_rkm_medis AND a.kd_penyakit = b.kd_penyakit AND a.prioritas = '2' "
                    + "AND a.no_rawat =?", TNoRw.getText()));
            param.put("kd_icd9_1", Sequel.cariIsi("select b.kode FROM prosedur_pasien as a, icd9 as b, "
                    + "reg_periksa as c,pasien as d WHERE a.no_rawat = c.no_rawat AND "
                    + "c.no_rkm_medis = d.no_rkm_medis AND a.kode = b.kode AND a.prioritas = '1' "
                    + "AND a.no_rawat =?", TNoRw.getText()));
            param.put("kd_icd9_2", Sequel.cariIsi("select b.kode FROM prosedur_pasien as a, icd9 as b, "
                    + "reg_periksa as c,pasien as d WHERE a.no_rawat = c.no_rawat AND "
                    + "c.no_rkm_medis = d.no_rkm_medis AND a.kode = b.kode AND a.prioritas = '2' "
                    + "AND a.no_rawat =?", TNoRw.getText()));
            param.put("icd9_1", Sequel.cariIsi("select b.deskripsi_pendek FROM prosedur_pasien as a, icd9 as b, "
                    + "reg_periksa as c,pasien as d WHERE a.no_rawat = c.no_rawat AND "
                    + "c.no_rkm_medis = d.no_rkm_medis AND a.kode = b.kode AND a.prioritas = '1' "
                    + "AND a.no_rawat =?", TNoRw.getText()));
            param.put("icd9_2", Sequel.cariIsi("select b.deskripsi_pendek FROM prosedur_pasien as a, icd9 as b, "
                    + "reg_periksa as c,pasien as d WHERE a.no_rawat = c.no_rawat AND "
                    + "c.no_rkm_medis = d.no_rkm_medis AND a.kode = b.kode AND a.prioritas = '2' "
                    + "AND a.no_rawat =?", TNoRw.getText()));
            param.put("pemeriksaan", Sequel.cariIsi("select pemeriksaan from pemeriksaan_ralan where no_rawat=?", TNoRw.getText()));
            param.put("keluhan", Sequel.cariIsi("select keluhan from pemeriksaan_ralan where no_rawat=?", TNoRw.getText()));
            param.put("tensi", Sequel.cariIsi("select tensi from pemeriksaan_ralan where no_rawat=?", TNoRw.getText()));
            param.put("nadi", Sequel.cariIsi("select nadi from pemeriksaan_ralan where no_rawat=?", TNoRw.getText()));
            param.put("respirasi", Sequel.cariIsi("select respirasi from pemeriksaan_ralan where no_rawat=?", TNoRw.getText()));

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

    private void idSakitTenggorokanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idSakitTenggorokanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_idSakitTenggorokanKeyPressed

    private void idSesakNafasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idSesakNafasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_idSesakNafasKeyPressed

    private void idLemasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idLemasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_idLemasKeyPressed

    private void idNyeriOtotKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idNyeriOtotKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_idNyeriOtotKeyPressed

    private void idAnosmiaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idAnosmiaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_idAnosmiaKeyPressed

    private void idMualKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idMualKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_idMualKeyPressed

    private void idNafasCepatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idNafasCepatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_idNafasCepatKeyPressed

    private void idDiareKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idDiareKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_idDiareKeyPressed

    private void idFrekNafasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idFrekNafasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_idFrekNafasKeyPressed

    private void idNafasBeratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idNafasBeratKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_idNafasBeratKeyPressed

    private void idLainnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idLainnyaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_idLainnyaKeyPressed

    private void btnDPJP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDPJP1ActionPerformed
        kabupaten.setDataCari(kode_kab);
        kabupaten.setToken(token);
        kabupaten.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        kabupaten.setLocationRelativeTo(internalFrame1);
        kabupaten.setVisible(true);
    }//GEN-LAST:event_btnDPJP1ActionPerformed

    private void btnDPJP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDPJP1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDPJP1KeyPressed

    private void idPenyintasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idPenyintasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_idPenyintasKeyPressed

    private void idStatusCoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idStatusCoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_idStatusCoKeyPressed

    private void btnPPKRujukan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPPKRujukan2ActionPerformed
        propinsi.setDataCari(kode_prov);
        propinsi.setToken(token);
        propinsi.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        propinsi.setLocationRelativeTo(internalFrame1);
        propinsi.setVisible(true);
    }//GEN-LAST:event_btnPPKRujukan2ActionPerformed

    private void btnPPKRujukan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPPKRujukan2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPPKRujukan2KeyPressed

    private void btnPPKRujukan3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPPKRujukan3ActionPerformed
        pilihan = 1;
        varian.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        varian.setLocationRelativeTo(internalFrame1);
        varian.setToken(token);
        varian.setVisible(true);
    }//GEN-LAST:event_btnPPKRujukan3ActionPerformed

    private void btnPPKRujukan3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPPKRujukan3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPPKRujukan3KeyPressed

    private void cmbGejalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbGejalaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbGejalaKeyPressed

    private void cmbOksigenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbOksigenKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbOksigenKeyPressed

    private void cmbPekerjaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbPekerjaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPekerjaanKeyPressed

    private void cmbAsalPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbAsalPasienKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbAsalPasienKeyPressed

    private void cmbJenisRawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJenisRawatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbJenisRawatKeyPressed

    private void cmbStatusPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbStatusPasienKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbStatusPasienKeyPressed

    private void cmbStatusRawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbStatusRawatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbStatusRawatKeyPressed

    private void cmbGejalaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbGejalaItemStateChanged
        if (cmbGejala.getSelectedIndex() == 0) {
            cmbOksigen.setVisible(false);
            cmbOksigen.setSelectedIndex(-1);
            LabelPoli4.setVisible(false);
            idDemam.setVisible(false);
            idDiare.setVisible(false);
            idDemam.setSelectedIndex(0);
            idBatuk.setSelectedIndex(0);
            idPilek.setSelectedIndex(0);
            idNafasCepat.setSelectedIndex(0);
            idSesakNafas.setSelectedIndex(0);
            idAnosmia.setVisible(false);
            idBatuk.setVisible(false);
            idFrekNafas.setVisible(false);
            idLainnya.setVisible(false);
            idLemas.setVisible(false);
            idMual.setVisible(false);
            idNafasBerat.setVisible(false);
            idNafasCepat.setVisible(false);
            idNyeriOtot.setVisible(false);
            idPilek.setVisible(false);
            idSakitTenggorokan.setVisible(false);
            idSesakNafas.setVisible(false);
            LabelKelas1.setVisible(false);
            LabelKelas2.setVisible(false);
            LabelKelas3.setVisible(false);
            LabelKelas4.setVisible(false);
            LabelKelas5.setVisible(false);
            jLabel15.setVisible(false);
            jLabel16.setVisible(false);
            jLabel17.setVisible(false);
            jLabel26.setVisible(false);
            jLabel37.setVisible(false);
            jLabel38.setVisible(false);
            jLabel40.setVisible(false);
            jLabel28.setVisible(false);
            jLabel41.setVisible(false);
        } else if (cmbGejala.getSelectedIndex() == 1) {
            cmbOksigen.setVisible(false);
            cmbOksigen.setSelectedIndex(-1);
            LabelPoli4.setVisible(false);
            idDemam.setVisible(true);
            idDemam.setSelectedIndex(1);
            idDiare.setVisible(true);
            idBatuk.setSelectedIndex(1);
            idPilek.setSelectedIndex(1);
            idNafasCepat.setSelectedIndex(1);
            idSesakNafas.setSelectedIndex(0);
            idAnosmia.setVisible(true);
            idBatuk.setVisible(true);
            idFrekNafas.setVisible(true);
            idLainnya.setVisible(true);
            idLemas.setVisible(true);
            idMual.setVisible(true);
            idNafasBerat.setVisible(true);
            idNafasCepat.setVisible(true);
            idNyeriOtot.setVisible(true);
            idPilek.setVisible(true);
            idSakitTenggorokan.setVisible(true);
            idSesakNafas.setVisible(true);
            LabelKelas1.setVisible(true);
            LabelKelas2.setVisible(true);
            LabelKelas3.setVisible(true);
            LabelKelas4.setVisible(true);
            LabelKelas5.setVisible(true);
            jLabel15.setVisible(true);
            jLabel16.setVisible(true);
            jLabel17.setVisible(true);
            jLabel26.setVisible(true);
            jLabel37.setVisible(true);
            jLabel38.setVisible(true);
            jLabel40.setVisible(true);
            jLabel28.setVisible(true);
            jLabel41.setVisible(true);
        } else {
            cmbOksigen.setVisible(true);
            cmbOksigen.setSelectedIndex(0);
            LabelPoli4.setVisible(true);
            idDemam.setVisible(true);
            idDemam.setSelectedIndex(1);
            idBatuk.setSelectedIndex(1);
            idPilek.setSelectedIndex(1);
            idNafasCepat.setSelectedIndex(0);
            idSesakNafas.setSelectedIndex(1);
            idDiare.setVisible(true);
            idAnosmia.setVisible(true);
            idBatuk.setVisible(true);
            idFrekNafas.setVisible(true);
            idLainnya.setVisible(true);
            idLemas.setVisible(true);
            idMual.setVisible(true);
            idNafasBerat.setVisible(true);
            idNafasCepat.setVisible(true);
            idNyeriOtot.setVisible(true);
            idPilek.setVisible(true);
            idSakitTenggorokan.setVisible(true);
            idSesakNafas.setVisible(true);
            LabelKelas1.setVisible(true);
            LabelKelas2.setVisible(true);
            LabelKelas3.setVisible(true);
            LabelKelas4.setVisible(true);
            LabelKelas5.setVisible(true);
            jLabel15.setVisible(true);
            jLabel16.setVisible(true);
            jLabel17.setVisible(true);
            jLabel26.setVisible(true);
            jLabel37.setVisible(true);
            jLabel38.setVisible(true);
            jLabel40.setVisible(true);
            jLabel28.setVisible(true);
            jLabel41.setVisible(true);
        }
    }//GEN-LAST:event_cmbGejalaItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            SirsLaporanCovid19V3 dialog = new SirsLaporanCovid19V3(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Inisial;
    private widget.TextBox JK;
    private widget.TextBox KdKab;
    private widget.TextBox KdKec;
    private widget.TextBox KdProv;
    private widget.TextBox KdVarian;
    private widget.TextBox KdWarga;
    private widget.Label LCount;
    private widget.Label LabelKelas1;
    private widget.Label LabelKelas2;
    private widget.Label LabelKelas3;
    private widget.Label LabelKelas4;
    private widget.Label LabelKelas5;
    private widget.Label LabelPoli;
    private widget.Label LabelPoli1;
    private widget.Label LabelPoli2;
    private widget.Label LabelPoli3;
    private widget.Label LabelPoli4;
    private widget.Label LabelPoli5;
    private widget.TextBox Nik;
    private widget.TextBox NmKab;
    private widget.TextBox NmKec;
    private widget.TextBox NmProv;
    private widget.TextBox NmVarian;
    private widget.TextBox NmWarga;
    private widget.TextBox NoBalasan;
    private widget.TextBox NoTelp;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TanggalMasuk;
    private widget.Tanggal TanggalOnset;
    private widget.TextBox TglLahir;
    private widget.Button btnDPJP;
    private widget.Button btnDPJP1;
    private widget.Button btnPPKRujukan;
    private widget.Button btnPPKRujukan2;
    private widget.Button btnPPKRujukan3;
    private widget.ComboBox cmbAsalPasien;
    private widget.ComboBox cmbGejala;
    private widget.ComboBox cmbJenisRawat;
    private widget.ComboBox cmbOksigen;
    private widget.ComboBox cmbPekerjaan;
    private widget.ComboBox cmbStatusPasien;
    private widget.ComboBox cmbStatusRawat;
    private widget.TextBox email;
    private widget.ComboBox idAnosmia;
    private widget.ComboBox idBatuk;
    private widget.ComboBox idDemam;
    private widget.ComboBox idDiare;
    private widget.ComboBox idFrekNafas;
    private widget.ComboBox idLainnya;
    private widget.ComboBox idLemas;
    private widget.ComboBox idMual;
    private widget.ComboBox idNafasBerat;
    private widget.ComboBox idNafasCepat;
    private widget.ComboBox idNyeriOtot;
    private widget.ComboBox idPenyintas;
    private widget.ComboBox idPilek;
    private widget.ComboBox idSakitTenggorokan;
    private widget.ComboBox idSesakNafas;
    private widget.ComboBox idStatusCo;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame4;
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
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel4;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.TextBox noPassport;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JMenuItem ppDetailSEPPeserta;
    private javax.swing.JMenuItem ppPulang;
    private javax.swing.JMenuItem ppRiwayatPerawatan;
    private javax.swing.JMenuItem ppRujukan;
    private javax.swing.JMenuItem ppSEP;
    private javax.swing.JMenuItem ppSEP1;
    private javax.swing.JMenuItem ppSEP2;
    private javax.swing.JMenuItem ppSEP3;
    private javax.swing.JMenuItem ppSEP4;
    private widget.Table tbObat;
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

    private void isRawat() {
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ", TNoRM, TNoRw.getText());

    }

    private void emptTeks() {
        TNoRw.setText("");
        TPasien.setText("");
        TanggalOnset.setDate(new Date());
        TanggalMasuk.setDate(new Date());
        TglLahir.setText("");
        Nik.setText("");
        email.setText("");
        noPassport.setText("");
        JK.setText("");
        KdWarga.setText("");
        NmWarga.setText("");
        TNoRM.setText("");
        KdKec.setText("");
        NmKec.setText("");
        KdKab.setText("");
        NmKab.setText("");
        KdProv.setText("");
        NmProv.setText("");
        KdVarian.setText("");
        NmVarian.setText("");
        Inisial.setText("");
        NoTelp.setText("");
//        idPilek.setSelectedIndex(0);

    }

    public void setNoRm(String norwt, String norm, String nama, String tokenKamar) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        TNoRM.setText(norm);
        token = tokenKamar;
        TPasien.setText(Sequel.cariIsi("SELECT nm_pasien FROM pasien WHERE no_rkm_medis = ?", TNoRM.getText()));
        cariInisial(nama);
        Nik.setText(Sequel.cariIsi("SELECT no_ktp FROM pasien WHERE no_rkm_medis = ?", TNoRM.getText()));
        JK.setText(Sequel.cariIsi("SELECT jk FROM pasien WHERE no_rkm_medis = ?", TNoRM.getText()));
        NoTelp.setText(Sequel.cariIsi("SELECT no_tlp FROM pasien WHERE no_rkm_medis = ?", TNoRM.getText()));
        email.setText(Sequel.cariIsi("SELECT email FROM pasien WHERE no_rkm_medis = ?", TNoRM.getText()));
        TglLahir.setText(Sequel.cariIsi("SELECT tgl_lahir FROM pasien WHERE no_rkm_medis = ?", TNoRM.getText()));
        Valid.SetTgl(TanggalMasuk, Sequel.cariIsi("SELECT tgl_masuk FROM kamar_inap WHERE no_rawat = ?", TNoRw.getText()));
        kd_kecamatan = Sequel.cariIsi("SELECT kd_kec FROM pasien WHERE no_rkm_medis = ?", TNoRM.getText());
        cariKecamatan(kd_kecamatan);
//        isRawat();
    }

    public void isCek() {
        BtnSimpan.setEnabled(var.getbpjs_sep());
        BtnHapus.setEnabled(var.getbpjs_sep());
        BtnPrint.setEnabled(var.getbpjs_sep());
        BtnEdit.setEnabled(var.getbpjs_sep());
        ppDetailSEPPeserta.setEnabled(var.getbpjs_sep());
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

            KdWarga.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString());
            NmWarga.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString());

            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 20).toString());
//            JenisPeserta.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 21).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 22).toString());
//            NoKartu.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 23).toString());
            idBatuk.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 26).toString());
            idDemam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 27).toString());

            NoTelp.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 29).toString());
            /*"Kd Prop",
                "Propinsi","Kd Kab","Kabupaten","Kd Kec","Kecamatan","No.SKDP",
                "Kd DPJP","DPJP"*/
            idPilek.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 30).toString());

            KdKec.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 42).toString());
            NmKec.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 43).toString());

            Valid.SetTgl(TanggalMasuk, tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString());

//            Status.setText("AKTIF");
        }
    }

    public void cariKecamatan(String kode) {
        switch (kode) {
            case "3520":
                KdKec.setText("630708");
                NmKec.setText("BATANG ALAI UTARA");
                siapKabProv();
                break;
            case "3519":
                KdKec.setText("630707");
                NmKec.setText("BATANG ALAI SELATAN");
                siapKabProv();
                break;
            case "4337":
                KdKec.setText("630710");
                NmKec.setText("BATANG ALAI TIMUR");
                siapKabProv();
                break;
            case "3523":
                KdKec.setText("630701");
                NmKec.setText("HARUYAN");
                siapKabProv();
                break;
            case "3521":
                KdKec.setText("630702");
                NmKec.setText("BATU BENAWA");
                siapKabProv();
                break;
            case "3524":
                KdKec.setText("630703");
                NmKec.setText("LABUAN AMAS SELATAN");
                siapKabProv();
                break;
            case "3525":
                KdKec.setText("630704");
                NmKec.setText("LABUAN AMAS UTARA");
                siapKabProv();
                break;
            case "3526":
                KdKec.setText("630705");
                NmKec.setText("PANDAWAN");
                siapKabProv();
                break;
            case "3518":
                KdKec.setText("630706");
                NmKec.setText("BARABAI");
                siapKabProv();
                break;
            case "3522":
                KdKec.setText("630709");
                NmKec.setText("HANTAKAN");
                siapKabProv();
                break;
            case "4300":
                KdKec.setText("630711");
                NmKec.setText("LIMPASU");
                siapKabProv();
                break;
        }
    }

    public void siapKabProv() {
        KdWarga.setText("ID");
        NmWarga.setText("Indonesia");
        KdKab.setText("6307");
        NmKab.setText("KAB. HULU SUNGAI TENGAH");
        KdProv.setText("63");
        NmProv.setText("KALIMANTAN SELATAN");
    }

    public void cariInisial(String contoh) {
        String example;
        String initial = "";
        int find = contoh.indexOf("BIN");
        String word;
        if (find > 0) {
            word = contoh.substring(find - 1);
            example = contoh.replace(word, "");
        } else {
            word = contoh.substring(contoh.indexOf("(") - 1);
            example = contoh.replace(word, "");
        }

        char c[] = example.toCharArray();

        for (int i = 0; i < c.length; i++) {
            // Logic to implement first character of each word in a string
            if (c[i] != ' ' && (i == 0 || c[i - 1] == ' ')) {
                initial += c[i];
            }
        }
        Inisial.setText(initial);
    }

    public void insertBridging(String tokenku) {
        try {
            String alatOksigen, nopass, e_mail;
            if (cmbOksigen.getSelectedIndex() == 0) {
                alatOksigen = null;
            } else {
                alatOksigen = "\"" + cmbOksigen.getSelectedItem().toString().substring(0, 1) + "\"";
            }
            if (noPassport.getText().equals("")) {
                nopass = null;
            } else {
                nopass = "\"" + noPassport.getText() + "\"";
            }
            if (email.getText().equals("")) {
                e_mail = null;
            } else {
                e_mail = "\"" + email.getText() + "\"";
            }
            String url = link + "/api/laporancovid19versi3";
            headers = new HttpHeaders();
            headers.add("accept", "*/*");
            headers.add("Authorization", "Bearer " + tokenku);
            headers.add("Content-Type", "application/json");
            requestJson = "{"
                    + "\"kewarganegaraanId\": \"" + KdWarga.getText() + "\","
                    + "\"nik\": \"" + Nik.getText() + "\","
                    + "\"noPassport\": " + nopass + ","
                    + "\"asalPasienId\": \"" + cmbAsalPasien.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"noRM\": \"" + TNoRM.getText() + "\","
                    + "\"namaLengkapPasien\": \"" + TPasien.getText() + "\","
                    + "\"namaInisialPasien\": \"" + Inisial.getText() + "\","
                    + "\"tanggalLahir\": \"" + TglLahir.getText() + "\","
                    + "\"email\": " + e_mail + ","
                    + "\"noTelp\": \"" + NoTelp.getText() + "\","
                    + "\"jenisKelaminId\": \"" + JK.getText() + "\","
                    + "\"domisiliKecamatanId\": \"" + KdKec.getText() + "\","
                    + "\"domisiliKabKotaId\": \"" + KdKab.getText() + "\","
                    + "\"domisiliProvinsiId\": \"" + KdProv.getText() + "\","
                    + "\"pekerjaanId\": \"" + cmbPekerjaan.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"tanggalMasuk\": \"" + Valid.SetTgl(TanggalMasuk.getSelectedItem() + "") + "\","
                    + "\"jenisPasienId\": \"" + cmbJenisRawat.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"varianCovidId\": \"" + KdVarian.getText() + "\","
                    + "\"statusPasienId\": \"" + cmbStatusPasien.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"statusCoInsidenId\": \"" + idStatusCo.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"statusRawatId\": \"" + cmbStatusRawat.getSelectedItem().toString().substring(0, 2) + "\","
                    + "\"alatOksigenId\": " + alatOksigen + ","
                    + "\"penyintasId\": \"" + idPenyintas.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"tanggalOnsetGejala\": \"" + Valid.SetTgl(TanggalOnset.getSelectedItem() + "") + "\","
                    + "\"kelompokGejalaId\": \"" + cmbGejala.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"gejala\": {"
                    + "\"demamId\": \"" + idDemam.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"batukId\": \"" + idBatuk.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"pilekId\": \"" + idPilek.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"sakitTenggorokanId\": \"" + idSakitTenggorokan.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"sesakNapasId\": \"" + idSesakNafas.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"lemasId\": \"" + idLemas.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"nyeriOtotId\": \"" + idNyeriOtot.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"mualMuntahId\": \"" + idMual.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"diareId\": \"" + idDiare.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"anosmiaId\": \"" + idAnosmia.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"napasCepatId\": \"" + idNafasCepat.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"frekNapas30KaliPerMenitId\": \"" + idFrekNafas.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"distresPernapasanBeratId\": \"" + idNafasBerat.getSelectedItem().toString().substring(0, 1) + "\","
                    + "\"lainnyaId\": \"" + idLainnya.getSelectedItem().toString().substring(0, 1) + "\""
                    + "}"
                    + "}";
            System.out.println("JSON : " + requestJson);
            requestEntity = new HttpEntity(requestJson, headers);
            root = mapper.readTree(api.getRest().exchange(url, HttpMethod.POST, requestEntity, String.class).getBody());
            System.out.println(root);
//            JOptionPane.showMessageDialog(null, root.path("message"));
            id_laporan_covid = root.path("data").path("id").toString();
            if (root.path("status").toString().equals("true")) {
                if(Sequel.menyimpantf("bridging_covid", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "id" ,30 , new String[]{
                    id_laporan_covid,TNoRw.getText(),nopass,Inisial.getText(),Valid.SetTgl(TanggalOnset.getSelectedItem() + ""),KdWarga.getText(),cmbAsalPasien.getSelectedItem().toString().substring(0, 1),
                    cmbJenisRawat.getSelectedItem().toString().substring(0, 1),cmbStatusPasien.getSelectedItem().toString().substring(0, 1),cmbStatusRawat.getSelectedItem().toString().substring(0, 2),
                    cmbPekerjaan.getSelectedItem().toString().substring(0, 1),cmbGejala.getSelectedItem().toString().substring(0, 1),KdVarian.getText(),alatOksigen,idPenyintas.getSelectedItem().toString().substring(0, 1),
                    idStatusCo.getSelectedItem().toString().substring(0, 1),idDemam.getSelectedItem().toString().substring(0, 1),idBatuk.getSelectedItem().toString().substring(0, 1),idPilek.getSelectedItem().toString().substring(0, 1),
                    idSakitTenggorokan.getSelectedItem().toString().substring(0, 1),idSesakNafas.getSelectedItem().toString().substring(0, 1),idLemas.getSelectedItem().toString().substring(0, 1),idNyeriOtot.getSelectedItem().toString().substring(0, 1),
                    idMual.getSelectedItem().toString().substring(0, 1),idDiare.getSelectedItem().toString().substring(0, 1),idAnosmia.getSelectedItem().toString().substring(0, 1),idNafasCepat.getSelectedItem().toString().substring(0, 1),
                    idLainnya.getSelectedItem().toString().substring(0, 1),idNafasBerat.getSelectedItem().toString().substring(0, 1),idFrekNafas.getSelectedItem().toString().substring(0, 1)
                }) == true){
                    JOptionPane.showMessageDialog(null, "Berhasil Simpan");
                    emptTeks();
                };
            } else {
                JOptionPane.showMessageDialog(null, root.path("status").toString());
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
            System.out.println(root.path("message"));
            JOptionPane.showMessageDialog(null, "Gagal Melakukan Bridging Laporan Covid 19 V3 "+ root.path("message"));
        }
    }
}
