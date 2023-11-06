/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * DlgPerawatan.java
 *
 * Created on May 23, 2010, 6:36:30 PM
 */
package perlap;

import permintaan.*;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import informasi.InformasiKerohanian;
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
import java.text.DecimalFormat;
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
import keuangan.DlgKamar;

/**
 *
 * @author dosen
 */
public final class DlgPermintaanRBA extends javax.swing.JDialog {

    private DefaultTableModel tabMode, tabMode1, tabMode2;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private DlgCariPetugas petugas = new DlgCariPetugas(null, false);
    private PreparedStatement ps;
    private ResultSet rs;
    private int jml = 0, i = 0, index = 0;
//    private String kelas_radiologi = "Yes", kelas = "", cara_bayar_radiologi = "Yes", pemeriksaan = "", namakamar, status = "";
    private double hitung = 0;
    private String no = "", key = "", bidang = Sequel.cariIsi("SELECT departemen FROM pegawai WHERE nik = ?", var.getkode());
//    private final Properties prop = new Properties();
    public DlgKamar kamar = new DlgKamar(null, false);
    private DecimalFormat df = new DecimalFormat("#");

    /**
     * Creates new form DlgPerawatan
     *
     * @param parent
     * @param modal
     */
    public DlgPermintaanRBA(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        WindowPilihData.setSize(574, 200);
        WindowPilihData1.setSize(574, 200);

        tabMode = new DefaultTableModel(null, new String[]{"ID", "No.Permintaan", "Tanggal", "Jam", "Uraian", "Kode Bangsal", "Ruang/Unit", "Nama Barang", "Jumlah", "Harga", "Total", "Status"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };

        tbPemeriksaan.setModel(tabMode);
        tbPemeriksaan.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbPemeriksaan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 12; i++) {
            TableColumn column = tbPemeriksaan.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 1) {
                column.setPreferredWidth(95);
            } else if (i == 2) {
                column.setPreferredWidth(65);
            } else if (i == 3) {
                column.setPreferredWidth(50);
            } else if (i == 4) {
                column.setPreferredWidth(200);
            } else if (i == 5) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 6) {
                column.setPreferredWidth(150);
            } else if (i == 7) {
                column.setPreferredWidth(250);
            } else if (i == 8) {
                column.setPreferredWidth(50);
            } else if (i == 9) {
                column.setPreferredWidth(95);
            } else if (i == 10) {
                column.setPreferredWidth(90);
            } else if (i == 11) {
                column.setPreferredWidth(65);
            }
        }
        tbPemeriksaan.setDefaultRenderer(Object.class, new WarnaTable());

        tabMode1 = new DefaultTableModel(null, new String[]{"Kode", "Uraian ", "Anggaran"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbFaskes1.setModel(tabMode1);
        tbFaskes1.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbFaskes1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 3; i++) {
            TableColumn column = tbFaskes1.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(95);
            } else if (i == 1) {
                column.setPreferredWidth(350);
            } else if (i == 2) {
                column.setPreferredWidth(95);
            }
        }
        tbFaskes1.setDefaultRenderer(Object.class, new WarnaTable());

        tabMode2 = new DefaultTableModel(null, new String[]{"Kode", "Uraian ", "Anggaran"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbFaskes2.setModel(tabMode2);
        tbFaskes2.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbFaskes2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 3; i++) {
            TableColumn column = tbFaskes2.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(95);
            } else if (i == 1) {
                column.setPreferredWidth(350);
            } else if (i == 2) {
                column.setPreferredWidth(95);
            }
        }
        tbFaskes2.setDefaultRenderer(Object.class, new WarnaTable());

        NmBarang.setDocument(new batasInput((byte) 80).getKata(NmBarang));
        JumlahBarang.setDocument(new batasInput((byte) 5).getOnlyAngka(JumlahBarang));
        HargaBarang.setDocument(new batasInput((byte) 15).getOnlyAngka(HargaBarang));
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

//        Valid.loadCombo(Departemen, "rekening.nm_rek", " rekeningtahun inner join rekening inner join subrekening ON rekeningtahun.kd_rek=rekening.kd_rek and rekening.kd_rek=subrekening.kd_rek2 WHERE subrekening.kd_rek='" + KdRek.getText() + "' ");
        ChkJln.setSelected(true);
        jam();

        kamar.bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("DlgPermintaanRBA")) {
                    if (kamar.bangsal.getTable().getSelectedRow() != -1) {
                        KdBangsal.setText(kamar.bangsal.getTable().getValueAt(kamar.bangsal.getTable().getSelectedRow(), 0).toString());
                        NmBangsal.setText(kamar.bangsal.getTable().getValueAt(kamar.bangsal.getTable().getSelectedRow(), 1).toString());
                    }
                    KdBangsal.requestFocus();
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

        Penjab = new widget.TextBox();
        Jk = new widget.TextBox();
        Umur = new widget.TextBox();
        Alamat = new widget.TextBox();
        WindowPilihData = new javax.swing.JDialog();
        internalFrame8 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbFaskes1 = new widget.Table();
        panelisi1 = new widget.panelisi();
        BtnKeluar1 = new widget.Button();
        WindowPilihData1 = new javax.swing.JDialog();
        internalFrame9 = new widget.InternalFrame();
        scrollPane2 = new widget.ScrollPane();
        tbFaskes2 = new widget.Table();
        panelisi2 = new widget.panelisi();
        BtnKeluar2 = new widget.Button();
        internalFrame1 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        jLabel10 = new widget.Label();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        FormInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        PanelInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        NmBarang = new widget.TextBox();
        JumlahBarang = new widget.TextBox();
        jLabel9 = new widget.Label();
        Tanggal = new widget.Tanggal();
        jLabel15 = new widget.Label();
        jLabel4 = new widget.Label();
        NmRek = new widget.TextBox();
        jLabel5 = new widget.Label();
        HargaBarang = new widget.TextBox();
        Anggaran = new widget.TextBox();
        jLabel6 = new widget.Label();
        jLabel11 = new widget.Label();
        TNoPermintaan = new widget.TextBox();
        TotalHarga = new widget.TextBox();
        jLabel12 = new widget.Label();
        jLabel21 = new widget.Label();
        NmBangsal = new widget.TextBox();
        btnBangsalCari = new widget.Button();
        jLabel16 = new widget.Label();
        CmbJam = new widget.ComboBox();
        CmbMenit = new widget.ComboBox();
        CmbDetik = new widget.ComboBox();
        ChkJln = new widget.CekBox();
        KdRek = new widget.TextBox();
        jLabel7 = new widget.Label();
        KdRek1 = new widget.TextBox();
        NmRek1 = new widget.TextBox();
        btnDokter1 = new widget.Button();
        KdRek2 = new widget.TextBox();
        NmRek2 = new widget.TextBox();
        btnDokter2 = new widget.Button();
        KdBangsal = new widget.TextBox();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        panelisi5 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        btnCari = new widget.Button();
        BtnAll = new widget.Button();
        Scroll2 = new widget.ScrollPane();
        tbPemeriksaan = new widget.Table();

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

        WindowPilihData.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowPilihData.setName("WindowPilihData"); // NOI18N
        WindowPilihData.setUndecorated(true);
        WindowPilihData.setResizable(false);
        WindowPilihData.getContentPane().setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pilih Data ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        internalFrame8.setName("internalFrame8"); // NOI18N
        internalFrame8.setWarnaBawah(new java.awt.Color(245, 250, 240));
        internalFrame8.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbFaskes1.setToolTipText("Klik data di tabel");
        tbFaskes1.setName("tbFaskes1"); // NOI18N
        tbFaskes1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbFaskes1MouseClicked(evt);
            }
        });
        tbFaskes1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbFaskes1KeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbFaskes1);

        internalFrame8.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(44, 54));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnKeluar1.setForeground(new java.awt.Color(0, 0, 0));
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
        BtnKeluar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluar1KeyPressed(evt);
            }
        });
        panelisi1.add(BtnKeluar1);

        internalFrame8.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        WindowPilihData.getContentPane().add(internalFrame8, java.awt.BorderLayout.CENTER);

        WindowPilihData1.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowPilihData1.setName("WindowPilihData1"); // NOI18N
        WindowPilihData1.setUndecorated(true);
        WindowPilihData1.setResizable(false);
        WindowPilihData1.getContentPane().setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pilih Data 2 ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        internalFrame9.setName("internalFrame9"); // NOI18N
        internalFrame9.setWarnaBawah(new java.awt.Color(245, 250, 240));
        internalFrame9.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setOpaque(true);

        tbFaskes2.setToolTipText("Klik data di tabel");
        tbFaskes2.setName("tbFaskes2"); // NOI18N
        tbFaskes2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbFaskes2MouseClicked(evt);
            }
        });
        tbFaskes2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbFaskes2KeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(tbFaskes2);

        internalFrame9.add(scrollPane2, java.awt.BorderLayout.CENTER);

        panelisi2.setName("panelisi2"); // NOI18N
        panelisi2.setPreferredSize(new java.awt.Dimension(44, 54));
        panelisi2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnKeluar2.setForeground(new java.awt.Color(0, 0, 0));
        BtnKeluar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar2.setMnemonic('K');
        BtnKeluar2.setText("Keluar");
        BtnKeluar2.setToolTipText("Alt+K");
        BtnKeluar2.setName("BtnKeluar2"); // NOI18N
        BtnKeluar2.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar2ActionPerformed(evt);
            }
        });
        BtnKeluar2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluar2KeyPressed(evt);
            }
        });
        panelisi2.add(BtnKeluar2);

        internalFrame9.add(panelisi2, java.awt.BorderLayout.PAGE_END);

        WindowPilihData1.getContentPane().add(internalFrame9, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Input Data Permintaan RBA  ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
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

        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(103, 30));
        panelGlass8.add(jLabel10);

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
        FormInput.setPreferredSize(new java.awt.Dimension(560, 185));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(560, 109));
        PanelInput.setLayout(null);

        jLabel3.setText("Sub :");
        jLabel3.setName("jLabel3"); // NOI18N
        PanelInput.add(jLabel3);
        jLabel3.setBounds(0, 100, 90, 23);

        NmBarang.setHighlighter(null);
        NmBarang.setName("NmBarang"); // NOI18N
        NmBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NmBarangActionPerformed(evt);
            }
        });
        PanelInput.add(NmBarang);
        NmBarang.setBounds(535, 70, 280, 23);

        JumlahBarang.setHighlighter(null);
        JumlahBarang.setName("JumlahBarang"); // NOI18N
        JumlahBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JumlahBarangKeyPressed(evt);
            }
        });
        PanelInput.add(JumlahBarang);
        JumlahBarang.setBounds(535, 100, 65, 23);

        jLabel9.setText("Jumlah Barang :");
        jLabel9.setName("jLabel9"); // NOI18N
        PanelInput.add(jLabel9);
        jLabel9.setBounds(440, 100, 90, 23);

        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "19-10-2023" }));
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
        Tanggal.setBounds(95, 10, 86, 23);

        jLabel15.setText("Harga :");
        jLabel15.setName("jLabel15"); // NOI18N
        PanelInput.add(jLabel15);
        jLabel15.setBounds(590, 100, 60, 23);

        jLabel4.setText("Jenis Belanja :");
        jLabel4.setName("jLabel4"); // NOI18N
        PanelInput.add(jLabel4);
        jLabel4.setBounds(0, 40, 90, 23);

        NmRek.setEditable(false);
        NmRek.setText("BELANJA MODAL");
        NmRek.setHighlighter(null);
        NmRek.setName("NmRek"); // NOI18N
        NmRek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NmRekActionPerformed(evt);
            }
        });
        NmRek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmRekKeyPressed(evt);
            }
        });
        PanelInput.add(NmRek);
        NmRek.setBounds(185, 40, 220, 23);

        jLabel5.setText("Nama Barang :");
        jLabel5.setName("jLabel5"); // NOI18N
        PanelInput.add(jLabel5);
        jLabel5.setBounds(440, 70, 90, 23);

        HargaBarang.setHighlighter(null);
        HargaBarang.setName("HargaBarang"); // NOI18N
        HargaBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HargaBarangActionPerformed(evt);
            }
        });
        HargaBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HargaBarangKeyPressed(evt);
            }
        });
        PanelInput.add(HargaBarang);
        HargaBarang.setBounds(655, 100, 160, 23);

        Anggaran.setEditable(false);
        Anggaran.setHighlighter(null);
        Anggaran.setName("Anggaran"); // NOI18N
        Anggaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnggaranActionPerformed(evt);
            }
        });
        Anggaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AnggaranKeyPressed(evt);
            }
        });
        PanelInput.add(Anggaran);
        Anggaran.setBounds(95, 130, 310, 23);

        jLabel6.setText("Anggaran :");
        jLabel6.setName("jLabel6"); // NOI18N
        PanelInput.add(jLabel6);
        jLabel6.setBounds(0, 130, 90, 23);

        jLabel11.setText("No. Permintaan :");
        jLabel11.setName("jLabel11"); // NOI18N
        PanelInput.add(jLabel11);
        jLabel11.setBounds(440, 10, 90, 23);

        TNoPermintaan.setEditable(false);
        TNoPermintaan.setForeground(new java.awt.Color(102, 102, 102));
        TNoPermintaan.setName("TNoPermintaan"); // NOI18N
        TNoPermintaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoPermintaanKeyPressed(evt);
            }
        });
        PanelInput.add(TNoPermintaan);
        TNoPermintaan.setBounds(535, 10, 150, 23);

        TotalHarga.setEditable(false);
        TotalHarga.setHighlighter(null);
        TotalHarga.setName("TotalHarga"); // NOI18N
        PanelInput.add(TotalHarga);
        TotalHarga.setBounds(655, 130, 160, 23);

        jLabel12.setText("Total :");
        jLabel12.setName("jLabel12"); // NOI18N
        PanelInput.add(jLabel12);
        jLabel12.setBounds(590, 130, 60, 23);

        jLabel21.setText("Ruangan :");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(50, 23));
        PanelInput.add(jLabel21);
        jLabel21.setBounds(440, 40, 90, 23);

        NmBangsal.setEditable(false);
        NmBangsal.setName("NmBangsal"); // NOI18N
        NmBangsal.setPreferredSize(new java.awt.Dimension(200, 23));
        NmBangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmBangsalKeyPressed(evt);
            }
        });
        PanelInput.add(NmBangsal);
        NmBangsal.setBounds(600, 40, 190, 23);

        btnBangsalCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBangsalCari.setMnemonic('3');
        btnBangsalCari.setToolTipText("Alt+3");
        btnBangsalCari.setName("btnBangsalCari"); // NOI18N
        btnBangsalCari.setPreferredSize(new java.awt.Dimension(28, 23));
        btnBangsalCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBangsalCariActionPerformed(evt);
            }
        });
        btnBangsalCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnBangsalCariKeyPressed(evt);
            }
        });
        PanelInput.add(btnBangsalCari);
        btnBangsalCari.setBounds(790, 40, 28, 23);

        jLabel16.setText("Tgl. Permintaan :");
        jLabel16.setName("jLabel16"); // NOI18N
        PanelInput.add(jLabel16);
        jLabel16.setBounds(0, 10, 90, 23);

        CmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        CmbJam.setName("CmbJam"); // NOI18N
        PanelInput.add(CmbJam);
        CmbJam.setBounds(185, 10, 62, 23);

        CmbMenit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbMenit.setName("CmbMenit"); // NOI18N
        PanelInput.add(CmbMenit);
        CmbMenit.setBounds(250, 10, 62, 23);

        CmbDetik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbDetik.setName("CmbDetik"); // NOI18N
        PanelInput.add(CmbDetik);
        CmbDetik.setBounds(315, 10, 62, 23);

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
        ChkJln.setBounds(380, 10, 23, 23);

        KdRek.setText("52");
        KdRek.setHighlighter(null);
        KdRek.setName("KdRek"); // NOI18N
        KdRek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdRekKeyPressed(evt);
            }
        });
        PanelInput.add(KdRek);
        KdRek.setBounds(95, 40, 90, 23);

        jLabel7.setText("Jenis Belanja :");
        jLabel7.setName("jLabel7"); // NOI18N
        PanelInput.add(jLabel7);
        jLabel7.setBounds(0, 70, 90, 23);

        KdRek1.setEditable(false);
        KdRek1.setHighlighter(null);
        KdRek1.setName("KdRek1"); // NOI18N
        KdRek1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdRek1KeyPressed(evt);
            }
        });
        PanelInput.add(KdRek1);
        KdRek1.setBounds(95, 70, 90, 23);

        NmRek1.setEditable(false);
        NmRek1.setHighlighter(null);
        NmRek1.setName("NmRek1"); // NOI18N
        NmRek1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NmRek1ActionPerformed(evt);
            }
        });
        NmRek1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmRek1KeyPressed(evt);
            }
        });
        PanelInput.add(NmRek1);
        NmRek1.setBounds(185, 70, 190, 23);

        btnDokter1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter1.setMnemonic('3');
        btnDokter1.setToolTipText("Alt+3");
        btnDokter1.setName("btnDokter1"); // NOI18N
        btnDokter1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokter1ActionPerformed(evt);
            }
        });
        btnDokter1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDokter1KeyPressed(evt);
            }
        });
        PanelInput.add(btnDokter1);
        btnDokter1.setBounds(380, 70, 28, 23);

        KdRek2.setEditable(false);
        KdRek2.setHighlighter(null);
        KdRek2.setName("KdRek2"); // NOI18N
        KdRek2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdRek2KeyPressed(evt);
            }
        });
        PanelInput.add(KdRek2);
        KdRek2.setBounds(95, 100, 90, 23);

        NmRek2.setEditable(false);
        NmRek2.setHighlighter(null);
        NmRek2.setName("NmRek2"); // NOI18N
        NmRek2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NmRek2ActionPerformed(evt);
            }
        });
        NmRek2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmRek2KeyPressed(evt);
            }
        });
        PanelInput.add(NmRek2);
        NmRek2.setBounds(185, 100, 190, 23);

        btnDokter2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter2.setMnemonic('3');
        btnDokter2.setToolTipText("Alt+3");
        btnDokter2.setName("btnDokter2"); // NOI18N
        btnDokter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokter2ActionPerformed(evt);
            }
        });
        btnDokter2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDokter2KeyPressed(evt);
            }
        });
        PanelInput.add(btnDokter2);
        btnDokter2.setBounds(380, 100, 28, 23);

        KdBangsal.setEditable(false);
        KdBangsal.setHighlighter(null);
        KdBangsal.setName("KdBangsal"); // NOI18N
        KdBangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdBangsalKeyPressed(evt);
            }
        });
        PanelInput.add(KdBangsal);
        KdBangsal.setBounds(535, 40, 65, 23);

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

        panelisi5.setName("panelisi5"); // NOI18N
        panelisi5.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi5.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelisi5.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(25, 23));
        panelisi5.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelisi5.add(Tgl2);

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi5.add(label10);

        TCari.setToolTipText("Alt+C");
        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(490, 23));
        TCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TCariActionPerformed(evt);
            }
        });
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi5.add(TCari);

        btnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        btnCari.setMnemonic('1');
        btnCari.setToolTipText("Alt+1");
        btnCari.setName("btnCari"); // NOI18N
        btnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });
        btnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCariKeyPressed(evt);
            }
        });
        panelisi5.add(btnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("Alt+2");
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
        panelisi5.add(BtnAll);

        jPanel3.add(panelisi5, java.awt.BorderLayout.PAGE_END);

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

        internalFrame1.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        petugas.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnCari, TCari);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    InformasiKerohanian form = new InformasiKerohanian(null, false);
    form.isCek();
    form.setPasien(NmBarang.getText());
    form.setSize(this.getWidth(), this.getHeight());
    form.setLocationRelativeTo(this);
    form.setVisible(true);
    this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void PenjabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenjabKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenjabKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        emptTeks();
    }//GEN-LAST:event_formWindowOpened

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            btnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
//            TnmrekrequestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        tampil();
    }//GEN-LAST:event_btnCariActionPerformed

    private void btnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            tampil();
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_btnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnAllActionPerformed(null);
        } else {
            Valid.pindah(evt, btnCari, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void tbPemeriksaanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPemeriksaanMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbPemeriksaanMouseClicked

    private void tbPemeriksaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPemeriksaanKeyPressed
        if (tabMode.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbPemeriksaanKeyPressed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnBatal);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (KdRek1.getText().equals("") || NmRek1.getText().equals("") || KdRek2.getText().equals("") || NmRek2.getText().equals("")) {
            Valid.textKosong(NmBarang, "Rekening");
        } else if (KdBangsal.getText().equals("") || NmBangsal.getText().equals("")) {
            Valid.textKosong(KdBangsal, "Ruangan");
        } else if (NmBarang.getText().equals("")) {
            Valid.textKosong(NmBarang, "Barang");
        } else if (JumlahBarang.getText().equals("") || HargaBarang.getText().equals("")) {
            Valid.textKosong(JumlahBarang, "Jumlah/Harga Barang");
        } else {
            int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, udah bener belum data yang mau disimpan..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                try {
                    koneksi.setAutoCommit(false);
                    //autoNomor();simpan
                    if (Sequel.menyimpantf2("permintaan_rba", "?,?,?,?,?,?,?,?,?,?,?,?", "Data", 12, new String[]{
                        "0", TNoPermintaan.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""), CmbJam.getSelectedItem().toString() + ":" + CmbMenit.getSelectedItem().toString() + ":" + CmbDetik.getSelectedItem().toString(),
                        KdRek2.getText(), KdBangsal.getText(), NmBarang.getText(), JumlahBarang.getText(), HargaBarang.getText(), TotalHarga.getText(), "Belum", var.getkode()
                    }) == true) {
                        emptTeks();
                        tampil();
                    }
                    koneksi.setAutoCommit(true);
                    JOptionPane.showMessageDialog(null, "Proses simpan selesai...!");
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnBatalActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnSimpan, BtnCari);
        }
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void TCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TCariActionPerformed

    private void HargaBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HargaBarangKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            isIMT();
        }
    }//GEN-LAST:event_HargaBarangKeyPressed

    private void HargaBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HargaBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HargaBarangActionPerformed

    private void NmRekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmRekKeyPressed
        //Valid.pindah(evt,TNoReg,DTPReg);
    }//GEN-LAST:event_NmRekKeyPressed

    private void NmRekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NmRekActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmRekActionPerformed

    private void TNoPermintaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoPermintaanKeyPressed

    }//GEN-LAST:event_TNoPermintaanKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt, TNoPermintaan, TCari);
    }//GEN-LAST:event_TanggalKeyPressed

    private void TanggalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TanggalItemStateChanged
        autoNomor();
    }//GEN-LAST:event_TanggalItemStateChanged

    private void AnggaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnggaranActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AnggaranActionPerformed

    private void AnggaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AnggaranKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AnggaranKeyPressed

    private void NmBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NmBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmBarangActionPerformed

    private void NmBangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmBangsalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            tampil();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnBangsalCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            btnBangsalCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            TCari.requestFocus();
        }
    }//GEN-LAST:event_NmBangsalKeyPressed

    private void btnBangsalCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBangsalCariActionPerformed
        var.setform("DlgPermintaanRBA");
        kamar.bangsal.isCek();
        kamar.bangsal.emptTeks();
        kamar.bangsal.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        kamar.bangsal.setLocationRelativeTo(internalFrame1);
        kamar.bangsal.setVisible(true);
    }//GEN-LAST:event_btnBangsalCariActionPerformed

    private void btnBangsalCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnBangsalCariKeyPressed
        Valid.pindah(evt, btnBangsalCari, TCari);
    }//GEN-LAST:event_btnBangsalCariKeyPressed

    private void JumlahBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JumlahBarangKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            isIMT();
        }
    }//GEN-LAST:event_JumlahBarangKeyPressed

    private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkJlnActionPerformed

    private void KdRekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdRekKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdRekKeyPressed

    private void KdRek1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdRek1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdRek1KeyPressed

    private void NmRek1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NmRek1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmRek1ActionPerformed

    private void NmRek1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmRek1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmRek1KeyPressed

    private void btnDokter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokter1ActionPerformed
        WindowPilihData.setLocationRelativeTo(internalFrame1);
        WindowPilihData.setVisible(true);
        tampilListData();
    }//GEN-LAST:event_btnDokter1ActionPerformed

    private void btnDokter1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDokter1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDokter1KeyPressed

    private void tbFaskes1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbFaskes1MouseClicked
        if (tabMode1.getRowCount() != 0) {
            try {
                getData1();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbFaskes1MouseClicked

    private void tbFaskes1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbFaskes1KeyPressed
        if (tabMode1.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData1();
                } catch (java.lang.NullPointerException e) {
                }
            }
            WindowPilihData.dispose();
        }
    }//GEN-LAST:event_tbFaskes1KeyPressed

    private void BtnKeluar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar1ActionPerformed
        WindowPilihData.dispose();
    }//GEN-LAST:event_BtnKeluar1ActionPerformed

    private void BtnKeluar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluar1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        }
    }//GEN-LAST:event_BtnKeluar1KeyPressed

    private void KdRek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdRek2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdRek2KeyPressed

    private void NmRek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NmRek2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmRek2ActionPerformed

    private void NmRek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmRek2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmRek2KeyPressed

    private void btnDokter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokter2ActionPerformed
        WindowPilihData1.setLocationRelativeTo(internalFrame1);
        WindowPilihData1.setVisible(true);
        tampilListData1();
    }//GEN-LAST:event_btnDokter2ActionPerformed

    private void btnDokter2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDokter2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDokter2KeyPressed

    private void tbFaskes2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbFaskes2MouseClicked
        if (tabMode2.getRowCount() != 0) {
            try {
                getData2();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbFaskes2MouseClicked

    private void tbFaskes2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbFaskes2KeyPressed
        if (tabMode2.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData2();
                } catch (java.lang.NullPointerException e) {
                }
            }
            WindowPilihData1.dispose();
        }
    }//GEN-LAST:event_tbFaskes2KeyPressed

    private void BtnKeluar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar2ActionPerformed
        WindowPilihData1.dispose();
    }//GEN-LAST:event_BtnKeluar2ActionPerformed

    private void BtnKeluar2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluar2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnKeluar2KeyPressed

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt, BtnKeluar, Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1, TCari);
    }//GEN-LAST:event_Tgl2KeyPressed

    private void KdBangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdBangsalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdBangsalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if (no.equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, data tidak boleh kosong.\nSilakan pilih data permintaan terlebih dahulu..!!");
        } else {
            int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, Yakin mau dihapus..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                Sequel.meghapus("permintaan_rba", "id", tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 0).toString());
                tampil();
                emptTeks();
            }
        }
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnBatal, BtnCari);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPermintaanRBA dialog = new DlgPermintaanRBA(new javax.swing.JFrame(), true);
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
    private widget.TextBox Anggaran;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluar1;
    private widget.Button BtnKeluar2;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkJln;
    private widget.ComboBox CmbDetik;
    private widget.ComboBox CmbJam;
    private widget.ComboBox CmbMenit;
    private javax.swing.JPanel FormInput;
    private widget.TextBox HargaBarang;
    private widget.TextBox Jk;
    private widget.TextBox JumlahBarang;
    private widget.TextBox KdBangsal;
    private widget.TextBox KdRek;
    private widget.TextBox KdRek1;
    private widget.TextBox KdRek2;
    private widget.TextBox NmBangsal;
    private widget.TextBox NmBarang;
    private widget.TextBox NmRek;
    private widget.TextBox NmRek1;
    private widget.TextBox NmRek2;
    private widget.PanelBiasa PanelInput;
    private widget.TextBox Penjab;
    private widget.ScrollPane Scroll2;
    private widget.TextBox TCari;
    private widget.TextBox TNoPermintaan;
    private widget.Tanggal Tanggal;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.TextBox TotalHarga;
    private widget.TextBox Umur;
    private javax.swing.JDialog WindowPilihData;
    private javax.swing.JDialog WindowPilihData1;
    private widget.Button btnBangsalCari;
    private widget.Button btnCari;
    private widget.Button btnDokter1;
    private widget.Button btnDokter2;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame8;
    private widget.InternalFrame internalFrame9;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel21;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label18;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi2;
    private widget.panelisi panelisi5;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.Table tbFaskes1;
    private widget.Table tbFaskes2;
    private widget.Table tbPemeriksaan;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        if (var.getkode().equals("Admin Utama") || bidang.equals("KEU") || bidang.equals("PL") || bidang.equals("SPR")) {
            tampil2();
        } else {
            tampil1();
        }
    }

    private void tampil1() {
        key = " permintaan_rba.username='" + var.getkode() + "' and ";
        try {
            Valid.tabelKosong(tabMode);
            ps = koneksi.prepareStatement(
                    "SELECT permintaan_rba.id, permintaan_rba.noorder, permintaan_rba.tgl_permintaan, permintaan_rba.jam_permintaan, rekening.nm_rek, permintaan_rba.kd_bangsal,bangsal.nm_bangsal, "
                    + "permintaan_rba.nama_brng, permintaan_rba.jlh_brng, permintaan_rba.harga_brng, permintaan_rba.total_brng, permintaan_rba.status "
                    + "FROM permintaan_rba inner join rekening inner join bangsal on permintaan_rba.kd_rek=rekening.kd_rek and permintaan_rba.kd_bangsal=bangsal.kd_bangsal "
                    + "WHERE " + key + " permintaan_rba.tgl_permintaan between ? and ? and (bangsal.nm_bangsal like ?)");
            try {
                ps.setString(1, Valid.SetTgl(Tgl1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(Tgl2.getSelectedItem() + ""));
                ps.setString(3, "%" + TCari.getText() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new Object[]{rs.getString("id"), rs.getString("noorder"), rs.getString("tgl_permintaan"), rs.getString("jam_permintaan"), rs.getString("nm_rek"), rs.getString("kd_bangsal"), rs.getString("nm_bangsal"),
                        rs.getString("nama_brng"), rs.getString("jlh_brng"), rs.getDouble("harga_brng"), rs.getDouble("total_brng"), rs.getString("status")});
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

    private void tampil2() {
        try {
            Valid.tabelKosong(tabMode);
            ps = koneksi.prepareStatement(
                    "SELECT permintaan_rba.id, permintaan_rba.noorder, permintaan_rba.tgl_permintaan, permintaan_rba.jam_permintaan, rekening.nm_rek, permintaan_rba.kd_bangsal,bangsal.nm_bangsal, "
                    + "permintaan_rba.nama_brng, permintaan_rba.jlh_brng, permintaan_rba.harga_brng, permintaan_rba.total_brng, permintaan_rba.status "
                    + "FROM permintaan_rba inner join rekening inner join bangsal on permintaan_rba.kd_rek=rekening.kd_rek and permintaan_rba.kd_bangsal=bangsal.kd_bangsal "
                    + "WHERE permintaan_rba.tgl_permintaan between ? and ? and (bangsal.nm_bangsal like ?)");
            try {
                ps.setString(1, Valid.SetTgl(Tgl1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(Tgl2.getSelectedItem() + ""));
                ps.setString(3, "%" + TCari.getText() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new Object[]{rs.getString("id"), rs.getString("noorder"), rs.getString("tgl_permintaan"), rs.getString("jam_permintaan"), rs.getString("nm_rek"), rs.getString("kd_bangsal"), rs.getString("nm_bangsal"),
                        rs.getString("nama_brng"), rs.getString("jlh_brng"), rs.getDouble("harga_brng"), rs.getDouble("total_brng"), rs.getString("status")});
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

    public void tampilListData() {
        try {
            Valid.tabelKosong(tabMode1);
            ps = koneksi.prepareStatement(
                    "select rekening.kd_rek, rekening.nm_rek, rekeningtahun.saldo_awal, rekeningtahun.saldo_berjalan "
                    + "from rekening inner join rekeningtahun inner join subrekening on rekening.kd_rek=rekeningtahun.kd_rek and rekening.kd_rek=subrekening.kd_rek2 "
                    + "where subrekening.kd_rek='" + KdRek.getText() + "' and rekening.level='1' and rekening.balance='K'");
            try {
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode1.addRow(new Object[]{rs.getString("kd_rek"), rs.getString("nm_rek"), rs.getString("saldo_berjalan")
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

    public void tampilListData1() {
        try {
            Valid.tabelKosong(tabMode2);
            ps = koneksi.prepareStatement(
                    "select rekening.kd_rek, rekening.nm_rek, rekeningtahun.saldo_awal, rekeningtahun.saldo_berjalan "
                    + "from rekening inner join rekeningtahun inner join subrekening on rekening.kd_rek=rekeningtahun.kd_rek and rekening.kd_rek=subrekening.kd_rek2 "
                    + "where subrekening.kd_rek='" + KdRek1.getText() + "' and rekening.level='1' and rekening.balance='K'");
            try {
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode2.addRow(new Object[]{rs.getString("kd_rek"), rs.getString("nm_rek"), rs.getString("saldo_berjalan")
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

    public void emptTeks() {
        KdRek1.setText("");
        NmRek1.setText("");
        KdRek2.setText("");
        NmRek2.setText("");
        Anggaran.setText("");
        KdBangsal.setText("");
        NmBangsal.setText("");
        NmBarang.setText("");
        JumlahBarang.setText("");
        HargaBarang.setText("");
        TotalHarga.setText("");
        TCari.setText("");
        autoNomor();
    }

    public void isCek() {

    }

    private void isForm() {
        if (ChkInput.isSelected() == true) {
            ChkInput.setVisible(false);
            FormInput.setPreferredSize(new Dimension(WIDTH, 190));
            PanelInput.setVisible(true);
            ChkInput.setVisible(true);
        } else if (ChkInput.isSelected() == false) {
            ChkInput.setVisible(false);
            FormInput.setPreferredSize(new Dimension(WIDTH, 20));
            PanelInput.setVisible(false);
            ChkInput.setVisible(true);
        }
    }

    public void setPetugas(String kodepetugas, String namapetugas) {
    }

    private void autoNomor() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(noorder,4),signed)),0) from permintaan_rba where tgl_permintaan='" + Valid.SetTgl(Tanggal.getSelectedItem() + "") + "' ", "PA" + Valid.SetTgl(Tanggal.getSelectedItem() + "").replaceAll("-", ""), 4, TNoPermintaan);
    }

    private void isIMT() {
        try {
            if (!JumlahBarang.getText().isEmpty() && !HargaBarang.getText().isEmpty()) {
                try {
                    hitung = Double.parseDouble(JumlahBarang.getText()) * Double.parseDouble(HargaBarang.getText());
                } catch (Exception e) {
                    hitung = 0;
                }
                TotalHarga.setText(df.format(hitung));
                double anggaran = Double.parseDouble(Anggaran.getText().trim());
                if (hitung > anggaran) {
                    JOptionPane.showMessageDialog(null, "Maaf, total harga melebihi anggaran..!!");
                }
            }
        } catch (NumberFormatException e) {
            TotalHarga.setText("");
            JOptionPane.showMessageDialog(null, "Input tidak valid. Harap masukkan angka yang valid.");
        }
    }

    private void getData() {
        String getJumlah = tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 9).toString();
        String getTotal = tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 10).toString();
        double jumlah = Double.parseDouble(getJumlah);
        double total = Double.parseDouble(getTotal);
        if (tbPemeriksaan.getSelectedRow() != -1) {
            no = tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 0).toString();
            TNoPermintaan.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 1).toString());
            Valid.SetTgl(Tanggal, tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 2).toString());
            CmbJam.setSelectedItem(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 3).toString().substring(0, 2));
            CmbMenit.setSelectedItem(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 3).toString().substring(3, 5));
            CmbDetik.setSelectedItem(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 3).toString().substring(6, 8));
            KdRek2.setText(Sequel.cariIsi("SELECT kd_rek FROM permintaan_rba where id=?", no));
            NmRek2.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 4).toString());
            Anggaran.setText(Sequel.cariIsi("SELECT saldo_berjalan FROM rekeningtahun where kd_rek=?", KdRek2.getText()));
            KdBangsal.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 5).toString());
            NmBangsal.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 6).toString());
            NmBarang.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 7).toString());
            JumlahBarang.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 8).toString());
            HargaBarang.setText(df.format(jumlah));
            TotalHarga.setText(df.format(total));
//            HargaBarang.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 9).toString());
//            TotalHarga.setText(tbPemeriksaan.getValueAt(tbPemeriksaan.getSelectedRow(), 10).toString());
            KdRek1.setText(Sequel.cariIsi("SELECT subrekening.kd_rek FROM permintaan_rba inner join subrekening on permintaan_rba.kd_rek=subrekening.kd_rek2 WHERE permintaan_rba.kd_rek=?", KdRek2.getText()));
            NmRek1.setText(Sequel.cariIsi("SELECT nm_rek FROM rekening where kd_rek=?", KdRek1.getText()));
        }
    }

    private void getData1() {
        if (tbFaskes1.getSelectedRow() != -1) {
            KdRek1.setText(tbFaskes1.getValueAt(tbFaskes1.getSelectedRow(), 0).toString());
            NmRek1.setText(tbFaskes1.getValueAt(tbFaskes1.getSelectedRow(), 1).toString());
        }
    }

    private void getData2() {
        if (tbFaskes2.getSelectedRow() != -1) {
            KdRek2.setText(tbFaskes2.getValueAt(tbFaskes2.getSelectedRow(), 0).toString());
            NmRek2.setText(tbFaskes2.getValueAt(tbFaskes2.getSelectedRow(), 1).toString());
            Anggaran.setText(tbFaskes2.getValueAt(tbFaskes2.getSelectedRow(), 2).toString());
        }
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
}
