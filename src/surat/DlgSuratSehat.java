/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package surat;

import fungsi.WarnaTable;
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
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariDokter2;
import simrskhanza.DlgReg;

/**
 *
 * @author root
 */
public class DlgSuratSehat extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private Connection koneksi_john = koneksiDB.condb();
    private validasi Valid = new validasi();
    private ResultSet rs;
    private sekuel Sequel = new sekuel();
    public DlgCariDokter dokter = new DlgCariDokter(null, false);
    public DlgCariDokter2 dokter2 = new DlgCariDokter2(null, false);
    private String no = "";

    public DlgSuratSehat(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        // WindowAmbilSampel.setSize(530,80);
        tabMode = new DefaultTableModel(null, new Object[]{
           "id", "No.Surat","Tgl.Surat", "No.Rawat", "No. RM", "Pasien", "Kode Dokter", "Dokter", "Keperluan", "Tinggi Badan",
            "Berat Badan", "Tensi","Gol.Darah"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbDataSuratSehat.setModel(tabMode);
        tbDataSuratSehat.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbDataSuratSehat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        int i;
        for (i = 0; i < 13; i++) {
            TableColumn column = tbDataSuratSehat.getColumnModel().getColumn(i);
            if ((i == 0) || (i==6)) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 1) {
                column.setPreferredWidth(150);
            } else if (i == 2) {
                column.setPreferredWidth(75);
            } else if (i == 3) {
                column.setPreferredWidth(100);
            } else if (i == 4) {
                column.setPreferredWidth(70);
            } else if (i == 5) {
                column.setPreferredWidth(200);
            } else if ((i == 7)||(i == 8)) {
                column.setPreferredWidth(180);
            } else if ((i == 9)|| (i == 10)||(i == 11)||(i == 12)) {
                column.setPreferredWidth(70);
            }
        }
        tbDataSuratSehat.setDefaultRenderer(Object.class, new WarnaTable());

        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("DlgSuratSehat")) {
                    if (dokter.getTable().getSelectedRow() != -1) {
                        TKdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                        TNamaDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                        TKdDokter.requestFocus();
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

        dokter2.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("DlgSuratSehat")) {
                    if (dokter2.getTable().getSelectedRow() != -1) {
                        TKdDokter.setText(dokter2.getTable().getValueAt(dokter2.getTable().getSelectedRow(), 0).toString());
                        TNamaDokter.setText(dokter2.getTable().getValueAt(dokter2.getTable().getSelectedRow(), 1).toString());
                        TKdDokter.requestFocus();
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCetakSurat = new javax.swing.JMenuItem();
        jPanel2 = new javax.swing.JPanel();
        panelGlass6 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnEdit = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        jLabel12 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass7 = new widget.panelisi();
        jLabel15 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel17 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel16 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        internalFrame1 = new widget.InternalFrame();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TTinggiBadan = new widget.TextBox();
        jLabel5 = new widget.Label();
        DTPTglSurat = new widget.Tanggal();
        jLabel6 = new widget.Label();
        jLabel7 = new widget.Label();
        jLabel8 = new widget.Label();
        jLabel9 = new widget.Label();
        TBeratBadan = new widget.TextBox();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        TTensi = new widget.TextBox();
        scrollPane1 = new widget.ScrollPane();
        TKesan = new widget.TextArea();
        jLabel13 = new widget.Label();
        TKdDokter = new widget.TextBox();
        TNamaDokter = new widget.TextBox();
        jLabel14 = new widget.Label();
        jLabel18 = new widget.Label();
        TNoSurat = new widget.TextBox();
        TNoRawat = new widget.TextBox();
        TNoRM1 = new widget.TextBox();
        TNamaPasien1 = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel19 = new widget.Label();
        GolDarah = new widget.TextBox();
        ChkInput = new widget.CekBox();
        Scroll = new widget.ScrollPane();
        tbDataSuratSehat = new widget.Table();

        MnCetakSurat.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSurat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSurat.setForeground(new java.awt.Color(70, 70, 70));
        MnCetakSurat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSurat.setText("Cetak Surat");
        MnCetakSurat.setPreferredSize(new java.awt.Dimension(200, 28));
        MnCetakSurat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakSurat);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel2.setLayout(new java.awt.BorderLayout());

        panelGlass6.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
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
        panelGlass6.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
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
        panelGlass6.add(BtnBatal);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
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
        panelGlass6.add(BtnEdit);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
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
        panelGlass6.add(BtnHapus);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
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
        panelGlass6.add(BtnPrint);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
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
        panelGlass6.add(BtnAll);

        jLabel12.setText("Record :");
        jLabel12.setPreferredSize(new java.awt.Dimension(70, 30));
        panelGlass6.add(jLabel12);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setPreferredSize(new java.awt.Dimension(72, 30));
        panelGlass6.add(LCount);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
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
        panelGlass6.add(BtnKeluar);

        jPanel2.add(panelGlass6, java.awt.BorderLayout.PAGE_END);

        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel15.setText("Periode :");
        jLabel15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass7.add(jLabel15);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-10-2023" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(133, 23));
        panelGlass7.add(DTPCari1);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("s.d");
        jLabel17.setPreferredSize(new java.awt.Dimension(24, 23));
        panelGlass7.add(jLabel17);

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-10-2023" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(133, 23));
        panelGlass7.add(DTPCari2);

        jLabel16.setText("Key Word :");
        jLabel16.setPreferredSize(new java.awt.Dimension(158, 23));
        panelGlass7.add(jLabel16);

        TCari.setPreferredSize(new java.awt.Dimension(300, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass7.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('7');
        BtnCari.setToolTipText("Alt+7");
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
        panelGlass7.add(BtnCari);

        jPanel2.add(panelGlass7, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Surat Sehat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout());

        PanelInput.setPreferredSize(new java.awt.Dimension(500, 220));
        PanelInput.setLayout(new java.awt.BorderLayout());

        FormInput.setPreferredSize(new java.awt.Dimension(500, 240));
        FormInput.setLayout(null);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("/kg");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        FormInput.add(jLabel4);
        jLabel4.setBounds(450, 160, 40, 23);

        TTinggiBadan.setBackground(new java.awt.Color(245, 250, 240));
        TTinggiBadan.setHighlighter(null);
        FormInput.add(TTinggiBadan);
        TTinggiBadan.setBounds(380, 130, 65, 23);

        jLabel5.setText("No.Rawat :");
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 40, 75, 23);

        DTPTglSurat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-10-2023" }));
        DTPTglSurat.setDisplayFormat("dd-MM-yyyy");
        DTPTglSurat.setOpaque(false);
        DTPTglSurat.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPTglSuratItemStateChanged(evt);
            }
        });
        DTPTglSurat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglSuratKeyPressed(evt);
            }
        });
        FormInput.add(DTPTglSurat);
        DTPTglSurat.setBounds(80, 100, 107, 23);

        jLabel6.setText("Keperluan :");
        FormInput.add(jLabel6);
        jLabel6.setBounds(0, 130, 75, 23);

        jLabel7.setText("Tgl. Surat :");
        FormInput.add(jLabel7);
        jLabel7.setBounds(0, 100, 75, 23);

        jLabel8.setText("Tinggi :");
        FormInput.add(jLabel8);
        jLabel8.setBounds(300, 130, 75, 23);

        jLabel9.setText("Berat :");
        FormInput.add(jLabel9);
        jLabel9.setBounds(315, 160, 60, 23);

        TBeratBadan.setBackground(new java.awt.Color(245, 250, 240));
        TBeratBadan.setHighlighter(null);
        FormInput.add(TBeratBadan);
        TBeratBadan.setBounds(380, 160, 65, 23);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("/mmHg");
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        FormInput.add(jLabel10);
        jLabel10.setBounds(450, 100, 40, 23);

        jLabel11.setText("Tensi :");
        FormInput.add(jLabel11);
        jLabel11.setBounds(315, 100, 60, 23);

        TTensi.setBackground(new java.awt.Color(245, 250, 240));
        TTensi.setHighlighter(null);
        FormInput.add(TTensi);
        TTensi.setBounds(380, 100, 65, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        TKesan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TKesan.setColumns(20);
        TKesan.setRows(5);
        TKesan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKesanKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(TKesan);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(80, 130, 240, 58);

        jLabel13.setText("Dokter :");
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 70, 75, 23);

        TKdDokter.setBackground(new java.awt.Color(245, 250, 240));
        TKdDokter.setHighlighter(null);
        FormInput.add(TKdDokter);
        TKdDokter.setBounds(80, 70, 130, 23);

        TNamaDokter.setBackground(new java.awt.Color(245, 250, 240));
        TNamaDokter.setHighlighter(null);
        FormInput.add(TNamaDokter);
        TNamaDokter.setBounds(210, 70, 250, 23);

        jLabel14.setText("No. Surat :");
        jLabel14.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        FormInput.add(jLabel14);
        jLabel14.setBounds(0, 10, 75, 23);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("/cm");
        jLabel18.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        FormInput.add(jLabel18);
        jLabel18.setBounds(450, 130, 40, 23);

        TNoSurat.setBackground(new java.awt.Color(245, 250, 240));
        TNoSurat.setHighlighter(null);
        TNoSurat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TNoSuratActionPerformed(evt);
            }
        });
        FormInput.add(TNoSurat);
        TNoSurat.setBounds(80, 10, 210, 23);

        TNoRawat.setEditable(false);
        TNoRawat.setHighlighter(null);
        TNoRawat.setPreferredSize(new java.awt.Dimension(130, 23));
        FormInput.add(TNoRawat);
        TNoRawat.setBounds(80, 40, 130, 23);

        TNoRM1.setEditable(false);
        TNoRM1.setHighlighter(null);
        TNoRM1.setPreferredSize(new java.awt.Dimension(90, 23));
        FormInput.add(TNoRM1);
        TNoRM1.setBounds(210, 40, 80, 23);

        TNamaPasien1.setEditable(false);
        TNamaPasien1.setHighlighter(null);
        TNamaPasien1.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(TNamaPasien1);
        TNamaPasien1.setBounds(290, 40, 190, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('3');
        BtnDokter.setToolTipText("ALt+3");
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokter);
        BtnDokter.setBounds(460, 70, 28, 23);

        jLabel19.setText("Gol. Darah :");
        FormInput.add(jLabel19);
        jLabel19.setBounds(210, 100, 60, 23);

        GolDarah.setBackground(new java.awt.Color(245, 250, 240));
        GolDarah.setHighlighter(null);
        FormInput.add(GolDarah);
        GolDarah.setBounds(275, 100, 45, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
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

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        Scroll.setOpaque(true);
        Scroll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ScrollMouseClicked(evt);
            }
        });

        tbDataSuratSehat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDataSuratSehat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataSuratSehatMouseClicked(evt);
            }
        });
        tbDataSuratSehat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDataSuratSehatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbDataSuratSehat);
        tbDataSuratSehat.setComponentPopupMenu(jPopupMenu1);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DTPTglSuratItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPTglSuratItemStateChanged
        try {
            autoNomor();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_DTPTglSuratItemStateChanged

    private void DTPTglSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglSuratKeyPressed

    }//GEN-LAST:event_DTPTglSuratKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (TNoSurat.getText().trim().equals("")) {
            Valid.textKosong(TNoSurat, "no surat");
        } else if (TNoRawat.getText().trim().equals("")) {
            Valid.textKosong(TNoRawat, "no rawat");
        } else if (TKdDokter.getText().trim().equals("")) {
            Valid.textKosong(TKdDokter, "kode dokter");
        } else if (TKesan.getText().trim().equals("")) {
            Valid.textKosong(TKesan, "keperluan");
        } else if (TTinggiBadan.getText().trim().equals("")) {
            Valid.textKosong(TTinggiBadan, "tinggi");
        } else if (TBeratBadan.getText().trim().equals("")) {
            Valid.textKosong(TBeratBadan, "berat");
        } else if (TTensi.getText().trim().equals("")) {
            Valid.textKosong(TTensi, "tensi");
        } else {
            if (Sequel.menyimpantf("datasurat", "?,?,?,?,?,?,?,?,?,?,?,?", "Kode", 12, new String[]{"0",TNoSurat.getText(), TNoRawat.getText(),
                TKdDokter.getText(), Valid.SetTgl(DTPTglSurat.getSelectedItem() + ""),"Sehat",TKesan.getText(),TTinggiBadan.getText(),TBeratBadan.getText(),TTensi.getText(),GolDarah.getText().toUpperCase(),var.getkode()}) == true) {
                tampil();
                emptTeks();
            } else {
                TNoSurat.requestFocus();
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, TNoSurat, BtnBatal);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        BtnSimpan.setEnabled(true);
        BtnEdit.setEnabled(false);
        BtnHapus.setEnabled(false);
        BtnPrint.setEnabled(false);
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            emptTeks();
        } else {
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if (TNoSurat.getText().trim().equals("")) {
            Valid.textKosong(TNoSurat, "no surat");
        } else if (TNoRawat.getText().trim().equals("")) {
            Valid.textKosong(TNoRawat, "no rawat");
        } else if (TKdDokter.getText().trim().equals("")) {
            Valid.textKosong(TKdDokter, "kode dokter");
        } else if (TKesan.getText().trim().equals("")) {
            Valid.textKosong(TKesan, "keperluan");
        } else if (TTinggiBadan.getText().trim().equals("")) {
            Valid.textKosong(TTinggiBadan, "tinggi");
        } else if (TBeratBadan.getText().trim().equals("")) {
            Valid.textKosong(TBeratBadan, "berat");
        } else if (TTensi.getText().trim().equals("")) {
            Valid.textKosong(TTensi, "tensi");
        } else {
            if (tbDataSuratSehat.getSelectedRow() > -1) {
                Sequel.mengedit2("datasurat", "id=?", "tgl_surat=?,no_surat=?,data1=?,data2=?,data3=?,data4=?,data5=?", 8, new String[]{                            
                    Valid.SetTgl(DTPTglSurat.getSelectedItem() + ""),TNoSurat.getText(),TKesan.getText(),TTinggiBadan.getText(),
                    TBeratBadan.getText(),TTensi.getText(),GolDarah.getText().toUpperCase(),no
                        });
                tampil();
                emptTeks();
            }
        }
    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if (TNoSurat.getText().trim().equals("")) {
            Valid.textKosong(TNoSurat, "no surat");
        } else {
            int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, Yakin mau dihapus..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                Sequel.meghapus("datasurat", "id", tbDataSuratSehat.getValueAt(tbDataSuratSehat.getSelectedRow(), 0).toString());
                tampil();
                emptTeks();
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

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed

    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed

    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed

    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed

    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed

    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void TKesanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKesanKeyPressed

    }//GEN-LAST:event_TKesanKeyPressed

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

    }//GEN-LAST:event_BtnCariKeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        tampil();
    }//GEN-LAST:event_formWindowActivated

    private void tbDataSuratSehatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDataSuratSehatKeyPressed
       if (tabMode.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
                try {
                    ambil_data();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbDataSuratSehatKeyPressed

    private void tbDataSuratSehatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataSuratSehatMouseClicked
         if (tabMode.getRowCount() != 0) {
            try {
                ambil_data();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbDataSuratSehatMouseClicked

    private void ScrollMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ScrollMouseClicked

    }//GEN-LAST:event_ScrollMouseClicked

    private void TNoSuratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TNoSuratActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoSuratActionPerformed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        var.setform("DlgSuratSehat");
        dokter.isCek();
        dokter.TCari.requestFocus();
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void MnCetakSuratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else if (no.equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Gagal mencetak. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        } else if (!(no.equals(""))) {
            Map<String, Object> param = new HashMap<>();
            String cari = Sequel.cariIsi("SELECT kd_sps FROM datasurat join dokter on datasurat.kd_dokter=dokter.kd_dokter WHERE datasurat.no_rawat=?",TNoRawat.getText());
            String nm_sps="";
            switch (cari) {
                case "-":
                case "S0001":
                case "S0025":
                case "S0029":
                case "S0030":
                case "UMUM":
                case "S0015":
                case "S0016":
                    nm_sps = "";
                    break;
                default:
                    nm_sps = "Dokter Spesialis";
                    break;
            }
            param.put("namasps", var.getnamars());
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptSuratSehat.jrxml", "report", "::[ Surat Keterangan Sehat ]::",
                    "SELECT datasurat.id, datasurat.tgl_surat,datasurat.no_rawat, pasien.no_rkm_medis, pasien.nm_pasien, CONCAT(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur) as umur,"
                    + "pasien.pekerjaan, IF(pasien.jk='P','PEREMPUAN','LAKI-LAKI') as jnskel, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.tgl_lahir, pasien.tmp_lahir ,"
                    + "dokter.nm_dokter, datasurat.jns_surat, datasurat.no_surat, datasurat.data1, datasurat.data2, datasurat.data3,datasurat.data4, datasurat.data5,dokter.nip "
                    + "FROM datasurat inner JOIN reg_periksa inner JOIN pasien inner join kelurahan inner join kecamatan inner join kabupaten inner JOIN dokter "
                    + "on datasurat.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and datasurat.kd_dokter=dokter.kd_dokter "
                    + "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "
                    + "where datasurat.jns_surat='Sehat' AND datasurat.id='"+no+"'", param);
        } 
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnCetakSuratActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DlgSuratSehat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DlgSuratSehat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DlgSuratSehat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DlgSuratSehat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DlgSuratSehat dialog = new DlgSuratSehat(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    public widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPTglSurat;
    private widget.PanelBiasa FormInput;
    private widget.TextBox GolDarah;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnCetakSurat;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TBeratBadan;
    private widget.TextBox TCari;
    private widget.TextBox TKdDokter;
    private widget.TextArea TKesan;
    private widget.TextBox TNamaDokter;
    private widget.TextBox TNamaPasien1;
    private widget.TextBox TNoRM1;
    private widget.TextBox TNoRawat;
    private widget.TextBox TNoSurat;
    private widget.TextBox TTensi;
    private widget.TextBox TTinggiBadan;
    private widget.InternalFrame internalFrame1;
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
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass6;
    private widget.panelisi panelGlass7;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDataSuratSehat;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            PreparedStatement ps = koneksi_john.prepareStatement(
                "SELECT datasurat.id, datasurat.no_surat,datasurat.tgl_surat,datasurat.no_rawat, pasien.no_rkm_medis, pasien.nm_pasien,datasurat.kd_dokter, dokter.nm_dokter, datasurat.data1, datasurat.data2, datasurat.data3, datasurat.data4, datasurat.data5 "
                + "FROM datasurat JOIN reg_periksa JOIN pasien JOIN dokter on datasurat.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and datasurat.kd_dokter=dokter.kd_dokter "
                + "WHERE datasurat.jns_surat='Sehat' and datasurat.tgl_surat BETWEEN ? and ? and "
                + "(pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or dokter.nm_dokter like ?) ORDER BY datasurat.tgl_surat DESC");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, "%" + TCari.getText().trim() + "%");
                ps.setString(4, "%" + TCari.getText().trim() + "%");
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new String[]{
                        rs.getString("id"),rs.getString("no_surat"), rs.getString("tgl_surat"),rs.getString("no_rawat"), rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien"), rs.getString("kd_dokter"), rs.getString("nm_dokter"), rs.getString("data1"),
                        rs.getString("data2"), rs.getString("data3"),rs.getString("data4"),rs.getString("data5")
                    });
                }
                rs.last();
                LCount.setText("" + rs.getRow());
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

    public void setPasien(String norm, String norawat, String nama, String nama_dokter, String kd_dokter) {
        TNoRawat.setText(norawat);
        TNoRM1.setText(norm);
        TNamaPasien1.setText(nama);
        TKdDokter.setText(kd_dokter);
        TNamaDokter.setText(nama_dokter);
    }

    private void ambil_data() {
        no = tbDataSuratSehat.getValueAt(tbDataSuratSehat.getSelectedRow(), 0).toString();
        TNoSurat.setText((tbDataSuratSehat.getValueAt(tbDataSuratSehat.getSelectedRow(), 1).toString()));
        Valid.SetTgl(DTPTglSurat, tbDataSuratSehat.getValueAt(tbDataSuratSehat.getSelectedRow(), 2).toString());
        TNoRawat.setText(tbDataSuratSehat.getValueAt(tbDataSuratSehat.getSelectedRow(), 3).toString());
        TNoRM1.setText(tbDataSuratSehat.getValueAt(tbDataSuratSehat.getSelectedRow(), 4).toString());
        TNamaPasien1.setText(tbDataSuratSehat.getValueAt(tbDataSuratSehat.getSelectedRow(), 5).toString());
        TKdDokter.setText(tbDataSuratSehat.getValueAt(tbDataSuratSehat.getSelectedRow(), 6).toString());
        TNamaDokter.setText(tbDataSuratSehat.getValueAt(tbDataSuratSehat.getSelectedRow(), 7).toString());
        TKesan.setText(tbDataSuratSehat.getValueAt(tbDataSuratSehat.getSelectedRow(), 8).toString());
        TTinggiBadan.setText(tbDataSuratSehat.getValueAt(tbDataSuratSehat.getSelectedRow(), 9).toString());
        TBeratBadan.setText(tbDataSuratSehat.getValueAt(tbDataSuratSehat.getSelectedRow(), 10).toString());
        TTensi.setText(tbDataSuratSehat.getValueAt(tbDataSuratSehat.getSelectedRow(), 11).toString());
        GolDarah.setText(tbDataSuratSehat.getValueAt(tbDataSuratSehat.getSelectedRow(), 12).toString());
    }

    public void emptTeks() {
        TNoSurat.setText("");
        TNoRawat.setText("");
        TKdDokter.setText("");
        TKesan.setText("");
        TTinggiBadan.setText("");
        TBeratBadan.setText("");
        TTensi.setText("");
        TCari.setText("");
        TNoRM1.setText("");
        TNamaPasien1.setText("");
        TNamaDokter.setText("");
        TNoSurat.requestFocus();
        autoNomor();
    }

    private void autoNomor() {
        Valid.autoNomer6("select ifnull(MAX(CONVERT(LEFT(no_surat,4),signed)),0) from datasurat where "
                + "tgl_surat like '" + DTPTglSurat.getSelectedItem().toString().substring(6, 10) + "%' ", "/RSUD/-Yan Kes/" + DTPTglSurat.getSelectedItem().toString().substring(6, 10), 4, TNoSurat);
    }
    
    private void isForm() {
        if (ChkInput.isSelected() == true) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 220));
            FormInput.setVisible(true);
            ChkInput.setVisible(true);
        } else if (ChkInput.isSelected() == false) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 20));
            FormInput.setVisible(false);
            ChkInput.setVisible(true);
        }
    }
}
