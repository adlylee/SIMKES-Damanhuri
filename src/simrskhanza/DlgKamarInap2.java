/*
  Dilarang keras menggandakan/mengcopy/menyebarkan/membajak/mendecompile 
  Software ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */
package simrskhanza;

import laporan.DlgDiagnosaPenyakit;
import keuangan.DlgKamar;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.DlgBilingRanap;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author perpustakaan
 */
public class DlgKamarInap2 extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private final Properties prop = new Properties();
    public DlgIKBBayi ikb = new DlgIKBBayi(null, false);
    public DlgKamar kamar = new DlgKamar(null, false);
    public DlgReg reg = new DlgReg(null, false);
    public DlgBilingRanap billing = new DlgBilingRanap(null, false);
    public DlgDiagnosaPenyakit diagnosa = new DlgDiagnosaPenyakit(null, false);
    public DlgDiagnosaPenyakit diagnosa1 = new DlgDiagnosaPenyakit(null, false);
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Date date = new Date();
    private String kmr = "", key = "";
    private PreparedStatement ps, psanak;
    private ResultSet rs, rs2;
    private int i;
    private String namakamar = "";
    private DlgCariBangsal ruang = new DlgCariBangsal(null, false);

    /**
     * Creates new form DlgKamarInap
     *
     * @param parent
     * @param modal
     */
    public DlgKamarInap2(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        tabMode = new DefaultTableModel(null, new Object[]{
            "No.Rawat", "Nomer RM", "Nama Pasien", "Alamat Pasien", "No. Telp", "Jenis Bayar", "Kamar", "Kode Pj"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbKamIn.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbKamIn.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbKamIn.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 8; i++) {
            TableColumn column = tbKamIn.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(105);
            } else if (i == 1) {
                column.setPreferredWidth(70);
            } else if (i == 2) {
                column.setPreferredWidth(300);
            } else if (i == 3) {
                column.setPreferredWidth(200);
            } else if (i == 4) {
                column.setPreferredWidth(120);
            } else if (i == 6) {
                column.setPreferredWidth(120);
            } else {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbKamIn.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

                String sep = (String) table.getModel().getValueAt(row, 5);
                String norawat = (String) table.getModel().getValueAt(row, 0);
                String kdpj = (String) table.getModel().getValueAt(row, 7);

                if (!sep.contains("1708") && "BPJ".equals(kdpj) && !"".equals(norawat)) {
                    setBackground(new Color(153, 0, 76));
                    setForeground(Color.WHITE);
                    if (isSelected) {
                        setForeground(Color.CYAN);
                    }
                } else {
                    if (row % 2 == 1) {
                        setForeground(Color.BLACK);
                        setBackground(new Color(226, 234, 248));
                    } else {
                        setForeground(Color.BLACK);
                        setBackground(new Color(255, 255, 255));
                    }
                    if (isSelected) {
                        setForeground(Color.RED);
                    }
                }
                return this;
            }
        });

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
                    BangsalCari.setText(ruang.getTable().getValueAt(ruang.getTable().getSelectedRow(), 1).toString());
                    BangsalCari.requestFocus();
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        internalFrame1 = new widget.InternalFrame();
        PanelCariUtama = new javax.swing.JPanel();
        panelGlass11 = new widget.panelisi();
        jLabel21 = new widget.Label();
        BangsalCari = new widget.TextBox();
        btnBangsalCari = new widget.Button();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelCari = new widget.panelisi();
        R1 = new widget.RadioButton();
        R2 = new widget.RadioButton();
        DTPCari1 = new widget.Tanggal();
        jLabel22 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        R3 = new widget.RadioButton();
        DTPCari3 = new widget.Tanggal();
        jLabel25 = new widget.Label();
        DTPCari4 = new widget.Tanggal();
        Scroll = new widget.ScrollPane();
        tbKamIn = new widget.Table();

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
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Kamar Inap Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        PanelCariUtama.setName("PanelCariUtama"); // NOI18N
        PanelCariUtama.setOpaque(false);
        PanelCariUtama.setPreferredSize(new java.awt.Dimension(100, 88));
        PanelCariUtama.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass11.setName("panelGlass11"); // NOI18N
        panelGlass11.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 9));

        jLabel21.setText("Kamar :");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass11.add(jLabel21);

        BangsalCari.setName("BangsalCari"); // NOI18N
        BangsalCari.setPreferredSize(new java.awt.Dimension(200, 23));
        BangsalCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BangsalCariKeyPressed(evt);
            }
        });
        panelGlass11.add(BangsalCari);

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
        panelGlass11.add(btnBangsalCari);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass11.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(230, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass11.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('4');
        BtnCari.setToolTipText("Alt+4");
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
        panelGlass11.add(BtnCari);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass11.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(75, 23));
        panelGlass11.add(LCount);

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
        panelGlass11.add(BtnKeluar);

        PanelCariUtama.add(panelGlass11, java.awt.BorderLayout.PAGE_END);

        panelCari.setName("panelCari"); // NOI18N
        panelCari.setPreferredSize(new java.awt.Dimension(44, 43));
        panelCari.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 9));

        R1.setBackground(new java.awt.Color(240, 250, 230));
        R1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.pink));
        buttonGroup1.add(R1);
        R1.setSelected(true);
        R1.setText("Belum Pulang");
        R1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R1.setName("R1"); // NOI18N
        R1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelCari.add(R1);

        R2.setBackground(new java.awt.Color(240, 250, 230));
        R2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.pink));
        buttonGroup1.add(R2);
        R2.setText("Tgl.Masuk :");
        R2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R2.setName("R2"); // NOI18N
        R2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelCari.add(R2);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-08-2023" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPCari1ItemStateChanged(evt);
            }
        });
        DTPCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPCari1KeyPressed(evt);
            }
        });
        panelCari.add(DTPCari1);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("s.d");
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(25, 23));
        panelCari.add(jLabel22);

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-08-2023" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPCari1ItemStateChanged(evt);
            }
        });
        DTPCari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPCari2KeyPressed(evt);
            }
        });
        panelCari.add(DTPCari2);

        R3.setBackground(new java.awt.Color(240, 250, 230));
        R3.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.pink));
        buttonGroup1.add(R3);
        R3.setText("Pulang :");
        R3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R3.setName("R3"); // NOI18N
        R3.setPreferredSize(new java.awt.Dimension(75, 23));
        panelCari.add(R3);

        DTPCari3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-08-2023" }));
        DTPCari3.setDisplayFormat("dd-MM-yyyy");
        DTPCari3.setName("DTPCari3"); // NOI18N
        DTPCari3.setOpaque(false);
        DTPCari3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPCari3ItemStateChanged(evt);
            }
        });
        DTPCari3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPCari3KeyPressed(evt);
            }
        });
        panelCari.add(DTPCari3);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("s.d");
        jLabel25.setName("jLabel25"); // NOI18N
        jLabel25.setPreferredSize(new java.awt.Dimension(25, 23));
        panelCari.add(jLabel25);

        DTPCari4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-08-2023" }));
        DTPCari4.setDisplayFormat("dd-MM-yyyy");
        DTPCari4.setName("DTPCari4"); // NOI18N
        DTPCari4.setOpaque(false);
        DTPCari4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPCari4ItemStateChanged(evt);
            }
        });
        DTPCari4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPCari4KeyPressed(evt);
            }
        });
        panelCari.add(DTPCari4);

        PanelCariUtama.add(panelCari, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(PanelCariUtama, java.awt.BorderLayout.PAGE_END);

        Scroll.setToolTipText("Klik data di table, kemudian klik kanan untuk memilih menu yang diinginkan");
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamIn.setToolTipText("Klik data di table, kemudian klik kanan untuk memilih menu yang diinginkan");
        tbKamIn.setName("tbKamIn"); // NOI18N
        tbKamIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKamInMouseClicked(evt);
            }
        });
        tbKamIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKamInKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbKamIn);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            tbKamIn.requestFocus();
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

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed

}//GEN-LAST:event_DTPTglKeyPressed

    private void DTPTglItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPTglItemStateChanged

    }//GEN-LAST:event_DTPTglItemStateChanged

    private void DTPCari1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPCari1ItemStateChanged
        R2.setSelected(true);

}//GEN-LAST:event_DTPCari1ItemStateChanged

    private void DTPCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPCari1KeyPressed
        Valid.pindah(evt, BangsalCari, DTPCari2);
}//GEN-LAST:event_DTPCari1KeyPressed

    private void DTPCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPCari2KeyPressed
        Valid.pindah(evt, DTPCari1, BangsalCari);
}//GEN-LAST:event_DTPCari2KeyPressed

    private void tbKamInMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKamInMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            if (evt.getClickCount() == 2) {
                dispose();
            }
        }
}//GEN-LAST:event_tbKamInMouseClicked

    private void tbKamInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamInKeyPressed
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
            if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
                dispose();
            } else if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
                TCari.setText("");
                TCari.requestFocus();
            }
        }
}//GEN-LAST:event_tbKamInKeyPressed

private void btnBangsalCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBangsalCariActionPerformed
    var.setform("DlgKamarInap2");
    ruang.isCek();
    ruang.emptTeks();
    ruang.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
    ruang.setLocationRelativeTo(internalFrame1);
    ruang.setVisible(true);
}//GEN-LAST:event_btnBangsalCariActionPerformed

private void btnBangsalCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnBangsalCariKeyPressed
    Valid.pindah(evt, DTPCari2, TCari);
}//GEN-LAST:event_btnBangsalCariKeyPressed

private void BangsalCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BangsalCariKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        tampil();
    } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
        btnBangsalCariActionPerformed(null);
    } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
        DTPCari3.requestFocus();
    } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
        TCari.requestFocus();
    }
}//GEN-LAST:event_BangsalCariKeyPressed

private void DTPCari3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPCari3ItemStateChanged
    R3.setSelected(true);
}//GEN-LAST:event_DTPCari3ItemStateChanged

private void DTPCari3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPCari3KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_DTPCari3KeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated

    }//GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void DTPCari4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPCari4ItemStateChanged
        R3.setSelected(true);
    }//GEN-LAST:event_DTPCari4ItemStateChanged

    private void DTPCari4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPCari4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DTPCari4KeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnCari, BtnCari);
        }
    }//GEN-LAST:event_BtnKeluarKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgKamarInap2 dialog = new DlgKamarInap2(new javax.swing.JFrame(), true);
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
    private widget.TextBox BangsalCari;
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPCari3;
    private widget.Tanggal DTPCari4;
    private widget.Label LCount;
    private javax.swing.JPanel PanelCariUtama;
    private widget.RadioButton R1;
    private widget.RadioButton R2;
    private widget.RadioButton R3;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.Button btnBangsalCari;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel25;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.panelisi panelCari;
    private widget.panelisi panelGlass11;
    private widget.Table tbKamIn;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        if (R1.isSelected() == true) {
            kmr = " stts_pulang='-'  ";
            if (!BangsalCari.getText().equals("")) {
                kmr = " stts_pulang='-' and bangsal.nm_bangsal='" + BangsalCari.getText() + "' ";
            }
        } else if (R2.isSelected() == true) {
            kmr = " kamar_inap.tgl_masuk between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' ";
            if (!BangsalCari.getText().equals("")) {
                kmr = " kamar_inap.tgl_masuk between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and bangsal.nm_bangsal='" + BangsalCari.getText() + "' ";
            }
        } else if (R3.isSelected() == true) {
            kmr = " kamar_inap.tgl_keluar between '" + Valid.SetTgl(DTPCari3.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari4.getSelectedItem() + "") + "' ";
            if (!BangsalCari.getText().equals("")) {
                kmr = " kamar_inap.tgl_keluar between '" + Valid.SetTgl(DTPCari3.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari4.getSelectedItem() + "") + "' and bangsal.nm_bangsal='" + BangsalCari.getText() + "'  ";
            }
        }

        key = kmr + " ";
        if (!TCari.getText().equals("")) {
            key = kmr + "and ( kamar_inap.no_rawat like '%" + TCari.getText().trim() + "%' or "
                    + "reg_periksa.no_rkm_medis like '%" + TCari.getText().trim() + "%' or "
                    + "pasien.nm_pasien like '%" + TCari.getText().trim() + "%' or "
                    + "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%" + TCari.getText().trim() + "%' or "
                    + "pasien.no_tlp like '%" + TCari.getText().trim() + "%' or "
                    + "kamar_inap.kd_kamar like '%" + TCari.getText().trim() + "%' or "
                    + "bangsal.nm_bangsal like '%" + TCari.getText().trim() + "%' or "
                    + "kamar_inap.diagnosa_awal like '%" + TCari.getText().trim() + "%' or "
                    + "kamar_inap.diagnosa_akhir like '%" + TCari.getText().trim() + "%' or "
                    + "kamar_inap.trf_kamar like '%" + TCari.getText().trim() + "%' or "
                    + "kamar_inap.tgl_masuk like '%" + TCari.getText().trim() + "%' or "
                    + "dokter.nm_dokter like '%" + TCari.getText().trim() + "%' or "
                    + "kamar_inap.stts_pulang like '%" + TCari.getText().trim() + "%' or "
                    + "kamar_inap.tgl_keluar like '%" + TCari.getText().trim() + "%' or "
                    + "penjab.png_jawab like '%" + TCari.getText().trim() + "%' or "
                    + "kamar_inap.ttl_biaya like '%" + TCari.getText().trim() + "%' ) ";
        }

        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement(
                    "select kamar_inap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,reg_periksa.p_jawab,reg_periksa.hubunganpj,"
                    + "penjab.png_jawab,concat(kamar_inap.kd_kamar,' ',bangsal.nm_bangsal) as kamar,pasien.no_tlp, kamar_inap.trf_kamar,kamar_inap.diagnosa_awal,kamar_inap.diagnosa_akhir,"
                    + "kamar_inap.tgl_masuk,kamar_inap.jam_masuk,if(kamar_inap.tgl_keluar='0000-00-00','',kamar_inap.tgl_keluar) as tgl_keluar,if(kamar_inap.jam_keluar='00:00:00','',kamar_inap.jam_keluar) as jam_keluar,"
                    + "kamar_inap.ttl_biaya,kamar_inap.stts_pulang,kamar_inap.lama,dokter.nm_dokter,kamar_inap.kd_kamar,reg_periksa.kd_pj,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,reg_periksa.status_bayar "
                    + "from kamar_inap , reg_periksa , pasien , kamar , bangsal , kelurahan , kecamatan , kabupaten , dokter , penjab "
                    + "where kamar_inap.no_rawat=reg_periksa.no_rawat "
                    + "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "and reg_periksa.kd_dokter=dokter.kd_dokter "
                    + "and reg_periksa.kd_pj=penjab.kd_pj "
                    + "and kamar_inap.kd_kamar=kamar.kd_kamar "
                    + "and kamar.kd_bangsal=bangsal.kd_bangsal and pasien.kd_kel=kelurahan.kd_kel "
                    + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "
                    + "and " + key + " order by bangsal.nm_bangsal,kamar_inap.tgl_masuk,kamar_inap.jam_masuk");
            try {
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien") + " (" + rs.getString("umur") + ")",
                        rs.getString("alamat"), rs.getString("no_tlp"), rs.getString("png_jawab") + ": " + Sequel.cariIsi("select no_sep from bridging_sep where no_rawat='" + rs.getString("no_rawat") + "'"),
                        rs.getString("kamar"), rs.getString("kd_pj")
                    });
                    psanak = koneksi.prepareStatement(
                            "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                            + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                            + "from reg_periksa inner join pasien inner join ranap_gabung on "
                            + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                    try {
                        psanak.setString(1, rs.getString(1));
                        rs2 = psanak.executeQuery();
                        if (rs2.next()) {
                            tabMode.addRow(new String[]{
                                "", rs2.getString("no_rkm_medis"), rs2.getString("nm_pasien") + " (" + rs2.getString("umur") + ")",
                                rs.getString("alamat"), "", rs.getString("png_jawab"),
                                rs.getString("kamar"), rs.getString("kd_pj")
                            });
                        }
                    } catch (Exception ex) {
                        System.out.println("Notifikasi : " + ex);
                    } finally {
                        if (rs2 != null) {
                            rs2.close();
                        }
                        if (psanak != null) {
                            psanak.close();
                        }
                    }
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
        LCount.setText("" + tabMode.getRowCount());
    }

    public void emptTeks() {
        date = new Date();
    }

    private void getData() {
        if (tbKamIn.getSelectedRow() != -1) {
        }
    }

    public void isCek() {
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            namakamar = prop.getProperty("KAMARAKTIFRANAP");
        } catch (Exception ex) {
            namakamar = "";
        }

        if (!namakamar.equals("")) {
            if (var.getkode().equals("Admin Utama")) {
                BangsalCari.setText("");
                btnBangsalCari.setEnabled(true);
                BangsalCari.setEditable(true);
            } else {
                BangsalCari.setText(namakamar);
                btnBangsalCari.setEnabled(true);
                BangsalCari.setEditable(true);
            }
        } else {
            btnBangsalCari.setEnabled(true);
            BangsalCari.setEditable(true);
        }
    }

    public void setCariKosong() {
        TCari.setText("");
    }

    public JTable getTable() {
        return tbKamIn;
    }
    
    public void setNoRM(String posisi){
        posisi="surat";
        R1.setEnabled(false);
        R2.setEnabled(false);
        DTPCari1.setEnabled(false);
        DTPCari2.setEnabled(false);
        jLabel22.setEnabled(false);
        R3.setSelected(true);
    }
}
