/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * DlgPerawatan.java
 *
 * Created on May 23, 2010, 6:36:30 PM
 */
package permintaan;

import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;
import fungsi.WarnaTable;
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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public final class DlgPermintaanUTD extends javax.swing.JDialog {

    private DefaultTableModel tabMode, tabModeStok;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private DlgCariPetugas petugas = new DlgCariPetugas(null, false);
    private DlgCariDokter dokter = new DlgCariDokter(null, false);
    private PreparedStatement pspemeriksaan, psstok;
    private ResultSet rs, rs2;
    private boolean[] pilih;
    private String[] kode, nama, jumlah, jlh, namastok, gd, resus;
    private int jml = 0, i = 0, index = 0, jmlparsial = 0;
    private String kelas = "", pemeriksaan = "", kamar, namakamar, status = "";
    private double ttl = 0, item = 0;
    private String norawatibu = "", aktifkanparsial = "no";
    private final Properties prop = new Properties();
    private WarnaTable2 warna = new WarnaTable2();

    /**
     * Creates new form DlgPerawatan
     *
     * @param parent
     * @param modal
     */
    public DlgPermintaanUTD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode = new DefaultTableModel(null, new Object[]{
            "P", "Jumlah", "Kode Periksa", "Nama Pemeriksaan"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if ((colIndex == 0) || (colIndex == 1)) {
                    a = true;
                }
                return a;
            }

            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbPemeriksaan.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbPemeriksaan.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbPemeriksaan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 4; i++) {
            TableColumn column = tbPemeriksaan.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(45);
            } else if (i == 2) {
                column.setPreferredWidth(130);
            } else if (i == 3) {
                column.setPreferredWidth(480);
            }
        }

        warna.kolom = 1;
        tbPemeriksaan.setDefaultRenderer(Object.class, warna);

//        tbPemeriksaan.setDefaultRenderer(Object.class, new WarnaTable());
        tabModeStok = new DefaultTableModel(null, new Object[]{"Jml", "Nama Barang", "Gol. Darah", "Resus"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
        };
        tbMedis.setModel(tabModeStok);
        tbMedis.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbMedis.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 4; i++) {
            TableColumn column = tbMedis.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(35);
            } else if (i == 1) {
                column.setPreferredWidth(180);
            } else if (i == 2) {
                column.setPreferredWidth(65);
            } else if (i == 3) {
                column.setPreferredWidth(45);
            }
        }
        tbMedis.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte) 17).getKata(TNoRw));
        KodePerujuk.setDocument(new batasInput((byte) 20).getKata(KodePerujuk));
        if (koneksiDB.cariCepat().equals("aktif")) {
            TCariPeriksa.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCariPeriksa.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCariPeriksa.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCariPeriksa.getText().length() > 2) {
                        tampil();
                    }
                }
            });
        }

        ChkJln.setSelected(true);
        jam();

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
                    KodePerujuk.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                    NmPerujuk.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                    KodePerujuk.requestFocus();
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

        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            aktifkanparsial = prop.getProperty("AKTIFKANBILLINGPARSIAL");
        } catch (Exception ex) {
            aktifkanparsial = "no";
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

        Penjab = new widget.TextBox();
        Jk = new widget.TextBox();
        Umur = new widget.TextBox();
        Alamat = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnPrint = new widget.Button();
        jLabel10 = new widget.Label();
        jLabel12 = new widget.Label();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        FormInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        PanelInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel9 = new widget.Label();
        Tanggal = new widget.Tanggal();
        CmbJam = new widget.ComboBox();
        CmbMenit = new widget.ComboBox();
        CmbDetik = new widget.ComboBox();
        ChkJln = new widget.CekBox();
        KodePerujuk = new widget.TextBox();
        NmPerujuk = new widget.TextBox();
        btnDokter = new widget.Button();
        jLabel15 = new widget.Label();
        jLabel4 = new widget.Label();
        TNoPermintaan = new widget.TextBox();
        jLabel11 = new widget.Label();
        TKet = new widget.TextBox();
        jLabel18 = new widget.Label();
        CmbCariGd = new widget.ComboBox();
        jLabel17 = new widget.Label();
        CmbCariResus = new widget.ComboBox();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        Scroll2 = new widget.ScrollPane();
        tbPemeriksaan = new widget.Table();
        panelisi6 = new widget.panelisi();
        jPanel4 = new javax.swing.JPanel();
        Scroll1 = new widget.ScrollPane();
        tbMedis = new widget.Table();
        panelisi7 = new widget.panelisi();
        label13 = new widget.Label();
        TCariStok = new widget.TextBox();
        BtnCari2 = new widget.Button();
        BtnAll1 = new widget.Button();
        panelisi5 = new widget.panelisi();
        label10 = new widget.Label();
        TCariPeriksa = new widget.TextBox();
        btnCariPeriksa = new widget.Button();
        BtnAllPeriksa = new widget.Button();

        Penjab.setEditable(false);
        Penjab.setFocusTraversalPolicyProvider(true);
        Penjab.setName("Penjab"); // NOI18N
        Penjab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenjabKeyPressed(evt);
            }
        });

        Jk.setEditable(false);
        Jk.setFocusTraversalPolicyProvider(true);
        Jk.setName("Jk"); // NOI18N

        Umur.setEditable(false);
        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N

        Alamat.setEditable(false);
        Alamat.setHighlighter(null);
        Alamat.setName("Alamat"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Input Data Permintaan UTD ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

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

        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(103, 30));
        panelGlass8.add(jLabel10);

        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(103, 30));
        panelGlass8.add(jLabel12);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnCari.setMnemonic('C');
        BtnCari.setText("Cari");
        BtnCari.setToolTipText("Alt+C");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(100, 30));
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
        panelGlass8.add(BtnCari);

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

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setOpaque(false);
        FormInput.setPreferredSize(new java.awt.Dimension(560, 169));
        FormInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setSelected(true);
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        FormInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setPreferredSize(new java.awt.Dimension(560, 128));
        PanelInput.setLayout(null);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        PanelInput.add(jLabel3);
        jLabel3.setBounds(0, 12, 92, 23);

        TNoRw.setEditable(false);
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        PanelInput.add(TNoRw);
        TNoRw.setBounds(95, 12, 128, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        PanelInput.add(TNoRM);
        TNoRM.setBounds(225, 12, 105, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        PanelInput.add(TPasien);
        TPasien.setBounds(332, 12, 300, 23);

        jLabel9.setText("Dokter Perujuk :");
        jLabel9.setName("jLabel9"); // NOI18N
        PanelInput.add(jLabel9);
        jLabel9.setBounds(0, 42, 92, 23);

        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-12-2022" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
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
        PanelInput.add(Tanggal);
        Tanggal.setBounds(95, 72, 90, 23);

        CmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        CmbJam.setName("CmbJam"); // NOI18N
        PanelInput.add(CmbJam);
        CmbJam.setBounds(189, 72, 62, 23);

        CmbMenit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbMenit.setName("CmbMenit"); // NOI18N
        PanelInput.add(CmbMenit);
        CmbMenit.setBounds(254, 72, 62, 23);

        CmbDetik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbDetik.setName("CmbDetik"); // NOI18N
        PanelInput.add(CmbDetik);
        CmbDetik.setBounds(319, 72, 62, 23);

        ChkJln.setBorder(null);
        ChkJln.setSelected(true);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        PanelInput.add(ChkJln);
        ChkJln.setBounds(384, 72, 23, 23);

        KodePerujuk.setName("KodePerujuk"); // NOI18N
        KodePerujuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KodePerujukActionPerformed(evt);
            }
        });
        KodePerujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodePerujukKeyPressed(evt);
            }
        });
        PanelInput.add(KodePerujuk);
        KodePerujuk.setBounds(95, 42, 128, 23);

        NmPerujuk.setEditable(false);
        NmPerujuk.setHighlighter(null);
        NmPerujuk.setName("NmPerujuk"); // NOI18N
        PanelInput.add(NmPerujuk);
        NmPerujuk.setBounds(225, 42, 377, 23);

        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter.setMnemonic('4');
        btnDokter.setToolTipText("ALt+4");
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        PanelInput.add(btnDokter);
        btnDokter.setBounds(604, 42, 28, 23);

        jLabel15.setText("Tgl.Periksa :");
        jLabel15.setName("jLabel15"); // NOI18N
        PanelInput.add(jLabel15);
        jLabel15.setBounds(0, 72, 92, 23);

        jLabel4.setText("No.Permintaan :");
        jLabel4.setName("jLabel4"); // NOI18N
        PanelInput.add(jLabel4);
        jLabel4.setBounds(409, 72, 90, 23);

        TNoPermintaan.setEditable(false);
        TNoPermintaan.setHighlighter(null);
        TNoPermintaan.setName("TNoPermintaan"); // NOI18N
        TNoPermintaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TNoPermintaanActionPerformed(evt);
            }
        });
        TNoPermintaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoPermintaanKeyPressed(evt);
            }
        });
        PanelInput.add(TNoPermintaan);
        TNoPermintaan.setBounds(502, 72, 130, 23);

        jLabel11.setText("Keterangan :");
        jLabel11.setName("jLabel11"); // NOI18N
        PanelInput.add(jLabel11);
        jLabel11.setBounds(0, 102, 92, 23);

        TKet.setHighlighter(null);
        TKet.setName("TKet"); // NOI18N
        PanelInput.add(TKet);
        TKet.setBounds(95, 102, 310, 40);

        jLabel18.setText("G.D.:");
        jLabel18.setName("jLabel18"); // NOI18N
        jLabel18.setPreferredSize(new java.awt.Dimension(33, 23));
        PanelInput.add(jLabel18);
        jLabel18.setBounds(410, 102, 40, 23);

        CmbCariGd.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A", "AB", "B", "O" }));
        CmbCariGd.setName("CmbCariGd"); // NOI18N
        CmbCariGd.setPreferredSize(new java.awt.Dimension(65, 23));
        CmbCariGd.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbCariGdItemStateChanged(evt);
            }
        });
        CmbCariGd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CmbCariGdActionPerformed(evt);
            }
        });
        CmbCariGd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbCariGdKeyPressed(evt);
            }
        });
        PanelInput.add(CmbCariGd);
        CmbCariGd.setBounds(460, 102, 52, 23);

        jLabel17.setText("Resus:");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(33, 23));
        PanelInput.add(jLabel17);
        jLabel17.setBounds(520, 102, 40, 23);

        CmbCariResus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "(-)", "(+)" }));
        CmbCariResus.setName("CmbCariResus"); // NOI18N
        CmbCariResus.setPreferredSize(new java.awt.Dimension(65, 23));
        CmbCariResus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbCariResusItemStateChanged(evt);
            }
        });
        CmbCariResus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbCariResusKeyPressed(evt);
            }
        });
        PanelInput.add(CmbCariResus);
        CmbCariResus.setBounds(570, 102, 52, 23);

        FormInput.add(PanelInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 102));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 102));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 255, 235)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbPemeriksaan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
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
        });
        Scroll2.setViewportView(tbPemeriksaan);

        jPanel3.add(Scroll2, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        panelisi6.setBorder(null);
        panelisi6.setName("panelisi6"); // NOI18N
        panelisi6.setPreferredSize(new java.awt.Dimension(450, 77));
        panelisi6.setLayout(new java.awt.GridLayout(2, 0));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(237, 242, 232)), ".: Stok Darah", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(300, 102));
        jPanel4.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 255, 235)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);
        Scroll1.setPreferredSize(new java.awt.Dimension(652, 802));

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

        jPanel4.add(Scroll1, java.awt.BorderLayout.CENTER);

        panelisi7.setBorder(null);
        panelisi7.setName("panelisi7"); // NOI18N
        panelisi7.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label13.setText("Key Word :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi7.add(label13);

        TCariStok.setToolTipText("Alt+C");
        TCariStok.setName("TCariStok"); // NOI18N
        TCariStok.setPreferredSize(new java.awt.Dimension(290, 23));
        TCariStok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariStokKeyPressed(evt);
            }
        });
        panelisi7.add(TCariStok);

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
        panelisi7.add(BtnCari2);

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
        panelisi7.add(BtnAll1);

        jPanel4.add(panelisi7, java.awt.BorderLayout.PAGE_END);

        panelisi6.add(jPanel4);
        jPanel4.getAccessibleContext().setAccessibleName(".: Stok Darah ");
        jPanel4.getAccessibleContext().setAccessibleDescription("");

        jPanel1.add(panelisi6, java.awt.BorderLayout.EAST);

        panelisi5.setName("panelisi5"); // NOI18N
        panelisi5.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi5.add(label10);

        TCariPeriksa.setToolTipText("Alt+C");
        TCariPeriksa.setName("TCariPeriksa"); // NOI18N
        TCariPeriksa.setPreferredSize(new java.awt.Dimension(680, 23));
        TCariPeriksa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TCariPeriksaActionPerformed(evt);
            }
        });
        TCariPeriksa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariPeriksaKeyPressed(evt);
            }
        });
        panelisi5.add(TCariPeriksa);

        btnCariPeriksa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        btnCariPeriksa.setMnemonic('1');
        btnCariPeriksa.setToolTipText("Alt+1");
        btnCariPeriksa.setName("btnCariPeriksa"); // NOI18N
        btnCariPeriksa.setPreferredSize(new java.awt.Dimension(28, 23));
        btnCariPeriksa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariPeriksaActionPerformed(evt);
            }
        });
        btnCariPeriksa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCariPeriksaKeyPressed(evt);
            }
        });
        panelisi5.add(btnCariPeriksa);

        BtnAllPeriksa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllPeriksa.setMnemonic('2');
        BtnAllPeriksa.setToolTipText("Alt+2");
        BtnAllPeriksa.setName("BtnAllPeriksa"); // NOI18N
        BtnAllPeriksa.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllPeriksa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllPeriksaActionPerformed(evt);
            }
        });
        BtnAllPeriksa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllPeriksaKeyPressed(evt);
            }
        });
        panelisi5.add(BtnAllPeriksa);

        jPanel1.add(panelisi5, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dokter.dispose();
        petugas.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnCari, TCariPeriksa);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    DlgCariPermintaanUTD form = new DlgCariPermintaanUTD(null, false);
    form.isCek();
    form.setPasien(TNoRw.getText());
    form.setSize(this.getWidth(), this.getHeight());
    form.setLocationRelativeTo(this);
    form.setVisible(true);
    this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_ChkJlnActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void PenjabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenjabKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenjabKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt, KodePerujuk, TCariPeriksa);
    }//GEN-LAST:event_TanggalKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
        tampilStok();
    }//GEN-LAST:event_formWindowOpened

    private void TCariPeriksaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariPeriksaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnCariPeriksaActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            btnCariPeriksa.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            TNoPermintaan.requestFocus();
        }
    }//GEN-LAST:event_TCariPeriksaKeyPressed

    private void btnCariPeriksaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariPeriksaActionPerformed
        tampil();
    }//GEN-LAST:event_btnCariPeriksaActionPerformed

    private void btnCariPeriksaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCariPeriksaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            tampil();
        } else {
            Valid.pindah(evt, TCariPeriksa, BtnAllPeriksa);
        }
    }//GEN-LAST:event_btnCariPeriksaKeyPressed

    private void BtnAllPeriksaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllPeriksaActionPerformed
        TCariPeriksa.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllPeriksaActionPerformed

    private void BtnAllPeriksaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllPeriksaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnAllPeriksaActionPerformed(null);
        } else {
            Valid.pindah(evt, btnCariPeriksa, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllPeriksaKeyPressed

    private void tbPemeriksaanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPemeriksaanMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                // getData2();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbPemeriksaanMouseClicked

    private void tbPemeriksaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPemeriksaanKeyPressed
        if (tbPemeriksaan.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                try {
                    int row = tbPemeriksaan.getSelectedColumn();
                    if ((row != 0) || (row != 20)) {
                        if (tbPemeriksaan.getSelectedRow() > -1) {
                            tbPemeriksaan.setValueAt(true, tbPemeriksaan.getSelectedRow(), 0);
                        }
                        TCariPeriksa.setText("");
                        TCariPeriksa.requestFocus();
                    }
                    //getData2();
                } catch (java.lang.NullPointerException e) {
                }
            } else if ((evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    // getData2();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbPemeriksaanKeyPressed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, TCariPeriksa, BtnBatal);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        jml = 0;
        for (i = 0; i < tbPemeriksaan.getRowCount(); i++) {
            if (tbPemeriksaan.getValueAt(i, 0).toString().equals("true")) {
                jml++;
            }
        }
        if (TNoRw.getText().equals("") || TNoRM.getText().equals("") || TPasien.getText().equals("")) {
            Valid.textKosong(TNoRw, "Pasien");
        } else if (KodePerujuk.getText().equals("") || NmPerujuk.getText().equals("")) {
            Valid.textKosong(KodePerujuk, "Dokter Perujuk");
        } else if (tabMode.getRowCount() == 0) {
            Valid.textKosong(TCariPeriksa, "Data Permintaan");
        } else if (jml == 0) {
            Valid.textKosong(TCariPeriksa, "Data Permintaan");
        } else {
            jmlparsial = 0;
            if (aktifkanparsial.equals("yes")) {
                jmlparsial = Sequel.cariInteger("select count(kd_pj) from set_input_parsial where kd_pj=?", Penjab.getText());
            }
            if (jmlparsial > 0) {
                simpan();
            } else {
                if (Sequel.cariRegistrasi(TNoRw.getText()) > 0) {
                    JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                    TCariPeriksa.requestFocus();
                } else {
                    simpan();
                }
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnBatalActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnSimpan, BtnPrint);
        }
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCariPeriksa, BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void KodePerujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodePerujukKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", NmPerujuk, KodePerujuk.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnDokterActionPerformed(null);
        } else {
            Valid.pindah(evt, TCariPeriksa, Tanggal);
        }
    }//GEN-LAST:event_KodePerujukKeyPressed

    private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokterActionPerformed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        jml = 0;
        for (i = 0; i < tbPemeriksaan.getRowCount(); i++) {
            if (tbPemeriksaan.getValueAt(i, 0).toString().equals("true")) {
                jml++;
            }
        }
        if (TNoRw.getText().equals("") || TNoRM.getText().equals("") || TPasien.getText().equals("")) {
            Valid.textKosong(TNoRw, "Pasien");
        } else if (KodePerujuk.getText().equals("") || NmPerujuk.getText().equals("")) {
            Valid.textKosong(KodePerujuk, "Dokter Pengirim");
        } else if (tabMode.getRowCount() == 0) {
            Valid.textKosong(TCariPeriksa, "Data Permintaan");
        } else if (jml == 0) {
            Valid.textKosong(TCariPeriksa, "Data Permintaan");
        } else {

            Sequel.queryu("delete from temporary_permintaan_utd");
            for (i = 0; i < tbPemeriksaan.getRowCount(); i++) {
                if (tbPemeriksaan.getValueAt(i, 0).toString().equals("true")) {
                    Sequel.menyimpan("temporary_permintaan_utd", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", 38, new String[]{
                        "0", tbPemeriksaan.getValueAt(i, 2).toString(),
                        tbPemeriksaan.getValueAt(i, 3).toString(), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
                    });
                }
            }

            Map<String, Object> param = new HashMap<>();
            param.put("noperiksa", TNoPermintaan.getText());
            param.put("norm", TNoRM.getText());
            param.put("pekerjaan", Sequel.cariIsi("select pekerjaan from pasien where no_rkm_medis=?", TNoRM.getText()));
            param.put("noktp", Sequel.cariIsi("select no_ktp from pasien where no_rkm_medis=?", TNoRM.getText()));
            param.put("namapasien", TPasien.getText());
            param.put("jkel", Jk.getText());
            param.put("umur", Umur.getText());
            param.put("lahir", Sequel.cariIsi("select DATE_FORMAT(tgl_lahir,'%d-%m-%Y') from pasien where no_rkm_medis=? ", TNoRM.getText()));
            param.put("pengirim", NmPerujuk.getText());
            param.put("tanggal", Tanggal.getSelectedItem());
            param.put("alamat", Alamat.getText());
            param.put("kamar", kamar);
            param.put("namakamar", namakamar);
            param.put("jam", CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem());
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));

            Valid.MyReport("rptPermintaanUtd.jrxml", "report", "::[ Permintaan UTD ]::",
                    "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14, temp14, temp15, temp16 from temporary_permintaan_utd order by no asc", param);
            ChkJln.setSelected(false);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnBatal, BtnCari);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariPeriksaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TCariPeriksaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TCariPeriksaActionPerformed

    private void TNoPermintaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoPermintaanKeyPressed
        //Valid.pindah(evt,TNoReg,DTPReg);
    }//GEN-LAST:event_TNoPermintaanKeyPressed

    private void TNoPermintaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TNoPermintaanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoPermintaanActionPerformed

    private void KodePerujukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KodePerujukActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KodePerujukActionPerformed

    private void TanggalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TanggalItemStateChanged
        autoNomor();
    }//GEN-LAST:event_TanggalItemStateChanged

    private void tbMedisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMedisMouseClicked

    }//GEN-LAST:event_tbMedisMouseClicked

    private void tbMedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbMedisKeyPressed
        if (tbMedis.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                try {
                    i = tbMedis.getSelectedColumn();
                    if (i == 1) {
                        TCariStok.setText("");
                        TCariStok.requestFocus();
                    }
                } catch (java.lang.NullPointerException e) {
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
                TCariStok.setText("");
                TCariStok.requestFocus();
            }
        }
    }//GEN-LAST:event_tbMedisKeyPressed

    private void TCariStokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariStokKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCari2ActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            TCariStok.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            // KodePetugasUSaring.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            tbMedis.requestFocus();
        }
    }//GEN-LAST:event_TCariStokKeyPressed

    private void BtnCari2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari2ActionPerformed
        tampilStok();
    }//GEN-LAST:event_BtnCari2ActionPerformed

    private void BtnCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCari2KeyPressed

    private void BtnAll1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAll1ActionPerformed
        TCariStok.setText("");
        tampilStok();
    }//GEN-LAST:event_BtnAll1ActionPerformed

    private void BtnAll1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAll1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnAll1KeyPressed

    private void CmbCariGdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbCariGdKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbCariGdKeyPressed

    private void CmbCariGdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CmbCariGdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbCariGdActionPerformed

    private void CmbCariGdItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CmbCariGdItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbCariGdItemStateChanged

    private void CmbCariResusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbCariResusKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_CmbCariResusKeyPressed

    private void CmbCariResusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CmbCariResusItemStateChanged
        tampil();
    }//GEN-LAST:event_CmbCariResusItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPermintaanUTD dialog = new DlgPermintaanUTD(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAll1;
    private widget.Button BtnAllPeriksa;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCari2;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkJln;
    private widget.ComboBox CmbCariGd;
    private widget.ComboBox CmbCariResus;
    private widget.ComboBox CmbDetik;
    private widget.ComboBox CmbJam;
    private widget.ComboBox CmbMenit;
    private javax.swing.JPanel FormInput;
    private widget.TextBox Jk;
    private widget.TextBox KodePerujuk;
    private widget.TextBox NmPerujuk;
    private widget.PanelBiasa PanelInput;
    private widget.TextBox Penjab;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.TextBox TCariPeriksa;
    private widget.TextBox TCariStok;
    private widget.TextBox TKet;
    private widget.TextBox TNoPermintaan;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox Umur;
    private widget.Button btnCariPeriksa;
    private widget.Button btnDokter;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel15;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private widget.Label label10;
    private widget.Label label13;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelisi5;
    private widget.panelisi panelisi6;
    private widget.panelisi panelisi7;
    private widget.Table tbMedis;
    private widget.Table tbPemeriksaan;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        try {
            jml = 0;
            for (i = 0; i < tbPemeriksaan.getRowCount(); i++) {
                if (tbPemeriksaan.getValueAt(i, 0).toString().equals("true")) {
                    jml++;
                }
            }

            pilih = null;
            pilih = new boolean[jml];
            jumlah = null;
            jumlah = new String[jml];
            kode = null;
            kode = new String[jml];
            nama = null;
            nama = new String[jml];

            index = 0;
            for (i = 0; i < tbPemeriksaan.getRowCount(); i++) {
                if (tbPemeriksaan.getValueAt(i, 0).toString().equals("true")) {
                    pilih[index] = true;
                    jumlah[index] = tbPemeriksaan.getValueAt(i, 1).toString();
                    kode[index] = tbPemeriksaan.getValueAt(i, 2).toString();
                    nama[index] = tbPemeriksaan.getValueAt(i, 3).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabMode);
            for (i = 0; i < jml; i++) {
                tabMode.addRow(new Object[]{pilih[i], jumlah[i], kode[i], nama[i]});
            }

            pspemeriksaan = koneksi.prepareStatement(
                    "select jns_perawatan_inap.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,penjab.png_jawab,jns_perawatan_inap.total_byrdr "
                    + "from jns_perawatan_inap inner join penjab on penjab.kd_pj=jns_perawatan_inap.kd_pj where jns_perawatan_inap.kd_jenis_prw "
                    + "in ('J001000','J001001','J001002','J001003','J001004','J001005','J001006') and jns_perawatan_inap.status='1' and "
                    + "(jns_perawatan_inap.kd_jenis_prw like ? or jns_perawatan_inap.nm_perawatan like ?) order by jns_perawatan_inap.kd_jenis_prw ");
            try {
                pspemeriksaan.setString(1, "%" + TCariPeriksa.getText().trim() + "%");
                pspemeriksaan.setString(2, "%" + TCariPeriksa.getText().trim() + "%");
                rs = pspemeriksaan.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new Object[]{false, "", rs.getString(1), rs.getString(2)});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi 1 : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (pspemeriksaan != null) {
                    pspemeriksaan.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi 2 : " + e);
        }
    }

    public void isReset() {
        jml = tbPemeriksaan.getRowCount();
        for (i = 0; i < jml; i++) {
            tbPemeriksaan.setValueAt(false, i, 0);
        }
        Valid.tabelKosong(tabMode);
        tampil();
    }

    public void emptTeks() {
        KodePerujuk.setText("");
        NmPerujuk.setText("");
        TCariPeriksa.setText("");
        TKet.setText("");
        autoNomor();
    }

    private void isRawat() {
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ", TNoRM, TNoRw.getText());
        Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=? ", Penjab, TNoRw.getText());
        Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=? ", KodePerujuk, TNoRw.getText());
        Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=? ", NmPerujuk, KodePerujuk.getText());

        norawatibu = Sequel.cariIsi("select no_rawat from ranap_gabung where no_rawat2=?", TNoRw.getText());
        if (!norawatibu.equals("")) {
            kamar = Sequel.cariIsi("select ifnull(kd_kamar,'') from kamar_inap where no_rawat=? order by tgl_masuk desc limit 1", norawatibu);
        } else {
            kamar = Sequel.cariIsi("select ifnull(kd_kamar,'') from kamar_inap where no_rawat=? order by tgl_masuk desc limit 1", TNoRw.getText());
        }
        if (!kamar.equals("")) {
            namakamar = kamar + ", " + Sequel.cariIsi("select nm_bangsal from bangsal inner join kamar on bangsal.kd_bangsal=kamar.kd_bangsal "
                    + " where kamar.kd_kamar=? ", kamar);
            kamar = "Kamar";
        } else if (kamar.equals("")) {
            kamar = "Poli";
            namakamar = Sequel.cariIsi("select nm_poli from poliklinik inner join reg_periksa on poliklinik.kd_poli=reg_periksa.kd_poli "
                    + "where reg_periksa.no_rawat=?", TNoRw.getText());
        }

        if (status.equals("Ranap")) {
            if (!norawatibu.equals("")) {
                kelas = Sequel.cariIsi(
                        "select kamar.kelas from kamar inner join kamar_inap "
                        + "on kamar.kd_kamar=kamar_inap.kd_kamar where no_rawat=? "
                        + "and stts_pulang='-' order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1", norawatibu);
            } else {
                kelas = Sequel.cariIsi(
                        "select kamar.kelas from kamar inner join kamar_inap "
                        + "on kamar.kd_kamar=kamar_inap.kd_kamar where no_rawat=? "
                        + "and stts_pulang='-' order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1", TNoRw.getText());
            }
        } else if (status.equals("Ralan")) {
            kelas = "Rawat Jalan";
        }
    }

    private void isPsien() {
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ", TPasien, TNoRM.getText());
        Sequel.cariIsi("select jk from pasien where no_rkm_medis=? ", Jk, TNoRM.getText());
        Sequel.cariIsi("select umur from pasien where no_rkm_medis=?", Umur, TNoRM.getText());
        Sequel.cariIsi("select concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat from pasien inner join kelurahan inner join kecamatan inner join kabupaten on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where no_rkm_medis=? ", Alamat, TNoRM.getText());
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
                    nilai_jam = CmbJam.getSelectedIndex();
                    nilai_menit = CmbMenit.getSelectedIndex();
                    nilai_detik = CmbDetik.getSelectedIndex();
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
                CmbJam.setSelectedItem(jam);
                CmbMenit.setSelectedItem(menit);
                CmbDetik.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }

    public void setNoRm(String norwt, String posisi) {
        TNoRw.setText(norwt);
        this.status = posisi;
        isRawat();
        isPsien();
        isReset();
    }

    public void setNoRm(String norwt, String posisi, String kddokter, String nmdokter) {
        TNoRw.setText(norwt);
        this.status = posisi;
        isRawat();
        isPsien();
        isReset();
        KodePerujuk.setText(kddokter);
        NmPerujuk.setText(nmdokter);
    }

    public void isCek() {
        BtnSimpan.setEnabled(var.getutd_penyerahan_darah());
        BtnPrint.setEnabled(var.getutd_penyerahan_darah());
    }

    private void isForm() {
        if (ChkInput.isSelected() == true) {
            ChkInput.setVisible(false);
            FormInput.setPreferredSize(new Dimension(WIDTH, 129));
            PanelInput.setVisible(true);
            ChkInput.setVisible(true);
        } else if (ChkInput.isSelected() == false) {
            ChkInput.setVisible(false);
            FormInput.setPreferredSize(new Dimension(WIDTH, 20));
            PanelInput.setVisible(false);
            ChkInput.setVisible(true);
        }
    }

    public void setDokterPerujuk(String kodeperujuk, String namaperujuk) {
        KodePerujuk.setText(kodeperujuk);
        NmPerujuk.setText(namaperujuk);
    }

    private void autoNomor() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(noorder,4),signed)),0) from permintaan_utd where tgl_permintaan='" + Valid.SetTgl(Tanggal.getSelectedItem() + "") + "' ", "PU" + Valid.SetTgl(Tanggal.getSelectedItem() + "").replaceAll("-", ""), 4, TNoPermintaan);
    }

    private void simpan() {
        int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, udah bener belum data yang mau disimpan..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            ChkJln.setSelected(false);
            try {
                koneksi.setAutoCommit(false);
                //autoNomor();
                jml = tbPemeriksaan.getRowCount();
                for (i = 0; i < jml; i++) {
                    if (Valid.SetAngka(tbPemeriksaan.getValueAt(i, 1).toString()) > 0) {
                        if (Sequel.menyimpantf2("permintaan_utd", "?,?,?,?,?,?,?,?,?", "No.Permintaan", 9, new String[]{
                            TNoPermintaan.getText(), TNoRw.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""),
                            CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem(),
                            KodePerujuk.getText(), tbPemeriksaan.getValueAt(i, 1).toString(), CmbCariGd.getSelectedItem().toString(),
                            CmbCariResus.getSelectedItem().toString(), status.replaceAll("R", "r")
                        }) == true) {
                            for (i = 0; i < tbPemeriksaan.getRowCount(); i++) {
                                if (tbPemeriksaan.getValueAt(i, 0).toString().equals("true")) {
                                    Sequel.menyimpan2("permintaan_pemeriksaan_utd", "?,?,?", "pemeriksaan lab", 3, new String[]{
                                        TNoPermintaan.getText(), tbPemeriksaan.getValueAt(i, 2).toString(), "Belum"
                                    });
                                }
                            }
                            isReset();
                            emptTeks();
                        }
                    }
                }
                koneksi.setAutoCommit(true);
                JOptionPane.showMessageDialog(null, "Proses simpan selesai...!");
            } catch (Exception e) {
                System.out.println(e);
            }
            ChkJln.setSelected(true);
        }
    }

    private void tampilStok() {
        try {
            jml = 0;
            for (i = 0; i < tbPemeriksaan.getRowCount(); i++) {
                if (tbPemeriksaan.getValueAt(i, 0).toString().equals("true")) {
                    jml++;
                }
            }

            jlh = null;
            jlh = new String[jml];
            namastok = null;
            namastok = new String[jml];
            gd = null;
            gd = new String[jml];
            resus = null;
            resus = new String[jml];

            index = 0;
            for (i = 0; i < tbPemeriksaan.getRowCount(); i++) {
                if (tbPemeriksaan.getValueAt(i, 0).toString().equals("true")) {
                    jlh[index] = tbPemeriksaan.getValueAt(i, 1).toString();
                    namastok[index] = tbPemeriksaan.getValueAt(i, 2).toString();
                    gd[index] = tbPemeriksaan.getValueAt(i, 3).toString();
                    resus[index] = tbPemeriksaan.getValueAt(i, 4).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabModeStok);
            for (i = 0; i < jml; i++) {
                tabModeStok.addRow(new Object[]{jlh[i], namastok[i], gd[i], resus[i]});
            }

            psstok = koneksi.prepareStatement(
                    "select count(utd_stok_darah.golongan_darah) as jlh, utd_komponen_darah.nama, utd_stok_darah.golongan_darah, "
                    + "utd_stok_darah.resus FROM `utd_stok_darah` inner join utd_komponen_darah on utd_stok_darah.kode_komponen=utd_komponen_darah.kode "
                    + "where utd_stok_darah.status='Ada' and (utd_komponen_darah.nama like ? or utd_stok_darah.golongan_darah like ?) "
                    + "group by utd_stok_darah.kode_komponen, utd_stok_darah.golongan_darah, utd_stok_darah.resus");
            try {
                psstok.setString(1, "%" + TCariStok.getText().trim() + "%");
                psstok.setString(2, "%" + TCariStok.getText().trim() + "%");
                rs2 = psstok.executeQuery();
                while (rs2.next()) {
                    tabModeStok.addRow(new Object[]{rs2.getString(1), rs2.getString(2), rs2.getString(3), rs2.getString(4)});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi 1 : " + e);
            } finally {
                if (rs2 != null) {
                    rs2.close();
                }
                if (psstok != null) {
                    psstok.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi 2 : " + e);
        }
    }

}
