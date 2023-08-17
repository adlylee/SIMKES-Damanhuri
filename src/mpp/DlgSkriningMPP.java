package mpp;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;

public class DlgSkriningMPP extends javax.swing.JDialog {

    private final DefaultTableModel tabMode, tabModeData, tabModeData2, tabModeForm;
    private int i = 0, jml = 0, index = 0, totalSelected = 0;
    private String[] header, sub, namasub, nama;
    private boolean[] pilih;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private PreparedStatement ps, ps2, ps3, ps4;
    private ResultSet rs, rs2, rs3, rs4;
    private String norawatibu = "", bangsal = "", kamar = "", namakamar, status = "", kelas = "", periksaceklist = "", list = "", total = "", norm = "",pilihan="";
    public DlgCariPetugas petugas = new DlgCariPetugas(null, false);
    public DlgCariPetugas petugas2 = new DlgCariPetugas(null, false);
    private BridgingWA kirimwa = new BridgingWA();

    /**
     * Creates new form
     *
     * @param parent
     * @param modal
     */
    public DlgSkriningMPP(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabModeData = new DefaultTableModel(null, new Object[]{"P", "No. Rawat", "Pasien", "Umur", "Kamar", "Tanggal", "Jumlah Ceklist", "Catatan", "Petugas"}) {
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
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbData.setModel(tabModeData);
        tbData.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = tbData.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(35);
            } else if (i == 1) {
                column.setPreferredWidth(105);
            } else if (i == 2) {
                column.setPreferredWidth(300);
            } else if (i == 3) {
                column.setPreferredWidth(40);
            } else if (i == 4) {
                column.setPreferredWidth(165);
            } else if (i == 5) {
                column.setPreferredWidth(70);
            } else if (i == 6) {
                column.setPreferredWidth(80);
            } else if (i == 7) {
                column.setPreferredWidth(120);
            } else if (i == 8) {
                column.setPreferredWidth(150);
            }
        }
        tbData.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeData2 = new DefaultTableModel(null, new Object[]{"No. Rawat", "Pasien", "Kamar", "Tanggal", "Jam", "Catatan Implementasi", "Evaluasi/Terminasi", "MPP"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbData1.setModel(tabModeData2);
        tbData1.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbData1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 8; i++) {
            TableColumn column = tbData1.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(105);
            } else if (i == 1) {
                column.setPreferredWidth(300);
            } else if (i == 2) {
                column.setPreferredWidth(165);
            } else if (i == 3) {
                column.setPreferredWidth(70);
            } else if (i == 4) {
                column.setPreferredWidth(60);
            } else if (i == 5) {
                column.setPreferredWidth(200);
            } else if (i == 6) {
                column.setPreferredWidth(200);
            } else if (i == 7) {
                column.setPreferredWidth(150);
            }
        }
        tbData1.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeForm = new DefaultTableModel(null, new Object[]{
            "No", "Evaluasi Awal", ""
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbFormA.setModel(tabModeForm);
        tbFormA.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbFormA.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbFormA.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(35);
            } else if (i == 1) {
                column.setPreferredWidth(150);
            } else if (i == 2) {
                column.setPreferredWidth(730);
            }
        }
        tbFormA.setDefaultRenderer(Object.class, new WarnaTable());

        tabMode = new DefaultTableModel(null, new Object[]{"P", "Header", "No", "Sub Unit", "Nama", ""}) {
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
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbForm.setModel(tabMode);
        tbForm.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbForm.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 6; i++) {
            TableColumn column = tbForm.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(35);
            } else if (i == 1) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 2) {
                column.setPreferredWidth(35);
            } else if (i == 3) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 4) {
                column.setPreferredWidth(300);
            } else if (i == 5) {
                column.setPreferredWidth(750);
            }
        }
        tbForm.setDefaultRenderer(Object.class, new WarnaTable());

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
                if (var.getform().equals("DlgSkriningMPP")) {
                    if (petugas.getTable().getSelectedRow() != -1) {
                        kdptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                        nmptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                        kdptg.requestFocus();
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

        petugas2.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("DlgSkriningMPP")) {
                    if (petugas2.getTable().getSelectedRow() != -1) {
                        kdptg1.setText(petugas2.getTable().getValueAt(petugas2.getTable().getSelectedRow(), 0).toString());
                        nmptg1.setText(petugas2.getTable().getValueAt(petugas2.getTable().getSelectedRow(), 1).toString());
                        kdptg1.requestFocus();
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
        isForm();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        WindowEvaluasiAwal = new javax.swing.JDialog();
        internalFrame4 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbFormA = new widget.Table();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoResepUbah = new widget.TextBox();
        BtnPrint = new widget.Button();
        BtnKeluar1 = new widget.Button();
        WindowCatatan = new javax.swing.JDialog();
        internalFrame7 = new widget.InternalFrame();
        BtnCloseIn4 = new widget.Button();
        BtnSimpan4 = new widget.Button();
        jLabel11 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        catatan = new widget.TextArea();
        jLabel4 = new widget.Label();
        TNoRM1 = new widget.TextBox();
        TPasien1 = new widget.TextBox();
        TSkriningCeklist = new widget.TextBox();
        TJam = new widget.TextBox();
        Popup1 = new javax.swing.JPopupMenu();
        ppCetakImplementasi = new javax.swing.JMenuItem();
        ppCetakEvaluasi = new javax.swing.JMenuItem();
        Popup2 = new javax.swing.JPopupMenu();
        MnInputEvaluasiAwal = new javax.swing.JMenuItem();
        MnHasilEvaluasiAwal = new javax.swing.JMenuItem();
        MnSkriningCeklist = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        jPanel2 = new javax.swing.JPanel();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnEdit = new widget.Button();
        BtnHapus = new widget.Button();
        BtnAll = new widget.Button();
        BtnKirim = new widget.Button();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        BtnPrint1 = new widget.Button();
        panelisi2 = new widget.panelisi();
        label8 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        TabInput = new javax.swing.JTabbedPane();
        ScrollTriase1 = new widget.ScrollPane();
        FormTriase1 = new widget.InternalFrame();
        FormInput8 = new widget.PanelBiasa();
        FormInput12 = new widget.PanelBiasa();
        jLabel13 = new widget.Label();
        tTotal = new widget.TextBox();
        jLabel27 = new widget.Label();
        jLabel28 = new widget.Label();
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
        jLabel16 = new widget.Label();
        jLabel17 = new widget.Label();
        jLabel18 = new widget.Label();
        jLabel19 = new widget.Label();
        jLabel20 = new widget.Label();
        jLabel23 = new widget.Label();
        jLabel24 = new widget.Label();
        jLabel25 = new widget.Label();
        jLabel26 = new widget.Label();
        jLabel29 = new widget.Label();
        TabInput2 = new javax.swing.JTabbedPane();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbData = new widget.Table();
        ScrollTriase2 = new widget.ScrollPane();
        FormTriase2 = new widget.InternalFrame();
        FormInput9 = new widget.PanelBiasa();
        FormInput13 = new widget.PanelBiasa();
        Scroll1 = new widget.ScrollPane();
        tbForm = new widget.Table();
        jLabel30 = new widget.Label();
        TDirawat = new widget.TextBox();
        jLabel31 = new widget.Label();
        TFinansial = new widget.TextBox();
        jLabel37 = new widget.Label();
        TAsuransi = new widget.TextBox();
        jLabel38 = new widget.Label();
        TRiwayatObat = new widget.TextBox();
        jLabel39 = new widget.Label();
        TRiwayatTrauma = new widget.TextBox();
        jLabel40 = new widget.Label();
        TAspekLegal = new widget.TextBox();
        scrollPane4 = new widget.ScrollPane();
        TIdentifikasi = new widget.TextArea();
        jLabel12 = new widget.Label();
        scrollPane6 = new widget.ScrollPane();
        TPerencanaan = new widget.TextArea();
        jLabel41 = new widget.Label();
        jLabel15 = new widget.Label();
        jLabel33 = new widget.Label();
        TDirawat1 = new widget.TextBox();
        internalFrame5 = new widget.InternalFrame();
        Scroll5 = new widget.ScrollPane();
        tbData1 = new widget.Table();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        panelGlass12 = new widget.panelisi();
        jLabel8 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        Implementasi = new widget.TextArea();
        jLabel10 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        Evaluasi = new widget.TextArea();
        panelGlass14 = new widget.panelisi();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel9 = new widget.Label();
        Tanggal = new widget.Tanggal();
        jLabel14 = new widget.Label();
        kdptg = new widget.TextBox();
        nmptg = new widget.TextBox();
        BtnSeekPetugas2 = new widget.Button();
        jLabel22 = new widget.Label();
        kdptg1 = new widget.TextBox();
        nmptg1 = new widget.TextBox();
        BtnSeekPetugas3 = new widget.Button();

        WindowEvaluasiAwal.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowEvaluasiAwal.setName("WindowEvaluasiAwal"); // NOI18N
        WindowEvaluasiAwal.setUndecorated(true);
        WindowEvaluasiAwal.setResizable(false);

        internalFrame4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Form A Evaluasi Awal Case Manager/MPP  ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbFormA.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbFormA.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbFormA.setName("tbFormA"); // NOI18N
        tbFormA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbFormAKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbFormA);

        internalFrame4.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label15.setText("No. Rawat :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);

        NoResepUbah.setEditable(false);
        NoResepUbah.setName("NoResepUbah"); // NOI18N
        NoResepUbah.setPreferredSize(new java.awt.Dimension(180, 23));
        NoResepUbah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoResepUbahKeyPressed(evt);
            }
        });
        panelisi3.add(NoResepUbah);

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
        panelisi3.add(BtnPrint);

        BtnKeluar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar1.setMnemonic('K');
        BtnKeluar1.setText("Keluar");
        BtnKeluar1.setToolTipText("Alt+K");
        BtnKeluar1.setName("BtnKeluar1"); // NOI18N
        BtnKeluar1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar1ActionPerformed(evt);
            }
        });
        panelisi3.add(BtnKeluar1);

        internalFrame4.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        WindowEvaluasiAwal.getContentPane().add(internalFrame4, java.awt.BorderLayout.CENTER);

        WindowCatatan.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowCatatan.setName("WindowCatatan"); // NOI18N
        WindowCatatan.setUndecorated(true);
        WindowCatatan.setResizable(false);

        internalFrame7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Catatan Skrining Ceklist ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame7.setName("internalFrame7"); // NOI18N
        internalFrame7.setLayout(null);

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
        internalFrame7.add(BtnCloseIn4);
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
        internalFrame7.add(BtnSimpan4);
        BtnSimpan4.setBounds(340, 140, 100, 30);

        jLabel11.setText("Keterangan :");
        jLabel11.setName("jLabel11"); // NOI18N
        internalFrame7.add(jLabel11);
        jLabel11.setBounds(0, 72, 100, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        catatan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        catatan.setColumns(20);
        catatan.setRows(5);
        catatan.setName("catatan"); // NOI18N
        scrollPane3.setViewportView(catatan);

        internalFrame7.add(scrollPane3);
        scrollPane3.setBounds(105, 72, 410, 38);

        jLabel4.setText("No.R.M. :");
        jLabel4.setName("jLabel4"); // NOI18N
        internalFrame7.add(jLabel4);
        jLabel4.setBounds(0, 30, 100, 23);

        TNoRM1.setEditable(false);
        TNoRM1.setHighlighter(null);
        TNoRM1.setName("TNoRM1"); // NOI18N
        internalFrame7.add(TNoRM1);
        TNoRM1.setBounds(105, 30, 80, 23);

        TPasien1.setEditable(false);
        TPasien1.setHighlighter(null);
        TPasien1.setName("TPasien1"); // NOI18N
        TPasien1.setPreferredSize(new java.awt.Dimension(25, 28));
        internalFrame7.add(TPasien1);
        TPasien1.setBounds(187, 30, 330, 23);

        WindowCatatan.getContentPane().add(internalFrame7, java.awt.BorderLayout.CENTER);

        TSkriningCeklist.setHighlighter(null);
        TSkriningCeklist.setName("TSkriningCeklist"); // NOI18N

        TJam.setHighlighter(null);
        TJam.setName("TJam"); // NOI18N

        Popup1.setName("Popup1"); // NOI18N

        ppCetakImplementasi.setBackground(new java.awt.Color(255, 255, 254));
        ppCetakImplementasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppCetakImplementasi.setForeground(new java.awt.Color(70, 70, 70));
        ppCetakImplementasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppCetakImplementasi.setText("Cetak Implementasi");
        ppCetakImplementasi.setName("ppCetakImplementasi"); // NOI18N
        ppCetakImplementasi.setPreferredSize(new java.awt.Dimension(200, 28));
        ppCetakImplementasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppCetakImplementasiActionPerformed(evt);
            }
        });
        Popup1.add(ppCetakImplementasi);

        ppCetakEvaluasi.setBackground(new java.awt.Color(255, 255, 254));
        ppCetakEvaluasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppCetakEvaluasi.setForeground(new java.awt.Color(70, 70, 70));
        ppCetakEvaluasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppCetakEvaluasi.setText("Cetak Evaluasi");
        ppCetakEvaluasi.setName("ppCetakEvaluasi"); // NOI18N
        ppCetakEvaluasi.setPreferredSize(new java.awt.Dimension(200, 28));
        ppCetakEvaluasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppCetakEvaluasiActionPerformed(evt);
            }
        });
        Popup1.add(ppCetakEvaluasi);

        Popup2.setName("Popup2"); // NOI18N

        MnInputEvaluasiAwal.setBackground(new java.awt.Color(255, 255, 254));
        MnInputEvaluasiAwal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnInputEvaluasiAwal.setForeground(new java.awt.Color(70, 70, 70));
        MnInputEvaluasiAwal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnInputEvaluasiAwal.setText("Input Evaluasi Awal");
        MnInputEvaluasiAwal.setName("MnInputEvaluasiAwal"); // NOI18N
        MnInputEvaluasiAwal.setPreferredSize(new java.awt.Dimension(200, 28));
        MnInputEvaluasiAwal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnInputEvaluasiAwalActionPerformed(evt);
            }
        });
        Popup2.add(MnInputEvaluasiAwal);

        MnHasilEvaluasiAwal.setBackground(new java.awt.Color(255, 255, 254));
        MnHasilEvaluasiAwal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHasilEvaluasiAwal.setForeground(new java.awt.Color(70, 70, 70));
        MnHasilEvaluasiAwal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHasilEvaluasiAwal.setText("Lihat Hasil Evaluasi Awal");
        MnHasilEvaluasiAwal.setName("MnHasilEvaluasiAwal"); // NOI18N
        MnHasilEvaluasiAwal.setPreferredSize(new java.awt.Dimension(200, 28));
        MnHasilEvaluasiAwal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHasilEvaluasiAwalActionPerformed(evt);
            }
        });
        Popup2.add(MnHasilEvaluasiAwal);

        MnSkriningCeklist.setBackground(new java.awt.Color(255, 255, 254));
        MnSkriningCeklist.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSkriningCeklist.setForeground(new java.awt.Color(70, 70, 70));
        MnSkriningCeklist.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSkriningCeklist.setText("Lihat Skrining Ceklist");
        MnSkriningCeklist.setName("MnSkriningCeklist"); // NOI18N
        MnSkriningCeklist.setPreferredSize(new java.awt.Dimension(200, 28));
        MnSkriningCeklist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSkriningCeklistActionPerformed(evt);
            }
        });
        Popup2.add(MnSkriningCeklist);

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Form MPP ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(44, 94));
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
        BtnEdit.setText("Ganti");
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

        BtnKirim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/edit_f2.png"))); // NOI18N
        BtnKirim.setMnemonic('R');
        BtnKirim.setText("Kirim WA");
        BtnKirim.setToolTipText("Alt+R");
        BtnKirim.setName("BtnKirim"); // NOI18N
        BtnKirim.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKirim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKirimActionPerformed(evt);
            }
        });
        panelisi1.add(BtnKirim);

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

        BtnPrint1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnPrint1.setMnemonic('M');
        BtnPrint1.setText("Cetak");
        BtnPrint1.setToolTipText("Alt+M");
        BtnPrint1.setName("BtnPrint1"); // NOI18N
        BtnPrint1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint1ActionPerformed(evt);
            }
        });
        BtnPrint1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrint1KeyPressed(evt);
            }
        });
        panelisi1.add(BtnPrint1);

        jPanel2.add(panelisi1, java.awt.BorderLayout.CENTER);

        panelisi2.setBackground(new java.awt.Color(255, 150, 255));
        panelisi2.setName("panelisi2"); // NOI18N
        panelisi2.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label8.setText("Tanggal :");
        label8.setName("label8"); // NOI18N
        label8.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi2.add(label8);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-08-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-08-2023" }));
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

        jPanel2.add(panelisi2, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

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

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Keluhan pasien teratasi");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput12.add(jLabel13);
        jLabel13.setBounds(60, 90, 400, 23);

        tTotal.setHighlighter(null);
        tTotal.setName("tTotal"); // NOI18N
        FormInput12.add(tTotal);
        tTotal.setBounds(458, 390, 40, 23);

        jLabel27.setText("Jumlah");
        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput12.add(jLabel27);
        jLabel27.setBounds(260, 390, 180, 23);

        jLabel28.setText("ADMINISTRASI");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput12.add(jLabel28);
        jLabel28.setBounds(0, 210, 110, 23);

        jLabel32.setText("KEBUTUHAN PASIEN");
        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput12.add(jLabel32);
        jLabel32.setBounds(0, 0, 130, 23);

        chkBox10.setBorder(null);
        chkBox10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkBox10.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkBox10.setName("chkBox10"); // NOI18N
        FormInput12.add(chkBox10);
        chkBox10.setBounds(470, 360, 20, 23);

        chkBox1.setBorder(null);
        chkBox1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkBox1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkBox1.setName("chkBox1"); // NOI18N
        FormInput12.add(chkBox1);
        chkBox1.setBounds(470, 60, 20, 23);

        chkBox2.setBorder(null);
        chkBox2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkBox2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkBox2.setName("chkBox2"); // NOI18N
        FormInput12.add(chkBox2);
        chkBox2.setBounds(470, 90, 20, 23);

        chkBox3.setBorder(null);
        chkBox3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkBox3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkBox3.setName("chkBox3"); // NOI18N
        FormInput12.add(chkBox3);
        chkBox3.setBounds(470, 120, 20, 23);

        chkBox4.setBorder(null);
        chkBox4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkBox4.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkBox4.setName("chkBox4"); // NOI18N
        FormInput12.add(chkBox4);
        chkBox4.setBounds(470, 150, 20, 23);

        chkBox5.setBorder(null);
        chkBox5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkBox5.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkBox5.setName("chkBox5"); // NOI18N
        FormInput12.add(chkBox5);
        chkBox5.setBounds(470, 180, 20, 23);

        chkBox6.setBorder(null);
        chkBox6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkBox6.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkBox6.setName("chkBox6"); // NOI18N
        FormInput12.add(chkBox6);
        chkBox6.setBounds(470, 240, 20, 23);

        chkBox7.setBorder(null);
        chkBox7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkBox7.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkBox7.setName("chkBox7"); // NOI18N
        FormInput12.add(chkBox7);
        chkBox7.setBounds(470, 270, 20, 23);

        chkBox8.setBorder(null);
        chkBox8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkBox8.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkBox8.setName("chkBox8"); // NOI18N
        FormInput12.add(chkBox8);
        chkBox8.setBounds(470, 300, 20, 23);

        chkBox9.setBorder(null);
        chkBox9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkBox9.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        chkBox9.setName("chkBox9"); // NOI18N
        FormInput12.add(chkBox9);
        chkBox9.setBounds(470, 330, 20, 23);

        jLabel16.setText("PELAYANAN");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput12.add(jLabel16);
        jLabel16.setBounds(0, 30, 100, 23);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("Perawat memberikan asuhan keperawatan");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput12.add(jLabel17);
        jLabel17.setBounds(60, 120, 400, 23);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Obat-obatan terdistribusi ke pasien");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput12.add(jLabel18);
        jLabel18.setBounds(60, 60, 400, 23);

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel19.setText("Pasien mendapatkan terapi yang sesuai dengan kebutuhannya");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput12.add(jLabel19);
        jLabel19.setBounds(60, 180, 400, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("Pasien divisite oleh dokter penanggung jawab");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput12.add(jLabel20);
        jLabel20.setBounds(60, 150, 400, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("Kelengkapan status pasien terisi lengkap");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput12.add(jLabel23);
        jLabel23.setBounds(60, 360, 400, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("Jaminan kesehatan berobat pasien aktif");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput12.add(jLabel24);
        jLabel24.setBounds(60, 240, 400, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("Surat Elegibilitas Pasien (SEP) sudah terbit ketika pasien mendaftar");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput12.add(jLabel25);
        jLabel25.setBounds(60, 270, 400, 23);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText("Bilingan pasien disesuaikan dengan jaminan kelas pasien");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput12.add(jLabel26);
        jLabel26.setBounds(60, 300, 400, 23);

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel29.setText("Pasien jaminan umum mendapatkan rincian kwitansi perawatan dan pengobatan");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput12.add(jLabel29);
        jLabel29.setBounds(60, 330, 400, 23);

        FormInput8.add(FormInput12, java.awt.BorderLayout.CENTER);

        FormTriase1.add(FormInput8, java.awt.BorderLayout.CENTER);

        ScrollTriase1.setViewportView(FormTriase1);

        TabInput.addTab("Perawat", ScrollTriase1);

        TabInput2.setBackground(new java.awt.Color(255, 255, 253));
        TabInput2.setForeground(new java.awt.Color(70, 70, 70));
        TabInput2.setName("TabInput2"); // NOI18N
        TabInput2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabInput2MouseClicked(evt);
            }
        });

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout());

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbData.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbData.setName("tbData"); // NOI18N
        tbData.setComponentPopupMenu(Popup2);
        tbData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbDataMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataMouseClicked(evt);
            }
        });
        tbData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbDataKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbData);

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        TabInput2.addTab("Data Pasien", internalFrame3);

        ScrollTriase2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)));
        ScrollTriase2.setName("ScrollTriase2"); // NOI18N
        ScrollTriase2.setOpaque(true);
        ScrollTriase2.setPreferredSize(new java.awt.Dimension(702, 850));

        FormTriase2.setBorder(null);
        FormTriase2.setName("FormTriase2"); // NOI18N
        FormTriase2.setPreferredSize(new java.awt.Dimension(690, 500));
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
        Scroll1.setViewportView(tbForm);

        FormInput13.add(Scroll1);
        Scroll1.setBounds(0, 0, 850, 500);

        jLabel30.setText("Pernah dirawat di RS :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput13.add(jLabel30);
        jLabel30.setBounds(890, 40, 140, 23);

        TDirawat.setHighlighter(null);
        TDirawat.setName("TDirawat"); // NOI18N
        FormInput13.add(TDirawat);
        TDirawat.setBounds(1035, 40, 175, 23);

        jLabel31.setText("Finansial :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput13.add(jLabel31);
        jLabel31.setBounds(890, 100, 140, 23);

        TFinansial.setHighlighter(null);
        TFinansial.setName("TFinansial"); // NOI18N
        FormInput13.add(TFinansial);
        TFinansial.setBounds(1035, 100, 175, 23);

        jLabel37.setText("Asuransi :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput13.add(jLabel37);
        jLabel37.setBounds(890, 130, 140, 23);

        TAsuransi.setHighlighter(null);
        TAsuransi.setName("TAsuransi"); // NOI18N
        FormInput13.add(TAsuransi);
        TAsuransi.setBounds(1035, 130, 175, 23);

        jLabel38.setText("Riwayat penggunaan obat :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput13.add(jLabel38);
        jLabel38.setBounds(890, 160, 140, 23);

        TRiwayatObat.setHighlighter(null);
        TRiwayatObat.setName("TRiwayatObat"); // NOI18N
        FormInput13.add(TRiwayatObat);
        TRiwayatObat.setBounds(1035, 160, 175, 23);

        jLabel39.setText("Riwayat trauma :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput13.add(jLabel39);
        jLabel39.setBounds(890, 190, 140, 23);

        TRiwayatTrauma.setHighlighter(null);
        TRiwayatTrauma.setName("TRiwayatTrauma"); // NOI18N
        FormInput13.add(TRiwayatTrauma);
        TRiwayatTrauma.setBounds(1035, 190, 175, 23);

        jLabel40.setText("Aspek legal :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput13.add(jLabel40);
        jLabel40.setBounds(890, 220, 140, 23);

        TAspekLegal.setHighlighter(null);
        TAspekLegal.setName("TAspekLegal"); // NOI18N
        FormInput13.add(TAspekLegal);
        TAspekLegal.setBounds(1035, 220, 175, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        TIdentifikasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TIdentifikasi.setColumns(20);
        TIdentifikasi.setRows(5);
        TIdentifikasi.setName("TIdentifikasi"); // NOI18N
        scrollPane4.setViewportView(TIdentifikasi);

        FormInput13.add(scrollPane4);
        scrollPane4.setBounds(920, 310, 290, 60);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("II. Assesment");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput13.add(jLabel12);
        jLabel12.setBounds(890, 10, 240, 23);

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane6.setName("scrollPane6"); // NOI18N

        TPerencanaan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TPerencanaan.setColumns(20);
        TPerencanaan.setRows(5);
        TPerencanaan.setName("TPerencanaan"); // NOI18N
        scrollPane6.setViewportView(TPerencanaan);

        FormInput13.add(scrollPane6);
        scrollPane6.setBounds(920, 440, 290, 60);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel41.setText("IV. Perencanaan Manajemen Pelayanan Pasien");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput13.add(jLabel41);
        jLabel41.setBounds(890, 410, 240, 23);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("III. Identifikasi Masalah-Resiko");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput13.add(jLabel15);
        jLabel15.setBounds(890, 280, 240, 23);

        jLabel33.setText("Perilaku psikososiokultural :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput13.add(jLabel33);
        jLabel33.setBounds(890, 70, 140, 23);

        TDirawat1.setHighlighter(null);
        TDirawat1.setName("TDirawat1"); // NOI18N
        FormInput13.add(TDirawat1);
        TDirawat1.setBounds(1035, 70, 175, 23);

        FormInput9.add(FormInput13, java.awt.BorderLayout.CENTER);

        FormTriase2.add(FormInput9, java.awt.BorderLayout.CENTER);

        ScrollTriase2.setViewportView(FormTriase2);

        TabInput2.addTab("Input Evaluasi Awal", ScrollTriase2);

        internalFrame5.setBorder(null);
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout());

        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);

        tbData1.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbData1.setName("tbData1"); // NOI18N
        tbData1.setComponentPopupMenu(Popup1);
        tbData1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbData1MouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbData1MouseClicked(evt);
            }
        });
        tbData1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbData1KeyPressed(evt);
            }
        });
        Scroll5.setViewportView(tbData1);

        internalFrame5.add(Scroll5, java.awt.BorderLayout.CENTER);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 90));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
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
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        panelGlass12.setName("panelGlass12"); // NOI18N
        panelGlass12.setPreferredSize(new java.awt.Dimension(44, 104));
        panelGlass12.setLayout(null);

        jLabel8.setText("Catatan Implementasi :");
        jLabel8.setName("jLabel8"); // NOI18N
        panelGlass12.add(jLabel8);
        jLabel8.setBounds(0, 10, 130, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        Implementasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Implementasi.setColumns(20);
        Implementasi.setRows(5);
        Implementasi.setName("Implementasi"); // NOI18N
        scrollPane2.setViewportView(Implementasi);

        panelGlass12.add(scrollPane2);
        scrollPane2.setBounds(135, 10, 320, 38);

        jLabel10.setText("Evaluasi/Terminasi :");
        jLabel10.setName("jLabel10"); // NOI18N
        panelGlass12.add(jLabel10);
        jLabel10.setBounds(460, 10, 130, 23);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        Evaluasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Evaluasi.setColumns(20);
        Evaluasi.setRows(5);
        Evaluasi.setName("Evaluasi"); // NOI18N
        scrollPane5.setViewportView(Evaluasi);

        panelGlass12.add(scrollPane5);
        scrollPane5.setBounds(595, 10, 320, 38);

        PanelInput.add(panelGlass12, java.awt.BorderLayout.CENTER);

        internalFrame5.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        TabInput2.addTab("Form Implementasi & Evaluasi/Terminasi", internalFrame5);

        TabInput.addTab("MPP", TabInput2);

        internalFrame1.add(TabInput, java.awt.BorderLayout.CENTER);

        panelGlass14.setName("panelGlass14"); // NOI18N
        panelGlass14.setPreferredSize(new java.awt.Dimension(44, 84));
        panelGlass14.setLayout(null);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        jLabel3.setPreferredSize(null);
        panelGlass14.add(jLabel3);
        jLabel3.setBounds(0, 10, 90, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        panelGlass14.add(TNoRw);
        TNoRw.setBounds(95, 10, 125, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        panelGlass14.add(TNoRM);
        TNoRM.setBounds(222, 10, 80, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        panelGlass14.add(TPasien);
        TPasien.setBounds(304, 10, 260, 23);

        jLabel9.setText("Tanggal :");
        jLabel9.setName("jLabel9"); // NOI18N
        panelGlass14.add(jLabel9);
        jLabel9.setBounds(0, 40, 90, 23);

        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-08-2023" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        panelGlass14.add(Tanggal);
        Tanggal.setBounds(95, 40, 125, 23);

        jLabel14.setText("Perawat/Bidan :");
        jLabel14.setName("jLabel14"); // NOI18N
        panelGlass14.add(jLabel14);
        jLabel14.setBounds(590, 10, 90, 23);

        kdptg.setHighlighter(null);
        kdptg.setName("kdptg"); // NOI18N
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelGlass14.add(kdptg);
        kdptg.setBounds(685, 10, 125, 23);

        nmptg.setEditable(false);
        nmptg.setBackground(new java.awt.Color(202, 202, 202));
        nmptg.setHighlighter(null);
        nmptg.setName("nmptg"); // NOI18N
        panelGlass14.add(nmptg);
        nmptg.setBounds(812, 10, 320, 23);

        BtnSeekPetugas2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekPetugas2.setMnemonic('5');
        BtnSeekPetugas2.setToolTipText("ALt+5");
        BtnSeekPetugas2.setName("BtnSeekPetugas2"); // NOI18N
        BtnSeekPetugas2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPetugas2ActionPerformed(evt);
            }
        });
        panelGlass14.add(BtnSeekPetugas2);
        BtnSeekPetugas2.setBounds(1130, 10, 28, 23);

        jLabel22.setText("MPP :");
        jLabel22.setName("jLabel22"); // NOI18N
        panelGlass14.add(jLabel22);
        jLabel22.setBounds(590, 40, 90, 23);

        kdptg1.setHighlighter(null);
        kdptg1.setName("kdptg1"); // NOI18N
        panelGlass14.add(kdptg1);
        kdptg1.setBounds(685, 40, 125, 23);

        nmptg1.setEditable(false);
        nmptg1.setBackground(new java.awt.Color(202, 202, 202));
        nmptg1.setHighlighter(null);
        nmptg1.setName("nmptg1"); // NOI18N
        panelGlass14.add(nmptg1);
        nmptg1.setBounds(812, 40, 320, 23);

        BtnSeekPetugas3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekPetugas3.setMnemonic('5');
        BtnSeekPetugas3.setToolTipText("ALt+5");
        BtnSeekPetugas3.setName("BtnSeekPetugas3"); // NOI18N
        BtnSeekPetugas3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPetugas3ActionPerformed(evt);
            }
        });
        panelGlass14.add(BtnSeekPetugas3);
        BtnSeekPetugas3.setBounds(1130, 40, 28, 23);

        internalFrame1.add(panelGlass14, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        if (TabInput.getSelectedIndex() == 1) {
            if (TabInput2.getSelectedIndex() == 0) {
                tampil2();
            }
            if (TabInput2.getSelectedIndex() == 2) {
                tampil3();
            }
        }
//        if (TabInput.getSelectedIndex() == 1) {
//            tampil2();
//        } else if (TabInput.getSelectedIndex() == 2) {
//            tampil3();
//        }
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabModeForm.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnKeluar1.requestFocus();
        } else if (tabModeForm.getRowCount() != 0) {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));

            Sequel.queryu("truncate table temporary_skrining_mpp");
            for (i = 0; i < tabModeForm.getRowCount(); i++) {
                Sequel.menyimpan("temporary_skrining_mpp", "'0','"
                        + tabModeForm.getValueAt(i, 0).toString() + "','"
                        + tabModeForm.getValueAt(i, 1).toString() + "','"
                        + tabModeForm.getValueAt(i, 2).toString() + "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''", "Data");
            }

            Valid.MyReport("rptEvaluasiAwalMPP.jrxml", "report", "::[ Laporan Evaluasi Awal ]::",
                    "select * from temporary_skrining_mpp order by no asc", param);
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
            if (TabInput.getSelectedIndex() == 0) {
                if (kdptg.getText().trim().equals("") || nmptg.getText().trim().equals("")) {
                    Valid.textKosong(TNoRw, "Perawat/Bidan");
                } else {
                    total();
                    if ((!tTotal.getText().trim().equals(""))) {
                        insertCeklist();
                        if (Sequel.menyimpantf("evaluasi_awal_mpp", "?,?,?,?,?,?,?,?", "Data", 8, new String[]{
                            TNoRw.getText(), Valid.SetTgl(DTPCari1.getSelectedItem() + ""), "", "", periksaceklist, "", total, kdptg.getText()
                        }) == true) {
                            if (totalSelected < 7) {
                                TNoRM1.setText(TNoRM.getText());
                                TPasien1.setText(TPasien.getText());
                                WindowCatatan.setSize(720, 185);
                                WindowCatatan.setLocationRelativeTo(internalFrame1);
                                WindowCatatan.setVisible(true);
                            }
                            emptTeks();
                        }
                    }
                }
            }
            if (TabInput.getSelectedIndex() == 1) {
                if (TabInput2.getSelectedIndex() == 1) {
                    if (Sequel.cariInteger("select count(no_rawat) from evaluasi_awal_mpp where no_rawat='" + TNoRw.getText() + "'") > 0) {
                        simpanform();
                        insertData();
                        isReset();
                        emptTeks();
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Data Skrining Ceklist Belum Diinput..!!!");
                    }
                }
                if (TabInput2.getSelectedIndex() == 2) {
                    if (kdptg1.getText().trim().equals("") || nmptg1.getText().trim().equals("")) {
                        Valid.textKosong(nmptg1, "Petugas MPP");
                    } else {
                        if (Sequel.cariInteger("select count(no_rawat) from evaluasi_awal_mpp where no_rawat='" + TNoRw.getText() + "'") > 0) {
                            if (Sequel.menyimpantf("implementasi_evaluasi_mpp", "?,?,?,?,?,?", "Data", 6, new String[]{
                                TNoRw.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""), Sequel.cariIsi("select current_time()"),
                                Implementasi.getText(), Evaluasi.getText(), kdptg1.getText()
                            }) == true) {
                                JOptionPane.showMessageDialog(null, "Berhasil Simpan");
                                tampil3();
                                emptTeks();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Maaf, Data Skrining Form A Evaluasi Awal Belum Diinput..!!!");
                        }
                    }
                }
            }

//            if (TabInput.getSelectedIndex() == 0) {
//                if (TabInput.getSelectedIndex() == 0) {
//                    if (kdptg.getText().trim().equals("") || nmptg.getText().trim().equals("")) {
//                        Valid.textKosong(TNoRw, "Perawat/Bidan");
//                    } else {
//                        total();
//                        if ((!tTotal.getText().trim().equals(""))) {
//                            insertCeklist();
//                            if (Sequel.menyimpantf("evaluasi_awal_mpp", "?,?,?,?,?,?,?", "Data", 8, new String[]{
//                                TNoRw.getText(), Valid.SetTgl(DTPCari1.getSelectedItem() + ""), "", "", periksaceklist, "", total, kdptg.getText()
//                            }) == true) {
//                                if (totalSelected < 7) {
//                                    TNoRM1.setText(TNoRM.getText());
//                                    TPasien1.setText(TPasien.getText());
//                                    WindowCatatan.setSize(720, 185);
//                                    WindowCatatan.setLocationRelativeTo(internalFrame1);
//                                    WindowCatatan.setVisible(true);
//                                }
//                                emptTeks();
//                            }
//                        }
//                    }
//                } else if (TabInput.getSelectedIndex() == 1) {
//                    if (Sequel.cariInteger("select count(no_rawat) from evaluasi_awal_mpp where no_rawat='" + TNoRw.getText() + "'") > 0) {
//                        simpanform();
//                        insertData();
//                        isReset();
//                        emptTeks();
//                    } else {
//                        JOptionPane.showMessageDialog(null, "Maaf, Data Skrining Ceklist Belum Diinput..!!!");
//                    }
//                }
//            } else if (TabInput.getSelectedIndex() == 2) {
//                if (kdptg1.getText().trim().equals("") || nmptg1.getText().trim().equals("")) {
//                    Valid.textKosong(nmptg1, "Petugas MPP");
//                } else {
//                    if (Sequel.cariInteger("select count(no_rawat) from evaluasi_awal_mpp where no_rawat='" + TNoRw.getText() + "'") > 0) {
//                        if (Sequel.menyimpantf("implementasi_evaluasi_mpp", "?,?,?,?,?,?", "Data", 6, new String[]{
//                            TNoRw.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""), Sequel.cariIsi("select current_time()"),
//                            Implementasi.getText(), Evaluasi.getText(), kdptg1.getText()
//                        }) == true) {
//                            JOptionPane.showMessageDialog(null, "Berhasil Simpan");
//                            tampil3();
//                            emptTeks();
//                        }
//                    } else {
//                        JOptionPane.showMessageDialog(null, "Maaf, Data Skrining Form A Evaluasi Awal Belum Diinput..!!!");
//                    }
//                }
//            }
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
//        pilihTab();
        ppCetakImplementasi.setVisible(false);
        ppCetakEvaluasi.setVisible(false);
    }//GEN-LAST:event_formWindowOpened

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if (TabInput.getSelectedIndex() == 0) {
            total();
            insertCeklist();
            if (Sequel.mengedittf("evaluasi_awal_mpp", "no_rawat=?", "skrining_ceklist=?,nilai_skrining=?", 3, new String[]{
                periksaceklist, tTotal.getText(), TNoRw.getText()
            }) == true) {
                emptTeks();
                tampil2();
            }
        }
        if (TabInput.getSelectedIndex() == 1) {
            if (TabInput2.getSelectedIndex() == 1) {
                if (Sequel.cariInteger("select count(no_rawat) from evaluasi_awal_mpp where no_rawat='" + TNoRw.getText() + "'") > 0) {
                    simpanform();
                    insertData();
                    isReset();
                    emptTeks();
                } else {
                    JOptionPane.showMessageDialog(null, "Maaf, Data Skrining Ceklist Belum Diinput..!!!");
                }
            }
            if (TabInput2.getSelectedIndex() == 2) {
                if (Sequel.mengedittf("implementasi_evaluasi_mpp", "no_rawat=? and tanggal=? and jam=?", "implementasi=?,evaluasi=?", 5, new String[]{
                    Implementasi.getText(), Evaluasi.getText(), TNoRw.getText(),
                    tbData1.getValueAt(tbData1.getSelectedRow(), 3).toString(), tbData1.getValueAt(tbData1.getSelectedRow(), 4).toString()
                }) == true) {
                    emptTeks();
                    tampil3();
                }
            }
        }

//        if (TabInput.getSelectedIndex() == 0) {
//            if (TabInput.getSelectedIndex() == 0) {
//                total();
//                insertCeklist();
//                if (Sequel.mengedittf("evaluasi_awal_mpp", "no_rawat=?", "skrining_ceklist=?,nilai_skrining=?", 3, new String[]{
//                    periksaceklist, tTotal.getText(), TNoRw.getText()
//                }) == true) {
//                    emptTeks();
//                    tampil2();
//                }
//            }
//        } else if (TabInput.getSelectedIndex() == 2) {
//            if (Sequel.mengedittf("implementasi_evaluasi_mpp", "no_rawat=? and tanggal=? and jam=?", "implementasi=?,evaluasi=?", 5, new String[]{
//                Implementasi.getText(), Evaluasi.getText(), TNoRw.getText(),
//                tbData1.getValueAt(tbData1.getSelectedRow(), 3).toString(), tbData1.getValueAt(tbData1.getSelectedRow(), 4).toString()
//            }) == true) {
//                emptTeks();
//                tampil3();
//            }
//        }
    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnSimpan, BtnPrint);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKirimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKirimActionPerformed
        if (tbData.getValueAt(tbData.getSelectedRow(), 1).toString().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
        } else {
            for (i = 0; i < tbData.getRowCount(); i++) {
                int reply = JOptionPane.showConfirmDialog(rootPane, "Apakah anda ingin mengirim whatsapp permintaan evaluasi MPP..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    if (tbData.getValueAt(i, 0).toString().equals("true")) {
                        kirimwa.sendwaMPP(tbData.getValueAt(i, 2).toString(), tbData.getValueAt(i, 4).toString(), tbData.getValueAt(i, 3).toString());
                        JOptionPane.showMessageDialog(null, "Berhasil Mengirim Whatsapp...");
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    }
                }
            }
        }
    }//GEN-LAST:event_BtnKirimActionPerformed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if (TabInput.getSelectedIndex() == 1) {
            if (TabInput2.getSelectedIndex() == 0) {
                if (tabModeData.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                } else if (TNoRw.getText().trim().equals("")) {
                    Valid.textKosong(TNoRw, "No.Rawat");
                } else {
                    Sequel.meghapus("evaluasi_awal_mpp", "no_rawat", tbData.getValueAt(tbData.getSelectedRow(), 1).toString());
                    tampil2();
                }
            }
            if (TabInput2.getSelectedIndex() == 2) {
                if (tabModeData2.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                    TNoRw.requestFocus();
                } else if (TNoRw.getText().trim().equals("")) {
                    Valid.textKosong(TNoRw, "No.Rawat");
                } else {
                    Sequel.queryu2("delete from implementasi_evaluasi_mpp where no_rawat=? and tanggal=? and jam=?", 3, new String[]{
                        TNoRw.getText(), tbData1.getValueAt(tbData1.getSelectedRow(), 3).toString(), tbData1.getValueAt(tbData1.getSelectedRow(), 4).toString()
                    });
                    emptTeks();
                    tampil3();
                }
            }
        }

//        if (TabInput.getSelectedIndex() == 1) {
//            if (tabModeData.getRowCount() == 0) {
//                JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
//                TNoRw.requestFocus();
//            } else if (TNoRw.getText().trim().equals("")) {
//                Valid.textKosong(TNoRw, "No.Rawat");
//            } else {
//                Sequel.meghapus("evaluasi_awal_mpp", "no_rawat", tbData.getValueAt(tbData.getSelectedRow(), 1).toString());
//                tampil2();
//            }
//        } else if (TabInput.getSelectedIndex() == 2) {
//            if (tabModeData2.getRowCount() == 0) {
//                JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
//                TNoRw.requestFocus();
//            } else if (TNoRw.getText().trim().equals("")) {
//                Valid.textKosong(TNoRw, "No.Rawat");
//            } else {
//                Sequel.queryu2("delete from implementasi_evaluasi_mpp where no_rawat=? and tanggal=? and jam=?", 3, new String[]{
//                    TNoRw.getText(), tbData1.getValueAt(tbData1.getSelectedRow(), 3).toString(), tbData1.getValueAt(tbData1.getSelectedRow(), 4).toString()
//                });
//                emptTeks();
//                tampil3();
//            }
//
//        }
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnEdit);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated

    }//GEN-LAST:event_formWindowActivated

    private void formWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeactivated

    }//GEN-LAST:event_formWindowDeactivated

    private void tbDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataMouseClicked
        if (tabModeData.getRowCount() != 0) {
            try {
                getData();
                emptTeks();
                getData3();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbDataMouseClicked

    private void tbDataMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataMouseReleased
        if (evt.isPopupTrigger()) {
            Popup1.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_tbDataMouseReleased

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

    private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg, kdptg.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnSeekPetugas2ActionPerformed(null);
        } else {
            Valid.pindah(evt, TNoRw, BtnSeekPetugas2);
        }
    }//GEN-LAST:event_kdptgKeyPressed

    private void BtnSeekPetugas2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekPetugas2ActionPerformed
        var.setform("DlgSkriningMPP");
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnSeekPetugas2ActionPerformed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        if (TabInput.getSelectedIndex() == 1) {
            if (TabInput2.getSelectedIndex() == 0) {
                TCari.setText("");
                tampil2();
            }
            if (TabInput2.getSelectedIndex() == 2) {
                TCari.setText("");
                tampil3();
            }
        }
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnAllActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void tbFormAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbFormAKeyPressed
        if (tbFormA.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
                i = tbFormA.getSelectedColumn();
            }
        }
    }//GEN-LAST:event_tbFormAKeyPressed

    private void NoResepUbahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoResepUbahKeyPressed
        Valid.pindah(evt, BtnKeluar, BtnSimpan);
    }//GEN-LAST:event_NoResepUbahKeyPressed

    private void BtnKeluar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar1ActionPerformed
        WindowEvaluasiAwal.dispose();
    }//GEN-LAST:event_BtnKeluar1ActionPerformed

    private void tbData1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbData1MouseClicked
        if (tabModeData2.getRowCount() != 0) {
            try {
                getData2();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbData1MouseClicked

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt, TNoRw, BtnAll);
    }//GEN-LAST:event_TanggalKeyPressed

    private void BtnCloseIn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn4ActionPerformed
        WindowCatatan.dispose();
    }//GEN-LAST:event_BtnCloseIn4ActionPerformed

    private void BtnSimpan4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan4ActionPerformed
        Sequel.mengedit("evaluasi_awal_mpp", "no_rawat=?", "catatan_skrining=?", 2, new String[]{
            catatan.getText(), TNoRw.getText()
        });
        WindowCatatan.dispose();
    }//GEN-LAST:event_BtnSimpan4ActionPerformed

    private void BtnSeekPetugas3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekPetugas3ActionPerformed
        var.setform("DlgSkriningMPP");
        petugas2.emptTeks();
        petugas2.isCek();
        petugas2.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas2.setLocationRelativeTo(internalFrame1);
        petugas2.setVisible(true);
    }//GEN-LAST:event_BtnSeekPetugas3ActionPerformed

    private void ppCetakImplementasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppCetakImplementasiActionPerformed
        if (tbData1.getSelectedRow() != -1) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (tbData1.getValueAt(tbData1.getSelectedRow(), 0).toString().trim().equals("")) {
                Valid.textKosong(TNoRw, "No.Rawat");
            } else {
                String user = var.getkode();
                Map<String, Object> param = new HashMap<>();
                norm = Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ", tbData1.getValueAt(tbData1.getSelectedRow(), 0).toString());
                param.put("norm", norm);
                param.put("namapasien", Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ", norm));
                param.put("lahir", Sequel.cariIsi("select DATE_FORMAT(tgl_lahir,'%d-%m-%Y') from pasien where no_rkm_medis=? ", norm));
                param.put("petugas", Sequel.cariIsi("select nama from petugas where nip=?", kdptg1.getText()));
                param.put("tanggal", tbData1.getValueAt(tbData1.getSelectedRow(), 3).toString());
                param.put("jam", tbData1.getValueAt(tbData1.getSelectedRow(), 4).toString());
                param.put("implementasi", tbData1.getValueAt(tbData1.getSelectedRow(), 5).toString());
                param.put("kamar", Sequel.cariIsi("select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=?", tbData1.getValueAt(tbData1.getSelectedRow(), 0).toString()));
                param.put("namars", var.getnamars());
                param.put("alamatrs", var.getalamatrs());
                param.put("kotars", var.getkabupatenrs());
                param.put("propinsirs", var.getpropinsirs());
                param.put("kontakrs", var.getkontakrs());
                param.put("emailrs", var.getemailrs());
                param.put("logo", Sequel.cariGambar("select logo from setting"));
                param.put("petugasmpp", Sequel.cariIsi("select nama from pegawai where nik=?", user));
                param.put("dpjp", Sequel.cariIsi("select GROUP_CONCAT(dokter.nm_dokter) from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat='" + TNoRw.getText() + "'"));
                Valid.MyReport("rptMPPImplementasi.jrxml", "report", "::[ Catatan Implementasi  ]::",
                        "select reg_periksa.no_rawat, concat(reg_periksa.no_rkm_medis,' ',pasien.nm_pasien) as pasien,"
                        + "concat(kamar_inap.kd_kamar,' ',bangsal.nm_bangsal) as kamar,implementasi_evaluasi_mpp.tanggal,implementasi_evaluasi_mpp.jam,implementasi_evaluasi_mpp.implementasi,"
                        + "implementasi_evaluasi_mpp.evaluasi,implementasi_evaluasi_mpp.petugas from implementasi_evaluasi_mpp, reg_periksa,pasien, kamar,bangsal, kamar_inap "
                        + "where implementasi_evaluasi_mpp.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and kamar_inap.kd_kamar=kamar.kd_kamar "
                        + "and kamar.kd_bangsal=bangsal.kd_bangsal and implementasi_evaluasi_mpp.no_rawat=kamar_inap.no_rawat and "
                        + "implementasi_evaluasi_mpp.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and implementasi_evaluasi_mpp.implementasi <> '' "
                        + "and (implementasi_evaluasi_mpp.no_rawat like '" + TCari.getText() + "' or reg_periksa.no_rkm_medis like '" + TCari.getText() + "' or pasien.nm_pasien like '" + TCari.getText() + "')");
                this.setCursor(Cursor.getDefaultCursor());

            }
            emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        } else {
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data permintaan...!!!!");
            TCari.requestFocus();
        }
    }//GEN-LAST:event_ppCetakImplementasiActionPerformed

    private void ppCetakEvaluasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppCetakEvaluasiActionPerformed
        if (tbData1.getSelectedRow() != -1) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (tbData1.getValueAt(tbData1.getSelectedRow(), 1).toString().trim().equals("")) {
                Valid.textKosong(TNoRw, "No.Rawat");
            } else {
                String user = var.getkode();
                Map<String, Object> param = new HashMap<>();
                norm = Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ", tbData1.getValueAt(tbData1.getSelectedRow(), 0).toString());
                param.put("norm", norm);
                param.put("namapasien", Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ", norm));
                param.put("lahir", Sequel.cariIsi("select DATE_FORMAT(tgl_lahir,'%d-%m-%Y') from pasien where no_rkm_medis=? ", norm));
                param.put("petugas", Sequel.cariIsi("select nama from petugas where nip=?", tbData1.getValueAt(tbData1.getSelectedRow(), 7).toString()));
                param.put("tanggal", tbData1.getValueAt(tbData1.getSelectedRow(), 3).toString());
                param.put("jam", tbData1.getValueAt(tbData1.getSelectedRow(), 4).toString());
                param.put("evaluasi", tbData1.getValueAt(tbData1.getSelectedRow(), 6).toString());
                param.put("kamar", Sequel.cariIsi("select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=?", tbData1.getValueAt(tbData1.getSelectedRow(), 0).toString()));
                param.put("namars", var.getnamars());
                param.put("alamatrs", var.getalamatrs());
                param.put("kotars", var.getkabupatenrs());
                param.put("propinsirs", var.getpropinsirs());
                param.put("kontakrs", var.getkontakrs());
                param.put("emailrs", var.getemailrs());
                param.put("logo", Sequel.cariGambar("select logo from setting"));
                param.put("petugasmpp", Sequel.cariIsi("select nama from pegawai where nik=?", user));
                param.put("dpjp", Sequel.cariIsi("select GROUP_CONCAT(dokter.nm_dokter) from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat='" + TNoRw.getText() + "'"));
                Valid.MyReport("rptMPPEvaluasi.jrxml", "report", "::[ Catatan Implementasi Dan Evaluasi Case Manager/MPP ]::",
                        "select reg_periksa.no_rawat, concat(reg_periksa.no_rkm_medis,' ',pasien.nm_pasien) as pasien,"
                        + "concat(kamar_inap.kd_kamar,' ',bangsal.nm_bangsal) as kamar,implementasi_evaluasi_mpp.tanggal,implementasi_evaluasi_mpp.jam,implementasi_evaluasi_mpp.implementasi,"
                        + "implementasi_evaluasi_mpp.evaluasi,implementasi_evaluasi_mpp.petugas from implementasi_evaluasi_mpp, reg_periksa,pasien, kamar,bangsal, kamar_inap "
                        + "where implementasi_evaluasi_mpp.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and kamar_inap.kd_kamar=kamar.kd_kamar "
                        + "and kamar.kd_bangsal=bangsal.kd_bangsal and implementasi_evaluasi_mpp.no_rawat=kamar_inap.no_rawat and "
                        + "implementasi_evaluasi_mpp.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and implementasi_evaluasi_mpp.evaluasi <> '' "
                        + "and (implementasi_evaluasi_mpp.no_rawat like '" + TCari.getText() + "' or reg_periksa.no_rkm_medis like '" + TCari.getText() + "' or pasien.nm_pasien like '" + TCari.getText() + "')");
            }
            emptTeks();
            this.setCursor(Cursor.getDefaultCursor());
        } else {
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data permintaan...!!!!");
            TCari.requestFocus();
        }
    }//GEN-LAST:event_ppCetakEvaluasiActionPerformed

    private void tbData1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbData1KeyPressed
        if (tabModeData2.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData2();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbData1KeyPressed

    private void tbData1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbData1MouseReleased
        if (evt.isPopupTrigger()) {
            Popup1.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_tbData1MouseReleased

    private void TabInput2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabInput2MouseClicked
        pilihTab();
    }//GEN-LAST:event_TabInput2MouseClicked

    private void TabInputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabInputMouseClicked
        if (TabInput.getSelectedIndex()==1) {
         pilihTab();
        }
    }//GEN-LAST:event_TabInputMouseClicked

    private void MnInputEvaluasiAwalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnInputEvaluasiAwalActionPerformed
        TabInput2.setSelectedIndex(1);
    }//GEN-LAST:event_MnInputEvaluasiAwalActionPerformed

    private void MnHasilEvaluasiAwalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHasilEvaluasiAwalActionPerformed
        if (tabModeData.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TNoRw.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Klik data yang ingin diinput terlebih dahulu...!!!!");
        } else {
            NoResepUbah.setText(tbData.getValueAt(tbData.getSelectedRow(), 1).toString());
            tampilFormA();
            WindowEvaluasiAwal.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            WindowEvaluasiAwal.setLocationRelativeTo(internalFrame1);
            WindowEvaluasiAwal.setVisible(true);
        }
    }//GEN-LAST:event_MnHasilEvaluasiAwalActionPerformed

    private void MnSkriningCeklistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSkriningCeklistActionPerformed
        TabInput.setSelectedIndex(0);
    }//GEN-LAST:event_MnSkriningCeklistActionPerformed

    private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabModeData2.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnAll.requestFocus();
        } else if (tabModeData2.getRowCount() != 0) {
            String user = var.getkode();
            Map<String, Object> param = new HashMap<>();
            param.put("lahir", Sequel.cariIsi("select DATE_FORMAT(tgl_lahir,'%d-%m-%Y') from pasien where no_rkm_medis=? ", norm));
            param.put("petugasmpp", Sequel.cariIsi("select nama from pegawai where nik=?", user));
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            param.put("dpjp", Sequel.cariIsi("select GROUP_CONCAT(dokter.nm_dokter) from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat=? ", TNoRw.getText()));
            pilihan = (String) JOptionPane.showInputDialog(null, "Silahkan pilih hasil pemeriksaan..!", "Hasil Pemeriksaan", JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Catatan Implementasi", "Catatan Evaluasi"}, "Catatan Implementasi");
            switch (pilihan) {
                case "Catatan Implementasi":
                    Valid.MyReport("rptMPPImplementasi.jrxml", "report", "::[ Catatan Implementasi ]::",
                            "select reg_periksa.no_rawat, reg_periksa.no_rkm_medis,pasien.nm_pasien, "
                            + "concat(kamar_inap.kd_kamar,' ',bangsal.nm_bangsal) as kamar,implementasi_evaluasi_mpp.tanggal,implementasi_evaluasi_mpp.jam,implementasi_evaluasi_mpp.implementasi,"
                            + "implementasi_evaluasi_mpp.evaluasi,implementasi_evaluasi_mpp.petugas, petugas.nama from implementasi_evaluasi_mpp, reg_periksa,pasien, kamar,bangsal, kamar_inap "
                            + "where implementasi_evaluasi_mpp.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and kamar_inap.kd_kamar=kamar.kd_kamar "
                            + "and kamar.kd_bangsal=bangsal.kd_bangsal and implementasi_evaluasi_mpp.no_rawat=kamar_inap.no_rawat and implementasi_evaluasi_mpp.petugas=petugas.nip and "
                            + "implementasi_evaluasi_mpp.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' "
                            + "and (implementasi_evaluasi_mpp.no_rawat like '%" + TCari.getText().trim() + "%' or reg_periksa.no_rkm_medis like '%" + TCari.getText().trim() + "%' or pasien.nm_pasien like '%" + TCari.getText().trim() + "%')", param);
                    break;
                case "Catatan Evaluasi":
                    Valid.MyReport("rptMPPEvaluasi.jrxml", "report", "::[ Catatan Evaluasi ]::",
                            "select reg_periksa.no_rawat, reg_periksa.no_rkm_medis,pasien.nm_pasien,"
                            + "concat(kamar_inap.kd_kamar,' ',bangsal.nm_bangsal) as kamar,implementasi_evaluasi_mpp.tanggal,implementasi_evaluasi_mpp.jam,implementasi_evaluasi_mpp.implementasi,"
                            + "implementasi_evaluasi_mpp.evaluasi,implementasi_evaluasi_mpp.petugas, petugas.nama from implementasi_evaluasi_mpp, reg_periksa,pasien, kamar,bangsal, kamar_inap "
                            + "where implementasi_evaluasi_mpp.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and kamar_inap.kd_kamar=kamar.kd_kamar "
                            + "and kamar.kd_bangsal=bangsal.kd_bangsal and implementasi_evaluasi_mpp.no_rawat=kamar_inap.no_rawat and implementasi_evaluasi_mpp.petugas=petugas.nip and "
                            + "implementasi_evaluasi_mpp.tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' "
                            + "and (implementasi_evaluasi_mpp.no_rawat like '%" + TCari.getText().trim() + "%' or reg_periksa.no_rkm_medis like '%" + TCari.getText().trim() + "%' or pasien.nm_pasien like '%" + TCari.getText().trim() + "%')", param);
                    break;
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    private void BtnPrint1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrint1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrint1ActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnAll, BtnAll);
        }
    }//GEN-LAST:event_BtnPrint1KeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgSkriningMPP dialog = new DlgSkriningMPP(new javax.swing.JFrame(), true);
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
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluar1;
    private widget.Button BtnKirim;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint1;
    private widget.Button BtnSeekPetugas2;
    private widget.Button BtnSeekPetugas3;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpan4;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextArea Evaluasi;
    private widget.PanelBiasa FormInput12;
    private widget.PanelBiasa FormInput13;
    private widget.PanelBiasa FormInput8;
    private widget.PanelBiasa FormInput9;
    private widget.InternalFrame FormTriase1;
    private widget.InternalFrame FormTriase2;
    private widget.TextArea Implementasi;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnHasilEvaluasiAwal;
    private javax.swing.JMenuItem MnInputEvaluasiAwal;
    private javax.swing.JMenuItem MnSkriningCeklist;
    private widget.TextBox NoResepUbah;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JPopupMenu Popup1;
    private javax.swing.JPopupMenu Popup2;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll5;
    private widget.ScrollPane ScrollTriase1;
    private widget.ScrollPane ScrollTriase2;
    private widget.TextBox TAspekLegal;
    private widget.TextBox TAsuransi;
    private widget.TextBox TCari;
    private widget.TextBox TDirawat;
    private widget.TextBox TDirawat1;
    private widget.TextBox TFinansial;
    private widget.TextArea TIdentifikasi;
    private widget.TextBox TJam;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRM1;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TPasien1;
    private widget.TextArea TPerencanaan;
    private widget.TextBox TRiwayatObat;
    private widget.TextBox TRiwayatTrauma;
    private widget.TextBox TSkriningCeklist;
    private javax.swing.JTabbedPane TabInput;
    private javax.swing.JTabbedPane TabInput2;
    private widget.Tanggal Tanggal;
    private javax.swing.JDialog WindowCatatan;
    private javax.swing.JDialog WindowEvaluasiAwal;
    private widget.TextArea catatan;
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
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame7;
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
    private widget.Label jLabel3;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel4;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel2;
    private widget.TextBox kdptg;
    private widget.TextBox kdptg1;
    private widget.Label label15;
    private widget.Label label8;
    private widget.Label label9;
    private widget.TextBox nmptg;
    private widget.TextBox nmptg1;
    private widget.panelisi panelGlass12;
    private widget.panelisi panelGlass14;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi2;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppCetakEvaluasi;
    private javax.swing.JMenuItem ppCetakImplementasi;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.TextBox tTotal;
    private widget.Table tbData;
    private widget.Table tbData1;
    private widget.Table tbForm;
    private widget.Table tbFormA;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        try {
            jml = 0;
            for (i = 0; i < tbForm.getRowCount(); i++) {
                if (tbForm.getValueAt(i, 0).toString().equals("true")) {
                    jml++;
                }
            }

            pilih = new boolean[jml];
            header = new String[jml];
            sub = new String[jml];
            namasub = new String[jml];
            nama = new String[jml];

            index = 0;
            for (i = 0; i < tbForm.getRowCount(); i++) {
                if (tbForm.getValueAt(i, 0).toString().equals("true")) {
                    pilih[index] = true;
                    header[index] = tbForm.getValueAt(i, 1).toString();
                    sub[index] = tbForm.getValueAt(i, 2).toString();
                    namasub[index] = tbForm.getValueAt(i, 3).toString();
                    nama[index] = tbForm.getValueAt(i, 4).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabMode);

            ps = koneksi.prepareStatement("SELECT header,sub,sub_unit,nm_sub,nama FROM master_skrinning_mpp WHERE header='1' ORDER BY sub, sub_unit");
            rs = ps.executeQuery();
            tabMode.addRow(new Object[]{false, "", "", ">>", "I. Skrining Permasalahan", ""});
            while (rs.next()) {
                tabMode.addRow(new Object[]{false, rs.getString("header"), rs.getString("sub"), rs.getString("sub_unit"), rs.getString("nm_sub"), rs.getString("nama")});
            }
            rs.close();
            ps.close();

            ps2 = koneksi.prepareStatement("SELECT header, sub, sub_unit, nm_sub, nama FROM master_skrinning_mpp WHERE header='2' ORDER BY CAST(sub as UNSIGNED), sub_unit");
            rs2 = ps2.executeQuery();
            tabMode.addRow(new Object[]{false, "", "", "", "", ""});
            tabMode.addRow(new Object[]{false, "", "", ">>", "II. Assesment"});
            while (rs2.next()) {
                tabMode.addRow(new Object[]{false, rs2.getString("header"), rs2.getString("sub"), rs2.getString("sub_unit"), rs2.getString("nm_sub"), rs2.getString("nama")});
            }
            rs2.close();
            ps2.close();

            ps3 = koneksi.prepareStatement("SELECT header,sub,sub_unit,nama FROM master_skrinning_mpp WHERE header='3' ORDER BY CAST(sub_unit as UNSIGNED)");
            rs3 = ps3.executeQuery();
            tabMode.addRow(new Object[]{false, "", "", "", "", ""});
            tabMode.addRow(new Object[]{false, "", "", ">>", "III. Identifikasi Masalah-Resiko", ""});
            while (rs3.next()) {
                tabMode.addRow(new Object[]{false, rs3.getString("header"), rs3.getString("sub"), rs3.getString("sub_unit"), rs3.getString("nama"), ""});
            }
            rs3.close();
            ps3.close();

            ps4 = koneksi.prepareStatement("SELECT header,sub,sub_unit,nama FROM master_skrinning_mpp WHERE header='4' ORDER BY CAST(sub_unit as UNSIGNED)");
            rs4 = ps4.executeQuery();
            tabMode.addRow(new Object[]{false, "", "", "", "", ""});
            tabMode.addRow(new Object[]{false, "", "", ">>", "IV. Perencanaan Manajemen Pelayanan Pasien", ""});
            while (rs4.next()) {
                tabMode.addRow(new Object[]{false, rs4.getString("header"), rs4.getString("sub"), rs4.getString("sub_unit"), rs4.getString("nama"), ""});
            }
            rs4.close();
            ps4.close();

        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    private void tampil2() {
        Valid.tabelKosong(tabModeData);
        try {
            ps = koneksi.prepareStatement(
                    "select reg_periksa.no_rawat, concat(reg_periksa.no_rkm_medis,' ',pasien.nm_pasien) as pasien, concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur) as pasien, concat(kamar_inap.kd_kamar,' ',bangsal.nm_bangsal) as kamar,evaluasi_awal_mpp.tanggal,"
                    + "evaluasi_awal_mpp.nilai_skrining, petugas.nama, evaluasi_awal_mpp.catatan_skrining from evaluasi_awal_mpp, reg_periksa,pasien,petugas, kamar,bangsal, kamar_inap where evaluasi_awal_mpp.no_rawat=reg_periksa.no_rawat "
                    + "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal and petugas.nip=evaluasi_awal_mpp.petugas and "
                    + "evaluasi_awal_mpp.no_rawat=kamar_inap.no_rawat and evaluasi_awal_mpp.tanggal between ? and ? and (evaluasi_awal_mpp.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ?)");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, "%" + TCari.getText().trim() + "%");
                ps.setString(4, "%" + TCari.getText().trim() + "%");
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeData.addRow(new Object[]{false, rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(8), rs.getString(7)});
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

    private void tampil3() {
        Valid.tabelKosong(tabModeData2);
        try {
            ps = koneksi.prepareStatement("select reg_periksa.no_rawat, concat(reg_periksa.no_rkm_medis,' ',pasien.nm_pasien) as pasien,"
                    + "concat(kamar_inap.kd_kamar,' ',bangsal.nm_bangsal) as kamar,implementasi_evaluasi_mpp.tanggal,implementasi_evaluasi_mpp.jam,implementasi_evaluasi_mpp.implementasi,"
                    + "implementasi_evaluasi_mpp.evaluasi,implementasi_evaluasi_mpp.petugas from implementasi_evaluasi_mpp, reg_periksa,pasien, kamar,bangsal, kamar_inap "
                    + "where implementasi_evaluasi_mpp.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and kamar_inap.kd_kamar=kamar.kd_kamar "
                    + "and kamar.kd_bangsal=bangsal.kd_bangsal and implementasi_evaluasi_mpp.no_rawat=kamar_inap.no_rawat and "
                    + "implementasi_evaluasi_mpp.tanggal between ? and ? and (implementasi_evaluasi_mpp.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ?)");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, "%" + TCari.getText().trim() + "%");
                ps.setString(4, "%" + TCari.getText().trim() + "%");
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeData2.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)});
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

    public void tampilFormA() {
        Valid.tabelKosong(tabModeForm);
        String evaluasiAwal = Sequel.cariIsi("select evaluasi_awal from evaluasi_awal_mpp where no_rawat=?", TNoRw.getText());
        String[] ceklist = evaluasiAwal.split(",");
        String prevHeader = ""; // Variabel untuk menyimpan header sebelumnya
        try {
            ps = koneksi.prepareStatement("SELECT * FROM master_skrinning_mpp WHERE header = ? AND sub = ? AND sub_unit = ?");
            for (String angka : ceklist) {
                String[] values = angka.split("\\.");
                if (values.length >= 3) {
                    String header = values[0].trim();
                    String data = header + values[1].trim() + values[2].trim();
                    if (!header.equals(prevHeader)) {
                        prevHeader = header;
                        if (header.equals("1")) {
                            tabModeForm.addRow(new Object[]{"I.", "Skrining Permasalahan", ""});
                        } else if (header.equals("2")) {
                            tabModeForm.addRow(new Object[]{"", "", ""});
                            tabModeForm.addRow(new Object[]{"II.", "Assessment", ""});
                        } else if (header.equals("3")) {
                            tabModeForm.addRow(new Object[]{"", "", ""});
                            tabModeForm.addRow(new Object[]{"III.", "Identifikasi Masalah - Resiko", ""});
                        } else if (header.equals("4")) {
                            tabModeForm.addRow(new Object[]{"", "", ""});
                            tabModeForm.addRow(new Object[]{"IV.", "Perencanaan Manajemen Pelayanan Pasien", ""});
                        }
                    }
                    ps.setString(1, header);
                    ps.setString(2, values[1].trim());
                    ps.setString(3, values[2].trim());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        if (rs.getString("header").equals("1") || rs.getString("header").equals("2")) {
                            tabModeForm.addRow(new Object[]{rs.getString("sub"), rs.getString("nm_sub"), rs.getString("nama")});
                        }
                        if (rs.getString("header").equals("3") || rs.getString("header").equals("4")) {
                            tabModeForm.addRow(new Object[]{rs.getString("sub"), rs.getString("nama"), ""});
                        }
                    }
                }
            }
        } catch (SQLException e) {
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
        kdptg1.setText("");
        nmptg1.setText("");
        Implementasi.setText("");
        Evaluasi.setText("");
        TCari.setText("");
        tTotal.setText("");
        TDirawat.setText("");
        TFinansial.setText("");
        TAsuransi.setText("");
        TRiwayatObat.setText("");
        TRiwayatTrauma.setText("");
        TAspekLegal.setText("");
        TIdentifikasi.setText("");
        TPerencanaan.setText("");
    }
    
    private void pilihTab(){
        if (TabInput2.getSelectedIndex() == 0) {
            tampil2();
        } else if (TabInput2.getSelectedIndex() == 2) {
            tampil3();
        } else if (TabInput2.getSelectedIndex() == 1) {
            tampil();
        }
    }
    private void getData() {
        if (tbData.getSelectedRow() != -1) {
            TNoRw.setText(tbData.getValueAt(tbData.getSelectedRow(), 1).toString());
            Valid.SetTgl(Tanggal, tbData.getValueAt(tbData.getSelectedRow(), 4).toString());
            Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=?", TNoRM, TNoRw.getText());
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", TPasien, TNoRM.getText());
            TSkriningCeklist.setText(tbData.getValueAt(tbData.getSelectedRow(), 6).toString());
        }
    }

    private void getData2() {
        if (tbData1.getSelectedRow() != -1) {
            TNoRw.setText(tbData1.getValueAt(tbData1.getSelectedRow(), 0).toString());
            Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=?", TNoRM, TNoRw.getText());
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", TPasien, TNoRM.getText());
            Valid.SetTgl(Tanggal, tbData1.getValueAt(tbData1.getSelectedRow(), 3).toString());
            TJam.setText(tbData1.getValueAt(tbData1.getSelectedRow(), 4).toString());
            Implementasi.setText(tbData1.getValueAt(tbData1.getSelectedRow(), 5).toString());
            Evaluasi.setText(tbData1.getValueAt(tbData1.getSelectedRow(), 6).toString());
            Sequel.cariIsi("select petugas from implementasi_evaluasi_mpp where no_rawat=?", kdptg1, TNoRw.getText());
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg1, kdptg1.getText());

        }
    }

    private void getData3() {
        if (Sequel.cariInteger("select count(no_rawat) from evaluasi_awal_mpp where no_rawat='" + TNoRw.getText() + "'") > 0) {
            Sequel.cariIsi("select nilai_skrining from evaluasi_awal_mpp where no_rawat=?", tTotal, TNoRw.getText());
            Sequel.cariIsi("select petugas from evaluasi_awal_mpp where no_rawat=?", kdptg, TNoRw.getText());
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg, kdptg.getText());
            String cari = Sequel.cariIsi("select skrining_ceklist from evaluasi_awal_mpp where no_rawat=?", TNoRw.getText());
//        cari = cari.replace("(", "").replace(")", "").trim();
            String[] ceklist = cari.split(",");
            JCheckBox[] cekbox = {chkBox1, chkBox2, chkBox3, chkBox4, chkBox5, chkBox6, chkBox7, chkBox8, chkBox9, chkBox10};
            for (String angka : ceklist) {
                int nomorCheckbox = Integer.parseInt(angka.trim()) - 1;
                if (nomorCheckbox >= 0 && nomorCheckbox < cekbox.length) {
                    cekbox[nomorCheckbox].setSelected(true);
                }
            }

            String dataTeks = Sequel.cariIsi("select keterangan from evaluasi_awal_mpp where no_rawat=?", TNoRw.getText());

//            if (dataTeks != null && !dataTeks.isEmpty()) {
            String[] teksArray = dataTeks.split(";");

            if (teksArray.length == 8) {
                TDirawat.setText(teksArray[0]);
                TFinansial.setText(teksArray[1]);
                TAsuransi.setText(teksArray[2]);
                TRiwayatObat.setText(teksArray[3]);
                TRiwayatTrauma.setText(teksArray[4]);
                TAspekLegal.setText(teksArray[5]);
                TIdentifikasi.setText(teksArray[6]);
                TPerencanaan.setText(teksArray[7]);
            } else {
            }
//            } else {
//            }
        }
    }

    private void isRawat() {
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ", TNoRM, TNoRw.getText());
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ", TPasien, TNoRM.getText());
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

    public void setNoRm(String norwt, String posisi) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        TCari.setText(norwt);
        this.status = posisi;
        getData3();
        isRawat();
    }

    public void setPasien(String pasien) {
        TCari.setText(pasien);
    }

//    public void pilihTab() {
//        if (TabInput.getSelectedIndex() == 1) {
//
//        }
//    }
    public void total() {
        JCheckBox[] checkBoxArray = new JCheckBox[10];
        checkBoxArray[0] = chkBox1;
        checkBoxArray[1] = chkBox2;
        checkBoxArray[2] = chkBox3;
        checkBoxArray[3] = chkBox4;
        checkBoxArray[4] = chkBox5;
        checkBoxArray[5] = chkBox6;
        checkBoxArray[6] = chkBox7;
        checkBoxArray[7] = chkBox8;
        checkBoxArray[8] = chkBox9;
        checkBoxArray[9] = chkBox10;

        for (int i = 0; i < checkBoxArray.length; i++) {
            JCheckBox checkBox = checkBoxArray[i];
            if (checkBox.isSelected()) {
                totalSelected++;
            }
        }

//        tTotal.setText(String.valueOf(totalSelected));
        total = Integer.toString(totalSelected);
        tTotal.setText(total);
    }

    private void simpanform() {
        try {
            StringBuilder sb = new StringBuilder();
            JTable table = tbForm;

            if (table != null) {
                for (int i = 0; i < table.getRowCount(); i++) {
                    if (table.getValueAt(i, 0).toString().equals("true")) {
                        String header = table.getValueAt(i, 1).toString();
                        String sub = table.getValueAt(i, 2).toString();
                        String subUnit = table.getValueAt(i, 3).toString();
                        sb.append(header).append(".").append(sub).append(".").append(subUnit).append(",");
                    }
                }
            }

            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
                String evaluasiAwal = sb.toString();
                Sequel.mengedit("evaluasi_awal_mpp", "no_rawat='" + TNoRw.getText() + "'", "evaluasi_awal='" + evaluasiAwal + "'");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void isReset() {
        jml = tbForm.getRowCount();
        for (i = 0; i < jml; i++) {
            tbForm.setValueAt(false, i, 0);
        }
        Valid.tabelKosong(tabMode);
        tampil();
    }

    private void isForm() {
        if (ChkInput.isSelected() == true) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 126));
            panelGlass12.setVisible(true);
            ChkInput.setVisible(true);
        } else if (ChkInput.isSelected() == false) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 20));
            panelGlass12.setVisible(false);
            ChkInput.setVisible(true);
        }
    }

    public void insertCeklist() {
        ArrayList<String> ceklist = new ArrayList<>();
        if (chkBox1.isSelected()) {
            ceklist.add("1");
        }
        if (chkBox2.isSelected()) {
            ceklist.add("2");
        }
        if (chkBox3.isSelected()) {
            ceklist.add("3");
        }
        if (chkBox4.isSelected()) {
            ceklist.add("4");
        }
        if (chkBox5.isSelected()) {
            ceklist.add("5");
        }
        if (chkBox6.isSelected()) {
            ceklist.add("6");
        }
        if (chkBox7.isSelected()) {
            ceklist.add("7");
        }
        if (chkBox8.isSelected()) {
            ceklist.add("8");
        }
        if (chkBox9.isSelected()) {
            ceklist.add("9");
        }
        if (chkBox10.isSelected()) {
            ceklist.add("10");
        }
//        StringBuilder sb = new StringBuilder();
//        for (String item : ceklist) {
//            sb.append(item).append(",");
//        }
//        periksaceklist = sb.toString();
        StringJoiner joiner = new StringJoiner(",");
        for (String item : ceklist) {
            joiner.add(item);
        }
        periksaceklist = joiner.toString();
    }

    public void insertData() {
        String[] data = new String[9];

        data[0] = TDirawat.getText();
        data[1] = TDirawat1.getText();
        data[2] = TFinansial.getText();
        data[3] = TAsuransi.getText();
        data[4] = TRiwayatObat.getText();
        data[5] = TRiwayatTrauma.getText();
        data[6] = TAspekLegal.getText();
        data[7] = TIdentifikasi.getText();
        data[8] = TPerencanaan.getText();
        String listdata = String.join(";", data);

        if (listdata.endsWith(";")) {
            listdata = listdata.substring(0, listdata.length() - 1);
        }
        Sequel.mengedit("evaluasi_awal_mpp", "no_rawat='" + TNoRw.getText() + "'", "keterangan='" + listdata + "'");
    }

    public void isCek() {
        if (var.getkode().equals("Admin Utama")) {
            BtnKirim.setVisible(true);
        } else if (var.getkode().contains("unit") || var.getkode().contains("adn")) {
        } else {
            BtnKirim.setVisible(false);
        }
    }
}
