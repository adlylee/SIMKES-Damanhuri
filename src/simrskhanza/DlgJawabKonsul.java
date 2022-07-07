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
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author perpustakaan
 */
public class DlgJawabKonsul extends javax.swing.JDialog {
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgCariDokter2 dokter2=new DlgCariDokter2(null,false);
    private DlgCariPoli poli=new DlgCariPoli(null,false);
    private DlgCariPoli2 poli2=new DlgCariPoli2(null,false);
    private String aktifjadwal="";
    private Properties prop = new Properties();
    private int lebar=0,tinggi=0;
    /** Creates new form DlgPemberianObat
     * @param parent
     * @param modal */
    public DlgJawabKonsul(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setSize(755,285);
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){   
                    Tkddokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    TDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    Tkddokter.requestFocus();
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
                
        dokter2.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter2.getTable().getSelectedRow()!= -1){   
                    Tkddokter.setText(dokter2.getTable().getValueAt(dokter2.getTable().getSelectedRow(),0).toString());
                    TDokter.setText(dokter2.getTable().getValueAt(dokter2.getTable().getSelectedRow(),1).toString());
                    Tkddokter.requestFocus();
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
                
        poli2.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
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
        
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            aktifjadwal=prop.getProperty("JADWALDOKTERDIREGISTRASI");
        } catch (Exception ex) {
            aktifjadwal="";            
        }
    }

    //private DlgCariObatPenyakit dlgobtpny=new DlgCariObatPenyakit(null,false);
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        FormInput = new widget.PanelBiasa();
        jLabel13 = new widget.Label();
        Tkddokter = new widget.TextBox();
        TDokter = new widget.TextBox();
        TPoli = new widget.TextBox();
        jLabel19 = new widget.Label();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel15 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        TCatatanKonsul = new widget.TextArea();
        jLabel16 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        TPemeriksaan = new widget.TextArea();
        jLabel17 = new widget.Label();
        TDiagnosa = new widget.TextBox();
        jLabel18 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        TSaran = new widget.TextArea();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        jLabel14 = new widget.Label();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Jawaban Konsul ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(865, 137));
        FormInput.setLayout(null);

        jLabel13.setText("Pemeriksaan :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 160, 90, 23);

        Tkddokter.setHighlighter(null);
        Tkddokter.setName("Tkddokter"); // NOI18N
        Tkddokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TkddokterKeyPressed(evt);
            }
        });
        FormInput.add(Tkddokter);
        Tkddokter.setBounds(97, 40, 100, 23);

        TDokter.setEditable(false);
        TDokter.setName("TDokter"); // NOI18N
        FormInput.add(TDokter);
        TDokter.setBounds(200, 40, 220, 23);

        TPoli.setEditable(false);
        TPoli.setName("TPoli"); // NOI18N
        FormInput.add(TPoli);
        TPoli.setBounds(448, 40, 280, 23);

        jLabel19.setText("Poli :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(370, 40, 77, 23);

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

        jLabel15.setText("dari Dokter :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(20, 40, 70, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        TCatatanKonsul.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TCatatanKonsul.setColumns(20);
        TCatatanKonsul.setRows(5);
        TCatatanKonsul.setName("TCatatanKonsul"); // NOI18N
        TCatatanKonsul.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCatatanKonsulKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(TCatatanKonsul);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(98, 75, 630, 80);

        jLabel16.setText("Catatan Konsul :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 70, 90, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        TPemeriksaan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TPemeriksaan.setColumns(20);
        TPemeriksaan.setRows(5);
        TPemeriksaan.setName("TPemeriksaan"); // NOI18N
        TPemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPemeriksaanKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(TPemeriksaan);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(98, 160, 630, 80);

        jLabel17.setText("Diagnosa :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(20, 245, 70, 23);

        TDiagnosa.setName("TDiagnosa"); // NOI18N
        FormInput.add(TDiagnosa);
        TDiagnosa.setBounds(100, 245, 450, 23);

        jLabel18.setText("Saran :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(0, 275, 90, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        TSaran.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TSaran.setColumns(20);
        TSaran.setRows(5);
        TSaran.setName("TSaran"); // NOI18N
        TSaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TSaranKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(TSaran);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(100, 275, 630, 80);

        internalFrame1.add(FormInput, java.awt.BorderLayout.CENTER);
        FormInput.getAccessibleContext().setAccessibleName("");
        FormInput.getAccessibleContext().setAccessibleDescription("");

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(100, 56));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);

        jLabel14.setName("jLabel14"); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(520, 23));
        panelGlass8.add(jLabel14);

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
    
    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TNoRM.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRM,"Pasien");
        }else{
            try {
                String detail_internal_poli;
                detail_internal_poli = "UPDATE rujukan_internal_poli_detail SET pemeriksaan= '"+TPemeriksaan.getText()+"', diagnosa = '"+TDiagnosa.getText()+"', saran = '"+TSaran.getText()+"' WHERE no_rawat ='"+TNoRw.getText()+"'"; 
                PreparedStatement pst_detail = koneksiDB.condb().prepareStatement(detail_internal_poli);          
                pst_detail.execute();
                JOptionPane.showMessageDialog(null,"Jawaban konsul berhasil disimpan..!!");
            } catch (Exception e) {
                System.out.println(e);
            }  
            dispose();
        }  
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        Tkddokter.setText("");
        TDokter.setText("");
        //Tkdpoli.setText("");
        TPoli.setText("");
        TCatatanKonsul.setText("");
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            Tkddokter.setText("");
            TDokter.setText("");
           // Tkdpoli.setText("");
            TPoli.setText("");
            dispose();
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        
    }//GEN-LAST:event_formWindowActivated

    private void TkddokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TkddokterKeyPressed
        
    }//GEN-LAST:event_TkddokterKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        
    }//GEN-LAST:event_TNoRwKeyPressed

    private void TCatatanKonsulKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCatatanKonsulKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TCatatanKonsulKeyPressed

    private void TPemeriksaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPemeriksaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TPemeriksaanKeyPressed

    private void TSaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TSaranKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TSaranKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgJawabKonsul dialog = new DlgJawabKonsul(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.PanelBiasa FormInput;
    private widget.TextArea TCatatanKonsul;
    private widget.TextBox TDiagnosa;
    private widget.TextBox TDokter;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextArea TPemeriksaan;
    private widget.TextBox TPoli;
    private widget.TextArea TSaran;
    private widget.TextBox Tkddokter;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel3;
    private widget.panelisi panelGlass8;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    // End of variables declaration//GEN-END:variables
    

 

    public void setDataPasien(String norw,String norm,String namapasien,String kddokter,String dokter,String poli, String catatankonsul) {
        TNoRw.setText(norw);
        TNoRM.setText(norm);
        TPasien.setText(namapasien);  
        Tkddokter.setText(kddokter);
        TDokter.setText(dokter);
        //Tkdpoli.setText(kdpoli);
        TPoli.setText(poli);
        TCatatanKonsul.setText(catatankonsul);
   }
    
    
    public void isCek(){
        BtnSimpan.setEnabled(true);
    }



}
