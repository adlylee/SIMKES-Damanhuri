/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * DlgJadwal.java
 *
 * Created on May 22, 2010, 10:25:16 PM
 */
package kepegawaian;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import simrskhanza.DlgCariPoli;

/**
 *
 * @author dosen
 */
public class DlgJadwalDokter extends javax.swing.JDialog {

    private DefaultTableModel tabMode;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private String id = "", dateString, dayOfWeek, hari;
    private Date date = null;
    private int i = 0;
    private DlgCariDokter dokter = new DlgCariDokter(null, false);
    private DlgCariPoli poli = new DlgCariPoli(null, false);
    private String[] remainingDaysArray;

    /**
     * Creates new form DlgJadwal
     *
     * @param parent
     * @param modal
     */
    public DlgJadwalDokter(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8, 1);
        setSize(628, 674);

        Valid.LoadTahun(ThnCari);
        tbJadwal.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbJadwal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
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
                    kddokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                    nmdokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                }
                kddokter.requestFocus();
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

        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (poli.getTable().getSelectedRow() != -1) {
                    KdPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(), 0).toString());
                    TPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(), 1).toString());
                }
                KdPoli.requestFocus();
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

        Popup = new javax.swing.JPopupMenu();
        ppSemua = new javax.swing.JMenuItem();
        ppSemua1 = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbJadwal = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        LCount = new widget.Label();
        jLabel7 = new widget.Label();
        BtnEdit = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        panelBiasa1 = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        nmdokter = new widget.TextBox();
        jLabel10 = new widget.Label();
        TPoli = new widget.TextBox();
        btnDokter = new widget.Button();
        kddokter = new widget.TextBox();
        KdPoli = new widget.TextBox();
        BtnPoli = new widget.Button();
        label11 = new widget.Label();
        ThnCari = new widget.ComboBox();
        BlnCari = new widget.ComboBox();
        scrollPane1 = new widget.ScrollPane();
        Tanggal = new widget.TextArea();
        jLabel8 = new widget.Label();

        Popup.setName("Popup"); // NOI18N

        ppSemua.setBackground(new java.awt.Color(255, 255, 254));
        ppSemua.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSemua.setForeground(new java.awt.Color(70, 70, 70));
        ppSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSemua.setText("Pilih Semua");
        ppSemua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSemua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSemua.setIconTextGap(8);
        ppSemua.setName("ppSemua"); // NOI18N
        ppSemua.setPreferredSize(new java.awt.Dimension(170, 25));
        ppSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSemuaActionPerformed(evt);
            }
        });
        Popup.add(ppSemua);

        ppSemua1.setBackground(new java.awt.Color(255, 255, 254));
        ppSemua1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSemua1.setForeground(new java.awt.Color(70, 70, 70));
        ppSemua1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSemua1.setText("Update tanggal");
        ppSemua1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSemua1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSemua1.setIconTextGap(8);
        ppSemua1.setName("ppSemua1"); // NOI18N
        ppSemua1.setPreferredSize(new java.awt.Dimension(170, 25));
        ppSemua1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSemua1ActionPerformed(evt);
            }
        });
        Popup.add(ppSemua1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengaturan Jadwal Dokter ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbJadwal.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbJadwal.setName("tbJadwal"); // NOI18N
        tbJadwal.setComponentPopupMenu(Popup);
        tbJadwal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbJadwalMouseClicked(evt);
            }
        });
        tbJadwal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbJadwalKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbJadwalKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbJadwal);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(null);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('U');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+U");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
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
        BtnSimpan.setBounds(6, 10, 100, 30);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
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
        BtnHapus.setBounds(220, 10, 100, 30);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
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
        BtnPrint.setBounds(500, 10, 100, 30);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
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
        BtnKeluar.setBounds(610, 10, 100, 30);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass8.add(LCount);
        LCount.setBounds(400, 15, 90, 23);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass8.add(jLabel7);
        jLabel7.setBounds(310, 15, 80, 23);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
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
        BtnEdit.setBounds(110, 10, 100, 30);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(450, 23));
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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('4');
        BtnAll.setToolTipText("Alt+4");
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
        panelGlass9.add(BtnAll);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        panelBiasa1.setName("panelBiasa1"); // NOI18N
        panelBiasa1.setPreferredSize(new java.awt.Dimension(1023, 75));
        panelBiasa1.setLayout(null);

        jLabel3.setText("Dokter :");
        jLabel3.setName("jLabel3"); // NOI18N
        panelBiasa1.add(jLabel3);
        jLabel3.setBounds(0, 10, 90, 23);

        nmdokter.setEditable(false);
        nmdokter.setHighlighter(null);
        nmdokter.setName("nmdokter"); // NOI18N
        panelBiasa1.add(nmdokter);
        nmdokter.setBounds(210, 10, 440, 23);

        jLabel10.setText("Poliklinik :");
        jLabel10.setName("jLabel10"); // NOI18N
        panelBiasa1.add(jLabel10);
        jLabel10.setBounds(250, 40, 66, 23);

        TPoli.setEditable(false);
        TPoli.setHighlighter(null);
        TPoli.setName("TPoli"); // NOI18N
        TPoli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPoliKeyPressed(evt);
            }
        });
        panelBiasa1.add(TPoli);
        TPoli.setBounds(422, 40, 228, 23);

        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter.setMnemonic('1');
        btnDokter.setToolTipText("ALt+1");
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        panelBiasa1.add(btnDokter);
        btnDokter.setBounds(651, 10, 28, 23);

        kddokter.setHighlighter(null);
        kddokter.setName("kddokter"); // NOI18N
        kddokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddokterKeyPressed(evt);
            }
        });
        panelBiasa1.add(kddokter);
        kddokter.setBounds(95, 10, 115, 23);

        KdPoli.setHighlighter(null);
        KdPoli.setName("KdPoli"); // NOI18N
        panelBiasa1.add(KdPoli);
        KdPoli.setBounds(320, 40, 102, 23);

        BtnPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPoli.setMnemonic('2');
        BtnPoli.setToolTipText("ALt+2");
        BtnPoli.setName("BtnPoli"); // NOI18N
        BtnPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPoliActionPerformed(evt);
            }
        });
        panelBiasa1.add(BtnPoli);
        BtnPoli.setBounds(650, 40, 28, 23);

        label11.setText("Tahun & Bulan :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(90, 23));
        panelBiasa1.add(label11);
        label11.setBounds(0, 40, 90, 23);

        ThnCari.setName("ThnCari"); // NOI18N
        ThnCari.setPreferredSize(new java.awt.Dimension(85, 23));
        ThnCari.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ThnCariItemStateChanged(evt);
            }
        });
        panelBiasa1.add(ThnCari);
        ThnCari.setBounds(95, 40, 85, 23);

        BlnCari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        BlnCari.setName("BlnCari"); // NOI18N
        BlnCari.setPreferredSize(new java.awt.Dimension(62, 23));
        BlnCari.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                BlnCariItemStateChanged(evt);
            }
        });
        panelBiasa1.add(BlnCari);
        BlnCari.setBounds(180, 40, 62, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        Tanggal.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Tanggal.setColumns(20);
        Tanggal.setRows(5);
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(Tanggal);

        panelBiasa1.add(scrollPane1);
        scrollPane1.setBounds(750, 10, 240, 53);

        jLabel8.setText("Tanggal :");
        jLabel8.setName("jLabel8"); // NOI18N
        panelBiasa1.add(jLabel8);
        jLabel8.setBounds(695, 10, 50, 23);

        internalFrame1.add(panelBiasa1, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (nmdokter.getText().trim().equals("")) {
            Valid.textKosong(kddokter, "Dokter");
        } else if (TPoli.getText().trim().equals("")) {
            Valid.textKosong(KdPoli, "Poliklinik");
        } else {
            int reply = JOptionPane.showConfirmDialog(rootPane, "Apakah yakin ingin menyimpan data...??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                isArrayTanggal();
                if (id.equals("")) {
                    id = null;
                } else {
                    id = id;
                }
                Sequel.menyimpan3("jadwal_dokter", "?,?,?,?,?,?", 6, new String[]{
                    id, kddokter.getText(), KdPoli.getText(), ThnCari.getSelectedItem().toString(), BlnCari.getSelectedItem().toString(), Arrays.toString(remainingDaysArray)
                }, "kd_dokter=? and kd_poli=? and tahun=? and bulan=? and id=?", "tanggal=?", 6, new String[]{
                    Arrays.toString(remainingDaysArray), kddokter.getText(), KdPoli.getText(), ThnCari.getSelectedItem().toString(), BlnCari.getSelectedItem().toString(), id
                });
                    tampil();
                }
            }        
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnKeluar, BtnHapus);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if (tabMode.getRowCount() != 0) {
            if (tbJadwal.getSelectedRow() != -1) {
                int reply = JOptionPane.showConfirmDialog(rootPane, "Apakah yakin ingin menghapus data...??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    Sequel.queryu2("delete from jadwal_dokter where id=? and kd_dokter=? and kd_poli=? and tahun=? and bulan=?", 5, new String[]{
                        tabMode.getValueAt(tbJadwal.getSelectedRow(), 1).toString(), kddokter.getText(), KdPoli.getText(), ThnCari.getSelectedItem().toString(), BlnCari.getSelectedItem().toString()
                    });
                    tampil();
                }
            }
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnSimpan, BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnPrint, TCari);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed

}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            //Valid.pindah(evt, BtnEdit, BtnKeluar);
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
            Valid.pindah(evt, BtnCari, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void tbJadwalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJadwalKeyPressed
        if (tabMode.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            } else if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
                tabMode.setValueAt("", tbJadwal.getSelectedRow(), tbJadwal.getSelectedColumn());
            }
        }
    }//GEN-LAST:event_tbJadwalKeyPressed

    private void TPoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPoliKeyPressed
        //Valid.pindah(evt,TJns,BtnSimpan);
    }//GEN-LAST:event_TPoliKeyPressed

    private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokterActionPerformed

    private void kddokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddokterKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter='" + kddokter.getText() + "'", nmdokter);
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnDokterActionPerformed(null);
        } else {
//            Valid.pindah(evt, TCari, cmbHari);
        }
    }//GEN-LAST:event_kddokterKeyPressed

    private void BtnPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPoliActionPerformed
        poli.isCek();
        poli.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setVisible(true);
    }//GEN-LAST:event_BtnPoliActionPerformed

    private void ThnCariItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ThnCariItemStateChanged
        if (this.isActive() == true) {
            tampil();
        }
    }//GEN-LAST:event_ThnCariItemStateChanged

    private void BlnCariItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_BlnCariItemStateChanged
        if (this.isActive() == true) {
            tampil();
        }
    }//GEN-LAST:event_BlnCariItemStateChanged

    private void tbJadwalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbJadwalMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbJadwalMouseClicked

    private void tbJadwalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJadwalKeyReleased
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbJadwalKeyReleased

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if (tbJadwal.getSelectedRow() != -1) {
            int reply = JOptionPane.showConfirmDialog(rootPane, "Apakah yakin ingin mengubah data...??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                Sequel.queryu2("update jadwal_dokter set tanggal=?, kd_dokter=?, kd_poli=? "
                        + " where id=? and tahun=? and bulan=? and kd_dokter=? and kd_poli=?", 8,
                        new String[]{Tanggal.getText(), kddokter.getText(), KdPoli.getText(),
                            tabMode.getValueAt(tbJadwal.getSelectedRow(), 1).toString(), tabMode.getValueAt(tbJadwal.getSelectedRow(), 5).toString().substring(0, 4),
                            tabMode.getValueAt(tbJadwal.getSelectedRow(), 5).toString().substring(5, 7), tabMode.getValueAt(tbJadwal.getSelectedRow(), 7).toString(), tabMode.getValueAt(tbJadwal.getSelectedRow(), 8).toString()
                        });
                tampil();
                JOptionPane.showMessageDialog(null, "Berhasil edit..");
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

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed

    }//GEN-LAST:event_TanggalKeyPressed

    private void ppSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSemuaActionPerformed
        for (i = 0; i < tbJadwal.getRowCount(); i++) {
            tbJadwal.setValueAt(true, i, 0);
        }
    }//GEN-LAST:event_ppSemuaActionPerformed

    private void ppSemua1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSemua1ActionPerformed
        int reply = JOptionPane.showConfirmDialog(rootPane, "Apakah yakin ingin update jadwal...??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            for (i = 0; i < tbJadwal.getRowCount(); i++) {
                if (tbJadwal.getValueAt(i, 0).toString().equals("true")) {
                    isArrayTanggal();
                    Sequel.queryu2("update jadwal_dokter set tanggal=?"
                            + " where id=? and tahun=? and bulan=? and kd_dokter=? and kd_poli=?", 6,
                            new String[]{Arrays.toString(remainingDaysArray), tabMode.getValueAt(i, 1).toString(), tabMode.getValueAt(i, 5).toString().substring(0, 4),
                                tabMode.getValueAt(i, 5).toString().substring(5, 7), tabMode.getValueAt(i, 7).toString(), tabMode.getValueAt(i, 8).toString()
                            });
                }
            }
            tampil();
            JOptionPane.showMessageDialog(null, "Berhasil update jadwal..!!!");
        }
    }//GEN-LAST:event_ppSemua1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgJadwalDokter dialog = new DlgJadwalDokter(new javax.swing.JFrame(), true);
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
    private widget.ComboBox BlnCari;
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPoli;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.TextBox KdPoli;
    private widget.Label LCount;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TPoli;
    private widget.TextArea Tanggal;
    private widget.ComboBox ThnCari;
    private widget.Button btnDokter;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel3;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private widget.TextBox kddokter;
    private widget.Label label11;
    private widget.TextBox nmdokter;
    private widget.PanelBiasa panelBiasa1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JMenuItem ppSemua;
    private javax.swing.JMenuItem ppSemua1;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbJadwal;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Object[] row = {"P","ID", "No", "Nama Dokter", "Poliklinik", "Tahun/Bulan", "Tanggal", "Kode Dokter", "Kode Poli"
        };
        tabMode = new DefaultTableModel(null, row) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class,java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbJadwal.setModel(tabMode);

        for (int i = 0; i < 9; i++) {
            TableColumn column = tbJadwal.getColumnModel().getColumn(i);
            if (i == 1) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 2) {
                column.setPreferredWidth(35);
            } else if (i == 3) {
                column.setPreferredWidth(200);
            } else if (i == 4) {
                column.setPreferredWidth(250);
            } else if (i == 5) {
                column.setPreferredWidth(80);
            } else if (i == 6) {
                column.setPreferredWidth(570);
            } else if (i == 0) {
                column.setPreferredWidth(35);
            } else {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbJadwal.setDefaultRenderer(Object.class, new WarnaTable());

        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement(
                    "select dokter.nm_dokter, poliklinik.nm_poli, jadwal_dokter.tahun, jadwal_dokter.bulan, jadwal_dokter.tanggal,jadwal_dokter.id, jadwal_dokter.kd_dokter,jadwal_dokter.kd_poli "
                    + "from jadwal_dokter inner join dokter inner join poliklinik on jadwal_dokter.kd_dokter=dokter.kd_dokter and jadwal_dokter.kd_poli=poliklinik.kd_poli "
                    + "where jadwal_dokter.tahun like ? and jadwal_dokter.bulan like ? and (dokter.nm_dokter like ? or poliklinik.nm_poli like ?) order by dokter.nm_dokter");
            try {
                ps.setString(1, "%" + ThnCari.getSelectedItem().toString() + "%");
                ps.setString(2, "%" + BlnCari.getSelectedItem().toString() + "%");
                ps.setString(3, "%" + TCari.getText().trim() + "%");
                ps.setString(4, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                i = 1;
                while (rs.next()) {
                    tabMode.addRow(new Object[]{
                        false,rs.getString("id"), " " + i + ".", rs.getString("nm_dokter"), rs.getString("nm_poli"), rs.getString("tahun").substring(0, 4) + "/" + rs.getString("bulan"), rs.getString("tanggal"), rs.getString("kd_dokter"), rs.getString("kd_poli")
                    });
                    i++;
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
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabMode.getRowCount());
    }

    public void isCek() {
        BtnSimpan.setEnabled(var.getjadwal_pegawai());
        BtnHapus.setEnabled(var.getjadwal_pegawai());
    }

    private void getData() {
        int row = tbJadwal.getSelectedRow();
        if (row != -1) {
            id = tabMode.getValueAt(row, 1).toString();
            nmdokter.setText(tabMode.getValueAt(row, 3).toString());
            TPoli.setText(tabMode.getValueAt(row, 4).toString());
            ThnCari.setSelectedItem(tabMode.getValueAt(row, 5).toString().substring(0, 4));
            BlnCari.setSelectedItem(tabMode.getValueAt(row, 5).toString().substring(5, 7));
            Tanggal.setText(tbJadwal.getValueAt(row, 6).toString());
            kddokter.setText(tbJadwal.getValueAt(row, 7).toString());
            KdPoli.setText(tbJadwal.getValueAt(row, 8).toString());
        }
    } 
    
    String konversi(int year, int month, int day) {
        dateString = String.format("%d-%d-%d", year, month, day);
        try {
            date = new SimpleDateFormat("yyyy-M-d").parse(dateString);
        } catch (Exception ex) {
            Logger.getLogger(DlgJadwalDokter.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Then get the day of week from the Date based on specific locale.
        dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);

        switch (dayOfWeek) {
            case "Monday":
                hari = "Senin";
                break;
            case "Tuesday":
                hari = "Selasa";
                break;
            case "Wednesday":
                hari = "Rabu";
                break;
            case "Thursday":
                hari = "Kamis";
                break;
            case "Friday":
                hari = "Jumat";
                break;
            case "Saturday":
                hari = "Sabtu";
                break;
            case "Sunday":
                hari = "Minggu";
                break;
        }
        return hari;
    }

    private void isArrayTanggal() {
        String tanggal = Sequel.cariIsi("select ktg from set_hari_libur where tanggal='" + ThnCari.getSelectedItem().toString() + "-" + BlnCari.getSelectedItem().toString() + "'");

        String[] hariLiburArray = tanggal.split(",");
        List<String> tanggalList = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            tanggalList.add(String.format("%02d", i));
        }
        tanggalList.removeAll(Arrays.asList(hariLiburArray));
        remainingDaysArray = tanggalList.toArray(new String[0]);
    }

}
