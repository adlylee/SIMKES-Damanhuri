/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgJnsPerawatanRalan.java
 *
 * Created on May 22, 2010, 11:58:21 PM
 */

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
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

/**
 *
 * @author dosen
 */
public final class MyLimsMapping extends javax.swing.JDialog {
    private final DefaultTableModel tabMode, tabMode1,tabMode2;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps,pstindakan;
    private ResultSet rs,rstindakan;    
    private int i=0;
    private String URL = "", query = "", token,requestJson,tanggal = "",jam ="";
    private MyLimsApi api = new MyLimsApi();
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    

    /** Creates new form DlgJnsPerawatanRalan
     * @param parent
     * @param modal */
    public MyLimsMapping(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(628,674);
        WindowCariMyLims.setSize(874, 250);
        WindowCariLab.setSize(874, 250);

        tabMode=new DefaultTableModel(null,new Object[]{
            "Kode Pemeriksaan RS","Id Template RS","Nama Pemeriksaan RS","Kode Pemeriksaan MyLims","Nama Pemeriksaan MyLims"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbJnsPerawatan.setModel(tabMode);

        tbJnsPerawatan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbJnsPerawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 5; i++) {
            TableColumn column = tbJnsPerawatan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(120);
            }else if(i==1){
                column.setPreferredWidth(120);
            }else if(i==2){
                column.setPreferredWidth(250);
            }else if(i==3){
                column.setPreferredWidth(130);
            }else if(i==4){
                column.setPreferredWidth(260);
            }
        }
        tbJnsPerawatan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode1 = new DefaultTableModel(null, new String[]{"No.", "Id", "Nama", "Kode",
            "Grouper"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbFaskes1.setModel(tabMode1);
        tbFaskes1.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbFaskes1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 5; i++) {
            TableColumn column = tbFaskes1.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(35);
            } else if (i == 1) {
                column.setPreferredWidth(50);
            } else if (i == 2) {
                column.setPreferredWidth(125);
            } else if (i == 3) {
                column.setPreferredWidth(90);
            } else if (i == 4) {
                column.setPreferredWidth(200);
            }
        }
        tbFaskes1.setDefaultRenderer(Object.class, new WarnaTable());
        
        Object[] row2={"Kode Pemeriksaan","Id Template","Nama Pemeriksaan"};
        tabMode2=new DefaultTableModel(null,row2){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbTarif.setModel(tabMode2);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbTarif.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTarif.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for(i = 0; i < 3; i++) {
            TableColumn column = tbTarif.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(200);
            }
        }
        tbTarif.setDefaultRenderer(Object.class, new WarnaTable());

        kdpoli.setDocument(new batasInput((byte)5).getKata(kdpoli)); 
        KdPoliBPJS.setDocument(new batasInput((byte)15).getKata(KdPoliBPJS)); 
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

        WindowCariMyLims = new javax.swing.JDialog();
        internalFrame8 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbFaskes1 = new widget.Table();
        panelisi1 = new widget.panelisi();
        label11 = new widget.Label();
        TCariLims = new widget.TextBox();
        BtnCariLims = new widget.Button();
        BtnKeluarLims = new widget.Button();
        WindowCariLab = new javax.swing.JDialog();
        internalFrame9 = new widget.InternalFrame();
        panelisi2 = new widget.panelisi();
        label10 = new widget.Label();
        TCariLab = new widget.TextBox();
        BtnCariLab = new widget.Button();
        BtnKeluarLab = new widget.Button();
        scrollPane2 = new widget.ScrollPane();
        tbTarif = new widget.Table();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbJnsPerawatan = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        kdpoli = new widget.TextBox();
        TPoli = new widget.TextBox();
        btnPoliRS = new widget.Button();
        jLabel19 = new widget.Label();
        KdPoliBPJS = new widget.TextBox();
        NmPoliBPJS = new widget.TextBox();
        btnPoliBPJS = new widget.Button();
        kdpoli1 = new widget.TextBox();

        WindowCariMyLims.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowCariMyLims.setName("WindowCariMyLims"); // NOI18N
        WindowCariMyLims.setUndecorated(true);
        WindowCariMyLims.setResizable(false);
        WindowCariMyLims.getContentPane().setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Cari Data MyLims ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        internalFrame8.setName("internalFrame8"); // NOI18N
        internalFrame8.setWarnaBawah(new java.awt.Color(245, 250, 240));
        internalFrame8.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbFaskes1.setToolTipText("Klik data di tabel");
        tbFaskes1.setName("tbFaskes1"); // NOI18N
        tbFaskes1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbFaskes1MouseClicked(evt);
            }
        });
        tbFaskes1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbFaskes1KeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbFaskes1);

        internalFrame8.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(44, 54));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Key Word :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(69, 23));
        panelisi1.add(label11);

        TCariLims.setName("TCariLims"); // NOI18N
        TCariLims.setPreferredSize(new java.awt.Dimension(120, 23));
        TCariLims.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariLimsKeyPressed(evt);
            }
        });
        panelisi1.add(TCariLims);

        BtnCariLims.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariLims.setMnemonic('2');
        BtnCariLims.setToolTipText("Alt+2");
        BtnCariLims.setName("BtnCariLims"); // NOI18N
        BtnCariLims.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariLims.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariLimsActionPerformed(evt);
            }
        });
        BtnCariLims.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariLimsKeyPressed(evt);
            }
        });
        panelisi1.add(BtnCariLims);

        BtnKeluarLims.setForeground(new java.awt.Color(0, 0, 0));
        BtnKeluarLims.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluarLims.setMnemonic('K');
        BtnKeluarLims.setText("Keluar");
        BtnKeluarLims.setToolTipText("Alt+K");
        BtnKeluarLims.setName("BtnKeluarLims"); // NOI18N
        BtnKeluarLims.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluarLims.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarLimsActionPerformed(evt);
            }
        });
        BtnKeluarLims.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarLimsKeyPressed(evt);
            }
        });
        panelisi1.add(BtnKeluarLims);

        internalFrame8.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        WindowCariMyLims.getContentPane().add(internalFrame8, java.awt.BorderLayout.CENTER);

        WindowCariLab.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowCariLab.setName("WindowCariLab"); // NOI18N
        WindowCariLab.setUndecorated(true);
        WindowCariLab.setResizable(false);
        WindowCariLab.getContentPane().setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Cari Data Lab ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        internalFrame9.setName("internalFrame9"); // NOI18N
        internalFrame9.setWarnaBawah(new java.awt.Color(245, 250, 240));
        internalFrame9.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi2.setName("panelisi2"); // NOI18N
        panelisi2.setPreferredSize(new java.awt.Dimension(44, 54));
        panelisi2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(69, 23));
        panelisi2.add(label10);

        TCariLab.setName("TCariLab"); // NOI18N
        TCariLab.setPreferredSize(new java.awt.Dimension(120, 23));
        TCariLab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariLabKeyPressed(evt);
            }
        });
        panelisi2.add(TCariLab);

        BtnCariLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariLab.setMnemonic('2');
        BtnCariLab.setToolTipText("Alt+2");
        BtnCariLab.setName("BtnCariLab"); // NOI18N
        BtnCariLab.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariLabActionPerformed(evt);
            }
        });
        BtnCariLab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariLabKeyPressed(evt);
            }
        });
        panelisi2.add(BtnCariLab);

        BtnKeluarLab.setForeground(new java.awt.Color(0, 0, 0));
        BtnKeluarLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluarLab.setMnemonic('K');
        BtnKeluarLab.setText("Keluar");
        BtnKeluarLab.setToolTipText("Alt+K");
        BtnKeluarLab.setName("BtnKeluarLab"); // NOI18N
        BtnKeluarLab.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnKeluarLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarLabActionPerformed(evt);
            }
        });
        BtnKeluarLab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarLabKeyPressed(evt);
            }
        });
        panelisi2.add(BtnKeluarLab);

        internalFrame9.add(panelisi2, java.awt.BorderLayout.PAGE_END);

        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setOpaque(true);

        tbTarif.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbTarif.setName("tbTarif"); // NOI18N
        tbTarif.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTarifMouseClicked(evt);
            }
        });
        scrollPane2.setViewportView(tbTarif);

        internalFrame9.add(scrollPane2, java.awt.BorderLayout.CENTER);

        WindowCariLab.getContentPane().add(internalFrame9, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Mapping Unit RS & MyLims ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbJnsPerawatan.setAutoCreateRowSorter(true);
        tbJnsPerawatan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbJnsPerawatan.setName("tbJnsPerawatan"); // NOI18N
        tbJnsPerawatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbJnsPerawatanMouseClicked(evt);
            }
        });
        tbJnsPerawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbJnsPerawatanKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbJnsPerawatan);

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

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setEnabled(false);
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
        panelGlass8.add(BtnAll);

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

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(450, 23));
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(75, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(LCount);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 44));
        FormInput.setLayout(null);

        jLabel4.setText("Unit RS :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 56, 23);

        kdpoli.setEditable(false);
        kdpoli.setHighlighter(null);
        kdpoli.setName("kdpoli"); // NOI18N
        FormInput.add(kdpoli);
        kdpoli.setBounds(130, 10, 70, 23);

        TPoli.setEditable(false);
        TPoli.setHighlighter(null);
        TPoli.setName("TPoli"); // NOI18N
        FormInput.add(TPoli);
        TPoli.setBounds(200, 10, 190, 23);

        btnPoliRS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPoliRS.setMnemonic('1');
        btnPoliRS.setToolTipText("Alt+1");
        btnPoliRS.setName("btnPoliRS"); // NOI18N
        btnPoliRS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPoliRSActionPerformed(evt);
            }
        });
        btnPoliRS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPoliRSKeyPressed(evt);
            }
        });
        FormInput.add(btnPoliRS);
        btnPoliRS.setBounds(390, 10, 28, 23);

        jLabel19.setText("Unit Lims :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(420, 10, 70, 23);

        KdPoliBPJS.setEditable(false);
        KdPoliBPJS.setHighlighter(null);
        KdPoliBPJS.setName("KdPoliBPJS"); // NOI18N
        FormInput.add(KdPoliBPJS);
        KdPoliBPJS.setBounds(500, 10, 70, 23);

        NmPoliBPJS.setEditable(false);
        NmPoliBPJS.setName("NmPoliBPJS"); // NOI18N
        FormInput.add(NmPoliBPJS);
        NmPoliBPJS.setBounds(570, 10, 190, 23);

        btnPoliBPJS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPoliBPJS.setMnemonic('3');
        btnPoliBPJS.setToolTipText("ALt+3");
        btnPoliBPJS.setName("btnPoliBPJS"); // NOI18N
        btnPoliBPJS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPoliBPJSActionPerformed(evt);
            }
        });
        FormInput.add(btnPoliBPJS);
        btnPoliBPJS.setBounds(760, 10, 28, 23);

        kdpoli1.setEditable(false);
        kdpoli1.setHighlighter(null);
        kdpoli1.setName("kdpoli1"); // NOI18N
        FormInput.add(kdpoli1);
        kdpoli1.setBounds(59, 10, 70, 23);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPoliRSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoliRSActionPerformed
        WindowCariLab.setLocationRelativeTo(internalFrame1);
        WindowCariLab.setVisible(true);
        tampiltarif();
}//GEN-LAST:event_btnPoliRSActionPerformed

    private void btnPoliRSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPoliRSKeyPressed
        
}//GEN-LAST:event_btnPoliRSKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(kdpoli.getText().trim().equals("")||TPoli.getText().trim().equals("")){
            Valid.textKosong(kdpoli,"Unit RS");
        }else if(KdPoliBPJS.getText().trim().equals("")||NmPoliBPJS.getText().trim().equals("")){
            Valid.textKosong(KdPoliBPJS,"Unit BPJS");
        }else{
            query = "INSERT INTO mapping_lab_mylims (kd_jenis_prw,id_template,nm_pemeriksaan,kdlab,nama_lab) VALUES ('"+kdpoli1.getText()+"','"+kdpoli.getText()+"','"+TPoli.getText()+"','"+KdPoliBPJS.getText()+"','"+NmPoliBPJS.getText()+"')";
            Sequel.queryu(query);
            tampil();
            emptTeks();          
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{Valid.pindah(evt,btnPoliBPJS, BtnBatal);}
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        Valid.hapusTable(tabMode,kdpoli,"mapping_lab_mylims","id_template");
        tampil();
        emptTeks();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(kdpoli.getText().trim().equals("")||TPoli.getText().trim().equals("")){
            Valid.textKosong(kdpoli,"Unit RS");
        }else if(KdPoliBPJS.getText().trim().equals("")||NmPoliBPJS.getText().trim().equals("")){
            Valid.textKosong(KdPoliBPJS,"Unit BPJS");
        }else{
            if(tbJnsPerawatan.getSelectedRow()>-1){
//                if(Sequel.mengedittf("maping_poli_bpjs","kd_poli_rs=?","kd_poli_rs=?,kd_poli_bpjs=?,nm_poli_bpjs=?",4,new String[]{
//                        kdpoli.getText(),KdPoliBPJS.getText(),NmPoliBPJS.getText(),tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),0).toString()
//                    })==true){
//                    emptTeks();
//                    tampil();
//                }
            }                
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){            
//                Map<String, Object> param = new HashMap<>();    
//                param.put("namars",var.getnamars());
//                param.put("alamatrs",var.getalamatrs());
//                param.put("kotars",var.getkabupatenrs());
//                param.put("propinsirs",var.getpropinsirs());
//                param.put("kontakrs",var.getkontakrs());
//                param.put("emailrs",var.getemailrs());   
//                param.put("logo",Sequel.cariGambar("select logo from setting")); 
//                Valid.MyReport("rptMapingPoliBPJS.jrxml","report","::[ Mapping Unit RS & BPJS ]::",
//                   "select maping_poli_bpjs.kd_poli_rs,poliklinik.nm_poli,maping_poli_bpjs.kd_poli_bpjs,maping_poli_bpjs.nm_poli_bpjs "+
//                   "from maping_poli_bpjs inner join poliklinik on maping_poli_bpjs.kd_poli_rs=poliklinik.kd_poli where "+
//                   "maping_poli_bpjs.kd_poli_rs like '%"+TCari.getText().trim()+"%' or poliklinik.nm_poli like '%"+TCari.getText().trim()+"%' or maping_poli_bpjs.kd_poli_bpjs like '%"+TCari.getText().trim()+"%' or maping_poli_bpjs.nm_poli_bpjs like '%"+TCari.getText().trim()+"%' order by poliklinik.nm_poli",param);            
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnKeluar);
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
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbJnsPerawatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbJnsPerawatanMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbJnsPerawatanMouseClicked

private void btnPoliBPJSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoliBPJSActionPerformed
    WindowCariMyLims.setLocationRelativeTo(internalFrame1);
    WindowCariMyLims.setVisible(true);
    tampilFaskes1();
}//GEN-LAST:event_btnPoliBPJSActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
        emptTeks();
    }//GEN-LAST:event_formWindowOpened

    private void tbJnsPerawatanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJnsPerawatanKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbJnsPerawatanKeyReleased

    private void tbFaskes1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbFaskes1MouseClicked
        if (tabMode1.getRowCount() != 0) {
            try {
                getDataFK1();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbFaskes1MouseClicked

    private void tbFaskes1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbFaskes1KeyPressed
        if (tabMode1.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataFK1();
                } catch (java.lang.NullPointerException e) {
                }
            }
            WindowCariMyLims.dispose();
        }
    }//GEN-LAST:event_tbFaskes1KeyPressed

    private void BtnKeluarLimsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarLimsActionPerformed
        WindowCariMyLims.dispose();
    }//GEN-LAST:event_BtnKeluarLimsActionPerformed

    private void BtnKeluarLimsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarLimsKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnKeluarLimsKeyPressed

    private void BtnKeluarLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarLabActionPerformed
        WindowCariLab.dispose();
    }//GEN-LAST:event_BtnKeluarLabActionPerformed

    private void BtnKeluarLabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarLabKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnKeluarLabKeyPressed

    private void tbTarifMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTarifMouseClicked
        if(tabMode2.getRowCount()!=0){
            try {
                getDataFK2();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbTarifMouseClicked

    private void TCariLabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariLabKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariLabActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCariLab.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluarLab.requestFocus();
        }
    }//GEN-LAST:event_TCariLabKeyPressed

    private void BtnCariLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariLabActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        tampiltarif();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCariLabActionPerformed

    private void BtnCariLabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariLabKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariLabActionPerformed(null);
        }else{
            Valid.pindah(evt, TCariLab, BtnAll);
        }
    }//GEN-LAST:event_BtnCariLabKeyPressed

    private void BtnCariLimsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariLimsActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        tampilFaskes1();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCariLimsActionPerformed

    private void BtnCariLimsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariLimsKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariLimsActionPerformed(null);
        }else{
            Valid.pindah(evt, TCariLims, BtnAll);
        }
    }//GEN-LAST:event_BtnCariLimsKeyPressed

    private void TCariLimsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariLimsKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariLimsActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCariLims.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluarLims.requestFocus();
        }
    }//GEN-LAST:event_TCariLimsKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            MyLimsMapping dialog = new MyLimsMapping(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCariLab;
    private widget.Button BtnCariLims;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluarLab;
    private widget.Button BtnKeluarLims;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.PanelBiasa FormInput;
    private widget.TextBox KdPoliBPJS;
    private widget.Label LCount;
    private widget.TextBox NmPoliBPJS;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TCariLab;
    private widget.TextBox TCariLims;
    private widget.TextBox TPoli;
    private javax.swing.JDialog WindowCariLab;
    private javax.swing.JDialog WindowCariMyLims;
    private widget.Button btnPoliBPJS;
    private widget.Button btnPoliRS;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame8;
    private widget.InternalFrame internalFrame9;
    private widget.Label jLabel19;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private widget.TextBox kdpoli;
    private widget.TextBox kdpoli1;
    private widget.Label label10;
    private widget.Label label11;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi2;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.Table tbFaskes1;
    private widget.Table tbJnsPerawatan;
    private widget.Table tbTarif;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
           ps=koneksi.prepareStatement(
                   "select kd_jenis_prw,id_template,nm_pemeriksaan,kdlab,nama_lab "+
                   "from mapping_lab_mylims where "+
                   "kd_jenis_prw like ? or nm_pemeriksaan like ? or nama_lab like ? order by kd_jenis_prw");
            try {
                ps.setString(1,"%"+TCari.getText()+"%");
                ps.setString(2,"%"+TCari.getText()+"%");
                ps.setString(3,"%"+TCari.getText()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("kd_jenis_prw"),rs.getString("id_template"),rs.getString("nm_pemeriksaan"),rs.getString("kdlab"),rs.getString("nama_lab")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif Ketersediaan : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        kdpoli.setText("");
        kdpoli1.setText("");
        TPoli.setText("");
        KdPoliBPJS.setText("");
        NmPoliBPJS.setText("");
        kdpoli.requestFocus();
    }

    private void getData() {
       if(tbJnsPerawatan.getSelectedRow()!= -1){
           kdpoli.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),1).toString());
           kdpoli1.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),0).toString());
           TPoli.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),2).toString());
           KdPoliBPJS.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),3).toString());
           NmPoliBPJS.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),4).toString());
        }
    }

    private void getDataFK1() {
        if (tbFaskes1.getSelectedRow() != -1) {
            KdPoliBPJS.setText(tbFaskes1.getValueAt(tbFaskes1.getSelectedRow(), 3).toString());
            NmPoliBPJS.setText(tbFaskes1.getValueAt(tbFaskes1.getSelectedRow(), 2).toString());
        }
    }
    
    private void getDataFK2() {
        if (tbTarif.getSelectedRow() != -1) {
            kdpoli1.setText(tbTarif.getValueAt(tbTarif.getSelectedRow(), 0).toString());
            kdpoli.setText(tbTarif.getValueAt(tbTarif.getSelectedRow(), 1).toString());
            TPoli.setText(tbTarif.getValueAt(tbTarif.getSelectedRow(), 2).toString());
        }
    }
   
    public void tampilFaskes1() {
        try {
            token = "NMjdBfX3WAHU44DMSZwKl8T5WEixAkRoKu97vUm4aPgNDWqDJbMzrcXS0UF1";
            headers = new HttpHeaders();
            headers.add("accept", "application/json");
            headers.add("Content-Type","application/json");
            headers.add("Authorization", "Bearer " + token);
            requestEntity = new HttpEntity(headers);
            URL = "http://192.168.0.11:777/api/v1/referensi";
            requestJson = "{"
                        + "\"method\":\"getCekItem\""
                        + "}";
            System.out.println("JSON : " + requestJson);
            requestEntity = new HttpEntity(requestJson, headers);
            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
            nameNode = root.path("sukses");
//            System.out.println(nameNode.asText());
            if (nameNode.asText().equals("1")) {
                Valid.tabelKosong(tabMode1);
                response = root.path("data");
//                System.out.println("JSON : " + response);
//                response = root.path("response");
                if (response.isArray()) {
                    i = 1;
                    for (JsonNode list : response) {
                        if (list.path("id").asText().toLowerCase().contains(TCariLims.getText().toLowerCase())
                                || list.path("nama").asText().toLowerCase().contains(TCariLims.getText().toLowerCase()) 
                                || list.path("kdlab").asText().toLowerCase().contains(TCariLims.getText().toLowerCase())) {
                            tabMode1.addRow(new Object[]{
                                i + ".", list.path("id").asText(), list.path("nama").asText().trim(), list.path("kdlab").asText().trim(),list.path("grup1").asText().trim()
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
    
    private void tampiltarif() {     
        try{
            Valid.tabelKosong(tabMode2);
            pstindakan=koneksi.prepareStatement(
                "select kd_jenis_prw,id_template,Pemeriksaan "+
                "from template_laboratorium where kd_jenis_prw like ? or Pemeriksaan like ? "+
                "order by kd_jenis_prw");
            try {
                pstindakan.setString(1, "%"+TCariLab.getText()+"%");
                pstindakan.setString(2, "%"+TCariLab.getText()+"%");
                rstindakan=pstindakan.executeQuery();                       
                while(rstindakan.next()){                
                    tabMode2.addRow(new Object[]{rstindakan.getString(1),rstindakan.getString(2),rstindakan.getString(3)});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rstindakan!=null){
                    rstindakan.close();
                }
                if(pstindakan!=null){
                    pstindakan.close();
                }                
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    
    public void isCek(){
        BtnSimpan.setEnabled(var.getmapping_poli_bpjs());
        BtnHapus.setEnabled(var.getmapping_poli_bpjs());
        BtnEdit.setEnabled(var.getmapping_poli_bpjs());
        BtnPrint.setEnabled(var.getmapping_poli_bpjs());
    }
    
    public JTable getTable(){
        return tbJnsPerawatan;
    }    

   
    
    
    

    
}
