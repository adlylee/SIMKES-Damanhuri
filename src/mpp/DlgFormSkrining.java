package mpp;

import inventory.*;
import fungsi.BackgroundMusic;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Color;
import java.awt.Component;
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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;
import simrskhanza.DlgCariBangsal;
import simrskhanza.DlgCariPoli;
import widget.CekBox;

public class DlgFormSkrining extends javax.swing.JDialog {

    private final DefaultTableModel tabMode, tabMode1, tabMode2, tabMode3, tabModeData;
    private int i = 0, jml = 0, index = 0, jmlskala1 = 0;
    private String[] head, sub, subunit, nama;
    private boolean[] pilih;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private PreparedStatement ps, ps2, ps3, ps4;
    private ResultSet rs, rs2, rs3, rs4;
    private DlgCariObat dlgobt = new DlgCariObat(null, false);
    private DlgCariObat2 dlgobt2 = new DlgCariObat2(null, false);
    private DlgCariObat4 dlgobt4 = new DlgCariObat4(null, false);
    private String norawatibu = "", bangsal = "", aktifkanparsial = "no", kamar = "", alarm = "", kelas = "",
            NoRawat = "", NoRM = "", Pasien = "", DokterPeresep = "", Status = "", KodeDokter = "", Ruang = "", KodeRuang = "", JnsBayar = "", namakamar, status = "", dx1 = "";
    private final Properties prop = new Properties();
    private boolean aktif = false;
    public DlgCariPetugas petugas = new DlgCariPetugas(null, false);

    /**
     * Creates new form
     *
     * @param parent
     * @param modal
     */
    public DlgFormSkrining(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode = new DefaultTableModel(null, new Object[]{"P", "No", "Judul", "Sub Unit", "Nama"}) {
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
        tbForm.setModel(tabMode);
        tbForm.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbForm.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 5; i++) {
            TableColumn column = tbForm.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(35);
            } else if (i == 1) {
                column.setPreferredWidth(35);
            } else if (i == 2) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
//                column.setPreferredWidth(200);
            } else if (i == 3) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 4) {
                column.setPreferredWidth(730);
            }
        }
        tbForm.setDefaultRenderer(Object.class, new WarnaTable());

        tabMode1 = new DefaultTableModel(null, new Object[]{"P", "No", "Judul", "Sub Unit", "Nama"}) {
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
        tbForm1.setModel(tabMode1);
        tbForm1.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbForm1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 5; i++) {
            TableColumn column = tbForm1.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(35);
            } else if (i == 1) {
                column.setPreferredWidth(35);
            } else if (i == 2) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 3) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 4) {
                column.setPreferredWidth(730);
            }
        }
        tbForm1.setDefaultRenderer(Object.class, new WarnaTable());

        tabMode2 = new DefaultTableModel(null, new Object[]{"P", "No", "Judul", "Sub Unit", "Nama"}) {
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
        tbForm2.setModel(tabMode2);
        tbForm2.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbForm2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 5; i++) {
            TableColumn column = tbForm2.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(35);
            } else if (i == 1) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 2) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 3) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 4) {
                column.setPreferredWidth(730);
            }
        }
        tbForm2.setDefaultRenderer(Object.class, new WarnaTable());

        tabMode3 = new DefaultTableModel(null, new Object[]{"P", "No", "Judul", "Sub Unit", "Nama"}) {
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
        tbForm3.setModel(tabMode3);
        tbForm3.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbForm3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 5; i++) {
            TableColumn column = tbForm3.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(35);
            } else if (i == 1) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 2) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 3) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 4) {
                column.setPreferredWidth(730);
            }
        }
        tbForm3.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeData = new DefaultTableModel(null, new Object[]{"P", "No. Rawat", "Pasien", "Kamar", "Tanggal", "Skrining", "Evaluasi Awal", "Petugas"}) {
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
                java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbData.setModel(tabModeData);
        tbData.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 8; i++) {
            TableColumn column = tbData.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(35);
            } else if (i == 1) {
                column.setPreferredWidth(105);
            } else if (i == 2) {
                column.setPreferredWidth(300);
            } else if (i == 3) {
                column.setPreferredWidth(165);
            } else if (i == 4) {
                column.setPreferredWidth(80);
            } else if (i == 5) {
                column.setPreferredWidth(80);
            } else if (i == 6) {
                column.setPreferredWidth(300);
            } else if (i == 7) {
                column.setPreferredWidth(150);
            }
        }
        tbData.setDefaultRenderer(Object.class, new WarnaTable());

        TCari.setDocument(
                new batasInput((byte) 100).getKata(TCari)
        );
        if (koneksiDB.cariCepat()
                .equals("aktif")) {
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
                if (var.getform().equals("DlgFormSkrining")) {
                    if (petugas.getTable().getSelectedRow() != -1) {
                        kdptg2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                        TPerawat2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                        kdptg2.requestFocus();
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
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        evaluasiawal = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        jPanel2 = new javax.swing.JPanel();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnEdit = new widget.Button();
        BtnHapus = new widget.Button();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnRekap = new widget.Button();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        TabSkrining = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        TabInput = new javax.swing.JTabbedPane();
        ScrollTriase1 = new widget.ScrollPane();
        FormTriase1 = new widget.InternalFrame();
        FormInput8 = new widget.PanelBiasa();
        FormInput12 = new widget.PanelBiasa();
        jLabel13 = new widget.Label();
        tTotal = new widget.TextBox();
        jLabel27 = new widget.Label();
        jLabel28 = new widget.Label();
        textBox1 = new widget.TextBox();
        textBox2 = new widget.TextBox();
        textBox3 = new widget.TextBox();
        textBox4 = new widget.TextBox();
        textBox5 = new widget.TextBox();
        textBox6 = new widget.TextBox();
        textBox7 = new widget.TextBox();
        textBox8 = new widget.TextBox();
        textBox9 = new widget.TextBox();
        textBox10 = new widget.TextBox();
        jLabel32 = new widget.Label();
        chkBox10 = new widget.CekBox();
        chkBox1 = new widget.CekBox();
        chkBox2 = new widget.CekBox();
        chkBox3 = new widget.CekBox();
        chkBox4 = new widget.CekBox();
        chkBox5 = new widget.CekBox();
        chkBox6 = new widget.CekBox();
        chkBox7 = new widget.CekBox();
        chkBox8 = new widget.CekBox();
        chkBox9 = new widget.CekBox();
        ScrollTriase2 = new widget.ScrollPane();
        FormTriase2 = new widget.InternalFrame();
        FormInput9 = new widget.PanelBiasa();
        FormInput13 = new widget.PanelBiasa();
        Scroll1 = new widget.ScrollPane();
        tbForm = new widget.Table();
        Scroll2 = new widget.ScrollPane();
        tbForm1 = new widget.Table();
        Scroll3 = new widget.ScrollPane();
        tbForm2 = new widget.Table();
        Scroll4 = new widget.ScrollPane();
        tbForm3 = new widget.Table();
        jLabel33 = new widget.Label();
        jLabel34 = new widget.Label();
        jLabel35 = new widget.Label();
        jLabel36 = new widget.Label();
        panelGlass13 = new widget.panelisi();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel14 = new widget.Label();
        kdptg2 = new widget.TextBox();
        TPerawat2 = new widget.TextBox();
        BtnSeekPetugas2 = new widget.Button();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbData = new widget.Table();
        panelisi2 = new widget.panelisi();
        Tanggal = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();

        evaluasiawal.setHighlighter(null);
        evaluasiawal.setName("evaluasiawal"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowDeactivated(java.awt.event.WindowEvent evt) {
                formWindowDeactivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Form Skrining MPP ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(44, 54));
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelisi1.add(BtnSimpan);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('U');
        BtnEdit.setText("Ubah");
        BtnEdit.setToolTipText("Alt+U");
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
        panelisi1.add(BtnEdit);

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

        BtnRekap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/edit_f2.png"))); // NOI18N
        BtnRekap.setMnemonic('R');
        BtnRekap.setText("Kirim WA");
        BtnRekap.setToolTipText("Alt+R");
        BtnRekap.setName("BtnRekap"); // NOI18N
        BtnRekap.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRekap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRekapActionPerformed(evt);
            }
        });
        BtnRekap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnRekapKeyPressed(evt);
            }
        });
        panelisi1.add(BtnRekap);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(103, 23));
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

        jPanel2.add(panelisi1, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        TabSkrining.setBackground(new java.awt.Color(255, 255, 253));
        TabSkrining.setForeground(new java.awt.Color(70, 70, 70));
        TabSkrining.setName("TabSkrining"); // NOI18N
        TabSkrining.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabSkriningMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout());

        TabInput.setBackground(new java.awt.Color(255, 255, 253));
        TabInput.setForeground(new java.awt.Color(70, 70, 70));
        TabInput.setName("TabInput"); // NOI18N
        TabInput.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabInputMouseClicked(evt);
            }
        });

        ScrollTriase1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        ScrollTriase1.setName("ScrollTriase1"); // NOI18N
        ScrollTriase1.setOpaque(true);
        ScrollTriase1.setPreferredSize(new java.awt.Dimension(502, 450));

        FormTriase1.setBorder(null);
        FormTriase1.setName("FormTriase1"); // NOI18N
        FormTriase1.setPreferredSize(new java.awt.Dimension(450, 430));
        FormTriase1.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput8.setName("FormInput8"); // NOI18N
        FormInput8.setPreferredSize(new java.awt.Dimension(265, 137));
        FormInput8.setLayout(new java.awt.BorderLayout());

        FormInput12.setName("FormInput12"); // NOI18N
        FormInput12.setPreferredSize(new java.awt.Dimension(265, 200));
        FormInput12.setLayout(null);

        jLabel13.setText("PELAYANAN");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput12.add(jLabel13);
        jLabel13.setBounds(0, 30, 100, 23);

        tTotal.setHighlighter(null);
        tTotal.setName("tTotal"); // NOI18N
        FormInput12.add(tTotal);
        tTotal.setBounds(470, 390, 50, 23);

        jLabel27.setText("Jumlah");
        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput12.add(jLabel27);
        jLabel27.setBounds(280, 390, 180, 23);

        jLabel28.setText("ADMINISTRASI");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput12.add(jLabel28);
        jLabel28.setBounds(0, 210, 110, 23);

        textBox1.setName("textBox1"); // NOI18N
        FormInput12.add(textBox1);
        textBox1.setBounds(470, 60, 50, 23);

        textBox2.setName("textBox2"); // NOI18N
        FormInput12.add(textBox2);
        textBox2.setBounds(470, 90, 50, 23);

        textBox3.setHighlighter(null);
        textBox3.setName("textBox3"); // NOI18N
        FormInput12.add(textBox3);
        textBox3.setBounds(470, 120, 50, 23);

        textBox4.setHighlighter(null);
        textBox4.setName("textBox4"); // NOI18N
        FormInput12.add(textBox4);
        textBox4.setBounds(470, 150, 50, 23);

        textBox5.setHighlighter(null);
        textBox5.setName("textBox5"); // NOI18N
        FormInput12.add(textBox5);
        textBox5.setBounds(470, 180, 50, 23);

        textBox6.setHighlighter(null);
        textBox6.setName("textBox6"); // NOI18N
        FormInput12.add(textBox6);
        textBox6.setBounds(470, 240, 50, 23);

        textBox7.setHighlighter(null);
        textBox7.setName("textBox7"); // NOI18N
        FormInput12.add(textBox7);
        textBox7.setBounds(470, 270, 50, 23);

        textBox8.setHighlighter(null);
        textBox8.setName("textBox8"); // NOI18N
        FormInput12.add(textBox8);
        textBox8.setBounds(470, 300, 50, 23);

        textBox9.setHighlighter(null);
        textBox9.setName("textBox9"); // NOI18N
        FormInput12.add(textBox9);
        textBox9.setBounds(470, 330, 50, 23);

        textBox10.setHighlighter(null);
        textBox10.setName("textBox10"); // NOI18N
        FormInput12.add(textBox10);
        textBox10.setBounds(470, 360, 50, 23);

        jLabel32.setText("KEBUTUHAN PASIEN");
        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput12.add(jLabel32);
        jLabel32.setBounds(0, 0, 130, 23);

        chkBox10.setBorder(null);
        chkBox10.setText("Kelengkapan status pasien terisi lengkap");
        chkBox10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkBox10.setName("chkBox10"); // NOI18N
        FormInput12.add(chkBox10);
        chkBox10.setBounds(60, 360, 400, 23);

        chkBox1.setBorder(null);
        chkBox1.setText("Obat-obatan terdistribusi ke pasien");
        chkBox1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkBox1.setName("chkBox1"); // NOI18N
        chkBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkBox1ItemStateChanged(evt);
            }
        });
        FormInput12.add(chkBox1);
        chkBox1.setBounds(60, 60, 400, 23);

        chkBox2.setBorder(null);
        chkBox2.setText("Keluhan pasien teratasi");
        chkBox2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkBox2.setName("chkBox2"); // NOI18N
        chkBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkBox2ItemStateChanged(evt);
            }
        });
        FormInput12.add(chkBox2);
        chkBox2.setBounds(60, 90, 400, 23);

        chkBox3.setBorder(null);
        chkBox3.setText("Perawat memberikan asuhan keperawatan");
        chkBox3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkBox3.setName("chkBox3"); // NOI18N
        chkBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkBox3ItemStateChanged(evt);
            }
        });
        FormInput12.add(chkBox3);
        chkBox3.setBounds(60, 120, 400, 23);

        chkBox4.setBorder(null);
        chkBox4.setText("Pasien divisite oleh dokter penanggung jawab");
        chkBox4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkBox4.setName("chkBox4"); // NOI18N
        chkBox4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkBox4ItemStateChanged(evt);
            }
        });
        FormInput12.add(chkBox4);
        chkBox4.setBounds(60, 150, 400, 23);

        chkBox5.setBorder(null);
        chkBox5.setText("Pasien mendapatkan terapi yang sesuai dengan kebutuhannya");
        chkBox5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkBox5.setName("chkBox5"); // NOI18N
        chkBox5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkBox5ItemStateChanged(evt);
            }
        });
        FormInput12.add(chkBox5);
        chkBox5.setBounds(60, 180, 400, 23);

        chkBox6.setBorder(null);
        chkBox6.setText("Jaminan kesehatan berobat pasien aktif");
        chkBox6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkBox6.setName("chkBox6"); // NOI18N
        FormInput12.add(chkBox6);
        chkBox6.setBounds(60, 240, 400, 23);

        chkBox7.setBorder(null);
        chkBox7.setText("Surat Elegibilitas Pasien (SEP) sudah terbit ketika pasien mendaftar");
        chkBox7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkBox7.setName("chkBox7"); // NOI18N
        FormInput12.add(chkBox7);
        chkBox7.setBounds(60, 270, 400, 23);

        chkBox8.setBorder(null);
        chkBox8.setText("Bilingan pasien disesuaikan dengan jaminan kelas pasien");
        chkBox8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkBox8.setName("chkBox8"); // NOI18N
        FormInput12.add(chkBox8);
        chkBox8.setBounds(60, 300, 400, 23);

        chkBox9.setBorder(null);
        chkBox9.setText("Pasien jaminan umum mendapatkan rincian kwitansi perawatan dan pengobatan");
        chkBox9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkBox9.setName("chkBox9"); // NOI18N
        FormInput12.add(chkBox9);
        chkBox9.setBounds(60, 330, 400, 23);

        FormInput8.add(FormInput12, java.awt.BorderLayout.CENTER);

        FormTriase1.add(FormInput8, java.awt.BorderLayout.CENTER);

        ScrollTriase1.setViewportView(FormTriase1);

        TabInput.addTab("Skrining Ceklist", ScrollTriase1);

        ScrollTriase2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        ScrollTriase2.setName("ScrollTriase2"); // NOI18N
        ScrollTriase2.setOpaque(true);
        ScrollTriase2.setPreferredSize(new java.awt.Dimension(702, 850));

        FormTriase2.setBorder(null);
        FormTriase2.setName("FormTriase2"); // NOI18N
        FormTriase2.setPreferredSize(new java.awt.Dimension(600, 840));
        FormTriase2.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput9.setName("FormInput9"); // NOI18N
        FormInput9.setPreferredSize(new java.awt.Dimension(265, 137));
        FormInput9.setLayout(new java.awt.BorderLayout());

        FormInput13.setName("FormInput13"); // NOI18N
        FormInput13.setPreferredSize(new java.awt.Dimension(265, 200));
        FormInput13.setLayout(null);

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll1.setName("Scroll1"); // NOI18N

        tbForm.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbForm.setName("tbForm"); // NOI18N
        tbForm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbFormMouseClicked(evt);
            }
        });
        Scroll1.setViewportView(tbForm);

        FormInput13.add(Scroll1);
        Scroll1.setBounds(0, 30, 850, 150);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll2.setName("Scroll2"); // NOI18N

        tbForm1.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbForm1.setName("tbForm1"); // NOI18N
        tbForm1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbForm1MouseClicked(evt);
            }
        });
        Scroll2.setViewportView(tbForm1);

        FormInput13.add(Scroll2);
        Scroll2.setBounds(0, 240, 850, 150);

        Scroll3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll3.setName("Scroll3"); // NOI18N

        tbForm2.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbForm2.setName("tbForm2"); // NOI18N
        tbForm2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbForm2MouseClicked(evt);
            }
        });
        Scroll3.setViewportView(tbForm2);

        FormInput13.add(Scroll3);
        Scroll3.setBounds(0, 440, 850, 150);

        Scroll4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll4.setName("Scroll4"); // NOI18N

        tbForm3.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbForm3.setName("tbForm3"); // NOI18N
        tbForm3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbForm3MouseClicked(evt);
            }
        });
        Scroll4.setViewportView(tbForm3);

        FormInput13.add(Scroll4);
        Scroll4.setBounds(0, 650, 850, 150);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText(".: Assesment");
        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput13.add(jLabel33);
        jLabel33.setBounds(0, 210, 150, 23);

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel34.setText(".: Skrining Permasalahan");
        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput13.add(jLabel34);
        jLabel34.setBounds(0, 0, 150, 23);

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel35.setText(".: Identifikasi Masalah - Resiko");
        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput13.add(jLabel35);
        jLabel35.setBounds(0, 410, 210, 23);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText(".: Perencanaan Manajemen Pelayanan Pasien");
        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput13.add(jLabel36);
        jLabel36.setBounds(0, 620, 290, 23);

        FormInput9.add(FormInput13, java.awt.BorderLayout.CENTER);

        FormTriase2.add(FormInput9, java.awt.BorderLayout.CENTER);

        ScrollTriase2.setViewportView(FormTriase2);

        TabInput.addTab("Form MPP", ScrollTriase2);

        internalFrame2.add(TabInput, java.awt.BorderLayout.CENTER);

        panelGlass13.setName("panelGlass13"); // NOI18N
        panelGlass13.setPreferredSize(new java.awt.Dimension(44, 74));
        panelGlass13.setLayout(null);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        jLabel3.setPreferredSize(null);
        panelGlass13.add(jLabel3);
        jLabel3.setBounds(0, 10, 70, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        panelGlass13.add(TNoRw);
        TNoRw.setBounds(74, 10, 125, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        panelGlass13.add(TNoRM);
        TNoRM.setBounds(201, 10, 80, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        panelGlass13.add(TPasien);
        TPasien.setBounds(283, 10, 260, 23);

        jLabel14.setText("Petugas :");
        jLabel14.setName("jLabel14"); // NOI18N
        panelGlass13.add(jLabel14);
        jLabel14.setBounds(0, 40, 70, 23);

        kdptg2.setHighlighter(null);
        kdptg2.setName("kdptg2"); // NOI18N
        kdptg2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptg2KeyPressed(evt);
            }
        });
        panelGlass13.add(kdptg2);
        kdptg2.setBounds(74, 40, 125, 23);

        TPerawat2.setEditable(false);
        TPerawat2.setBackground(new java.awt.Color(202, 202, 202));
        TPerawat2.setHighlighter(null);
        TPerawat2.setName("TPerawat2"); // NOI18N
        panelGlass13.add(TPerawat2);
        TPerawat2.setBounds(201, 40, 320, 23);

        BtnSeekPetugas2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekPetugas2.setMnemonic('5');
        BtnSeekPetugas2.setToolTipText("ALt+5");
        BtnSeekPetugas2.setName("BtnSeekPetugas2"); // NOI18N
        BtnSeekPetugas2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPetugas2ActionPerformed(evt);
            }
        });
        panelGlass13.add(BtnSeekPetugas2);
        BtnSeekPetugas2.setBounds(520, 40, 28, 23);

        internalFrame2.add(panelGlass13, java.awt.BorderLayout.PAGE_START);

        TabSkrining.addTab("Input Skrining", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout());

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbData.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbData.setName("tbData"); // NOI18N
        tbData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbDataMouseReleased(evt);
            }
        });
        tbData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDataKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbDataKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbData);

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelisi2.setBackground(new java.awt.Color(255, 150, 255));
        panelisi2.setName("panelisi2"); // NOI18N
        panelisi2.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        Tanggal.setText("Tanggal :");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi2.add(Tanggal);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-05-2023" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelisi2.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(24, 23));
        panelisi2.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "05-05-2023" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelisi2.add(DTPCari2);

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi2.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(290, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi2.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
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
        panelisi2.add(BtnCari);

        internalFrame3.add(panelisi2, java.awt.BorderLayout.PAGE_END);

        TabSkrining.addTab("Data Skrining", internalFrame3);

        internalFrame1.add(TabSkrining, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            //TabRawatMouseClicked(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
//            tbResepRalan.requestFocus();
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

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
//                if (tabMode2.getRowCount() == 0) {
//                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
//                    TCari.requestFocus();
//                } else if (tabMode2.getRowCount() != 0) {
//                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//                    Sequel.queryu("delete from temporary_resep");
//
//                    for (int i = 0; i < tabMode2.getRowCount(); i++) {
//                        Sequel.menyimpan("temporary_resep", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", 38, new String[]{
//                            "0", tabMode2.getValueAt(i, 0).toString(), tabMode2.getValueAt(i, 1).toString(), tabMode2.getValueAt(i, 2).toString(),
//                            tabMode2.getValueAt(i, 3).toString(), tabMode2.getValueAt(i, 4).toString(), tabMode2.getValueAt(i, 5).toString(), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
//                        });
//                    }
//
//                    Map<String, Object> param = new HashMap<>();
//                    param.put("namars", var.getnamars());
//                    param.put("alamatrs", var.getalamatrs());
//                    param.put("kotars", var.getkabupatenrs());
//                    param.put("propinsirs", var.getpropinsirs());
//                    param.put("kontakrs", var.getkontakrs());
//                    param.put("emailrs", var.getemailrs());
//                    param.put("logo", Sequel.cariGambar("select logo from setting"));
//                    Valid.MyReport2("rptDaftarResep.jrxml", "report", "::[ Daftar Resep Obat ]::",
//                            "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14 from temporary_resep order by no asc", param);
//                    this.setCursor(Cursor.getDefaultCursor());
//                }
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            //Valid.pindah(evt,BtnEdit,BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

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

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (TNoRw.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
        } else {
            if (TabSkrining.getSelectedIndex() == 0) {
                if (TabInput.getSelectedIndex() == 0) {
                    if ((!tTotal.getText().trim().equals(""))) {
                        if (Sequel.menyimpantf("evaluasi_awal_mpp", "?,?,?,?,?", "Data", 5, new String[]{
                            TNoRw.getText(), Valid.SetTgl(DTPCari1.getSelectedItem() + ""), "", tTotal.getText(), kdptg2.getText()
                        }) == true) {
                            emptTeks();
                        }
                    }
                } else if (TabInput.getSelectedIndex() == 1) {
                    if (Sequel.cariInteger("select count(no_rawat) from evaluasi_awal_mpp where no_rawat='" + TNoRw.getText() + "'") > 0) {
                        Sequel.queryu("delete from temporary_skrining where temp1=?", TNoRw.getText());
                        for (i = 0; i < tbForm.getRowCount(); i++) {
                            if (tbForm.getValueAt(i, 0).toString().equals("true")) {
                                Sequel.menyimpan("temporary_skrining", "'0','" + TNoRw.getText() + "','" + tbForm.getValueAt(i, 3).toString() + "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''", "Skrining");
                            }
                        }
                        for (i = 0; i < tbForm1.getRowCount(); i++) {
                            if (tbForm1.getValueAt(i, 0).toString().equals("true")) {
                                Sequel.menyimpan("temporary_skrining", "'0','" + TNoRw.getText() + "','" + tbForm1.getValueAt(i, 3).toString() + "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''", "Skrining");
                            }
                        }
                        for (i = 0; i < tbForm2.getRowCount(); i++) {
                            if (tbForm2.getValueAt(i, 0).toString().equals("true")) {
                                Sequel.menyimpan("temporary_skrining", "'0','" + TNoRw.getText() + "','" + tbForm2.getValueAt(i, 3).toString() + "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''", "Skrining");
                            }
                        }
                        for (i = 0; i < tbForm3.getRowCount(); i++) {
                            if (tbForm3.getValueAt(i, 0).toString().equals("true")) {
                                Sequel.menyimpan("temporary_skrining", "'0','" + TNoRw.getText() + "','" + tbForm3.getValueAt(i, 3).toString() + "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''", "Skrining");
                            }
                        }
                        isForm();
                        isReset();
                        emptTeks();
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Data skrining ceklist belum diinput..!!!");
                    }
                }
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed
    /*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed

    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnSimpan, BtnPrint);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void BtnRekapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRekapActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgResepObat resep = new DlgResepObat(null, false);
        resep.tampil();
        resep.emptTeks();
        resep.isCek();
        resep.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        resep.setLocationRelativeTo(internalFrame1);
        resep.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnRekapActionPerformed

    private void BtnRekapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnRekapKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnRekapKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed

    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnEdit);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        aktif = true;
    }//GEN-LAST:event_formWindowActivated

    private void formWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeactivated
        aktif = false;
    }//GEN-LAST:event_formWindowDeactivated

    private void TabSkriningMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabSkriningMouseClicked
        if (TabSkrining.getSelectedIndex() == 1) {
            tampil2();
        }
    }//GEN-LAST:event_TabSkriningMouseClicked

    private void TabInputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabInputMouseClicked

    }//GEN-LAST:event_TabInputMouseClicked

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed

    }//GEN-LAST:event_TNoRwKeyPressed

    private void tbFormMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbFormMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbFormMouseClicked

    private void tbForm1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbForm1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbForm1MouseClicked

    private void tbForm2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbForm2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbForm2MouseClicked

    private void tbForm3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbForm3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbForm3MouseClicked

    private void tbDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataMouseClicked

    }//GEN-LAST:event_tbDataMouseClicked

    private void tbDataMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataMouseReleased

    }//GEN-LAST:event_tbDataMouseReleased

    private void tbDataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDataKeyPressed
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbDataKeyPressed

    private void tbDataKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDataKeyReleased
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbDataKeyReleased

    private void chkBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkBox1ItemStateChanged
        if (chkBox1.isSelected() == true) {
            textBox1.setText("1");
        } else {
            textBox1.setText("");
        }
        total();
    }//GEN-LAST:event_chkBox1ItemStateChanged

    private void chkBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkBox2ItemStateChanged
        if (chkBox2.isSelected() == true) {
            textBox2.setText("1");
        } else {
            textBox2.setText("");
        }
        total();
    }//GEN-LAST:event_chkBox2ItemStateChanged

    private void chkBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkBox3ItemStateChanged
        if (chkBox3.isSelected() == true) {
            textBox3.setText("1");
        } else {
            textBox3.setText("");
        }
        total();
    }//GEN-LAST:event_chkBox3ItemStateChanged

    private void chkBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkBox4ItemStateChanged
        if (chkBox4.isSelected() == true) {
            textBox4.setText("1");
        } else {
            textBox4.setText("");
        }
        total();
    }//GEN-LAST:event_chkBox4ItemStateChanged

    private void chkBox5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkBox5ItemStateChanged
        if (chkBox5.isSelected() == true) {
            textBox5.setText("1");
        } else {
            textBox5.setText("");
        }
        total();
    }//GEN-LAST:event_chkBox5ItemStateChanged

    private void kdptg2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptg2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select nama from petugas where nip=?", TPerawat2, kdptg2.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnSeekPetugas2ActionPerformed(null);
        } else {
            Valid.pindah(evt, TNoRw, BtnSeekPetugas2);
        }
    }//GEN-LAST:event_kdptg2KeyPressed

    private void BtnSeekPetugas2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekPetugas2ActionPerformed
        var.setform("DlgFormSkrining");
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnSeekPetugas2ActionPerformed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil2();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnAllActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgFormSkrining dialog = new DlgFormSkrining(new javax.swing.JFrame(), true);
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
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnRekap;
    private widget.Button BtnSeekPetugas2;
    private widget.Button BtnSimpan;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput12;
    private widget.PanelBiasa FormInput13;
    private widget.PanelBiasa FormInput8;
    private widget.PanelBiasa FormInput9;
    private widget.InternalFrame FormTriase1;
    private widget.InternalFrame FormTriase2;
    private widget.Label LCount;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.ScrollPane ScrollTriase1;
    private widget.ScrollPane ScrollTriase2;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TPerawat2;
    private javax.swing.JTabbedPane TabInput;
    private javax.swing.JTabbedPane TabSkrining;
    private widget.Label Tanggal;
    private widget.CekBox chkBox1;
    private widget.CekBox chkBox10;
    private widget.CekBox chkBox2;
    private widget.CekBox chkBox3;
    private widget.CekBox chkBox4;
    private widget.CekBox chkBox5;
    private widget.CekBox chkBox6;
    private widget.CekBox chkBox7;
    private widget.CekBox chkBox8;
    private widget.CekBox chkBox9;
    private widget.TextBox evaluasiawal;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel21;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel3;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private javax.swing.JPanel jPanel2;
    private widget.TextBox kdptg2;
    private widget.Label label9;
    private widget.panelisi panelGlass13;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi2;
    private widget.TextBox tTotal;
    private widget.Table tbData;
    private widget.Table tbForm;
    private widget.Table tbForm1;
    private widget.Table tbForm2;
    private widget.Table tbForm3;
    private widget.TextBox textBox1;
    private widget.TextBox textBox10;
    private widget.TextBox textBox2;
    private widget.TextBox textBox3;
    private widget.TextBox textBox4;
    private widget.TextBox textBox5;
    private widget.TextBox textBox6;
    private widget.TextBox textBox7;
    private widget.TextBox textBox8;
    private widget.TextBox textBox9;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        try {
            jml = 0;
            for (i = 0; i < tbForm.getRowCount(); i++) {
                if (tbForm.getValueAt(i, 0).toString().equals("true")) {
                    jml++;
                }
            }

            pilih = null;
            pilih = new boolean[jml];
            head = null;
            head = new String[jml];
            sub = null;
            sub = new String[jml];
            subunit = null;
            subunit = new String[jml];
            nama = null;
            nama = new String[jml];

            index = 0;
            for (i = 0; i < tbForm.getRowCount(); i++) {
                if (tbForm.getValueAt(i, 0).toString().equals("true")) {
                    pilih[index] = true;
                    head[index] = tbForm.getValueAt(i, 1).toString();
                    sub[index] = tbForm.getValueAt(i, 2).toString();
                    subunit[index] = tbForm.getValueAt(i, 3).toString();
                    nama[index] = tbForm.getValueAt(i, 4).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabMode);
            for (i = 0; i < jml; i++) {
                tabMode.addRow(new Object[]{
                    pilih[i], head[i], sub[i], subunit[i], nama[i]
                });
            }

            ps = koneksi.prepareStatement(
                    "SELECT * FROM master_skrinning_mpp where header='1' order by sub, sub_unit");
            try {
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new Object[]{false, rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)});
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

            ps2 = koneksi.prepareStatement(
                    "SELECT * FROM master_skrinning_mpp where header='2' order by cast(sub as int), sub_unit");
            try {
                rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    tabMode1.addRow(new Object[]{false, rs2.getString(3), rs2.getString(4), rs2.getString(5), rs2.getString(6)});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi 1 : " + e);
            } finally {
                if (rs2 != null) {
                    rs2.close();
                }
                if (ps2 != null) {
                    ps2.close();
                }
            }

            ps3 = koneksi.prepareStatement(
                    "SELECT * FROM master_skrinning_mpp where header='3' order by sub_unit");
            try {
                rs3 = ps3.executeQuery();
                while (rs3.next()) {
                    tabMode2.addRow(new Object[]{false, rs3.getString(3), rs3.getString(4), rs3.getString(5), rs3.getString(6)});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi 1 : " + e);
            } finally {
                if (rs3 != null) {
                    rs3.close();
                }
                if (ps3 != null) {
                    ps3.close();
                }
            }

            ps4 = koneksi.prepareStatement(
                    "SELECT * FROM master_skrinning_mpp where header='4' order by sub_unit");
            try {
                rs4 = ps4.executeQuery();
                while (rs4.next()) {
                    tabMode3.addRow(new Object[]{false, rs4.getString(3), rs4.getString(4), rs4.getString(5), rs4.getString(6)});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi 1 : " + e);
            } finally {
                if (rs4 != null) {
                    rs4.close();
                }
                if (ps4 != null) {
                    ps4.close();
                }
            }

        } catch (Exception e) {
            System.out.println("Notifikasi 2 : " + e);
        }
    }

    private void tampil2() {
        Valid.tabelKosong(tabModeData);
        try {
            ps = koneksi.prepareStatement(
                    "select reg_periksa.no_rawat, concat(reg_periksa.no_rkm_medis,' ',pasien.nm_pasien) as pasien, concat(kamar_inap.kd_kamar,' ',bangsal.nm_bangsal) as kamar,evaluasi_awal_mpp.tanggal,"
                    + "evaluasi_awal_mpp.nilai_skrining, evaluasi_awal_mpp.evaluasi_awal, petugas.nama from evaluasi_awal_mpp, reg_periksa,pasien,petugas, kamar,bangsal, kamar_inap where evaluasi_awal_mpp.no_rawat=reg_periksa.no_rawat "
                    + "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal and petugas.nip=evaluasi_awal_mpp.petugas and "
                    + "evaluasi_awal_mpp.no_rawat=kamar_inap.no_rawat and evaluasi_awal_mpp.tanggal between ? and ? and evaluasi_awal_mpp.no_rawat like ?");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeData.addRow(new Object[]{false, rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)});
                    ps2 = koneksi.prepareStatement(
                            "SELECT concat(a.header,'.',a.sub) as nomor ,a.nama "
                            + "FROM master_skrinning_mpp a "
                            + "INNER JOIN evaluasi_awal_mpp b ON FIND_IN_SET(a.sub_unit, REPLACE(b.evaluasi_awal, ' ','')) > 0 "
                            + "WHERE b.no_rawat=?");
                    try {
                        ps2.setString(1, rs.getString(1));
                        rs2 = ps2.executeQuery();
                        while (rs2.next()) {
                            tabModeData.addRow(new Object[]{
                                false, "", rs2.getString(1), "", "", "", "", ""
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
            System.out.println("Notifikasi : " + e);
        }
    }

    public void emptTeks() {
        chkBox1.setSelected(false);
        chkBox2.setSelected(false);
        chkBox3.setSelected(false);
        chkBox4.setSelected(false);
        chkBox5.setSelected(false);
        chkBox6.setSelected(false);
        chkBox7.setSelected(false);
        chkBox8.setSelected(false);
        chkBox9.setSelected(false);
        chkBox10.setSelected(false);
        textBox1.setText("");
        textBox2.setText("");
        textBox3.setText("");
        textBox4.setText("");
        textBox5.setText("");
        textBox6.setText("");
        textBox7.setText("");
        textBox8.setText("");
        textBox9.setText("");
        textBox10.setText("");
        TCari.setText("");
        TCari.requestFocus();
    }

    private void getData() {

    }

    private void isRawat() {
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ", TNoRM, TNoRw.getText());
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
//        Sequel.cariIsi("select jk from pasien where no_rkm_medis=? ", Jk, TNoRM.getText());
//        Sequel.cariIsi("select umur from pasien where no_rkm_medis=?", Umur, TNoRM.getText());
    }

    public void setNoRm(String norwt, String posisi) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        this.status = posisi;
        isRawat();
        isPsien();
    }

    public void setPasien(String pasien) {
        TCari.setText(pasien);
    }

    public void total() {
        tTotal.setText(Valid.SetAngka(Valid.SetAngka(textBox1.getText()) + Valid.SetAngka(textBox2.getText()) + Valid.SetAngka(textBox3.getText())
                + Valid.SetAngka(textBox4.getText()) + Valid.SetAngka(textBox5.getText()) + Valid.SetAngka(textBox6.getText()) + Valid.SetAngka(textBox7.getText())
                + Valid.SetAngka(textBox8.getText()) + Valid.SetAngka(textBox9.getText()) + Valid.SetAngka(textBox10.getText())) + "");

    }

    public void isReset() {
        jml = tbForm.getRowCount();
        for (i = 0; i < jml; i++) {
            tbForm.setValueAt(false, i, 0);
        }
        jml = tbForm1.getRowCount();
        for (i = 0; i < jml; i++) {
            tbForm1.setValueAt(false, i, 0);
        }
        jml = tbForm2.getRowCount();
        for (i = 0; i < jml; i++) {
            tbForm2.setValueAt(false, i, 0);
        }
        jml = tbForm3.getRowCount();
        for (i = 0; i < jml; i++) {
            tbForm3.setValueAt(false, i, 0);
        }
    }

    public void isForm() {
        try {
            ps = koneksi.prepareStatement("select temp2 from temporary_skrining where temp1=? order by temp2 ASC");
            try {
                ps.setString(1, TNoRw.getText());
                rs = ps.executeQuery();
                while (rs.next()) {
                    dx1 = rs.getString(1) + "," + dx1;
                    evaluasiawal.setText(dx1);
                    Sequel.mengedit("evaluasi_awal_mpp", "no_rawat='" + TNoRw.getText() + "'", "evaluasi_awal='" + evaluasiawal.getText() + "'");
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
    }

}
