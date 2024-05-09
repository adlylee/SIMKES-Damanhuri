package ipsrs;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;
import keuangan.Jurnal;

public class DlgSirkulasiNonMedis extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Jurnal jur = new Jurnal();
    private Connection koneksi = koneksiDB.condb();
    private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    private double ttltotalbeli = 0, totalbeli = 0, stok = 0, aset = 0, ttlaset = 0, jumlahbeli = 0, stok_awal = 0, harga_awal = 0, ttltotalpesan = 0, totalpesan = 0, jumlahpesan = 0, stokbyfaktur = 0, hargabyfaktur = 0, stokgetbyfaktur = 0, hargagetbyfaktur = 0, stokpostbyfaktur = 0, hargapostbyfaktur = 0, saldo_awal = 0, saldo_akhir = 0,
            jumlahkeluar, totalkeluar, ttltotalkeluar, ttlmasuk=0,stokmasuk =0, sisasaldo =0,saldoawalbrg=0,sisastok=0,stokawal=0,ttlstokawal=0,ttlstokmsk,ttlstokklr,ttlstokakhir,harga=0;
    private String kd_rek = "",tgl="";
    private PreparedStatement ps, ps2, ps3, ps4;
    private ResultSet rs, rs2, rs3, rs4;
    public DlgCariPetugas petugas = new DlgCariPetugas(null, false);

    /**
     * @param parent
     * @param modal
     */
    public DlgSirkulasiNonMedis(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row = {"Kode Barang","Kode Rekening", "Nama Barang", "Satuan", "Harga", "Stok Awal", "Stok Masuk","Stok Keluar","Stok Akhir", "Saldo Awal" ,"Saldo Masuk",
             "Saldo Keluar", "Saldo Akhir"};
        tabMode = new DefaultTableModel(null, row) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 13; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            switch (i) {
                case 0:
                    column.setPreferredWidth(90);
                    break;
                case 1:
                    column.setPreferredWidth(100);
                    break;
                case 2:
                    column.setPreferredWidth(260);
                    break;
                case 3:
                    column.setPreferredWidth(100);
                    break;
                case 4:
                    column.setPreferredWidth(70);
                    break;
                case 5:
                    column.setPreferredWidth(100);
                    break;
                case 6:
                    column.setPreferredWidth(70);
                    break;
                case 7:
                    column.setPreferredWidth(100);
                    break;
                case 8:
                    column.setPreferredWidth(70);
                    break;
                case 9:
                    column.setPreferredWidth(100);
                    break;
                case 10:
                    column.setPreferredWidth(100);
                    break;
                case 11:
                    column.setPreferredWidth(100);
                    break;
                case 12:
                    column.setPreferredWidth(100);
                    break;
                default:
                    break;
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        TCari.setDocument(new batasInput((byte) 100).getKata(TCari));
        

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
                    kdptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                    nmptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                    kdptg.requestFocus();
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

        Kd2 = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi4 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label19 = new widget.Label();
        kdptg = new widget.TextBox();
        nmptg = new widget.TextBox();
        btnPetugas = new widget.Button();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label9 = new widget.Label();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Sirkulasi Barang Non Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 13), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbDokter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDokter.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDokter.setName("tbDokter"); // NOI18N
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tanggal Transaksi :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(113, 23));
        panelisi4.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(110, 23));
        panelisi4.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi4.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(110, 23));
        panelisi4.add(Tgl2);

        label19.setText("Petugas :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(85, 23));
        panelisi4.add(label19);

        kdptg.setEditable(false);
        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi4.add(kdptg);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nmptg);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('1');
        btnPetugas.setToolTipText("Alt+1");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        panelisi4.add(btnPetugas);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(69, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(300, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
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
        panelisi1.add(BtnCari);

        label9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(79, 30));
        panelisi1.add(label9);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('A');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+A");
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
        BtnPrint.setMnemonic('P');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+P");
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

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        } else if (tabMode.getRowCount() != 0) {

            Sequel.queryu("delete from temporary");
            int row = tabMode.getRowCount();
            for (int i = 0; i < row; i++) {
                Sequel.menyimpan("temporary", "'0',?,?,?,?,?,?,?,?,?,?,?,?,?,'','','','','','','','','','','','','','','','','','','','','','','',''", 13, new String[]{
                    tabMode.getValueAt(i, 0).toString(), tabMode.getValueAt(i, 1).toString(), tabMode.getValueAt(i, 2).toString(),
                    tabMode.getValueAt(i, 3).toString(), tabMode.getValueAt(i, 4).toString(), tabMode.getValueAt(i, 5).toString(),
                    tabMode.getValueAt(i, 6).toString(), tabMode.getValueAt(i, 7).toString(), tabMode.getValueAt(i, 8).toString(),
                    tabMode.getValueAt(i, 9).toString(), tabMode.getValueAt(i, 10).toString(), tabMode.getValueAt(i, 11).toString(), tabMode.getValueAt(i, 12).toString()
                });
            }

            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Valid.MyReport("rptSirkulasiNonMedis.jrxml", "report", "::[ Stock Off Name Barang Non Medis]::",
                    "select * from temporary order by no asc", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, Tgl2, BtnKeluar);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnPrint, Tgl1);
        }
    }//GEN-LAST:event_BtnKeluarKeyPressed

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
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        prosesCari();
        this.setCursor(Cursor.getDefaultCursor());
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
        kdptg.setText("");
        nmptg.setText("");
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        prosesCari();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnAllActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

    }//GEN-LAST:event_formWindowOpened

    private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kdptgKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
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
            DlgSirkulasiNonMedis dialog = new DlgSirkulasiNonMedis(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.TextBox Kd2;
    private widget.TextBox TCari;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.TextBox kdptg;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label9;
    private widget.TextBox nmptg;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void prosesCari() {
        Valid.tabelKosong(tabMode);
        try {
            ps4 = koneksi.prepareStatement("SELECT kd_jenis FROM ipsrs_setpj WHERE nik like ? and ipsrs_setpj.status='1'");
            try {
                ps4.setString(1, "%" + kdptg.getText() + "%");
                rs4 = ps4.executeQuery();
                while (rs4.next()) {

                    ps = koneksi.prepareStatement("select ipsrsbarang.kode_brng,ipsrsbarang.nama_brng, "
                            + "kodesatuan.satuan,ipsrsbarang.stok,(ipsrsbarang.stok*ipsrsbarang.harga) as aset, ipsrsbarang.jenis "
                            + "from ipsrsbarang inner join ipsrsjenisbarang inner join kodesatuan on ipsrsbarang.kode_sat=kodesatuan.kode_sat "
                            + "and ipsrsbarang.jenis=ipsrsjenisbarang.kd_jenis "
                            + "where ipsrsbarang.jenis like ? and ipsrsbarang.kode_brng like ? or "
                            + "ipsrsbarang.jenis like ? and kodesatuan.satuan like ? "
                            + " order by ipsrsbarang.kode_brng");
                    try {
                        ttltotalbeli = 0;
                        ttltotalpesan = 0;
                        ttltotalkeluar = 0;
                        ttlaset = 0;
                        saldo_awal = 0;
                        saldo_akhir = 0;
                        ps.setString(1, "%" + rs4.getString("kd_jenis") + "%");
                        ps.setString(2, "%" + TCari.getText().trim() + "%");
                        ps.setString(3, "%" + rs4.getString("kd_jenis") + "%");
                        ps.setString(4, "%" + TCari.getText().trim() + "%");
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            totalbeli = 0;
                            jumlahbeli = 0;
                            totalpesan = 0;
                            jumlahpesan = 0;
                            jumlahkeluar = 0;
                            totalkeluar = 0;
                            stok = 0;
                            aset = 0;
                            stok_awal = 0;
                            harga_awal = 0;

                            stok = rs.getDouble("stok");
                            aset = rs.getDouble("aset");

                            ps2 = koneksi.prepareStatement("select stok, harga ,tgl_beli"
                                    + " from ipsrsgudang "
                                    + " where ipsrsgudang.kode_brng=? and ipsrsgudang.tgl_beli "
                                    + " between ? and ? and ipsrsgudang.no_faktur='Opname' ORDER BY tgl_beli ASC LIMIT 1");
                            try { 
                                ps2.setString(1, rs.getString(1));
                                ps2.setString(2, Valid.SetTgl(Tgl1.getSelectedItem() + ""));
                                ps2.setString(3, Valid.SetTgl(Tgl2.getSelectedItem() + ""));
                                rs2 = ps2.executeQuery();
                                if (rs2.next()) {
                                    stok_awal = rs2.getDouble(1);
                                    harga_awal = rs2.getDouble(2);
                                    tgl = rs2.getString("tgl_beli");
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi Stok Awal : " + e);
                            } finally {
                                if (rs2 != null) {
                                    rs2.close();
                                }
                                if (ps2 != null) {
                                    ps2.close();
                                }
                            }

                            ps2 = koneksi.prepareStatement("select sum(ipsrsdetailbeli.jumlah), sum(ipsrsdetailbeli.subtotal) "
                                    + " from ipsrspembelian inner join ipsrsdetailbeli "
                                    + " on ipsrspembelian.no_faktur=ipsrsdetailbeli.no_faktur "
                                    + " where ipsrsdetailbeli.kode_brng=? and ipsrspembelian.tgl_beli "
                                    + " between ? and ? ");
                            try {
                                ps2.setString(1, rs.getString(1));
                                ps2.setString(2, Valid.SetTgl(Tgl1.getSelectedItem() + ""));
                                ps2.setString(3, Valid.SetTgl(Tgl2.getSelectedItem() + ""));
                                rs2 = ps2.executeQuery();
                                if (rs2.next()) {
                                    jumlahbeli = rs2.getDouble(1);
                                    totalbeli = rs2.getDouble(2);
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi Detail Beli : " + e);
                            } finally {
                                if (rs2 != null) {
                                    rs2.close();
                                }
                                if (ps2 != null) {
                                    ps2.close();
                                }
                            }

                            //ipsrspemesanan
                            ps2 = koneksi.prepareStatement("select sum(ipsrsdetailpesan.jumlah), sum(ipsrsdetailpesan.subtotal) "
                                    + " from ipsrspemesanan inner join ipsrsdetailpesan "
                                    + " on ipsrspemesanan.no_faktur=ipsrsdetailpesan.no_faktur "
                                    + " where ipsrsdetailpesan.kode_brng=? and ipsrspemesanan.tgl_pesan "
                                    + " between ? and ? ");
                            try {
                                ps2.setString(1, rs.getString(1));
                                ps2.setString(2, Valid.SetTgl(Tgl1.getSelectedItem() + ""));
                                ps2.setString(3, Valid.SetTgl(Tgl2.getSelectedItem() + ""));
                                rs2 = ps2.executeQuery();
                                if (rs2.next()) {
                                    jumlahpesan = rs2.getDouble(1);
                                    totalpesan = rs2.getDouble(2);
                                }
                            } catch (Exception e) {
                                System.out.println("Pemesanan : " + e);
                            } finally {
                                if (rs2 != null) {
                                    rs2.close();
                                }
                                if (ps2 != null) {
                                    ps2.close();
                                }
                            }

                            ps2 = koneksi.prepareStatement("select sum(ipsrsdetailpengeluaran.jumlah), sum(ipsrsdetailpengeluaran.total) "
                                    + " from ipsrspengeluaran inner join ipsrsdetailpengeluaran "
                                    + " on ipsrspengeluaran.no_keluar=ipsrsdetailpengeluaran.no_keluar "
                                    + " where ipsrsdetailpengeluaran.kode_brng=? and "
                                    + " ipsrspengeluaran.tanggal between ? and ?");
                            try {
                                ps2.setString(1, rs.getString(1));
                                ps2.setString(2, Valid.SetTgl(Tgl1.getSelectedItem() + ""));
                                ps2.setString(3, Valid.SetTgl(Tgl2.getSelectedItem() + ""));
                                rs2 = ps2.executeQuery();
                                if (rs2.next()) {
                                    jumlahkeluar = rs2.getDouble(1);
                                    totalkeluar = rs2.getDouble(2);
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi keluar : " + e);
                            } finally {
                                if (rs2 != null) {
                                    rs2.close();
                                }
                                if (ps2 != null) {
                                    ps2.close();
                                }
                            }

                            ps2 = koneksi.prepareStatement("select rekeningtahun.saldo_awal , rekeningtahun.kd_rek"
                                    + " from rekeningtahun inner join set_akun_sub_akun "
                                    + " on rekeningtahun.kd_rek=set_akun_sub_akun.kode_akun "
                                    + " where set_akun_sub_akun.kode_mapping=?");
                            try {
                                ps2.setString(1, rs.getString(6));
                                rs2 = ps2.executeQuery();
                                if (rs2.next()) {
                                    saldo_awal = rs2.getDouble(1);
                                    kd_rek = rs2.getString(2);
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi rekening : " + e);
                            } finally {
                                if (rs2 != null) {
                                    rs2.close();
                                }
                                if (ps2 != null) {
                                    ps2.close();
                                }
                            }
                            
                            ps2 = koneksi.prepareStatement("select harga "//added
                                        + " from ipsrsgudang "
                                        + " where ipsrsgudang.kode_brng=? and ipsrsgudang.tgl_beli "
                                        + " between ? and ? AND no_faktur !='Opname'");
                            try {
                                ps2.setString(1, rs.getString(1));
                                ps2.setString(2, Valid.SetTgl(Tgl1.getSelectedItem() + ""));
                                ps2.setString(3, Valid.SetTgl(Tgl2.getSelectedItem() + ""));
                                rs2 = ps2.executeQuery();
                                if (rs2.next()) {
                                    harga = rs2.getDouble(1);
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi tes : " + e);
                            } finally {
                                if (rs2 != null) {
                                    rs2.close();
                                }
                                if (ps2 != null) {
                                    ps2.close();
                                }
                            }

                            if ((aset > 0) || (jumlahbeli > 0) || (jumlahpesan > 0) || (jumlahkeluar > 0)) {
                                tabMode.addRow(new Object[]{rs.getString(1),"", rs.getString(2) + " (" + rs.getString(6) + ")",
                                    rs.getString(3),"", "", "", "", "", "", "","",""
//                           Valid.SetAngka(jumlahbeli),Valid.SetAngka(totalbeli),
//                           Valid.SetAngka(jumlahpesan),Valid.SetAngka(totalpesan),
//                           Valid.SetAngka(jumlahkeluar),Valid.SetAngka(totalkeluar)
                                });

                                ps3 = koneksi.prepareStatement("select stok, harga , no_faktur , no_batch , tgl_beli "
                                        + " from ipsrsgudang "
                                        + " where ipsrsgudang.kode_brng=? and ipsrsgudang.tgl_beli "
                                        + " between ? and ? AND no_faktur !='Opname'");
                                try {
                                    ps3.setString(1, rs.getString(1));
                                    ps3.setString(2, Valid.SetTgl(Tgl1.getSelectedItem() + ""));
                                    ps3.setString(3, Valid.SetTgl(Tgl2.getSelectedItem() + ""));
                                    rs3 = ps3.executeQuery();
                                    while (rs3.next()) {
                                        hargabyfaktur = 0;
                                        stokbyfaktur = 0;
                                        stokgetbyfaktur = 0;
                                        hargagetbyfaktur = 0;
                                        stokpostbyfaktur = 0;
                                        hargapostbyfaktur = 0;
                                        ttlmasuk = 0;
                                        stokmasuk = 0;
                                        sisastok = 0;sisasaldo=0;saldoawalbrg=0;                                                                    
//                                        String getharga = rs3.getString("harga");
//                                        System.out.println("getharga "+getharga);
                                        System.out.println("harga "+harga);
                                        
                                        ps2 = koneksi.prepareStatement("select ifnull(ipsrsopname.real,'0') "
                                                + " from ipsrsopname "
                                                + " where ipsrsopname.tanggal=? and ipsrsopname.kode_brng=?");
                                        try {
                                            System.out.println("tgl "+tgl);
                                            ps2.setString(1, tgl);
                                            System.out.println("barang "+rs.getString(1));
                                            ps2.setString(2, rs.getString(1));
//                                            System.out.println("harga "+harga);
//                                            System.out.println("harga rs3 "+rs3.getString("harga"));
                                            rs2 = ps2.executeQuery();
                                            if (rs2.next()) {
                                                stokawal = rs2.getDouble(1);
                                            }
                                        } catch (Exception e) {
                                            System.out.println("Notifikasi Detail Opname : " + e);
                                        } finally { 
                                            if (rs2 != null) {
                                                rs2.close();
                                            }
                                            if (ps2 != null) {
                                                ps2.close();
                                            }
                                        }
                                        ps2 = koneksi.prepareStatement("select ipsrsdetailbeli.jumlah, (ipsrsdetailbeli.jumlah * ipsrsdetailbeli.harga) as ttl "
                                                + " from ipsrsdetailbeli "
                                                + " where ipsrsdetailbeli.no_faktur=? and ipsrsdetailbeli.kode_brng=?");
                                        try {
                                            ps2.setString(1, rs3.getString("no_faktur"));
                                            ps2.setString(2, rs.getString(1));
                                            rs2 = ps2.executeQuery();
                                            if (rs2.next()) {
                                                stokbyfaktur = rs2.getDouble(1);
                                                hargabyfaktur = rs2.getDouble(2);
                                            }
                                        } catch (Exception e) {
                                            System.out.println("Notifikasi Detail Beli : " + e);
                                        } finally {
                                            if (rs2 != null) {
                                                rs2.close();
                                            }
                                            if (ps2 != null) {
                                                ps2.close();
                                            }
                                        }
                                        ps2 = koneksi.prepareStatement("select ipsrsdetailpesan.jumlah, (ipsrsdetailpesan.jumlah*ipsrsdetailpesan.harga) as ttl "
                                                + " from ipsrsdetailpesan "
                                                + " where ipsrsdetailpesan.no_faktur=? and ipsrsdetailpesan.kode_brng=?");
                                        try {
                                            ps2.setString(1, rs3.getString("no_faktur"));
                                            ps2.setString(2, rs.getString(1));
                                            rs2 = ps2.executeQuery();
                                            if (rs2.next()) {
                                                stokgetbyfaktur = rs2.getDouble(1);
                                                hargagetbyfaktur = rs2.getDouble(2);
                                            }
                                        } catch (Exception e) {
                                            System.out.println("Notifikasi Detail Beli : " + e);
                                        } finally {
                                            if (rs2 != null) {
                                                rs2.close();
                                            }
                                            if (ps2 != null) {
                                                ps2.close();
                                            }
                                        }
                                        ps2 = koneksi.prepareStatement("select ipsrsdetailpengeluaran.jumlah, (ipsrsdetailpengeluaran.jumlah*ipsrsdetailpengeluaran.harga) as ttl "
                                                + " from ipsrsdetailpengeluaran "
                                                + " where ipsrsdetailpengeluaran.no_batch=? ");
                                        try {
                                            ps2.setString(1, rs3.getString("no_batch"));
                                            rs2 = ps2.executeQuery();
                                            if (rs2.next()) {
                                                stokpostbyfaktur = rs2.getDouble(1);
                                                hargapostbyfaktur = rs2.getDouble(2);
                                            }
                                        } catch (Exception e) {
                                            System.out.println("Notifikasi Detail Beli : " + e);
                                        } finally {
                                            if (rs2 != null) {
                                                rs2.close();
                                            }
                                            if (ps2 != null) {
                                                ps2.close();
                                            }
                                        }
                                        ttlmasuk = hargabyfaktur + hargagetbyfaktur;
                                        stokmasuk = stokbyfaktur + stokgetbyfaktur;
                                        sisastok = stokawal + stokmasuk - stokpostbyfaktur;
                                        saldoawalbrg = stokawal * rs3.getDouble(2);
                                        sisasaldo = saldoawalbrg + ttlmasuk - hargapostbyfaktur;
                                        tabMode.addRow(new Object[]{"",kd_rek, rs.getString(2),
                                            "", Valid.SetAngka(rs3.getDouble(2)), Valid.SetAngka(stokawal),
                                            Valid.SetAngka(stokmasuk), Valid.SetAngka(stokpostbyfaktur),Valid.SetAngka(sisastok),
                                            Valid.SetAngka(saldoawalbrg), Valid.SetAngka(ttlmasuk),Valid.SetAngka(hargapostbyfaktur),Valid.SetAngka(sisasaldo)});
                                        ttlstokawal = ttlstokawal + stokawal;
                                        ttlstokmsk = ttlstokmsk + stokmasuk;
                                        ttlstokklr = ttlstokklr + stokpostbyfaktur;
                                        ttlstokakhir = ttlstokakhir + sisastok;
                                        ttltotalbeli = ttltotalbeli + ttlmasuk;
                                        ttltotalkeluar = ttltotalkeluar + hargapostbyfaktur;
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notifikasi Detail Beli : " + e);
                                }
//                        ttltotalbeli=ttltotalbeli+totalbeli+hargabyfaktur;
//                        ttltotalpesan=ttltotalpesan+totalpesan+hargagetbyfaktur;
//                        ttlaset=ttlaset+aset;
//                        ttltotalkeluar=ttltotalkeluar+totalkeluar+hargapostbyfaktur;
                                saldo_akhir = saldo_awal + ttltotalbeli;
                                saldo_akhir = saldo_akhir - ttltotalkeluar;
                            }
                        }
                        tabMode.addRow(new Object[]{"", "", "", "", "", "", "", "", "", "", "", "","",""});
                        tabMode.addRow(new Object[]{"<>>>>>", "Total :", "", "", "", Valid.SetAngka(ttlstokawal), Valid.SetAngka(ttlstokmsk),
                            Valid.SetAngka(ttlstokklr), Valid.SetAngka(ttlstokakhir),Valid.SetAngka(saldo_awal), Valid.SetAngka(ttltotalbeli), Valid.SetAngka(ttltotalkeluar),Valid.SetAngka(saldo_akhir)
                        });
                        tabMode.addRow(new Object[]{"", "", "", "", "", "", "", "", "", "", "", "","",""});
                    } catch (Exception e) {
                        System.out.println("Notifikasi Data Barang : " + e);
                    } finally {
                        if (rs != null) {
                            rs.close();
                        }
                        if (ps != null) {
                            ps.close();
                        }
                    }
                }
            } catch (Exception ex) {

            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }

    }

    public void isCek() {
        BtnPrint.setEnabled(true);
    }

}
