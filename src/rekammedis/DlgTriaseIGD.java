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
package rekammedis;

import fungsi.WarnaTable;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;

/**
 *
 * @author perpustakaan
 */
public final class DlgTriaseIGD extends javax.swing.JDialog {

    private DefaultTableModel tabMode, tabModeLevel;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps, ps2;
    private ResultSet rs, rs2;
    private int i = 0, jml = 0, index = 0, jmlskala1 = 0;
//    private DlgMasterTriaseMacamKasus kasus = new DlgMasterTriaseMacamKasus(null, false);
    private boolean[] pilih;
    private String[] kodelevel, kodetindakan, level, pengkajian;
    private DlgCariDokter dokter = new DlgCariDokter(null, false);
    private DlgCariPetugas petugas = new DlgCariPetugas(null, false);
    private DlgMasterTriaseSkala triase = new DlgMasterTriaseSkala(null, false);
    private String pilihan = "", kodepetugas = "", norm = "", key = "", resiko = "", periksafisik = "",
            nmkasus = "", cmbantar = "", transport = "", fungsional = "";
    private boolean sukses = true;

    /**
     * Creates new form DlgRujuk
     *
     * @param parent
     * @param modal
     */
    public DlgTriaseIGD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8, 1);
        tabMode = new DefaultTableModel(null, new Object[]{
            "No.Rawat", "Tanggal", "Jam", "No.RM", "Nama Pasien", "J.K", "Tgl.Lahir", "Umur", "NIK", "Alamat", "No.Telp",
            "Agama", "Jns.Bayar", "Kasus", "Diantar", "Transportasi", "Fungsional", "Psikologis", "Tinggal Dgn",
            "Keluhan Utama", "Riwayat Penyakit", "TD", "Nadi", "RR", "Suhu", "Saturasi", "LK", "LILA","LD","LP",
            "TB", "BB", "IMT", "Edukasi", "Riwayat P.Dahulu", "Riwayat Pengobatan", "Riwayat Masuk RS", "Riwayat P.Keluarga",
            "Riwayat Operasi", "Riwayat Trauma", "Periksa Fisik", "Skala Nyeri", "Resiko Jatuh",
            "Nilai R.J", "Diagnosa Keperawatan", "Intervensi", "Diagnosis", "Tindakan", "Keterangan",
            "kd dokter", "Dokter", "kd petugas", "Petugas"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbTriase.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbTriase.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbTriase.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 53; i++) {
            TableColumn column = tbTriase.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(105);
            } else if (i == 1) {
                column.setPreferredWidth(70);
            } else if (i == 2) {
                column.setPreferredWidth(50);
            } else if (i == 3) {
                column.setPreferredWidth(50);
            } else if (i == 4) {
                column.setPreferredWidth(200);
            } else if (i == 5) {
                column.setPreferredWidth(30);
            } else if (i == 6) {
                column.setPreferredWidth(65);
            } else if (i == 7) {
                column.setPreferredWidth(50);
            } else if (i == 8) {
                column.setPreferredWidth(100);
            } else if (i == 9) {
                column.setPreferredWidth(175);
            } else if (i == 10) {
                column.setPreferredWidth(80);
            } else if (i == 11) {
                column.setPreferredWidth(60);
            } else if (i == 12) {
                column.setPreferredWidth(70);
            } else if (i == 13) {
                column.setPreferredWidth(145);
            } else if (i == 14) {
                column.setPreferredWidth(110);
            } else if (i == 15) {
                column.setPreferredWidth(110);
            } else if (i == 16) {
                column.setPreferredWidth(110);
            } else if (i == 17) {
                column.setPreferredWidth(110);
            } else if (i == 18) {
                column.setPreferredWidth(110);
            } else if (i == 19) {
                column.setPreferredWidth(120);
            } else if (i == 20) {
                column.setPreferredWidth(150);
            } else if (i == 21) {
                column.setPreferredWidth(45);
            } else if (i == 22) {
                column.setPreferredWidth(40);
            } else if (i == 23) {
                column.setPreferredWidth(40);
            } else if (i == 24) {
                column.setPreferredWidth(40);
            } else if (i == 25) {
                column.setPreferredWidth(55);
            } else if ((i == 26)|| (i==28) || (i==29)) {
                column.setPreferredWidth(25);
            } else if (i == 27) {
                column.setPreferredWidth(40);
            } else if (i == 30) {
                column.setPreferredWidth(25);
            } else if (i == 31) {
                column.setPreferredWidth(25);
            } else if (i == 32) {
                column.setPreferredWidth(30);
            } else if (i == 33) {
                column.setPreferredWidth(150);
            } else if (i == 34) {
                column.setPreferredWidth(155);
            } else if (i == 35) {
                column.setPreferredWidth(150);
            } else if (i == 36) {
                column.setPreferredWidth(150);
            } else if (i == 37) {
                column.setPreferredWidth(150);
            } else if (i == 38) {
                column.setPreferredWidth(150);
            } else if (i == 39) {
                column.setPreferredWidth(150);
            } else if (i == 40) {
                column.setPreferredWidth(200);
            } else if (i == 41) {
                column.setPreferredWidth(90);
            } else if (i == 42) {
                column.setPreferredWidth(120);
            } else if (i == 43) {
                column.setPreferredWidth(120);
            } else if (i == 44) {
                column.setPreferredWidth(130);
            } else if (i == 45) {
                column.setPreferredWidth(100);
            } else if (i == 46) {
                column.setPreferredWidth(100);
            } else if (i == 47) {
                column.setPreferredWidth(100);
            } else if (i == 48) {
                column.setPreferredWidth(100);
            } else if (i == 49) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 50) {
                column.setPreferredWidth(130);
            } else if (i == 51) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 52) {
                column.setPreferredWidth(120);
            }
        }
        tbTriase.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeLevel = new DefaultTableModel(null, new Object[]{
            "P", "Kode Level", "Level", "Kode Tindakan", "Nama Pemeriksaan"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbLevel.setModel(tabModeLevel);
        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbLevel.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbLevel.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 5; i++) {
            TableColumn column = tbLevel.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 2) {
                column.setPreferredWidth(60);
            } else if (i == 3) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 4) {
                column.setPreferredWidth(280);
            }
        }
        tbLevel.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

                String stts_rawat = (String) table.getModel().getValueAt(row, 2);

                if ("Level 1".equals(stts_rawat)) {
                    setBackground(new Color(170, 00, 0));
                    setForeground(Color.BLACK);
                } else if ("Level 2".equals(stts_rawat) || "Level 3".equals(stts_rawat) || "Level 4".equals(stts_rawat)) {
                    setBackground(new Color(242, 242, 70));
                    setForeground(Color.BLACK);
                } else if ("Level 5".equals(stts_rawat)) {
                    setBackground(new Color(131, 189, 69));
                    setForeground(Color.BLACK);
                } else {
                    if (row % 2 == 1) {
                        setForeground(Color.BLACK);
                        setBackground(new Color(226, 234, 248));
                    } else {
                        setForeground(Color.BLACK);
                        setBackground(new Color(255, 255, 255));
                    }
                }

                if (isSelected) {
                    setForeground(new Color(37, 250, 162));
                }
                return this;
            }
        });

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
                    if (index == 1) {
                        KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                        NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                        btnDokter.requestFocus();
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
                    if (index == 1) {
                        KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                        NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                        btnPetugas.requestFocus();
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

        BB.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isIMT();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isIMT();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                isIMT();
            }
        });

        TB.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isIMT();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isIMT();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                isIMT();
            }
        });

        chkMasukRS.setSelected(false);
        R1.setVisible(false);
        R2.setVisible(false);
        R3.setVisible(false);
        R4.setVisible(false);
        R5.setVisible(false);
        R6.setVisible(false);
        R7.setVisible(false);
        R8.setVisible(false);
        chkKepala.setSelected(false);
        Kepala.setVisible(false);
        chkMata.setSelected(false);
        Mata.setVisible(false);
        chkTHT.setSelected(false);
        THT.setVisible(false);
        chkMulut.setSelected(false);
        Mulut.setVisible(false);
        chkLeher.setSelected(false);
        Leher.setVisible(false);
        chkDada.setSelected(false);
        Dada.setVisible(false);
        chkAbdomen.setSelected(false);
        Abdomen.setVisible(false);
        chkPunggung.setSelected(false);
        Punggung.setVisible(false);
        chkGenetalia.setSelected(false);
        Genetalia.setVisible(false);
        chkEkstrimitas.setSelected(false);
        Ekstrimitas.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        LoadHTML2 = new widget.editorpane();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCetakTriase = new javax.swing.JMenuItem();
        Kd2 = new widget.TextBox();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        TNoRw2 = new widget.TextBox();
        TResiko = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        TabPilihan = new javax.swing.JTabbedPane();
        ScrollTriase = new widget.ScrollPane();
        FormTriase = new widget.InternalFrame();
        internalFrame5 = new widget.InternalFrame();
        internalFrame3 = new widget.InternalFrame();
        internalFrame9 = new widget.InternalFrame();
        jPanel4 = new javax.swing.JPanel();
        panelisi6 = new widget.panelisi();
        label11 = new widget.Label();
        TCariSkala = new widget.TextBox();
        BtnCariSkala = new widget.Button();
        BtnTambah = new widget.Button();
        Scroll3 = new widget.ScrollPane();
        tbLevel = new widget.Table();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel18 = new widget.Label();
        Tanggal = new widget.Tanggal();
        jLabel23 = new widget.Label();
        cmbDiantar = new widget.ComboBox();
        jLabel14 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel12 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        btnDokter = new widget.Button();
        jLabel5 = new widget.Label();
        jLabel15 = new widget.Label();
        NIK = new widget.TextBox();
        jLabel17 = new widget.Label();
        Alamat = new widget.TextBox();
        Transportasi = new widget.ComboBox();
        jLabel22 = new widget.Label();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel31 = new widget.Label();
        Agama = new widget.TextBox();
        jLabel32 = new widget.Label();
        JK = new widget.TextBox();
        jLabel29 = new widget.Label();
        NoTelp = new widget.TextBox();
        jLabel28 = new widget.Label();
        JnsBayar = new widget.TextBox();
        jLabel33 = new widget.Label();
        Umur = new widget.TextBox();
        TKasus = new widget.TextBox();
        jLabel13 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        KeluhanUtama = new widget.TextArea();
        jLabel9 = new widget.Label();
        jLabel36 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        RiwayatPenyakit = new widget.TextArea();
        jLabel43 = new widget.Label();
        SttsFungsional = new widget.ComboBox();
        jLabel42 = new widget.Label();
        Psikologis = new widget.ComboBox();
        jLabel44 = new widget.Label();
        SttsTinggal = new widget.ComboBox();
        jLabel46 = new widget.Label();
        Edukasi = new widget.TextBox();
        jLabel11 = new widget.Label();
        Tensi = new widget.TextBox();
        jLabel26 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel10 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel27 = new widget.Label();
        Respirasi = new widget.TextBox();
        jLabel16 = new widget.Label();
        Saturasi = new widget.TextBox();
        jLabel25 = new widget.Label();
        LK = new widget.TextBox();
        jLabel24 = new widget.Label();
        TB = new widget.TextBox();
        jLabel39 = new widget.Label();
        BB = new widget.TextBox();
        jLabel40 = new widget.Label();
        IMT = new widget.TextBox();
        jLabel35 = new widget.Label();
        LILA = new widget.TextBox();
        jLabel20 = new widget.Label();
        Alergi = new widget.TextBox();
        cmbTriase = new widget.ComboBox();
        jLabel48 = new widget.Label();
        Rujukan = new widget.TextBox();
        chkNontrauma = new widget.CekBox();
        chkTrauma = new widget.CekBox();
        chkLainnya = new widget.CekBox();
        chkLakaTunggal = new widget.CekBox();
        chkLakaGanda = new widget.CekBox();
        TFungsional = new widget.TextBox();
        TTransportasi = new widget.TextBox();
        TDiantar = new widget.TextBox();
        jLabel41 = new widget.Label();
        LD = new widget.TextBox();
        jLabel45 = new widget.Label();
        LP = new widget.TextBox();
        FormInput1 = new widget.PanelBiasa();
        jLabel94 = new widget.Label();
        jLabel38 = new widget.Label();
        jLabel47 = new widget.Label();
        jLabel49 = new widget.Label();
        jLabel50 = new widget.Label();
        jLabel51 = new widget.Label();
        jLabel52 = new widget.Label();
        jLabel53 = new widget.Label();
        rpDahulu = new widget.TextBox();
        rPengobatan = new widget.TextBox();
        rMasukRS = new widget.TextBox();
        rTrauma = new widget.TextBox();
        rOperasi = new widget.TextBox();
        rpKeluarga = new widget.TextBox();
        Kepala = new widget.TextBox();
        Leher = new widget.TextBox();
        Punggung = new widget.TextBox();
        Mata = new widget.TextBox();
        Dada = new widget.TextBox();
        Genetalia = new widget.TextBox();
        THT = new widget.TextBox();
        Abdomen = new widget.TextBox();
        Ekstrimitas = new widget.TextBox();
        Mulut = new widget.TextBox();
        Diagnosa = new widget.TextBox();
        jLabel55 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        Terapi = new widget.TextArea();
        jLabel56 = new widget.Label();
        jLabel57 = new widget.Label();
        Tindakan = new widget.TextBox();
        PanelWall = new usu.widget.glass.PanelGlass();
        PanelWall1 = new usu.widget.glass.PanelGlass();
        jLabel58 = new widget.Label();
        cmbSkalaNyeri = new widget.ComboBox();
        jLabel59 = new widget.Label();
        cmbResikoJatuh = new widget.ComboBox();
        R1 = new widget.RadioButton();
        R2 = new widget.RadioButton();
        R3 = new widget.RadioButton();
        R4 = new widget.RadioButton();
        R5 = new widget.RadioButton();
        R6 = new widget.RadioButton();
        R7 = new widget.RadioButton();
        R8 = new widget.RadioButton();
        jLabel62 = new widget.Label();
        jLabel63 = new widget.Label();
        jLabel64 = new widget.Label();
        jLabel95 = new widget.Label();
        jLabel65 = new widget.Label();
        scrollPane6 = new widget.ScrollPane();
        Keterangan = new widget.TextArea();
        jLabel73 = new widget.Label();
        dxKeperawatan = new widget.ComboBox();
        Intervensi = new widget.ComboBox();
        jLabel96 = new widget.Label();
        jLabel75 = new widget.Label();
        chkMulut = new widget.CekBox();
        chkMasukRS = new widget.CekBox();
        chkKepala = new widget.CekBox();
        chkMata = new widget.CekBox();
        chkTHT = new widget.CekBox();
        chkLeher = new widget.CekBox();
        chkDada = new widget.CekBox();
        chkAbdomen = new widget.CekBox();
        chkPunggung = new widget.CekBox();
        chkGenetalia = new widget.CekBox();
        chkEkstrimitas = new widget.CekBox();
        internalFrame4 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbTriase = new widget.Table();
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
        BtnEdit = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();

        LoadHTML2.setBorder(null);
        LoadHTML2.setName("LoadHTML2"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCetakTriase.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakTriase.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakTriase.setForeground(new java.awt.Color(70, 70, 70));
        MnCetakTriase.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakTriase.setText("Cetak Lembar Triase");
        MnCetakTriase.setName("MnCetakTriase"); // NOI18N
        MnCetakTriase.setPreferredSize(new java.awt.Dimension(200, 28));
        MnCetakTriase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakTriaseActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakTriase);

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        TNoRw2.setName("TNoRw2"); // NOI18N
        TNoRw2.setPreferredSize(new java.awt.Dimension(207, 23));

        TResiko.setName("TResiko"); // NOI18N
        TResiko.setPreferredSize(new java.awt.Dimension(207, 23));

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Triage IGD ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(2040, 1053));
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        TabPilihan.setBackground(new java.awt.Color(255, 255, 254));
        TabPilihan.setForeground(new java.awt.Color(50, 50, 50));
        TabPilihan.setName("TabPilihan"); // NOI18N
        TabPilihan.setPreferredSize(new java.awt.Dimension(907, 829));
        TabPilihan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabPilihanMouseClicked(evt);
            }
        });

        ScrollTriase.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        ScrollTriase.setName("ScrollTriase"); // NOI18N
        ScrollTriase.setOpaque(true);
        ScrollTriase.setPreferredSize(new java.awt.Dimension(902, 1651));

        FormTriase.setBorder(null);
        FormTriase.setName("FormTriase"); // NOI18N
        FormTriase.setPreferredSize(new java.awt.Dimension(900, 1710));
        FormTriase.setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame5.setBorder(null);
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setPreferredSize(new java.awt.Dimension(700, 94));
        internalFrame5.setLayout(new java.awt.BorderLayout());

        internalFrame3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 4, 0, 4));
        internalFrame3.setMinimumSize(new java.awt.Dimension(553, 119));
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setPreferredSize(new java.awt.Dimension(700, 74));
        internalFrame3.setLayout(new java.awt.BorderLayout());

        internalFrame9.setBorder(null);
        internalFrame9.setMinimumSize(new java.awt.Dimension(364, 70));
        internalFrame9.setName("internalFrame9"); // NOI18N
        internalFrame9.setPreferredSize(new java.awt.Dimension(791, 72));
        internalFrame9.setLayout(new java.awt.BorderLayout(2, 1));

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(390, 72));
        jPanel4.setRequestFocusEnabled(false);
        jPanel4.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi6.setBorder(null);
        panelisi6.setName("panelisi6"); // NOI18N
        panelisi6.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label11.setText("Key Word :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi6.add(label11);

        TCariSkala.setForeground(new java.awt.Color(170, 0, 0));
        TCariSkala.setToolTipText("Alt+C");
        TCariSkala.setName("TCariSkala"); // NOI18N
        TCariSkala.setPreferredSize(new java.awt.Dimension(240, 23));
        TCariSkala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariSkalaKeyPressed(evt);
            }
        });
        panelisi6.add(TCariSkala);

        BtnCariSkala.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariSkala.setMnemonic('1');
        BtnCariSkala.setToolTipText("Alt+1");
        BtnCariSkala.setName("BtnCariSkala"); // NOI18N
        BtnCariSkala.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariSkala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariSkalaActionPerformed(evt);
            }
        });
        panelisi6.add(BtnCariSkala);

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
        panelisi6.add(BtnTambah);

        jPanel4.add(panelisi6, java.awt.BorderLayout.PAGE_END);

        Scroll3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll3.setForeground(new java.awt.Color(170, 0, 0));
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);
        Scroll3.setPreferredSize(new java.awt.Dimension(152, 72));

        tbLevel.setForeground(new java.awt.Color(170, 0, 0));
        tbLevel.setName("tbLevel"); // NOI18N
        tbLevel.setPreferredScrollableViewportSize(new java.awt.Dimension(450, 72));
        tbLevel.setSelectionForeground(new java.awt.Color(0, 255, 0));
        Scroll3.setViewportView(tbLevel);

        jPanel4.add(Scroll3, java.awt.BorderLayout.CENTER);

        internalFrame9.add(jPanel4, java.awt.BorderLayout.WEST);

        internalFrame3.add(internalFrame9, java.awt.BorderLayout.WEST);

        internalFrame5.add(internalFrame3, java.awt.BorderLayout.CENTER);

        FormTriase.add(internalFrame5, java.awt.BorderLayout.CENTER);

        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 450));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 100, 23);

        TNoRw.setEditable(false);
        TNoRw.setBackground(new java.awt.Color(245, 250, 240));
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        FormInput.add(TNoRw);
        TNoRw.setBounds(105, 10, 152, 23);

        TPasien.setEditable(false);
        TPasien.setBackground(new java.awt.Color(245, 250, 240));
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(371, 10, 392, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(259, 10, 110, 23);

        jLabel18.setText("Tgl :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(584, 70, 50, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-10-2023 10:06:12" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(637, 70, 128, 23);

        jLabel23.setText("Diantar oleh :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(0, 260, 100, 23);

        cmbDiantar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Keluarga", "Polisi", "Petugas Kesehatan", "Datang Sendiri", "Lainnya" }));
        cmbDiantar.setName("cmbDiantar"); // NOI18N
        cmbDiantar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDiantarActionPerformed(evt);
            }
        });
        FormInput.add(cmbDiantar);
        cmbDiantar.setBounds(105, 260, 155, 23);

        jLabel14.setText("Petugas :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(0, 130, 100, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setHighlighter(null);
        KdPetugas.setName("KdPetugas"); // NOI18N
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(105, 130, 75, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(182, 130, 180, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('3');
        btnPetugas.setToolTipText("ALt+3");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        FormInput.add(btnPetugas);
        btnPetugas.setBounds(362, 130, 28, 23);

        jLabel12.setText("Dokter :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 100, 100, 23);

        KdDokter.setEditable(false);
        KdDokter.setHighlighter(null);
        KdDokter.setName("KdDokter"); // NOI18N
        FormInput.add(KdDokter);
        KdDokter.setBounds(105, 100, 75, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        FormInput.add(NmDokter);
        NmDokter.setBounds(182, 100, 180, 23);

        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter.setMnemonic('1');
        btnDokter.setToolTipText("Alt+1");
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        FormInput.add(btnDokter);
        btnDokter.setBounds(362, 100, 28, 23);

        jLabel5.setText("Macam Kasus :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 160, 100, 23);

        jLabel15.setText("NIK :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(213, 70, 60, 23);

        NIK.setEditable(false);
        NIK.setBackground(new java.awt.Color(245, 250, 240));
        NIK.setHighlighter(null);
        NIK.setName("NIK"); // NOI18N
        FormInput.add(NIK);
        NIK.setBounds(275, 70, 130, 23);

        jLabel17.setText("Alamat :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(213, 40, 60, 23);

        Alamat.setEditable(false);
        Alamat.setBackground(new java.awt.Color(245, 250, 240));
        Alamat.setHighlighter(null);
        Alamat.setName("Alamat"); // NOI18N
        FormInput.add(Alamat);
        Alamat.setBounds(275, 40, 170, 23);

        Transportasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Ambulance", "Mobil Polisi", "Jalan Kaki", "Lainnya" }));
        Transportasi.setName("Transportasi"); // NOI18N
        Transportasi.setPreferredSize(new java.awt.Dimension(55, 28));
        Transportasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TransportasiActionPerformed(evt);
            }
        });
        Transportasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TransportasiKeyPressed(evt);
            }
        });
        FormInput.add(Transportasi);
        Transportasi.setBounds(105, 290, 155, 23);

        jLabel22.setText("Transportasi :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(0, 290, 100, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 40, 100, 23);

        TglLahir.setEditable(false);
        TglLahir.setBackground(new java.awt.Color(245, 250, 240));
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(105, 40, 110, 23);

        jLabel31.setText("Agama :");
        jLabel31.setName("jLabel31"); // NOI18N
        jLabel31.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel31);
        jLabel31.setBounds(0, 70, 100, 23);

        Agama.setEditable(false);
        Agama.setBackground(new java.awt.Color(245, 250, 240));
        Agama.setHighlighter(null);
        Agama.setName("Agama"); // NOI18N
        FormInput.add(Agama);
        Agama.setBounds(105, 70, 110, 23);

        jLabel32.setText("J.K.:");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(375, 70, 70, 23);

        JK.setEditable(false);
        JK.setBackground(new java.awt.Color(245, 250, 240));
        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N
        FormInput.add(JK);
        JK.setBounds(448, 70, 40, 23);

        jLabel29.setText("No.Telp :");
        jLabel29.setName("jLabel29"); // NOI18N
        jLabel29.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel29);
        jLabel29.setBounds(600, 40, 58, 23);

        NoTelp.setEditable(false);
        NoTelp.setHighlighter(null);
        NoTelp.setName("NoTelp"); // NOI18N
        NoTelp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoTelpKeyPressed(evt);
            }
        });
        FormInput.add(NoTelp);
        NoTelp.setBounds(662, 40, 100, 23);

        jLabel28.setText("Jns. Bayar :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(455, 40, 60, 23);

        JnsBayar.setEditable(false);
        JnsBayar.setBackground(new java.awt.Color(245, 250, 240));
        JnsBayar.setHighlighter(null);
        JnsBayar.setName("JnsBayar"); // NOI18N
        FormInput.add(JnsBayar);
        JnsBayar.setBounds(517, 40, 80, 23);

        jLabel33.setText("Umur :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(465, 70, 70, 23);

        Umur.setEditable(false);
        Umur.setBackground(new java.awt.Color(245, 250, 240));
        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N
        FormInput.add(Umur);
        Umur.setBounds(537, 70, 60, 23);

        TKasus.setName("TKasus"); // NOI18N
        FormInput.add(TKasus);
        TKasus.setBounds(180, 220, 200, 23);

        jLabel13.setText("Rujukan :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(400, 100, 90, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        KeluhanUtama.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KeluhanUtama.setColumns(20);
        KeluhanUtama.setRows(5);
        KeluhanUtama.setName("KeluhanUtama"); // NOI18N
        scrollPane1.setViewportView(KeluhanUtama);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(493, 130, 270, 38);

        jLabel9.setText("Keluhan Utama :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(400, 130, 90, 20);

        jLabel36.setText("Riwayat Penyakit :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(400, 175, 90, 20);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        RiwayatPenyakit.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatPenyakit.setColumns(20);
        RiwayatPenyakit.setRows(5);
        RiwayatPenyakit.setName("RiwayatPenyakit"); // NOI18N
        scrollPane2.setViewportView(RiwayatPenyakit);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(493, 175, 270, 38);

        jLabel43.setText("Stts. Fungsional :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(0, 320, 100, 23);

        SttsFungsional.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Mandiri", "Perlu bantuan", "Alat bantu" }));
        SttsFungsional.setName("SttsFungsional"); // NOI18N
        SttsFungsional.setPreferredSize(new java.awt.Dimension(55, 28));
        FormInput.add(SttsFungsional);
        SttsFungsional.setBounds(105, 320, 155, 23);

        jLabel42.setText("Psikologis :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(0, 350, 100, 23);

        Psikologis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Stabil ", "Marah", "Cemas", "Takut", "Gelisah", "Percobaan bunuh diri", "Halusinasi", "Lainnya", " " }));
        Psikologis.setName("Psikologis"); // NOI18N
        Psikologis.setPreferredSize(new java.awt.Dimension(55, 28));
        FormInput.add(Psikologis);
        Psikologis.setBounds(105, 350, 280, 23);

        jLabel44.setText("Sosial / Ekonomi :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(0, 380, 100, 23);

        SttsTinggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Sendiri", "Keluarga", "Teman", "Orang Tua", "Suami / Istri", "Teman", "Tunawisma", "Rumah perawatan / jompo" }));
        SttsTinggal.setName("SttsTinggal"); // NOI18N
        SttsTinggal.setPreferredSize(new java.awt.Dimension(55, 28));
        FormInput.add(SttsTinggal);
        SttsTinggal.setBounds(105, 380, 280, 23);

        jLabel46.setText("Edukasi :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(400, 370, 90, 23);

        Edukasi.setName("Edukasi"); // NOI18N
        FormInput.add(Edukasi);
        Edukasi.setBounds(493, 370, 273, 23);

        jLabel11.setText("TD (mmHg) :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(400, 220, 90, 23);

        Tensi.setHighlighter(null);
        Tensi.setName("Tensi"); // NOI18N
        FormInput.add(Tensi);
        Tensi.setBounds(493, 220, 55, 23);

        jLabel26.setText("N (x/mnt) :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(533, 220, 79, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        FormInput.add(Nadi);
        Nadi.setBounds(614, 220, 50, 23);

        jLabel10.setText("T(°C) :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(635, 220, 79, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        FormInput.add(Suhu);
        Suhu.setBounds(715, 220, 50, 23);

        jLabel27.setText("RR(x/mnt) :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(400, 250, 90, 23);

        Respirasi.setHighlighter(null);
        Respirasi.setName("Respirasi"); // NOI18N
        FormInput.add(Respirasi);
        Respirasi.setBounds(493, 250, 55, 23);

        jLabel16.setText("Sp.O²(%) :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(533, 250, 79, 23);

        Saturasi.setFocusTraversalPolicyProvider(true);
        Saturasi.setName("Saturasi"); // NOI18N
        FormInput.add(Saturasi);
        Saturasi.setBounds(614, 250, 50, 23);

        jLabel25.setText("LK (cm) :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(635, 250, 79, 23);

        LK.setHighlighter(null);
        LK.setName("LK"); // NOI18N
        FormInput.add(LK);
        LK.setBounds(715, 250, 50, 23);

        jLabel24.setText("TB (cm) :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(400, 280, 90, 23);

        TB.setName("TB"); // NOI18N
        FormInput.add(TB);
        TB.setBounds(493, 280, 55, 23);

        jLabel39.setText("BB (kg) :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(533, 280, 79, 23);

        BB.setHighlighter(null);
        BB.setName("BB"); // NOI18N
        FormInput.add(BB);
        BB.setBounds(614, 280, 50, 23);

        jLabel40.setText("IMT :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(635, 280, 79, 23);

        IMT.setEditable(false);
        IMT.setHighlighter(null);
        IMT.setName("IMT"); // NOI18N
        FormInput.add(IMT);
        IMT.setBounds(715, 280, 50, 23);

        jLabel35.setText("LILA (cm) :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(400, 310, 90, 23);

        LILA.setHighlighter(null);
        LILA.setName("LILA"); // NOI18N
        FormInput.add(LILA);
        LILA.setBounds(493, 310, 55, 23);

        jLabel20.setText("Alergi :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(410, 340, 79, 23);

        Alergi.setName("Alergi"); // NOI18N
        FormInput.add(Alergi);
        Alergi.setBounds(493, 340, 273, 23);

        cmbTriase.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "AIRWAY", "BREATHING", "CIRCULATION", "DISABILITY", "Prediksi pemeriksaan penunjang", "Prediksi SDM yang akan terlibat", "Do'a" }));
        cmbTriase.setName("cmbTriase"); // NOI18N
        cmbTriase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTriaseActionPerformed(evt);
            }
        });
        FormInput.add(cmbTriase);
        cmbTriase.setBounds(105, 420, 280, 23);

        jLabel48.setText("Pemeriksaan :");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(0, 420, 100, 23);

        Rujukan.setEditable(false);
        Rujukan.setName("Rujukan"); // NOI18N
        FormInput.add(Rujukan);
        Rujukan.setBounds(493, 100, 273, 23);

        chkNontrauma.setBorder(null);
        chkNontrauma.setText("Non Trauma");
        chkNontrauma.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkNontrauma.setName("chkNontrauma"); // NOI18N
        FormInput.add(chkNontrauma);
        chkNontrauma.setBounds(105, 160, 80, 23);

        chkTrauma.setBorder(null);
        chkTrauma.setText("Trauma");
        chkTrauma.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkTrauma.setName("chkTrauma"); // NOI18N
        FormInput.add(chkTrauma);
        chkTrauma.setBounds(105, 190, 70, 23);

        chkLainnya.setBorder(null);
        chkLainnya.setText("Lainnya");
        chkLainnya.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkLainnya.setName("chkLainnya"); // NOI18N
        chkLainnya.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkLainnyaActionPerformed(evt);
            }
        });
        FormInput.add(chkLainnya);
        chkLainnya.setBounds(105, 220, 60, 23);

        chkLakaTunggal.setBorder(null);
        chkLakaTunggal.setText("Laka Tunggal");
        chkLakaTunggal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkLakaTunggal.setName("chkLakaTunggal"); // NOI18N
        FormInput.add(chkLakaTunggal);
        chkLakaTunggal.setBounds(190, 160, 110, 20);

        chkLakaGanda.setBorder(null);
        chkLakaGanda.setText("Laka Ganda");
        chkLakaGanda.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkLakaGanda.setName("chkLakaGanda"); // NOI18N
        FormInput.add(chkLakaGanda);
        chkLakaGanda.setBounds(190, 190, 90, 20);

        TFungsional.setName("TFungsional"); // NOI18N
        FormInput.add(TFungsional);
        TFungsional.setBounds(265, 320, 120, 23);

        TTransportasi.setName("TTransportasi"); // NOI18N
        FormInput.add(TTransportasi);
        TTransportasi.setBounds(265, 290, 120, 23);

        TDiantar.setName("TDiantar"); // NOI18N
        FormInput.add(TDiantar);
        TDiantar.setBounds(265, 260, 120, 23);

        jLabel41.setText("LD :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(533, 310, 79, 23);

        LD.setHighlighter(null);
        LD.setName("LD"); // NOI18N
        FormInput.add(LD);
        LD.setBounds(614, 310, 50, 23);

        jLabel45.setText("LP :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(635, 310, 79, 23);

        LP.setHighlighter(null);
        LP.setName("LP"); // NOI18N
        FormInput.add(LP);
        LP.setBounds(715, 310, 50, 23);

        FormTriase.add(FormInput, java.awt.BorderLayout.PAGE_START);

        FormInput1.setName("FormInput1"); // NOI18N
        FormInput1.setPreferredSize(new java.awt.Dimension(540, 1107));
        FormInput1.setLayout(null);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("ASSESMENT IGD");
        jLabel94.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput1.add(jLabel94);
        jLabel94.setBounds(20, 165, 180, 23);

        jLabel38.setText("Pernah masuk RSHD");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput1.add(jLabel38);
        jLabel38.setBounds(0, 190, 130, 20);

        jLabel47.setText("Riwayat Peny.Dahulu :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput1.add(jLabel47);
        jLabel47.setBounds(0, 220, 130, 20);

        jLabel49.setText("Riwayat Masuk RS :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput1.add(jLabel49);
        jLabel49.setBounds(547, 220, 120, 20);

        jLabel50.setText("Riwayat Pengobatan :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput1.add(jLabel50);
        jLabel50.setBounds(264, 220, 140, 20);

        jLabel51.setText("Riwayat Operasi :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput1.add(jLabel51);
        jLabel51.setBounds(264, 250, 140, 20);

        jLabel52.setText("Riwayat Trauma :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput1.add(jLabel52);
        jLabel52.setBounds(547, 250, 120, 20);

        jLabel53.setText("Riwayat Peny. Keluarga :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput1.add(jLabel53);
        jLabel53.setBounds(0, 250, 130, 20);

        rpDahulu.setName("rpDahulu"); // NOI18N
        FormInput1.add(rpDahulu);
        rpDahulu.setBounds(135, 220, 145, 23);

        rPengobatan.setName("rPengobatan"); // NOI18N
        FormInput1.add(rPengobatan);
        rPengobatan.setBounds(407, 220, 145, 23);

        rMasukRS.setName("rMasukRS"); // NOI18N
        FormInput1.add(rMasukRS);
        rMasukRS.setBounds(670, 220, 145, 23);

        rTrauma.setName("rTrauma"); // NOI18N
        FormInput1.add(rTrauma);
        rTrauma.setBounds(670, 250, 145, 23);

        rOperasi.setName("rOperasi"); // NOI18N
        FormInput1.add(rOperasi);
        rOperasi.setBounds(407, 250, 145, 23);

        rpKeluarga.setName("rpKeluarga"); // NOI18N
        FormInput1.add(rpKeluarga);
        rpKeluarga.setBounds(135, 250, 145, 23);

        Kepala.setName("Kepala"); // NOI18N
        FormInput1.add(Kepala);
        Kepala.setBounds(155, 310, 125, 23);

        Leher.setName("Leher"); // NOI18N
        FormInput1.add(Leher);
        Leher.setBounds(427, 310, 125, 23);

        Punggung.setName("Punggung"); // NOI18N
        FormInput1.add(Punggung);
        Punggung.setBounds(690, 310, 125, 23);

        Mata.setName("Mata"); // NOI18N
        FormInput1.add(Mata);
        Mata.setBounds(155, 340, 125, 23);

        Dada.setName("Dada"); // NOI18N
        FormInput1.add(Dada);
        Dada.setBounds(427, 340, 125, 23);

        Genetalia.setName("Genetalia"); // NOI18N
        FormInput1.add(Genetalia);
        Genetalia.setBounds(690, 340, 125, 23);

        THT.setName("THT"); // NOI18N
        FormInput1.add(THT);
        THT.setBounds(155, 370, 125, 23);

        Abdomen.setName("Abdomen"); // NOI18N
        FormInput1.add(Abdomen);
        Abdomen.setBounds(427, 370, 125, 23);

        Ekstrimitas.setName("Ekstrimitas"); // NOI18N
        FormInput1.add(Ekstrimitas);
        Ekstrimitas.setBounds(690, 370, 125, 23);

        Mulut.setName("Mulut"); // NOI18N
        FormInput1.add(Mulut);
        Mulut.setBounds(155, 400, 125, 23);

        Diagnosa.setName("Diagnosa"); // NOI18N
        FormInput1.add(Diagnosa);
        Diagnosa.setBounds(135, 440, 260, 23);

        jLabel55.setText("Diagnosa :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput1.add(jLabel55);
        jLabel55.setBounds(0, 440, 130, 20);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        Terapi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Terapi.setColumns(20);
        Terapi.setRows(5);
        Terapi.setName("Terapi"); // NOI18N
        scrollPane4.setViewportView(Terapi);

        FormInput1.add(scrollPane4);
        scrollPane4.setBounds(555, 440, 260, 58);

        jLabel56.setText("Terapi :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput1.add(jLabel56);
        jLabel56.setBounds(410, 440, 140, 20);

        jLabel57.setText("Tindakan :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput1.add(jLabel57);
        jLabel57.setBounds(0, 470, 130, 20);

        Tindakan.setName("Tindakan"); // NOI18N
        FormInput1.add(Tindakan);
        Tindakan.setBounds(135, 470, 260, 23);

        PanelWall.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/semua2.jpg"))); // NOI18N
        PanelWall.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall.setRound(false);
        PanelWall.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall.setLayout(null);
        FormInput1.add(PanelWall);
        PanelWall.setBounds(30, 580, 780, 300);

        PanelWall1.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall1.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/nyeri2.jpg"))); // NOI18N
        PanelWall1.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall1.setPreferredSize(new java.awt.Dimension(300, 200));
        PanelWall1.setRound(false);
        PanelWall1.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall1.setLayout(null);
        FormInput1.add(PanelWall1);
        PanelWall1.setBounds(30, 10, 350, 120);

        jLabel58.setText("Skala Nyeri :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput1.add(jLabel58);
        jLabel58.setBounds(400, 10, 90, 23);

        cmbSkalaNyeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        cmbSkalaNyeri.setName("cmbSkalaNyeri"); // NOI18N
        FormInput1.add(cmbSkalaNyeri);
        cmbSkalaNyeri.setBounds(493, 10, 200, 23);

        jLabel59.setText("Resiko Jatuh :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput1.add(jLabel59);
        jLabel59.setBounds(400, 40, 90, 23);

        cmbResikoJatuh.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Pediatri (humpty dumpty)", "Dewasa (skala morse)", "Lansia (skala ontario)", " " }));
        cmbResikoJatuh.setName("cmbResikoJatuh"); // NOI18N
        cmbResikoJatuh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbResikoJatuhActionPerformed(evt);
            }
        });
        FormInput1.add(cmbResikoJatuh);
        cmbResikoJatuh.setBounds(493, 40, 200, 23);

        buttonGroup1.add(R1);
        R1.setText("Rendah (0-24)");
        R1.setName("R1"); // NOI18N
        FormInput1.add(R1);
        R1.setBounds(493, 70, 150, 20);

        buttonGroup1.add(R2);
        R2.setText("Sedang (25-44)");
        R2.setName("R2"); // NOI18N
        FormInput1.add(R2);
        R2.setBounds(493, 90, 150, 20);

        buttonGroup1.add(R3);
        R3.setText("Tinggi (>= 45)");
        R3.setName("R3"); // NOI18N
        FormInput1.add(R3);
        R3.setBounds(493, 110, 150, 20);

        buttonGroup2.add(R4);
        R4.setText("Tinggi (>=12)");
        R4.setName("R4"); // NOI18N
        FormInput1.add(R4);
        R4.setBounds(493, 90, 150, 20);

        buttonGroup2.add(R5);
        R5.setText("Rendah (7-11)");
        R5.setName("R5"); // NOI18N
        FormInput1.add(R5);
        R5.setBounds(493, 70, 150, 20);

        buttonGroup3.add(R6);
        R6.setText("Rendah (0-5)");
        R6.setName("R6"); // NOI18N
        FormInput1.add(R6);
        R6.setBounds(493, 70, 150, 20);

        buttonGroup3.add(R7);
        R7.setText("Sedang (6-16)");
        R7.setName("R7"); // NOI18N
        FormInput1.add(R7);
        R7.setBounds(493, 90, 150, 20);

        buttonGroup3.add(R8);
        R8.setText("Tinggi (17-30)");
        R8.setName("R8"); // NOI18N
        FormInput1.add(R8);
        R8.setBounds(493, 110, 150, 20);

        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel62.setText("VL : Vulnus laseratum (robek)     VM : Vulnus morsum (gigitan binatang)     VP : Vulnus punctum (tusuk)      VS : Vulnus scissum (sayat)");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput1.add(jLabel62);
        jLabel62.setBounds(30, 910, 800, 20);

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel63.setText("C  : Combustio     Ek : Ekskoriasi    E : Eritema    P : Ptekie    D : Dekubitus");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput1.add(jLabel63);
        jLabel63.setBounds(30, 930, 800, 20);

        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel64.setText("S  : Scar    ST : Stoma   UD : Ulkus diabetik    O : Other ( tato, amputasi, perubahan warna) ");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput1.add(jLabel64);
        jLabel64.setBounds(30, 950, 800, 20);

        jLabel95.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel95.setText("Status Kode Diagram");
        jLabel95.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel95.setName("jLabel95"); // NOI18N
        FormInput1.add(jLabel95);
        jLabel95.setBounds(20, 890, 180, 23);

        jLabel65.setText("Ketrangan :");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput1.add(jLabel65);
        jLabel65.setBounds(0, 980, 90, 20);

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane6.setName("scrollPane6"); // NOI18N

        Keterangan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Keterangan.setColumns(20);
        Keterangan.setRows(5);
        Keterangan.setName("Keterangan"); // NOI18N
        scrollPane6.setViewportView(Keterangan);

        FormInput1.add(scrollPane6);
        scrollPane6.setBounds(95, 980, 570, 48);

        jLabel73.setText("Diagnosa :");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput1.add(jLabel73);
        jLabel73.setBounds(0, 550, 130, 23);

        dxKeperawatan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Bersihan jalan nafas tidak efektif", "Pola nafas tidak efektif", "Tanda vital tidak stabil", "Gangguan volume cairan", "Nyeri akut" }));
        dxKeperawatan.setName("dxKeperawatan"); // NOI18N
        FormInput1.add(dxKeperawatan);
        dxKeperawatan.setBounds(135, 550, 260, 23);

        Intervensi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Bersihan jalan nafas", "O2 ..LPM VIA ...", "Monitoring vital sign", "Monitoring cairan", "Manajemen nyeri", "Pasang kateter urine", "Hecting", "Dressing luka", "Cross incision", "Pemasangan bidai", "Pemasangan ngt", " " }));
        Intervensi.setName("Intervensi"); // NOI18N
        FormInput1.add(Intervensi);
        Intervensi.setBounds(555, 550, 260, 23);

        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel96.setText("DIAGNOSIS KEPERAWATAN DAN INTERVENSI");
        jLabel96.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput1.add(jLabel96);
        jLabel96.setBounds(20, 520, 290, 23);

        jLabel75.setText("Intervensi :");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput1.add(jLabel75);
        jLabel75.setBounds(410, 550, 140, 20);

        chkMulut.setBorder(null);
        chkMulut.setText("Mulut :");
        chkMulut.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkMulut.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkMulut.setName("chkMulut"); // NOI18N
        chkMulut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMulutActionPerformed(evt);
            }
        });
        FormInput1.add(chkMulut);
        chkMulut.setBounds(0, 400, 148, 20);

        chkMasukRS.setBorder(null);
        chkMasukRS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkMasukRS.setName("chkMasukRS"); // NOI18N
        chkMasukRS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMasukRSActionPerformed(evt);
            }
        });
        FormInput1.add(chkMasukRS);
        chkMasukRS.setBounds(135, 190, 20, 20);

        chkKepala.setBorder(null);
        chkKepala.setText("Kepala :");
        chkKepala.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkKepala.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkKepala.setName("chkKepala"); // NOI18N
        chkKepala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkKepalaActionPerformed(evt);
            }
        });
        FormInput1.add(chkKepala);
        chkKepala.setBounds(0, 310, 148, 20);

        chkMata.setBorder(null);
        chkMata.setText("Mata :");
        chkMata.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkMata.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkMata.setName("chkMata"); // NOI18N
        chkMata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMataActionPerformed(evt);
            }
        });
        FormInput1.add(chkMata);
        chkMata.setBounds(0, 340, 148, 20);

        chkTHT.setBorder(null);
        chkTHT.setText("THT :");
        chkTHT.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkTHT.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkTHT.setName("chkTHT"); // NOI18N
        chkTHT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkTHTActionPerformed(evt);
            }
        });
        FormInput1.add(chkTHT);
        chkTHT.setBounds(0, 370, 148, 20);

        chkLeher.setBorder(null);
        chkLeher.setText("Leher :");
        chkLeher.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkLeher.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkLeher.setName("chkLeher"); // NOI18N
        chkLeher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkLeherActionPerformed(evt);
            }
        });
        FormInput1.add(chkLeher);
        chkLeher.setBounds(320, 310, 100, 20);

        chkDada.setBorder(null);
        chkDada.setText("Dada :");
        chkDada.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkDada.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkDada.setName("chkDada"); // NOI18N
        chkDada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDadaActionPerformed(evt);
            }
        });
        FormInput1.add(chkDada);
        chkDada.setBounds(320, 340, 100, 20);

        chkAbdomen.setBorder(null);
        chkAbdomen.setText("Abdomen :");
        chkAbdomen.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkAbdomen.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkAbdomen.setName("chkAbdomen"); // NOI18N
        chkAbdomen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAbdomenActionPerformed(evt);
            }
        });
        FormInput1.add(chkAbdomen);
        chkAbdomen.setBounds(320, 370, 100, 20);

        chkPunggung.setBorder(null);
        chkPunggung.setText("Punggung :");
        chkPunggung.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkPunggung.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkPunggung.setName("chkPunggung"); // NOI18N
        chkPunggung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPunggungActionPerformed(evt);
            }
        });
        FormInput1.add(chkPunggung);
        chkPunggung.setBounds(582, 310, 100, 20);

        chkGenetalia.setBorder(null);
        chkGenetalia.setText("Genetalia :");
        chkGenetalia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkGenetalia.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkGenetalia.setName("chkGenetalia"); // NOI18N
        chkGenetalia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkGenetaliaActionPerformed(evt);
            }
        });
        FormInput1.add(chkGenetalia);
        chkGenetalia.setBounds(582, 340, 100, 20);

        chkEkstrimitas.setBorder(null);
        chkEkstrimitas.setText("Ekstrimitas :");
        chkEkstrimitas.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkEkstrimitas.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkEkstrimitas.setName("chkEkstrimitas"); // NOI18N
        chkEkstrimitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkEkstrimitasActionPerformed(evt);
            }
        });
        FormInput1.add(chkEkstrimitas);
        chkEkstrimitas.setBounds(582, 370, 100, 20);

        FormTriase.add(FormInput1, java.awt.BorderLayout.PAGE_END);

        ScrollTriase.setViewportView(FormTriase);

        TabPilihan.addTab("Input Triage", ScrollTriase);

        internalFrame4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbTriase.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbTriase.setName("tbTriase"); // NOI18N
        tbTriase.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbTriaseMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTriaseMouseClicked(evt);
            }
        });
        tbTriase.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTriaseKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbTriaseKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbTriase);

        internalFrame4.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Triase :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-10-2023" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-10-2023" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
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

        TabPilihan.addTab("Data Triage", internalFrame4);

        internalFrame1.add(TabPilihan, java.awt.BorderLayout.CENTER);

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
        if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Pasien");
        } else {
            Sequel.AutoComitFalse();
            sukses = true;
            jmlskala1 = 0;
            for (i = 0; i < tbLevel.getRowCount(); i++) {
                if (tbLevel.getValueAt(i, 0).toString().equals("true")) {
                    jmlskala1++;
                }
            }
            if (KeluhanUtama.getText().trim().equals("")) {
                Valid.textKosong(KeluhanUtama, "Keluhan Utama");
            } else if (RiwayatPenyakit.getText().trim().equals("")) {
                Valid.textKosong(RiwayatPenyakit, "Riwayat Penyakit");
            } else if (Tensi.getText().trim().equals("")) {
                Valid.textKosong(Tensi, "Tensi");
            } else if (Nadi.getText().trim().equals("")) {
                Valid.textKosong(Nadi, "Nadi");
            } else if (Suhu.getText().trim().equals("")) {
                Valid.textKosong(Suhu, "Suhu");
            } else if (Respirasi.getText().trim().equals("")) {
                Valid.textKosong(Respirasi, "Respirasi");
            } else if (Saturasi.getText().trim().equals("")) {
                Valid.textKosong(Saturasi, "Saturasi O²");
            } else if (TB.getText().trim().equals("")) {
                Valid.textKosong(TB, "TB");
            } else if (BB.getText().trim().equals("")) {
                Valid.textKosong(BB, "BB");
            } else if (KdPetugas.getText().trim().equals("") || NmPetugas.getText().trim().equals("")) {
                Valid.textKosong(btnPetugas, "Petugas");
            } else {
//                int cariTriage = Sequel.cariInteger("SELECT COUNT(no_rawat) FROM data_triase_igd WHERE no_rawat=?", TNoRw.getText());
//                if (cariTriage > 0) {
//                    insertPasien();
//                    simpan2();                    
//                }
//                if (cariTriage == 0) {
                    insertPasien();
                    simpan();
//                }
//                TabPilihan.setSelectedIndex(1);
            }
            if (sukses == true) {
                Sequel.Commit();
            } else {
                sukses = false;
                JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat pemrosesan data, input data dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
//                Sequel.RollBack();
            }
            Sequel.AutoComitTrue();
            if (sukses == true) {
//                emptTeks();
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        Valid.pindah(evt, btnDokter, BtnBatal);
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        isReset();
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
         if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else {
            if (tbTriase.getSelectedRow() != -1) {
                if (var.getkode().equals("Admin Utama")) {
                    hapus();
                    emptTeks();
                } else {
                    hapus();
                    emptTeks();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data yang mau dihapus terlebih dahulu ...!!!!");
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
        if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Pasien");
        } else {
            if (tbTriase.getSelectedRow() > -1) {
                if (var.getkode().equals("Admin Utama")) {
                    insertPasien();
                    ganti();
                    TabPilihan.setSelectedIndex(1);
                } else {
//                    if (var.getkode().equals(kodepetugas)) {
                        insertPasien();
                        ganti();
                        TabPilihan.setSelectedIndex(1);
//                    } else {
//                        JOptionPane.showMessageDialog(null, "Hanya bisa diganti oleh petugas yang bersangkutan..!!");
//                    }
                }
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

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
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
        BtnCariActionPerformed(evt);
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        } else if (tabMode.getRowCount() != 0) {

            Sequel.queryu("delete from temporary");
            int row = tabMode.getRowCount();
            for (int i = 0; i < row; i++) {
                Sequel.menyimpan("temporary", "'0','"
                        + tabMode.getValueAt(i, 0).toString() + "','"
                        + tabMode.getValueAt(i, 1).toString() + "','"
                        + tabMode.getValueAt(i, 2).toString() + "','"
                        + tabMode.getValueAt(i, 3).toString() + "','"
                        + tabMode.getValueAt(i, 4).toString() + "','"
                        + tabMode.getValueAt(i, 5).toString() + "','"
                        + tabMode.getValueAt(i, 6).toString() + "','"
                        + tabMode.getValueAt(i, 7).toString() + "','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''", "Triage");
            }
            System.out.println("menyimpan");
            Sequel.menyimpan("temporary", "'0','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''", "Triage");

            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptLapTriaseIGD.jrxml", "report", "::[ Data Triage IGD ]::",
                    "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14, temp14, temp15, temp16 from temporary order by no asc", param);
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

    private void tbTriaseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTriaseMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
                getData2();
            } catch (java.lang.NullPointerException e) {
            }
        }}//GEN-LAST:event_tbTriaseMouseClicked

    private void tbTriaseKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTriaseKeyPressed
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbTriaseKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
    }//GEN-LAST:event_formWindowOpened

    private void TabPilihanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabPilihanMouseClicked
        if (TabPilihan.getSelectedIndex() == 1) {
            tampil();
        }
    }//GEN-LAST:event_TabPilihanMouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated

    }//GEN-LAST:event_formWindowActivated

    private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        index = 1;
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokterActionPerformed

    private void TransportasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TransportasiKeyPressed
        Valid.pindah(evt, TCari, Tanggal);
    }//GEN-LAST:event_TransportasiKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt, Transportasi, Alergi);
    }//GEN-LAST:event_TanggalKeyPressed

    private void tbTriaseKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTriaseKeyReleased
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbTriaseKeyReleased

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        index = 1;
        petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void MnCetakTriaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakTriaseActionPerformed
//        if (tbTriase.getSelectedRow() != -1) {
//            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//            if (tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString().trim().equals("")) {
//                Valid.textKosong(TCari, "No.Rawat");
//            } else {
//
//                Sequel.queryu("delete from temporary");
//                try {
//                    ps2 = koneksi.prepareStatement(
//                            "select master_triase_pemeriksaan.nama_pemeriksaan, master_triase_skala1.pengkajian_skala1 "
//                            + "from data_triase_igd inner join data_triase_igddetail_skala1 inner join master_triase_skala1 "
//                            + "inner join master_triase_pemeriksaan on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala1.kode_pemeriksaan and "
//                            + "data_triase_igddetail_skala1.kode_skala1=master_triase_skala1.kode_skala1 and data_triase_igd.no_rawat=data_triase_igddetail_skala1.no_rawat "
//                            + "where data_triase_igd.no_rawat=?");
//                    try {
//                        ps2.setString(1, tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString());
//                        rs2 = ps2.executeQuery();
//                        while (rs2.next()) {
//                            Sequel.menyimpan("temporary", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", 38, new String[]{
//                                "0", rs2.getString("nama_pemeriksaan"), rs2.getString("pengkajian_skala1"), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
//                            });
//                        }
//                    } catch (Exception e) {
//                        System.out.println("Notif 2 : " + e);
//                    } finally {
//                        if (rs2 != null) {
//                            rs2.close();
//                        }
//                        if (ps2 != null) {
//                            ps2.close();
//                        }
//                    }
//                } catch (Exception e) {
//                    System.out.println("Notif : " + e);
//                }
//
//                Map<String, Object> param = new HashMap<>();
//                param.put("noperiksa", tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString());
//                norm = Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ", tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString());
//                param.put("norm", norm);
//                param.put("pekerjaan", Sequel.cariIsi("select pekerjaan from pasien where no_rkm_medis=?", norm));
//                param.put("noktp", Sequel.cariIsi("select no_ktp from pasien where no_rkm_medis=?", norm));
//                param.put("namapasien", Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ", norm));
//                param.put("jkel", Sequel.cariIsi("select if(jk='L','Laki-laki','Perempuan') as jk from pasien where no_rkm_medis=? ", norm));
//                param.put("umur", Sequel.cariIsi("select umur from pasien where no_rkm_medis=?", norm));
//                param.put("lahir", Sequel.cariIsi("select DATE_FORMAT(tgl_lahir,'%d-%m-%Y') from pasien where no_rkm_medis=? ", norm));
//                param.put("tanggal", Valid.SetTgl3(tbTriase.getValueAt(tbTriase.getSelectedRow(), 3).toString().substring(0, 11)));
//                param.put("alamat", Sequel.cariIsi("select concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat from pasien inner join kelurahan inner join kecamatan inner join kabupaten on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where no_rkm_medis=? ", norm));
//                param.put("jns_bayar", tbTriase.getValueAt(tbTriase.getSelectedRow(), 4).toString());
//                param.put("namars", var.getnamars());
//                param.put("alamatrs", var.getalamatrs());
//                param.put("kotars", var.getkabupatenrs());
//                param.put("propinsirs", var.getpropinsirs());
//                param.put("kontakrs", var.getkontakrs());
//                param.put("emailrs", var.getemailrs());
//                param.put("logo", Sequel.cariGambar("select logo from setting"));
//
//                pilihan = (String) JOptionPane.showInputDialog(null, "Silahkan pilih Lembar Level Triase..!", "Pilihan", JOptionPane.QUESTION_MESSAGE, null, new Object[]{
//                    "Lembar Triase Level 1", "Lembar Triase Level 2", "Lembar Triase Level 3", "Lembar Triase Level 4", "Lembar Triase Level 5"}, "Lambar Triase");
//                switch (pilihan) {
//                    case "Lembar Triase Level 1":
//                        Valid.MyReport("rptLembarTriaseSkala1.jrxml", "report", "::[ Triase Level 1 ]::", "select * from temporary order by temporary.no", param);
//                        break;
//                    case "Lembar Triase Level 2":
//                        Valid.MyReport("rptLembarTriaseSkala2.jrxml", "report", "::[ Triase Level 2 ]::", "select * from temporary order by temporary.no", param);
//                        break;
//                    case "Lembar Triase Level 3":
//                        Valid.MyReport("rptLembarTriaseSkala3.jrxml", "report", "::[ Triase Level 3 ]::", "select * from temporary order by temporary.no", param);
//                        break;
//                    case "Lembar Triase Level 4":
//                        Valid.MyReport("rptLembarTriaseSkala4.jrxml", "report", "::[ Triase Level 4 ]::", "select * from temporary order by temporary.no", param);
//                        break;
//                    case "Lembar Triase Level 5":
//                        Valid.MyReport("rptLembarTriaseSkala5.jrxml", "report", "::[ Triase Level 5 ]::", "select * from temporary order by temporary.no", param);
//                        break;
//                }
//            }
//            this.setCursor(Cursor.getDefaultCursor());
//        } else {
//            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data permintaan...!!!!");
//            TCari.requestFocus();
//        }
    }//GEN-LAST:event_MnCetakTriaseActionPerformed

    private void tbTriaseMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTriaseMouseReleased
        if (evt.isPopupTrigger()) {
            jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_tbTriaseMouseReleased

    private void NoTelpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoTelpKeyPressed
        Valid.pindah(evt, JK, NIK);
    }//GEN-LAST:event_NoTelpKeyPressed

    private void BtnCariSkalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariSkalaActionPerformed
        tampillevel();
    }//GEN-LAST:event_BtnCariSkalaActionPerformed

    private void TCariSkalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariSkalaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariSkalaActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCariSkala.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariSkalaKeyPressed

    private void cmbDiantarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDiantarActionPerformed
        if (cmbDiantar.getSelectedIndex() != 5) {
            TDiantar.disable();
        } else {
            TDiantar.enable();
        }
    }//GEN-LAST:event_cmbDiantarActionPerformed

    private void TransportasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TransportasiActionPerformed
        if (Transportasi.getSelectedIndex() != 4) {
            TTransportasi.disable();
        } else {
            TTransportasi.enable();
        }
    }//GEN-LAST:event_TransportasiActionPerformed

    private void chkLainnyaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkLainnyaActionPerformed
        if (chkLainnya.isSelected() == true) {
            TKasus.setVisible(true);
        } else {
            TKasus.setVisible(false);
            TKasus.setText("");
        }
    }//GEN-LAST:event_chkLainnyaActionPerformed

    private void cmbResikoJatuhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbResikoJatuhActionPerformed
        if (cmbResikoJatuh.getSelectedIndex() == 1) {
            R1.setVisible(true);
            R2.setVisible(true);
            R3.setVisible(true);
            R4.setVisible(false);
            R5.setVisible(false);
            R6.setVisible(false);
            R7.setVisible(false);
            R8.setVisible(false);
        } else if (cmbResikoJatuh.getSelectedIndex() == 2) {
            R1.setVisible(false);
            R2.setVisible(false);
            R3.setVisible(false);
            R4.setVisible(true);
            R5.setVisible(true);
            R6.setVisible(false);
            R7.setVisible(false);
            R8.setVisible(false);
        } else if (cmbResikoJatuh.getSelectedIndex() == 3) {
            R1.setVisible(false);
            R2.setVisible(false);
            R3.setVisible(false);
            R4.setVisible(false);
            R5.setVisible(false);
            R6.setVisible(true);
            R7.setVisible(true);
            R8.setVisible(true);
        } else {
            R1.setVisible(false);
            R2.setVisible(false);
            R3.setVisible(false);
            R4.setVisible(false);
            R5.setVisible(false);
            R6.setVisible(false);
            R7.setVisible(false);
            R8.setVisible(false);
        }
    }//GEN-LAST:event_cmbResikoJatuhActionPerformed

    private void chkMasukRSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMasukRSActionPerformed
        if (chkMasukRS.isSelected() == true) {
            jLabel47.hide();
            rpDahulu.hide();
            jLabel53.hide();
            rpKeluarga.hide();
            jLabel50.hide();
            rPengobatan.hide();
            jLabel51.hide();
            rOperasi.hide();
            jLabel52.hide();
            rTrauma.hide();
            jLabel49.hide();
            rMasukRS.hide();
        } else {
            jLabel47.setVisible(true);
            rpDahulu.setVisible(true);
            jLabel53.setVisible(true);
            rpKeluarga.setVisible(true);
            jLabel50.setVisible(true);
            rPengobatan.setVisible(true);
            jLabel51.setVisible(true);
            rOperasi.setVisible(true);
            jLabel52.setVisible(true);
            rTrauma.setVisible(true);
            jLabel49.setVisible(true);
            rMasukRS.setVisible(true);
        }
    }//GEN-LAST:event_chkMasukRSActionPerformed

    private void cmbTriaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTriaseActionPerformed
        if (cmbTriase.getSelectedIndex() != 0) {
            tampillevel();
        }
    }//GEN-LAST:event_cmbTriaseActionPerformed

    private void chkEkstrimitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEkstrimitasActionPerformed
        if (chkEkstrimitas.isSelected() == true) {
            Ekstrimitas.setVisible(true);
        } else {
            Ekstrimitas.setVisible(false);
            Ekstrimitas.setText("");
        }
    }//GEN-LAST:event_chkEkstrimitasActionPerformed

    private void chkGenetaliaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkGenetaliaActionPerformed
        if (chkGenetalia.isSelected() == true) {
            Genetalia.setVisible(true);
        } else {
            Genetalia.setVisible(false);
            Genetalia.setText("");
        }
    }//GEN-LAST:event_chkGenetaliaActionPerformed

    private void chkPunggungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPunggungActionPerformed
        if (chkPunggung.isSelected() == true) {
            Punggung.setVisible(true);
        } else {
            Punggung.setVisible(false);
            Punggung.setText("");
        }
    }//GEN-LAST:event_chkPunggungActionPerformed

    private void chkAbdomenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAbdomenActionPerformed
        if (chkAbdomen.isSelected() == true) {
            Abdomen.setVisible(true);
        } else {
            Abdomen.setVisible(false);
            Abdomen.setText("");
        }
    }//GEN-LAST:event_chkAbdomenActionPerformed

    private void chkDadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDadaActionPerformed
        if (chkDada.isSelected() == true) {
            Dada.setVisible(true);
        } else {
            Dada.setVisible(false);
            Dada.setText("");
        }
    }//GEN-LAST:event_chkDadaActionPerformed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//        triase.emptTeks();
        triase.isCek();
        triase.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        triase.setLocationRelativeTo(internalFrame1);
        triase.setAlwaysOnTop(false);
        triase.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());

    }//GEN-LAST:event_BtnTambahActionPerformed

    private void chkKepalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkKepalaActionPerformed
        if (chkKepala.isSelected() == true) {
            Kepala.setVisible(true);
        } else {
            Kepala.setVisible(false);
            Kepala.setText("");
        }
    }//GEN-LAST:event_chkKepalaActionPerformed

    private void chkMataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMataActionPerformed
        if (chkMata.isSelected() == true) {
            Mata.setVisible(true);
        } else {
            Mata.setVisible(false);
            Mata.setText("");
        }
    }//GEN-LAST:event_chkMataActionPerformed

    private void chkTHTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkTHTActionPerformed
        if (chkTHT.isSelected() == true) {
            THT.setVisible(true);
        } else {
            THT.setVisible(false);
            THT.setText("");
        }
    }//GEN-LAST:event_chkTHTActionPerformed

    private void chkMulutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMulutActionPerformed
        if (chkMulut.isSelected() == true) {
            Mulut.setVisible(true);
        } else {
            Mulut.setVisible(false);
            Mulut.setText("");
        }
    }//GEN-LAST:event_chkMulutActionPerformed

    private void chkLeherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkLeherActionPerformed
        if (chkLeher.isSelected() == true) {
            Leher.setVisible(true);
        } else {
            Leher.setVisible(false);
            Leher.setText("");
        }
    }//GEN-LAST:event_chkLeherActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgTriaseIGD dialog = new DlgTriaseIGD(new javax.swing.JFrame(), true);
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
    private widget.TextBox Abdomen;
    private widget.TextBox Agama;
    private widget.TextBox Alamat;
    private widget.TextBox Alergi;
    private widget.TextBox BB;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCariSkala;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox Dada;
    private widget.TextBox Diagnosa;
    private widget.TextBox Edukasi;
    private widget.TextBox Ekstrimitas;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormInput1;
    private widget.InternalFrame FormTriase;
    private widget.TextBox Genetalia;
    private widget.TextBox IMT;
    private widget.ComboBox Intervensi;
    private widget.TextBox JK;
    private widget.TextBox JnsBayar;
    private widget.TextBox Kd2;
    private widget.TextBox KdDokter;
    private widget.TextBox KdPetugas;
    private widget.TextArea KeluhanUtama;
    private widget.TextBox Kepala;
    private widget.TextArea Keterangan;
    private widget.Label LCount;
    private widget.TextBox LD;
    private widget.TextBox LILA;
    private widget.TextBox LK;
    private widget.TextBox LP;
    private widget.TextBox Leher;
    private widget.editorpane LoadHTML2;
    private widget.TextBox Mata;
    private javax.swing.JMenuItem MnCetakTriase;
    private widget.TextBox Mulut;
    private widget.TextBox NIK;
    private widget.TextBox Nadi;
    private widget.TextBox NmDokter;
    private widget.TextBox NmPetugas;
    private widget.TextBox NoTelp;
    private usu.widget.glass.PanelGlass PanelWall;
    private usu.widget.glass.PanelGlass PanelWall1;
    private widget.ComboBox Psikologis;
    private widget.TextBox Punggung;
    private widget.RadioButton R1;
    private widget.RadioButton R2;
    private widget.RadioButton R3;
    private widget.RadioButton R4;
    private widget.RadioButton R5;
    private widget.RadioButton R6;
    private widget.RadioButton R7;
    private widget.RadioButton R8;
    private widget.TextBox Respirasi;
    private widget.TextArea RiwayatPenyakit;
    private widget.TextBox Rujukan;
    private widget.TextBox Saturasi;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane ScrollTriase;
    private widget.ComboBox SttsFungsional;
    private widget.ComboBox SttsTinggal;
    private widget.TextBox Suhu;
    private widget.TextBox TB;
    private widget.TextBox TCari;
    private widget.TextBox TCariSkala;
    private widget.TextBox TDiantar;
    private widget.TextBox TFungsional;
    private widget.TextBox THT;
    private widget.TextBox TKasus;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TNoRw2;
    private widget.TextBox TPasien;
    private widget.TextBox TResiko;
    private widget.TextBox TTransportasi;
    private javax.swing.JTabbedPane TabPilihan;
    private widget.Tanggal Tanggal;
    private widget.TextBox Tensi;
    private widget.TextArea Terapi;
    private widget.TextBox TglLahir;
    private widget.TextBox Tindakan;
    private widget.ComboBox Transportasi;
    private widget.TextBox Umur;
    private widget.Button btnDokter;
    private widget.Button btnPetugas;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private widget.CekBox chkAbdomen;
    private widget.CekBox chkDada;
    private widget.CekBox chkEkstrimitas;
    private widget.CekBox chkGenetalia;
    private widget.CekBox chkKepala;
    private widget.CekBox chkLainnya;
    private widget.CekBox chkLakaGanda;
    private widget.CekBox chkLakaTunggal;
    private widget.CekBox chkLeher;
    private widget.CekBox chkMasukRS;
    private widget.CekBox chkMata;
    private widget.CekBox chkMulut;
    private widget.CekBox chkNontrauma;
    private widget.CekBox chkPunggung;
    private widget.CekBox chkTHT;
    private widget.CekBox chkTrauma;
    private widget.ComboBox cmbDiantar;
    private widget.ComboBox cmbResikoJatuh;
    private widget.ComboBox cmbSkalaNyeri;
    private widget.ComboBox cmbTriase;
    private widget.ComboBox dxKeperawatan;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
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
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
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
    private widget.Label jLabel49;
    private widget.Label jLabel5;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
    private widget.Label jLabel53;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel57;
    private widget.Label jLabel58;
    private widget.Label jLabel59;
    private widget.Label jLabel6;
    private widget.Label jLabel62;
    private widget.Label jLabel63;
    private widget.Label jLabel64;
    private widget.Label jLabel65;
    private widget.Label jLabel7;
    private widget.Label jLabel73;
    private widget.Label jLabel75;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private widget.Label jLabel94;
    private widget.Label jLabel95;
    private widget.Label jLabel96;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.Label label11;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.panelisi panelisi6;
    private widget.TextBox rMasukRS;
    private widget.TextBox rOperasi;
    private widget.TextBox rPengobatan;
    private widget.TextBox rTrauma;
    private widget.TextBox rpDahulu;
    private widget.TextBox rpKeluarga;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane6;
    private widget.Table tbLevel;
    private widget.Table tbTriase;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement(
                    "select a.no_rawat,a.tanggal, pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,"
                    + "concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_ktp,pasien.alamat,pasien.no_tlp,pasien.agama,"
                    + "penjab.png_jawab,a.namakasus,a.stts_diantar,a.transportasi,a.stts_fungsional,a.psikologis,a.stts_tinggal,"
                    + "b.keluhan,b.pemeriksaan,b.tensi,b.nadi,b.respirasi,b.suhu_tubuh,a.saturasi,a.lk,a.lila,b.tinggi,b.berat,a.imt,"
                    + "a.edukasi,a.riwayat_penyakit_dahulu,a.riwayat_pengobatan,a.riwayat_masuk_rs,a.riwayat_penyakit_keluarga,"
                    + "a.riwayat_operasi,a.riwayat_trauma,a.periksafisik,a.skala_nyeri,a.resiko_jatuh,a.nilai_resiko_jatuh,"
                    + "a.diagnosa_keperawatan,a.intervensi,a.diagnosis,a.tindakan,a.keterangan,a.kd_dokter,dokter.nm_dokter,a.kd_petugas,petugas.nama,a.jam, a.ld, a.lp "
                    + "from data_triase_igd as a,pemeriksaan_ralan as b,reg_periksa,pasien,dokter,petugas,penjab "
                    + "where a.no_rawat=reg_periksa.no_rawat and a.no_rawat=b.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "and a.kd_dokter=dokter.kd_dokter and a.kd_petugas=petugas.nip and reg_periksa.kd_pj=penjab.kd_pj "
                    + "and a.tanggal between ? and ? and (a.no_rawat like ? or "
                    + "pasien.no_rkm_medis like ? or "
                    + "pasien.nm_pasien like ?) group by a.tanggal, a.jam order by a.tanggal DESC, a.jam DESC");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00");
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59");
                ps.setString(3, "%" + TCari.getText().trim() + "%");
                ps.setString(4, "%" + TCari.getText().trim() + "%");
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new String[]{
                        rs.getString(1), rs.getString(2), rs.getString(51), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                        rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14),
                        rs.getString(15), rs.getString(16), rs.getString(17), rs.getString(18), rs.getString(19), rs.getString(20), rs.getString(21),
                        rs.getString(22), rs.getString(23), rs.getString(24), rs.getString(25), rs.getString(26),rs.getString(27),rs.getString("ld"),rs.getString("lp"),  rs.getString(28),
                        rs.getString(29), rs.getString(30), rs.getString(31), rs.getString(32), rs.getString(33), rs.getString(34), rs.getString(35),
                        rs.getString(36), rs.getString(37), rs.getString(38), rs.getString(39), rs.getString(40), rs.getString(41), rs.getString(42),
                        rs.getString(43), rs.getString(44), rs.getString(45), rs.getString(46), rs.getString(47), rs.getString(48), rs.getString(49),
                        rs.getString(50)
                    });
                    ps2 = koneksi.prepareStatement(
                            "select master_triase_igd.nm_level,master_triase_igd.nm_tindakan from detail_pemeriksaan_triase inner join master_triase_igd "
                            + "on detail_pemeriksaan_triase.kd_tindakan=master_triase_igd.kd_tindakan where detail_pemeriksaan_triase.no_rawat=?");
                    try {
                        ps2.setString(1, rs.getString("no_rawat"));
                        rs2 = ps2.executeQuery();
                        while (rs2.next()) {
                            tabMode.addRow(new Object[]{
                                "", "", "", "", rs2.getString("nm_level") + "     " + rs2.getString("nm_tindakan"), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                                "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "","",""
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notif 2 : " + e);
                    } finally {
                        if (rs2 != null) {
                            rs2.close();
                        }
                        if (ps2 != null) {
                            ps2.close();
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi 3: " + e);
        }
        LCount.setText("" + tabMode.getRowCount());
    }

    private void emptTeks() {
        Tanggal.setDate(new Date());
        Transportasi.setSelectedIndex(0);
        Psikologis.setSelectedIndex(0);
        cmbDiantar.setSelectedIndex(0);
        SttsFungsional.setSelectedIndex(0);
        SttsTinggal.setSelectedIndex(0);
        TKasus.setText("");
        Alergi.setText("");
        KeluhanUtama.setText("");
        Suhu.setText("");
        LK.setText("");
        LILA.setText("");
        Tensi.setText("");
        Nadi.setText("");
        Saturasi.setText("");
        Respirasi.setText("");
        TB.setText("");
        BB.setText("");
        IMT.setText("");
        KdPetugas.setText("");
        NmPetugas.setText("");
        TabPilihan.setSelectedIndex(0);
        Transportasi.requestFocus();
        RiwayatPenyakit.setText("");
        Edukasi.setText("");
        jmlskala1 = 0;
        kodepetugas = "";
        cmbTriase.setSelectedIndex(0);
        cmbSkalaNyeri.setSelectedIndex(0);
        cmbResikoJatuh.setSelectedIndex(0);
        rpDahulu.setText("");
        rPengobatan.setText("");
        rMasukRS.setText("");
        rpKeluarga.setText("");
        rOperasi.setText("");
        rTrauma.setText("");
        chkKepala.setSelected(false);
        Kepala.setText("");
        chkMata.setSelected(false);
        Mata.setText("");
        chkTHT.setSelected(false);
        THT.setText("");
        chkMulut.setSelected(false);
        Mulut.setText("");
        chkLeher.setSelected(false);
        Leher.setText("");
        chkDada.setSelected(false);
        Dada.setText("");
        chkAbdomen.setSelected(false);
        Abdomen.setText("");
        chkPunggung.setSelected(false);
        Punggung.setText("");
        chkGenetalia.setSelected(false);
        Genetalia.setText("");
        chkEkstrimitas.setSelected(false);
        Ekstrimitas.setText("");
        Diagnosa.setText("");
        Tindakan.setText("");
        Terapi.setText("");
        Keterangan.setText("");
        dxKeperawatan.setSelectedIndex(0);
        Intervensi.setSelectedIndex(0);
        chkNontrauma.setSelected(false);
        chkTrauma.setSelected(false);
        chkLakaTunggal.setSelected(false);
        chkLakaGanda.setSelected(false);
        chkLainnya.setSelected(false);
        LD.setText("");
        LP.setText("");
    }

    public void setNoRm(String norwt, String norm, String namapasien) {
        emptTeks();
        TNoRw.setText(norwt);
        TNoRM.setText(norm);
        TPasien.setText(namapasien);
        TCari.setText(norwt);
    }

    private void tampillevel() {
        try {
            jml = 0;
            for (i = 0; i < tbLevel.getRowCount(); i++) {
                if (tbLevel.getValueAt(i, 0).toString().equals("true")) {
                    jml++;
                }
            }

            pilih = null;
            pilih = new boolean[jml];
            kodelevel = null;
            kodelevel = new String[jml];
            level = null;
            level = new String[jml];
            kodetindakan = null;
            kodetindakan = new String[jml];
            pengkajian = null;
            pengkajian = new String[jml];

            index = 0;
            for (i = 0; i < tbLevel.getRowCount(); i++) {
                if (tbLevel.getValueAt(i, 0).toString().equals("true")) {
                    pilih[index] = true;
                    kodelevel[index] = tbLevel.getValueAt(i, 1).toString();
                    level[index] = tbLevel.getValueAt(i, 2).toString();
                    kodetindakan[index] = tbLevel.getValueAt(i, 3).toString();
                    pengkajian[index] = tbLevel.getValueAt(i, 4).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabModeLevel);

            for (i = 0; i < jml; i++) {
                tabModeLevel.addRow(new Object[]{
                    pilih[i], kodelevel[i], level[i], kodetindakan[i], pengkajian[i]
                });
            }
            if (null == cmbTriase.getSelectedItem().toString()) {
                key = " kd_pemeriksaan like '%%' ";
            } else switch (cmbTriase.getSelectedItem().toString()) {
                case "AIRWAY":
                    key = " kd_pemeriksaan='KP01' ";
                    break;
                case "BREATHING":
                    key = " kd_pemeriksaan='KP02' ";
                    break;
                case "CIRCULATION":
                    key = " kd_pemeriksaan='KP03' ";
                    break;
                case "DISABILITY":
                    key = " kd_pemeriksaan='KP04' ";
                    break;
                case "Prediksi pemeriksaan penunjang":
                    key = " kd_pemeriksaan='KP05' ";
                    break;
                case "Prediksi SDM yang akan terlibat":
                    key = " kd_pemeriksaan='KP06' ";
                    break;
                case "Do'a":
                    key = " kd_pemeriksaan='KP07' ";
                    break;
                default:
                    key = " kd_pemeriksaan like '%%' ";
                    break;
            }
            ps = koneksi.prepareStatement(
                    "select * from master_triase_igd where " + key + " and nm_tindakan like ? order by kd_level, kd_tindakan");
            try {
                ps.setString(1, "%" + TCariSkala.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeLevel.addRow(new Object[]{false, rs.getString("kd_level"), rs.getString("nm_level"), rs.getString("kd_tindakan"), rs.getString("nm_tindakan")});
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi 5: " + e);
        }
    }

    public void isCek() {
///        BtnSimpan.setEnabled(var.getdata_triase_igd());
//        BtnHapus.setEnabled(var.getdata_triase_igd());
//        BtnPrint.setEnabled(var.getdata_triase_igd());
//        BtnEdit.setEnabled(var.getdata_triase_igd());  
//        BtnTambahPemeriksaan.setEnabled(var.getmaster_triase_pemeriksaan());
//        BtnTambahPemeriksaan1.setEnabled(var.getmaster_triase_pemeriksaan());
//        BtnTambahSkala1.setEnabled(var.getmaster_triase_skala1());
//        BtnTambahSkala2.setEnabled(var.getmaster_triase_skala3());

        if (var.getjml2() >= 1) {
            btnDokter.setEnabled(false);
            KdDokter.setText(var.getkode());
            Sequel.cariIsi("select dokter.nm_dokter from dokter where dokter.kd_dokter=?", NmDokter, KdDokter.getText());
        }
        if (var.getkode().equals("Admin Utama")) {
            BtnTambah.setEnabled(true);
        } else {
            BtnTambah.setVisible(false);
        }
    }

    private void getData() {
        if (tbTriase.getSelectedRow() != -1) {
            TNoRw.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString());
            Tanggal.setDate(new Date());
            Sequel.cariIsi("SELECT no_rkm_medis FROM reg_periksa where no_rawat=? ", TNoRM, TNoRw.getText());
            Sequel.cariIsi("select pasien.nm_pasien from reg_periksa inner join pasien "
                    + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rawat=? ", TPasien, TNoRw.getText());
            JK.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 5).toString());
            TglLahir.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 6).toString());
            Umur.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 7).toString());
            NIK.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 8).toString());
            Alamat.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 9).toString());
            NoTelp.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 10).toString());
            Agama.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 11).toString());
            JnsBayar.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 12).toString());
            Psikologis.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(), 17).toString());
            SttsTinggal.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(), 18).toString());
            KeluhanUtama.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 19).toString());
            RiwayatPenyakit.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 20).toString());
            Tensi.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 21).toString());
            Nadi.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 22).toString());
            Respirasi.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 23).toString());
            Suhu.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 24).toString());
            Saturasi.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 25).toString());
            LK.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 26).toString());
            LILA.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 27).toString());
            LD.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 28).toString());
            LP.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 29).toString());
            TB.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 30).toString());
            BB.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 31).toString());
            IMT.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 32).toString());
            Edukasi.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 33).toString());
            rpDahulu.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 34).toString());
            rPengobatan.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 35).toString());
            rMasukRS.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 36).toString());
            rpKeluarga.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 37).toString());
            rOperasi.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 38).toString());
            rTrauma.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 39).toString());
            cmbSkalaNyeri.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(), 41).toString());
            cmbResikoJatuh.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(), 42).toString());
            dxKeperawatan.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(), 44).toString());
            Intervensi.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(), 45).toString());
            Diagnosa.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 46).toString());
            Tindakan.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 47).toString());
            Keterangan.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 48).toString());
            Sequel.cariIsi("select kd_dokter from data_triase_igd where no_rawat=?", KdDokter, TNoRw.getText());
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", NmDokter, KdDokter.getText());
            Sequel.cariIsi("select kd_petugas from data_triase_igd where no_rawat=?", KdPetugas, TNoRw.getText());
            Sequel.cariIsi("select nama from petugas where nip=?", NmPetugas, KdPetugas.getText());
        }
    }

    private void hapus() {
        if (TabPilihan.getSelectedIndex() == 1) {
            if (tabMode.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                TCari.requestFocus();
            } else if (tbTriase.getSelectedRow() <= -1) {
                JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data yang mau dihapus..!!");
            } else if (TNoRw.getText().trim().equals("")) {
                Valid.textKosong(TNoRw, "No. Rawat");
            } else {
                int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, Yakin mau dihapus..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
//                    if (Sequel.queryu2tf("delete from data_triase_igd where no_rawat=? and tanggal=? and jam=?", 3, new String[]{
//                        tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString(), tbTriase.getValueAt(tbTriase.getSelectedRow(), 1).toString(), tbTriase.getValueAt(tbTriase.getSelectedRow(), 2).toString()
//                    }) == true) {
//                        Sequel.queryu("delete from pemeriksaan_ralan where no_rawat='" + tbTriase.getValueAt(i, 0).toString()
//                                + "' and tgl_perawatan='" + tbTriase.getValueAt(i, 1).toString()
//                                + "' and jam_rawat='" + tbTriase.getValueAt(i, 2).toString() + "' ");
//                        Sequel.meghapus("detail_pemeriksaan_triase", "no_rawat", tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString());
//                        tampil();
//                    }
                    if (Sequel.queryu2tf("delete from detail_pemeriksaan_triase where no_rawat=?", 1, new String[]{
                        tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString()
                    }) == true) {
                        Sequel.queryu("delete from pemeriksaan_ralan where no_rawat='" + tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString()
                                + "' and tgl_perawatan='" + tbTriase.getValueAt(tbTriase.getSelectedRow(), 1).toString()
                                + "' and jam_rawat='" + tbTriase.getValueAt(tbTriase.getSelectedRow(), 2).toString() + "' ");
                        Sequel.queryu("delete from data_triase_igd where no_rawat='" + tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString()
                                + "' and tanggal='" + tbTriase.getValueAt(tbTriase.getSelectedRow(), 1).toString()
                                + "' and jam='" + tbTriase.getValueAt(tbTriase.getSelectedRow(), 2).toString() + "' ");
                        tampil();
                    }
                }
            }
        }
    }

    private void simpan() {
        System.out.println("simpan");
        try {
            koneksi.setAutoCommit(false);
//            for (i = 0; i < tbLevel.getRowCount(); i++) {
//                if (tbLevel.getValueAt(i, 0).toString().equals("true")) {
                    if (Sequel.menyimpantf("data_triase_igd", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "No.Rawat", 35, new String[]{
                        TNoRw.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""), Tanggal.getSelectedItem().toString().substring(11, 19),
                        KdDokter.getText(), KdPetugas.getText(), nmkasus, cmbantar, transport,
                        fungsional, Psikologis.getSelectedItem().toString(), SttsTinggal.getSelectedItem().toString(),
                        KeluhanUtama.getText(), RiwayatPenyakit.getText(), Saturasi.getText(), LK.getText(),
                        LILA.getText(), IMT.getText(),LD.getText(),LP.getText(), Edukasi.getText(), rpDahulu.getText(), rPengobatan.getText(), rMasukRS.getText(),
                        rpKeluarga.getText(), rOperasi.getText(), rTrauma.getText(), periksafisik, cmbSkalaNyeri.getSelectedItem().toString(), cmbResikoJatuh.getSelectedItem().toString(),
                        resiko, dxKeperawatan.getSelectedItem().toString(), Intervensi.getSelectedItem().toString(), Diagnosa.getText(), Tindakan.getText(), Keterangan.getText()
                    }) == true) {
                        if (Sequel.menyimpantf("pemeriksaan_ralan", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Data", 16, new String[]{
                            TNoRw.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""), Tanggal.getSelectedItem().toString().substring(11, 19),
                            Suhu.getText(), Tensi.getText(), Nadi.getText(), Respirasi.getText(), TB.getText(), BB.getText(), Alergi.getText(),
                            KeluhanUtama.getText(), RiwayatPenyakit.getText(), "",
                            "-", "", ""
                        }) == true) {
                            for (i = 0; i < tbLevel.getRowCount(); i++) {
                                if (tbLevel.getValueAt(i, 0).toString().equals("true")) {
                                    Sequel.menyimpan2("detail_pemeriksaan_triase", "?,?", "Pemeriksaan Triage", 2, new String[]{
                                        TNoRw.getText(), tbLevel.getValueAt(i, 3).toString()
                                    });
                                }
                            }                            
                        }
                        JOptionPane.showMessageDialog(null, "Berhasil Simpan.");
                        isReset();
                        TabPilihan.setSelectedIndex(1);
                        tampil();
                        emptTeks();
                    }
//                }
//            }
            koneksi.setAutoCommit(true);
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }
    }
    
    private void simpan2() {
        System.out.println("simpan2");
        try {
//            koneksi.setAutoCommit(false);
//            for (i = 0; i < tbLevel.getRowCount(); i++) {
//                if (tbLevel.getValueAt(i, 0).toString().equals("true")) {
            if (Sequel.mengedittf("data_triase_igd", "no_rawat=?", "kd_dokter=?,kd_petugas=?,namakasus=?,stts_diantar=?,transportasi=?,stts_fungsional=?,"
                    + "psikologis=?,stts_tinggal=?,keluhan_utama=?,riwayat_penyakit=?,saturasi=?,lk=?,lila=?,imt=?,ld=?,lp=?,"
                    + "edukasi=?,riwayat_penyakit_dahulu=?,riwayat_pengobatan=?,riwayat_masuk_rs=?,riwayat_penyakit_keluarga=?,"
                    + "riwayat_operasi=?,riwayat_trauma=?,periksafisik=?,skala_nyeri=?,resiko_jatuh=?,nilai_resiko_jatuh=?,"
                    + "diagnosa_keperawatan=?,intervensi=?,diagnosis=?,tindakan=?,keterangan=?", 33, new String[]{
                KdDokter.getText(), KdPetugas.getText(), nmkasus, cmbantar, transport,
                fungsional, Psikologis.getSelectedItem().toString(), SttsTinggal.getSelectedItem().toString(),
                KeluhanUtama.getText(), RiwayatPenyakit.getText(), Saturasi.getText(), LK.getText(),
                LILA.getText(), IMT.getText(), LD.getText(), LP.getText(), Edukasi.getText(), rpDahulu.getText(), rPengobatan.getText(), rMasukRS.getText(), rpKeluarga.getText(),
                rOperasi.getText(), rTrauma.getText(), periksafisik, cmbSkalaNyeri.getSelectedItem().toString(), cmbResikoJatuh.getSelectedItem().toString(),
                resiko, dxKeperawatan.getSelectedItem().toString(), Intervensi.getSelectedItem().toString(), Diagnosa.getText(), Tindakan.getText(), Keterangan.getText(),
                tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString()
            }) == true) {
                
//if (Sequel.mengedittf("data_triase_igd", "no_rawat=?",
//                    "tanggal=?,jam=?,kd_dokter=?,kd_petugas=?,namakasus=?,stts_diantar=?,transportasi=?,stts_fungsional=?,"
//                    + "psikologis=?,stts_tinggal=?,keluhan_utama=?,riwayat_penyakit=?,saturasi=?,lk=?,lila=?,imt=?,ld=?,lp=?,"
//                    + "edukasi=?,riwayat_penyakit_dahulu=?,riwayat_pengobatan=?,riwayat_masuk_rs=?,riwayat_penyakit_keluarga=?,"
//                    + "riwayat_operasi=?,riwayat_trauma=?,periksafisik=?,skala_nyeri=?,resiko_jatuh=?,nilai_resiko_jatuh=?,"
//                    + "diagnosa_keperawatan=?,intervensi=?,diagnosis=?,tindakan=?,keterangan=?",
//                    35, new String[]{
//                        Valid.SetTgl(Tanggal.getSelectedItem() + ""), Tanggal.getSelectedItem().toString().substring(11, 19),KdDokter.getText(), KdPetugas.getText(), nmkasus, cmbantar, transport,
//                        fungsional, Psikologis.getSelectedItem().toString(), SttsTinggal.getSelectedItem().toString(),
//                        KeluhanUtama.getText(), RiwayatPenyakit.getText(), Saturasi.getText(), LK.getText(),
//                        LILA.getText(), IMT.getText(),LD.getText(),LP.getText(), Edukasi.getText(), rpDahulu.getText(), rPengobatan.getText(), rMasukRS.getText(), rpKeluarga.getText(),
//                        rOperasi.getText(), rTrauma.getText(), periksafisik, cmbSkalaNyeri.getSelectedItem().toString(), cmbResikoJatuh.getSelectedItem().toString(),
//                        resiko, dxKeperawatan.getSelectedItem().toString(), Intervensi.getSelectedItem().toString(), Diagnosa.getText(), Tindakan.getText(), Keterangan.getText(),
//                        tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString()
//                    }) == true) {
                        System.out.println("edit triase 2");
                        if (Sequel.menyimpantf("pemeriksaan_ralan", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Data", 16, new String[]{
                            TNoRw.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""), Tanggal.getSelectedItem().toString().substring(11, 19),
                            Suhu.getText(), Tensi.getText(), Nadi.getText(), Respirasi.getText(), TB.getText(), BB.getText(), "",
                            KeluhanUtama.getText(), RiwayatPenyakit.getText(), "",
                            "-", "", ""
                        }) == true) {
                            System.out.println("simpan2 pemeriksaan_ralan");
                            for (i = 0; i < tbLevel.getRowCount(); i++) {
                                if (tbLevel.getValueAt(i, 0).toString().equals("true")) {
                                    Sequel.menyimpan2("detail_pemeriksaan_triase", "?,?", "Pemeriksaan Triage", 2, new String[]{
                                        TNoRw.getText(), tbLevel.getValueAt(i, 3).toString()
                                    });
                                    System.out.println("simpan2 detail_pemeriksaan_triase");
                                }
                            }                            
                        }
                        JOptionPane.showMessageDialog(null, "Berhasil Simpan.");
                        isReset();
                        TabPilihan.setSelectedIndex(1);
                        tampil();
                        emptTeks();
                    }
//                }
//            }
            koneksi.setAutoCommit(true);
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }
    }

    private void ganti() {
        Sequel.AutoComitFalse();
        sukses = true;
        jmlskala1 = 0;
        for (i = 0; i < tbLevel.getRowCount(); i++) {
            if (tbLevel.getValueAt(i, 0).toString().equals("true")) {
                jmlskala1++;
            }
        }
            if (KeluhanUtama.getText().trim().equals("")) {
                Valid.textKosong(KeluhanUtama, "Keluhan Utama");
            } else if (RiwayatPenyakit.getText().trim().equals("")) {
                Valid.textKosong(RiwayatPenyakit, "Riwayat Penyakit");
            } else if (Tensi.getText().trim().equals("")) {
                Valid.textKosong(Tensi, "Tensi");
            } else if (Nadi.getText().trim().equals("")) {
                Valid.textKosong(Nadi, "Nadi");
            } else if (Suhu.getText().trim().equals("")) {
                Valid.textKosong(Suhu, "Suhu");
            } else if (Respirasi.getText().trim().equals("")) {
                Valid.textKosong(Respirasi, "Respirasi");
            } else if (Saturasi.getText().trim().equals("")) {
                Valid.textKosong(Saturasi, "Saturasi O²");
            } else if (TB.getText().trim().equals("")) {
                Valid.textKosong(TB, "TB");
            } else if (BB.getText().trim().equals("")) {
                Valid.textKosong(BB, "BB");
            } else if (KdPetugas.getText().trim().equals("") || NmPetugas.getText().trim().equals("")) {
                Valid.textKosong(btnPetugas, "Petugas");
        } else {
            if (Sequel.mengedittf("data_triase_igd", "no_rawat=? and tanggal=? and jam=?",
                    "kd_dokter=?,kd_petugas=?,namakasus=?,stts_diantar=?,transportasi=?,stts_fungsional=?,"
                    + "psikologis=?,stts_tinggal=?,keluhan_utama=?,riwayat_penyakit=?,saturasi=?,lk=?,lila=?,imt=?,ld=?,lp=?,"
                    + "edukasi=?,riwayat_penyakit_dahulu=?,riwayat_pengobatan=?,riwayat_masuk_rs=?,riwayat_penyakit_keluarga=?,"
                    + "riwayat_operasi=?,riwayat_trauma=?,periksafisik=?,skala_nyeri=?,resiko_jatuh=?,nilai_resiko_jatuh=?,"
                    + "diagnosa_keperawatan=?,intervensi=?,diagnosis=?,tindakan=?,keterangan=?",
                    35, new String[]{
                        KdDokter.getText(), KdPetugas.getText(), nmkasus, cmbantar, transport,
                        fungsional, Psikologis.getSelectedItem().toString(), SttsTinggal.getSelectedItem().toString(),
                        KeluhanUtama.getText(), RiwayatPenyakit.getText(), Saturasi.getText(), LK.getText(),
                        LILA.getText(), IMT.getText(),LD.getText(),LP.getText(), Edukasi.getText(), rpDahulu.getText(), rPengobatan.getText(), rMasukRS.getText(), rpKeluarga.getText(),
                        rOperasi.getText(), rTrauma.getText(), periksafisik, cmbSkalaNyeri.getSelectedItem().toString(), cmbResikoJatuh.getSelectedItem().toString(),
                        resiko, dxKeperawatan.getSelectedItem().toString(), Intervensi.getSelectedItem().toString(), Diagnosa.getText(), Tindakan.getText(), Keterangan.getText(),
                        tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString(), tbTriase.getValueAt(tbTriase.getSelectedRow(), 1).toString(), tbTriase.getValueAt(tbTriase.getSelectedRow(), 2).toString()
                    }) == true) {
                Sequel.queryu2("update pemeriksaan_ralan set suhu_tubuh=?,tensi=?,nadi=?,respirasi=?,tinggi=?,berat=?,keluhan=?,pemeriksaan=?,alergi=? "
                        + " where no_rawat=? and tgl_perawatan=? and jam_rawat=?", 12,
                        new String[]{Suhu.getText(), Tensi.getText(), Nadi.getText(), Respirasi.getText(), TB.getText(), BB.getText(), KeluhanUtama.getText(), RiwayatPenyakit.getText(),Alergi.getText(),
                            tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString(), tbTriase.getValueAt(tbTriase.getSelectedRow(), 1).toString(),
                            tbTriase.getValueAt(tbTriase.getSelectedRow(), 2).toString()
                        });
                for (i = 0; i < tbLevel.getRowCount(); i++) {
                    if (tbLevel.getValueAt(i, 0).toString().equals("true")) {
                        if (Sequel.menyimpantf2("detail_pemeriksaan_triase", "?,?", "Pemeriksaan Triage", 2, new String[]{
                            TNoRw.getText(), tbLevel.getValueAt(i, 3).toString()
                        }) == true) {
                            tbLevel.setValueAt(false, i, 0);
                        } else {
                            sukses = false;
                        }
                    }
                }
                JOptionPane.showMessageDialog(null, "Berhasil mengedit data..");                
                emptTeks();
                tampil();
            } else {
                sukses = false;
            }
            if (sukses == true) {
                Sequel.Commit();
            } else {
                sukses = false;
                JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat pemrosesan data, input data dibatalkan.\nPeriksa kembali data sebelum melanjutkan mengganti..!!");
                Sequel.RollBack();
            }
//            Sequel.AutoComitTrue();
//            if (sukses == true) {
//                JOptionPane.showMessageDialog(null, "Berhasil mengedit data..");
//                emptTeks();
//                tampil();
//                TabPilihan.setSelectedIndex(1);
//            }
        }
    }

    private void insertPasien() {
        if (R1.isSelected() == true) {
            resiko = "R1: Rendah (0-24)";
        } else if (R2.isSelected() == true) {
            resiko = "R2: Sedang (25-44)";
        } else if (R3.isSelected() == true) {
            resiko = "R3: Tinggi (>= 45)";
        } else if (R4.isSelected() == true) {
            resiko = "R4: Rendah (7-11)";
        } else if (R5.isSelected() == true) {
            resiko = "R5: Tinggi (>=12)";
        } else if (R6.isSelected() == true) {
            resiko = "R6: Rendah (0-5)";
        } else if (R7.isSelected() == true) {
            resiko = "R7: Sedang (6-16)";
        } else if (R8.isSelected() == true) {
            resiko = "R8: Tinggi (17-30)";
        }

        cmbantar = "";
        switch (cmbDiantar.getSelectedIndex()) {
            case 1:
                cmbantar = cmbantar + "Keluarga, " + TDiantar.getText();
                break;
            case 2:
                cmbantar = cmbantar + "Polisi, " + TDiantar.getText();
                break;
            case 3:
                cmbantar = cmbantar + "Petugas Kesehatan, " + TDiantar.getText();
                break;
            case 4:
                cmbantar = cmbantar + "Datang Sendiri, " + TDiantar.getText();
                break;
            case 5:
                cmbantar = cmbantar + "Lainnya, " + TDiantar.getText();
                break;
        }

        transport = "";
        switch (Transportasi.getSelectedIndex()) {
            case 1:
                transport = transport + "Ambulance, " + TTransportasi.getText();
                break;
            case 2:
                transport = transport + "Mobil Polisi, " + TTransportasi.getText();
                break;
            case 3:
                transport = transport + "Jalan Kaki, " + TTransportasi.getText();
                break;
            case 4:
                transport = transport + "Lainnya, " + TTransportasi.getText();
                break;
        }

        fungsional = "";
        switch (SttsFungsional.getSelectedIndex()) {
            case 1:
                fungsional = fungsional + "Mandiri, " + TFungsional.getText();
                break;
            case 2:
                fungsional = fungsional + "Perlu bantuan, " + TFungsional.getText();
                break;
            case 3:
                fungsional = fungsional + "Alat bantu, " + TFungsional.getText();
                break;
        }

        periksafisik = "";
        if (chkKepala.isSelected()) {
            periksafisik = periksafisik + "1. " + Kepala.getText() + ";";
        }
        if (chkMata.isSelected()) {
            periksafisik = periksafisik + "2. " + Mata.getText() + ";";
        }
        if (chkTHT.isSelected()) {
            periksafisik = periksafisik + "3. " + THT.getText() + ";";
        }
        if (chkMulut.isSelected()) {
            periksafisik = periksafisik + "4. " + Mulut.getText() + ";";
        }
        if (chkLeher.isSelected()) {
            periksafisik = periksafisik + "5. " + Leher.getText() + ";";
        }
        if (chkDada.isSelected()) {
            periksafisik = periksafisik + "6. " + Dada.getText() + ";";
        }
        if (chkAbdomen.isSelected()) {
            periksafisik = periksafisik + "7. " + Abdomen.getText() + ";";
        }
        if (chkPunggung.isSelected()) {
            periksafisik = periksafisik + "8. " + Punggung.getText() + ";";
        }
        if (chkGenetalia.isSelected()) {
            periksafisik = periksafisik + "9. " + Genetalia.getText() + ";";
        }
        if (chkEkstrimitas.isSelected()) {
            periksafisik = periksafisik + "10. " + Ekstrimitas.getText() + ";";
        }

        if (periksafisik.endsWith(";")) {
            periksafisik = periksafisik.substring(0, periksafisik.length() - 1);
        }

        nmkasus = "";
        if (chkNontrauma.isSelected() == true) {
            nmkasus = nmkasus + "Non Trauma" + ";";
        }
        if (chkTrauma.isSelected() == true) {
            nmkasus = nmkasus + "Trauma" + ";";
        }
        if (chkLakaTunggal.isSelected() == true) {
            nmkasus = nmkasus + "Laka Tunggal" + ";";
        }
        if (chkLakaGanda.isSelected() == true) {
            nmkasus = nmkasus + "Laka Ganda" + ";";
        }
        if (chkLainnya.isSelected() == true) {
            nmkasus = nmkasus + "Lainnya, " + TKasus.getText() + ";";
        }

        if (nmkasus.endsWith(";")) {
            nmkasus = nmkasus.substring(0, nmkasus.length() - 1);
        }
    }

    public void setDokter(String norwt) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        KdDokter.setText(Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?", norwt));
        Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", NmDokter, KdDokter.getText());
        isReset();
    }

    public void isReset() {
        jml = tbLevel.getRowCount();
        for (i = 0; i < jml; i++) {
            tbLevel.setValueAt(false, i, 0);
        }
        Valid.tabelKosong(tabModeLevel);
        tampil();
    }

    private void isIMT() {
        if ((!TB.getText().equals("")) && (!BB.getText().equals(""))) {
            try {
                IMT.setText(Valid.SetAngka8(Valid.SetAngka(BB.getText()) / ((Valid.SetAngka(TB.getText()) / 100) * (Valid.SetAngka(TB.getText()) / 100)), 1) + "");
            } catch (Exception e) {
                IMT.setText("");
            }
        }
    }

    public void setNoRM(String norawat) {
        TabPilihan.setSelectedIndex(0);
        TNoRw.setText(norawat);
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=?", TNoRM, TNoRw.getText());
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", TPasien, TNoRM.getText());
        Sequel.cariIsi("select jk from pasien where no_rkm_medis=?", JK, TNoRM.getText());
        Sequel.cariIsi("select tgl_lahir from pasien where no_rkm_medis=?", TglLahir, TNoRM.getText());
        Sequel.cariIsi("select concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur) from reg_periksa where no_rawat=?", Umur, TNoRw.getText());
        Sequel.cariIsi("select no_ktp from pasien where no_rkm_medis=?", NIK, TNoRM.getText());
        Sequel.cariIsi("select alamat from pasien where no_rkm_medis=?", Alamat, TNoRM.getText());
        Sequel.cariIsi("select agama from pasien where no_rkm_medis=?", Agama, TNoRM.getText());
        Sequel.cariIsi("select no_tlp from pasien where no_rkm_medis=?", NoTelp, TNoRM.getText());
        Sequel.cariIsi("select penjab.png_jawab from reg_periksa inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where no_rawat=?", JnsBayar, TNoRw.getText());
        Sequel.cariIsi("select perujuk from rujuk_masuk where no_rawat=?", Rujukan, TNoRw.getText());
        Sequel.cariIsi("SELECT GROUP_CONCAT(detail_pemberian_obat.jml,' ', databarang.nama_brng,' ',aturan_pakai.aturan,'\n') "
                + "FROM detail_pemberian_obat inner join aturan_pakai inner join databarang "
                + "ON detail_pemberian_obat.no_rawat=aturan_pakai.no_rawat and detail_pemberian_obat.kode_brng=aturan_pakai.kode_brng and aturan_pakai.kode_brng=databarang.kode_brng "
                + "WHERE detail_pemberian_obat.no_rawat=?", Terapi, TNoRw.getText());
        setRiwayat();
        emptTeks();
    }

    public void getData2() {
        nmkasus = tbTriase.getValueAt(tbTriase.getSelectedRow(), 13).toString();
        String[] kasus = nmkasus.split(";");
        for (int i = 0; i < kasus.length; i++) {
//            System.out.println("i " + kasus[i]);
            String[] kss = kasus[i].split(", ");
            for (int j = 0; j < kss.length; j++) {
//                System.out.println("j " + kss[j]);
                switch (kss[0]) {
                    case "Non Trauma":
                        chkNontrauma.setSelected(true);
                        break;
                    case "Trauma":
                        chkTrauma.setSelected(true);
                        break;
                    case "Laka Tunggal":
                        chkLakaTunggal.setSelected(true);
                        break;
                    case "Laka Ganda":
                        chkLakaGanda.setSelected(true);
                        break;
                    case "Lainnya":
                        chkLainnya.setSelected(true);
                        TKasus.setText(kss[1]);
                        break;
                }
            }
        }

        cmbantar = tbTriase.getValueAt(tbTriase.getSelectedRow(), 14).toString();
        String[] diantar = cmbantar.split(", ");
        for (int i = 0; i < diantar.length; i++) {
            switch (diantar[0]) {
                case "Keluarga":
                    cmbDiantar.setSelectedIndex(1);
                    TDiantar.setText(diantar[1]);
                    break;
                case "Polisi":
                    cmbDiantar.setSelectedIndex(2);
                    TDiantar.setText(diantar[1]);
                    break;
                case "Petugas Kesehatan":
                    cmbDiantar.setSelectedIndex(3);
                    TDiantar.setText(diantar[1]);
                    break;
                case "Datang Sendiri":
                    cmbDiantar.setSelectedIndex(4);
                    TDiantar.setText(diantar[1]);
                    break;
                case "Lainnya":
                    cmbDiantar.setSelectedIndex(5);
                    TDiantar.setText(diantar[1]);
                    break;
            }
        }

        transport = tbTriase.getValueAt(tbTriase.getSelectedRow(), 15).toString();
        String[] trans = transport.split(", ");
        for (int i = 0; i < trans.length; i++) {
            switch (trans[0]) {
                case "Ambulance":
                    Transportasi.setSelectedIndex(1);
                    TTransportasi.setText(trans[1]);
                    break;
                case "Mobil Polisi":
                    Transportasi.setSelectedIndex(2);
                    TTransportasi.setText(trans[1]);
                    break;
                case "Jalan Kaki":
                    Transportasi.setSelectedIndex(3);
                    TTransportasi.setText(trans[1]);
                    break;
                case "Lainnya":
                    Transportasi.setSelectedIndex(4);
                    TTransportasi.setText(trans[1]);
                    break;
            }
        }

        fungsional = tbTriase.getValueAt(tbTriase.getSelectedRow(), 16).toString();
        String[] fungsi = fungsional.split(", ");
        for (int i = 0; i < fungsi.length; i++) {
            switch (fungsi[0]) {
                case "Mandiri":
                    SttsFungsional.setSelectedIndex(1);
                    TFungsional.setText(fungsi[1]);
                    break;
                case "Perlu bantuan":
                    SttsFungsional.setSelectedIndex(2);
                    TFungsional.setText(fungsi[1]);
                    break;
                case "Alat bantu":
                    SttsFungsional.setSelectedIndex(3);
                    TFungsional.setText(fungsi[1]);
                    break;
            }
        }

        periksafisik = tbTriase.getValueAt(tbTriase.getSelectedRow(), 40).toString();
        String[] splite = periksafisik.split(";");
        for (int i = 0; i < splite.length; i++) {
            String[] fisik = splite[i].split(". ");
            for (int j = 0; j < fisik.length; j++) {
                switch (fisik[0]) {
                    case "1":
                        chkKepala.setSelected(true);
                        Kepala.setVisible(true);
                        Kepala.setText(fisik[1]);
                        break;
                    case "2":
                        chkMata.setSelected(true);
                        Mata.setVisible(true);
                        Mata.setText(fisik[1]);
                        break;
                    case "3":
                        chkTHT.setSelected(true);
                        THT.setVisible(true);
                        THT.setText(fisik[1]);
                        break;
                    case "4":
                        chkMulut.setSelected(true);
                        Mulut.setVisible(true);
                        Mulut.setText(fisik[1]);
                        break;
                    case "5":
                        chkLeher.setSelected(true);
                        Leher.setVisible(true);
                        Leher.setText(fisik[1]);
                        break;
                    case "6":
                        chkDada.setSelected(true);
                        Dada.setVisible(true);
                        Dada.setText(fisik[1]);
                        break;
                    case "7":
                        chkAbdomen.setSelected(true);
                        Abdomen.setVisible(true);
                        Abdomen.setText(fisik[1]);
                        break;
                    case "8":
                        chkPunggung.setSelected(true);
                        Punggung.setVisible(true);
                        Punggung.setText(fisik[1]);
                        break;
                    case "9":
                        chkGenetalia.setSelected(true);
                        Genetalia.setVisible(true);
                        Genetalia.setText(fisik[1]);
                        break;
                    case "10":
                        chkEkstrimitas.setSelected(true);
                        Ekstrimitas.setVisible(true);
                        Ekstrimitas.setText(fisik[1]);
                        break;
                }
            }
        }

        resiko = tbTriase.getValueAt(tbTriase.getSelectedRow(), 43).toString();
        String[] rsk = resiko.split(": ");
        for (int i = 0; i < rsk.length; i++) {
            switch (rsk[0]) {
                case "R1":
                    R1.setSelected(true);
                    break;
                case "R2":
                    R2.setSelected(true);
                    break;
                case "R3":
                    R3.setSelected(true);
                    break;
                case "R4":
                    R4.setSelected(true);
                    break;
                case "R5":
                    R5.setSelected(true);
                    break;
                case "R6":
                    R6.setSelected(true);
                    break;
                case "R7":
                    R7.setSelected(true);
                    break;
                case "R8":
                    R8.setSelected(true);
                    break;
            }
        }
    }

    public void setRiwayat() {
        TNoRw2.setText(Sequel.cariIsi("select reg_periksa.no_rawat from reg_periksa  where stts<>'Batal' and no_rkm_medis=? order by tgl_registrasi,jam_reg limit 1", TNoRM.getText()));
        Sequel.cariIsi("select GROUP_CONCAT(penyakit.kd_penyakit) from reg_periksa inner join diagnosa_pasien inner join penyakit "
                + "on reg_periksa.no_rawat=diagnosa_pasien.no_rawat and diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "
                + "where diagnosa_pasien.no_rawat=?", rpDahulu, TNoRw2.getText());
        Sequel.cariIsi("select paket_operasi.nm_perawatan from reg_periksa inner join pasien inner join operasi inner join paket_operasi "
                + "on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.no_rawat=operasi.no_rawat and operasi.kode_paket=paket_operasi.kode_paket "
                + "where reg_periksa.no_rkm_medis=?", rOperasi, TNoRM.getText());
    }
}