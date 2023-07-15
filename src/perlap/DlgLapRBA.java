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
package perlap;

import inventory.*;
import fungsi.WarnaTable;
import fungsi.WarnaTable2;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import widget.Button;

/**
 *
 * @author dosen
 */
public final class DlgLapRBA extends javax.swing.JDialog {

    private final DefaultTableModel tabModeResep, tabMode1, tabMode2;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private PreparedStatement psresep, pscarikapasitas, psresepasuransi, ps2;
    private ResultSet rsobat, carikapasitas, rs2;
    private double x = 0, y = 0, kenaikan = 0, ttl = 0, ppnobat = 0, jumlahracik = 0, persenracik = 0, kapasitasracik = 0, ttlselisih = 0;
    private int i = 0, z = 0, row2 = 0, r = 0;
    private boolean ubah = false;
    private boolean[] jumlah;
    private String[] koderek, uraian, anggaran, pergeseran, selisih;
    public DlgBarang barang = new DlgBarang(null, false);
    private WarnaTable2 warna = new WarnaTable2();
    private WarnaTable2 warna2 = new WarnaTable2();
    private WarnaTable2 warna3 = new WarnaTable2();

    /**
     * Creates new form DlgPenyakit
     *
     * @param parent
     * @param modal
     */
    public DlgLapRBA(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8, 1);
        WindowPilihData.setSize(874, 250);
        WindowPilihData1.setSize(874, 250);

        tabModeResep = new DefaultTableModel(null, new Object[]{
            "Kode Akun", "Uraian", "Anggaran", "Pergeseran", "Selisih"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if ((colIndex == 0)) {
                    a = false;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbResep.setModel(tabModeResep);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbResep.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbResep.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 5; i++) {
            TableColumn column = tbResep.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(75);
            } else if (i == 1) {
                column.setPreferredWidth(300);
            } else {
                column.setPreferredWidth(100);
            }
        }
        tbResep.setDefaultRenderer(Object.class, new WarnaTable());

        tabMode1 = new DefaultTableModel(null, new String[]{"Kode Akun", "Uraian"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbFaskes1.setModel(tabMode1);
        tbFaskes1.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbFaskes1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 2; i++) {
            TableColumn column = tbFaskes1.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(100);
            } else if (i == 1) {
                column.setPreferredWidth(400);
            }
        }
        tbFaskes1.setDefaultRenderer(Object.class, new WarnaTable());

        tabMode2 = new DefaultTableModel(null, new String[]{"Kode Akun", "Uraian"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbFaskes2.setModel(tabMode2);
        tbFaskes2.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbFaskes2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 2; i++) {
            TableColumn column = tbFaskes2.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(100);
            } else if (i == 1) {
                column.setPreferredWidth(400);
            }
        }
        tbFaskes2.setDefaultRenderer(Object.class, new WarnaTable());

        TPergeseran.setDocument(new batasInput((byte) 25).getOnlyAngka(TPergeseran));
        TCari.setDocument(new batasInput((byte) 100).getKata(TCari));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        ppStok1 = new javax.swing.JMenuItem();
        idPaket = new widget.TextBox();
        LPpn = new widget.Label();
        jLabel6 = new widget.Label();
        WindowPilihData = new javax.swing.JDialog();
        internalFrame8 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbFaskes1 = new widget.Table();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari1 = new widget.TextBox();
        BtnCari1 = new widget.Button();
        BtnKeluar1 = new widget.Button();
        WindowPilihData1 = new javax.swing.JDialog();
        internalFrame9 = new widget.InternalFrame();
        scrollPane2 = new widget.ScrollPane();
        tbFaskes2 = new widget.Table();
        panelisi2 = new widget.panelisi();
        label11 = new widget.Label();
        TCari2 = new widget.TextBox();
        BtnCari2 = new widget.Button();
        BtnKeluar2 = new widget.Button();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbResep = new widget.Table();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.PanelBiasa();
        kdrekdari = new widget.TextBox();
        jLabel3 = new widget.Label();
        btnDokter1 = new widget.Button();
        nmrekdari = new widget.TextBox();
        TPergeseran = new widget.TextBox();
        jLabel14 = new widget.Label();
        jLabel4 = new widget.Label();
        kdrekke = new widget.TextBox();
        nmrekke = new widget.TextBox();
        btnDokter2 = new widget.Button();
        jLabel16 = new widget.Label();
        Tanggal = new widget.Tanggal();
        jLabel18 = new widget.Label();
        TKeterangan = new widget.TextBox();
        jPanel2 = new javax.swing.JPanel();
        panelisi4 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnGanti = new widget.Button();
        BtnAll = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelisi3 = new widget.panelisi();
        jLabel15 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel17 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnBatal = new widget.Button();

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(70, 70, 70));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkan.setText("Bersihkan Jumlah");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setIconTextGap(8);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        ppStok1.setBackground(new java.awt.Color(255, 255, 254));
        ppStok1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppStok1.setForeground(new java.awt.Color(70, 70, 70));
        ppStok1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppStok1.setText("Tampilkan Stok Depo");
        ppStok1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppStok1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppStok1.setIconTextGap(8);
        ppStok1.setName("ppStok1"); // NOI18N
        ppStok1.setPreferredSize(new java.awt.Dimension(200, 25));
        ppStok1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppStok1ActionPerformed(evt);
            }
        });
        Popup.add(ppStok1);

        idPaket.setHighlighter(null);
        idPaket.setName("idPaket"); // NOI18N

        LPpn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LPpn.setText("0");
        LPpn.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LPpn.setName("LPpn"); // NOI18N
        LPpn.setPreferredSize(new java.awt.Dimension(65, 23));

        jLabel6.setText("PPN :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(35, 23));

        WindowPilihData.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowPilihData.setName("WindowPilihData"); // NOI18N
        WindowPilihData.setUndecorated(true);
        WindowPilihData.setResizable(false);
        WindowPilihData.getContentPane().setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pilih Rekening Awal ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
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

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi1.add(label10);

        TCari1.setToolTipText("Alt+C");
        TCari1.setName("TCari1"); // NOI18N
        TCari1.setPreferredSize(new java.awt.Dimension(200, 23));
        TCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCari1KeyPressed(evt);
            }
        });
        panelisi1.add(TCari1);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('1');
        BtnCari1.setToolTipText("Alt+1");
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
        panelisi1.add(BtnCari1);

        BtnKeluar1.setForeground(new java.awt.Color(0, 0, 0));
        BtnKeluar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar1.setMnemonic('K');
        BtnKeluar1.setText("Keluar");
        BtnKeluar1.setToolTipText("Alt+K");
        BtnKeluar1.setName("BtnKeluar1"); // NOI18N
        BtnKeluar1.setPreferredSize(new java.awt.Dimension(140, 30));
        BtnKeluar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar1ActionPerformed(evt);
            }
        });
        BtnKeluar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluar1KeyPressed(evt);
            }
        });
        panelisi1.add(BtnKeluar1);

        internalFrame8.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        WindowPilihData.getContentPane().add(internalFrame8, java.awt.BorderLayout.CENTER);

        WindowPilihData1.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowPilihData1.setName("WindowPilihData1"); // NOI18N
        WindowPilihData1.setUndecorated(true);
        WindowPilihData1.setResizable(false);
        WindowPilihData1.getContentPane().setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pilih Rekening Tujuan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        internalFrame9.setName("internalFrame9"); // NOI18N
        internalFrame9.setWarnaBawah(new java.awt.Color(245, 250, 240));
        internalFrame9.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setOpaque(true);

        tbFaskes2.setToolTipText("Klik data di tabel");
        tbFaskes2.setName("tbFaskes2"); // NOI18N
        tbFaskes2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbFaskes2MouseClicked(evt);
            }
        });
        tbFaskes2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbFaskes2KeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(tbFaskes2);

        internalFrame9.add(scrollPane2, java.awt.BorderLayout.CENTER);

        panelisi2.setName("panelisi2"); // NOI18N
        panelisi2.setPreferredSize(new java.awt.Dimension(44, 54));
        panelisi2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Key Word :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi2.add(label11);

        TCari2.setToolTipText("Alt+C");
        TCari2.setName("TCari2"); // NOI18N
        TCari2.setPreferredSize(new java.awt.Dimension(200, 23));
        TCari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCari2KeyPressed(evt);
            }
        });
        panelisi2.add(TCari2);

        BtnCari2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari2.setMnemonic('1');
        BtnCari2.setToolTipText("Alt+1");
        BtnCari2.setName("BtnCari2"); // NOI18N
        BtnCari2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari2ActionPerformed(evt);
            }
        });
        BtnCari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari2KeyPressed(evt);
            }
        });
        panelisi2.add(BtnCari2);

        BtnKeluar2.setForeground(new java.awt.Color(0, 0, 0));
        BtnKeluar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar2.setMnemonic('K');
        BtnKeluar2.setText("Keluar");
        BtnKeluar2.setToolTipText("Alt+K");
        BtnKeluar2.setName("BtnKeluar2"); // NOI18N
        BtnKeluar2.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar2ActionPerformed(evt);
            }
        });
        BtnKeluar2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluar2KeyPressed(evt);
            }
        });
        panelisi2.add(BtnKeluar2);

        internalFrame9.add(panelisi2, java.awt.BorderLayout.PAGE_END);

        WindowPilihData1.getContentPane().add(internalFrame9, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rencana Bisnis Anggaran ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setComponentPopupMenu(Popup);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbResep.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbResep.setComponentPopupMenu(Popup);
        tbResep.setName("tbResep"); // NOI18N
        tbResep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbResepMouseClicked(evt);
            }
        });
        tbResep.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbResepPropertyChange(evt);
            }
        });
        tbResep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbResepKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbResep);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 94));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('M');
        ChkInput.setText(".: Input Data");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(190, 77));
        FormInput.setLayout(null);

        kdrekdari.setHighlighter(null);
        kdrekdari.setName("kdrekdari"); // NOI18N
        kdrekdari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdrekdariKeyPressed(evt);
            }
        });
        FormInput.add(kdrekdari);
        kdrekdari.setBounds(55, 10, 120, 23);

        jLabel3.setText("Dari :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 10, 52, 23);

        btnDokter1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter1.setMnemonic('3');
        btnDokter1.setToolTipText("Alt+3");
        btnDokter1.setName("btnDokter1"); // NOI18N
        btnDokter1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokter1ActionPerformed(evt);
            }
        });
        btnDokter1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDokter1KeyPressed(evt);
            }
        });
        FormInput.add(btnDokter1);
        btnDokter1.setBounds(408, 10, 28, 23);

        nmrekdari.setHighlighter(null);
        nmrekdari.setName("nmrekdari"); // NOI18N
        nmrekdari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nmrekdariKeyPressed(evt);
            }
        });
        FormInput.add(nmrekdari);
        nmrekdari.setBounds(175, 10, 230, 23);

        TPergeseran.setHighlighter(null);
        TPergeseran.setName("TPergeseran"); // NOI18N
        TPergeseran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPergeseranKeyPressed(evt);
            }
        });
        FormInput.add(TPergeseran);
        TPergeseran.setBounds(560, 10, 340, 23);

        jLabel14.setText("Pergeseran :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(480, 10, 72, 23);

        jLabel4.setText("Ke :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 40, 52, 23);

        kdrekke.setHighlighter(null);
        kdrekke.setName("kdrekke"); // NOI18N
        kdrekke.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdrekkeKeyPressed(evt);
            }
        });
        FormInput.add(kdrekke);
        kdrekke.setBounds(55, 40, 120, 23);

        nmrekke.setHighlighter(null);
        nmrekke.setName("nmrekke"); // NOI18N
        nmrekke.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nmrekkeKeyPressed(evt);
            }
        });
        FormInput.add(nmrekke);
        nmrekke.setBounds(175, 40, 230, 23);

        btnDokter2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter2.setMnemonic('3');
        btnDokter2.setToolTipText("Alt+3");
        btnDokter2.setName("btnDokter2"); // NOI18N
        btnDokter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokter2ActionPerformed(evt);
            }
        });
        btnDokter2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDokter2KeyPressed(evt);
            }
        });
        FormInput.add(btnDokter2);
        btnDokter2.setBounds(408, 40, 28, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(480, 40, 72, 23);

        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "13-07-2023" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.setPreferredSize(new java.awt.Dimension(95, 23));
        FormInput.add(Tanggal);
        Tanggal.setBounds(560, 40, 90, 23);

        jLabel18.setText("Keterangan :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(650, 40, 72, 23);

        TKeterangan.setHighlighter(null);
        TKeterangan.setName("TKeterangan"); // NOI18N
        TKeterangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TKeteranganActionPerformed(evt);
            }
        });
        TKeterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKeteranganKeyPressed(evt);
            }
        });
        FormInput.add(TKeterangan);
        TKeterangan.setBounds(725, 40, 180, 24);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 23));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        panelisi4.add(BtnSimpan);

        BtnGanti.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/file-edit-16x16.png"))); // NOI18N
        BtnGanti.setMnemonic('P');
        BtnGanti.setText("Ganti");
        BtnGanti.setToolTipText("Alt+P");
        BtnGanti.setName("BtnGanti"); // NOI18N
        BtnGanti.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnGanti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGantiActionPerformed(evt);
            }
        });
        BtnGanti.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnGantiKeyPressed(evt);
            }
        });
        panelisi4.add(BtnGanti);

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
        panelisi4.add(BtnAll);

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
        panelisi4.add(BtnHapus);

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
        panelisi4.add(BtnPrint);

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi4.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(73, 23));
        panelisi4.add(LCount);

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
        panelisi4.add(BtnKeluar);

        jPanel2.add(panelisi4, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        jLabel15.setText("Tanggal :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(jLabel15);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "13-07-2023" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelisi3.add(DTPCari1);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("s.d");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(24, 23));
        panelisi3.add(jLabel17);

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "13-07-2023" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelisi3.add(DTPCari2);

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi3.add(label9);

        TCari.setToolTipText("Alt+C");
        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(240, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
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
        panelisi3.add(BtnCari);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setIconTextGap(3);
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelisi3.add(BtnBatal);

        jPanel2.add(panelisi3, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            tbResep.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampilObat();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void tbResepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbResepMouseClicked
        if (tbResep.getRowCount() != 0) {

        }
}//GEN-LAST:event_tbResepMouseClicked

    private void tbResepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbResepKeyPressed
        if (tbResep.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
                i = tbResep.getSelectedColumn();
                if ((i == 1) || (i == 8)) {
                    if (tbResep.getSelectedRow() != -1) {
                        tbResep.setValueAt("", tbResep.getSelectedRow(), i);
                    }
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
                i = tbResep.getSelectedColumn();
                if (i != 11) {
                    TCari.requestFocus();
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
                i = tbResep.getSelectedColumn();
                if (i == 8) {
//                    var.setform("DlgCariObat");
//                    aturanpakai.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
//                    aturanpakai.setLocationRelativeTo(internalFrame1);
//                    aturanpakai.setVisible(true);
                } else if (i == 2) {
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                i = tbResep.getSelectedColumn();
                if ((i == 8) || (i == 9)) {
                    TCari.requestFocus();
                }
            }
        }
}//GEN-LAST:event_tbResepKeyPressed

private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
    LocalDate today = LocalDate.now();
    if (kdrekdari.getText().trim().equals("") || nmrekdari.getText().equals("")) {
        Valid.textKosong(kdrekdari, "Kode Rekening");
    } else if (TPergeseran.getText().equals("")) {
        Valid.textKosong(TPergeseran, "Jumlah Pergeseran");
    } else {
        int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, udah bener belum data yang mau disimpan..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            try {
                if (Sequel.menyimpantf("rba_perlap",
                        "'" + Valid.SetTgl(Tanggal.getSelectedItem() + "") + "','" + kdrekdari.getText() + "',"
                        + "'" + TPergeseran.getText() + "','" + kdrekke.getText() + "','" + TPergeseran.getText() + "',"
                        + "'" + TKeterangan.getText() + "','" + today + "','" + var.getkode() + "'", "Data") == true) {
                    Sequel.mengedit("rekeningtahun", "kd_rek = '" + kdrekdari.getText() + "'", "saldo_berjalan = saldo_berjalan-" + TPergeseran.getText() + "");
                    Sequel.mengedit("rekeningtahun", "kd_rek = '" + kdrekke.getText() + "'", "saldo_berjalan = saldo_berjalan+" + TPergeseran.getText() + "");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            tampilObat();
            batalFungsi();
            JOptionPane.showMessageDialog(null, "Berhasil Simpan");
        }
    }
}//GEN-LAST:event_BtnSimpanActionPerformed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
    for (i = 0; i < tbResep.getRowCount(); i++) {
        tbResep.setValueAt("", i, 1);
        tbResep.setValueAt(0, i, 10);
        tbResep.setValueAt(0, i, 9);
        tbResep.setValueAt(0, i, 8);
    }
}//GEN-LAST:event_ppBersihkanActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated

    }//GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        ubah = false;
        tampilObat();
    }//GEN-LAST:event_formWindowOpened

    private void kdrekdariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdrekdariKeyPressed

    }//GEN-LAST:event_kdrekdariKeyPressed

    private void tbResepPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbResepPropertyChange

    }//GEN-LAST:event_tbResepPropertyChange

    private void ppStok1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppStok1ActionPerformed

    }//GEN-LAST:event_ppStok1ActionPerformed

    private void btnDokter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokter1ActionPerformed
        WindowPilihData.setLocationRelativeTo(internalFrame1);
        WindowPilihData.setVisible(true);
        tampilListData();
        TCari1.requestFocus();
    }//GEN-LAST:event_btnDokter1ActionPerformed

    private void btnDokter1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDokter1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDokter1KeyPressed

    private void tbFaskes1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbFaskes1MouseClicked
        if (tabMode1.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbFaskes1MouseClicked

    private void tbFaskes1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbFaskes1KeyPressed
        if (tabMode1.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
            WindowPilihData.dispose();
        }
    }//GEN-LAST:event_tbFaskes1KeyPressed

    private void BtnKeluar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar1ActionPerformed
        WindowPilihData.dispose();
    }//GEN-LAST:event_BtnKeluar1ActionPerformed

    private void BtnKeluar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluar1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        }
    }//GEN-LAST:event_BtnKeluar1KeyPressed

    private void nmrekdariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nmrekdariKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nmrekdariKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        batalFungsi();
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnBatalActionPerformed(null);
        }
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void TCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCari1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCari1ActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari1.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar1.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            tbFaskes1.requestFocus();
        }
    }//GEN-LAST:event_TCari1KeyPressed

    private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        tampilListData();
    }//GEN-LAST:event_BtnCari1ActionPerformed

    private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCari1KeyPressed

    private void TPergeseranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPergeseranKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TPergeseranKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed

    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnGantiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGantiActionPerformed

    }//GEN-LAST:event_BtnGantiActionPerformed

    private void BtnGantiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnGantiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnGantiKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampilListData();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnAllActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed

    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnAll, BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed

    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void kdrekkeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdrekkeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kdrekkeKeyPressed

    private void nmrekkeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nmrekkeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_nmrekkeKeyPressed

    private void btnDokter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokter2ActionPerformed
        WindowPilihData1.setLocationRelativeTo(internalFrame1);
        WindowPilihData1.setVisible(true);
        tampilListData2();
        TCari2.requestFocus();
    }//GEN-LAST:event_btnDokter2ActionPerformed

    private void btnDokter2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDokter2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDokter2KeyPressed

    private void TKeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKeteranganKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TKeteranganKeyPressed

    private void TKeteranganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TKeteranganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TKeteranganActionPerformed

    private void BtnKeluar2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluar2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        }
    }//GEN-LAST:event_BtnKeluar2KeyPressed

    private void BtnKeluar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar2ActionPerformed
        WindowPilihData1.dispose();
    }//GEN-LAST:event_BtnKeluar2ActionPerformed

    private void tbFaskes2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbFaskes2KeyPressed
        if (tabMode2.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData2();
                } catch (java.lang.NullPointerException e) {
                }
            }
            WindowPilihData1.dispose();
        }
    }//GEN-LAST:event_tbFaskes2KeyPressed

    private void tbFaskes2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbFaskes2MouseClicked
        if (tabMode2.getRowCount() != 0) {
            try {
                getData2();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbFaskes2MouseClicked

    private void TCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCari2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCari2ActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari2.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar2.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            tbFaskes2.requestFocus();
        }
    }//GEN-LAST:event_TCari2KeyPressed

    private void BtnCari2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari2ActionPerformed
        tampilListData2();
    }//GEN-LAST:event_BtnCari2ActionPerformed

    private void BtnCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCari2KeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgLapRBA dialog = new DlgLapRBA(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari1;
    private widget.Button BtnCari2;
    private widget.Button BtnGanti;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluar1;
    private widget.Button BtnKeluar2;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.Label LCount;
    private widget.Label LPpn;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TCari1;
    private widget.TextBox TCari2;
    private widget.TextBox TKeterangan;
    private widget.TextBox TPergeseran;
    private widget.Tanggal Tanggal;
    private javax.swing.JDialog WindowPilihData;
    private javax.swing.JDialog WindowPilihData1;
    private widget.Button btnDokter1;
    private widget.Button btnDokter2;
    private widget.TextBox idPaket;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame8;
    private widget.InternalFrame internalFrame9;
    private widget.Label jLabel10;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private javax.swing.JPanel jPanel2;
    private widget.TextBox kdrekdari;
    private widget.TextBox kdrekke;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label9;
    private widget.TextBox nmrekdari;
    private widget.TextBox nmrekke;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi2;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppStok1;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.Table tbFaskes1;
    private widget.Table tbFaskes2;
    private widget.Table tbResep;
    // End of variables declaration//GEN-END:variables

    public void tampilObat() {
        z = 0;
        for (i = 0; i < tbResep.getRowCount(); i++) {
            if (!tbResep.getValueAt(i, 0).toString().equals("")) {
                z++;
            }
        }
        koderek = null;
        koderek = new String[z];
        uraian = null;
        uraian = new String[z];
        anggaran = null;
        anggaran = new String[z];
        pergeseran = null;
        pergeseran = new String[z];
        selisih = null;
        selisih = new String[z];
        z = 0;
        for (i = 0; i < tbResep.getRowCount(); i++) {
            if (!tbResep.getValueAt(i, 1).toString().equals("")) {
                koderek[z] = tbResep.getValueAt(i, 0).toString();
                uraian[z] = tbResep.getValueAt(i, 1).toString();
                anggaran[z] = tbResep.getValueAt(i, 2).toString();
                pergeseran[z] = tbResep.getValueAt(i, 3).toString();
                selisih[z] = tbResep.getValueAt(i, 4).toString();
                z++;
            }
        }

        Valid.tabelKosong(tabModeResep);

        for (i = 0; i < z; i++) {
            tabModeResep.addRow(new Object[]{
                koderek[i], uraian[i], anggaran[i], pergeseran[i], selisih[i]
            });
        }
        try {
            psresep = koneksi.prepareStatement(
                    "select rekening.kd_rek,rekening.nm_rek, rekeningtahun.saldo_awal, rekeningtahun.saldo_berjalan from rekeningtahun inner join rekening "
                    + "on rekeningtahun.kd_rek=rekening.kd_rek "
                    + "where rekening.kd_rek like '5%' and rekening.balance='K'");
            try {
                rsobat = psresep.executeQuery();
                while (rsobat.next()) {
                    tabModeResep.addRow(new Object[]{rsobat.getString("kd_rek"), rsobat.getString("nm_rek"), rsobat.getString("saldo_awal"), rsobat.getString("saldo_berjalan"), 0
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rsobat != null) {
                    rsobat.close();
                }

                if (psresep != null) {
                    psresep.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        getSelisih();
    }

    public void tampilListData() {
        try {
            Valid.tabelKosong(tabMode1);
            psresep = koneksi.prepareStatement(
                    "SELECT * FROM rekening where kd_rek like '5%' and balance='K' and nm_rek like ?");
            try {
                psresep.setString(1, "%" + TCari1.getText().trim() + "%");
                rsobat = psresep.executeQuery();
                while (rsobat.next()) {
                    tabMode1.addRow(new Object[]{rsobat.getString("kd_rek"), rsobat.getString("nm_rek")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rsobat != null) {
                    rsobat.close();
                }

                if (psresep != null) {
                    psresep.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    public void tampilListData2() {
        try {
            Valid.tabelKosong(tabMode2);
            psresep = koneksi.prepareStatement(
                    "SELECT * FROM rekening where kd_rek like '5%' and balance='K' and nm_rek like ?");
            try {
                psresep.setString(1, "%" + TCari2.getText().trim() + "%");
                rsobat = psresep.executeQuery();
                while (rsobat.next()) {
                    tabMode2.addRow(new Object[]{rsobat.getString("kd_rek"), rsobat.getString("nm_rek")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rsobat != null) {
                    rsobat.close();
                }

                if (psresep != null) {
                    psresep.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    public JTable getTable() {
        return tbResep;
    }

    public Button getButton() {
        return BtnSimpan;
    }

    public void isCek() {
        TCari.requestFocus();
    }

    private void getData() {
        if (tbFaskes1.getSelectedRow() != -1) {
            kdrekdari.setText(tbFaskes1.getValueAt(tbFaskes1.getSelectedRow(), 0).toString());
            nmrekdari.setText(tbFaskes1.getValueAt(tbFaskes1.getSelectedRow(), 1).toString());
        }
    }

    private void getData2() {
        if (tbFaskes2.getSelectedRow() != -1) {
            kdrekke.setText(tbFaskes2.getValueAt(tbFaskes2.getSelectedRow(), 0).toString());
            nmrekke.setText(tbFaskes2.getValueAt(tbFaskes2.getSelectedRow(), 1).toString());
        }
    }

    private void getSelisih() {
        System.out.println("getSelish()");
        i = tbResep.getSelectedRow();
        if (i != -1) {
            if ((tbResep.getSelectedColumn() == 1) || (tbResep.getSelectedColumn() == 0)) {
                try {
                    ttlselisih = 0;
                    try {
                        ttlselisih = Double.parseDouble(tbResep.getValueAt(tbResep.getSelectedRow(), 3).toString())
                                - Double.parseDouble(tbResep.getValueAt(tbResep.getSelectedRow(), 2).toString());
                    } catch (Exception e) {
                        ttlselisih = 0;
                    }
                    tbResep.setValueAt(ttlselisih, i, 4);
                } catch (Exception e) {
                }
            }
        }
    }

    private void empTable() {
        Valid.tabelKosong(tabModeResep);
    }

    private void batalFungsi() {
        kdrekdari.setText("");
        nmrekdari.setText("");
        TPergeseran.setText("");
        kdrekke.setText("");
        nmrekke.setText("");
        TCari.setText("");
//        empTable();
    }

    private void isForm() {
        if (ChkInput.isSelected() == true) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 216));
            FormInput.setVisible(true);
            ChkInput.setVisible(true);
        } else if (ChkInput.isSelected() == false) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 20));
            FormInput.setVisible(false);
            ChkInput.setVisible(true);
        }
    }
}
