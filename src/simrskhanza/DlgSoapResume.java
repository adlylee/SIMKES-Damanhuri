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

import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariDokter2;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import widget.Tanggal;

/**
 *
 * @author perpustakaan
 */
public class DlgSoapResume extends javax.swing.JDialog {

    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps, ps2;
    private ResultSet rs, rs2;
    private DlgCariDokter dokter = new DlgCariDokter(null, false);
    private DlgCariDokter2 dokter2 = new DlgCariDokter2(null, false);
    private DlgCariPoli poli = new DlgCariPoli(null, false);
    private DlgCariPoli2 poli2 = new DlgCariPoli2(null, false);
    private String aktifjadwal = "", kamar = "", namakamar = "", instruksiPagi = "", instruksiSiang = "", instruksiMalam = "";
    private Properties prop = new Properties();
    private int lebar = 0, tinggi = 0;
//    LocalDate today = LocalDate.now();
//    StringBuilder instruksiPagi = new StringBuilder();
//    StringBuilder instruksiSiang = new StringBuilder();
//    StringBuilder instruksiMalam = new StringBuilder();

    /**
     * Creates new form DlgPemberianObat
     *
     * @param parent
     * @param modal
     */
    public DlgSoapResume(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setSize(755, 285);

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
//                    Tkddokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
//                    TDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
//                    Tkddokter.requestFocus();
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

        dokter2.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (dokter2.getTable().getSelectedRow() != -1) {
//                    Tkddokter.setText(dokter2.getTable().getValueAt(dokter2.getTable().getSelectedRow(),0).toString());
//                    TDokter.setText(dokter2.getTable().getValueAt(dokter2.getTable().getSelectedRow(),1).toString());
//                    Tkddokter.requestFocus();
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

        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {

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

        poli2.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
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
            aktifjadwal = prop.getProperty("JADWALDOKTERDIREGISTRASI");
        } catch (Exception ex) {
            aktifjadwal = "";
        }        
    }

    //private DlgCariObatPenyakit dlgobtpny=new DlgCariObatPenyakit(null,false);
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        FormInput = new widget.PanelBiasa();
        jLabel13 = new widget.Label();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        scrollPane1 = new widget.ScrollPane();
        TCatatanKonsul = new widget.TextArea();
        jLabel16 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        TPemeriksaan = new widget.TextArea();
        jLabel18 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        TSaran = new widget.TextArea();
        panelGlass8 = new widget.panelisi();
        jLabel20 = new widget.Label();
        TanggalCari1 = new widget.Tanggal();
        BtnCari = new widget.Button();
        jLabel14 = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Laporan SOAP ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(865, 137));
        FormInput.setLayout(null);

        jLabel13.setText("Instruksi :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 145, 90, 23);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(20, 10, 70, 23);

        TNoRw.setEditable(false);
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(97, 10, 153, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(253, 10, 110, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(369, 10, 360, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        TCatatanKonsul.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TCatatanKonsul.setColumns(20);
        TCatatanKonsul.setRows(5);
        TCatatanKonsul.setName("TCatatanKonsul"); // NOI18N
        scrollPane1.setViewportView(TCatatanKonsul);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(98, 50, 270, 80);

        jLabel16.setText("DPJP :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 55, 90, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        TPemeriksaan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TPemeriksaan.setColumns(20);
        TPemeriksaan.setRows(5);
        TPemeriksaan.setName("TPemeriksaan"); // NOI18N
        scrollPane2.setViewportView(TPemeriksaan);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(98, 145, 630, 200);

        jLabel18.setText("Diagnosa :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(0, 365, 90, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        TSaran.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TSaran.setColumns(20);
        TSaran.setRows(5);
        TSaran.setName("TSaran"); // NOI18N
        scrollPane3.setViewportView(TSaran);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(100, 365, 630, 130);

        internalFrame1.add(FormInput, java.awt.BorderLayout.CENTER);
        FormInput.getAccessibleContext().setAccessibleName("");
        FormInput.getAccessibleContext().setAccessibleDescription("");

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(100, 56));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel20.setText("Tanggal :");
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass8.add(jLabel20);

        TanggalCari1.setForeground(new java.awt.Color(50, 70, 50));
        TanggalCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "09-09-2023" }));
        TanggalCari1.setDisplayFormat("dd-MM-yyyy");
        TanggalCari1.setName("TanggalCari1"); // NOI18N
        TanggalCari1.setOpaque(false);
        TanggalCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass8.add(TanggalCari1);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
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

        jLabel14.setName("jLabel14"); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(320, 23));
        panelGlass8.add(jLabel14);

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

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Tutup");
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

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
//        Tkddokter.setText("");
//        TDokter.setText("");
        //Tkdpoli.setText("");
//        TPoli.setText("");
        TCatatanKonsul.setText("");
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated

    }//GEN-LAST:event_formWindowActivated

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed

    }//GEN-LAST:event_TNoRwKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        LocalDate today = LocalDate.parse(Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Map<String, Object> param = new HashMap<>();
        param.put("norawat", TNoRw.getText());
        param.put("norm", TNoRM.getText());
        param.put("namapasien", Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ", TNoRM.getText()));
        param.put("jkel", Sequel.cariIsi("select if(jk='L','Laki-laki','Perempuan') as jk from pasien where no_rkm_medis=? ", TNoRM.getText()));
        param.put("umur", Sequel.cariIsi("select umur from pasien where no_rkm_medis=?", TNoRM.getText()));
        param.put("tanggal", Valid.SetTgl(TanggalCari1.getSelectedItem()+""));
        param.put("kamar", kamar);
        param.put("dpjp", TCatatanKonsul.getText());
        param.put("instruksi", TPemeriksaan.getText());
        param.put("diagnosa", TSaran.getText());
        param.put("jns_bayar", Sequel.cariIsi("select png_jawab from reg_periksa join penjab on reg_periksa.kd_pj=penjab.kd_pj where no_rawat=?", TNoRw.getText()));
        instruksiPagi = getInstruksi(TNoRw.getText(), today.toString(), "Pagi");
        instruksiPagi += getObat(TNoRw.getText(), today.toString(), "Pagi");
        instruksiSiang = getInstruksi(TNoRw.getText(), today.toString(), "Siang");
        instruksiSiang += getObat(TNoRw.getText(), today.toString(), "Siang");
        instruksiMalam = getInstruksi(TNoRw.getText(), today.toString(), "Malam");
        instruksiMalam += getObat(TNoRw.getText(), today.toString(), "Malam");
        param.put("instruksiPagi", instruksiPagi);
        param.put("instruksiSiang", instruksiSiang);
        param.put("instruksiMalam", instruksiMalam);
        param.put("namars", var.getnamars());
        param.put("alamatrs", var.getalamatrs());
        param.put("kotars", var.getkabupatenrs());
        param.put("propinsirs", var.getpropinsirs());
        param.put("kontakrs", var.getkontakrs());
        param.put("emailrs", var.getemailrs());
        param.put("logo", Sequel.cariGambar("select logo from setting"));
        Valid.MyReport("rptLaporanSOAP.jrxml", "report", "::[ Laporan SOAP ]::", "select * from pasien where pasien.no_rkm_medis='" + TNoRM.getText() + "'", param);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            //Valid.pindah(evt,BtnEdit,BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        setDataPasien(TNoRw.getText(), TNoRM.getText(), TPasien.getText(),kamar);
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
//            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgSoapResume dialog = new DlgSoapResume(new javax.swing.JFrame(), true);
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
    private widget.Button BtnPrint;
    private widget.PanelBiasa FormInput;
    private widget.TextArea TCatatanKonsul;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextArea TPemeriksaan;
    private widget.TextArea TSaran;
    private widget.Tanggal TanggalCari1;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel16;
    private widget.Label jLabel18;
    private widget.Label jLabel20;
    private widget.Label jLabel3;
    private widget.panelisi panelGlass8;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    // End of variables declaration//GEN-END:variables

    public void setDataPasien(String norw, String norm, String namapasien, String kamar) {
        LocalDate today = LocalDate.parse(Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
        LocalDate lastday = today.minus(1, ChronoUnit.DAYS);
        TNoRw.setText(norw);
        TNoRM.setText(norm);
        TPasien.setText(namapasien + " (" + Sequel.cariIsi("SELECT CONCAT(umurdaftar,' ' ,sttsumur) FROM reg_periksa WHERE no_rawat=?", norw) + ")");
        this.kamar=kamar;
        String asu = Sequel.cariStringArrayLine("SELECT GROUP_CONCAT(CONCAT(nm_dokter,' (',jenis_dpjp,')') SEPARATOR '\\n') FROM dpjp_ranap JOIN dokter ON dpjp_ranap.kd_dokter = dokter.kd_dokter where no_rawat='" + norw + "'");
//        String lab = Sequel.cariStringArrayLine("SELECT IFNULL(GROUP_CONCAT(jns_perawatan_lab.nm_perawatan),'') from periksa_lab inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.no_rawat='"+norw+"' and periksa_lab.tgl_periksa='"+lastday+"'");
        String lab = Sequel.cariIsi("SELECT IFNULL(GROUP_CONCAT(CONCAT(detail_periksa_lab.jam,' ', template_laboratorium.Pemeriksaan,' ', detail_periksa_lab.nilai,template_laboratorium.satuan,' ',detail_periksa_lab.nilai_rujukan,' (',detail_periksa_lab.keterangan,')') SEPARATOR '\n'),'') as datalab "
                + "FROM detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template where detail_periksa_lab.no_rawat='" + norw + "' "
                + "and detail_periksa_lab.tgl_periksa='" + lastday + "' and detail_periksa_lab.keterangan <> '' ORDER BY detail_periksa_lab.jam desc");
        String radiologi = Sequel.cariStringArrayLine("SELECT IFNULL(GROUP_CONCAT(jns_perawatan_radiologi.nm_perawatan),'') from periksa_radiologi inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw where periksa_radiologi.no_rawat='" + norw + "' and periksa_radiologi.tgl_periksa='" + lastday + "'");
        String diet = Sequel.cariIsi("select GROUP_CONCAT(CONCAT(detail_beri_diet.waktu,' ', diet.nama_diet) SEPARATOR'\n') from detail_beri_diet inner join diet on detail_beri_diet.kd_diet=diet.kd_diet where no_rawat='" + norw + "' and tanggal='" + lastday + "'");
        TCatatanKonsul.setText(asu);
        TPemeriksaan.setText("(Lab)" + "\n" + lab + "\n\n");
        TPemeriksaan.append("(Radiologi)" + "\n" + radiologi + "\n\n");
//        TPemeriksaan.append(Sequel.cariIsi("SELECT GROUP_CONCAT(instruksi SEPARATOR'\n\n') FROM pemeriksaan_ranap WHERE no_rawat=? AND tgl_perawatan = '" + lastday + "' ORDER BY jam_rawat", norw));
//        TPemeriksaan.append("\n\n");
//        String asu2 = Sequel.cariStringArrayLine("SELECT databarang.nama_brng FROM resep_obat JOIN resep_dokter ON resep_obat.no_resep = resep_dokter.no_resep JOIN databarang ON resep_dokter.kode_brng = databarang.kode_brng where resep_obat.no_rawat='"+norw+"' AND resep_obat.tgl_perawatan = '"+lastday+"'");
        TPemeriksaan.append("(Diet)" + "\n" + diet);
        TPemeriksaan.append("\n\n");
//        TPemeriksaan.append(getInstruksi(norw, lastday.toString()));
        TPemeriksaan.append(getInstruksi(norw, lastday.toString(), "Pagi"));
        TPemeriksaan.append(getInstruksi(norw, lastday.toString(), "Siang"));
        TPemeriksaan.append(getInstruksi(norw, lastday.toString(), "Malam"));
        TPemeriksaan.append(getObat(norw, lastday.toString(), "Pagi"));
        TPemeriksaan.append(getObat(norw, lastday.toString(), "Siang"));
        TPemeriksaan.append(getObat(norw, lastday.toString(), "Malam"));
//        TPemeriksaan.append(asu2);
        TSaran.setText(Sequel.cariStringArrayLine("SELECT nm_penyakit FROM diagnosa_pasien JOIN penyakit ON penyakit.kd_penyakit = diagnosa_pasien.kd_penyakit WHERE no_rawat='" + norw + "'"));
    }

    public void isCek() {

    }

    public static String getShift(LocalTime time) {
        if (time.isAfter(LocalTime.of(6, 0)) && time.isBefore(LocalTime.of(12, 0))) {
            return "Pagi";
        } else if (time.isAfter(LocalTime.of(13, 0)) && time.isBefore(LocalTime.of(18, 0))) {
            return "Siang";
        } else {
            return "Malam";
        }
    }

    public String getObat(String norw, String theday, String shift) {
        StringBuilder obat = new StringBuilder();
        try {
            ps = koneksi.prepareStatement("SELECT resep_obat.jam, resep_obat.kd_dokter, dokter.nm_dokter from resep_obat, dokter where resep_obat.kd_dokter=dokter.kd_dokter and resep_obat.no_rawat='" + norw + "' and resep_obat.tgl_perawatan='" + theday + "' order by resep_obat.tgl_perawatan desc,resep_obat.jam asc");
            rs = ps.executeQuery();
            while (rs.next()) {
                LocalTime jamResep = LocalTime.parse(rs.getString("jam"));
                String resepShift = getShift(jamResep);
                if (shift.equals(resepShift)) {
                    obat.append("\n").append("Terapi ").append(rs.getString("nm_dokter")).append("\n");
                    ps2 = koneksi.prepareStatement("SELECT GROUP_CONCAT(CONCAT(resep_dokter.jml, ' ',databarang.nama_brng, ' ', resep_dokter.aturan_pakai) SEPARATOR '\n') AS nama_brng FROM resep_obat "
                            + "JOIN resep_dokter ON resep_obat.no_resep = resep_dokter.no_resep INNER JOIN databarang ON resep_dokter.kode_brng = databarang.kode_brng WHERE "
                            + "resep_obat.no_rawat = '" + norw + "' AND resep_obat.status = 'Ranap' AND resep_obat.tgl_perawatan = '" + theday + "' AND resep_obat.kd_dokter = '" + rs.getString("kd_dokter") + "'");
                    try {
                        rs2 = ps2.executeQuery();
                        while (rs2.next()) {
                            obat.append(rs2.getString("nama_brng")).append("\n");
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi 2 : " + e);
                    } finally {
                        if (rs2 != null) {
                            rs2.close();
                        }
                        if (ps2 != null) {
                            ps2.close();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        } finally {
//            if (rs != null) {
//                rs.close();
//            }
//            if (ps != null) {
//                ps.close();
//            }
        }
        return obat.toString();
    }

    public String getInstruksi(String norw, String theday, String shift) {
        StringBuilder instruksi = new StringBuilder();
        try {
            String sql = "SELECT jam_rawat, instruksi, suhu_tubuh, tensi, nadi, respirasi, spo2, gcs FROM pemeriksaan_ranap WHERE no_rawat = ? AND tgl_perawatan = ?";
            ps = koneksi.prepareStatement(sql);
            ps.setString(1, norw);
            ps.setString(2, theday);
            rs = ps.executeQuery();

            while (rs.next()) {
                String jamRawatStr = rs.getString("jam_rawat");
                LocalTime jamRawat = LocalTime.parse(jamRawatStr);
                String shiftLabel = "(" + "Instruksi " + shift + ")";
                if (shift.equals(getShift(jamRawat))) {
                    String instruksiStr = shiftLabel + "\n"
                            + "Suhu: " + rs.getString("suhu_tubuh") + "\n"
                            + "Tensi: " + rs.getString("tensi") + "\n"
                            + "Nadi: " + rs.getString("nadi") + "\n"
                            + "Respirasi: " + rs.getString("respirasi") + "\n"
                            + "SPO2: " + rs.getString("spo2") + "\n"
                            + "GCS: " + rs.getString("gcs") + "\n\n"
                            + rs.getString("instruksi") + "\n\n";
                    instruksi.append(instruksiStr);
                }
            }
        } catch (Exception e) {
            System.out.println("Notif: " + e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            }
        }
        return instruksi.toString();
    }
}
