/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import inventory.DlgCariObat;
import javax.swing.JOptionPane;
/**
 *
 * @author root
 */
public class DlgPaketObatMaster extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    
    /**
     * Creates new form DlgPaketObatMaster
     */
    public DlgPaketObatMaster(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        Object[] row={"Kode Obat","Nama Paket Obat"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObatPaket.setModel(tabMode);
        tbObatPaket.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObatPaket.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 0; i < 2; i++) {
            TableColumn column = tbObatPaket.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(300);
            }
        }
       tbObatPaket.setDefaultRenderer(Object.class, new WarnaTable());   
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        panelBiasa2 = new widget.PanelBiasa();
        label1 = new widget.Label();
        TKodePaket = new widget.TextBox();
        label2 = new widget.Label();
        TNamaPaket = new widget.TextBox();
        panelBiasa1 = new widget.PanelBiasa();
        Scroll = new widget.ScrollPane();
        tbObatPaket = new widget.Table();
        panelisi3 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        BtnTambah = new widget.Button();
        BtnSimpan1 = new widget.Button();
        BtnHapus1 = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "::[ Data Master Paket Obat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11))); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout());

        panelBiasa2.setPreferredSize(new java.awt.Dimension(828, 80));

        label1.setText("Kode Paket :");

        label2.setText("Nama Paket :");

        javax.swing.GroupLayout panelBiasa2Layout = new javax.swing.GroupLayout(panelBiasa2);
        panelBiasa2.setLayout(panelBiasa2Layout);
        panelBiasa2Layout.setHorizontalGroup(
            panelBiasa2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBiasa2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBiasa2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBiasa2Layout.createSequentialGroup()
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(TKodePaket, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBiasa2Layout.createSequentialGroup()
                        .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(TNamaPaket, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(423, Short.MAX_VALUE))
        );
        panelBiasa2Layout.setVerticalGroup(
            panelBiasa2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBiasa2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(panelBiasa2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TKodePaket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBiasa2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TNamaPaket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        internalFrame1.add(panelBiasa2, java.awt.BorderLayout.PAGE_START);

        panelBiasa1.setLayout(new java.awt.BorderLayout());

        Scroll.setOpaque(true);

        tbObatPaket.setAutoCreateRowSorter(true);
        tbObatPaket.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObatPaket.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatPaketMouseClicked(evt);
            }
        });
        tbObatPaket.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatPaketKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObatPaket);

        panelBiasa1.add(Scroll, java.awt.BorderLayout.CENTER);

        internalFrame1.add(panelBiasa1, java.awt.BorderLayout.CENTER);

        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi3.add(label9);

        TCari.setPreferredSize(new java.awt.Dimension(312, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
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
        panelisi3.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("2Alt+2");
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
        panelisi3.add(BtnAll);

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambah.setMnemonic('3');
        BtnTambah.setToolTipText("Alt+3");
        BtnTambah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        panelisi3.add(BtnTambah);

        BtnSimpan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan1.setMnemonic('S');
        BtnSimpan1.setToolTipText("Alt+S");
        BtnSimpan1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSimpan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan1ActionPerformed(evt);
            }
        });
        panelisi3.add(BtnSimpan1);

        BtnHapus1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus1.setMnemonic('H');
        BtnHapus1.setToolTipText("Alt+H");
        BtnHapus1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnHapus1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapus1ActionPerformed(evt);
            }
        });
        panelisi3.add(BtnHapus1);

        label10.setText("Record :");
        label10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi3.add(LCount);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('4');
        BtnKeluar.setToolTipText("Alt+4");
        BtnKeluar.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        panelisi3.add(BtnKeluar);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbObatPaket.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed

    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed

    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed

    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed

    }//GEN-LAST:event_BtnTambahActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnSimpan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan1ActionPerformed
        if(TKodePaket.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Kode tidak boleh kosong..!!");
        } else if (TNamaPaket.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Kode tidak boleh kosong..!!");
        }else{
            try {
                String paket;
                paket = "insert into paketobat values ('"+TKodePaket.getText()+"','"+TNamaPaket.getText()+"')";
                PreparedStatement paket_ps = koneksi.prepareStatement(paket);          
                paket_ps.execute();
            } catch (Exception e) {
                System.err.println("Notifikasi : "+e);
            }
           tampil();
        } 
    }//GEN-LAST:event_BtnSimpan1ActionPerformed

    private void BtnHapus1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapus1ActionPerformed
        Valid.hapusTable(tabMode,TKodePaket,"paketobat","kode_paket");
        tampil();
        
    }//GEN-LAST:event_BtnHapus1ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
       tampil();
    }//GEN-LAST:event_formWindowOpened

    private void tbObatPaketKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatPaketKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
         dispose();
        }
    }//GEN-LAST:event_tbObatPaketKeyPressed

    private void tbObatPaketMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatPaketMouseClicked
        TKodePaket.setText(tbObatPaket.getValueAt(tbObatPaket.getSelectedRow(),0).toString());
        TNamaPaket.setText(tbObatPaket.getValueAt(tbObatPaket.getSelectedRow(),1).toString());
    }//GEN-LAST:event_tbObatPaketMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DlgPaketObatMaster dialog = new DlgPaketObatMaster(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnHapus1;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan1;
    private widget.Button BtnTambah;
    private widget.Label LCount;
    private widget.ScrollPane Scroll;
    public widget.TextBox TCari;
    private widget.TextBox TKodePaket;
    private widget.TextBox TNamaPaket;
    private widget.InternalFrame internalFrame1;
    private widget.Label label1;
    private widget.Label label10;
    private widget.Label label2;
    private widget.Label label9;
    private widget.PanelBiasa panelBiasa1;
    private widget.PanelBiasa panelBiasa2;
    private widget.panelisi panelisi3;
    private widget.Table tbObatPaket;
    // End of variables declaration//GEN-END:variables

    public void tampil(){
        try {
            ps=koneksi.prepareStatement(" select * from paketobat where kode_paket like ? or paket_obat like ? "); 
            ps.setString(1,"%"+TCari.getText()+"%");
            ps.setString(2,"%"+TCari.getText()+"%");
            rs=ps.executeQuery();
            Valid.tabelKosong(tabMode);  
            while(rs.next()){
                tabMode.addRow(new Object[] {rs.getString("kode_paket"),rs.getString("paket_obat")});
                }
            } catch (Exception e) {
               System.err.println("Notifikasi :"+e);
            }
      
    }

    public JTable getTable() {
        return tbObatPaket;
    }
}
