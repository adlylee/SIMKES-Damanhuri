package inventory;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;
import simrskhanza.DlgCariBangsal;

public class DlgSirkulasiBarang3 extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private DlgCariBangsal bangsal = new DlgCariBangsal(null, false);
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize(); 
    private String lokasi="";
    private double ttltotaljual=0,totaljual=0,jumlahjual=0,ttltotalbeli=0,totalbeli=0,jumlahbeli=0,
                   ttltotalpesan=0,totalpesan=0,jumlahpesan=0,jumlahutd,totalutd,ttltotalutd,jumlahkeluar,totalkeluar,ttltotalkeluar,
                   ttltotalpiutang=0,totalpiutang=0,jumlahpiutang=0,ttltotalretbeli=0,totalretbeli=0,jumlahretbeli=0,
                   ttltotalretjual=0,totalretjual=0,jumlahretjual=0,ttltotalretpiut=0,totalretpiut=0,jumlahretpiut=0,
                   jumlahpasin=0,totalpasien=0,ttltotalpasien=0,stok=0,aset=0,ttlaset=0,jumlahrespulang=0,totalrespulang=0,
                   ttltotalrespulang=0,jumlahmutasimasuk=0,jumlahmutasikeluar=0,totalmutasimasuk=0,totalmutasikeluar=0,
                   ttltotalmutasimasuk=0,ttltotalmutasikeluar=0,saldo_awal=0,saldo_akhir=0,saldo_masuk=0,saldo_keluar=0,
                   stok_masuk=0,stok_keluar=0,ttlsaldo_masuk=0,ttlsaldo_keluar=0,stoksaldoawal=0,totalsaldoawal=0,stoksaldoakhir=0,totalsaldoakhir=0,ttlstokawal=0,ttlstokmasuk=0,ttlstokkeluar=0,ttlstokakhir=0,
                   Tstokakhir=0,Tttlsaldoakhir=0;
    private DlgBarang barang=new DlgBarang(null,false);
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;

    /** 
     * @param parent
     * @param modal */
    public DlgSirkulasiBarang3(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"Kode Rek","Kode Barang","Nama Barang","Satuan","Harga","Spek","Stok Awal","Stok Masuk","Stok Keluar","Stok Akhir","Saldo Awal","Saldo Masuk","Saldo Keluar","Saldo Akhir"
        };
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 14; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(80);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(250);
            }else if(i==3){
                column.setPreferredWidth(50);
            }else if(i==4){
                column.setPreferredWidth(80);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(70);
            }else if(i==7){
                column.setPreferredWidth(70);
            }else if(i==8){
                column.setPreferredWidth(70);
            }else if(i==9){
                column.setPreferredWidth(70);
            }else if(i==10){
                column.setPreferredWidth(100);
            }else if(i==11){
                column.setPreferredWidth(100);
            }else if(i==12){
                column.setPreferredWidth(100);
            }else if(i==13){
                column.setPreferredWidth(100);
//            }else if(i==12){
//                column.setPreferredWidth(100);
//            }else if(i==13){
//                column.setPreferredWidth(70);
//            }else if(i==14){
//                column.setPreferredWidth(100);
//            }else if(i==15){
//                column.setPreferredWidth(70);
//            }else if(i==16){
//                column.setPreferredWidth(100);
//            }else if(i==17){
//                column.setPreferredWidth(70);
//            }else if(i==18){
//                column.setPreferredWidth(100);
//            }else if(i==19){
//                column.setPreferredWidth(70);
//            }else if(i==20){
//                column.setPreferredWidth(100);
//            }else if(i==21){
//                column.setPreferredWidth(90);
//            }else if(i==22){
//                column.setPreferredWidth(120);
//            }else if(i==23){
//                column.setPreferredWidth(90);
//            }else if(i==24){
//                column.setPreferredWidth(120);
//            }else if(i==25){
//                column.setPreferredWidth(80);
//            }else if(i==26){
//                column.setPreferredWidth(110);
//            }else if(i==27){
//                column.setPreferredWidth(80);
//            }else if(i==28){
//                column.setPreferredWidth(110);
//            }else if(i==29){
//                column.setPreferredWidth(80);
//            }else if(i==30){
//                column.setPreferredWidth(110);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());         
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.cariCepat().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        prosesCari();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        prosesCari();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        prosesCari();
                    }
                }
            });
        }   
        
        barang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgSirkulasiBarang")){
                    if(barang.getTable().getSelectedRow()!= -1){                   
                        kdbar.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(),1).toString());                    
                        nmbar.setText(barang.getTable().getValueAt(barang.getTable().getSelectedRow(),2).toString());
                    }  
                    kdbar.requestFocus();
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
        
        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (bangsal.getTable().getSelectedRow() != -1) {
                    lokasi=bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString();
                    prosesCari2(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(), 0).toString());
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
                bangsal.emptTeks();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        
        barang.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(var.getform().equals("DlgSirkulasiBarang")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        barang.dispose();                    
                    }                
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
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

        Kd2 = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppGrafikJualBanyak = new javax.swing.JMenuItem();
        ppGrafikJualDikit = new javax.swing.JMenuItem();
        ppGrafikbeliBanyak = new javax.swing.JMenuItem();
        ppGrafikbelidikit = new javax.swing.JMenuItem();
        ppGrafikPiutangBanyak = new javax.swing.JMenuItem();
        ppGrafikPiutangDikit = new javax.swing.JMenuItem();
        ppGrafikResepPaliingBanyak = new javax.swing.JMenuItem();
        ppGrafikResepPaliingSedikit = new javax.swing.JMenuItem();
        ppLokasi = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi4 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label17 = new widget.Label();
        kdbar = new widget.TextBox();
        nmbar = new widget.TextBox();
        btnBarang = new widget.Button();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label9 = new widget.Label();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppGrafikJualBanyak.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikJualBanyak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikJualBanyak.setForeground(new java.awt.Color(70, 70, 70));
        ppGrafikJualBanyak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikJualBanyak.setText("Grafik 10 Barang Penjualan Terbanyak");
        ppGrafikJualBanyak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikJualBanyak.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikJualBanyak.setIconTextGap(8);
        ppGrafikJualBanyak.setName("ppGrafikJualBanyak"); // NOI18N
        ppGrafikJualBanyak.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikJualBanyak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikJualBanyakActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikJualBanyak);

        ppGrafikJualDikit.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikJualDikit.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikJualDikit.setForeground(new java.awt.Color(70, 70, 70));
        ppGrafikJualDikit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikJualDikit.setText("Grafik 10 Barang Penjualan Tersedikit");
        ppGrafikJualDikit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikJualDikit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikJualDikit.setIconTextGap(8);
        ppGrafikJualDikit.setName("ppGrafikJualDikit"); // NOI18N
        ppGrafikJualDikit.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikJualDikit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikJualDikitActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikJualDikit);

        ppGrafikbeliBanyak.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikbeliBanyak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikbeliBanyak.setForeground(new java.awt.Color(70, 70, 70));
        ppGrafikbeliBanyak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikbeliBanyak.setText("Grafik 10 Barang Pembelian Terbanyak");
        ppGrafikbeliBanyak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikbeliBanyak.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikbeliBanyak.setIconTextGap(8);
        ppGrafikbeliBanyak.setName("ppGrafikbeliBanyak"); // NOI18N
        ppGrafikbeliBanyak.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikbeliBanyak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikbeliBanyakActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikbeliBanyak);

        ppGrafikbelidikit.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikbelidikit.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikbelidikit.setForeground(new java.awt.Color(70, 70, 70));
        ppGrafikbelidikit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikbelidikit.setText("Grafik 10 Barang Pembelian Tersedikit");
        ppGrafikbelidikit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikbelidikit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikbelidikit.setIconTextGap(8);
        ppGrafikbelidikit.setName("ppGrafikbelidikit"); // NOI18N
        ppGrafikbelidikit.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikbelidikit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikbelidikitActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikbelidikit);

        ppGrafikPiutangBanyak.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikPiutangBanyak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPiutangBanyak.setForeground(new java.awt.Color(70, 70, 70));
        ppGrafikPiutangBanyak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikPiutangBanyak.setText("Grafik 10 Barang Piutang Terbanyak");
        ppGrafikPiutangBanyak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPiutangBanyak.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPiutangBanyak.setIconTextGap(8);
        ppGrafikPiutangBanyak.setName("ppGrafikPiutangBanyak"); // NOI18N
        ppGrafikPiutangBanyak.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikPiutangBanyak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPiutangBanyakActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikPiutangBanyak);

        ppGrafikPiutangDikit.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikPiutangDikit.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPiutangDikit.setForeground(new java.awt.Color(70, 70, 70));
        ppGrafikPiutangDikit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikPiutangDikit.setText("Grafik 10 Barang Piutang Tersedikit");
        ppGrafikPiutangDikit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPiutangDikit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPiutangDikit.setIconTextGap(8);
        ppGrafikPiutangDikit.setName("ppGrafikPiutangDikit"); // NOI18N
        ppGrafikPiutangDikit.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikPiutangDikit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPiutangDikitActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikPiutangDikit);

        ppGrafikResepPaliingBanyak.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikResepPaliingBanyak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikResepPaliingBanyak.setForeground(new java.awt.Color(70, 70, 70));
        ppGrafikResepPaliingBanyak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikResepPaliingBanyak.setText("Grafik 10 Barang Resep Ke Pasien Terbanyak");
        ppGrafikResepPaliingBanyak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikResepPaliingBanyak.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikResepPaliingBanyak.setIconTextGap(8);
        ppGrafikResepPaliingBanyak.setName("ppGrafikResepPaliingBanyak"); // NOI18N
        ppGrafikResepPaliingBanyak.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikResepPaliingBanyak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikResepPaliingBanyakActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikResepPaliingBanyak);

        ppGrafikResepPaliingSedikit.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikResepPaliingSedikit.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikResepPaliingSedikit.setForeground(new java.awt.Color(70, 70, 70));
        ppGrafikResepPaliingSedikit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikResepPaliingSedikit.setText("Grafik 10 Barang Resep Ke Pasien Tersedikit");
        ppGrafikResepPaliingSedikit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikResepPaliingSedikit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikResepPaliingSedikit.setIconTextGap(8);
        ppGrafikResepPaliingSedikit.setName("ppGrafikResepPaliingSedikit"); // NOI18N
        ppGrafikResepPaliingSedikit.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikResepPaliingSedikit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikResepPaliingSedikitActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikResepPaliingSedikit);

        ppLokasi.setBackground(new java.awt.Color(255, 255, 254));
        ppLokasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppLokasi.setForeground(new java.awt.Color(70, 70, 70));
        ppLokasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppLokasi.setText("Tampilkan Per Lokasi");
        ppLokasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppLokasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppLokasi.setIconTextGap(8);
        ppLokasi.setName("ppLokasi"); // NOI18N
        ppLokasi.setPreferredSize(new java.awt.Dimension(180, 25));
        ppLokasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppLokasiBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppLokasi);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Sirkulasi Obat, Alkes & BHP Medis Keluar Masuk ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(jPopupMenu1);
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
        tbDokter.setComponentPopupMenu(jPopupMenu1);
        tbDokter.setName("tbDokter"); // NOI18N
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tanggal Transaksi :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(113, 23));
        panelisi4.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(110, 23));
        panelisi4.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi4.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(110, 23));
        panelisi4.add(Tgl2);

        label17.setText("Barang :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(85, 23));
        panelisi4.add(label17);

        kdbar.setName("kdbar"); // NOI18N
        kdbar.setPreferredSize(new java.awt.Dimension(80, 23));
        kdbar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdbarKeyPressed(evt);
            }
        });
        panelisi4.add(kdbar);

        nmbar.setEditable(false);
        nmbar.setName("nmbar"); // NOI18N
        nmbar.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nmbar);

        btnBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBarang.setMnemonic('1');
        btnBarang.setToolTipText("Alt+1");
        btnBarang.setName("btnBarang"); // NOI18N
        btnBarang.setPreferredSize(new java.awt.Dimension(28, 23));
        btnBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarangActionPerformed(evt);
            }
        });
        panelisi4.add(btnBarang);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(69, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(300, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

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
        panelisi1.add(BtnCari);

        label9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(79, 30));
        panelisi1.add(label9);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('A');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+A");
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
        BtnPrint.setMnemonic('P');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+P");
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
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            
            Sequel.queryu("delete from temporary");
            int row=tabMode.getRowCount();
            for(int i=0;i<row;i++){  
                Sequel.menyimpan("temporary","'0',?,?,?,?,?,?,?,?,?,?,?,?,?,?,'','','','','','','','','','','','','','','','','','','','','','',''"
//                        + ",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'','','','','',''",31,new String[]{
                            ,14,new String[]{
                    tabMode.getValueAt(i,0).toString(),tabMode.getValueAt(i,1).toString(),tabMode.getValueAt(i,2).toString(),
                    tabMode.getValueAt(i,3).toString(),tabMode.getValueAt(i,4).toString(),tabMode.getValueAt(i,5).toString(),
                    tabMode.getValueAt(i,6).toString(),tabMode.getValueAt(i,7).toString(),tabMode.getValueAt(i,8).toString(),
                    tabMode.getValueAt(i,9).toString(),tabMode.getValueAt(i,10).toString()
                    ,tabMode.getValueAt(i,11).toString(),tabMode.getValueAt(i,12).toString(),tabMode.getValueAt(i,13).toString()
//                    tabMode.getValueAt(i,14).toString(),
//                    tabMode.getValueAt(i,15).toString(),tabMode.getValueAt(i,16).toString(),tabMode.getValueAt(i,17).toString(),
//                    tabMode.getValueAt(i,18).toString(),tabMode.getValueAt(i,19).toString(),tabMode.getValueAt(i,20).toString(),
//                    tabMode.getValueAt(i,21).toString(),tabMode.getValueAt(i,22).toString(),tabMode.getValueAt(i,23).toString(),
//                    tabMode.getValueAt(i,24).toString(),tabMode.getValueAt(i,25).toString(),tabMode.getValueAt(i,26).toString(),
//                    tabMode.getValueAt(i,27).toString(),tabMode.getValueAt(i,28).toString(),tabMode.getValueAt(i,29).toString(),
//                    tabMode.getValueAt(i,30).toString()
                }); 
            }
            
            Map<String, Object> param = new HashMap<>(); 
                param.put("namars",var.getnamars());
                param.put("alamatrs",var.getalamatrs());
                param.put("kotars",var.getkabupatenrs());
                param.put("propinsirs",var.getpropinsirs());
                param.put("kontakrs",var.getkontakrs());
                param.put("emailrs",var.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting"));
                param.put("tanggal1",Valid.SetTgl(Tgl1.getSelectedItem()+""));  
                param.put("tanggal2",Valid.SetTgl(Tgl2.getSelectedItem()+""));  
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if(lokasi.equals("")){
                Valid.MyReport("rptSirkulasi5.jrxml","report","::[ Stock Off Name Barang ]::",
                    "select * from temporary order by no asc",param);
            }else if(!lokasi.equals("")){
                param.put("bangsal",lokasi);  
                Valid.MyReport("rptSirkulasi6.jrxml","report","::[ Stock Off Name Barang ]::",
                    "select * from temporary order by no asc",param);
            }
            this.setCursor(Cursor.getDefaultCursor());
        }        
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,Tgl2,BtnKeluar);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,Tgl1);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

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
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        lokasi="";
        prosesCari();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void kdbarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?",nmbar,kdbar.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?",nmbar,kdbar.getText());
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?",nmbar,kdbar.getText());
            TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnBarangActionPerformed(null);
        }
    }//GEN-LAST:event_kdbarKeyPressed

    private void btnBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarangActionPerformed
        var.setform("DlgSirkulasiBarang");
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
    }//GEN-LAST:event_btnBarangActionPerformed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        kdbar.setText("");
        nmbar.setText("");
        lokasi="";
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        prosesCari();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void ppGrafikJualBanyakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikJualBanyakActionPerformed
        
    }//GEN-LAST:event_ppGrafikJualBanyakActionPerformed

    private void ppGrafikJualDikitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikJualDikitActionPerformed
        
    }//GEN-LAST:event_ppGrafikJualDikitActionPerformed

    private void ppGrafikbeliBanyakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikbeliBanyakActionPerformed
        
    }//GEN-LAST:event_ppGrafikbeliBanyakActionPerformed

    private void ppGrafikbelidikitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikbelidikitActionPerformed
        
    }//GEN-LAST:event_ppGrafikbelidikitActionPerformed

    private void ppGrafikPiutangBanyakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPiutangBanyakActionPerformed
        
    }//GEN-LAST:event_ppGrafikPiutangBanyakActionPerformed

    private void ppGrafikPiutangDikitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPiutangDikitActionPerformed
        
    }//GEN-LAST:event_ppGrafikPiutangDikitActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
    }//GEN-LAST:event_formWindowOpened

    private void ppGrafikResepPaliingBanyakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikResepPaliingBanyakActionPerformed
        
    }//GEN-LAST:event_ppGrafikResepPaliingBanyakActionPerformed

    private void ppGrafikResepPaliingSedikitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikResepPaliingSedikitActionPerformed
        
    }//GEN-LAST:event_ppGrafikResepPaliingSedikitActionPerformed

    private void ppLokasiBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppLokasiBtnPrintActionPerformed
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth() - 40, internalFrame1.getHeight() - 40);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setVisible(true);
    }//GEN-LAST:event_ppLokasiBtnPrintActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgSirkulasiBarang3 dialog = new DlgSirkulasiBarang3(new javax.swing.JFrame(), true);
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
    private widget.TextBox Kd2;
    private widget.TextBox TCari;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.Button btnBarang;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdbar;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label9;
    private widget.TextBox nmbar;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppGrafikJualBanyak;
    private javax.swing.JMenuItem ppGrafikJualDikit;
    private javax.swing.JMenuItem ppGrafikPiutangBanyak;
    private javax.swing.JMenuItem ppGrafikPiutangDikit;
    private javax.swing.JMenuItem ppGrafikResepPaliingBanyak;
    private javax.swing.JMenuItem ppGrafikResepPaliingSedikit;
    private javax.swing.JMenuItem ppGrafikbeliBanyak;
    private javax.swing.JMenuItem ppGrafikbelidikit;
    private javax.swing.JMenuItem ppLokasi;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void prosesCari() {
       Valid.tabelKosong(tabMode); 
       tabMode.addRow(new Object[]{"","","","","","","","","","","","","",""}); 
       try{   
            saldo_awal = Sequel.cariIsiAngka("SELECT saldo_awal FROM rekeningtahun WHERE kd_rek = '11070101'");
            ps=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng, "+
                        "kodesatuan.satuan , databarang.h_beli from databarang inner join kodesatuan   "+
                        "on databarang.kode_sat=kodesatuan.kode_sat "+
                        "where databarang.status='1' and databarang.nama_brng like ? and databarang.kode_brng like ? or "+
                        "databarang.status='1' and databarang.nama_brng like ? and databarang.nama_brng like ? or "+
                        "databarang.status='1' and databarang.nama_brng like ? and kodesatuan.satuan like ? "+
                        " order by databarang.kode_brng");
            try {
                ttltotaljual=0;ttltotalbeli=0;ttltotalpesan=0;
                ttltotalpiutang=0;ttltotalretbeli=0;ttltotalretjual=0;
                ttltotalretpiut=0;ttltotalpasien=0;ttlaset=0;
                ttltotalutd=0;ttltotalkeluar=0;ttltotalrespulang=0;
                ttltotalmutasikeluar=0;ttltotalmutasimasuk=0;
                ttlstokawal=0;ttlstokmasuk=0;ttlstokkeluar=0;ttlstokakhir=0;Tttlsaldoakhir=0;
                ps.setString(1,"%"+nmbar.getText()+"%");
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,"%"+nmbar.getText()+"%");
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                ps.setString(5,"%"+nmbar.getText()+"%");
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();            
                while(rs.next()){
                    totaljual=0;jumlahjual=0;totalbeli=0;jumlahbeli=0;totalpiutang=0;jumlahpiutang=0;
                    totalpesan=0;jumlahpesan=0;jumlahrespulang=0;
                    totalretbeli=0;jumlahretbeli=0;totalretjual=0;jumlahretjual=0;totalretpiut=0;jumlahretpiut=0;
                    jumlahpasin=0;stok=0;aset=0;totalrespulang=0;stoksaldoawal=0;totalsaldoawal=0;
                    jumlahutd=0;jumlahkeluar=0;totalkeluar=0;totalutd=0;
                    jumlahmutasikeluar=0;totalmutasikeluar=0;jumlahmutasimasuk=0;totalmutasimasuk=0;stoksaldoakhir=0;totalsaldoakhir=0;Tstokakhir=0;
                    
                    ps2=koneksi.prepareStatement("select sum(stok_akhir),(sum(stok_akhir)*h_beli) as aset "+
                        " from riwayat_barang_medis inner join databarang on riwayat_barang_medis.kode_brng=databarang.kode_brng "+
                        " where riwayat_barang_medis.kode_brng=? AND riwayat_barang_medis.tanggal "+
                        " between ? and ? AND riwayat_barang_medis.posisi='Opname' AND riwayat_barang_medis.status='Simpan'");
                    try {
                        LocalDate theday = LocalDate.parse(Valid.SetTgl(Tgl1.getSelectedItem()+""));
//                        System.out.println(theday);
                        LocalDate lastweek = theday.minus(2, ChronoUnit.WEEKS);
                        LocalDate nextweek = theday.plus(2, ChronoUnit.WEEKS);
//                        System.out.println("Last week: " + lastweek);
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,lastweek.toString());
                        ps2.setString(3,nextweek.toString());
//                        ps2.setString(3,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            stoksaldoawal=rs2.getDouble(1);
                            totalsaldoawal=rs2.getDouble(1) * rs.getDouble(4);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Stok : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }

                    ps2=koneksi.prepareStatement("select sum(stok),(sum(stok)*h_beli) as aset "+
                        "from gudangbarang inner join databarang on gudangbarang.kode_brng=databarang.kode_brng "+
                        "where gudangbarang.kode_brng=?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            stok=rs2.getDouble(1);
                            aset=rs2.getDouble(1) * rs.getDouble(4);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Stok : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }

                    //pembelian  
                    ps2=koneksi.prepareStatement("select sum(detailbeli.jumlah2), sum(detailbeli.subtotal) "+
                        " from pembelian inner join detailbeli "+
                        " on pembelian.no_faktur=detailbeli.no_faktur "+
                        " where detailbeli.kode_brng=? and pembelian.tgl_beli "+
                        " between ? and ? ");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahbeli=rs2.getDouble(1);
                            totalbeli=rs2.getDouble(1) * rs.getDouble(4);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Detail Beli : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }          
                    
                    //pemesanan
                    ps2=koneksi.prepareStatement("select sum(detailpesan.jumlah2), sum(detailpesan.subtotal) "+
                        " from pemesanan inner join detailpesan "+
                        " on pemesanan.no_faktur=detailpesan.no_faktur "+
                        " where detailpesan.kode_brng=? and pemesanan.tgl_pesan "+
                        " between ? and ? ");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahpesan=rs2.getDouble(1);
                            totalpesan=rs2.getDouble(1) * rs.getDouble(4);
                        }
                    } catch (Exception e) {
                        System.out.println("Pemesanan : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                        
                    //penjualan
                    ps2=koneksi.prepareStatement("select sum(detailjual.jumlah), sum(detailjual.total) "+
                        " from penjualan inner join detailjual "+
                        " on penjualan.nota_jual=detailjual.nota_jual "+
                        " where detailjual.kode_brng=? and "+
                        " penjualan.tgl_jual  between ? and ? ");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahjual=rs2.getDouble(1);
                            totaljual=rs2.getDouble(1) * rs.getDouble(4);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Penjualan : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }

                    //piutang  
                    ps2=koneksi.prepareStatement("select sum(detailpiutang.jumlah), sum(detailpiutang.total) "+
                        " from piutang inner join detailpiutang "+
                        " on piutang.nota_piutang=detailpiutang.nota_piutang "+
                        " where detailpiutang.kode_brng=? and "+
                        " piutang.tgl_piutang between ? and ? ");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahpiutang=rs2.getDouble(1);
                            totalpiutang=rs2.getDouble(1) * rs.getDouble(4);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Piutang : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }

                    //returbeli
                    ps2=koneksi.prepareStatement("select sum(detreturbeli.jml_retur2), sum(detreturbeli.total) "+
                        " from returbeli inner join detreturbeli "+
                        " on returbeli.no_retur_beli=detreturbeli.no_retur_beli "+
                        " where detreturbeli.kode_brng=? and "+
                        " returbeli.tgl_retur between ? and ? ");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahretbeli=rs2.getDouble(1);
                            totalretbeli=rs2.getDouble(1) * rs.getDouble(4);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Retur Beli : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }

                    //returjual
                    ps2=koneksi.prepareStatement("select sum(detreturjual.jml_retur), sum(detreturjual.subtotal) "+
                        " from returjual inner join detreturjual "+
                        " on returjual.no_retur_jual=detreturjual.no_retur_jual "+
                        " where detreturjual.kode_brng=? and "+
                        " returjual.tgl_retur between ? and ? ");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahretjual=rs2.getDouble(1);
                            totalretjual=rs2.getDouble(1) * rs.getDouble(4);
                        } 
                    } catch (Exception e) {
                        System.out.println("Notifikasi Retur Jual : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }               

                    ps2=koneksi.prepareStatement("select sum(detreturpiutang.jml_retur), sum(detreturpiutang.subtotal) "+
                        " from returpiutang inner join detreturpiutang "+
                        " on returpiutang.no_retur_piutang=detreturpiutang.no_retur_piutang "+
                        " where detreturpiutang.kode_brng=? and "+
                        " returpiutang.tgl_retur between ? and ?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahretpiut=rs2.getDouble(1);
                            totalretpiut=rs2.getDouble(1) * rs.getDouble(4);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikas Retur Piutang : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    //pemberian
                    ps2=koneksi.prepareStatement("select sum(detail_pemberian_obat.jml) as jumlah, "+
                        "(sum(detail_pemberian_obat.total)-sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as jumpas "+
                        " from detail_pemberian_obat where detail_pemberian_obat.kode_brng=? and "+
                        " detail_pemberian_obat.tgl_perawatan between ? and ?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahpasin=rs2.getDouble(1);
                            totalpasien=rs2.getDouble(1) * rs.getDouble(4);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Pemberian Obat : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    ps2=koneksi.prepareStatement("select sum(utd_pengambilan_medis.jml) as jumlah, "+
                        "sum(utd_pengambilan_medis.total) as jumpas "+
                        " from utd_pengambilan_medis where utd_pengambilan_medis.kode_brng=? and "+
                        " utd_pengambilan_medis.tanggal between ? and ?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahutd=rs2.getDouble(1);
                            totalutd=rs2.getDouble(1) * rs.getDouble(4);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi UTD : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    //stok keluar
                    ps2=koneksi.prepareStatement("select sum(detail_pengeluaran_obat_bhp.jumlah), sum(detail_pengeluaran_obat_bhp.total) "+
                        " from pengeluaran_obat_bhp inner join detail_pengeluaran_obat_bhp "+
                        " on pengeluaran_obat_bhp.no_keluar=detail_pengeluaran_obat_bhp.no_keluar "+
                        " where detail_pengeluaran_obat_bhp.kode_brng=? and "+
                        " pengeluaran_obat_bhp.tanggal between ? and ?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahkeluar=rs2.getDouble(1);
                            totalkeluar=rs2.getDouble(1) * rs.getDouble(4);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikas keluar : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    //resep pulang
                    ps2=koneksi.prepareStatement("select sum(resep_pulang.jml_barang), sum(resep_pulang.total) "+
                        " from resep_pulang where resep_pulang.kode_brng=? and "+
                        " resep_pulang.tanggal between ? and ?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahrespulang=rs2.getDouble(1);
                            totalrespulang=rs2.getDouble(1) * rs.getDouble(4);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikas Resep Pulang : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    //stokakhir (gudangbarang)
                    ps2=koneksi.prepareStatement("select sum(gudangbarang.stok) from gudangbarang where "+
                        "gudangbarang.kd_bangsal in ('B0001','B0002','B0014','B0018') and gudangbarang.kode_brng=?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            Tstokakhir=rs2.getDouble(1);
                            Tttlsaldoakhir=rs2.getDouble(1)* rs.getDouble(4);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikas Stok Akhir : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    if((aset>0)||(jumlahbeli>0)||(jumlahpesan>0)||(jumlahjual>0)||(jumlahpasin>0)||(jumlahpiutang>0)||
                            (jumlahretbeli>0)||(jumlahretjual>0)||(jumlahretpiut>0)||(jumlahutd>0)||(jumlahkeluar>0)||
                            (jumlahrespulang>0)||(jumlahmutasimasuk>0)||(jumlahmutasikeluar>0)){
                        stok_masuk = jumlahbeli + jumlahpesan;
//                        + jumlahretjual;
                        saldo_masuk = totalbeli + totalpesan;
//                        + totalretjual;
//                        stok_keluar = jumlahjual + jumlahpasin + jumlahkeluar + jumlahrespulang;
                        stok_keluar = stoksaldoawal + stok_masuk - Tstokakhir;//rumusapotek
//                         + jumlahretbeli 
//                        saldo_keluar = totaljual + totalpasien + totalkeluar + totalrespulang;
                        saldo_keluar = totalsaldoawal+saldo_masuk-Tttlsaldoakhir;
//                         + totalretbeli
                        stoksaldoakhir = stoksaldoawal + stok_masuk;
                        stoksaldoakhir = stoksaldoakhir - stok_keluar;
                        totalsaldoakhir = totalsaldoawal + saldo_masuk ;
                        totalsaldoakhir = totalsaldoakhir - saldo_keluar;
                        
                        tabMode.addRow(new Object[]{"510299102","",rs.getString(2),
                           rs.getString(3),Valid.SetAngka(rs.getDouble(4)),Sequel.cariIsi("select kategori_barang.nama from databarang "+
                           "inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode where databarang.kode_brng='" + rs.getString(1) + "'"),
                           Valid.SetAngka(stoksaldoawal),Valid.SetAngka(stok_masuk),Valid.SetAngka(stok_keluar),
//                           Valid.SetAngka(stoksaldoakhir)
                                Valid.SetAngka(Tstokakhir),
                                Valid.SetAngka(totalsaldoawal),
                           Valid.SetAngka(saldo_masuk),Valid.SetAngka(saldo_keluar),Valid.SetAngka(Tttlsaldoakhir)
//                           Valid.SetAngka(totalsaldoakhir)//saldoakhirreal
                           
//                           Valid.SetAngka(jumlahbeli),Valid.SetAngka(totalbeli),
//                           Valid.SetAngka(jumlahpesan),Valid.SetAngka(totalpesan),
//                           Valid.SetAngka(jumlahjual),Valid.SetAngka(totaljual),
//                           Valid.SetAngka(jumlahpasin),Valid.SetAngka(totalpasien),
//                           Valid.SetAngka(jumlahpiutang),Valid.SetAngka(totalpiutang),
//                           Valid.SetAngka(jumlahretbeli),Valid.SetAngka(totalretbeli),
//                           Valid.SetAngka(jumlahretjual),Valid.SetAngka(totalretjual),
//                           Valid.SetAngka(jumlahretpiut),Valid.SetAngka(totalretpiut),
//                           Valid.SetAngka(jumlahutd),Valid.SetAngka(totalutd),
//                           Valid.SetAngka(jumlahkeluar),Valid.SetAngka(totalkeluar),
//                           Valid.SetAngka(jumlahrespulang),Valid.SetAngka(totalrespulang),
//                           Valid.SetAngka(jumlahmutasimasuk),Valid.SetAngka(totalmutasimasuk),
//                           Valid.SetAngka(jumlahmutasikeluar),Valid.SetAngka(totalmutasikeluar)
                        }); 
                        ttlstokawal=ttlstokawal + stoksaldoawal;
                        ttlstokmasuk=ttlstokmasuk + stok_masuk;
                        ttlstokkeluar=ttlstokkeluar + stok_keluar;
                        ttlstokakhir=ttlstokakhir+stoksaldoakhir;
                        
                        ttltotalbeli=ttltotalbeli+totalbeli;
                        ttltotalpesan=ttltotalpesan+totalpesan;
                        ttltotaljual=ttltotaljual+totaljual;
                        ttlaset=ttlaset+aset;
                        ttltotalpasien=ttltotalpasien+totalpasien;
                        ttltotalpiutang=ttltotalpiutang+totalpiutang;
                        ttltotalretbeli=ttltotalretbeli+totalretbeli;
                        ttltotalretjual=ttltotalretjual+totalretjual;
                        ttltotalretpiut=ttltotalretpiut+totalretpiut;
                        ttltotalutd=ttltotalutd+totalutd;
                        ttltotalkeluar=ttltotalkeluar+totalkeluar;
                        ttltotalrespulang=ttltotalrespulang+totalrespulang;
                        ttltotalmutasimasuk=ttltotalmutasimasuk+totalmutasimasuk;
                        ttltotalmutasikeluar=ttltotalmutasikeluar+totalmutasikeluar;
                    }
                }
                ttlsaldo_masuk = ttltotalbeli + ttltotalpesan;
//                        + ttltotalretjual;
                ttlsaldo_keluar = ttltotaljual + ttltotalkeluar + ttltotalpasien + ttltotalrespulang;
                saldo_akhir = saldo_awal + ttltotalbeli + ttltotalpesan ;
//                        + ttltotalretjual;
                saldo_akhir = saldo_akhir - ttltotaljual - ttltotalkeluar - ttltotalpasien - ttltotalrespulang;
                tabMode.insertRow(0,new Object[]{"","<>>","Grand Total :","","","",Valid.SetAngka(ttlstokawal),Valid.SetAngka(ttlstokmasuk),Valid.SetAngka(ttlstokkeluar),Valid.SetAngka(ttlstokakhir),Valid.SetAngka(saldo_awal),
                   Valid.SetAngka(ttlaset),Valid.SetAngka(ttlsaldo_masuk),Valid.SetAngka(ttlsaldo_keluar),Valid.SetAngka(saldo_akhir),
//                   Valid.SetAngka(ttltotaljual),"",Valid.SetAngka(ttltotalpasien),"",
//                   Valid.SetAngka(ttltotalpiutang),"",Valid.SetAngka(ttltotalretbeli),"",
//                   Valid.SetAngka(ttltotalretjual),"",Valid.SetAngka(ttltotalretpiut),"",
//                   Valid.SetAngka(ttltotalutd),"",Valid.SetAngka(ttltotalkeluar),"",
//                   Valid.SetAngka(ttltotalrespulang),"",Valid.SetAngka(ttltotalmutasimasuk),"",Valid.SetAngka(ttltotalmutasikeluar)
                }); 
            } catch (Exception e) {
                System.out.println("Notifikasi Data Barang : "+e);
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
        
    }
    
    private void prosesCari2(String lokasi) {
       this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
       Valid.tabelKosong(tabMode);      
       try{   
            ps=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng, "+
                        "kodesatuan.satuan from databarang inner join kodesatuan   "+
                        "on databarang.kode_sat=kodesatuan.kode_sat "+
                        "where databarang.nama_brng like ? and databarang.kode_brng like ? or "+
                        "databarang.nama_brng like ? and databarang.nama_brng like ? or "+
                        "databarang.nama_brng like ? and kodesatuan.satuan like ? "+
                        " order by databarang.kode_brng");
            try {
                ttltotaljual=0;ttltotalbeli=0;ttltotalpesan=0;
                ttltotalpiutang=0;ttltotalretbeli=0;ttltotalretjual=0;
                ttltotalretpiut=0;ttltotalpasien=0;ttlaset=0;
                ttltotalutd=0;ttltotalkeluar=0;ttltotalrespulang=0;
                ttltotalmutasikeluar=0;ttltotalmutasimasuk=0;
                ps.setString(1,"%"+nmbar.getText()+"%");
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,"%"+nmbar.getText()+"%");
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                ps.setString(5,"%"+nmbar.getText()+"%");
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();            
                while(rs.next()){
                    totaljual=0;jumlahjual=0;totalbeli=0;jumlahbeli=0;totalpiutang=0;jumlahpiutang=0;
                    totalpesan=0;jumlahpesan=0;jumlahrespulang=0;
                    totalretbeli=0;jumlahretbeli=0;totalretjual=0;jumlahretjual=0;totalretpiut=0;jumlahretpiut=0;
                    jumlahpasin=0;stok=0;aset=0;totalrespulang=0;
                    jumlahutd=0;jumlahkeluar=0;totalkeluar=0;totalutd=0;
                    jumlahmutasikeluar=0;totalmutasikeluar=0;jumlahmutasimasuk=0;totalmutasimasuk=0;

                    ps2=koneksi.prepareStatement("select sum(stok),(sum(stok)*h_beli) as aset "+
                        "from gudangbarang inner join databarang on gudangbarang.kode_brng=databarang.kode_brng "+
                        "where gudangbarang.kode_brng=? and gudangbarang.kd_bangsal=?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            stok=rs2.getDouble(1);
                            aset=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Stok : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }

                    //pembelian  
                    ps2=koneksi.prepareStatement("select sum(detailbeli.jumlah2), sum(detailbeli.subtotal) "+
                        " from pembelian inner join detailbeli "+
                        " on pembelian.no_faktur=detailbeli.no_faktur "+
                        " where detailbeli.kode_brng=? and pembelian.tgl_beli "+
                        " between ? and ? and pembelian.kd_bangsal=?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps2.setString(4,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahbeli=rs2.getDouble(1);
                            totalbeli=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Detail Beli : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }          
                    
                    //pemesanan
                    ps2=koneksi.prepareStatement(
                        "select sum(detailpesan.jumlah2), sum(detailpesan.subtotal) "+
                        " from pemesanan inner join detailpesan "+
                        " on pemesanan.no_faktur=detailpesan.no_faktur "+
                        " where detailpesan.kode_brng=? and pemesanan.tgl_pesan "+
                        " between ? and ? and pemesanan.kd_bangsal=?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps2.setString(4,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahpesan=rs2.getDouble(1);
                            totalpesan=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Pemesanan : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                        
                    //penjualan
                    ps2=koneksi.prepareStatement("select sum(detailjual.jumlah), sum(detailjual.total) "+
                        " from penjualan inner join detailjual "+
                        " on penjualan.nota_jual=detailjual.nota_jual "+
                        " where detailjual.kode_brng=? and "+
                        " penjualan.tgl_jual  between ? and ? and penjualan.kd_bangsal=?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps2.setString(4,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahjual=rs2.getDouble(1);
                            totaljual=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Penjualan : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }

                    //piutang  
                    ps2=koneksi.prepareStatement("select sum(detailpiutang.jumlah), sum(detailpiutang.total) "+
                        " from piutang inner join detailpiutang "+
                        " on piutang.nota_piutang=detailpiutang.nota_piutang "+
                        " where detailpiutang.kode_brng=? and "+
                        " piutang.tgl_piutang between ? and ? and piutang.kd_bangsal=?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps2.setString(4,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahpiutang=rs2.getDouble(1);
                            totalpiutang=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Piutang : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }

                    //returbeli
                    ps2=koneksi.prepareStatement("select sum(detreturbeli.jml_retur2), sum(detreturbeli.total) "+
                        " from returbeli inner join detreturbeli "+
                        " on returbeli.no_retur_beli=detreturbeli.no_retur_beli "+
                        " where detreturbeli.kode_brng=? and "+
                        " returbeli.tgl_retur between ? and ? and returbeli.kd_bangsal=?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps2.setString(4,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahretbeli=rs2.getDouble(1);
                            totalretbeli=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Retur Beli : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }

                    //returjual
                    ps2=koneksi.prepareStatement("select sum(detreturjual.jml_retur), sum(detreturjual.subtotal) "+
                        " from returjual inner join detreturjual "+
                        " on returjual.no_retur_jual=detreturjual.no_retur_jual "+
                        " where detreturjual.kode_brng=? and "+
                        " returjual.tgl_retur between ? and ? and returjual.kd_bangsal=?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps2.setString(4,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahretjual=rs2.getDouble(1);
                            totalretjual=rs2.getDouble(2);
                        } 
                    } catch (Exception e) {
                        System.out.println("Notifikasi Retur Jual : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }               

                    ps2=koneksi.prepareStatement("select sum(detreturpiutang.jml_retur), sum(detreturpiutang.subtotal) "+
                        " from returpiutang inner join detreturpiutang "+
                        " on returpiutang.no_retur_piutang=detreturpiutang.no_retur_piutang "+
                        " where detreturpiutang.kode_brng=? and "+
                        " returpiutang.tgl_retur between ? and ? and returpiutang.kd_bangsal=?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps2.setString(4,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahretpiut=rs2.getDouble(1);
                            totalretpiut=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikas Retur Piutang : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }

                    ps2=koneksi.prepareStatement("select sum(detail_pemberian_obat.jml) as jumlah, "+
                        "(sum(detail_pemberian_obat.total)-sum(detail_pemberian_obat.embalase+detail_pemberian_obat.tuslah)) as jumpas "+
                        " from detail_pemberian_obat where detail_pemberian_obat.kode_brng=? and "+
                        " detail_pemberian_obat.tgl_perawatan between ? and ? and detail_pemberian_obat.kd_bangsal=?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps2.setString(4,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahpasin=rs2.getDouble(1);
                            totalpasien=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Pemberian Obat : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    ps2=koneksi.prepareStatement("select sum(utd_pengambilan_medis.jml) as jumlah, "+
                        "sum(utd_pengambilan_medis.total) as jumpas "+
                        " from utd_pengambilan_medis where utd_pengambilan_medis.kode_brng=? and "+
                        " utd_pengambilan_medis.tanggal between ? and ? and utd_pengambilan_medis.kd_bangsal_dr=?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps2.setString(4,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahutd=rs2.getDouble(1);
                            totalutd=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi UTD : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }

                    ps2=koneksi.prepareStatement("select sum(detail_pengeluaran_obat_bhp.jumlah), sum(detail_pengeluaran_obat_bhp.total) "+
                        " from pengeluaran_obat_bhp inner join detail_pengeluaran_obat_bhp "+
                        " on pengeluaran_obat_bhp.no_keluar=detail_pengeluaran_obat_bhp.no_keluar "+
                        " where detail_pengeluaran_obat_bhp.kode_brng=? and "+
                        " pengeluaran_obat_bhp.tanggal between ? and ? and pengeluaran_obat_bhp.kd_bangsal=?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps2.setString(4,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahkeluar=rs2.getDouble(1);
                            totalkeluar=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikas keluar : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    ps2=koneksi.prepareStatement("select sum(resep_pulang.jml_barang), sum(resep_pulang.total) "+
                        " from resep_pulang where resep_pulang.kode_brng=? and "+
                        " resep_pulang.tanggal between ? and ? and resep_pulang.kd_bangsal=?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps2.setString(4,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahrespulang=rs2.getDouble(1);
                            totalrespulang=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikas Resep Pulang : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    ps2=koneksi.prepareStatement("select sum(jml), sum(jml*harga) "+
                        " from mutasibarang where kode_brng=? and "+
                        " tanggal between ? and ? and kd_bangsalke=?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                        ps2.setString(4,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahmutasimasuk=rs2.getDouble(1);
                            totalmutasimasuk=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikas Mutasi Masuk : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    ps2=koneksi.prepareStatement("select sum(jml), sum(jml*harga) "+
                        " from mutasibarang where kode_brng=? and "+
                        " tanggal between ? and ? and kd_bangsaldari=?");
                    try {
                        ps2.setString(1,rs.getString(1));
                        ps2.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                        ps2.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                        ps2.setString(4,lokasi);
                        rs2=ps2.executeQuery();
                        if(rs2.next()){                    
                            jumlahmutasikeluar=rs2.getDouble(1);
                            totalmutasikeluar=rs2.getDouble(2);
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikas Mutasi Keluar : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    if((aset>0)||(jumlahbeli>0)||(jumlahpesan>0)||(jumlahjual>0)||(jumlahpasin>0)||(jumlahpiutang>0)||
                            (jumlahretbeli>0)||(jumlahretjual>0)||(jumlahretpiut>0)||(jumlahutd>0)||(jumlahkeluar>0)||
                            (jumlahrespulang>0)||(jumlahmutasimasuk>0)||(jumlahmutasikeluar>0)){
                        tabMode.addRow(new Object[]{"",rs.getString(1),rs.getString(2),
                           rs.getString(3),Valid.SetAngka(stok),
                           Sequel.cariIsi("select kategori_barang.nama from databarang "+
                           "inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode where databarang.kode_brng='" + rs.getString(1) + "'"),Valid.SetAngka(aset),
                           Valid.SetAngka(jumlahbeli),Valid.SetAngka(totalbeli),
                           Valid.SetAngka(jumlahpesan),Valid.SetAngka(totalpesan),
                           Valid.SetAngka(jumlahjual),Valid.SetAngka(totaljual),
                           Valid.SetAngka(jumlahpasin),Valid.SetAngka(totalpasien),
                           Valid.SetAngka(jumlahpiutang),Valid.SetAngka(totalpiutang),
                           Valid.SetAngka(jumlahretbeli),Valid.SetAngka(totalretbeli),
                           Valid.SetAngka(jumlahretjual),Valid.SetAngka(totalretjual),
                           Valid.SetAngka(jumlahretpiut),Valid.SetAngka(totalretpiut),
                           Valid.SetAngka(jumlahutd),Valid.SetAngka(totalutd),
                           Valid.SetAngka(jumlahkeluar),Valid.SetAngka(totalkeluar),
                           Valid.SetAngka(jumlahrespulang),Valid.SetAngka(totalrespulang),
                           Valid.SetAngka(jumlahmutasimasuk),Valid.SetAngka(totalmutasimasuk),
                           Valid.SetAngka(jumlahmutasikeluar),Valid.SetAngka(totalmutasikeluar)
                        }); 
                        ttltotalbeli=ttltotalbeli+totalbeli;
                        ttltotalpesan=ttltotalpesan+totalpesan;
                        ttltotaljual=ttltotaljual+totaljual;
                        ttlaset=ttlaset+aset;
                        ttltotalpasien=ttltotalpasien+totalpasien;
                        ttltotalpiutang=ttltotalpiutang+totalpiutang;
                        ttltotalretbeli=ttltotalretbeli+totalretbeli;
                        ttltotalretjual=ttltotalretjual+totalretjual;
                        ttltotalretpiut=ttltotalretpiut+totalretpiut;
                        ttltotalutd=ttltotalutd+totalutd;
                        ttltotalkeluar=ttltotalkeluar+totalkeluar;
                        ttltotalrespulang=ttltotalrespulang+totalrespulang;
                        ttltotalmutasimasuk=ttltotalmutasimasuk+totalmutasimasuk;
                        ttltotalmutasikeluar=ttltotalmutasikeluar+totalmutasikeluar;
                    }
                }   
                tabMode.addRow(new Object[]{"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""}); 
                tabMode.addRow(new Object[]{"","<>>","Total :","","","",Valid.SetAngka(ttlaset),"",
                   Valid.SetAngka(ttltotalbeli),"",Valid.SetAngka(ttltotalpesan),"",
                   Valid.SetAngka(ttltotaljual),"",Valid.SetAngka(ttltotalpasien),"",
                   Valid.SetAngka(ttltotalpiutang),"",Valid.SetAngka(ttltotalretbeli),"",
                   Valid.SetAngka(ttltotalretjual),"",Valid.SetAngka(ttltotalretpiut),"",
                   Valid.SetAngka(ttltotalutd),"",Valid.SetAngka(ttltotalkeluar),"",
                   Valid.SetAngka(ttltotalrespulang),"",Valid.SetAngka(ttltotalmutasimasuk),"",Valid.SetAngka(ttltotalmutasikeluar)
                }); 
            } catch (Exception e) {
                System.out.println("Notifikasi Data Barang : "+e);
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
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    public void isCek(){
         BtnPrint.setEnabled(true);
    }
    
}
