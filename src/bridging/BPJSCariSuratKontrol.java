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
package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.net.URI;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author dosen
 */
public final class BPJSCariSuratKontrol extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private validasi Valid = new validasi();
    private int i = 0;
    private BPJSApi api = new BPJSApi();
    private String URL = "", link = "", utc = "", requestJson = "", user = "", page,tahun;
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private sekuel Sequel = new sekuel();

    /**
     * Creates new form DlgKamar
     *
     * @param parent
     * @param modal
     */
    public BPJSCariSuratKontrol(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(10, 2);
        setSize(628, 674);

        tabMode = new DefaultTableModel(null, new String[]{"No.", "Jenis Kontrol", "No Surat", "Jenis Pelayanan", "Tanggal Kontrol", "No Sep", "Terbit Sep", "Kode Dokter", "Nama Dokter"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbKamar.setModel(tabMode);

        //tbKamar.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbKamar.getBackground()));
        tbKamar.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 7; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(35);
            } else if (i == 1) {
                column.setPreferredWidth(100);
            } else if (i == 2) {
                column.setPreferredWidth(200);
            } else if (i == 3) {
                column.setPreferredWidth(150);
            } else if (i == 4) {
                column.setPreferredWidth(100);
            } else {
                column.setPreferredWidth(180);
            }
        }
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());

        Dokter.setDocument(new batasInput((byte) 100).getKata(Dokter));

        if (koneksiDB.cariCepat().equals("aktif")) {
            Dokter.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (Dokter.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (Dokter.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (Dokter.getText().length() > 2) {
                        tampil();
                    }
                }
            });
        }

        try {
            user = var.getkode().replace(" ", "").substring(0, 9);
        } catch (Exception e) {
            user = var.getkode();
        }

        try {
            link = koneksiDB.UrlBpjs();
            URL = link + "/RencanaKontrol/";
        } catch (Exception e) {
            System.out.println("E : " + e);
        }
        cmbTahun.setSelectedIndex(4);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Poli = new widget.TextBox();
        TanggalKontrol = new widget.TextBox();
        JenisKontrol = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbKamar = new widget.Table();
        panelGlass6 = new widget.panelisi();
        jLabel16 = new widget.Label();
        Dokter = new widget.TextBox();
        jLabel37 = new widget.Label();
        Status = new widget.ComboBox();
        jLabel38 = new widget.Label();
        cmbTahun = new widget.ComboBox();
        BtnCari = new widget.Button();
        jLabel17 = new widget.Label();
        BtnKeluar = new widget.Button();

        Poli.setEditable(false);
        Poli.setName("Poli"); // NOI18N
        Poli.setPreferredSize(new java.awt.Dimension(170, 23));

        TanggalKontrol.setEditable(false);
        TanggalKontrol.setName("TanggalKontrol"); // NOI18N
        TanggalKontrol.setPreferredSize(new java.awt.Dimension(100, 23));

        JenisKontrol.setEditable(false);
        JenisKontrol.setName("JenisKontrol"); // NOI18N
        JenisKontrol.setPreferredSize(new java.awt.Dimension(130, 23));

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        jMenuItem1.setText("Hapus Surat Kontrol");
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Surat Kontrol / SPRI ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamar.setAutoCreateRowSorter(true);
        tbKamar.setComponentPopupMenu(jPopupMenu1);
        tbKamar.setName("tbKamar"); // NOI18N
        Scroll.setViewportView(tbKamar);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel16.setText("No Kartu :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass6.add(jLabel16);

        Dokter.setName("Dokter"); // NOI18N
        Dokter.setPreferredSize(new java.awt.Dimension(250, 23));
        Dokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DokterKeyPressed(evt);
            }
        });
        panelGlass6.add(Dokter);

        jLabel37.setText("Bulan :");
        jLabel37.setName("jLabel37"); // NOI18N
        panelGlass6.add(jLabel37);

        Status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        Status.setName("Status"); // NOI18N
        Status.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusKeyPressed(evt);
            }
        });
        panelGlass6.add(Status);

        jLabel38.setText("Tahun :");
        jLabel38.setName("jLabel38"); // NOI18N
        panelGlass6.add(jLabel38);

        cmbTahun.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2020", "2021", "2022", "2023", "2024", "2025" }));
        cmbTahun.setName("cmbTahun"); // NOI18N
        cmbTahun.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbTahunKeyPressed(evt);
            }
        });
        panelGlass6.add(cmbTahun);

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

        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(25, 23));
        panelGlass6.add(jLabel17);

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
            Valid.pindah(evt, Poli, BtnKeluar);
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
        } else {
            Valid.pindah(evt, Poli, BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void DokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DokterKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            tampil();
            Dokter.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            tampil();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnCariActionPerformed(null);
        }
    }//GEN-LAST:event_DokterKeyPressed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        if (tbKamar.getSelectedRow() != -1) {
            try {
                bodyWithDeleteRequest();
            } catch (Exception e) {

            }
        } else {
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data SUrat Kontrol ...!!!!");
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void StatusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusKeyPressed

    }//GEN-LAST:event_StatusKeyPressed

    private void cmbTahunKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbTahunKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbTahunKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            BPJSCariSuratKontrol dialog = new BPJSCariSuratKontrol(new javax.swing.JFrame(), true);
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
    private widget.TextBox Dokter;
    private widget.TextBox JenisKontrol;
    private widget.TextBox Poli;
    private widget.ScrollPane Scroll;
    private widget.ComboBox Status;
    private widget.TextBox TanggalKontrol;
    private widget.ComboBox cmbTahun;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass6;
    private widget.Table tbKamar;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        try {
            page = Status.getSelectedItem().toString();
            tahun = cmbTahun.getSelectedItem().toString();
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("X-Cons-ID", koneksiDB.ConsIdBpjs());
            headers.add("X-Timestamp", String.valueOf(api.GetUTCdatetimeAsString()));
            headers.add("X-Signature", api.getHmac());
            headers.add("user_key", koneksiDB.UserKeyBpjs());
            requestEntity = new HttpEntity(headers);
            if (Dokter.getText().length() == 13) {
                root = mapper.readTree(api.getRest().exchange(URL + "ListRencanaKontrol/Bulan/" + page + "/Tahun/"+tahun+"/Nokartu/" + Dokter.getText() + "/filter/1", HttpMethod.GET, requestEntity, String.class).getBody());
                nameNode = root.path("metaData");
                System.out.println("code : " + nameNode.path("code").asText());
                System.out.println("message : " + nameNode.path("message").asText());
                if (nameNode.path("code").asText().equals("200")) {
                    Valid.tabelKosong(tabMode);
                    JsonNode res1 = root.path("response");
                    String res = api.decrypt(res1.asText());
                    String lz = api.lzDecrypt(res);
                    response = mapper.readTree(lz);
//                System.out.println(response);
                    if (response.path("list").isArray()) {
                        i = 1;
                        for (JsonNode list : response.path("list")) {
//                        if (list.path("noSuratKontrol").asText().toLowerCase().contains(Dokter.getText().toLowerCase())
//                                || list.path("noSuratKontrol").asText().toLowerCase().contains(Dokter.getText().toLowerCase())) {
                            tabMode.addRow(new Object[]{
                                i + ".", list.path("namaJnsKontrol").asText(), list.path("noSuratKontrol").asText(), list.path("jnsPelayanan").asText(), list.path("tglRencanaKontrol").asText(), list.path("noSepAsalKontrol").asText(), list.path("terbitSEP").asText(), list.path("kodeDokter").asText(), list.path("namaDokter").asText()
                            });
                            i++;
//                        }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, nameNode.path("message").asText());
                }
            } else {
                root = mapper.readTree(api.getRest().exchange(URL + "noSuratKontrol/" + Dokter.getText(), HttpMethod.GET, requestEntity, String.class).getBody());
                nameNode = root.path("metaData");
                System.out.println("code : " + nameNode.path("code").asText());
                System.out.println("message : " + nameNode.path("message").asText());
                if (nameNode.path("code").asText().equals("200")) {
                    Valid.tabelKosong(tabMode);
                    JsonNode res1 = root.path("response");
                    String res = api.decrypt(res1.asText());
                    String lz = api.lzDecrypt(res);
                    response = mapper.readTree(lz);
//                System.out.println(response);
                    tabMode.addRow(new Object[]{
                                i + ".", response.path("namaJnsKontrol").asText(), response.path("noSuratKontrol").asText(), response.path("sep").path("jnsPelayanan").asText(), response.path("tglRencanaKontrol").asText(), response.path("sep").path("noSep").asText(), response.path("sep").path("tglSep").asText(), response.path("kodeDokter").asText(), response.path("namaDokter").asText()
                            });
                    tabMode.addRow(new Object[]{
                                i + ".", response.path("namaJnsKontrol").asText(), response.path("noSuratKontrol").asText(), response.path("sep").path("jnsPelayanan").asText(), "", "", "Pembuat Surat Kontrol", response.path("kodeDokterPembuat").asText(), response.path("namaDokterPembuat").asText()
                            });
                } else {
                    JOptionPane.showMessageDialog(null, nameNode.path("message").asText());
                }
            }

        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(rootPane, "Koneksi ke server BPJS terputus...!");
            }
        }
    }

    public JTable getTable() {
        return tbKamar;
    }

    public void SetNoKarut(String noka) {
        Dokter.setText(noka);
    }

    @Test
    public void bodyWithDeleteRequest() throws Exception {
        String notif = "";
        RestTemplate restTemplate = new RestTemplate();
        SSLContext sslContext = SSLContext.getInstance("SSL");
        javax.net.ssl.TrustManager[] trustManagers = {
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                }

                public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                }
            }
        };
        sslContext.init(null, trustManagers, new SecureRandom());
        SSLSocketFactory sslFactory = new SSLSocketFactory(sslContext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Scheme scheme = new Scheme("https", 443, sslFactory);

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory() {
            @Override
            protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
                if (HttpMethod.DELETE == httpMethod) {
                    return new BPJSSuratKontrol.HttpEntityEnclosingDeleteRequest(uri);
                }
                return super.createHttpUriRequest(httpMethod, uri);
            }
        };
        factory.getHttpClient().getConnectionManager().getSchemeRegistry().register(scheme);
        restTemplate.setRequestFactory(factory);

        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("X-Cons-ID", koneksiDB.ConsIdBpjs());
            headers.add("X-Timestamp", String.valueOf(api.GetUTCdatetimeAsString()));
            headers.add("X-Signature", api.getHmac());
            headers.add("user_key", koneksiDB.UserKeyBpjs());
            URL = link + "/RencanaKontrol/Delete";
            requestJson = "{\"request\":{\"t_suratkontrol\":{\"noSuratKontrol\":\"" + tbKamar.getValueAt(tbKamar.getSelectedRow(), 2).toString() + "\",\"user\":\"" + user + "\"}}}";
            System.out.println(requestJson);
            requestEntity = new HttpEntity(requestJson, headers);
            root = mapper.readTree(restTemplate.exchange(URL, HttpMethod.DELETE, requestEntity, String.class).getBody());
            nameNode = root.path("metaData");
            System.out.println("code : " + nameNode.path("code").asText());
            System.out.println("message : " + nameNode.path("message").asText());
            if (nameNode.path("code").asText().equals("200")) {
                System.out.println("Berhasil Hapus");
                JOptionPane.showMessageDialog(null, "Berhasil Hapus");
                tampil();
            } else {
                JOptionPane.showMessageDialog(null, nameNode.path("message").asText());
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
            if (e.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(null, "Koneksi ke server BPJS terputus...!");
            }
        }
    }
}
