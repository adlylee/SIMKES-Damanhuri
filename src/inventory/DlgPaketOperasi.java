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
package inventory;

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
public final class DlgPaketOperasi extends javax.swing.JDialog {

    private final DefaultTableModel tabModeResep, tabMode1;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private PreparedStatement psresep, pscarikapasitas, psresepasuransi, ps2;
    private ResultSet rsobat, carikapasitas, rs2;
    private double x = 0, y = 0, kenaikan = 0, ttl = 0, ppnobat = 0, jumlahracik = 0, persenracik = 0, kapasitasracik = 0;
    private int i = 0, z = 0, row2 = 0, r = 0;
    private boolean ubah = false;
    private boolean[] pilih;
    private double[] harga, beli, stok, kapasitas, p1, p2, stokgudang, stokranap, stokigd;
    private String[] no, kodebarang, namabarang, kodesatuan, kandungan, letakbarang, namajenis, aturan, jumlah;
    public DlgBarang barang = new DlgBarang(null, false);
    public DlgAturanPakai aturanpakai = new DlgAturanPakai(null, false);
    private WarnaTable2 warna = new WarnaTable2();
    private WarnaTable2 warna2 = new WarnaTable2();
    private WarnaTable2 warna3 = new WarnaTable2();
    private DlgMetodeRacik metoderacik = new DlgMetodeRacik(null, false);
    public DlgCariDokter dokter = new DlgCariDokter(null, false);
    private String noracik = "", tampilkan_ppnobat_ralan = "", status = "", bangsal = "", kamar = "", norawatibu = "", kelas, bangsaldefault = Sequel.cariIsi("select kd_bangsal from set_lokasi limit 1");

    /**
     * Creates new form DlgPenyakit
     *
     * @param parent
     * @param modal
     */
    public DlgPaketOperasi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8, 1);
        WindowPilihData.setSize(874, 250);

        tabModeResep = new DefaultTableModel(null, new Object[]{
            "K", "Jumlah", "Kode Barang", "Nama Barang"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if ((colIndex == 0) || (colIndex == 1)) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
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
        for (i = 0; i < 4; i++) {
            TableColumn column = tbResep.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(45);
            } else if (i == 2) {
                column.setPreferredWidth(100);
            } else if (i == 3) {
                column.setPreferredWidth(250);
            }
        }
        tbResep.setDefaultRenderer(Object.class, new WarnaTable());

        tabMode1 = new DefaultTableModel(null, new String[]{"ID", "Kode Paket", "Nama Paket", "Kode Dokter", "Dokter"}) {
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
//                column.setMinWidth(0);
//                column.setMaxWidth(0);
            } else if (i == 1) {
                column.setPreferredWidth(125);
            } else if (i == 2) {
                column.setPreferredWidth(90);
            } else if (i == 4) {//sembunyi
//                column.setPreferredWidth(200);
//                column.setMinWidth(0);
//                column.setMaxWidth(0);
                column.setPreferredWidth(90);
            } else if (i == 3) {//sembunyi
                column.setPreferredWidth(200);
//                column.setMinWidth(0);
//                column.setMaxWidth(0);
            }
        }
        tbFaskes1.setDefaultRenderer(Object.class, new WarnaTable());

        TCari.setDocument(new batasInput((byte) 100).getKata(TCari));

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
                    KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                    NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                }
                KdDokter.requestFocus();
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
        BtnKeluar1 = new widget.Button();
        internalFrame1 = new widget.InternalFrame();
        panelisi3 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        BtnBatal = new widget.Button();
        BtnSimpan = new widget.Button();
        BtnKeluar = new widget.Button();
        FormInput = new widget.PanelBiasa();
        TkdPaket = new widget.TextBox();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        jLabel3 = new widget.Label();
        jLabel13 = new widget.Label();
        btnDokter = new widget.Button();
        btnDokter1 = new widget.Button();
        TNmPaket = new widget.TextBox();
        cmbBangsal = new widget.ComboBox();
        jLabel14 = new widget.Label();
        Scroll = new widget.ScrollPane();
        tbResep = new widget.Table();

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

        internalFrame8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pilih Data ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
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

        BtnKeluar1.setForeground(new java.awt.Color(0, 0, 0));
        BtnKeluar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar1.setMnemonic('K');
        BtnKeluar1.setText("Keluar");
        BtnKeluar1.setToolTipText("Alt+K");
        BtnKeluar1.setName("BtnKeluar1"); // NOI18N
        BtnKeluar1.setPreferredSize(new java.awt.Dimension(100, 30));
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Paket Obat Operasi Dokter ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi3.add(label9);

        TCari.setToolTipText("Alt+C");
        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(245, 23));
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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("Alt+2");
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
        panelisi3.add(BtnAll);

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

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        panelisi3.add(BtnSimpan);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('5');
        BtnKeluar.setToolTipText("Alt+5");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        panelisi3.add(BtnKeluar);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(440, 107));
        FormInput.setLayout(null);

        TkdPaket.setHighlighter(null);
        TkdPaket.setName("TkdPaket"); // NOI18N
        TkdPaket.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TkdPaketKeyPressed(evt);
            }
        });
        FormInput.add(TkdPaket);
        TkdPaket.setBounds(75, 10, 120, 23);

        KdDokter.setHighlighter(null);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokterKeyPressed(evt);
            }
        });
        FormInput.add(KdDokter);
        KdDokter.setBounds(75, 40, 120, 23);

        NmDokter.setEditable(false);
        NmDokter.setHighlighter(null);
        NmDokter.setName("NmDokter"); // NOI18N
        FormInput.add(NmDokter);
        NmDokter.setBounds(195, 40, 230, 23);

        jLabel3.setText("Nama Paket :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 10, 72, 23);

        jLabel13.setText("Dokter :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 40, 72, 23);

        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter.setMnemonic('3');
        btnDokter.setToolTipText("Alt+3");
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        btnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDokterKeyPressed(evt);
            }
        });
        FormInput.add(btnDokter);
        btnDokter.setBounds(428, 40, 28, 23);

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
        btnDokter1.setBounds(428, 10, 28, 23);

        TNmPaket.setHighlighter(null);
        TNmPaket.setName("TNmPaket"); // NOI18N
        TNmPaket.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNmPaketKeyPressed(evt);
            }
        });
        FormInput.add(TNmPaket);
        TNmPaket.setBounds(195, 10, 230, 23);

        cmbBangsal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "B0014 APOTEK RAJAL", "B0001 APOTEK RANAP", "B0018 APOTEK IGD", "B0002 GUDANG FARMASI" }));
        cmbBangsal.setName("cmbBangsal"); // NOI18N
        cmbBangsal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBangsalActionPerformed(evt);
            }
        });
        cmbBangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbBangsalKeyPressed(evt);
            }
        });
        FormInput.add(cmbBangsal);
        cmbBangsal.setBounds(80, 70, 210, 23);

        jLabel14.setText("Depo :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(0, 70, 72, 23);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

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

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        BtnCariActionPerformed(evt);
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnAllActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnCari, TCari);
        }
}//GEN-LAST:event_BtnAllKeyPressed

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
                    var.setform("DlgCariObat");
                    aturanpakai.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    aturanpakai.setLocationRelativeTo(internalFrame1);
                    aturanpakai.setVisible(true);
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

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
    if (TkdPaket.getText().trim().equals("")) {
        Valid.textKosong(TkdPaket, "Kode Paket");
    } else if (KdDokter.getText().trim().equals("") || NmDokter.getText().trim().equals("")) {
        Valid.textKosong(KdDokter, "Dokter");
    } else {
        int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, udah bener belum data yang mau disimpan..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            if(ubah==false){
                System.out.println(ubah);
                if (Sequel.menyimpantf2("eresep_paket_operasi (kd_paket,nama_paket,kd_dokter,status)", "'" + TkdPaket.getText() + "','" + TNmPaket.getText() + "','" + KdDokter.getText() + "','1'", "") == true) {
                    simpandata();
                    batalFungsi();
//                    JOptionPane.showMessageDialog(null, "Berhasil Simpan Resep");
                }
            }
            if(ubah==true){
                System.out.println(ubah);
                ubah = false;
                simpandata();
                batalFungsi();
            }
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
        batalFungsi();
        tampilObat();
    }//GEN-LAST:event_formWindowOpened

    private void TkdPaketKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TkdPaketKeyPressed

    }//GEN-LAST:event_TkdPaketKeyPressed

    private void KdDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokterKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", NmDokter, KdDokter.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnDokterActionPerformed(null);
        } else {

        }
    }//GEN-LAST:event_KdDokterKeyPressed

    private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.isCek();
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokterActionPerformed

    private void btnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDokterKeyPressed
        Valid.pindah(evt, KdDokter, BtnSimpan);
    }//GEN-LAST:event_btnDokterKeyPressed

    private void tbResepPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbResepPropertyChange

    }//GEN-LAST:event_tbResepPropertyChange

    private void ppStok1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppStok1ActionPerformed

    }//GEN-LAST:event_ppStok1ActionPerformed

    private void btnDokter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokter1ActionPerformed
        WindowPilihData.setLocationRelativeTo(internalFrame1);
        WindowPilihData.setVisible(true);
        tampilListData();
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

    private void TNmPaketKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNmPaketKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNmPaketKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        batalFungsi();
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnBatalActionPerformed(null);
        }
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void cmbBangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbBangsalKeyPressed
        
    }//GEN-LAST:event_cmbBangsalKeyPressed

    private void cmbBangsalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBangsalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbBangsalActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPaketOperasi dialog = new DlgPaketOperasi(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluar1;
    private widget.Button BtnSimpan;
    private widget.PanelBiasa FormInput;
    private widget.TextBox KdDokter;
    private widget.Label LPpn;
    private widget.TextBox NmDokter;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNmPaket;
    private widget.TextBox TkdPaket;
    private javax.swing.JDialog WindowPilihData;
    private widget.Button btnDokter;
    private widget.Button btnDokter1;
    private widget.ComboBox cmbBangsal;
    private widget.TextBox idPaket;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame8;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel3;
    private widget.Label jLabel6;
    private widget.Label label9;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppStok1;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbFaskes1;
    private widget.Table tbResep;
    // End of variables declaration//GEN-END:variables

    public void tampilObat() {
        z=0;
        for(i=0;i<tbResep.getRowCount();i++){
            if(!tbResep.getValueAt(i,0).toString().equals("")){
                z++;
            }
        }    
        pilih=null;
        pilih=new boolean[z]; 
        jumlah=null;
        jumlah=new String[z];
        kodebarang=null;
        kodebarang=new String[z];
        namabarang=null;
        namabarang=new String[z];
        z=0;        
        for(i=0;i<tbResep.getRowCount();i++){
            if(!tbResep.getValueAt(i,1).toString().equals("")){
                pilih[z]=Boolean.parseBoolean(tbResep.getValueAt(i,0).toString());                
                jumlah[z]=tbResep.getValueAt(i,1).toString();
                kodebarang[z]=tbResep.getValueAt(i,2).toString();
                namabarang[z]=tbResep.getValueAt(i,3).toString();
                z++;
            }
        }
        
        Valid.tabelKosong(tabModeResep);             
        
        for(i=0;i<z;i++){
            tabModeResep.addRow(new Object[] {
                pilih[i],jumlah[i],kodebarang[i],namabarang[i]
            });
        }
        try {
            psresep = koneksi.prepareStatement(
                    "select databarang.kode_brng, databarang.nama_brng "
                    + " from databarang inner join gudangbarang "
                    + " on databarang.kode_brng=gudangbarang.kode_brng "
                    + " where  databarang.status='1' and gudangbarang.kd_bangsal=? and databarang.kode_brng like ? or "
                    + " databarang.status='1' and gudangbarang.kd_bangsal=? and databarang.nama_brng like ? ");
            try {
                psresep.setString(1, cmbBangsal.getSelectedItem().toString().substring(0,5));
                psresep.setString(2, "%" + TCari.getText().trim() + "%");
                psresep.setString(3, cmbBangsal.getSelectedItem().toString().substring(0,5));
                psresep.setString(4, "%" + TCari.getText().trim() + "%");
                rsobat = psresep.executeQuery();
                while (rsobat.next()) {
                    tabModeResep.addRow(new Object[]{false, "", rsobat.getString("kode_brng"), rsobat.getString("nama_brng")
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

    public void tampilObatEdit(String id) {
        ubah = false;
        try {
            Valid.tabelKosong(tabModeResep);
            psresep = koneksi.prepareStatement(
                    "select databarang.kode_brng, databarang.nama_brng, databarang.kode_sat,databarang.letak_barang,eresep_paket_operasi_template.jumlah "
                    + " from databarang INNER JOIN eresep_paket_operasi_template "
                    + " on databarang.kode_brng=eresep_paket_operasi_template.kd_obat "
                    + " where databarang.status='1' and eresep_paket_operasi_template.id_paket = ? ");
            try {
                psresep.setString(1, id);
                rsobat = psresep.executeQuery();
                while (rsobat.next()) {
                    tabModeResep.addRow(new Object[]{true, rsobat.getString("jumlah"), rsobat.getString("kode_brng"), rsobat.getString("nama_brng")
                    });
                }
                ubah = true;
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
        System.out.println(ubah);
    }

    public void tampilListData() {
        try {
            Valid.tabelKosong(tabMode1);
            psresep = koneksi.prepareStatement(
                    "SELECT * FROM eresep_paket_operasi JOIN dokter ON eresep_paket_operasi.kd_dokter = dokter.kd_dokter");
            try {
                rsobat = psresep.executeQuery();
                while (rsobat.next()) {
                    tabMode1.addRow(new Object[]{rsobat.getString("id"), rsobat.getString("kd_paket"), rsobat.getString("nama_paket"), rsobat.getString("kd_dokter"), rsobat.getString("nm_dokter")
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
        bangsal = bangsaldefault;

    }

    private void simpandata() {
        try {
            koneksi.setAutoCommit(false);
            for (i = 0; i < tbResep.getRowCount(); i++) {
                if (Valid.SetAngka(tbResep.getValueAt(i, 1).toString()) > 0) {
                    if (tbResep.getValueAt(i, 0).toString().equals("true")) {
                        String kd_obat = Sequel.cariIsi("SELECT kd_obat FROM eresep_paket_operasi_template WHERE id_paket = '"+idPaket.getText()+"' AND kd_obat = '"+tbResep.getValueAt(i, 2).toString()+"' AND jumlah = '"+tbResep.getValueAt(i, 1).toString()+"'");
                        if (!kd_obat.isEmpty()) {
                            Sequel.mengedit("eresep_paket_operasi_template","id_paket = '"+idPaket.getText()+"' AND kd_obat = '"+tbResep.getValueAt(i, 2).toString()+"'","jumlah = '"+tbResep.getValueAt(i, 1).toString()+"'");   
                        }
                        if (kd_obat.isEmpty()) {
                            Sequel.menyimpan("eresep_paket_operasi_template (id_paket,kd_obat,jumlah)","'"+idPaket.getText()+"','"+tbResep.getValueAt(i, 2).toString()+"','"+tbResep.getValueAt(i, 1).toString()+"'");   
                        }
                    }
                    if (tbResep.getValueAt(i, 0).toString().equals("false")) {
                        Sequel.meghapus("eresep_paket_operasi_template","id_paket","kd_obat",idPaket.getText(),tbResep.getValueAt(i, 2).toString());
                    }
                }
                tbResep.setValueAt("", i, 1);
            }
            koneksi.setAutoCommit(true);
            System.out.println("Berhasil Simpan Paket Operasi");
            JOptionPane.showMessageDialog(null, "Berhasil Simpan Paket Operasi");
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }
    }

    private void getData() {
        if (tbFaskes1.getSelectedRow() != -1) {
            idPaket.setText(tbFaskes1.getValueAt(tbFaskes1.getSelectedRow(), 0).toString());
            TkdPaket.setText(tbFaskes1.getValueAt(tbFaskes1.getSelectedRow(), 1).toString());
            TNmPaket.setText(tbFaskes1.getValueAt(tbFaskes1.getSelectedRow(), 2).toString());
            KdDokter.setText(tbFaskes1.getValueAt(tbFaskes1.getSelectedRow(), 3).toString());
            NmDokter.setText(tbFaskes1.getValueAt(tbFaskes1.getSelectedRow(), 4).toString());
            tampilObatEdit(idPaket.getText());
        }
    }

    private void empTable() {
        Valid.tabelKosong(tabModeResep);
    }
    
    private void batalFungsi(){
        TkdPaket.setText("");
        TNmPaket.setText("");
        KdDokter.setText("");
        NmDokter.setText("");
        TCari.setText("");
        empTable();
        tampilObat();
        isNumber();
        cmbBangsal.setSelectedIndex(0);
    }
    
    private void isNumber() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(kd_paket,4),signed)),0) from eresep_paket_operasi", "EOP", 4, TkdPaket);
    }
}
