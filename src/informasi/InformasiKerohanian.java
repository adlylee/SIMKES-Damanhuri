package informasi;

import bridging.BridgingWA;
import permintaan.*;
import fungsi.BackgroundMusic;
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
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;
import simrskhanza.DlgCariBangsal;

public class InformasiKerohanian extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private int i;
    private PreparedStatement ps, ps2;
    private ResultSet rs, rs2;
    private DlgCariPetugas petugas = new DlgCariPetugas(null, false);
    private DlgCariBangsal ruang = new DlgCariBangsal(null, false);
    private String norm = "", NoRawat = "", NoPermintaan = "", Perujuk = "", Petugas = "", TglPermintaan = "", Kamar = "", JamPermintaan = "", Ket = "", namapetugas = "";
    private BridgingWA kirimwa = new BridgingWA();

    /**
     * Creates new form DlgProgramStudi
     *
     * @param parent
     * @param modal
     */
    public InformasiKerohanian(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        WindowAmbilPetugas.setSize(550, 200);
        tabMode = new DefaultTableModel(null, new Object[]{
            "P", "No.Permintaan", "No.Rawat", "Pasien", "Kamar", "Tgl.Permintaan", "Perujuk", "Petugas", "Keterangan"}) {
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
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbKerohanian.setModel(tabMode);

        tbKerohanian.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbKerohanian.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = tbKerohanian.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(100);
            } else if (i == 2) {
                column.setPreferredWidth(105);
            } else if (i == 3) {
                column.setPreferredWidth(300);
            } else if (i == 4) {
                column.setPreferredWidth(165);
            } else if (i == 5) {
                column.setPreferredWidth(80);
            } else if (i == 6) {
                column.setPreferredWidth(150);
            } else if (i == 7) {
                column.setPreferredWidth(180);
            } else if (i == 8) {
                column.setPreferredWidth(180);
            }
        }
        tbKerohanian.setDefaultRenderer(Object.class, new WarnaTable());

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

        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("InformasiKerohanian")) {
                    if (petugas.getTable().getSelectedRow() != -1) {
                        NmPtg1.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                        NmPtg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                        NmPtg.requestFocus();
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

        ruang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (ruang.getTable().getSelectedRow() != -1) {
                    CrKamar.setText(ruang.getTable().getValueAt(ruang.getTable().getSelectedRow(), 1).toString());
                    CrKamar.requestFocus();
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

//        try {
//            prop.loadFromXML(new FileInputStream("setting/database.xml"));
//            alarm=prop.getProperty("ALARMRADIOLOGI");
//            formalarm=prop.getProperty("FORMALARMRADIOLOGI");
//        } catch (Exception ex) {
//            alarm="no";
//            formalarm="ralan + ranap";
//        }
//        
//        if(alarm.equals("yes")){
//        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Kd2 = new widget.TextBox();
        noPermintaan = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCetakKerohanian = new javax.swing.JMenuItem();
        WindowAmbilPetugas = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        BtnCloseIn4 = new widget.Button();
        BtnSimpan4 = new widget.Button();
        jLabel26 = new widget.Label();
        jLabel9 = new widget.Label();
        btnPetugas = new widget.Button();
        NmPtg = new widget.TextBox();
        scrollPane3 = new widget.ScrollPane();
        TKeterangan = new widget.TextArea();
        NmPtg1 = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        jPanel2 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        jLabel16 = new widget.Label();
        CrKamar = new widget.TextBox();
        BtnSeek4 = new widget.Button();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        panelisi1 = new widget.panelisi();
        BtnHapus = new widget.Button();
        BtnPetugas = new widget.Button();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        BtnKirimWA = new widget.Button();
        scrollPane1 = new widget.ScrollPane();
        tbKerohanian = new widget.Table();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        noPermintaan.setName("noPermintaan"); // NOI18N
        noPermintaan.setPreferredSize(new java.awt.Dimension(207, 23));

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCetakKerohanian.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakKerohanian.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakKerohanian.setForeground(new java.awt.Color(70, 70, 70));
        MnCetakKerohanian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakKerohanian.setText("Cetak Permintaan Kerohanian");
        MnCetakKerohanian.setName("MnCetakKerohanian"); // NOI18N
        MnCetakKerohanian.setPreferredSize(new java.awt.Dimension(200, 28));
        MnCetakKerohanian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakKerohanianActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakKerohanian);

        WindowAmbilPetugas.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowAmbilPetugas.setName("WindowAmbilPetugas"); // NOI18N
        WindowAmbilPetugas.setUndecorated(true);
        WindowAmbilPetugas.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Petugas ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
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
        BtnCloseIn4.setBounds(430, 142, 100, 30);

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
        BtnSimpan4.setBounds(330, 142, 100, 30);

        jLabel26.setText("Petugas :");
        jLabel26.setName("jLabel26"); // NOI18N
        internalFrame5.add(jLabel26);
        jLabel26.setBounds(6, 32, 100, 23);

        jLabel9.setText("Keterangan :");
        jLabel9.setName("jLabel9"); // NOI18N
        internalFrame5.add(jLabel9);
        jLabel9.setBounds(6, 72, 100, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('4');
        btnPetugas.setToolTipText("ALt+4");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        internalFrame5.add(btnPetugas);
        btnPetugas.setBounds(488, 32, 28, 23);

        NmPtg.setEditable(false);
        NmPtg.setHighlighter(null);
        NmPtg.setName("NmPtg"); // NOI18N
        internalFrame5.add(NmPtg);
        NmPtg.setBounds(217, 32, 270, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        TKeterangan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TKeterangan.setColumns(20);
        TKeterangan.setRows(5);
        TKeterangan.setName("TKeterangan"); // NOI18N
        scrollPane3.setViewportView(TKeterangan);

        internalFrame5.add(scrollPane3);
        scrollPane3.setBounds(110, 75, 380, 60);

        NmPtg1.setEditable(false);
        NmPtg1.setHighlighter(null);
        NmPtg1.setName("NmPtg1"); // NOI18N
        internalFrame5.add(NmPtg1);
        NmPtg1.setBounds(110, 32, 106, 23);

        WindowAmbilPetugas.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowDeactivated(java.awt.event.WindowEvent evt) {
                formWindowDeactivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Permintaan Kerohanian ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 9));

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass8.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelGlass8.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(25, 23));
        panelGlass8.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelGlass8.add(Tgl2);

        jLabel16.setText("Kamar :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass8.add(jLabel16);

        CrKamar.setEditable(false);
        CrKamar.setName("CrKamar"); // NOI18N
        CrKamar.setPreferredSize(new java.awt.Dimension(200, 23));
        CrKamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CrKamarActionPerformed(evt);
            }
        });
        panelGlass8.add(CrKamar);

        BtnSeek4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek4.setMnemonic('5');
        BtnSeek4.setToolTipText("ALt+5");
        BtnSeek4.setName("BtnSeek4"); // NOI18N
        BtnSeek4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek4ActionPerformed(evt);
            }
        });
        panelGlass8.add(BtnSeek4);

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass8.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(200, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass8.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('5');
        BtnCari.setToolTipText("Alt+5");
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
        panelGlass8.add(BtnCari);

        jPanel2.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelisi1.add(BtnHapus);

        BtnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/file-edit-16x16.png"))); // NOI18N
        BtnPetugas.setMnemonic('P');
        BtnPetugas.setText("Petugas");
        BtnPetugas.setToolTipText("Alt+P");
        BtnPetugas.setName("BtnPetugas"); // NOI18N
        BtnPetugas.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugasActionPerformed(evt);
            }
        });
        BtnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPetugasKeyPressed(evt);
            }
        });
        panelisi1.add(BtnPetugas);

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
        panelisi1.add(BtnAll);

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
        panelisi1.add(BtnPrint);

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi1.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(73, 23));
        panelisi1.add(LCount);

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
        panelisi1.add(BtnKeluar);

        BtnKirimWA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/edit_f2.png"))); // NOI18N
        BtnKirimWA.setMnemonic('G');
        BtnKirimWA.setText("Kirim WA");
        BtnKirimWA.setToolTipText("Alt+G");
        BtnKirimWA.setName("BtnKirimWA"); // NOI18N
        BtnKirimWA.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKirimWA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKirimWAActionPerformed(evt);
            }
        });
        BtnKirimWA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKirimWAKeyPressed(evt);
            }
        });
        panelisi1.add(BtnKirimWA);

        jPanel2.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);
        scrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                scrollPane1MouseReleased(evt);
            }
        });

        tbKerohanian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbKerohanian.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbKerohanian.setName("tbKerohanian"); // NOI18N
        tbKerohanian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKerohanianMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbKerohanianMouseReleased(evt);
            }
        });
        tbKerohanian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKerohanianKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbKerohanian);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt, BtnKeluar, Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1, TCari);
    }//GEN-LAST:event_Tgl2KeyPressed

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
        NmPtg.setText("");
        NmPtg1.setText("");        
        CrKamar.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnAllActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        } else if (tabMode.getRowCount() != 0) {

            Sequel.queryu("delete from temporary_permintaan_kerohanian");
            int row = tabMode.getRowCount();
            for (int i = 0; i < row; i++) {
                Sequel.menyimpan("temporary_permintaan_kerohanian", "'0','"
                        + tabMode.getValueAt(i, 1).toString() + "','"
                        + tabMode.getValueAt(i, 2).toString() + "','"
                        + tabMode.getValueAt(i, 3).toString() + "','"
                        + tabMode.getValueAt(i, 4).toString() + "','"
                        + tabMode.getValueAt(i, 5).toString() + "','"
                        + tabMode.getValueAt(i, 6).toString() + "','"
                        + tabMode.getValueAt(i, 7).toString() + "','"
                        + tabMode.getValueAt(i, 8).toString() + "','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''", "Kerohanian");
            }
            Sequel.menyimpan("temporary_permintaan_kerohanian", "'0','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''", "Kerohanian");

            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptLapPermintaankerohanian.jrxml", "report", "::[ Data Permintaan Kerohanian ]::",
                    "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14, temp14, temp15, temp16 from temporary_permintaan_kerohanian order by no asc", param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnAll, BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        WindowAmbilPetugas.dispose();
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            WindowAmbilPetugas.dispose();
            dispose();
        } else {
            Valid.pindah(evt, BtnPrint, BtnHapus);
        }
    }//GEN-LAST:event_BtnKeluarKeyPressed

private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
    if (tbKerohanian.getSelectedRow() != -1) {
        if (tbKerohanian.getValueAt(tbKerohanian.getSelectedRow(), 1).toString().trim().equals("")) {
            Valid.textKosong(TCari, "No.Permintaan");
        } else {
//            if (Sequel.cariInteger("select count(noorder) from permintaan_pemeriksaan_kerohanian where noorder=?", tbKerohanian.getValueAt(tbKerohanian.getSelectedRow(), 1).toString()) > 0) {
//                JOptionPane.showMessageDialog(null, "Maaf, Tidak boleh dihapus karena sudah ada tindakan yang sudah dibayar.\nSilahkan hubungi kasir...!!!!");
//            } else {
                Sequel.meghapus("permintaan_kerohanian", "noorder", tbKerohanian.getValueAt(tbKerohanian.getSelectedRow(), 1).toString());
                tampil();
//        }
        }
    } else {
        JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data permintaan...!!!!");
        TCari.requestFocus();
    }
 }//GEN-LAST:event_BtnHapusActionPerformed

private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
        BtnHapusActionPerformed(null);
    } else {
        Valid.pindah(evt, TCari, BtnAll);
    }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
        isCek();
    }//GEN-LAST:event_formWindowOpened

    private void BtnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasActionPerformed
        if (tbKerohanian.getSelectedRow() != -1) {
            if (tbKerohanian.getValueAt(tbKerohanian.getSelectedRow(), 1).toString().trim().equals("")) {
                Valid.textKosong(TCari, "No.Permintaan");
            } else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                WindowAmbilPetugas.setLocationRelativeTo(internalFrame1);
                WindowAmbilPetugas.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data permintaan...!!!!");
            TCari.requestFocus();
        }
    }//GEN-LAST:event_BtnPetugasActionPerformed

    private void BtnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPetugasKeyPressed

    private void BtnCloseIn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn4ActionPerformed
        WindowAmbilPetugas.dispose();
    }//GEN-LAST:event_BtnCloseIn4ActionPerformed

    private void BtnSimpan4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan4ActionPerformed
        if (tbKerohanian.getSelectedRow() != -1) {
            if (tbKerohanian.getValueAt(tbKerohanian.getSelectedRow(), 1).toString().trim().equals("")) {
                Valid.textKosong(NmPtg, "No.Permintaan");
            } else {
                Sequel.mengedit("permintaan_kerohanian", "noorder=?", "petugas=?,keterangan=?", 3, new String[]{
                    NmPtg1.getText(), TKeterangan.getText(), tbKerohanian.getValueAt(tbKerohanian.getSelectedRow(), 1).toString()
                });
                TeksKosong();
                tampil();
                WindowAmbilPetugas.dispose();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data permintaan...!!!!");
            TCari.requestFocus();
        }
    }//GEN-LAST:event_BtnSimpan4ActionPerformed

    private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
        ruang.isCek();
        ruang.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        ruang.setLocationRelativeTo(internalFrame1);
        ruang.setVisible(true);
    }//GEN-LAST:event_BtnSeek4ActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        tampil();
    }//GEN-LAST:event_formWindowActivated

    private void formWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeactivated
    }//GEN-LAST:event_formWindowDeactivated

    private void tbKerohanianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKerohanianMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbKerohanianMouseClicked

    private void tbKerohanianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKerohanianKeyPressed
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbKerohanianKeyPressed

    private void CrKamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CrKamarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CrKamarActionPerformed

    private void MnCetakKerohanianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakKerohanianActionPerformed
        if (tbKerohanian.getSelectedRow() != -1) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (tbKerohanian.getValueAt(tbKerohanian.getSelectedRow(), 1).toString().trim().equals("")) {
                Valid.textKosong(TCari, "No.Permintaan");
            } else {

                Sequel.queryu("truncate table temporary_permintaan_kerohanian");
                try {
                    ps = koneksi.prepareStatement(
                            "select permintaan_pemeriksaan_kerohanian.kd_rh, jns_kerohanian.nama_rh "
                            + "from permintaan_pemeriksaan_kerohanian inner join jns_kerohanian on "
                            + "permintaan_pemeriksaan_kerohanian.kd_rh=jns_kerohanian.kd_rh "
                            + "where permintaan_pemeriksaan_kerohanian.noorder=?");
                    try {
                        ps.setString(1, tbKerohanian.getValueAt(tbKerohanian.getSelectedRow(), 1).toString());
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            Sequel.menyimpan("temporary_permintaan_kerohanian", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", 38, new String[]{
                                "0", rs.getString("nama_rh"), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
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
                param.put("noperiksa", tbKerohanian.getValueAt(tbKerohanian.getSelectedRow(), 1).toString());
                norm = Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ", tbKerohanian.getValueAt(tbKerohanian.getSelectedRow(), 2).toString());
                param.put("norm", norm);
                param.put("namapasien", Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ", norm));
                param.put("lahir", Sequel.cariIsi("select DATE_FORMAT(tgl_lahir,'%d-%m-%Y') from pasien where no_rkm_medis=? ", norm));
                param.put("pekerjaan", Sequel.cariIsi("select pekerjaan from pasien where no_rkm_medis=?", norm));
                param.put("jkel", Sequel.cariIsi("select if(jk='L','Laki-laki','Perempuan') as jk from pasien where no_rkm_medis=? ", norm));
                param.put("umur", Sequel.cariIsi("select umur from pasien where no_rkm_medis=?", norm));
                param.put("noktp", Sequel.cariIsi("select no_ktp from pasien where no_rkm_medis=?", norm));
                param.put("agama", Sequel.cariIsi("select agama from pasien where no_rkm_medis=?", norm));
                param.put("namakamar", tbKerohanian.getValueAt(tbKerohanian.getSelectedRow(), 4).toString());
                param.put("pengirim", tbKerohanian.getValueAt(tbKerohanian.getSelectedRow(), 6).toString());
                param.put("petugas", tbKerohanian.getValueAt(tbKerohanian.getSelectedRow(), 7).toString());
                param.put("keterangan", tbKerohanian.getValueAt(tbKerohanian.getSelectedRow(), 8).toString());
                param.put("tanggal", tbKerohanian.getValueAt(tbKerohanian.getSelectedRow(), 5).toString());
                param.put("alamat", Sequel.cariIsi("select concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat from pasien inner join kelurahan inner join kecamatan inner join kabupaten on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where no_rkm_medis=? ", norm));
                param.put("jam", JamPermintaan);
                param.put("namars", var.getnamars());
                param.put("alamatrs", var.getalamatrs());
                param.put("kotars", var.getkabupatenrs());
                param.put("propinsirs", var.getpropinsirs());
                param.put("kontakrs", var.getkontakrs());
                param.put("emailrs", var.getemailrs());
                param.put("logo", Sequel.cariGambar("select logo from setting"));

                Valid.MyReport("rptPermintaanKerohanian.jrxml", "report", "::[ Permintaan Kerohanian ]::",
                        "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14, temp14, temp15, temp16 from temporary_permintaan_kerohanian order by no asc", param);
            }
            TeksKosong();
            this.setCursor(Cursor.getDefaultCursor());
        } else {
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data permintaan...!!!!");
            TCari.requestFocus();
        }
    }//GEN-LAST:event_MnCetakKerohanianActionPerformed

    private void scrollPane1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scrollPane1MouseReleased

    }//GEN-LAST:event_scrollPane1MouseReleased

    private void tbKerohanianMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKerohanianMouseReleased
        if (evt.isPopupTrigger()) {
            jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_tbKerohanianMouseReleased

    private void BtnKirimWAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKirimWAKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnKirimWAKeyPressed

    private void BtnKirimWAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKirimWAActionPerformed
        if (tbKerohanian.getValueAt(tbKerohanian.getSelectedRow(), 1).toString().trim().equals("")) {
            Valid.textKosong(TCari, "No.Permintaan");
        } else {
            for (i = 0; i < tbKerohanian.getRowCount(); i++) {
                if (tbKerohanian.getValueAt(i, 0).toString().equals("true")) {
                    int reply = JOptionPane.showConfirmDialog(rootPane, "Apakah anda ingin mengirim whatsapp permintaan kerohanian..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        kirimwa.sendwaKerohanian(tbKerohanian.getValueAt(i, 3).toString(), tbKerohanian.getValueAt(i, 5).toString(), tbKerohanian.getValueAt(i, 4).toString());
                        JOptionPane.showMessageDialog(null, "Berhasil Mengirim Whatsapp ...!!!!");
                    }
                }
            }
        }
    }//GEN-LAST:event_BtnKirimWAActionPerformed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        var.setform("InformasiKerohanian");
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            InformasiKerohanian dialog = new InformasiKerohanian(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCloseIn4;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnKirimWA;
    private widget.Button BtnPetugas;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek4;
    private widget.Button BtnSimpan4;
    private widget.TextBox CrKamar;
    private widget.TextBox Kd2;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnCetakKerohanian;
    private widget.TextBox NmPtg;
    private widget.TextBox NmPtg1;
    private widget.TextBox TCari;
    private widget.TextArea TKeterangan;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private javax.swing.JDialog WindowAmbilPetugas;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame5;
    private widget.Label jLabel10;
    private widget.Label jLabel16;
    private widget.Label jLabel26;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label18;
    private widget.TextBox noPermintaan;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelisi1;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane3;
    private widget.Table tbKerohanian;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement(
                    "select permintaan_kerohanian.noorder,permintaan_kerohanian.no_rawat,reg_periksa.no_rkm_medis, pasien.nm_pasien,"
                    + "permintaan_kerohanian.tgl_permintaan, permintaan_kerohanian.kd_kamar, bangsal.nm_bangsal, "
                    + "petugas.nama, permintaan_kerohanian.petugas, permintaan_kerohanian.keterangan from permintaan_kerohanian inner join reg_periksa "
                    + "inner join pasien inner join permintaan_pemeriksaan_kerohanian inner join petugas inner join bangsal inner join kamar "
                    + "inner join kamar_inap on permintaan_kerohanian.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "and permintaan_kerohanian.noorder=permintaan_pemeriksaan_kerohanian.noorder and permintaan_kerohanian.perujuk=petugas.nip and "
                    + "kamar.kd_bangsal=bangsal.kd_bangsal and reg_periksa.no_rawat=kamar_inap.no_rawat and permintaan_kerohanian.kd_kamar=kamar.kd_kamar where "
                    + "kamar_inap.tgl_keluar='0000-00-00' and permintaan_kerohanian.tgl_permintaan between ? and ? and bangsal.nm_bangsal like ? and (permintaan_kerohanian.noorder like ? or "
                    + "permintaan_kerohanian.no_rawat like ? or "
                    + "reg_periksa.no_rkm_medis like ? or "
                    + "pasien.nm_pasien like ? or "
                    + "permintaan_kerohanian.petugas like ? or "
                    + "permintaan_kerohanian.perujuk like ? or "
                    + "permintaan_kerohanian.keterangan like ?) "
                    + "group by permintaan_kerohanian.noorder order by permintaan_kerohanian.noorder");
            try {
                ps.setString(1, Valid.SetTgl(Tgl1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(Tgl2.getSelectedItem() + ""));
                ps.setString(3, "%" + CrKamar.getText().trim() + "%");
                ps.setString(4, "%" + TCari.getText() + "%");
                ps.setString(5, "%" + TCari.getText() + "%");
                ps.setString(6, "%" + TCari.getText() + "%");
                ps.setString(7, "%" + TCari.getText() + "%");
                ps.setString(8, "%" + TCari.getText() + "%");
                ps.setString(9, "%" + TCari.getText() + "%");
                ps.setString(10, "%" + TCari.getText() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new Object[]{
                        false, rs.getString("noorder"), rs.getString("no_rawat"), rs.getString("no_rkm_medis") + " " + rs.getString("nm_pasien"), rs.getString("kd_kamar") + " " + rs.getString("nm_bangsal"),
                        rs.getString("tgl_permintaan"), rs.getString("nama"), rs.getString("petugas"), rs.getString("keterangan")
                    });
                    ps2 = koneksi.prepareStatement(
                            "select permintaan_pemeriksaan_kerohanian.kd_rh, jns_kerohanian.nama_rh from permintaan_pemeriksaan_kerohanian "
                            + "inner join jns_kerohanian on permintaan_pemeriksaan_kerohanian.kd_rh=jns_kerohanian.kd_rh "
                            + "where permintaan_pemeriksaan_kerohanian.noorder=?");
                    try {
                        ps2.setString(1, rs.getString("noorder"));
                        rs2 = ps2.executeQuery();
                        while (rs2.next()) {
                            tabMode.addRow(new Object[]{
                                false, "", "", rs2.getString("nama_rh"), "", "", "", "", ""
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
                rs.last();
                LCount.setText("" + rs.getRow());
            } catch (Exception e) {
                System.out.println(e);
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

    private void getData() {
        Kd2.setText("");
        if (tbKerohanian.getSelectedRow() != -1) {
            Kd2.setText(tbKerohanian.getValueAt(tbKerohanian.getSelectedRow(), 1).toString());
            Sequel.cariIsi("select keterangan from permintaan_kerohanian where noorder=?", TKeterangan, Kd2.getText());
            Sequel.cariIsi("select petugas from permintaan_kerohanian where noorder=?", NmPtg, Kd2.getText());
        }
    }

    public void isCek() {
//        BtnPetugas.setEnabled(var.getpasien_meninggal());
        BtnHapus.setEnabled(var.getpasien_meninggal());
        BtnPrint.setEnabled(var.getpasien_meninggal());
    }

    public void setPasien(String pasien) {
        TCari.setText(pasien);
    }

    private void TeksKosong() {
        NoPermintaan = "";
        NoRawat = "";
        Kamar = "";
        TglPermintaan = "";
        Perujuk = "";
        Petugas = "";
        Ket = "";
        NmPtg.setText("");
        NmPtg1.setText("");
        TKeterangan.setText("");
    }
}
