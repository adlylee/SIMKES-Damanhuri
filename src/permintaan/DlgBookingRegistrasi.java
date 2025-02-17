package permintaan;

import bridging.BPJSSuratKontrol;
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
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariDokter2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import simrskhanza.DlgCariPoli;
import simrskhanza.DlgCariPoli2;
import simrskhanza.DlgPasien;
import simrskhanza.DlgPenanggungJawab;
import bridging.BridgingWA;
import com.google.gson.JsonObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author dosen
 */
public class DlgBookingRegistrasi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private BridgingWA kirimwa = new BridgingWA();
    private Properties prop = new Properties();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0,kuota=0,jml=0;
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgCariDokter2 dokter2=new DlgCariDokter2(null,false);
    private DlgCariPoli poli=new DlgCariPoli(null,false);
    private DlgCariPoli2 poli2=new DlgCariPoli2(null,false);
    private DlgCariPoli poli3=new DlgCariPoli(null,false);
    private DlgCariPoli poli4=new DlgCariPoli(null,false);
    private DlgCariPoli2 poli5=new DlgCariPoli2(null,false);
    private DlgCariPoli2 poli6=new DlgCariPoli2(null,false);
    private DlgPasien pasien=new DlgPasien(null,false);
    private String aktifjadwal="",URUTNOREG="",status="",no_rawat="",umur="",sttsumur="",noreg="",Alasan2="", Rtl2="",Pj="";
    private JsonNode root, nameNode ,response;
    private ObjectMapper mapper = new ObjectMapper();
    private HttpHeaders headers ;
    private HttpEntity requestEntity;
    private DlgPenanggungJawab penjab=new DlgPenanggungJawab(null,false);
    private DlgCariDokter dokter3 = new DlgCariDokter(null, false);
    private DlgCariDokter2 dokter4 = new DlgCariDokter2(null, false);
    private DlgCariPoli poli7 = new DlgCariPoli(null, false);
    private DlgCariPoli2 poli8 = new DlgCariPoli2(null, false);
    private BPJSSuratKontrol kontrol = new BPJSSuratKontrol(null, false);//added
    

    /** Creates new form DlgPemberianInfus
     * @param parent
     * @param modal */
    public DlgBookingRegistrasi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        WindowBatalWhatsapp.setSize(500, 144);
        WindowGanti.setSize(500, 174);

        tabMode=new DefaultTableModel(null,new Object[]{
                "P","Tgl.Booking","Jam Booking","No.RM","Nama Pasien","Tgl.Periksa","Kode Dokter",
                "Nama Dokter","Kode Poli","Nama Poli","No.Reg","Nama PJ","Alamat PJ",
                "kelurahanpj","kecamatanpj","kabupatenpj","propinsipj","Hubungan","Bayar",
                "Tahun","Bulan","Hari","Asal Booking","Status","Kd PJ","Cara Bayar", "No. Telp"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 27; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(80);
            }else if(i==4){
                column.setPreferredWidth(170);
            }else if(i==5){
                column.setPreferredWidth(70);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setPreferredWidth(170);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==9){
                column.setPreferredWidth(160);
            }else if(i==10){
                column.setPreferredWidth(50);
            }else if(i==11){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==12){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==14){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==15){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==16){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==17){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==18){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==19){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==20){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==21){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==22){
                column.setWidth(70);
            }else if(i==23){
                column.setWidth(70);
            }else if(i==24){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==26){
                column.setPreferredWidth(100);
            }else{
                column.setWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());


        TNoRM.setDocument(new batasInput((byte)17).getKata(TNoRM));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        KdDokter.setDocument(new batasInput((byte)3).getKata(KdDokter));
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
        
        ChkInput.setSelected(false);
        isForm();
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){                    
                    KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    isNomer();
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
                    KdDokter.setText(dokter2.getTable().getValueAt(dokter2.getTable().getSelectedRow(),0).toString());
                    NmDokter.setText(dokter2.getTable().getValueAt(dokter2.getTable().getSelectedRow(),1).toString());
                    if(aktifjadwal.equals("aktif")){
                        kuota=Integer.parseInt(dokter2.getTable().getValueAt(dokter2.getTable().getSelectedRow(),13).toString());
                    }
                    isNomer();                        
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
                if(poli.getTable().getSelectedRow()!= -1){                    
                    KdPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),0).toString());
                    NmPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                    isNomer();
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
        
        poli2.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli2.getTable().getSelectedRow()!= -1){                    
                    KdPoli.setText(poli2.getTable().getValueAt(poli2.getTable().getSelectedRow(),0).toString());
                    NmPoli.setText(poli2.getTable().getValueAt(poli2.getTable().getSelectedRow(),1).toString());
                    isNomer();
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
        
        poli3.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli3.getTable().getSelectedRow()!= -1){                    
                    KdPoli1.setText(poli3.getTable().getValueAt(poli3.getTable().getSelectedRow(),0).toString());
                    NmPoli1.setText(poli3.getTable().getValueAt(poli3.getTable().getSelectedRow(),1).toString());
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
        
        poli5.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli5.getTable().getSelectedRow()!= -1){                    
                    KdPoli1.setText(poli5.getTable().getValueAt(poli5.getTable().getSelectedRow(),0).toString());
                    NmPoli1.setText(poli5.getTable().getValueAt(poli5.getTable().getSelectedRow(),1).toString());
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
        
        poli4.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli4.getTable().getSelectedRow()!= -1){                    
                    KdPoli2.setText(poli4.getTable().getValueAt(poli4.getTable().getSelectedRow(),0).toString());
                    NmPoli2.setText(poli4.getTable().getValueAt(poli4.getTable().getSelectedRow(),1).toString());
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
        
        poli6.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli6.getTable().getSelectedRow()!= -1){                    
                    KdPoli2.setText(poli6.getTable().getValueAt(poli6.getTable().getSelectedRow(),0).toString());
                    NmPoli2.setText(poli6.getTable().getValueAt(poli6.getTable().getSelectedRow(),1).toString());
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
        
        pasien.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pasien.getTable().getSelectedRow()!= -1){  
                    TNoRM.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),1).toString());
                    TPasien.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),2).toString());                                                
                }  
                TNoRM.requestFocus();
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
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    pasien.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });    
        
        pasien.penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgBooking")){
                    if(pasien.penjab.getTable().getSelectedRow()!= -1){
                        kdpnj.setText(pasien.penjab.getTable().getValueAt(pasien.penjab.getTable().getSelectedRow(),1).toString());
                        nmpnj.setText(pasien.penjab.getTable().getValueAt(pasien.penjab.getTable().getSelectedRow(),2).toString());
                    }    
                    kdpnj.requestFocus();
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
        
        pasien.penjab.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(var.getform().equals("DlgBooking")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pasien.penjab.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
       
        dokter3.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter3.getTable().getSelectedRow()!= -1){                    
                    KdDokter1.setText(dokter3.getTable().getValueAt(dokter3.getTable().getSelectedRow(),0).toString());
                    NmDokter1.setText(dokter3.getTable().getValueAt(dokter3.getTable().getSelectedRow(),1).toString());
                    isNomer2();
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
        
        dokter4.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter4.getTable().getSelectedRow()!= -1){                    
                    KdDokter1.setText(dokter4.getTable().getValueAt(dokter4.getTable().getSelectedRow(),0).toString());
                    NmDokter1.setText(dokter4.getTable().getValueAt(dokter4.getTable().getSelectedRow(),1).toString());
                    if(aktifjadwal.equals("aktif")){
                        kuota=Integer.parseInt(dokter4.getTable().getValueAt(dokter4.getTable().getSelectedRow(),13).toString());
                    }
                    isNomer2();                        
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
        
        poli7.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli7.getTable().getSelectedRow()!= -1){                    
                    KdPoli4.setText(poli7.getTable().getValueAt(poli7.getTable().getSelectedRow(),0).toString());
                    NmPoli4.setText(poli7.getTable().getValueAt(poli7.getTable().getSelectedRow(),1).toString());
                    isNomer2();
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
        
        poli8.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli8.getTable().getSelectedRow()!= -1){                    
                    KdPoli4.setText(poli8.getTable().getValueAt(poli8.getTable().getSelectedRow(),0).toString());
                    NmPoli4.setText(poli8.getTable().getValueAt(poli8.getTable().getSelectedRow(),1).toString());
                    isNomer2();
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
        
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            aktifjadwal=prop.getProperty("JADWALDOKTERDIREGISTRASI");
            URUTNOREG=prop.getProperty("URUTNOREG");
        } catch (Exception ex) {
            aktifjadwal="";
            URUTNOREG="";
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        ppSemua = new javax.swing.JMenuItem();
        ppGanti = new javax.swing.JMenuItem();
        WindowBatalWhatsapp = new javax.swing.JDialog();
        internalFrame7 = new widget.InternalFrame();
        BtnCloseIn6 = new widget.Button();
        BtnSimpan6 = new widget.Button();
        jLabel12 = new widget.Label();
        KdPoli1 = new widget.TextBox();
        NmPoli1 = new widget.TextBox();
        BtnPoli1 = new widget.Button();
        jLabel13 = new widget.Label();
        KdPoli2 = new widget.TextBox();
        NmPoli2 = new widget.TextBox();
        BtnPoli2 = new widget.Button();
        WindowGanti = new javax.swing.JDialog();
        internalFrame8 = new widget.InternalFrame();
        BtnCloseIn7 = new widget.Button();
        BtnSimpan7 = new widget.Button();
        jLabel15 = new widget.Label();
        KdDokter1 = new widget.TextBox();
        NmDokter1 = new widget.TextBox();
        BtnDokter1 = new widget.Button();
        jLabel16 = new widget.Label();
        KdPoli4 = new widget.TextBox();
        NmPoli4 = new widget.TextBox();
        BtnPoli4 = new widget.Button();
        jLabel17 = new widget.Label();
        TanggalPeriksa1 = new widget.Tanggal();
        NoReg1 = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit3 = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnEdit = new widget.Button();
        BtnKeluar = new widget.Button();
        BtnEdit1 = new widget.Button();
        BtnEdit2 = new widget.Button();
        panelGlass10 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        panelCari = new widget.panelisi();
        R2 = new widget.RadioButton();
        DTPCari1 = new widget.Tanggal();
        jLabel22 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        R3 = new widget.RadioButton();
        DTPCari3 = new widget.Tanggal();
        jLabel25 = new widget.Label();
        DTPCari4 = new widget.Tanggal();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRM = new widget.TextBox();
        jLabel9 = new widget.Label();
        NmDokter = new widget.TextBox();
        TPasien = new widget.TextBox();
        TanggalBooking = new widget.Tanggal();
        jLabel10 = new widget.Label();
        KdDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel11 = new widget.Label();
        KdPoli = new widget.TextBox();
        NmPoli = new widget.TextBox();
        BtnPoli = new widget.Button();
        jLabel14 = new widget.Label();
        TanggalPeriksa = new widget.Tanggal();
        NoReg = new widget.TextBox();
        jLabel18 = new widget.Label();
        BtnPasien = new widget.Button();
        jLabel19 = new widget.Label();
        kdpnj = new widget.TextBox();
        nmpnj = new widget.TextBox();
        btnPenjab = new widget.Button();
        jLabel20 = new widget.Label();
        Kuota = new widget.TextBox();

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(70, 70, 70));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkan.setText("Bersihkan Pilihan");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setIconTextGap(8);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(170, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        ppSemua.setBackground(new java.awt.Color(255, 255, 254));
        ppSemua.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSemua.setForeground(new java.awt.Color(70, 70, 70));
        ppSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSemua.setText("Pilih Semua");
        ppSemua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSemua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSemua.setIconTextGap(8);
        ppSemua.setName("ppSemua"); // NOI18N
        ppSemua.setPreferredSize(new java.awt.Dimension(170, 25));
        ppSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSemuaActionPerformed(evt);
            }
        });
        Popup.add(ppSemua);

        ppGanti.setBackground(new java.awt.Color(255, 255, 254));
        ppGanti.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGanti.setForeground(new java.awt.Color(70, 70, 70));
        ppGanti.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGanti.setText("Ganti Jadwal Booking");
        ppGanti.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGanti.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGanti.setIconTextGap(8);
        ppGanti.setName("ppGanti"); // NOI18N
        ppGanti.setPreferredSize(new java.awt.Dimension(170, 25));
        ppGanti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGantiActionPerformed(evt);
            }
        });
        Popup.add(ppGanti);

        WindowBatalWhatsapp.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowBatalWhatsapp.setName("WindowBatalWhatsapp"); // NOI18N
        WindowBatalWhatsapp.setUndecorated(true);
        WindowBatalWhatsapp.setResizable(false);

        internalFrame7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pemindahan Jadwal Poliklinik ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame7.setName("internalFrame7"); // NOI18N
        internalFrame7.setLayout(null);

        BtnCloseIn6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn6.setMnemonic('U');
        BtnCloseIn6.setText("Tutup");
        BtnCloseIn6.setToolTipText("Alt+U");
        BtnCloseIn6.setName("BtnCloseIn6"); // NOI18N
        BtnCloseIn6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn6ActionPerformed(evt);
            }
        });
        internalFrame7.add(BtnCloseIn6);
        BtnCloseIn6.setBounds(310, 100, 100, 30);

        BtnSimpan6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/delete-16x16.png"))); // NOI18N
        BtnSimpan6.setMnemonic('S');
        BtnSimpan6.setText("Kirim WA Batal");
        BtnSimpan6.setToolTipText("Alt+S");
        BtnSimpan6.setName("BtnSimpan6"); // NOI18N
        BtnSimpan6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan6ActionPerformed(evt);
            }
        });
        internalFrame7.add(BtnSimpan6);
        BtnSimpan6.setBounds(40, 100, 150, 30);

        jLabel12.setText("Dari Unit/Poli :");
        jLabel12.setName("jLabel12"); // NOI18N
        internalFrame7.add(jLabel12);
        jLabel12.setBounds(0, 30, 90, 23);

        KdPoli1.setEditable(false);
        KdPoli1.setHighlighter(null);
        KdPoli1.setName("KdPoli1"); // NOI18N
        internalFrame7.add(KdPoli1);
        KdPoli1.setBounds(110, 30, 70, 23);

        NmPoli1.setEditable(false);
        NmPoli1.setHighlighter(null);
        NmPoli1.setName("NmPoli1"); // NOI18N
        internalFrame7.add(NmPoli1);
        NmPoli1.setBounds(180, 30, 263, 23);

        BtnPoli1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPoli1.setMnemonic('X');
        BtnPoli1.setToolTipText("Alt+X");
        BtnPoli1.setName("BtnPoli1"); // NOI18N
        BtnPoli1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPoli1ActionPerformed(evt);
            }
        });
        BtnPoli1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPoli1KeyPressed(evt);
            }
        });
        internalFrame7.add(BtnPoli1);
        BtnPoli1.setBounds(440, 30, 28, 23);

        jLabel13.setText("Ke Unit/Poli :");
        jLabel13.setName("jLabel13"); // NOI18N
        internalFrame7.add(jLabel13);
        jLabel13.setBounds(20, 60, 70, 23);

        KdPoli2.setEditable(false);
        KdPoli2.setHighlighter(null);
        KdPoli2.setName("KdPoli2"); // NOI18N
        internalFrame7.add(KdPoli2);
        KdPoli2.setBounds(110, 60, 70, 23);

        NmPoli2.setEditable(false);
        NmPoli2.setHighlighter(null);
        NmPoli2.setName("NmPoli2"); // NOI18N
        internalFrame7.add(NmPoli2);
        NmPoli2.setBounds(180, 60, 263, 23);

        BtnPoli2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPoli2.setMnemonic('X');
        BtnPoli2.setToolTipText("Alt+X");
        BtnPoli2.setName("BtnPoli2"); // NOI18N
        BtnPoli2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPoli2ActionPerformed(evt);
            }
        });
        BtnPoli2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPoli2KeyPressed(evt);
            }
        });
        internalFrame7.add(BtnPoli2);
        BtnPoli2.setBounds(440, 60, 28, 23);

        WindowBatalWhatsapp.getContentPane().add(internalFrame7, java.awt.BorderLayout.CENTER);

        WindowGanti.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowGanti.setName("WindowGanti"); // NOI18N
        WindowGanti.setUndecorated(true);
        WindowGanti.setResizable(false);

        internalFrame8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pemindahan Jadwal Poliklinik ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame8.setName("internalFrame8"); // NOI18N
        internalFrame8.setLayout(null);

        BtnCloseIn7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn7.setMnemonic('U');
        BtnCloseIn7.setText("Tutup");
        BtnCloseIn7.setToolTipText("Alt+U");
        BtnCloseIn7.setName("BtnCloseIn7"); // NOI18N
        BtnCloseIn7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn7ActionPerformed(evt);
            }
        });
        internalFrame8.add(BtnCloseIn7);
        BtnCloseIn7.setBounds(340, 130, 100, 30);

        BtnSimpan7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan7.setMnemonic('S');
        BtnSimpan7.setText("Simpan");
        BtnSimpan7.setToolTipText("Alt+S");
        BtnSimpan7.setName("BtnSimpan7"); // NOI18N
        BtnSimpan7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan7ActionPerformed(evt);
            }
        });
        internalFrame8.add(BtnSimpan7);
        BtnSimpan7.setBounds(200, 130, 150, 30);

        jLabel15.setText("Dokter :");
        jLabel15.setName("jLabel15"); // NOI18N
        internalFrame8.add(jLabel15);
        jLabel15.setBounds(0, 60, 70, 23);

        KdDokter1.setEditable(false);
        KdDokter1.setHighlighter(null);
        KdDokter1.setName("KdDokter1"); // NOI18N
        internalFrame8.add(KdDokter1);
        KdDokter1.setBounds(75, 60, 70, 23);

        NmDokter1.setEditable(false);
        NmDokter1.setHighlighter(null);
        NmDokter1.setName("NmDokter1"); // NOI18N
        internalFrame8.add(NmDokter1);
        NmDokter1.setBounds(145, 60, 263, 23);

        BtnDokter1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter1.setMnemonic('X');
        BtnDokter1.setToolTipText("Alt+X");
        BtnDokter1.setName("BtnDokter1"); // NOI18N
        BtnDokter1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokter1ActionPerformed(evt);
            }
        });
        BtnDokter1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokter1KeyPressed(evt);
            }
        });
        internalFrame8.add(BtnDokter1);
        BtnDokter1.setBounds(410, 60, 28, 23);

        jLabel16.setText("Poli :");
        jLabel16.setName("jLabel16"); // NOI18N
        internalFrame8.add(jLabel16);
        jLabel16.setBounds(0, 90, 70, 23);

        KdPoli4.setEditable(false);
        KdPoli4.setHighlighter(null);
        KdPoli4.setName("KdPoli4"); // NOI18N
        internalFrame8.add(KdPoli4);
        KdPoli4.setBounds(75, 90, 70, 23);

        NmPoli4.setEditable(false);
        NmPoli4.setHighlighter(null);
        NmPoli4.setName("NmPoli4"); // NOI18N
        internalFrame8.add(NmPoli4);
        NmPoli4.setBounds(145, 90, 263, 23);

        BtnPoli4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPoli4.setMnemonic('X');
        BtnPoli4.setToolTipText("Alt+X");
        BtnPoli4.setName("BtnPoli4"); // NOI18N
        BtnPoli4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPoli4ActionPerformed(evt);
            }
        });
        BtnPoli4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPoli4KeyPressed(evt);
            }
        });
        internalFrame8.add(BtnPoli4);
        BtnPoli4.setBounds(410, 90, 28, 23);

        jLabel17.setText("Tgl.Periksa :");
        jLabel17.setName("jLabel17"); // NOI18N
        internalFrame8.add(jLabel17);
        jLabel17.setBounds(0, 30, 70, 23);

        TanggalPeriksa1.setForeground(new java.awt.Color(50, 70, 50));
        TanggalPeriksa1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "16-04-2024 08:29:13" }));
        TanggalPeriksa1.setDisplayFormat("dd-MM-yyyy hh:mm:ss");
        TanggalPeriksa1.setName("TanggalPeriksa1"); // NOI18N
        TanggalPeriksa1.setOpaque(false);
        TanggalPeriksa1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TanggalPeriksa1ItemStateChanged(evt);
            }
        });
        TanggalPeriksa1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalPeriksa1KeyPressed(evt);
            }
        });
        internalFrame8.add(TanggalPeriksa1);
        TanggalPeriksa1.setBounds(75, 30, 150, 23);

        WindowGanti.getContentPane().add(internalFrame8, java.awt.BorderLayout.CENTER);

        NoReg1.setHighlighter(null);
        NoReg1.setName("NoReg1"); // NOI18N
        NoReg1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoReg1KeyPressed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Booking Registrasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(Popup);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setAutoCreateRowSorter(true);
        tbObat.setToolTipText("Silahkan klik untuk memilih data yang ataupun dihapus");
        tbObat.setComponentPopupMenu(Popup);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 144));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(55, 55));
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

        BtnEdit3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit3.setMnemonic('G');
        BtnEdit3.setText("Ganti");
        BtnEdit3.setToolTipText("Alt+G");
        BtnEdit3.setName("BtnEdit3"); // NOI18N
        BtnEdit3.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEdit3ActionPerformed(evt);
            }
        });
        BtnEdit3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEdit3KeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit3);

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

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/add-file-16x16.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Regist");
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

        BtnEdit1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/edit_f2.png"))); // NOI18N
        BtnEdit1.setMnemonic('G');
        BtnEdit1.setText("Kirim WA");
        BtnEdit1.setToolTipText("Alt+G");
        BtnEdit1.setName("BtnEdit1"); // NOI18N
        BtnEdit1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEdit1ActionPerformed(evt);
            }
        });
        BtnEdit1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEdit1KeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit1);

        BtnEdit2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/edit_f2.png"))); // NOI18N
        BtnEdit2.setMnemonic('G');
        BtnEdit2.setText("WA Batal");
        BtnEdit2.setToolTipText("Alt+G");
        BtnEdit2.setName("BtnEdit2"); // NOI18N
        BtnEdit2.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEdit2ActionPerformed(evt);
            }
        });
        BtnEdit2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEdit2KeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit2);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass10.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(300, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass10.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
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
        panelGlass10.add(BtnCari);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass10.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass10.add(LCount);

        jPanel3.add(panelGlass10, java.awt.BorderLayout.CENTER);

        panelCari.setName("panelCari"); // NOI18N
        panelCari.setPreferredSize(new java.awt.Dimension(44, 43));
        panelCari.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 9));

        R2.setBackground(new java.awt.Color(240, 250, 230));
        R2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.pink));
        buttonGroup1.add(R2);
        R2.setSelected(true);
        R2.setText("Tanggal Booking :");
        R2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R2.setName("R2"); // NOI18N
        R2.setPreferredSize(new java.awt.Dimension(125, 23));
        panelCari.add(R2);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "16-04-2024" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(100, 23));
        DTPCari1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPCari1ItemStateChanged(evt);
            }
        });
        DTPCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPCari1KeyPressed(evt);
            }
        });
        panelCari.add(DTPCari1);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("s.d");
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(25, 23));
        panelCari.add(jLabel22);

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "16-04-2024" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(100, 23));
        DTPCari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPCari2KeyPressed(evt);
            }
        });
        panelCari.add(DTPCari2);

        R3.setBackground(new java.awt.Color(240, 250, 230));
        R3.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.pink));
        buttonGroup1.add(R3);
        R3.setText("Tanggal Periksa :");
        R3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R3.setName("R3"); // NOI18N
        R3.setPreferredSize(new java.awt.Dimension(135, 23));
        panelCari.add(R3);

        DTPCari3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "16-04-2024" }));
        DTPCari3.setDisplayFormat("dd-MM-yyyy");
        DTPCari3.setName("DTPCari3"); // NOI18N
        DTPCari3.setOpaque(false);
        DTPCari3.setPreferredSize(new java.awt.Dimension(100, 23));
        DTPCari3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPCari3ItemStateChanged(evt);
            }
        });
        DTPCari3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPCari3KeyPressed(evt);
            }
        });
        panelCari.add(DTPCari3);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("s.d");
        jLabel25.setName("jLabel25"); // NOI18N
        jLabel25.setPreferredSize(new java.awt.Dimension(25, 23));
        panelCari.add(jLabel25);

        DTPCari4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "16-04-2024" }));
        DTPCari4.setDisplayFormat("dd-MM-yyyy");
        DTPCari4.setName("DTPCari4"); // NOI18N
        DTPCari4.setOpaque(false);
        DTPCari4.setPreferredSize(new java.awt.Dimension(100, 23));
        DTPCari4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPCari4ItemStateChanged(evt);
            }
        });
        DTPCari4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPCari4KeyPressed(evt);
            }
        });
        panelCari.add(DTPCari4);

        jPanel3.add(panelCari, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 156));
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
        FormInput.setPreferredSize(new java.awt.Dimension(190, 107));
        FormInput.setLayout(null);

        jLabel4.setText("Pasien :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 55, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(59, 10, 90, 23);

        jLabel9.setText("Dokter :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 40, 55, 23);

        NmDokter.setEditable(false);
        NmDokter.setHighlighter(null);
        NmDokter.setName("NmDokter"); // NOI18N
        FormInput.add(NmDokter);
        NmDokter.setBounds(151, 40, 311, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(151, 10, 311, 23);

        TanggalBooking.setForeground(new java.awt.Color(50, 70, 50));
        TanggalBooking.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "16-04-2024 08:29:13" }));
        TanggalBooking.setDisplayFormat("dd-MM-yyyy hh:mm:ss");
        TanggalBooking.setName("TanggalBooking"); // NOI18N
        TanggalBooking.setOpaque(false);
        TanggalBooking.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalBookingKeyPressed(evt);
            }
        });
        FormInput.add(TanggalBooking);
        TanggalBooking.setBounds(580, 10, 140, 23);

        jLabel10.setText("Tgl.Booking :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(506, 10, 70, 23);

        KdDokter.setEditable(false);
        KdDokter.setHighlighter(null);
        KdDokter.setName("KdDokter"); // NOI18N
        FormInput.add(KdDokter);
        KdDokter.setBounds(59, 40, 90, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('X');
        BtnDokter.setToolTipText("Alt+X");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        BtnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokterKeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter);
        BtnDokter.setBounds(464, 40, 28, 23);

        jLabel11.setText("Unit/Poli :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(0, 70, 121, 23);

        KdPoli.setEditable(false);
        KdPoli.setHighlighter(null);
        KdPoli.setName("KdPoli"); // NOI18N
        FormInput.add(KdPoli);
        KdPoli.setBounds(125, 70, 70, 23);

        NmPoli.setEditable(false);
        NmPoli.setHighlighter(null);
        NmPoli.setName("NmPoli"); // NOI18N
        FormInput.add(NmPoli);
        NmPoli.setBounds(198, 70, 263, 23);

        BtnPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPoli.setMnemonic('X');
        BtnPoli.setToolTipText("Alt+X");
        BtnPoli.setName("BtnPoli"); // NOI18N
        BtnPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPoliActionPerformed(evt);
            }
        });
        BtnPoli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPoliKeyPressed(evt);
            }
        });
        FormInput.add(BtnPoli);
        BtnPoli.setBounds(464, 70, 28, 23);

        jLabel14.setText("Tgl.Periksa :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(506, 40, 70, 23);

        TanggalPeriksa.setForeground(new java.awt.Color(50, 70, 50));
        TanggalPeriksa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "16-04-2024 08:29:13" }));
        TanggalPeriksa.setDisplayFormat("dd-MM-yyyy hh:mm:ss");
        TanggalPeriksa.setName("TanggalPeriksa"); // NOI18N
        TanggalPeriksa.setOpaque(false);
        TanggalPeriksa.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TanggalPeriksaItemStateChanged(evt);
            }
        });
        TanggalPeriksa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalPeriksaKeyPressed(evt);
            }
        });
        FormInput.add(TanggalPeriksa);
        TanggalPeriksa.setBounds(580, 40, 140, 23);

        NoReg.setHighlighter(null);
        NoReg.setName("NoReg"); // NOI18N
        NoReg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRegKeyPressed(evt);
            }
        });
        FormInput.add(NoReg);
        NoReg.setBounds(630, 70, 90, 23);

        jLabel18.setText("No.Reg :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(526, 70, 100, 23);

        BtnPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPasien.setMnemonic('X');
        BtnPasien.setToolTipText("Alt+X");
        BtnPasien.setName("BtnPasien"); // NOI18N
        BtnPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPasienActionPerformed(evt);
            }
        });
        BtnPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPasienKeyPressed(evt);
            }
        });
        FormInput.add(BtnPasien);
        BtnPasien.setBounds(464, 10, 28, 23);

        jLabel19.setText("Cara Bayar :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(0, 100, 121, 23);

        kdpnj.setEditable(false);
        kdpnj.setHighlighter(null);
        kdpnj.setName("kdpnj"); // NOI18N
        kdpnj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpnjKeyPressed(evt);
            }
        });
        FormInput.add(kdpnj);
        kdpnj.setBounds(125, 100, 70, 23);

        nmpnj.setEditable(false);
        nmpnj.setName("nmpnj"); // NOI18N
        FormInput.add(nmpnj);
        nmpnj.setBounds(198, 100, 263, 23);

        btnPenjab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPenjab.setMnemonic('2');
        btnPenjab.setToolTipText("ALt+2");
        btnPenjab.setName("btnPenjab"); // NOI18N
        btnPenjab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPenjabActionPerformed(evt);
            }
        });
        btnPenjab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPenjabKeyPressed(evt);
            }
        });
        FormInput.add(btnPenjab);
        btnPenjab.setBounds(464, 100, 28, 23);

        jLabel20.setText("Kuota :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(526, 100, 100, 23);

        Kuota.setEditable(false);
        Kuota.setHighlighter(null);
        Kuota.setName("Kuota"); // NOI18N
        Kuota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KuotaKeyPressed(evt);
            }
        });
        FormInput.add(Kuota);
        Kuota.setBounds(630, 100, 90, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
       // Valid.pindah(evt,Status,KdDokter);
        
}//GEN-LAST:event_TNoRMKeyPressed

    private void TanggalBookingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalBookingKeyPressed
        Valid.pindah(evt,btnPenjab,TanggalPeriksa);
}//GEN-LAST:event_TanggalBookingKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRM,"pasien");
        }else if(NmDokter.getText().trim().equals("")||KdDokter.getText().trim().equals("")){
            Valid.textKosong(KdDokter,"Operator");
        }else if(NmPoli.getText().trim().equals("")||NmPoli.getText().trim().equals("")){
            Valid.textKosong(KdPoli,"Operasi");
        }else if(kdpnj.getText().trim().equals("")||nmpnj.getText().trim().equals("")){
            Valid.textKosong(kdpnj,"Cara Bayar");
        }else if(NoReg.getText().trim().equals("")){
            Valid.textKosong(NoReg,"No.Antri");
        }else{
            if(var.getkode().equals("Admin Utama")){
                isBooking();
            }else{
                if(aktifjadwal.equals("aktif")){
                    if(Sequel.cariInteger("select count(no_rkm_medis) from booking_registrasi where kd_dokter='"+KdDokter.getText()+"' and tanggal_periksa='"+Valid.SetTgl(TanggalPeriksa.getSelectedItem()+"")+"' ")>=kuota){
                        JOptionPane.showMessageDialog(null,"Eiiits, Kuota registrasi penuh..!!!");
                        TCari.requestFocus();
                    }else{
                        isBooking();
                    }                    
                }else{
                    isBooking();
                } 
            }                                       
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,NoReg,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm(); 
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        int reply = JOptionPane.showConfirmDialog(rootPane, "Apakah yakin ingin menghapus data...??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            for (i = 0; i < tbObat.getRowCount(); i++) {
                if (tbObat.getValueAt(i, 0).toString().equals("true")) {
                    Sequel.queryu2("delete from booking_registrasi where no_rkm_medis=? and tanggal_periksa=?", 2, new String[]{
                        tbObat.getValueAt(i, 3).toString(), tbObat.getValueAt(i, 5).toString()
                    });
                }
            }
            tampil();
        }
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
            if(R2.isSelected()==true){
                status=" booking_registrasi.tanggal_booking between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ";
            }else if(R3.isSelected()==true){
                status=" booking_registrasi.tanggal_periksa between '"+Valid.SetTgl(DTPCari3.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari4.getSelectedItem()+"")+"' ";           
            }
            Valid.MyReport("rptBookingRegistrasi.jrxml","report","::[ Laporan Daftar Booking Registrasi ]::",
                "select booking_registrasi.tanggal_booking,booking_registrasi.jam_booking,booking_registrasi.no_rkm_medis, "+
                    "pasien.nm_pasien,booking_registrasi.tanggal_periksa,booking_registrasi.kd_dokter,"+
                    "dokter.nm_dokter,booking_registrasi.kd_poli,poliklinik.nm_poli,booking_registrasi.no_reg, "+
                    "pasien.namakeluarga,pasien.alamatpj,pasien.kelurahanpj,pasien.kecamatanpj,"+
                    "pasien.kabupatenpj,pasien.propinsipj,pasien.keluarga,pasien.kd_pj,TIMESTAMPDIFF(YEAR, pasien.tgl_lahir, CURDATE()) as tahun, "+
                    "(TIMESTAMPDIFF(MONTH, pasien.tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, pasien.tgl_lahir, CURDATE()) div 12) * 12)) as bulan, "+
                    "TIMESTAMPDIFF(DAY, DATE_ADD(DATE_ADD(pasien.tgl_lahir,INTERVAL TIMESTAMPDIFF(YEAR, pasien.tgl_lahir, CURDATE()) YEAR), INTERVAL TIMESTAMPDIFF(MONTH, pasien.tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, pasien.tgl_lahir, CURDATE()) div 12) * 12) MONTH), CURDATE()) as hari, "+
                    "booking_registrasi.limit_reg,booking_registrasi.status,booking_registrasi.kd_pj,penjab.png_jawab from booking_registrasi inner join pasien "+
                    "inner join dokter inner join poliklinik inner join penjab on "+
                    "booking_registrasi.no_rkm_medis=pasien.no_rkm_medis and booking_registrasi.kd_pj=penjab.kd_pj and "+
                    "booking_registrasi.kd_dokter=dokter.kd_dokter and booking_registrasi.kd_poli=poliklinik.kd_poli "+
                    "where "+status+" and booking_registrasi.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                    status+" and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                    status+" and poliklinik.nm_poli like '%"+TCari.getText().trim()+"%' or "+
                    status+" and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' order by booking_registrasi.tanggal_booking,dokter.nm_dokter",param);
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
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
    if(aktifjadwal.equals("aktif")){
        if(var.getkode().equals("Admin Utama")){
            dokter.isCek();        
            dokter.TCari.requestFocus();
            dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            dokter.setLocationRelativeTo(internalFrame1);
            dokter.setVisible(true);
        }else{
            dokter2.setPoli(NmPoli.getText());
            dokter2.isCek();  
            dokter2.SetHari(TanggalPeriksa.getDate());   
            dokter2.tampil();
            dokter2.TCari.requestFocus();
            dokter2.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            dokter2.setLocationRelativeTo(internalFrame1);
            dokter2.setVisible(true);
        }                
    }else{
        dokter.isCek();        
        dokter.TCari.requestFocus();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }
}//GEN-LAST:event_BtnDokterActionPerformed

private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_SPACE){
        BtnDokterActionPerformed(null);
    }else{
        Valid.pindah(evt,BtnPasien,BtnPoli);
    }        
}//GEN-LAST:event_BtnDokterKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

    private void DTPCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPCari1KeyPressed

    }//GEN-LAST:event_DTPCari1KeyPressed

    private void DTPCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPCari2KeyPressed
        R2.setSelected(true);
    }//GEN-LAST:event_DTPCari2KeyPressed

    private void DTPCari3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPCari3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DTPCari3KeyPressed

    private void DTPCari4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPCari4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DTPCari4KeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        for(i=0;i<tbObat.getRowCount();i++){ 
            if(tbObat.getValueAt(i,0).toString().equals("true")){
                Sequel.mengedit("pasien","no_rkm_medis=?","umur=CONCAT(CONCAT(CONCAT(TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()), ' Th '),CONCAT(TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12), ' Bl ')),CONCAT(TIMESTAMPDIFF(DAY, DATE_ADD(DATE_ADD(tgl_lahir,INTERVAL TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()) YEAR), INTERVAL TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12) MONTH), CURDATE()), ' Hr'))",1,new String[]{tbObat.getValueAt(i,3).toString()});
                status=Sequel.cariIsi("select if((select count(no_rkm_medis) from reg_periksa where no_rkm_medis='"+tbObat.getValueAt(i,3).toString()+"' and kd_poli='"+tbObat.getValueAt(i,8).toString()+"')>0,'Lama','Baru' )");
                no_rawat=Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_rawat,6),signed)),0) from reg_periksa where tgl_registrasi='"+tbObat.getValueAt(i,5).toString()+"' ",tbObat.getValueAt(i,5).toString().replace("-","/")+"/",6); 
                umur="0";
                sttsumur="Th";
                if(Double.parseDouble(tbObat.getValueAt(i,19).toString())>0){
                    umur=""+Double.parseDouble(tbObat.getValueAt(i,19).toString());
                    sttsumur="Th";
                }else if(Double.parseDouble(tbObat.getValueAt(i,19).toString())==0){
                    if(Double.parseDouble(tbObat.getValueAt(i,20).toString())>0){
                        umur=""+Double.parseDouble(tbObat.getValueAt(i,20).toString());
                        sttsumur="Bl";
                    }else if(Double.parseDouble(tbObat.getValueAt(i,20).toString())==0){
                        umur=""+Double.parseDouble(tbObat.getValueAt(i,21).toString());
                        sttsumur="Hr";
                    }
                }
                if(Sequel.menyimpantf2("reg_periksa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",19,new String[]{
                    tbObat.getValueAt(i,10).toString(),no_rawat,tbObat.getValueAt(i,5).toString(),jam(),
                    tbObat.getValueAt(i,6).toString(),tbObat.getValueAt(i,3).toString(),tbObat.getValueAt(i,8).toString(),
                    tbObat.getValueAt(i,11).toString(),tbObat.getValueAt(i,12).toString()+", "+tbObat.getValueAt(i,13).toString()+
                    ", "+tbObat.getValueAt(i,14).toString()+", "+tbObat.getValueAt(i,15).toString()+
                    ", "+tbObat.getValueAt(i,16).toString(),tbObat.getValueAt(i,17).toString(),
                    ""+Sequel.cariIsiAngka("select registrasilama from poliklinik where kd_poli=?",tbObat.getValueAt(i,8).toString()),
                    "Belum","Lama","Ralan",tbObat.getValueAt(i,18).toString(),umur,sttsumur,"Belum Bayar",status
                })==true){
                    Sequel.mengedit3("skdp_bpjs","no_rkm_medis=? and tanggal_datang=?","status='Sudah Periksa'",2,new String[]{
                        tbObat.getValueAt(i,3).toString(),tbObat.getValueAt(i,5).toString()
                    });
                    Sequel.queryu2("update booking_registrasi set status='terdaftar' where no_rkm_medis=? and tanggal_periksa=?",2,new String[]{
                        tbObat.getValueAt(i,3).toString(),tbObat.getValueAt(i,5).toString()
                    });
                }
            }
        }
        tampil();
    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if (!var.getkode().equals("Admin Utama")) {
            tampil();
        }
    }//GEN-LAST:event_formWindowOpened

    private void DTPCari1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPCari1ItemStateChanged
        R2.setSelected(true);
    }//GEN-LAST:event_DTPCari1ItemStateChanged

    private void DTPCari3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPCari3ItemStateChanged
        R3.setSelected(true);
    }//GEN-LAST:event_DTPCari3ItemStateChanged

    private void DTPCari4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPCari4ItemStateChanged
        R3.setSelected(true);
    }//GEN-LAST:event_DTPCari4ItemStateChanged

    private void TanggalPeriksaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalPeriksaKeyPressed
        Valid.pindah(evt,TanggalBooking,NoReg);
    }//GEN-LAST:event_TanggalPeriksaKeyPressed

    private void TanggalPeriksaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TanggalPeriksaItemStateChanged
        try {
            isNomer();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_TanggalPeriksaItemStateChanged

    private void BtnPoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPoliKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPoliActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnDokter,btnPenjab);
        }
    }//GEN-LAST:event_BtnPoliKeyPressed

    private void BtnPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPoliActionPerformed
        if(aktifjadwal.equals("aktif")){
            if(var.getkode().equals("Admin Utama")){
                poli.isCek();        
                poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                poli.setLocationRelativeTo(internalFrame1);
                poli.setVisible(true);
            }else{
                poli2.isCek();                     
                poli2.SetHari(TanggalPeriksa.getDate());
                poli2.tampil(); 
                poli2.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                poli2.setLocationRelativeTo(internalFrame1);
                poli2.setVisible(true);
            }                
        }else{
            poli.isCek();        
            poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            poli.setLocationRelativeTo(internalFrame1);
            poli.setVisible(true);
        }
    }//GEN-LAST:event_BtnPoliActionPerformed

    private void NoRegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRegKeyPressed
        Valid.pindah(evt,TanggalPeriksa,BtnSimpan);
    }//GEN-LAST:event_NoRegKeyPressed

    private void BtnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPasienActionPerformed
        pasien.emptTeks();
        pasien.isCek();
        pasien.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.setLocationRelativeTo(internalFrame1);
        pasien.setVisible(true);
    }//GEN-LAST:event_BtnPasienActionPerformed

    private void BtnPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPasienKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPasienActionPerformed(null);
        }else{
            Valid.pindah(evt,TCari,BtnDokter);
        }   
    }//GEN-LAST:event_BtnPasienKeyPressed

    private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
        for(i=0;i<tbObat.getRowCount();i++){
            tbObat.setValueAt(false,i,0);
        }
    }//GEN-LAST:event_ppBersihkanActionPerformed

    private void ppSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSemuaActionPerformed
        for(i=0;i<tbObat.getRowCount();i++){
            tbObat.setValueAt(true,i,0);
        }
    }//GEN-LAST:event_ppSemuaActionPerformed

    private void kdpnjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpnjKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?",nmpnj,kdpnj.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnPoli.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            TanggalBooking.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPenjabActionPerformed(null);
        }
    }//GEN-LAST:event_kdpnjKeyPressed

    private void btnPenjabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPenjabActionPerformed
        var.setform("DlgBooking");
        pasien.penjab.onCari();
        pasien.penjab.isCek();
        pasien.penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.penjab.setLocationRelativeTo(internalFrame1);
        pasien.penjab.setVisible(true);
    }//GEN-LAST:event_btnPenjabActionPerformed

    private void btnPenjabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPenjabKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            btnPenjabActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnPoli,TanggalBooking);
        }
    }//GEN-LAST:event_btnPenjabKeyPressed

    private void KuotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KuotaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KuotaKeyPressed

    private void BtnEdit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEdit1ActionPerformed
        for(i=0;i<tbObat.getRowCount();i++){ 
            if(tbObat.getValueAt(i,0).toString().equals("true")){
                kirimwa.sendWa(tbObat.getValueAt(i,3).toString(),tbObat.getValueAt(i,4).toString(),tbObat.getValueAt(i,5).toString(),tbObat.getValueAt(i,9).toString());
            }
        }
    }//GEN-LAST:event_BtnEdit1ActionPerformed

    private void BtnEdit1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEdit1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnEdit1KeyPressed

    private void BtnEdit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEdit2ActionPerformed
        for(i=0;i<tbObat.getRowCount();i++){ 
            if(tbObat.getValueAt(i,0).toString().equals("true")){
                WindowBatalWhatsapp.setLocationRelativeTo(internalFrame1);
                WindowBatalWhatsapp.setVisible(true);
            }
        }
    }//GEN-LAST:event_BtnEdit2ActionPerformed

    private void BtnEdit2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEdit2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnEdit2KeyPressed

    private void BtnCloseIn6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn6ActionPerformed
        WindowBatalWhatsapp.dispose();
    }//GEN-LAST:event_BtnCloseIn6ActionPerformed

    private void BtnSimpan6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan6ActionPerformed
        for(i=0;i<tbObat.getRowCount();i++){ 
            if(tbObat.getValueAt(i,0).toString().equals("true")){
                kirimwa.sendWaBatal(tbObat.getValueAt(i,3).toString(),tbObat.getValueAt(i,4).toString(),tbObat.getValueAt(i,5).toString(),NmPoli1.getText(),NmPoli2.getText());        
            }
        }
    }//GEN-LAST:event_BtnSimpan6ActionPerformed

    private void BtnPoli1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPoli1ActionPerformed
        if(aktifjadwal.equals("aktif")){
            if(var.getkode().equals("Admin Utama")){
                poli3.isCek();        
                poli3.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                poli3.setLocationRelativeTo(internalFrame1);
                poli3.setVisible(true);
            }else{
                poli5.isCek();                     
                poli5.SetHari(TanggalPeriksa.getDate());
                poli5.tampil(); 
                poli5.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                poli5.setLocationRelativeTo(internalFrame1);
                poli5.setVisible(true);
            }                
        }else{
            poli3.isCek();        
            poli3.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            poli3.setLocationRelativeTo(internalFrame1);
            poli3.setVisible(true);
        }
    }//GEN-LAST:event_BtnPoli1ActionPerformed

    private void BtnPoli1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPoli1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPoli1KeyPressed

    private void BtnPoli2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPoli2ActionPerformed
        if(aktifjadwal.equals("aktif")){
            if(var.getkode().equals("Admin Utama")){
                poli4.isCek();        
                poli4.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                poli4.setLocationRelativeTo(internalFrame1);
                poli4.setVisible(true);
            }else{
                poli6.isCek();                     
                poli6.SetHari(TanggalPeriksa.getDate());
                poli6.tampil(); 
                poli6.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                poli6.setLocationRelativeTo(internalFrame1);
                poli6.setVisible(true);
            }                
        }else{
            poli4.isCek();        
            poli4.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            poli4.setLocationRelativeTo(internalFrame1);
            poli4.setVisible(true);
        }
    }//GEN-LAST:event_BtnPoli2ActionPerformed

    private void BtnPoli2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPoli2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPoli2KeyPressed

    private void BtnEdit3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEdit3ActionPerformed
        if(TNoRM.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRM,"pasien");
        }else if(NmDokter.getText().trim().equals("")||KdDokter.getText().trim().equals("")){
            Valid.textKosong(KdDokter,"Operator");
        }else if(NmPoli.getText().trim().equals("")||NmPoli.getText().trim().equals("")){
            Valid.textKosong(KdPoli,"Operasi");
        }else if(kdpnj.getText().trim().equals("")||nmpnj.getText().trim().equals("")){
            Valid.textKosong(kdpnj,"Cara Bayar");
        }else if(NoReg.getText().trim().equals("")){
            Valid.textKosong(NoReg,"No.Antri");
        }else{
            if(var.getkode().equals("Admin Utama")){
                isEditBooking();
            }else{
                if(aktifjadwal.equals("aktif")){
                    if(Sequel.cariInteger("select count(no_rkm_medis) from booking_registrasi where kd_dokter='"+KdDokter.getText()+"' and tanggal_periksa='"+Valid.SetTgl(TanggalPeriksa.getSelectedItem()+"")+"' ")>=kuota){
                        JOptionPane.showMessageDialog(null,"Eiiits, Kuota registrasi penuh..!!!");
                        TCari.requestFocus();
                    }else{
                        isEditBooking();
                    }                    
                }else{
                    isEditBooking();
                } 
            }                                       
        }
    }//GEN-LAST:event_BtnEdit3ActionPerformed

    private void BtnEdit3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEdit3KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
    }//GEN-LAST:event_BtnEdit3KeyPressed

    private void BtnCloseIn7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn7ActionPerformed
        WindowGanti.dispose();
    }//GEN-LAST:event_BtnCloseIn7ActionPerformed

    private void BtnSimpan7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan7ActionPerformed
        String nomer = "";
        String KdPoli1 = Sequel.cariIsi("select kd_poli_bpjs from maping_poli_bpjs where kd_poli_rs=?", KdPoli4.getText());
        String NmPoli1 = Sequel.cariIsi("select nm_poli_bpjs from maping_poli_bpjs where kd_poli_bpjs=?", KdPoli1);
        String KdDokter = Sequel.cariIsi("select kd_dokter_bpjs from maping_dokter_dpjpvclaim where kd_dokter=?", KdDokter1.getText());
        String NmDokter = Sequel.cariIsi("select nm_dokter_bpjs from maping_dokter_dpjpvclaim where kd_dokter=?", KdDokter1.getText());

        if (tabMode.getRowCount() == tbObat.getRowCount() && TCari.equals("")) {
            JOptionPane.showMessageDialog(null, "Jangan dipilih semua");
        } else if (KdPoli4.getText().trim().equals("") || NmPoli4.getText().trim().equals("")) {
            Valid.textKosong(KdPoli4, "Poli");
        } else if (KdDokter1.getText().trim().equals("") || NmDokter1.getText().trim().equals("")) {
            Valid.textKosong(KdDokter1, "Dokter");
        } else {
            int reply = JOptionPane.showConfirmDialog(rootPane, "Apakah yakin ingin menyimpan data...??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                for (i = 0; i < tbObat.getRowCount(); i++) {
                    if (tbObat.getValueAt(i, 0).toString().equals("true")) {
                        NoReg1.setText("");
                        isNomer2();
                        cekSKDP();//get nosurat nosep untuk edit surkon                        
//                        String surkonSEP = Sequel.cariIsi("SELECT no_sep FROM bridging_surat_kontrol_bpjs WHERE no_sep='" + Rtl2 + "'");
                        String surkonSurat = Sequel.cariIsi("SELECT no_surat FROM bridging_surat_kontrol_bpjs WHERE no_sep='" + Rtl2 + "'");                        
                        String cekCrontab = Sequel.cariIsi("SELECT no_sep FROM booking_cronbot WHERE no_sep='" + Rtl2 + "'");
                                                
                        if (Pj.equals("BPJ") || Pj.equals("A02")) {
                            if (!surkonSurat.isEmpty()) {
                                System.out.println(tbObat.getValueAt(i, 3).toString() + " sep: " + Rtl2 + " nosurat: " + surkonSurat);
                                if (surkonSurat.length() == 19) {
                                    nomer = kontrol.setNoSurat2(surkonSurat);
                                    if (nomer.equals("200")) {
                                        if (cekCrontab.isEmpty()) {
                                            isCrontab(Rtl2);
                                            isSimpan();
                                        }
                                        if (!cekCrontab.isEmpty()) {
                                            isEditCrontab(Rtl2, surkonSurat);
                                            isSimpan();
                                        }
                                    }
                                    if (nomer.equals("401")) {
                                        String bridgSEP = Sequel.cariIsi("SELECT no_sep FROM bridging_sep WHERE no_sep='" + Rtl2 + "'");
                                        if (!bridgSEP.isEmpty()) {
                                            isSimpan();
                                        }
                                    }
                                }
                                if (surkonSurat.length() < 19) {
                                    isSimpan();
                                }
                            }
                            if (surkonSurat.isEmpty()) {
                                isEditCrontab(Rtl2, surkonSurat);
                                isSimpan();
                            }
                        }
                        if (!Pj.equals("BPJ") || !Pj.equals("A02")) {
                            isSimpan();
                        }                        
                        isBookMjkn(tbObat.getValueAt(i, 3).toString(), tbObat.getValueAt(i, 5).toString());
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Berhasil ganti jadwal booking..!!!");
            isReset();
            tampil();
            WindowGanti.dispose();
        }
    }//GEN-LAST:event_BtnSimpan7ActionPerformed

    private void BtnDokter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokter1ActionPerformed
        if (aktifjadwal.equals("aktif")) {
            if (var.getkode().equals("Admin Utama")) {
                dokter3.isCek();
                dokter3.TCari.requestFocus();
                dokter3.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                dokter3.setLocationRelativeTo(internalFrame1);
                dokter3.setVisible(true);
            } else {
                dokter4.setPoli(NmPoli4.getText());
                dokter4.isCek();
                dokter4.SetHari(TanggalPeriksa.getDate());
                dokter4.tampil();
                dokter4.TCari.requestFocus();
                dokter4.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                dokter4.setLocationRelativeTo(internalFrame1);
                dokter4.setVisible(true);
            }
        } else {
            dokter3.isCek();
            dokter3.TCari.requestFocus();
            dokter3.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            dokter3.setLocationRelativeTo(internalFrame1);
            dokter3.setVisible(true);
        }
    }//GEN-LAST:event_BtnDokter1ActionPerformed

    private void BtnDokter1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokter1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnDokter1KeyPressed

    private void BtnPoli4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPoli4ActionPerformed
        if (aktifjadwal.equals("aktif")) {
            if (var.getkode().equals("Admin Utama")) {
                poli7.isCek();
                poli7.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                poli7.setLocationRelativeTo(internalFrame1);
                poli7.setVisible(true);
            } else {
                poli8.isCek();
                poli8.SetHari(TanggalPeriksa.getDate());
                poli8.tampil();
                poli8.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                poli8.setLocationRelativeTo(internalFrame1);
                poli8.setVisible(true);
            }
        } else {
            poli7.isCek();
            poli7.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            poli7.setLocationRelativeTo(internalFrame1);
            poli7.setVisible(true);
        }
    }//GEN-LAST:event_BtnPoli4ActionPerformed

    private void BtnPoli4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPoli4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPoli4KeyPressed

    private void NoReg1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoReg1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoReg1KeyPressed

    private void ppGantiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGantiActionPerformed
        for (i = 0; i < tbObat.getRowCount(); i++) {
            if (tbObat.getValueAt(i, 0).toString().equals("true")) {
                WindowGanti.setLocationRelativeTo(internalFrame1);
                WindowGanti.setVisible(true);
            }
        }
    }//GEN-LAST:event_ppGantiActionPerformed

    private void TanggalPeriksa1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TanggalPeriksa1ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalPeriksa1ItemStateChanged

    private void TanggalPeriksa1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalPeriksa1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalPeriksa1KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgBookingRegistrasi dialog = new DlgBookingRegistrasi(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCloseIn6;
    private widget.Button BtnCloseIn7;
    private widget.Button BtnDokter;
    private widget.Button BtnDokter1;
    private widget.Button BtnEdit;
    private widget.Button BtnEdit1;
    private widget.Button BtnEdit2;
    private widget.Button BtnEdit3;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPasien;
    private widget.Button BtnPoli;
    private widget.Button BtnPoli1;
    private widget.Button BtnPoli2;
    private widget.Button BtnPoli4;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpan6;
    private widget.Button BtnSimpan7;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPCari3;
    private widget.Tanggal DTPCari4;
    private widget.PanelBiasa FormInput;
    private widget.TextBox KdDokter;
    private widget.TextBox KdDokter1;
    private widget.TextBox KdPoli;
    private widget.TextBox KdPoli1;
    private widget.TextBox KdPoli2;
    private widget.TextBox KdPoli4;
    private widget.TextBox Kuota;
    private widget.Label LCount;
    private widget.TextBox NmDokter;
    private widget.TextBox NmDokter1;
    private widget.TextBox NmPoli;
    private widget.TextBox NmPoli1;
    private widget.TextBox NmPoli2;
    private widget.TextBox NmPoli4;
    private widget.TextBox NoReg;
    private widget.TextBox NoReg1;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JPopupMenu Popup;
    private widget.RadioButton R2;
    private widget.RadioButton R3;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TPasien;
    private widget.Tanggal TanggalBooking;
    private widget.Tanggal TanggalPeriksa;
    private widget.Tanggal TanggalPeriksa1;
    private javax.swing.JDialog WindowBatalWhatsapp;
    private javax.swing.JDialog WindowGanti;
    private widget.Button btnPenjab;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame7;
    private widget.InternalFrame internalFrame8;
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
    private widget.Label jLabel22;
    private widget.Label jLabel25;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private widget.TextBox kdpnj;
    private widget.TextBox nmpnj;
    private widget.panelisi panelCari;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass8;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppGanti;
    private javax.swing.JMenuItem ppSemua;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    private void tampil() {     
        if(R2.isSelected()==true){
            status=" booking_registrasi.tanggal_booking between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' ";
        }else if(R3.isSelected()==true){
            status=" booking_registrasi.tanggal_periksa between '"+Valid.SetTgl(DTPCari3.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari4.getSelectedItem()+"")+"' ";           
        }
        Valid.tabelKosong(tabMode);
        try {
            ps=koneksi.prepareStatement(
                    "select booking_registrasi.tanggal_booking,booking_registrasi.jam_booking,booking_registrasi.no_rkm_medis, "+
                    "pasien.nm_pasien,booking_registrasi.tanggal_periksa,booking_registrasi.kd_dokter,"+
                    "dokter.nm_dokter,booking_registrasi.kd_poli,poliklinik.nm_poli,booking_registrasi.no_reg, "+
                    "pasien.namakeluarga,pasien.alamatpj,pasien.kelurahanpj,pasien.kecamatanpj,"+
                    "pasien.kabupatenpj,pasien.propinsipj,pasien.keluarga,pasien.kd_pj,TIMESTAMPDIFF(YEAR, pasien.tgl_lahir, CURDATE()) as tahun, "+
                    "(TIMESTAMPDIFF(MONTH, pasien.tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, pasien.tgl_lahir, CURDATE()) div 12) * 12)) as bulan, "+
                    "TIMESTAMPDIFF(DAY, DATE_ADD(DATE_ADD(pasien.tgl_lahir,INTERVAL TIMESTAMPDIFF(YEAR, pasien.tgl_lahir, CURDATE()) YEAR), INTERVAL TIMESTAMPDIFF(MONTH, pasien.tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, pasien.tgl_lahir, CURDATE()) div 12) * 12) MONTH), CURDATE()) as hari, "+
                    "booking_registrasi.limit_reg,booking_registrasi.status,booking_registrasi.kd_pj,penjab.png_jawab, pasien.no_tlp from booking_registrasi inner join pasien "+
                    "inner join dokter inner join poliklinik inner join penjab on "+
                    "booking_registrasi.no_rkm_medis=pasien.no_rkm_medis and booking_registrasi.kd_pj=penjab.kd_pj and "+
                    "booking_registrasi.kd_dokter=dokter.kd_dokter and booking_registrasi.kd_poli=poliklinik.kd_poli "+
                    "where "+status+" and booking_registrasi.no_rkm_medis like ? or "+
                    status+" and pasien.nm_pasien like ? or "+
                    status+" and poliklinik.nm_poli like ? or "+
                    status+" and dokter.nm_dokter like ? order by booking_registrasi.tanggal_booking,dokter.nm_dokter");
            try {
                ps.setString(1,"%"+TCari.getText().trim()+"%");
                ps.setString(2,"%"+TCari.getText().trim()+"%");
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){                    
                    tabMode.addRow(new Object[]{
                        false,rs.getString("tanggal_booking"),rs.getString("jam_booking"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),
                        rs.getString("tanggal_periksa"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),
                        rs.getString("kd_poli"),rs.getString("nm_poli"),rs.getString("no_reg"),
                        rs.getString("namakeluarga"),rs.getString("alamatpj"),rs.getString("kelurahanpj"),
                        rs.getString("kecamatanpj"),rs.getString("kabupatenpj"),rs.getString("propinsipj"),
                        rs.getString("keluarga"),rs.getString("kd_pj"),rs.getString("tahun"),rs.getString("bulan"),
                        rs.getString("hari"),rs.getString("limit_reg").replaceAll("0","Offline").replace("1","Online"),
                        rs.getString("status"),rs.getString("kd_pj"),rs.getString("png_jawab"),rs.getString("no_tlp")
                    });                    
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        } 
        LCount.setText(""+tabMode.getRowCount());
    }


    public void emptTeks() {
        TNoRM.setText("");
        TPasien.setText("");
        kdpnj.setText("");
        nmpnj.setText("");
        TanggalBooking.setDate(new Date());
        BtnPasien.requestFocus();
        isNomer();
    }
    
    private void isNomer(){
        switch (URUTNOREG) {
            case "poli":
                Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_poli='"+KdPoli.getText()+"' and tanggal_periksa='"+Valid.SetTgl(TanggalPeriksa.getSelectedItem()+"")+"'","",3,NoReg);
                break;
            case "dokter":
                Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_dokter='"+KdDokter.getText()+"' and tanggal_periksa='"+Valid.SetTgl(TanggalPeriksa.getSelectedItem()+"")+"'","",3,NoReg);
                break;
            case "dokter + poli":             
                Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_dokter='"+KdDokter.getText()+"' and kd_poli='"+KdPoli.getText()+"' and tanggal_periksa='"+Valid.SetTgl(TanggalPeriksa.getSelectedItem()+"")+"'","",3,NoReg);
                break;
            default:
                Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_dokter='"+KdDokter.getText()+"' and tanggal_periksa='"+Valid.SetTgl(TanggalPeriksa.getSelectedItem()+"")+"'","",3,NoReg);
                break;
        }
    }
    
    private void isNomer2(){
        Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_poli='"+KdPoli4.getText()+"' and tanggal_periksa='"+Valid.SetTgl(TanggalPeriksa1.getSelectedItem()+"")+"'","",3,NoReg1);
        }

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){            
            Valid.SetTgl(TanggalBooking,tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString()); 
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Valid.SetTgl(TanggalPeriksa,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            KdDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            NmDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            KdPoli.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            NmPoli.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            NoReg.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            kdpnj.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            nmpnj.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
        }
    }
    
    public void setNoRm(String norm,String nama) {
        TNoRM.setText(norm);
        TPasien.setText(nama);
        TCari.setText(norm);
        ChkInput.setSelected(true);
        isForm();
        tampil();
    }
    
    public void setNoRm(String norm,String nama,String kodepoli,String namapoli,String kodedokter,String namadokter) {
        TNoRM.setText(norm);
        TPasien.setText(nama);
        KdPoli.setText(kodepoli);
        NmPoli.setText(namapoli);
        KdDokter.setText(kodedokter);
        NmDokter.setText(namadokter);
        TCari.setText(norm);
        ChkInput.setSelected(true);
        isForm();
        tampil();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,156));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(var.getbooking_registrasi());
        BtnHapus.setEnabled(var.getbooking_registrasi());
        BtnPrint.setEnabled(var.getbooking_registrasi());
        BtnEdit.setEnabled(var.getbooking_registrasi());
    }

    private String jam(){
        int nilai_jam;
        int nilai_menit;
        int nilai_detik;
        String nol_jam = "";
        String nol_menit = "";
        String nol_detik = "";
        Date now = Calendar.getInstance().getTime();
        nilai_jam = now.getHours();
        nilai_menit = now.getMinutes();
        nilai_detik = now.getSeconds();

        // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
        if (nilai_jam <= 9) {
            // Tambahkan "0" didepannya
            nol_jam = "0";
        }
        // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
        if (nilai_menit <= 9) {
            // Tambahkan "0" didepannya
            nol_menit = "0";
        }
        // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
        if (nilai_detik <= 9) {
            // Tambahkan "0" didepannya
            nol_detik = "0";
        }
        String jam = nol_jam + Integer.toString(nilai_jam);
        String menit = nol_menit + Integer.toString(nilai_menit);
        String detik = nol_detik + Integer.toString(nilai_detik);
        return jam+":"+menit+":"+detik;
    }

    private void isBooking() {
        if(Sequel.menyimpantf("booking_registrasi","?,?,?,?,?,?,?,?,?,?,?","Pasien dan Tanggal",11,new String[]{
             Valid.SetTgl(TanggalBooking.getSelectedItem()+""),TanggalBooking.getSelectedItem().toString().substring(11,19),TNoRM.getText(),
             Valid.SetTgl(TanggalPeriksa.getSelectedItem()+""),KdDokter.getText(),
             KdPoli.getText(),NoReg.getText(),kdpnj.getText(),"0",
             Valid.SetTgl(TanggalPeriksa.getSelectedItem()+"")+" "+TanggalBooking.getSelectedItem().toString().substring(11,19),
             "belum"
           })==true){
            emptTeks();
            tampil();
        } 
    }
    
    private void isEditBooking() {
        int reply = JOptionPane.showConfirmDialog(rootPane, "Apakah yakin ingin menyimpan data...??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            if (Sequel.mengedittf("booking_registrasi", "no_rkm_medis=? and tanggal_periksa=?", "tanggal_periksa=?,kd_dokter=?,kd_poli=?,no_reg=?,kd_pj=?", 7, new String[]{
                Valid.SetTgl(TanggalPeriksa.getSelectedItem() + ""), KdDokter.getText(),
                KdPoli.getText(), NoReg.getText(), kdpnj.getText(), tabMode.getValueAt(tbObat.getSelectedRow(), 3).toString(), tabMode.getValueAt(tbObat.getSelectedRow(), 5).toString()
            }) == true) {
                emptTeks();
                tampil();
            }
        }
    }
    
    public void isReset() {
        jml = tbObat.getRowCount();
        for (i = 0; i < jml; i++) {
            tbObat.setValueAt(true, i, 0);
        }
    }
    
    public void cekSKDP() {
        try {
            ps = koneksi.prepareStatement("SELECT rtl2,alasan2,kd_pj FROM skdp_bpjs INNER JOIN booking_registrasi ON skdp_bpjs.no_rkm_medis=booking_registrasi.no_rkm_medis AND skdp_bpjs.tanggal_datang=booking_registrasi.tanggal_periksa "
                    + " AND booking_registrasi.no_rkm_medis=? AND booking_registrasi.tanggal_periksa=? LIMIT 1");
            Rtl2 = "";//sep
            Alasan2 = "";//nosurat
            Pj = "";
            try {
                ps.setString(1, tbObat.getValueAt(i, 3).toString());
                ps.setString(2, tbObat.getValueAt(i, 5).toString());
                rs = ps.executeQuery();
                if (rs.next()) {
                    Rtl2 = rs.getString("rtl2");
                    Alasan2 = rs.getString("alasan2");
                    Pj = rs.getString("kd_pj");
                }
            } catch (Exception e) {
                System.out.println("Notifikasi cekSKDP() : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi cekSKDP() 2 : " + e);
        }
    }
    
    private void isSimpan(){
        if (Sequel.mengedittf("booking_registrasi", "no_rkm_medis=? and tanggal_periksa=?", "kd_dokter=?,kd_poli=?,tanggal_periksa=?, no_reg=?", 6,
                new String[]{
                    KdDokter1.getText(), KdPoli4.getText(), Valid.SetTgl(TanggalPeriksa1.getSelectedItem() + ""), NoReg1.getText(),
                    tbObat.getValueAt(i, 3).toString(), tbObat.getValueAt(i, 5).toString()
                }) == true) {
            Sequel.mengedit3("skdp_bpjs", "no_rkm_medis=? and tanggal_datang=?", "tanggal_datang=?,kd_dokter=?", 4, new String[]{
                Valid.SetTgl(TanggalPeriksa1.getSelectedItem() + ""), KdDokter1.getText(),
                tbObat.getValueAt(i, 3).toString(), tbObat.getValueAt(i, 5).toString()
            });
        }
    }
    
    private void isCrontab(String sep) {
        try {
            ps = koneksi.prepareStatement("SELECT * FROM bridging_surat_kontrol_bpjs WHERE no_sep='" + sep + "'");
            rs = ps.executeQuery();
            if (rs.next()) {                    
                if (Sequel.menyimpantf("booking_cronbot", "?,?,?,?,?,?,?,?", "No. SEP", 8, new String[]{
                    rs.getString("no_sep"), rs.getString("tgl_surat"), rs.getString("tgl_rencana"), tbObat.getValueAt(i, 3).toString(),
                    KdPoli4.getText(), KdDokter1.getText(), "Belum", null
                }) == true) {
                    Sequel.meghapus("bridging_surat_kontrol_bpjs", "no_sep", rs.getString("no_sep"));
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi isCrontab(): " + e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception ex) {
                System.out.println("Notifikasi isCrontab(): " + ex);
            }
        }
    }
    
    private void isEditCrontab(String sep, String surkon) {
        if (Sequel.mengedittf("booking_cronbot", "no_sep=?", "status=?,keterangan=?,kd_poli=?,kd_dokter=?,tanggal_periksa=?", 6, new String[]{
            "Belum", null, KdPoli4.getText(), KdDokter1.getText(), Valid.SetTgl(TanggalPeriksa1.getSelectedItem() + ""), sep
        }) == true) {
            if (!surkon.isEmpty()) {
                Sequel.meghapus("bridging_surat_kontrol_bpjs", "no_sep", sep);
            }
        }
    }
    
    private void isBookMjkn(String norm, String tgl_periksa) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter ymd = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime now = LocalDateTime.now();
        try {
            ps = koneksi.prepareStatement("SELECT kodebooking FROM mlite_antrian_referensi WHERE no_rkm_medis='" + norm + "' AND tanggal_periksa='" + tgl_periksa + "'");
            rs = ps.executeQuery();
            if (rs.next()) {
                if (Sequel.menyimpantf("mlite_antrian_referensi_batal", "?,?,?", "Data", 3, new String[]{
                    dtf.format(now), rs.getString("kodebooking"), "Perubahan Jadwal Dokter " + norm + " " + ymd.format(now) + ""
                }) == true) {
                    Sequel.queryu("update mlite_antrian_referensi set status_kirim='Batal' where kodebooking='" + rs.getString("kodebooking") + "'");
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi isBookMjkn(): " + e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception ex) {
                System.out.println("Notifikasi isBookMjkn(): " + ex);
            }
        }
    }   
}
