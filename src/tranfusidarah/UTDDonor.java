/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * DlgPenyakit.java
 *
 * Created on May 23, 2010, 12:57:16 AM
 */
package tranfusidarah;

import bridging.BPJSSPRI;
import bridging.BridgingWA;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import static java.time.temporal.ChronoUnit.DAYS;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;

/**
 *
 * @author dosen
 */
public final class UTDDonor extends javax.swing.JDialog {

    private final DefaultTableModel tabModeMedis, tabModeNonMedis, tabModeTranfusi, tabModeTotalDonor;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps, ps2, pstranfusi, pscekmedis, psceknonmedis, psTotal, psTotalLk, psTotalPr, psUmur1, psUmur2, psUmur3, psUmur4, psUmur5,
            psOpos, psOneg, psApos, psAneg, psBpos, psBneg, psABpos, psABneg, ps3, psjenisdb, psjenisdp, psjenisds;
    private ResultSet rs, rs2, rstranfusi, rsTotal, rsTotalLk, rsTotalPr, rsUmur1, rsUmur2, rsUmur3, rsUmur4, rsUmur5,
            rsOpos, rsOneg, rsApos, rsAneg, rsBpos, rsBneg, rsABpos, rsABneg, rs3, rsjenisdb, rsjenisdp, rsjenisds;
    private Connection koneksi = koneksiDB.condb();
    private DlgCariPetugas petugas = new DlgCariPetugas(null, false);
    private int jml = 0, i = 0, row = 0, index = 0, pilih = 0, inthari = 0;
    private String[] kodebarang, namabarang, jumlah, satuan, stokasal, hbeli, total, printTotal;
    private String totaldonor, totallk, totalpr, umur1, umur2, umur3, umur4, umur5,
            opos, oneg, apos, aneg, bpos, bneg, abpos, abneg, jenisdb, jenisdp, jenisds;
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
    private String aktifkan = "",
            sqlpscekmedis = "select utd_penggunaan_medis_donor.kode_brng,databarang.nama_brng,utd_penggunaan_medis_donor.jml,utd_penggunaan_medis_donor.harga,"
            + "utd_penggunaan_medis_dono r.total,databarang.kode_sat from utd_penggunaan_medis_donor inner join databarang "
            + "on utd_penggunaan_medis_donor.kode_brng=databarang.kode_brng where utd_penggunaan_medis_donor.no_donor=?",
            sqlpsceknonmedis = "select utd_penggunaan_penunjang_donor.kode_brng,ipsrsbarang.nama_brng,utd_penggunaan_penunjang_donor.jml,utd_penggunaan_penunjang_donor.harga,"
            + "utd_penggunaan_penunjang_donor.total,ipsrsbarang.kode_sat from utd_penggunaan_penunjang_donor inner join ipsrsbarang "
            + "on utd_penggunaan_penunjang_donor.kode_brng=ipsrsbarang.kode_brng where utd_penggunaan_penunjang_donor.no_donor=?",
            hari = "";
    private BridgingWA kirimwa = new BridgingWA();
//    private int hari;

    /**
     * Creates new form DlgPenyakit
     *
     * @param parent
     * @param modal
     */
    public UTDDonor(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10, 2);
        setSize(628, 674);

        tabModeMedis = new DefaultTableModel(null, new Object[]{"Jml", "Kode Barang", "Nama Barang", "Harga", "Subtotal", "Satuan", "Stok"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
        };
        tbMedis.setModel(tabModeMedis);
        tbMedis.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbMedis.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbMedis.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(35);
            } else if (i == 1) {
                column.setPreferredWidth(80);
            } else if (i == 2) {
                column.setPreferredWidth(200);
            } else if (i == 3) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 4) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 5) {
                column.setPreferredWidth(50);
            } else if (i == 6) {
                column.setPreferredWidth(40);
            }
        }
        tbMedis.setDefaultRenderer(Object.class, new WarnaTable());
        //non medis
        tabModeNonMedis = new DefaultTableModel(null, new Object[]{"Jml", "Kode Barang", "Nama Barang", "Harga", "Subtotal", "Satuan", "Stok"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
        };
        tbNonMedis.setModel(tabModeNonMedis);
        tbNonMedis.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbNonMedis.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbNonMedis.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(35);
            } else if (i == 1) {
                column.setPreferredWidth(80);
            } else if (i == 2) {
                column.setPreferredWidth(200);
            } else if (i == 3) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 4) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 5) {
                column.setPreferredWidth(50);
            } else if (i == 6) {
                column.setPreferredWidth(40);
            }
        }
        tbNonMedis.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeTranfusi = new DefaultTableModel(null, new Object[]{
            "Nomor Donor", "Nama Pendonor", "NIK", "Tanggal", "Dinas", "J.K.", "Tanggal Lahir", "Umur", "Alamat", "G.D.", "Resus", "Tensi",
            "No. Kantong", "No.Telp", "J.B.", "J.D.", "Tempat Aftap", "Petugas Aftap", "HBSAg", "HCV", "HIV",
            "Spilis", "Malaria", "Petugas U.Saring"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbTranfusiDarah.setModel(tabModeTranfusi);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbTranfusiDarah.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbTranfusiDarah.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 24; i++) {
            TableColumn column = tbTranfusiDarah.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(93);
            } else if (i == 1) {
                column.setPreferredWidth(150);
            } else if (i == 2) {
                column.setPreferredWidth(150);
            } else if (i == 3) {
                column.setPreferredWidth(70);
            } else if (i == 4) {
                column.setPreferredWidth(40);
            } else if (i == 5) {
                column.setPreferredWidth(30);
            } else if (i == 6) {
                column.setPreferredWidth(70);
            } else if (i == 7) {
                column.setPreferredWidth(37);
            } else if (i == 8) {
                column.setPreferredWidth(210);
            } else if (i == 9) {
                column.setPreferredWidth(30);
            } else if (i == 10) {
                column.setPreferredWidth(40);
            } else if (i == 11) {
                column.setPreferredWidth(46);
            } else if (i == 12) {
                column.setPreferredWidth(80);
            } else if (i == 13) {
                column.setPreferredWidth(65);
            } else if (i == 14) {
                column.setPreferredWidth(27);
            } else if (i == 15) {
                column.setPreferredWidth(27);
            } else if (i == 16) {
                column.setPreferredWidth(88);
            } else if (i == 17) {
                column.setPreferredWidth(140);
            } else if (i == 18) {
                column.setPreferredWidth(43);
            } else if (i == 19) {
                column.setPreferredWidth(43);
            } else if (i == 20) {
                column.setPreferredWidth(43);
            } else if (i == 21) {
                column.setPreferredWidth(43);
            } else if (i == 22) {
                column.setPreferredWidth(43);
            } else if (i == 23) {
                column.setPreferredWidth(140);
            }
        }
        tbTranfusiDarah.setDefaultRenderer(Object.class, new WarnaTable());

        //Total Donor
        tabModeTotalDonor = new DefaultTableModel(null, new Object[]{
            "Total Donasi Darah", "Donasi Darah (lk)", "Donasi Darah (pr)", "DB","DP","DS","17 Tahun", "18-24 Tahun",
            "25-44 Tahun", "45-64 Tahun", ">= 65 Tahun",
            "O resus (+)", "O resus (-)", "A resus (+)", "A resus (-)", "B resus (+)", "B resus (-)",
            "AB resus (+)", "B resus (-)"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbTotalDonor.setModel(tabModeTotalDonor);
        tbTotalDonor.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbTotalDonor.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 17; i++) {
            TableColumn column = tbTotalDonor.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(100);
            } else if (i == 1) {
                column.setPreferredWidth(100);
            } else if (i == 2) {
                column.setPreferredWidth(100);
            } else if (i == 3) {
                column.setPreferredWidth(45);
            } else if (i == 4) {
                column.setPreferredWidth(45);
            } else if (i == 5) {
                column.setPreferredWidth(45);
            } else {
                column.setPreferredWidth(70);
            } 
        }
        tbTotalDonor.setDefaultRenderer(Object.class, new WarnaTable());

        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (petugas.getTable().getSelectedRow() != -1) {
                    if (pilih == 1) {
                        KodePetugasAftap.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                        NamaPetugasAftap.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                        KodePetugasAftap.requestFocus();
                    } else if (pilih == 2) {
                        KodePetugasUSaring.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                        NamaPetugasUSaring.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                        KodePetugasUSaring.requestFocus();
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

        TCari.setDocument(new batasInput((byte) 100).getKata(TCari));
        TCariMedis.setDocument(new batasInput((byte) 100).getKata(TCariMedis));
        TCariNonMedis.setDocument(new batasInput((byte) 100).getKata(TCariNonMedis));

        NomorDonor.setDocument(new batasInput((byte) 15).getKata(NomorDonor));
        NamaPendonor.setDocument(new batasInput((byte) 60).getKata(NamaPendonor));
        Umur.setDocument(new batasInput((byte) 4).getOnlyAngka(Umur));
        Alamat.setDocument(new batasInput((byte) 100).getKata(Alamat));
        Tensi.setDocument(new batasInput((byte) 7).getKata(Tensi));
//        NomorBag.setDocument(new batasInput((byte)8).getOnlyAngka(NomorBag));
        nik.setDocument(new batasInput((byte) 20).getOnlyAngka(nik));
        NomorTelp.setDocument(new batasInput((byte) 13).getOnlyAngka(NomorTelp));
        KodePetugasAftap.setDocument(new batasInput((byte) 20).getKata(KodePetugasAftap));
        KodePetugasUSaring.setDocument(new batasInput((byte) 20).getKata(KodePetugasUSaring));
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
            TCariMedis.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCariMedis.getText().length() > 2) {
                        tampilMedis();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCariMedis.getText().length() > 2) {
                        tampilMedis();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCariMedis.getText().length() > 2) {
                        tampilMedis();
                    }
                }
            });
            TCariNonMedis.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCariNonMedis.getText().length() > 2) {
                        tampilNonMedis();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCariNonMedis.getText().length() > 2) {
                        tampilNonMedis();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCariNonMedis.getText().length() > 2) {
                        tampilNonMedis();
                    }
                }
            });
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppBaru = new javax.swing.JMenuItem();
        ppUbah = new javax.swing.JMenuItem();
        ppSaring = new javax.swing.JMenuItem();
        ppTampilkanBHPMedis = new javax.swing.JMenuItem();
        ppTampilkanBHPPenunjang = new javax.swing.JMenuItem();
        ppTampilkanBHPPenunjangDanMedis = new javax.swing.JMenuItem();
        ppHapusBHPMedis = new javax.swing.JMenuItem();
        ppHapusBHPNonMedis = new javax.swing.JMenuItem();
        ppHapusBHPMedisDanNonMedis = new javax.swing.JMenuItem();
        ppCekal = new javax.swing.JMenuItem();
        printWB = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        panelisi5 = new widget.panelisi();
        jPanel3 = new javax.swing.JPanel();
        panelisi6 = new widget.panelisi();
        label10 = new widget.Label();
        TCariMedis = new widget.TextBox();
        BtnCari2 = new widget.Button();
        BtnAll1 = new widget.Button();
        Scroll1 = new widget.ScrollPane();
        tbMedis = new widget.Table();
        jPanel4 = new javax.swing.JPanel();
        panelisi7 = new widget.panelisi();
        label11 = new widget.Label();
        TCariNonMedis = new widget.TextBox();
        BtnCari3 = new widget.Button();
        BtnAll2 = new widget.Button();
        Scroll2 = new widget.ScrollPane();
        tbNonMedis = new widget.Table();
        scrollPane1 = new widget.ScrollPane();
        panelisi4 = new widget.panelisi();
        label34 = new widget.Label();
        NomorDonor = new widget.TextBox();
        label36 = new widget.Label();
        NamaPendonor = new widget.TextBox();
        label32 = new widget.Label();
        Tanggal = new widget.Tanggal();
        jLabel8 = new widget.Label();
        Dinas = new widget.ComboBox();
        label35 = new widget.Label();
        Umur = new widget.TextBox();
        label37 = new widget.Label();
        label38 = new widget.Label();
        Alamat = new widget.TextBox();
        jLabel9 = new widget.Label();
        JK = new widget.ComboBox();
        jLabel10 = new widget.Label();
        GolonganDarah = new widget.ComboBox();
        jLabel11 = new widget.Label();
        Resus = new widget.ComboBox();
        label39 = new widget.Label();
        Tensi = new widget.TextBox();
        label40 = new widget.Label();
        NomorBag = new widget.TextBox();
        label41 = new widget.Label();
        NomorTelp = new widget.TextBox();
        jLabel12 = new widget.Label();
        JenisBag = new widget.ComboBox();
        jLabel13 = new widget.Label();
        JenisDonor = new widget.ComboBox();
        jLabel14 = new widget.Label();
        TempatAftap = new widget.ComboBox();
        label17 = new widget.Label();
        KodePetugasAftap = new widget.TextBox();
        NamaPetugasAftap = new widget.TextBox();
        btnPetugasAftap = new widget.Button();
        label18 = new widget.Label();
        jLabel15 = new widget.Label();
        HBSAg = new widget.ComboBox();
        HCV = new widget.ComboBox();
        jLabel16 = new widget.Label();
        jLabel17 = new widget.Label();
        HIV = new widget.ComboBox();
        jLabel18 = new widget.Label();
        Spilis = new widget.ComboBox();
        label19 = new widget.Label();
        KodePetugasUSaring = new widget.TextBox();
        NamaPetugasUSaring = new widget.TextBox();
        btnPetugasUSaring = new widget.Button();
        jLabel19 = new widget.Label();
        Malaria = new widget.ComboBox();
        label33 = new widget.Label();
        tgl_lahir = new widget.Tanggal();
        nik = new widget.TextBox();
        label42 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbTranfusiDarah = new widget.Table();
        internalFrame4 = new widget.InternalFrame();
        Scroll3 = new widget.ScrollPane();
        tbTotalDonor = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnEdit1 = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnBatal = new widget.Button();
        BtnKeluar = new widget.Button();
        BtnWA = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel20 = new widget.Label();
        TanggalCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        TanggalCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll3 = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppBaru.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBaru.setForeground(new java.awt.Color(70, 70, 70));
        ppBaru.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Add patient.png"))); // NOI18N
        ppBaru.setText("Daftar Baru");
        ppBaru.setName("ppBaru"); // NOI18N
        ppBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBaruActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppBaru);

        ppUbah.setBackground(new java.awt.Color(255, 255, 254));
        ppUbah.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppUbah.setForeground(new java.awt.Color(70, 70, 70));
        ppUbah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppUbah.setText("Input Aftap");
        ppUbah.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppUbah.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppUbah.setIconTextGap(8);
        ppUbah.setName("ppUbah"); // NOI18N
        ppUbah.setPreferredSize(new java.awt.Dimension(280, 25));
        ppUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppUbahActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppUbah);

        ppSaring.setBackground(new java.awt.Color(255, 255, 254));
        ppSaring.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSaring.setForeground(new java.awt.Color(70, 70, 70));
        ppSaring.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSaring.setText("Input Uji Saring");
        ppSaring.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSaring.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSaring.setIconTextGap(8);
        ppSaring.setName("ppSaring"); // NOI18N
        ppSaring.setPreferredSize(new java.awt.Dimension(280, 25));
        ppSaring.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSaringActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppSaring);

        ppTampilkanBHPMedis.setBackground(new java.awt.Color(255, 255, 254));
        ppTampilkanBHPMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTampilkanBHPMedis.setForeground(new java.awt.Color(70, 70, 70));
        ppTampilkanBHPMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTampilkanBHPMedis.setText("Tampilkan Penggunaan BHP Medis");
        ppTampilkanBHPMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTampilkanBHPMedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTampilkanBHPMedis.setIconTextGap(8);
        ppTampilkanBHPMedis.setName("ppTampilkanBHPMedis"); // NOI18N
        ppTampilkanBHPMedis.setPreferredSize(new java.awt.Dimension(280, 25));
        ppTampilkanBHPMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTampilkanBHPMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTampilkanBHPMedis);

        ppTampilkanBHPPenunjang.setBackground(new java.awt.Color(255, 255, 254));
        ppTampilkanBHPPenunjang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTampilkanBHPPenunjang.setForeground(new java.awt.Color(70, 70, 70));
        ppTampilkanBHPPenunjang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTampilkanBHPPenunjang.setText("Tampilkan Penggunaan BHP Non Medis");
        ppTampilkanBHPPenunjang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTampilkanBHPPenunjang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTampilkanBHPPenunjang.setIconTextGap(8);
        ppTampilkanBHPPenunjang.setName("ppTampilkanBHPPenunjang"); // NOI18N
        ppTampilkanBHPPenunjang.setPreferredSize(new java.awt.Dimension(280, 25));
        ppTampilkanBHPPenunjang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTampilkanBHPPenunjangActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTampilkanBHPPenunjang);

        ppTampilkanBHPPenunjangDanMedis.setBackground(new java.awt.Color(255, 255, 254));
        ppTampilkanBHPPenunjangDanMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTampilkanBHPPenunjangDanMedis.setForeground(new java.awt.Color(70, 70, 70));
        ppTampilkanBHPPenunjangDanMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTampilkanBHPPenunjangDanMedis.setText("Tampilkan Penggunaan BHP Medis & Non Medis");
        ppTampilkanBHPPenunjangDanMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTampilkanBHPPenunjangDanMedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTampilkanBHPPenunjangDanMedis.setIconTextGap(8);
        ppTampilkanBHPPenunjangDanMedis.setName("ppTampilkanBHPPenunjangDanMedis"); // NOI18N
        ppTampilkanBHPPenunjangDanMedis.setPreferredSize(new java.awt.Dimension(280, 25));
        ppTampilkanBHPPenunjangDanMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTampilkanBHPPenunjangDanMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTampilkanBHPPenunjangDanMedis);

        ppHapusBHPMedis.setBackground(new java.awt.Color(255, 255, 254));
        ppHapusBHPMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapusBHPMedis.setForeground(new java.awt.Color(70, 70, 70));
        ppHapusBHPMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppHapusBHPMedis.setText("Hapus Penggunaan BHP Medis");
        ppHapusBHPMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapusBHPMedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapusBHPMedis.setIconTextGap(8);
        ppHapusBHPMedis.setName("ppHapusBHPMedis"); // NOI18N
        ppHapusBHPMedis.setPreferredSize(new java.awt.Dimension(280, 25));
        ppHapusBHPMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHapusBHPMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppHapusBHPMedis);

        ppHapusBHPNonMedis.setBackground(new java.awt.Color(255, 255, 254));
        ppHapusBHPNonMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapusBHPNonMedis.setForeground(new java.awt.Color(70, 70, 70));
        ppHapusBHPNonMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppHapusBHPNonMedis.setText("Hapus Penggunaan BHP Non Medis");
        ppHapusBHPNonMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapusBHPNonMedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapusBHPNonMedis.setIconTextGap(8);
        ppHapusBHPNonMedis.setName("ppHapusBHPNonMedis"); // NOI18N
        ppHapusBHPNonMedis.setPreferredSize(new java.awt.Dimension(280, 25));
        ppHapusBHPNonMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHapusBHPNonMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppHapusBHPNonMedis);

        ppHapusBHPMedisDanNonMedis.setBackground(new java.awt.Color(255, 255, 254));
        ppHapusBHPMedisDanNonMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapusBHPMedisDanNonMedis.setForeground(new java.awt.Color(70, 70, 70));
        ppHapusBHPMedisDanNonMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppHapusBHPMedisDanNonMedis.setText("Hapus Penggunaan BHP Medis & Non Medis");
        ppHapusBHPMedisDanNonMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapusBHPMedisDanNonMedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapusBHPMedisDanNonMedis.setIconTextGap(8);
        ppHapusBHPMedisDanNonMedis.setName("ppHapusBHPMedisDanNonMedis"); // NOI18N
        ppHapusBHPMedisDanNonMedis.setPreferredSize(new java.awt.Dimension(280, 25));
        ppHapusBHPMedisDanNonMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHapusBHPMedisDanNonMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppHapusBHPMedisDanNonMedis);

        ppCekal.setBackground(new java.awt.Color(255, 255, 254));
        ppCekal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppCekal.setForeground(new java.awt.Color(70, 70, 70));
        ppCekal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppCekal.setText("Cekal Darah");
        ppCekal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppCekal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppCekal.setIconTextGap(8);
        ppCekal.setName("ppCekal"); // NOI18N
        ppCekal.setPreferredSize(new java.awt.Dimension(280, 25));
        ppCekal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppCekalActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppCekal);

        printWB.setBackground(new java.awt.Color(255, 255, 254));
        printWB.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        printWB.setForeground(new java.awt.Color(70, 70, 70));
        printWB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        printWB.setText("printWB");
        printWB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        printWB.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        printWB.setIconTextGap(8);
        printWB.setName("printWB"); // NOI18N
        printWB.setPreferredSize(new java.awt.Dimension(280, 25));
        printWB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printWBActionPerformed(evt);
            }
        });
        jPopupMenu1.add(printWB);

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Donor Darah ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(70, 70, 70));
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi5.setBorder(null);
        panelisi5.setName("panelisi5"); // NOI18N
        panelisi5.setPreferredSize(new java.awt.Dimension(450, 77));
        panelisi5.setLayout(new java.awt.GridLayout(2, 0));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(237, 242, 232)), ".: Penggunaan BHP Medis ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 102));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi6.setBorder(null);
        panelisi6.setName("panelisi6"); // NOI18N
        panelisi6.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi6.add(label10);

        TCariMedis.setToolTipText("Alt+C");
        TCariMedis.setName("TCariMedis"); // NOI18N
        TCariMedis.setPreferredSize(new java.awt.Dimension(290, 23));
        TCariMedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariMedisKeyPressed(evt);
            }
        });
        panelisi6.add(TCariMedis);

        BtnCari2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari2.setMnemonic('1');
        BtnCari2.setToolTipText("Alt+1");
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
        panelisi6.add(BtnCari2);

        BtnAll1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll1.setMnemonic('2');
        BtnAll1.setToolTipText("Alt+2");
        BtnAll1.setName("BtnAll1"); // NOI18N
        BtnAll1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAll1ActionPerformed(evt);
            }
        });
        BtnAll1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAll1KeyPressed(evt);
            }
        });
        panelisi6.add(BtnAll1);

        jPanel3.add(panelisi6, java.awt.BorderLayout.PAGE_END);

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 255, 235)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbMedis.setToolTipText("Klik pada cell Jml untuk memasukkan jumlah penggunaan");
        tbMedis.setName("tbMedis"); // NOI18N
        tbMedis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMedisMouseClicked(evt);
            }
        });
        tbMedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbMedisKeyPressed(evt);
            }
        });
        Scroll1.setViewportView(tbMedis);

        jPanel3.add(Scroll1, java.awt.BorderLayout.CENTER);

        panelisi5.add(jPanel3);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(237, 242, 232)), ".: Penggunaan BHP Non Medis ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(300, 202));
        jPanel4.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi7.setBorder(null);
        panelisi7.setName("panelisi7"); // NOI18N
        panelisi7.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label11.setText("Key Word :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi7.add(label11);

        TCariNonMedis.setToolTipText("Alt+C");
        TCariNonMedis.setName("TCariNonMedis"); // NOI18N
        TCariNonMedis.setPreferredSize(new java.awt.Dimension(290, 23));
        TCariNonMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TCariNonMedisActionPerformed(evt);
            }
        });
        TCariNonMedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariNonMedisKeyPressed(evt);
            }
        });
        panelisi7.add(TCariNonMedis);

        BtnCari3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari3.setMnemonic('1');
        BtnCari3.setToolTipText("Alt+1");
        BtnCari3.setName("BtnCari3"); // NOI18N
        BtnCari3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari3ActionPerformed(evt);
            }
        });
        BtnCari3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari3KeyPressed(evt);
            }
        });
        panelisi7.add(BtnCari3);

        BtnAll2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll2.setMnemonic('2');
        BtnAll2.setToolTipText("Alt+2");
        BtnAll2.setName("BtnAll2"); // NOI18N
        BtnAll2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAll2ActionPerformed(evt);
            }
        });
        BtnAll2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAll2KeyPressed(evt);
            }
        });
        panelisi7.add(BtnAll2);

        jPanel4.add(panelisi7, java.awt.BorderLayout.PAGE_END);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 255, 235)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbNonMedis.setToolTipText("Klik pada cell Jml untuk memasukkan jumlah penggunaan");
        tbNonMedis.setName("tbNonMedis"); // NOI18N
        tbNonMedis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbNonMedisMouseClicked(evt);
            }
        });
        tbNonMedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbNonMedisKeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbNonMedis);

        jPanel4.add(Scroll2, java.awt.BorderLayout.CENTER);

        panelisi5.add(jPanel4);

        internalFrame2.add(panelisi5, java.awt.BorderLayout.EAST);

        scrollPane1.setName("scrollPane1"); // NOI18N

        panelisi4.setBorder(null);
        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 77));
        panelisi4.setLayout(null);

        label34.setText("Nomor Kantong :");
        label34.setName("label34"); // NOI18N
        label34.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label34);
        label34.setBounds(0, 170, 102, 23);

        NomorDonor.setEditable(false);
        NomorDonor.setHighlighter(null);
        NomorDonor.setName("NomorDonor"); // NOI18N
        NomorDonor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NomorDonorActionPerformed(evt);
            }
        });
        panelisi4.add(NomorDonor);
        NomorDonor.setBounds(110, 10, 140, 23);

        label36.setText("Nama Pendonor :");
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label36);
        label36.setBounds(0, 40, 102, 23);

        NamaPendonor.setHighlighter(null);
        NamaPendonor.setName("NamaPendonor"); // NOI18N
        NamaPendonor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaPendonorKeyPressed(evt);
            }
        });
        panelisi4.add(NamaPendonor);
        NamaPendonor.setBounds(110, 40, 270, 23);

        label32.setText("Tanggal :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label32);
        label32.setBounds(260, 10, 57, 23);

        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-12-2022" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TanggalItemStateChanged(evt);
            }
        });
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        panelisi4.add(Tanggal);
        Tanggal.setBounds(320, 10, 90, 23);

        jLabel8.setText("Dinas :");
        jLabel8.setName("jLabel8"); // NOI18N
        panelisi4.add(jLabel8);
        jLabel8.setBounds(430, 10, 50, 23);

        Dinas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pagi", "Siang", "Sore", "Malam" }));
        Dinas.setName("Dinas"); // NOI18N
        Dinas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DinasKeyPressed(evt);
            }
        });
        panelisi4.add(Dinas);
        Dinas.setBounds(480, 10, 86, 23);

        label35.setText("Tahun");
        label35.setName("label35"); // NOI18N
        label35.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label35);
        label35.setBounds(320, 70, 40, 23);

        Umur.setEditable(false);
        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N
        panelisi4.add(Umur);
        Umur.setBounds(286, 70, 40, 23);

        label37.setText("Umur :");
        label37.setName("label37"); // NOI18N
        label37.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label37);
        label37.setBounds(240, 70, 40, 23);

        label38.setText("Alamat :");
        label38.setName("label38"); // NOI18N
        label38.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label38);
        label38.setBounds(0, 130, 102, 23);

        Alamat.setHighlighter(null);
        Alamat.setName("Alamat"); // NOI18N
        Alamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlamatKeyPressed(evt);
            }
        });
        panelisi4.add(Alamat);
        Alamat.setBounds(110, 130, 450, 23);

        jLabel9.setText("J.K. :");
        jLabel9.setName("jLabel9"); // NOI18N
        panelisi4.add(jLabel9);
        jLabel9.setBounds(0, 100, 102, 23);

        JK.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "LAKI-LAKI", "PEREMPUAN" }));
        JK.setName("JK"); // NOI18N
        JK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JKKeyPressed(evt);
            }
        });
        panelisi4.add(JK);
        JK.setBounds(110, 100, 110, 23);

        jLabel10.setText("Golongan Darah :");
        jLabel10.setName("jLabel10"); // NOI18N
        panelisi4.add(jLabel10);
        jLabel10.setBounds(0, 200, 102, 23);

        GolonganDarah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A", "AB", "B", "O" }));
        GolonganDarah.setName("GolonganDarah"); // NOI18N
        GolonganDarah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GolonganDarahKeyPressed(evt);
            }
        });
        panelisi4.add(GolonganDarah);
        GolonganDarah.setBounds(110, 200, 70, 23);

        jLabel11.setText("Resus :");
        jLabel11.setName("jLabel11"); // NOI18N
        panelisi4.add(jLabel11);
        jLabel11.setBounds(210, 200, 50, 23);

        Resus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "(-)", "(+)" }));
        Resus.setName("Resus"); // NOI18N
        panelisi4.add(Resus);
        Resus.setBounds(260, 200, 70, 23);

        label39.setText("Tensi :");
        label39.setName("label39"); // NOI18N
        label39.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label39);
        label39.setBounds(350, 200, 40, 23);

        Tensi.setHighlighter(null);
        Tensi.setName("Tensi"); // NOI18N
        Tensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TensiKeyPressed(evt);
            }
        });
        panelisi4.add(Tensi);
        Tensi.setBounds(400, 200, 80, 23);

        label40.setText("Nomor Donor :");
        label40.setName("label40"); // NOI18N
        label40.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label40);
        label40.setBounds(0, 10, 102, 23);

        NomorBag.setHighlighter(null);
        NomorBag.setName("NomorBag"); // NOI18N
        panelisi4.add(NomorBag);
        NomorBag.setBounds(110, 170, 140, 23);

        label41.setText("No.Telp :");
        label41.setName("label41"); // NOI18N
        label41.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label41);
        label41.setBounds(230, 100, 50, 23);

        NomorTelp.setHighlighter(null);
        NomorTelp.setName("NomorTelp"); // NOI18N
        NomorTelp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NomorTelpKeyPressed(evt);
            }
        });
        panelisi4.add(NomorTelp);
        NomorTelp.setBounds(290, 100, 110, 23);

        jLabel12.setText("Jenis Bag :");
        jLabel12.setName("jLabel12"); // NOI18N
        panelisi4.add(jLabel12);
        jLabel12.setBounds(190, 230, 70, 23);

        JenisBag.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SB", "DB", "TB", "QB", "P" }));
        JenisBag.setName("JenisBag"); // NOI18N
        JenisBag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JenisBagActionPerformed(evt);
            }
        });
        JenisBag.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisBagKeyPressed(evt);
            }
        });
        panelisi4.add(JenisBag);
        JenisBag.setBounds(260, 230, 70, 23);

        jLabel13.setText("Jenis Donor :");
        jLabel13.setName("jLabel13"); // NOI18N
        panelisi4.add(jLabel13);
        jLabel13.setBounds(0, 230, 102, 23);

        JenisDonor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "DB", "DP", "DS" }));
        JenisDonor.setName("JenisDonor"); // NOI18N
        panelisi4.add(JenisDonor);
        JenisDonor.setBounds(110, 230, 70, 23);

        jLabel14.setText("Tempat Aftap :");
        jLabel14.setName("jLabel14"); // NOI18N
        panelisi4.add(jLabel14);
        jLabel14.setBounds(280, 170, 80, 23);

        TempatAftap.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dalam Gedung", "Luar Gedung" }));
        TempatAftap.setName("TempatAftap"); // NOI18N
        TempatAftap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TempatAftapKeyPressed(evt);
            }
        });
        panelisi4.add(TempatAftap);
        TempatAftap.setBounds(360, 170, 150, 23);

        label17.setText("Hasil Uji Saring :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label17);
        label17.setBounds(0, 290, 102, 23);

        KodePetugasAftap.setName("KodePetugasAftap"); // NOI18N
        KodePetugasAftap.setPreferredSize(new java.awt.Dimension(80, 23));
        KodePetugasAftap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodePetugasAftapKeyPressed(evt);
            }
        });
        panelisi4.add(KodePetugasAftap);
        KodePetugasAftap.setBounds(110, 260, 90, 23);

        NamaPetugasAftap.setEditable(false);
        NamaPetugasAftap.setName("NamaPetugasAftap"); // NOI18N
        NamaPetugasAftap.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(NamaPetugasAftap);
        NamaPetugasAftap.setBounds(200, 260, 286, 23);

        btnPetugasAftap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugasAftap.setMnemonic('1');
        btnPetugasAftap.setToolTipText("Alt+1");
        btnPetugasAftap.setName("btnPetugasAftap"); // NOI18N
        btnPetugasAftap.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugasAftap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasAftapActionPerformed(evt);
            }
        });
        panelisi4.add(btnPetugasAftap);
        btnPetugasAftap.setBounds(490, 260, 28, 23);

        label18.setText("Petugas Aftap :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label18);
        label18.setBounds(0, 260, 102, 23);

        jLabel15.setText("HBSAg :");
        jLabel15.setName("jLabel15"); // NOI18N
        panelisi4.add(jLabel15);
        jLabel15.setBounds(40, 310, 97, 23);

        HBSAg.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Negatif", "Positif" }));
        HBSAg.setName("HBSAg"); // NOI18N
        HBSAg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HBSAgActionPerformed(evt);
            }
        });
        HBSAg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HBSAgKeyPressed(evt);
            }
        });
        panelisi4.add(HBSAg);
        HBSAg.setBounds(140, 310, 110, 23);

        HCV.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Negatif", "Positif" }));
        HCV.setName("HCV"); // NOI18N
        HCV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HCVKeyPressed(evt);
            }
        });
        panelisi4.add(HCV);
        HCV.setBounds(340, 310, 110, 23);

        jLabel16.setText("HCV :");
        jLabel16.setName("jLabel16"); // NOI18N
        panelisi4.add(jLabel16);
        jLabel16.setBounds(270, 310, 70, 23);

        jLabel17.setText("HIV :");
        jLabel17.setName("jLabel17"); // NOI18N
        panelisi4.add(jLabel17);
        jLabel17.setBounds(40, 340, 97, 23);

        HIV.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Negatif", "Positif" }));
        HIV.setName("HIV"); // NOI18N
        HIV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HIVKeyPressed(evt);
            }
        });
        panelisi4.add(HIV);
        HIV.setBounds(140, 340, 110, 23);

        jLabel18.setText("Spilis :");
        jLabel18.setName("jLabel18"); // NOI18N
        panelisi4.add(jLabel18);
        jLabel18.setBounds(270, 340, 70, 23);

        Spilis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Negatif", "Positif" }));
        Spilis.setName("Spilis"); // NOI18N
        Spilis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SpilisKeyPressed(evt);
            }
        });
        panelisi4.add(Spilis);
        Spilis.setBounds(340, 340, 110, 23);

        label19.setText("Petugas U.Saring :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label19);
        label19.setBounds(0, 400, 102, 23);

        KodePetugasUSaring.setName("KodePetugasUSaring"); // NOI18N
        KodePetugasUSaring.setPreferredSize(new java.awt.Dimension(80, 23));
        KodePetugasUSaring.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodePetugasUSaringKeyPressed(evt);
            }
        });
        panelisi4.add(KodePetugasUSaring);
        KodePetugasUSaring.setBounds(110, 400, 90, 23);

        NamaPetugasUSaring.setEditable(false);
        NamaPetugasUSaring.setName("NamaPetugasUSaring"); // NOI18N
        NamaPetugasUSaring.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(NamaPetugasUSaring);
        NamaPetugasUSaring.setBounds(200, 400, 286, 23);

        btnPetugasUSaring.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugasUSaring.setMnemonic('1');
        btnPetugasUSaring.setToolTipText("Alt+1");
        btnPetugasUSaring.setName("btnPetugasUSaring"); // NOI18N
        btnPetugasUSaring.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugasUSaring.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasUSaringActionPerformed(evt);
            }
        });
        panelisi4.add(btnPetugasUSaring);
        btnPetugasUSaring.setBounds(490, 400, 28, 23);

        jLabel19.setText("Malaria :");
        jLabel19.setName("jLabel19"); // NOI18N
        panelisi4.add(jLabel19);
        jLabel19.setBounds(40, 370, 97, 23);

        Malaria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Negatif", "Positif" }));
        Malaria.setName("Malaria"); // NOI18N
        Malaria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MalariaKeyPressed(evt);
            }
        });
        panelisi4.add(Malaria);
        Malaria.setBounds(140, 370, 110, 23);

        label33.setText("Tanggal Lahir :");
        label33.setName("label33"); // NOI18N
        label33.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label33);
        label33.setBounds(12, 70, 90, 23);

        tgl_lahir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-12-2022" }));
        tgl_lahir.setDisplayFormat("dd-MM-yyyy");
        tgl_lahir.setName("tgl_lahir"); // NOI18N
        tgl_lahir.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                tgl_lahirItemStateChanged(evt);
            }
        });
        panelisi4.add(tgl_lahir);
        tgl_lahir.setBounds(110, 70, 90, 23);

        nik.setHighlighter(null);
        nik.setName("nik"); // NOI18N
        nik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nikActionPerformed(evt);
            }
        });
        panelisi4.add(nik);
        nik.setBounds(420, 40, 140, 23);

        label42.setText("NIK : ");
        label42.setName("label42"); // NOI18N
        label42.setPreferredSize(new java.awt.Dimension(33, 23));
        panelisi4.add(label42);
        label42.setBounds(310, 40, 105, 23);

        jSeparator1.setName("jSeparator1"); // NOI18N
        panelisi4.add(jSeparator1);
        jSeparator1.setBounds(0, 285, 780, 2);

        jSeparator2.setName("jSeparator2"); // NOI18N
        panelisi4.add(jSeparator2);
        jSeparator2.setBounds(0, 156, 780, 2);

        scrollPane1.setViewportView(panelisi4);

        internalFrame2.add(scrollPane1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Pendonor Darah", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ScrollMouseClicked(evt);
            }
        });

        tbTranfusiDarah.setAutoCreateRowSorter(true);
        tbTranfusiDarah.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbTranfusiDarah.setComponentPopupMenu(jPopupMenu1);
        tbTranfusiDarah.setName("tbTranfusiDarah"); // NOI18N
        tbTranfusiDarah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTranfusiDarahMouseClicked(evt);
            }
        });
        tbTranfusiDarah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTranfusiDarahKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbTranfusiDarah);

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Daftar Pendonor Darah", internalFrame3);

        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll3.setComponentPopupMenu(jPopupMenu1);
        Scroll3.setName("Scroll"); // NOI18N
        Scroll3.setOpaque(true);
        Scroll3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Scroll3MouseClicked(evt);
            }
        });

        tbTotalDonor.setAutoCreateRowSorter(true);
        tbTotalDonor.setToolTipText("");
        tbTotalDonor.setComponentPopupMenu(jPopupMenu1);
        tbTotalDonor.setName("tbTotalDonor"); // NOI18N
        tbTotalDonor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTotalDonorMouseClicked(evt);
            }
        });
        tbTotalDonor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTotalDonorKeyPressed(evt);
            }
        });
        Scroll3.setViewportView(tbTotalDonor);

        internalFrame4.add(Scroll3, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Laporan Total Donor Darah", internalFrame4);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(55, 55));
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

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Aftap");
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

        BtnEdit1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit1.setMnemonic('G');
        BtnEdit1.setText("Saring");
        BtnEdit1.setToolTipText("Alt+G");
        BtnEdit1.setName("BtnEdit1"); // NOI18N
        BtnEdit1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEdit1ActionPerformed(evt);
            }
        });
        BtnEdit1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEdit1KeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit1);

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

        BtnWA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/edit_f2.png"))); // NOI18N
        BtnWA.setMnemonic('G');
        BtnWA.setText("Kirim WA");
        BtnWA.setToolTipText("Alt+G");
        BtnWA.setName("BtnWA"); // NOI18N
        BtnWA.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnWA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnWAActionPerformed(evt);
            }
        });
        BtnWA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnWAKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnWA);

        jPanel1.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel20.setText("Tanggal :");
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(jLabel20);

        TanggalCari1.setForeground(new java.awt.Color(50, 70, 50));
        TanggalCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-12-2022" }));
        TanggalCari1.setDisplayFormat("dd-MM-yyyy");
        TanggalCari1.setName("TanggalCari1"); // NOI18N
        TanggalCari1.setOpaque(false);
        TanggalCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(TanggalCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(24, 23));
        panelGlass9.add(jLabel21);

        TanggalCari2.setForeground(new java.awt.Color(50, 70, 50));
        TanggalCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-12-2022" }));
        TanggalCari2.setDisplayFormat("dd-MM-yyyy");
        TanggalCari2.setName("TanggalCari2"); // NOI18N
        TanggalCari2.setOpaque(false);
        TanggalCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        TanggalCari2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TanggalCari2ActionPerformed(evt);
            }
        });
        panelGlass9.add(TanggalCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(200, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
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

        BtnAll3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll3.setMnemonic('M');
        BtnAll3.setText("Cari");
        BtnAll3.setToolTipText("Alt+M");
        BtnAll3.setName("BtnAll3"); // NOI18N
        BtnAll3.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnAll3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAll3ActionPerformed(evt);
            }
        });
        panelGlass9.add(BtnAll3);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(75, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(LCount);

        jPanel1.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void NamaPendonorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaPendonorKeyPressed
    Valid.pindah(evt, Dinas, JK);
}//GEN-LAST:event_NamaPendonorKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        emptTeks();
        tampilMedis();
        tampilNonMedis();
        BtnBatal.hide();
//        tampilTotalDonor();
    }//GEN-LAST:event_formWindowOpened

    private void TCariMedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariMedisKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCari2ActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            TCariNonMedis.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            KodePetugasUSaring.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            tbMedis.requestFocus();
        }
    }//GEN-LAST:event_TCariMedisKeyPressed

    private void BtnCari2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari2ActionPerformed
        tampilMedis();
    }//GEN-LAST:event_BtnCari2ActionPerformed

    private void BtnCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCari2KeyPressed

    private void BtnAll1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAll1ActionPerformed
        TCariMedis.setText("");
        tampilMedis();
    }//GEN-LAST:event_BtnAll1ActionPerformed

    private void BtnAll1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAll1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnAll1KeyPressed

    private void tbMedisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMedisMouseClicked

    }//GEN-LAST:event_tbMedisMouseClicked

    private void tbMedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbMedisKeyPressed
        if (tbMedis.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                try {
                    i = tbMedis.getSelectedColumn();
                    if (i == 1) {
                        TCariMedis.setText("");
                        TCariMedis.requestFocus();
                    }
                } catch (java.lang.NullPointerException e) {
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
                TCariMedis.setText("");
                TCariMedis.requestFocus();
            }
        }
    }//GEN-LAST:event_tbMedisKeyPressed

    private void TCariNonMedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariNonMedisKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCari3ActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnSimpan.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            TCariMedis.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            tbNonMedis.requestFocus();
        }
    }//GEN-LAST:event_TCariNonMedisKeyPressed

    private void BtnCari3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari3ActionPerformed
        tampilNonMedis();
    }//GEN-LAST:event_BtnCari3ActionPerformed

    private void BtnCari3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCari3KeyPressed

    private void BtnAll2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAll2ActionPerformed
        TCariNonMedis.setText("");
        tampilNonMedis();
    }//GEN-LAST:event_BtnAll2ActionPerformed

    private void BtnAll2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAll2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnAll2KeyPressed

    private void tbNonMedisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbNonMedisMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbNonMedisMouseClicked

    private void tbNonMedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbNonMedisKeyPressed
        if (tbNonMedis.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                try {
                    i = tbNonMedis.getSelectedColumn();
                    if (i == 1) {
                        TCariNonMedis.setText("");
                        TCariNonMedis.requestFocus();
                    }
                } catch (java.lang.NullPointerException e) {
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
                TCariNonMedis.setText("");
                TCariNonMedis.requestFocus();
            }
        }
    }//GEN-LAST:event_tbNonMedisKeyPressed

    private void tbTranfusiDarahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTranfusiDarahMouseClicked
        /*if(tabMode.getRowCount()!=0){
            try {
                getData();
                if(evt.getClickCount()==2){
                    TabRawat.setSelectedIndex(0);
                }
            } catch (java.lang.NullPointerException e) {
            }
        }*/
    }//GEN-LAST:event_tbTranfusiDarahMouseClicked

    private void tbTranfusiDarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTranfusiDarahKeyPressed
        /*if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }*/
    }//GEN-LAST:event_tbTranfusiDarahKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if (TabRawat.getSelectedIndex() == 1) {
            tampil();
        } else if (TabRawat.getSelectedIndex() == 0) {
            tampilMedis();
            tampilNonMedis();
        } else {
            tampilTotalDonor();
        };
    }//GEN-LAST:event_TabRawatMouseClicked

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (NamaPendonor.getText().trim().equals("")) {
            Valid.textKosong(NamaPendonor, "Nama Pendonor");
        } else if (Umur.getText().trim().equals("")) {
            Valid.textKosong(Umur, "Umur");
        } else if (nik.getText().trim().equals("")) {
            Valid.textKosong(nik, "NIK");
//        }else if(Tensi.getText().trim().equals("")){
//            Valid.textKosong(Tensi,"Tensi");
////        }else if(NomorBag.getText().trim().equals("")){
////            Valid.textKosong(NomorBag,"Nomor Bag");
//        }else if(NomorTelp.getText().trim().equals("")){
//            Valid.textKosong(NomorTelp,"Nomor Selang");
//        }else if(KodePetugasAftap.getText().trim().equals("")||NamaPetugasAftap.getText().trim().equals("")){
//            Valid.textKosong(KodePetugasAftap,"Petugas Aftap");
//        }else if(KodePetugasUSaring.getText().trim().equals("")||NamaPetugasUSaring.getText().trim().equals("")){
//            Valid.textKosong(KodePetugasUSaring,"Petugas Uji Saring");
        } else {
            String user = var.getkode();
            if (Sequel.menyimpantf("utd_donor", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Nomor Donor", 25, new String[]{
                NomorDonor.getText(), NamaPendonor.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""),
                Dinas.getSelectedItem().toString(), JK.getSelectedItem().toString().substring(0, 1), Umur.getText(),
                Alamat.getText(), null, null, "", "",
                //                    GolonganDarah.getSelectedItem().toString(),Resus.getSelectedItem().toString(), 
                //                    Tensi.getText(),NomorBag.getText(),
                NomorTelp.getText(), null, null, null,
                //                    JenisBag.getSelectedItem().toString(),JenisDonor.getSelectedItem().toString(),TempatAftap.getSelectedItem().toString(), 
                user, null, null, null, null, null,
                //                    HBSAg.getSelectedItem().toString(),HCV.getSelectedItem().toString(), 
                //                    HIV.getSelectedItem().toString(),Spilis.getSelectedItem().toString(),Malaria.getSelectedItem().toString(),
                user, "Aman", Valid.SetTgl(tgl_lahir.getSelectedItem() + ""), nik.getText()
            }) == true) {
                for (i = 0; i < tbMedis.getRowCount(); i++) {
                    try {
                        if (Valid.SetAngka(tbMedis.getValueAt(i, 0).toString()) > 0) {
                            if (Sequel.menyimpantf2("utd_penggunaan_medis_donor", "?,?,?,?,?", "BHP Medis", 5, new String[]{
                                NomorDonor.getText(), tbMedis.getValueAt(i, 1).toString(), tbMedis.getValueAt(i, 0).toString(), tbMedis.getValueAt(i, 3).toString(),
                                Double.toString(Double.parseDouble(tbMedis.getValueAt(i, 0).toString()) * Double.parseDouble(tbMedis.getValueAt(i, 3).toString()))
                            }) == true) {
                                Sequel.menyimpan("utd_stok_medis", "'" + tbMedis.getValueAt(i, 1).toString() + "','-" + tbMedis.getValueAt(i, 0).toString() + "','" + tbMedis.getValueAt(i, 3).toString() + "'",
                                        "stok=stok-" + tbMedis.getValueAt(i, 0).toString() + "", "kode_brng='" + tbMedis.getValueAt(i, 1).toString() + "'");
                                tbMedis.setValueAt(null, i, 0);
                                tbMedis.setValueAt(0, i, 4);
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                for (i = 0; i < tbNonMedis.getRowCount(); i++) {
                    try {
                        if (Valid.SetAngka(tbNonMedis.getValueAt(i, 0).toString()) > 0) {
                            if (Sequel.menyimpantf2("utd_penggunaan_penunjang_donor", "?,?,?,?,?", "BHP Non Medis", 5, new String[]{
                                NomorDonor.getText(), tbNonMedis.getValueAt(i, 1).toString(), tbNonMedis.getValueAt(i, 0).toString(), tbNonMedis.getValueAt(i, 3).toString(),
                                Double.toString(Double.parseDouble(tbNonMedis.getValueAt(i, 0).toString()) * Double.parseDouble(tbNonMedis.getValueAt(i, 3).toString()))
                            }) == true) {
                                Sequel.menyimpan("utd_stok_penunjang", "'" + tbNonMedis.getValueAt(i, 1).toString() + "','-" + tbNonMedis.getValueAt(i, 0).toString() + "','" + tbNonMedis.getValueAt(i, 3).toString() + "'",
                                        "stok=stok-" + tbNonMedis.getValueAt(i, 0).toString() + "", "kode_brng='" + tbNonMedis.getValueAt(i, 1).toString() + "'");
                                tbNonMedis.setValueAt(null, i, 0);
                                tbNonMedis.setValueAt(0, i, 4);
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                JOptionPane.showMessageDialog(null, "Berhasil Simpan.");
                tampil();
                emptTeks();
            } else {
//                autoNomer();
                isNumber();
                if (Sequel.menyimpantf("utd_donor", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Nomor Donor", 25, new String[]{
                    NomorDonor.getText(), NamaPendonor.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""),
                    Dinas.getSelectedItem().toString(), JK.getSelectedItem().toString().substring(0, 1), Umur.getText(),
                    Alamat.getText(), null, null, "", "",
                    //                    GolonganDarah.getSelectedItem().toString(),Resus.getSelectedItem().toString(), 
                    //                    Tensi.getText(),NomorBag.getText(),
                    NomorTelp.getText(), null, null, null,
                    //                    JenisBag.getSelectedItem().toString(),JenisDonor.getSelectedItem().toString(),TempatAftap.getSelectedItem().toString(), 
                    user, null, null, null, null, null,
                    //                    HBSAg.getSelectedItem().toString(),HCV.getSelectedItem().toString(), 
                    //                    HIV.getSelectedItem().toString(),Spilis.getSelectedItem().toString(),Malaria.getSelectedItem().toString(),
                    user, "Aman", Valid.SetTgl(tgl_lahir.getSelectedItem() + ""), nik.getText()
                }) == true) {
                    for (i = 0; i < tbMedis.getRowCount(); i++) {
                        try {
                            if (Valid.SetAngka(tbMedis.getValueAt(i, 0).toString()) > 0) {
                                if (Sequel.menyimpantf2("utd_penggunaan_medis_donor", "?,?,?,?,?", "BHP Medis", 5, new String[]{
                                    NomorDonor.getText(), tbMedis.getValueAt(i, 1).toString(), tbMedis.getValueAt(i, 0).toString(), tbMedis.getValueAt(i, 3).toString(),
                                    Double.toString(Double.parseDouble(tbMedis.getValueAt(i, 0).toString()) * Double.parseDouble(tbMedis.getValueAt(i, 3).toString()))
                                }) == true) {
                                    Sequel.menyimpan("utd_stok_medis", "'" + tbMedis.getValueAt(i, 1).toString() + "','-" + tbMedis.getValueAt(i, 0).toString() + "','" + tbMedis.getValueAt(i, 3).toString() + "'",
                                            "stok=stok-" + tbMedis.getValueAt(i, 0).toString() + "", "kode_brng='" + tbMedis.getValueAt(i, 1).toString() + "'");
                                    tbMedis.setValueAt(null, i, 0);
                                    tbMedis.setValueAt(0, i, 4);
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                    for (i = 0; i < tbNonMedis.getRowCount(); i++) {
                        try {
                            if (Valid.SetAngka(tbNonMedis.getValueAt(i, 0).toString()) > 0) {
                                if (Sequel.menyimpantf2("utd_penggunaan_penunjang_donor", "?,?,?,?,?", "BHP Non Medis", 5, new String[]{
                                    NomorDonor.getText(), tbNonMedis.getValueAt(i, 1).toString(), tbNonMedis.getValueAt(i, 0).toString(), tbNonMedis.getValueAt(i, 3).toString(),
                                    Double.toString(Double.parseDouble(tbNonMedis.getValueAt(i, 0).toString()) * Double.parseDouble(tbNonMedis.getValueAt(i, 3).toString()))
                                }) == true) {
                                    Sequel.menyimpan("utd_stok_penunjang", "'" + tbNonMedis.getValueAt(i, 1).toString() + "','-" + tbNonMedis.getValueAt(i, 0).toString() + "','" + tbNonMedis.getValueAt(i, 3).toString() + "'",
                                            "stok=stok-" + tbNonMedis.getValueAt(i, 0).toString() + "", "kode_brng='" + tbNonMedis.getValueAt(i, 1).toString() + "'");
                                    tbNonMedis.setValueAt(null, i, 0);
                                    tbNonMedis.setValueAt(0, i, 4);
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Berhasil Simpan.");
                    tampil();
                    emptTeks();
                }
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, KodePetugasUSaring, BtnBatal);
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
        if (tabModeTranfusi.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else if (tbTranfusiDarah.getSelectedRow() <= -1) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data yang mau dihapus..!!");
        } else {
            if (!tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString().equals("")) {
                try {

                    pscekmedis = koneksi.prepareStatement(sqlpscekmedis);
                    try {
                        pscekmedis.setString(1, tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());
                        rs = pscekmedis.executeQuery();
                        while (rs.next()) {
                            Sequel.menyimpan("utd_stok_medis", "'" + rs.getString("kode_brng") + "','" + rs.getString("jml") + "','" + rs.getDouble("harga") + "'",
                                    "stok=stok+" + rs.getString("jml") + "", "kode_brng='" + rs.getString("kode_brng") + "'");
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (rs != null) {
                            rs.close();
                        }
                        if (pscekmedis != null) {
                            pscekmedis.close();
                        }
                    }

                    psceknonmedis = koneksi.prepareStatement(sqlpsceknonmedis);
                    try {
                        psceknonmedis.setString(1, tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());
                        rs2 = psceknonmedis.executeQuery();
                        while (rs2.next()) {
                            Sequel.menyimpan("utd_stok_penunjang", "'" + rs2.getString("kode_brng") + "','" + rs2.getString("jml") + "','" + rs2.getDouble("harga") + "'",
                                    "stok=stok+" + rs2.getString("jml") + "", "kode_brng='" + rs2.getString("kode_brng") + "'");
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    } finally {
                        if (rs2 != null) {
                            rs2.close();
                        }
                        if (psceknonmedis != null) {
                            psceknonmedis.close();
                        }
                    }

                    Sequel.meghapus("utd_donor", "no_donor", tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());

                    tampil();
                    emptTeks();
                } catch (Exception e) {
                    System.out.println("Notifikasi : " + e);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Maaf,Pilih pada nomor donor...!!!!");
            }
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
        if (NomorDonor.getText().trim().equals("")) {
            Valid.textKosong(NomorDonor, "Nomor Donor");
        } else if (NamaPendonor.getText().trim().equals("")) {
            Valid.textKosong(NamaPendonor, "Nama Pendonor");
        } else if (Umur.getText().trim().equals("")) {
            Valid.textKosong(Umur, "Umur");
        } else if (Tensi.getText().trim().equals("")) {
            Valid.textKosong(Tensi, "Tensi");
//        }else if(NomorBag.getText().trim().equals("")){
//            Valid.textKosong(NomorBag,"Nomor Bag");
        } else if (NomorTelp.getText().trim().equals("")) {
            Valid.textKosong(NomorTelp, "Nomor Selang");
        } else if (KodePetugasAftap.getText().trim().equals("") || NamaPetugasAftap.getText().trim().equals("")) {
            Valid.textKosong(KodePetugasAftap, "Petugas Aftap");
        } else if (KodePetugasUSaring.getText().trim().equals("") || NamaPetugasUSaring.getText().trim().equals("")) {
            Valid.textKosong(KodePetugasUSaring, "Petugas Uji Saring");
        } else if (tabModeTranfusi.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else if (tbTranfusiDarah.getSelectedRow() <= -1) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data yang mau dihapus..!!");
        } else {
            if (!tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString().equals("")) {
                String hbs = null;
                String hcv = null;
                String hiv = null;
                String spilis = null;
                String malar = null;
                if (HBSAg.getSelectedItem().toString() == "-") {
                    hbs = hbs;
                } else {
                    hbs = HBSAg.getSelectedItem().toString();
                }
                if (HCV.getSelectedItem().toString() == "-") {
                    hcv = hcv;
                } else {
                    hcv = HCV.getSelectedItem().toString();
                }
                if (HIV.getSelectedItem().toString() == "-") {
                    hiv = hiv;
                } else {
                    hiv = HIV.getSelectedItem().toString();
                }
                if (Spilis.getSelectedItem().toString() == "-") {
                    spilis = spilis;
                } else {
                    spilis = Spilis.getSelectedItem().toString();
                }
                if (Malaria.getSelectedItem().toString() == "-") {
                    malar = malar;
                } else {
                    malar = Malaria.getSelectedItem().toString();
                }
                if (Sequel.mengedittf("utd_donor", "no_donor=?", "no_donor=?,nama=?,tanggal=?,dinas=?,jk=?,"
                        + "umur=?,alamat=?,golongan_darah=?,resus=?,tensi=?,no_bag=?,no_telp=?,jenis_bag=?,"
                        + "jenis_donor=?,tempat_aftap=?,petugas_aftap=?,hbsag=?,hcv=?,hiv=?,spilis=?,malaria=?,"
                        + "petugas_u_saring=?,tgl_lahir=?,nik=?", 25, new String[]{
                            NomorDonor.getText(), NamaPendonor.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""),
                            Dinas.getSelectedItem().toString(), JK.getSelectedItem().toString().substring(0, 1), Umur.getText(),
                            Alamat.getText(), GolonganDarah.getSelectedItem().toString(), Resus.getSelectedItem().toString(),
                            Tensi.getText(), NomorBag.getText(), NomorTelp.getText(), JenisBag.getSelectedItem().toString(),
                            JenisDonor.getSelectedItem().toString(), TempatAftap.getSelectedItem().toString(),
                            KodePetugasAftap.getText(), hbs, hcv,
                            hiv, spilis,
                            malar, KodePetugasUSaring.getText(),
                            Valid.SetTgl(tgl_lahir.getSelectedItem() + ""), nik.getText(), tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString()
                        }) == true) {
                    try {
                        pscekmedis = koneksi.prepareStatement(sqlpscekmedis);
                        try {
                            pscekmedis.setString(1, tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());
                            rs = pscekmedis.executeQuery();
                            while (rs.next()) {
                                Sequel.menyimpan("utd_stok_medis", "'" + rs.getString("kode_brng") + "','" + rs.getString("jml") + "','" + rs.getDouble("harga") + "'",
                                        "stok=stok+" + rs.getString("jml") + "", "kode_brng='" + rs.getString("kode_brng") + "'");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs != null) {
                                rs.close();
                            }
                            if (pscekmedis != null) {
                                pscekmedis.close();
                            }
                        }
                        Sequel.meghapus("utd_penggunaan_medis_donor", "no_donor", tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());

                        psceknonmedis = koneksi.prepareStatement(sqlpsceknonmedis);
                        try {
                            psceknonmedis.setString(1, tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());
                            rs2 = psceknonmedis.executeQuery();
                            while (rs2.next()) {
                                Sequel.menyimpan("utd_stok_penunjang", "'" + rs2.getString("kode_brng") + "','" + rs2.getString("jml") + "','" + rs2.getDouble("harga") + "'",
                                        "stok=stok+" + rs2.getString("jml") + "", "kode_brng='" + rs2.getString("kode_brng") + "'");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psceknonmedis != null) {
                                psceknonmedis.close();
                            }
                        }
                        Sequel.meghapus("utd_penggunaan_penunjang_donor", "no_donor", tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());

                        for (i = 0; i < tbMedis.getRowCount(); i++) {
                            try {
                                if (Valid.SetAngka(tbMedis.getValueAt(i, 0).toString()) > 0) {
                                    if (Sequel.menyimpantf2("utd_penggunaan_medis_donor", "?,?,?,?,?", "BHP Medis", 5, new String[]{
                                        NomorDonor.getText(), tbMedis.getValueAt(i, 1).toString(), tbMedis.getValueAt(i, 0).toString(), tbMedis.getValueAt(i, 3).toString(),
                                        Double.toString(Double.parseDouble(tbMedis.getValueAt(i, 0).toString()) * Double.parseDouble(tbMedis.getValueAt(i, 3).toString()))
                                    }) == true) {
                                        Sequel.menyimpan("utd_stok_medis", "'" + tbMedis.getValueAt(i, 1).toString() + "','-" + tbMedis.getValueAt(i, 0).toString() + "','" + tbMedis.getValueAt(i, 3).toString() + "'",
                                                "stok=stok-" + tbMedis.getValueAt(i, 0).toString() + "", "kode_brng='" + tbMedis.getValueAt(i, 1).toString() + "'");
                                        tbMedis.setValueAt(null, i, 0);
                                        tbMedis.setValueAt(0, i, 4);
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        for (i = 0; i < tbNonMedis.getRowCount(); i++) {
                            try {
                                if (Valid.SetAngka(tbNonMedis.getValueAt(i, 0).toString()) > 0) {
                                    if (Sequel.menyimpantf2("utd_penggunaan_penunjang_donor", "?,?,?,?,?", "BHP Non Medis", 5, new String[]{
                                        NomorDonor.getText(), tbNonMedis.getValueAt(i, 1).toString(), tbNonMedis.getValueAt(i, 0).toString(), tbNonMedis.getValueAt(i, 3).toString(),
                                        Double.toString(Double.parseDouble(tbNonMedis.getValueAt(i, 0).toString()) * Double.parseDouble(tbNonMedis.getValueAt(i, 3).toString()))
                                    }) == true) {
                                        Sequel.menyimpan("utd_stok_penunjang", "'" + tbNonMedis.getValueAt(i, 1).toString() + "','-" + tbNonMedis.getValueAt(i, 0).toString() + "','" + tbNonMedis.getValueAt(i, 3).toString() + "'",
                                                "stok=stok-" + tbNonMedis.getValueAt(i, 0).toString() + "", "kode_brng='" + tbNonMedis.getValueAt(i, 1).toString() + "'");
                                        tbNonMedis.setValueAt(null, i, 0);
                                        tbNonMedis.setValueAt(0, i, 4);
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    }
                    emptTeks();
                    TabRawat.setSelectedIndex(1);
                    JOptionPane.showMessageDialog(null, "Proses input selesai..");
                }
                tampil();
            } else {
                JOptionPane.showMessageDialog(null, "Maaf,Pilih pada nomor donor...!!!!");
            }
        }
    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabModeTranfusi.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        } else if (tabModeTranfusi.getRowCount() != 0) {
            Sequel.queryu("truncate table temporary");
            jml = tabModeTranfusi.getRowCount();
            for (i = 0; i < jml; i++) {
                Sequel.menyimpan("temporary", "'0','"
                        + tabModeTranfusi.getValueAt(i, 0).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 1).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 2).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 3).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 4).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 5).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 6).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 7).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 8).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 9).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 10).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 11).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 12).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 13).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 14).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 15).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 16).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 17).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 18).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 19).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 20).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 21).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 22).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 23).toString() + "','','','','','','','','','','','','',''", "Transaksi Pembelian");
            }

            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptDonorDarah.jrxml", "report", "::[ Data Donor Darah ]::",
                    "select * from temporary order by no asc", param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        aktifkan = "";
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            tampil();
            TCari.setText("");
        } else {
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnAll, TCari);
        }
    }//GEN-LAST:event_BtnKeluarKeyPressed

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
        if (TabRawat.getSelectedIndex() == 1) {
            tampil();
        } else if (TabRawat.getSelectedIndex() == 0) {
            tampilMedis();
            tampilNonMedis();
        } else {
            tampilTotalDonor();
        };
//       // aktifkan = "";
//        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//        if (TabRawat.getSelectedIndex()==1){
//            tampil();
////            System.out.println(TabRawat.getSelectedIndex());
//        } else if(TabRawat.getSelectedIndex()==2){
//            tampilTotalDonor();
////            System.out.println("Ini Tab Daftar Donor");
//        }
//        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt, NomorDonor, Dinas);
//        autoNomer();
        isNumber();
    }//GEN-LAST:event_TanggalKeyPressed

    private void AlamatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlamatKeyPressed
        Valid.pindah(evt, Umur, GolonganDarah);
    }//GEN-LAST:event_AlamatKeyPressed

    private void TensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TensiKeyPressed
        Valid.pindah(evt, Resus, NomorBag);
    }//GEN-LAST:event_TensiKeyPressed

    private void NomorTelpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NomorTelpKeyPressed
        Valid.pindah(evt, JenisBag, JenisDonor);
    }//GEN-LAST:event_NomorTelpKeyPressed

    private void KodePetugasAftapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodePetugasAftapKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select nama from petugas where nip=?", NamaPetugasAftap, KodePetugasAftap.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnPetugasAftapActionPerformed(null);
        } else {
            Valid.pindah(evt, TempatAftap, HBSAg);
        }
    }//GEN-LAST:event_KodePetugasAftapKeyPressed

    private void btnPetugasAftapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasAftapActionPerformed
        pilih = 1;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasAftapActionPerformed

    private void HBSAgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HBSAgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HBSAgActionPerformed

    private void KodePetugasUSaringKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodePetugasUSaringKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select nama from petugas where nip=?", NamaPetugasUSaring, KodePetugasUSaring.getText());
            TCariMedis.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnPetugasUSaringActionPerformed(null);
        } else {
            Valid.pindah(evt, Malaria, BtnSimpan);
        }
    }//GEN-LAST:event_KodePetugasUSaringKeyPressed

    private void btnPetugasUSaringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasUSaringActionPerformed
        pilih = 2;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasUSaringActionPerformed

    private void DinasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DinasKeyPressed
        Valid.pindah(evt, Tanggal, NamaPendonor);
    }//GEN-LAST:event_DinasKeyPressed

    private void JKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JKKeyPressed
        Valid.pindah(evt, NamaPendonor, Umur);
    }//GEN-LAST:event_JKKeyPressed

    private void GolonganDarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GolonganDarahKeyPressed
        Valid.pindah(evt, Alamat, Resus);
    }//GEN-LAST:event_GolonganDarahKeyPressed

    private void JenisBagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisBagKeyPressed
        Valid.pindah(evt, NomorBag, NomorTelp);
    }//GEN-LAST:event_JenisBagKeyPressed

    private void TempatAftapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TempatAftapKeyPressed
        Valid.pindah(evt, JenisDonor, KodePetugasAftap);
    }//GEN-LAST:event_TempatAftapKeyPressed

    private void HBSAgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HBSAgKeyPressed
        Valid.pindah(evt, KodePetugasAftap, HCV);
    }//GEN-LAST:event_HBSAgKeyPressed

    private void HCVKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HCVKeyPressed
        Valid.pindah(evt, HBSAg, HIV);
    }//GEN-LAST:event_HCVKeyPressed

    private void HIVKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HIVKeyPressed
        Valid.pindah(evt, HCV, Spilis);
    }//GEN-LAST:event_HIVKeyPressed

    private void SpilisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SpilisKeyPressed
        Valid.pindah(evt, HIV, Malaria);
    }//GEN-LAST:event_SpilisKeyPressed

    private void ppTampilkanBHPMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTampilkanBHPMedisActionPerformed
        aktifkan = "medis";
        tampil();
    }//GEN-LAST:event_ppTampilkanBHPMedisActionPerformed

    private void ppTampilkanBHPPenunjangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTampilkanBHPPenunjangActionPerformed
        aktifkan = "nonmedis";
        tampil();
    }//GEN-LAST:event_ppTampilkanBHPPenunjangActionPerformed

    private void ppTampilkanBHPPenunjangDanMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTampilkanBHPPenunjangDanMedisActionPerformed
        aktifkan = "medis&nonmedis";
        tampil();
    }//GEN-LAST:event_ppTampilkanBHPPenunjangDanMedisActionPerformed

    private void ppHapusBHPMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusBHPMedisActionPerformed
        if (tabModeTranfusi.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else if (tbTranfusiDarah.getSelectedRow() <= -1) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data yang mau dihapus..!!");
        } else {
            if (!tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString().equals("")) {
                int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, Yakin mau dihapus..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    try {

                        pscekmedis = koneksi.prepareStatement(sqlpscekmedis);
                        try {
                            pscekmedis.setString(1, tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());
                            rs = pscekmedis.executeQuery();
                            while (rs.next()) {
                                Sequel.menyimpan("utd_stok_medis", "'" + rs.getString("kode_brng") + "','" + rs.getString("jml") + "','" + rs.getDouble("harga") + "'",
                                        "stok=stok+" + rs.getString("jml") + "", "kode_brng='" + rs.getString("kode_brng") + "'");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs != null) {
                                rs.close();
                            }
                            if (pscekmedis != null) {
                                pscekmedis.close();
                            }
                        }
                        Sequel.meghapus("utd_penggunaan_medis_donor", "no_donor", tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());

                        tampil();
                        emptTeks();
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Maaf,Pilih pada nomor donor...!!!!");
            }
        }
    }//GEN-LAST:event_ppHapusBHPMedisActionPerformed

    private void ppHapusBHPNonMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusBHPNonMedisActionPerformed
        if (tabModeTranfusi.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else if (tbTranfusiDarah.getSelectedRow() <= -1) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data yang mau dihapus..!!");
        } else {
            if (!tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString().equals("")) {
                int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, Yakin mau dihapus..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    try {

                        psceknonmedis = koneksi.prepareStatement(sqlpsceknonmedis);
                        try {
                            psceknonmedis.setString(1, tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());
                            rs2 = psceknonmedis.executeQuery();
                            while (rs2.next()) {
                                Sequel.menyimpan("utd_stok_penunjang", "'" + rs2.getString("kode_brng") + "','" + rs2.getString("jml") + "','" + rs2.getDouble("harga") + "'",
                                        "stok=stok+" + rs2.getString("jml") + "", "kode_brng='" + rs2.getString("kode_brng") + "'");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psceknonmedis != null) {
                                psceknonmedis.close();
                            }
                        }
                        Sequel.meghapus("utd_penggunaan_penunjang_donor", "no_donor", tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());

                        tampil();
                        emptTeks();
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Maaf,Pilih pada nomor donor...!!!!");
            }
        }
    }//GEN-LAST:event_ppHapusBHPNonMedisActionPerformed

    private void ppHapusBHPMedisDanNonMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusBHPMedisDanNonMedisActionPerformed
        if (tabModeTranfusi.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else if (tbTranfusiDarah.getSelectedRow() <= -1) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data yang mau dihapus..!!");
        } else {
            if (!tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString().equals("")) {
                int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, Yakin mau dihapus..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    try {

                        pscekmedis = koneksi.prepareStatement(sqlpscekmedis);
                        try {
                            pscekmedis.setString(1, tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());
                            rs = pscekmedis.executeQuery();
                            while (rs.next()) {
                                Sequel.menyimpan("utd_stok_medis", "'" + rs.getString("kode_brng") + "','" + rs.getString("jml") + "','" + rs.getDouble("harga") + "'",
                                        "stok=stok+" + rs.getString("jml") + "", "kode_brng='" + rs.getString("kode_brng") + "'");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs != null) {
                                rs.close();
                            }
                            if (pscekmedis != null) {
                                pscekmedis.close();
                            }
                        }
                        Sequel.meghapus("utd_penggunaan_medis_donor", "no_donor", tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());
                        psceknonmedis = koneksi.prepareStatement(sqlpsceknonmedis);
                        try {
                            psceknonmedis.setString(1, tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());
                            rs2 = psceknonmedis.executeQuery();
                            while (rs2.next()) {
                                Sequel.menyimpan("utd_stok_penunjang", "'" + rs2.getString("kode_brng") + "','" + rs2.getString("jml") + "','" + rs2.getDouble("harga") + "'",
                                        "stok=stok+" + rs2.getString("jml") + "", "kode_brng='" + rs2.getString("kode_brng") + "'");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psceknonmedis != null) {
                                psceknonmedis.close();
                            }
                        }
                        Sequel.meghapus("utd_penggunaan_penunjang_donor", "no_donor", tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());

                        tampil();
                        emptTeks();
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Maaf,Pilih pada nomor donor...!!!!");
            }
        }
    }//GEN-LAST:event_ppHapusBHPMedisDanNonMedisActionPerformed

    private void ppUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppUbahActionPerformed
        if (tabModeTranfusi.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else if (tbTranfusiDarah.getSelectedRow() <= -1) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data yang mau diubah..!!");
        } else {
            if (!tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString().equals("")) {
                if (tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 12).toString().equals("")) {
                    TabRawat.setSelectedIndex(0);

                    TempatAftap.setVisible(true);
                    jLabel14.setVisible(true);
                    NomorBag.setVisible(true);
                    label34.setVisible(true);
                    GolonganDarah.setVisible(true);
                    jLabel10.setVisible(true);
                    Resus.setVisible(true);
                    jLabel11.setVisible(true);
                    Tensi.setVisible(true);
                    label39.setVisible(true);
                    jLabel12.setVisible(true);
                    JenisBag.setVisible(true);
                    JenisDonor.setVisible(true);
                    jLabel13.setVisible(true);
                    NamaPetugasAftap.setVisible(true);
                    KodePetugasAftap.setVisible(true);
                    label18.setVisible(true);
                    btnPetugasAftap.setVisible(true);
                    label17.setVisible(false);
                    HBSAg.setVisible(false);
                    jLabel15.setVisible(false);
                    HIV.setVisible(false);
                    jLabel17.setVisible(false);
                    Malaria.setVisible(false);
                    jLabel19.setVisible(false);
                    HCV.setVisible(false);
                    jLabel16.setVisible(false);
                    Spilis.setVisible(false);
                    jLabel18.setVisible(false);
                    KodePetugasUSaring.setVisible(false);
                    NamaPetugasUSaring.setVisible(false);
                    label19.setVisible(false);
                    btnPetugasUSaring.setVisible(false);

                    NomorDonor.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());
                    NamaPendonor.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 1).toString());
                    Valid.SetTgl(Tanggal, tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 3).toString());
                    Dinas.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 4).toString());
                    if (tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 5).toString().equals("L")) {
                        JK.setSelectedIndex(0);
                    } else {
                        JK.setSelectedIndex(1);
                    }
                    nik.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 2).toString());
                    Valid.SetTgl(tgl_lahir, tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 6).toString());
                    Umur.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 7).toString());
                    Alamat.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 8).toString());
//                    GolonganDarah.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(),9).toString());
//                    Resus.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(),10).toString());
                    Tensi.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 11).toString());
                    NomorBag.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 12).toString());
                    NomorTelp.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 13).toString());
//                    JenisBag.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(),14).toString());
//                    JenisDonor.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(),15).toString());
//                    TempatAftap.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(),16).toString());
                    NamaPetugasAftap.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 17).toString());
                    KodePetugasAftap.setText(Sequel.cariIsi("select petugas_aftap from utd_donor where no_donor=?", NomorDonor.getText()));
//                    HBSAg.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(),18).toString());
//                    HCV.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(),19).toString());
//                    HIV.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(),20).toString());
//                    Spilis.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(),21).toString());
//                    Malaria.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(),22).toString());
                    NamaPetugasUSaring.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 23).toString());
                    KodePetugasUSaring.setText(Sequel.cariIsi("select petugas_u_saring from utd_donor where no_donor=?", NomorDonor.getText()));
                    try {
                        Valid.tabelKosong(tabModeMedis);
                        pscekmedis = koneksi.prepareStatement(sqlpscekmedis);
                        try {
                            pscekmedis.setString(1, NomorDonor.getText());
                            rs = pscekmedis.executeQuery();
                            while (rs.next()) {
                                tabModeMedis.addRow(new String[]{
                                    rs.getString("jml"), rs.getString("kode_brng"), rs.getString("nama_brng"),
                                    rs.getString("harga"), rs.getString("total"), rs.getString("kode_sat"), "0"
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs != null) {
                                rs.close();
                            }
                            if (pscekmedis != null) {
                                pscekmedis.close();
                            }
                        }

                        Valid.tabelKosong(tabModeNonMedis);
                        psceknonmedis = koneksi.prepareStatement(sqlpsceknonmedis);
                        try {
                            psceknonmedis.setString(1, NomorDonor.getText());
                            rs2 = psceknonmedis.executeQuery();
                            while (rs2.next()) {
                                tabModeNonMedis.addRow(new String[]{
                                    rs2.getString("jml"), rs2.getString("kode_brng"), rs2.getString("nama_brng"),
                                    rs2.getString("harga"), rs2.getString("total"), rs2.getString("kode_sat"), "0"
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psceknonmedis != null) {
                                psceknonmedis.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Sudah Dilakukan Penginputan Aftap");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Maaf,Pilih pada nomor donor...!!!!");
            }
        }
    }//GEN-LAST:event_ppUbahActionPerformed

    private void ppCekalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppCekalActionPerformed
        if (tabModeTranfusi.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else if (tbTranfusiDarah.getSelectedRow() <= -1) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data darah yang mau dimusnahkan..!!");
        } else {
            if (!tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString().equals("")) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                UTDCekalDarah pemusnahan = new UTDCekalDarah(null, false);
                pemusnahan.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                pemusnahan.setLocationRelativeTo(internalFrame1);
                pemusnahan.emptTeks();
                pemusnahan.isCek();
                pemusnahan.setDarah(
                        tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString(),
                        tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 1).toString(),
                        tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 6).toString(),
                        tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 11).toString()
                );
                pemusnahan.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            } else {
                JOptionPane.showMessageDialog(null, "Maaf,Pilih pada nomor donor...!!!!");
            }
        }
    }//GEN-LAST:event_ppCekalActionPerformed

    private void MalariaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MalariaKeyPressed
        Valid.pindah(evt, Spilis, KodePetugasUSaring);
    }//GEN-LAST:event_MalariaKeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        if (panelisi4.getHeight() < 400) {
            scrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            panelisi4.setPreferredSize(new Dimension(panelisi4.WIDTH, 400));
            if (panelisi4.getWidth() < 530) {
                scrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                panelisi4.setPreferredSize(new Dimension(530, 400));
            } else {
                scrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            }
        } else {
            scrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
            if (panelisi4.getWidth() < 530) {
                scrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                panelisi4.setPreferredSize(new Dimension(530, panelisi4.WIDTH));
            } else {
                scrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            }
        }
    }//GEN-LAST:event_formWindowActivated

    private void tgl_lahirItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_tgl_lahirItemStateChanged
        // TODO add your handling code here:
        Date lahir = tgl_lahir.getDate();
        LocalDate today = LocalDate.now();
        LocalDate birthday = lahir.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period p = Period.between(birthday, today);
        long p2 = ChronoUnit.DAYS.between(birthday, today);
        Umur.setText(String.valueOf(p.getYears()));
    }//GEN-LAST:event_tgl_lahirItemStateChanged

    private void ppBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBaruActionPerformed
        if (tabModeTranfusi.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else if (tbTranfusiDarah.getSelectedRow() <= -1) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data yang mau diubah..!!");
        } else {
            if (!tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString().equals("")) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String inputString1 = tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 3).toString();
                LocalDate month = LocalDate.now();
                String inputString2 = month.format(dtf);
                System.out.println(inputString1);
                System.out.println(inputString2);
                try {
                    LocalDate date1 = LocalDate.parse(inputString1);
                    LocalDate date2 = LocalDate.parse(inputString2);
                    long daysBetween = DAYS.between(date1, date2);
                    System.out.println("Days: " + daysBetween);
                    if (daysBetween < 76) {
                        JOptionPane.showMessageDialog(null, "Maaf,Tidak Bisa Donor Sebelum Lewat 2,5 Bulan");
                    } else {
                        TabRawat.setSelectedIndex(0);

                        NamaPendonor.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 1).toString());
                        if (tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 5).toString().equals("L")) {
                            JK.setSelectedIndex(0);
                        } else {
                            JK.setSelectedIndex(1);
                        }
                        nik.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 2).toString());
                        Valid.SetTgl(tgl_lahir, tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 6).toString());
                        Umur.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 7).toString());
                        Alamat.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 8).toString());
                        GolonganDarah.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 9).toString());
                        NomorTelp.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 13).toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Maaf,Pilih pada nomor donor...!!!!");
            }
        }
    }//GEN-LAST:event_ppBaruActionPerformed

    private void BtnAll3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAll3ActionPerformed
        // TODO add your handling code here:
        tampilCari();
    }//GEN-LAST:event_BtnAll3ActionPerformed

    private void BtnEdit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEdit1ActionPerformed
        // TODO add your handling code here:
        BtnEditActionPerformed(evt);
    }//GEN-LAST:event_BtnEdit1ActionPerformed

    private void BtnEdit1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEdit1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnEdit1KeyPressed

    private void ppSaringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSaringActionPerformed
        // TODO add your handling code here:
        if (tabModeTranfusi.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else if (tbTranfusiDarah.getSelectedRow() <= -1) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data yang mau diubah..!!");
        } else {
            if (!tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString().equals("")) {
                if (!tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 12).toString().equals("") && tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 18).toString().equals("-")) {
                    TabRawat.setSelectedIndex(0);

                    TempatAftap.setVisible(true);
                    jLabel14.setVisible(true);
                    NomorBag.setVisible(true);
                    label34.setVisible(true);
                    GolonganDarah.setVisible(true);
                    jLabel10.setVisible(true);
                    Resus.setVisible(true);
                    jLabel11.setVisible(true);
                    Tensi.setVisible(true);
                    label39.setVisible(true);
                    jLabel12.setVisible(true);
                    JenisBag.setVisible(true);
                    JenisDonor.setVisible(true);
                    jLabel13.setVisible(true);
                    NamaPetugasAftap.setVisible(true);
                    KodePetugasAftap.setVisible(true);
                    label18.setVisible(true);
                    btnPetugasAftap.setVisible(true);
                    label17.setVisible(true);
                    HBSAg.setVisible(true);
                    jLabel15.setVisible(true);
                    HIV.setVisible(true);
                    jLabel17.setVisible(true);
                    Malaria.setVisible(true);
                    jLabel19.setVisible(true);
                    HCV.setVisible(true);
                    jLabel16.setVisible(true);
                    Spilis.setVisible(true);
                    jLabel18.setVisible(true);
                    KodePetugasUSaring.setVisible(true);
                    NamaPetugasUSaring.setVisible(true);
                    label19.setVisible(true);
                    btnPetugasUSaring.setVisible(true);

                    NomorDonor.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());
                    NamaPendonor.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 1).toString());
                    Valid.SetTgl(Tanggal, tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 3).toString());
                    Dinas.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 4).toString());
                    if (tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 5).toString().equals("L")) {
                        JK.setSelectedIndex(0);
                    } else {
                        JK.setSelectedIndex(1);
                    }
                    nik.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 2).toString());
                    Valid.SetTgl(tgl_lahir, tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 6).toString());
                    Umur.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 7).toString());
                    Alamat.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 8).toString());
                    GolonganDarah.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 9).toString());
                    Resus.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 10).toString());
                    Tensi.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 11).toString());
                    NomorBag.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 12).toString());
                    NomorTelp.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 13).toString());
                    JenisBag.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 14).toString());
                    JenisDonor.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 15).toString());
                    TempatAftap.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 16).toString());
                    NamaPetugasAftap.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 17).toString());
                    KodePetugasAftap.setText(Sequel.cariIsi("select petugas_aftap from utd_donor where no_donor=?", NomorDonor.getText()));
//                    HBSAg.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(),18).toString());
//                    HCV.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(),19).toString());
//                    HIV.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(),20).toString());
//                    Spilis.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(),21).toString());
//                    Malaria.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(),22).toString());
                    NamaPetugasUSaring.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 23).toString());
                    KodePetugasUSaring.setText(Sequel.cariIsi("select petugas_u_saring from utd_donor where no_donor=?", NomorDonor.getText()));
                    try {
                        Valid.tabelKosong(tabModeMedis);
                        pscekmedis = koneksi.prepareStatement(sqlpscekmedis);
                        try {
                            pscekmedis.setString(1, NomorDonor.getText());
                            rs = pscekmedis.executeQuery();
                            while (rs.next()) {
                                tabModeMedis.addRow(new String[]{
                                    rs.getString("jml"), rs.getString("kode_brng"), rs.getString("nama_brng"),
                                    rs.getString("harga"), rs.getString("total"), rs.getString("kode_sat"), "0"
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs != null) {
                                rs.close();
                            }
                            if (pscekmedis != null) {
                                pscekmedis.close();
                            }
                        }

                        Valid.tabelKosong(tabModeNonMedis);
                        psceknonmedis = koneksi.prepareStatement(sqlpsceknonmedis);
                        try {
                            psceknonmedis.setString(1, NomorDonor.getText());
                            rs2 = psceknonmedis.executeQuery();
                            while (rs2.next()) {
                                tabModeNonMedis.addRow(new String[]{
                                    rs2.getString("jml"), rs2.getString("kode_brng"), rs2.getString("nama_brng"),
                                    rs2.getString("harga"), rs2.getString("total"), rs2.getString("kode_sat"), "0"
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psceknonmedis != null) {
                                psceknonmedis.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Sudah Dilakukan Penginputan Uji Saring / Belum Dilakukan Aftap");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Maaf,Pilih pada nomor donor...!!!!");
            }
        }
    }//GEN-LAST:event_ppSaringActionPerformed

    private void NomorDonorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NomorDonorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NomorDonorActionPerformed

    private void printWBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printWBActionPerformed
        if (tabModeTranfusi.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else if (tbTranfusiDarah.getSelectedRow() <= -1) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data yang mau diubah..!!");
        } else {
            TabRawat.setSelectedIndex(2);
            tampilTotalDonor();
        }
    }//GEN-LAST:event_printWBActionPerformed

    private void ScrollMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ScrollMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_ScrollMouseClicked

    private void tbTotalDonorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTotalDonorMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbTotalDonorMouseClicked

    private void tbTotalDonorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTotalDonorKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbTotalDonorKeyPressed

    private void Scroll3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Scroll3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Scroll3MouseClicked

    private void JenisBagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JenisBagActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JenisBagActionPerformed

    private void TCariNonMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TCariNonMedisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TCariNonMedisActionPerformed

    private void TanggalCari2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TanggalCari2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalCari2ActionPerformed

    private void nikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nikActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nikActionPerformed

    private void BtnWAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnWAActionPerformed
        try {
            System.out.println("Mulai Mencari Data Pasien Yang Akan Dikirim Pesan WA .........");
            LocalDate d = LocalDate.now().minusMonths(3);
            LocalDate d2 = LocalDate.now().minusMonths(4);
            String tgl = d.toString();
            String tgl2 = d2.toString();
            String query = "select nama, no_telp, jk, tanggal, datediff(now(),tanggal) as hari from utd_donor where tanggal < '"+tgl+"' and tanggal > '"+tgl2+"' order by tanggal ASC";
            ps3 = koneksi.prepareStatement(query);
            try {
                rs3 = ps3.executeQuery();
                while (rs3.next()) {
                    System.out.println("Mulai Memilah Data .........");
                    if (rs3.getString("jk").equals("L")) {
                        if (rs3.getInt("hari") >= 76) {
                            if(rs3.getString("no_telp").length() > 11){
                                System.out.println("Mulai Mengirim WA");
                                kirimwa.sendwaUTD(rs3.getString("nama"), rs3.getString("no_telp"), rs3.getString("tanggal"));    
                            } 
                        }
                    }
                    if (rs3.getString("jk").equals("P")) {
                        if (rs3.getInt("hari") >= 90) {
                            if(rs3.getString("no_telp").length() > 11){
                                System.out.println("Mulai Mengirim WA");
                                kirimwa.sendwaUTD(rs3.getString("nama"), rs3.getString("no_telp"), rs3.getString("tanggal"));    
                            }   
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs3 != null) {
                    rs3.close();
                }
                if (ps3 != null) {
                    ps3.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }
        JOptionPane.showMessageDialog(null,"Proses kirim whatsapp selesai...");
    }//GEN-LAST:event_BtnWAActionPerformed

    private void BtnWAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnWAKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnWAKeyPressed

    private void TanggalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TanggalItemStateChanged
        isNumber();
    }//GEN-LAST:event_TanggalItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            UTDDonor dialog = new UTDDonor(new javax.swing.JFrame(), true);
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
    private widget.TextBox Alamat;
    private widget.Button BtnAll;
    private widget.Button BtnAll1;
    private widget.Button BtnAll2;
    private widget.Button BtnAll3;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCari2;
    private widget.Button BtnCari3;
    private widget.Button BtnEdit;
    private widget.Button BtnEdit1;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnWA;
    private widget.ComboBox Dinas;
    private widget.ComboBox GolonganDarah;
    private widget.ComboBox HBSAg;
    private widget.ComboBox HCV;
    private widget.ComboBox HIV;
    private widget.ComboBox JK;
    private widget.ComboBox JenisBag;
    private widget.ComboBox JenisDonor;
    private widget.TextBox KodePetugasAftap;
    private widget.TextBox KodePetugasUSaring;
    private widget.Label LCount;
    private widget.ComboBox Malaria;
    private widget.TextBox NamaPendonor;
    private widget.TextBox NamaPetugasAftap;
    private widget.TextBox NamaPetugasUSaring;
    private widget.TextBox NomorBag;
    private widget.TextBox NomorDonor;
    private widget.TextBox NomorTelp;
    private widget.ComboBox Resus;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.ComboBox Spilis;
    private widget.TextBox TCari;
    private widget.TextBox TCariMedis;
    private widget.TextBox TCariNonMedis;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tanggal;
    private widget.Tanggal TanggalCari1;
    private widget.Tanggal TanggalCari2;
    private widget.ComboBox TempatAftap;
    private widget.TextBox Tensi;
    private widget.TextBox Umur;
    private widget.Button btnPetugasAftap;
    private widget.Button btnPetugasUSaring;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
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
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label32;
    private widget.Label label33;
    private widget.Label label34;
    private widget.Label label35;
    private widget.Label label36;
    private widget.Label label37;
    private widget.Label label38;
    private widget.Label label39;
    private widget.Label label40;
    private widget.Label label41;
    private widget.Label label42;
    private widget.TextBox nik;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.panelisi panelisi4;
    private widget.panelisi panelisi5;
    private widget.panelisi panelisi6;
    private widget.panelisi panelisi7;
    private javax.swing.JMenuItem ppBaru;
    private javax.swing.JMenuItem ppCekal;
    private javax.swing.JMenuItem ppHapusBHPMedis;
    private javax.swing.JMenuItem ppHapusBHPMedisDanNonMedis;
    private javax.swing.JMenuItem ppHapusBHPNonMedis;
    private javax.swing.JMenuItem ppSaring;
    private javax.swing.JMenuItem ppTampilkanBHPMedis;
    private javax.swing.JMenuItem ppTampilkanBHPPenunjang;
    private javax.swing.JMenuItem ppTampilkanBHPPenunjangDanMedis;
    private javax.swing.JMenuItem ppUbah;
    private javax.swing.JMenuItem printWB;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbMedis;
    private widget.Table tbNonMedis;
    private widget.Table tbTotalDonor;
    private widget.Table tbTranfusiDarah;
    private widget.Tanggal tgl_lahir;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabModeTranfusi);
        try {
            pstranfusi = koneksi.prepareStatement(
                    "select * from utd_donor where tanggal between ? and ? and no_donor like ? or "
                    + "tanggal between ? and ? and nama like ? or "
                    + "tanggal between ? and ? and alamat like ? or "
                    + "tanggal between ? and ? and jenis_donor like ? or "
                    + "tanggal between ? and ? and tempat_aftap like ? or "
                    + "tanggal between ? and ? and jenis_bag like ? or "
                    + "tanggal between ? and ? and dinas like ? order by tanggal,no_donor "
            );
            try {
                pstranfusi.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                pstranfusi.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                pstranfusi.setString(3, "%" + TCari.getText().trim() + "%");
                pstranfusi.setString(4, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                pstranfusi.setString(5, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                pstranfusi.setString(6, "%" + TCari.getText().trim() + "%");
                pstranfusi.setString(7, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                pstranfusi.setString(8, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                pstranfusi.setString(9, "%" + TCari.getText().trim() + "%");
                pstranfusi.setString(10, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                pstranfusi.setString(11, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                pstranfusi.setString(12, "%" + TCari.getText().trim() + "%");
                pstranfusi.setString(13, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                pstranfusi.setString(14, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                pstranfusi.setString(15, "%" + TCari.getText().trim() + "%");
                pstranfusi.setString(16, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                pstranfusi.setString(17, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                pstranfusi.setString(18, "%" + TCari.getText().trim() + "%");
                pstranfusi.setString(19, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                pstranfusi.setString(20, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                pstranfusi.setString(21, "%" + TCari.getText().trim() + "%");
                rstranfusi = pstranfusi.executeQuery();
                while (rstranfusi.next()) {
                    String hbsag = "-";
                    if (rstranfusi.getString("hbsag") == null) {
                        hbsag = "-";
                    } else {
                        hbsag = rstranfusi.getString("hbsag");
                    }
                    tabModeTranfusi.addRow(new Object[]{
                        rstranfusi.getString("no_donor"), rstranfusi.getString("nama"), rstranfusi.getString("nik"), rstranfusi.getString("tanggal"),
                        rstranfusi.getString("dinas"), rstranfusi.getString("jk"), rstranfusi.getString("tgl_lahir"), rstranfusi.getString("umur"),
                        rstranfusi.getString("alamat"),
                        rstranfusi.getString("golongan_darah"), rstranfusi.getString("resus"),
                        rstranfusi.getString("tensi"), rstranfusi.getString("no_bag"), rstranfusi.getString("no_telp"),
                        rstranfusi.getString("jenis_bag"), rstranfusi.getString("jenis_donor"), rstranfusi.getString("tempat_aftap"),
                        Sequel.cariIsi("select nama from petugas where nip=?", rstranfusi.getString("petugas_aftap")), hbsag, rstranfusi.getString("hcv"),
                        rstranfusi.getString("hiv"), rstranfusi.getString("spilis"), rstranfusi.getString("malaria"),
                        Sequel.cariIsi("select nama from petugas where nip=?", rstranfusi.getString("petugas_u_saring"))
                    });
                    if (aktifkan.equals("medis")) {
                        pscekmedis = koneksi.prepareStatement(sqlpscekmedis);
                        try {
                            pscekmedis.setString(1, rstranfusi.getString("no_donor"));
                            rs = pscekmedis.executeQuery();
                            if (rs.next()) {
                                i = 1;
                                tabModeTranfusi.addRow(new String[]{
                                    "", "", "", "", "", "", "", "Penggunaan BHP Medis :", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
                                });
                            }
                            rs.beforeFirst();
                            while (rs.next()) {
                                tabModeTranfusi.addRow(new String[]{
                                    "", "", "", "", "", "", "", i + ". " + rs.getString("nama_brng") + " (" + rs.getString("jml") + " " + rs.getString("kode_sat") + " X " + Valid.SetAngka(rs.getDouble("harga")) + ") = " + Valid.SetAngka(rs.getDouble("total")), "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
                                });
                                i++;
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs != null) {
                                rs.close();
                            }
                            if (pscekmedis != null) {
                                pscekmedis.close();
                            }
                        }
                    } else if (aktifkan.equals("nonmedis")) {
                        psceknonmedis = koneksi.prepareStatement(sqlpsceknonmedis);
                        try {
                            psceknonmedis.setString(1, rstranfusi.getString("no_donor"));
                            rs2 = psceknonmedis.executeQuery();
                            if (rs2.next()) {
                                tabModeTranfusi.addRow(new String[]{
                                    "", "", "", "", "", "", "", "Penggunaan BHP Non Medis :", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
                                });
                                i = 1;
                            }
                            rs2.beforeFirst();
                            while (rs2.next()) {
                                tabModeTranfusi.addRow(new String[]{
                                    "", "", "", "", "", "", "", i + ". " + rs2.getString("nama_brng") + " (" + rs2.getString("jml") + " " + rs2.getString("kode_sat") + " X " + Valid.SetAngka(rs2.getDouble("harga")) + ") = " + Valid.SetAngka(rs2.getDouble("total")), "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
                                });
                                i++;
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psceknonmedis != null) {
                                psceknonmedis.close();
                            }
                        }
                    } else if (aktifkan.equals("medis&nonmedis")) {
                        pscekmedis = koneksi.prepareStatement(sqlpscekmedis);
                        try {
                            pscekmedis.setString(1, rstranfusi.getString("no_donor"));
                            rs = pscekmedis.executeQuery();
                            if (rs.next()) {
                                tabModeTranfusi.addRow(new String[]{
                                    "", "", "", "", "", "", "", "Penggunaan BHP Medis :", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
                                });
                                i = 1;
                            }
                            rs.beforeFirst();
                            while (rs.next()) {
                                tabModeTranfusi.addRow(new String[]{
                                    "", "", "", "", "", "", "", i + ". " + rs.getString("nama_brng") + " (" + rs.getString("jml") + " " + rs.getString("kode_sat") + " X " + Valid.SetAngka(rs.getDouble("harga")) + ") = " + Valid.SetAngka(rs.getDouble("total")), "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
                                });
                                i++;
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs != null) {
                                rs.close();
                            }
                            if (pscekmedis != null) {
                                pscekmedis.close();
                            }
                        }

                        psceknonmedis = koneksi.prepareStatement(sqlpsceknonmedis);
                        try {
                            psceknonmedis.setString(1, rstranfusi.getString("no_donor"));
                            rs2 = psceknonmedis.executeQuery();
                            if (rs2.next()) {
                                tabModeTranfusi.addRow(new String[]{
                                    "", "", "", "", "", "", "", "Penggunaan BHP Non Medis :", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
                                });
                                i = 1;
                            }
                            rs2.beforeFirst();
                            while (rs2.next()) {
                                tabModeTranfusi.addRow(new String[]{
                                    "", "", "", "", "", "", "", i + ". " + rs2.getString("nama_brng") + " (" + rs2.getString("jml") + " " + rs2.getString("kode_sat") + " X " + Valid.SetAngka(rs2.getDouble("harga")) + ") = " + Valid.SetAngka(rs2.getDouble("total")), "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
                                });
                                i++;
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psceknonmedis != null) {
                                psceknonmedis.close();
                            }
                        }
                    }
                }

            } catch (Exception e) {
                System.out.println(e);
            } finally {
                if (rstranfusi != null) {
                    rstranfusi.close();
                }
                if (pstranfusi != null) {
                    pstranfusi.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabModeTranfusi.getRowCount());
    }

    private void tampilCari() {
        Valid.tabelKosong(tabModeTranfusi);
        try {
            pstranfusi = koneksi.prepareStatement(
                    "select * from utd_donor where no_donor like ? or "
                    + "nama like ? or nik like ? or "
                    + "alamat like ? or "
                    + "jenis_donor like ? or "
                    + "tempat_aftap like ? or "
                    + "jenis_bag like ? or "
                    + "dinas like ? GROUP BY nik order by tanggal,no_donor "
            );
            try {
                pstranfusi.setString(1, "%" + TCari.getText().trim() + "%");
                pstranfusi.setString(2, "%" + TCari.getText().trim() + "%");
                pstranfusi.setString(3, "%" + TCari.getText().trim() + "%");
                pstranfusi.setString(4, "%" + TCari.getText().trim() + "%");
                pstranfusi.setString(5, "%" + TCari.getText().trim() + "%");
                pstranfusi.setString(6, "%" + TCari.getText().trim() + "%");
                pstranfusi.setString(7, "%" + TCari.getText().trim() + "%");
                pstranfusi.setString(8, "%" + TCari.getText().trim() + "%");
                rstranfusi = pstranfusi.executeQuery();
                while (rstranfusi.next()) {
                    tabModeTranfusi.addRow(new Object[]{
                        rstranfusi.getString("no_donor"), rstranfusi.getString("nama"), rstranfusi.getString("nik"), rstranfusi.getString("tanggal"),
                        rstranfusi.getString("dinas"), rstranfusi.getString("jk"), rstranfusi.getString("tgl_lahir"), rstranfusi.getString("umur"),
                        rstranfusi.getString("alamat"), rstranfusi.getString("golongan_darah"), rstranfusi.getString("resus"),
                        rstranfusi.getString("tensi"), rstranfusi.getString("no_bag"), rstranfusi.getString("no_telp"),
                        rstranfusi.getString("jenis_bag"), rstranfusi.getString("jenis_donor"), rstranfusi.getString("tempat_aftap"),
                        Sequel.cariIsi("select nama from petugas where nip=?", rstranfusi.getString("petugas_aftap")), rstranfusi.getString("hbsag"), rstranfusi.getString("hcv"),
                        rstranfusi.getString("hiv"), rstranfusi.getString("spilis"), rstranfusi.getString("malaria"),
                        Sequel.cariIsi("select nama from petugas where nip=?", rstranfusi.getString("petugas_u_saring"))
                    });
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                if (rstranfusi != null) {
                    rstranfusi.close();
                }
                if (pstranfusi != null) {
                    pstranfusi.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabModeTranfusi.getRowCount());
    }

    private void tampilMedis() {
        row = tbMedis.getRowCount();
        jml = 0;
        for (i = 0; i < row; i++) {
            try {
                if (Double.parseDouble(tbMedis.getValueAt(i, 0).toString()) > 0) {
                    jml++;
                }
            } catch (Exception e) {
                jml = jml + 0;
            }
        }

        kodebarang = null;
        namabarang = null;
        satuan = null;
        hbeli = null;
        total = null;
        jumlah = null;
        stokasal = null;

        kodebarang = new String[jml];
        namabarang = new String[jml];
        satuan = new String[jml];
        hbeli = new String[jml];
        total = new String[jml];
        jumlah = new String[jml];
        stokasal = new String[jml];
        index = 0;
        for (i = 0; i < row; i++) {
            try {
                if (Double.parseDouble(tbMedis.getValueAt(i, 0).toString()) > 0) {
                    jumlah[index] = tbMedis.getValueAt(i, 0).toString();
                    kodebarang[index] = tbMedis.getValueAt(i, 1).toString();
                    namabarang[index] = tbMedis.getValueAt(i, 2).toString();
                    hbeli[index] = tbMedis.getValueAt(i, 3).toString();
                    total[index] = tbMedis.getValueAt(i, 4).toString();
                    satuan[index] = tbMedis.getValueAt(i, 5).toString();
                    stokasal[index] = tbMedis.getValueAt(i, 6).toString();
                    index++;
                }
            } catch (Exception e) {
            }
        }
        Valid.tabelKosong(tabModeMedis);
        for (i = 0; i < jml; i++) {
            tabModeMedis.addRow(new Object[]{jumlah[i], kodebarang[i], namabarang[i], hbeli[i], total[i], satuan[i], stokasal[i]});
        }

        try {
            ps = koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,utd_stok_medis.hargaterakhir,databarang.kode_sat, "
                    + " utd_stok_medis.stok from databarang inner join utd_stok_medis on databarang.kode_brng=utd_stok_medis.kode_brng "
                    + " where databarang.status='1' and databarang.kode_brng like ? or "
                    + " databarang.status='1' and databarang.nama_brng like ? order by databarang.nama_brng");
            try {
                ps.setString(1, "%" + TCariMedis.getText().trim() + "%");
                ps.setString(2, "%" + TCariMedis.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeMedis.addRow(new Object[]{null, rs.getString(1), rs.getString(2), rs.getString(3), 0, rs.getString(4), rs.getString(5)});
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
    }

    private void tampilNonMedis() {
        row = tbNonMedis.getRowCount();
        jml = 0;
        for (i = 0; i < row; i++) {
            try {
                if (Double.parseDouble(tbNonMedis.getValueAt(i, 0).toString()) > 0) {
                    jml++;
                }
            } catch (Exception e) {
                jml = jml + 0;
            }
        }

        kodebarang = null;
        namabarang = null;
        satuan = null;
        hbeli = null;
        total = null;
        jumlah = null;
        stokasal = null;

        kodebarang = new String[jml];
        namabarang = new String[jml];
        satuan = new String[jml];
        hbeli = new String[jml];
        total = new String[jml];
        jumlah = new String[jml];
        stokasal = new String[jml];
        index = 0;
        for (i = 0; i < row; i++) {
            try {
                if (Double.parseDouble(tbNonMedis.getValueAt(i, 0).toString()) > 0) {
                    jumlah[index] = tbNonMedis.getValueAt(i, 0).toString();
                    kodebarang[index] = tbNonMedis.getValueAt(i, 1).toString();
                    namabarang[index] = tbNonMedis.getValueAt(i, 2).toString();
                    hbeli[index] = tbNonMedis.getValueAt(i, 3).toString();
                    total[index] = tbNonMedis.getValueAt(i, 4).toString();
                    satuan[index] = tbNonMedis.getValueAt(i, 5).toString();
                    stokasal[index] = tbNonMedis.getValueAt(i, 6).toString();
                    index++;
                }
            } catch (Exception e) {
            }
        }
        Valid.tabelKosong(tabModeNonMedis);
        for (i = 0; i < jml; i++) {
            tabModeNonMedis.addRow(new Object[]{jumlah[i], kodebarang[i], namabarang[i], hbeli[i], total[i], satuan[i], stokasal[i]});
        }

        try {
            ps2 = koneksi.prepareStatement("select ipsrsbarang.kode_brng, ipsrsbarang.nama_brng,utd_stok_penunjang.hargaterakhir,ipsrsbarang.kode_sat, "
                    + " utd_stok_penunjang.stok from ipsrsbarang inner join utd_stok_penunjang on ipsrsbarang.kode_brng=utd_stok_penunjang.kode_brng "
                    + " where ipsrsbarang.kode_brng like ? or ipsrsbarang.nama_brng like ? order by ipsrsbarang.nama_brng");
            try {
                ps2.setString(1, "%" + TCariNonMedis.getText().trim() + "%");
                ps2.setString(2, "%" + TCariNonMedis.getText().trim() + "%");
                rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    tabModeNonMedis.addRow(new Object[]{null, rs2.getString(1), rs2.getString(2), rs2.getString(3), 0, rs2.getString(4), rs2.getString(5)});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs2 != null) {
                    rs2.close();
                }
                if (ps2 != null) {
                    ps2.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    private void tampilTotalDonor() {
        Valid.tabelKosong(tabModeTotalDonor);
        try {
            psTotal = koneksi.prepareStatement("SELECT COUNT((nik)) as jumlah FROM utd_donor WHERE tanggal BETWEEN ? AND ?");
            psTotalLk = koneksi.prepareStatement("SELECT COUNT((nik)) as jumlah FROM utd_donor WHERE tanggal BETWEEN ? AND ? AND jk='L'");
            psTotalPr = koneksi.prepareStatement("SELECT COUNT((nik)) as jumlah FROM utd_donor WHERE tanggal BETWEEN ? AND ? AND jk='P'");
            psUmur1 = koneksi.prepareStatement("SELECT COUNT((nik)) as jumlah FROM utd_donor WHERE umur=17 AND tanggal BETWEEN ? AND ?");
            psUmur2 = koneksi.prepareStatement("SELECT COUNT((nik)) as jumlah FROM utd_donor WHERE umur BETWEEN 18 AND 24 AND tanggal BETWEEN ? AND ?");
            psUmur3 = koneksi.prepareStatement("SELECT COUNT((nik)) as jumlah FROM utd_donor WHERE umur BETWEEN 25 AND 44 AND tanggal BETWEEN ? AND ?");
            psUmur4 = koneksi.prepareStatement("SELECT COUNT((nik)) as jumlah FROM utd_donor WHERE umur BETWEEN 45 AND 64 AND tanggal BETWEEN ? AND ?");
            psUmur5 = koneksi.prepareStatement("SELECT COUNT((nik)) as jumlah FROM utd_donor WHERE umur>=65 AND tanggal BETWEEN ? AND ?");
            psOpos = koneksi.prepareStatement("SELECT COUNT((nik)) as jumlah FROM utd_donor WHERE tanggal BETWEEN ? AND ? AND golongan_darah='O' AND resus='(+)'");
            psOneg = koneksi.prepareStatement("SELECT COUNT(DISTINCT(nik)) as jumlah FROM utd_donor WHERE tanggal BETWEEN ? AND ? AND golongan_darah='O' AND resus='(-)'");
            psApos = koneksi.prepareStatement("SELECT COUNT(DISTINCT(nik)) as jumlah FROM utd_donor WHERE tanggal BETWEEN ? AND ? AND golongan_darah='A' AND resus='(+)'");
            psAneg = koneksi.prepareStatement("SELECT COUNT(DISTINCT(nik)) as jumlah FROM utd_donor WHERE tanggal BETWEEN ? AND ? AND golongan_darah='A' AND resus='(-)'");
            psBpos = koneksi.prepareStatement("SELECT COUNT(DISTINCT(nik)) as jumlah FROM utd_donor WHERE tanggal BETWEEN ? AND ? AND golongan_darah='B' AND resus='(+)'");
            psBneg = koneksi.prepareStatement("SELECT COUNT(DISTINCT(nik)) as jumlah FROM utd_donor WHERE tanggal BETWEEN ? AND ? AND golongan_darah='B' AND resus='(-)'");
            psABpos = koneksi.prepareStatement("SELECT COUNT(DISTINCT(nik)) as jumlah FROM utd_donor WHERE tanggal BETWEEN ? AND ? AND golongan_darah='AB' AND resus='(+)'");
            psABneg = koneksi.prepareStatement("SELECT COUNT(DISTINCT(nik)) as jumlah FROM utd_donor WHERE tanggal BETWEEN ? AND ? AND golongan_darah='AB' AND resus='(-)'");
            psjenisdb = koneksi.prepareStatement("SELECT COUNT((nik)) as jumlah FROM utd_donor WHERE jenis_bag='DB' and tanggal BETWEEN ? AND ?");
            psjenisdp = koneksi.prepareStatement("SELECT COUNT((nik)) as jumlah FROM utd_donor WHERE jenis_bag='DP' and tanggal BETWEEN ? AND ?");
            psjenisds = koneksi.prepareStatement("SELECT COUNT((nik)) as jumlah FROM utd_donor WHERE jenis_bag='DS' and tanggal BETWEEN ? AND ?");
            try {
                psTotal.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psTotal.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psTotalLk.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psTotalLk.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psTotalPr.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psTotalPr.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psUmur1.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psUmur1.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psUmur2.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psUmur2.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psUmur3.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psUmur3.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psUmur4.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psUmur4.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psUmur5.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psUmur5.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psOpos.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psOpos.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psOneg.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psOneg.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psApos.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psApos.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psAneg.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psAneg.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psBpos.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psBpos.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psBneg.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psBneg.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psABpos.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psABpos.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psABneg.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psABneg.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psjenisdb.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psjenisdb.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psjenisdp.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psjenisdp.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psjenisds.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psjenisds.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));

                rsTotal = psTotal.executeQuery();
                rsTotalLk = psTotalLk.executeQuery();
                rsTotalPr = psTotalPr.executeQuery();
                rsUmur1 = psUmur1.executeQuery();
                rsUmur2 = psUmur2.executeQuery();
                rsUmur3 = psUmur3.executeQuery();
                rsUmur4 = psUmur4.executeQuery();
                rsUmur5 = psUmur5.executeQuery();
                rsOpos = psOpos.executeQuery();
                rsOneg = psOneg.executeQuery();
                rsApos = psApos.executeQuery();
                rsAneg = psAneg.executeQuery();
                rsBpos = psBpos.executeQuery();
                rsBneg = psBneg.executeQuery();
                rsABpos = psABpos.executeQuery();
                rsABneg = psABneg.executeQuery();
                rsjenisdb = psjenisdb.executeQuery();
                rsjenisdp = psjenisdp.executeQuery();
                rsjenisds = psjenisds.executeQuery();
                while (rsTotal.next()) {
                    totaldonor = rsTotal.getString("jumlah");
                }
                while (rsTotalLk.next()) {
                    totallk = rsTotalLk.getString("jumlah");
                }
                while (rsTotalPr.next()) {
                    totalpr = rsTotalPr.getString("jumlah");
                }
                while (rsUmur1.next()) {
                    umur1 = rsUmur1.getString("jumlah");
                }
                while (rsUmur2.next()) {
                    umur2 = rsUmur2.getString("jumlah");
                }
                while (rsUmur3.next()) {
                    umur3 = rsUmur3.getString("jumlah");
                }
                while (rsUmur4.next()) {
                    umur4 = rsUmur4.getString("jumlah");
                }
                while (rsUmur5.next()) {
                    umur5 = rsUmur5.getString("jumlah");
                }
                while (rsOpos.next()) {
                    opos = rsOpos.getString("jumlah");
                }
                while (rsOneg.next()) {
                    oneg = rsOneg.getString("jumlah");
                }
                while (rsApos.next()) {
                    apos = rsApos.getString("jumlah");
                }
                while (rsAneg.next()) {
                    aneg = rsAneg.getString("jumlah");
                }
                while (rsBpos.next()) {
                    bpos = rsBpos.getString("jumlah");
                }
                while (rsBneg.next()) {
                    bneg = rsBneg.getString("jumlah");
                }
                while (rsABpos.next()) {
                    abpos = rsABpos.getString("jumlah");
                }
                while (rsABneg.next()) {
                    abneg = rsABneg.getString("jumlah");
                }
                while (rsjenisdb.next()) {
                    jenisdb = rsjenisdb.getString("jumlah");
                }
                while (rsjenisdp.next()) {
                    jenisdp = rsjenisdp.getString("jumlah");
                }
                while (rsjenisds.next()) {
                    jenisds = rsjenisds.getString("jumlah");
                }
            } catch (Exception e) {
            }
            tabModeTotalDonor.addRow(new Object[]{
                totaldonor, totallk, totalpr, jenisdb, jenisdp, jenisds, umur1, umur2, umur3, umur4, umur5, opos, oneg, apos, aneg, bpos, bneg, abpos, abneg
            });
        } catch (Exception e) {
        }
        LCount.setText("0");
    }

    public void emptTeks() {
//        autoNomer();
        isNumber();
        NamaPendonor.setText("");
        Umur.setText("");
        Alamat.setText("");
        Tensi.setText("");
        NomorBag.setText("");
        NomorTelp.setText("");
        nik.setText("");
        KodePetugasAftap.setText("");
        NamaPetugasAftap.setText("");
        KodePetugasUSaring.setText("");
        NamaPetugasUSaring.setText("");
//        NomorDonor.requestFocus(); 
        TempatAftap.setVisible(false);
        jLabel14.setVisible(false);
        NomorBag.setVisible(false);
        label34.setVisible(false);
        GolonganDarah.setVisible(false);
        jLabel10.setVisible(false);
        Resus.setVisible(false);
        jLabel11.setVisible(false);
        Tensi.setVisible(false);
        label39.setVisible(false);
        jLabel12.setVisible(false);
        JenisBag.setVisible(false);
        JenisDonor.setVisible(false);
        jLabel13.setVisible(false);
        NamaPetugasAftap.setVisible(false);
        KodePetugasAftap.setVisible(false);
        label18.setVisible(false);
        btnPetugasAftap.setVisible(false);
        label17.setVisible(false);
        HBSAg.setVisible(false);
        jLabel15.setVisible(false);
        HIV.setVisible(false);
        jLabel17.setVisible(false);
        Malaria.setVisible(false);
        jLabel19.setVisible(false);
        HCV.setVisible(false);
        jLabel16.setVisible(false);
        Spilis.setVisible(false);
        jLabel18.setVisible(false);
        KodePetugasUSaring.setVisible(false);
        NamaPetugasUSaring.setVisible(false);
        label19.setVisible(false);
        btnPetugasUSaring.setVisible(false);
        //Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_donor,4),signed)),0) from utd_donor where tanggal like '%"+Valid.SetTgl(Tanggal.getSelectedItem()+"").substring(0,7)+"%'",dateformat.format(Tanggal.getDate()).substring(0,7)+"/UTD",4,NomorDonor); 
    }

    private void autoNomer() {
//        String nomer_string;
////        int nomer;
//        int norut = 0;
//        String sql;
//        try {
//            sql = "select ifnull(MAX(CONVERT(RIGHT(no_donor,3),signed)),0) from utd_donor where tanggal='" + Valid.SetTgl(Tanggal.getSelectedItem() + "") + "'";
//            ps = koneksi.prepareStatement(sql);
//            try {
//                rs = ps.executeQuery();
//                while (rs.next()) {
//                    norut = Integer.parseInt(rs.getString(1)) + 1;
//                }
//            } catch (SQLException e) {
//                System.out.println("Notifikasi : " + e);
//            } finally {
//                if (rs != null) {
//                    rs.close();
//                }
//
//                if (ps != null) {
//                    ps.close();
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("Notifikasi : " + e);
//        }
//        String str = String.format("%03d", norut);
//        DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
//        String year = df.format(new Date());
//        DateFormat mm = new SimpleDateFormat("MM"); // Just the year, with 2 digits
//        String month = mm.format(new Date());
//        DateFormat dd = new SimpleDateFormat("dd"); // Just the year, with 2 digits
//        String day = dd.format(new Date());
//        nomer_string = year + month + day + str;
////        nomer = Integer.parseInt(nomer_string);
//        NomorDonor.setText(nomer_string);
////        return nomer;
                }

    public JTextField getTextField() {
        return NomorDonor;
    }

    public JButton getButton() {
        return BtnKeluar;
    }

    public JTable getTable() {
        return tbTranfusiDarah;
    }

    public void isCek() {
        BtnSimpan.setEnabled(var.getutd_donor());
        BtnHapus.setEnabled(var.getutd_donor());
        BtnEdit.setEnabled(var.getutd_donor());
        BtnPrint.setEnabled(var.getutd_donor());
        ppUbah.setEnabled(var.getutd_donor());
        ppHapusBHPMedis.setEnabled(var.getutd_donor());
        ppHapusBHPMedisDanNonMedis.setEnabled(var.getutd_donor());
        ppHapusBHPNonMedis.setEnabled(var.getutd_donor());
        ppCekal.setEnabled(var.getutd_cekal_darah());
        printWB.setEnabled(var.getutd_cekal_darah());
        if(var.getkode().equals("Admin Utama")){
            BtnWA.setEnabled(true);
        }else{
            BtnWA.setEnabled(false);
        }

    }

    private void isNumber() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_donor,3),signed)),0) from utd_donor where tanggal like '%"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"%'",dateformat.format(Tanggal.getDate()).substring(2,4)
                +dateformat.format(Tanggal.getDate()).substring(5,7)+dateformat.format(Tanggal.getDate()).substring(8,10),3,NomorDonor); 
    }
}
