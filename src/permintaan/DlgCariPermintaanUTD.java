package permintaan;

import fungsi.BackgroundMusic;
import fungsi.WarnaTable;
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
import kepegawaian.DlgCariDokter;
import simrskhanza.DlgCariBangsal;
import simrskhanza.DlgCariPoli;

public class DlgCariPermintaanUTD extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private DlgCariDokter dokter = new DlgCariDokter(null, false);
    private DlgCariPoli poli = new DlgCariPoli(null, false);
    private DlgCariBangsal ruang = new DlgCariBangsal(null, false);
    private int i, nilai_detik, permintaanbaru = 0;
    private PreparedStatement ps, ps2;
    private final Properties prop = new Properties();
    private BackgroundMusic music;
    private ResultSet rs, rs2;
    private Date now;
    private boolean aktif = false;
    private String alarm = "", formalarm = "", nol_detik, detik, norm = "", kamar = "", namakamar = "", diagnosa = "";

    /**
     * Creates new form DlgProgramStudi
     *
     * @param parent
     * @param modal
     */
    public DlgCariPermintaanUTD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode = new DefaultTableModel(null, new Object[]{
            "No.Permintaan", "No.Rawat", "Pasien", "Jns. Bayar", "Tanggal", "Jam", "Jumlah",
            "Gol. Darah", "Resus", "Kode Dokter", "Dokter Perujuk", "Kamar Terakhir"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbTransfusi.setModel(tabMode);

        tbTransfusi.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbTransfusi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 12; i++) {
            TableColumn column = tbTransfusi.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(90);
            } else if (i == 1) {
                column.setPreferredWidth(105);
            } else if (i == 2) {
                column.setPreferredWidth(400);
            } else if (i == 3) {
                column.setPreferredWidth(60);
            } else if (i == 4) {
                column.setPreferredWidth(65);
            } else if (i == 5) {
                column.setPreferredWidth(65);
            } else if (i == 6) {
                column.setPreferredWidth(60);
            } else if (i == 7) {
                column.setPreferredWidth(65);
            } else if (i == 8) {
                column.setPreferredWidth(65);
            } else if (i == 9) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
                //column.setPreferredWidth(150);
            } else if (i == 10) {
                column.setPreferredWidth(150);
            } else if (i == 11) {
                column.setPreferredWidth(150);
            }
        }
        tbTransfusi.setDefaultRenderer(Object.class, new WarnaTable());

//        tabMode2 = new DefaultTableModel(null, new Object[]{
//            "No.Permintaan", "No.Rawat", "Pasien", "Pemeriksaan", "Permintaan",
//            "Jam", "Kode Dokter", "Dokter Perujuk", "Hasil Akhir"
//        }) {
//            @Override
//            public boolean isCellEditable(int rowIndex, int colIndex) {
//                return false;
//            }
//        };
//        tbTransfusi2.setModel(tabMode2);
//
//        tbTransfusi2.setPreferredScrollableViewportSize(new Dimension(800, 800));
//        tbTransfusi2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//
//        for (i = 0; i < 9; i++) {
//            TableColumn column = tbTransfusi2.getColumnModel().getColumn(i);
//            if (i == 0) {
//                column.setPreferredWidth(90);
//            } else if (i == 1) {
//                column.setPreferredWidth(105);
//            } else if (i == 2) {
//                column.setPreferredWidth(250);
//            } else if (i == 3) {
//                column.setPreferredWidth(150);
//            } else if (i == 4) {
//                column.setPreferredWidth(65);
//            } else if (i == 5) {
//                column.setPreferredWidth(50);
//            } else if (i == 6) {
//                column.setMinWidth(0);
//                column.setMaxWidth(0);
//            } else if (i == 7) {
//                column.setPreferredWidth(150);
//            } else if (i == 8) {
//                column.setPreferredWidth(150);
//            }
//        }
//        tbTransfusi2.setDefaultRenderer(Object.class, new WarnaTable());

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
            public void windowOpened(WindowEvent e) {;
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (dokter.getTable().getSelectedRow() != -1) {
                    CrDokter2.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                    CrDokter2.requestFocus();
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
                    Kamar.setText(ruang.getTable().getValueAt(ruang.getTable().getSelectedRow(), 1).toString());
                    Kamar.requestFocus();
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
            alarm = prop.getProperty("ALARMRADIOLOGI");
            formalarm = prop.getProperty("FORMALARMRADIOLOGI");
        } catch (Exception ex) {
            alarm = "no";
            formalarm = "ralan + ranap";
        }

        if (alarm.equals("yes")) {
            jam();
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

        Kd2 = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCetakHasilUTD = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        jPanel2 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        panelisi1 = new widget.panelisi();
        BtnHapus = new widget.Button();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass10 = new widget.panelisi();
        jLabel15 = new widget.Label();
        CrDokter2 = new widget.TextBox();
        BtnSeek5 = new widget.Button();
        jLabel17 = new widget.Label();
        Kamar = new widget.TextBox();
        BtnSeek6 = new widget.Button();
        internalFrame3 = new widget.InternalFrame();
        scrollPane3 = new widget.ScrollPane();
        tbTransfusi = new widget.Table();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCetakHasilUTD.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakHasilUTD.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilUTD.setForeground(new java.awt.Color(70, 70, 70));
        MnCetakHasilUTD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilUTD.setText("Cetak Permintaan UTD");
        MnCetakHasilUTD.setName("MnCetakHasilUTD"); // NOI18N
        MnCetakHasilUTD.setPreferredSize(new java.awt.Dimension(200, 28));
        MnCetakHasilUTD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilUTDActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakHasilUTD);

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Permintaan UTD ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
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
        label11.setPreferredSize(new java.awt.Dimension(60, 23));
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

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(112, 23));
        panelGlass8.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(318, 23));
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
        LCount.setPreferredSize(new java.awt.Dimension(53, 23));
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

        jPanel2.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        panelGlass10.setBorder(null);
        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 41));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel15.setText("Dokter :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass10.add(jLabel15);

        CrDokter2.setEditable(false);
        CrDokter2.setName("CrDokter2"); // NOI18N
        CrDokter2.setPreferredSize(new java.awt.Dimension(250, 23));
        panelGlass10.add(CrDokter2);

        BtnSeek5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek5.setMnemonic('6');
        BtnSeek5.setToolTipText("ALt+6");
        BtnSeek5.setName("BtnSeek5"); // NOI18N
        BtnSeek5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek5ActionPerformed(evt);
            }
        });
        panelGlass10.add(BtnSeek5);

        jLabel17.setText("Ruang/Kamar :");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(104, 23));
        panelGlass10.add(jLabel17);

        Kamar.setEditable(false);
        Kamar.setName("Kamar"); // NOI18N
        Kamar.setPreferredSize(new java.awt.Dimension(250, 23));
        panelGlass10.add(Kamar);

        BtnSeek6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek6.setMnemonic('5');
        BtnSeek6.setToolTipText("ALt+5");
        BtnSeek6.setName("BtnSeek6"); // NOI18N
        BtnSeek6.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek6ActionPerformed(evt);
            }
        });
        panelGlass10.add(BtnSeek6);

        jPanel2.add(panelGlass10, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout());

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane3.setName("scrollPane3"); // NOI18N
        scrollPane3.setOpaque(true);

        tbTransfusi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbTransfusi.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbTransfusi.setComponentPopupMenu(jPopupMenu1);
        tbTransfusi.setName("tbTransfusi"); // NOI18N
        scrollPane3.setViewportView(tbTransfusi);

        internalFrame3.add(scrollPane3, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(internalFrame3, java.awt.BorderLayout.CENTER);

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
//        pilihRanap();
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
        CrDokter2.setText("");
        Kamar.setText("");
//        pilihRanap();
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
//        if (TabRawatInap.getSelectedIndex() == 0) {
            if (tabMode.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                TCari.requestFocus();
            } else if (tabMode.getRowCount() != 0) {

                Sequel.queryu("delete from temporary_permintaan_utd");
                int row = tabMode.getRowCount();
                for (i = 0; i < row; i++) {
                    Sequel.menyimpan("temporary_permintaan_utd", "'0','"
                            + tabMode.getValueAt(i, 0).toString() + "','"
                            + tabMode.getValueAt(i, 1).toString() + "','"
                            + tabMode.getValueAt(i, 2).toString() + "','"
                            + tabMode.getValueAt(i, 3).toString() + "','"
                            + tabMode.getValueAt(i, 4).toString() + "','"
                            + tabMode.getValueAt(i, 5).toString() + "','"
                            + tabMode.getValueAt(i, 6).toString() + "','"
                            + tabMode.getValueAt(i, 7).toString() + "','"
                            + tabMode.getValueAt(i, 8).toString() + "','"
                            + tabMode.getValueAt(i, 9).toString() + "','"
                            + tabMode.getValueAt(i, 10).toString() + "','"
                            + tabMode.getValueAt(i, 11).toString() + "','"
                            + "','','','','','','','','','','','','','','','','','','','','','','','',''", "Periksa UTD");
                }

                Map<String, Object> param = new HashMap<>();
                param.put("namars", var.getnamars());
                param.put("alamatrs", var.getalamatrs());
                param.put("kotars", var.getkabupatenrs());
                param.put("propinsirs", var.getpropinsirs());
                param.put("kontakrs", var.getkontakrs());
                param.put("emailrs", var.getemailrs());
                param.put("logo", Sequel.cariGambar("select logo from setting"));
                Valid.MyReport("rptLapPermintaanUtd.jrxml", "report", "::[ Data Permintaan UTD ]::",
                        "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14, temp14, temp15, temp16 from temporary_permintaan_utd order by no asc", param);
            }
//        } else if (TabRawatInap.getSelectedIndex() == 1) {
//            if (tabMode2.getRowCount() == 0) {
//                JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
//                TCari.requestFocus();
//            } else if (tabMode2.getRowCount() != 0) {
//
//                Sequel.queryu("delete from temporary_permintaan_utd");
//                int row = tabMode2.getRowCount();
//                for (i = 0; i < row; i++) {
//                    Sequel.menyimpan("temporary_permintaan_utd", "'0','"
//                            + tabMode.getValueAt(i, 0).toString() + "','"
//                            + tabMode.getValueAt(i, 1).toString() + "','"
//                            + tabMode.getValueAt(i, 2).toString() + "','"
//                            + tabMode.getValueAt(i, 3).toString() + "','"
//                            + tabMode.getValueAt(i, 4).toString() + "','"
//                            + tabMode.getValueAt(i, 5).toString() + "','"
//                            + tabMode.getValueAt(i, 6).toString() + "','"
//                            + tabMode.getValueAt(i, 7).toString() + "','"
//                            + tabMode.getValueAt(i, 8).toString() + "','"
//                            + tabMode.getValueAt(i, 9).toString() + "','"
//                            + tabMode.getValueAt(i, 10).toString() + "','"
//                            + tabMode.getValueAt(i, 11).toString() + "','"
//                            + "','','','','','','','','','','','','','','','','','','','','','','','',''", "Periksa UTD");
//                }
//
//                Map<String, Object> param = new HashMap<>();
//                param.put("namars", var.getnamars());
//                param.put("alamatrs", var.getalamatrs());
//                param.put("kotars", var.getkabupatenrs());
//                param.put("propinsirs", var.getpropinsirs());
//                param.put("kontakrs", var.getkontakrs());
//                param.put("emailrs", var.getemailrs());
//                param.put("logo", Sequel.cariGambar("select logo from setting"));
//                Valid.MyReport("rptLapPermintaanUtd2.jrxml", "report", "::[ Data Detail Permintaan UTD ]::",
//                        "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14, temp14, temp15, temp16 from temporary_permintaan_utd order by no asc", param);
//            }
//        }
//        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnAll, BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnPrint, BtnHapus);
        }
    }//GEN-LAST:event_BtnKeluarKeyPressed

private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
//    if (TabRawatInap.getSelectedIndex() == 0) {
        if (tbTransfusi.getSelectedRow() != -1) {
            if (tbTransfusi.getValueAt(tbTransfusi.getSelectedRow(), 0).toString().trim().equals("")) {
                Valid.textKosong(TCari, "No.Permintaan");
            } else {
                if (Sequel.cariInteger("select count(noorder) from permintaan_pemeriksaan_utd where noorder=?", tbTransfusi.getValueAt(tbTransfusi.getSelectedRow(), 0).toString()) > 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, Tidak boleh dihapus karena sudah ada tindakan yang sudah dibayar.\nSilahkan hubungi kasir...!!!!");
                } else {
                    Sequel.meghapus("permintaan_utd", "noorder", tbTransfusi.getValueAt(tbTransfusi.getSelectedRow(), 0).toString());
                    tampil();
                }
            }
        }
//        else {
//            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data permintaan...!!!!");
//            TCari.requestFocus();
//        }
//    } else if (TabRawatInap.getSelectedIndex() == 1) {
//        JOptionPane.showMessageDialog(null, "Hanya bisa dilakukan hapus di Data Permintaan..!!!");
//        TabRawatInap.setSelectedIndex(0);
//        TCari.requestFocus();
//    }
}//GEN-LAST:event_BtnHapusActionPerformed

private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
        BtnHapusActionPerformed(null);
    } else {
        Valid.pindah(evt, TCari, BtnAll);
    }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void MnCetakHasilUTDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakHasilUTDActionPerformed
        if (tbTransfusi.getSelectedRow() != -1) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (tbTransfusi.getValueAt(tbTransfusi.getSelectedRow(), 0).toString().trim().equals("")) {
                Valid.textKosong(TCari, "No.Permintaan");
            } else {

                Sequel.queryu("delete from temporary_permintaan_utd");
                try {
                    ps2 = koneksi.prepareStatement(
                            "select permintaan_pemeriksaan_utd.kd_jenis_prw, jns_perawatan_inap.nm_perawatan "
                            + "from permintaan_pemeriksaan_utd inner join jns_perawatan_inap on "
                            + "permintaan_pemeriksaan_utd.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw where "
                            + "permintaan_pemeriksaan_utd.noorder=?");
                    try {
                        ps2.setString(1, tbTransfusi.getValueAt(tbTransfusi.getSelectedRow(), 0).toString());
                        rs2 = ps2.executeQuery();
                        while (rs2.next()) {
                            Sequel.menyimpan("temporary_permintaan_utd", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", 38, new String[]{
                                "0", rs2.getString("kd_jenis_prw"), rs2.getString("nm_perawatan"), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
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
                param.put("noperiksa", tbTransfusi.getValueAt(tbTransfusi.getSelectedRow(), 0).toString());
                norm = Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ", tbTransfusi.getValueAt(tbTransfusi.getSelectedRow(), 1).toString());
                param.put("norm", norm);
                param.put("pekerjaan", Sequel.cariIsi("select pekerjaan from pasien where no_rkm_medis=?", norm));
                param.put("noktp", Sequel.cariIsi("select no_ktp from pasien where no_rkm_medis=?", norm));
                param.put("namapasien", Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ", norm));
                // param.put("jkel",Sequel.cariIsi("select jk from pasien where no_rkm_medis=? ",norm));
                param.put("jkel", Sequel.cariIsi("select if(jk='L','Laki-laki','Perempuan') as jk from pasien where no_rkm_medis=? ", norm));
                param.put("umur", Sequel.cariIsi("select umur from pasien where no_rkm_medis=?", norm));
                param.put("lahir", Sequel.cariIsi("select DATE_FORMAT(tgl_lahir,'%d-%m-%Y') from pasien where no_rkm_medis=? ", norm));
                // param.put("pengirim",tbRadiologiRanap.getValueAt(tbRadiologiRanap.getSelectedRow(),10).toString());
                param.put("pengirim", tbTransfusi.getValueAt(tbTransfusi.getSelectedRow(), 10).toString());//Sequel.cariIsi("select dokter.nm_dokter from reg_periksa, dokter where reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rawat=? ", tbRadiologiRanap.getValueAt(tbRadiologiRanap.getSelectedRow(),12).toString()));
                param.put("tanggal", Valid.SetTgl3(tbTransfusi.getValueAt(tbTransfusi.getSelectedRow(), 4).toString()));
                param.put("alamat", Sequel.cariIsi("select concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat from pasien inner join kelurahan inner join kecamatan inner join kabupaten on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where no_rkm_medis=? ", norm));
                param.put("diagnosa1", Sequel.cariIsi("select diagnosa_awal from kamar_inap where no_rawat=? ", tbTransfusi.getValueAt(tbTransfusi.getSelectedRow(), 1).toString()));
                param.put("jns_bayar", tbTransfusi.getValueAt(tbTransfusi.getSelectedRow(), 3).toString());
                param.put("klinis", Sequel.cariIsi("select klinis from diagnosa_pasien_klinis where noorder=? ", tbTransfusi.getValueAt(tbTransfusi.getSelectedRow(), 0).toString()));

                kamar = Sequel.cariIsi("select ifnull(kd_kamar,'') from kamar_inap where no_rawat=? order by tgl_masuk desc limit 1", tbTransfusi.getValueAt(tbTransfusi.getSelectedRow(), 1).toString());
                namakamar = kamar + ", " + Sequel.cariIsi("select nm_bangsal from bangsal inner join kamar on bangsal.kd_bangsal=kamar.kd_bangsal "
                        + " where kamar.kd_kamar=? ", kamar);
                kamar = "Kamar";
                diagnosa = Sequel.cariIsi("select diagnosa_awal from kamar_inap where no_rawat=?", tbTransfusi.getValueAt(tbTransfusi.getSelectedRow(), 1).toString());
                param.put("kamar", kamar);
                param.put("namakamar", namakamar);
                param.put("diagnosa", diagnosa);
                param.put("jam", tbTransfusi.getValueAt(tbTransfusi.getSelectedRow(), 5).toString());
                param.put("namars", var.getnamars());
                param.put("alamatrs", var.getalamatrs());
                param.put("kotars", var.getkabupatenrs());
                param.put("propinsirs", var.getpropinsirs());
                param.put("kontakrs", var.getkontakrs());
                param.put("emailrs", var.getemailrs());
                param.put("logo", Sequel.cariGambar("select logo from setting"));

                Valid.MyReport("rptPermintaanUtd.jrxml", "report", "::[ Permintaan UTD ]::",
                        "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14, temp14, temp15, temp16 from temporary_permintaan_utd order by no asc", param);
            }
            this.setCursor(Cursor.getDefaultCursor());
        } else {
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data permintaan...!!!!");
            TCari.requestFocus();
        }
    }//GEN-LAST:event_MnCetakHasilUTDActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
//        pilihRanap();
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        aktif = true;
    }//GEN-LAST:event_formWindowActivated

    private void formWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeactivated
        aktif = false;
    }//GEN-LAST:event_formWindowDeactivated

    private void TCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TCariActionPerformed

    private void BtnSeek6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek6ActionPerformed
        ruang.isCek();
        ruang.emptTeks();
        ruang.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        ruang.setLocationRelativeTo(internalFrame1);
        ruang.setVisible(true);
    }//GEN-LAST:event_BtnSeek6ActionPerformed

    private void BtnSeek5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek5ActionPerformed
        dokter.isCek();
        dokter.TCari.requestFocus();
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeek5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariPermintaanUTD dialog = new DlgCariPermintaanUTD(new javax.swing.JFrame(), true);
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
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek5;
    private widget.Button BtnSeek6;
    private widget.TextBox CrDokter2;
    private widget.TextBox Kamar;
    private widget.TextBox Kd2;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnCetakHasilUTD;
    private widget.TextBox TCari;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel15;
    private widget.Label jLabel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label18;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelisi1;
    private widget.ScrollPane scrollPane3;
    private widget.Table tbTransfusi;
    // End of variables declaration//GEN-END:variables

    private void getData() {
        Kd2.setText("");
        if (tbTransfusi.getSelectedRow() != -1) {
            Kd2.setText(tbTransfusi.getValueAt(tbTransfusi.getSelectedRow(), 0).toString());
        }
    }

    public void isCek() {
        MnCetakHasilUTD.setEnabled(var.getpermintaan_radiologi());
        BtnHapus.setEnabled(var.getpermintaan_radiologi());
        BtnPrint.setEnabled(var.getpermintaan_radiologi());
    }

    public void setPasien(String pasien) {
        TCari.setText(pasien);
    }

//    public void pilihRanap() {
//        if (TabRawatInap.getSelectedIndex() == 0) {
//            tampil();
//        } else if (TabRawatInap.getSelectedIndex() == 1) {
//            tampil2();
//        }
//    }

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement(
                    "select permintaan_utd.noorder, permintaan_utd.no_rawat, reg_periksa.no_rkm_medis,pasien.nm_pasien,penjab.png_jawab,permintaan_utd.tgl_permintaan, permintaan_utd.jam_permintaan, permintaan_utd.jumlah, permintaan_utd.golongan_darah, permintaan_utd.resus, "
                    + "permintaan_utd.dokter_perujuk, dokter.nm_dokter,bangsal.nm_bangsal from permintaan_utd inner join reg_periksa inner join pasien inner join dokter inner join bangsal inner join "
                    + "kamar inner join kamar_inap inner join penjab on permintaan_utd.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and permintaan_utd.dokter_perujuk=dokter.kd_dokter and "
                    + "kamar.kd_bangsal=bangsal.kd_bangsal and reg_periksa.no_rawat=kamar_inap.no_rawat and kamar_inap.kd_kamar=kamar.kd_kamar and penjab.kd_pj=reg_periksa.kd_pj where "
                    + "permintaan_utd.status='ranap' and permintaan_utd.tgl_permintaan between ? and ? and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and (permintaan_utd.noorder like ? or "
                    + "permintaan_utd.no_rawat like ? or "
                    + "reg_periksa.no_rkm_medis like ? or "
                    + "pasien.nm_pasien like ? or "
                    + "dokter.nm_dokter like ?) "
                    + "group by permintaan_utd.noorder order by permintaan_utd.tgl_permintaan,permintaan_utd.jam_permintaan desc");
            try {
                ps.setString(1, Valid.SetTgl(Tgl1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(Tgl2.getSelectedItem() + ""));
                ps.setString(3, "%" + CrDokter2.getText().trim() + "%");
                ps.setString(4, "%" + Kamar.getText().trim() + "%");
                ps.setString(5, "%" + TCari.getText() + "%");
                ps.setString(6, "%" + TCari.getText() + "%");
                ps.setString(7, "%" + TCari.getText() + "%");
                ps.setString(8, "%" + TCari.getText() + "%");
                ps.setString(9, "%" + TCari.getText() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new String[]{
                        rs.getString("noorder"), rs.getString("no_rawat"), rs.getString("no_rkm_medis") + " " + rs.getString("nm_pasien"), rs.getString("png_jawab"),
                        rs.getString("tgl_permintaan"), rs.getString("jam_permintaan"), rs.getString("jumlah"), rs.getString("golongan_darah"), rs.getString("resus"),
                        rs.getString("dokter_perujuk"), rs.getString("nm_dokter"), rs.getString("nm_bangsal")
                    });
                    ps2 = koneksi.prepareStatement(
                            "select permintaan_pemeriksaan_utd.kd_jenis_prw, jns_perawatan_inap.nm_perawatan "
                            + "from permintaan_pemeriksaan_utd inner join jns_perawatan_inap on "
                            + "permintaan_pemeriksaan_utd.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw where "
                            + "permintaan_pemeriksaan_utd.noorder=?");
                    try {
                        ps2.setString(1, rs.getString("noorder"));
                        rs2 = ps2.executeQuery();
                        while (rs2.next()) {
                            tabMode.addRow(new Object[]{
                                "", "", rs2.getString("nm_perawatan"), "", "", "", "", "", "", "", "", ""
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

//    private void tampil2() {
//        Valid.tabelKosong(tabMode2);
//        try {
//            ps = koneksi.prepareStatement(
//                    "select permintaan_utd.noorder, permintaan_utd.no_rawat, reg_periksa.no_rkm_medis,pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,permintaan_utd.tgl_permintaan, permintaan_utd.jam_permintaan, "
//                    + "permintaan_utd.dokter_perujuk, dokter.nm_dokter,bangsal.nm_bangsal from permintaan_utd inner join reg_periksa inner join pasien inner join permintaan_pemeriksaan_utd inner join jns_perawatan_inap "
//                    + "inner join dokter inner join bangsal inner join kamar inner join kamar_inap inner join penjab on permintaan_utd.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
//                    + "and permintaan_utd.dokter_perujuk=dokter.kd_dokter and kamar.kd_bangsal=bangsal.kd_bangsal and reg_periksa.no_rawat=kamar_inap.no_rawat and kamar_inap.kd_kamar=kamar.kd_kamar and penjab.kd_pj=reg_periksa.kd_pj "
//                    + "and permintaan_pemeriksaan_utd.noorder=permintaan_utd.noorder and permintaan_pemeriksaan_utd.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw where "
//                    + "permintaan_utd.status='ranap' and permintaan_utd.tgl_permintaan between ? and ? and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and (permintaan_utd.noorder like ? or "
//                    + "permintaan_utd.no_rawat like ? or "
//                    + "reg_periksa.no_rkm_medis like ? or "
//                    + "pasien.nm_pasien like ? or "
//                    + "jns_perawatan_inap.nm_perawatan like ? or "
//                    + "dokter.nm_dokter like ?) "
//                    + "group by permintaan_utd.noorder order by permintaan_utd.tgl_permintaan,permintaan_utd.jam_permintaan desc");
//            try {
//                ps.setString(1, Valid.SetTgl(Tgl1.getSelectedItem() + ""));
//                ps.setString(2, Valid.SetTgl(Tgl2.getSelectedItem() + ""));
//                ps.setString(3, "%" + CrDokter2.getText().trim() + "%");
//                ps.setString(4, "%" + Kamar.getText().trim() + "%");
//                ps.setString(5, "%" + TCari.getText() + "%");
//                ps.setString(6, "%" + TCari.getText() + "%");
//                ps.setString(7, "%" + TCari.getText() + "%");
//                ps.setString(8, "%" + TCari.getText() + "%");
//                ps.setString(9, "%" + TCari.getText() + "%");
//                ps.setString(10, "%" + TCari.getText() + "%");
//                rs = ps.executeQuery();
//                while (rs.next()) {
//                    tabMode2.addRow(new String[]{
//                        rs.getString("noorder"), rs.getString("no_rawat"), rs.getString("no_rkm_medis") + " " + rs.getString("nm_pasien"),
//                        rs.getString("nm_perawatan"), rs.getString("tgl_permintaan"), rs.getString("jam_permintaan"),
//                        rs.getString("dokter_perujuk"), rs.getString("nm_dokter"), rs.getString("nm_bangsal")
//                    });
//                }
//            } catch (Exception e) {
//                System.out.println("Notif : " + e);
//            } finally {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (ps != null) {
//                    ps.close();
//                }
//            }
//            LCount.setText("" + tabMode2.getRowCount());
//        } catch (Exception e) {
//            System.out.println("Notif : " + e);
//        }
//    }

    private void jam() {
        ActionListener taskPerformer = (ActionEvent e) -> {
            if (aktif == true) {
                nol_detik = "";
                now = Calendar.getInstance().getTime();
                nilai_detik = now.getSeconds();
                if (nilai_detik <= 9) {
                    nol_detik = "0";
                }

                detik = nol_detik + Integer.toString(nilai_detik);
                if (detik.equals("05")) {
                    permintaanbaru = 0;

                    if (formalarm.contains("ranap")) {
                        tampil();
                        for (i = 0; i < tbTransfusi.getRowCount(); i++) {
                            if ((!tbTransfusi.getValueAt(i, 0).toString().equals("")) && tbTransfusi.getValueAt(i, 5).toString().equals("")) {
                                permintaanbaru++;
                            }
                        }
                    }

                    if (permintaanbaru > 0) {
                        try {
                            music = new BackgroundMusic("./suara/alarm.mp3");
                            music.start();
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                    }
                }
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }

}
