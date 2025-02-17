/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgLhtBiaya.java
 *
 * Created on 12 Jul 10, 16:21:34
 */

package laporan;

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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author perpustakaan
 */
public final class DlgDkkSurveilansRalan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2,ps3;
    private ResultSet rs,rs2,rs3;
    private int i=0,hr0s7=0,hr8s28=0,kr1th=0,th1s4=0,th5s9=0,th10s14=0,th15s19=0,th20s44=0,th45s54=0,th55s59=0,
                th60s69=0,th70plus=0,laki=0,per=0,ttl=0,jmltotal;
    private String pny="",key="";
    
    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public DlgDkkSurveilansRalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);

        Object[] rowRwJlDr={"No.","Kode ICD 10","Jenis Penyakit","0-7 Hr","8-28 Hr","< 1","1-4","5-9","10-14","15-19",
                            "20-44","45-54","55-59","60-69","70+","Laki","Perp","Ttl.Kunjungan"};
        tabMode=new DefaultTableModel(null,rowRwJlDr){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbBangsal.setModel(tabMode);
        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal.getBackground()));
        tbBangsal.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBangsal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 18; i++) {
            TableColumn column = tbBangsal.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(30);
            }else if(i==1){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==17){
                column.setPreferredWidth(100);
            }else{
                column.setPreferredWidth(45);
            }
        }
        tbBangsal.setDefaultRenderer(Object.class, new WarnaTable());

        TKd.setDocument(new batasInput((byte)20).getKata(TKd));                
        key=" select concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur) as umur,pasien.jk,pasien.no_rkm_medis from pasien inner join reg_periksa inner join diagnosa_pasien " +
                    "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=diagnosa_pasien.no_rawat where " +
                    "diagnosa_pasien.status='Ralan' and diagnosa_pasien.prioritas='1' and diagnosa_pasien.kd_penyakit in (SELECT diagnosa_pasien.kd_penyakit " +
                    "FROM reg_periksa inner join diagnosa_pasien " +
                    "on reg_periksa.no_rawat=diagnosa_pasien.no_rawat " +
                    "WHERE diagnosa_pasien.kd_penyakit in ('E11','E14')) " +
                    "group by diagnosa_pasien.no_rawat";
        try {
            /*ps=koneksi.prepareStatement("select diagnosa_pasien.kd_penyakit,SUBSTRING(penyakit.nm_penyakit,1,80) as nm_penyakit from diagnosa_pasien inner join penyakit "+
                    "inner join reg_periksa on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit and reg_periksa.no_rawat=diagnosa_pasien.no_rawat "+
                    "where diagnosa_pasien.status='Ralan' and diagnosa_pasien.prioritas='1' and reg_periksa.tgl_registrasi between ? and ? and diagnosa_pasien.kd_penyakit<>'-' group by diagnosa_pasien.kd_penyakit ");*/
//            ps=koneksi.prepareStatement("select diagnosa_pasien.kd_penyakit,SUBSTRING(penyakit.nm_penyakit,1,80) as nm_penyakit from diagnosa_pasien inner join penyakit "+
//                    "inner join reg_periksa on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit and reg_periksa.no_rawat=diagnosa_pasien.no_rawat "+
//                    "where diagnosa_pasien.status='Ralan' and diagnosa_pasien.prioritas='1' and reg_periksa.tgl_registrasi between ? and ? and diagnosa_pasien.kd_penyakit IN "+
////                    "('A09','A06','A01','A15','A16','Z03','A35','K75.9','O98.4','B15.9','B16.9','B54','B51','B50','A91','A90','J18','P23.9','J06.9','G04.9','G03.9','I20.9','I21.9','I10','I11','I12','I13','I15','E10','E11','E12','E14','C53','C50','C34','S06','F29.9') "+
//                    "('A00.9','A09.0','A09.9','A75.9','A15.2','A16.9','A30.1','A30.2','A30.3','A30.4','A30.5','B05.9','A36.9','R05','A35','K75.9','B54','B51.9','B50.9','A91','A90','J18.9','J18.8','A53.9','A54.9','A66.9','B74.9','J11.1','I10') "+
//                    "group by diagnosa_pasien.kd_penyakit ");
            

        } catch (Exception e) {
            System.out.println(e);
        }
    }    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TKd = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbBangsal = new widget.Table();
        panelGlass5 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        BtnCari1 = new widget.Button();
        label12 = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        TKd.setForeground(new java.awt.Color(255, 255, 255));
        TKd.setName("TKd"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Surveilans Rawat Jalan Kasus Baru ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbBangsal.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbBangsal.setName("tbBangsal"); // NOI18N
        Scroll.setViewportView(tbBangsal);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tgl.Masuk :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass5.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass5.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(30, 23));
        panelGlass5.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass5.add(Tgl2);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('2');
        BtnCari1.setToolTipText("Alt+2");
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
        panelGlass5.add(BtnCari1);

        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass5.add(label12);

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
        panelGlass5.add(BtnPrint);

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
        panelGlass5.add(BtnKeluar);

        internalFrame1.add(panelGlass5, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            
            Sequel.queryu("delete from temporary");
            Map<String, Object> param = new HashMap<>();
            param.put("tanggal",Tgl2.getDate());
            param.put("jmltotal",jmltotal+"");
            for(int r=0;r<tabMode.getRowCount();r++){  
                    Sequel.menyimpan("temporary","'0','"+
                                    tabMode.getValueAt(r,0).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,1).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,2).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,3).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,4).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,5).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,6).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,7).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,8).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,9).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,10).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,11).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,12).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,13).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,14).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,15).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,16).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,17).toString().replaceAll("'","`")+"','','','','','','','','','','','','','','','','','','',''","Rekap Nota Pembayaran");
            }
              
                param.put("namars",var.getnamars());
                param.put("alamatrs",var.getalamatrs());
                param.put("kotars",var.getkabupatenrs());
                param.put("propinsirs",var.getpropinsirs());
            Valid.MyReport("rptSurveilansRalan.jrxml","report","::[ Surveilans PD3I ]::",
                "select * from temporary order by no asc",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            //Valid.pindah(evt, BtnHapus, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnKeluar,TKd);}
}//GEN-LAST:event_BtnKeluarKeyPressed

private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        
        tampil();
}//GEN-LAST:event_BtnCari1ActionPerformed

private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            Valid.pindah(evt, TKd, BtnPrint);
        }
}//GEN-LAST:event_BtnCari1KeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
//        tampil();
    }//GEN-LAST:event_formWindowOpened

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgDkkSurveilansRalan dialog = new DlgDkkSurveilansRalan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari1;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.ScrollPane Scroll;
    private widget.TextBox TKd;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label18;
    private widget.panelisi panelGlass5;
    private widget.Table tbBangsal;
    // End of variables declaration//GEN-END:variables

    public void tampil(){        
        try{   
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            Valid.tabelKosong(tabMode);
            i=1;            
            String[][] pnykt = {{"Kolera","'A00.9'"},{"Diare","'A09.0'"},{"Diare berdarah","'A09.9'"},{"Tifus Perut Klinis","'A75.9'"},{"TB Paru BTA+","'A15.2'"},{"Tersangka TB Paru","'A16.9'"},{"Kusta PB","'A30.1','A30.2'"},{"Kusta MB","'A30.3','A30.4','A30.5'"},{"Campak","'B05.9'"},{"Difteri","'A36.9'"},{"Batuk Rejan","'R05'"},{"Tetanus","'A35'"},{"Hepatitis Klinis","'K75.9'"},{"Malaria Klinis","'B54'"},{"Malaria Vivax","'B51.9'"},{"Malaria Falcifarusm","'B50.9'"},{"Malaria Mix","'B54','B51.9','B50.9'"},{"DBD","'A91'"},{"Demam Dengue","'A90'"},{"Pneumonia","'J18.9','J18.8'"},{"sifilis","'A53.9'"},{"Gonorrhoe","'A54.9'"},{"Frambusia","'A66.9'"},{"Filariasis","'B74.9'"},{"Influensa","'J11.1'"},{"Hipertensi","'I10'"},{"Diabetes Melitus","'E11','E14'"}};
            for (int j = 0; j < pnykt.length; j++) {
                hr0s7=0;hr8s28=0;kr1th=0;th1s4=0;th5s9=0;th10s14=0;th15s19=0;th20s44=0;th45s54=0;th55s59=0;th60s69=0;th70plus=0;laki=0;per=0;ttl=0;
                    ps=koneksi.prepareStatement(" select concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur) as umur,pasien.jk,pasien.no_rkm_medis from pasien inner join reg_periksa inner join diagnosa_pasien " +
                    "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=diagnosa_pasien.no_rawat where " +
                    "diagnosa_pasien.status='Ralan' and diagnosa_pasien.prioritas='1' and reg_periksa.tgl_registrasi between ? and ?  and diagnosa_pasien.kd_penyakit in (SELECT diagnosa_pasien.kd_penyakit " +
                    "FROM reg_periksa inner join diagnosa_pasien " +
                    "on reg_periksa.no_rawat=diagnosa_pasien.no_rawat " +
                    "WHERE diagnosa_pasien.kd_penyakit in ("+pnykt[j][1]+")) " +
                    "group by diagnosa_pasien.no_rawat");
                    pny = pnykt[j][0];
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    rs=ps.executeQuery();
                    while (rs.next()) {
                        ttl=ttl+1;
                        jmltotal=jmltotal+1;
                            switch (rs.getString("jk")) {
                                case "L":
                                    laki=laki+1;
                                    break;
                                case "P":
                                    per=per+1;
                                    break;
                            }
                        if(rs.getString("umur").contains("Hr")){
                                if(Valid.SetAngka(rs.getString("umur").replaceAll(" Hr","").replaceAll("Hr","").replaceAll(" ",""))<=7){
                                    hr0s7=hr0s7+1;
                                }else if(Valid.SetAngka(rs.getString("umur").replaceAll(" Hr","").replaceAll("Hr","").replaceAll(" ",""))<=28){
                                    hr8s28=hr8s28+1;
                                }
                            }else if(rs.getString("umur").contains("Bl")){
                                kr1th=kr1th+1;
                            }else if(rs.getString("umur").contains("Th")){
                                if(Valid.SetAngka(rs.getString("umur").replaceAll(" Th","").replaceAll("Th","").replaceAll(" ",""))<=4){
                                    th1s4=th1s4+1;
                                }else if(Valid.SetAngka(rs.getString("umur").replaceAll(" Th","").replaceAll("Th","").replaceAll(" ",""))<=9){
                                    th5s9=th5s9+1;
                                }else if(Valid.SetAngka(rs.getString("umur").replaceAll(" Th","").replaceAll("Th","").replaceAll(" ",""))<=14){
                                    th10s14=th10s14+1;
                                }else if(Valid.SetAngka(rs.getString("umur").replaceAll(" Th","").replaceAll("Th","").replaceAll(" ",""))<=19){
                                    th15s19=th15s19+1;
                                }else if(Valid.SetAngka(rs.getString("umur").replaceAll(" Th","").replaceAll("Th","").replaceAll(" ",""))<=44){
                                    th20s44=th20s44+1;
                                }else if(Valid.SetAngka(rs.getString("umur").replaceAll(" Th","").replaceAll("Th","").replaceAll(" ",""))<=54){
                                    th45s54=th45s54+1;
                                }else if(Valid.SetAngka(rs.getString("umur").replaceAll(" Th","").replaceAll("Th","").replaceAll(" ",""))<=59){
                                    th55s59=th55s59+1;
                                }else if(Valid.SetAngka(rs.getString("umur").replaceAll(" Th","").replaceAll("Th","").replaceAll(" ",""))<=69){
                                    th60s69=th60s69+1;
                                }else if(Valid.SetAngka(rs.getString("umur").replaceAll(" Th","").replaceAll("Th","").replaceAll(" ",""))>=70){
                                    th70plus=th70plus+1;
                                }
                            }
                    }
                tabMode.addRow(new Object[]{
                   i, pnykt[j][1],pny,hr0s7,hr8s28,kr1th,th1s4,th5s9,th10s14,th15s19,th20s44,th45s54,th55s59,th60s69,th70plus,laki,per,ttl
                });
                i++;
            }            
            this.setCursor(Cursor.getDefaultCursor());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }


}
