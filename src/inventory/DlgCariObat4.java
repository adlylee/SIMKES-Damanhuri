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
package inventory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable2;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import simrskhanza.DlgCariBangsal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author dosen
 */
public final class DlgCariObat4 extends javax.swing.JDialog {

    private final DefaultTableModel tabMode, tabModeObatRacikan, tabModeDetailObatRacikan;
    ;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private riwayatobat Trackobat = new riwayatobat();
    private PreparedStatement psobat, pscarikapasitas, psstok, psrekening, ps2, psbatch;
    private ResultSet rsobat, carikapasitas, rsstok, rsrekening, rs2, rsbatch;
    private Jurnal jur = new Jurnal();
    private double h_belicari = 0, hargacari = 0, sisacari = 0, x = 0, y = 0, embalase = Sequel.cariIsiAngka("select embalase_per_obat from set_embalase"),
            tuslah = Sequel.cariIsiAngka("select tuslah_per_obat from set_embalase"), kenaikan, stokbarang, ttlhpp, ttljual;
    private int jml = 0, i = 0, z = 0, row = 0;
    private boolean[] pilih;
    private double[] jumlah, harga, eb, ts, stok, beli, kapasitas, kandungan;
    private String[] no, kodebarang, namabarang, kodesatuan, letakbarang, namajenis, industri, aturan, kategori, golongan, nobatch, nofaktur, kadaluarsa;
    private DlgBarang barang = new DlgBarang(null, false);
    private String signa1 = "1", signa2 = "1", kdObatSK = "", requestJson = "", nokunjungan = "", URL = "", otorisasi, sql = "", no_batchcari = "", tgl_kadaluarsacari = "", no_fakturcari = "", aktifkanbatch = "no", aktifpcare = "no", noresep = "", Suspen_Piutang_Obat_Ranap = "", Obat_Ranap = "", HPP_Obat_Rawat_Inap = "", Persediaan_Obat_Rawat_Inap = "";
    private WarnaTable2 warna = new WarnaTable2();
    private DlgCariBangsal caribangsal = new DlgCariBangsal(null, false);
    public DlgAturanPakai aturanpakai = new DlgAturanPakai(null, false);
    private DlgMetodeRacik metoderacik = new DlgMetodeRacik(null, false);
    private WarnaTable2 warna2 = new WarnaTable2();
    private WarnaTable2 warna3 = new WarnaTable2();
    private final Properties prop = new Properties();
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private String[] arrSplit;

    /**
     * Creates new form DlgPenyakit
     *
     * @param parent
     * @param modal
     */
    public DlgCariObat4(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10, 2);
        setSize(656, 250);

        Object[] row = {"K", "Jumlah", "Kode", "Nama Barang", "Satuan", "Letak Barang",
            "Harga(Rp)", "Jenis Obat", "Emb", "Tslh", "Stok", "I.F.", "H.Beli",
            "Aturan Pakai", "Kategori", "Golongan", "No.Batch", "No.Faktur", "Kadaluarsa"
        };
        tabMode = new DefaultTableModel(null, row) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if ((colIndex == 0) || (colIndex == 1) || (colIndex == 8) || (colIndex == 9) || (colIndex == 13) || (colIndex == 16)) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class,
                java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Object.class, java.lang.Double.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbObat.setModel(tabMode);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 19; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(45);
            } else if (i == 2) {
                column.setPreferredWidth(70);
            } else if (i == 3) {
                column.setPreferredWidth(200);
            } else if (i == 4) {
                column.setPreferredWidth(70);
            } else if (i == 5) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 6) {
                column.setPreferredWidth(70);
            } else if (i == 7) {
                column.setPreferredWidth(85);
            } else if (i == 8) {
                column.setPreferredWidth(40);
            } else if (i == 9) {
                column.setPreferredWidth(40);
            } else if (i == 10) {
                column.setPreferredWidth(40);
            } else if (i == 11) {
                column.setPreferredWidth(80);
            } else if (i == 12) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 13) {
                column.setPreferredWidth(100);
            } else if (i == 14) {
                column.setPreferredWidth(80);
            } else if (i == 15) {
                column.setPreferredWidth(80);
            } else if (i == 16) {
                column.setPreferredWidth(75);
            } else if (i == 17) {
                column.setPreferredWidth(75);
            } else if (i == 18) {
                column.setPreferredWidth(65);
            } 
        }
        warna.kolom = 1;
        tbObat.setDefaultRenderer(Object.class, warna);

        tabModeObatRacikan = new DefaultTableModel(null, new Object[]{
            "No", "Nama Racikan", "Kode Racik", "Metode Racik", "Jml.Racik",
            "Aturan Pakai", "Keterangan"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = true;
                if ((colIndex == 0) || (colIndex == 2) || (colIndex == 3)) {
                    a = false;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };

        tbObatRacikan.setModel(tabModeObatRacikan);
        tbObatRacikan.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbObatRacikan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbObatRacikan.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(25);
            } else if (i == 1) {
                column.setPreferredWidth(250);
            } else if (i == 2) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 3) {
                column.setPreferredWidth(100);
            } else if (i == 4) {
                column.setPreferredWidth(60);
            } else if (i == 5) {
                column.setPreferredWidth(200);
            } else if (i == 6) {
                column.setPreferredWidth(250);
            }
        }

        warna2.kolom = 4;
        tbObatRacikan.setDefaultRenderer(Object.class, warna2);

        tabModeDetailObatRacikan = new DefaultTableModel(null, new Object[]{
            "No", "Kode Barang", "Nama Barang", "Satuan", "Harga(Rp)", "H.Beli",
            "Jenis Obat", "Stok", "Kps", "Kandungan", "Jml",
            "Embal", "Tuslah", "I.F.", "Kategori", "Golongan", "No.Batch", "No.Faktur", "Kadaluarsa"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if ((colIndex == 9) || (colIndex == 10) || (colIndex == 11) || (colIndex == 12) || (colIndex == 13) || (colIndex == 16)) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Object.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Object.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };

        tbDetailObatRacikan.setModel(tabModeDetailObatRacikan);
        tbDetailObatRacikan.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbDetailObatRacikan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 19; i++) {
            TableColumn column = tbDetailObatRacikan.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(25);
            } else if (i == 1) {
                column.setPreferredWidth(75);
            } else if (i == 2) {
                column.setPreferredWidth(200);
            } else if (i == 3) {
                column.setPreferredWidth(45);
            } else if (i == 4) {
                column.setPreferredWidth(85);
            } else if (i == 5) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 6) {
                column.setPreferredWidth(85);
            } else if (i == 7) {
                column.setPreferredWidth(40);
            } else if (i == 8) {
                column.setPreferredWidth(40);
            } else if (i == 9) {
                column.setPreferredWidth(70);
            } else if (i == 10) {
                column.setPreferredWidth(40);
            } else if (i == 11) {
                column.setPreferredWidth(40);
            } else if (i == 12) {
                column.setPreferredWidth(50);
            } else if (i == 13) {
                column.setPreferredWidth(80);
            } else if (i == 14) {
                column.setPreferredWidth(80);
            } else if (i == 15) {
                column.setPreferredWidth(80);
            } else if (i == 16) {
                column.setPreferredWidth(75);
            } else if (i == 17) {
                column.setPreferredWidth(75);
            } else if (i == 18) {
                column.setPreferredWidth(65);
            }
        }

        warna3.kolom = 9;
        tbDetailObatRacikan.setDefaultRenderer(Object.class, warna3);

        TCari.setDocument(new batasInput((byte) 100).getKata(TCari));
        if (koneksiDB.cariCepat().equals("aktif")) {
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        BtnCariActionPerformed(null);
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        BtnCariActionPerformed(null);
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        BtnCariActionPerformed(null);
                    }
                }
            });
        }
        jam();

        caribangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (caribangsal.getTable().getSelectedRow() != -1) {
                    kdgudang.setText(caribangsal.getTable().getValueAt(caribangsal.getTable().getSelectedRow(), 0).toString());
                    nmgudang.setText(caribangsal.getTable().getValueAt(caribangsal.getTable().getSelectedRow(), 1).toString());
                }
                kdgudang.requestFocus();
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

        aturanpakai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (aturanpakai.getTable().getSelectedRow() != -1) {
                    if (TabRawat.getSelectedIndex() == 0) {
                        tbObat.setValueAt(aturanpakai.getTable().getValueAt(aturanpakai.getTable().getSelectedRow(), 0).toString(), tbObat.getSelectedRow(), 13);
                    } else if (TabRawat.getSelectedIndex() == 1) {
                        tbObatRacikan.setValueAt(aturanpakai.getTable().getValueAt(aturanpakai.getTable().getSelectedRow(), 0).toString(), tbObatRacikan.getSelectedRow(), 5);
                        tbObatRacikan.requestFocus();
                    }
                }
                tbObat.requestFocus();
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

        metoderacik.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (metoderacik.getTable().getSelectedRow() != -1) {
                    tbObatRacikan.setValueAt(metoderacik.getTable().getValueAt(metoderacik.getTable().getSelectedRow(), 1).toString(), tbObatRacikan.getSelectedRow(), 2);
                    tbObatRacikan.setValueAt(metoderacik.getTable().getValueAt(metoderacik.getTable().getSelectedRow(), 2).toString(), tbObatRacikan.getSelectedRow(), 3);
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

        metoderacik.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    metoderacik.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            aktifkanbatch = prop.getProperty("AKTIFKANBATCHOBAT");
            if (aktifkanbatch.equals("no")) {
                ppStok.setVisible(true);
            } else {
                ppStok.setVisible(false);
            }
            otorisasi = prop.getProperty("USERPCARE") + ":" + prop.getProperty("PASSPCARE") + ":095";
            URL = prop.getProperty("URLAPIPCARE");
        } catch (Exception e) {
            System.out.println("E : " + e);
            aktifkanbatch = "no";
            ppStok.setVisible(true);
        }

        try {
            psrekening = koneksi.prepareStatement("select * from set_akun_ranap");
            try {
                rsrekening = psrekening.executeQuery();
                while (rsrekening.next()) {
                    Suspen_Piutang_Obat_Ranap = rsrekening.getString("Suspen_Piutang_Obat_Ranap");
                    Obat_Ranap = rsrekening.getString("Obat_Ranap");
                    HPP_Obat_Rawat_Inap = rsrekening.getString("HPP_Obat_Rawat_Inap");
                    Persediaan_Obat_Rawat_Inap = rsrekening.getString("Persediaan_Obat_Rawat_Inap");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : " + e);
            } finally {
                if (rsrekening != null) {
                    rsrekening.close();
                }
                if (psrekening != null) {
                    psrekening.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
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
        ppBersihkan = new javax.swing.JMenuItem();
        ppStok = new javax.swing.JMenuItem();
        ppStok1 = new javax.swing.JMenuItem();
        TNoRw = new widget.TextBox();
        KdPj = new widget.TextBox();
        kelas = new widget.TextBox();
        NoResep = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        panelisi3 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        BtnTambah = new widget.Button();
        BtnSeek5 = new widget.Button();
        BtnSimpan = new widget.Button();
        BtnTambah1 = new widget.Button();
        BtnHapus = new widget.Button();
        label13 = new widget.Label();
        BtnKeluar = new widget.Button();
        FormInput = new widget.PanelBiasa();
        jLabel5 = new widget.Label();
        DTPTgl = new widget.Tanggal();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        ChkJln = new widget.CekBox();
        label12 = new widget.Label();
        Jeniskelas = new widget.ComboBox();
        ChkNoResep = new widget.CekBox();
        label21 = new widget.Label();
        kdgudang = new widget.TextBox();
        nmgudang = new widget.TextBox();
        BtnGudang = new widget.Button();
        jLabel19 = new widget.Label();
        jLabel20 = new widget.Label();
        jLabel14 = new widget.Label();
        jLabel15 = new widget.Label();
        jLabel16 = new widget.Label();
        jLabel17 = new widget.Label();
        jLabel21 = new widget.Label();
        jLabel22 = new widget.Label();
        jLabel23 = new widget.Label();
        jLabel24 = new widget.Label();
        jLabel25 = new widget.Label();
        TabRawat = new javax.swing.JTabbedPane();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        Scroll2 = new widget.ScrollPane();
        tbObatRacikan = new widget.Table();
        Scroll3 = new widget.ScrollPane();
        tbDetailObatRacikan = new widget.Table();

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(70, 70, 70));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkan.setText("Bersihkan Jumlah");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setIconTextGap(8);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        ppStok.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppStok.setForeground(new java.awt.Color(70, 70, 70));
        ppStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppStok.setText("Tampilkan Semua Stok");
        ppStok.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppStok.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppStok.setIconTextGap(8);
        ppStok.setName("ppStok"); // NOI18N
        ppStok.setPreferredSize(new java.awt.Dimension(200, 25));
        ppStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppStokActionPerformed(evt);
            }
        });
        Popup.add(ppStok);

        ppStok1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppStok1.setForeground(new java.awt.Color(70, 70, 70));
        ppStok1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppStok1.setText("Tampilkan Stok Lokasi Lain");
        ppStok1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppStok1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppStok1.setIconTextGap(8);
        ppStok1.setName("ppStok1"); // NOI18N
        ppStok1.setPreferredSize(new java.awt.Dimension(200, 25));
        ppStok1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppStok1ActionPerformed(evt);
            }
        });
        Popup.add(ppStok1);

        TNoRw.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N

        KdPj.setHighlighter(null);
        KdPj.setName("KdPj"); // NOI18N
        KdPj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPjKeyPressed(evt);
            }
        });

        kelas.setHighlighter(null);
        kelas.setName("kelas"); // NOI18N
        kelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kelasKeyPressed(evt);
            }
        });

        NoResep.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        NoResep.setHighlighter(null);
        NoResep.setName("NoResep"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Obat, Alkes & BHP Medis Resep Pulang ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi3.add(label9);

        TCari.setToolTipText("Alt+C");
        TCari.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(285, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
        BtnCari.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
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
        panelisi3.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("Alt+2");
        BtnAll.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelisi3.add(BtnAll);

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambah.setMnemonic('3');
        BtnTambah.setToolTipText("Alt+3");
        BtnTambah.setName("BtnTambah"); // NOI18N
        BtnTambah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        panelisi3.add(BtnTambah);

        BtnSeek5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/011.png"))); // NOI18N
        BtnSeek5.setMnemonic('5');
        BtnSeek5.setToolTipText("Alt+5");
        BtnSeek5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BtnSeek5.setName("BtnSeek5"); // NOI18N
        BtnSeek5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek5ActionPerformed(evt);
            }
        });
        BtnSeek5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek5KeyPressed(evt);
            }
        });
        panelisi3.add(BtnSeek5);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        panelisi3.add(BtnSimpan);

        BtnTambah1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        BtnTambah1.setMnemonic('3');
        BtnTambah1.setToolTipText("Alt+3");
        BtnTambah1.setName("BtnTambah1"); // NOI18N
        BtnTambah1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambah1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambah1ActionPerformed(evt);
            }
        });
        panelisi3.add(BtnTambah1);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        panelisi3.add(BtnHapus);

        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi3.add(label13);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('4');
        BtnKeluar.setToolTipText("Alt+4");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        panelisi3.add(BtnKeluar);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        FormInput.setBackground(new java.awt.Color(215, 225, 215));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 73));
        FormInput.setLayout(null);

        jLabel5.setText("Tanggal :");
        jLabel5.setName("jLabel5"); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel5);
        jLabel5.setBounds(4, 10, 68, 23);

        DTPTgl.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-01-2023" }));
        DTPTgl.setDisplayFormat("dd-MM-yyyy");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.setPreferredSize(new java.awt.Dimension(100, 23));
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        FormInput.add(DTPTgl);
        DTPTgl.setBounds(75, 10, 90, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.setPreferredSize(new java.awt.Dimension(50, 23));
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        FormInput.add(cmbJam);
        cmbJam.setBounds(169, 10, 62, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.setPreferredSize(new java.awt.Dimension(50, 23));
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        FormInput.add(cmbMnt);
        cmbMnt.setBounds(234, 10, 62, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.setPreferredSize(new java.awt.Dimension(50, 23));
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        FormInput.add(cmbDtk);
        cmbDtk.setBounds(299, 10, 62, 23);

        ChkJln.setBorder(null);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        ChkJln.setPreferredSize(new java.awt.Dimension(22, 23));
        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        FormInput.add(ChkJln);
        ChkJln.setBounds(364, 10, 22, 23);

        label12.setText("Tarif :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput.add(label12);
        label12.setBounds(382, 10, 50, 23);

        Jeniskelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kelas 1", "Kelas 2", "Kelas 3", "Utama/BPJS", "VIP", "VVIP", "Beli Luar", "Karyawan" }));
        Jeniskelas.setName("Jeniskelas"); // NOI18N
        Jeniskelas.setPreferredSize(new java.awt.Dimension(100, 23));
        Jeniskelas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JeniskelasItemStateChanged(evt);
            }
        });
        Jeniskelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JeniskelasKeyPressed(evt);
            }
        });
        FormInput.add(Jeniskelas);
        Jeniskelas.setBounds(435, 10, 110, 23);

        ChkNoResep.setSelected(true);
        ChkNoResep.setText("No.Resep   ");
        ChkNoResep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkNoResep.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkNoResep.setName("ChkNoResep"); // NOI18N
        ChkNoResep.setOpaque(false);
        ChkNoResep.setPreferredSize(new java.awt.Dimension(85, 23));
        ChkNoResep.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkNoResepItemStateChanged(evt);
            }
        });
        FormInput.add(ChkNoResep);
        ChkNoResep.setBounds(360, 40, 100, 23);

        label21.setText("Depo :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label21);
        label21.setBounds(4, 40, 68, 23);

        kdgudang.setEditable(false);
        kdgudang.setName("kdgudang"); // NOI18N
        kdgudang.setPreferredSize(new java.awt.Dimension(80, 23));
        kdgudang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdgudangKeyPressed(evt);
            }
        });
        FormInput.add(kdgudang);
        kdgudang.setBounds(75, 40, 55, 23);

        nmgudang.setEditable(false);
        nmgudang.setName("nmgudang"); // NOI18N
        nmgudang.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmgudang);
        nmgudang.setBounds(132, 40, 197, 23);

        BtnGudang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnGudang.setMnemonic('2');
        BtnGudang.setToolTipText("Alt+2");
        BtnGudang.setName("BtnGudang"); // NOI18N
        BtnGudang.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnGudang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGudangActionPerformed(evt);
            }
        });
        FormInput.add(BtnGudang);
        BtnGudang.setBounds(332, 40, 28, 23);

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Tombol pintasan :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel19);
        jLabel19.setBounds(550, 10, 90, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("1 =1x1");
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel20);
        jLabel20.setBounds(640, 10, 80, 23);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("2 =2x1");
        jLabel14.setName("jLabel14"); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel14);
        jLabel14.setBounds(640, 20, 80, 23);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("3 =3x1");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel15);
        jLabel15.setBounds(640, 30, 80, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("4 =4x1");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel16);
        jLabel16.setBounds(640, 40, 80, 23);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("q =Sebelum makan");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel17);
        jLabel17.setBounds(640, 50, 100, 23);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel21.setText("w =Sesudah makan");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel21);
        jLabel21.setBounds(750, 50, 100, 23);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("n =Jika nyeri 1 tab");
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel22);
        jLabel22.setBounds(690, 10, 100, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("L =Jika nyeri dada, dibawah lidah");
        jLabel23.setName("jLabel23"); // NOI18N
        jLabel23.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel23);
        jLabel23.setBounds(690, 20, 180, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("S =Jika sesak / bengkak");
        jLabel24.setName("jLabel24"); // NOI18N
        jLabel24.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel24);
        jLabel24.setBounds(690, 30, 180, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("E =Harus habis");
        jLabel25.setName("jLabel25"); // NOI18N
        jLabel25.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel25);
        jLabel25.setBounds(690, 40, 180, 23);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(70, 70, 70));
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setComponentPopupMenu(Popup);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(Popup);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbObatPropertyChange(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        TabRawat.addTab("Umum", Scroll);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 102));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);
        Scroll2.setPreferredSize(new java.awt.Dimension(454, 90));

        tbObatRacikan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObatRacikan.setName("tbObatRacikan"); // NOI18N
        tbObatRacikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatRacikanKeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbObatRacikan);

        jPanel3.add(Scroll2, java.awt.BorderLayout.PAGE_START);

        Scroll3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbDetailObatRacikan.setAutoCreateRowSorter(true);
        tbDetailObatRacikan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDetailObatRacikan.setName("tbDetailObatRacikan"); // NOI18N
        tbDetailObatRacikan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDetailObatRacikanMouseClicked(evt);
            }
        });
        tbDetailObatRacikan.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbDetailObatRacikanPropertyChange(evt);
            }
        });
        tbDetailObatRacikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDetailObatRacikanKeyPressed(evt);
            }
        });
        Scroll3.setViewportView(tbDetailObatRacikan);

        jPanel3.add(Scroll3, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Racikan", jPanel3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            tbObat.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        if (TabRawat.getSelectedIndex() == 0) {
            tampil();
        } else if (TabRawat.getSelectedIndex() == 1) {
            if (tbObatRacikan.getRowCount() != 0) {
                if (tbObatRacikan.getSelectedRow() != -1) {
                    if (tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 0).toString().equals("")
                            || tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 1).toString().equals("")
                            || tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 2).toString().equals("")
                            || tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 3).toString().equals("")
                            || tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 4).toString().equals("")
                            || tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 5).toString().equals("")
                            || tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 6).toString().equals("")) {
                        JOptionPane.showMessageDialog(null, "Silahkan lengkapi data racikan..!!");
                    } else {
                        tampildetailracikanobat();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Silahkan pilih racikan..!!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Silahkan masukkan racikan..!!");
            }
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
        BtnCariActionPerformed(evt);
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnAllActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnCari, TCari);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if (tbObat.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            if (evt.getClickCount() == 2) {
                if (var.getform().equals("DlgPemberianObat")) {
                    dispose();
                }
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if (kdgudang.getText().equals("")) {
            Valid.textKosong(TCari, "Lokasi");
        } else if (tbObat.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                try {
                    getData();
                    i = tbObat.getSelectedColumn();
                    if (i == 8) {
                        try {
                            if (tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString().equals("0") || tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString().equals("") || tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString().equals("0.0") || tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString().equals("0,0")) {
                                tbObat.setValueAt(embalase, tbObat.getSelectedRow(), 8);
                            }
                        } catch (Exception e) {
                            tbObat.setValueAt(0, tbObat.getSelectedRow(), 8);
                        }
                    } else if (i == 9) {
                        try {
                            if (tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString().equals("0") || tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString().equals("") || tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString().equals("0.0") || tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString().equals("0,0")) {
                                tbObat.setValueAt(tuslah, tbObat.getSelectedRow(), 9);
                            }
                        } catch (Exception e) {
                            tbObat.setValueAt(0, tbObat.getSelectedRow(), 9);
                        }

                        TCari.setText("");
                        TCari.requestFocus();
                    } else if ((i == 10) || (i == 3)) {
                        TCari.setText("");
                        TCari.requestFocus();
                    }
                } catch (java.lang.NullPointerException e) {
                }
            } else if ((evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
                if (tbObat.getSelectedRow() != -1) {
                    tbObat.setValueAt("", tbObat.getSelectedRow(), 1);
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
                TCari.requestFocus();
            } else if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
                i = tbObat.getSelectedColumn();
                if (i == 2) {
                    try {
                        getData();

                        try {
                            if (tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString().equals("0") || tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString().equals("") || tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString().equals("0.0") || tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString().equals("0,0")) {
                                tbObat.setValueAt(embalase, tbObat.getSelectedRow(), 8);
                            }
                        } catch (Exception e) {
                            tbObat.setValueAt(0, tbObat.getSelectedRow(), 8);
                        }

                        try {
                            if (tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString().equals("0") || tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString().equals("") || tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString().equals("0.0") || tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString().equals("0,0")) {
                                tbObat.setValueAt(tuslah, tbObat.getSelectedRow(), 9);
                            }
                        } catch (Exception e) {
                            tbObat.setValueAt(0, tbObat.getSelectedRow(), 9);
                        }

                    } catch (Exception e) {
                        tbObat.setValueAt(0, tbObat.getSelectedRow(), 10);
                    }
                } else if (i == 13) {
                    aturanpakai.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                    aturanpakai.setLocationRelativeTo(internalFrame1);
                    aturanpakai.setVisible(true);
                }
            }
            //tambahan koding ulun alridho
            if (evt.getKeyCode() == KeyEvent.VK_1) {
                tbObat.setValueAt("1xsehari 1 tablet", tbObat.getSelectedRow(), 13);
            } else if (evt.getKeyCode() == KeyEvent.VK_2) {
                tbObat.setValueAt("2xsehari 1 tablet", tbObat.getSelectedRow(), 13);
            } else if (evt.getKeyCode() == KeyEvent.VK_3) {
                tbObat.setValueAt("3xsehari 1 tablet", tbObat.getSelectedRow(), 13);
            } else if (evt.getKeyCode() == KeyEvent.VK_4) {
                tbObat.setValueAt("4xsehari 1 tablet", tbObat.getSelectedRow(), 13);
            } else if (evt.getKeyCode() == KeyEvent.VK_5) {
                tbObat.setValueAt("1 x sehari  5  ml", tbObat.getSelectedRow(), 13);
            } else if (evt.getKeyCode() == KeyEvent.VK_6) {
                tbObat.setValueAt("2 x sehari  5  ml", tbObat.getSelectedRow(), 13);
            } else if (evt.getKeyCode() == KeyEvent.VK_7) {
                tbObat.setValueAt("3 x sehari  5  ml", tbObat.getSelectedRow(), 13);
            } else if (evt.getKeyCode() == KeyEvent.VK_T) {
                tbObat.setValueAt("4 x sehari  5  ml", tbObat.getSelectedRow(), 13);
            } else if (evt.getKeyCode() == KeyEvent.VK_Y) {
                tbObat.setValueAt("Minggu  I 4x1 Sehari 1 Tetes", tbObat.getSelectedRow(), 13); //28
            } else if (evt.getKeyCode() == KeyEvent.VK_U) {
                tbObat.setValueAt("Minggu II 3x1 Sehari 1 Tetes", tbObat.getSelectedRow(), 13); //28
            }
            if (evt.getKeyCode() == KeyEvent.VK_Q) {
                String a;
                a = tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString().substring(0, 17);
                tbObat.setValueAt(a + " Sebelum Makan", tbObat.getSelectedRow(), 13);
            } else if (evt.getKeyCode() == KeyEvent.VK_W) {
                String a;
                a = tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString().substring(0, 17);
                tbObat.setValueAt(a + " Sesudah Makan", tbObat.getSelectedRow(), 13);
            } else if (evt.getKeyCode() == KeyEvent.VK_N) {
                tbObat.setValueAt("Jika Nyeri 1 Tablet", tbObat.getSelectedRow(), 13);
            } else if (evt.getKeyCode() == KeyEvent.VK_L) {
                tbObat.setValueAt("1 tablet Jika nyeri dada, dibawah lidah ", tbObat.getSelectedRow(), 13);
            } else if (evt.getKeyCode() == KeyEvent.VK_S) {
                tbObat.setValueAt("1 tablet Jika sesak / kaki bengkak", tbObat.getSelectedRow(), 13);
            } else if (evt.getKeyCode() == KeyEvent.VK_E) {
                String a;
                a = tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString().substring(0, 17);
                tbObat.setValueAt(a + " Harus habis", tbObat.getSelectedRow(), 13);
            } else if (evt.getKeyCode() == KeyEvent.VK_R) {
                String a;
                a = tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString().substring(0, 17);
                tbObat.setValueAt(a + " Bila gatal", tbObat.getSelectedRow(), 13);
            }
            if (evt.getKeyCode() == KeyEvent.VK_Z) {
                String a;
                a = tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString().substring(0, 31);
                tbObat.setValueAt(a + "  Pagi", tbObat.getSelectedRow(), 13);
            } else if (evt.getKeyCode() == KeyEvent.VK_X) {
                String a;
                a = tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString().substring(0, 31);
                tbObat.setValueAt(a + " Siang", tbObat.getSelectedRow(), 13);
            } else if (evt.getKeyCode() == KeyEvent.VK_C) {
                String a;
                a = tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString().substring(0, 31);
                tbObat.setValueAt(a + "  Sore", tbObat.getSelectedRow(), 13);
            } else if (evt.getKeyCode() == KeyEvent.VK_V) {
                String a;
                a = tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString().substring(0, 31);
                tbObat.setValueAt(a + " Malam", tbObat.getSelectedRow(), 13);
            } else if (evt.getKeyCode() == KeyEvent.VK_A) {
                String a;
                a = tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString().substring(0, 31);
                tbObat.setValueAt(a + " Dikunyah", tbObat.getSelectedRow(), 13);
            } else if (evt.getKeyCode() == KeyEvent.VK_O) {
                tbObat.setValueAt("2xsehari 4 tetes telinga", tbObat.getSelectedRow(), 13);
            } else if (evt.getKeyCode() == KeyEvent.VK_P) {
                tbObat.setValueAt("2xsehari 1/2 tablet", tbObat.getSelectedRow(), 13);
            } else if (evt.getKeyCode() == KeyEvent.VK_I) {
                tbObat.setValueAt("1xsehari 1/2 tablet", tbObat.getSelectedRow(), 13);
            } else if (evt.getKeyCode() == KeyEvent.VK_8) {
                tbObat.setValueAt("2xsehari 1 tetes", tbObat.getSelectedRow(), 13);
            } else if (evt.getKeyCode() == KeyEvent.VK_9) {
                tbObat.setValueAt("4xsehari 1 tetes", tbObat.getSelectedRow(), 13);
            } else if (evt.getKeyCode() == KeyEvent.VK_0) {
                tbObat.setValueAt("6xsehari 1 tetes", tbObat.getSelectedRow(), 13);
            } else if (evt.getKeyCode() == KeyEvent.VK_G) {
                String a;
                a = tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString();
                //if (a.length()==16){
                tbObat.setValueAt(a + " Mata kanan & kiri", tbObat.getSelectedRow(), 13);
                //} 
                //System.out.println(a.length()); 

            } else if (evt.getKeyCode() == KeyEvent.VK_H) {
                String a;
                a = tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString().substring(0, 16);
                tbObat.setValueAt(a + " Mata kanan", tbObat.getSelectedRow(), 13);
            } else if (evt.getKeyCode() == KeyEvent.VK_J) {
                String a;
                a = tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString().substring(0, 16);
                tbObat.setValueAt(a + " Mata kiri", tbObat.getSelectedRow(), 13);
            }
            //end tambahan koding ulun alridho
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        //barang.setModal(true);
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        barang.setLocationRelativeTo(internalFrame1);
        barang.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahActionPerformed

private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
    if (TNoRw.getText().trim().equals("")) {
        Valid.textKosong(TCari, "Data");
    } else if (kdgudang.getText().equals("")) {
        Valid.textKosong(TCari, "Lokasi");
    } else {
        int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, udah bener belum data yang mau disimpan..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            try {
                ChkJln.setSelected(false);
                koneksi.setAutoCommit(false);
                ttlhpp = 0;
                ttljual = 0;
                for (i = 0; i < tbObat.getRowCount(); i++) {
                    if (Valid.SetAngka(tbObat.getValueAt(i, 1).toString()) > 0) {
                        if (tbObat.getValueAt(i, 0).toString().equals("true")) {
                            pscarikapasitas = koneksi.prepareStatement("select IFNULL(kapasitas,1) from databarang where kode_brng=?");
                            try {
                                pscarikapasitas.setString(1, tbObat.getValueAt(i, 2).toString());
                                carikapasitas = pscarikapasitas.executeQuery();
                                if (carikapasitas.next()) {
                                    if (Sequel.menyimpantf2("detail_pemberian_obat", "?,?,?,?,?,?,?,?,?,?,?,?,?", "data", 13, new String[]{
                                        Valid.SetTgl(DTPTgl.getSelectedItem() + ""), cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(), TNoRw.getText(), tbObat.getValueAt(i, 2).toString(), tbObat.getValueAt(i, 12).toString(),
                                        tbObat.getValueAt(i, 6).toString(), "" + (Double.parseDouble(tbObat.getValueAt(i, 1).toString()) / carikapasitas.getDouble(1)),
                                        tbObat.getValueAt(i, 8).toString(), tbObat.getValueAt(i, 9).toString(), "" + Valid.SetAngka2(Double.parseDouble(tbObat.getValueAt(i, 8).toString())
                                        + Double.parseDouble(tbObat.getValueAt(i, 9).toString()) + (Double.parseDouble(tbObat.getValueAt(i, 6).toString())
                                        * (Double.parseDouble(tbObat.getValueAt(i, 1).toString()) / carikapasitas.getDouble(1)))),
                                        "Pulang", kdgudang.getText(), tbObat.getValueAt(i, 16).toString()
                                    }) == true) {
                                        if (Sequel.mengedittf("resep_pulang", "no_rawat=? and kode_brng=?", "jml_barang=?,tuslah=?,total=?,dosis=?", 6, new String[]{
                                            tbObat.getValueAt(i, 1).toString(), tbObat.getValueAt(i, 9).toString(), "" + (Double.parseDouble(tbObat.getValueAt(i, 8).toString())
                                            + Double.parseDouble(tbObat.getValueAt(i, 9).toString()) + (Double.parseDouble(tbObat.getValueAt(i, 6).toString())
                                            * Double.parseDouble(tbObat.getValueAt(i, 1).toString()))),tbObat.getValueAt(i, 13).toString(), TNoRw.getText(), tbObat.getValueAt(i, 2).toString()
                                        }) == true) {
                                        }
                                        ttljual = ttljual + Double.parseDouble(tbObat.getValueAt(i, 8).toString())
                                                + Double.parseDouble(tbObat.getValueAt(i, 9).toString()) + (Double.parseDouble(tbObat.getValueAt(i, 6).toString())
                                                * (Double.parseDouble(tbObat.getValueAt(i, 1).toString()) / carikapasitas.getDouble(1)));
                                        ttlhpp = ttlhpp + (Double.parseDouble(tbObat.getValueAt(i, 12).toString())
                                                * (Double.parseDouble(tbObat.getValueAt(i, 1).toString()) / carikapasitas.getDouble(1)));
                                        if (!tbObat.getValueAt(i, 13).toString().equals("")) {
                                            Sequel.menyimpan("aturan_pakai", "?,?,?,?,?", 5, new String[]{
                                                Valid.SetTgl(DTPTgl.getSelectedItem() + ""), cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(), TNoRw.getText(), tbObat.getValueAt(i, 2).toString(), tbObat.getValueAt(i, 13).toString()
                                            });
                                        }
                                        Trackobat.catatRiwayat(tbObat.getValueAt(i, 2).toString(), 0, (Double.parseDouble(tbObat.getValueAt(i, 1).toString()) / carikapasitas.getDouble(1)), "Resep Pulang", var.getkode(), kdgudang.getText(), "Simpan");
                                        Sequel.menyimpan("gudangbarang", "'" + tbObat.getValueAt(i, 2).toString() + "','" + kdgudang.getText() + "','-" + (Double.parseDouble(tbObat.getValueAt(i, 1).toString()) / carikapasitas.getDouble(1)) + "'",
                                                "stok=stok-'" + (Double.parseDouble(tbObat.getValueAt(i, 1).toString()) / carikapasitas.getDouble(1)) + "'", "kode_brng='" + tbObat.getValueAt(i, 2).toString() + "' and kd_bangsal='" + kdgudang.getText() + "'");
                                        if (aktifkanbatch.equals("yes")) {
                                            Sequel.mengedit("data_batch", "no_batch=? and kode_brng=?", "sisa=sisa-?", 3, new String[]{
                                                "" + (Double.parseDouble(tbObat.getValueAt(i, 1).toString()) / carikapasitas.getDouble(1)), tbObat.getValueAt(i, 16).toString(), tbObat.getValueAt(i, 2).toString()
                                            });
                                        }
                                        if (aktifpcare.equals("yes")) {
                                            arrSplit = tbObat.getValueAt(i, 13).toString().toLowerCase().split("x");
                                            signa1 = "1";
                                            try {
                                                if (!arrSplit[0].replaceAll("[^0-9.]+", "").equals("")) {
                                                    signa1 = arrSplit[0].replaceAll("[^0-9.]+", "");
                                                }
                                            } catch (Exception e) {
                                                signa1 = "1";
                                            }
                                            signa2 = "1";
                                            try {
                                                if (!arrSplit[1].replaceAll("[^0-9.]+", "").equals("")) {
                                                    signa2 = arrSplit[1].replaceAll("[^0-9.]+", "");
                                                }
                                            } catch (Exception e) {
                                                signa2 = "1";
                                            }
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Gagal Menyimpan, Kemungkinan ada data sama/kapasitas tidak ditemukan..!!");
                                    }
                                } else {
                                    if (Sequel.menyimpantf("detail_pemberian_obat", "?,?,?,?,?,?,?,?,?,?,?,?,?", "data", 13, new String[]{
                                        Valid.SetTgl(DTPTgl.getSelectedItem() + ""), cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(), TNoRw.getText(), tbObat.getValueAt(i, 2).toString(), tbObat.getValueAt(i, 12).toString(),
                                        tbObat.getValueAt(i, 6).toString(), "" + Double.parseDouble(tbObat.getValueAt(i, 1).toString()),
                                        tbObat.getValueAt(i, 8).toString(), tbObat.getValueAt(i, 9).toString(), "" + (Double.parseDouble(tbObat.getValueAt(i, 8).toString())
                                        + Double.parseDouble(tbObat.getValueAt(i, 9).toString()) + (Double.parseDouble(tbObat.getValueAt(i, 6).toString())
                                        * Double.parseDouble(tbObat.getValueAt(i, 1).toString()))),
                                        "Pulang", kdgudang.getText(), tbObat.getValueAt(i, 16).toString()
                                    }) == true) {
                                        if (Sequel.mengedittf("resep_pulang", "no_rawat=? and kode_brng=?", "jml_barang=?,tuslah=?,total=?,dosis=?", 6, new String[]{
                                            tbObat.getValueAt(i, 1).toString(), tbObat.getValueAt(i, 9).toString(), "" + (Double.parseDouble(tbObat.getValueAt(i, 8).toString())
                                            + Double.parseDouble(tbObat.getValueAt(i, 9).toString()) + (Double.parseDouble(tbObat.getValueAt(i, 6).toString())
                                            * Double.parseDouble(tbObat.getValueAt(i, 1).toString()))),tbObat.getValueAt(i, 13).toString() , TNoRw.getText(), tbObat.getValueAt(i, 2).toString()
                                        }) == true) {
                                        }
                                    }
                                    {
                                        ttljual = ttljual + Double.parseDouble(tbObat.getValueAt(i, 8).toString())
                                                + Double.parseDouble(tbObat.getValueAt(i, 9).toString()) + (Double.parseDouble(tbObat.getValueAt(i, 6).toString())
                                                * Double.parseDouble(tbObat.getValueAt(i, 1).toString()));
                                        ttlhpp = ttlhpp + (Double.parseDouble(tbObat.getValueAt(i, 12).toString())
                                                * Double.parseDouble(tbObat.getValueAt(i, 1).toString()));
                                        if (!tbObat.getValueAt(i, 13).toString().equals("")) {
                                            Sequel.menyimpan("aturan_pakai", "?,?,?,?,?", 5, new String[]{
                                                Valid.SetTgl(DTPTgl.getSelectedItem() + ""), cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(), TNoRw.getText(), tbObat.getValueAt(i, 2).toString(), tbObat.getValueAt(i, 13).toString()
                                            });
                                        }
                                        Trackobat.catatRiwayat(tbObat.getValueAt(i, 2).toString(), 0, Double.parseDouble(tbObat.getValueAt(i, 1).toString()), "Resep Pulang", var.getkode(), kdgudang.getText(), "Simpan");
                                        Sequel.menyimpan("gudangbarang", "'" + tbObat.getValueAt(i, 2).toString() + "','" + kdgudang.getText() + "','-" + Double.parseDouble(tbObat.getValueAt(i, 1).toString()) + "'",
                                                "stok=stok-'" + Double.parseDouble(tbObat.getValueAt(i, 1).toString()) + "'", "kode_brng='" + tbObat.getValueAt(i, 2).toString() + "' and kd_bangsal='" + kdgudang.getText() + "'");
                                        if (aktifkanbatch.equals("yes")) {
                                            Sequel.mengedit("data_batch", "no_batch=? and kode_brng=?", "sisa=sisa-?", 3, new String[]{
                                                "" + (Double.parseDouble(tbObat.getValueAt(i, 1).toString())), tbObat.getValueAt(i, 16).toString(), tbObat.getValueAt(i, 2).toString()
                                            });
                                        }
                                        if (aktifpcare.equals("yes")) {
                                            arrSplit = tbObat.getValueAt(i, 13).toString().toLowerCase().split("x");
                                            signa1 = "1";
                                            try {
                                                if (!arrSplit[0].replaceAll("[^0-9.]+", "").equals("")) {
                                                    signa1 = arrSplit[0].replaceAll("[^0-9.]+", "");
                                                }
                                            } catch (Exception e) {
                                                signa1 = "1";
                                            }
                                            signa2 = "1";
                                            try {
                                                if (!arrSplit[1].replaceAll("[^0-9.]+", "").equals("")) {
                                                    signa2 = arrSplit[1].replaceAll("[^0-9.]+", "");
                                                }
                                            } catch (Exception e) {
                                                signa2 = "1";
                                            }
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi Kapasitas : " + e);
                            } finally {
                                if (carikapasitas != null) {
                                    carikapasitas.close();
                                }
                                if (pscarikapasitas != null) {
                                    pscarikapasitas.close();
                                }
                            }
                        } else {
                            if (Sequel.menyimpantf("detail_pemberian_obat", "?,?,?,?,?,?,?,?,?,?,?,?,?", "data", 13, new String[]{
                                Valid.SetTgl(DTPTgl.getSelectedItem() + ""), cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(), TNoRw.getText(), tbObat.getValueAt(i, 2).toString(), tbObat.getValueAt(i, 12).toString(),
                                tbObat.getValueAt(i, 6).toString(), "" + Double.parseDouble(tbObat.getValueAt(i, 1).toString()),
                                tbObat.getValueAt(i, 8).toString(), tbObat.getValueAt(i, 9).toString(), "" + (Double.parseDouble(tbObat.getValueAt(i, 8).toString())
                                + Double.parseDouble(tbObat.getValueAt(i, 9).toString()) + (Double.parseDouble(tbObat.getValueAt(i, 6).toString())
                                * Double.parseDouble(tbObat.getValueAt(i, 1).toString()))),
                                "Pulang", kdgudang.getText(), tbObat.getValueAt(i, 16).toString()
                            }) == true) {
                                if (Sequel.mengedittf("resep_pulang", "no_rawat=? and kode_brng=?", "jml_barang=?,tuslah=?,total=?,dosis=?", 6, new String[]{
                                    tbObat.getValueAt(i, 1).toString(), tbObat.getValueAt(i, 9).toString(), "" + (Double.parseDouble(tbObat.getValueAt(i, 8).toString())
                                    + Double.parseDouble(tbObat.getValueAt(i, 9).toString()) + (Double.parseDouble(tbObat.getValueAt(i, 6).toString())
                                    * Double.parseDouble(tbObat.getValueAt(i, 1).toString()))),tbObat.getValueAt(i, 13).toString(), TNoRw.getText(), tbObat.getValueAt(i, 2).toString()
                                }) == true) {
                                }
                            }
                            {

                                ttljual = ttljual + Double.parseDouble(tbObat.getValueAt(i, 8).toString())
                                        + Double.parseDouble(tbObat.getValueAt(i, 9).toString()) + (Double.parseDouble(tbObat.getValueAt(i, 6).toString())
                                        * Double.parseDouble(tbObat.getValueAt(i, 1).toString()));
                                ttlhpp = ttlhpp + (Double.parseDouble(tbObat.getValueAt(i, 12).toString())
                                        * Double.parseDouble(tbObat.getValueAt(i, 1).toString()));

                                if (!tbObat.getValueAt(i, 13).toString().equals("")) {
                                    Sequel.menyimpan("aturan_pakai", "?,?,?,?,?", 5, new String[]{
                                        Valid.SetTgl(DTPTgl.getSelectedItem() + ""), cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(), TNoRw.getText(), tbObat.getValueAt(i, 2).toString(), tbObat.getValueAt(i, 13).toString()
                                    });
                                }
                                Trackobat.catatRiwayat(tbObat.getValueAt(i, 2).toString(), 0, Double.parseDouble(tbObat.getValueAt(i, 1).toString()), "Resep Pulang", var.getkode(), kdgudang.getText(), "Simpan");
                                Sequel.menyimpan("gudangbarang", "'" + tbObat.getValueAt(i, 2).toString() + "','" + kdgudang.getText() + "','-" + Double.parseDouble(tbObat.getValueAt(i, 1).toString()) + "'",
                                        "stok=stok-'" + Double.parseDouble(tbObat.getValueAt(i, 1).toString()) + "'", "kode_brng='" + tbObat.getValueAt(i, 2).toString() + "' and kd_bangsal='" + kdgudang.getText() + "'");
                                if (aktifkanbatch.equals("yes")) {
                                    Sequel.mengedit("data_batch", "no_batch=? and kode_brng=?", "sisa=sisa-?", 3, new String[]{
                                        "" + Double.parseDouble(tbObat.getValueAt(i, 1).toString()), tbObat.getValueAt(i, 16).toString(), tbObat.getValueAt(i, 2).toString()
                                    });
                                }
                                if (aktifpcare.equals("yes")) {
                                    arrSplit = tbObat.getValueAt(i, 13).toString().toLowerCase().split("x");
                                    signa1 = "1";
                                    try {
                                        if (!arrSplit[0].replaceAll("[^0-9.]+", "").equals("")) {
                                            signa1 = arrSplit[0].replaceAll("[^0-9.]+", "");
                                        }
                                    } catch (Exception e) {
                                        signa1 = "1";
                                    }
                                    signa2 = "1";
                                    try {
                                        if (!arrSplit[1].replaceAll("[^0-9.]+", "").equals("")) {
                                            signa2 = arrSplit[1].replaceAll("[^0-9.]+", "");
                                        }
                                    } catch (Exception e) {
                                        signa2 = "1";
                                    }
                                }
                            }
                        }
                    }
                    tbObat.setValueAt("", i, 1);
                }

                for (i = 0; i < tbObatRacikan.getRowCount(); i++) {
                    if (Valid.SetAngka(tbObatRacikan.getValueAt(i, 4).toString()) > 0) {
                        Sequel.menyimpan("obat_racikan", "?,?,?,?,?,?,?,?,?", 9, new String[]{
                            Valid.SetTgl(DTPTgl.getSelectedItem() + ""), cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(), TNoRw.getText(),
                            tbObatRacikan.getValueAt(i, 0).toString(), tbObatRacikan.getValueAt(i, 1).toString(),
                            tbObatRacikan.getValueAt(i, 2).toString(), tbObatRacikan.getValueAt(i, 4).toString(),
                            tbObatRacikan.getValueAt(i, 5).toString(), tbObatRacikan.getValueAt(i, 6).toString()
                        });
                    }
                }

                Valid.tabelKosong(tabModeObatRacikan);

                for (i = 0; i < tbDetailObatRacikan.getRowCount(); i++) {
                    if (Valid.SetAngka(tbDetailObatRacikan.getValueAt(i, 10).toString()) > 0) {
                        if (Sequel.menyimpantf2("detail_obat_racikan", "?,?,?,?,?", "Data", 5, new String[]{
                            Valid.SetTgl(DTPTgl.getSelectedItem() + ""), cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(), TNoRw.getText(),
                            tbDetailObatRacikan.getValueAt(i, 0).toString(), tbDetailObatRacikan.getValueAt(i, 1).toString()
                        }) == true) {
                            if (Sequel.menyimpantf("detail_pemberian_obat", "?,?,?,?,?,?,?,?,?,?,?,?,?", "data", 13, new String[]{
                                Valid.SetTgl(DTPTgl.getSelectedItem() + ""), cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem(), TNoRw.getText(),
                                tbDetailObatRacikan.getValueAt(i, 1).toString(), tbDetailObatRacikan.getValueAt(i, 5).toString(),
                                tbDetailObatRacikan.getValueAt(i, 4).toString(), "" + Double.parseDouble(tbDetailObatRacikan.getValueAt(i, 10).toString()),
                                tbDetailObatRacikan.getValueAt(i, 11).toString(), tbDetailObatRacikan.getValueAt(i, 12).toString(),
                                "" + (Double.parseDouble(tbDetailObatRacikan.getValueAt(i, 11).toString())
                                + Double.parseDouble(tbDetailObatRacikan.getValueAt(i, 12).toString())
                                + (Double.parseDouble(tbDetailObatRacikan.getValueAt(i, 4).toString())
                                * Double.parseDouble(tbDetailObatRacikan.getValueAt(i, 10).toString()))),
                                "Pulang", kdgudang.getText(), tbDetailObatRacikan.getValueAt(i, 16).toString()
                            }) == true) {
                                ttljual = ttljual + Double.parseDouble(tbDetailObatRacikan.getValueAt(i, 11).toString())
                                        + Double.parseDouble(tbDetailObatRacikan.getValueAt(i, 12).toString()) + (Double.parseDouble(tbDetailObatRacikan.getValueAt(i, 4).toString())
                                        * Double.parseDouble(tbDetailObatRacikan.getValueAt(i, 10).toString()));
                                ttlhpp = ttlhpp + (Double.parseDouble(tbDetailObatRacikan.getValueAt(i, 5).toString())
                                        * Double.parseDouble(tbDetailObatRacikan.getValueAt(i, 10).toString()));

                                Trackobat.catatRiwayat(tbDetailObatRacikan.getValueAt(i, 1).toString(), 0, Double.parseDouble(tbDetailObatRacikan.getValueAt(i, 10).toString()), "Resep Pulang", var.getkode(), kdgudang.getText(), "Simpan");
                                Sequel.menyimpan("gudangbarang", "'" + tbDetailObatRacikan.getValueAt(i, 1).toString() + "','" + kdgudang.getText() + "','-" + Double.parseDouble(tbDetailObatRacikan.getValueAt(i, 10).toString()) + "'",
                                        "stok=stok-'" + Double.parseDouble(tbDetailObatRacikan.getValueAt(i, 10).toString()) + "'", "kode_brng='" + tbDetailObatRacikan.getValueAt(i, 1).toString() + "' and kd_bangsal='" + kdgudang.getText() + "'");
                                if (aktifkanbatch.equals("yes")) {
                                    Sequel.mengedit("data_batch", "no_batch=? and kode_brng=?", "sisa=sisa-?", 3, new String[]{
                                        "" + Double.parseDouble(tbDetailObatRacikan.getValueAt(i, 10).toString()), tbDetailObatRacikan.getValueAt(i, 16).toString(), tbDetailObatRacikan.getValueAt(i, 1).toString()
                                    });
                                }
                                if (aktifpcare.equals("yes")) {
                                    arrSplit = Sequel.cariIsi("select aturan_pakai from obat_racikan where tgl_perawatan='" + Valid.SetTgl(DTPTgl.getSelectedItem() + "") + "' and "
                                            + "jam='" + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "' and "
                                            + "no_rawat='" + TNoRw.getText() + "' and no_racik='" + tbDetailObatRacikan.getValueAt(i, 0).toString() + "'").toLowerCase().split("x");
                                    signa1 = "1";
                                    try {
                                        if (!arrSplit[0].replaceAll("[^0-9.]+", "").equals("")) {
                                            signa1 = arrSplit[0].replaceAll("[^0-9.]+", "");
                                        }
                                    } catch (Exception e) {
                                        signa1 = "1";
                                    }
                                    signa2 = "1";
                                    try {
                                        if (!arrSplit[1].replaceAll("[^0-9.]+", "").equals("")) {
                                            signa2 = arrSplit[1].replaceAll("[^0-9.]+", "");
                                        }
                                    } catch (Exception e) {
                                        signa2 = "2";
                                    }
                                }
                            }
                        }
                    }
                }

                Valid.tabelKosong(tabModeDetailObatRacikan);
                if (!noresep.equals("")) {
                    Sequel.mengedit("resep_dokter_pulang", "no_resep='" + noresep + "'", "tgl_perawatan='" + Valid.SetTgl(DTPTgl.getSelectedItem() + "") + "',jam_perawatan='" + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "'");
                }
                Sequel.queryu("delete from tampjurnal");
                if (ttljual > 0) {
                    Sequel.menyimpan("tampjurnal", "'" + Suspen_Piutang_Obat_Ranap + "','Suspen Piutang Obat Ranap','" + ttljual + "','0'", "Rekening");
                    Sequel.menyimpan("tampjurnal", "'" + Obat_Ranap + "','Pendapatan Obat Rawat Inap','0','" + ttljual + "'", "Rekening");
                }
                if (ttlhpp > 0) {
                    Sequel.menyimpan("tampjurnal", "'" + HPP_Obat_Rawat_Inap + "','HPP Persediaan Obat Rawat Inap','" + ttlhpp + "','0'", "Rekening");
                    Sequel.menyimpan("tampjurnal", "'" + Persediaan_Obat_Rawat_Inap + "','Persediaan Obat Rawat Inap','0','" + ttlhpp + "'", "Rekening");
                }
                jur.simpanJurnal(TNoRw.getText(), Valid.SetTgl(DTPTgl.getSelectedItem() + ""), "U", "RESEP PULANG RAWAT INAP PASIEN, DIPOSTING OLEH " + var.getkode());

                koneksi.setAutoCommit(true);
                if (ChkNoResep.isSelected() == true) {
                    DlgResepObat resep = new DlgResepObat(null, false);
                    resep.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                    resep.setLocationRelativeTo(internalFrame1);
                    resep.emptTeks();
                    resep.isCek();
                    resep.setNoRm(TNoRw.getText(), DTPTgl.getDate(), DTPTgl.getDate(), cmbJam.getSelectedItem().toString(), cmbMnt.getSelectedItem().toString(), cmbDtk.getSelectedItem().toString(), "pulang");
                    resep.tampil();
                    //resep.setAlwaysOnTop(true);
                    resep.dokter.setAlwaysOnTop(true);
                    resep.setVisible(true);
                }
                ChkJln.setSelected(true);
                dispose();
            } catch (Exception ex) {
                System.out.println(ex);
                JOptionPane.showMessageDialog(null, "Maaf, gagal menyimpan data. Kemungkinan ada data yang sama dimasukkan sebelumnya?\nKapasitas belum dimasukkan...!");
            }
        }
    }
}//GEN-LAST:event_BtnSimpanActionPerformed

private void BtnSeek5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek5ActionPerformed
    DlgCariKonversi carikonversi = new DlgCariKonversi(null, false);
    carikonversi.setLocationRelativeTo(internalFrame1);
    carikonversi.setVisible(true);
}//GEN-LAST:event_BtnSeek5ActionPerformed

private void BtnSeek5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek5KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_BtnSeek5KeyPressed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
    for (i = 0; i < tbObat.getRowCount(); i++) {
        tbObat.setValueAt("", i, 1);
        tbObat.setValueAt(0, i, 10);
        tbObat.setValueAt(0, i, 9);
        tbObat.setValueAt(0, i, 8);
    }
}//GEN-LAST:event_ppBersihkanActionPerformed

private void JeniskelasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JeniskelasItemStateChanged
    if (this.isVisible() == true) {
        tampil();
    }
}//GEN-LAST:event_JeniskelasItemStateChanged

private void JeniskelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JeniskelasKeyPressed
    Valid.pindah(evt, cmbDtk, TCari);
}//GEN-LAST:event_JeniskelasKeyPressed

private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
    Valid.pindah(evt, BtnKeluar, cmbJam);
}//GEN-LAST:event_DTPTglKeyPressed

private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
    Valid.pindah(evt, DTPTgl, cmbMnt);
}//GEN-LAST:event_cmbJamKeyPressed

private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
    Valid.pindah(evt, cmbJam, cmbDtk);
}//GEN-LAST:event_cmbMntKeyPressed

private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
    Valid.pindah(evt, cmbMnt, Jeniskelas);
}//GEN-LAST:event_cmbDtkKeyPressed

private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_ChkJlnActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        if (TabRawat.getSelectedIndex() == 0) {
            BtnTambah1.setVisible(false);
            BtnHapus.setVisible(false);
            label13.setPreferredSize(new Dimension(65, 23));
        } else if (TabRawat.getSelectedIndex() == 1) {
            BtnTambah1.setVisible(true);
            BtnHapus.setVisible(true);
            label13.setPreferredSize(new Dimension(1, 23));
        }
    }//GEN-LAST:event_formWindowActivated

    private void ChkNoResepItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkNoResepItemStateChanged
        if (ChkNoResep.isSelected() == true) {
            DlgResepObat resep = new DlgResepObat(null, false);
            resep.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            resep.setLocationRelativeTo(internalFrame1);
            resep.emptTeks();
            resep.isCek();
            resep.setNoRm(TNoRw.getText(), DTPTgl.getDate(), DTPTgl.getDate(), cmbJam.getSelectedItem().toString(), cmbMnt.getSelectedItem().toString(), cmbDtk.getSelectedItem().toString(), "pulang");
            resep.tampil();
            resep.setVisible(true);
        }
    }//GEN-LAST:event_ChkNoResepItemStateChanged

    private void KdPjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPjKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPjKeyPressed

    private void kelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kelasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kelasKeyPressed

    private void ppStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppStokActionPerformed
        if (kdgudang.getText().equals("")) {
            Valid.textKosong(TCari, "Lokasi");
        } else {
            for (i = 0; i < tbObat.getRowCount(); i++) {
                try {
                    stokbarang = 0;
                    psstok = koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=?");
                    try {
                        psstok.setString(1, kdgudang.getText());
                        psstok.setString(2, tbObat.getValueAt(i, 2).toString());
                        rsstok = psstok.executeQuery();
                        if (rsstok.next()) {
                            stokbarang = rsstok.getDouble(1);
                        }
                    } catch (Exception e) {
                        stokbarang = 0;
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (rsstok != null) {
                            rsstok.close();
                        }
                        if (psstok != null) {
                            psstok.close();
                        }
                    }
                    tbObat.setValueAt(stokbarang, i, 10);
                } catch (Exception e) {
                    tbObat.setValueAt(0, i, 10);
                }
            }
        }
    }//GEN-LAST:event_ppStokActionPerformed

    private void kdgudangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdgudangKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_PAGE_DOWN:
                Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?", nmgudang, kdgudang.getText());
                break;
            case KeyEvent.VK_PAGE_UP:
                Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?", nmgudang, kdgudang.getText());
                TCari.requestFocus();
                break;
            case KeyEvent.VK_ENTER:
                Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?", nmgudang, kdgudang.getText());
                BtnSimpan.requestFocus();
                break;
            case KeyEvent.VK_UP:
                BtnGudangActionPerformed(null);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_kdgudangKeyPressed

    private void BtnGudangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGudangActionPerformed
        caribangsal.isCek();
        caribangsal.emptTeks();
        caribangsal.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        caribangsal.setLocationRelativeTo(internalFrame1);
        caribangsal.setAlwaysOnTop(false);
        caribangsal.setVisible(true);
    }//GEN-LAST:event_BtnGudangActionPerformed

    private void tbObatRacikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatRacikanKeyPressed
        if (tbObatRacikan.getRowCount() != 0) {
            i = tbObatRacikan.getSelectedColumn();
            if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (i == 5) {
                    var.setform("DlgCariObat");
                    aturanpakai.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                    aturanpakai.setLocationRelativeTo(internalFrame1);
                    aturanpakai.setVisible(true);
                } else if (i == 3) {
                    if (tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 1).equals("")) {
                        JOptionPane.showMessageDialog(null, "Silahkan masukkan nama racikan..!!");
                        tbObatRacikan.requestFocus();
                    } else {
                        metoderacik.isCek();
                        metoderacik.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                        metoderacik.setLocationRelativeTo(internalFrame1);
                        metoderacik.setVisible(true);
                    }
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
                if ((i == 4)) {
                    TCari.requestFocus();
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                if ((i == 6)) {
                    if (tbObatRacikan.getSelectedRow() != -1) {
                        if (tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 0).toString().equals("")
                                || tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 1).toString().equals("")
                                || tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 2).toString().equals("")
                                || tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 3).toString().equals("")
                                || tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 4).toString().equals("")
                                || tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 5).toString().equals("")) {
                            JOptionPane.showMessageDialog(null, "Silahkan lengkapi data racikan..!!");
                        } else {
                            tampildetailracikanobat();
                            TCari.requestFocus();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Silahkan pilih racikan..!!");
                    }
                }
            }
        }
    }//GEN-LAST:event_tbObatRacikanKeyPressed

    private void tbDetailObatRacikanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDetailObatRacikanMouseClicked
        if (tbObat.getRowCount() != 0) {
            try {
                getDatadetailobatracikan();
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_tbDetailObatRacikanMouseClicked

    private void tbDetailObatRacikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDetailObatRacikanKeyPressed
        if (tbObatRacikan.getSelectedRow() != -1) {
            if (tbDetailObatRacikan.getRowCount() != 0) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        getDatadetailobatracikan();
                        i = tbDetailObatRacikan.getSelectedColumn();
                        if (i == 11) {
                            try {
                                if (tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(), 11).toString().equals("0") || tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(), 11).toString().equals("") || tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(), 11).toString().equals("0.0") || tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(), 11).toString().equals("0,0")) {
                                    tbDetailObatRacikan.setValueAt(embalase, tbDetailObatRacikan.getSelectedRow(), 11);
                                }
                            } catch (Exception e) {
                                tbDetailObatRacikan.setValueAt(0, tbDetailObatRacikan.getSelectedRow(), 11);
                            }
                        } else if (i == 12) {
                            try {
                                if (tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(), 12).toString().equals("0") || tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(), 12).toString().equals("") || tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(), 12).toString().equals("0.0") || tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(), 12).toString().equals("0,0")) {
                                    tbDetailObatRacikan.setValueAt(tuslah, tbDetailObatRacikan.getSelectedRow(), 12);
                                }
                            } catch (Exception e) {
                                tbDetailObatRacikan.setValueAt(0, tbDetailObatRacikan.getSelectedRow(), 12);
                            }

                            TCari.setText("");
                            TCari.requestFocus();
                        } else if ((i == 9) || (i == 10)) {
                            try {
                                tbDetailObatRacikan.setValueAt(
                                        Valid.SetAngka8((Double.parseDouble(tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 4).toString())
                                                * Double.parseDouble(tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(), 9).toString()))
                                                / Double.parseDouble(tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(), 8).toString()), 1),
                                        tbDetailObatRacikan.getSelectedRow(), 10);
                            } catch (Exception e) {
                                tbDetailObatRacikan.setValueAt(0, tbDetailObatRacikan.getSelectedRow(), 10);
                                tbDetailObatRacikan.setValueAt(0, tbDetailObatRacikan.getSelectedRow(), 11);
                                tbDetailObatRacikan.setValueAt(0, tbDetailObatRacikan.getSelectedRow(), 12);
                            }

                            TCari.setText("");
                            TCari.requestFocus();
                        } else if (i == 11) {
                            TCari.setText("");
                            TCari.requestFocus();
                        }
                    } catch (java.lang.NullPointerException e) {
                    }
                } else if ((evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                    try {
                        if (!tabModeDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(), 9).toString().equals("")) {
                            getDatadetailobatracikan();
                        }
                    } catch (java.lang.NullPointerException e) {
                    }
                } else if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
                    i = tbDetailObatRacikan.getSelectedColumn();
                    if ((i == 9) || (i == 10)) {
                        if (tbDetailObatRacikan.getSelectedRow() != -1) {
                            tbDetailObatRacikan.setValueAt("", tbDetailObatRacikan.getSelectedRow(), 9);
                            tbDetailObatRacikan.setValueAt(0, tbDetailObatRacikan.getSelectedRow(), 10);
                            tbDetailObatRacikan.setValueAt(0, tbDetailObatRacikan.getSelectedRow(), 11);
                            tbDetailObatRacikan.setValueAt(0, tbDetailObatRacikan.getSelectedRow(), 12);
                        }
                    }

                } else if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
                    i = tbDetailObatRacikan.getSelectedColumn();
                    if (i != 9) {
                        TCari.requestFocus();
                    }
                } else if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
                    i = tbDetailObatRacikan.getSelectedColumn();
                    if ((i == 9) || (i == 10)) {
                        try {
                            getDatadetailobatracikan();

                            try {
                                if (tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(), 11).toString().equals("0") || tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(), 11).toString().equals("") || tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(), 11).toString().equals("0.0") || tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(), 11).toString().equals("0,0")) {
                                    tbDetailObatRacikan.setValueAt(embalase, tbDetailObatRacikan.getSelectedRow(), 11);
                                }
                            } catch (Exception e) {
                                tbDetailObatRacikan.setValueAt(0, tbDetailObatRacikan.getSelectedRow(), 11);
                            }

                            try {
                                if (tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(), 12).toString().equals("0") || tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(), 12).toString().equals("") || tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(), 12).toString().equals("0.0") || tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(), 12).toString().equals("0,0")) {
                                    tbDetailObatRacikan.setValueAt(tuslah, tbDetailObatRacikan.getSelectedRow(), 12);
                                }
                            } catch (Exception e) {
                                tbDetailObatRacikan.setValueAt(0, tbDetailObatRacikan.getSelectedRow(), 12);
                            }
                        } catch (Exception e) {
                            tbDetailObatRacikan.setValueAt(0, tbDetailObatRacikan.getSelectedRow(), 10);
                            tbDetailObatRacikan.setValueAt(0, tbDetailObatRacikan.getSelectedRow(), 11);
                            tbDetailObatRacikan.setValueAt(0, tbDetailObatRacikan.getSelectedRow(), 12);
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Silahkan pilih No.Racikan terlebih dahulu");
        }
    }//GEN-LAST:event_tbDetailObatRacikanKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if (TabRawat.getSelectedIndex() == 0) {
            BtnTambah1.setVisible(false);
            BtnHapus.setVisible(false);
            label13.setPreferredSize(new Dimension(65, 23));
        } else if (TabRawat.getSelectedIndex() == 1) {
            BtnTambah1.setVisible(true);
            BtnHapus.setVisible(true);
            label13.setPreferredSize(new Dimension(1, 23));
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void BtnTambah1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambah1ActionPerformed
        i = tabModeObatRacikan.getRowCount() + 1;
        if (i == 99) {
            JOptionPane.showMessageDialog(null, "Maksimal 98 Racikan..!!");
        } else {
            tabModeObatRacikan.addRow(new Object[]{"" + i, "", "", "", "", "", ""});
        }
    }//GEN-LAST:event_BtnTambah1ActionPerformed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if (tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 1).equals("") && tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 4).equals("") && tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 5).equals("") && tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 6).equals("")) {
            tabModeObatRacikan.removeRow(tbObatRacikan.getSelectedRow());
        } else {
            JOptionPane.showMessageDialog(null, "Maaf sudah terisi, gak boleh dihapus..!!");
        }

    }//GEN-LAST:event_BtnHapusActionPerformed

    private void tbObatPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbObatPropertyChange
        if (this.isVisible() == true) {
            getData();
        }
    }//GEN-LAST:event_tbObatPropertyChange

    private void tbDetailObatRacikanPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbDetailObatRacikanPropertyChange
        if (this.isVisible() == true) {
            getDatadetailobatracikan();
        }
    }//GEN-LAST:event_tbDetailObatRacikanPropertyChange

    private void ppStok1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppStok1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgCekStok ceksetok = new DlgCekStok(null, false);
        ceksetok.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        ceksetok.setLocationRelativeTo(internalFrame1);
        ceksetok.setAlwaysOnTop(false);
        ceksetok.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppStok1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariObat4 dialog = new DlgCariObat4(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnGudang;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnSeek5;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.Button BtnTambah1;
    private widget.CekBox ChkJln;
    private widget.CekBox ChkNoResep;
    private widget.Tanggal DTPTgl;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox Jeniskelas;
    private widget.TextBox KdPj;
    private widget.TextBox NoResep;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.TextBox TCari;
    private widget.TextBox TNoRw;
    private javax.swing.JTabbedPane TabRawat;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbMnt;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel5;
    private javax.swing.JPanel jPanel3;
    private widget.TextBox kdgudang;
    private widget.TextBox kelas;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label21;
    private widget.Label label9;
    private widget.TextBox nmgudang;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppStok;
    private javax.swing.JMenuItem ppStok1;
    private widget.Table tbDetailObatRacikan;
    private widget.Table tbObat;
    private widget.Table tbObatRacikan;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        jml = 0;
        for (i = 0; i < tbObat.getRowCount(); i++) {
            if (!tbObat.getValueAt(i, 0).toString().equals("")) {
                jml++;
            }
        }

        pilih = null;
        pilih = new boolean[jml];
        jumlah = null;
        jumlah = new double[jml];
        eb = null;
        eb = new double[jml];
        ts = null;
        ts = new double[jml];
        stok = null;
        stok = new double[jml];
        harga = null;
        harga = new double[jml];
        kodebarang = null;
        kodebarang = new String[jml];
        namabarang = null;
        namabarang = new String[jml];
        kodesatuan = null;
        kodesatuan = new String[jml];
        letakbarang = null;
        letakbarang = new String[jml];
        namajenis = null;
        namajenis = new String[jml];
        industri = null;
        industri = new String[jml];
        beli = null;
        beli = new double[jml];
        aturan = null;
        aturan = new String[jml];
        kategori = null;
        kategori = new String[jml];
        golongan = null;
        golongan = new String[jml];
        nobatch = new String[jml];
        nofaktur = new String[jml];
        kadaluarsa = new String[jml];

        jml = 0;
        for (i = 0; i < tbObat.getRowCount(); i++) {
            if (!tbObat.getValueAt(i, 1).toString().equals("")) {
                pilih[jml] = Boolean.parseBoolean(tbObat.getValueAt(i, 0).toString());
                try {
                    jumlah[jml] = Double.parseDouble(tbObat.getValueAt(i, 1).toString());
                } catch (Exception e) {
                    jumlah[jml] = 0;
                }
                kodebarang[jml] = tbObat.getValueAt(i, 2).toString();
                namabarang[jml] = tbObat.getValueAt(i, 3).toString();
                kodesatuan[jml] = tbObat.getValueAt(i, 4).toString();
                letakbarang[jml] = tbObat.getValueAt(i, 5).toString();
                try {
                    harga[jml] = Double.parseDouble(tbObat.getValueAt(i, 6).toString());
                } catch (Exception e) {
                    harga[jml] = 0;
                }
                namajenis[jml] = tbObat.getValueAt(i, 7).toString();
                try {
                    eb[jml] = Double.parseDouble(tbObat.getValueAt(i, 8).toString());
                } catch (Exception e) {
                    eb[jml] = 0;
                }
                try {
                    ts[jml] = Double.parseDouble(tbObat.getValueAt(i, 9).toString());
                } catch (Exception e) {
                    ts[jml] = 0;
                }
                try {
                    stok[jml] = Double.parseDouble(tbObat.getValueAt(i, 10).toString());
                } catch (Exception e) {
                    stok[jml] = 0;
                }
                industri[jml] = tbObat.getValueAt(i, 11).toString();
                try {
                    beli[jml] = Double.parseDouble(tbObat.getValueAt(i, 12).toString());
                } catch (Exception e) {
                    beli[jml] = 0;
                }
                aturan[jml] = tbObat.getValueAt(i, 13).toString();
                kategori[jml] = tbObat.getValueAt(i, 14).toString();
                golongan[jml] = tbObat.getValueAt(i, 15).toString();
                nobatch[jml] = tbObat.getValueAt(i, 16).toString();
                nofaktur[jml] = tbObat.getValueAt(i, 17).toString();
                kadaluarsa[jml] = tbObat.getValueAt(i, 18).toString();
                jml++;
            }
        }

        Valid.tabelKosong(tabMode);

        for (i = 0; i < jml; i++) {
            tabMode.addRow(new Object[]{pilih[i], jumlah[i], kodebarang[i], namabarang[i],
                kodesatuan[i], letakbarang[i], harga[i], namajenis[i], eb[i], ts[i], stok[i], industri[i],
                beli[i], aturan[i], kategori[i], golongan[i], nobatch[i], nofaktur[i], kadaluarsa[i]
            });
        }

        try {
            if (kenaikan > 0) {
                if (aktifkanbatch.equals("yes")) {
                    if (aktifpcare.equals("yes")) {
                        sql = "select data_batch.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(data_batch.h_beli+(data_batch.h_beli*?)) as harga,"
                                + " databarang.letak_barang,industrifarmasi.nama_industri,data_batch.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan, "
                                + " data_batch.no_batch,data_batch.no_faktur,data_batch.tgl_kadaluarsa,data_batch.sisa "
                                + " from data_batch inner join databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang inner join maping_obat_pcare on maping_obat_pcare.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns "
                                + " and data_batch.kode_brng=databarang.kode_brng and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode ";
                    } else {
                        sql = "select data_batch.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(data_batch.h_beli+(data_batch.h_beli*?)) as harga,"
                                + " databarang.letak_barang,industrifarmasi.nama_industri,data_batch.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan, "
                                + " data_batch.no_batch,data_batch.no_faktur,data_batch.tgl_kadaluarsa,data_batch.sisa "
                                + " from data_batch inner join databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang on databarang.kdjns=jenis.kdjns "
                                + " and data_batch.kode_brng=databarang.kode_brng and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode ";
                    }
                    psobat = koneksi.prepareStatement(
                            sql + " where data_batch.sisa>0 and databarang.kode_brng like ? or "
                            + " data_batch.sisa>0 and databarang.nama_brng like ? or "
                            + " data_batch.sisa>0 and kategori_barang.nama like ? or "
                            + " data_batch.sisa>0 and golongan_barang.nama like ? or "
                            + " data_batch.sisa>0 and data_batch.no_batch like ? or "
                            + " data_batch.sisa>0 and jenis.nama like ? order by data_batch.tgl_kadaluarsa");
                    try {
                        psobat.setDouble(1, kenaikan);
                        psobat.setString(2, "%" + TCari.getText().trim() + "%");
                        psobat.setString(3, "%" + TCari.getText().trim() + "%");
                        psobat.setString(4, "%" + TCari.getText().trim() + "%");
                        psobat.setString(5, "%" + TCari.getText().trim() + "%");
                        psobat.setString(6, "%" + TCari.getText().trim() + "%");
                        psobat.setString(7, "%" + TCari.getText().trim() + "%");
                        rsobat = psobat.executeQuery();
                        while (rsobat.next()) {
                            tabMode.addRow(new Object[]{false, "", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("harga"), 100),
                                rsobat.getString("nama"), 0, 0, rsobat.getDouble("sisa"), rsobat.getString("nama_industri"),
                                rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"),
                                rsobat.getString("no_batch"), rsobat.getString("no_faktur"), rsobat.getString("tgl_kadaluarsa")
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (rsobat != null) {
                            rsobat.close();
                        }
                        if (psobat != null) {
                            psobat.close();
                        }
                    }
                } else {
                    if (aktifpcare.equals("yes")) {
                        sql = "select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as harga,"
                                + " databarang.letak_barang,industrifarmasi.nama_industri,databarang.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan "
                                + " from databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang inner join maping_obat_pcare on maping_obat_pcare.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns "
                                + " and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode ";
                    } else {
                        sql = "select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as harga,"
                                + " databarang.letak_barang,industrifarmasi.nama_industri,databarang.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan "
                                + " from databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang on databarang.kdjns=jenis.kdjns "
                                + " and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode ";
                    }
                    psobat = koneksi.prepareStatement(
                            sql + " where databarang.status='1' and databarang.kode_brng like ? or "
                            + " databarang.status='1' and databarang.nama_brng like ? or "
                            + " databarang.status='1' and kategori_barang.nama like ? or "
                            + " databarang.status='1' and golongan_barang.nama like ? or "
                            + " databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
                    try {
                        psobat.setDouble(1, kenaikan);
                        psobat.setString(2, "%" + TCari.getText().trim() + "%");
                        psobat.setString(3, "%" + TCari.getText().trim() + "%");
                        psobat.setString(4, "%" + TCari.getText().trim() + "%");
                        psobat.setString(5, "%" + TCari.getText().trim() + "%");
                        psobat.setString(6, "%" + TCari.getText().trim() + "%");
                        rsobat = psobat.executeQuery();
                        while (rsobat.next()) {
                            tabMode.addRow(new Object[]{false, "", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("harga"), 100),
                                rsobat.getString("nama"), 0, 0, 0, rsobat.getString("nama_industri"),
                                rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (rsobat != null) {
                            rsobat.close();
                        }
                        if (psobat != null) {
                            psobat.close();
                        }
                    }
                }
            } else {
                if (aktifkanbatch.equals("yes")) {
                    if (aktifpcare.equals("yes")) {
                        sql = "select data_batch.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,data_batch.kelas1,"
                                + " data_batch.kelas2,data_batch.kelas3,data_batch.utama,data_batch.vip,data_batch.vvip,data_batch.beliluar,data_batch.karyawan,"
                                + " databarang.letak_barang,industrifarmasi.nama_industri,data_batch.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan, "
                                + " data_batch.no_batch,data_batch.no_faktur,data_batch.tgl_kadaluarsa,data_batch.sisa "
                                + " from data_batch inner join databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang "
                                + " inner join maping_obat_pcare on maping_obat_pcare.kode_brng=databarang.kode_brng and data_batch.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode  ";
                    } else {
                        sql = "select data_batch.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,data_batch.kelas1,"
                                + " data_batch.kelas2,data_batch.kelas3,data_batch.utama,data_batch.vip,data_batch.vvip,data_batch.beliluar,data_batch.karyawan,"
                                + " databarang.letak_barang,industrifarmasi.nama_industri,data_batch.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan, "
                                + " data_batch.no_batch,data_batch.no_faktur,data_batch.tgl_kadaluarsa,data_batch.sisa "
                                + " from data_batch inner join databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang "
                                + " on data_batch.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode  ";
                    }
                    psobat = koneksi.prepareStatement(
                            sql + " where data_batch.sisa>0 and databarang.kode_brng like ? or "
                            + " data_batch.sisa>0 and databarang.nama_brng like ? or "
                            + " data_batch.sisa>0 and kategori_barang.nama like ? or "
                            + " data_batch.sisa>0 and golongan_barang.nama like ? or "
                            + " data_batch.sisa>0 and data_batch.no_batch like ? or "
                            + " data_batch.sisa>0 and jenis.nama like ? order by data_batch.tgl_kadaluarsa");
                    try {
                        psobat.setString(1, "%" + TCari.getText().trim() + "%");
                        psobat.setString(2, "%" + TCari.getText().trim() + "%");
                        psobat.setString(3, "%" + TCari.getText().trim() + "%");
                        psobat.setString(4, "%" + TCari.getText().trim() + "%");
                        psobat.setString(5, "%" + TCari.getText().trim() + "%");
                        psobat.setString(6, "%" + TCari.getText().trim() + "%");
                        rsobat = psobat.executeQuery();
                        while (rsobat.next()) {
                            if (Jeniskelas.getSelectedItem().equals("Kelas 1")) {
                                tabMode.addRow(new Object[]{false, "", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("kelas1"), 100),
                                    rsobat.getString("nama"), 0, 0, rsobat.getDouble("sisa"), rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"),
                                    rsobat.getString("no_batch"), rsobat.getString("no_faktur"), rsobat.getString("tgl_kadaluarsa")
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Kelas 2")) {
                                tabMode.addRow(new Object[]{false, "", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("kelas2"), 100),
                                    rsobat.getString("nama"), 0, 0, rsobat.getDouble("sisa"), rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"),
                                    rsobat.getString("no_batch"), rsobat.getString("no_faktur"), rsobat.getString("tgl_kadaluarsa")
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Kelas 3")) {
                                tabMode.addRow(new Object[]{false, "", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("kelas3"), 100),
                                    rsobat.getString("nama"), 0, 0, rsobat.getDouble("sisa"), rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"),
                                    rsobat.getString("no_batch"), rsobat.getString("no_faktur"), rsobat.getString("tgl_kadaluarsa")
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Utama/BPJS")) {
                                tabMode.addRow(new Object[]{false, "", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("utama"), 100),
                                    rsobat.getString("nama"), 0, 0, rsobat.getDouble("sisa"), rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"),
                                    rsobat.getString("no_batch"), rsobat.getString("no_faktur"), rsobat.getString("tgl_kadaluarsa")
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("VIP")) {
                                tabMode.addRow(new Object[]{false, "", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("vip"), 100),
                                    rsobat.getString("nama"), 0, 0, rsobat.getDouble("sisa"), rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"),
                                    rsobat.getString("no_batch"), rsobat.getString("no_faktur"), rsobat.getString("tgl_kadaluarsa")
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("VVIP")) {
                                tabMode.addRow(new Object[]{false, "", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("vvip"), 100),
                                    rsobat.getString("nama"), 0, 0, rsobat.getDouble("sisa"), rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"),
                                    rsobat.getString("no_batch"), rsobat.getString("no_faktur"), rsobat.getString("tgl_kadaluarsa")
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Beli Luar")) {
                                tabMode.addRow(new Object[]{false, "", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("beliluar"), 100),
                                    rsobat.getString("nama"), 0, 0, rsobat.getDouble("sisa"), rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"),
                                    rsobat.getString("no_batch"), rsobat.getString("no_faktur"), rsobat.getString("tgl_kadaluarsa")
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Karyawan")) {
                                tabMode.addRow(new Object[]{false, "", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("karyawan"), 100),
                                    rsobat.getString("nama"), 0, 0, rsobat.getDouble("sisa"), rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"),
                                    rsobat.getString("no_batch"), rsobat.getString("no_faktur"), rsobat.getString("tgl_kadaluarsa")
                                });
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (rsobat != null) {
                            rsobat.close();
                        }
                        if (psobat != null) {
                            psobat.close();
                        }
                    }
                } else {
                    if (aktifpcare.equals("yes")) {
                        sql = "select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,databarang.kelas1,"
                                + " databarang.kelas2,databarang.kelas3,databarang.utama,databarang.vip,databarang.vvip,databarang.beliluar,databarang.karyawan,"
                                + " databarang.letak_barang,industrifarmasi.nama_industri,databarang.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan "
                                + " from databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang "
                                + " inner join maping_obat_pcare on maping_obat_pcare.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode  ";
                    } else {
                        sql = "select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,databarang.kelas1,"
                                + " databarang.kelas2,databarang.kelas3,databarang.utama,databarang.vip,databarang.vvip,databarang.beliluar,databarang.karyawan,"
                                + " databarang.letak_barang,industrifarmasi.nama_industri,databarang.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan "
                                + " from databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang "
                                + " on databarang.kdjns=jenis.kdjns and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode  ";
                    }
                    psobat = koneksi.prepareStatement(
                            sql + " where databarang.status='1' and databarang.kode_brng like ? or "
                            + " databarang.status='1' and databarang.nama_brng like ? or "
                            + " databarang.status='1' and kategori_barang.nama like ? or "
                            + " databarang.status='1' and golongan_barang.nama like ? or "
                            + " databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
                    try {
                        psobat.setString(1, "%" + TCari.getText().trim() + "%");
                        psobat.setString(2, "%" + TCari.getText().trim() + "%");
                        psobat.setString(3, "%" + TCari.getText().trim() + "%");
                        psobat.setString(4, "%" + TCari.getText().trim() + "%");
                        psobat.setString(5, "%" + TCari.getText().trim() + "%");
                        rsobat = psobat.executeQuery();
                        while (rsobat.next()) {
                            if (Jeniskelas.getSelectedItem().equals("Kelas 1")) {
                                tabMode.addRow(new Object[]{false, "", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("kelas1"), 100),
                                    rsobat.getString("nama"), 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Kelas 2")) {
                                tabMode.addRow(new Object[]{false, "", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("kelas2"), 100),
                                    rsobat.getString("nama"), 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Kelas 3")) {
                                tabMode.addRow(new Object[]{false, "", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("kelas3"), 100),
                                    rsobat.getString("nama"), 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Utama/BPJS")) {
                                tabMode.addRow(new Object[]{false, "", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("utama"), 100),
                                    rsobat.getString("nama"), 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("VIP")) {
                                tabMode.addRow(new Object[]{false, "", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("vip"), 100),
                                    rsobat.getString("nama"), 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("VVIP")) {
                                tabMode.addRow(new Object[]{false, "", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("vvip"), 100),
                                    rsobat.getString("nama"), 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Beli Luar")) {
                                tabMode.addRow(new Object[]{false, "", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("beliluar"), 100),
                                    rsobat.getString("nama"), 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Karyawan")) {
                                tabMode.addRow(new Object[]{false, "", rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("karyawan"), 100),
                                    rsobat.getString("nama"), 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), "", rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (rsobat != null) {
                            rsobat.close();
                        }
                        if (psobat != null) {
                            psobat.close();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    public void emptTeks() {
        TCari.requestFocus();
    }

    private void getData() {
        if (nmgudang.getText().trim().equals("")) {
            Valid.textKosong(kdgudang, "Lokasi");
        } else {
            if (tbObat.getSelectedRow() != -1) {
                row = tbObat.getSelectedRow();
                try {
                    if (Valid.SetAngka(tbObat.getValueAt(row, 1).toString()) > 0) {
                        if (aktifkanbatch.equals("no")) {
                            stokbarang = 0;
                            psstok = koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=?");
                            try {
                                psstok.setString(1, kdgudang.getText());
                                psstok.setString(2, tbObat.getValueAt(row, 2).toString());
                                rsstok = psstok.executeQuery();
                                if (rsstok.next()) {
                                    stokbarang = rsstok.getDouble(1);
                                }
                            } catch (Exception e) {
                                stokbarang = 0;
                                System.out.println("Notifikasi : " + e);
                            } finally {
                                if (rsstok != null) {
                                    rsstok.close();
                                }
                                if (psstok != null) {
                                    psstok.close();
                                }
                            }
                            tbObat.setValueAt(stokbarang, row, 10);
                        }

                        y = 0;
                        try {
                            if (tbObat.getValueAt(row, 0).toString().equals("true")) {
                                pscarikapasitas = koneksi.prepareStatement("select IFNULL(kapasitas,1) from databarang where kode_brng=?");
                                try {
                                    pscarikapasitas.setString(1, tbObat.getValueAt(row, 2).toString());
                                    carikapasitas = pscarikapasitas.executeQuery();
                                    if (carikapasitas.next()) {
                                        y = Double.parseDouble(tbObat.getValueAt(row, 1).toString()) / carikapasitas.getDouble(1);
                                    } else {
                                        y = Double.parseDouble(tbObat.getValueAt(row, 1).toString());
                                    }
                                } catch (Exception e) {
                                    y = Double.parseDouble(tbObat.getValueAt(row, 1).toString());
                                    System.out.println("Kapasitasmu masih kosong broooh : " + e);
                                } finally {
                                    if (carikapasitas != null) {
                                        carikapasitas.close();
                                    }
                                    if (pscarikapasitas != null) {
                                        pscarikapasitas.close();
                                    }
                                }
                            } else {
                                y = Double.parseDouble(tbObat.getValueAt(row, 1).toString());
                            }
                        } catch (Exception e) {
                            y = 0;
                        }

                        stokbarang = 0;
                        try {
                            stokbarang = Double.parseDouble(tbObat.getValueAt(row, 10).toString());
                        } catch (Exception e) {
                            stokbarang = 0;
                        }

                        if (stokbarang < y) {
                            JOptionPane.showMessageDialog(rootPane, "Maaf stok tidak mencukupi..!!");
                            tbObat.setValueAt("", row, 1);
                        }
                    }
                    if ((tbObat.getSelectedColumn() == 16) || (tbObat.getSelectedColumn() == 17)) {
                        cariBatch();
                        //getData2();
                    }
                } catch (Exception e) {
                    tbObat.setValueAt(0, row, 10);
                }
            }
        }
    }

    private void getDataobat(int data) {
        try {
            if (aktifkanbatch.equals("no")) {
                stokbarang = 0;
                psstok = koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=?");
                try {
                    psstok.setString(1, kdgudang.getText());
                    psstok.setString(2, tbObat.getValueAt(data, 2).toString());
                    rsstok = psstok.executeQuery();
                    if (rsstok.next()) {
                        stokbarang = rsstok.getDouble(1);
                    }
                } catch (Exception e) {
                    stokbarang = 0;
                    System.out.println("Notifikasi : " + e);
                } finally {
                    if (rsstok != null) {
                        rsstok.close();
                    }
                    if (psstok != null) {
                        psstok.close();
                    }
                }

                tbObat.setValueAt(stokbarang, data, 10);
                y = 0;
                try {
                    if (tbObat.getValueAt(data, 0).toString().equals("true")) {
                        pscarikapasitas = koneksi.prepareStatement("select IFNULL(kapasitas,1) from databarang where kode_brng=?");
                        try {
                            pscarikapasitas.setString(1, tbObat.getValueAt(data, 2).toString());
                            carikapasitas = pscarikapasitas.executeQuery();
                            if (carikapasitas.next()) {
                                y = Double.parseDouble(tbObat.getValueAt(data, 1).toString()) / carikapasitas.getDouble(1);
                            } else {
                                y = Double.parseDouble(tbObat.getValueAt(data, 1).toString());
                            }
                        } catch (Exception e) {
                            y = Double.parseDouble(tbObat.getValueAt(data, 1).toString());
                            System.out.println("Kapasitasmu masih kosong broooh : " + e);
                        } finally {
                            if (carikapasitas != null) {
                                carikapasitas.close();
                            }
                            if (pscarikapasitas != null) {
                                pscarikapasitas.close();
                            }
                        }
                    } else {
                        y = Double.parseDouble(tbObat.getValueAt(data, 1).toString());
                    }
                } catch (Exception e) {
                    y = 0;
                }
                if (stokbarang < y) {
                    JOptionPane.showMessageDialog(rootPane, "Maaf stok tidak mencukupi..!!");
                }
            }
        } catch (Exception e) {
            tbObat.setValueAt(0, data, 10);
        }
    }

    public JTable getTable() {
        return tbObat;
    }

    public void isCek() {
        kdgudang.setText(var.getkdbangsal());
        Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?", nmgudang, kdgudang.getText());
        BtnTambah.setEnabled(var.getobat());
        TCari.requestFocus();

        if (Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?", kdgudang.getText()).equals("")) {
            kdgudang.setEditable(true);
            nmgudang.setEditable(true);
            BtnGudang.setEnabled(true);
        } else {
            if (var.getakses_depo_obat() == true) {
                kdgudang.setEditable(true);
                nmgudang.setEditable(true);
                BtnGudang.setEnabled(true);
            } else {
                kdgudang.setEditable(false);
                nmgudang.setEditable(false);
                BtnGudang.setEnabled(false);
            }
        }

        if (var.getkode().equals("Admin Utama")) {
            kdgudang.setEditable(true);
            nmgudang.setEditable(true);
            BtnGudang.setEnabled(true);
        }
    }

    public void setNoRm(String norwt, Date tanggal, String jam, String menit, String detik, boolean status) {
        aktifpcare = "no";
        TNoRw.setText(norwt);
        DTPTgl.setDate(tanggal);
        cmbJam.setSelectedItem(jam);
        cmbMnt.setSelectedItem(menit);
        cmbDtk.setSelectedItem(detik);
        ChkJln.setSelected(status);
        this.noresep = "";
        KdPj.setText(Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?", norwt));
        kelas.setText(Sequel.cariIsi(
                "select kamar.kelas from kamar inner join kamar_inap on kamar.kd_kamar=kamar_inap.kd_kamar "
                + "where no_rawat=? and stts_pulang='-' order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1", norwt));
        if (kelas.getText().equals("Kelas 1")) {
            Jeniskelas.setSelectedItem("Kelas 1");
        } else if (kelas.getText().equals("Kelas 2")) {
            Jeniskelas.setSelectedItem("Kelas 2");
        } else if (kelas.getText().equals("Kelas 3")) {
            Jeniskelas.setSelectedItem("Kelas 3");
        } else if (kelas.getText().equals("Kelas Utama")) {
            Jeniskelas.setSelectedItem("Utama/BPJS");
        } else if (kelas.getText().equals("Kelas VIP")) {
            Jeniskelas.setSelectedItem("VIP");
        } else if (kelas.getText().equals("Kelas VVIP")) {
            Jeniskelas.setSelectedItem("VVIP");
        }
        kenaikan = Sequel.cariIsiAngka("select (hargajual/100) from set_harga_obat_ranap where kd_pj='" + KdPj.getText() + "' and kelas='" + kelas.getText() + "'");
        TCari.requestFocus();
    }

    public void setNoRm(String norwt, Date tanggal) {
        aktifpcare = "no";
        TNoRw.setText(norwt);
        DTPTgl.setDate(tanggal);
        ChkJln.setSelected(true);
        KdPj.setText(Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?", norwt));
        kelas.setText(Sequel.cariIsi(
                "select kamar.kelas from kamar inner join kamar_inap on kamar.kd_kamar=kamar_inap.kd_kamar "
                + "where no_rawat=? and stts_pulang='-' order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1", norwt));
        if (kelas.getText().equals("Kelas 1")) {
            Jeniskelas.setSelectedItem("Kelas 1");
        } else if (kelas.getText().equals("Kelas 2")) {
            Jeniskelas.setSelectedItem("Kelas 2");
        } else if (kelas.getText().equals("Kelas 3")) {
            Jeniskelas.setSelectedItem("Kelas 3");
        } else if (kelas.getText().equals("Kelas Utama")) {
            Jeniskelas.setSelectedItem("Utama/BPJS");
        } else if (kelas.getText().equals("Kelas VIP")) {
            Jeniskelas.setSelectedItem("VIP");
        } else if (kelas.getText().equals("Kelas VVIP")) {
            Jeniskelas.setSelectedItem("VVIP");
        }
        kenaikan = Sequel.cariIsiAngka("select (hargajual/100) from set_harga_obat_ranap where kd_pj='" + KdPj.getText() + "' and kelas='" + kelas.getText() + "'");
        TCari.requestFocus();
    }

    private void jam() {
        ActionListener taskPerformer = new ActionListener() {
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;

            @Override
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                // Membuat Date
                //Date dt = new Date();
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if (ChkJln.isSelected() == true) {
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                } else if (ChkJln.isSelected() == false) {
                    nilai_jam = cmbJam.getSelectedIndex();
                    nilai_menit = cmbMnt.getSelectedIndex();
                    nilai_detik = cmbDtk.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                cmbJam.setSelectedItem(jam);
                cmbMnt.setSelectedItem(menit);
                cmbDtk.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }

    private void getDatadetailobatracikan() {
        if (tbDetailObatRacikan.getSelectedRow() != -1) {
            row = tbDetailObatRacikan.getSelectedRow();
            try {
                tbDetailObatRacikan.setValueAt(
                        Valid.SetAngka8((Double.parseDouble(tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 4).toString())
                                * Valid.SetAngka(tbDetailObatRacikan.getValueAt(row, 9).toString()))
                                / Valid.SetAngka(tbDetailObatRacikan.getValueAt(row, 8).toString()), 1), row, 10
                );
                if (Valid.SetAngka(tbDetailObatRacikan.getValueAt(row, 8).toString()) > 0) {
                    if (aktifkanbatch.equals("no")) {
                        stokbarang = 0;
                        psstok = koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=?");
                        try {
                            psstok.setString(1, kdgudang.getText());
                            psstok.setString(2, tbDetailObatRacikan.getValueAt(row, 1).toString());
                            rsstok = psstok.executeQuery();
                            if (rsstok.next()) {
                                stokbarang = rsstok.getDouble(1);
                            }
                        } catch (Exception e) {
                            stokbarang = 0;
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rsstok != null) {
                                rsstok.close();
                            }
                            if (psstok != null) {
                                psstok.close();
                            }
                        }
                        tbDetailObatRacikan.setValueAt(stokbarang, row, 7);
                    }

                    y = 0;
                    try {
                        y = Double.parseDouble(tbDetailObatRacikan.getValueAt(row, 10).toString());
                    } catch (Exception e) {
                        y = 0;
                    }

                    stokbarang = 0;
                    try {
                        stokbarang = Double.parseDouble(tbDetailObatRacikan.getValueAt(row, 7).toString());
                    } catch (Exception e) {
                        stokbarang = 0;
                    }

                    if (stokbarang < y) {
                        JOptionPane.showMessageDialog(rootPane, "Maaf stok tidak mencukupi..!!");
                        tbDetailObatRacikan.setValueAt("", row, 9);
                        tbDetailObatRacikan.setValueAt(0, row, 10);
                        tbDetailObatRacikan.setValueAt(0, row, 11);
                        tbDetailObatRacikan.setValueAt(0, row, 12);
                    }
                }
                if ((tbDetailObatRacikan.getSelectedColumn() == 16) || (tbDetailObatRacikan.getSelectedColumn() == 17)) {
                    cariBatch();
                    //getData2();
                }
            } catch (Exception e) {
                System.out.println("Notif Racikan : " + e);
                tbDetailObatRacikan.setValueAt(0, row, 10);
                tbDetailObatRacikan.setValueAt(0, row, 11);
                tbDetailObatRacikan.setValueAt(0, row, 12);
            }
        }
    }

    public void tampildetailracikanobat() {
        z = 0;
        for (i = 0; i < tbDetailObatRacikan.getRowCount(); i++) {
            if (!tbDetailObatRacikan.getValueAt(i, 9).toString().equals("")) {
                z++;
            }
        }

        pilih = null;
        pilih = new boolean[z];
        jumlah = null;
        jumlah = new double[z];
        harga = null;
        harga = new double[z];
        eb = null;
        eb = new double[z];
        ts = null;
        ts = new double[z];
        stok = null;
        stok = new double[z];
        kodebarang = null;
        kodebarang = new String[z];
        namabarang = null;
        namabarang = new String[z];
        kodesatuan = null;
        kodesatuan = new String[z];
        letakbarang = null;
        letakbarang = new String[z];
        no = null;
        no = new String[z];
        namajenis = null;
        namajenis = new String[z];
        industri = null;
        industri = new String[z];
        beli = null;
        beli = new double[z];
        kategori = null;
        kategori = new String[z];
        golongan = null;
        golongan = new String[z];
        kapasitas = null;
        kapasitas = new double[z];
        kandungan = null;
        kandungan = new double[z];
        nobatch = new String[z];
        nofaktur = new String[z];
        kadaluarsa = new String[z];
        z = 0;
        for (i = 0; i < tbDetailObatRacikan.getRowCount(); i++) {
            if (!tbDetailObatRacikan.getValueAt(i, 9).toString().equals("")) {
                no[z] = tbDetailObatRacikan.getValueAt(i, 0).toString();
                kodebarang[z] = tbDetailObatRacikan.getValueAt(i, 1).toString();
                namabarang[z] = tbDetailObatRacikan.getValueAt(i, 2).toString();
                kodesatuan[z] = tbDetailObatRacikan.getValueAt(i, 3).toString();
                try {
                    harga[z] = Double.parseDouble(tbDetailObatRacikan.getValueAt(i, 4).toString());
                } catch (Exception e) {
                    harga[z] = 0;
                }
                try {
                    beli[z] = Double.parseDouble(tbDetailObatRacikan.getValueAt(i, 5).toString());
                } catch (Exception e) {
                    beli[z] = 0;
                }
                namajenis[z] = tbDetailObatRacikan.getValueAt(i, 6).toString();
                try {
                    stok[z] = Double.parseDouble(tbDetailObatRacikan.getValueAt(i, 7).toString());
                } catch (Exception e) {
                    stok[z] = 0;
                }
                try {
                    kapasitas[z] = Double.parseDouble(tbDetailObatRacikan.getValueAt(i, 8).toString());
                } catch (Exception e) {
                    kapasitas[z] = 0;
                }
                try {
                    kandungan[z] = Double.parseDouble(tbDetailObatRacikan.getValueAt(i, 9).toString());
                } catch (Exception e) {
                    kandungan[z] = 0;
                }
                try {
                    jumlah[z] = Double.parseDouble(tbDetailObatRacikan.getValueAt(i, 10).toString());
                } catch (Exception e) {
                    jumlah[z] = 0;
                }
                try {
                    eb[z] = Double.parseDouble(tbDetailObatRacikan.getValueAt(i, 11).toString());
                } catch (Exception e) {
                    eb[z] = 0;
                }
                try {
                    ts[z] = Double.parseDouble(tbDetailObatRacikan.getValueAt(i, 12).toString());
                } catch (Exception e) {
                    ts[z] = 0;
                }
                industri[z] = tbDetailObatRacikan.getValueAt(i, 13).toString();
                kategori[z] = tbDetailObatRacikan.getValueAt(i, 14).toString();
                golongan[z] = tbDetailObatRacikan.getValueAt(i, 15).toString();
                nobatch[z] = tbDetailObatRacikan.getValueAt(i, 16).toString();
                nofaktur[z] = tbDetailObatRacikan.getValueAt(i, 17).toString();
                kadaluarsa[z] = tbDetailObatRacikan.getValueAt(i, 18).toString();
                z++;
            }
        }

        Valid.tabelKosong(tabModeDetailObatRacikan);

        for (i = 0; i < z; i++) {
            tabModeDetailObatRacikan.addRow(new Object[]{
                no[i], kodebarang[i], namabarang[i], kodesatuan[i], harga[i], beli[i],
                namajenis[i], stok[i], kapasitas[i], kandungan[i], jumlah[i], eb[i],
                ts[i], industri[i], kategori[i], golongan[i], nobatch[i], nofaktur[i], kadaluarsa[i]
            });
        }

        try {
            if (kenaikan > 0) {
                if (aktifkanbatch.equals("yes")) {
                    if (aktifpcare.equals("yes")) {
                        sql = "select data_batch.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(data_batch.h_beli+(data_batch.h_beli*?)) as harga,"
                                + " databarang.letak_barang,industrifarmasi.nama_industri,data_batch.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan,databarang.kapasitas, "
                                + " data_batch.no_batch,data_batch.no_faktur,data_batch.tgl_kadaluarsa,data_batch.sisa "
                                + " from data_batch inner join databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang inner join maping_obat_pcare on maping_obat_pcare.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns "
                                + " and data_batch.kode_brng=databarang.kode_brng and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode ";
                    } else {
                        sql = "select data_batch.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(data_batch.h_beli+(data_batch.h_beli*?)) as harga,"
                                + " databarang.letak_barang,industrifarmasi.nama_industri,data_batch.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan,databarang.kapasitas, "
                                + " data_batch.no_batch,data_batch.no_faktur,data_batch.tgl_kadaluarsa,data_batch.sisa "
                                + " from data_batch inner join databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang on databarang.kdjns=jenis.kdjns "
                                + " and data_batch.kode_brng=databarang.kode_brng and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode ";
                    }
                    psobat = koneksi.prepareStatement(
                            sql + " where data_batch.sisa>0 and databarang.kode_brng like ? or "
                            + " data_batch.sisa>0 and databarang.nama_brng like ? or "
                            + " data_batch.sisa>0 and kategori_barang.nama like ? or "
                            + " data_batch.sisa>0 and golongan_barang.nama like ? or "
                            + " data_batch.sisa>0 and data_batch.no_batch like ? or "
                            + " data_batch.sisa>0 and jenis.nama like ? order by data_batch.tgl_kadaluarsa");
                    try {
                        psobat.setDouble(1, kenaikan);
                        psobat.setString(2, "%" + TCari.getText().trim() + "%");
                        psobat.setString(3, "%" + TCari.getText().trim() + "%");
                        psobat.setString(4, "%" + TCari.getText().trim() + "%");
                        psobat.setString(5, "%" + TCari.getText().trim() + "%");
                        psobat.setString(6, "%" + TCari.getText().trim() + "%");
                        psobat.setString(7, "%" + TCari.getText().trim() + "%");
                        rsobat = psobat.executeQuery();
                        while (rsobat.next()) {
                            tabModeDetailObatRacikan.addRow(new Object[]{
                                tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 0).toString(),
                                rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                rsobat.getString("kode_sat"), Valid.roundUp(rsobat.getDouble("harga"), 100),
                                rsobat.getDouble("h_beli"), rsobat.getString("nama"), rsobat.getDouble("sisa"),
                                rsobat.getDouble("kapasitas"), "", 0, 0, 0, rsobat.getString("nama_industri"),
                                rsobat.getString("kategori"), rsobat.getString("golongan"),
                                rsobat.getString("no_batch"), rsobat.getString("no_faktur"), rsobat.getString("tgl_kadaluarsa")
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (rsobat != null) {
                            rsobat.close();
                        }
                        if (psobat != null) {
                            psobat.close();
                        }
                    }
                } else {
                    if (aktifpcare.equals("yes")) {
                        sql = "select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as harga,"
                                + " databarang.letak_barang,industrifarmasi.nama_industri,databarang.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan,databarang.kapasitas "
                                + " from databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang inner join maping_obat_pcare on maping_obat_pcare.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns "
                                + " and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode ";
                    } else {
                        sql = "select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as harga,"
                                + " databarang.letak_barang,industrifarmasi.nama_industri,databarang.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan,databarang.kapasitas "
                                + " from databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang on databarang.kdjns=jenis.kdjns "
                                + " and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode ";
                    }
                    psobat = koneksi.prepareStatement(
                            sql + " where databarang.status='1' and databarang.kode_brng like ? or "
                            + " databarang.status='1' and databarang.nama_brng like ? or "
                            + " databarang.status='1' and kategori_barang.nama like ? or "
                            + " databarang.status='1' and golongan_barang.nama like ? or "
                            + " databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
                    try {
                        psobat.setDouble(1, kenaikan);
                        psobat.setString(2, "%" + TCari.getText().trim() + "%");
                        psobat.setString(3, "%" + TCari.getText().trim() + "%");
                        psobat.setString(4, "%" + TCari.getText().trim() + "%");
                        psobat.setString(5, "%" + TCari.getText().trim() + "%");
                        psobat.setString(6, "%" + TCari.getText().trim() + "%");
                        rsobat = psobat.executeQuery();
                        while (rsobat.next()) {
                            tabModeDetailObatRacikan.addRow(new Object[]{
                                tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 0).toString(),
                                rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                rsobat.getString("kode_sat"), Valid.roundUp(rsobat.getDouble("harga"), 100),
                                rsobat.getDouble("h_beli"), rsobat.getString("nama"), 0,
                                rsobat.getDouble("kapasitas"), "", 0, 0, 0, rsobat.getString("nama_industri"),
                                rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (rsobat != null) {
                            rsobat.close();
                        }
                        if (psobat != null) {
                            psobat.close();
                        }
                    }
                }
            } else {
                if (aktifkanbatch.equals("yes")) {
                    if (aktifpcare.equals("yes")) {
                        sql = "select data_batch.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,data_batch.kelas1,"
                                + " data_batch.kelas2,data_batch.kelas3,data_batch.utama,data_batch.vip,data_batch.vvip,data_batch.beliluar,data_batch.karyawan,"
                                + " databarang.letak_barang,data_batch.utama,industrifarmasi.nama_industri,data_batch.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan,databarang.kapasitas,  "
                                + " data_batch.no_batch,data_batch.no_faktur,data_batch.tgl_kadaluarsa,data_batch.sisa "
                                + " from data_batch inner join databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang inner join maping_obat_pcare on maping_obat_pcare.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns "
                                + " and data_batch.kode_brng=databarang.kode_brng and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode ";
                    } else {
                        sql = "select data_batch.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,data_batch.kelas1,"
                                + " data_batch.kelas2,data_batch.kelas3,data_batch.utama,data_batch.vip,data_batch.vvip,data_batch.beliluar,data_batch.karyawan,"
                                + " databarang.letak_barang,data_batch.utama,industrifarmasi.nama_industri,data_batch.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan,databarang.kapasitas,  "
                                + " data_batch.no_batch,data_batch.no_faktur,data_batch.tgl_kadaluarsa,data_batch.sisa "
                                + " from data_batch inner join databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang on databarang.kdjns=jenis.kdjns "
                                + " and data_batch.kode_brng=databarang.kode_brng and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode ";
                    }
                    psobat = koneksi.prepareStatement(
                            sql + " where data_batch.sisa>0 and databarang.kode_brng like ? or "
                            + " data_batch.sisa>0 and databarang.nama_brng like ? or "
                            + " data_batch.sisa>0 and kategori_barang.nama like ? or "
                            + " data_batch.sisa>0 and golongan_barang.nama like ? or "
                            + " data_batch.sisa>0 and data_batch.no_batch like ? or "
                            + " data_batch.sisa>0 and jenis.nama like ? order by data_batch.tgl_kadaluarsa");
                    try {
                        psobat.setString(1, "%" + TCari.getText().trim() + "%");
                        psobat.setString(2, "%" + TCari.getText().trim() + "%");
                        psobat.setString(3, "%" + TCari.getText().trim() + "%");
                        psobat.setString(4, "%" + TCari.getText().trim() + "%");
                        psobat.setString(5, "%" + TCari.getText().trim() + "%");
                        psobat.setString(6, "%" + TCari.getText().trim() + "%");
                        rsobat = psobat.executeQuery();
                        while (rsobat.next()) {
                            if (Jeniskelas.getSelectedItem().equals("Kelas 1")) {
                                tabModeDetailObatRacikan.addRow(new Object[]{
                                    tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 0).toString(),
                                    rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), Valid.roundUp(rsobat.getDouble("kelas1"), 100),
                                    rsobat.getDouble("h_beli"), rsobat.getString("nama"), rsobat.getDouble("sisa"),
                                    rsobat.getDouble("kapasitas"), "", 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getString("kategori"), rsobat.getString("golongan"),
                                    rsobat.getString("no_batch"), rsobat.getString("no_faktur"), rsobat.getString("tgl_kadaluarsa")
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Kelas 2")) {
                                tabModeDetailObatRacikan.addRow(new Object[]{
                                    tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 0).toString(),
                                    rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), Valid.roundUp(rsobat.getDouble("kelas2"), 100),
                                    rsobat.getDouble("h_beli"), rsobat.getString("nama"), rsobat.getDouble("sisa"),
                                    rsobat.getDouble("kapasitas"), "", 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getString("kategori"), rsobat.getString("golongan"),
                                    rsobat.getString("no_batch"), rsobat.getString("no_faktur"), rsobat.getString("tgl_kadaluarsa")
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Kelas 3")) {
                                tabModeDetailObatRacikan.addRow(new Object[]{
                                    tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 0).toString(),
                                    rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), Valid.roundUp(rsobat.getDouble("kelas3"), 100),
                                    rsobat.getDouble("h_beli"), rsobat.getString("nama"), rsobat.getDouble("sisa"),
                                    rsobat.getDouble("kapasitas"), "", 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getString("kategori"), rsobat.getString("golongan"),
                                    rsobat.getString("no_batch"), rsobat.getString("no_faktur"), rsobat.getString("tgl_kadaluarsa")
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Utama/BPJS")) {
                                tabModeDetailObatRacikan.addRow(new Object[]{
                                    tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 0).toString(),
                                    rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), Valid.roundUp(rsobat.getDouble("utama"), 100),
                                    rsobat.getDouble("h_beli"), rsobat.getString("nama"), rsobat.getDouble("sisa"),
                                    rsobat.getDouble("kapasitas"), "", 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getString("kategori"), rsobat.getString("golongan"),
                                    rsobat.getString("no_batch"), rsobat.getString("no_faktur"), rsobat.getString("tgl_kadaluarsa")
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("VIP")) {
                                tabModeDetailObatRacikan.addRow(new Object[]{
                                    tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 0).toString(),
                                    rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), Valid.roundUp(rsobat.getDouble("vip"), 100),
                                    rsobat.getDouble("h_beli"), rsobat.getString("nama"), rsobat.getDouble("sisa"),
                                    rsobat.getDouble("kapasitas"), "", 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getString("kategori"), rsobat.getString("golongan"),
                                    rsobat.getString("no_batch"), rsobat.getString("no_faktur"), rsobat.getString("tgl_kadaluarsa")
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("VVIP")) {
                                tabModeDetailObatRacikan.addRow(new Object[]{
                                    tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 0).toString(),
                                    rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), Valid.roundUp(rsobat.getDouble("vvip"), 100),
                                    rsobat.getDouble("h_beli"), rsobat.getString("nama"), rsobat.getDouble("sisa"),
                                    rsobat.getDouble("kapasitas"), "", 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getString("kategori"), rsobat.getString("golongan"),
                                    rsobat.getString("no_batch"), rsobat.getString("no_faktur"), rsobat.getString("tgl_kadaluarsa")
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Beli Luar")) {
                                tabModeDetailObatRacikan.addRow(new Object[]{
                                    tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 0).toString(),
                                    rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), Valid.roundUp(rsobat.getDouble("beliluar"), 100),
                                    rsobat.getDouble("h_beli"), rsobat.getString("nama"), rsobat.getDouble("sisa"),
                                    rsobat.getDouble("kapasitas"), "", 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getString("kategori"), rsobat.getString("golongan"),
                                    rsobat.getString("no_batch"), rsobat.getString("no_faktur"), rsobat.getString("tgl_kadaluarsa")
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Karyawan")) {
                                tabModeDetailObatRacikan.addRow(new Object[]{
                                    tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 0).toString(),
                                    rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), Valid.roundUp(rsobat.getDouble("karyawan"), 100),
                                    rsobat.getDouble("h_beli"), rsobat.getString("nama"), rsobat.getDouble("sisa"),
                                    rsobat.getDouble("kapasitas"), "", 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getString("kategori"), rsobat.getString("golongan"),
                                    rsobat.getString("no_batch"), rsobat.getString("no_faktur"), rsobat.getString("tgl_kadaluarsa")
                                });
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (rsobat != null) {
                            rsobat.close();
                        }
                        if (psobat != null) {
                            psobat.close();
                        }
                    }
                } else {
                    if (aktifpcare.equals("yes")) {
                        sql = "select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,databarang.kelas1,"
                                + " databarang.kelas2,databarang.kelas3,databarang.utama,databarang.vip,databarang.vvip,databarang.beliluar,databarang.karyawan,"
                                + " databarang.letak_barang,databarang.utama,industrifarmasi.nama_industri,databarang.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan,databarang.kapasitas  "
                                + " from databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang inner join maping_obat_pcare on maping_obat_pcare.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns "
                                + " and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode ";
                    } else {
                        sql = "select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,databarang.kelas1,"
                                + " databarang.kelas2,databarang.kelas3,databarang.utama,databarang.vip,databarang.vvip,databarang.beliluar,databarang.karyawan,"
                                + " databarang.letak_barang,databarang.utama,industrifarmasi.nama_industri,databarang.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan,databarang.kapasitas  "
                                + " from databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang on databarang.kdjns=jenis.kdjns "
                                + " and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode ";
                    }
                    psobat = koneksi.prepareStatement(
                            sql + " where databarang.status='1' and databarang.kode_brng like ? or "
                            + " databarang.status='1' and databarang.nama_brng like ? or "
                            + " databarang.status='1' and kategori_barang.nama like ? or "
                            + " databarang.status='1' and golongan_barang.nama like ? or "
                            + " databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
                    try {
                        psobat.setString(1, "%" + TCari.getText().trim() + "%");
                        psobat.setString(2, "%" + TCari.getText().trim() + "%");
                        psobat.setString(3, "%" + TCari.getText().trim() + "%");
                        psobat.setString(4, "%" + TCari.getText().trim() + "%");
                        psobat.setString(5, "%" + TCari.getText().trim() + "%");
                        rsobat = psobat.executeQuery();
                        while (rsobat.next()) {
                            if (Jeniskelas.getSelectedItem().equals("Kelas 1")) {
                                tabModeDetailObatRacikan.addRow(new Object[]{
                                    tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 0).toString(),
                                    rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), Valid.roundUp(rsobat.getDouble("kelas1"), 100),
                                    rsobat.getDouble("h_beli"), rsobat.getString("nama"), 0,
                                    rsobat.getDouble("kapasitas"), "", 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Kelas 2")) {
                                tabModeDetailObatRacikan.addRow(new Object[]{
                                    tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 0).toString(),
                                    rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), Valid.roundUp(rsobat.getDouble("kelas2"), 100),
                                    rsobat.getDouble("h_beli"), rsobat.getString("nama"), 0,
                                    rsobat.getDouble("kapasitas"), "", 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Kelas 3")) {
                                tabModeDetailObatRacikan.addRow(new Object[]{
                                    tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 0).toString(),
                                    rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), Valid.roundUp(rsobat.getDouble("kelas3"), 100),
                                    rsobat.getDouble("h_beli"), rsobat.getString("nama"), 0,
                                    rsobat.getDouble("kapasitas"), "", 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Utama/BPJS")) {
                                tabModeDetailObatRacikan.addRow(new Object[]{
                                    tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 0).toString(),
                                    rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), Valid.roundUp(rsobat.getDouble("utama"), 100),
                                    rsobat.getDouble("h_beli"), rsobat.getString("nama"), 0,
                                    rsobat.getDouble("kapasitas"), "", 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("VIP")) {
                                tabModeDetailObatRacikan.addRow(new Object[]{
                                    tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 0).toString(),
                                    rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), Valid.roundUp(rsobat.getDouble("vip"), 100),
                                    rsobat.getDouble("h_beli"), rsobat.getString("nama"), 0,
                                    rsobat.getDouble("kapasitas"), "", 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("VVIP")) {
                                tabModeDetailObatRacikan.addRow(new Object[]{
                                    tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 0).toString(),
                                    rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), Valid.roundUp(rsobat.getDouble("vvip"), 100),
                                    rsobat.getDouble("h_beli"), rsobat.getString("nama"), 0,
                                    rsobat.getDouble("kapasitas"), "", 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Beli Luar")) {
                                tabModeDetailObatRacikan.addRow(new Object[]{
                                    tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 0).toString(),
                                    rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), Valid.roundUp(rsobat.getDouble("beliluar"), 100),
                                    rsobat.getDouble("h_beli"), rsobat.getString("nama"), 0,
                                    rsobat.getDouble("kapasitas"), "", 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Karyawan")) {
                                tabModeDetailObatRacikan.addRow(new Object[]{
                                    tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(), 0).toString(),
                                    rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), Valid.roundUp(rsobat.getDouble("karyawan"), 100),
                                    rsobat.getDouble("h_beli"), rsobat.getString("nama"), 0,
                                    rsobat.getDouble("kapasitas"), "", 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (rsobat != null) {
                            rsobat.close();
                        }
                        if (psobat != null) {
                            psobat.close();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    public void tampilobat2(String no_resep) {
        this.noresep = no_resep;
        try {
            Valid.tabelKosong(tabMode);
            Valid.tabelKosong(tabModeObatRacikan);
            Valid.tabelKosong(tabModeDetailObatRacikan);
            if (kenaikan > 0) {
                if (aktifkanbatch.equals("yes")) {
                    psobat = koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as harga,"
                            + " databarang.letak_barang,industrifarmasi.nama_industri,databarang.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan,"
                            + " resep_dokter.jml, resep_dokter.aturan_pakai from databarang inner join jenis inner join industrifarmasi inner join golongan_barang "
                            + " inner join kategori_barang inner join resep_dokter on databarang.kdjns=jenis.kdjns "
                            + " and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode "
                            + " and resep_dokter.kode_brng=databarang.kode_brng where "
                            + " resep_dokter.no_resep=? and databarang.status='1' and databarang.kode_brng like ? or "
                            + " resep_dokter.no_resep=? and databarang.status='1' and databarang.nama_brng like ? or "
                            + " resep_dokter.no_resep=? and databarang.status='1' and kategori_barang.nama like ? or "
                            + " resep_dokter.no_resep=? and databarang.status='1' and golongan_barang.nama like ? or "
                            + " resep_dokter.no_resep=? and databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
                    try {
                        psobat.setDouble(1, kenaikan);
                        psobat.setString(2, no_resep);
                        psobat.setString(3, "%" + TCari.getText().trim() + "%");
                        psobat.setString(4, no_resep);
                        psobat.setString(5, "%" + TCari.getText().trim() + "%");
                        psobat.setString(6, no_resep);
                        psobat.setString(7, "%" + TCari.getText().trim() + "%");
                        psobat.setString(8, no_resep);
                        psobat.setString(9, "%" + TCari.getText().trim() + "%");
                        psobat.setString(10, no_resep);
                        psobat.setString(11, "%" + TCari.getText().trim() + "%");
                        rsobat = psobat.executeQuery();
                        while (rsobat.next()) {
                            no_batchcari = "";
                            tgl_kadaluarsacari = "";
                            no_fakturcari = "";
                            h_belicari = 0;
                            hargacari = 0;
                            sisacari = 0;
                            psbatch = koneksi.prepareStatement(
                                    "select no_batch, kode_brng, tgl_beli, tgl_kadaluarsa, asal, no_faktur, "
                                    + "h_beli,(h_beli+(h_beli*?)) as harga, sisa from data_batch where "
                                    + "sisa>0 and kode_brng=? order by tgl_kadaluarsa desc limit 1");
                            try {
                                psbatch.setDouble(1, kenaikan);
                                psbatch.setString(2, rsobat.getString("kode_brng"));
                                rsbatch = psbatch.executeQuery();
                                while (rsbatch.next()) {
                                    no_batchcari = rsbatch.getString("no_batch");
                                    tgl_kadaluarsacari = rsbatch.getString("tgl_kadaluarsa");
                                    no_fakturcari = rsbatch.getString("no_faktur");
                                    h_belicari = rsbatch.getDouble("h_beli");
                                    hargacari = rsbatch.getDouble("harga");
                                    sisacari = rsbatch.getDouble("sisa");
                                }
                            } catch (Exception e) {
                                System.out.println("Notif : " + e);
                            } finally {
                                if (rsbatch != null) {
                                    rsbatch.close();
                                }
                                if (psbatch != null) {
                                    psbatch.close();
                                }
                            }
                            tabMode.addRow(new Object[]{false, rsobat.getString("jml"), rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(hargacari, 100),
                                rsobat.getString("nama"), 0, 0, sisacari, rsobat.getString("nama_industri"),
                                h_belicari, rsobat.getString("aturan_pakai"), rsobat.getString("kategori"), rsobat.getString("golongan"),
                                no_batchcari, no_fakturcari, tgl_kadaluarsacari
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (rsobat != null) {
                            rsobat.close();
                        }
                        if (psobat != null) {
                            psobat.close();
                        }
                    }
                } else {
                    psobat = koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as harga,"
                            + " databarang.letak_barang,industrifarmasi.nama_industri,databarang.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan,"
                            + " resep_pulang.jml_barang, resep_dokter.aturan_pakai from databarang inner join jenis inner join industrifarmasi inner join golongan_barang "
                            + " inner join kategori_barang inner join resep_pulang on databarang.kdjns=jenis.kdjns "
                            + " and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode "
                            + " and resep_pulang.kode_brng=databarang.kode_brng where "
                            + " resep_pulang.no_resep=? and databarang.status='1' and ( databarang.kode_brng like ? or "
                            + " databarang.nama_brng like ? or "
                            + " kategori_barang.nama like ? or "
                            + " golongan_barang.nama like ? or "
                            + " jenis.nama like ? ) order by databarang.nama_brng");
                    try {
                        psobat.setDouble(1, kenaikan);
                        psobat.setString(2, no_resep);
                        psobat.setString(3, "%" + TCari.getText().trim() + "%");
                        psobat.setString(4, "%" + TCari.getText().trim() + "%");
                        psobat.setString(5, "%" + TCari.getText().trim() + "%");
                        psobat.setString(6, "%" + TCari.getText().trim() + "%");
                        psobat.setString(7, "%" + TCari.getText().trim() + "%");
                        rsobat = psobat.executeQuery();
                        while (rsobat.next()) {
//                            budcari = "";
//                            psbud = koneksi.prepareStatement(
//                                    "select no_resep, kode_brng, bud from obat_bud where no_resep=? and kode_brng=?");
//                            try {
//                                psbud.setString(1, no_resep);
//                                psbud.setString(2, rsobat.getString("kode_brng"));
//                                rsbud = psbud.executeQuery();
//                                while (rsbud.next()) {
//                                    budcari = rsbud.getString("bud");
//                                }
//                            } catch (Exception e) {
//                                System.out.println("Notif : " + e);
//                            } finally {
//                                if (rsbud != null) {
//                                    rsbud.close();
//                                }
//                                if (psbud != null) {
//                                    psbud.close();
//                                }
//                            }
                            tabMode.addRow(new Object[]{false, rsobat.getString("jml_barang"), rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("harga"), 100),
                                rsobat.getString("nama"), 0, 0, 0, rsobat.getString("nama_industri"),
                                rsobat.getDouble("h_beli"), rsobat.getString("aturan_pakai"), rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (rsobat != null) {
                            rsobat.close();
                        }
                        if (psobat != null) {
                            psobat.close();
                        }
                    }
                }
            } else {
                if (aktifkanbatch.equals("yes")) {
                    psobat = koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,databarang.kelas1,"
                            + " databarang.kelas2,databarang.kelas3,databarang.utama,databarang.vip,databarang.vvip,databarang.beliluar,databarang.karyawan,"
                            + " databarang.letak_barang,industrifarmasi.nama_industri,databarang.h_beli,kategori_barang.nama as kategori,"
                            + " golongan_barang.nama as golongan,resep_dokter.jml,resep_dokter.aturan_pakai "
                            + " from databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang inner join resep_dokter "
                            + " on databarang.kdjns=jenis.kdjns and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode "
                            + " and databarang.kode_kategori=kategori_barang.kode and resep_dokter.kode_brng=databarang.kode_brng "
                            + " where resep_dokter.no_resep=? and databarang.status='1' and databarang.kode_brng like ? or "
                            + " resep_dokter.no_resep=? and databarang.status='1' and databarang.nama_brng like ? or "
                            + " resep_dokter.no_resep=? and databarang.status='1' and kategori_barang.nama like ? or "
                            + " resep_dokter.no_resep=? and databarang.status='1' and golongan_barang.nama like ? or "
                            + " resep_dokter.no_resep=? and databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
                    try {
                        psobat.setString(1, no_resep);
                        psobat.setString(2, "%" + TCari.getText().trim() + "%");
                        psobat.setString(3, no_resep);
                        psobat.setString(4, "%" + TCari.getText().trim() + "%");
                        psobat.setString(5, no_resep);
                        psobat.setString(6, "%" + TCari.getText().trim() + "%");
                        psobat.setString(7, no_resep);
                        psobat.setString(8, "%" + TCari.getText().trim() + "%");
                        psobat.setString(9, no_resep);
                        psobat.setString(10, "%" + TCari.getText().trim() + "%");
                        rsobat = psobat.executeQuery();
                        while (rsobat.next()) {
                            no_batchcari = "";
                            tgl_kadaluarsacari = "";
                            no_fakturcari = "";
                            h_belicari = 0;
                            hargacari = 0;
                            sisacari = 0;
                            psbatch = koneksi.prepareStatement(
                                    "select no_batch, kode_brng, tgl_beli, tgl_kadaluarsa, asal, no_faktur, "
                                    + "h_beli, ralan, kelas1, kelas2, kelas3, utama, vip, vvip, beliluar, "
                                    + "jualbebas, karyawan, jumlahbeli, sisa from data_batch where sisa>0 and kode_brng=? order by tgl_kadaluarsa desc limit 1");
                            try {
                                psbatch.setString(1, rsobat.getString("kode_brng"));
                                rsbatch = psbatch.executeQuery();
                                while (rsbatch.next()) {
                                    no_batchcari = rsbatch.getString("no_batch");
                                    tgl_kadaluarsacari = rsbatch.getString("tgl_kadaluarsa");
                                    no_fakturcari = rsbatch.getString("no_faktur");
                                    h_belicari = rsbatch.getDouble("h_beli");
                                    if (Jeniskelas.getSelectedItem().equals("Kelas 1")) {
                                        hargacari = rsbatch.getDouble("kelas1");
                                    } else if (Jeniskelas.getSelectedItem().equals("Kelas 2")) {
                                        hargacari = rsbatch.getDouble("kelas2");
                                    } else if (Jeniskelas.getSelectedItem().equals("Kelas 3")) {
                                        hargacari = rsbatch.getDouble("kelas3");
                                    } else if (Jeniskelas.getSelectedItem().equals("Utama/BPJS")) {
                                        hargacari = rsbatch.getDouble("utama");
                                    } else if (Jeniskelas.getSelectedItem().equals("VIP")) {
                                        hargacari = rsbatch.getDouble("vip");
                                    } else if (Jeniskelas.getSelectedItem().equals("VVIP")) {
                                        hargacari = rsbatch.getDouble("vvip");
                                    } else if (Jeniskelas.getSelectedItem().equals("Beli Luar")) {
                                        hargacari = rsbatch.getDouble("beliluar");
                                    } else if (Jeniskelas.getSelectedItem().equals("Karyawan")) {
                                        hargacari = rsbatch.getDouble("karyawan");
                                    }
                                    sisacari = rsbatch.getDouble("sisa");
                                }
                            } catch (Exception e) {
                                System.out.println("Notif : " + e);
                            } finally {
                                if (rsbatch != null) {
                                    rsbatch.close();
                                }
                                if (psbatch != null) {
                                    psbatch.close();
                                }
                            }
                            tabMode.addRow(new Object[]{
                                false, rsobat.getString("jml"), rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(hargacari, 100),
                                rsobat.getString("nama"), 0, 0, sisacari, rsobat.getString("nama_industri"),
                                h_belicari, rsobat.getString("aturan_pakai"), rsobat.getString("kategori"), rsobat.getString("golongan"),
                                no_batchcari, no_fakturcari, tgl_kadaluarsacari
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (rsobat != null) {
                            rsobat.close();
                        }
                        if (psobat != null) {
                            psobat.close();
                        }
                    }
                } else {
                    psobat = koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,databarang.kelas1,"
                            + " databarang.kelas2,databarang.kelas3,databarang.utama,databarang.vip,databarang.vvip,databarang.beliluar,databarang.karyawan,"
                            + " databarang.letak_barang,industrifarmasi.nama_industri,databarang.h_beli,kategori_barang.nama as kategori,"
                            + " golongan_barang.nama as golongan,resep_pulang.jml_barang,resep_pulang.dosis "
                            + " from databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang inner join resep_pulang "
                            + " on databarang.kdjns=jenis.kdjns and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode "
                            + " and databarang.kode_kategori=kategori_barang.kode and resep_pulang.kode_brng=databarang.kode_brng "
                            + " where resep_pulang.no_resep=? and databarang.status='1' and ( databarang.kode_brng like ? or "
                            + " databarang.nama_brng like ? or "
                            + " kategori_barang.nama like ? or "
                            + " golongan_barang.nama like ? or "
                            + " jenis.nama like ? ) order by databarang.nama_brng");
                    try {
                        psobat.setString(1, no_resep);
                        psobat.setString(2, "%" + TCari.getText().trim() + "%");
                        psobat.setString(3, "%" + TCari.getText().trim() + "%");
                        psobat.setString(4, "%" + TCari.getText().trim() + "%");
                        psobat.setString(5, "%" + TCari.getText().trim() + "%");
                        psobat.setString(6, "%" + TCari.getText().trim() + "%");
                        rsobat = psobat.executeQuery();
                        while (rsobat.next()) {
//                            budcari = "";
//                            psbud = koneksi.prepareStatement(
//                                    "select no_resep, kode_brng, bud from obat_bud where no_resep=? and kode_brng=?");
//                            try {
//                                psbud.setString(1, no_resep);
//                                psbud.setString(2, rsobat.getString("kode_brng"));
//                                rsbud = psbud.executeQuery();
//                                while (rsbud.next()) {
//                                    budcari = rsbud.getString("bud");
//                                }
//                            } catch (Exception e) {
//                                System.out.println("Notif : " + e);
//                            } finally {
//                                if (rsbud != null) {
//                                    rsbud.close();
//                                }
//                                if (psbud != null) {
//                                    psbud.close();
//                                }
//                            }
                            if (Jeniskelas.getSelectedItem().equals("Kelas 1")) {
                                tabMode.addRow(new Object[]{false, rsobat.getString("jml_barang"), rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("kelas1"), 100),
                                    rsobat.getString("nama"), 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), rsobat.getString("dosis"), rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Kelas 2")) {
                                tabMode.addRow(new Object[]{false, rsobat.getString("jml_barang"), rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("kelas2"), 100),
                                    rsobat.getString("nama"), 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), rsobat.getString("dosis"), rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Kelas 3")) {
                                tabMode.addRow(new Object[]{false, rsobat.getString("jml_barang"), rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("kelas3"), 100),
                                    rsobat.getString("nama"), 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), rsobat.getString("dosis"), rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Utama/BPJS")) {
                                tabMode.addRow(new Object[]{false, rsobat.getString("jml_barang"), rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("utama"), 100),
                                    rsobat.getString("nama"), 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), rsobat.getString("dosis"), rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("VIP")) {
                                tabMode.addRow(new Object[]{false, rsobat.getString("jml_barang"), rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("vip"), 100),
                                    rsobat.getString("nama"), 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), rsobat.getString("dosis"), rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("VVIP")) {
                                tabMode.addRow(new Object[]{false, rsobat.getString("jml_barang"), rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("vvip"), 100),
                                    rsobat.getString("nama"), 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), rsobat.getString("dosis"), rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Beli Luar")) {
                                tabMode.addRow(new Object[]{false, rsobat.getString("jml_barang"), rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("beliluar"), 100),
                                    rsobat.getString("nama"), 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), rsobat.getString("dosis"), rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            } else if (Jeniskelas.getSelectedItem().equals("Karyawan")) {
                                tabMode.addRow(new Object[]{false, rsobat.getString("jml_barang"), rsobat.getString("kode_brng"), rsobat.getString("nama_brng"),
                                    rsobat.getString("kode_sat"), rsobat.getString("letak_barang"), Valid.roundUp(rsobat.getDouble("karyawan"), 100),
                                    rsobat.getString("nama"), 0, 0, 0, rsobat.getString("nama_industri"),
                                    rsobat.getDouble("h_beli"), rsobat.getString("dosis"), rsobat.getString("kategori"), rsobat.getString("golongan"), "", "", ""
                                });
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (rsobat != null) {
                            rsobat.close();
                        }
                        if (psobat != null) {
                            psobat.close();
                        }
                    }
                }
            }
            for (i = 0; i < tbObat.getRowCount(); i++) {
                getDataobat(i);
            }
            psobat = koneksi.prepareStatement(
                    "select resep_dokter_racikan.no_racik,resep_dokter_racikan.nama_racik,"
                    + "resep_dokter_racikan.kd_racik,metode_racik.nm_racik as metode,"
                    + "resep_dokter_racikan.jml_dr,resep_dokter_racikan.aturan_pakai,"
                    + "resep_dokter_racikan.keterangan from resep_dokter_racikan inner join metode_racik "
                    + "on resep_dokter_racikan.kd_racik=metode_racik.kd_racik where "
                    + "resep_dokter_racikan.no_resep=? ");
            try {
                psobat.setString(1, no_resep);
                rsobat = psobat.executeQuery();
                while (rsobat.next()) {
                    tabModeObatRacikan.addRow(new String[]{
                        rsobat.getString("no_racik"), rsobat.getString("nama_racik"), rsobat.getString("kd_racik"),
                        rsobat.getString("metode"), rsobat.getString("jml_dr"), rsobat.getString("aturan_pakai"),
                        rsobat.getString("keterangan")
                    });
                    if (kenaikan > 0) {
                        if (aktifkanbatch.equals("yes")) {
                            ps2 = koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter_racikan_detail.jml,resep_dokter_racikan_detail.kandungan,"
                                    + "databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as harga,databarang.letak_barang,"
                                    + "databarang.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan,"
                                    + "industrifarmasi.nama_industri,jenis.nama as jenis,databarang.kapasitas from resep_dokter_racikan_detail inner join databarang inner join jenis "
                                    + "inner join golongan_barang inner join kategori_barang inner join industrifarmasi on "
                                    + "resep_dokter_racikan_detail.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns "
                                    + "and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode "
                                    + "and databarang.kode_kategori=kategori_barang.kode where resep_dokter_racikan_detail.no_resep=? and "
                                    + "resep_dokter_racikan_detail.no_racik=? order by databarang.kode_brng");
                            try {
                                ps2.setDouble(1, kenaikan);
                                ps2.setString(2, no_resep);
                                ps2.setString(3, rsobat.getString("no_racik"));
                                rs2 = ps2.executeQuery();
                                while (rs2.next()) {
                                    no_batchcari = "";
                                    tgl_kadaluarsacari = "";
                                    no_fakturcari = "";
                                    h_belicari = 0;
                                    hargacari = 0;
                                    sisacari = 0;
                                    psbatch = koneksi.prepareStatement(
                                            "select no_batch, kode_brng, tgl_beli, tgl_kadaluarsa, asal, no_faktur, "
                                            + "h_beli,(h_beli+(h_beli*?)) as harga, sisa from data_batch where "
                                            + "sisa>0 and kode_brng=? order by tgl_kadaluarsa desc limit 1");
                                    try {
                                        psbatch.setDouble(1, kenaikan);
                                        psbatch.setString(2, rs2.getString("kode_brng"));
                                        rsbatch = psbatch.executeQuery();
                                        while (rsbatch.next()) {
                                            no_batchcari = rsbatch.getString("no_batch");
                                            tgl_kadaluarsacari = rsbatch.getString("tgl_kadaluarsa");
                                            no_fakturcari = rsbatch.getString("no_faktur");
                                            h_belicari = rsbatch.getDouble("h_beli");
                                            hargacari = rsbatch.getDouble("harga");
                                            sisacari = rsbatch.getDouble("sisa");
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif : " + e);
                                    } finally {
                                        if (rsbatch != null) {
                                            rsbatch.close();
                                        }
                                        if (psbatch != null) {
                                            psbatch.close();
                                        }
                                    }
                                    tabModeDetailObatRacikan.addRow(new Object[]{
                                        rsobat.getString("no_racik"), rs2.getString("kode_brng"), rs2.getString("nama_brng"),
                                        rs2.getString("kode_sat"), hargacari, h_belicari,
                                        rs2.getString("jenis"), sisacari, rs2.getDouble("kapasitas"), rs2.getDouble("kandungan"),
                                        rs2.getDouble("jml"), 0, 0, rs2.getString("nama_industri"), rs2.getString("kategori"),
                                        rs2.getString("golongan"), no_batchcari, no_fakturcari, tgl_kadaluarsacari
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi 3 : " + e);
                            } finally {
                                if (rs2 != null) {
                                    rs2.close();
                                }
                                if (ps2 != null) {
                                    ps2.close();
                                }
                            }
                        } else {
                            ps2 = koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter_racikan_detail.jml,resep_dokter_racikan_detail.kandungan,"
                                    + "databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as harga,databarang.letak_barang,"
                                    + "databarang.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan,"
                                    + "industrifarmasi.nama_industri,jenis.nama as jenis,databarang.kapasitas from resep_dokter_racikan_detail inner join databarang inner join jenis "
                                    + "inner join golongan_barang inner join kategori_barang inner join industrifarmasi on "
                                    + "resep_dokter_racikan_detail.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns "
                                    + "and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode "
                                    + "and databarang.kode_kategori=kategori_barang.kode where resep_dokter_racikan_detail.no_resep=? and "
                                    + "resep_dokter_racikan_detail.no_racik=? order by databarang.kode_brng");
                            try {
                                ps2.setDouble(1, kenaikan);
                                ps2.setString(2, no_resep);
                                ps2.setString(3, rsobat.getString("no_racik"));
                                rs2 = ps2.executeQuery();
                                while (rs2.next()) {
                                    tabModeDetailObatRacikan.addRow(new Object[]{
                                        rsobat.getString("no_racik"), rs2.getString("kode_brng"), rs2.getString("nama_brng"),
                                        rs2.getString("kode_sat"), rs2.getDouble("harga"), rs2.getDouble("h_beli"),
                                        rs2.getString("jenis"), 0, rs2.getDouble("kapasitas"), rs2.getDouble("kandungan"),
                                        rs2.getDouble("jml"), 0, 0, rs2.getString("nama_industri"), rs2.getString("kategori"),
                                        rs2.getString("golongan"), "", "", ""
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi 3 : " + e);
                            } finally {
                                if (rs2 != null) {
                                    rs2.close();
                                }
                                if (ps2 != null) {
                                    ps2.close();
                                }
                            }
                        }
                    } else {
                        if (aktifkanbatch.equals("yes")) {
                            ps2 = koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter_racikan_detail.jml,resep_dokter_racikan_detail.kandungan,"
                                    + "databarang.kode_sat,databarang.kelas1,databarang.kelas2,databarang.kelas3,databarang.utama,databarang.vip,databarang.vvip,databarang.beliluar,"
                                    + "databarang.karyawan,databarang.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan,"
                                    + "industrifarmasi.nama_industri,jenis.nama as jenis,databarang.kapasitas from resep_dokter_racikan_detail inner join databarang inner join jenis "
                                    + "inner join golongan_barang inner join kategori_barang inner join industrifarmasi on "
                                    + "resep_dokter_racikan_detail.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns "
                                    + "and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode "
                                    + "and databarang.kode_kategori=kategori_barang.kode where resep_dokter_racikan_detail.no_resep=? and "
                                    + "resep_dokter_racikan_detail.no_racik=? order by databarang.kode_brng");
                            try {
                                ps2.setString(1, no_resep);
                                ps2.setString(2, rsobat.getString("no_racik"));
                                rs2 = ps2.executeQuery();
                                while (rs2.next()) {
                                    no_batchcari = "";
                                    tgl_kadaluarsacari = "";
                                    no_fakturcari = "";
                                    h_belicari = 0;
                                    hargacari = 0;
                                    sisacari = 0;
                                    psbatch = koneksi.prepareStatement(
                                            "select no_batch, kode_brng, tgl_beli, tgl_kadaluarsa, asal, no_faktur, "
                                            + "h_beli, ralan, kelas1, kelas2, kelas3, utama, vip, vvip, beliluar, "
                                            + "jualbebas, karyawan, jumlahbeli, sisa from data_batch where sisa>0 and kode_brng=? order by tgl_kadaluarsa desc limit 1");
                                    try {
                                        psbatch.setString(1, rs2.getString("kode_brng"));
                                        rsbatch = psbatch.executeQuery();
                                        while (rsbatch.next()) {
                                            no_batchcari = rsbatch.getString("no_batch");
                                            tgl_kadaluarsacari = rsbatch.getString("tgl_kadaluarsa");
                                            no_fakturcari = rsbatch.getString("no_faktur");
                                            h_belicari = rsbatch.getDouble("h_beli");
                                            if (Jeniskelas.getSelectedItem().equals("Kelas 1")) {
                                                hargacari = rsbatch.getDouble("kelas1");
                                            } else if (Jeniskelas.getSelectedItem().equals("Kelas 2")) {
                                                hargacari = rsbatch.getDouble("kelas2");
                                            } else if (Jeniskelas.getSelectedItem().equals("Kelas 3")) {
                                                hargacari = rsbatch.getDouble("kelas3");
                                            } else if (Jeniskelas.getSelectedItem().equals("Utama/BPJS")) {
                                                hargacari = rsbatch.getDouble("utama");
                                            } else if (Jeniskelas.getSelectedItem().equals("VIP")) {
                                                hargacari = rsbatch.getDouble("vip");
                                            } else if (Jeniskelas.getSelectedItem().equals("VVIP")) {
                                                hargacari = rsbatch.getDouble("vvip");
                                            } else if (Jeniskelas.getSelectedItem().equals("Beli Luar")) {
                                                hargacari = rsbatch.getDouble("beliluar");
                                            } else if (Jeniskelas.getSelectedItem().equals("Karyawan")) {
                                                hargacari = rsbatch.getDouble("karyawan");
                                            }
                                            sisacari = rsbatch.getDouble("sisa");
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif : " + e);
                                    } finally {
                                        if (rsbatch != null) {
                                            rsbatch.close();
                                        }
                                        if (psbatch != null) {
                                            psbatch.close();
                                        }
                                    }
                                    tabModeDetailObatRacikan.addRow(new Object[]{
                                        rsobat.getString("no_racik"), rs2.getString("kode_brng"), rs2.getString("nama_brng"),
                                        rs2.getString("kode_sat"), hargacari, h_belicari,
                                        rs2.getString("jenis"), sisacari, rs2.getDouble("kapasitas"), rs2.getDouble("kandungan"),
                                        rs2.getDouble("jml"), 0, 0, rs2.getString("nama_industri"), rs2.getString("kategori"),
                                        rs2.getString("golongan"), no_batchcari, no_fakturcari, tgl_kadaluarsacari
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi 3 : " + e);
                            } finally {
                                if (rs2 != null) {
                                    rs2.close();
                                }
                                if (ps2 != null) {
                                    ps2.close();
                                }
                            }
                        } else {
                            ps2 = koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter_racikan_detail.jml,resep_dokter_racikan_detail.kandungan,"
                                    + "databarang.kode_sat,databarang.kelas1,databarang.kelas2,databarang.kelas3,databarang.utama,databarang.vip,databarang.vvip,databarang.beliluar,"
                                    + "databarang.karyawan,databarang.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan,"
                                    + "industrifarmasi.nama_industri,jenis.nama as jenis,databarang.kapasitas from resep_dokter_racikan_detail inner join databarang inner join jenis "
                                    + "inner join golongan_barang inner join kategori_barang inner join industrifarmasi on "
                                    + "resep_dokter_racikan_detail.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns "
                                    + "and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode "
                                    + "and databarang.kode_kategori=kategori_barang.kode where resep_dokter_racikan_detail.no_resep=? and "
                                    + "resep_dokter_racikan_detail.no_racik=? order by databarang.kode_brng");
                            try {
                                ps2.setString(1, no_resep);
                                ps2.setString(2, rsobat.getString("no_racik"));
                                rs2 = ps2.executeQuery();
                                while (rs2.next()) {
                                    if (Jeniskelas.getSelectedItem().equals("Kelas 1")) {
                                        tabModeDetailObatRacikan.addRow(new Object[]{
                                            rsobat.getString("no_racik"), rs2.getString("kode_brng"), rs2.getString("nama_brng"),
                                            rs2.getString("kode_sat"), rs2.getDouble("kelas1"), rs2.getDouble("h_beli"),
                                            rs2.getString("jenis"), 0, rs2.getDouble("kapasitas"), rs2.getDouble("kandungan"),
                                            rs2.getDouble("jml"), 0, 0, rs2.getString("nama_industri"), rs2.getString("kategori"),
                                            rs2.getString("golongan"), "", "", ""
                                        });
                                    } else if (Jeniskelas.getSelectedItem().equals("Kelas 2")) {
                                        tabModeDetailObatRacikan.addRow(new Object[]{
                                            rsobat.getString("no_racik"), rs2.getString("kode_brng"), rs2.getString("nama_brng"),
                                            rs2.getString("kode_sat"), rs2.getDouble("kelas2"), rs2.getDouble("h_beli"),
                                            rs2.getString("jenis"), 0, rs2.getDouble("kapasitas"), rs2.getDouble("kandungan"),
                                            rs2.getDouble("jml"), 0, 0, rs2.getString("nama_industri"), rs2.getString("kategori"),
                                            rs2.getString("golongan"), "", "", ""
                                        });
                                    } else if (Jeniskelas.getSelectedItem().equals("Kelas 3")) {
                                        tabModeDetailObatRacikan.addRow(new Object[]{
                                            rsobat.getString("no_racik"), rs2.getString("kode_brng"), rs2.getString("nama_brng"),
                                            rs2.getString("kode_sat"), rs2.getDouble("kelas3"), rs2.getDouble("h_beli"),
                                            rs2.getString("jenis"), 0, rs2.getDouble("kapasitas"), rs2.getDouble("kandungan"),
                                            rs2.getDouble("jml"), 0, 0, rs2.getString("nama_industri"), rs2.getString("kategori"),
                                            rs2.getString("golongan"), "", "", ""
                                        });
                                    } else if (Jeniskelas.getSelectedItem().equals("Utama/BPJS")) {
                                        tabModeDetailObatRacikan.addRow(new Object[]{
                                            rsobat.getString("no_racik"), rs2.getString("kode_brng"), rs2.getString("nama_brng"),
                                            rs2.getString("kode_sat"), rs2.getDouble("utama"), rs2.getDouble("h_beli"),
                                            rs2.getString("jenis"), 0, rs2.getDouble("kapasitas"), rs2.getDouble("kandungan"),
                                            rs2.getDouble("jml"), 0, 0, rs2.getString("nama_industri"), rs2.getString("kategori"),
                                            rs2.getString("golongan"), "", "", ""
                                        });
                                    } else if (Jeniskelas.getSelectedItem().equals("VIP")) {
                                        tabModeDetailObatRacikan.addRow(new Object[]{
                                            rsobat.getString("no_racik"), rs2.getString("kode_brng"), rs2.getString("nama_brng"),
                                            rs2.getString("kode_sat"), rs2.getDouble("vip"), rs2.getDouble("h_beli"),
                                            rs2.getString("jenis"), 0, rs2.getDouble("kapasitas"), rs2.getDouble("kandungan"),
                                            rs2.getDouble("jml"), 0, 0, rs2.getString("nama_industri"), rs2.getString("kategori"),
                                            rs2.getString("golongan"), "", "", ""
                                        });
                                    } else if (Jeniskelas.getSelectedItem().equals("VVIP")) {
                                        tabModeDetailObatRacikan.addRow(new Object[]{
                                            rsobat.getString("no_racik"), rs2.getString("kode_brng"), rs2.getString("nama_brng"),
                                            rs2.getString("kode_sat"), rs2.getDouble("vvip"), rs2.getDouble("h_beli"),
                                            rs2.getString("jenis"), 0, rs2.getDouble("kapasitas"), rs2.getDouble("kandungan"),
                                            rs2.getDouble("jml"), 0, 0, rs2.getString("nama_industri"), rs2.getString("kategori"),
                                            rs2.getString("golongan"), "", "", ""
                                        });
                                    } else if (Jeniskelas.getSelectedItem().equals("Beli Luar")) {
                                        tabModeDetailObatRacikan.addRow(new Object[]{
                                            rsobat.getString("no_racik"), rs2.getString("kode_brng"), rs2.getString("nama_brng"),
                                            rs2.getString("kode_sat"), rs2.getDouble("beliluar"), rs2.getDouble("h_beli"),
                                            rs2.getString("jenis"), 0, rs2.getDouble("kapasitas"), rs2.getDouble("kandungan"),
                                            rs2.getDouble("jml"), 0, 0, rs2.getString("nama_industri"), rs2.getString("kategori"),
                                            rs2.getString("golongan"), "", "", ""
                                        });
                                    } else if (Jeniskelas.getSelectedItem().equals("Karyawan")) {
                                        tabModeDetailObatRacikan.addRow(new Object[]{
                                            rsobat.getString("no_racik"), rs2.getString("kode_brng"), rs2.getString("nama_brng"),
                                            rs2.getString("kode_sat"), rs2.getDouble("karyawan"), rs2.getDouble("h_beli"),
                                            rs2.getString("jenis"), 0, rs2.getDouble("kapasitas"), rs2.getDouble("kandungan"),
                                            rs2.getDouble("jml"), 0, 0, rs2.getString("nama_industri"), rs2.getString("kategori"),
                                            rs2.getString("golongan"), "", "", ""
                                        });
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi 3 : " + e);
                            } finally {
                                if (rs2 != null) {
                                    rs2.close();
                                }
                                if (ps2 != null) {
                                    ps2.close();
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Notifikasi 2 : " + e);
            } finally {
                if (rsobat != null) {
                    rsobat.close();
                }
                if (psobat != null) {
                    psobat.close();
                }
            }
            for (i = 0; i < tbDetailObatRacikan.getRowCount(); i++) {
                getDatadetailobatracikan(i);
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    private void getDatadetailobatracikan(int data) {
        try {
            if (aktifkanbatch.equals("no")) {
                stokbarang = 0;
                psstok = koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=?");
                try {
                    psstok.setString(1, kdgudang.getText());
                    psstok.setString(2, tbDetailObatRacikan.getValueAt(data, 1).toString());
                    rsstok = psstok.executeQuery();
                    if (rsstok.next()) {
                        stokbarang = rsstok.getDouble(1);
                    }
                } catch (Exception e) {
                    stokbarang = 0;
                    System.out.println("Notifikasi : " + e);
                } finally {
                    if (rsstok != null) {
                        rsstok.close();
                    }
                    if (psstok != null) {
                        psstok.close();
                    }
                }

                tbDetailObatRacikan.setValueAt(stokbarang, data, 7);
                y = 0;
                try {
                    y = Double.parseDouble(tbDetailObatRacikan.getValueAt(data, 10).toString());
                } catch (Exception e) {
                    y = 0;
                }

                if (stokbarang < y) {
                    JOptionPane.showMessageDialog(rootPane, "Maaf stok tidak mencukupi..!!");
                }
            }
        } catch (Exception e) {
            tbDetailObatRacikan.setValueAt(0, data, 10);
            tbDetailObatRacikan.setValueAt(0, data, 11);
            tbDetailObatRacikan.setValueAt(0, data, 12);
        }
    }

    private void cariBatch() {
        if (TabRawat.getSelectedIndex() == 0) {
            try {
                ps2 = koneksi.prepareStatement("select * from data_batch where no_batch=? and kode_brng=?");
                try {
                    ps2.setString(1, tbObat.getValueAt(tbObat.getSelectedRow(), 16).toString());
                    ps2.setString(2, tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString());
                    rs2 = ps2.executeQuery();
                    if (rs2.next()) {
                        tbObat.setValueAt(rs2.getString("no_faktur"), tbObat.getSelectedRow(), 17);
                        tbObat.setValueAt(rs2.getString("tgl_kadaluarsa"), tbObat.getSelectedRow(), 18);
                        if (aktifkanbatch.equals("yes")) {
                            if (Jeniskelas.getSelectedItem().equals("Karyawan")) {
                                tbObat.setValueAt(rs2.getDouble("karyawan"), tbObat.getSelectedRow(), 6);
                            } else if (Jeniskelas.getSelectedItem().equals("Rawat Jalan")) {
                                tbObat.setValueAt(rs2.getDouble("ralan"), tbObat.getSelectedRow(), 6);
                            } else if (Jeniskelas.getSelectedItem().equals("Kelas 1")) {
                                tbObat.setValueAt(rs2.getDouble("kelas1"), tbObat.getSelectedRow(), 6);
                            } else if (Jeniskelas.getSelectedItem().equals("Kelas 2")) {
                                tbObat.setValueAt(rs2.getDouble("kelas2"), tbObat.getSelectedRow(), 6);
                            } else if (Jeniskelas.getSelectedItem().equals("Kelas 3")) {
                                tbObat.setValueAt(rs2.getDouble("kelas3"), tbObat.getSelectedRow(), 6);
                            } else if (Jeniskelas.getSelectedItem().equals("Utama/BPJS")) {
                                tbObat.setValueAt(rs2.getDouble("utama"), tbObat.getSelectedRow(), 6);
                            } else if (Jeniskelas.getSelectedItem().equals("VIP")) {
                                tbObat.setValueAt(rs2.getDouble("vip"), tbObat.getSelectedRow(), 6);
                            } else if (Jeniskelas.getSelectedItem().equals("VVIP")) {
                                tbObat.setValueAt(rs2.getDouble("vvip"), tbObat.getSelectedRow(), 6);
                            }

                            try {
                                stokbarang = rs2.getDouble("sisa");
                                tbObat.setValueAt(stokbarang, tbObat.getSelectedRow(), 10);
                                y = 0;
                                try {
                                    y = Double.parseDouble(tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString());
                                } catch (Exception e) {
                                    y = 0;
                                }
                                if (stokbarang < y) {
                                    JOptionPane.showMessageDialog(rootPane, "Maaf stok tidak mencukupi..!!");
                                    tbObat.setValueAt("", tbObat.getSelectedRow(), 1);
                                }
                            } catch (Exception e) {
                                tbObat.setValueAt(0, tbObat.getSelectedRow(), 10);
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Notif : " + e);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (ps2 != null) {
                        ps2.close();
                    }
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            }
        } else if (TabRawat.getSelectedIndex() == 1) {
            try {
                ps2 = koneksi.prepareStatement("select * from data_batch where no_batch=? and kode_brng=?");
                try {
                    ps2.setString(1, tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(), 16).toString());
                    ps2.setString(2, tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(), 1).toString());
                    rs2 = ps2.executeQuery();
                    if (rs2.next()) {
                        tbDetailObatRacikan.setValueAt(rs2.getString("no_faktur"), tbDetailObatRacikan.getSelectedRow(), 17);
                        tbDetailObatRacikan.setValueAt(rs2.getString("tgl_kadaluarsa"), tbDetailObatRacikan.getSelectedRow(), 18);
                        if (aktifkanbatch.equals("yes")) {
                            if (Jeniskelas.getSelectedItem().equals("Karyawan")) {
                                tbDetailObatRacikan.setValueAt(rs2.getDouble("karyawan"), tbDetailObatRacikan.getSelectedRow(), 4);
                            } else if (Jeniskelas.getSelectedItem().equals("Beli Luar")) {
                                tbDetailObatRacikan.setValueAt(rs2.getDouble("beliluar"), tbDetailObatRacikan.getSelectedRow(), 4);
                            } else if (Jeniskelas.getSelectedItem().equals("Kelas 1")) {
                                tbDetailObatRacikan.setValueAt(rs2.getDouble("kelas1"), tbDetailObatRacikan.getSelectedRow(), 4);
                            } else if (Jeniskelas.getSelectedItem().equals("Kelas 2")) {
                                tbDetailObatRacikan.setValueAt(rs2.getDouble("kelas2"), tbDetailObatRacikan.getSelectedRow(), 4);
                            } else if (Jeniskelas.getSelectedItem().equals("Kelas 3")) {
                                tbDetailObatRacikan.setValueAt(rs2.getDouble("kelas3"), tbDetailObatRacikan.getSelectedRow(), 4);
                            } else if (Jeniskelas.getSelectedItem().equals("Utama/BPJS")) {
                                tbDetailObatRacikan.setValueAt(rs2.getDouble("utama"), tbDetailObatRacikan.getSelectedRow(), 4);
                            } else if (Jeniskelas.getSelectedItem().equals("VIP")) {
                                tbDetailObatRacikan.setValueAt(rs2.getDouble("vip"), tbDetailObatRacikan.getSelectedRow(), 4);
                            } else if (Jeniskelas.getSelectedItem().equals("VVIP")) {
                                tbDetailObatRacikan.setValueAt(rs2.getDouble("vvip"), tbDetailObatRacikan.getSelectedRow(), 4);
                            }

                            try {
                                stokbarang = rs2.getDouble("sisa");
                                tbDetailObatRacikan.setValueAt(stokbarang, tbDetailObatRacikan.getSelectedRow(), 7);
                                y = 0;
                                try {
                                    y = Double.parseDouble(tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(), 10).toString());
                                } catch (Exception e) {
                                    y = 0;
                                }
                                if (stokbarang < y) {
                                    JOptionPane.showMessageDialog(rootPane, "Maaf stok tidak mencukupi..!!");
                                    tbDetailObatRacikan.setValueAt(0, tbDetailObatRacikan.getSelectedRow(), 10);
                                }
                            } catch (Exception e) {
                                tbDetailObatRacikan.setValueAt(0, tbDetailObatRacikan.getSelectedRow(), 7);
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Notif : " + e);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (ps2 != null) {
                        ps2.close();
                    }
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            }
        }
    }

    public void setPCare(String aktif, String nokunjung) {
        aktifpcare = aktif;
        nokunjungan = nokunjung;
    }

}
