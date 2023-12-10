package permintaan;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
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
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariDokter2;
import simrskhanza.DlgCariPoli;

/**
 *
 * @author dosen
 */
public class DlgBookingMJKN extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0;
    private DlgCariDokter dokter = new DlgCariDokter(null, false);
    private DlgCariPoli poli=new DlgCariPoli(null,false);
    private String norm="",tglperiksa="",noreg="";
    /**
     * Creates new form DlgPemberianInfus
     *
     * @param parent
     * @param modal
     */
    public DlgBookingMJKN(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        WindowGanti.setSize(500, 174);

        tabMode = new DefaultTableModel(null, new Object[]{
            "Tgl.Booking", "Jam Booking", "No.RM", "Nama Pasien", "Tgl.Periksa", "Kode Dokter","Nama Dokter",
            "Kode Poli", "Poli", "No.Reg", "Status"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 11; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if (i == 0) {
            } else if (i == 1) {
                column.setPreferredWidth(70);
            } else if (i == 2) {
                column.setPreferredWidth(70);
            } else if (i == 3) {
                column.setPreferredWidth(300);
            } else if (i == 4) {
                column.setPreferredWidth(70);
            } else if (i == 5) {
                column.setPreferredWidth(70);
            } else if (i == 6) {
                column.setPreferredWidth(120);
            } else if (i == 7) {
                column.setPreferredWidth(70);
            } else if (i == 8) {
                column.setPreferredWidth(120);
            } else if (i == 9) {
                column.setPreferredWidth(50);
            } else if (i == 10) {
                column.setWidth(70);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

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
            public void windowOpened(WindowEvent e) {;}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){                    
                    KdDokter1.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NmDokter1.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli.getTable().getSelectedRow()!= -1){                    
                    KdPoli4.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),0).toString());
                    NmPoli4.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                }   
                isNomer2();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
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
        MnEdit = new javax.swing.JMenuItem();
        WindowGanti = new javax.swing.JDialog();
        internalFrame8 = new widget.InternalFrame();
        BtnCloseIn7 = new widget.Button();
        BtnSimpan7 = new widget.Button();
        jLabel15 = new widget.Label();
        KdDokter1 = new widget.TextBox();
        NmDokter1 = new widget.TextBox();
        BtnDokter1 = new widget.Button();
        jLabel16 = new widget.Label();
        KdPoli4 = new widget.TextBox();
        NmPoli4 = new widget.TextBox();
        BtnPoli4 = new widget.Button();
        jLabel17 = new widget.Label();
        NoReg = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelCari = new widget.panelisi();
        jLabel8 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel22 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnEdit.setBackground(new java.awt.Color(255, 255, 254));
        MnEdit.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnEdit.setForeground(new java.awt.Color(70, 70, 70));
        MnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnEdit.setText("Edit Booking MJKN");
        MnEdit.setName("MnEdit"); // NOI18N
        MnEdit.setPreferredSize(new java.awt.Dimension(200, 28));
        MnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnEditActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnEdit);

        WindowGanti.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowGanti.setName("WindowGanti"); // NOI18N
        WindowGanti.setUndecorated(true);
        WindowGanti.setResizable(false);

        internalFrame8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pemindahan Jadwal Poliklinik ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame8.setName("internalFrame8"); // NOI18N
        internalFrame8.setLayout(null);

        BtnCloseIn7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn7.setMnemonic('U');
        BtnCloseIn7.setText("Tutup");
        BtnCloseIn7.setToolTipText("Alt+U");
        BtnCloseIn7.setName("BtnCloseIn7"); // NOI18N
        BtnCloseIn7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn7ActionPerformed(evt);
            }
        });
        internalFrame8.add(BtnCloseIn7);
        BtnCloseIn7.setBounds(340, 130, 100, 30);

        BtnSimpan7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnSimpan7.setMnemonic('S');
        BtnSimpan7.setText("Ganti");
        BtnSimpan7.setToolTipText("Alt+S");
        BtnSimpan7.setName("BtnSimpan7"); // NOI18N
        BtnSimpan7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan7ActionPerformed(evt);
            }
        });
        internalFrame8.add(BtnSimpan7);
        BtnSimpan7.setBounds(200, 130, 150, 30);

        jLabel15.setText("Dokter :");
        jLabel15.setName("jLabel15"); // NOI18N
        internalFrame8.add(jLabel15);
        jLabel15.setBounds(0, 60, 70, 23);

        KdDokter1.setEditable(false);
        KdDokter1.setHighlighter(null);
        KdDokter1.setName("KdDokter1"); // NOI18N
        internalFrame8.add(KdDokter1);
        KdDokter1.setBounds(75, 59, 70, 24);

        NmDokter1.setEditable(false);
        NmDokter1.setHighlighter(null);
        NmDokter1.setName("NmDokter1"); // NOI18N
        internalFrame8.add(NmDokter1);
        NmDokter1.setBounds(145, 60, 263, 23);

        BtnDokter1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter1.setMnemonic('X');
        BtnDokter1.setToolTipText("Alt+X");
        BtnDokter1.setName("BtnDokter1"); // NOI18N
        BtnDokter1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter1ActionPerformed(evt);
            }
        });
        BtnDokter1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokter1KeyPressed(evt);
            }
        });
        internalFrame8.add(BtnDokter1);
        BtnDokter1.setBounds(410, 60, 28, 23);

        jLabel16.setText("Poli :");
        jLabel16.setName("jLabel16"); // NOI18N
        internalFrame8.add(jLabel16);
        jLabel16.setBounds(0, 90, 70, 23);

        KdPoli4.setEditable(false);
        KdPoli4.setHighlighter(null);
        KdPoli4.setName("KdPoli4"); // NOI18N
        internalFrame8.add(KdPoli4);
        KdPoli4.setBounds(75, 89, 70, 24);

        NmPoli4.setEditable(false);
        NmPoli4.setHighlighter(null);
        NmPoli4.setName("NmPoli4"); // NOI18N
        internalFrame8.add(NmPoli4);
        NmPoli4.setBounds(145, 89, 263, 24);

        BtnPoli4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPoli4.setMnemonic('X');
        BtnPoli4.setToolTipText("Alt+X");
        BtnPoli4.setName("BtnPoli4"); // NOI18N
        BtnPoli4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPoli4ActionPerformed(evt);
            }
        });
        BtnPoli4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPoli4KeyPressed(evt);
            }
        });
        internalFrame8.add(BtnPoli4);
        BtnPoli4.setBounds(410, 90, 28, 23);

        jLabel17.setText("No. Reg :");
        jLabel17.setName("jLabel17"); // NOI18N
        internalFrame8.add(jLabel17);
        jLabel17.setBounds(0, 30, 70, 23);

        NoReg.setHighlighter(null);
        NoReg.setName("NoReg"); // NOI18N
        internalFrame8.add(NoReg);
        NoReg.setBounds(75, 30, 70, 23);

        WindowGanti.getContentPane().add(internalFrame8, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Booking MJKN ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setAutoCreateRowSorter(true);
        tbObat.setToolTipText("Silahkan klik untuk memilih data yang ataupun dihapus");
        tbObat.setName("tbObat"); // NOI18N
        tbObat.setComponentPopupMenu(jPopupMenu1);
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 54));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelCari.setName("panelCari"); // NOI18N
        panelCari.setPreferredSize(new java.awt.Dimension(44, 43));
        panelCari.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 9));

        jLabel8.setText("Tanggal Periksa :");
        jLabel8.setName("jLabel8"); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(100, 23));
        panelCari.add(jLabel8);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-12-2023" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(100, 23));
        panelCari.add(DTPCari1);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("s.d");
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(25, 23));
        panelCari.add(jLabel22);

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-12-2023" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(100, 23));
        panelCari.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelCari.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(200, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelCari.add(TCari);

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
        panelCari.add(BtnCari);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelCari.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelCari.add(LCount);

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
        panelCari.add(BtnKeluar);

        jPanel3.add(panelCari, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, TCari, TCari);
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
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnCari);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void MnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnEditActionPerformed
        WindowGanti.setLocationRelativeTo(internalFrame1);
        WindowGanti.setVisible(true);
    }//GEN-LAST:event_MnEditActionPerformed

    private void BtnCloseIn7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn7ActionPerformed
        WindowGanti.dispose();
    }//GEN-LAST:event_BtnCloseIn7ActionPerformed


    private void BtnSimpan7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan7ActionPerformed
        Sequel.mengedit3("booking_registrasi", "no_rkm_medis=? and tanggal_periksa=?", "kd_dokter=?,kd_poli=?,no_reg=?", 5, new String[]{
            KdDokter1.getText(), KdPoli4.getText(), NoReg.getText(), tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString(), tbObat.getValueAt(tbObat.getSelectedRow(), 4).toString()});
        Sequel.mengedit3("reg_periksa", "no_rkm_medis=? and tgl_registrasi=?", "kd_dokter=?,kd_poli=?,no_reg=?", 5, new String[]{
            KdDokter1.getText(), KdPoli4.getText(), NoReg.getText(), tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString(), tbObat.getValueAt(tbObat.getSelectedRow(), 4).toString()});
        JOptionPane.showMessageDialog(null, "Berhasil ubah jadwal..!!!");
        tampil();
        WindowGanti.dispose();
    }//GEN-LAST:event_BtnSimpan7ActionPerformed

    private void BtnDokter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter1ActionPerformed
        dokter.isCek();
        dokter.TCari.requestFocus();
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);        
    }//GEN-LAST:event_BtnDokter1ActionPerformed

    private void BtnDokter1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter1KeyPressed

    private void BtnPoli4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPoli4ActionPerformed
        poli.isCek();
        poli.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setVisible(true);        
    }//GEN-LAST:event_BtnPoli4ActionPerformed

    private void BtnPoli4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPoli4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPoli4KeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbObatMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgBookingMJKN dialog = new DlgBookingMJKN(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCloseIn7;
    private widget.Button BtnDokter1;
    private widget.Button BtnKeluar;
    private widget.Button BtnPoli4;
    private widget.Button BtnSimpan7;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox KdDokter1;
    private widget.TextBox KdPoli4;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnEdit;
    private widget.TextBox NmDokter1;
    private widget.TextBox NmPoli4;
    private widget.TextBox NoReg;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private javax.swing.JDialog WindowGanti;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame8;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel22;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelCari;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement(
                    "select booking_registrasi.tanggal_booking, booking_registrasi.jam_booking, booking_registrasi.no_rkm_medis, pasien.nm_pasien, booking_registrasi.tanggal_periksa, "
                    + "booking_registrasi.kd_dokter, dokter.nm_dokter, booking_registrasi.kd_poli, poliklinik.nm_poli, booking_registrasi.no_reg, booking_registrasi.status "
                    + "from mlite_antrian_referensi inner join booking_registrasi inner join pasien inner join poliklinik "
                    + "on mlite_antrian_referensi.tanggal_periksa=booking_registrasi.tanggal_periksa and mlite_antrian_referensi.no_rkm_medis=booking_registrasi.no_rkm_medis and "
                    + "booking_registrasi.no_rkm_medis=pasien.no_rkm_medis and booking_registrasi.kd_poli=poliklinik.kd_poli left join dokter on booking_registrasi.kd_dokter=dokter.kd_dokter "
                    + "where mlite_antrian_referensi.keterangan='Via MJKN' and mlite_antrian_referensi.tanggal_periksa between ? and ? "
                    + "and (mlite_antrian_referensi.no_rkm_medis like ? or "
                    + "pasien.nm_pasien like ?)");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, "%"+TCari.getText().trim()+"%");
                ps.setString(4, "%"+TCari.getText().trim()+"%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new Object[]{
                        rs.getString("tanggal_booking"), rs.getString("jam_booking"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"),rs.getString("tanggal_periksa"), 
                        rs.getString("kd_dokter"), rs.getString("nm_dokter"),rs.getString("kd_poli"), rs.getString("nm_poli"),rs.getString("no_reg"),rs.getString("status")
                    });
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
            System.out.println("Notif : " + e);
        }
        LCount.setText("" + tabMode.getRowCount());
    }
    
    private void getData() {
        if(tbObat.getSelectedRow()!= -1){            
            norm = tbObat.getValueAt(tbObat.getSelectedRow(),2).toString();
            tglperiksa = tbObat.getValueAt(tbObat.getSelectedRow(),4).toString();
            noreg = tbObat.getValueAt(tbObat.getSelectedRow(),9).toString();
        }
    }
    
    private void isNomer2(){
        Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_poli='"+KdPoli4.getText()+"' and tanggal_periksa='"+tbObat.getValueAt(tbObat.getSelectedRow(), 4).toString()+"'","",3,NoReg);
    }
}