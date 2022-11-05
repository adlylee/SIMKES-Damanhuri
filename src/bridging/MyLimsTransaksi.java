/*
  Dilarang keras menggandakan/mengcopy/menyebarkan/membajak/mendecompile 
  Software ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nyab,tdpai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */
package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import javax.swing.JOptionPane;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

/**
 *
 * @author dosen
 */
public final class MyLimsTransaksi extends javax.swing.JDialog {

    private final DefaultTableModel tabMode,tabMode1;
    private final Properties prop = new Properties();
    private validasi Valid = new validasi();
    private sekuel Sequel = new sekuel();
    private Connection koneksi=koneksiDB.condb();
    private BPJSCekReferensiPropinsi propinsi = new BPJSCekReferensiPropinsi(null, false);
    private int i = 0,row = 0,r = 0;
    private MyLimsApi api = new MyLimsApi();
    private String URL = "", link = "", token,requestJson,tanggal = "",jam ="" , tindakan = "";
    private PreparedStatement ps,ps2,ps3,ps4,psrekening,ps5;
    private ResultSet rs,rs2,rs3,rs5,rsrekening;
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;

    /**
     * Creates new form DlgKamar
     *
     * @param parent
     * @param modal
     */
    public MyLimsTransaksi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(10, 2);
        setSize(628, 674);

        tabMode = new DefaultTableModel(null, new String[]{"No.", "Id Pasien", "Nama Pasien","No RM","Tanggal", "No Lab","Ruang","Dokter","Keterangan"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbKamar.setModel(tabMode);

        //tbKamar.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbKamar.getBackground()));
        tbKamar.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(30);
            } else if (i == 1) {
                column.setPreferredWidth(100);
            } else if (i == 2) {
                column.setPreferredWidth(300);
            } else if (i == 3) {
                column.setPreferredWidth(50);
            } else if (i == 4) {
                column.setPreferredWidth(140);
            } else if (i == 5) {
                column.setPreferredWidth(120);
            } else if (i == 6) {
                column.setPreferredWidth(140);
            } else if (i == 7) {
                column.setPreferredWidth(180);
            } else if (i == 8) {
                column.setPreferredWidth(180);
            }
        }
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode1 = new DefaultTableModel(null, new String[]{"P","No.", "Kode", "Pemeriksaan", "Hasil","Rujukan", "Satuan", "Keterangan"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
            }
            Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbKamar1.setModel(tabMode1);

        //tbKamar.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbKamar.getBackground()));
        tbKamar1.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbKamar1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 8; i++) {
            TableColumn column = tbKamar1.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(30);
            } else if (i == 1) {
                column.setPreferredWidth(30);
            } else if (i == 2) {
                column.setPreferredWidth(70);
            } else if (i == 3) {
                column.setPreferredWidth(300);
            } else if (i == 4) {
                column.setPreferredWidth(100);
            } else if (i == 5) {
                column.setPreferredWidth(100);
            } else if (i == 6) {
                column.setPreferredWidth(100);
            } else if (i == 7) {
                column.setPreferredWidth(100);
            }
        }
        tbKamar1.setDefaultRenderer(Object.class, new WarnaTable());

        Kabupaten.setDocument(new batasInput((byte) 100).getKata(Kabupaten));



        try {
            link = koneksiDB.UrlSirs();
        } catch (Exception e) {
            System.out.println("E : " + e);
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        panelGlass6 = new widget.panelisi();
        jLabel16 = new widget.Label();
        noRawat = new widget.TextBox();
        Kabupaten = new widget.TextBox();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        jLabel17 = new widget.Label();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        jPanel1 = new javax.swing.JPanel();
        Scroll = new widget.ScrollPane();
        tbKamar = new widget.Table();
        jPanel2 = new javax.swing.JPanel();
        Scroll1 = new widget.ScrollPane();
        tbKamar1 = new widget.Table();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        jMenuItem1.setText("Simpan Hasil");
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(null);
        setIconImages(null);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pencarian Data MyLims ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 9));

        jLabel16.setText("No Rawat :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass6.add(jLabel16);

        noRawat.setName("noRawat"); // NOI18N
        noRawat.setPreferredSize(new java.awt.Dimension(180, 23));
        noRawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                noRawatKeyPressed(evt);
            }
        });
        panelGlass6.add(noRawat);

        Kabupaten.setName("Kabupaten"); // NOI18N
        Kabupaten.setPreferredSize(new java.awt.Dimension(180, 23));
        Kabupaten.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KabupatenKeyPressed(evt);
            }
        });
        panelGlass6.add(Kabupaten);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass6.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelGlass6.add(Tgl1);

        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(20, 23));
        panelGlass6.add(jLabel17);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('6');
        BtnCari.setToolTipText("Alt+6");
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
        panelGlass6.add(BtnCari);

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
        panelGlass6.add(BtnKeluar);

        internalFrame1.add(panelGlass6, java.awt.BorderLayout.PAGE_END);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(100, 250));
        jPanel1.setLayout(new java.awt.BorderLayout());

        Scroll.setBorder(javax.swing.BorderFactory.createTitledBorder("Data Pasien"));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamar.setName("tbKamar"); // NOI18N
        tbKamar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKamarMouseClicked(evt);
            }
        });
        Scroll.setViewportView(tbKamar);

        jPanel1.add(Scroll, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(100, 250));
        jPanel2.setLayout(new java.awt.BorderLayout());

        Scroll1.setBorder(javax.swing.BorderFactory.createTitledBorder("Data Detail Pemeriksaan"));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbKamar1.setComponentPopupMenu(jPopupMenu1);
        tbKamar1.setName("tbKamar1"); // NOI18N
        tbKamar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKamar1MouseClicked(evt);
            }
        });
        Scroll1.setViewportView(tbKamar1);

        jPanel2.add(Scroll1, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        propinsi.dispose();
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnKeluarActionPerformed(null);
        }
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        tampil();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        
    }//GEN-LAST:event_Tgl1KeyPressed

    private void KabupatenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KabupatenKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            tbKamar.requestFocus();
        }
    }//GEN-LAST:event_KabupatenKeyPressed

    private void tbKamarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKamarMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                if(tbKamar.getSelectedRow()!= -1){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    tampilHasil(tbKamar.getValueAt(tbKamar.getSelectedRow(),5).toString());
                    this.setCursor(Cursor.getDefaultCursor());
                }
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbKamarMouseClicked

    private void noRawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_noRawatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_noRawatKeyPressed

    private void tbKamar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKamar1MouseClicked

    }//GEN-LAST:event_tbKamar1MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        row=tabMode1.getRowCount();
        Boolean status = false;
        for(r=0;r<row;r++){ 
            try {
                if(tbKamar1.getValueAt(r,0).toString().equals("true")){
                    try {
                        ps = koneksi.prepareStatement("SELECT kd_jenis_prw,id_template FROM mapping_lab_mylims WHERE kdlab = ? AND kd_jenis_prw IN ("+tindakan+")");
                        try {
                            ps.setString(1, tbKamar1.getValueAt(r,2).toString());
                            rs = ps.executeQuery();
                            while (rs.next()) { 
                                Sequel.menyimpan("detail_periksa_lab", "'"+noRawat.getText()+"','"+rs.getString("kd_jenis_prw")+"','"+tanggal+"','"+jam+"','"+rs.getString("id_template")+"','"+tbKamar1.getValueAt(r,4).toString()+"','"+tbKamar1.getValueAt(r,5).toString()+"','"+tbKamar1.getValueAt(r,7).toString()+"','0','0','0','0','0','0','0','0'");
                                status = true;
                            }
                        } catch (Exception e) {
                            System.out.println("Error rs "+e);
                            status = false;
                        }
                    } catch (Exception e) {
                        System.out.println("Error awal "+e);
                        status = false;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error true false "+e);
                status = false;
            }    
        }
        if(status != false){
            JOptionPane.showMessageDialog(null, "Berhasil Simpan");
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            MyLimsTransaksi dialog = new MyLimsTransaksi(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKeluar;
    private widget.TextBox Kabupaten;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.Tanggal Tgl1;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.Label label11;
    private widget.TextBox noRawat;
    private widget.panelisi panelGlass6;
    private widget.Table tbKamar;
    private widget.Table tbKamar1;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        try {
            token = "NMjdBfX3WAHU44DMSZwKl8T5WEixAkRoKu97vUm4aPgNDWqDJbMzrcXS0UF1";
            headers = new HttpHeaders();
            headers.add("accept", "application/json");
            headers.add("Content-Type","application/json");
            headers.add("Authorization", "Bearer " + token);
            requestEntity = new HttpEntity(headers);
            URL = "http://192.168.0.11:777/api/v1/cekup";
            requestJson = "{"
                        + "\"method\":\"getTransaksi\","
                        + "\"from\":\"" + Valid.SetTgl(Tgl1.getSelectedItem()+"")+ "\""
                        + "}";
            System.out.println("JSON : " + requestJson);
            requestEntity = new HttpEntity(requestJson, headers);
            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
            nameNode = root.path("sukses");
//            System.out.println(nameNode.asText());
            if (nameNode.asText().equals("1")) {
                Valid.tabelKosong(tabMode);
                response = root.path("data");
//                response = root.path("response");
                if (response.isArray()) {
                    i = 1;
                    for (JsonNode list : response) {
                        if (list.path("id").asText().toLowerCase().contains(Kabupaten.getText().toLowerCase())
                                || list.path("nama").asText().toLowerCase().contains(Kabupaten.getText().toLowerCase()) 
                                || list.path("recmed").asText().toLowerCase().contains(Kabupaten.getText().toLowerCase())) {
                            tabMode.addRow(new Object[]{
                                i + ".", list.path("id").asText(), list.path("nama").asText().trim(), list.path("recmed").asText().trim(),list.path("tgl").asText().trim(), list.path("no_lab").asText().trim(), list.path("ruang").asText().trim(), list.path("dokter").asText().trim(), list.path("ket").asText().trim()
                            });
                        }
                        i++;
                    }
                }
            } else {
                System.out.println("message : " + nameNode.path("message").asText());
                JOptionPane.showMessageDialog(null, nameNode.path("message").asText());
            }
        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(rootPane, "Koneksi ke server LIMS terputus...!");
            }
        }
    }
    
    public void tampilHasil(String nolab) {
        try {
            token = "NMjdBfX3WAHU44DMSZwKl8T5WEixAkRoKu97vUm4aPgNDWqDJbMzrcXS0UF1";
            headers = new HttpHeaders();
            headers.add("accept", "application/json");
            headers.add("Content-Type","application/json");
            headers.add("Authorization", "Bearer " + token);
            requestEntity = new HttpEntity(headers);
            URL = "http://192.168.0.11:777/api/v1/cekup";
            requestJson = "{"
                        + "\"method\":\"getCekup\","
                        + "\"nolab\":\"" + nolab + "\""
                        + "}";
            System.out.println("JSON : " + requestJson);
            requestEntity = new HttpEntity(requestJson, headers);
            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
            nameNode = root.path("sukses");
//            System.out.println(nameNode.asText());
            if (nameNode.asText().equals("1")) {
                Valid.tabelKosong(tabMode1);
                response = root.path("data").path("detil");
//                response = root.path("response");
                if (response.isArray()) {
                    i = 1;
                    for (JsonNode list : response) {
                            tabMode1.addRow(new Object[]{
                                true,i + ".",list.path("kode").asText().trim(), list.path("pemeriksaan").asText().trim(), list.path("hasil").asText().trim(), list.path("rujukan").asText().trim(), list.path("satuan").asText().trim(), list.path("ket").asText().trim()
                            });
//                        }
                        i++;
                    }
                }
            } else {
                System.out.println("message : " + nameNode.path("message").asText());
                JOptionPane.showMessageDialog(null, nameNode.path("message").asText());
            }
        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(rootPane, "Koneksi ke server LIMS terputus...!");
            }
            if (ex.toString().contains("Read timed out")) {
                JOptionPane.showMessageDialog(rootPane, "Koneksi ke server LIMS terputus...!");
            }
            if (ex.toString().contains("500")) {
                JOptionPane.showMessageDialog(rootPane, "Koneksi ke server LIMS terputus...!");
            }
        }
    }

    public JTable getTable() {
        return tbKamar;
    }

    public void setPasien(String norawat,String nomr,String tanggallab,String jamlab){
        tindakan = Sequel.buangChar(Sequel.cariStringArray("SELECT kd_jenis_prw FROM periksa_lab WHERE no_rawat='"+norawat+"' AND tgl_periksa='"+tanggallab+"' AND jam='"+jamlab+"'"));
        System.out.println(tindakan);
        tanggal = "";
        jam = "";
        tanggal = tanggallab;
        jam = jamlab;
        Kabupaten.setText(nomr);
        noRawat.setText(norawat);
        Valid.tabelKosong(tabMode1);
    }
}
