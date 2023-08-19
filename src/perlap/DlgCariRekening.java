package perlap;

import fungsi.WarnaTable2;
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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.DlgRekening;

public class DlgCariRekening extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private PreparedStatement ps, ps2, ps3, ps4, ps5;
    private ResultSet rs, rs2, rs3, rs4, rs5;
    private int i = 0, pilihan = 0;
    private WarnaTable2 warna = new WarnaTable2();
    public boolean tampilkanpermintaan = false;
    private DlgRekening rekening = new DlgRekening(null, false);
    private riwayatRBA riwayat = new riwayatRBA();
    private double cariselisih = 0;

    /**
     * Creates new form DlgProgramStudi
     *
     * @param parent
     * @param modal
     */
    public DlgCariRekening(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] judul = {"P", "Kode Rek", "Nama", "Nominal", "Anggaran"};
        tabMode = new DefaultTableModel(null, judul) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0 || colIndex == 3) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 5; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(35);
            } else if (i == 1) {
                column.setPreferredWidth(95);
            } else if (i == 2) {
                column.setPreferredWidth(350);
            } else {
                column.setPreferredWidth(95);
            }
        }
        warna.kolom = 3;
        tbDokter.setDefaultRenderer(Object.class, warna);

        kdrekdari.setDocument(new batasInput((byte) 10).getKata(kdrekdari));
        Keterangan.setDocument(new batasInput((byte) 30).getKata(Keterangan));
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

        rekening.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("DlgLapRBA")) {
                    if (rekening.getTabel().getSelectedRow() != -1) {
                        if (pilihan == 1) {
                            kdrekdari.setText(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(), 0).toString());
                            nmrekdari.setText(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(), 1).toString());
                            TCari.requestFocus();
                        }
                        tampil();
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

        rekening.getTabel().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (var.getform().equals("DlgLapRBA")) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        rekening.dispose();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
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
        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        ppStok = new javax.swing.JMenuItem();
        TSaldoAwal = new widget.TextBox();
        TSaldoBerjalan = new widget.TextBox();
        TSelisih = new widget.TextBox();
        TSaldoAkhir = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari1 = new widget.Button();
        BtnCari = new widget.Button();
        BtnSimpan = new widget.Button();
        label12 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelisi3 = new widget.panelisi();
        label17 = new widget.Label();
        kdrekdari = new widget.TextBox();
        nmrekdari = new widget.TextBox();
        btnDari = new widget.Button();
        label32 = new widget.Label();
        Keterangan = new widget.TextBox();
        label39 = new widget.Label();
        Tanggal = new widget.Tanggal();

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(70, 70, 70));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkan.setText("Bersihkan Jumlah");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setIconTextGap(8);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        ppStok.setBackground(new java.awt.Color(255, 255, 254));
        ppStok.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppStok.setForeground(new java.awt.Color(70, 70, 70));
        ppStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppStok.setText("Tampilkan Semua Stok");
        ppStok.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppStok.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppStok.setIconTextGap(8);
        ppStok.setName("ppStok"); // NOI18N
        ppStok.setPreferredSize(new java.awt.Dimension(200, 25));
        ppStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppStokActionPerformed(evt);
            }
        });
        Popup.add(ppStok);

        TSaldoAwal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TSaldoAwal.setName("TSaldoAwal"); // NOI18N
        TSaldoAwal.setPreferredSize(new java.awt.Dimension(207, 23));

        TSaldoBerjalan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TSaldoBerjalan.setName("TSaldoBerjalan"); // NOI18N
        TSaldoBerjalan.setPreferredSize(new java.awt.Dimension(207, 23));

        TSelisih.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TSelisih.setName("TSelisih"); // NOI18N
        TSelisih.setPreferredSize(new java.awt.Dimension(207, 23));

        TSaldoAkhir.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TSaldoAkhir.setName("TSaldoAkhir"); // NOI18N
        TSaldoAkhir.setPreferredSize(new java.awt.Dimension(207, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Laporan Rencana Bisnis dan Anggaran ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(Popup);
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
        tbDokter.setComponentPopupMenu(Popup);
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        tbDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDokterKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(734, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(77, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(270, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('1');
        BtnCari1.setToolTipText("Alt+1");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari1ActionPerformed(evt);
            }
        });
        BtnCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari1KeyPressed(evt);
            }
        });
        panelisi1.add(BtnCari1);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnCari.setMnemonic('C');
        BtnCari.setToolTipText("Alt+C");
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

        label12.setText("Record :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label12);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(60, 23));
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

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setLayout(null);

        label17.setText("Dari :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi3.add(label17);
        label17.setBounds(0, 10, 45, 23);

        kdrekdari.setName("kdrekdari"); // NOI18N
        kdrekdari.setPreferredSize(new java.awt.Dimension(80, 23));
        kdrekdari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdrekdariKeyPressed(evt);
            }
        });
        panelisi3.add(kdrekdari);
        kdrekdari.setBounds(49, 10, 90, 23);

        nmrekdari.setEditable(false);
        nmrekdari.setName("nmrekdari"); // NOI18N
        nmrekdari.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmrekdari);
        nmrekdari.setBounds(141, 10, 257, 23);

        btnDari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDari.setMnemonic('1');
        btnDari.setToolTipText("Alt+1");
        btnDari.setName("btnDari"); // NOI18N
        btnDari.setPreferredSize(new java.awt.Dimension(28, 23));
        btnDari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDariActionPerformed(evt);
            }
        });
        panelisi3.add(btnDari);
        btnDari.setBounds(400, 10, 28, 23);

        label32.setText("Tanggal :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi3.add(label32);
        label32.setBounds(710, 10, 70, 23);

        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        panelisi3.add(Keterangan);
        Keterangan.setBounds(560, 10, 160, 23);

        label39.setText("Keterangan :");
        label39.setName("label39"); // NOI18N
        label39.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi3.add(label39);
        label39.setBounds(485, 10, 70, 23);

        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        panelisi3.add(Tanggal);
        Tanggal.setBounds(785, 10, 95, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnSimpan, TCari);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed
    /*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        String saldoawal = Sequel.cariIsi("select ifnull(saldo_berjalan,'0') from rekeningtahun where thn='" + Tanggal.getSelectedItem().toString().substring(6, 10) + "' and kd_rek='" + kdrekdari.getText() + "'");
        if (nmrekdari.getText().trim().equals("") || kdrekdari.getText().trim().equals("")) {
            Valid.textKosong(kdrekdari, "Rekening Asal");
        } else if (Keterangan.getText().trim().equals("")) {
            Valid.textKosong(Keterangan, "Keterangan");
        } else {
            i = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, udah bener belum data yang mau disimpan..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (i == JOptionPane.YES_OPTION) {
                for (i = 0; i < tbDokter.getRowCount(); i++) {
                    if (tbDokter.getValueAt(i, 0).toString().equals("true")) {
                        riwayat.catatRiwayat(Valid.SetTgl(Tanggal.getSelectedItem() + ""), Tanggal.getSelectedItem().toString().substring(6, 10),
                                kdrekdari.getText(), 0, Valid.SetAngka(tbDokter.getValueAt(i, 3).toString()), var.getkode(), "Simpan", Keterangan.getText());
                        System.out.println("riwayat dari");
                        Sequel.menyimpan("rekeningtahun", "'" + Tanggal.getSelectedItem().toString().substring(6, 10) + "','" + kdrekdari.getText() + "','" + tbDokter.getValueAt(i, 3).toString() + "','-" + Valid.SetAngka(tbDokter.getValueAt(i, 3).toString()) + "'",
                                "saldo_berjalan=saldo_berjalan-" + Valid.SetAngka(tbDokter.getValueAt(i, 3).toString()) + "", "kd_rek='" + kdrekdari.getText() + "' and thn='" + Tanggal.getSelectedItem().toString().substring(6, 10) + "'");
                        System.out.println("simpan dari");
                        riwayat.catatRiwayat(Valid.SetTgl(Tanggal.getSelectedItem() + ""), Tanggal.getSelectedItem().toString().substring(6, 10), tbDokter.getValueAt(i, 1).toString(), Valid.SetAngka(tbDokter.getValueAt(i, 3).toString()), 0, var.getkode(), "Simpan", Keterangan.getText());
                        System.out.println("riwayat ke");
                        Sequel.menyimpan("rekeningtahun", "'" + Tanggal.getSelectedItem().toString().substring(6, 10) + "','" + kdrekdari.getText() + "','" + tbDokter.getValueAt(i, 3).toString() + "','-" + Valid.SetAngka(tbDokter.getValueAt(i, 3).toString()) + "'",
                                "saldo_berjalan=saldo_berjalan+" + Valid.SetAngka(tbDokter.getValueAt(i, 3).toString()) + "", "kd_rek='" + tbDokter.getValueAt(i, 1) + "' and thn='" + Tanggal.getSelectedItem().toString().substring(6, 10) + "'");
                        System.out.println("simpan ke");
                    }
                }
                tampil();
                emptTeks();
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnKeluar, TCari);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnSimpan, BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        BtnCari1ActionPerformed(null);
    } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
        BtnCari1.requestFocus();
    } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
        kdrekdari.requestFocus();
    } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
        tbDokter.requestFocus();
    }
}//GEN-LAST:event_TCariKeyPressed

private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
    if (TCari.getText().trim().equals("")) {
        tampil();
    } else {
        tampil2();
    }
}//GEN-LAST:event_BtnCari1ActionPerformed

private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
        BtnCari1ActionPerformed(null);
    } else {
        Valid.pindah(evt, BtnSimpan, BtnKeluar);
    }
}//GEN-LAST:event_BtnCari1KeyPressed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
    for (i = 0; i < tbDokter.getRowCount(); i++) {
        tbDokter.setValueAt("", i, 0);
        tbDokter.setValueAt(0, i, 2);
    }
}//GEN-LAST:event_ppBersihkanActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void kdrekdariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdrekdariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select nm_rek from rekening where kd_rek='" + kdrekdari.getText() + "'", nmrekdari);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            Sequel.cariIsi("select nm_rek from rekening where kd_rek='" + kdrekdari.getText() + "'", nmrekdari);
            TCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Sequel.cariIsi("select nm_rek from rekening where kd_rek='" + kdrekdari.getText() + "'", nmrekdari);
            TCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnDariActionPerformed(null);
        }
    }//GEN-LAST:event_kdrekdariKeyPressed

    private void btnDariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDariActionPerformed
        pilihan = 1;
        var.setform("DlgLapRBA");
        rekening.emptTeks();
        rekening.isCek();
        rekening.tampil3();
        rekening.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        rekening.setLocationRelativeTo(internalFrame1);
        rekening.setVisible(true);
    }//GEN-LAST:event_btnDariActionPerformed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt, Tanggal, TCari);
    }//GEN-LAST:event_KeteranganKeyPressed

    private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
        if (tabMode.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbDokterKeyPressed

    private void ppStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppStokActionPerformed

    }//GEN-LAST:event_ppStokActionPerformed

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbDokterMouseClicked

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt, TCari, BtnKeluar);
    }//GEN-LAST:event_TanggalKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariRekening dialog = new DlgCariRekening(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.TextBox Kd2;
    private widget.TextBox Keterangan;
    private widget.Label LCount;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox TCari;
    private widget.TextBox TSaldoAkhir;
    private widget.TextBox TSaldoAwal;
    private widget.TextBox TSaldoBerjalan;
    private widget.TextBox TSelisih;
    private widget.Tanggal Tanggal;
    private widget.Button btnDari;
    private widget.InternalFrame internalFrame1;
    private widget.TextBox kdrekdari;
    private widget.Label label10;
    private widget.Label label12;
    private widget.Label label17;
    private widget.Label label32;
    private widget.Label label39;
    private widget.TextBox nmrekdari;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppStok;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement("select rekening.kd_rek,rekening.nm_rek, rekeningtahun.saldo_awal, rekeningtahun.saldo_berjalan from rekeningtahun inner join rekening "
                    + "on rekeningtahun.kd_rek=rekening.kd_rek "
                    + "where rekening.kd_rek like '5%' and level='0' and rekening.balance='K' and rekening.nm_rek like ?");
            try {
                ps.setString(1, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new Object[]{
                        false, rs.getString(1), rs.getString(2), 0, rs.getDouble(3)
                    });
                    ps2 = koneksi.prepareStatement("select rekening.kd_rek, rekening.nm_rek, rekeningtahun.saldo_awal, rekeningtahun.saldo_berjalan "
                            + " from rekening inner join rekeningtahun inner join subrekening on rekening.kd_rek=rekeningtahun.kd_rek and rekening.kd_rek=subrekening.kd_rek2 "
                            + " where subrekening.kd_rek=? and rekening.level='1' and rekening.balance='K' and rekening.nm_rek like ?");
                    try {
                        ps2.setString(1, rs.getString("kd_rek"));
                        ps2.setString(2, "%" + TCari.getText().trim() + "%");
                        rs2 = ps2.executeQuery();
                        while (rs2.next()) {
                            tabMode.addRow(new Object[]{
                                false, "  " + rs2.getString(1), rs2.getString(2), 0, rs2.getDouble(3)
                            });
                            ps3 = koneksi.prepareStatement("select rekening.kd_rek, rekening.nm_rek, rekeningtahun.saldo_awal, rekeningtahun.saldo_berjalan "
                                    + " from rekening inner join rekeningtahun inner join subrekening on rekening.kd_rek=rekeningtahun.kd_rek and rekening.kd_rek=subrekening.kd_rek2 "
                                    + " where subrekening.kd_rek=? and rekening.level='1' and rekening.balance='K' and rekening.nm_rek like ?");
                            try {
                                ps3.setString(1, rs2.getString("kd_rek"));
                                ps3.setString(2, "%" + TCari.getText().trim() + "%");
                                rs3 = ps3.executeQuery();
                                while (rs3.next()) {
                                    tabMode.addRow(new Object[]{
                                        false, "   " + rs3.getString(1), rs3.getString(2), 0, rs3.getDouble(3)
                                    });
                                    ps4 = koneksi.prepareStatement("select rekening.kd_rek, rekening.nm_rek, rekeningtahun.saldo_awal, rekeningtahun.saldo_berjalan "
                                            + " from rekening inner join rekeningtahun inner join subrekening on rekening.kd_rek=rekeningtahun.kd_rek and rekening.kd_rek=subrekening.kd_rek2 "
                                            + " where subrekening.kd_rek=? and rekening.level='1' and rekening.balance='K' and rekening.nm_rek like ?");
                                    try {
                                        ps4.setString(1, rs3.getString("kd_rek"));
                                        ps4.setString(2, "%" + TCari.getText().trim() + "%");
                                        rs4 = ps4.executeQuery();
                                        while (rs4.next()) {
                                            tabMode.addRow(new Object[]{
                                                false, "    " + rs4.getString(1), rs4.getString(2), 0, rs4.getDouble(3)
                                            });
                                            ps5 = koneksi.prepareStatement("select rekening.kd_rek, rekening.nm_rek, rekeningtahun.saldo_awal, rekeningtahun.saldo_berjalan "
                                                    + " from rekening inner join rekeningtahun inner join subrekening on rekening.kd_rek=rekeningtahun.kd_rek and rekening.kd_rek=subrekening.kd_rek2 "
                                                    + " where subrekening.kd_rek=? and rekening.level='1' and rekening.balance='K' and rekening.nm_rek like ?");
                                            try {
                                                ps5.setString(1, rs4.getString("kd_rek"));
                                                ps5.setString(2, "%" + TCari.getText().trim() + "%");
                                                rs5 = ps5.executeQuery();
                                                while (rs5.next()) {
                                                    tabMode.addRow(new Object[]{
                                                        false, "     " + rs5.getString(1), rs5.getString(2), 0, rs5.getDouble(3)
                                                    });
                                                }
                                            } catch (Exception e) {
                                                System.out.println("Notif 5 : " + e);
                                            } finally {
                                                if (rs5 != null) {
                                                    rs5.close();
                                                }
                                                if (ps5 != null) {
                                                    ps5.close();
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif 4 : " + e);
                                    } finally {
                                        if (rs4 != null) {
                                            rs4.close();
                                        }
                                        if (ps4 != null) {
                                            ps4.close();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Notif 3 : " + e);
                            } finally {
                                if (rs3 != null) {
                                    rs3.close();
                                }
                                if (ps3 != null) {
                                    ps3.close();
                                }
                            }
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
                System.out.println("Note : " + e);
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

    public void tampil2() {
        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement("select rekening.kd_rek, rekening.nm_rek, rekeningtahun.saldo_awal, rekeningtahun.saldo_berjalan "
                    + "from rekening inner join rekeningtahun on rekening.kd_rek=rekeningtahun.kd_rek "
                    + "where rekeningtahun.kd_rek like '5%' and rekening.balance='K' and rekening.level='1' and rekening.nm_rek like ? order by kd_rek");
            try {
                ps.setString(1, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new Object[]{
                        false, rs.getString(1), rs.getString(2), 0, rs.getDouble(3)
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif 1 : " + e);
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

    }

    public void emptTeks() {
        Keterangan.setText("");
        TCari.setText("");
        TCari.requestFocus();
    }

    private void getData() {
        TSaldoAwal.setText(Sequel.cariIsi("select saldo_awal from rekeningtahun where kd_rek=?", kdrekdari.getText()));
        TSaldoBerjalan.setText(Sequel.cariIsi("select saldo_berjalan from rekeningtahun where kd_rek=?", kdrekdari.getText()));
    }

    public void setPetugas(String kdrek, String nmrek) {
        kdrekdari.setText(kdrek);
        nmrekdari.setText(nmrek);
    }

    public void isCekStok() {
//        try {
//            String saldoawal = Sequel.cariIsi("select ifnull(saldo_berjalan,'0') from rekeningtahun where thn='" + Tanggal.getSelectedItem().toString().substring(6, 10) + "' and kd_rek='" + kdrekdari.getText() + "'");
//            String saldotujuan = Sequel.cariIsi("select ifnull(saldo_berjalan,'0') from rekeningtahun where thn='" + Tanggal.getSelectedItem().toString().substring(6, 10) + "' and kd_rek='" + kdrekke.getText() + "'");
//            if (Double.parseDouble(saldotujuan) > Double.parseDouble(saldoawal)) {
//                JOptionPane.showMessageDialog(null, "Eiiitsss, saldo tidak mencukupi..!!");
//            }
//        } catch (Exception e) {
//        }
    }

    public double cariSelisih(String kdrek) {
        double selisih = 0;
        selisih = Sequel.cariIsiAngka("SELECT saldo_berjalan-saldo_awal FROM rekeningtahun WHERE kd_rek = '" + kdrek + "'");
        return selisih;
    }
}
