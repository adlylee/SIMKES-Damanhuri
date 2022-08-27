package bridging;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

public class BPJSMonitoringKunjungan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    private PreparedStatement ps,pssep;
    private ResultSet rs,rssep;
    private final Properties prop = new Properties();
    private BPJSApi api=new BPJSApi();
    private BPJSCekReferensiFaskes faskes=new BPJSCekReferensiFaskes(null,false);
    private BPJSCekReferensiPenyakit penyakit=new BPJSCekReferensiPenyakit(null,false);
    private BPJSCekReferensiPoli poli=new BPJSCekReferensiPoli(null,false);
    private int i=0;
    private String URL="",link="",mr="";
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private double tagihan=0,gruper=0,tarifrs=0,total=0,subtotal=0;
   
    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public BPJSMonitoringKunjungan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.SEP","No.Rawat","No.RM","Nama Pasien","Tanggal SEP","Tanggal Rujukan", 
                "No.Rujukan","Kode PPK Rujukan","Nama PPK Rujukan","Kode PPK Pelayanan", 
                "Nama PPK Pelayanan","Jenis Pelayanan","Catatan", "Kode Diagnosa", 
                "Nama Diagnosa", "Kode Poli", "Nama Poli", "Kelas Rawat", "Laka Lantas", 
                "Lokasi Laka Lantas", "User Input","Tgl.Lahir","Peserta",
                "J.Kel","No.Kartu","Tanggal Pulang","Asal Rujukan","Eksekutif",
                "COB","Penjamin","No.Telp","INACBG","Status","No.FPK","Pengajuan",
                "Disetujui","Tarif Gruper","Tarif RS","Topup","Untung/Rugi"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        //tbDokter.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbDokter.getBackground()));
        tbDokter.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 40; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(125);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(80);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(120);
            }else if(i==5){
                column.setPreferredWidth(120);
            }else if(i==6){
                column.setPreferredWidth(110);
            }else if(i==7){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==8){
                column.setPreferredWidth(180);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==11){
                column.setPreferredWidth(100);
            }else if(i==12){
                column.setPreferredWidth(180);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==14){
                column.setPreferredWidth(200);
            }else if(i==15){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==16){
                column.setPreferredWidth(180);
            }else if(i==17){
                column.setPreferredWidth(80);
            }else if(i==20){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==21){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==22){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==23){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==24){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==25){
                column.setPreferredWidth(120);
            }else{
                column.setPreferredWidth(150);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());
          
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));    
        
        if(koneksiDB.cariCepat().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
            });
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

        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi3 = new widget.panelisi();
        label11 = new widget.Label();
        TglSEP1 = new widget.Tanggal();
        noSep = new widget.TextBox();
        noRujukan = new widget.TextBox();
        noRM = new widget.TextBox();
        jLabel13 = new widget.Label();
        JenisPelayanan = new widget.ComboBox();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label9 = new widget.Label();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Monitoring Verifikasi Klaim SEP BPJS ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
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

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 103));
        panelisi3.setLayout(null);

        label11.setText("Tgl.SEP :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 10, 70, 23);

        TglSEP1.setDisplayFormat("dd-MM-yyyy");
        TglSEP1.setName("TglSEP1"); // NOI18N
        TglSEP1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglSEP1KeyPressed(evt);
            }
        });
        panelisi3.add(TglSEP1);
        TglSEP1.setBounds(73, 10, 110, 23);

        noSep.setEditable(false);
        noSep.setName("noSep"); // NOI18N
        noSep.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(noSep);
        noSep.setBounds(530, 10, 200, 23);

        noRujukan.setEditable(false);
        noRujukan.setName("noRujukan"); // NOI18N
        noRujukan.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(noRujukan);
        noRujukan.setBounds(530, 40, 200, 23);

        noRM.setEditable(false);
        noRM.setName("noRM"); // NOI18N
        noRM.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(noRM);
        noRM.setBounds(530, 70, 200, 23);

        jLabel13.setText("Pelayanan :");
        jLabel13.setName("jLabel13"); // NOI18N
        panelisi3.add(jLabel13);
        jLabel13.setBounds(0, 40, 70, 23);

        JenisPelayanan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "1. Rawat Inap", "2. Rawat Jalan" }));
        JenisPelayanan.setName("JenisPelayanan"); // NOI18N
        JenisPelayanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisPelayananKeyPressed(evt);
            }
        });
        panelisi3.add(JenisPelayanan);
        JenisPelayanan.setBounds(73, 40, 125, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(290, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

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
        panelisi1.add(BtnCari);

        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(45, 30));
        panelisi1.add(label9);

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

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
            dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnPrint,TglSEP1);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void TglSEP1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglSEP1KeyPressed

    }//GEN-LAST:event_TglSEP1KeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbDokter.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        noSep.setText("");
        noRujukan.setText("");
        noRM.setText("");
        JenisPelayanan.setSelectedIndex(0);
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Sequel.queryu("delete from temporary");
            int row=tabMode.getRowCount();
            for(int i=0;i<row;i++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(i,0).toString()+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,3).toString()+"','"+
                                tabMode.getValueAt(i,4).toString()+"','"+
                                tabMode.getValueAt(i,11).toString()+"','"+
                                tabMode.getValueAt(i,29).toString()+"','"+
                                tabMode.getValueAt(i,37).toString()+"','"+
                                tabMode.getValueAt(i,38).toString()+"','"+
                                tabMode.getValueAt(i,39).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Pembelian"); 
            }
            
            Map<String, Object> param = new HashMap<>();    
                param.put("namars",var.getnamars());
                param.put("alamatrs",var.getalamatrs());
                param.put("kotars",var.getkabupatenrs());
                param.put("propinsirs",var.getpropinsirs());
                param.put("kontakrs",var.getkontakrs());
                param.put("emailrs",var.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptBridgingMonitoringSEP.jrxml","report","::[ Monitoring Klaim SEP ]::",
                "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14 from temporary order by no asc",param);
            
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnAll,BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void JenisPelayananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisPelayananKeyPressed

    }//GEN-LAST:event_JenisPelayananKeyPressed

    private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
        getData();
    }//GEN-LAST:event_tbDokterKeyPressed

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbDokterMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            BPJSMonitoringKunjungan dialog = new BPJSMonitoringKunjungan(new javax.swing.JFrame(), true);
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
    private widget.ComboBox JenisPelayanan;
    private widget.TextBox TCari;
    private widget.Tanggal TglSEP1;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel13;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label9;
    private widget.TextBox noRM;
    private widget.TextBox noRujukan;
    private widget.TextBox noSep;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    public void tampil() {        
        Valid.tabelKosong(tabMode);
        if(JenisPelayanan.getSelectedItem().toString().equals("Semua")){
            Monitor(Valid.SetTgl(TglSEP1.getSelectedItem()+""),"1");
            Monitor(Valid.SetTgl(TglSEP1.getSelectedItem()+""),"2");
        }else{
            Monitor(Valid.SetTgl(TglSEP1.getSelectedItem()+""),JenisPelayanan.getSelectedItem().toString().substring(0,1));

        }
    }

    public void emptTeks() {
        TCari.requestFocus();        
    }
    
    public void isCek(){
        BtnPrint.setVisible(false);
    }
    
    private void Monitor(String tanggal,String jenispelayanan){
        try { 
            link = koneksiDB.UrlBpjs();
            URL = link+"/Monitoring/Kunjungan/Tanggal/"+tanggal+"/JnsPelayanan/"+jenispelayanan;	
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("X-Cons-ID",koneksiDB.ConsIdBpjs());
            headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
            headers.add("X-Signature",api.getHmac());
            headers.add("user_key", koneksiDB.UserKeyBpjs());
            requestEntity = new HttpEntity(headers);
            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            nameNode = root.path("metaData");
//            System.out.println("code : "+nameNode.path("code").asText());
//            System.out.println("message : "+nameNode.path("message").asText());
            if(nameNode.path("code").asText().equals("200")){
                JsonNode res1 = root.path("response");
                String res = api.decrypt(res1.asText());
                String lz = api.lzDecrypt(res);
                response = mapper.readTree(lz);
//                System.out.println(response);
                if(response.path("sep").isArray()){
                    for(JsonNode list:response.path("sep")){
                        mr = Sequel.cariIsi("SELECT no_rkm_medis FROM pasien WHERE no_peserta=?",list.path("noKartu").asText());
                        pssep=koneksi.prepareStatement("select * from bridging_sep where no_sep=?");
                        try {
                            pssep.setString(1,list.path("noSEP").asText());
                            rssep=pssep.executeQuery();
                            if (!rssep.isBeforeFirst() ) {    
                                tabMode.addRow(new Object[]{
                                    list.path("noSep").asText(),"",mr,list.path("nama").asText(),list.path("tglSep").asText(),"",list.path("noRujukan").asText(),list.path("jnsPelayanan").asText()
                                });
                            } 
                            while(rssep.next()){
                                tabMode.addRow(new Object[]{
                                    list.path("noSep").asText(),rssep.getString("no_rawat"),rssep.getString("nomr"),rssep.getString("nama_pasien"),
                                    rssep.getString("tglsep"),rssep.getString("tglrujukan"),rssep.getString("no_rujukan"),rssep.getString("kdppkrujukan"),
                                    rssep.getString("nmppkrujukan"),rssep.getString("kdppkpelayanan"),rssep.getString("nmppkpelayanan"),rssep.getString("jnspelayanan"),
                                    rssep.getString("catatan"),rssep.getString("diagawal"),rssep.getString("nmdiagnosaawal"),rssep.getString("kdpolitujuan"),
                                    rssep.getString("nmpolitujuan"),rssep.getString("klsrawat"),rssep.getString("lakalantas"),rssep.getString("keterangankkl"),
                                    rssep.getString("user"),rssep.getString("tanggal_lahir"),rssep.getString("peserta"),rssep.getString("jkel"),
                                    rssep.getString("no_kartu"),rssep.getString("tglpulang"),rssep.getString("asal_rujukan"),rssep.getString("eksekutif"),
                                    rssep.getString("cob"),rssep.getString("penjamin"),rssep.getString("notelep")
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rssep!=null){
                                rssep.close();
                            }
                            if(pssep!=null){
                                pssep.close();
                            }
                        }
                    }
                }
                tabMode.addRow(new Object[]{
                                    "TOTAL",">>>>>",">>>>>","",
                                    tabMode.getRowCount(),"","","",
                                    "","","","",
                                    "","","","",
                                    "","","","",
                                    "","","","",
                                    "","","","",
                                    "","","",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "","",
                                    ""
                                });
            }else {
                JOptionPane.showMessageDialog(rootPane,nameNode.path("message").asText());
                System.out.println(nameNode.path("message").asText());               
            }
        } catch (Exception ex) {
            System.out.println("Gagal Terhubung Ke Server BPJS : "+ex);
            if(ex.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(rootPane,"Koneksi ke server BPJS terputus...!");
                dispose();
            }
        }
    }
    
    private void getData() {
        if (tbDokter.getSelectedRow() != -1) {
            noSep.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString());
            noRujukan.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(), 6).toString());
            noRM.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(), 2).toString());
        }
    }
    
}
