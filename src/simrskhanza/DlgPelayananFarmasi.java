package simrskhanza;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import inventory.DlgObatPerTanggal;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public class DlgPelayananFarmasi extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps, ps2, ps3;
    private ResultSet rs, rs2, rs3;
    private int i = 0, r;
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
    private DlgCariBangsal bangsal = new DlgCariBangsal(null, false);

//    private String dateString,hari,status;
//    private LocalDate tgl;
//    private Date date;
    /**
     * Creates new form DlgPemberianInfus
     *
     * @param parent
     * @param modal
     */
    public DlgPelayananFarmasi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10, 2);
        setSize(628, 674);

        Object[] row = {"Tanggal", "Jumlah Pasien", "Visite", "PIO", "Konseling", "MESO"};
        tabMode = new DefaultTableModel(null, row) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbPelayanan.setModel(tabMode);

        tbPelayanan.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbPelayanan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 6; i++) {
            TableColumn column = tbPelayanan.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(120);
            } else if (i == 1) {
                column.setPreferredWidth(250);
            } else if (i == 2) {
                column.setPreferredWidth(120);
            } else if (i == 3) {
                column.setPreferredWidth(120);
            } else if (i == 4) {
                column.setPreferredWidth(120);
            } else if (i == 5) {
                column.setPreferredWidth(150);
            }
        }
        tbPelayanan.setDefaultRenderer(Object.class, new WarnaTable());

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

        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("DlgPelayananFarmasi")) {
                    if (bangsal.getTable().getSelectedRow() != -1) {
                        kdbangsal.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(), 0).toString());
                        nmbangsal.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(), 1).toString());
                        nmbangsal.requestFocus();
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
        MnLabelDiet = new javax.swing.JMenuItem();
        MnLabelDiet1 = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbPelayanan = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        panelGlass10 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel12 = new widget.Label();
        kdbangsal = new widget.TextBox();
        nmbangsal = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnLabelDiet.setBackground(new java.awt.Color(255, 255, 254));
        MnLabelDiet.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelDiet.setForeground(java.awt.Color.darkGray);
        MnLabelDiet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLabelDiet.setText("Label Diet");
        MnLabelDiet.setName("MnLabelDiet"); // NOI18N
        MnLabelDiet.setPreferredSize(new java.awt.Dimension(150, 28));
        MnLabelDiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLabelDietActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnLabelDiet);

        MnLabelDiet1.setBackground(new java.awt.Color(255, 255, 254));
        MnLabelDiet1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelDiet1.setForeground(java.awt.Color.darkGray);
        MnLabelDiet1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLabelDiet1.setText("Semua Label Diet");
        MnLabelDiet1.setName("MnLabelDiet1"); // NOI18N
        MnLabelDiet1.setPreferredSize(new java.awt.Dimension(150, 28));
        MnLabelDiet1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLabelDiet1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnLabelDiet1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pelayanan Farmasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbPelayanan.setAutoCreateRowSorter(true);
        tbPelayanan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPelayanan.setComponentPopupMenu(jPopupMenu1);
        tbPelayanan.setName("tbPelayanan"); // NOI18N
        tbPelayanan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPelayananMouseClicked(evt);
            }
        });
        tbPelayanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPelayananKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbPelayanan);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 144));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelGlass8.add(BtnAll);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass8.add(LCount);

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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));
        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Rawat :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass10.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "22-09-2022" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(85, 23));
        panelGlass10.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(20, 23));
        panelGlass10.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "22-09-2022" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(85, 23));
        panelGlass10.add(DTPCari2);

        jLabel12.setText("Bangsal :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass10.add(jLabel12);

        kdbangsal.setName("kdbangsal"); // NOI18N
        kdbangsal.setPreferredSize(new java.awt.Dimension(70, 23));
        kdbangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdbangsalKeyPressed(evt);
            }
        });
        panelGlass10.add(kdbangsal);

        nmbangsal.setEditable(false);
        nmbangsal.setHighlighter(null);
        nmbangsal.setName("nmbangsal"); // NOI18N
        nmbangsal.setPreferredSize(new java.awt.Dimension(200, 23));
        nmbangsal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nmbangsalActionPerformed(evt);
            }
        });
        panelGlass10.add(nmbangsal);

        BtnSeek2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek2.setMnemonic('X');
        BtnSeek2.setToolTipText("Alt+X");
        BtnSeek2.setName("BtnSeek2"); // NOI18N
        BtnSeek2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek2ActionPerformed(evt);
            }
        });
        BtnSeek2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek2KeyPressed(evt);
            }
        });
        panelGlass10.add(BtnSeek2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass10.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(200, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass10.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
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
        panelGlass10.add(BtnCari);

        jPanel3.add(panelGlass10, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);
        internalFrame1.getAccessibleContext().setAccessibleName("::[ Pelayanan Farmasi]::");

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        } else if (tabMode.getRowCount() != 0) {

            Sequel.queryu("delete from temporary_gizi");
            int row = tabMode.getRowCount();
            for (int i = 0; i < row; i++) {
                Sequel.menyimpan("temporary_gizi", "'0','"
                        + tabMode.getValueAt(i, 0).toString() + "','"
                        + tabMode.getValueAt(i, 1).toString() + "','"
                        + tabMode.getValueAt(i, 2).toString() + "','"
                        + tabMode.getValueAt(i, 3).toString() + "','"
                        + tabMode.getValueAt(i, 4).toString() + "','"
                        + tabMode.getValueAt(i, 5).toString() + "','"
                        + tabMode.getValueAt(i, 5).toString() + "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''", "Transaksi Penerimaan");
            }
            Sequel.menyimpan("temporary_gizi", "'0','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''", "Transaksi Penerimaan");

            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptDiet.jrxml", "report", "::[ Data Pemberian Obat ]::",
                    "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14 from temporary_gizi order by no asc", param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
//        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
        BtnPrintActionPerformed(null);
//        }else{
//            Valid.pindah(evt, BtnHapus, BtnKeluar);
//        }
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
            tampil();
            TCari.setText("");
        } else {
            Valid.pindah(evt, BtnCari, TCari);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbPelayananMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPelayananMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
//                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbPelayananMouseClicked

    private void tbPelayananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPelayananKeyPressed
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
//                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbPelayananKeyPressed

    private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        var.setform("DlgPelayananFarmasi");
        bangsal.emptTeks();
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setVisible(true);
    }//GEN-LAST:event_BtnSeek2ActionPerformed

    private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek2KeyPressed

    private void MnLabelDietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLabelDietActionPerformed
//        if(TPasien.getText().trim().equals("")){
//            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
//        }else{
//            Map<String, Object> param = new HashMap<>();
//            param.put("namars",var.getnamars());
//            param.put("alamatrs",var.getalamatrs());
//            param.put("kotars",var.getkabupatenrs());
//            param.put("propinsirs",var.getpropinsirs());
//            param.put("kontakrs",var.getkontakrs());
//            param.put("emailrs",var.getemailrs());
//            param.put("logo",Sequel.cariGambar("select logo from setting"));
//            Valid.MyReport("rptLabelDiet.jrxml","report","::[ Label Diet ]::",
//                "select detail_beri_diet.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir, " +
//                "concat(detail_beri_diet.kd_kamar,', ',bangsal.nm_bangsal),detail_beri_diet.tanggal,detail_beri_diet.waktu,diet.nama_diet " +
//                "from detail_beri_diet inner join reg_periksa inner join pasien inner join diet inner join kamar inner join bangsal " +
//                "on detail_beri_diet.no_rawat=reg_periksa.no_rawat " +
//                "and detail_beri_diet.kd_kamar=kamar.kd_kamar "+
//                "and kamar.kd_bangsal=bangsal.kd_bangsal "+
//                "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
//                "and detail_beri_diet.kd_diet=diet.kd_diet " +
//                "where detail_beri_diet.tanggal='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"' and detail_beri_diet.waktu='"+cmbJam.getSelectedItem()+"' "+
//                "and detail_beri_diet.no_rawat='"+TNoRw.getText()+"' and diet.nama_diet='"+NmDiet.getText()+"'",param);
//        }
    }//GEN-LAST:event_MnLabelDietActionPerformed

    private void MnLabelDiet1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLabelDiet1ActionPerformed
//        if(tabMode.getRowCount()==0){
//            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
//            BtnBatal.requestFocus();
//        }else if(tabMode.getRowCount()!=0){
//            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//            Map<String, Object> param = new HashMap<>();
//            param.put("namars",var.getnamars());
//            param.put("alamatrs",var.getalamatrs());
//            param.put("kotars",var.getkabupatenrs());
//            param.put("propinsirs",var.getpropinsirs());
//            param.put("kontakrs",var.getkontakrs());
//            param.put("emailrs",var.getemailrs());
//            param.put("logo",Sequel.cariGambar("select logo from setting"));
//            Valid.MyReport("rptLabelDiet.jrxml","report","::[ Label Diet ]::",
//                "select detail_beri_diet.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir, " +
//                "concat(detail_beri_diet.kd_kamar,', ',bangsal.nm_bangsal),detail_beri_diet.tanggal,detail_beri_diet.waktu,diet.nama_diet " +
//                "from detail_beri_diet inner join reg_periksa inner join pasien inner join diet inner join kamar inner join bangsal " +
//                "on detail_beri_diet.no_rawat=reg_periksa.no_rawat and detail_beri_diet.kd_kamar=kamar.kd_kamar "+
//                "and kamar.kd_bangsal=bangsal.kd_bangsal and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and detail_beri_diet.kd_diet=diet.kd_diet " +
//                "where detail_beri_diet.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and detail_beri_diet.waktu like '%"+cmbJamCari.getSelectedItem().toString().replaceAll("Semua","").trim()+"%' and bangsal.nm_bangsal like '%"+NmBangsalCari.getText().trim()+"%' and detail_beri_diet.no_rawat like '%"+TCari.getText().trim()+"%' or "+
//                "detail_beri_diet.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and detail_beri_diet.waktu like '%"+cmbJamCari.getSelectedItem().toString().replaceAll("Semua","").trim()+"%' and bangsal.nm_bangsal like '%"+NmBangsalCari.getText().trim()+"%' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
//                "detail_beri_diet.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and detail_beri_diet.waktu like '%"+cmbJamCari.getSelectedItem().toString().replaceAll("Semua","").trim()+"%' and bangsal.nm_bangsal like '%"+NmBangsalCari.getText().trim()+"%' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' "+
//                "order by bangsal.nm_bangsal,diet.nama_diet",param);
//            this.setCursor(Cursor.getDefaultCursor());
//        }
    }//GEN-LAST:event_MnLabelDiet1ActionPerformed

    private void nmbangsalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nmbangsalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nmbangsalActionPerformed

    private void kdbangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbangsalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=?", nmbangsal, kdbangsal.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=?", nmbangsal, kdbangsal.getText());
            BtnAll.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=?", nmbangsal, kdbangsal.getText());
            DTPCari2.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnSeek2ActionPerformed(null);
        }    }//GEN-LAST:event_kdbangsalKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPelayananFarmasi dialog = new DlgPelayananFarmasi(new javax.swing.JFrame(), true);
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
    private widget.Button BtnSeek2;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnLabelDiet;
    private javax.swing.JMenuItem MnLabelDiet1;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel12;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdbangsal;
    private widget.TextBox nmbangsal;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbPelayanan;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try {           
            LocalDate startDate = LocalDate.parse(Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
            LocalDate endDate = LocalDate.parse(Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                String visite = "", kons = "";
                int jumlah = 0;
//                ps = koneksi.prepareStatement("select count(distinct no_rawat) as jlh from kamar_inap where tgl_masuk>=? AND tgl_keluar <=? group by no_rawat");
                ps = koneksi.prepareStatement("select count(kamar_inap.no_rawat) as jlh from kamar_inap inner join " +
                        "kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar and " +
                        "kamar.kd_bangsal=bangsal.kd_bangsal where bangsal.nm_bangsal like ? and " +
                        "kamar_inap.tgl_masuk <=? AND kamar_inap.tgl_keluar >=? and kamar_inap.tgl_keluar <> '0000-00-00' group by kamar_inap.tgl_masuk");
                ps2 = koneksi.prepareStatement("select count(pemeriksaan_ranap.no_rawat) as visite from pemeriksaan_ranap join pegawai on "+
                        "pegawai.nik=pemeriksaan_ranap.nip where pegawai.bidang='Ikhtiyar' and pemeriksaan_ranap.tgl_perawatan=? "+
                        "group by pemeriksaan_ranap.tgl_perawatan");
                ps3 = koneksi.prepareStatement("select count(distinct no_rawat) as kons from resep_pulang where tanggal=? group by tanggal");
                try {
                    ps.setString(1, "%" + kdbangsal.getText() + "%");
                    ps.setString(2, date.toString());
                    ps.setString(3, date.toString());
                    ps2.setString(1, date.toString());
                    ps3.setString(1, date.toString());
                    rs = ps.executeQuery();
                    rs2 = ps2.executeQuery();
                    rs3 = ps3.executeQuery();
                    while (rs.next()) {
                        jumlah = rs.getInt("jlh") + jumlah;
                    }
                    while (rs2.next()) {
                        visite = rs2.getString("visite");
                    }
                    while (rs3.next()) {
                        kons = rs3.getString("kons");
                    }
                    tabMode.addRow(new Object[]{
                        date, jumlah, visite, "", kons, ""
                    });
                } catch (Exception e) {
                    System.out.println("Notif : " + e);
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabMode.getRowCount());
    }

    public void emptTeks() {
        nmbangsal.setText("");
    }

//    private void getData() {
//        if(tbPelayanan.getSelectedRow()!= -1){
//            TNoRw.setText(tbPelayanan.getValueAt(tbPelayanan.getSelectedRow(),0).toString()); 
//            isRawat();            
//            cmbJam.setSelectedItem(tbPelayanan.getValueAt(tbPelayanan.getSelectedRow(),4).toString());
//            NmDiet.setText(tbPelayanan.getValueAt(tbPelayanan.getSelectedRow(),5).toString());
//            KdDiet.setText(Sequel.cariString("select kd_diet from diet where nama_diet='"+tbPelayanan.getValueAt(tbPelayanan.getSelectedRow(),5).toString()+"'"));
//            Valid.SetTgl(DTPTgl,tbPelayanan.getValueAt(tbPelayanan.getSelectedRow(),3).toString());
//        }
//    }
//    private void isRawat() {
//         Sequel.cariIsi("select pasien.nm_pasien from reg_periksa inner join pasien "+
//                        "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rawat=? ",TPasien,TNoRw.getText());
//         Sequel.cariIsi("select kd_kamar from kamar_inap where no_rawat=? order by tgl_masuk desc limit 1",Kamar,TNoRw.getText());
//    }
    public void setNoRm(String norwt, Date tgl1, Date tgl2) {
        TCari.setText(norwt);
        DTPCari1.setDate(tgl1);
        DTPCari2.setDate(tgl2);
    }

    public void isCek() {
//        BtnSimpan.setEnabled(var.getdiet_pasien());
//        BtnHapus.setEnabled(var.getdiet_pasien());
        BtnPrint.setEnabled(var.getdiet_pasien());
    }

//select count(no_rawat) from kamar_inap where tgl_masuk >= 2022-02-02 AND tgl_keluar <= '2022-02-03' group by tgl_masuk;
//select count(kamar_inap.no_rawat) as jlh from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal where bangsal.kd_bangsal like '%b0010%' and kamar_inap.tgl_masuk >='2022-02-01' AND kamar_inap.tgl_keluar <='2022-02-01' group by kamar_inap.no_rawat;
//select kamar_inap.no_rawat, kamar_inap.tgl_masuk, kamar_inap.tgl_keluar, count(kamar_inap.no_rawat) as jlh from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal where bangsal.kd_bangsal like '%%' and kamar_inap.tgl_masuk >='2022-02-02' AND kamar_inap.tgl_keluar <='2022-02-05' and tgl_keluar <> '0000-00-00' group by kamar_inap.no_rawat;
}
