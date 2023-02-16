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
import java.awt.Component;
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
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableCellRenderer;
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

    private DefaultTableModel tabMode, tabModePemeriksaan, tabModeLevel;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps, ps2, ps3;
    private ResultSet rs, rs2, rs3;
    private int i = 0, jml = 0, index = 0, jmlskala1 = 0;
    private DlgMasterTriaseMacamKasus kasus = new DlgMasterTriaseMacamKasus(null, false);
    private boolean[] pilih;
    private String[] kodelevel, kodetindakan, level, pengkajian;
    private DlgCariDokter dokter = new DlgCariDokter(null, false);
    private DlgCariPetugas petugas = new DlgCariPetugas(null, false);
    private String keputusan = "", pilihan = "", datatriase = "", finger = "", kodepetugas = "", norm = "", key = "", aturan = "", resiko = "",
            kepala = "", mata = "", tht = "", mulut = "", leher = "", dada = "", abdomen = "", punggung = "", gen = "", eks = "", periksafisik = "";
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
            "No.Rawat", "Tanggal", "No.RM", "Nama Pasien", "J.K", "Tgl.Lahir", "Umur", "NIK", "Alamat", "No.Telp",
            "Agama", "Jns.Bayar", "Diantar", "Transportasi", "Fungsional", "Psikologis", "Tinggal Dgn", "Alat Bantu",
            "Ket. Alat Bantu", "Keluhan Utama", "Riwayat Penyakit", "TD", "Nadi", "RR", "Suhu", "Sp.o2", "LK", "LILA",
            "TB", "BB", "IMT", "Edukasi", "Riwayat P.Dahulu", "Riwayat Pengobatan", "Riwayat Masuk RS", "Riwayat P.Keluarga",
            "Riwayat Operasi", "Riwayat Trauma", "Periksa Fisik", "Diagnosis", "Tindakan", "Skala Nyeri", "Resiko Jatuh", 
            "Nilai R.J", "Diagnosa Keperawatan", "Intervensi", "Keterangan",
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

        for (i = 0; i < 74; i++) {
            TableColumn column = tbTriase.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(105);
            } else if (i == 1) {
                column.setPreferredWidth(70);
            } else if (i == 2) {
                column.setPreferredWidth(70);
            } else if (i == 3) {
                column.setPreferredWidth(200);
            } else if (i == 4) {
                column.setPreferredWidth(25);
            } else if (i == 5) {
                column.setPreferredWidth(65);
            } else if (i == 6) {
                column.setPreferredWidth(40);
            } else if (i == 7) {
                column.setPreferredWidth(90);
            } else if (i == 8) {
                column.setPreferredWidth(165);
            } else if (i == 9) {
                column.setPreferredWidth(90);
            } else if (i == 10) {
                column.setPreferredWidth(65);
            } else if (i == 11) {
                column.setPreferredWidth(70);
            } else if (i == 12) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
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
                column.setPreferredWidth(70);
            } else if (i == 20) {
                column.setPreferredWidth(120);
            } else if (i == 21) {
                column.setPreferredWidth(90);
            } else if (i == 22) {
                column.setPreferredWidth(170);
            } else if (i == 23) {
                column.setPreferredWidth(25);
            } else if (i == 24) {
                column.setPreferredWidth(40);
            } else if (i == 25) {
                column.setPreferredWidth(25);
            } else if (i == 26) {
                column.setPreferredWidth(40);
            } else if (i == 27) {
                column.setPreferredWidth(40);
            } else if (i == 28) {
                column.setPreferredWidth(25);
            } else if (i == 29) {
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
                column.setPreferredWidth(150);
            } else if (i == 41) {
                column.setPreferredWidth(150);
            } else if (i == 42) {
                column.setPreferredWidth(50);
            } else if (i == 43) {
                column.setPreferredWidth(65);
            } else if (i == 44) {
                column.setPreferredWidth(50);
            } else if (i == 45) {
                column.setPreferredWidth(65);
            } else if (i == 46) {
                column.setPreferredWidth(50);
            } else if (i == 47) {
                column.setPreferredWidth(65);
            } else if (i == 48) {
                column.setPreferredWidth(50);
            } else if (i == 49) {
                column.setPreferredWidth(65);
            } else if (i == 50) {
                column.setPreferredWidth(50);
            } else if (i == 51) {
                column.setPreferredWidth(65);
            } else if (i == 52) {
                column.setPreferredWidth(50);
            } else if (i == 53) {
                column.setPreferredWidth(65);
            } else if (i == 54) {
                column.setPreferredWidth(50);
            } else if (i == 55) {
                column.setPreferredWidth(65);
            } else if (i == 56) {
                column.setPreferredWidth(50);
            } else if (i == 57) {
                column.setPreferredWidth(65);
            } else if (i == 58) {
                column.setPreferredWidth(50);
            } else if (i == 59) {
                column.setPreferredWidth(65);
            } else if (i == 60) {
                column.setPreferredWidth(50);
            } else if (i == 61) {
                column.setPreferredWidth(65);
            } else if (i == 62) {
                column.setPreferredWidth(150);
            } else if (i == 63) {
                column.setPreferredWidth(150);
            } else if (i == 64) {
                column.setPreferredWidth(70);
            } else if (i == 65) {
                column.setPreferredWidth(70);
            } else if (i == 66) {
                column.setPreferredWidth(90);
            } else if (i == 67) {
                column.setPreferredWidth(150);
            } else if (i == 68) {
                column.setPreferredWidth(150);
            } else if (i == 69) {
                column.setPreferredWidth(150);
            } else if (i == 70) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 71) {
                column.setPreferredWidth(150);
            } else if (i == 72) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 73) {
                column.setPreferredWidth(140);
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
//        tbSkala1.getTableHeader().setForeground(new Color(170,00,0));
//        tbLevel.getTableHeader().setForeground(new Color(237, 28, 36));
//        tbSkala1.setDefaultRenderer(Object.class, new WarnaTable());
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

        TCari.setDocument(new batasInput((int) 100).getKata(TCari));
        TCariSkala.setDocument(new batasInput((int) 100).getKata(TCariSkala));
        Suhu.setDocument(new batasInput((byte) 5).getKata(Suhu));
        LK.setDocument(new batasInput((byte) 5).getKata(LK));
        Tensi.setDocument(new batasInput((byte) 8).getKata(Tensi));
        Nadi.setDocument(new batasInput((byte) 3).getKata(Nadi));
        Saturasi.setDocument(new batasInput((byte) 3).getKata(Saturasi));
        Respirasi.setDocument(new batasInput((byte) 3).getKata(Respirasi));
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
        chkMasukRS.setSelected(false);
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
        BtnTambahSkala = new widget.Button();
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
        NmKasus = new widget.TextBox();
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
        jLabel45 = new widget.Label();
        AlatBantu = new widget.TextBox();
        jLabel46 = new widget.Label();
        Edukasi = new widget.TextBox();
        cmbAlatBantu = new widget.ComboBox();
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
        Edukasi1 = new widget.TextBox();
        ChkRspPlg = new widget.CekBox();
        ChkRspPlg1 = new widget.CekBox();
        ChkRspPlg2 = new widget.CekBox();
        ChkRspPlg3 = new widget.CekBox();
        ChkRspPlg4 = new widget.CekBox();
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
        jLabel54 = new widget.Label();
        jLabel61 = new widget.Label();
        Leher = new widget.TextBox();
        jLabel66 = new widget.Label();
        Punggung = new widget.TextBox();
        Mata = new widget.TextBox();
        jLabel60 = new widget.Label();
        jLabel67 = new widget.Label();
        Dada = new widget.TextBox();
        jLabel68 = new widget.Label();
        Genetalia = new widget.TextBox();
        THT = new widget.TextBox();
        jLabel69 = new widget.Label();
        jLabel70 = new widget.Label();
        Abdomen = new widget.TextBox();
        jLabel71 = new widget.Label();
        Ekstrimitas = new widget.TextBox();
        Mulut = new widget.TextBox();
        jLabel72 = new widget.Label();
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
        jLabel74 = new widget.Label();
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
        FormTriase.setPreferredSize(new java.awt.Dimension(900, 1659));
        FormTriase.setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame5.setBorder(null);
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setPreferredSize(new java.awt.Dimension(700, 74));
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

        TNoRw.setBackground(new java.awt.Color(245, 250, 240));
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        FormInput.add(TNoRw);
        TNoRw.setBounds(105, 10, 152, 23);

        TPasien.setBackground(new java.awt.Color(245, 250, 240));
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(371, 10, 392, 23);

        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TNoRMActionPerformed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(259, 10, 110, 23);

        jLabel18.setText("Tgl :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(584, 70, 50, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-02-2023 23:55:32" }));
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

        cmbDiantar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Keluarga", "Polisi", "Petugas Kesehatan", "Datang Sendiri", "Rescue" }));
        cmbDiantar.setName("cmbDiantar"); // NOI18N
        cmbDiantar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbDiantarItemStateChanged(evt);
            }
        });
        FormInput.add(cmbDiantar);
        cmbDiantar.setBounds(100, 260, 280, 23);

        jLabel14.setText("Petugas :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(0, 130, 100, 23);

        KdPetugas.setHighlighter(null);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
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
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(105, 100, 75, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmDokterKeyPressed(evt);
            }
        });
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
        btnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDokterKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btnDokterKeyReleased(evt);
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

        Transportasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Ambulance", "Mobil Polisi", "Jalan Kaki", "Kendaraan umum", "Kendaraan pribadi" }));
        Transportasi.setName("Transportasi"); // NOI18N
        Transportasi.setPreferredSize(new java.awt.Dimension(55, 28));
        Transportasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TransportasiKeyPressed(evt);
            }
        });
        FormInput.add(Transportasi);
        Transportasi.setBounds(100, 290, 280, 23);

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

        NmKasus.setEditable(false);
        NmKasus.setName("NmKasus"); // NOI18N
        NmKasus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmKasusKeyPressed(evt);
            }
        });
        FormInput.add(NmKasus);
        NmKasus.setBounds(180, 220, 200, 23);

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
        KeluhanUtama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeluhanUtamaKeyPressed(evt);
            }
        });
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
        RiwayatPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPenyakitKeyPressed(evt);
            }
        });
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
        SttsFungsional.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SttsFungsionalKeyPressed(evt);
            }
        });
        FormInput.add(SttsFungsional);
        SttsFungsional.setBounds(100, 320, 280, 23);

        jLabel42.setText("Psikologis :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(0, 350, 100, 23);

        Psikologis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Stabil ", "Marah", "Cemas", "Takut", "Gelisah", "Percobaan bunuh diri", "Halusinasi", "Lainnya", " " }));
        Psikologis.setName("Psikologis"); // NOI18N
        Psikologis.setPreferredSize(new java.awt.Dimension(55, 28));
        Psikologis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PsikologisKeyPressed(evt);
            }
        });
        FormInput.add(Psikologis);
        Psikologis.setBounds(100, 350, 280, 23);

        jLabel44.setText("Tinggal dengan :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(0, 380, 100, 23);

        SttsTinggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Sendiri", "Keluarga", "Teman", "Orang Tua", "Suami / Istri", "Teman", "Tunawisma", "Rumah perawatan / jompo" }));
        SttsTinggal.setName("SttsTinggal"); // NOI18N
        SttsTinggal.setPreferredSize(new java.awt.Dimension(55, 28));
        SttsTinggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SttsTinggalKeyPressed(evt);
            }
        });
        FormInput.add(SttsTinggal);
        SttsTinggal.setBounds(100, 380, 280, 23);

        jLabel45.setText("Alat Bantu :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(390, 370, 100, 23);

        AlatBantu.setName("AlatBantu"); // NOI18N
        AlatBantu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlatBantuKeyPressed(evt);
            }
        });
        FormInput.add(AlatBantu);
        AlatBantu.setBounds(610, 370, 165, 23);

        jLabel46.setText("Edukasi :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(400, 340, 90, 23);

        Edukasi.setName("Edukasi"); // NOI18N
        Edukasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EdukasiKeyPressed(evt);
            }
        });
        FormInput.add(Edukasi);
        Edukasi.setBounds(493, 340, 273, 23);

        cmbAlatBantu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        cmbAlatBantu.setName("cmbAlatBantu"); // NOI18N
        cmbAlatBantu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbAlatBantuItemStateChanged(evt);
            }
        });
        FormInput.add(cmbAlatBantu);
        cmbAlatBantu.setBounds(490, 370, 110, 23);

        jLabel11.setText("TD (mmHg) :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(400, 220, 90, 23);

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
        FormInput.add(Tensi);
        Tensi.setBounds(493, 220, 55, 23);

        jLabel26.setText("N (x/mnt) :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(533, 220, 79, 23);

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
        FormInput.add(Nadi);
        Nadi.setBounds(614, 220, 50, 23);

        jLabel10.setText("T(°C) :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(635, 220, 79, 23);

        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        Suhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SuhuKeyPressed(evt);
            }
        });
        FormInput.add(Suhu);
        Suhu.setBounds(715, 220, 50, 23);

        jLabel27.setText("RR(x/mnt) :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(400, 250, 90, 23);

        Respirasi.setHighlighter(null);
        Respirasi.setName("Respirasi"); // NOI18N
        Respirasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RespirasiKeyPressed(evt);
            }
        });
        FormInput.add(Respirasi);
        Respirasi.setBounds(493, 250, 55, 23);

        jLabel16.setText("Sp.O²(%) :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(533, 250, 79, 23);

        Saturasi.setFocusTraversalPolicyProvider(true);
        Saturasi.setName("Saturasi"); // NOI18N
        Saturasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SaturasiKeyPressed(evt);
            }
        });
        FormInput.add(Saturasi);
        Saturasi.setBounds(614, 250, 50, 23);

        jLabel25.setText("LK (cm) :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(635, 250, 79, 23);

        LK.setHighlighter(null);
        LK.setName("LK"); // NOI18N
        LK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LKKeyPressed(evt);
            }
        });
        FormInput.add(LK);
        LK.setBounds(715, 250, 50, 23);

        jLabel24.setText("TB (cm) :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(400, 280, 90, 23);

        TB.setName("TB"); // NOI18N
        TB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBKeyPressed(evt);
            }
        });
        FormInput.add(TB);
        TB.setBounds(493, 280, 55, 23);

        jLabel39.setText("BB (kg) :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(533, 280, 79, 23);

        BB.setHighlighter(null);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(614, 280, 50, 23);

        jLabel40.setText("IMT :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(635, 280, 79, 23);

        IMT.setEditable(false);
        IMT.setHighlighter(null);
        IMT.setName("IMT"); // NOI18N
        IMT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IMTKeyPressed(evt);
            }
        });
        FormInput.add(IMT);
        IMT.setBounds(715, 280, 50, 23);

        jLabel35.setText("LILA (cm) :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(400, 310, 90, 23);

        LILA.setHighlighter(null);
        LILA.setName("LILA"); // NOI18N
        LILA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LILAKeyPressed(evt);
            }
        });
        FormInput.add(LILA);
        LILA.setBounds(493, 310, 55, 23);

        jLabel20.setText("Alergi :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(533, 310, 79, 23);

        Alergi.setName("Alergi"); // NOI18N
        Alergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiKeyPressed(evt);
            }
        });
        FormInput.add(Alergi);
        Alergi.setBounds(614, 310, 152, 23);

        cmbTriase.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "AIRWAY", "BREATHING", "CIRCULATION", "DISABILITY", "Prediksi pemeriksaan penunjang", "Prediksi SDM yang akan terlibat", "Do'a" }));
        cmbTriase.setName("cmbTriase"); // NOI18N
        cmbTriase.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbTriaseItemStateChanged(evt);
            }
        });
        FormInput.add(cmbTriase);
        cmbTriase.setBounds(100, 420, 280, 23);

        jLabel48.setText("Pemeriksaan :");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(0, 420, 100, 23);

        Edukasi1.setName("Edukasi1"); // NOI18N
        Edukasi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi1KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi1);
        Edukasi1.setBounds(493, 100, 273, 23);

        ChkRspPlg.setBorder(null);
        ChkRspPlg.setText("Non Trauma");
        ChkRspPlg.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkRspPlg.setName("ChkRspPlg"); // NOI18N
        ChkRspPlg.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkRspPlgItemStateChanged(evt);
            }
        });
        ChkRspPlg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkRspPlgActionPerformed(evt);
            }
        });
        FormInput.add(ChkRspPlg);
        ChkRspPlg.setBounds(105, 160, 80, 23);

        ChkRspPlg1.setBorder(null);
        ChkRspPlg1.setText("Trauma");
        ChkRspPlg1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkRspPlg1.setName("ChkRspPlg1"); // NOI18N
        ChkRspPlg1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkRspPlg1ItemStateChanged(evt);
            }
        });
        ChkRspPlg1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkRspPlg1ActionPerformed(evt);
            }
        });
        FormInput.add(ChkRspPlg1);
        ChkRspPlg1.setBounds(105, 190, 80, 23);

        ChkRspPlg2.setBorder(null);
        ChkRspPlg2.setText("Lainnya");
        ChkRspPlg2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkRspPlg2.setName("ChkRspPlg2"); // NOI18N
        ChkRspPlg2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkRspPlg2ItemStateChanged(evt);
            }
        });
        ChkRspPlg2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkRspPlg2ActionPerformed(evt);
            }
        });
        FormInput.add(ChkRspPlg2);
        ChkRspPlg2.setBounds(105, 220, 90, 23);

        ChkRspPlg3.setBorder(null);
        ChkRspPlg3.setText("Laka Tunggu");
        ChkRspPlg3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkRspPlg3.setName("ChkRspPlg3"); // NOI18N
        ChkRspPlg3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkRspPlg3ItemStateChanged(evt);
            }
        });
        ChkRspPlg3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkRspPlg3ActionPerformed(evt);
            }
        });
        FormInput.add(ChkRspPlg3);
        ChkRspPlg3.setBounds(190, 160, 110, 20);

        ChkRspPlg4.setBorder(null);
        ChkRspPlg4.setText("Laka Ganda");
        ChkRspPlg4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkRspPlg4.setName("ChkRspPlg4"); // NOI18N
        ChkRspPlg4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkRspPlg4ItemStateChanged(evt);
            }
        });
        ChkRspPlg4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkRspPlg4ActionPerformed(evt);
            }
        });
        FormInput.add(ChkRspPlg4);
        ChkRspPlg4.setBounds(190, 190, 90, 20);

        FormTriase.add(FormInput, java.awt.BorderLayout.PAGE_START);

        FormInput1.setName("FormInput1"); // NOI18N
        FormInput1.setPreferredSize(new java.awt.Dimension(540, 1107));
        FormInput1.setLayout(null);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("ASSESMENT IGD");
        jLabel94.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput1.add(jLabel94);
        jLabel94.setBounds(20, 160, 180, 23);

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
        rpDahulu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rpDahuluKeyPressed(evt);
            }
        });
        FormInput1.add(rpDahulu);
        rpDahulu.setBounds(135, 220, 145, 23);

        rPengobatan.setName("rPengobatan"); // NOI18N
        rPengobatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rPengobatanKeyPressed(evt);
            }
        });
        FormInput1.add(rPengobatan);
        rPengobatan.setBounds(407, 220, 145, 23);

        rMasukRS.setName("rMasukRS"); // NOI18N
        rMasukRS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rMasukRSKeyPressed(evt);
            }
        });
        FormInput1.add(rMasukRS);
        rMasukRS.setBounds(670, 220, 145, 23);

        rTrauma.setName("rTrauma"); // NOI18N
        rTrauma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rTraumaKeyPressed(evt);
            }
        });
        FormInput1.add(rTrauma);
        rTrauma.setBounds(670, 250, 145, 23);

        rOperasi.setName("rOperasi"); // NOI18N
        rOperasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rOperasiKeyPressed(evt);
            }
        });
        FormInput1.add(rOperasi);
        rOperasi.setBounds(407, 250, 145, 23);

        rpKeluarga.setName("rpKeluarga"); // NOI18N
        rpKeluarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rpKeluargaKeyPressed(evt);
            }
        });
        FormInput1.add(rpKeluarga);
        rpKeluarga.setBounds(135, 250, 145, 23);

        Kepala.setName("Kepala"); // NOI18N
        Kepala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KepalaKeyPressed(evt);
            }
        });
        FormInput1.add(Kepala);
        Kepala.setBounds(155, 310, 125, 23);

        jLabel54.setText("Kepala :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput1.add(jLabel54);
        jLabel54.setBounds(0, 310, 130, 23);

        jLabel61.setText("Leher :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput1.add(jLabel61);
        jLabel61.setBounds(264, 310, 140, 23);

        Leher.setName("Leher"); // NOI18N
        Leher.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LeherKeyPressed(evt);
            }
        });
        FormInput1.add(Leher);
        Leher.setBounds(427, 310, 125, 23);

        jLabel66.setText("Punggung :");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput1.add(jLabel66);
        jLabel66.setBounds(547, 310, 120, 23);

        Punggung.setName("Punggung"); // NOI18N
        Punggung.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PunggungKeyPressed(evt);
            }
        });
        FormInput1.add(Punggung);
        Punggung.setBounds(690, 310, 125, 23);

        Mata.setName("Mata"); // NOI18N
        Mata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MataKeyPressed(evt);
            }
        });
        FormInput1.add(Mata);
        Mata.setBounds(155, 340, 125, 23);

        jLabel60.setText("Mata :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput1.add(jLabel60);
        jLabel60.setBounds(0, 340, 130, 23);

        jLabel67.setText("Dada :");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput1.add(jLabel67);
        jLabel67.setBounds(264, 340, 140, 23);

        Dada.setName("Dada"); // NOI18N
        Dada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DadaKeyPressed(evt);
            }
        });
        FormInput1.add(Dada);
        Dada.setBounds(427, 340, 125, 23);

        jLabel68.setText("Genetalia :");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput1.add(jLabel68);
        jLabel68.setBounds(547, 340, 120, 23);

        Genetalia.setName("Genetalia"); // NOI18N
        Genetalia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GenetaliaKeyPressed(evt);
            }
        });
        FormInput1.add(Genetalia);
        Genetalia.setBounds(690, 340, 125, 23);

        THT.setName("THT"); // NOI18N
        THT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                THTKeyPressed(evt);
            }
        });
        FormInput1.add(THT);
        THT.setBounds(155, 370, 125, 23);

        jLabel69.setText("THT :");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput1.add(jLabel69);
        jLabel69.setBounds(0, 370, 130, 23);

        jLabel70.setText("Abdomen :");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput1.add(jLabel70);
        jLabel70.setBounds(264, 370, 140, 23);

        Abdomen.setName("Abdomen"); // NOI18N
        Abdomen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AbdomenKeyPressed(evt);
            }
        });
        FormInput1.add(Abdomen);
        Abdomen.setBounds(427, 370, 125, 23);

        jLabel71.setText("Ekstrimitas :");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput1.add(jLabel71);
        jLabel71.setBounds(547, 370, 120, 23);

        Ekstrimitas.setName("Ekstrimitas"); // NOI18N
        Ekstrimitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EkstrimitasKeyPressed(evt);
            }
        });
        FormInput1.add(Ekstrimitas);
        Ekstrimitas.setBounds(690, 370, 125, 23);

        Mulut.setName("Mulut"); // NOI18N
        Mulut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MulutKeyPressed(evt);
            }
        });
        FormInput1.add(Mulut);
        Mulut.setBounds(155, 400, 125, 23);

        jLabel72.setText("Mulut :");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput1.add(jLabel72);
        jLabel72.setBounds(0, 400, 130, 23);

        Diagnosa.setName("Diagnosa"); // NOI18N
        Diagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaKeyPressed(evt);
            }
        });
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
        Terapi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TerapiKeyPressed(evt);
            }
        });
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
        Tindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakanKeyPressed(evt);
            }
        });
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
        cmbSkalaNyeri.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbSkalaNyeriItemStateChanged(evt);
            }
        });
        FormInput1.add(cmbSkalaNyeri);
        cmbSkalaNyeri.setBounds(493, 10, 200, 23);

        jLabel59.setText("Resiko Jatuh :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput1.add(jLabel59);
        jLabel59.setBounds(400, 40, 90, 23);

        cmbResikoJatuh.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Pediatri (humpty dumpty)", "Dewasa (skala morse)", "Lansia (skala ontario)", " " }));
        cmbResikoJatuh.setName("cmbResikoJatuh"); // NOI18N
        cmbResikoJatuh.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbResikoJatuhItemStateChanged(evt);
            }
        });
        FormInput1.add(cmbResikoJatuh);
        cmbResikoJatuh.setBounds(493, 40, 200, 23);

        buttonGroup1.add(R1);
        R1.setText("Rendah (0-24)");
        R1.setName("R1"); // NOI18N
        R1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                R1ActionPerformed(evt);
            }
        });
        FormInput1.add(R1);
        R1.setBounds(493, 70, 150, 16);

        buttonGroup1.add(R2);
        R2.setText("Sedang (25-44)");
        R2.setName("R2"); // NOI18N
        R2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                R2ActionPerformed(evt);
            }
        });
        FormInput1.add(R2);
        R2.setBounds(493, 90, 150, 16);

        buttonGroup1.add(R3);
        R3.setText("Tinggi (>= 45)");
        R3.setName("R3"); // NOI18N
        R3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                R3ActionPerformed(evt);
            }
        });
        FormInput1.add(R3);
        R3.setBounds(493, 110, 150, 16);

        buttonGroup2.add(R4);
        R4.setText("Tinggi (>=12)");
        R4.setName("R4"); // NOI18N
        R4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                R4ActionPerformed(evt);
            }
        });
        FormInput1.add(R4);
        R4.setBounds(493, 90, 150, 16);

        buttonGroup2.add(R5);
        R5.setText("Rendah (7-11)");
        R5.setName("R5"); // NOI18N
        R5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                R5ActionPerformed(evt);
            }
        });
        FormInput1.add(R5);
        R5.setBounds(493, 70, 150, 16);

        buttonGroup3.add(R6);
        R6.setText("Rendah (0-5)");
        R6.setName("R6"); // NOI18N
        R6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                R6ActionPerformed(evt);
            }
        });
        FormInput1.add(R6);
        R6.setBounds(493, 70, 150, 16);

        buttonGroup3.add(R7);
        R7.setText("Sedang (6-16)");
        R7.setName("R7"); // NOI18N
        R7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                R7ActionPerformed(evt);
            }
        });
        FormInput1.add(R7);
        R7.setBounds(493, 90, 150, 16);

        buttonGroup3.add(R8);
        R8.setText("Tinggi (17-30)");
        R8.setName("R8"); // NOI18N
        R8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                R8ActionPerformed(evt);
            }
        });
        FormInput1.add(R8);
        R8.setBounds(493, 110, 150, 16);

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
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
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

        jLabel74.setText("Abdomen :");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput1.add(jLabel74);
        jLabel74.setBounds(255, 550, 90, 23);

        Intervensi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "Bersihan jalan nafas", "O2 ..LPM VIA ...", "Monitoring vital sign", "Monitoring cairan", "Manajemen nyeri", "Pasang kateter urine", "Hecting", "Dressing luka", "Cross incision", "Pemasangan bidai", "Pemasangan ngt", " " }));
        Intervensi.setName("Intervensi"); // NOI18N
        FormInput1.add(Intervensi);
        Intervensi.setBounds(555, 550, 260, 23);

        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel96.setText("DIAGNOSIS KEPERAWATAN DAN INTERVENSI");
        jLabel96.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel96.setName("jLabel96"); // NOI18N
        FormInput1.add(jLabel96);
        jLabel96.setBounds(20, 520, 250, 23);

        jLabel75.setText("Intervensi :");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput1.add(jLabel75);
        jLabel75.setBounds(410, 550, 140, 20);

        chkMulut.setBorder(null);
        chkMulut.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkMulut.setName("chkMulut"); // NOI18N
        chkMulut.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkMulutItemStateChanged(evt);
            }
        });
        chkMulut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMulutActionPerformed(evt);
            }
        });
        FormInput1.add(chkMulut);
        chkMulut.setBounds(135, 400, 20, 20);

        chkMasukRS.setBorder(null);
        chkMasukRS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkMasukRS.setName("chkMasukRS"); // NOI18N
        chkMasukRS.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkMasukRSItemStateChanged(evt);
            }
        });
        chkMasukRS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMasukRSActionPerformed(evt);
            }
        });
        FormInput1.add(chkMasukRS);
        chkMasukRS.setBounds(135, 190, 20, 20);

        chkKepala.setBorder(null);
        chkKepala.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkKepala.setName("chkKepala"); // NOI18N
        chkKepala.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkKepalaItemStateChanged(evt);
            }
        });
        chkKepala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkKepalaActionPerformed(evt);
            }
        });
        FormInput1.add(chkKepala);
        chkKepala.setBounds(135, 310, 20, 20);

        chkMata.setBorder(null);
        chkMata.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkMata.setName("chkMata"); // NOI18N
        chkMata.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkMataItemStateChanged(evt);
            }
        });
        chkMata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMataActionPerformed(evt);
            }
        });
        FormInput1.add(chkMata);
        chkMata.setBounds(135, 340, 20, 20);

        chkTHT.setBorder(null);
        chkTHT.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkTHT.setName("chkTHT"); // NOI18N
        chkTHT.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkTHTItemStateChanged(evt);
            }
        });
        chkTHT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkTHTActionPerformed(evt);
            }
        });
        FormInput1.add(chkTHT);
        chkTHT.setBounds(135, 370, 20, 20);

        chkLeher.setBorder(null);
        chkLeher.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkLeher.setName("chkLeher"); // NOI18N
        chkLeher.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkLeherItemStateChanged(evt);
            }
        });
        chkLeher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkLeherActionPerformed(evt);
            }
        });
        FormInput1.add(chkLeher);
        chkLeher.setBounds(407, 310, 20, 20);

        chkDada.setBorder(null);
        chkDada.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkDada.setName("chkDada"); // NOI18N
        chkDada.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkDadaItemStateChanged(evt);
            }
        });
        chkDada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkDadaActionPerformed(evt);
            }
        });
        FormInput1.add(chkDada);
        chkDada.setBounds(407, 340, 20, 20);

        chkAbdomen.setBorder(null);
        chkAbdomen.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkAbdomen.setName("chkAbdomen"); // NOI18N
        chkAbdomen.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkAbdomenItemStateChanged(evt);
            }
        });
        chkAbdomen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAbdomenActionPerformed(evt);
            }
        });
        FormInput1.add(chkAbdomen);
        chkAbdomen.setBounds(407, 370, 20, 20);

        chkPunggung.setBorder(null);
        chkPunggung.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkPunggung.setName("chkPunggung"); // NOI18N
        chkPunggung.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkPunggungItemStateChanged(evt);
            }
        });
        chkPunggung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPunggungActionPerformed(evt);
            }
        });
        FormInput1.add(chkPunggung);
        chkPunggung.setBounds(670, 310, 20, 20);

        chkGenetalia.setBorder(null);
        chkGenetalia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkGenetalia.setName("chkGenetalia"); // NOI18N
        chkGenetalia.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkGenetaliaItemStateChanged(evt);
            }
        });
        chkGenetalia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkGenetaliaActionPerformed(evt);
            }
        });
        FormInput1.add(chkGenetalia);
        chkGenetalia.setBounds(670, 340, 20, 20);

        chkEkstrimitas.setBorder(null);
        chkEkstrimitas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkEkstrimitas.setName("chkEkstrimitas"); // NOI18N
        chkEkstrimitas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkEkstrimitasItemStateChanged(evt);
            }
        });
        chkEkstrimitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkEkstrimitasActionPerformed(evt);
            }
        });
        FormInput1.add(chkEkstrimitas);
        chkEkstrimitas.setBounds(670, 370, 20, 20);

        FormTriase.add(FormInput1, java.awt.BorderLayout.PAGE_END);

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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-02-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-02-2023" }));
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
//        } else if (NmKasus.getText().trim().equals("")) {
//            Valid.textKosong(NmKasus, "Macam Kasus");
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
            } else if (Suhu.getText().trim().equals("")) {
                Valid.textKosong(Suhu, "Suhu");
            } else if (Tensi.getText().trim().equals("")) {
                Valid.textKosong(Tensi, "Tensi");
            } else if (Nadi.getText().trim().equals("")) {
                Valid.textKosong(Nadi, "Nadi");
            } else if (Saturasi.getText().trim().equals("")) {
                Valid.textKosong(Saturasi, "Saturasi O²");
            } else if (Respirasi.getText().trim().equals("")) {
                Valid.textKosong(Respirasi, "Respirasi");
            } else if (TB.getText().trim().equals("")) {
                Valid.textKosong(TB, "TB");
            } else if (BB.getText().trim().equals("")) {
                Valid.textKosong(BB, "BB");
            } else if (KdDokter.getText().trim().equals("") || NmDokter.getText().trim().equals("")) {
                Valid.textKosong(btnDokter, "Dokter/Petugas Triase");
            } else {
                insertPasien();
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
        } else if (NmKasus.getText().trim().equals("")) {
            Valid.textKosong(NmKasus, "Macam Kasus");
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
//        BtnEdit.hide();
        setVisible();
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
//        if (this.getHeight() < 620) {
//            ScrollTriase.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//            FormTriase.setPreferredSize(new Dimension(FormTriase.WIDTH, 500));
//            if (this.getWidth() < 780) {
//                ScrollTriase.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
//                FormTriase.setPreferredSize(new Dimension(770, 500));
//            } else {
//                ScrollTriase.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//            }
//        } else {
//            ScrollTriase.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
//            if (this.getWidth() < 780) {
//                ScrollTriase.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
//                FormTriase.setPreferredSize(new Dimension(770, FormTriase.HEIGHT));
//            } else {
//                ScrollTriase.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//            }
//        }
    }//GEN-LAST:event_formWindowActivated

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

    private void TransportasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TransportasiKeyPressed
        Valid.pindah(evt, TCari, Tanggal);
    }//GEN-LAST:event_TransportasiKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt, Transportasi, Alergi);
    }//GEN-LAST:event_TanggalKeyPressed

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

    private void RiwayatPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPenyakitKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_RiwayatPenyakitKeyPressed

    private void NoTelpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoTelpKeyPressed
        Valid.pindah(evt, JK, NIK);
    }//GEN-LAST:event_NoTelpKeyPressed

    private void NmKasusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmKasusKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmKasusKeyPressed

    private void AlatBantuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlatBantuKeyPressed
        //Valid.pindah(evt,kdskala,BtnSimpan);
    }//GEN-LAST:event_AlatBantuKeyPressed

    private void cmbTriaseItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbTriaseItemStateChanged
        if (cmbTriase.getSelectedIndex() != 7) {
            tampillevel();
        }
    }//GEN-LAST:event_cmbTriaseItemStateChanged

    private void rpDahuluKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rpDahuluKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_rpDahuluKeyPressed

    private void rPengobatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rPengobatanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_rPengobatanKeyPressed

    private void rMasukRSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rMasukRSKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_rMasukRSKeyPressed

    private void rTraumaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rTraumaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_rTraumaKeyPressed

    private void rOperasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rOperasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_rOperasiKeyPressed

    private void rpKeluargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rpKeluargaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_rpKeluargaKeyPressed

    private void KepalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KepalaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KepalaKeyPressed

    private void LeherKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LeherKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_LeherKeyPressed

    private void PunggungKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PunggungKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PunggungKeyPressed

    private void MataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MataKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MataKeyPressed

    private void DadaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DadaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DadaKeyPressed

    private void GenetaliaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GenetaliaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GenetaliaKeyPressed

    private void THTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_THTKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_THTKeyPressed

    private void AbdomenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AbdomenKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AbdomenKeyPressed

    private void EkstrimitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EkstrimitasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_EkstrimitasKeyPressed

    private void MulutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MulutKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MulutKeyPressed

    private void DiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosaKeyPressed

    private void TerapiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TerapiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TerapiKeyPressed

    private void TindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TindakanKeyPressed

    private void BtnTambahSkalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahSkalaActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgMasterTriaseSkala1 form = new DlgMasterTriaseSkala1(null, false);
        form.isCek();
        form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        form.setLocationRelativeTo(internalFrame1);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTambahSkalaActionPerformed

    private void BtnCariSkalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariSkalaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCariSkalaKeyPressed

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

    private void cmbSkalaNyeriItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbSkalaNyeriItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSkalaNyeriItemStateChanged

    private void cmbResikoJatuhItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbResikoJatuhItemStateChanged
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
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbResikoJatuhItemStateChanged

    private void R2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_R2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_R2ActionPerformed

    private void R3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_R3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_R3ActionPerformed

    private void R1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_R1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_R1ActionPerformed

    private void R4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_R4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_R4ActionPerformed

    private void R5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_R5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_R5ActionPerformed

    private void R6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_R6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_R6ActionPerformed

    private void R7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_R7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_R7ActionPerformed

    private void R8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_R8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_R8ActionPerformed

    private void cmbAlatBantuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbAlatBantuItemStateChanged
        if (cmbAlatBantu.getSelectedIndex() == 1) {
            AlatBantu.setVisible(true);
        } else {
            AlatBantu.setVisible(false);
            AlatBantu.setText("");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbAlatBantuItemStateChanged

    private void cmbDiantarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbDiantarItemStateChanged
    }//GEN-LAST:event_cmbDiantarItemStateChanged

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KeteranganKeyPressed

    private void Edukasi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Edukasi1KeyPressed

    private void ChkRspPlgItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkRspPlgItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkRspPlgItemStateChanged

    private void ChkRspPlgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkRspPlgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkRspPlgActionPerformed

    private void ChkRspPlg1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkRspPlg1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkRspPlg1ItemStateChanged

    private void ChkRspPlg1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkRspPlg1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkRspPlg1ActionPerformed

    private void ChkRspPlg2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkRspPlg2ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkRspPlg2ItemStateChanged

    private void ChkRspPlg2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkRspPlg2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkRspPlg2ActionPerformed

    private void ChkRspPlg3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkRspPlg3ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkRspPlg3ItemStateChanged

    private void ChkRspPlg3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkRspPlg3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkRspPlg3ActionPerformed

    private void ChkRspPlg4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkRspPlg4ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkRspPlg4ItemStateChanged

    private void ChkRspPlg4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkRspPlg4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkRspPlg4ActionPerformed

    private void chkMulutItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkMulutItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_chkMulutItemStateChanged

    private void chkMulutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMulutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkMulutActionPerformed

    private void chkMasukRSItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkMasukRSItemStateChanged
        setVisible();
    }//GEN-LAST:event_chkMasukRSItemStateChanged

    private void chkMasukRSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMasukRSActionPerformed

    }//GEN-LAST:event_chkMasukRSActionPerformed

    private void chkKepalaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkKepalaItemStateChanged
        if (chkKepala.isSelected() == true) {
            Kepala.setVisible(true);
        } else {
            Kepala.setVisible(false);
            Kepala.setText("");
        }
    }//GEN-LAST:event_chkKepalaItemStateChanged

    private void chkKepalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkKepalaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkKepalaActionPerformed

    private void chkMataItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkMataItemStateChanged
        if (chkMata.isSelected() == true) {
            Mata.setVisible(true);
        } else {
            Mata.setVisible(false);
            Mata.setText("");
        }
    }//GEN-LAST:event_chkMataItemStateChanged

    private void chkMataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkMataActionPerformed

    private void chkTHTItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkTHTItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_chkTHTItemStateChanged

    private void chkTHTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkTHTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkTHTActionPerformed

    private void chkLeherItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkLeherItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_chkLeherItemStateChanged

    private void chkLeherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkLeherActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkLeherActionPerformed

    private void chkDadaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkDadaItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_chkDadaItemStateChanged

    private void chkDadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkDadaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkDadaActionPerformed

    private void chkAbdomenItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkAbdomenItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_chkAbdomenItemStateChanged

    private void chkAbdomenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAbdomenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkAbdomenActionPerformed

    private void chkPunggungItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkPunggungItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_chkPunggungItemStateChanged

    private void chkPunggungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPunggungActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkPunggungActionPerformed

    private void chkGenetaliaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkGenetaliaItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_chkGenetaliaItemStateChanged

    private void chkGenetaliaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkGenetaliaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkGenetaliaActionPerformed

    private void chkEkstrimitasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkEkstrimitasItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_chkEkstrimitasItemStateChanged

    private void chkEkstrimitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkEkstrimitasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkEkstrimitasActionPerformed

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
    private widget.TextBox AlatBantu;
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
    private widget.Button BtnTambahSkala;
    private widget.CekBox ChkRspPlg;
    private widget.CekBox ChkRspPlg1;
    private widget.CekBox ChkRspPlg2;
    private widget.CekBox ChkRspPlg3;
    private widget.CekBox ChkRspPlg4;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox Dada;
    private widget.TextBox Diagnosa;
    private widget.TextBox Edukasi;
    private widget.TextBox Edukasi1;
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
    private widget.TextBox LILA;
    private widget.TextBox LK;
    private widget.TextBox Leher;
    private widget.editorpane LoadHTML2;
    private widget.TextBox Mata;
    private javax.swing.JMenuItem MnCetakTriase;
    private widget.TextBox Mulut;
    private widget.TextBox NIK;
    private widget.TextBox Nadi;
    private widget.TextBox NmDokter;
    private widget.TextBox NmKasus;
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
    private widget.TextBox THT;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
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
    private widget.CekBox chkLeher;
    private widget.CekBox chkMasukRS;
    private widget.CekBox chkMata;
    private widget.CekBox chkMulut;
    private widget.CekBox chkPunggung;
    private widget.CekBox chkTHT;
    private widget.ComboBox cmbAlatBantu;
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
    private widget.Label jLabel54;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel57;
    private widget.Label jLabel58;
    private widget.Label jLabel59;
    private widget.Label jLabel6;
    private widget.Label jLabel60;
    private widget.Label jLabel61;
    private widget.Label jLabel62;
    private widget.Label jLabel63;
    private widget.Label jLabel64;
    private widget.Label jLabel65;
    private widget.Label jLabel66;
    private widget.Label jLabel67;
    private widget.Label jLabel68;
    private widget.Label jLabel69;
    private widget.Label jLabel7;
    private widget.Label jLabel70;
    private widget.Label jLabel71;
    private widget.Label jLabel72;
    private widget.Label jLabel73;
    private widget.Label jLabel74;
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
                    + "penjab.png_jawab,a.kode_kasus,master_triase_macam_kasus.macam_kasus,a.stts_diantar,a.transportasi,a.stts_fungsional,a.psikologis,"
                    + "a.stts_tinggal,a.alat_bantu,a.ket_alatbantu,a.keluhan_utama,a.riwayat_penyakit,a.tekanan_darah,a.nadi,a.pernapasan,a.suhu,a.saturasi_o2,"
                    + "a.lk,a.lila,a.tinggi_badan,a.berat_badan,a.imt,a.edukasi,a.keluhan_utama_igd,a.riwayat_penyakit_skrng,a.riwayat_penyakit_dahulu,"
                    + "a.riwayat_pengobatan,a.riwayat_masuk_rs,a.riwayat_penyakit_keluarga,a.riwayat_operasi,a.riwayat_trauma,a.kepala,a.mata,a.tht,"
                    + "a.mulut,a.leher, a.dada,a.abdomen,a.punggung,a.genetalia,a.ekstrimitas,a.skala_nyeri,a.resiko_jatuh,a.kd_dokter,dokter.nm_dokter,"
                    + "a.kd_petugas,petugas.nama,a.ket_kepala,a.ket_mata,a.ket_tht,a.ket_mulut,a.ket_leher,a.ket_dada,a.ket_abdomen,a.ket_punggung,a.ket_genetalia,"
                    + "a.ket_ekstrimitas,a.nilai_resiko_jatuh,a.diagnosa_keperawatan,a.intervensi,a.diagnosis,a.tindakan,a.keterangan "
                    + "from data_triase_igd as a,reg_periksa,pasien,master_triase_macam_kasus,dokter,petugas,penjab "
                    + "where a.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and a.kode_kasus=master_triase_macam_kasus.kode_kasus "
                    + "and a.kd_dokter=dokter.kd_dokter and a.kd_petugas=petugas.nip and reg_periksa.kd_pj=penjab.kd_pj "
                    + "and a.tanggal between ? and ? and (a.no_rawat like ? or "
                    + "pasien.no_rkm_medis like ? or "
                    + "pasien.nm_pasien like ? or "
                    + "master_triase_macam_kasus.macam_kasus like ?) order by a.tanggal");
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
                        rs.getString("no_rawat"), rs.getString("tanggal"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"),
                        rs.getString("jk"), rs.getString("tgl_lahir"), rs.getString("umur"), rs.getString("no_ktp"), rs.getString("alamat"), rs.getString("no_tlp"),
                        rs.getString("agama"), rs.getString("png_jawab"), "","", rs.getString("stts_diantar"), rs.getString("transportasi"),
                        rs.getString("stts_fungsional"), rs.getString("psikologis"), rs.getString("stts_tinggal"), rs.getString("alat_bantu"), rs.getString("ket_alatbantu"), 
                        rs.getString("keluhan"),rs.getString("pemeriksaan"),rs.getString("tensi"), rs.getString("nadi"), rs.getString("respirasi"), rs.getString("suhu_tubuh"),
                        rs.getString("saturasi"), rs.getString("lk"), rs.getString("lila"), rs.getString("tinggi"), rs.getString("berat"), rs.getString("imt"),
                        rs.getString("edukasi"), rs.getString("riwayat_penyakit_dahulu"),rs.getString("riwayat_pengobatan"), rs.getString("riwayat_masuk_rs"), 
                        rs.getString("riwayat_penyakit_keluarga"), rs.getString("riwayat_operasi"),rs.getString("riwayat_trauma"), rs.getString("periksafisik"), 
                        rs.getString("diagnosis"), rs.getString("tindakan"), rs.getString("skala_nyeri"),rs.getString("resiko_jatuh"), rs.getString("nilai_resiko_jatuh"), 
                        rs.getString("diagnosa_keperawatan"), rs.getString("intervensi"), rs.getString("keterangan"),rs.getString("kd_dokter"), rs.getString("nm_dokter"), 
                        rs.getString("kd_petugas"), rs.getString("nama")

                    });
                    ps2 = koneksi.prepareStatement(
                            "select master_triase_igd.nm_tindakan from detail_pemeriksaan_triase inner join master_triase_igd "
                            + "on detail_pemeriksaan_triase.kd_tindakan=master_triase_igd.kd_tindakan where detail_pemeriksaan_triase.no_rawat=?");
                    try {
                        ps2.setString(1, rs.getString("no_rawat"));
                        rs2 = ps2.executeQuery();
                        while (rs2.next()) {
                            tabMode.addRow(new Object[]{
                                "", "", "", rs2.getString("nm_tindakan"), "", "", "", "", "", "",
                                "", "", "", "", "", "", "", "", "", "",
                                "", "", "", "", "", "", "", "", "", "",
                                "", "", "", "", "", "", "", "", "", "",
                                "", "", "", "", "", "", "", "", "", "",
                                ""
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
        Kepala.setText("");
        Mata.setText("");
        THT.setText("");
        Mulut.setText("");
        Leher.setText("");
        Dada.setText("");
        Abdomen.setText("");
        Punggung.setText("");
        Genetalia.setText("");
        Ekstrimitas.setText("");
        Diagnosa.setText("");
        Tindakan.setText("");
        Terapi.setText("");
        Keterangan.setText("");
        cmbAlatBantu.setSelectedIndex(0);
        dxKeperawatan.setSelectedIndex(0);
        Intervensi.setSelectedIndex(0);
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
            if (cmbTriase.getSelectedItem().toString() == "AIRWAY") {
                key = "kd_pemeriksaan='KP01'";
            } else if (cmbTriase.getSelectedItem().toString() == "BREATHING") {
                key = "kd_pemeriksaan='KP02'";
            } else if (cmbTriase.getSelectedItem().toString() == "CIRCULATION") {
                key = "kd_pemeriksaan='KP03'";
            } else if (cmbTriase.getSelectedItem().toString() == "DISABILITY") {
                key = "kd_pemeriksaan='KP04'";
            } else if (cmbTriase.getSelectedItem().toString() == "Prediksi pemeriksaan penunjang") {
                key = "kd_pemeriksaan='KP05'";
            } else if (cmbTriase.getSelectedItem().toString() == "Prediksi SDM yang akan terlibat") {
                key = "kd_pemeriksaan='KP06'";
            }
            ps = koneksi.prepareStatement(
                    //                        "select * from master_triase_skala1 where kode_pemeriksaan=? and pengkajian_skala1 like ? order by master_triase_skala1.kode_skala1");
                    "select * from master_triase_igd where " + key + " and nm_tindakan like ? order by kd_level, kd_tindakan");
            try {
//                    ps.setString(1, tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 0).toString());
                ps.setString(1, "%" + TCariSkala.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeLevel.addRow(new Object[]{false, rs.getString("kd_tindakan"), rs.getString("nm_level"), rs.getString("kd_tindakan"), rs.getString("nm_tindakan")});
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
    }

    private void getData() {
//        Kd2.setText("");
        if (tbTriase.getSelectedRow() != -1) {
//            Kd2.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString());
            TNoRw.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString());
            Tanggal.setDate(new Date());
            TNoRM.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 2).toString());
            TPasien.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 3).toString());
            JK.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 4).toString());
            TglLahir.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 5).toString());
            Umur.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 6).toString());
            NIK.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 7).toString());
            Alamat.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 8).toString());
            NoTelp.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 9).toString());
            Agama.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 10).toString());
            JnsBayar.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 11).toString());
//            KdKasus.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 12).toString());
            NmKasus.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 13).toString());
            cmbDiantar.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(), 14).toString());
            Transportasi.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(), 15).toString());
            SttsFungsional.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(), 16).toString());
            Psikologis.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(), 17).toString());
            SttsTinggal.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(), 18).toString());
            cmbAlatBantu.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(), 19).toString());
            AlatBantu.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 20).toString());
            KeluhanUtama.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 21).toString());
            RiwayatPenyakit.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 22).toString());
            Tensi.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 23).toString());
            Nadi.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 24).toString());
            Respirasi.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 25).toString());
            Suhu.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 26).toString());
            Saturasi.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 27).toString());
            LK.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 28).toString());
            LILA.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 29).toString());
            TB.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 30).toString());
            BB.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 31).toString());
            IMT.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 32).toString());
            Edukasi.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 33).toString());
//            KeluhanUtama1.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 34).toString());
//            RPSekarang.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 35).toString());
            rpDahulu.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 36).toString());
            rPengobatan.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 37).toString());
            rMasukRS.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 38).toString());
            rpKeluarga.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 39).toString());
            rOperasi.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 40).toString());
            rTrauma.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 41).toString());
//            chkKepala.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(), 42).toString());
            Kepala.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 43).toString());
//            chkMata.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(), 44).toString());
            Mata.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 45).toString());
//            chkTHT.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(), 46).toString());
            THT.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 47).toString());
//            chkMulut.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(), 48).toString());
            Mulut.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 49).toString());
//            chkLeher.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(), 50).toString());
            Leher.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 51).toString());
//            chkDada.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(), 52).toString());
            Dada.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 53).toString());
//            chkbdomen.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(), 54).toString());
            Abdomen.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 55).toString());
//            chkPunggung.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(), 56).toString());
            Punggung.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 57).toString());
//            chkGenetalia.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(), 58).toString());
            Genetalia.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 59).toString());
//            chkEkstrimitas.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(), 60).toString());
            Ekstrimitas.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 61).toString());
            Diagnosa.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 62).toString());
            Tindakan.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 63).toString());
            cmbSkalaNyeri.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(), 64).toString());
            cmbResikoJatuh.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(), 65).toString());

            dxKeperawatan.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(), 67).toString());
            Intervensi.setSelectedItem(tbTriase.getValueAt(tbTriase.getSelectedRow(), 68).toString());
            Keterangan.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 69).toString());
            KdDokter.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 70).toString());
            NmDokter.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 71).toString());
            KdPetugas.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 72).toString());
            NmPetugas.setText(tbTriase.getValueAt(tbTriase.getSelectedRow(), 73).toString());

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
        Sequel.meghapus("detail_pemeriksaan_triase", "no_rawat", tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString());
        tampil();
    }

    private void simpan() {
        try {
            koneksi.setAutoCommit(false);
            for (i = 0; i < tbLevel.getRowCount(); i++) {
                if (tbLevel.getValueAt(i, 0).toString().equals("true")) {
                    if (Sequel.menyimpantf("data_triase_igd", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "No.Rawat", 33, new String[]{
                        TNoRw.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""), Tanggal.getSelectedItem().toString().substring(11, 19),
                        KdDokter.getText(), KdPetugas.getText(), cmbDiantar.getSelectedItem().toString(), Transportasi.getSelectedItem().toString(),
                        SttsFungsional.getSelectedItem().toString(), Psikologis.getSelectedItem().toString(), SttsTinggal.getSelectedItem().toString(),
                        cmbAlatBantu.getSelectedItem().toString(), AlatBantu.getText(), KeluhanUtama.getText(), RiwayatPenyakit.getText(), LK.getText(),
                        LILA.getText(), IMT.getText(), Edukasi.getText(), rpDahulu.getText(), rPengobatan.getText(), rMasukRS.getText(),
                        rpKeluarga.getText(), rOperasi.getText(), rTrauma.getText(), periksafisik, cmbSkalaNyeri.getSelectedItem().toString(), cmbResikoJatuh.getSelectedItem().toString(),
                        resiko, dxKeperawatan.getSelectedItem().toString(), Intervensi.getSelectedItem().toString(), Diagnosa.getText(), Tindakan.getText(), Keterangan.getText()
                    }) == true) {
                        if (Sequel.menyimpantf("pemeriksaan_ralan", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Data", 16, new String[]{
                            TNoRw.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""), Tanggal.getSelectedItem().toString().substring(11, 19),
                            Suhu.getText(), Tensi.getText(), Nadi.getText(), Respirasi.getText(), TB.getText(), BB.getText(), "",
                            KeluhanUtama.getText(), RiwayatPenyakit.getText(), "",
                            "-", "", ""
                        }) == true) {
                            for (i = 0; i < tbLevel.getRowCount(); i++) {
                                if (tbLevel.getValueAt(i, 0).toString().equals("true")) {
                                    Sequel.menyimpan2("detail_pemeriksaan_triase", "?,?", "triase", 2, new String[]{
                                        TNoRw.getText(), tbLevel.getValueAt(i, 3).toString()
                                    });
                                }
                            }
                            isReset();
                            emptTeks();
                        }
                    }
                }
            }
            koneksi.setAutoCommit(true);
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }
    }

    private void ganti() {
        Sequel.AutoComitFalse();
        sukses = true;
        Sequel.meghapus("detail_pemeriksaan_triase", "no_rawat", tbTriase.getValueAt(tbTriase.getSelectedRow(), 0).toString());
        jmlskala1 = 0;
        for (i = 0; i < tbLevel.getRowCount(); i++) {
            if (tbLevel.getValueAt(i, 0).toString().equals("true")) {
                jmlskala1++;
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
        } else if (KdDokter.getText().trim().equals("") || NmDokter.getText().trim().equals("")) {
            Valid.textKosong(btnDokter, "Dokter/Petugas Triase");
        } else if ((jmlskala1 == 0)) {
            Valid.textKosong(TCariSkala, "Skala 1 / Skala 2");
        } else {
            if (Sequel.mengedittf("data_triase_igd", "no_rawat=?",
                    "no_rawat=?,tanggal=?,jam=?,kd_dokter=?,kd_petugas=?,stts_diantar=?,transportasi=?,stts_fungsional=?,"
                    + "psikologis=?,stts_tinggal=?,alat_bantu=?,ket_alatbantu=?,keluhan_utama=?,riwayat_penyakit=?,lk=?,lila=?,imt=?,"
                    + "edukasi=?riwayat_penyakit_dahulu=?,riwayat_pengobatan=?,riwayat_masuk_rs=?,riwayat_penyakit_keluarga=?,"
                    + "riwayat_operasi=?,riwayat_trauma=?,periksafisik=?,skala_nyeri=?,resiko_jatuh=?,nilai_resiko_jatuh=?,"
                    + "diagnosa_keperawatan=?,intervensi=?,diagnosis=?,tindakan=?,keterangan=?",
                    34, new String[]{
                        TNoRw.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""), Tanggal.getSelectedItem().toString().substring(11, 19),
                        KdDokter.getText(), KdPetugas.getText(), cmbDiantar.getSelectedItem().toString(), Transportasi.getSelectedItem().toString(),
                        SttsFungsional.getSelectedItem().toString(), Psikologis.getSelectedItem().toString(), SttsTinggal.getSelectedItem().toString(),
                        cmbAlatBantu.getSelectedItem().toString(), AlatBantu.getText(), KeluhanUtama.getText(), RiwayatPenyakit.getText(), LK.getText(),
                        LILA.getText(), IMT.getText(), Edukasi.getText(), rpDahulu.getText(), rPengobatan.getText(), rMasukRS.getText(),
                        rpKeluarga.getText(), rOperasi.getText(), rTrauma.getText(), periksafisik, cmbSkalaNyeri.getSelectedItem().toString(), cmbResikoJatuh.getSelectedItem().toString(),
                        resiko, dxKeperawatan.getSelectedItem().toString(), Intervensi.getSelectedItem().toString(), Diagnosa.getText(), Tindakan.getText(), Keterangan.getText()
                    }) == true) {
                if (Sequel.mengedittf("pemeriksaan_ralan", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Data", 16, new String[]{
                    TNoRw.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""), Tanggal.getSelectedItem().toString().substring(11, 19),
                    Suhu.getText(), Tensi.getText(), Nadi.getText(), Respirasi.getText(), TB.getText(), BB.getText(), "",
                    KeluhanUtama.getText(), RiwayatPenyakit.getText(), "",
                    "-", "", ""
                }) == true) {

                    for (i = 0; i < tbLevel.getRowCount(); i++) {
                        if (tbLevel.getValueAt(i, 0).toString().equals("true")) {
                            if (Sequel.menyimpantf2("detail_pemeriksaan_triase", "?,?", "triase", 2, new String[]{
                                TNoRw.getText(), tbLevel.getValueAt(i, 3).toString()
                            }) == true) {
                                tbLevel.setValueAt(false, i, 0);
                            } else {
                                sukses = false;
                            }
                        }
                    }
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
    }

    private void insertPasien() {
        if (R1.isSelected() == true) {
            resiko = "Pediatri: Rendah (0-24)";
        } else if (R2.isSelected() == true) {
            resiko = "Pediatri: Sedang (25-44)";
        } else if (R3.isSelected() == true) {
            resiko = "Pediatri: Tinggi (>= 45)";
        } else if (R4.isSelected() == true) {
            resiko = "Dewasa: Rendah (7-11)";
        } else if (R5.isSelected() == true) {
            resiko = "Dewasa: Tinggi (>=12)";
        } else if (R6.isSelected() == true) {
            resiko = "Lansia: Rendah (0-5)";
        } else if (R7.isSelected() == true) {
            resiko = "Lansia: Sedang (6-16)";
        } else if (R8.isSelected() == true) {
            resiko = "Lansia: Tinggi (17-30)";
        }

        kepala = "";
        mata = "";
        periksafisik = "";
        if (chkKepala.isSelected() == true) {
            kepala = "1. " + Kepala.getText() + "\n";
        }
        if (chkMata.isSelected() == true) {
            mata = "2. ";
        }

        if ((chkKepala.isSelected() == true) || (chkMata.isSelected() == true)) {
            periksafisik = kepala + mata;
        } else {
            periksafisik = "";
        }

        if (periksafisik.endsWith("\n")) {
            periksafisik = periksafisik.substring(0, periksafisik.length() - 1);
        }

        simpan();
    }

    public void setDokter(String norwt) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        KdDokter.setText(Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?", norwt));
        Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", NmDokter, KdDokter.getText());
        isReset();
        setobat();
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
        try {
            ps = koneksi.prepareStatement(
                    "select pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,"
                    + "concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.agama,"
                    + "pasien.no_ktp,pasien.alamat,pasien.no_tlp,penjab.png_jawab "
                    + "from pasien inner join reg_periksa inner join penjab on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "
                    + "and reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.no_rawat=?"
            );
            try {
                ps.setString(1, norawat);
                rs = ps.executeQuery();
                if (rs.next()) {
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    JK.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    Umur.setText(rs.getString("umur"));
                    NIK.setText(rs.getString("no_ktp"));
                    Alamat.setText(rs.getString("alamat"));
                    Agama.setText(rs.getString("agama"));
                    NoTelp.setText(rs.getString("no_tlp"));
                    JnsBayar.setText(rs.getString("png_jawab"));
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
            System.out.println("Notif : " + e);
        }
    }

    public void setobat() {
        TabPilihan.setSelectedIndex(0);
        try {
            ps = koneksi.prepareStatement("select no_rawat, tgl_perawatan, jam from resep_obat where no_rawat=?");
            try {
                ps.setString(1, TNoRw.getText());
                rs = ps.executeQuery();
                while (rs.next()) {
                    ps2 = koneksi.prepareStatement("select detail_pemberian_obat.jml, detail_pemberian_obat.kode_brng, databarang.nama_brng "
                            + "from detail_pemberian_obat inner join aturan_pakai inner join databarang "
                            + "on detail_pemberian_obat.no_rawat=aturan_pakai.no_rawat and detail_pemberian_obat.kode_brng=aturan_pakai.kode_brng and aturan_pakai.kode_brng=databarang.kode_brng "
                            + "where detail_pemberian_obat.tgl_perawatan=? and detail_pemberian_obat.jam=? and detail_pemberian_obat.no_rawat=?");
                    try {
                        ps2.setString(1, rs.getString("tgl_perawatan"));
                        ps2.setString(2, rs.getString("jam"));
                        ps2.setString(3, rs.getString("no_rawat"));
                        rs2 = ps2.executeQuery();
                        while (rs2.next()) {
                            aturan = Sequel.cariIsi("select aturan from aturan_pakai where tgl_perawatan='" + rs.getString("tgl_perawatan") + "' and "
                                    + "jam='" + rs.getString("jam") + "' and no_rawat='" + rs.getString("no_rawat") + "' and kode_brng='" + rs2.getString("kode_brng") + "'");
                            Terapi.setText(rs2.getString("jml") + " " + rs2.getString("nama_brng") + " " + aturan);
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
                }
            } catch (Exception ex) {
                System.out.println("Notifikasi : " + ex);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (SQLException e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    public void setVisible() {
        if (chkMasukRS.isSelected() == true) {
            jLabel47.setVisible(false);
            rpDahulu.setVisible(false);
            jLabel53.setVisible(false);
            rpKeluarga.setVisible(false);
            jLabel50.setVisible(false);
            rPengobatan.setVisible(false);
            jLabel51.setVisible(false);
            rOperasi.setVisible(false);
            jLabel52.setVisible(false);
            rTrauma.setVisible(false);
            jLabel49.setVisible(false);
            rMasukRS.setVisible(false);
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
        AlatBantu.setVisible(false);
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
}
