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
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPegawai;
import kepegawaian.DlgCariPetugas;

/**
 *
 * @author perpustakaan
 */
public final class DlgTriaseIGD extends javax.swing.JDialog {

    private DefaultTableModel tabMode, tabModePemeriksaan, tabModeSkala1, tabModeSkala2, tabModeSkala3, tabModeSkala4, tabModeSkala5;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps, ps2, ps3;
    private ResultSet rs, rs2, rs3;
    private int i = 0, jml = 0, index = 0, jmlskala1 = 0, jmlskala2 = 0, jmlskala3 = 0, jmlskala4 = 0, jmlskala5 = 0;
    private DlgMasterTriaseMacamKasus kasus = new DlgMasterTriaseMacamKasus(null, false);
    private boolean[] pilih;
    private String[] kode, pengkajian;
    private DlgCariDokter dokter = new DlgCariDokter(null, false);
    private DlgCariPetugas petugas = new DlgCariPetugas(null, false);
    private String keputusan = "", pilihan = "", datatriase = "", finger = "", kodepetugas = "", norm = "";
    private StringBuilder htmlContent;
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
            "No.Rawat", "No.RM", "Nama Pasien", "Tgl.Kunjungan", "Jns. Bayar", "Kasus", "Catatan", "Dokter"
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

        for (i = 0; i < 8; i++) {
            TableColumn column = tbTriase.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(105);
            } else if (i == 1) {
                column.setPreferredWidth(70);
            } else if (i == 2) {
                column.setPreferredWidth(300);
            } else if (i == 3) {
                column.setPreferredWidth(80);
            } else if (i == 4) {
                column.setPreferredWidth(65);
            } else if (i == 5) {
                column.setPreferredWidth(200);
            } else {
                column.setPreferredWidth(150);
            }
        }
        tbTriase.setDefaultRenderer(Object.class, new WarnaTable());

        tabModePemeriksaan = new DefaultTableModel(null, new Object[]{
            "Kode Pemeriksaan", "Pemeriksaan"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbPemeriksaan.setModel(tabModePemeriksaan);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbPemeriksaan.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbPemeriksaan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 2; i++) {
            TableColumn column = tbPemeriksaan.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 1) {
                column.setPreferredWidth(395);
            }
        }
        tbPemeriksaan.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeSkala1 = new DefaultTableModel(null, new Object[]{
            "P", "Kode", "Immediate/Segera"
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
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbSkala1.setModel(tabModeSkala1);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbSkala1.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbSkala1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbSkala1.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 2) {
                column.setPreferredWidth(340);
            }
        }
//        tbSkala1.getTableHeader().setForeground(new Color(170,00,0));
        tbSkala1.getTableHeader().setForeground(new Color(237, 28, 36));
        tbSkala1.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeSkala2 = new DefaultTableModel(null, new Object[]{
            "P", "Kode", "Emergensi"
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
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbSkala2.setModel(tabModeSkala2);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbSkala2.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbSkala2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbSkala2.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 2) {
                column.setPreferredWidth(340);
            }
        }
        tbSkala2.getTableHeader().setForeground(new Color(204, 204, 0));
        tbSkala2.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeSkala3 = new DefaultTableModel(null, new Object[]{
            "P", "Kode", "Urgensi"
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
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbSkala3.setModel(tabModeSkala3);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbSkala3.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbSkala3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbSkala3.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 2) {
                column.setPreferredWidth(340);
            }
        }
        tbSkala3.getTableHeader().setForeground(new Color(204, 204, 0));
        tbSkala3.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeSkala4 = new DefaultTableModel(null, new Object[]{
            "P", "Kode", "Semi Urgensi/Urgensi Rendah"
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
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbSkala4.setModel(tabModeSkala4);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbSkala4.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbSkala4.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbSkala4.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 2) {
                column.setPreferredWidth(340);
            }
        }
        tbSkala4.getTableHeader().setForeground(new Color(204, 204, 0));
        tbSkala4.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeSkala5 = new DefaultTableModel(null, new Object[]{
            "P", "Kode", "Non Urgensi"
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
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbSkala5.setModel(tabModeSkala5);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbSkala5.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbSkala5.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbSkala5.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 2) {
                column.setPreferredWidth(340);
            }
        }
        tbSkala5.getTableHeader().setForeground(new Color(114, 191, 68));
        tbSkala5.setDefaultRenderer(Object.class, new WarnaTable());

        TCari.setDocument(new batasInput((int) 100).getKata(TCari));
        TCariPemeriksaan.setDocument(new batasInput((int) 100).getKata(TCariPemeriksaan));
        TCariSkala.setDocument(new batasInput((int) 100).getKata(TCariSkala));
        Suhu.setDocument(new batasInput((byte) 5).getKata(Suhu));
        LK.setDocument(new batasInput((byte) 5).getKata(LK));
        Tensi.setDocument(new batasInput((byte) 8).getKata(Tensi));
        Nadi.setDocument(new batasInput((byte) 3).getKata(Nadi));
        Saturasi.setDocument(new batasInput((byte) 3).getKata(Saturasi));
        Respirasi.setDocument(new batasInput((byte) 3).getKata(Respirasi));
        Diagnosa.setDocument(new batasInput((int) 100).getKata(Diagnosa));
        KeluhanUtama.setDocument(new batasInput((int) 400).getKata(KeluhanUtama));

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

        kasus.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (kasus.getTable().getSelectedRow() != -1) {
                    KdKasus.setText(kasus.getTable().getValueAt(kasus.getTable().getSelectedRow(), 0).toString());
                    NmKasus.setText(kasus.getTable().getValueAt(kasus.getTable().getSelectedRow(), 1).toString());
                }
                btnKasus.requestFocus();
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

        kasus.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    kasus.dispose();
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

        HTMLEditorKit kit = new HTMLEditorKit();
//        LoadHTML.setEditable(true);
//        LoadHTML.setEditorKit(kit);
        LoadHTML2.setEditable(true);
        LoadHTML2.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                + ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"
                + ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                + ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                + ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"
                + ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"
                + ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"
                + ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"
                + ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
        );

        Document doc = kit.createDefaultDocument();
//        LoadHTML.setDocument(doc);
        LoadHTML2.setDocument(doc);

//        ChkAccor.setSelected(false);
//        isMenu();
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
        internalFrame1 = new widget.InternalFrame();
        TabPilihan = new javax.swing.JTabbedPane();
        ScrollTriase = new widget.ScrollPane();
        FormTriase = new widget.InternalFrame();
        internalFrame5 = new widget.InternalFrame();
        internalFrame7 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        KeluhanUtama = new widget.TextArea();
        jLabel9 = new widget.Label();
        jLabel24 = new widget.Label();
        jLabel10 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel16 = new widget.Label();
        Saturasi = new widget.TextBox();
        jLabel25 = new widget.Label();
        LK = new widget.TextBox();
        Nadi = new widget.TextBox();
        jLabel26 = new widget.Label();
        Respirasi = new widget.TextBox();
        jLabel27 = new widget.Label();
        Tensi = new widget.TextBox();
        jLabel11 = new widget.Label();
        jLabel35 = new widget.Label();
        LILA = new widget.TextBox();
        jLabel36 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        RiwayatPenyakit = new widget.TextArea();
        TB = new widget.TextBox();
        BB = new widget.TextBox();
        jLabel39 = new widget.Label();
        Alergi = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel40 = new widget.Label();
        IMT = new widget.TextBox();
        internalFrame8 = new widget.InternalFrame();
        label14 = new widget.Label();
        Diagnosa = new widget.TextBox();
        label15 = new widget.Label();
        Catatan = new widget.TextBox();
        label16 = new widget.Label();
        Intervensi = new widget.TextBox();
        internalFrame3 = new widget.InternalFrame();
        internalFrame9 = new widget.InternalFrame();
        jPanel3 = new javax.swing.JPanel();
        panelisi5 = new widget.panelisi();
        label10 = new widget.Label();
        TCariPemeriksaan = new widget.TextBox();
        BtnCariPemeriksaan = new widget.Button();
        BtnTambahPemeriksaan = new widget.Button();
        Scroll2 = new widget.ScrollPane();
        tbPemeriksaan = new widget.Table();
        jPanel4 = new javax.swing.JPanel();
        panelisi6 = new widget.panelisi();
        label11 = new widget.Label();
        TCariSkala = new widget.TextBox();
        BtnCariSkala = new widget.Button();
        BtnTambahSkala = new widget.Button();
        TabLevelTriase = new javax.swing.JTabbedPane();
        Scroll3 = new widget.ScrollPane();
        tbSkala1 = new widget.Table();
        Scroll4 = new widget.ScrollPane();
        tbSkala2 = new widget.Table();
        Scroll9 = new widget.ScrollPane();
        tbSkala3 = new widget.Table();
        Scroll10 = new widget.ScrollPane();
        tbSkala4 = new widget.Table();
        Scroll11 = new widget.ScrollPane();
        tbSkala5 = new widget.Table();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel18 = new widget.Label();
        Tanggal = new widget.Tanggal();
        jLabel22 = new widget.Label();
        Transportasi = new widget.ComboBox();
        jLabel23 = new widget.Label();
        SttsDiantar = new widget.ComboBox();
        jLabel5 = new widget.Label();
        KdKasus = new widget.TextBox();
        AlatBantu = new widget.TextBox();
        btnKasus = new widget.Button();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel43 = new widget.Label();
        jLabel45 = new widget.Label();
        SttsFungsional = new widget.ComboBox();
        jLabel42 = new widget.Label();
        Psikologis = new widget.ComboBox();
        jLabel46 = new widget.Label();
        jLabel44 = new widget.Label();
        SttsTinggal = new widget.ComboBox();
        NmKasus = new widget.TextBox();
        Edukasi = new widget.TextBox();
        jLabel14 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel12 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        btnDokter = new widget.Button();
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
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        BtnEdit = new widget.Button();

        LoadHTML2.setBorder(null);
        LoadHTML2.setName("LoadHTML2"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCetakTriase.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakTriase.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakTriase.setForeground(new java.awt.Color(70, 70, 70));
        MnCetakTriase.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakTriase.setText("Cetak Permintaan Radiologi");
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Triase IGD ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(2040, 553));
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        TabPilihan.setBackground(new java.awt.Color(255, 255, 254));
        TabPilihan.setForeground(new java.awt.Color(50, 50, 50));
        TabPilihan.setName("TabPilihan"); // NOI18N
        TabPilihan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabPilihanMouseClicked(evt);
            }
        });

        ScrollTriase.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        ScrollTriase.setName("ScrollTriase"); // NOI18N
        ScrollTriase.setOpaque(true);

        FormTriase.setBorder(null);
        FormTriase.setName("FormTriase"); // NOI18N
        FormTriase.setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame5.setBorder(null);
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout());

        internalFrame7.setBorder(null);
        internalFrame7.setName("internalFrame7"); // NOI18N
        internalFrame7.setPreferredSize(new java.awt.Dimension(0, 130));
        internalFrame7.setLayout(null);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        KeluhanUtama.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        KeluhanUtama.setColumns(20);
        KeluhanUtama.setRows(5);
        KeluhanUtama.setName("KeluhanUtama"); // NOI18N
        KeluhanUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtamaKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(KeluhanUtama);

        internalFrame7.add(scrollPane1);
        scrollPane1.setBounds(95, 10, 305, 53);

        jLabel9.setText("Keluhan Utama :");
        jLabel9.setName("jLabel9"); // NOI18N
        internalFrame7.add(jLabel9);
        jLabel9.setBounds(0, 10, 90, 23);

        jLabel24.setText("TB (cm) :");
        jLabel24.setName("jLabel24"); // NOI18N
        internalFrame7.add(jLabel24);
        jLabel24.setBounds(465, 10, 60, 23);

        jLabel10.setText("T(°C) :");
        jLabel10.setName("jLabel10"); // NOI18N
        internalFrame7.add(jLabel10);
        jLabel10.setBounds(290, 70, 50, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        internalFrame7.add(Suhu);
        Suhu.setBounds(345, 70, 55, 23);

        jLabel16.setText("Sp.O²(%) :");
        jLabel16.setName("jLabel16"); // NOI18N
        internalFrame7.add(jLabel16);
        jLabel16.setBounds(140, 100, 79, 23);

        Saturasi.setFocusTraversalPolicyProvider(true);
        Saturasi.setName("Saturasi"); // NOI18N
        Saturasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SaturasiKeyPressed(evt);
            }
        });
        internalFrame7.add(Saturasi);
        Saturasi.setBounds(225, 100, 55, 23);

        jLabel25.setText("LK (cm) :");
        jLabel25.setName("jLabel25"); // NOI18N
        internalFrame7.add(jLabel25);
        jLabel25.setBounds(290, 100, 50, 23);

        LK.setHighlighter(null);
        LK.setName("LK"); // NOI18N
        LK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LKKeyPressed(evt);
            }
        });
        internalFrame7.add(LK);
        LK.setBounds(345, 100, 55, 23);

        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        Nadi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NadiActionPerformed(evt);
            }
        });
        Nadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NadiKeyPressed(evt);
            }
        });
        internalFrame7.add(Nadi);
        Nadi.setBounds(225, 70, 55, 23);

        jLabel26.setText("N (x/mnt) :");
        jLabel26.setName("jLabel26"); // NOI18N
        internalFrame7.add(jLabel26);
        jLabel26.setBounds(140, 70, 79, 23);

        Respirasi.setHighlighter(null);
        Respirasi.setName("Respirasi"); // NOI18N
        Respirasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RespirasiKeyPressed(evt);
            }
        });
        internalFrame7.add(Respirasi);
        Respirasi.setBounds(95, 100, 55, 23);

        jLabel27.setText("RR(x/mnt) :");
        jLabel27.setName("jLabel27"); // NOI18N
        internalFrame7.add(jLabel27);
        jLabel27.setBounds(0, 100, 90, 23);

        Tensi.setHighlighter(null);
        Tensi.setName("Tensi"); // NOI18N
        Tensi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TensiActionPerformed(evt);
            }
        });
        Tensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TensiKeyPressed(evt);
            }
        });
        internalFrame7.add(Tensi);
        Tensi.setBounds(95, 70, 55, 23);

        jLabel11.setText("TD (mmHg) :");
        jLabel11.setName("jLabel11"); // NOI18N
        internalFrame7.add(jLabel11);
        jLabel11.setBounds(0, 70, 90, 23);

        jLabel35.setText("LILA (cm) :");
        jLabel35.setName("jLabel35"); // NOI18N
        internalFrame7.add(jLabel35);
        jLabel35.setBounds(465, 40, 60, 23);

        LILA.setHighlighter(null);
        LILA.setName("LILA"); // NOI18N
        LILA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LILAKeyPressed(evt);
            }
        });
        internalFrame7.add(LILA);
        LILA.setBounds(530, 40, 55, 23);

        jLabel36.setText("Riwayat Penyakit :");
        jLabel36.setName("jLabel36"); // NOI18N
        internalFrame7.add(jLabel36);
        jLabel36.setBounds(425, 70, 100, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        RiwayatPenyakit.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RiwayatPenyakit.setColumns(20);
        RiwayatPenyakit.setRows(5);
        RiwayatPenyakit.setName("RiwayatPenyakit"); // NOI18N
        RiwayatPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPenyakitKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(RiwayatPenyakit);

        internalFrame7.add(scrollPane4);
        scrollPane4.setBounds(530, 70, 305, 53);

        TB.setName("TB"); // NOI18N
        TB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBKeyPressed(evt);
            }
        });
        internalFrame7.add(TB);
        TB.setBounds(530, 10, 55, 23);

        BB.setHighlighter(null);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        internalFrame7.add(BB);
        BB.setBounds(660, 10, 55, 23);

        jLabel39.setText("BB (kg) :");
        jLabel39.setName("jLabel39"); // NOI18N
        internalFrame7.add(jLabel39);
        jLabel39.setBounds(575, 10, 79, 23);

        Alergi.setName("Alergi"); // NOI18N
        Alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        internalFrame7.add(Alergi);
        Alergi.setBounds(660, 40, 175, 23);

        jLabel20.setText("Alergi :");
        jLabel20.setName("jLabel20"); // NOI18N
        internalFrame7.add(jLabel20);
        jLabel20.setBounds(575, 40, 79, 23);

        jLabel40.setText("IMT :");
        jLabel40.setName("jLabel40"); // NOI18N
        internalFrame7.add(jLabel40);
        jLabel40.setBounds(695, 10, 79, 23);

        IMT.setEditable(false);
        IMT.setHighlighter(null);
        IMT.setName("IMT"); // NOI18N
        IMT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IMTKeyPressed(evt);
            }
        });
        internalFrame7.add(IMT);
        IMT.setBounds(780, 10, 55, 23);

        internalFrame5.add(internalFrame7, java.awt.BorderLayout.PAGE_START);

        internalFrame8.setBorder(null);
        internalFrame8.setName("internalFrame8"); // NOI18N
        internalFrame8.setPreferredSize(new java.awt.Dimension(100, 74));
        internalFrame8.setLayout(null);

        label14.setText("Diagnosa :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(60, 23));
        internalFrame8.add(label14);
        label14.setBounds(0, 10, 65, 23);

        Diagnosa.setToolTipText("Alt+C");
        Diagnosa.setName("Diagnosa"); // NOI18N
        Diagnosa.setPreferredSize(new java.awt.Dimension(140, 23));
        Diagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaKeyPressed(evt);
            }
        });
        internalFrame8.add(Diagnosa);
        Diagnosa.setBounds(69, 10, 310, 23);

        label15.setText("Catatan :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        internalFrame8.add(label15);
        label15.setBounds(0, 40, 65, 23);

        Catatan.setToolTipText("Alt+C");
        Catatan.setName("Catatan"); // NOI18N
        Catatan.setPreferredSize(new java.awt.Dimension(140, 23));
        Catatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CatatanKeyPressed(evt);
            }
        });
        internalFrame8.add(Catatan);
        Catatan.setBounds(69, 40, 723, 23);

        label16.setText("Intervensi :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        internalFrame8.add(label16);
        label16.setBounds(382, 10, 90, 23);

        Intervensi.setToolTipText("Alt+C");
        Intervensi.setName("Intervensi"); // NOI18N
        Intervensi.setPreferredSize(new java.awt.Dimension(140, 23));
        Intervensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IntervensiKeyPressed(evt);
            }
        });
        internalFrame8.add(Intervensi);
        Intervensi.setBounds(480, 10, 310, 23);

        internalFrame5.add(internalFrame8, java.awt.BorderLayout.PAGE_END);

        internalFrame3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 4, 0, 4));
        internalFrame3.setMinimumSize(new java.awt.Dimension(543, 119));
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setPreferredSize(new java.awt.Dimension(700, 354));
        internalFrame3.setLayout(new java.awt.BorderLayout());

        internalFrame9.setBorder(null);
        internalFrame9.setName("internalFrame9"); // NOI18N
        internalFrame9.setPreferredSize(new java.awt.Dimension(791, 352));
        internalFrame9.setLayout(new java.awt.BorderLayout(2, 1));

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(390, 352));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi5.setBorder(null);
        panelisi5.setName("panelisi5"); // NOI18N
        panelisi5.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi5.add(label10);

        TCariPemeriksaan.setToolTipText("Alt+C");
        TCariPemeriksaan.setName("TCariPemeriksaan"); // NOI18N
        TCariPemeriksaan.setPreferredSize(new java.awt.Dimension(140, 23));
        TCariPemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariPemeriksaanKeyPressed(evt);
            }
        });
        panelisi5.add(TCariPemeriksaan);

        BtnCariPemeriksaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariPemeriksaan.setMnemonic('1');
        BtnCariPemeriksaan.setToolTipText("Alt+1");
        BtnCariPemeriksaan.setName("BtnCariPemeriksaan"); // NOI18N
        BtnCariPemeriksaan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariPemeriksaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariPemeriksaanActionPerformed(evt);
            }
        });
        BtnCariPemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariPemeriksaanKeyPressed(evt);
            }
        });
        panelisi5.add(BtnCariPemeriksaan);

        BtnTambahPemeriksaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahPemeriksaan.setMnemonic('3');
        BtnTambahPemeriksaan.setToolTipText("Alt+3");
        BtnTambahPemeriksaan.setName("BtnTambahPemeriksaan"); // NOI18N
        BtnTambahPemeriksaan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahPemeriksaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahPemeriksaanActionPerformed(evt);
            }
        });
        panelisi5.add(BtnTambahPemeriksaan);

        jPanel3.add(panelisi5, java.awt.BorderLayout.PAGE_END);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbPemeriksaan.setName("tbPemeriksaan"); // NOI18N
        tbPemeriksaan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPemeriksaanMouseClicked(evt);
            }
        });
        tbPemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPemeriksaanKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPemeriksaanKeyReleased(evt);
            }
        });
        Scroll2.setViewportView(tbPemeriksaan);

        jPanel3.add(Scroll2, java.awt.BorderLayout.CENTER);

        internalFrame9.add(jPanel3, java.awt.BorderLayout.WEST);

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(295, 352));
        jPanel4.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi6.setBorder(null);
        panelisi6.setName("panelisi6"); // NOI18N
        panelisi6.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label11.setForeground(new java.awt.Color(170, 0, 0));
        label11.setText("Key Word :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi6.add(label11);

        TCariSkala.setForeground(new java.awt.Color(170, 0, 0));
        TCariSkala.setToolTipText("Alt+C");
        TCariSkala.setName("TCariSkala"); // NOI18N
        TCariSkala.setPreferredSize(new java.awt.Dimension(200, 23));
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
        BtnCariSkala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariSkalaKeyPressed(evt);
            }
        });
        panelisi6.add(BtnCariSkala);

        BtnTambahSkala.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahSkala.setMnemonic('3');
        BtnTambahSkala.setToolTipText("Alt+3");
        BtnTambahSkala.setName("BtnTambahSkala"); // NOI18N
        BtnTambahSkala.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahSkala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahSkalaActionPerformed(evt);
            }
        });
        panelisi6.add(BtnTambahSkala);

        jPanel4.add(panelisi6, java.awt.BorderLayout.PAGE_END);

        TabLevelTriase.setBackground(new java.awt.Color(255, 255, 255));
        TabLevelTriase.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TabLevelTriase.setForeground(new java.awt.Color(50, 50, 50));
        TabLevelTriase.setName("TabLevelTriase"); // NOI18N
        TabLevelTriase.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabLevelTriaseMouseClicked(evt);
            }
        });

        Scroll3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll3.setForeground(new java.awt.Color(170, 0, 0));
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbSkala1.setForeground(new java.awt.Color(170, 0, 0));
        tbSkala1.setName("tbSkala1"); // NOI18N
        tbSkala1.setSelectionForeground(new java.awt.Color(0, 255, 0));
        Scroll3.setViewportView(tbSkala1);

        TabLevelTriase.addTab("Level 1", Scroll3);

        Scroll4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);

        tbSkala2.setForeground(new java.awt.Color(255, 242, 0));
        tbSkala2.setName("tbSkala2"); // NOI18N
        tbSkala2.setSelectionForeground(new java.awt.Color(0, 255, 0));
        Scroll4.setViewportView(tbSkala2);

        TabLevelTriase.addTab("Level 2", Scroll4);

        Scroll9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll9.setName("Scroll9"); // NOI18N
        Scroll9.setOpaque(true);

        tbSkala3.setForeground(new java.awt.Color(255, 242, 0));
        tbSkala3.setName("tbSkala3"); // NOI18N
        tbSkala3.setSelectionForeground(new java.awt.Color(0, 255, 0));
        Scroll9.setViewportView(tbSkala3);

        TabLevelTriase.addTab("Level 3", Scroll9);

        Scroll10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll10.setName("Scroll10"); // NOI18N
        Scroll10.setOpaque(true);

        tbSkala4.setForeground(new java.awt.Color(255, 242, 0));
        tbSkala4.setName("tbSkala4"); // NOI18N
        tbSkala4.setSelectionForeground(new java.awt.Color(0, 255, 0));
        Scroll10.setViewportView(tbSkala4);

        TabLevelTriase.addTab("Level 4", Scroll10);

        Scroll11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        Scroll11.setName("Scroll11"); // NOI18N
        Scroll11.setOpaque(true);

        tbSkala5.setForeground(new java.awt.Color(0, 170, 0));
        tbSkala5.setName("tbSkala5"); // NOI18N
        tbSkala5.setSelectionForeground(new java.awt.Color(0, 255, 0));
        Scroll11.setViewportView(tbSkala5);

        TabLevelTriase.addTab("Level 5", Scroll11);

        jPanel4.add(TabLevelTriase, java.awt.BorderLayout.CENTER);
        TabLevelTriase.getAccessibleContext().setAccessibleName("Level 1");

        internalFrame9.add(jPanel4, java.awt.BorderLayout.CENTER);

        internalFrame3.add(internalFrame9, java.awt.BorderLayout.WEST);

        internalFrame5.add(internalFrame3, java.awt.BorderLayout.CENTER);

        FormTriase.add(internalFrame5, java.awt.BorderLayout.CENTER);

        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 140));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 110, 23);

        TNoRw.setBackground(new java.awt.Color(245, 250, 240));
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        FormInput.add(TNoRw);
        TNoRw.setBounds(115, 10, 122, 23);

        TPasien.setBackground(new java.awt.Color(245, 250, 240));
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(309, 10, 220, 23);

        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TNoRMActionPerformed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(238, 10, 70, 23);

        jLabel18.setText("Tanggal :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(1005, 10, 89, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-02-2023 09:36:45" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(1100, 10, 140, 23);

        jLabel22.setText("Transportasi :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(535, 110, 89, 23);

        Transportasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ambulance", "Mobil Polisi", "Jalan Kaki", "Kendaraan umum", "Kendaraan pribadi" }));
        Transportasi.setName("Transportasi"); // NOI18N
        Transportasi.setPreferredSize(new java.awt.Dimension(55, 28));
        Transportasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TransportasiKeyPressed(evt);
            }
        });
        FormInput.add(Transportasi);
        Transportasi.setBounds(630, 110, 140, 23);

        jLabel23.setText("Diantar oleh :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(1005, 40, 89, 23);

        SttsDiantar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Sendiri", "Keluarga", "Polisi", "Petugas Kesehatan", "Rujukan" }));
        SttsDiantar.setName("SttsDiantar"); // NOI18N
        SttsDiantar.setPreferredSize(new java.awt.Dimension(55, 28));
        SttsDiantar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SttsDiantarKeyPressed(evt);
            }
        });
        FormInput.add(SttsDiantar);
        SttsDiantar.setBounds(1100, 40, 140, 23);

        jLabel5.setText("Macam Kasus :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 40, 110, 23);

        KdKasus.setEditable(false);
        KdKasus.setHighlighter(null);
        KdKasus.setName("KdKasus"); // NOI18N
        KdKasus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdKasusKeyPressed(evt);
            }
        });
        FormInput.add(KdKasus);
        KdKasus.setBounds(115, 40, 55, 23);

        AlatBantu.setName("AlatBantu"); // NOI18N
        AlatBantu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlatBantuKeyPressed(evt);
            }
        });
        FormInput.add(AlatBantu);
        AlatBantu.setBounds(115, 110, 140, 23);

        btnKasus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKasus.setMnemonic('1');
        btnKasus.setToolTipText("Alt+1");
        btnKasus.setName("btnKasus"); // NOI18N
        btnKasus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKasusActionPerformed(evt);
            }
        });
        btnKasus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnKasusKeyPressed(evt);
            }
        });
        FormInput.add(btnKasus);
        btnKasus.setBounds(503, 40, 28, 23);

        jSeparator13.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator13.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator13.setName("jSeparator13"); // NOI18N
        FormInput.add(jSeparator13);
        jSeparator13.setBounds(10, 70, 850, 1);

        jLabel43.setText("Status Fungsional :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(0, 80, 110, 23);

        jLabel45.setText("Alat Bantu :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(0, 110, 110, 23);

        SttsFungsional.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Mandiri", "Alat bantu" }));
        SttsFungsional.setName("SttsFungsional"); // NOI18N
        SttsFungsional.setPreferredSize(new java.awt.Dimension(55, 28));
        SttsFungsional.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SttsFungsionalKeyPressed(evt);
            }
        });
        FormInput.add(SttsFungsional);
        SttsFungsional.setBounds(115, 80, 140, 23);

        jLabel42.setText("Psikologis :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(250, 80, 110, 23);

        Psikologis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Stabil", "Marah", "Cemas", "Takut", "Gelisah", "Percobaan bunuh diri", "Halusinasi" }));
        Psikologis.setName("Psikologis"); // NOI18N
        Psikologis.setPreferredSize(new java.awt.Dimension(55, 28));
        Psikologis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PsikologisKeyPressed(evt);
            }
        });
        FormInput.add(Psikologis);
        Psikologis.setBounds(365, 80, 140, 23);

        jLabel46.setText("Edukasi :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(250, 110, 110, 23);

        jLabel44.setText("Tinggal dengan :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(535, 80, 89, 23);

        SttsTinggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Sendiri", "Keluarga", "Orang Tua", "Suami / Istri", "Teman", "Tunawisma" }));
        SttsTinggal.setName("SttsTinggal"); // NOI18N
        SttsTinggal.setPreferredSize(new java.awt.Dimension(55, 28));
        SttsTinggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SttsTinggalKeyPressed(evt);
            }
        });
        FormInput.add(SttsTinggal);
        SttsTinggal.setBounds(630, 80, 140, 23);

        NmKasus.setEditable(false);
        NmKasus.setName("NmKasus"); // NOI18N
        NmKasus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmKasusKeyPressed(evt);
            }
        });
        FormInput.add(NmKasus);
        NmKasus.setBounds(172, 40, 330, 23);

        Edukasi.setName("Edukasi"); // NOI18N
        Edukasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdukasiKeyPressed(evt);
            }
        });
        FormInput.add(Edukasi);
        Edukasi.setBounds(365, 110, 140, 23);

        jLabel14.setText("Petugas :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(555, 40, 70, 23);

        KdPetugas.setHighlighter(null);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(630, 40, 107, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(740, 40, 210, 23);

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
        btnPetugas.setBounds(950, 40, 28, 23);

        jLabel12.setText("Dokter :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(555, 10, 70, 23);

        KdDokter.setEditable(false);
        KdDokter.setHighlighter(null);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(630, 10, 107, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmDokterKeyPressed(evt);
            }
        });
        FormInput.add(NmDokter);
        NmDokter.setBounds(740, 10, 210, 23);

        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter.setMnemonic('1');
        btnDokter.setToolTipText("Alt+1");
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        btnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDokterKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btnDokterKeyReleased(evt);
            }
        });
        FormInput.add(btnDokter);
        btnDokter.setBounds(950, 10, 28, 23);

        FormTriase.add(FormInput, java.awt.BorderLayout.PAGE_START);

        ScrollTriase.setViewportView(FormTriase);

        TabPilihan.addTab("Input Triase", ScrollTriase);

        internalFrame4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbTriase.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbTriase.setName("tbTriase"); // NOI18N
        tbTriase.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTriaseMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbTriaseMouseReleased(evt);
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-02-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-02-2023" }));
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

        TabPilihan.addTab("Data Triase", internalFrame4);

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

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "Pasien");
        } else if (KdKasus.getText().trim().equals("") || AlatBantu.getText().trim().equals("")) {
            Valid.textKosong(btnKasus, "Macam Kasus");
        } else {
            Sequel.AutoComitFalse();
            sukses = true;
//            if(TabTriase.getSelectedIndex()==0){
            jmlskala1 = 0;
            jmlskala2 = 0;
            jmlskala3 = 0;
            jmlskala4 = 0;
            jmlskala5 = 0;
            for (i = 0; i < tbSkala1.getRowCount(); i++) {
                if (tbSkala1.getValueAt(i, 0).toString().equals("true")) {
                    jmlskala1++;
                }
            }
            for (i = 0; i < tbSkala2.getRowCount(); i++) {
                if (tbSkala2.getValueAt(i, 0).toString().equals("true")) {
                    jmlskala2++;
                }
            }
            for (i = 0; i < tbSkala3.getRowCount(); i++) {
                if (tbSkala3.getValueAt(i, 0).toString().equals("true")) {
                    jmlskala3++;
                }
            }
            for (i = 0; i < tbSkala4.getRowCount(); i++) {
                if (tbSkala4.getValueAt(i, 0).toString().equals("true")) {
                    jmlskala4++;
                }
            }
            for (i = 0; i < tbSkala5.getRowCount(); i++) {
                if (tbSkala5.getValueAt(i, 0).toString().equals("true")) {
                    jmlskala5++;
                }
            }
            if (KeluhanUtama.getText().trim().equals("")) {
                Valid.textKosong(KeluhanUtama, "Keluhan Utama");
            } else if (Suhu.getText().trim().equals("")) {
                Valid.textKosong(Suhu, "Suhu");
            } else if (LK.getText().trim().equals("")) {
                Valid.textKosong(LK, "Nyeri");
            } else if (Tensi.getText().trim().equals("")) {
                Valid.textKosong(Tensi, "Tensi");
            } else if (Nadi.getText().trim().equals("")) {
                Valid.textKosong(Nadi, "Nadi");
            } else if (Saturasi.getText().trim().equals("")) {
                Valid.textKosong(Saturasi, "Saturasi O²");
            } else if (Respirasi.getText().trim().equals("")) {
                Valid.textKosong(Respirasi, "Respirasi");
            } else if (Diagnosa.getText().trim().equals("")) {
                Valid.textKosong(Diagnosa, "Catatan");
            } else if (KdDokter.getText().trim().equals("") || NmDokter.getText().trim().equals("")) {
                Valid.textKosong(btnDokter, "Dokter/Petugas Triase");
//            } else if ((jmlskala1 == 0) && (jmlskala2 == 0)) {
//                Valid.textKosong(TCariPemeriksaan, "Skala 1 / Skala 2");
            } else {
                if (Sequel.menyimpantf("data_triase_igd", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "No.Rawat", 28, new String[]{
                    TNoRw.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + "") + " " + Tanggal.getSelectedItem().toString().substring(11, 19),
                    SttsFungsional.getSelectedItem().toString(), SttsDiantar.getSelectedItem().toString(), Transportasi.getSelectedItem().toString(),
                    KeluhanUtama.getText(), RiwayatPenyakit.getText(), KdKasus.getText(), Tensi.getText(), Nadi.getText(), Respirasi.getText(), Suhu.getText(),
                    Saturasi.getText(), LK.getText(), LILA.getText(), TB.getText(), BB.getText(), IMT.getText(), Alergi.getText().toString(), Diagnosa.getText(),
                    Intervensi.getText(), Catatan.getText(), KdDokter.getText(), KdPetugas.getText(), Psikologis.getSelectedItem().toString(), SttsTinggal.getSelectedItem().toString(),
                    AlatBantu.getText(), Edukasi.getText()
                }) == true) {
                    if (TabLevelTriase.getSelectedIndex() == 0) {
                        for (i = 0; i < tbSkala1.getRowCount(); i++) {
                            if (tbSkala1.getValueAt(i, 0).toString().equals("true")) {
                                if (Sequel.menyimpantf2("data_triase_igddetail_skala1", "?,?,?", "Level 1", 3, new String[]{
                                    TNoRw.getText(), tbSkala1.getValueAt(i, 1).toString(), "Level 1"
                                }) == true) {
                                    tbSkala1.setValueAt(false, i, 0);
                                } else {
                                    sukses = false;
                                }
                            }
                        }

                    }
                } else {
                    sukses = false;
                }
            }

            if (sukses == true) {
                Sequel.Commit();
            } else {
                sukses = false;
                JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat pemrosesan data, input data dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                Sequel.RollBack();
            }
            Sequel.AutoComitTrue();
            if (sukses == true) {
                emptTeks();
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
//        if(TabTriase.getSelectedIndex()==0){
        Valid.pindah(evt, btnDokter, BtnBatal);
//        }
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
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else {
            if (tbTriase.getSelectedRow() != -1) {
                if (var.getkode().equals("Admin Utama")) {
                    hapus();
                } else {
                    if (var.getkode().equals(kodepetugas)) {
                        hapus();
                    } else {
                        JOptionPane.showMessageDialog(null, "Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
                    }
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
        } else if (KdKasus.getText().trim().equals("") || AlatBantu.getText().trim().equals("")) {
            Valid.textKosong(btnKasus, "Macam Kasus");
//        }else if(KetVaksin.equals("")){
//            Valid.textKosong(btnKasus,"Keterangan");
        } else {
            if (tbTriase.getSelectedRow() > -1) {
                if (var.getkode().equals("Admin Utama")) {
                    ganti();
                } else {
                    if (var.getkode().equals(kodepetugas)) {
                        ganti();
                    } else {
                        JOptionPane.showMessageDialog(null, "Hanya bisa diganti oleh petugas yang bersangkutan..!!");
                    }
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
                        + tabMode.getValueAt(i, 7).toString() + "','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''", "Kerohanian");
            }
            System.out.println("menyimpan");
            Sequel.menyimpan("temporary", "'0','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''", "Kerohanian");

            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptLapTriaseIGD.jrxml", "report", "::[ Data Triase IGD ]::",
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
//        if (tabMode.getRowCount() != 0) {
//            if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
//                TCari.setText("");
//                TCari.requestFocus();
//            } else if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
//                if (tbTriase.getSelectedRow() != -1) {
//                    try {
//                        Valid.tabelKosong(tabModeSkala1);
//                        Valid.tabelKosong(tabModeSkala2);
//                        Valid.tabelKosong(tabModeSkala3);
//                        Valid.tabelKosong(tabModeSkala4);
//                        Valid.tabelKosong(tabModeSkala5);
//                        TNoRw.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString());
//                        TNoRM.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 1).toString());
//                        TPasien.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 2).toString());
//                        Valid.SetTgl2(Tanggal, tbTriase.getValueAt(tbTriase.getSelectedRow(), 3).toString());
//                        Alergi.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 4).toString());
//                        Transportasi.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(), 5).toString());
//                        SttsDiantar.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(), 6).toString());
////                        KetVaksin.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(),7).toString());
//                        KdKasus.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 8).toString());
//                        AlatBantu.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 9).toString());
//                        TabPilihan.setSelectedIndex(0);
//                        ps = koneksi.prepareStatement(
//                                "select data_triase_igd.keluhan_utama,data_triase_igd.catatan,"
//                                + "data_triase_igd.tanggal,data_triase_igd.tekanan_darah,"
//                                + "data_triase_igd.nadi,data_triase_igd.pernapasan,data_triase_igd.suhu,data_triase_igd.saturasi_o2,data_triase_igd.nyeri,"
//                                + "data_triase_igd.no_rawat from data_triase_igd where data_triase_igd.no_rawat=?");
//                        try {
//                            ps.setString(1, tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString());
//                            rs = ps.executeQuery();
//                            if (rs.next()) {
//                                kodepetugas = rs.getString("nik");
//                                KeluhanUtama.setText(rs.getString("keluhan_utama"));
//                                Suhu.setText(rs.getString("suhu"));
//                                LK.setText(rs.getString("nyeri"));
//                                Tensi.setText(rs.getString("tekanan_darah"));
//                                Nadi.setText(rs.getString("nadi"));
//                                Saturasi.setText(rs.getString("saturasi_o2"));
//                                Respirasi.setText(rs.getString("pernapasan"));
//                                TB.setText(rs.getString("kebutuhan_khusus"));
////                                TabTriase.setSelectedIndex(0);
//
//                                ps2 = koneksi.prepareStatement(
//                                        "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "
//                                        + "from master_triase_pemeriksaan inner join master_triase_skala1 inner join data_triase_igddetail_skala1 "
//                                        + "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala1.kode_pemeriksaan and "
//                                        + "master_triase_skala1.kode_skala1=data_triase_igddetail_skala1.kode_skala1 where data_triase_igddetail_skala1.no_rawat=? "
//                                        + "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan");
//                                try {
//                                    ps2.setString(1, rs.getString("no_rawat"));
//                                    rs2 = ps2.executeQuery();
//                                    if (rs2.next()) {
//                                        TabLevelTriase.setSelectedIndex(0);
//                                        Valid.tabelKosong(tabModePemeriksaan);
//                                        Valid.tabelKosong(tabModeSkala1);
//                                        rs2.beforeFirst();
//                                        while (rs2.next()) {
//                                            tabModePemeriksaan.addRow(new String[]{rs2.getString("kode_pemeriksaan"), rs2.getString("nama_pemeriksaan")});
//                                            ps3 = koneksi.prepareStatement(
//                                                    "select master_triase_skala1.kode_skala1,master_triase_skala1.pengkajian_skala1 from master_triase_skala1 inner join data_triase_igddetail_skala1 "
//                                                    + "on master_triase_skala1.kode_skala1=data_triase_igddetail_skala1.kode_skala1 where "
//                                                    + "master_triase_skala1.kode_pemeriksaan=? and data_triase_igddetail_skala1.no_rawat=? "
//                                                    + "order by data_triase_igddetail_skala1.kode_skala1");
//                                            try {
//                                                ps3.setString(1, rs2.getString("kode_pemeriksaan"));
//                                                ps3.setString(2, rs.getString("no_rawat"));
//                                                rs3 = ps3.executeQuery();
//                                                while (rs3.next()) {
//                                                    tabModeSkala1.addRow(new Object[]{true, rs3.getString("kode_skala1"), rs3.getString("pengkajian_skala1")});
//                                                }
//                                            } catch (Exception e) {
//                                                System.out.println("Notif : " + e);
//                                            } finally {
//                                                if (rs3 != null) {
//                                                    rs3.close();
//                                                }
//                                                if (ps3 != null) {
//                                                    ps3.close();
//                                                }
//                                            }
//                                        }
//                                    }
//                                } catch (Exception e) {
//                                    System.out.println("Notif : " + e);
//                                } finally {
//                                    if (rs2 != null) {
//                                        rs2.close();
//                                    }
//                                    if (ps2 != null) {
//                                        ps2.close();
//                                    }
//                                }
//
//                                ps2 = koneksi.prepareStatement(
//                                        "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "
//                                        + "from master_triase_pemeriksaan inner join master_triase_skala2 inner join data_triase_igddetail_skala2 "
//                                        + "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala2.kode_pemeriksaan and "
//                                        + "master_triase_skala2.kode_skala2=data_triase_igddetail_skala2.kode_skala2 where data_triase_igddetail_skala2.no_rawat=? "
//                                        + "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan");
//                                try {
//                                    ps2.setString(1, rs.getString("no_rawat"));
//                                    rs2 = ps2.executeQuery();
//                                    if (rs2.next()) {
//                                        TabLevelTriase.setSelectedIndex(1);
//                                        Valid.tabelKosong(tabModePemeriksaan);
//                                        Valid.tabelKosong(tabModeSkala2);
//                                        rs2.beforeFirst();
//                                        while (rs2.next()) {
//                                            tabModePemeriksaan.addRow(new String[]{rs2.getString("kode_pemeriksaan"), rs2.getString("nama_pemeriksaan")});
//                                            ps3 = koneksi.prepareStatement(
//                                                    "select master_triase_skala2.kode_skala2,master_triase_skala2.pengkajian_skala2 from master_triase_skala2 inner join data_triase_igddetail_skala2 "
//                                                    + "on master_triase_skala2.kode_skala2=data_triase_igddetail_skala2.kode_skala2 where "
//                                                    + "master_triase_skala2.kode_pemeriksaan=? and data_triase_igddetail_skala2.no_rawat=? "
//                                                    + "order by data_triase_igddetail_skala2.kode_skala2");
//                                            try {
//                                                ps3.setString(1, rs2.getString("kode_pemeriksaan"));
//                                                ps3.setString(2, rs.getString("no_rawat"));
//                                                rs3 = ps3.executeQuery();
//                                                while (rs3.next()) {
//                                                    tabModeSkala2.addRow(new Object[]{true, rs3.getString("kode_skala2"), rs3.getString("pengkajian_skala2")});
//                                                }
//                                            } catch (Exception e) {
//                                                System.out.println("Notif : " + e);
//                                            } finally {
//                                                if (rs3 != null) {
//                                                    rs3.close();
//                                                }
//                                                if (ps3 != null) {
//                                                    ps3.close();
//                                                }
//                                            }
//                                        }
//                                    }
//                                } catch (Exception e) {
//                                    System.out.println("Notif : " + e);
//                                } finally {
//                                    if (rs2 != null) {
//                                        rs2.close();
//                                    }
//                                    if (ps2 != null) {
//                                        ps2.close();
//                                    }
//                                }
//
////                                PrimerTanggalTriase.setDate(rs.getDate("tanggaltriase"));
//                                Diagnosa.setText(rs.getString("catatan"));
//                            }
//                        } catch (Exception e) {
//                            System.out.println("Notifikasi 2: " + e);
//                        } finally {
//                            if (rs != null) {
//                                rs.close();
//                            }
//                            if (ps != null) {
//                                ps.close();
//                            }
//                        }

//                        ps=koneksi.prepareStatement(
//                                "select data_triase_igdsekunder.anamnesa_singkat,data_triase_igdsekunder.catatan,"+
//                                "data_triase_igdsekunder.plan,data_triase_igdsekunder.tanggaltriase,data_triase_igdsekunder.nik,data_triase_igd.tekanan_darah,"+
//                                "data_triase_igd.nadi,data_triase_igd.pernapasan,data_triase_igd.suhu,data_triase_igd.saturasi_o2,data_triase_igd.nyeri,"+
//                                "data_triase_igd.no_rawat from data_triase_igdsekunder inner join data_triase_igd on data_triase_igd.no_rawat="+
//                                "data_triase_igdsekunder.no_rawat where data_triase_igd.no_rawat=?");
//                        try {
//                            ps.setString(1,tbTriase.getValueAt(tbTriase.getSelectedRow(),0).toString());
//                            rs=ps.executeQuery();
//                            if(rs.next()){
//                                kodepetugas=rs.getString("nik");
//                                TabTriase.setSelectedIndex(1);
//
//                                ps2=koneksi.prepareStatement(
//                                        "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
//                                        "from master_triase_pemeriksaan inner join master_triase_skala3 inner join data_triase_igddetail_skala3 "+
//                                        "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala3.kode_pemeriksaan and "+
//                                        "master_triase_skala3.kode_skala3=data_triase_igddetail_skala3.kode_skala3 where data_triase_igddetail_skala3.no_rawat=? "+
//                                        "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan");
//                                try {
//                                    ps2.setString(1,rs.getString("no_rawat"));
//                                    rs2=ps2.executeQuery();
//                                    if(rs2.next()){
//                                        TabSkala3dan4dan5.setSelectedIndex(0);
//                                        Valid.tabelKosong(tabModePemeriksaan2);
//                                        Valid.tabelKosong(tabModeSkala3);
//                                        rs2.beforeFirst();
//                                        while(rs2.next()){
//                                            tabModePemeriksaan2.addRow(new String[]{rs2.getString("kode_pemeriksaan"),rs2.getString("nama_pemeriksaan")});
//                                            ps3=koneksi.prepareStatement(
//                                                    "select master_triase_skala3.kode_skala3,master_triase_skala3.pengkajian_skala3 from master_triase_skala3 inner join data_triase_igddetail_skala3 "+
//                                                    "on master_triase_skala3.kode_skala3=data_triase_igddetail_skala3.kode_skala3 where "+
//                                                    "master_triase_skala3.kode_pemeriksaan=? and data_triase_igddetail_skala3.no_rawat=? "+
//                                                    "order by data_triase_igddetail_skala3.kode_skala3");
//                                            try {
//                                                ps3.setString(1,rs2.getString("kode_pemeriksaan"));
//                                                ps3.setString(2,rs.getString("no_rawat"));
//                                                rs3=ps3.executeQuery();
//                                                while(rs3.next()){
//                                                    tabModeSkala3.addRow(new Object[]{true,rs3.getString("kode_skala3"),rs3.getString("pengkajian_skala3")});
//                                                }
//                                            } catch (Exception e) {
//                                                System.out.println("Notif : "+e);
//                                            } finally{
//                                                if(rs3!=null){
//                                                    rs3.close();
//                                                }
//                                                if(ps3!=null){
//                                                    ps3.close();
//                                                }
//                                            }
//                                        }
//                                    }
//                                } catch (Exception e) {
//                                    System.out.println("Notif : "+e);
//                                } finally{
//                                    if(rs2!=null){
//                                        rs2.close();
//                                    }
//                                    if(ps2!=null){
//                                        ps2.close();
//                                    }
//                                }
//
//                                ps2=koneksi.prepareStatement(
//                                        "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
//                                        "from master_triase_pemeriksaan inner join master_triase_skala4 inner join data_triase_igddetail_skala4 "+
//                                        "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala4.kode_pemeriksaan and "+
//                                        "master_triase_skala4.kode_skala4=data_triase_igddetail_skala4.kode_skala4 where data_triase_igddetail_skala4.no_rawat=? "+
//                                        "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan");
//                                try {
//                                    ps2.setString(1,rs.getString("no_rawat"));
//                                    rs2=ps2.executeQuery();
//                                    if(rs2.next()){
//                                        TabSkala3dan4dan5.setSelectedIndex(1);
//                                        Valid.tabelKosong(tabModePemeriksaan2);
//                                        Valid.tabelKosong(tabModeSkala4);
//                                        rs2.beforeFirst();
//                                        while(rs2.next()){
//                                            tabModePemeriksaan2.addRow(new String[]{rs2.getString("kode_pemeriksaan"),rs2.getString("nama_pemeriksaan")});
//                                            ps3=koneksi.prepareStatement(
//                                                    "select master_triase_skala4.kode_skala4,master_triase_skala4.pengkajian_skala4 from master_triase_skala4 inner join data_triase_igddetail_skala4 "+
//                                                    "on master_triase_skala4.kode_skala4=data_triase_igddetail_skala4.kode_skala4 where "+
//                                                    "master_triase_skala4.kode_pemeriksaan=? and data_triase_igddetail_skala4.no_rawat=? "+
//                                                    "order by data_triase_igddetail_skala4.kode_skala4");
//                                            try {
//                                                ps3.setString(1,rs2.getString("kode_pemeriksaan"));
//                                                ps3.setString(2,rs.getString("no_rawat"));
//                                                rs3=ps3.executeQuery();
//                                                while(rs3.next()){
//                                                    tabModeSkala4.addRow(new Object[]{true,rs3.getString("kode_skala4"),rs3.getString("pengkajian_skala4")});
//                                                }
//                                            } catch (Exception e) {
//                                                System.out.println("Notif : "+e);
//                                            } finally{
//                                                if(rs3!=null){
//                                                    rs3.close();
//                                                }
//                                                if(ps3!=null){
//                                                    ps3.close();
//                                                }
//                                            }
//                                        }
//                                    }
//                                } catch (Exception e) {
//                                    System.out.println("Notif : "+e);
//                                } finally{
//                                    if(rs2!=null){
//                                        rs2.close();
//                                    }
//                                    if(ps2!=null){
//                                        ps2.close();
//                                    }
//                                }
//
//                                ps2=koneksi.prepareStatement(
//                                        "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
//                                        "from master_triase_pemeriksaan inner join master_triase_skala5 inner join data_triase_igddetail_skala5 "+
//                                        "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala5.kode_pemeriksaan and "+
//                                        "master_triase_skala5.kode_skala5=data_triase_igddetail_skala5.kode_skala5 where data_triase_igddetail_skala5.no_rawat=? "+
//                                        "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan");
//                                try {
//                                    ps2.setString(1,rs.getString("no_rawat"));
//                                    rs2=ps2.executeQuery();
//                                    if(rs2.next()){
//                                        TabSkala3dan4dan5.setSelectedIndex(2);
//                                        Valid.tabelKosong(tabModePemeriksaan2);
//                                        Valid.tabelKosong(tabModeSkala5);
//                                        rs2.beforeFirst();
//                                        while(rs2.next()){
//                                            tabModePemeriksaan2.addRow(new String[]{rs2.getString("kode_pemeriksaan"),rs2.getString("nama_pemeriksaan")});
//                                            ps3=koneksi.prepareStatement(
//                                                    "select master_triase_skala5.kode_skala5,master_triase_skala5.pengkajian_skala5 from master_triase_skala5 inner join data_triase_igddetail_skala5 "+
//                                                    "on master_triase_skala5.kode_skala5=data_triase_igddetail_skala5.kode_skala5 where "+
//                                                    "master_triase_skala5.kode_pemeriksaan=? and data_triase_igddetail_skala5.no_rawat=? "+
//                                                    "order by data_triase_igddetail_skala5.kode_skala5");
//                                            try {
//                                                ps3.setString(1,rs2.getString("kode_pemeriksaan"));
//                                                ps3.setString(2,rs.getString("no_rawat"));
//                                                rs3=ps3.executeQuery();
//                                                while(rs3.next()){
//                                                    tabModeSkala5.addRow(new Object[]{true,rs3.getString("kode_skala5"),rs3.getString("pengkajian_skala5")});
//                                                }
//                                            } catch (Exception e) {
//                                                System.out.println("Notif : "+e);
//                                            } finally{
//                                                if(rs3!=null){
//                                                    rs3.close();
//                                                }
//                                                if(ps3!=null){
//                                                    ps3.close();
//                                                }
//                                            }
//                                        }
//                                    }
//                                } catch (Exception e) {
//                                    System.out.println("Notif : "+e);
//                                } finally{
//                                    if(rs2!=null){
//                                        rs2.close();
//                                    }
//                                    if(ps2!=null){
//                                        ps2.close();
//                                    }
//                                }
//
//                                if(rs.getString("plan").equals("Zona Kuning")){
//                                    SekunderZonaKuning.setSelected(true);
//                                }else if(rs.getString("plan").equals("Zona Hijau")){
//                                    SekunderZonaHijau.setSelected(true);
//                                }
//                                
//                                SekunderTanggalTriase.setDate(rs.getDate("tanggaltriase"));
//                                SekunderCatatan.setText(rs.getString("catatan"));
//                            }
//                        } catch (Exception e) {
//                            System.out.println("Notifikasi : "+e);
//                        } finally{
//                            if(rs!=null){
//                                rs.close();
//                            }
//                            if(ps!=null){
//                                ps.close();
//                            }
//                        }
//                    } catch (Exception e) {
//                        System.out.println("Notif : " + e);
//                    }
//                    TNoRM1.setText("");
//                    TPasien1.setText("");
//                    LoadHTML.setText("");
//                    ChkAccor.setSelected(false);
//                    isMenu();
//                }
//            }
//        }
}//GEN-LAST:event_tbTriaseKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        BtnEdit.hide();
        tampilPemeriksaan();
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void TNoRMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TNoRMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoRMActionPerformed

    private void TabPilihanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabPilihanMouseClicked
        if (TabPilihan.getSelectedIndex() == 1) {
            tampil();
        }
    }//GEN-LAST:event_TabPilihanMouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        if (this.getHeight() < 620) {
            ScrollTriase.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            FormTriase.setPreferredSize(new Dimension(FormTriase.WIDTH, 500));
            if (this.getWidth() < 780) {
                ScrollTriase.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                FormTriase.setPreferredSize(new Dimension(770, 500));
            } else {
                ScrollTriase.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            }
        } else {
            ScrollTriase.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
            if (this.getWidth() < 780) {
                ScrollTriase.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                FormTriase.setPreferredSize(new Dimension(770, FormTriase.HEIGHT));
            } else {
                ScrollTriase.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            }
        }
    }//GEN-LAST:event_formWindowActivated

    private void KdKasusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdKasusKeyPressed
        //Valid.pindah(evt, TCari,kdskala);
    }//GEN-LAST:event_KdKasusKeyPressed

    private void AlatBantuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlatBantuKeyPressed
        //Valid.pindah(evt,kdskala,BtnSimpan);
    }//GEN-LAST:event_AlatBantuKeyPressed

    private void btnKasusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKasusActionPerformed
        kasus.isCek();
        kasus.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        kasus.setLocationRelativeTo(internalFrame1);
        kasus.setVisible(true);
    }//GEN-LAST:event_btnKasusActionPerformed

    private void btnKasusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnKasusKeyPressed
        Valid.pindah(evt, SttsDiantar, Alergi);
    }//GEN-LAST:event_btnKasusKeyPressed

    private void SuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SuhuKeyPressed
        Valid.pindah(evt, KeluhanUtama, LK);
    }//GEN-LAST:event_SuhuKeyPressed

    private void SaturasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SaturasiKeyPressed
        Valid.pindah(evt, Nadi, Respirasi);
    }//GEN-LAST:event_SaturasiKeyPressed

    private void LKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LKKeyPressed
        Valid.pindah(evt, Suhu, Tensi);
    }//GEN-LAST:event_LKKeyPressed

    private void NadiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NadiActionPerformed

    }//GEN-LAST:event_NadiActionPerformed

    private void NadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NadiKeyPressed
        Valid.pindah(evt, Tensi, Saturasi);
    }//GEN-LAST:event_NadiKeyPressed

    private void RespirasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RespirasiKeyPressed
        Valid.pindah(evt, Saturasi, TB);
    }//GEN-LAST:event_RespirasiKeyPressed

    private void TensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TensiKeyPressed
        Valid.pindah(evt, LK, Nadi);
    }//GEN-LAST:event_TensiKeyPressed

    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdDokterKeyPressed

    private void NmDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmDokterKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmDokterKeyPressed

    private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        index = 1;
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokterActionPerformed

    private void btnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDokterKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDokterKeyPressed

    private void TCariPemeriksaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariPemeriksaanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            tampilPemeriksaan();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Diagnosa.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            TB.requestFocus();
        }
    }//GEN-LAST:event_TCariPemeriksaanKeyPressed

    private void BtnCariPemeriksaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariPemeriksaanActionPerformed
        tampilPemeriksaan();
    }//GEN-LAST:event_BtnCariPemeriksaanActionPerformed

    private void BtnCariPemeriksaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariPemeriksaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCariPemeriksaanKeyPressed

    private void BtnTambahPemeriksaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahPemeriksaanActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgMasterTriasePemeriksaan form = new DlgMasterTriasePemeriksaan(null, false);
        form.isCek();
        form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        form.setLocationRelativeTo(internalFrame1);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahPemeriksaanActionPerformed

    private void TCariSkalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariSkalaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariSkalaActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCariSkala.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariSkalaKeyPressed

    private void BtnCariSkalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariSkalaActionPerformed
        if (tbPemeriksaan.getSelectedRow() != -1) {
            tampilskala1();
            tampilskala2();
        } else {
            JOptionPane.showMessageDialog(null, "Silahkan pilih pemeriksaan terlebih dahulu..!!");
        }
    }//GEN-LAST:event_BtnCariSkalaActionPerformed

    private void BtnCariSkalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariSkalaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCariSkalaKeyPressed

    private void BtnTambahSkalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahSkalaActionPerformed
        if (TabLevelTriase.getSelectedIndex() == 0) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgMasterTriaseSkala1 form = new DlgMasterTriaseSkala1(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        } else if (TabLevelTriase.getSelectedIndex() == 1) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgMasterTriaseSkala2 form = new DlgMasterTriaseSkala2(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnTambahSkalaActionPerformed

    private void tbPemeriksaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPemeriksaanKeyPressed
        if (tabModePemeriksaan.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
                TCariPemeriksaan.setText("");
                TCariPemeriksaan.requestFocus();
            }
        }
    }//GEN-LAST:event_tbPemeriksaanKeyPressed

    private void tbPemeriksaanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPemeriksaanKeyReleased
        if (tabModePemeriksaan.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    tampilskala1();
                    tampilskala2();
                    tampilskala3();
                    tampilskala4();
                    tampilskala5();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbPemeriksaanKeyReleased

    private void tbPemeriksaanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPemeriksaanMouseClicked
        if (tabModePemeriksaan.getRowCount() != 0) {
            try {
                tampilskala1();
                tampilskala2();
                tampilskala3();
                tampilskala4();
                tampilskala5();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbPemeriksaanMouseClicked

    private void TabLevelTriaseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabLevelTriaseMouseClicked
        if (TabLevelTriase.getSelectedIndex() == 0) {
            label11.setForeground(new Color(237, 28, 36));
            TCariSkala.setForeground(new Color(237, 28, 36));
//            BtnTambahSkala1.setEnabled(var.getmaster_triase_skala1());
//            PrimerResusitasi.setSelected(true);
        } else if (TabLevelTriase.getSelectedIndex() == 1 || TabLevelTriase.getSelectedIndex() == 2 || TabLevelTriase.getSelectedIndex() == 3) {
            label11.setForeground(new Color(204, 204, 0));
            TCariSkala.setForeground(new Color(204, 204, 0));
//            BtnTambahSkala1.setEnabled(var.getmaster_triase_skala2());
//            PrimerKritis.setSelected(true);
        } else if (TabLevelTriase.getSelectedIndex() == 4) {
            label11.setForeground(new Color(114, 191, 68));
            TCariSkala.setForeground(new Color(114, 191, 68));
//            BtnTambahSkala1.setEnabled(var.getmaster_triase_skala2());
//            PrimerKritis1.setSelected(true);
        }
    }//GEN-LAST:event_TabLevelTriaseMouseClicked

    private void DiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKeyPressed
        Valid.pindah(evt, TCariPemeriksaan, Tanggal);
    }//GEN-LAST:event_DiagnosaKeyPressed

    private void TransportasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TransportasiKeyPressed
        Valid.pindah(evt, TCari, Tanggal);
    }//GEN-LAST:event_TransportasiKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt, Transportasi, Alergi);
    }//GEN-LAST:event_TanggalKeyPressed

    private void SttsDiantarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SttsDiantarKeyPressed
        Valid.pindah(evt, Alergi, btnKasus);
    }//GEN-LAST:event_SttsDiantarKeyPressed

    private void KeluhanUtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeluhanUtamaKeyPressed
//       Valid.pindah2(evt,KetVaksin,PrimerSuhu);
    }//GEN-LAST:event_KeluhanUtamaKeyPressed

    private void btnDokterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDokterKeyReleased
        Valid.pindah(evt, Tanggal, BtnSimpan);
    }//GEN-LAST:event_btnDokterKeyReleased

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

    private void AlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlergiKeyPressed

    private void LILAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LILAKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LILAKeyPressed

    private void TensiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TensiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TensiActionPerformed

    private void TBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TBKeyPressed

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BBKeyPressed

    private void RiwayatPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPenyakitKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RiwayatPenyakitKeyPressed

    private void IMTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IMTKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_IMTKeyPressed

    private void PsikologisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PsikologisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PsikologisKeyPressed

    private void SttsFungsionalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SttsFungsionalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SttsFungsionalKeyPressed

    private void SttsTinggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SttsTinggalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_SttsTinggalKeyPressed

    private void CatatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CatatanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CatatanKeyPressed

    private void IntervensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IntervensiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_IntervensiKeyPressed

    private void NmKasusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmKasusKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmKasusKeyPressed

    private void EdukasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdukasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_EdukasiKeyPressed

    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPetugasKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        index = 1;
        petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void MnCetakTriaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakTriaseActionPerformed
        if (tbTriase.getSelectedRow() != -1) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString().trim().equals("")) {
                Valid.textKosong(TCari, "No.Rawat");
            } else {

                Sequel.queryu("delete from temporary");
                try {
                    ps2 = koneksi.prepareStatement(
                            "select master_triase_pemeriksaan.nama_pemeriksaan, master_triase_skala1.pengkajian_skala1 "
                            + "from data_triase_igd inner join data_triase_igddetail_skala1 inner join master_triase_skala1 "
                            + "inner join master_triase_pemeriksaan on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala1.kode_pemeriksaan and "
                            + "data_triase_igddetail_skala1.kode_skala1=master_triase_skala1.kode_skala1 and data_triase_igd.no_rawat=data_triase_igddetail_skala1.no_rawat "
                            + "where data_triase_igd.no_rawat=?");
                    try {
                        ps2.setString(1, tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString());
                        rs2 = ps2.executeQuery();
                        while (rs2.next()) {
                            Sequel.menyimpan("temporary", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", 38, new String[]{
                                "0", rs2.getString("nama_pemeriksaan"), rs2.getString("pengkajian_skala1"), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
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
                } catch (Exception e) {
                    System.out.println("Notif : " + e);
                }

                Map<String, Object> param = new HashMap<>();
                param.put("noperiksa", tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString());
                norm = Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ", tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString());
                param.put("norm", norm);
                param.put("pekerjaan", Sequel.cariIsi("select pekerjaan from pasien where no_rkm_medis=?", norm));
                param.put("noktp", Sequel.cariIsi("select no_ktp from pasien where no_rkm_medis=?", norm));
                param.put("namapasien", Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ", norm));
                param.put("jkel", Sequel.cariIsi("select if(jk='L','Laki-laki','Perempuan') as jk from pasien where no_rkm_medis=? ", norm));
                param.put("umur", Sequel.cariIsi("select umur from pasien where no_rkm_medis=?", norm));
                param.put("lahir", Sequel.cariIsi("select DATE_FORMAT(tgl_lahir,'%d-%m-%Y') from pasien where no_rkm_medis=? ", norm));
//                param.put("pengirim", Sequel.cariIsi("select dokter.nm_dokter from reg_periksa, dokter where reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rawat=? ", tbTriase.getValueAt(tbTriase.getSelectedRow(), 1).toString()));//tbRadiologiRalan.getValueAt(tbRadiologiRalan.getSelectedRow(),10).toString());
                param.put("tanggal", Valid.SetTgl3(tbTriase.getValueAt(tbTriase.getSelectedRow(), 3).toString().substring(0, 11)));
                param.put("alamat", Sequel.cariIsi("select concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat from pasien inner join kelurahan inner join kecamatan inner join kabupaten on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where no_rkm_medis=? ", norm));
//                param.put("diagnosa1", Sequel.cariIsi("select penyakit.nm_penyakit from diagnosa_pasien, penyakit where diagnosa_pasien.kd_penyakit = penyakit.kd_penyakit and diagnosa_pasien.no_rawat=? ", tbRadiologiRalan.getValueAt(tbRadiologiRalan.getSelectedRow(), 1).toString()));
                param.put("jns_bayar", tbTriase.getValueAt(tbTriase.getSelectedRow(), 4).toString());
//                param.put("jam", tbTriase.getValueAt(tbTriase.getSelectedRow(), 3).toString().substring(11, 18));
                param.put("namars", var.getnamars());
                param.put("alamatrs", var.getalamatrs());
                param.put("kotars", var.getkabupatenrs());
                param.put("propinsirs", var.getpropinsirs());
                param.put("kontakrs", var.getkontakrs());
                param.put("emailrs", var.getemailrs());
                param.put("logo", Sequel.cariGambar("select logo from setting"));

                pilihan = (String) JOptionPane.showInputDialog(null, "Silahkan pilih Lembar Level Triase..!", "Pilihan", JOptionPane.QUESTION_MESSAGE, null, new Object[]{
                    "Lembar Triase Level 1", "Lembar Triase Level 2", "Lembar Triase Level 3", "Lembar Triase Level 4", "Lembar Triase Level 5"}, "Lambar Triase");
                switch (pilihan) {
                    case "Lembar Triase Level 1":
                        Valid.MyReport("rptLembarTriaseSkala1.jrxml", "report", "::[ Triase Level 1 ]::", "select * from temporary order by temporary.no", param);
                        break;
                    case "Lembar Triase Level 2":
                        Valid.MyReport("rptLembarTriaseSkala2.jrxml", "report", "::[ Triase Level 2 ]::", "select * from temporary order by temporary.no", param);
                        break;
                    case "Lembar Triase Level 3":
                        Valid.MyReport("rptLembarTriaseSkala3.jrxml", "report", "::[ Triase Level 3 ]::", "select * from temporary order by temporary.no", param);
                        break;
                    case "Lembar Triase Level 4":
                        Valid.MyReport("rptLembarTriaseSkala4.jrxml", "report", "::[ Triase Level 4 ]::", "select * from temporary order by temporary.no", param);
                        break;
                    case "Lembar Triase Level 5":
                        Valid.MyReport("rptLembarTriaseSkala5.jrxml", "report", "::[ Triase Level 5 ]::", "select * from temporary order by temporary.no", param);
                        break;
                }
            }
            this.setCursor(Cursor.getDefaultCursor());
        } else {
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data permintaan...!!!!");
            TCari.requestFocus();
        }
    }//GEN-LAST:event_MnCetakTriaseActionPerformed

    private void tbTriaseMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTriaseMouseReleased
        if (evt.isPopupTrigger()) {
            jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_tbTriaseMouseReleased

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
    private widget.TextBox AlatBantu;
    private widget.TextBox Alergi;
    private widget.TextBox BB;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCariPemeriksaan;
    private widget.Button BtnCariSkala;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambahPemeriksaan;
    private widget.Button BtnTambahSkala;
    private widget.TextBox Catatan;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox Diagnosa;
    private widget.TextBox Edukasi;
    private widget.PanelBiasa FormInput;
    private widget.InternalFrame FormTriase;
    private widget.TextBox IMT;
    private widget.TextBox Intervensi;
    private widget.TextBox Kd2;
    private widget.TextBox KdDokter;
    private widget.TextBox KdKasus;
    private widget.TextBox KdPetugas;
    private widget.TextArea KeluhanUtama;
    private widget.Label LCount;
    private widget.TextBox LILA;
    private widget.TextBox LK;
    private widget.editorpane LoadHTML2;
    private javax.swing.JMenuItem MnCetakTriase;
    private widget.TextBox Nadi;
    private widget.TextBox NmDokter;
    private widget.TextBox NmKasus;
    private widget.TextBox NmPetugas;
    private widget.ComboBox Psikologis;
    private widget.TextBox Respirasi;
    private widget.TextArea RiwayatPenyakit;
    private widget.TextBox Saturasi;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll10;
    private widget.ScrollPane Scroll11;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.ScrollPane Scroll9;
    private widget.ScrollPane ScrollTriase;
    private widget.ComboBox SttsDiantar;
    private widget.ComboBox SttsFungsional;
    private widget.ComboBox SttsTinggal;
    private widget.TextBox Suhu;
    private widget.TextBox TB;
    private widget.TextBox TCari;
    private widget.TextBox TCariPemeriksaan;
    private widget.TextBox TCariSkala;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabLevelTriase;
    private javax.swing.JTabbedPane TabPilihan;
    private widget.Tanggal Tanggal;
    private widget.TextBox Tensi;
    private widget.ComboBox Transportasi;
    private widget.Button btnDokter;
    private widget.Button btnKasus;
    private widget.Button btnPetugas;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame7;
    private widget.InternalFrame internalFrame8;
    private widget.InternalFrame internalFrame9;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel14;
    private widget.Label jLabel16;
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
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel39;
    private widget.Label jLabel4;
    private widget.Label jLabel40;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator13;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.panelisi panelisi5;
    private widget.panelisi panelisi6;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane4;
    private widget.Table tbPemeriksaan;
    private widget.Table tbSkala1;
    private widget.Table tbSkala2;
    private widget.Table tbSkala3;
    private widget.Table tbSkala4;
    private widget.Table tbSkala5;
    private widget.Table tbTriase;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,data_triase_igd.tanggal,penjab.png_jawab,"
                    + "master_triase_macam_kasus.macam_kasus,data_triase_igd.catatan,dokter.nm_dokter "
                    + "from reg_periksa inner join pasien inner join data_triase_igd inner join master_triase_macam_kasus inner join dokter inner join penjab "
                    + "on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.no_rawat=data_triase_igd.no_rawat "
                    + "and data_triase_igd.kode_kasus=master_triase_macam_kasus.kode_kasus and dokter.kd_dokter=reg_periksa.kd_dokter and penjab.kd_pj=reg_periksa.kd_pj "
                    + "where data_triase_igd.tanggal between ? and ? and (reg_periksa.no_rawat like ? or "
                    + "pasien.no_rkm_medis like ? or "
                    + "pasien.nm_pasien like ? or "
                    + "master_triase_macam_kasus.macam_kasus like ?) order by data_triase_igd.tanggal");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + "") + " 00:00:00");
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + "") + " 23:59:59");
                ps.setString(3, "%" + TCari.getText().trim() + "%");
                ps.setString(4, "%" + TCari.getText().trim() + "%");
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                ps.setString(6, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"), rs.getString("tanggal").substring(0, 11),
                        rs.getString("png_jawab"), rs.getString("macam_kasus"), rs.getString("catatan"), rs.getString("nm_dokter")
                    });
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
        TNoRw.setText("");
        TPasien.setText("");
        TNoRM.setText("");
        Tanggal.setDate(new Date());
        Transportasi.setSelectedIndex(0);
        Psikologis.setSelectedIndex(0);
        SttsDiantar.setSelectedIndex(0);
        SttsFungsional.setSelectedIndex(0);
        SttsTinggal.setSelectedIndex(0);        
        KdKasus.setText("");
        NmKasus.setText("");
        AlatBantu.setText("");
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
        Diagnosa.setText("");
        Intervensi.setText("");
        Catatan.setText("");
        KdPetugas.setText("");
        NmPetugas.setText("");
        TabPilihan.setSelectedIndex(0);
        Transportasi.requestFocus();
        RiwayatPenyakit.setText("");
        Edukasi.setText("");
        jmlskala1 = 0;
        jmlskala2 = 0;
        jmlskala3 = 0;
        jmlskala4 = 0;
        jmlskala5 = 0;
        kodepetugas = "";
    }

    public void setNoRm(String norwt, String norm, String namapasien) {
        emptTeks();
        TNoRw.setText(norwt);
        TNoRM.setText(norm);
        TPasien.setText(namapasien);
        TCari.setText(norwt);
    }

    public void tampilPemeriksaan() {
        Valid.tabelKosong(tabModePemeriksaan);
        try {
            ps = koneksi.prepareStatement("select * from master_triase_pemeriksaan where kode_pemeriksaan like ? or nama_pemeriksaan like ? order by kode_pemeriksaan");
            try {
                ps.setString(1, "%" + TCariPemeriksaan.getText().trim() + "%");
                ps.setString(2, "%" + TCariPemeriksaan.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModePemeriksaan.addRow(new String[]{rs.getString(1), rs.getString(2)});
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
            System.out.println("Notifikasi 4: " + e);
        }
    }

    private void tampilskala1() {
        if (tbPemeriksaan.getSelectedRow() != -1) {
            try {
                jml = 0;
                for (i = 0; i < tbSkala1.getRowCount(); i++) {
                    if (tbSkala1.getValueAt(i, 0).toString().equals("true")) {
                        jml++;
                    }
                }

                pilih = null;
                pilih = new boolean[jml];
                kode = null;
                kode = new String[jml];
                pengkajian = null;
                pengkajian = new String[jml];

                index = 0;
                for (i = 0; i < tbSkala1.getRowCount(); i++) {
                    if (tbSkala1.getValueAt(i, 0).toString().equals("true")) {
                        pilih[index] = true;
                        kode[index] = tbSkala1.getValueAt(i, 1).toString();
                        pengkajian[index] = tbSkala1.getValueAt(i, 2).toString();
                        index++;
                    }
                }

                Valid.tabelKosong(tabModeSkala1);

                for (i = 0; i < jml; i++) {
                    tabModeSkala1.addRow(new Object[]{
                        pilih[i], kode[i], pengkajian[i]
                    });
                }

                ps = koneksi.prepareStatement(
                        "select * from master_triase_skala1 where kode_pemeriksaan=? and pengkajian_skala1 like ? order by master_triase_skala1.kode_skala1");
                try {
                    ps.setString(1, tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 0).toString());
                    ps.setString(2, "%" + TCariSkala.getText().trim() + "%");
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        tabModeSkala1.addRow(new Object[]{false, rs.getString("kode_skala1"), rs.getString("pengkajian_skala1")});
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
    }

    private void tampilskala2() {
        if (tbPemeriksaan.getSelectedRow() != -1) {
            try {
                jml = 0;
                for (i = 0; i < tbSkala2.getRowCount(); i++) {
                    if (tbSkala2.getValueAt(i, 0).toString().equals("true")) {
                        jml++;
                    }
                }

                pilih = null;
                pilih = new boolean[jml];
                kode = null;
                kode = new String[jml];
                pengkajian = null;
                pengkajian = new String[jml];

                index = 0;
                for (i = 0; i < tbSkala2.getRowCount(); i++) {
                    if (tbSkala2.getValueAt(i, 0).toString().equals("true")) {
                        pilih[index] = true;
                        kode[index] = tbSkala2.getValueAt(i, 1).toString();
                        pengkajian[index] = tbSkala2.getValueAt(i, 2).toString();
                        index++;
                    }
                }

                Valid.tabelKosong(tabModeSkala2);

                for (i = 0; i < jml; i++) {
                    tabModeSkala2.addRow(new Object[]{
                        pilih[i], kode[i], pengkajian[i]
                    });
                }

                ps = koneksi.prepareStatement(
                        "select * from master_triase_skala2 where kode_pemeriksaan=? and pengkajian_skala2 like ? order by master_triase_skala2.kode_skala2");
                try {
                    ps.setString(1, tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 0).toString());
                    ps.setString(2, "%" + TCariSkala.getText().trim() + "%");
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        tabModeSkala2.addRow(new Object[]{false, rs.getString("kode_skala2"), rs.getString("pengkajian_skala2")});
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
                System.out.println("Notifikasi 6: " + e);
            }
        }
    }

    private void tampilskala3() {
        if (tbPemeriksaan.getSelectedRow() != -1) {
            try {
                jml = 0;
                for (i = 0; i < tbSkala3.getRowCount(); i++) {
                    if (tbSkala3.getValueAt(i, 0).toString().equals("true")) {
                        jml++;
                    }
                }

                pilih = null;
                pilih = new boolean[jml];
                kode = null;
                kode = new String[jml];
                pengkajian = null;
                pengkajian = new String[jml];

                index = 0;
                for (i = 0; i < tbSkala3.getRowCount(); i++) {
                    if (tbSkala3.getValueAt(i, 0).toString().equals("true")) {
                        pilih[index] = true;
                        kode[index] = tbSkala3.getValueAt(i, 1).toString();
                        pengkajian[index] = tbSkala3.getValueAt(i, 2).toString();
                        index++;
                    }
                }

                Valid.tabelKosong(tabModeSkala3);

                for (i = 0; i < jml; i++) {
                    tabModeSkala3.addRow(new Object[]{
                        pilih[i], kode[i], pengkajian[i]
                    });
                }

                ps = koneksi.prepareStatement(
                        "select * from master_triase_skala3 where kode_pemeriksaan=? and pengkajian_skala3 like ? order by master_triase_skala3.kode_skala3");
                try {
                    ps.setString(1, tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 0).toString());
                    ps.setString(2, "%" + TCariSkala.getText().trim() + "%");
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        tabModeSkala3.addRow(new Object[]{false, rs.getString("kode_skala3"), rs.getString("pengkajian_skala3")});
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
                System.out.println("Notifikasi 7: " + e);
            }
        }
    }

    private void tampilskala4() {
        if (tbPemeriksaan.getSelectedRow() != -1) {
            try {
                jml = 0;
                for (i = 0; i < tbSkala4.getRowCount(); i++) {
                    if (tbSkala4.getValueAt(i, 0).toString().equals("true")) {
                        jml++;
                    }
                }

                pilih = null;
                pilih = new boolean[jml];
                kode = null;
                kode = new String[jml];
                pengkajian = null;
                pengkajian = new String[jml];

                index = 0;
                for (i = 0; i < tbSkala4.getRowCount(); i++) {
                    if (tbSkala4.getValueAt(i, 0).toString().equals("true")) {
                        pilih[index] = true;
                        kode[index] = tbSkala4.getValueAt(i, 1).toString();
                        pengkajian[index] = tbSkala4.getValueAt(i, 2).toString();
                        index++;
                    }
                }

                Valid.tabelKosong(tabModeSkala4);

                for (i = 0; i < jml; i++) {
                    tabModeSkala4.addRow(new Object[]{
                        pilih[i], kode[i], pengkajian[i]
                    });
                }

                ps = koneksi.prepareStatement(
                        "select * from master_triase_skala4 where kode_pemeriksaan=? and pengkajian_skala4 like ? order by master_triase_skala4.kode_skala4");
                try {
                    ps.setString(1, tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 0).toString());
                    ps.setString(2, "%" + TCariSkala.getText().trim() + "%");
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        tabModeSkala4.addRow(new Object[]{false, rs.getString("kode_skala4"), rs.getString("pengkajian_skala4")});
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
                System.out.println("Notifikasi 8: " + e);
            }
        }
    }

    private void tampilskala5() {
        if (tbPemeriksaan.getSelectedRow() != -1) {
            try {
                jml = 0;
                for (i = 0; i < tbSkala5.getRowCount(); i++) {
                    if (tbSkala5.getValueAt(i, 0).toString().equals("true")) {
                        jml++;
                    }
                }

                pilih = null;
                pilih = new boolean[jml];
                kode = null;
                kode = new String[jml];
                pengkajian = null;
                pengkajian = new String[jml];

                index = 0;
                for (i = 0; i < tbSkala5.getRowCount(); i++) {
                    if (tbSkala5.getValueAt(i, 0).toString().equals("true")) {
                        pilih[index] = true;
                        kode[index] = tbSkala5.getValueAt(i, 1).toString();
                        pengkajian[index] = tbSkala5.getValueAt(i, 2).toString();
                        index++;
                    }
                }

                Valid.tabelKosong(tabModeSkala5);

                for (i = 0; i < jml; i++) {
                    tabModeSkala5.addRow(new Object[]{
                        pilih[i], kode[i], pengkajian[i]
                    });
                }

                ps = koneksi.prepareStatement(
                        "select * from master_triase_skala5 where kode_pemeriksaan=? and pengkajian_skala5 like ? order by master_triase_skala5.kode_skala5");
                try {
                    ps.setString(1, tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 0).toString());
                    ps.setString(2, "%" + TCariSkala.getText().trim() + "%");
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        tabModeSkala5.addRow(new Object[]{false, rs.getString("kode_skala5"), rs.getString("pengkajian_skala5")});
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
                System.out.println("Notifikasi 9: " + e);
            }
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
    }

    private void getData() {
        Kd2.setText("");
        if (tbTriase.getSelectedRow() != -1) {
            Kd2.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString());
        }

    }

//    private void isMenu() {
//        if (ChkAccor.isSelected() == true) {
//            ChkAccor.setVisible(false);
//            PanelAccor.setPreferredSize(new Dimension(470, HEIGHT));
//            FormMenu.setVisible(true);
//            ScrollHTML.setVisible(true);
//            ChkAccor.setVisible(true);
//        } else if (ChkAccor.isSelected() == false) {
//            ChkAccor.setVisible(false);
//            PanelAccor.setPreferredSize(new Dimension(15, HEIGHT));
//            FormMenu.setVisible(false);
//            ScrollHTML.setVisible(false);
//            ChkAccor.setVisible(true);
//
//        }
//    }
    private void hapus() {
        Sequel.meghapus("data_triase_igd", "no_rawat", tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString());
        Sequel.meghapus("data_triase_igddetail_skala1", "no_rawat", tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString());
        tampil();
    }

    private void ganti() {
        Sequel.AutoComitFalse();
        sukses = true;
        Sequel.meghapus("data_triase_igdprimer", "no_rawat", tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString());
        Sequel.meghapus("data_triase_igddetail_skala1", "no_rawat", tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString());
//        if(TabTriase.getSelectedIndex()==0){
        jmlskala1 = 0;
        jmlskala2 = 0;
        jmlskala3 = 0;
        jmlskala4 = 0;
        jmlskala5 = 0;
        for (i = 0; i < tbSkala1.getRowCount(); i++) {
            if (tbSkala1.getValueAt(i, 0).toString().equals("true")) {
                jmlskala1++;
            }
        }
//        for (i = 0; i < tbSkala2.getRowCount(); i++) {
//            if (tbSkala2.getValueAt(i, 0).toString().equals("true")) {
//                jmlskala2++;
//            }
//        }
//        for (i = 0; i < tbSkala3.getRowCount(); i++) {
//            if (tbSkala3.getValueAt(i, 0).toString().equals("true")) {
//                jmlskala3++;
//            }
//        }
//        for (i = 0; i < tbSkala4.getRowCount(); i++) {
//            if (tbSkala4.getValueAt(i, 0).toString().equals("true")) {
//                jmlskala4++;
//            }
//        }
//        for (i = 0; i < tbSkala5.getRowCount(); i++) {
//            if (tbSkala5.getValueAt(i, 0).toString().equals("true")) {
//                jmlskala5++;
//            }
//        }
        if (KeluhanUtama.getText().trim().equals("")) {
            Valid.textKosong(KeluhanUtama, "Keluhan Utama");
        } else if (Suhu.getText().trim().equals("")) {
            Valid.textKosong(Suhu, "Suhu");
        } else if (LK.getText().trim().equals("")) {
            Valid.textKosong(LK, "Nyeri");
        } else if (Tensi.getText().trim().equals("")) {
            Valid.textKosong(Tensi, "Tensi");
        } else if (Nadi.getText().trim().equals("")) {
            Valid.textKosong(Nadi, "Nadi");
        } else if (Saturasi.getText().trim().equals("")) {
            Valid.textKosong(Saturasi, "Saturasi O²");
        } else if (Respirasi.getText().trim().equals("")) {
            Valid.textKosong(Respirasi, "Respirasi");
        } else if (Diagnosa.getText().trim().equals("")) {
            Valid.textKosong(Diagnosa, "Catatan");
        } else if (KdDokter.getText().trim().equals("") || NmDokter.getText().trim().equals("")) {
            Valid.textKosong(btnDokter, "Dokter/Petugas Triase");
        } else if ((jmlskala1 == 0) && (jmlskala2 == 0)) {
            Valid.textKosong(TCariPemeriksaan, "Skala 1 / Skala 2");
        } else {
            if (Sequel.mengedittf("data_triase_igd", "no_rawat=?", "no_rawat=?,tgl_kunjungan=?,cara_masuk=?,alat_transportasi=?,alasan_kedatangan=?,keterangan_kedatangan=?,kode_kasus=?,tekanan_darah=?,nadi=?,pernapasan=?,suhu=?,saturasi_o2=?,nyeri=?", 14, new String[]{
                TNoRw.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + "") + " " + Tanggal.getSelectedItem().toString().substring(11, 19),
                Alergi.getText().toString(), Transportasi.getSelectedItem().toString(), SttsDiantar.getSelectedItem().toString(),
                //                        KetVaksin.getText(),
                KdKasus.getText(), Tensi.getText(), Nadi.getText(), Respirasi.getText(), Suhu.getText(),
                Saturasi.getText(), LK.getText(), tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString()
            }) == true) {
                if (TabLevelTriase.getSelectedIndex() == 0) {
                    for (i = 0; i < tbSkala1.getRowCount(); i++) {
                        if (tbSkala1.getValueAt(i, 0).toString().equals("true")) {
                            if (Sequel.menyimpantf2("data_triase_igddetail_skala1", "?,?", "Skala 1", 2, new String[]{
                                TNoRw.getText(), tbSkala1.getValueAt(i, 1).toString()
                            }) == true) {
                                tbSkala1.setValueAt(false, i, 0);
                            } else {
                                sukses = false;
                            }
                        }
                    }
                }
//else if (TabLevelTriase.getSelectedIndex() == 1) {
//                        for (i = 0; i < tbSkala2.getRowCount(); i++) {
//                            if (tbSkala2.getValueAt(i, 0).toString().equals("true")) {
//                                if (Sequel.menyimpantf2("data_triase_igddetail_skala2", "?,?", "Skala 2", 2, new String[]{
//                                    TNoRw.getText(), tbSkala2.getValueAt(i, 1).toString()
//                                }) == true) {
//                                    tbSkala2.setValueAt(false, i, 0);
//                                } else {
//                                    sukses = false;
//                                }
//                            }
//                        }
//                    } else if (TabLevelTriase.getSelectedIndex() == 2) {
//                        for (i = 0; i < tbSkala3.getRowCount(); i++) {
//                            if (tbSkala3.getValueAt(i, 0).toString().equals("true")) {
//                                if (Sequel.menyimpantf2("data_triase_igddetail_skala3", "?,?", "Skala 3", 2, new String[]{
//                                    TNoRw.getText(), tbSkala3.getValueAt(i, 1).toString()
//                                }) == true) {
//                                    tbSkala3.setValueAt(false, i, 0);
//                                } else {
//                                    sukses = false;
//                                }
//                            }
//                        }
//                    } else if (TabLevelTriase.getSelectedIndex() == 3) {
//                        for (i = 0; i < tbSkala4.getRowCount(); i++) {
//                            if (tbSkala4.getValueAt(i, 0).toString().equals("true")) {
//                                if (Sequel.menyimpantf2("data_triase_igddetail_skala4", "?,?", "Skala 4", 2, new String[]{
//                                    TNoRw.getText(), tbSkala4.getValueAt(i, 1).toString()
//                                }) == true) {
//                                    tbSkala4.setValueAt(false, i, 0);
//                                } else {
//                                    sukses = false;
//                                }
//                            }
//                        }
//                    } else if (TabLevelTriase.getSelectedIndex() == 4) {
//                        for (i = 0; i < tbSkala5.getRowCount(); i++) {
//                            if (tbSkala5.getValueAt(i, 0).toString().equals("true")) {
//                                if (Sequel.menyimpantf2("data_triase_igddetail_skala5", "?,?", "Skala 5", 2, new String[]{
//                                    TNoRw.getText(), tbSkala5.getValueAt(i, 1).toString()
//                                }) == true) {
//                                    tbSkala5.setValueAt(false, i, 0);
//                                } else {
//                                    sukses = false;
//                                }
//                            }
//                        }
//                    }
            } else {
                sukses = false;
            }
        }
//        }
        if (sukses == true) {
            Sequel.Commit();
        } else {
            sukses = false;
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat pemrosesan data, input data dibatalkan.\nPeriksa kembali data sebelum melanjutkan mengganti..!!");
            Sequel.RollBack();
        }
        Sequel.AutoComitTrue();
        if (sukses == true) {
            emptTeks();
            tampil();
            TabPilihan.setSelectedIndex(1);
        }
    }

    private void isRawat() {
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ", TNoRM, TNoRw.getText());
//        TCariPasien.setText(TNoRM.getText());
    }

    private void isPsien() {
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ", TPasien, TNoRM.getText());
    }

    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);
        KdDokter.setText(Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?", norwt));
        Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", NmDokter, KdDokter.getText());
        isRawat();
        isPsien();
        isReset();
    }

    public void isReset() {
        jml = tbPemeriksaan.getRowCount();
        for (i = 0; i < jml; i++) {
            tbPemeriksaan.setValueAt(false, i, 0);
        }
        Valid.tabelKosong(tabMode);
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

}
