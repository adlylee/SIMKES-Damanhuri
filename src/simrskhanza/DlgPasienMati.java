/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgPasienMati.java
 *
 * Created on Aug 30, 2010, 7:46:01 AM
 */

package simrskhanza;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import laporan.DlgDiagnosaPenyakit;

/**
 *
 * @author dosen3
 */
public class DlgPasienMati extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private DlgPasien pasien=new DlgPasien(null,false);
    private PreparedStatement ps;
    private ResultSet rs;
    private String sql=" pasien_mati.no_rkm_medis=pasien.no_rkm_medis  ", queryku=" pasien.jk IN ('L','P') ", lamainap="", hitunglama="", ruangan="",pilihan="";
    private DlgDiagnosaPenyakit resep = new DlgDiagnosaPenyakit(null, false);
    private DlgCariDokter dokter = new DlgCariDokter(null, false);
    /** Creates new form DlgPasienMati
     * @param parent
     * @param modal */
    public DlgPasienMati(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(10,2);
        setSize(628,674);

        Object[] row={"Tanggal","Jam","No.R.Medik","Nama Pasien","NIK","J.K.","Tmp.Lahir",
                      "Tgl.Lahir","Umur","Alamat","G.D.","Stts.Nikah","Agama","Keterangan","Tempat Meninggal",
                      "ICD-X","Antara 1","Antara 2","Langsung","Pemulasaran", "Tgl. Pemulasaran", "Stts. Penduduk","Keadaan"};

        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbMati.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbMati.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbMati.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 23; i++) {
            TableColumn column = tbMati.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(75);
            }else if(i==1){
                column.setPreferredWidth(60);
            }else if(i==2){
                column.setPreferredWidth(80);
            }else if(i==3){
                column.setPreferredWidth(90);
            }else if(i==4){
                column.setPreferredWidth(110);
            }else if(i==5){
                column.setPreferredWidth(30);
            }else if(i==6){
                column.setPreferredWidth(120);
            }else if(i==7){
                column.setPreferredWidth(75);
            }else if(i==8){
                column.setPreferredWidth(57);
            }else if(i==9){
                column.setPreferredWidth(120);
            }else if(i==10){
                column.setPreferredWidth(30);
            }else if(i==11){
                column.setPreferredWidth(70);
            }else if(i==12){
                column.setPreferredWidth(70);
            }else if(i==13){
                column.setPreferredWidth(120);
            }else if(i==14){
                column.setPreferredWidth(120);
            }else if(i==15){
                column.setPreferredWidth(65);
            }else if(i==16){
                column.setPreferredWidth(65);
            }else if(i==17){
                column.setPreferredWidth(65);
            }else if(i==18){
                column.setPreferredWidth(65);
            }else if(i==19){
                column.setPreferredWidth(75);
            }else if(i==20){
                column.setPreferredWidth(65);
            }else if(i==21){
                column.setPreferredWidth(85);
            }else if(i==22){
                column.setPreferredWidth(65);
            }
        }
        tbMati.setDefaultRenderer(Object.class, new WarnaTable());


        TNoRM.setDocument(new batasInput((byte)15).getKata(TNoRM));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        TKtg.setDocument(new batasInput((byte)100).getKata(TKtg));
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
         pasien.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgPasienMati")){
                    if(pasien.getTable().getSelectedRow()!= -1){                   
                        TNoRM.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),1).toString());
                        TPasien.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),2).toString());
                    }   
                    TNoRM.requestFocus();
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
        
        pasien.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(var.getform().equals("DlgPasienMati")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pasien.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        resep.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (resep.panelDiagnosa1.getTable().getSelectedRow() != -1) {
                    switch(pilihan){
                        case "1":
                            kdicd1.setText(resep.panelDiagnosa1.getTable().getValueAt(resep.panelDiagnosa1.getTable().getSelectedRow(), 1).toString());
                            icd1.setText(resep.panelDiagnosa1.getTable().getValueAt(resep.panelDiagnosa1.getTable().getSelectedRow(), 2).toString());
                            break;                            
                        case "2":
                            kdicd2.setText(resep.panelDiagnosa1.getTable().getValueAt(resep.panelDiagnosa1.getTable().getSelectedRow(), 1).toString());
                            icd2.setText(resep.panelDiagnosa1.getTable().getValueAt(resep.panelDiagnosa1.getTable().getSelectedRow(), 2).toString());
                            break;                            
                        case "3":
                            kdicd3.setText(resep.panelDiagnosa1.getTable().getValueAt(resep.panelDiagnosa1.getTable().getSelectedRow(), 1).toString());
                            icd3.setText(resep.panelDiagnosa1.getTable().getValueAt(resep.panelDiagnosa1.getTable().getSelectedRow(), 2).toString());
                            break;                            
                        case "4":
                            kdicd4.setText(resep.panelDiagnosa1.getTable().getValueAt(resep.panelDiagnosa1.getTable().getSelectedRow(), 1).toString());
                            icd4.setText(resep.panelDiagnosa1.getTable().getValueAt(resep.panelDiagnosa1.getTable().getSelectedRow(), 2).toString());
                            break;                            
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

        resep.panelDiagnosa1.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    resep.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (dokter.getTable().getSelectedRow() != -1) {
                        NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                        btnDokter.requestFocus();
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
    
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TNoRW = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCetakSuratMati = new javax.swing.JMenuItem();
        MnAngkutJenazah = new javax.swing.JMenuItem();
        WindowInput = new javax.swing.JDialog();
        internalFrame7 = new widget.InternalFrame();
        panelBiasa4 = new widget.PanelBiasa();
        jLabel38 = new widget.Label();
        BtnPrint5 = new widget.Button();
        BtnKeluar4 = new widget.Button();
        btnDokter = new widget.Button();
        NmDokter = new widget.TextBox();
        jLabel36 = new widget.Label();
        dasarDiagnosis = new widget.ComboBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbMati = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel16 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel17 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel18 = new widget.Label();
        jk = new widget.ComboBox();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        panelBiasa1 = new widget.PanelBiasa();
        jLabel8 = new widget.Label();
        jLabel4 = new widget.Label();
        jLabel9 = new widget.Label();
        TKtg = new widget.TextBox();
        TPasien = new widget.TextBox();
        tglPemulasaran = new widget.Tanggal();
        TNoRM = new widget.TextBox();
        BtnSeek = new widget.Button();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        jLabel10 = new widget.Label();
        jLabel5 = new widget.Label();
        jLabel11 = new widget.Label();
        jLabel12 = new widget.Label();
        jLabel13 = new widget.Label();
        jLabel14 = new widget.Label();
        jLabel15 = new widget.Label();
        icd1 = new widget.TextBox();
        icd2 = new widget.TextBox();
        icd3 = new widget.TextBox();
        icd4 = new widget.TextBox();
        tmptmeninggal = new widget.ComboBox();
        sttspenduduk = new widget.ComboBox();
        jLabel19 = new widget.Label();
        jLabel20 = new widget.Label();
        keadaan = new widget.ComboBox();
        jLabel21 = new widget.Label();
        jnsPemulasaran = new widget.ComboBox();
        DTPTgl = new widget.Tanggal();
        BtnSeek1 = new widget.Button();
        kdicd1 = new widget.TextBox();
        kdicd3 = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        kdicd2 = new widget.TextBox();
        kdicd4 = new widget.TextBox();
        BtnSeek3 = new widget.Button();
        BtnSeek4 = new widget.Button();
        jLabel22 = new widget.Label();

        TNoRW.setHighlighter(null);
        TNoRW.setName("TNoRW"); // NOI18N
        TNoRW.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRWKeyPressed(evt);
            }
        });

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCetakSuratMati.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratMati.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratMati.setForeground(java.awt.Color.darkGray);
        MnCetakSuratMati.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratMati.setText("Surat Kematian");
        MnCetakSuratMati.setName("MnCetakSuratMati"); // NOI18N
        MnCetakSuratMati.setPreferredSize(new java.awt.Dimension(250, 28));
        MnCetakSuratMati.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratMatiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakSuratMati);

        MnAngkutJenazah.setBackground(new java.awt.Color(255, 255, 254));
        MnAngkutJenazah.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnAngkutJenazah.setForeground(java.awt.Color.darkGray);
        MnAngkutJenazah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnAngkutJenazah.setText("Surat Angkut Jenazah");
        MnAngkutJenazah.setName("MnAngkutJenazah"); // NOI18N
        MnAngkutJenazah.setPreferredSize(new java.awt.Dimension(250, 28));
        MnAngkutJenazah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnAngkutJenazahActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnAngkutJenazah);

        WindowInput.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput.setName("WindowInput"); // NOI18N
        WindowInput.setUndecorated(true);
        WindowInput.setResizable(false);

        internalFrame7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Cetak Surat Kematian ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame7.setName("internalFrame7"); // NOI18N
        internalFrame7.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa4.setName("panelBiasa4"); // NOI18N
        panelBiasa4.setLayout(null);

        jLabel38.setText("Dasar Diagnosis :");
        jLabel38.setName("jLabel38"); // NOI18N
        panelBiasa4.add(jLabel38);
        jLabel38.setBounds(0, 10, 115, 23);

        BtnPrint5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint5.setMnemonic('T');
        BtnPrint5.setText("Cetak");
        BtnPrint5.setToolTipText("Alt+T");
        BtnPrint5.setName("BtnPrint5"); // NOI18N
        BtnPrint5.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint5ActionPerformed(evt);
            }
        });
        panelBiasa4.add(BtnPrint5);
        BtnPrint5.setBounds(300, 80, 100, 30);

        BtnKeluar4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar4.setMnemonic('K');
        BtnKeluar4.setText("Keluar");
        BtnKeluar4.setToolTipText("Alt+K");
        BtnKeluar4.setName("BtnKeluar4"); // NOI18N
        BtnKeluar4.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar4ActionPerformed(evt);
            }
        });
        panelBiasa4.add(BtnKeluar4);
        BtnKeluar4.setBounds(400, 80, 100, 30);

        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter.setMnemonic('6');
        btnDokter.setToolTipText("ALt+6");
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        panelBiasa4.add(btnDokter);
        btnDokter.setBounds(460, 40, 28, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(300, 23));
        panelBiasa4.add(NmDokter);
        NmDokter.setBounds(120, 40, 340, 23);

        jLabel36.setText("Dokter Png. Jawab :");
        jLabel36.setName("jLabel36"); // NOI18N
        jLabel36.setPreferredSize(new java.awt.Dimension(60, 23));
        panelBiasa4.add(jLabel36);
        jLabel36.setBounds(0, 40, 115, 23);

        dasarDiagnosis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Rekam Medis", "2. Autopsi Verbal", "3. Autopsi Forensik" }));
        dasarDiagnosis.setName("dasarDiagnosis"); // NOI18N
        dasarDiagnosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dasarDiagnosisKeyPressed(evt);
            }
        });
        panelBiasa4.add(dasarDiagnosis);
        dasarDiagnosis.setBounds(120, 10, 170, 23);

        internalFrame7.add(panelBiasa4, java.awt.BorderLayout.CENTER);

        WindowInput.getContentPane().add(internalFrame7, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pasien Meninggal ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbMati.setAutoCreateRowSorter(true);
        tbMati.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbMati.setComponentPopupMenu(jPopupMenu1);
        tbMati.setName("tbMati"); // NOI18N
        tbMati.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMatiMouseClicked(evt);
            }
        });
        tbMati.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbMatiKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbMati);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
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

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnBatal);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnHapus);

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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 23));
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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel16.setText("Periode :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel16);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-10-2023" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass9.add(DTPCari1);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("s.d");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(24, 23));
        panelGlass9.add(jLabel17);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-10-2023" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass9.add(DTPCari2);

        jLabel18.setText("J.K :");
        jLabel18.setName("jLabel18"); // NOI18N
        jLabel18.setPreferredSize(new java.awt.Dimension(30, 23));
        panelGlass9.add(jLabel18);

        jk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "L", "P" }));
        jk.setName("jk"); // NOI18N
        jk.setPreferredSize(new java.awt.Dimension(70, 23));
        jk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jkActionPerformed(evt);
            }
        });
        jk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jkKeyPressed(evt);
            }
        });
        panelGlass9.add(jk);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(150, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

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
        panelGlass9.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
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
        panelGlass9.add(BtnAll);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        panelBiasa1.setName("panelBiasa1"); // NOI18N
        panelBiasa1.setPreferredSize(new java.awt.Dimension(610, 165));
        panelBiasa1.setLayout(null);

        jLabel8.setText("Jam :");
        jLabel8.setName("jLabel8"); // NOI18N
        panelBiasa1.add(jLabel8);
        jLabel8.setBounds(209, 10, 39, 23);

        jLabel4.setText("No.Rekam Medik :");
        jLabel4.setName("jLabel4"); // NOI18N
        panelBiasa1.add(jLabel4);
        jLabel4.setBounds(0, 40, 115, 23);

        jLabel9.setText("Keterangan :");
        jLabel9.setName("jLabel9"); // NOI18N
        panelBiasa1.add(jLabel9);
        jLabel9.setBounds(0, 130, 115, 23);

        TKtg.setHighlighter(null);
        TKtg.setName("TKtg"); // NOI18N
        TKtg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKtgKeyPressed(evt);
            }
        });
        panelBiasa1.add(TKtg);
        TKtg.setBounds(118, 130, 530, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        panelBiasa1.add(TPasien);
        TPasien.setBounds(230, 40, 399, 23);

        tglPemulasaran.setEditable(false);
        tglPemulasaran.setForeground(new java.awt.Color(50, 70, 50));
        tglPemulasaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-10-2023" }));
        tglPemulasaran.setDisplayFormat("dd-MM-yyyy");
        tglPemulasaran.setName("tglPemulasaran"); // NOI18N
        tglPemulasaran.setOpaque(false);
        tglPemulasaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tglPemulasaranKeyPressed(evt);
            }
        });
        panelBiasa1.add(tglPemulasaran);
        tglPemulasaran.setBounds(800, 100, 90, 23);

        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        panelBiasa1.add(TNoRM);
        TNoRM.setBounds(118, 40, 110, 23);

        BtnSeek.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek.setMnemonic('1');
        BtnSeek.setToolTipText("Alt+1");
        BtnSeek.setName("BtnSeek"); // NOI18N
        BtnSeek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekActionPerformed(evt);
            }
        });
        BtnSeek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeekKeyPressed(evt);
            }
        });
        panelBiasa1.add(BtnSeek);
        BtnSeek.setBounds(626, 40, 28, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        panelBiasa1.add(cmbJam);
        cmbJam.setBounds(251, 10, 62, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        panelBiasa1.add(cmbMnt);
        cmbMnt.setBounds(316, 10, 62, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        panelBiasa1.add(cmbDtk);
        cmbDtk.setBounds(381, 10, 62, 23);

        jLabel10.setText("Tgl.Meninggal :");
        jLabel10.setName("jLabel10"); // NOI18N
        panelBiasa1.add(jLabel10);
        jLabel10.setBounds(0, 10, 115, 23);

        jLabel5.setText("Di :");
        jLabel5.setName("jLabel5"); // NOI18N
        panelBiasa1.add(jLabel5);
        jLabel5.setBounds(455, 10, 20, 23);

        jLabel11.setText("ICD-X (Langsung) :");
        jLabel11.setName("jLabel11"); // NOI18N
        panelBiasa1.add(jLabel11);
        jLabel11.setBounds(380, 100, 110, 23);

        jLabel12.setText("Penyebab Kematian :");
        jLabel12.setName("jLabel12"); // NOI18N
        panelBiasa1.add(jLabel12);
        jLabel12.setBounds(0, 70, 115, 23);

        jLabel13.setText("ICD-X (Dasar) :");
        jLabel13.setName("jLabel13"); // NOI18N
        panelBiasa1.add(jLabel13);
        jLabel13.setBounds(105, 70, 110, 23);

        jLabel14.setText("ICD-X (Antara #1) :");
        jLabel14.setName("jLabel14"); // NOI18N
        panelBiasa1.add(jLabel14);
        jLabel14.setBounds(105, 100, 110, 23);

        jLabel15.setText("ICD-X (Antara #2) :");
        jLabel15.setName("jLabel15"); // NOI18N
        panelBiasa1.add(jLabel15);
        jLabel15.setBounds(380, 70, 110, 23);

        icd1.setHighlighter(null);
        icd1.setName("icd1"); // NOI18N
        icd1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                icd1KeyPressed(evt);
            }
        });
        panelBiasa1.add(icd1);
        icd1.setBounds(270, 70, 95, 23);

        icd2.setHighlighter(null);
        icd2.setName("icd2"); // NOI18N
        icd2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                icd2KeyPressed(evt);
            }
        });
        panelBiasa1.add(icd2);
        icd2.setBounds(270, 100, 95, 23);

        icd3.setHighlighter(null);
        icd3.setName("icd3"); // NOI18N
        icd3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                icd3KeyPressed(evt);
            }
        });
        panelBiasa1.add(icd3);
        icd3.setBounds(545, 70, 83, 23);

        icd4.setHighlighter(null);
        icd4.setName("icd4"); // NOI18N
        icd4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                icd4KeyPressed(evt);
            }
        });
        panelBiasa1.add(icd4);
        icd4.setBounds(545, 100, 83, 23);

        tmptmeninggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Rumah Sakit", "Puskesmas", "Rumah Bersalin", "Rumah Tempat Tinggal", "Lain-lain (Termasuk Doa)", "Tidak tahu" }));
        tmptmeninggal.setName("tmptmeninggal"); // NOI18N
        tmptmeninggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tmptmeninggalKeyPressed(evt);
            }
        });
        panelBiasa1.add(tmptmeninggal);
        tmptmeninggal.setBounds(478, 10, 170, 23);

        sttspenduduk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Penduduk Tetap", "2. Bukan Penduduk Tetap" }));
        sttspenduduk.setName("sttspenduduk"); // NOI18N
        sttspenduduk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sttspendudukKeyPressed(evt);
            }
        });
        panelBiasa1.add(sttspenduduk);
        sttspenduduk.setBounds(800, 10, 200, 23);

        jLabel19.setText("Stts. Kependudukan :");
        jLabel19.setName("jLabel19"); // NOI18N
        panelBiasa1.add(jLabel19);
        jLabel19.setBounds(675, 10, 120, 23);

        jLabel20.setText("Keadaan (jika wanita) :");
        jLabel20.setName("jLabel20"); // NOI18N
        panelBiasa1.add(jLabel20);
        jLabel20.setBounds(675, 40, 120, 23);

        keadaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Hamil", "Bersalin", "Nifas", "Lainnya" }));
        keadaan.setName("keadaan"); // NOI18N
        keadaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                keadaanKeyPressed(evt);
            }
        });
        panelBiasa1.add(keadaan);
        keadaan.setBounds(800, 40, 200, 23);

        jLabel21.setText("Rencana Pemulasaran :");
        jLabel21.setName("jLabel21"); // NOI18N
        panelBiasa1.add(jLabel21);
        jLabel21.setBounds(675, 70, 120, 23);

        jnsPemulasaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Dikubur", "Dikremasi", "Transportasi keluar kota" }));
        jnsPemulasaran.setName("jnsPemulasaran"); // NOI18N
        jnsPemulasaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jnsPemulasaranKeyPressed(evt);
            }
        });
        panelBiasa1.add(jnsPemulasaran);
        jnsPemulasaran.setBounds(800, 70, 200, 23);

        DTPTgl.setEditable(false);
        DTPTgl.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-10-2023" }));
        DTPTgl.setDisplayFormat("dd-MM-yyyy");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        panelBiasa1.add(DTPTgl);
        DTPTgl.setBounds(118, 10, 90, 23);

        BtnSeek1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek1.setMnemonic('1');
        BtnSeek1.setToolTipText("Alt+1");
        BtnSeek1.setName("BtnSeek1"); // NOI18N
        BtnSeek1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek1ActionPerformed(evt);
            }
        });
        BtnSeek1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek1KeyPressed(evt);
            }
        });
        panelBiasa1.add(BtnSeek1);
        BtnSeek1.setBounds(363, 70, 28, 23);

        kdicd1.setHighlighter(null);
        kdicd1.setName("kdicd1"); // NOI18N
        kdicd1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdicd1KeyPressed(evt);
            }
        });
        panelBiasa1.add(kdicd1);
        kdicd1.setBounds(220, 70, 50, 23);

        kdicd3.setHighlighter(null);
        kdicd3.setName("kdicd3"); // NOI18N
        kdicd3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdicd3KeyPressed(evt);
            }
        });
        panelBiasa1.add(kdicd3);
        kdicd3.setBounds(495, 70, 50, 23);

        BtnSeek2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek2.setMnemonic('1');
        BtnSeek2.setToolTipText("Alt+1");
        BtnSeek2.setName("BtnSeek2"); // NOI18N
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
        panelBiasa1.add(BtnSeek2);
        BtnSeek2.setBounds(363, 100, 28, 23);

        kdicd2.setHighlighter(null);
        kdicd2.setName("kdicd2"); // NOI18N
        kdicd2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdicd2KeyPressed(evt);
            }
        });
        panelBiasa1.add(kdicd2);
        kdicd2.setBounds(220, 100, 50, 23);

        kdicd4.setHighlighter(null);
        kdicd4.setName("kdicd4"); // NOI18N
        kdicd4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdicd4KeyPressed(evt);
            }
        });
        panelBiasa1.add(kdicd4);
        kdicd4.setBounds(495, 100, 50, 23);

        BtnSeek3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek3.setMnemonic('1');
        BtnSeek3.setToolTipText("Alt+1");
        BtnSeek3.setName("BtnSeek3"); // NOI18N
        BtnSeek3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek3ActionPerformed(evt);
            }
        });
        BtnSeek3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek3KeyPressed(evt);
            }
        });
        panelBiasa1.add(BtnSeek3);
        BtnSeek3.setBounds(626, 70, 28, 23);

        BtnSeek4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek4.setMnemonic('1');
        BtnSeek4.setToolTipText("Alt+1");
        BtnSeek4.setName("BtnSeek4"); // NOI18N
        BtnSeek4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek4ActionPerformed(evt);
            }
        });
        BtnSeek4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek4KeyPressed(evt);
            }
        });
        panelBiasa1.add(BtnSeek4);
        BtnSeek4.setBounds(626, 100, 28, 23);

        jLabel22.setText("Tgl. Pemulasaran :");
        jLabel22.setName("jLabel22"); // NOI18N
        panelBiasa1.add(jLabel22);
        jLabel22.setBounds(675, 100, 120, 23);

        internalFrame1.add(panelBiasa1, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tglPemulasaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tglPemulasaranKeyPressed
        Valid.pindah(evt,TCari,cmbJam);
}//GEN-LAST:event_tglPemulasaranKeyPressed

    private void BtnSeekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekActionPerformed
        var.setform("DlgPasienMati");
        pasien.emptTeks();
        pasien.isCek();
        pasien.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.setLocationRelativeTo(internalFrame1);
        pasien.setVisible(true);
}//GEN-LAST:event_BtnSeekActionPerformed

    private void BtnSeekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeekKeyPressed
        Valid.pindah(evt,TNoRM,TKtg);
}//GEN-LAST:event_BtnSeekKeyPressed

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt,tglPemulasaran,cmbMnt);
}//GEN-LAST:event_cmbJamKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt,cmbJam,cmbDtk);
}//GEN-LAST:event_cmbMntKeyPressed

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
        Valid.pindah(evt,cmbMnt,tmptmeninggal);
}//GEN-LAST:event_cmbDtkKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRM,"pasien");
        }else if(TKtg.getText().trim().equals("")){
            Valid.textKosong(TKtg,"keterangan");
        }else{
            Sequel.menyimpan("pasien_mati","'"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"','"+
                    cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"','"+
                    TNoRM.getText()+"','"+
                    TKtg.getText()+"','"+
                    tmptmeninggal.getSelectedItem()+"','"+
                    kdicd1.getText()+"','"+
                    kdicd2.getText()+"','"+
                    kdicd3.getText()+"','"+
                    kdicd4.getText()+"','"+
                    sttspenduduk.getSelectedItem().toString().substring(0,1)+"','"+
                    keadaan.getSelectedItem()+"','"
                    +jnsPemulasaran.getSelectedItem()+"','"
                    +Valid.SetTgl(tglPemulasaran.getSelectedItem()+"")+"'","pasien");
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,TKtg,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        Valid.hapusTable(tabMode,TNoRM,"pasien_mati","pasien_mati.no_rkm_medis");
        tampil();
        emptTeks();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(! TCari.getText().trim().equals("")){
            BtnCariActionPerformed(evt);
        }
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){            
            Map<String, Object> param = new HashMap<>();    
                param.put("namars",var.getnamars());
                param.put("alamatrs",var.getalamatrs());
                param.put("kotars",var.getkabupatenrs());
                param.put("propinsirs",var.getpropinsirs());
                param.put("kontakrs",var.getkontakrs());
                param.put("emailrs",var.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting"));
                String tgl = " tanggal between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' ";
                Valid.MyReport("rptPasienMati.jrxml","report","::[ Data Pasien Meninggal ]::",
                        "select tanggal,jam,pasien_mati.no_rkm_medis,nm_pasien, "+
                        "jk,tmp_lahir,tgl_lahir,gol_darah,stts_nikah, "+
                        "agama,keterangan,temp_meninggal,icd1,icd2,icd3,icd4 from pasien_mati,pasien where "+
                         sql+" and "+tgl+"and tanggal like '%"+TCari.getText().trim()+"%' or "+
                         sql+" and "+tgl+"and pasien_mati.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                         sql+" and "+tgl+"and nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                         sql+" and "+tgl+"and jk like '%"+TCari.getText().trim()+"%' or "+
                         sql+" and "+tgl+"and tmp_lahir like '%"+TCari.getText().trim()+"%' or "+
                         sql+" and "+tgl+"and gol_darah like '%"+TCari.getText().trim()+"%' or "+
                         sql+" and "+tgl+"and stts_nikah like '%"+TCari.getText().trim()+"%' or "+
                         sql+" and "+tgl+"and agama like '%"+TCari.getText().trim()+"%' or "+
                         sql+" and "+tgl+"and keterangan like '%"+TCari.getText().trim()+"%' "+
                    " order by tanggal ",param);  
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
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
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TCari.setText("");
            tampil();
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbMatiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMatiMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbMatiMouseClicked

private void MnCetakSuratMatiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratMatiActionPerformed
    if (TPasien.getText().trim().equals("")) {
        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
    } else {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        WindowInput.setSize(550, 150);
        WindowInput.setLocationRelativeTo(internalFrame1);
        WindowInput.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }
}//GEN-LAST:event_MnCetakSuratMatiActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void MnAngkutJenazahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnAngkutJenazahActionPerformed
        if(TPasien.getText().trim().equals("")){
          JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");                
        }else{
            Map<String, Object> param = new HashMap<>();
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptAngkutJenazah.jrxml","report","::[ Surat Angkut Jenazah ]::",
                          "select tanggal,jam,pasien_mati.no_rkm_medis,pasien.nm_pasien,pasien.pekerjaan, "+
                          "pasien.umur,pasien.alamat,jk,tmp_lahir,tgl_lahir,gol_darah,stts_nikah, "+
                          "agama,keterangan from pasien_mati,pasien "+
                          "where pasien_mati.no_rkm_medis=pasien.no_rkm_medis "+
                          "and pasien_mati.no_rkm_medis='"+TNoRM.getText()+"' ",param);
        }
    }//GEN-LAST:event_MnAngkutJenazahActionPerformed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",TPasien,TNoRM.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeekActionPerformed(null);
        }else{
            Valid.pindah(evt,tmptmeninggal,icd1);
        }
    }//GEN-LAST:event_TNoRMKeyPressed

    private void icd1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_icd1KeyPressed
        Valid.pindah(evt,TNoRM,icd2);
    }//GEN-LAST:event_icd1KeyPressed

    private void icd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_icd2KeyPressed
        Valid.pindah(evt,icd1,icd3);
    }//GEN-LAST:event_icd2KeyPressed

    private void icd3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_icd3KeyPressed
        Valid.pindah(evt,icd2,icd4);
    }//GEN-LAST:event_icd3KeyPressed

    private void icd4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_icd4KeyPressed
        Valid.pindah(evt,icd3,TKtg);
    }//GEN-LAST:event_icd4KeyPressed

    private void tmptmeninggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tmptmeninggalKeyPressed
        Valid.pindah(evt,cmbDtk,TNoRM);
    }//GEN-LAST:event_tmptmeninggalKeyPressed

    private void TKtgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKtgKeyPressed
        Valid.pindah(evt,icd4,BtnSimpan);
    }//GEN-LAST:event_TKtgKeyPressed

    private void tbMatiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbMatiKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbMatiKeyReleased

    private void jkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jkKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jkKeyPressed

    private void jkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jkActionPerformed
        String selectBox = (String) jk.getSelectedItem();
        if(selectBox.equals("-")){
            queryku = " pasien.jk IN ('L','P') ";
        }else if(selectBox.equals("L")){
            queryku = " pasien.jk IN ('L') ";
        }else if(selectBox.equals("P")){
            queryku = " pasien.jk IN ('P') ";
        }
    }//GEN-LAST:event_jkActionPerformed

    private void TNoRWKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRWKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoRWKeyPressed

    private void sttspendudukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sttspendudukKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_sttspendudukKeyPressed

    private void keadaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keadaanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_keadaanKeyPressed

    private void jnsPemulasaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jnsPemulasaranKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jnsPemulasaranKeyPressed

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DTPTglKeyPressed

    private void BtnSeek1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        pilihan="1";
        resep.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        resep.setLocationRelativeTo(internalFrame1);
        resep.isCek();
        resep.isPsien2();
        resep.panelDiagnosa1.tampil();
        resep.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnSeek1ActionPerformed

    private void BtnSeek1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek1KeyPressed

    private void kdicd1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdicd1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kdicd1KeyPressed

    private void kdicd3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdicd3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kdicd3KeyPressed

    private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        pilihan="2";
        resep.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        resep.setLocationRelativeTo(internalFrame1);
        resep.isCek();
        resep.isPsien2();
        resep.panelDiagnosa1.tampil();
        resep.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnSeek2ActionPerformed

    private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek2KeyPressed

    private void kdicd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdicd2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kdicd2KeyPressed

    private void kdicd4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdicd4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kdicd4KeyPressed

    private void BtnSeek3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek3ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        pilihan="3";
        resep.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        resep.setLocationRelativeTo(internalFrame1);
        resep.isCek();
        resep.isPsien2();
        resep.panelDiagnosa1.tampil();
        resep.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnSeek3ActionPerformed

    private void BtnSeek3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek3KeyPressed
        
    }//GEN-LAST:event_BtnSeek3KeyPressed

    private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        pilihan="4";
        resep.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        resep.setLocationRelativeTo(internalFrame1);
        resep.isCek();
        resep.isPsien2();
        resep.panelDiagnosa1.tampil();
        resep.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnSeek4ActionPerformed

    private void BtnSeek4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek4KeyPressed
        
    }//GEN-LAST:event_BtnSeek4KeyPressed

    private void BtnPrint5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint5ActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
        } else {
            isLamaRawat();
            ruangan = Sequel.cariIsi("SELECT CONCAT(kamar.kd_kamar,' ',bangsal.nm_bangsal) FROM kamar_inap,kamar,bangsal WHERE kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal "
                    + "and kamar_inap.no_rawat='" + TNoRW.getText() + "' ORDER BY tgl_masuk DESC limit 1");
            String lamaInap = Sequel.cariIsi("SELECT SUM(lama) FROM kamar_inap where no_rawat=?", TNoRW.getText());
            String caridoa = Sequel.cariIsi("SELECT stts FROM reg_periksa where no_rawat=? and stts='DOA'", TNoRW.getText());
            int lamainapInt = Integer.parseInt(lamainap);
            if (!caridoa.isEmpty()) {
                hitunglama = caridoa;
            }
            if (caridoa.isEmpty()) {
                if (lamainapInt <= 46) {
                    hitunglama = lamainap + " jam";
                }
                if (lamainapInt > 46) {
                    hitunglama = lamaInap + " hari";
                }
            }
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            param.put("lamainap", hitunglama);
            param.put("kamar", ruangan);
            param.put("dpjp", Sequel.cariIsi("SELECT dokter.nm_dokter FROM dpjp_ranap, dokter where dpjp_ranap.kd_dokter=dokter.kd_dokter and dpjp_ranap.jenis_dpjp='Utama' and dpjp_ranap.no_rawat='" + TNoRW.getText() + "' LIMIT 1"));
            param.put("nmicd1", icd1.getText());
            param.put("nmicd2", icd2.getText());
            param.put("nmicd3", icd3.getText());
            param.put("nmicd4", icd4.getText());
            param.put("dxdasar", dasarDiagnosis.getSelectedItem().toString());
            param.put("dokter", NmDokter.getText());
            Valid.MyReport("rptSuratKematian.jrxml", "report", "::[ Surat Kematian ]::",
                    "SELECT pasien_mati.tanggal, pasien_mati.jam, pasien_mati.no_rkm_medis, pasien_mati.keterangan, pasien_mati.temp_meninggal, pasien_mati.icd1, pasien_mati.icd2, pasien_mati.icd3, pasien_mati.icd4,"
                    + "pasien.nm_pasien, pasien.no_ktp,pasien.gol_darah, pasien.jk, pasien.tmp_lahir, pasien.tgl_lahir, pasien.umur,pasien.stts_nikah,pasien.agama, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, "
                    + "pasien.namakeluarga, IF(pasien_mati.stts_penduduk='1','1. Penduduk Tetap','2. Bukan Penduduk Tetap') as stts_penduduk, pasien_mati.keadaan, pasien_mati.jns_pemulasaran, pasien_mati.tgl_pemulasaran "
                    + "FROM reg_periksa,pasien_mati,pasien, kelurahan, kecamatan, kabupaten "
                    + "WHERE reg_periksa.no_rkm_medis=pasien_mati.no_rkm_medis and pasien_mati.no_rkm_medis=pasien.no_rkm_medis and pasien.kd_kel=kelurahan.kd_kel AND pasien.kd_kec=kecamatan.kd_kec AND pasien.kd_kab=kabupaten.kd_kab AND "
                    + "pasien_mati.no_rkm_medis='" + TNoRM.getText() + "' GROUP BY pasien_mati.no_rkm_medis", param);
            Valid.MyReport("rptSuratPenyebabKematian.jrxml", "report", "::[ Surat Penyebab Kematian ]::",
                    "SELECT pasien_mati.tanggal, pasien_mati.jam, pasien_mati.no_rkm_medis, pasien_mati.keterangan, pasien_mati.temp_meninggal, pasien_mati.icd1, pasien_mati.icd2, pasien_mati.icd3, pasien_mati.icd4,"
                    + "pasien.nm_pasien, pasien.no_ktp,pasien.gol_darah, pasien.jk, pasien.tmp_lahir, pasien.tgl_lahir,pasien.umur,pasien.stts_nikah,pasien.agama, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, "
                    + "pasien.namakeluarga, IF(pasien_mati.stts_penduduk='1','1. Penduduk Tetap','2. Bukan Penduduk Tetap') as stts_penduduk, pasien_mati.keadaan, pasien_mati.jns_pemulasaran, pasien_mati.tgl_pemulasaran "
                    + "FROM reg_periksa,pasien_mati,pasien, kelurahan, kecamatan, kabupaten "
                    + "WHERE reg_periksa.no_rkm_medis=pasien_mati.no_rkm_medis and pasien_mati.no_rkm_medis=pasien.no_rkm_medis and pasien.kd_kel=kelurahan.kd_kel AND pasien.kd_kec=kecamatan.kd_kec AND pasien.kd_kab=kabupaten.kd_kab AND "
                    + "pasien_mati.no_rkm_medis='" + TNoRM.getText() + "' GROUP BY pasien_mati.no_rkm_medis", param);
        
            WindowInput.dispose();
        }
    }//GEN-LAST:event_BtnPrint5ActionPerformed

    private void BtnKeluar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar4ActionPerformed
        WindowInput.dispose();
    }//GEN-LAST:event_BtnKeluar4ActionPerformed

    private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokterActionPerformed

    private void dasarDiagnosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dasarDiagnosisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_dasarDiagnosisKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPasienMati dialog = new DlgPasienMati(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluar4;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint5;
    private widget.Button BtnSeek;
    private widget.Button BtnSeek1;
    private widget.Button BtnSeek2;
    private widget.Button BtnSeek3;
    private widget.Button BtnSeek4;
    private widget.Button BtnSimpan;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPTgl;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnAngkutJenazah;
    private javax.swing.JMenuItem MnCetakSuratMati;
    private widget.TextBox NmDokter;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TKtg;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRW;
    private widget.TextBox TPasien;
    private javax.swing.JDialog WindowInput;
    private widget.Button btnDokter;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbMnt;
    private widget.ComboBox dasarDiagnosis;
    private widget.TextBox icd1;
    private widget.TextBox icd2;
    private widget.TextBox icd3;
    private widget.TextBox icd4;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame7;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel36;
    private widget.Label jLabel38;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.ComboBox jk;
    private widget.ComboBox jnsPemulasaran;
    private widget.TextBox kdicd1;
    private widget.TextBox kdicd2;
    private widget.TextBox kdicd3;
    private widget.TextBox kdicd4;
    private widget.ComboBox keadaan;
    private widget.PanelBiasa panelBiasa1;
    private widget.PanelBiasa panelBiasa4;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ComboBox sttspenduduk;
    private widget.Table tbMati;
    private widget.Tanggal tglPemulasaran;
    private widget.ComboBox tmptmeninggal;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement("select tanggal,jam,pasien_mati.no_rkm_medis,nm_pasien,pasien.no_ktp, "+
                   "jk,tmp_lahir,tgl_lahir,pasien.alamat,gol_darah,stts_nikah, "+
                   "agama,keterangan,temp_meninggal,icd1,icd2,icd3,icd4 , "
                    + "TIMESTAMPDIFF( YEAR, pasien.tgl_lahir, pasien_mati.tanggal ) as age , TIMESTAMPDIFF( MONTH, pasien.tgl_lahir, pasien_mati.tanggal  ) % 12 as month "
                    + ",pasien_mati.jns_pemulasaran, pasien_mati.tgl_pemulasaran,IF(pasien_mati.stts_penduduk='1','1. Penduduk Tetap','2. Bukan Penduduk Tetap') as stts_penduduk, pasien_mati.keadaan "
                    + "from pasien_mati,pasien where "+
                    sql+"and tanggal between ? and ? and "+queryku+
                    " and ( pasien_mati.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                    "nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                    "tmp_lahir like '%"+TCari.getText().trim()+"%' or "+
                    "gol_darah like '%"+TCari.getText().trim()+"%' or "+
                    "stts_nikah like '%"+TCari.getText().trim()+"%' or "+
                    "agama like '"+TCari.getText().trim()+"%' or "+
                    "keterangan like '%"+TCari.getText().trim()+"%' )"+
                    " order by tanggal ");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                
                rs=ps.executeQuery();
                while(rs.next()){               
                    tabMode.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),
                        rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString("age")+" Th "+rs.getString("month")+" Bl",
                        rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),
                        rs.getString(13),rs.getString(14),rs.getString(15),rs.getString(16),rs.getString(17),rs.getString(18)
                        ,rs.getString("jns_pemulasaran"),rs.getString("tgl_pemulasaran"),rs.getString("stts_penduduk"), rs.getString("keadaan")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        int b=tabMode.getRowCount();
        LCount.setText(""+b);
    }

    public void emptTeks() {
        TNoRM.setText("");
        TPasien.setText("");
        TKtg.setText("");
        tmptmeninggal.setSelectedItem("");
        icd1.setText("");
        icd2.setText("");
        icd3.setText("");
        icd4.setText("");
        kdicd1.setText("");
        kdicd2.setText("");
        kdicd3.setText("");
        kdicd4.setText("");
        sttspenduduk.setSelectedIndex(0);
        keadaan.setSelectedIndex(0);
        jnsPemulasaran.setSelectedIndex(0);
        DTPTgl.setDate(new Date());
        tglPemulasaran.setDate(new Date());
        NmDokter.setText("");
        DTPTgl.requestFocus();
    }

    private void getData() {
        if(tbMati.getSelectedRow()!= -1){
            cmbJam.setSelectedItem(tbMati.getValueAt(tbMati.getSelectedRow(),1).toString().substring(0,2));
            cmbMnt.setSelectedItem(tbMati.getValueAt(tbMati.getSelectedRow(),1).toString().substring(3,5));
            cmbDtk.setSelectedItem(tbMati.getValueAt(tbMati.getSelectedRow(),1).toString().substring(6,8));
            TNoRM.setText(tbMati.getValueAt(tbMati.getSelectedRow(),2).toString());
            TPasien.setText(tbMati.getValueAt(tbMati.getSelectedRow(),3).toString());
            TKtg.setText(tbMati.getValueAt(tbMati.getSelectedRow(),13).toString());
            Valid.SetTgl(DTPTgl,tbMati.getValueAt(tbMati.getSelectedRow(),0).toString());
            tmptmeninggal.setSelectedItem(tbMati.getValueAt(tbMati.getSelectedRow(),14).toString());
            kdicd1.setText(tbMati.getValueAt(tbMati.getSelectedRow(),15).toString());
            icd1.setText(Sequel.cariIsi("SELECT nm_penyakit FROM penyakit WHERE kd_penyakit=?",kdicd1.getText()));
            kdicd2.setText(tbMati.getValueAt(tbMati.getSelectedRow(),16).toString());
            icd2.setText(Sequel.cariIsi("SELECT nm_penyakit FROM penyakit WHERE kd_penyakit=?",kdicd2.getText()));
            kdicd3.setText(tbMati.getValueAt(tbMati.getSelectedRow(),17).toString());
            icd3.setText(Sequel.cariIsi("SELECT nm_penyakit FROM penyakit WHERE kd_penyakit=?",kdicd3.getText()));
            kdicd4.setText(tbMati.getValueAt(tbMati.getSelectedRow(),18).toString());
            icd4.setText(Sequel.cariIsi("SELECT nm_penyakit FROM penyakit WHERE kd_penyakit=?",kdicd4.getText()));
            TNoRW.setText(Sequel.cariIsi("SELECT no_rawat FROM reg_periksa WHERE no_rkm_medis='"+TNoRM.getText()+"' ORDER BY tgl_registrasi DESC LIMIT 1"));            
            jnsPemulasaran.setSelectedItem(tbMati.getValueAt(tbMati.getSelectedRow(),19).toString());
            Valid.SetTgl(tglPemulasaran,tbMati.getValueAt(tbMati.getSelectedRow(),20).toString());
            sttspenduduk.setSelectedItem(tbMati.getValueAt(tbMati.getSelectedRow(),21).toString());
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(var.getpasien_meninggal());
        BtnHapus.setEnabled(var.getpasien_meninggal());
        BtnPrint.setEnabled(var.getpasien_meninggal());
    }
    
    public void setNoRm(String norm) {
        TNoRM.setText(norm);  
        isPsien();       
    }
    
    private void isPsien() {
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",TPasien,TNoRM.getText());
    }
    
    private String isLamaRawat(){
        String cari_reg = Sequel.cariIsi("SELECT CONCAT(tgl_registrasi,' ',jam_reg) FROM reg_periksa WHERE no_rkm_medis='" + TNoRM.getText() + "' ORDER BY tgl_registrasi DESC LIMIT 1");
        String cari_mati = Sequel.cariIsi("SELECT CONCAT(tanggal,' ',jam) FROM pasien_mati WHERE no_rkm_medis='" + TNoRM.getText() + "'");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime tglreg = LocalDateTime.parse(cari_reg, formatter);
        LocalDateTime tglmati = LocalDateTime.parse(cari_mati, formatter);
        long hoursDifference = ChronoUnit.HOURS.between(tglreg, tglmati);
        lamainap = String.valueOf(hoursDifference);
        return lamainap;
    }
}
