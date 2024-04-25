/*
 * Kontribusi dari M. Syukur RS. Jiwa Prov Sultra
 */
package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
import kepegawaian.DlgCariPetugas;
import keuangan.DlgKamar;
import org.apache.commons.codec.language.Nysiis;
import widget.Tanggal;

/**
 *
 * @author perpustakaan
 */
public final class RMTransferPasienAntarRuang extends javax.swing.JDialog {

    private final DefaultTableModel tabMode, tabModeResep, tabMode2;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps, ps2, ps3;
    private ResultSet rs, rs2, rs3;
    private int i = 0, pilihan = 0, z = 0, jml = 0,index = 0;
    private boolean[] pilih;
    private String[] kodebarang, namabarang, dosis, caraberi, tanggal, jam;
    private String penunjang = "", tglpindah = "", jampindah = "", ruangpindah = "", dokterpindah = "", petugaspindah = "", suhu = "", nadi = "", darah = "", respirasi = "", nyeri = "", dx = "", dxkep = "",tglmasuk="",jammasuk="",kelas="",sttsTransfer="", key="",kodeptg="",limit="";    
    public DlgKamar kamar = new DlgKamar(null, false);
    private DlgCariPetugas petugas = new DlgCariPetugas(null, false);
    private DlgCariDokter dokter = new DlgCariDokter(null, false);
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private LocalDateTime now = LocalDateTime.now();

    /**
     * Creates new form DlgRujuk
     *
     * @param parent
     * @param modal
     */
    public RMTransferPasienAntarRuang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode = new DefaultTableModel(null, new Object[]{
            "id", "Tanggal", "Jam", "No.Rawat", "No.RM", "Nama Pasien", "Tgl.Lahir", "J.K", "Tgl. Masuk", "Jam Masuk", "Asal Ruang Rawat", "Dokter Awal", "Petugas Awal",
            "Tgl. Pindah", "Jam Pindah", "Ruang Selanjutnya", "Dokter Selanjutnya", "Petugas yg Menerima", "Alasan Pindah", "Hasil Penunjang", "Rekomendasi",
            "Suhu", "Nadi", "TD", "RR", "Kesadaran", "Nyeri", "Diagnosa Medis", "Diagnosa Keperawatan","Status"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 30; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if ((i == 0)||(i==29)) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 1) {
                column.setPreferredWidth(70);
            } else if (i == 2) {
                column.setPreferredWidth(55);
            } else if (i == 3) {
                column.setPreferredWidth(105);
            } else if (i == 4) {
                column.setPreferredWidth(60);
            } else if (i == 5) {
                column.setPreferredWidth(150);
            } else if (i == 6) {
                column.setPreferredWidth(70);
            } else if (i == 7) {
                column.setPreferredWidth(30);
            } else if (i == 8) {
                column.setPreferredWidth(70);
            } else if (i == 9) {
                column.setPreferredWidth(55);
            } else if (i == 10) {
                column.setPreferredWidth(100);
            } else if (i == 11) {
                column.setPreferredWidth(150);
            } else if (i == 12) {
                column.setPreferredWidth(150);
            } else if (i == 13) {
                column.setPreferredWidth(70);
            } else if (i == 14) {
                column.setPreferredWidth(55);
            } else if (i == 15) {
                column.setPreferredWidth(150);
            } else if (i == 16) {
                column.setPreferredWidth(150);
            } else if (i == 17) {
                column.setPreferredWidth(120);
            } else if (i == 18) {
                column.setPreferredWidth(150);
            } else if (i == 19) {
                column.setPreferredWidth(155);
            } else if (i == 20) {
                column.setPreferredWidth(165);
            } else if (i == 21) {//suhu
//                column.setPreferredWidth(40);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 22) {
//                column.setPreferredWidth(40);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 23) {
//                column.setPreferredWidth(40);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 24) {
//                column.setPreferredWidth(40);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 25) {
//                column.setPreferredWidth(75);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 26) {
//                column.setPreferredWidth(40);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 27) {
//                column.setPreferredWidth(90);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 28) {
//                column.setPreferredWidth(100);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeResep = new DefaultTableModel(null, new Object[]{
            "P", "Tanggal", "Jam", "Kode Barang", "Nama Barang", "Dosis", "Cara Pemberian"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if ((colIndex == 0) || (colIndex == 5) || (colIndex == 6)) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbResep.setModel(tabModeResep);
        tbResep.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbResep.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 7; i++) {
            TableColumn column = tbResep.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(30);
            } else if (i == 1) {
                column.setPreferredWidth(60);
            } else if (i == 2) {
                column.setPreferredWidth(55);
            } else if (i == 3) {
//                column.setPreferredWidth(75);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 4) {
                column.setPreferredWidth(150);
            } else if (i == 5) {
                column.setPreferredWidth(45);
            } else if (i == 6) {
                column.setPreferredWidth(90);
            }
        }
        tbResep.setDefaultRenderer(Object.class, new WarnaTable());

        tabMode2 = new DefaultTableModel(null, new Object[]{
            "id", "Tanggal", "Jam", "Obat", "Dosis", "Cara Pemberian"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbResep1.setModel(tabMode2);

        tbResep1.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbResep1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 6; i++) {
            TableColumn column = tbResep1.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 1) {
                column.setPreferredWidth(60);
            } else if (i == 2) {
                column.setPreferredWidth(50);
            } else if (i == 3) {
                column.setPreferredWidth(180);
            } else if (i == 4) {
                column.setPreferredWidth(50);
            } else if (i == 5) {
                column.setPreferredWidth(90);
            }
        }
        tbResep1.setDefaultRenderer(Object.class, new WarnaTable());

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
                    if (pilihan == 1) {
                        Petugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                        NmPetugasMenyerahkan.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                        Petugas.requestFocus();
                    } else {
                        Petugas1.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                        NmPetugasMenerima.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                        Petugas1.requestFocus();
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

        kamar.bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (kamar.bangsal.getTable().getSelectedRow() != -1) {
                    KdBangsal1.setText(kamar.bangsal.getTable().getValueAt(kamar.bangsal.getTable().getSelectedRow(), 0).toString());
                    RuangSelanjutnya.setText(kamar.bangsal.getTable().getValueAt(kamar.bangsal.getTable().getSelectedRow(), 1).toString());
                }
                RuangSelanjutnya.requestFocus();
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
                    KdDokter1.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                    NmDokter1.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                    btnDokter1.requestFocus();
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
        MnCetakSurat = new javax.swing.JMenuItem();
        Tanggal = new widget.TextBox();
        Jam = new widget.TextBox();
        Kd2 = new widget.TextBox();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        MnHapusObat = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame5 = new widget.InternalFrame();
        FormInput4 = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel35 = new widget.Label();
        JK = new widget.TextBox();
        TglLahir = new widget.TextBox();
        jLabel9 = new widget.Label();
        panelisi5 = new widget.panelisi();
        jPanel2 = new javax.swing.JPanel();
        FormInput3 = new javax.swing.JPanel();
        PanelInput1 = new widget.PanelBiasa();
        jLabel32 = new widget.Label();
        CmbJam2 = new widget.ComboBox();
        CmbMenit2 = new widget.ComboBox();
        CmbDetik2 = new widget.ComboBox();
        DTPReg = new widget.Tanggal();
        jLabel33 = new widget.Label();
        jLabel34 = new widget.Label();
        Scroll1 = new widget.ScrollPane();
        tbResep = new widget.Table();
        BtnAll1 = new widget.Button();
        TCari1 = new widget.TextBox();
        BtnCari2 = new widget.Button();
        jPanel3 = new javax.swing.JPanel();
        Scroll4 = new widget.ScrollPane();
        tbResep1 = new widget.Table();
        ScrollTriase2 = new widget.ScrollPane();
        FormTriase1 = new widget.InternalFrame();
        FormInput1 = new widget.PanelBiasa();
        jSeparator15 = new javax.swing.JSeparator();
        label13 = new widget.Label();
        label14 = new widget.Label();
        RuangSelanjutnya = new widget.TextBox();
        jLabel36 = new widget.Label();
        jLabel37 = new widget.Label();
        AsalRuang = new widget.TextBox();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel49 = new widget.Label();
        jLabel50 = new widget.Label();
        TD = new widget.TextBox();
        jLabel38 = new widget.Label();
        jLabel39 = new widget.Label();
        jLabel51 = new widget.Label();
        Nadi = new widget.TextBox();
        jLabel52 = new widget.Label();
        RR = new widget.TextBox();
        jLabel53 = new widget.Label();
        jLabel54 = new widget.Label();
        Suhu = new widget.TextBox();
        jLabel56 = new widget.Label();
        jLabel57 = new widget.Label();
        Petugas = new widget.TextBox();
        NmPetugasMenyerahkan = new widget.TextBox();
        btnBangsalPindah = new widget.Button();
        label17 = new widget.Label();
        Petugas1 = new widget.TextBox();
        NmPetugasMenerima = new widget.TextBox();
        BtnMenerima = new widget.Button();
        label18 = new widget.Label();
        btnPetugasAwal = new widget.Button();
        jLabel59 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        jLabel60 = new widget.Label();
        KdDokter1 = new widget.TextBox();
        NmDokter1 = new widget.TextBox();
        btnDokter1 = new widget.Button();
        jLabel74 = new widget.Label();
        jLabel61 = new widget.Label();
        Diagnosa = new widget.TextBox();
        jLabel62 = new widget.Label();
        jLabel63 = new widget.Label();
        Alasan = new widget.ComboBox();
        jLabel64 = new widget.Label();
        TLainnya = new widget.TextBox();
        jLabel65 = new widget.Label();
        jLabel66 = new widget.Label();
        jLabel67 = new widget.Label();
        jSeparator10 = new javax.swing.JSeparator();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel68 = new widget.Label();
        chkLab = new widget.CekBox();
        chkRontgen = new widget.CekBox();
        chkEkg = new widget.CekBox();
        chkLain = new widget.CekBox();
        chkUsg = new widget.CekBox();
        chkEeg = new widget.CekBox();
        TAlergi = new widget.TextBox();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel69 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        Terapi = new widget.TextArea();
        KdBangsal = new widget.TextBox();
        KdBangsal1 = new widget.TextBox();
        TanggalPindah = new widget.TextBox();
        cmbNyeri = new widget.TextBox();
        KeadaanUmumSebelum1 = new widget.TextBox();
        TanggalMasuk = new widget.TextBox();
        scrollPane1 = new widget.ScrollPane();
        dxKeperawatan = new widget.TextArea();
        jLabel55 = new widget.Label();
        spo2 = new widget.TextBox();
        jLabel70 = new widget.Label();
        gcs = new widget.TextBox();
        internalFrame3 = new widget.InternalFrame();
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
        cmbJenisTransfer = new widget.ComboBox();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCetakSurat.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSurat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSurat.setForeground(new java.awt.Color(70, 70, 70));
        MnCetakSurat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSurat.setText("Cetak Form Transfer Pasien");
        MnCetakSurat.setName("MnCetakSurat"); // NOI18N
        MnCetakSurat.setPreferredSize(new java.awt.Dimension(200, 28));
        MnCetakSurat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakSurat);

        Tanggal.setHighlighter(null);
        Tanggal.setName("Tanggal"); // NOI18N

        Jam.setHighlighter(null);
        Jam.setName("Jam"); // NOI18N

        Kd2.setHighlighter(null);
        Kd2.setName("Kd2"); // NOI18N

        jPopupMenu2.setName("jPopupMenu2"); // NOI18N

        MnHapusObat.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusObat.setForeground(new java.awt.Color(70, 70, 70));
        MnHapusObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusObat.setText("Hapus Obat");
        MnHapusObat.setName("MnHapusObat"); // NOI18N
        MnHapusObat.setPreferredSize(new java.awt.Dimension(200, 28));
        MnHapusObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusObatActionPerformed(evt);
            }
        });
        jPopupMenu2.add(MnHapusObat);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Transfer Pasien Antar Ruang ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

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

        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame5.setBorder(null);
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput4.setName("FormInput4"); // NOI18N
        FormInput4.setPreferredSize(new java.awt.Dimension(260, 43));
        FormInput4.setLayout(null);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput4.add(jLabel3);
        jLabel3.setBounds(0, 10, 70, 23);

        TNoRw.setEditable(false);
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        FormInput4.add(TNoRw);
        TNoRw.setBounds(74, 10, 125, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput4.add(TNoRM);
        TNoRM.setBounds(201, 10, 80, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput4.add(TPasien);
        TPasien.setBounds(283, 10, 270, 23);

        jLabel35.setText("J.K. :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput4.add(jLabel35);
        jLabel35.setBounds(565, 10, 30, 23);

        JK.setEditable(false);
        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N
        FormInput4.add(JK);
        JK.setBounds(600, 10, 50, 23);

        TglLahir.setEditable(false);
        TglLahir.setBackground(new java.awt.Color(245, 250, 240));
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput4.add(TglLahir);
        TglLahir.setBounds(730, 10, 90, 23);

        jLabel9.setText("Tgl.Lahir :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput4.add(jLabel9);
        jLabel9.setBounds(625, 10, 100, 23);

        internalFrame5.add(FormInput4, java.awt.BorderLayout.PAGE_START);

        panelisi5.setBorder(null);
        panelisi5.setName("panelisi5"); // NOI18N
        panelisi5.setPreferredSize(new java.awt.Dimension(450, 77));
        panelisi5.setLayout(new java.awt.GridLayout(2, 0));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), ".: Input Riwayat Pemberian Obat", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(100, 80, 80))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(200, 102));
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput3.setName("FormInput3"); // NOI18N
        FormInput3.setOpaque(false);
        FormInput3.setPreferredSize(new java.awt.Dimension(560, 243));
        FormInput3.setLayout(new java.awt.BorderLayout(1, 1));

        PanelInput1.setName("PanelInput1"); // NOI18N
        PanelInput1.setPreferredSize(new java.awt.Dimension(560, 159));
        PanelInput1.setLayout(null);

        jLabel32.setText("Jam pemberian :");
        jLabel32.setName("jLabel32"); // NOI18N
        PanelInput1.add(jLabel32);
        jLabel32.setBounds(150, 10, 90, 23);

        CmbJam2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        CmbJam2.setName("CmbJam2"); // NOI18N
        PanelInput1.add(CmbJam2);
        CmbJam2.setBounds(245, 10, 62, 23);

        CmbMenit2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbMenit2.setName("CmbMenit2"); // NOI18N
        PanelInput1.add(CmbMenit2);
        CmbMenit2.setBounds(305, 10, 62, 23);

        CmbDetik2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbDetik2.setName("CmbDetik2"); // NOI18N
        PanelInput1.add(CmbDetik2);
        CmbDetik2.setBounds(365, 10, 62, 23);

        DTPReg.setForeground(new java.awt.Color(50, 70, 50));
        DTPReg.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "20-02-2024" }));
        DTPReg.setDisplayFormat("dd-MM-yyyy");
        DTPReg.setName("DTPReg"); // NOI18N
        DTPReg.setOpaque(false);
        PanelInput1.add(DTPReg);
        DTPReg.setBounds(65, 10, 90, 23);

        jLabel33.setText("Tanggal :");
        jLabel33.setName("jLabel33"); // NOI18N
        PanelInput1.add(jLabel33);
        jLabel33.setBounds(0, 10, 60, 23);

        jLabel34.setText("Obat :");
        jLabel34.setName("jLabel34"); // NOI18N
        PanelInput1.add(jLabel34);
        jLabel34.setBounds(0, 40, 60, 23);

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll1.setName("Scroll1"); // NOI18N

        tbResep.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbResep.setName("tbResep"); // NOI18N
        Scroll1.setViewportView(tbResep);

        PanelInput1.add(Scroll1);
        Scroll1.setBounds(10, 70, 410, 80);

        BtnAll1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnAll1.setMnemonic('2');
        BtnAll1.setText("Tambah Obat Pemberian");
        BtnAll1.setToolTipText("Alt+2");
        BtnAll1.setName("BtnAll1"); // NOI18N
        BtnAll1.setPreferredSize(new java.awt.Dimension(208, 23));
        BtnAll1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAll1ActionPerformed(evt);
            }
        });
        PanelInput1.add(BtnAll1);
        BtnAll1.setBounds(210, 160, 208, 23);

        TCari1.setName("TCari1"); // NOI18N
        TCari1.setPreferredSize(new java.awt.Dimension(197, 23));
        TCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCari1KeyPressed(evt);
            }
        });
        PanelInput1.add(TCari1);
        TCari1.setBounds(65, 40, 320, 23);

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
        PanelInput1.add(BtnCari2);
        BtnCari2.setBounds(390, 40, 28, 23);

        FormInput3.add(PanelInput1, java.awt.BorderLayout.CENTER);

        jPanel2.add(FormInput3, java.awt.BorderLayout.PAGE_START);

        panelisi5.add(jPanel2);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(100, 80, 80))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(200, 102));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll4.setName("Scroll4"); // NOI18N

        tbResep1.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbResep1.setName("tbResep1"); // NOI18N
        tbResep1.setComponentPopupMenu(jPopupMenu2);
        Scroll4.setViewportView(tbResep1);

        jPanel3.add(Scroll4, java.awt.BorderLayout.CENTER);

        panelisi5.add(jPanel3);

        internalFrame5.add(panelisi5, java.awt.BorderLayout.EAST);

        ScrollTriase2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        ScrollTriase2.setName("ScrollTriase2"); // NOI18N
        ScrollTriase2.setOpaque(true);
        ScrollTriase2.setPreferredSize(new java.awt.Dimension(882, 761));

        FormTriase1.setBorder(null);
        FormTriase1.setName("FormTriase1"); // NOI18N
        FormTriase1.setPreferredSize(new java.awt.Dimension(882, 751));
        FormTriase1.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput1.setBackground(new java.awt.Color(255, 255, 255));
        FormInput1.setBorder(null);
        FormInput1.setName("FormInput1"); // NOI18N
        FormInput1.setPreferredSize(new java.awt.Dimension(870, 743));
        FormInput1.setLayout(null);

        jSeparator15.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator15.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator15.setName("jSeparator15"); // NOI18N
        FormInput1.add(jSeparator15);
        jSeparator15.setBounds(0, 861, 880, 0);

        label13.setText("Tgl/Jam Masuk :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label13);
        label13.setBounds(0, 10, 100, 23);

        label14.setText("Tgl/Jam Pindah :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label14);
        label14.setBounds(0, 120, 100, 23);

        RuangSelanjutnya.setEditable(false);
        RuangSelanjutnya.setHighlighter(null);
        RuangSelanjutnya.setName("RuangSelanjutnya"); // NOI18N
        FormInput1.add(RuangSelanjutnya);
        RuangSelanjutnya.setBounds(175, 150, 195, 23);

        jLabel36.setText("Dipindah Ke :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput1.add(jLabel36);
        jLabel36.setBounds(30, 150, 70, 23);

        jLabel37.setText("Dari Ruang :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput1.add(jLabel37);
        jLabel37.setBounds(30, 40, 70, 23);

        AsalRuang.setEditable(false);
        AsalRuang.setHighlighter(null);
        AsalRuang.setName("AsalRuang"); // NOI18N
        FormInput1.add(AsalRuang);
        AsalRuang.setBounds(175, 40, 215, 23);

        jSeparator8.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator8.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator8.setName("jSeparator8"); // NOI18N
        FormInput1.add(jSeparator8);
        jSeparator8.setBounds(0, 80, 880, 1);

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel49.setText("BACKGROUND");
        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput1.add(jLabel49);
        jLabel49.setBounds(20, 190, 170, 23);

        jLabel50.setText("Kesadaran :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput1.add(jLabel50);
        jLabel50.setBounds(0, 380, 100, 23);

        TD.setEditable(false);
        TD.setFocusTraversalPolicyProvider(true);
        TD.setName("TD"); // NOI18N
        FormInput1.add(TD);
        TD.setBounds(275, 320, 60, 23);

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel38.setText("mmHg");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput1.add(jLabel38);
        jLabel38.setBounds(340, 320, 50, 23);

        jLabel39.setText("Nadi :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput1.add(jLabel39);
        jLabel39.setBounds(0, 350, 100, 23);

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel51.setText("x/menit");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput1.add(jLabel51);
        jLabel51.setBounds(170, 350, 50, 23);

        Nadi.setEditable(false);
        Nadi.setFocusTraversalPolicyProvider(true);
        Nadi.setName("Nadi"); // NOI18N
        FormInput1.add(Nadi);
        Nadi.setBounds(105, 350, 60, 23);

        jLabel52.setText("RR :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput1.add(jLabel52);
        jLabel52.setBounds(220, 350, 50, 23);

        RR.setEditable(false);
        RR.setFocusTraversalPolicyProvider(true);
        RR.setName("RR"); // NOI18N
        FormInput1.add(RR);
        RR.setBounds(275, 350, 60, 23);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("x/menit");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput1.add(jLabel53);
        jLabel53.setBounds(340, 350, 50, 23);

        jLabel54.setText("Suhu :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput1.add(jLabel54);
        jLabel54.setBounds(0, 320, 100, 23);

        Suhu.setEditable(false);
        Suhu.setFocusTraversalPolicyProvider(true);
        Suhu.setName("Suhu"); // NOI18N
        FormInput1.add(Suhu);
        Suhu.setBounds(105, 320, 60, 23);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("°C");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput1.add(jLabel56);
        jLabel56.setBounds(170, 320, 30, 23);

        jLabel57.setText("TD :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput1.add(jLabel57);
        jLabel57.setBounds(220, 320, 50, 23);

        Petugas.setEditable(false);
        Petugas.setName("Petugas"); // NOI18N
        Petugas.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput1.add(Petugas);
        Petugas.setBounds(510, 40, 70, 23);

        NmPetugasMenyerahkan.setEditable(false);
        NmPetugasMenyerahkan.setName("NmPetugasMenyerahkan"); // NOI18N
        NmPetugasMenyerahkan.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput1.add(NmPetugasMenyerahkan);
        NmPetugasMenyerahkan.setBounds(580, 40, 190, 23);

        btnBangsalPindah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBangsalPindah.setMnemonic('2');
        btnBangsalPindah.setToolTipText("Alt+2");
        btnBangsalPindah.setName("btnBangsalPindah"); // NOI18N
        btnBangsalPindah.setPreferredSize(new java.awt.Dimension(28, 23));
        btnBangsalPindah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBangsalPindahActionPerformed(evt);
            }
        });
        FormInput1.add(btnBangsalPindah);
        btnBangsalPindah.setBounds(370, 150, 28, 23);

        label17.setText("Petugas Selanjutnya :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label17);
        label17.setBounds(395, 150, 110, 23);

        Petugas1.setEditable(false);
        Petugas1.setName("Petugas1"); // NOI18N
        Petugas1.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput1.add(Petugas1);
        Petugas1.setBounds(510, 150, 70, 23);

        NmPetugasMenerima.setEditable(false);
        NmPetugasMenerima.setName("NmPetugasMenerima"); // NOI18N
        NmPetugasMenerima.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput1.add(NmPetugasMenerima);
        NmPetugasMenerima.setBounds(580, 150, 190, 23);

        BtnMenerima.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnMenerima.setMnemonic('2');
        BtnMenerima.setToolTipText("Alt+2");
        BtnMenerima.setName("BtnMenerima"); // NOI18N
        BtnMenerima.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnMenerima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMenerimaActionPerformed(evt);
            }
        });
        FormInput1.add(BtnMenerima);
        BtnMenerima.setBounds(770, 150, 28, 23);

        label18.setText("Petugas Awal :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput1.add(label18);
        label18.setBounds(395, 40, 110, 23);

        btnPetugasAwal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugasAwal.setMnemonic('3');
        btnPetugasAwal.setToolTipText("Alt+3");
        btnPetugasAwal.setName("btnPetugasAwal"); // NOI18N
        btnPetugasAwal.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugasAwal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasAwalActionPerformed(evt);
            }
        });
        FormInput1.add(btnPetugasAwal);
        btnPetugasAwal.setBounds(770, 40, 28, 23);

        jLabel59.setText("Dokter Awal :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput1.add(jLabel59);
        jLabel59.setBounds(395, 10, 110, 23);

        KdDokter.setEditable(false);
        KdDokter.setHighlighter(null);
        KdDokter.setName("KdDokter"); // NOI18N
        FormInput1.add(KdDokter);
        KdDokter.setBounds(510, 10, 70, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        FormInput1.add(NmDokter);
        NmDokter.setBounds(580, 10, 210, 23);

        jLabel60.setText("Dokter Selanjutnya :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput1.add(jLabel60);
        jLabel60.setBounds(395, 120, 110, 23);

        KdDokter1.setEditable(false);
        KdDokter1.setHighlighter(null);
        KdDokter1.setName("KdDokter1"); // NOI18N
        FormInput1.add(KdDokter1);
        KdDokter1.setBounds(510, 120, 70, 23);

        NmDokter1.setEditable(false);
        NmDokter1.setName("NmDokter1"); // NOI18N
        FormInput1.add(NmDokter1);
        NmDokter1.setBounds(580, 120, 190, 23);

        btnDokter1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter1.setMnemonic('1');
        btnDokter1.setToolTipText("Alt+1");
        btnDokter1.setName("btnDokter1"); // NOI18N
        btnDokter1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokter1ActionPerformed(evt);
            }
        });
        FormInput1.add(btnDokter1);
        btnDokter1.setBounds(770, 120, 28, 23);

        jLabel74.setText("Dx Keperawatan :");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput1.add(jLabel74);
        jLabel74.setBounds(395, 350, 110, 23);

        jLabel61.setText("Dx Medis :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput1.add(jLabel61);
        jLabel61.setBounds(395, 320, 110, 20);

        Diagnosa.setEditable(false);
        Diagnosa.setName("Diagnosa"); // NOI18N
        FormInput1.add(Diagnosa);
        Diagnosa.setBounds(510, 320, 270, 23);

        jLabel62.setText("Nyeri :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput1.add(jLabel62);
        jLabel62.setBounds(220, 380, 50, 23);

        jLabel63.setText("Alasan Pindah :");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput1.add(jLabel63);
        jLabel63.setBounds(0, 220, 100, 23);

        Alasan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Atas order dokter", "Atas permintaan sendiri" }));
        Alasan.setName("Alasan"); // NOI18N
        FormInput1.add(Alasan);
        Alasan.setBounds(105, 220, 285, 23);

        jLabel64.setText("Riwayat alergi :");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput1.add(jLabel64);
        jLabel64.setBounds(395, 220, 110, 23);

        TLainnya.setFocusTraversalPolicyProvider(true);
        TLainnya.setName("TLainnya"); // NOI18N
        FormInput1.add(TLainnya);
        TLainnya.setBounds(645, 480, 130, 23);

        jLabel65.setText("Hasil Pemeriksaan Penunjang yang disertakan pindah :");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput1.add(jLabel65);
        jLabel65.setBounds(20, 450, 280, 23);

        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel66.setText("SITUATIONS");
        jLabel66.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput1.add(jLabel66);
        jLabel66.setBounds(20, 90, 170, 23);

        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel67.setText("ASSESSMENT");
        jLabel67.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput1.add(jLabel67);
        jLabel67.setBounds(20, 260, 170, 23);

        jSeparator10.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator10.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator10.setName("jSeparator10"); // NOI18N
        FormInput1.add(jSeparator10);
        jSeparator10.setBounds(0, 185, 880, 1);

        jSeparator11.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator11.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator11.setName("jSeparator11"); // NOI18N
        FormInput1.add(jSeparator11);
        jSeparator11.setBounds(0, 255, 880, 1);

        jLabel68.setText("Keadaan Pasien Saat Pindah ");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput1.add(jLabel68);
        jLabel68.setBounds(10, 290, 160, 23);

        chkLab.setBorder(null);
        chkLab.setText("Laboratorium");
        chkLab.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkLab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        chkLab.setName("chkLab"); // NOI18N
        FormInput1.add(chkLab);
        chkLab.setBounds(160, 480, 100, 23);

        chkRontgen.setBorder(null);
        chkRontgen.setText("Foto Rontgen");
        chkRontgen.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkRontgen.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        chkRontgen.setName("chkRontgen"); // NOI18N
        FormInput1.add(chkRontgen);
        chkRontgen.setBounds(30, 480, 100, 23);

        chkEkg.setBorder(null);
        chkEkg.setText("EKG");
        chkEkg.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkEkg.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        chkEkg.setName("chkEkg"); // NOI18N
        FormInput1.add(chkEkg);
        chkEkg.setBounds(300, 480, 50, 23);

        chkLain.setBorder(null);
        chkLain.setText("Lain-lain");
        chkLain.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkLain.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        chkLain.setName("chkLain"); // NOI18N
        chkLain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chkLainMouseClicked(evt);
            }
        });
        FormInput1.add(chkLain);
        chkLain.setBounds(565, 480, 70, 23);

        chkUsg.setBorder(null);
        chkUsg.setText("USG");
        chkUsg.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkUsg.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        chkUsg.setName("chkUsg"); // NOI18N
        FormInput1.add(chkUsg);
        chkUsg.setBounds(390, 480, 50, 23);

        chkEeg.setBorder(null);
        chkEeg.setText("EEG");
        chkEeg.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkEeg.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        chkEeg.setName("chkEeg"); // NOI18N
        FormInput1.add(chkEeg);
        chkEeg.setBounds(480, 480, 50, 23);

        TAlergi.setEditable(false);
        TAlergi.setFocusTraversalPolicyProvider(true);
        TAlergi.setName("TAlergi"); // NOI18N
        FormInput1.add(TAlergi);
        TAlergi.setBounds(510, 220, 270, 23);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput1.add(jSeparator12);
        jSeparator12.setBounds(0, 510, 880, 1);

        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel69.setText("RECOMENDATION");
        jLabel69.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput1.add(jLabel69);
        jLabel69.setBounds(20, 520, 170, 23);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        Terapi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Terapi.setColumns(20);
        Terapi.setRows(5);
        Terapi.setName("Terapi"); // NOI18N
        scrollPane5.setViewportView(Terapi);

        FormInput1.add(scrollPane5);
        scrollPane5.setBounds(60, 550, 715, 120);

        KdBangsal.setEditable(false);
        KdBangsal.setHighlighter(null);
        KdBangsal.setName("KdBangsal"); // NOI18N
        FormInput1.add(KdBangsal);
        KdBangsal.setBounds(105, 40, 70, 23);

        KdBangsal1.setEditable(false);
        KdBangsal1.setName("KdBangsal1"); // NOI18N
        KdBangsal1.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput1.add(KdBangsal1);
        KdBangsal1.setBounds(105, 150, 70, 23);

        TanggalPindah.setEditable(false);
        TanggalPindah.setFocusTraversalPolicyProvider(true);
        TanggalPindah.setName("TanggalPindah"); // NOI18N
        FormInput1.add(TanggalPindah);
        TanggalPindah.setBounds(105, 120, 285, 23);

        cmbNyeri.setEditable(false);
        cmbNyeri.setFocusTraversalPolicyProvider(true);
        cmbNyeri.setName("cmbNyeri"); // NOI18N
        FormInput1.add(cmbNyeri);
        cmbNyeri.setBounds(275, 380, 110, 23);

        KeadaanUmumSebelum1.setEditable(false);
        KeadaanUmumSebelum1.setFocusTraversalPolicyProvider(true);
        KeadaanUmumSebelum1.setName("KeadaanUmumSebelum1"); // NOI18N
        FormInput1.add(KeadaanUmumSebelum1);
        KeadaanUmumSebelum1.setBounds(105, 380, 110, 23);

        TanggalMasuk.setEditable(false);
        TanggalMasuk.setFocusTraversalPolicyProvider(true);
        TanggalMasuk.setName("TanggalMasuk"); // NOI18N
        FormInput1.add(TanggalMasuk);
        TanggalMasuk.setBounds(105, 10, 285, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        dxKeperawatan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        dxKeperawatan.setColumns(20);
        dxKeperawatan.setRows(5);
        dxKeperawatan.setName("dxKeperawatan"); // NOI18N
        dxKeperawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dxKeperawatanKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(dxKeperawatan);

        FormInput1.add(scrollPane1);
        scrollPane1.setBounds(510, 350, 270, 83);

        jLabel55.setText("spO2 :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput1.add(jLabel55);
        jLabel55.setBounds(0, 410, 100, 23);

        spo2.setEditable(false);
        spo2.setFocusTraversalPolicyProvider(true);
        spo2.setName("spo2"); // NOI18N
        FormInput1.add(spo2);
        spo2.setBounds(105, 410, 110, 23);

        jLabel70.setText("GCS :");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput1.add(jLabel70);
        jLabel70.setBounds(220, 410, 50, 23);

        gcs.setEditable(false);
        gcs.setFocusTraversalPolicyProvider(true);
        gcs.setName("gcs"); // NOI18N
        FormInput1.add(gcs);
        gcs.setBounds(275, 410, 110, 23);

        FormTriase1.add(FormInput1, java.awt.BorderLayout.PAGE_START);

        ScrollTriase2.setViewportView(FormTriase1);

        internalFrame5.add(ScrollTriase2, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Transfer Pasien", internalFrame5);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setName("tbObat"); // NOI18N
        tbObat.setComponentPopupMenu(jPopupMenu1);
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

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(68, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "20-02-2024" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "20-02-2024" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(197, 23));
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

        cmbJenisTransfer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ralan", "Ranap" }));
        cmbJenisTransfer.setName("cmbJenisTransfer"); // NOI18N
        cmbJenisTransfer.setPreferredSize(new java.awt.Dimension(85, 28));
        cmbJenisTransfer.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbJenisTransferItemStateChanged(evt);
            }
        });
        cmbJenisTransfer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbJenisTransferActionPerformed(evt);
            }
        });
        cmbJenisTransfer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJenisTransferKeyPressed(evt);
            }
        });
        panelGlass9.add(cmbJenisTransfer);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(LCount);

        internalFrame3.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        TabRawat.addTab("Data Transfer Pasien", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
        } else if (KdBangsal.getText().trim().equals("") || AsalRuang.getText().trim().equals("")) {
            Valid.textKosong(KdBangsal, "Ruang Awal");
        } else if (Petugas.getText().trim().equals("") || NmPetugasMenyerahkan.getText().trim().equals("")) {
            Valid.textKosong(Petugas, "Petugas Awal");
        } else if (KdDokter.getText().trim().equals("") || NmDokter.getText().trim().equals("")) {
            Valid.textKosong(KdDokter, "Dokter Awal");
        } else if (KdBangsal1.getText().trim().equals("") || RuangSelanjutnya.getText().trim().equals("")) {
            Valid.textKosong(KdBangsal1, "Ruang Selanjutnya");
        } else if (Petugas1.getText().trim().equals("") || NmPetugasMenerima.getText().trim().equals("")) {
            Valid.textKosong(Petugas1, "Petugas Selanjutnya");
        } else if (KdDokter1.getText().trim().equals("") || NmDokter1.getText().trim().equals("")) {
            Valid.textKosong(KdDokter1, "Dokter Selanjutnya");
        } else {
            switch (TabRawat.getSelectedIndex()) {
                case 0:
                    int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, udah bener belum data yang mau disimpan..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        insertPenunjang();
                        if (Sequel.menyimpantf("transfer_pasien", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "No.Rawat", 17, new String[]{
                            null, TNoRw.getText(), dtf.format(now).toString().substring(0, 10), dtf.format(now).toString().substring(11, 19),
                            KdBangsal.getText(), KdDokter.getText(), Petugas.getText(),TanggalMasuk.getText(), KdBangsal1.getText(), KdDokter1.getText(), Petugas1.getText(),TanggalPindah.getText(), Alasan.getSelectedItem().toString(), penunjang, Terapi.getText(), var.getkode(),sttsTransfer}) == true) {
                            emptTeks();
                            JOptionPane.showMessageDialog(null, "Berhasil simpan data transfer pasien..");
                        }
                    }
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "Maaf, Simpan data hanya bisa dilakukan di Tab Input Transfer Pasien..!!");
                    break;
            }
        }

}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnMenerima, BtnBatal);
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
        if (tbObat.getSelectedRow() > -1) {
            if (var.getkode().equals("Admin Utama")) {
                hapus();
            } else {
                if (var.getkode().equals(kodeptg)) {
                    hapus();
                } else {
                    JOptionPane.showMessageDialog(null, "Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
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
            Valid.textKosong(TNoRw, "No.Rawat");
        } else if (KdBangsal.getText().trim().equals("") || AsalRuang.getText().trim().equals("")) {
            Valid.textKosong(KdBangsal, "Ruang Awal");
        } else if (Petugas.getText().trim().equals("") || NmPetugasMenyerahkan.getText().trim().equals("")) {
            Valid.textKosong(Petugas, "Petugas Awal");
        } else if (KdDokter.getText().trim().equals("") || NmDokter.getText().trim().equals("")) {
            Valid.textKosong(KdDokter, "Dokter Awal");
        } else if (KdBangsal1.getText().trim().equals("") || RuangSelanjutnya.getText().trim().equals("")) {
            Valid.textKosong(KdBangsal1, "Ruang Selanjutnya");
        } else if (Petugas1.getText().trim().equals("") || NmPetugasMenerima.getText().trim().equals("")) {
            Valid.textKosong(Petugas1, "Petugas Selanjutnya");
        } else if (KdDokter1.getText().trim().equals("") || NmDokter1.getText().trim().equals("")) {
            Valid.textKosong(KdDokter1, "Dokter Selanjutnya");
        } else {
            if (tbObat.getSelectedRow() > -1) {
                if (var.getkode().equals("Admin Utama")) {
                    insertPenunjang();
                    ganti();
                } else {
                    if (var.getkode().equals(kodeptg)) {
                        insertPenunjang();
                        ganti();
                    } else {
                        JOptionPane.showMessageDialog(null, "Hanya bisa diganti oleh petugas yang bersangkutan..!!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
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
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
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
                getData2();
            } catch (java.lang.NullPointerException e) {
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
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if (TabRawat.getSelectedIndex() == 1) {
            tampil();
        }else{
            tampilResep();
            tampilResep(TNoRw.getText());
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void btnBangsalPindahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBangsalPindahActionPerformed
        kamar.bangsal.isCek();
        kamar.bangsal.emptTeks();
        kamar.bangsal.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        kamar.bangsal.setLocationRelativeTo(internalFrame1);
        kamar.bangsal.setVisible(true);
    }//GEN-LAST:event_btnBangsalPindahActionPerformed

    private void BtnMenerimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMenerimaActionPerformed
        pilihan = 2;
        petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnMenerimaActionPerformed

    private void btnPetugasAwalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasAwalActionPerformed
        pilihan = 1;
        petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasAwalActionPerformed

    private void btnDokter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokter1ActionPerformed
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokter1ActionPerformed

    private void BtnAll1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAll1ActionPerformed
        int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, udah bener belum data yang mau ditambah..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            for (i = 0; i < tbResep.getRowCount(); i++) {
                if (tbResep.getValueAt(i, 0).toString().equals("true")) {
                    String kd_obat = Sequel.cariIsi("SELECT kd_barang FROM transfer_pasien_detail WHERE no_rawat = '" + TNoRw.getText() + "' AND kd_barang = '" + tbResep.getValueAt(i, 1).toString() + "' and tanggal='"+Valid.SetTgl(DTPReg.getSelectedItem() + "")+"' and jam='"+CmbJam2.getSelectedItem() + ":" + CmbMenit2.getSelectedItem() + ":" + CmbDetik2.getSelectedItem()+"' ");
                    if (!kd_obat.isEmpty()) {
                        Sequel.mengedit("transfer_pasien_detail", "no_rawat = '" + TNoRw.getText() + "' AND kd_barang = '" + tbResep.getValueAt(i, 1).toString() + "'", "dosis = '" + tbResep.getValueAt(i, 3).toString() + "', cara_pemberian='" + tbResep.getValueAt(i, 4).toString() + "'");
                    }
                    if (kd_obat.isEmpty()) {
                        Sequel.menyimpan2("transfer_pasien_detail", "?,?,?,?,?,?,?,?", "Data", 8, new String[]{
                            null, TNoRw.getText(), Valid.SetTgl(DTPReg.getSelectedItem() + ""), CmbJam2.getSelectedItem() + ":" + CmbMenit2.getSelectedItem() + ":" + CmbDetik2.getSelectedItem(),
                            tbResep.getValueAt(i, 3).toString(), tbResep.getValueAt(i, 5).toString(), tbResep.getValueAt(i, 6).toString(),sttsTransfer
                        });
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Berhasil menambahkan obat transfer pasien..");
            isReset();
            tampilResep(TNoRw.getText());
        }
    }//GEN-LAST:event_BtnAll1ActionPerformed

    private void MnCetakSuratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (Kd2.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Gagal mencetak. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        } else if (!Kd2.getText().trim().equals("")) {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            param.put("norm", TNoRM.getText());
            param.put("nmpasien", TPasien.getText());
            param.put("nik", Sequel.cariIsi("SELECT no_ktp from pasien where no_rkm_medis=?", TNoRM.getText()));
            param.put("jk", JK.getText());
            param.put("tgllahir", TglLahir.getText());
            param.put("ruangawal", AsalRuang.getText());
            param.put("dokterawal", NmDokter.getText());
            param.put("petugasawal", NmPetugasMenyerahkan.getText());
            param.put("tglmasuk", TanggalMasuk.getText().substring(0, 10));
            param.put("jammasuk", TanggalMasuk.getText().substring(11, 19));
            param.put("ruangpindah", RuangSelanjutnya.getText());
            param.put("dokterpindah", NmDokter1.getText());
            param.put("petugaspindah", NmPetugasMenerima.getText());
            param.put("tglpindah", TanggalPindah.getText().substring(0, 10));
            param.put("jampindah", TanggalPindah.getText().substring(11, 19));
            param.put("suhu", Suhu.getText());
            param.put("nadi", Nadi.getText());
            param.put("tensi", TD.getText());
            param.put("respirasi", RR.getText());
            param.put("nyeri", cmbNyeri.getText());
            param.put("dx", Diagnosa.getText());
            param.put("dxkep", dxKeperawatan.getText());
            param.put("spo2", spo2.getText());
            param.put("gsc", gcs.getText());
            param.put("raber", Sequel.cariIsi("SELECT GROUP_CONCAT(CONCAT(' ',dokter.nm_dokter)) as raber FROM dpjp_ranap INNER JOIN dokter ON dpjp_ranap.kd_dokter=dokter.kd_dokter WHERE dpjp_ranap.jenis_dpjp='Raber' AND dpjp_ranap.no_rawat='"+TNoRw.getText()+"'"));
            param.put("raber1", Sequel.cariIsi("SELECT dokter.nm_dokter as raber FROM dpjp_ranap INNER JOIN dokter ON dpjp_ranap.kd_dokter=dokter.kd_dokter WHERE dpjp_ranap.jenis_dpjp='Raber' AND dpjp_ranap.no_rawat='"+TNoRw.getText()+"' LIMIT 1"));
            if (sttsTransfer.equals("Ralan")) {
                param.put("kateter", Sequel.cariIsi("SELECT rawat_jl_pr.tgl_perawatan FROM rawat_jl_pr INNER JOIN jns_perawatan ON rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw WHERE jns_perawatan.nm_perawatan LIKE '%kateter%' and no_rawat=? LIMIT 1", TNoRw.getText()));
                param.put("oksigen", Sequel.cariIsi("SELECT rawat_jl_pr.tgl_perawatan FROM rawat_jl_pr INNER JOIN jns_perawatan ON rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw WHERE jns_perawatan.nm_perawatan LIKE '%oksigen%' and no_rawat=? LIMIT 1", TNoRw.getText()));
                param.put("ngt", Sequel.cariIsi("SELECT rawat_jl_pr.tgl_perawatan FROM rawat_jl_pr INNER JOIN jns_perawatan ON rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw WHERE jns_perawatan.nm_perawatan LIKE '%ngt%' and no_rawat=? LIMIT 1", TNoRw.getText()));
                param.put("infus", Sequel.cariIsi("SELECT rawat_jl_pr.tgl_perawatan FROM rawat_jl_pr INNER JOIN jns_perawatan ON rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw WHERE jns_perawatan.nm_perawatan LIKE '%infus%' and no_rawat=? LIMIT 1", TNoRw.getText()));
            }
            if (sttsTransfer.equals("Ranap")) {
                param.put("kateter", Sequel.cariIsi("SELECT rawat_inap_pr.tgl_perawatan FROM rawat_inap_pr join jns_perawatan_inap on rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "
                        + "WHERE jns_perawatan_inap.nm_perawatan like '%kateter%' and rawat_inap_pr.no_rawat=? and rawat_inap_pr.tgl_perawatan between '" + TanggalMasuk.getText().substring(0, 10) + "' and '" + TanggalPindah.getText().substring(0, 10) + "' order by rawat_inap_pr.tgl_perawatan limit 1", TNoRw.getText()));
                param.put("oksigen", Sequel.cariIsi("SELECT rawat_inap_pr.tgl_perawatan FROM rawat_inap_pr join jns_perawatan_inap on rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "
                        + "WHERE jns_perawatan_inap.nm_perawatan like '%oksigen%' and rawat_inap_pr.no_rawat=? and rawat_inap_pr.tgl_perawatan between '" + TanggalMasuk.getText().substring(0, 10) + "' and '" + TanggalPindah.getText().substring(0, 10) + "' order by rawat_inap_pr.tgl_perawatan limit 1", TNoRw.getText()));
                param.put("ngt", Sequel.cariIsi("SELECT rawat_inap_pr.tgl_perawatan FROM rawat_inap_pr join jns_perawatan_inap on rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "
                        + "WHERE jns_perawatan_inap.nm_perawatan like '%ngt%' and rawat_inap_pr.no_rawat=? and rawat_inap_pr.tgl_perawatan between '" + TanggalMasuk.getText().substring(0, 10) + "' and '" + TanggalPindah.getText().substring(0, 10) + "' order by rawat_inap_pr.tgl_perawatan limit 1", TNoRw.getText()));
                param.put("infus", Sequel.cariIsi("SELECT rawat_inap_pr.tgl_perawatan FROM rawat_inap_pr join jns_perawatan_inap on rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "
                        + "WHERE jns_perawatan_inap.nm_perawatan like '%infus%' and rawat_inap_pr.no_rawat=? and rawat_inap_pr.tgl_perawatan between '" + TanggalMasuk.getText().substring(0, 10) + "' and '" + TanggalPindah.getText().substring(0, 10) + "' order by rawat_inap_pr.tgl_perawatan limit 1", TNoRw.getText()));
            }
            param.put("alasan", Alasan.getSelectedItem().toString());
            param.put("alergi", TAlergi.getText());
            param.put("rekomendasi", Terapi.getText());
            param.put("jk_dokterawal", Sequel.cariIsi("SELECT jk FROM dokter WHERE kd_dokter=?", KdDokter.getText()));
            param.put("penunjang", Sequel.cariIsi("SELECT hasil_penunjang FROM transfer_pasien WHERE id=?", Kd2.getText()));
            param.put("kelas", kelas);
            Sequel.queryu("truncate table temporary");
            try {
                ps = koneksi.prepareStatement(
                        "SELECT transfer_pasien_detail.id, transfer_pasien_detail.tanggal, transfer_pasien_detail.jam, databarang.nama_brng, transfer_pasien_detail.dosis, transfer_pasien_detail.cara_pemberian "
                        + "FROM transfer_pasien_detail, databarang WHERE transfer_pasien_detail.kd_barang=databarang.kode_brng and transfer_pasien_detail.no_rawat=? and transfer_pasien_detail.status='" + sttsTransfer + "' "
                        + "and (transfer_pasien_detail.tanggal + INTERVAL transfer_pasien_detail.jam HOUR_SECOND) BETWEEN '"+TanggalMasuk.getText()+"' AND '"+TanggalPindah.getText()+"'");
                try {
                    ps.setString(1, tbObat.getValueAt(tbObat.getSelectedRow(), 3).toString());
                    rs = ps.executeQuery();
                    if (rs.isBeforeFirst()) {
                        int num = 0;
                        while (rs.next()) {
//                            System.out.println("insert "+num+" : "+rs.getString("nama_brng"));
                            Sequel.menyimpan("temporary", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", 38, new String[]{
                                null, rs.getString("id"), rs.getString("tanggal"), rs.getString("jam"), rs.getString("nama_brng"), rs.getString("dosis"), rs.getString("cara_pemberian"), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
                            }); 
                            num++;
                        }
                    } else {
                        Sequel.menyimpan("temporary", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", 38, new String[]{
                                null, "-", "-", "-", "-", "-", "-", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
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
            if (sttsTransfer.equals("Ralan")) {
//                if (Sequel.cariInteger("select count(no) from temporary") == 0) {
//                    System.out.println("kosong");
//                    Valid.MyReport("rptTransferPasien.jrxml", param, "::[ Transfer Pasien IGD ]::");
//                }
//                if (Sequel.cariInteger("select count(no) from temporary") > 0) {
//                    System.out.println("ada");
                    Valid.MyReport("rptTransferPasien.jrxml", "report", "::[ Transfer Pasien IGD ]::",
                            "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14, temp14, temp15, temp16 from temporary order by no asc", param);
//                }
            }
            if (sttsTransfer.equals("Ranap")) {
                Valid.MyReport("rptTransferPasienRanap.jrxml", "report", "::[ Transfer Pasien Antar Ruang ]::",
                        "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14, temp14, temp15, temp16 from temporary order by no asc", param);
            }
            this.setCursor(Cursor.getDefaultCursor());
        }       
    }//GEN-LAST:event_MnCetakSuratActionPerformed

    private void MnHapusObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusObatActionPerformed
        if (tbResep1.getSelectedRow() > -1) {
            int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, Yakin mau dihapus..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                if (Sequel.queryu2tf("delete from transfer_pasien_detail where id=?", 1, new String[]{
                    tbResep1.getValueAt(tbResep1.getSelectedRow(), 0).toString()
                }) == true) {
                    JOptionPane.showMessageDialog(null, "Berhasil hapus obat..!!");
                    tampilResep(TNoRw.getText());
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal menghapus..!!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Silahkan anda pilih data terlebih dahulu..!!");
        }
    }//GEN-LAST:event_MnHapusObatActionPerformed

    private void cmbJenisTransferItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbJenisTransferItemStateChanged

    }//GEN-LAST:event_cmbJenisTransferItemStateChanged

    private void cmbJenisTransferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbJenisTransferActionPerformed

    }//GEN-LAST:event_cmbJenisTransferActionPerformed

    private void cmbJenisTransferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJenisTransferKeyPressed

    }//GEN-LAST:event_cmbJenisTransferKeyPressed

    private void TCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCari1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCari2ActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari2.requestFocus();
        }
    }//GEN-LAST:event_TCari1KeyPressed

    private void BtnCari2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari2ActionPerformed
        tampilResep();
    }//GEN-LAST:event_BtnCari2ActionPerformed

    private void BtnCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCari2ActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari1, BtnAll1);
        }
    }//GEN-LAST:event_BtnCari2KeyPressed

    private void chkLainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkLainMouseClicked
        if (chkLain.isSelected()) {
            TLainnya.setVisible(true);
        }else{
            TLainnya.setVisible(false);
        }
    }//GEN-LAST:event_chkLainMouseClicked

    private void dxKeperawatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dxKeperawatanKeyPressed
        Valid.pindah(evt,Diagnosa,Terapi);
    }//GEN-LAST:event_dxKeperawatanKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMTransferPasienAntarRuang dialog = new RMTransferPasienAntarRuang(new javax.swing.JFrame(), true);
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
    private widget.ComboBox Alasan;
    private widget.TextBox AsalRuang;
    private widget.Button BtnAll;
    private widget.Button BtnAll1;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCari2;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnMenerima;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.ComboBox CmbDetik2;
    private widget.ComboBox CmbJam2;
    private widget.ComboBox CmbMenit2;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPReg;
    private widget.TextBox Diagnosa;
    private widget.PanelBiasa FormInput1;
    private javax.swing.JPanel FormInput3;
    private widget.PanelBiasa FormInput4;
    private widget.InternalFrame FormTriase1;
    private widget.TextBox JK;
    private widget.TextBox Jam;
    private widget.TextBox Kd2;
    private widget.TextBox KdBangsal;
    private widget.TextBox KdBangsal1;
    private widget.TextBox KdDokter;
    private widget.TextBox KdDokter1;
    private widget.TextBox KeadaanUmumSebelum1;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnCetakSurat;
    private javax.swing.JMenuItem MnHapusObat;
    private widget.TextBox Nadi;
    private widget.TextBox NmDokter;
    private widget.TextBox NmDokter1;
    private widget.TextBox NmPetugasMenerima;
    private widget.TextBox NmPetugasMenyerahkan;
    private widget.PanelBiasa PanelInput1;
    private widget.TextBox Petugas;
    private widget.TextBox Petugas1;
    private widget.TextBox RR;
    private widget.TextBox RuangSelanjutnya;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll4;
    private widget.ScrollPane ScrollTriase2;
    private widget.TextBox Suhu;
    private widget.TextBox TAlergi;
    private widget.TextBox TCari;
    private widget.TextBox TCari1;
    private widget.TextBox TD;
    private widget.TextBox TLainnya;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox Tanggal;
    private widget.TextBox TanggalMasuk;
    private widget.TextBox TanggalPindah;
    private widget.TextArea Terapi;
    private widget.TextBox TglLahir;
    private widget.Button btnBangsalPindah;
    private widget.Button btnDokter1;
    private widget.Button btnPetugasAwal;
    private widget.CekBox chkEeg;
    private widget.CekBox chkEkg;
    private widget.CekBox chkLab;
    private widget.CekBox chkLain;
    private widget.CekBox chkRontgen;
    private widget.CekBox chkUsg;
    private widget.ComboBox cmbJenisTransfer;
    private widget.TextBox cmbNyeri;
    private widget.TextArea dxKeperawatan;
    private widget.TextBox gcs;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame5;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel3;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel49;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel57;
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
    private widget.Label jLabel74;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator8;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label17;
    private widget.Label label18;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.panelisi panelisi5;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane5;
    private widget.TextBox spo2;
    private widget.Table tbObat;
    private widget.Table tbResep;
    private widget.Table tbResep1;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        if (TCari.getText().equals("")) {
            key = " a.status like '%" + cmbJenisTransfer.getSelectedItem().toString() + "%' and ";
        } else {
            key = "";
        }
        try {
            ps = koneksi.prepareStatement(
                    "SELECT a.id,a.tanggal, a.jam, a.no_rawat, c.no_rkm_medis, c.nm_pasien,c.jk, c.tgl_lahir, d.nm_dokter, e.nama, f.nm_bangsal, a.alasan_pindah, a.hasil_penunjang, a.keterangan, a.status, a.tgl_awal, a.tgl_pindah "
                    + "FROM transfer_pasien as a,reg_periksa as b,pasien as c,dokter as d,petugas as e,bangsal as f "
                    + "WHERE a.dokter_awal=d.kd_dokter and a.no_rawat=b.no_rawat and b.no_rkm_medis=c.no_rkm_medis and a.petugas_awal=e.nip and a.ruang_awal=f.kd_bangsal "
                    + "and "+key+" a.tanggal between ? and ? and (a.no_rawat like ? or c.no_rkm_medis like ? or c.nm_pasien like ?)");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, "%" + TCari.getText().trim() + "%");
                ps.setString(4, "%" + TCari.getText().trim() + "%");
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
//                    tglpindah = "";
//                    jampindah = "";
//                    ruangpindah = "";
//                    dokterpindah = "";
//                    petugaspindah = "";
                    suhu = "";
                    nadi = "";
                    darah = "";
                    respirasi = "";
                    nyeri = "";
//                    dx = Sequel.cariIsi("SELECT GROUP_CONCAT(CONCAT(penyakit.kd_penyakit,' ',penyakit.nm_penyakit)) FROM diagnosa_pasien INNER JOIN penyakit ON diagnosa_pasien.kd_penyakit = penyakit.kd_penyakit WHERE diagnosa_pasien.no_rawat='"+rs.getString("no_rawat")+"' AND diagnosa_pasien.status = 'Ralan'");
                    dx = "";
                    dxkep = "";
//                    ps2 = koneksi.prepareStatement(
//                            "SELECT bangsal.nm_bangsal, dokter.nm_dokter, petugas.nama FROM transfer_pasien as tf,dokter,petugas,bangsal "
//                            + "WHERE tf.dokter_pindah=dokter.kd_dokter and tf.petugas_pindah=petugas.nip and tf.ruang_pindah=bangsal.kd_bangsal and tf.no_rawat=?");
//                    try {
//                        ps2.setString(1, rs.getString("no_rawat"));
//                        rs2 = ps2.executeQuery();
//                        if (rs2.next()) {
//                            ruangpindah = rs2.getString("nm_bangsal");
//                            dokterpindah = rs2.getString("nm_dokter");
//                            petugaspindah = rs2.getString("nama");
//                        }
//                    } catch (Exception e) {
//                        System.out.println("Notif ps2: " + e);
//                    } finally {
//                        if (rs2 != null) {
//                            rs2.close();
//                        }
//                        if (ps2 != null) {
//                            ps2.close();
//                        }
//                    }

//                    ps3 = koneksi.prepareStatement(
//                            "SELECT triase.skala_nyeri, triase.diagnosa_keperawatan, ralan.suhu_tubuh, ralan.tensi, ralan.nadi, ralan.respirasi "
//                            + "FROM data_triase_igd as triase,pemeriksaan_ralan as ralan where triase.no_rawat=ralan.no_rawat and triase.no_rawat=? ORDER BY ralan.tgl_perawatan DESC, ralan.jam_rawat DESC limit 1");
//                    try {
//                        ps3.setString(1, rs.getString("no_rawat"));
//                        rs3 = ps3.executeQuery();
//                        if (rs3.next()) {
//                            suhu = rs3.getString("suhu_tubuh");
//                            nadi = rs3.getString("nadi");
//                            darah = rs3.getString("tensi");
//                            respirasi = rs3.getString("respirasi");
//                            nyeri = rs3.getString("skala_nyeri");
//                            dxkep = rs3.getString("diagnosa_keperawatan");
//                        }
//                    } catch (Exception e) {
//                        System.out.println("Notif : " + e);
//                    } finally {
//                        if (rs3 != null) {
//                            rs3.close();
//                        }
//                        if (ps3 != null) {
//                            ps3.close();
//                        }
//                    }
                    tabMode.addRow(new Object[]{
                        rs.getString("id"), rs.getString("tanggal"), rs.getString("jam"), rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"), rs.getString("tgl_lahir"), rs.getString("jk"),
                        rs.getString("tgl_awal").substring(0,10), rs.getString("tgl_awal").substring(11,19), rs.getString("nm_bangsal"), rs.getString("nm_dokter"), rs.getString("nama"), rs.getString("tgl_pindah").substring(0,10), rs.getString("tgl_pindah").substring(11,19), 
//                        ruangpindah, dokterpindah, petugaspindah,
                        Sequel.cariIsi("SELECT bangsal.nm_bangsal FROM transfer_pasien INNER JOIN bangsal ON transfer_pasien.ruang_pindah=bangsal.kd_bangsal WHERE transfer_pasien.id='"+rs.getString("id")+"'"),
                        Sequel.cariIsi("SELECT dokter.nm_dokter FROM transfer_pasien INNER JOIN dokter ON transfer_pasien.dokter_pindah=dokter.kd_dokter WHERE transfer_pasien.id='"+rs.getString("id")+"'"),
                        Sequel.cariIsi("SELECT petugas.nama FROM transfer_pasien INNER JOIN petugas ON transfer_pasien.petugas_pindah=petugas.nip WHERE transfer_pasien.id='"+rs.getString("id")+"'"),
                        rs.getString("alasan_pindah"), rs.getString("hasil_penunjang"), rs.getString("keterangan"),
//                        suhu, nadi, darah, respirasi, "", nyeri, dx, dxkep,
                        "","","","","","","","",
                        rs.getString("status")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif 1: " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif 2: " + e);
        }
        LCount.setText("" + tabMode.getRowCount());
    }

    public void emptTeks() {
        Petugas.setText("");
        NmPetugasMenyerahkan.setText("");
        Petugas1.setText("");
        NmPetugasMenerima.setText("");
        Terapi.setText("");
        chkRontgen.setSelected(false);
        chkLab.setSelected(false);
        chkEkg.setSelected(false);
        chkEeg.setSelected(false);
        chkUsg.setSelected(false);
        chkLain.setSelected(false);
        TLainnya.setVisible(false);
        TLainnya.setText("");        
    }

    private void getData() {
        if (tbObat.getSelectedRow() != -1) {
            Kd2.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString());
            Tanggal.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString());
            Jam.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString());
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 3).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 4).toString());
            TPasien.setText(Sequel.cariIsi("SELECT nm_pasien FROM pasien WHERE no_rkm_medis=?", TNoRM.getText()));
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString());
            TanggalMasuk.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString()+" "+tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString());            
            KdBangsal.setText(Sequel.cariIsi("SELECT ruang_awal FROM transfer_pasien WHERE id=?", Kd2.getText()));
            AsalRuang.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 10).toString());
            KdDokter.setText(Sequel.cariIsi("SELECT dokter_awal FROM transfer_pasien WHERE id=?", Kd2.getText()));
            NmDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString());
            Petugas.setText(Sequel.cariIsi("SELECT petugas_awal FROM transfer_pasien WHERE id=?", Kd2.getText()));
            NmPetugasMenyerahkan.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 12).toString());
            TanggalPindah.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString()+" "+tbObat.getValueAt(tbObat.getSelectedRow(), 14).toString());            
            KdBangsal1.setText(Sequel.cariIsi("SELECT ruang_pindah FROM transfer_pasien WHERE id=?", Kd2.getText()));
            RuangSelanjutnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 15).toString());
            KdDokter1.setText(Sequel.cariIsi("SELECT dokter_pindah FROM transfer_pasien WHERE id=?", Kd2.getText()));
            NmDokter1.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 16).toString());
            Petugas1.setText(Sequel.cariIsi("SELECT petugas_pindah FROM transfer_pasien WHERE id=?", Kd2.getText()));
            NmPetugasMenerima.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 17).toString());
            Alasan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 18).toString());
            Terapi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 20).toString());
            sttsTransfer=tbObat.getValueAt(tbObat.getSelectedRow(), 29).toString();
            getCPPT(TNoRw.getText());
            kodeptg=Sequel.cariIsi("select user from transfer_pasien where id='"+Kd2.getText()+"'");
        }
    }

    public void getData2() {
        penunjang = tbObat.getValueAt(tbObat.getSelectedRow(), 19).toString();
        String[] kasus = penunjang.split(";");
        for (int i = 0; i < kasus.length; i++) {
            String[] kss = kasus[i].split(", ");
            for (int j = 0; j < kss.length; j++) {
                switch (kss[0]) {
                    case "Rontgen":
                        chkRontgen.setSelected(true);
                        break;
                    case "Laboratorium":
                        chkLab.setSelected(true);
                        break;
                    case "EKG":
                        chkEkg.setSelected(true);
                        break;
                    case "USG":
                        chkUsg.setSelected(true);
                        break;
                    case "EEG":
                        chkEeg.setSelected(true);
                        break;
                    case "Lain-lain":
                        chkLain.setSelected(true);
                        TLainnya.setText(kss[1]);
                        break;
                }
            }
        }
    }

    private void isRawat() {
        try {
            ps = koneksi.prepareStatement(
                    "SELECT kamar_inap.tgl_masuk, kamar_inap.jam_masuk, bangsal.kd_bangsal,bangsal.nm_bangsal FROM kamar_inap,kamar,bangsal "
                    + "WHERE kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal AND kamar_inap.no_rawat=? "
                    + "ORDER BY kamar_inap.tgl_masuk ASC");
            try {
                ps.setString(1, TNoRw.getText());
                rs = ps.executeQuery();
                if (rs.next()) {
                    tglpindah=rs.getString("tgl_masuk");
                    jampindah= rs.getString("jam_masuk");
                    TanggalPindah.setText(tglpindah+" "+jampindah);
                    KdBangsal1.setText(rs.getString("kd_bangsal"));
                    RuangSelanjutnya.setText(rs.getString("nm_bangsal"));
                    KdDokter1.setText(Sequel.cariIsi("select dpjp_ranap.kd_dokter from dpjp_ranap,dokter where dpjp_ranap.kd_dokter=dokter.kd_dokter and dpjp_ranap.jenis_dpjp='Utama' and dpjp_ranap.no_rawat='" + TNoRw.getText() + "' Limit 1"));
                    NmDokter1.setText(Sequel.cariIsi("SELECT nm_dokter FROM dokter WHERE kd_dokter='" + KdDokter1.getText() + "'"));
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

    public void isCek() {
    }

    private void hapus() {
        int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, Yakin mau dihapus..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            if (Sequel.queryu2tf("delete from transfer_pasien where id=?", 1, new String[]{
                tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()
            }) == true) {
                tampil();
                TabRawat.setSelectedIndex(1);
            } else {
                JOptionPane.showMessageDialog(null, "Gagal menghapus..!!");
            }
        }
    }

    private void ganti() {
        int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, Yakin mau diedit..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            if (Sequel.mengedittf("transfer_pasien", "id=?", "petugas_awal=?,dokter_pindah=?,petugas_pindah=?,alasan_pindah=?,hasil_penunjang=?,keterangan=?", 7, new String[]{
                Petugas.getText(), KdDokter1.getText(), Petugas1.getText(), Alasan.getSelectedItem().toString(), penunjang, Terapi.getText(),
                tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString()
            }) == true) {
                JOptionPane.showMessageDialog(null, "Berhasil mengedit data..!!");
                TabRawat.setSelectedIndex(1);
                tampil();
            }
        }
    }

    private void simpan() {
        if (Sequel.menyimpantf("transfer_pasien", "?,?,?,?,?,?,?,?,?,?,?,?,?,?", "No.Rawat", 14, new String[]{
            "0", TNoRw.getText(), TanggalPindah.getText().toString().substring(0,10), CmbJam2.getSelectedItem() + ":" + CmbMenit2.getSelectedItem() + ":" + CmbDetik2.getSelectedItem(),
            AsalRuang.getText(), KdDokter.getText(), Petugas.getText(), RuangSelanjutnya.getText(), KdDokter1.getText(), Petugas1.getText(), Alasan.getSelectedItem().toString(), penunjang, Terapi.getText(), var.getkode()}) == true) {
            emptTeks();
            JOptionPane.showMessageDialog(null, "Berhasil simpan data transfer pasien..");
            TabRawat.setSelectedIndex(1);
            tampil();
        }
    }

    public void setNoRM(String norawat, String tglmasuk, String jam, String kd_bangsal, String status) {
        this.tglmasuk=tglmasuk;
        jammasuk=jam;
        TNoRw.setText(norawat);
        sttsTransfer=status;
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=?", TNoRM, norawat);
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", TPasien, TNoRM.getText());
        Sequel.cariIsi("select jk from pasien where no_rkm_medis=?", JK, TNoRM.getText());
        Sequel.cariIsi("select tgl_lahir from pasien where no_rkm_medis=?", TglLahir, TNoRM.getText());
        KdBangsal.setText(kd_bangsal);
        AsalRuang.setText(Sequel.cariIsi("select nm_bangsal FROM bangsal WHERE kd_bangsal=?", kd_bangsal));
        TanggalMasuk.setText(this.tglmasuk +" "+jammasuk);
        isRawat();
        getCPPT(norawat);
        tampilResep(norawat);
    }

    public void setDokter(String norwt,String status) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        if (status.equals("Ranap")) {
            jLabel60.setText("DPJP :");
            jLabel59.setText("DPJP :");
            KdDokter.setText(Sequel.cariIsi("select kd_dokter from dpjp_ranap where no_rawat=? and jenis_dpjp='Utama'", norwt));
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", NmDokter, KdDokter.getText());
            KdDokter1.setText(Sequel.cariIsi("select kd_dokter from dpjp_ranap where no_rawat=? and jenis_dpjp='Utama'", norwt));
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", NmDokter1, KdDokter1.getText());
        }
        if (status.equals("Ralan")) {            
            KdDokter.setText(Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?", norwt));
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", NmDokter, KdDokter.getText());
        }
    }

    public void tampilResep() {
        try {
            jml = 0;
            for (i = 0; i < tbResep.getRowCount(); i++) {
                if (tbResep.getValueAt(i, 0).toString().equals("true")) {
                    jml++;
                }
            }

            pilih = null;
            pilih = new boolean[jml];
            tanggal = null;
            tanggal = new String[jml];
            jam = null;
            jam = new String[jml];
            kodebarang = null;
            kodebarang = new String[jml];
            namabarang = null;
            namabarang = new String[jml];
            dosis = null;
            dosis = new String[jml];
            caraberi = null;
            caraberi = new String[jml];

            index = 0;
            for (i = 0; i < tbResep.getRowCount(); i++) {
                if (tbResep.getValueAt(i, 0).toString().equals("true")) {
                    pilih[index] = true;
                    tanggal[index] = tbResep.getValueAt(i, 1).toString();
                    jam[index] = tbResep.getValueAt(i, 2).toString();
                    kodebarang[index] = tbResep.getValueAt(i, 3).toString();
                    namabarang[index] = tbResep.getValueAt(i, 4).toString();
                    dosis[index] = tbResep.getValueAt(i, 5).toString();
                    caraberi[index] = tbResep.getValueAt(i, 6).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabModeResep);
            for (i = 0; i < jml; i++) {
                tabModeResep.addRow(new Object[]{pilih[i], tanggal[i], jam[i], kodebarang[i], namabarang[i], dosis[i], caraberi[i]});
            }
            
            if (TCari.getText().equals("")) {
                limit = " limit 20 ";
            } else {
                limit = "";
            }

            ps = koneksi.prepareStatement(
                    "SELECT detail_pemberian_obat.tgl_perawatan, detail_pemberian_obat.jam, detail_pemberian_obat.kode_brng, databarang.nama_brng,detail_pemberian_obat.no_rawat,detail_pemberian_obat.status FROM detail_pemberian_obat join databarang ON  detail_pemberian_obat.kode_brng=databarang.kode_brng "
                    + "WHERE detail_pemberian_obat.status='"+sttsTransfer+"' and detail_pemberian_obat.no_rawat like ? and databarang.nama_brng like ? order by detail_pemberian_obat.tgl_perawatan, detail_pemberian_obat.jam "+limit+"");
            try {
                ps.setString(1, "%" + TNoRw.getText().trim() + "%");
                ps.setString(2, "%" + TCari1.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeResep.addRow(new Object[]{false, rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), "", ""});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi 1 : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi 2 : " + e);
        }
    }

    public void tampilResep(String norawat) {
        Valid.tabelKosong(tabMode2);
        try {
            ps = koneksi.prepareStatement(
                    "SELECT transfer_pasien_detail.id, transfer_pasien_detail.tanggal, transfer_pasien_detail.jam, databarang.nama_brng, transfer_pasien_detail.dosis, transfer_pasien_detail.cara_pemberian "
                    + "FROM transfer_pasien_detail, databarang WHERE transfer_pasien_detail.kd_barang=databarang.kode_brng and transfer_pasien_detail.status='"+sttsTransfer+"' and transfer_pasien_detail.no_rawat=?");
            try {
                ps.setString(1, norawat);
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode2.addRow(new Object[]{rs.getString("id"), rs.getString("tanggal"), rs.getString("jam"), rs.getString("nama_brng"), rs.getString("dosis"), rs.getString("cara_pemberian")
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
    }

    private void insertPenunjang() {
        penunjang = "";
        if (chkRontgen.isSelected() == true) {
            penunjang = penunjang + "Rontgen;";
        }
        if (chkLab.isSelected() == true) {
            penunjang = penunjang + "Laboratorium;";
        }
        if (chkEkg.isSelected() == true) {
            penunjang = penunjang + "EKG;";
        }
        if (chkUsg.isSelected() == true) {
            penunjang = penunjang + "USG;";
        }
        if (chkEeg.isSelected() == true) {
            penunjang = penunjang + "EEG;";
        }
        if (chkLain.isSelected() == true) {
            penunjang = penunjang + "Lain-lain, " + TLainnya.getText() + ";";
        }
        if (penunjang.endsWith(";")) {
            penunjang = penunjang.substring(0, penunjang.length() - 1);
        }
    }

    public void isReset() {
        jml = tbResep.getRowCount();
        for (i = 0; i < jml; i++) {
            tbResep.setValueAt(false, i, 0);
        }
        Valid.tabelKosong(tabModeResep);
        tampilResep();
    }    
    
    public void setNoRm(String norwt,String tgl, String jam, String status) {
        jLabel59.setVisible(false);
        KdDokter.setVisible(false);
        NmDokter.setVisible(false);
        TNoRw.setText(norwt);
        sttsTransfer=status;
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=?", TNoRM, norwt);
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", TPasien, TNoRM.getText());
        Sequel.cariIsi("select jk from pasien where no_rkm_medis=?", JK, TNoRM.getText());
        Sequel.cariIsi("select tgl_lahir from pasien where no_rkm_medis=?", TglLahir, TNoRM.getText());
        tampilResep(norwt);
        try {
            ps = koneksi.prepareStatement("select bangsal.kd_bangsal, bangsal.nm_bangsal, kamar.kelas, kamar_inap.tgl_masuk, kamar_inap.jam_masuk from kamar_inap join kamar join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal "
                    + "where kamar_inap.no_rawat=? and tgl_keluar=? and jam_keluar=? and stts_pulang='Pindah Kamar' order by kamar_inap.tgl_keluar, kamar_inap.jam_keluar desc limit 1");
            ps2 = koneksi.prepareStatement("select bangsal.kd_bangsal, bangsal.nm_bangsal from kamar_inap join kamar join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal "
                    + "where kamar_inap.no_rawat=? and tgl_masuk=? and jam_masuk=? and stts_pulang != 'Pindah Kamar'");
            try {
                ps.setString(1, norwt);
                ps.setString(2, tgl);
                ps.setString(3, jam);
                ps2.setString(1, norwt);
                ps2.setString(2, tgl);
                ps2.setString(3, jam);
                rs = ps.executeQuery();
                rs2 = ps2.executeQuery();
                while (rs.next()) {
                    TanggalMasuk.setText(rs.getString(4) +" "+rs.getString(5));
                    KdBangsal.setText(rs.getString(1));
                    AsalRuang.setText(rs.getString(2));
                    kelas = rs.getString(3); 
                }
                if (rs2.next()) {
                    TanggalPindah.setText(tgl +" "+ jam);
                    KdBangsal1.setText(rs2.getString(1));
                    RuangSelanjutnya.setText(rs2.getString(2));
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
        } catch (SQLException e) {
            System.out.println(e);
        }
        getCPPT(norwt);
    }
    
    private void getCPPT(String no_rawat) {
        try {
            if (sttsTransfer.equals("Ranap")) {
                ps = koneksi.prepareStatement("SELECT suhu_tubuh, nadi, tensi, respirasi, alergi, spo2, gcs  FROM pemeriksaan_ranap WHERE CAST(CONCAT(tgl_perawatan,' ',jam_rawat) AS DATETIME) "
                        + "BETWEEN ? AND ? AND no_rawat=? ORDER BY tgl_perawatan DESC, jam_rawat DESC LIMIT 1");
                ps.setString(1, TanggalMasuk.getText());
                ps.setString(2, TanggalPindah.getText());
                ps.setString(3, no_rawat);
            } 
            if (sttsTransfer.equals("Ralan")) {
                ps = koneksi.prepareStatement("SELECT suhu_tubuh, nadi, tensi, respirasi, alergi,gcs  FROM pemeriksaan_ralan WHERE CAST(CONCAT(tgl_perawatan,' ',jam_rawat) AS DATETIME) "
                        + "BETWEEN ? AND ? AND no_rawat=? ORDER BY tgl_perawatan DESC, jam_rawat DESC LIMIT 1");
                ps.setString(1, TanggalMasuk.getText());
                ps.setString(2, TanggalPindah.getText());
                ps.setString(3, no_rawat);
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                Suhu.setText(rs.getString("suhu_tubuh"));
                Nadi.setText(rs.getString("nadi"));
                TD.setText(rs.getString("tensi"));
                RR.setText(rs.getString("respirasi"));
                TAlergi.setText(rs.getString("alergi"));
                if (sttsTransfer.equals("Ranap")) {
                    spo2.setText(rs.getString("spo2"));
                    gcs.setText(rs.getString("gcs"));
                    Diagnosa.setText("");
                    dxKeperawatan.setText("");
                    cmbNyeri.setText("");
                }
                if (sttsTransfer.equals("Ralan")) {
                    spo2.setText("");
                    gcs.setText(rs.getString("gcs"));
                    Diagnosa.setText(Sequel.cariIsi("SELECT GROUP_CONCAT(CONCAT(penyakit.kd_penyakit,' ',penyakit.nm_penyakit)) FROM diagnosa_pasien INNER JOIN penyakit ON diagnosa_pasien.kd_penyakit = penyakit.kd_penyakit WHERE diagnosa_pasien.no_rawat=? AND diagnosa_pasien.status = 'Ralan'", no_rawat));
                    dxKeperawatan.setText(Sequel.cariIsi("SELECT diagnosa_keperawatan FROM data_triase_igd WHERE no_rawat=?", no_rawat));
                    cmbNyeri.setText(Sequel.cariIsi("SELECT skala_nyeri FROM data_triase_igd WHERE no_rawat=?", no_rawat));
                }                                
            }
        } catch (SQLException e) {
            System.out.println("Notifikasi : " + e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("Notifikasi : " + ex);
            }
        }
    }
}
