package simrskhanza;

import bridging.ApiOrthanc;
import bridging.OrthancDICOM;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonObject;
import kepegawaian.DlgCariPetugas;
import keuangan.Jurnal;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;

public class DlgCariPeriksaRadiologi extends javax.swing.JDialog {

    private final DefaultTableModel tabMode, tabModeDicom,tabModeSeries,tabModeRiwayat, tabModeLab;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Jurnal jur = new Jurnal();
    private Connection koneksi = koneksiDB.condb();
    private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    private DlgPasien member = new DlgPasien(null, false);
    private DlgCariDokter dokter = new DlgCariDokter(null, false);
    private DlgCariDokter dokter2 = new DlgCariDokter(null, false);
    private final Properties prop = new Properties();
    private DlgCariPetugas petugas = new DlgCariPetugas(null, false); 
    public DlgRujukMasuk rujukmasuk = new DlgRujukMasuk(null, false);
    private JsonNode root,seriesbyid,hasilexpertise;
    private int i;
    private ApiOrthanc orthanc = new ApiOrthanc();
    private StringBuilder htmlContent;
    private PreparedStatement ps, ps2, ps3, ps4, ps5, ps6, ps7, ps8, psrekening, ps9;
    private ResultSet rs, rs2, rs3, rs5, rs6, rs7, rs8, rsrekening, rs9;
    private String kamar, namakamar, pemeriksaan = "", pilihan = "", status = "", finger = "", url_orthanc = "", judul="",hasil="", kesan="", saran="", queryriwayat;
    private double ttl = 0, item = 0;
    private double ttljmdokter = 0, ttljmpetugas = 0, ttlkso = 0, ttlpendapatan = 0, ttlbhp = 0;
    private String kdpetugas = "", kdpenjab = "", Suspen_Piutang_Radiologi_Ranap = "", Radiologi_Ranap = "", Beban_Jasa_Medik_Dokter_Radiologi_Ranap = "",
            Utang_Jasa_Medik_Dokter_Radiologi_Ranap = "", Beban_Jasa_Medik_Petugas_Radiologi_Ranap = "",
            Utang_Jasa_Medik_Petugas_Radiologi_Ranap = "", Beban_Kso_Radiologi_Ranap = "", Utang_Kso_Radiologi_Ranap = "",
            HPP_Persediaan_Radiologi_Rawat_Inap = "", Persediaan_BHP_Radiologi_Rawat_Inap = "",klinis="";
    private DlgCariHasilPeriksaRad radiologi = new DlgCariHasilPeriksaRad(null, false);

    /**
     * Creates new form DlgProgramStudi
     *
     * @param parent
     * @param modal
     */
    public DlgCariPeriksaRadiologi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row = {"No.Rawat", "Pasien", "Petugas", "Tgl.Periksa", "Jam Periksa", "Dokter Perujuk", "Penanggung Jawab", "Klinis", "Asal Rujukan"};
        tabMode = new DefaultTableModel(null, row) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(110);
            } else if (i == 1) {
                column.setPreferredWidth(300);
            } else if (i == 2) {
                column.setPreferredWidth(150);
            } else if (i == 3) {
                column.setPreferredWidth(200);
            } else if (i == 4) {
                column.setPreferredWidth(100);
            } else if (i == 5) {
                column.setPreferredWidth(150);
            } else if (i == 6) {
                column.setPreferredWidth(150);
            } else if (i == 7) {
                column.setPreferredWidth(150);
            } else if (i == 8) {
                column.setPreferredWidth(150);
            }
        }

        tbDokter.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

                String pasien = (String) table.getModel().getValueAt(row, 1);

                if (var.getkode().equals("DR00019") || var.getkode().equals("D0000132")) {
                    if (pasien.contains("POLI MCU")) {
                        setBackground(new Color(27, 117, 209));
                        setForeground(Color.BLACK);
                    } else {
                        if (row % 2 == 1) {
                            setForeground(Color.BLACK);
                            setBackground(new Color(226, 234, 248));
                        } else {
                            setForeground(Color.BLACK);
                            setBackground(new Color(255, 255, 255));
                        }
                    }
                } else {
                    if (row % 2 == 1) {
                        setForeground(Color.BLACK);
                        setBackground(new Color(226, 234, 248));
                    } else {
                        setForeground(Color.BLACK);
                        setBackground(new Color(255, 255, 255));
                    }
                }
                if (isSelected) {
                    setForeground(Color.RED);
                }
                return this;
            }
        });

        tabModeDicom = new DefaultTableModel(null, new Object[]{
            "Tanggal", "UUID Pasien", "ID Studies", "ID Series", "UID Instance Studies"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbListDicom.setModel(tabModeDicom);
        tbListDicom.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbListDicom.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 5; i++) {
            TableColumn column = tbListDicom.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(100);
            } else if (i == 1) {
                column.setPreferredWidth(100);
            } else if (i == 2) {
                column.setPreferredWidth(310);
            } else if (i == 3) {
                column.setPreferredWidth(310);
            } else if (i == 4) {
                column.setPreferredWidth(360);
            }
        }
        tbListDicom.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeSeries = new DefaultTableModel(null, new Object[]{
            "Modality","Body Part", "Instance"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbListDicom1.setModel(tabModeSeries);
        tbListDicom1.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbListDicom1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbListDicom1.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(100);
            } else if (i == 1) {
                column.setPreferredWidth(100);
            } else if (i == 1) {
                column.setPreferredWidth(100);
            }
        }
        tbListDicom1.setDefaultRenderer(Object.class, new WarnaTable());
        
        Object[] row2 = {"No.Rawat", "Pasien", "Petugas", "Tgl.Periksa", "Jam Periksa", "Dokter Perujuk", "Penanggung Jawab", "Klinis", "Asal Rujukan"};
        tabModeRiwayat = new DefaultTableModel(null, row2) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbRiwayat.setModel(tabModeRiwayat);
        tbRiwayat.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbRiwayat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = tbRiwayat.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(110);
            } else if (i == 1) {
                column.setPreferredWidth(300);
            } else if (i == 2) {
                column.setPreferredWidth(150);
            } else if (i == 3) {
                column.setPreferredWidth(200);
            } else if (i == 4) {
                column.setPreferredWidth(100);
            } else if (i == 5) {
                column.setPreferredWidth(150);
            } else if (i == 6) {
                column.setPreferredWidth(150);
            } else if (i == 7) {
                column.setPreferredWidth(150);
            } else if (i == 8) {
                column.setPreferredWidth(150);
            }
        }
        tbRiwayat.setDefaultRenderer(Object.class, new WarnaTable());
        
        Object[] row3={"Petugas","Tgl.Periksa","Jam Periksa","Dokter Perujuk","Penanggung Jawab"};
        tabModeLab=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbTranfusiDarah.setModel(tabModeLab);

        tbTranfusiDarah.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbTranfusiDarah.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 5; i++) {
            TableColumn column = tbTranfusiDarah.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(200);
            }else if(i==1){
                column.setPreferredWidth(130);
            }else if(i==2){
                column.setPreferredWidth(100);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(200);
            }
        }
        tbTranfusiDarah.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

                String keterangan = (String) table.getModel().getValueAt(row, 4);
                
                switch (keterangan) {
                    case "H":
                        setBackground(new Color(255, 152, 152));
                        setForeground(Color.WHITE);
                        break;
                    case "L":
                        setBackground(new Color(102, 178, 255));
                        setForeground(Color.WHITE);
                        break;
                    default:
                        if (row % 2 == 1) {
                            setForeground(Color.BLACK);
                            setBackground(new Color(226, 234, 248));
                        } else {
                            setForeground(Color.BLACK);
                            setBackground(new Color(255, 255, 255));
                        }   break;
                }
                if (isSelected) {
                    setForeground(Color.RED);
                }
                return this;
            }
        });
        
        NoRawat.setDocument(new batasInput((byte) 17).getKata(NoRawat));
        kdmem.setDocument(new batasInput((byte) 8).getKata(kdmem));
        kdptg.setDocument(new batasInput((byte) 25).getKata(kdptg));
        TCari.setDocument(new batasInput((byte) 100).getKata(TCari));
        if (koneksiDB.cariCepat().equals("aktif")) {
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCari.getText().length() > 2) {
                        tampil();
                    }
                }
            });
        }
        member.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("DlgCariPeriksaRadiologi")) {
                    if (member.getTable().getSelectedRow() != -1) {
                        kdmem.setText(member.getTable().getValueAt(member.getTable().getSelectedRow(), 1).toString());
                        nmmem.setText(member.getTable().getValueAt(member.getTable().getSelectedRow(), 2).toString());
                    }
                    kdmem.requestFocus();
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

        member.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (var.getform().equals("DlgCariPeriksaRadiologi")) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        member.dispose();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("DlgCariPeriksaRadiologi")) {
                    if (petugas.getTable().getSelectedRow() != -1) {
                        if (pilihan.equals("ubahpetugas")) {
                            KdPtgUbah.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                            NmPtgUbah.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                            KdPtgUbah.requestFocus();
                        } else if (pilihan.equals("caripetugas")) {
                            kdptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                            nmptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                            kdptg.requestFocus();
                        }
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

        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("DlgCariPeriksaRadiologi")) {
                    if (dokter.getTable().getSelectedRow() != -1) {
                        if (pilihan.equals("perujuk")) {
                            KodePerujuk.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                            NmPerujuk.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                            KodePerujuk.requestFocus();
                        } else if (pilihan.equals("penjab")) {
                            KodePj.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                            NmDokterPj.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                            KodePj.requestFocus();
                        }
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
        
        rujukmasuk.WindowPerujuk.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("DlgCariPeriksaRadiologi")) {
                    if (rujukmasuk.tbPerujuk.getSelectedRow() != -1) {
                        NmPerujuk.setText(rujukmasuk.tbPerujuk.getValueAt(rujukmasuk.tbPerujuk.getSelectedRow(), 0).toString());
//                        alamatperujuk = rujukmasuk.tbPerujuk.getValueAt(rujukmasuk.tbPerujuk.getSelectedRow(), 1).toString();
                    }
//                    AsalRujukan.requestFocus();
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

        radiologi.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("DlgCariPeriksaRadiologi")) {
                    if (radiologi.getTable().getSelectedRow() != -1) {
                        TJudul.setText(radiologi.getTable().getValueAt(radiologi.getTable().getSelectedRow(), 1).toString());
                        TKlinis.setText(radiologi.getTable().getValueAt(radiologi.getTable().getSelectedRow(), 2).toString());
                        HasilPeriksa.setText(radiologi.getTable().getValueAt(radiologi.getTable().getSelectedRow(), 3).toString());
                        TKesan.setText(radiologi.getTable().getValueAt(radiologi.getTable().getSelectedRow(), 4).toString());
                        TSaran.setText(radiologi.getTable().getValueAt(radiologi.getTable().getSelectedRow(), 5).toString());
                    }
                    TJudul.requestFocus();
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
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter2.getTable().getSelectedRow()!= -1){
                    kddokter.setText(dokter2.getTable().getValueAt(dokter2.getTable().getSelectedRow(),0).toString());
                    nmdokter.setText(dokter2.getTable().getValueAt(dokter2.getTable().getSelectedRow(),1).toString());
                }      
                kddokter.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {dokter2.emptTeks();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });   
        
        dokter2.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    dokter2.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        try {
            psrekening = koneksi.prepareStatement("select * from set_akun_ranap");
            try {
                rsrekening = psrekening.executeQuery();
                while (rsrekening.next()) {
                    Suspen_Piutang_Radiologi_Ranap = rsrekening.getString("Suspen_Piutang_Radiologi_Ranap");
                    Radiologi_Ranap = rsrekening.getString("Radiologi_Ranap");
                    Beban_Jasa_Medik_Dokter_Radiologi_Ranap = rsrekening.getString("Beban_Jasa_Medik_Dokter_Radiologi_Ranap");
                    Utang_Jasa_Medik_Dokter_Radiologi_Ranap = rsrekening.getString("Utang_Jasa_Medik_Dokter_Radiologi_Ranap");
                    Beban_Jasa_Medik_Petugas_Radiologi_Ranap = rsrekening.getString("Beban_Jasa_Medik_Petugas_Radiologi_Ranap");
                    Utang_Jasa_Medik_Petugas_Radiologi_Ranap = rsrekening.getString("Utang_Jasa_Medik_Petugas_Radiologi_Ranap");
                    Beban_Kso_Radiologi_Ranap = rsrekening.getString("Beban_Kso_Radiologi_Ranap");
                    Utang_Kso_Radiologi_Ranap = rsrekening.getString("Utang_Kso_Radiologi_Ranap");
                    HPP_Persediaan_Radiologi_Rawat_Inap = rsrekening.getString("HPP_Persediaan_Radiologi_Rawat_Inap");
                    Persediaan_BHP_Radiologi_Rawat_Inap = rsrekening.getString("Persediaan_BHP_Radiologi_Rawat_Inap");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : " + e);
            } finally {
                if (rsrekening != null) {
                    rsrekening.close();
                }
                if (psrekening != null) {
                    psrekening.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        java.awt.Font font = HasilPeriksa.getFont();
        float size = font.getSize() + 14;
        HasilPeriksa.setFont(font.deriveFont(size));
    
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Kd2 = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCetakNota = new javax.swing.JMenuItem();
        MnLihatHasil = new javax.swing.JMenuItem();
        MnLihatGambar = new javax.swing.JMenuItem();
        MnUbahDokterPetugas = new javax.swing.JMenuItem();
        MnRiwayatRad = new javax.swing.JMenuItem();
        MnTarikHasil = new javax.swing.JMenuItem();
        WindowHasil = new javax.swing.JDialog();
        internalFrame6 = new widget.InternalFrame();
        panelGlass6 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnPrint1 = new widget.Button();
        BtnCloseIn5 = new widget.Button();
        label12 = new widget.Label();
        label14 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        TKesan = new widget.TextArea();
        scrollPane3 = new widget.ScrollPane();
        TSaran = new widget.TextArea();
        Scroll3 = new widget.ScrollPane();
        HasilPeriksa = new widget.TextArea();
        panelIsi7 = new widget.panelisi();
        label17 = new widget.Label();
        TJudul = new widget.TextBox();
        label19 = new widget.Label();
        TKlinis = new widget.TextBox();
        btnHasilRad = new widget.Button();
        TNoRw2 = new widget.TextBox();
        label22 = new widget.Label();
        BtnLihatLab = new widget.Button();
        internalFrame8 = new widget.InternalFrame();
        Scroll5 = new widget.ScrollPane();
        tbTranfusiDarah = new widget.Table();
        Penjab = new widget.TextBox();
        Jk = new widget.TextBox();
        Umur = new widget.TextBox();
        Alamat = new widget.TextBox();
        NoRM = new widget.TextBox();
        WindowGantiDokterParamedis = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        FormInput = new widget.panelisi();
        BtnSimpan4 = new widget.Button();
        BtnCloseIn4 = new widget.Button();
        jLabel7 = new widget.Label();
        KodePj = new widget.TextBox();
        NmDokterPj = new widget.TextBox();
        btnDokterPj = new widget.Button();
        btnDokter = new widget.Button();
        NmPerujuk = new widget.TextBox();
        KodePerujuk = new widget.TextBox();
        jLabel9 = new widget.Label();
        jLabel12 = new widget.Label();
        KdPtgUbah = new widget.TextBox();
        NmPtgUbah = new widget.TextBox();
        btnPetugas1 = new widget.Button();
        NoOrder = new widget.TextBox();
        Perujuk = new widget.TextBox();
        Petugas = new widget.TextBox();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        MnTarikGambar = new javax.swing.JMenuItem();
        WindowRiwayatRad = new javax.swing.JDialog();
        internalFrame7 = new widget.InternalFrame();
        Scroll4 = new widget.ScrollPane();
        tbRiwayat = new widget.Table();
        panelGlass5 = new widget.panelisi();
        label27 = new widget.Label();
        BtnKeluar1 = new widget.Button();
        panelisi4 = new widget.panelisi();
        label21 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        label28 = new widget.Label();
        jLabel6 = new widget.Label();
        cmbHlm = new widget.ComboBox();
        BtnCari3 = new widget.Button();
        tglPeriksa = new widget.TextBox();
        jamPeriksa = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoRawat = new widget.TextBox();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label16 = new widget.Label();
        label13 = new widget.Label();
        kdmem = new widget.TextBox();
        kdptg = new widget.TextBox();
        nmmem = new widget.TextBox();
        nmptg = new widget.TextBox();
        btnPasien = new widget.Button();
        btnPetugas = new widget.Button();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        BtnSeek4 = new widget.Button();
        label20 = new widget.Label();
        kddokter = new widget.TextBox();
        nmdokter = new widget.TextBox();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label9 = new widget.Label();
        BtnHapus = new widget.Button();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi6 = new widget.panelisi();
        jPanel3 = new javax.swing.JPanel();
        Scroll6 = new widget.ScrollPane();
        tbListDicom = new widget.Table();
        panelGlass8 = new widget.panelisi();
        BtnCari1 = new widget.Button();
        btnDicom = new widget.Button();
        BtnCari2 = new widget.Button();
        jPanel4 = new javax.swing.JPanel();
        scrollPane4 = new widget.ScrollPane();
        tbListDicom1 = new widget.Table();
        scrollPane5 = new widget.ScrollPane();
        jLabel1 = new javax.swing.JLabel();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCetakNota.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakNota.setForeground(new java.awt.Color(100, 80, 80));
        MnCetakNota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakNota.setText("Cetak Nota Radiologi");
        MnCetakNota.setName("MnCetakNota"); // NOI18N
        MnCetakNota.setPreferredSize(new java.awt.Dimension(250, 28));
        MnCetakNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakNotaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakNota);

        MnLihatHasil.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLihatHasil.setForeground(new java.awt.Color(100, 80, 80));
        MnLihatHasil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLihatHasil.setText("Tampilkan Hasil Pemeriksaan");
        MnLihatHasil.setName("MnLihatHasil"); // NOI18N
        MnLihatHasil.setPreferredSize(new java.awt.Dimension(250, 28));
        MnLihatHasil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLihatHasilActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnLihatHasil);

        MnLihatGambar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLihatGambar.setForeground(new java.awt.Color(100, 80, 80));
        MnLihatGambar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLihatGambar.setText("Tampilkan Gambar Radiologi");
        MnLihatGambar.setName("MnLihatGambar"); // NOI18N
        MnLihatGambar.setPreferredSize(new java.awt.Dimension(250, 28));
        MnLihatGambar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLihatGambarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnLihatGambar);

        MnUbahDokterPetugas.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUbahDokterPetugas.setForeground(new java.awt.Color(70, 70, 70));
        MnUbahDokterPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUbahDokterPetugas.setText("Ubah P.J.Lab, Perujuk & Petugas");
        MnUbahDokterPetugas.setName("MnUbahDokterPetugas"); // NOI18N
        MnUbahDokterPetugas.setPreferredSize(new java.awt.Dimension(220, 26));
        MnUbahDokterPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUbahDokterPetugasActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnUbahDokterPetugas);

        MnRiwayatRad.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRiwayatRad.setForeground(new java.awt.Color(100, 80, 80));
        MnRiwayatRad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRiwayatRad.setText("Riwayat Pemeriksaan Radiologi");
        MnRiwayatRad.setName("MnRiwayatRad"); // NOI18N
        MnRiwayatRad.setPreferredSize(new java.awt.Dimension(250, 28));
        MnRiwayatRad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRiwayatRadActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnRiwayatRad);

        MnTarikHasil.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTarikHasil.setForeground(new java.awt.Color(100, 80, 80));
        MnTarikHasil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTarikHasil.setText("Tarik Hasil Expertise");
        MnTarikHasil.setName("MnTarikHasil"); // NOI18N
        MnTarikHasil.setPreferredSize(new java.awt.Dimension(250, 28));
        MnTarikHasil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnTarikHasilActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnTarikHasil);

        WindowHasil.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowHasil.setName("WindowHasil"); // NOI18N
        WindowHasil.setUndecorated(true);
        WindowHasil.setResizable(false);
        WindowHasil.getContentPane().setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Hasil Pemeriksaan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(100, 80, 80))); // NOI18N
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setLayout(new java.awt.BorderLayout());

        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(55, 210));
        panelGlass6.setLayout(null);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('U');
        BtnSimpan.setText("Update");
        BtnSimpan.setToolTipText("Alt+U");
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
        panelGlass6.add(BtnSimpan);
        BtnSimpan.setBounds(10, 170, 100, 30);

        BtnPrint1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint1.setMnemonic('T');
        BtnPrint1.setText("Cetak");
        BtnPrint1.setToolTipText("Alt+T");
        BtnPrint1.setName("BtnPrint1"); // NOI18N
        BtnPrint1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint1ActionPerformed(evt);
            }
        });
        BtnPrint1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrint1KeyPressed(evt);
            }
        });
        panelGlass6.add(BtnPrint1);
        BtnPrint1.setBounds(120, 170, 100, 30);

        BtnCloseIn5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn5.setMnemonic('U');
        BtnCloseIn5.setText("Tutup");
        BtnCloseIn5.setToolTipText("Alt+U");
        BtnCloseIn5.setName("BtnCloseIn5"); // NOI18N
        BtnCloseIn5.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnCloseIn5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn5ActionPerformed(evt);
            }
        });
        panelGlass6.add(BtnCloseIn5);
        BtnCloseIn5.setBounds(220, 170, 100, 30);

        label12.setText("Kesan : ");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(48, 23));
        panelGlass6.add(label12);
        label12.setBounds(4, 9, 48, 23);

        label14.setText("Saran : ");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(48, 23));
        panelGlass6.add(label14);
        label14.setBounds(4, 90, 48, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        TKesan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TKesan.setColumns(20);
        TKesan.setRows(5);
        TKesan.setName("TKesan"); // NOI18N
        TKesan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKesanKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(TKesan);

        panelGlass6.add(scrollPane2);
        scrollPane2.setBounds(58, 10, 620, 70);

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

        panelGlass6.add(scrollPane3);
        scrollPane3.setBounds(58, 90, 620, 70);

        internalFrame6.add(panelGlass6, java.awt.BorderLayout.PAGE_END);

        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        HasilPeriksa.setColumns(20);
        HasilPeriksa.setRows(5);
        HasilPeriksa.setName("HasilPeriksa"); // NOI18N
        Scroll3.setViewportView(HasilPeriksa);

        internalFrame6.add(Scroll3, java.awt.BorderLayout.CENTER);

        panelIsi7.setName("panelIsi7"); // NOI18N
        panelIsi7.setPreferredSize(new java.awt.Dimension(44, 84));
        panelIsi7.setLayout(null);

        label17.setText("Judul : ");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(48, 23));
        panelIsi7.add(label17);
        label17.setBounds(4, 10, 48, 23);

        TJudul.setToolTipText("Alt+C");
        TJudul.setName("TJudul"); // NOI18N
        TJudul.setPreferredSize(new java.awt.Dimension(400, 23));
        TJudul.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TJudulKeyPressed(evt);
            }
        });
        panelIsi7.add(TJudul);
        TJudul.setBounds(58, 10, 400, 23);

        label19.setText("Klinis : ");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(48, 23));
        panelIsi7.add(label19);
        label19.setBounds(4, 40, 48, 23);

        TKlinis.setToolTipText("Alt+C");
        TKlinis.setName("TKlinis"); // NOI18N
        TKlinis.setPreferredSize(new java.awt.Dimension(400, 23));
        TKlinis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKlinisKeyPressed(evt);
            }
        });
        panelIsi7.add(TKlinis);
        TKlinis.setBounds(58, 40, 400, 23);

        btnHasilRad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnHasilRad.setMnemonic('2');
        btnHasilRad.setToolTipText("Alt+2");
        btnHasilRad.setName("btnHasilRad"); // NOI18N
        btnHasilRad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHasilRadActionPerformed(evt);
            }
        });
        panelIsi7.add(btnHasilRad);
        btnHasilRad.setBounds(460, 10, 58, 32);

        TNoRw2.setEditable(false);
        TNoRw2.setToolTipText("Alt+C");
        TNoRw2.setName("TNoRw2"); // NOI18N
        TNoRw2.setPreferredSize(new java.awt.Dimension(400, 23));
        panelIsi7.add(TNoRw2);
        TNoRw2.setBounds(595, 10, 150, 23);

        label22.setText("No. Rawat :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(48, 23));
        panelIsi7.add(label22);
        label22.setBounds(510, 10, 80, 23);

        BtnLihatLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnLihatLab.setMnemonic('U');
        BtnLihatLab.setText("Lihat Hasil Lab");
        BtnLihatLab.setToolTipText("Alt+U");
        BtnLihatLab.setName("BtnLihatLab"); // NOI18N
        BtnLihatLab.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnLihatLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLihatLabActionPerformed(evt);
            }
        });
        BtnLihatLab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnLihatLabKeyPressed(evt);
            }
        });
        panelIsi7.add(BtnLihatLab);
        BtnLihatLab.setBounds(510, 40, 140, 30);

        internalFrame6.add(panelIsi7, java.awt.BorderLayout.PAGE_START);

        internalFrame8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Hasil Laboratorium ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(100, 80, 80))); // NOI18N
        internalFrame8.setName("internalFrame8"); // NOI18N
        internalFrame8.setLayout(new java.awt.BorderLayout());

        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);

        tbTranfusiDarah.setAutoCreateRowSorter(true);
        tbTranfusiDarah.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbTranfusiDarah.setName("tbTranfusiDarah"); // NOI18N
        tbTranfusiDarah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTranfusiDarahMouseClicked(evt);
            }
        });
        tbTranfusiDarah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTranfusiDarahKeyPressed(evt);
            }
        });
        Scroll5.setViewportView(tbTranfusiDarah);

        internalFrame8.add(Scroll5, java.awt.BorderLayout.CENTER);

        internalFrame6.add(internalFrame8, java.awt.BorderLayout.EAST);

        WindowHasil.getContentPane().add(internalFrame6, java.awt.BorderLayout.CENTER);

        Penjab.setEditable(false);
        Penjab.setFocusTraversalPolicyProvider(true);
        Penjab.setName("Penjab"); // NOI18N
        Penjab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenjabKeyPressed(evt);
            }
        });

        Jk.setEditable(false);
        Jk.setFocusTraversalPolicyProvider(true);
        Jk.setName("Jk"); // NOI18N

        Umur.setEditable(false);
        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N

        Alamat.setEditable(false);
        Alamat.setHighlighter(null);
        Alamat.setName("Alamat"); // NOI18N

        NoRM.setName("NoRM"); // NOI18N
        NoRM.setPreferredSize(new java.awt.Dimension(207, 23));

        WindowGantiDokterParamedis.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowGantiDokterParamedis.setName("WindowGantiDokterParamedis"); // NOI18N
        WindowGantiDokterParamedis.setUndecorated(true);
        WindowGantiDokterParamedis.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)), "::[ Ubah P.J.Rad, Perujuk & Petugas ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(89, 434));
        FormInput.setLayout(null);

        BtnSimpan4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan4.setMnemonic('S');
        BtnSimpan4.setText("Simpan");
        BtnSimpan4.setToolTipText("Alt+S");
        BtnSimpan4.setName("BtnSimpan4"); // NOI18N
        BtnSimpan4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan4ActionPerformed(evt);
            }
        });
        FormInput.add(BtnSimpan4);
        BtnSimpan4.setBounds(470, 15, 100, 30);

        BtnCloseIn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn4.setMnemonic('U');
        BtnCloseIn4.setText("Tutup");
        BtnCloseIn4.setToolTipText("Alt+U");
        BtnCloseIn4.setName("BtnCloseIn4"); // NOI18N
        BtnCloseIn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn4ActionPerformed(evt);
            }
        });
        FormInput.add(BtnCloseIn4);
        BtnCloseIn4.setBounds(470, 60, 100, 30);

        jLabel7.setText("Dokter P.J. :");
        jLabel7.setName("jLabel7"); // NOI18N
        FormInput.add(jLabel7);
        jLabel7.setBounds(0, 12, 92, 23);

        KodePj.setEditable(false);
        KodePj.setName("KodePj"); // NOI18N
        FormInput.add(KodePj);
        KodePj.setBounds(95, 12, 113, 23);

        NmDokterPj.setEditable(false);
        NmDokterPj.setHighlighter(null);
        NmDokterPj.setName("NmDokterPj"); // NOI18N
        FormInput.add(NmDokterPj);
        NmDokterPj.setBounds(210, 12, 208, 23);

        btnDokterPj.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokterPj.setMnemonic('4');
        btnDokterPj.setToolTipText("ALt+4");
        btnDokterPj.setName("btnDokterPj"); // NOI18N
        btnDokterPj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterPjActionPerformed(evt);
            }
        });
        FormInput.add(btnDokterPj);
        btnDokterPj.setBounds(420, 12, 28, 23);

        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter.setMnemonic('4');
        btnDokter.setToolTipText("ALt+4");
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        FormInput.add(btnDokter);
        btnDokter.setBounds(420, 42, 28, 23);

        NmPerujuk.setEditable(false);
        NmPerujuk.setHighlighter(null);
        NmPerujuk.setName("NmPerujuk"); // NOI18N
        FormInput.add(NmPerujuk);
        NmPerujuk.setBounds(210, 42, 208, 23);

        KodePerujuk.setName("KodePerujuk"); // NOI18N
        KodePerujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodePerujukKeyPressed(evt);
            }
        });
        FormInput.add(KodePerujuk);
        KodePerujuk.setBounds(95, 42, 113, 23);

        jLabel9.setText("Dokter Perujuk :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 42, 92, 23);

        jLabel12.setText("Petugas :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 72, 92, 23);

        KdPtgUbah.setName("KdPtgUbah"); // NOI18N
        KdPtgUbah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPtgUbahKeyPressed(evt);
            }
        });
        FormInput.add(KdPtgUbah);
        KdPtgUbah.setBounds(95, 72, 113, 23);

        NmPtgUbah.setEditable(false);
        NmPtgUbah.setName("NmPtgUbah"); // NOI18N
        FormInput.add(NmPtgUbah);
        NmPtgUbah.setBounds(210, 72, 208, 23);

        btnPetugas1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas1.setMnemonic('2');
        btnPetugas1.setToolTipText("Alt+2");
        btnPetugas1.setName("btnPetugas1"); // NOI18N
        btnPetugas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugas1ActionPerformed(evt);
            }
        });
        FormInput.add(btnPetugas1);
        btnPetugas1.setBounds(420, 72, 28, 23);

        internalFrame5.add(FormInput, java.awt.BorderLayout.CENTER);

        WindowGantiDokterParamedis.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        NoOrder.setName("NoOrder"); // NOI18N
        NoOrder.setPreferredSize(new java.awt.Dimension(207, 23));

        Perujuk.setEditable(false);
        Perujuk.setHighlighter(null);
        Perujuk.setName("Perujuk"); // NOI18N

        Petugas.setEditable(false);
        Petugas.setHighlighter(null);
        Petugas.setName("Petugas"); // NOI18N

        jPopupMenu2.setName("jPopupMenu2"); // NOI18N

        MnTarikGambar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTarikGambar.setForeground(new java.awt.Color(100, 80, 80));
        MnTarikGambar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTarikGambar.setText("Tarik Hasil Expertise");
        MnTarikGambar.setName("MnTarikGambar"); // NOI18N
        MnTarikGambar.setPreferredSize(new java.awt.Dimension(250, 28));
        MnTarikGambar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnTarikGambarActionPerformed(evt);
            }
        });
        jPopupMenu2.add(MnTarikGambar);

        WindowRiwayatRad.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowRiwayatRad.setName("WindowRiwayatRad"); // NOI18N
        WindowRiwayatRad.setUndecorated(true);
        WindowRiwayatRad.setResizable(false);
        WindowRiwayatRad.getContentPane().setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Riwayat Pemeriksaan Radiologi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(100, 80, 80))); // NOI18N
        internalFrame7.setName("internalFrame7"); // NOI18N
        internalFrame7.setLayout(new java.awt.BorderLayout());

        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);

        tbRiwayat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbRiwayat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRiwayat.setComponentPopupMenu(jPopupMenu1);
        tbRiwayat.setName("tbRiwayat"); // NOI18N
        tbRiwayat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRiwayatMouseClicked(evt);
            }
        });
        tbRiwayat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRiwayatKeyPressed(evt);
            }
        });
        Scroll4.setViewportView(tbRiwayat);

        internalFrame7.add(Scroll4, java.awt.BorderLayout.CENTER);

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label27.setName("label27"); // NOI18N
        label27.setPreferredSize(new java.awt.Dimension(725, 23));
        panelGlass5.add(label27);

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
        panelGlass5.add(BtnKeluar1);

        internalFrame7.add(panelGlass5, java.awt.BorderLayout.PAGE_END);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label21.setText("Pasien :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi4.add(label21);

        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.setPreferredSize(new java.awt.Dimension(120, 23));
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        panelisi4.add(TNoRw);

        TPasien.setEditable(false);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.setPreferredSize(new java.awt.Dimension(390, 23));
        panelisi4.add(TPasien);

        label28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label28.setName("label28"); // NOI18N
        label28.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi4.add(label28);

        jLabel6.setText("Tampil Per :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(jLabel6);

        cmbHlm.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "3", "5", "7", "10" }));
        cmbHlm.setName("cmbHlm"); // NOI18N
        cmbHlm.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi4.add(cmbHlm);

        BtnCari3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari3.setMnemonic('4');
        BtnCari3.setToolTipText("Alt+4");
        BtnCari3.setName("BtnCari3"); // NOI18N
        BtnCari3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari3ActionPerformed(evt);
            }
        });
        BtnCari3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari3KeyPressed(evt);
            }
        });
        panelisi4.add(BtnCari3);

        internalFrame7.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        WindowRiwayatRad.getContentPane().add(internalFrame7, java.awt.BorderLayout.CENTER);

        tglPeriksa.setName("tglPeriksa"); // NOI18N
        tglPeriksa.setPreferredSize(new java.awt.Dimension(207, 23));

        jamPeriksa.setName("jamPeriksa"); // NOI18N
        jamPeriksa.setPreferredSize(new java.awt.Dimension(207, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Pemeriksaan Radiologi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(100, 80, 80))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

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
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        tbDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDokterKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 73));
        panelisi3.setLayout(null);

        label15.setText("No.Rawat :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 75, 23);

        NoRawat.setName("NoRawat"); // NOI18N
        NoRawat.setPreferredSize(new java.awt.Dimension(207, 23));
        NoRawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRawatKeyPressed(evt);
            }
        });
        panelisi3.add(NoRawat);
        NoRawat.setBounds(79, 10, 226, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 40, 75, 23);

        Tgl1.setEditable(false);
        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelisi3.add(Tgl1);
        Tgl1.setBounds(79, 40, 100, 23);

        label16.setText("Pasien :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(385, 10, 60, 23);

        label13.setText("Petugas :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(385, 40, 60, 23);

        kdmem.setName("kdmem"); // NOI18N
        kdmem.setPreferredSize(new java.awt.Dimension(80, 23));
        kdmem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdmemKeyPressed(evt);
            }
        });
        panelisi3.add(kdmem);
        kdmem.setBounds(449, 10, 80, 23);

        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi3.add(kdptg);
        kdptg.setBounds(449, 40, 80, 23);

        nmmem.setEditable(false);
        nmmem.setName("nmmem"); // NOI18N
        nmmem.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmmem);
        nmmem.setBounds(531, 10, 240, 23);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptg);
        nmptg.setBounds(531, 40, 240, 23);

        btnPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPasien.setMnemonic('1');
        btnPasien.setToolTipText("Alt+1");
        btnPasien.setName("btnPasien"); // NOI18N
        btnPasien.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasienActionPerformed(evt);
            }
        });
        panelisi3.add(btnPasien);
        btnPasien.setBounds(774, 10, 28, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("Alt+2");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        panelisi3.add(btnPetugas);
        btnPetugas.setBounds(774, 40, 28, 23);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label18);
        label18.setBounds(178, 40, 30, 23);

        Tgl2.setEditable(false);
        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelisi3.add(Tgl2);
        Tgl2.setBounds(205, 40, 100, 23);

        BtnSeek4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek4.setMnemonic('3');
        BtnSeek4.setToolTipText("Alt+3");
        BtnSeek4.setName("BtnSeek4"); // NOI18N
        BtnSeek4.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelisi3.add(BtnSeek4);
        BtnSeek4.setBounds(1225, 10, 28, 23);

        label20.setText("Dokter :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi3.add(label20);
        label20.setBounds(830, 10, 75, 23);

        kddokter.setEditable(false);
        kddokter.setName("kddokter"); // NOI18N
        kddokter.setPreferredSize(new java.awt.Dimension(75, 23));
        kddokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddokterKeyPressed(evt);
            }
        });
        panelisi3.add(kddokter);
        kddokter.setBounds(910, 10, 85, 23);

        nmdokter.setEditable(false);
        nmdokter.setName("nmdokter"); // NOI18N
        nmdokter.setPreferredSize(new java.awt.Dimension(215, 23));
        panelisi3.add(nmdokter);
        nmdokter.setBounds(997, 10, 230, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);
        panelisi3.getAccessibleContext().setAccessibleDescription("");

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(170, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('5');
        BtnCari.setToolTipText("Alt+5");
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

        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(100, 30));
        panelisi1.add(label9);

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
        panelisi1.add(BtnHapus);

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
        panelisi1.add(BtnAll);

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

        panelisi6.setBorder(null);
        panelisi6.setName("panelisi6"); // NOI18N
        panelisi6.setPreferredSize(new java.awt.Dimension(450, 77));
        panelisi6.setLayout(new java.awt.GridLayout(2, 0));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(237, 242, 232)), ".: PACS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 102));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbListDicom.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbListDicom.setName("tbListDicom"); // NOI18N
        tbListDicom.setComponentPopupMenu(jPopupMenu2);
        tbListDicom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbListDicomMouseClicked(evt);
            }
        });
        tbListDicom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbListDicomKeyPressed(evt);
            }
        });
        Scroll6.setViewportView(tbListDicom);

        jPanel3.add(Scroll6, java.awt.BorderLayout.CENTER);

        panelGlass8.setBorder(null);
        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(115, 40));

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnCari1.setMnemonic('5');
        BtnCari1.setText("Ambil");
        BtnCari1.setToolTipText("Alt+5");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(108, 23));
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
        panelGlass8.add(BtnCari1);

        btnDicom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        btnDicom.setMnemonic('T');
        btnDicom.setText("DICOM");
        btnDicom.setToolTipText("Alt+T");
        btnDicom.setName("btnDicom"); // NOI18N
        btnDicom.setPreferredSize(new java.awt.Dimension(150, 30));
        btnDicom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDicomActionPerformed(evt);
            }
        });
        panelGlass8.add(btnDicom);

        BtnCari2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item.png"))); // NOI18N
        BtnCari2.setMnemonic('5');
        BtnCari2.setText("STONE");
        BtnCari2.setToolTipText("Alt+5");
        BtnCari2.setName("BtnCari2"); // NOI18N
        BtnCari2.setPreferredSize(new java.awt.Dimension(108, 23));
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
        panelGlass8.add(BtnCari2);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        panelisi6.add(jPanel3);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(237, 242, 232)), ".: Preview", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(300, 202));
        jPanel4.setLayout(new java.awt.GridLayout(1, 0));

        scrollPane4.setName("scrollPane4"); // NOI18N
        scrollPane4.setOpaque(true);
        scrollPane4.setPreferredSize(new java.awt.Dimension(452, 102));

        tbListDicom1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbListDicom1.setName("tbListDicom1"); // NOI18N
        tbListDicom1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbListDicom1MouseClicked(evt);
            }
        });
        tbListDicom1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbListDicom1KeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(tbListDicom1);

        jPanel4.add(scrollPane4);

        scrollPane5.setName("scrollPane5"); // NOI18N
        scrollPane5.setOpaque(true);
        scrollPane5.setPreferredSize(new java.awt.Dimension(452, 102));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel1.setName("jLabel1"); // NOI18N
        jLabel1.setSize(new java.awt.Dimension(33, 15));
        scrollPane5.setViewportView(jLabel1);

        jPanel4.add(scrollPane5);

        panelisi6.add(jPanel4);

        internalFrame1.add(panelisi6, java.awt.BorderLayout.EAST);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void btnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasienActionPerformed
        var.setform("DlgCariPeriksaRadiologi");
        member.emptTeks();
        member.isCek();
        member.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        member.setLocationRelativeTo(internalFrame1);
        member.setAlwaysOnTop(false);
        member.setVisible(true);
    }//GEN-LAST:event_btnPasienActionPerformed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        var.setform("DlgCariPeriksaRadiologi");
        pilihan = "caripetugas";
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt, kdmem, Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void kdmemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdmemKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", nmmem, kdmem.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", nmmem, kdmem.getText());
            NoRawat.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnPasienActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", nmmem, kdmem.getText());
            Tgl1.requestFocus();
        }
    }//GEN-LAST:event_kdmemKeyPressed

    private void NoRawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRawatKeyPressed
        Valid.pindah(evt, BtnKeluar, kdptg);
    }//GEN-LAST:event_NoRawatKeyPressed

    private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg, kdptg.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg, kdptg.getText());
            Tgl2.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnPetugasActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg, kdptg.getText());
            NoRawat.requestFocus();
        }
    }//GEN-LAST:event_kdptgKeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1, kdptg);
    }//GEN-LAST:event_Tgl2KeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
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
        NoRawat.setText("");
        kdmem.setText("");
        nmmem.setText("");
        kdptg.setText("");
        nmptg.setText("");
        kddokter.setText("");
        nmdokter.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnAllActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        } else if (tabMode.getRowCount() != 0) {

            Sequel.queryu("delete from temporary_radiologi");
            int row = tabMode.getRowCount();
            for (i = 0; i < row; i++) {
                Sequel.menyimpan("temporary_radiologi", "'0','"
                        + tabMode.getValueAt(i, 0).toString() + "','"
                        + tabMode.getValueAt(i, 1).toString() + "','"
                        + tabMode.getValueAt(i, 2).toString() + "','"
                        + tabMode.getValueAt(i, 3).toString() + "','"
                        + tabMode.getValueAt(i, 4).toString() + "','"
                        + tabMode.getValueAt(i, 5).toString() + "','"
                        + tabMode.getValueAt(i, 6).toString() + "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''", "Periksa Radiologi");
            }

            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptDataRadiologi.jrxml", "report", "::[ Data Pemeriksaan Radiologi ]::",
                    "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14, temp14, temp15, temp16 from temporary_radiologi order by no asc", param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnAll, BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        petugas.dispose();
        member.dispose();
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnPrint, NoRawat);
        }
    }//GEN-LAST:event_BtnKeluarKeyPressed

private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
    if (tabMode.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
        TCari.requestFocus();
    } else if (Kd2.getText().trim().equals("")) {
        JOptionPane.showMessageDialog(null, "Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik No.Rawat pada table untuk memilih...!!!!");
    } else if (!(Kd2.getText().trim().equals(""))) {
        if (Sequel.cariRegistrasi(tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString()) > 0) {
            JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
            TCari.requestFocus();
        } else {
            try {

                status = "";
                ttljmdokter = 0;
                ttljmpetugas = 0;
                ttlkso = 0;
                ttlpendapatan = 0;
                ttlbhp = 0;
                ttljmdokter = Sequel.cariIsiAngka("select sum(tarif_perujuk)+sum(tarif_tindakan_dokter) from periksa_radiologi where no_rawat='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 0)
                        + "' and tgl_periksa='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 3)
                        + "' and jam='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 4) + "'");
                ttljmpetugas = Sequel.cariIsiAngka("select sum(tarif_tindakan_petugas) from periksa_radiologi where no_rawat='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 0)
                        + "' and tgl_periksa='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 3)
                        + "' and jam='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 4) + "'");
                ttlkso = Sequel.cariIsiAngka("select sum(kso) from periksa_radiologi where no_rawat='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 0)
                        + "' and tgl_periksa='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 3)
                        + "' and jam='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 4) + "'");
                ttlpendapatan = Sequel.cariIsiAngka("select sum(biaya) from periksa_radiologi where no_rawat='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 0)
                        + "' and tgl_periksa='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 3)
                        + "' and jam='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 4) + "'");

                status = Sequel.cariIsi("select status from periksa_radiologi where no_rawat='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 0)
                        + "' and tgl_periksa='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 3)
                        + "' and jam='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 4) + "'");

                if (Sequel.queryu2tf("delete from periksa_radiologi where no_rawat=? and tgl_periksa=? and jam=?", 3, new String[]{
                    tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString(), tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString(), tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString()
                }) == true) {
                    Sequel.queryu2("delete from hasil_radiologi where no_rawat=? and tgl_periksa=? and jam=?", 3, new String[]{
                        tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString(), tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString(), tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString()
                    });
                    Sequel.queryu2("delete from saran_kesan_rad where no_rawat=? and tgl_periksa=? and jam=?", 3, new String[]{
                        tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString(), tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString(), tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString()
                    });
                    Sequel.queryu2("delete from gambar_radiologi where no_rawat=? and tgl_periksa=? and jam=?", 3, new String[]{
                        tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString(), tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString(), tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString()
                    });
                    Sequel.queryu2("delete from diagnosa_pasien_klinis where noorder=?", 1, new String[]{
                        NoOrder.getText()
                    });
                    ps3 = koneksi.prepareStatement(
                            "select beri_bhp_radiologi.kode_brng,ipsrsbarang.nama_brng,beri_bhp_radiologi.kode_sat,beri_bhp_radiologi.jumlah, "
                            + "beri_bhp_radiologi.total from beri_bhp_radiologi inner join ipsrsbarang on ipsrsbarang.kode_brng=beri_bhp_radiologi.kode_brng "
                            + "where beri_bhp_radiologi.no_rawat=? and beri_bhp_radiologi.tgl_periksa=? and beri_bhp_radiologi.jam=?");
                    try {
                        ps3.setString(1, tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString());
                        ps3.setString(2, tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString());
                        ps3.setString(3, tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString());
                        rs3 = ps3.executeQuery();
                        while (rs3.next()) {
                            Sequel.mengedit("ipsrsbarang", "kode_brng=?", "stok=stok+?", 2, new String[]{
                                rs3.getString("jumlah"), rs3.getString("kode_brng")
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("simrskhanza.DlgCariPeriksaRadiologi.BtnHapusActionPerformed() ps3 : " + e);
                    } finally {
                        if (rs3 != null) {
                            rs3.close();
                        }
                        if (ps3 != null) {
                            ps3.close();
                        }
                    }

                    ttlbhp = Sequel.cariIsiAngka("select sum(total) from beri_bhp_radiologi where no_rawat='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 0)
                            + "' and tgl_periksa='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 3)
                            + "' and jam='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 4) + "'");
                    if (Sequel.queryu2tf("delete from beri_bhp_radiologi where no_rawat=? and tgl_periksa=? and jam=?", 3, new String[]{
                        tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString(), tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString(), tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString()
                    }) == false) {
                        ttlbhp = 0;
                    }
                    if (status.equals("Ranap")) {
                        Sequel.queryu("delete from tampjurnal");
                        if (ttlpendapatan > 0) {
                            Sequel.menyimpan("tampjurnal", "'" + Suspen_Piutang_Radiologi_Ranap + "','Suspen Piutang Radiologi Ranap','0','" + ttlpendapatan + "'", "Rekening");
                            Sequel.menyimpan("tampjurnal", "'" + Radiologi_Ranap + "','Pendapatan Radiologi Rawat Inap','" + ttlpendapatan + "','0'", "Rekening");
                        }
                        if (ttljmdokter > 0) {
                            Sequel.menyimpan("tampjurnal", "'" + Beban_Jasa_Medik_Dokter_Radiologi_Ranap + "','Beban Jasa Medik Dokter Radiologi Ranap','0','" + ttljmdokter + "'", "Rekening");
                            Sequel.menyimpan("tampjurnal", "'" + Utang_Jasa_Medik_Dokter_Radiologi_Ranap + "','Utang Jasa Medik Dokter Radiologi Ranap','" + ttljmdokter + "','0'", "Rekening");
                        }
                        if (ttljmpetugas > 0) {
                            Sequel.menyimpan("tampjurnal", "'" + Beban_Jasa_Medik_Petugas_Radiologi_Ranap + "','Beban Jasa Medik Petugas Radiologi Ranap','0','" + ttljmpetugas + "'", "Rekening");
                            Sequel.menyimpan("tampjurnal", "'" + Utang_Jasa_Medik_Petugas_Radiologi_Ranap + "','Utang Jasa Medik Petugas Radiologi Ranap','" + ttljmpetugas + "','0'", "Rekening");
                        }
                        if (ttlbhp > 0) {
                            Sequel.menyimpan("tampjurnal", "'" + HPP_Persediaan_Radiologi_Rawat_Inap + "','HPP Persediaan Radiologi Rawat Inap','0','" + ttlbhp + "'", "Rekening");
                            Sequel.menyimpan("tampjurnal", "'" + Persediaan_BHP_Radiologi_Rawat_Inap + "','Persediaan BHP Radiologi Rawat Inap','" + ttlbhp + "','0'", "Rekening");
                        }
                        if (ttlkso > 0) {
                            Sequel.menyimpan("tampjurnal", "'" + Beban_Kso_Radiologi_Ranap + "','Beban KSO Radiologi Ranap','0','" + ttlkso + "'", "Rekening");
                            Sequel.menyimpan("tampjurnal", "'" + Utang_Kso_Radiologi_Ranap + "','Utang KSO Radiologi Ranap','" + ttlkso + "','0'", "Rekening");
                        }
                        jur.simpanJurnal(tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString(), Sequel.cariIsi("select current_date()"), "U", "PEMBATALAN PEMERIKSAAN RADIOLOGI RAWAT INAP PASIEN OLEH " + var.getkode());
                    }
                }

                tampil();
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
                JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih terlebih dulu data yang mau anda hapus...\n Klik data pada table untuk memilih data...!!!!");
            }
        }
    }
}//GEN-LAST:event_BtnHapusActionPerformed

private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
        BtnHapusActionPerformed(null);
    } else {
        Valid.pindah(evt, TCari, BtnAll);
    }
}//GEN-LAST:event_BtnHapusKeyPressed

private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
    if (tabMode.getRowCount() != 0) {
        try {
            getData();
        } catch (java.lang.NullPointerException e) {
        }
    }
}//GEN-LAST:event_tbDokterMouseClicked

private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
    if (tabMode.getRowCount() != 0) {
        if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }
}//GEN-LAST:event_tbDokterKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void MnCetakNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakNotaActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else if (tbDokter.getSelectedRow() <= -1) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data..!!");
        } else {
            if (Kd2.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data yang mau ditampilkan...!!!!");
            } else {
                try {
                    ps4 = koneksi.prepareStatement(
                            "select periksa_radiologi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,petugas.nama,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,"
                            + "periksa_radiologi.dokter_perujuk,periksa_radiologi.kd_dokter,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,pasien.alamat,dokter.nm_dokter from periksa_radiologi inner join reg_periksa inner join pasien inner join petugas  inner join dokter "
                            + "on periksa_radiologi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_radiologi.nip=petugas.nip and periksa_radiologi.kd_dokter=dokter.kd_dokter where "
                            + "periksa_radiologi.tgl_periksa=? and periksa_radiologi.jam=? and periksa_radiologi.no_rawat=? group by concat(periksa_radiologi.no_rawat,periksa_radiologi.tgl_periksa,periksa_radiologi.jam)");
                    try {
                        ps4.setString(1, tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString());
                        ps4.setString(2, tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString());
                        ps4.setString(3, tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString());
                        rs = ps4.executeQuery();
                        while (rs.next()) {
                            Sequel.queryu("delete from temporary_radiologi");
                            koneksi.setAutoCommit(false);
                            ps2 = koneksi.prepareStatement(
                                    "select jns_perawatan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.biaya from periksa_radiologi inner join jns_perawatan_radiologi "
                                    + "on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw where periksa_radiologi.no_rawat=? and periksa_radiologi.tgl_periksa=? "
                                    + "and periksa_radiologi.jam=?");
                            try {
                                ps2.setString(1, rs.getString("no_rawat"));
                                ps2.setString(2, rs.getString("tgl_periksa"));
                                ps2.setString(3, rs.getString("jam"));
                                rs2 = ps2.executeQuery();
                                ttl = 0;
                                while (rs2.next()) {
                                    item = rs2.getDouble("biaya") + Sequel.cariIsiAngka("select sum(biaya_item) from template_laboratorium where kd_jenis_prw=?", rs2.getString("kd_jenis_prw"));
                                    ttl = ttl + item;
                                    Sequel.menyimpan("temporary_radiologi", "'0','" + rs2.getString("nm_perawatan") + "','" + item + "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''", "Transaksi Biaya Lab");
                                }
                            } catch (Exception e) {
                                System.out.println("simrskhanza.DlgCariPeriksaRadiologi.MnCetakNotaActionPerformed() ps2 : " + e);
                            } finally {
                                if (rs2 != null) {
                                    rs2.close();
                                }
                                if (ps2 != null) {
                                    ps2.close();
                                }
                            }

                            Sequel.menyimpan("temporary_radiologi", "'0','Total Biaya Pemeriksaan Radiologi','" + ttl + "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''", "Transaksi Biaya Lab");
                            Valid.panggilUrl("billing/LaporanBiayaRadiologi.php?norm=" + rs.getString("no_rkm_medis") + "&pasien=" + rs.getString("nm_pasien").replaceAll(" ", "_")
                                    + "&tanggal=" + rs.getString("tgl_periksa") + "&jam=" + rs.getString("jam") + "&pjlab=" + rs.getString("nm_dokter").replaceAll(" ", "_")
                                    + "&petugas=" + rs.getString("nama").replaceAll(" ", "_") + "&kasir=" + Sequel.cariIsi("select nama from pegawai where nik=?", var.getkode()));
                            koneksi.setAutoCommit(true);
                        }
                    } catch (Exception e) {
                        System.out.println("simrskhanza.DlgCariPeriksaRadiologi.MnCetakNotaActionPerformed() ps4 : " + e);
                    } finally {
                        if (rs != null) {
                            rs.close();
                        }
                        if (ps4 != null) {
                            ps4.close();
                        }
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnCetakNotaActionPerformed

    private void MnLihatHasilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLihatHasilActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else if (tbDokter.getSelectedRow() <= -1) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data..!!");
        } else {
            if (Kd2.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data yang mau ditampilkan...!!!!");
            } else {                
                try {
                    HasilPeriksa.setText("");                     
                    ps5 = koneksi.prepareStatement(
                            "select hasil from hasil_radiologi where hasil_radiologi.no_rawat=? and hasil_radiologi.tgl_periksa=? and hasil_radiologi.jam=?");
                    try {
                        ps5.setString(1, tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString());
                        ps5.setString(2, tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString());
                        ps5.setString(3, tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString());
                        rs5 = ps5.executeQuery();
                        while (rs5.next()) {
                            HasilPeriksa.setText(rs5.getString(1));
                        }
                    } catch (Exception e) {
                        System.out.println("simrskhanza.DlgCariPeriksaRadiologi.MnLihatHasilActionPerformed() ps5 : " + e);
                    } finally {
                        if (rs5 != null) {
                            rs5.close();
                        }
                        if (ps5 != null) {
                            ps5.close();
                        }
                    }

                    ps6 = koneksi.prepareStatement(
                            "select judul from saran_kesan_rad where no_rawat=? and tgl_periksa=? and jam=?");
                    try {
                        ps6.setString(1, tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString());
                        ps6.setString(2, tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString());
                        ps6.setString(3, tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString());
                        rs6 = ps6.executeQuery();
                        while (rs6.next()) {
                            TJudul.setText(rs6.getString(1));
                        }
                    } catch (Exception e) {
                        System.out.println("simrskhanza.DlgCariPeriksaRadiologi.MnLihatHasilActionPerformed() ps6 : " + e);
                    } finally {
                        if (rs6 != null) {
                            rs6.close();
                        }
                        if (ps6 != null) {
                            ps6.close();
                        }
                    }

                    ps7 = koneksi.prepareStatement(
                            "select saran from saran_kesan_rad where no_rawat=? and tgl_periksa=? and jam=?");
                    try {
                        ps7.setString(1, tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString());
                        ps7.setString(2, tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString());
                        ps7.setString(3, tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString());
                        rs7 = ps7.executeQuery();
                        while (rs7.next()) {
                            TSaran.setText(rs7.getString(1));
                        }
                    } catch (Exception e) {
                        System.out.println("simrskhanza.DlgCariPeriksaRadiologi.MnLihatHasilActionPerformed() ps7 : " + e);
                    } finally {
                        if (rs7 != null) {
                            rs7.close();
                        }
                        if (ps7 != null) {
                            ps7.close();
                        }
                    }

                    ps8 = koneksi.prepareStatement(
                            "select kesan from saran_kesan_rad where no_rawat=? and tgl_periksa=? and jam=?");
                    try {
                        ps8.setString(1, tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString());
                        ps8.setString(2, tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString());
                        ps8.setString(3, tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString());
                        rs8 = ps8.executeQuery();
                        while (rs8.next()) {
                            TKesan.setText(rs8.getString(1));
                        }
                    } catch (Exception e) {
                        System.out.println("simrskhanza.DlgCariPeriksaRadiologi.MnLihatHasilActionPerformed() ps8 : " + e);
                    } finally {
                        if (rs8 != null) {
                            rs8.close();
                        }
                        if (ps8 != null) {
                            ps8.close();
                        }
                    }

                    ps9 = koneksi.prepareStatement(
                            "select klinis from diagnosa_pasien_klinis where noorder=?");
                    try {
                        ps9.setString(1, NoOrder.getText());
                        rs9 = ps9.executeQuery();
                        while (rs9.next()) {
                            TKlinis.setText(rs9.getString(1));
                        }
                    } catch (Exception e) {
                        System.out.println("simrskhanza.DlgCariPeriksaRadiologi.MnLihatHasilActionPerformed() ps9 : " + e);
                    } finally {
                        if (rs9 != null) {
                            rs9.close();
                        }
                        if (ps9 != null) {
                            ps9.close();
                        }
                    }
                    WindowHasil.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    WindowHasil.setLocationRelativeTo(internalFrame1);
                    WindowHasil.setVisible(true);
//                    tglPeriksa.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString());
//                    jamPeriksa.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString());
                } catch (Exception ex) {
                    System.out.println(ex);
                }
                Valid.tabelKosong(tabModeLab); 
                TNoRw2.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString());                
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnLihatHasilActionPerformed

    private void BtnCloseIn5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn5ActionPerformed
        int reply = JOptionPane.showConfirmDialog(rootPane, "Apakah anda yakin ingin keluar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            WindowHasil.dispose();
        }
    }//GEN-LAST:event_BtnCloseIn5ActionPerformed

    private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint1ActionPerformed
        if (Kd2.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data yang mau ditampilkan...!!!!");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            pemeriksaan = "";
            judul="";hasil = "";kesan="";saran="";
            try {
                ps2 = koneksi.prepareStatement(
                        "select jns_perawatan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.biaya from periksa_radiologi inner join jns_perawatan_radiologi "
                        + "on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw where periksa_radiologi.no_rawat=? and periksa_radiologi.tgl_periksa=? "
                        + "and periksa_radiologi.jam=?");
                try {
                    ps2.setString(1, Kd2.getText());
                    ps2.setString(2, tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString());
                    ps2.setString(3, tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString());
                    rs2 = ps2.executeQuery();
                    while (rs2.next()) {
                        pemeriksaan = rs2.getString("nm_perawatan") + ", " + pemeriksaan;
                    }
                } catch (Exception e) {
                    System.out.println("simrskhanza.DlgCariPeriksaRadiologi.BtnPrint1ActionPerformed() ps2 : " + e);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (ps2 != null) {
                        ps2.close();
                    }
                }

            } catch (Exception e) {
                System.out.println("Notifikasi Pemeriksaan : " + e);
            }
            Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ", NoRM, Kd2.getText());
            Sequel.cariIsi("select jk from pasien where no_rkm_medis=? ", Jk, NoRM.getText());
            Sequel.cariIsi("select umur from pasien where no_rkm_medis=?", Umur, NoRM.getText());
            Sequel.cariIsi("select alamat from pasien where no_rkm_medis=? ", Alamat, NoRM.getText());
            Sequel.cariIsi("select perujuk from rujuk_masuk where no_rawat=?", Perujuk, Kd2.getText());
            kamar = Sequel.cariIsi("select ifnull(kd_kamar,'') from kamar_inap where no_rawat='" + Kd2.getText() + "' order by tgl_masuk desc limit 1");
            if (!kamar.equals("")) {
                namakamar = kamar + ", " + Sequel.cariIsi("select nm_bangsal from bangsal inner join kamar on bangsal.kd_bangsal=kamar.kd_bangsal "
                        + " where kamar.kd_kamar='" + kamar + "' ");
                kamar = "Kamar";
            } else if (kamar.equals("")) {
                kamar = "Poli";
                namakamar = Sequel.cariIsi("select nm_poli from poliklinik inner join reg_periksa on poliklinik.kd_poli=reg_periksa.kd_poli "
                        + "where reg_periksa.no_rawat='" + Kd2.getText() + "'");
            }
            judul = Sequel.cariIsi("select judul from saran_kesan_rad where no_rawat='"+Kd2.getText()+"' and tgl_periksa='"+tglPeriksa.getText()+"' and jam='"+jamPeriksa.getText()+"'");
            hasil = Sequel.cariIsi("select hasil from hasil_radiologi where hasil_radiologi.no_rawat='"+Kd2.getText()+"' and hasil_radiologi.tgl_periksa='"+tglPeriksa.getText()+"' and hasil_radiologi.jam='"+jamPeriksa.getText()+"'");
            kesan = Sequel.cariIsi("select kesan from saran_kesan_rad where no_rawat='"+Kd2.getText()+"' and tgl_periksa='"+tglPeriksa.getText()+"' and jam='"+jamPeriksa.getText()+"'");
            saran = Sequel.cariIsi("select saran from saran_kesan_rad where no_rawat='"+Kd2.getText()+"' and tgl_periksa='"+tglPeriksa.getText()+"' and jam='"+jamPeriksa.getText()+"'");
            Map<String, Object> param = new HashMap<>();
            param.put("noperiksa", Kd2.getText());
            param.put("norm", NoRM.getText());
            param.put("namapasien", Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ", NoRM.getText()));
            param.put("jkel", (Jk.getText().equals("L")) ? "Laki - Laki" : "Perempuan");
            param.put("umur", Umur.getText());
            param.put("lahir", Sequel.cariIsi("select DATE_FORMAT(tgl_lahir,'%d-%m-%Y') from pasien where no_rkm_medis=? ", NoRM.getText()));
            param.put("pengirim", tbDokter.getValueAt(tbDokter.getSelectedRow(), 5).toString());
            param.put("tanggal", Valid.SetTgl3(tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString()));
            param.put("penjab", tbDokter.getValueAt(tbDokter.getSelectedRow(), 6).toString());
            param.put("petugas", tbDokter.getValueAt(tbDokter.getSelectedRow(), 2).toString());
            param.put("alamat", Alamat.getText());
            param.put("kamar", kamar);
            param.put("namakamar", namakamar);
            param.put("pemeriksaan", pemeriksaan);
            param.put("jam", tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString());
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("hasil", hasil);
            param.put("judul", judul);
            param.put("saran", saran);
            param.put("kesan", kesan);
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            param.put("klinis", Sequel.cariIsi("SELECT klinis FROM diagnosa_pasien_klinis WHERE noorder='"+NoOrder.getText()+"'"));
            param.put("perujuk", Perujuk.getText());
            String pj = Sequel.cariIsi("select kd_dokter from periksa_radiologi where no_rawat=?", tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString());
            pilihan = (String) JOptionPane.showInputDialog(null, "Silahkan pilih hasil pemeriksaan..!", "Hasil Pemeriksaan", JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Pasien IGD", "Pasien Rawat Jalan", "Pasien Rawat Inap", "Pasien Dari Luar"}, "Pasien IGD");
            String reportName = "";
            switch (pj) {
                case "-":
                    JOptionPane.showMessageDialog(null, "Silakan sesuaikan dokter penanggung jawab terlebih dahulu..!!!");
                    break;
                case "DR00019":
                case "D0000132":
                case "D0000189":
                    switch (pilihan) {
                        case "Pasien IGD":
                            reportName = "rptPeriksaRadiologi" + (pj.equals("DR00019") ? "" : (pj.equals("D0000132") ? "5" : "9")) + ".jrxml";
                            break;
                        case "Pasien Rawat Jalan":
                            reportName = "rptPeriksaRadiologi" + (pj.equals("DR00019") ? "2" : (pj.equals("D0000132") ? "6" : "10")) + ".jrxml";
                            break;
                        case "Pasien Rawat Inap":
                            reportName = "rptPeriksaRadiologi" + (pj.equals("DR00019") ? "3" : (pj.equals("D0000132") ? "7" : "11")) + ".jrxml";
                            break;
                        case "Pasien Dari Luar":
                            param.put("pengirim", Perujuk.getText());
                            reportName = "rptPeriksaRadiologi" + (pj.equals("DR00019") ? "4" : (pj.equals("D0000132") ? "8" : "12")) + ".jrxml";
                            break;
                    }
                    break;
            }

            if (!reportName.isEmpty()) {
                Valid.MyReport(reportName, "report", "::[ Pemeriksaan Radiologi ]::", "select current_date as tanggal", param);
            }

            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    private void BtnPrint1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrint1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrint1ActionPerformed(null);
        } else {
            //  Valid.pindah(evt, BtnBatal,BtnCari);
        }
    }//GEN-LAST:event_BtnPrint1KeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (Kd2.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data yang mau ditampilkan...!!!!");
        } else {
            int reply = JOptionPane.showConfirmDialog(rootPane, "Apakah yakin ingin mengubah data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {         
                if (HasilPeriksa.getText().equals("")) {
                    Sequel.queryu2("delete from hasil_radiologi where no_rawat=? and tgl_periksa=? and jam=?", 3, new String[]{
                        tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString(), tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString(), tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString()
                    });
                    Sequel.queryu2("delete from saran_kesan_rad where no_rawat=? and tgl_periksa=? and jam=?", 3, new String[]{
                        tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString(), tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString(), tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString()
                    });
                } else {
                    if (Sequel.menyimpantf2("hasil_radiologi", "?,?,?,?", "Hasil Pemeriksaan", 4, new String[]{
                        tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString(), tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString(),
                        tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString(), HasilPeriksa.getText()                           
                    }) == false) {
                        isLog("Edit");
                        isUpdate();
                    }
                }
                JOptionPane.showMessageDialog(null, "Proses update selesai...!!!!");
        }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void PenjabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenjabKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenjabKeyPressed

    private void MnLihatGambarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLihatGambarActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else if (tbDokter.getSelectedRow() <= -1) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data..!!");
        } else {
            if (Kd2.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Maaf, silahkan pilih data yang mau ditampilkan...!!!!");
            } else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Valid.panggilUrl("radiologi/login.php?act=login&usere=admin&passwordte=akusayangsamakamu&no_rawat=" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString() + "&tanggal=" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString() + "&jam=" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString());
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnLihatGambarActionPerformed

    private void BtnSimpan4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan4ActionPerformed
        if (tbDokter.getSelectedRow() > -1) {
            if (!tbDokter.getValueAt(tbDokter.getSelectedRow(), 1).toString().equals("")) {
                if (!KodePerujuk.getText().equals("-")) {
                    Sequel.queryu2("update periksa_radiologi set nip=?,dokter_perujuk=?,kd_dokter=? where no_rawat=? and tgl_periksa=? and jam=?", 6, new String[]{
                        KdPtgUbah.getText(), KodePerujuk.getText(), KodePj.getText(), tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString(),
                        tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString(), tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString()
                    });
                    tampil();
                    dokter.dispose();
                    petugas.dispose();
                    WindowGantiDokterParamedis.dispose();
                }
                if (KodePerujuk.getText().equals("-")) {
                    Sequel.queryu2("update periksa_radiologi set nip=?,kd_dokter=? where no_rawat=? and tgl_periksa=? and jam=?", 5, new String[]{
                        KdPtgUbah.getText(), KodePj.getText(), tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString(),
                        tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString(), tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString()
                    });
                    Sequel.queryu2("update rujuk_masuk set perujuk=? where no_rawat=?", 2, new String[]{
                        NmPerujuk.getText(), tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString()
                    });
                    tampil();
                    dokter.dispose();
                    petugas.dispose();
                    WindowGantiDokterParamedis.dispose();
                }
            }
        }
    }//GEN-LAST:event_BtnSimpan4ActionPerformed

    private void BtnCloseIn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn4ActionPerformed
        dokter.dispose();
        petugas.dispose();
        WindowGantiDokterParamedis.dispose();
    }//GEN-LAST:event_BtnCloseIn4ActionPerformed

    private void btnDokterPjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterPjActionPerformed
        pilihan = "penjab";
        var.setform("DlgCariPeriksaRadiologi");
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokterPjActionPerformed

    private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        pilihan = "perujuk";
        var.setform("DlgCariPeriksaRadiologi");
        if (KodePerujuk.getText().equals("-")) {
            rujukmasuk.tampil2();
            rujukmasuk.TCariPerujuk.requestFocus();
            rujukmasuk.WindowPerujuk.setSize(this.getWidth() - 20, this.getHeight() - 20);
            rujukmasuk.WindowPerujuk.setLocationRelativeTo(internalFrame1);
            rujukmasuk.WindowPerujuk.setVisible(true);
        } else {
            dokter.emptTeks();
            dokter.isCek();
            dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            dokter.setLocationRelativeTo(internalFrame1);
            dokter.setVisible(true);
        }
        
    }//GEN-LAST:event_btnDokterActionPerformed

    private void KodePerujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodePerujukKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", NmPerujuk, KodePerujuk.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnDokterActionPerformed(null);
        } else {
            Valid.pindah(evt, KdPtgUbah, BtnSimpan4);
        }
    }//GEN-LAST:event_KodePerujukKeyPressed

    private void KdPtgUbahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPtgUbahKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select nama from petugas where nip=?", NmPtgUbah, KdPtgUbah.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnPetugasActionPerformed(null);
        } else {
            Valid.pindah(evt, KodePj, KodePerujuk);
        }
    }//GEN-LAST:event_KdPtgUbahKeyPressed

    private void btnPetugas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugas1ActionPerformed
        var.setform("DlgCariPeriksaRadiologi");
        pilihan = "ubahpetugas";
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugas1ActionPerformed

    private void MnUbahDokterPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUbahDokterPetugasActionPerformed
        if (tbDokter.getSelectedRow() > -1) {
            if (!tbDokter.getValueAt(tbDokter.getSelectedRow(), 1).toString().equals("")) {
                if (Sequel.cariRegistrasi(tbDokter.getValueAt(tbDokter.getSelectedRow(), 1).toString()) > 0) {
                    JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi, data tidak boleh diubah.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                    TCari.requestFocus();
                } else {
                    panggilMedis();
                }
            }
        }
    }//GEN-LAST:event_MnUbahDokterPetugasActionPerformed

    private void tbListDicomMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListDicomMouseClicked
        if (tabModeDicom.getRowCount() != 0) {
            if (evt.getClickCount() == 1) {
                Valid.tabelKosong(tabModeSeries);
                seriesbyid = orthanc.AmbilSeriesById(tbListDicom.getValueAt(tbListDicom.getSelectedRow(), 3).toString());
//                for (JsonNode list : seriesbyid) {
                    JsonNode listSeries = seriesbyid;
                    for (JsonNode sublist : listSeries.path("Instances")) {
                        tabModeSeries.addRow(new Object[]{
                            listSeries.path("MainDicomTags").path("Modality").asText(),listSeries.path("MainDicomTags").path("BodyPartExamined").asText(),sublist.asText()
                        });
                    }
//                }
            }
            if (evt.getClickCount() == 2) {
                btnDicomActionPerformed(null);
            }
        }
    }//GEN-LAST:event_tbListDicomMouseClicked

    private void tbListDicomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbListDicomKeyPressed
        if (tabModeDicom.getRowCount() != 0) {
//            if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
//                btnDicomActionPerformed(null);
//            }
        }
    }//GEN-LAST:event_tbListDicomKeyPressed

    private void btnDicomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDicomActionPerformed
        tampilGambar("dicom");
    }//GEN-LAST:event_btnDicomActionPerformed

    private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        tampilOrthanc();
    }//GEN-LAST:event_BtnCari1ActionPerformed

    private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCari1ActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCari1KeyPressed

    private void BtnCari2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari2ActionPerformed
        tampilGambar("stone");
    }//GEN-LAST:event_BtnCari2ActionPerformed

    private void BtnCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCari2KeyPressed

    private void btnHasilRadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHasilRadActionPerformed
        var.setform("DlgCariPeriksaRadiologi");
        radiologi.emptTeks();
        radiologi.isCek();
        radiologi.setSize(internalFrame6.getWidth() - 20, internalFrame6.getHeight() - 20);
        radiologi.setLocationRelativeTo(internalFrame6);
        radiologi.setAlwaysOnTop(false);
        radiologi.setVisible(true);
    }//GEN-LAST:event_btnHasilRadActionPerformed

    private void TKlinisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKlinisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TKlinisKeyPressed

    private void TJudulKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TJudulKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TJudulKeyPressed

    private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
        dokter2.isCek();
        dokter2.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter2.setLocationRelativeTo(internalFrame1);
        dokter2.setAlwaysOnTop(false);
        dokter2.setVisible(true);
    }//GEN-LAST:event_BtnSeek4ActionPerformed

    private void BtnSeek4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek4KeyPressed

    private void kddokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddokterKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kddokterKeyPressed

    private void MnTarikGambarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnTarikGambarActionPerformed
        hasilexpertise = orthanc.AmbilHasilExpertise(Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat=?", tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString()), Valid.SetTgl(Tgl1.getSelectedItem() + "").replaceAll("-", ""), tbListDicom.getValueAt(tbListDicom.getSelectedRow(), 2).toString());
        String notif = "<html>Judul : " + hasilexpertise.path("response").path("judul");
        notif += "<br>Hasil : " + hasilexpertise.path("response").path("hasil");
        notif += "<br>Kesan : " + hasilexpertise.path("response").path("kesan");
        notif += "<br>Saran : " + hasilexpertise.path("response").path("saran");
        notif += "<br>Apakah Ingin Disimpan ? </html>";
//        JOptionPane.showConfirmDialog(null, notif);
        String judul = hasilexpertise.path("response").path("judul").asText();
        String hasil = hasilexpertise.path("response").path("hasil").asText();
        String kesan = hasilexpertise.path("response").path("kesan").asText();
        String saran = hasilexpertise.path("response").path("saran").asText();
        if (Sequel.cariInteger("select count(no_rawat) from saran_kesan_rad where no_rawat='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString() + "'") > 0) {
            int opsi = JOptionPane.showConfirmDialog(null, notif, "Sudah ada hasil expertise,apakah ingin update hasil expertise?", JOptionPane.YES_NO_OPTION);
            if (opsi == JOptionPane.YES_OPTION) {
//                JOptionPane.showMessageDialog(null, "Saat dipilih yes !");
                Sequel.mengedit("hasil_radiologi", "no_rawat='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString() + "' and tgl_periksa='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString() + "' and jam='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString() + "'",
                        "hasil='" + hasil + "'");
                Sequel.queryu2("update saran_kesan_rad set judul=?,saran=?,kesan=? where no_rawat=? and tgl_periksa=? and jam=?", 6, new String[]{
                    judul, saran, kesan, tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString(), tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString(), tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString()
                });
                tampil();
            }
        }

        if (Sequel.cariInteger("select count(no_rawat) from saran_kesan_rad where no_rawat='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString() + "'") < 0) {
            int opsi = JOptionPane.showConfirmDialog(null, notif, "Simpan Hasil Expertise Data", JOptionPane.YES_NO_OPTION);
            if (opsi == JOptionPane.YES_OPTION) {
//                JOptionPane.showMessageDialog(null, "Saat dipilih yes !");

                if (Sequel.menyimpantf("hasil_radiologi", "?,?,?,?", "Hasil Radiologi", 4, new String[]{
                    tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString(), Sequel.cariIsi("select current_date()"),
                    Sequel.cariIsi("select current_time()"), hasil
                }) == true) {
                    Sequel.menyimpan("saran_kesan_rad", "?,?,?,?,?,?", 6, new String[]{
                        tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString(), Sequel.cariIsi("select current_date()"),
                        Sequel.cariIsi("select current_time()"), judul, saran, kesan});
                    tampil();
                }
            }
//            else 
//                JOptionPane.showMessageDialog(null, "Saat dipilih no !");            
        }    
    }//GEN-LAST:event_MnTarikGambarActionPerformed

    private void tbListDicom1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbListDicom1MouseClicked
        String urlins;
        BufferedImage c = null;
        URL url = null;
        try {
            urlins = orthanc.ambilPreview(tbListDicom1.getValueAt(tbListDicom1.getSelectedRow(), 2).toString());
            jLabel1.setText("");
            url = new URL(urlins);
            c = ImageIO.read(url);
            ImageIcon image = new ImageIcon(c);
            jLabel1.setIcon(image);
        } catch (NoSuchAlgorithmException | KeyManagementException | IOException ex) {
            Logger.getLogger(DlgCariPeriksaRadiologi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tbListDicom1MouseClicked

    private void tbListDicom1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbListDicom1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbListDicom1KeyPressed

    private void MnRiwayatRadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRiwayatRadActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else if (tbDokter.getSelectedRow() <= -1) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data..!!");
        } else {
            TNoRw.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(), 1).toString().substring(0,6));
            TPasien.setText(Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?",TNoRw.getText()));
            WindowRiwayatRad.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            WindowRiwayatRad.setLocationRelativeTo(internalFrame1);
            WindowRiwayatRad.setVisible(true);
            tampilRiwayat();
        }
    }//GEN-LAST:event_MnRiwayatRadActionPerformed

    private void BtnKeluar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar1ActionPerformed
        WindowRiwayatRad.dispose();
    }//GEN-LAST:event_BtnKeluar1ActionPerformed

    private void BtnKeluar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluar1KeyPressed

    }//GEN-LAST:event_BtnKeluar1KeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed

    }//GEN-LAST:event_TNoRwKeyPressed

    private void tbRiwayatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRiwayatMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRiwayatMouseClicked

    private void tbRiwayatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRiwayatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbRiwayatKeyPressed

    private void BtnCari3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari3ActionPerformed
        tampilRiwayat();
    }//GEN-LAST:event_BtnCari3ActionPerformed

    private void BtnCari3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari3KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCari3ActionPerformed(null);
        } else {
            Valid.pindah(evt, TNoRw, TPasien);
        }
    }//GEN-LAST:event_BtnCari3KeyPressed

    private void MnTarikHasilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnTarikHasilActionPerformed
        hasilexpertise = orthanc.AmbilHasilExpertise(Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat=?", tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString()), Valid.SetTgl(Tgl1.getSelectedItem() + "").replaceAll("-", ""), tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString());
        String notif = "<html>Judul : " + hasilexpertise.path("response").path("judul");
        notif += "<br>Hasil : " + hasilexpertise.path("response").path("hasil");
        notif += "<br>Kesan : " + hasilexpertise.path("response").path("kesan");
        notif += "<br>Saran : " + hasilexpertise.path("response").path("saran");
        notif += "<br>Apakah Ingin Disimpan ? </html>";
//        JOptionPane.showConfirmDialog(null, notif);
        String judul = hasilexpertise.path("response").path("judul").asText();
        String hasil = hasilexpertise.path("response").path("hasil").asText();
        String kesan = hasilexpertise.path("response").path("kesan").asText();
        String saran = hasilexpertise.path("response").path("saran").asText();
        if (Sequel.cariInteger("select count(no_rawat) from saran_kesan_rad where no_rawat='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString() + "'") > 0) {
            int opsi = JOptionPane.showConfirmDialog(null, notif, "Sudah ada hasil expertise,apakah ingin update hasil expertise?", JOptionPane.YES_NO_OPTION);
            if (opsi == JOptionPane.YES_OPTION) {
                Sequel.mengedit("hasil_radiologi", "no_rawat='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString() + "' and tgl_periksa='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString() + "' and jam='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString() + "'",
                        "hasil='" + hasil + "'");
                Sequel.queryu2("update saran_kesan_rad set judul=?,saran=?,kesan=? where no_rawat=? and tgl_periksa=? and jam=?", 6, new String[]{
                    judul, saran, kesan, tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString(), tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString(), tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString()
                });
                tampil();
            }
        }

//        if (Sequel.cariInteger("select count(no_rawat) from saran_kesan_rad where no_rawat='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString() + "'") < 0) {
//            int opsi = JOptionPane.showConfirmDialog(null, notif, "Simpan Hasil Expertise Data", JOptionPane.YES_NO_OPTION);
//            if (opsi == JOptionPane.YES_OPTION) {
//                if (Sequel.menyimpantf("hasil_radiologi", "?,?,?,?", "Hasil Radiologi", 4, new String[]{
//                    tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString(), Sequel.cariIsi("select current_date()"),
//                    Sequel.cariIsi("select current_time()"), hasil
//                }) == true) {
//                    Sequel.menyimpan("saran_kesan_rad", "?,?,?,?,?,?", 6, new String[]{
//                        tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString(), Sequel.cariIsi("select current_date()"),
//                        Sequel.cariIsi("select current_time()"), judul, saran, kesan});
//                    tampil();
//                }
//            }
//        }
    }//GEN-LAST:event_MnTarikHasilActionPerformed

    private void tbTranfusiDarahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTranfusiDarahMouseClicked
        if (tabModeLab.getRowCount() != 0) {
            try {
//                getData2();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbTranfusiDarahMouseClicked

    private void tbTranfusiDarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTranfusiDarahKeyPressed
        if (tabModeLab.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
//                    getData2();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbTranfusiDarahKeyPressed

    private void BtnLihatLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLihatLabActionPerformed
        tampilLab();
    }//GEN-LAST:event_BtnLihatLabActionPerformed

    private void BtnLihatLabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnLihatLabKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnLihatLabActionPerformed(null);
        }
    }//GEN-LAST:event_BtnLihatLabKeyPressed

    private void TKesanKeyPressed(java.awt.event.KeyEvent evt) {
        //        Valid.pindah(evt,TKdPrw,TPemeriksaan);
    }

    private void TSaranKeyPressed(java.awt.event.KeyEvent evt) {
        // TODO add your handling code here:
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariPeriksaRadiologi dialog = new DlgCariPeriksaRadiologi(new javax.swing.JFrame(), true);
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
    private widget.TextBox Alamat;
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnCari2;
    private widget.Button BtnCari3;
    private widget.Button BtnCloseIn4;
    private widget.Button BtnCloseIn5;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluar1;
    private widget.Button BtnLihatLab;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint1;
    private widget.Button BtnSeek4;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpan4;
    private widget.panelisi FormInput;
    private widget.TextArea HasilPeriksa;
    private widget.TextBox Jk;
    private widget.TextBox Kd2;
    private widget.TextBox KdPtgUbah;
    private widget.TextBox KodePerujuk;
    private widget.TextBox KodePj;
    private javax.swing.JMenuItem MnCetakNota;
    private javax.swing.JMenuItem MnLihatGambar;
    private javax.swing.JMenuItem MnLihatHasil;
    private javax.swing.JMenuItem MnRiwayatRad;
    private javax.swing.JMenuItem MnTarikGambar;
    private javax.swing.JMenuItem MnTarikHasil;
    private javax.swing.JMenuItem MnUbahDokterPetugas;
    private widget.TextBox NmDokterPj;
    private widget.TextBox NmPerujuk;
    private widget.TextBox NmPtgUbah;
    private widget.TextBox NoOrder;
    private widget.TextBox NoRM;
    private widget.TextBox NoRawat;
    private widget.TextBox Penjab;
    private widget.TextBox Perujuk;
    private widget.TextBox Petugas;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.ScrollPane Scroll5;
    private widget.ScrollPane Scroll6;
    private widget.TextBox TCari;
    private widget.TextBox TJudul;
    private widget.TextArea TKesan;
    private widget.TextBox TKlinis;
    private widget.TextBox TNoRw;
    private widget.TextBox TNoRw2;
    private widget.TextBox TPasien;
    private widget.TextArea TSaran;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.TextBox Umur;
    private javax.swing.JDialog WindowGantiDokterParamedis;
    private javax.swing.JDialog WindowHasil;
    private javax.swing.JDialog WindowRiwayatRad;
    private widget.Button btnDicom;
    private widget.Button btnDokter;
    private widget.Button btnDokterPj;
    private widget.Button btnHasilRad;
    private widget.Button btnPasien;
    private widget.Button btnPetugas;
    private widget.Button btnPetugas1;
    private widget.ComboBox cmbHlm;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
    private widget.InternalFrame internalFrame7;
    private widget.InternalFrame internalFrame8;
    private javax.swing.JLabel jLabel1;
    private widget.Label jLabel12;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private widget.TextBox jamPeriksa;
    private widget.TextBox kddokter;
    private widget.TextBox kdmem;
    private widget.TextBox kdptg;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label27;
    private widget.Label label28;
    private widget.Label label9;
    private widget.TextBox nmdokter;
    private widget.TextBox nmmem;
    private widget.TextBox nmptg;
    private widget.panelisi panelGlass5;
    private widget.panelisi panelGlass6;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelIsi7;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private widget.panelisi panelisi6;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.Table tbDokter;
    private widget.Table tbListDicom;
    private widget.Table tbListDicom1;
    private widget.Table tbRiwayat;
    private widget.Table tbTranfusiDarah;
    private widget.TextBox tglPeriksa;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        try {
            Valid.tabelKosong(tabMode);
            ps = koneksi.prepareStatement(
                    "select periksa_radiologi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,petugas.nama,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,"
                    + "periksa_radiologi.dokter_perujuk,periksa_radiologi.kd_dokter,dokter.nm_dokter from periksa_radiologi inner join reg_periksa inner join pasien inner join petugas inner join dokter "
                    + "on periksa_radiologi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_radiologi.kd_dokter=dokter.kd_dokter and periksa_radiologi.nip=petugas.nip WHERE "
//                    + "left join permintaan_radiologi on periksa_radiologi.no_rawat=permintaan_radiologi.no_rawat where "
//                    + "left join permintaan_radiologi on periksa_radiologi.no_rawat=permintaan_radiologi.no_rawat left join diagnosa_pasien_klinis on diagnosa_pasien_klinis.noorder=permintaan_radiologi.noorder where "
                    + "periksa_radiologi.tgl_periksa between ? and ? and periksa_radiologi.no_rawat like ? and reg_periksa.no_rkm_medis like ? "
                    + "and petugas.nip like ? and dokter.nm_dokter like ? and pasien.nm_pasien like ? or "
                    + "periksa_radiologi.tgl_periksa between ? and ? and periksa_radiologi.no_rawat like ? and reg_periksa.no_rkm_medis like ? "
                    + "and petugas.nip like ? and dokter.nm_dokter like ? and petugas.nama like ? or "
                    + "periksa_radiologi.tgl_periksa between ? and ? and periksa_radiologi.no_rawat like ? and reg_periksa.no_rkm_medis like ? "
                    + "and petugas.nip like ? and dokter.nm_dokter like ? and reg_periksa.no_rkm_medis like ? group by concat(periksa_radiologi.no_rawat,periksa_radiologi.tgl_periksa,periksa_radiologi.jam) "
                    + "order by periksa_radiologi.tgl_periksa desc,periksa_radiologi.jam desc");
            try {
                ps.setString(1, Valid.SetTgl(Tgl1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(Tgl2.getSelectedItem() + ""));
                ps.setString(3, "%" + NoRawat.getText() + "%");
                ps.setString(4, "%" + kdmem.getText() + "%");
                ps.setString(5, "%" + kdptg.getText() + "%");
                ps.setString(6, "%" + nmdokter.getText() + "%");
                ps.setString(7, "%" + TCari.getText().trim() + "%");
                ps.setString(8, Valid.SetTgl(Tgl1.getSelectedItem() + ""));
                ps.setString(9, Valid.SetTgl(Tgl2.getSelectedItem() + ""));
                ps.setString(10, "%" + NoRawat.getText() + "%");
                ps.setString(11, "%" + kdmem.getText() + "%");
                ps.setString(12, "%" + kdptg.getText() + "%");
                ps.setString(13, "%" + nmdokter.getText() + "%");
                ps.setString(14, "%" + TCari.getText().trim() + "%");
                ps.setString(15, Valid.SetTgl(Tgl1.getSelectedItem() + ""));
                ps.setString(16, Valid.SetTgl(Tgl2.getSelectedItem() + ""));
                ps.setString(17, "%" + NoRawat.getText() + "%");
                ps.setString(18, "%" + kdmem.getText() + "%");
                ps.setString(19, "%" + kdptg.getText() + "%");
                ps.setString(20, "%" + nmdokter.getText() + "%");
                ps.setString(21, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                ttl = 0;
                while (rs.next()) {
                    kamar = Sequel.cariIsi("select ifnull(kd_kamar,'') from kamar_inap where no_rawat='" + rs.getString("no_rawat") + "' order by tgl_masuk desc limit 1");
                    if (!kamar.equals("")) {
                        namakamar = kamar + ", " + Sequel.cariIsi("select nm_bangsal from bangsal inner join kamar on bangsal.kd_bangsal=kamar.kd_bangsal "
                                + " where kamar.kd_kamar='" + kamar + "' ");
                        kamar = "Kamar";
                    } else if (kamar.equals("")) {
                        kamar = "Poli";
                        namakamar = Sequel.cariIsi("select nm_poli from poliklinik inner join reg_periksa on poliklinik.kd_poli=reg_periksa.kd_poli "
                                + "where reg_periksa.no_rawat='" + rs.getString("no_rawat") + "'");
                    }
                    klinis = Sequel.cariIsi("SELECT klinis FROM diagnosa_pasien_klinis INNER JOIN permintaan_radiologi ON diagnosa_pasien_klinis.noorder=permintaan_radiologi.noorder " +
                            "WHERE permintaan_radiologi.tgl_hasil='"+rs.getString("tgl_periksa")+"' AND permintaan_radiologi.jam_hasil='"+rs.getString("jam")+"' AND permintaan_radiologi.no_rawat='"+rs.getString("no_rawat")+"'");
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rawat"), rs.getString("no_rkm_medis") + " " + rs.getString("nm_pasien") + " (" + kamar + " : " + namakamar + ")", rs.getString("nama"),
                        rs.getString("tgl_periksa"), rs.getString("jam"), Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", rs.getString("dokter_perujuk")), rs.getString("nm_dokter"), klinis,
                         Sequel.cariIsi("select perujuk from rujuk_masuk where no_rawat='" + rs.getString("no_rawat") + "'")
                    });
                    tabMode.addRow(new Object[]{"", "", "Kode Periksa", "Nama Pemeriksaan", "Biaya Pemeriksaan", "", "", "", ""});
                    ps2 = koneksi.prepareStatement(
                            "select jns_perawatan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.biaya from periksa_radiologi inner join jns_perawatan_radiologi "
                            + "on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw where periksa_radiologi.no_rawat=? and periksa_radiologi.tgl_periksa=? "
                            + "and periksa_radiologi.jam=?");
                    try {
                        ps2.setString(1, rs.getString("no_rawat"));
                        ps2.setString(2, rs.getString("tgl_periksa"));
                        ps2.setString(3, rs.getString("jam"));
                        rs2 = ps2.executeQuery();
                        while (rs2.next()) {
                            ttl = ttl + rs2.getDouble("biaya");
                            tabMode.addRow(new Object[]{"", "", rs2.getString("kd_jenis_prw"), rs2.getString("nm_perawatan"), Valid.SetAngka(rs2.getDouble("biaya")), "", "", "", ""});
                        }
                    } catch (Exception e) {
                        System.out.println("simrskhanza.DlgCariPeriksaRadiologi.tampil() ps2 : " + e);
                    } finally {
                        if (rs2 != null) {
                            rs2.close();
                        }
                        if (ps2 != null) {
                            ps2.close();
                        }
                    }

                    tabMode.addRow(new Object[]{"", "", "Kode BHP", "Nama BHP", "Satuan", "Jumlah", "", "", ""});
                    ps3 = koneksi.prepareStatement(
                            "select beri_bhp_radiologi.kode_brng,ipsrsbarang.nama_brng,beri_bhp_radiologi.kode_sat,beri_bhp_radiologi.jumlah, "
                            + "beri_bhp_radiologi.total from beri_bhp_radiologi inner join ipsrsbarang on ipsrsbarang.kode_brng=beri_bhp_radiologi.kode_brng "
                            + "where beri_bhp_radiologi.no_rawat=? and beri_bhp_radiologi.tgl_periksa=? and beri_bhp_radiologi.jam=?");
                    try {
                        ps3.setString(1, rs.getString("no_rawat"));
                        ps3.setString(2, rs.getString("tgl_periksa"));
                        ps3.setString(3, rs.getString("jam"));
                        rs3 = ps3.executeQuery();
                        while (rs3.next()) {
                            tabMode.addRow(new Object[]{"", "", rs3.getString("kode_brng"), rs3.getString("nama_brng"), rs3.getString("kode_sat"), rs3.getString("jumlah"), "", "", ""});
                        }
                    } catch (Exception e) {
                        System.out.println("simrskhanza.DlgCariPeriksaRadiologi.tampil() ps3 : " + e);
                    } finally {
                        if (rs3 != null) {
                            rs3.close();
                        }
                        if (ps3 != null) {
                            ps3.close();
                        }
                    }
                    ps5 = koneksi.prepareStatement(
                            "select hasil from hasil_radiologi where hasil_radiologi.no_rawat=? and hasil_radiologi.tgl_periksa=? and hasil_radiologi.jam=?");
                    try {
                        ps5.setString(1, rs.getString("no_rawat"));
                        ps5.setString(2, rs.getString("tgl_periksa"));
                        ps5.setString(3, rs.getString("jam"));
                        rs5 = ps5.executeQuery();
                        while (rs5.next()) {
                            tabMode.addRow(new Object[]{"", "", "Hasil Pemeriksaan :", rs5.getString("hasil"), "", "", "", "", ""});
                        }
                    } catch (Exception e) {
                        System.out.println("simrskhanza.DlgCariPeriksaRadiologi.tampil() ps5 : " + e);
                    } finally {
                        if (rs5 != null) {
                            rs5.close();
                        }
                        if (ps5 != null) {
                            ps5.close();
                        }
                    }

                    ps6 = koneksi.prepareStatement(
                            "select judul from saran_kesan_rad where no_rawat=? and tgl_periksa=? and jam=?");
                    try {
                        ps6.setString(1, rs.getString("no_rawat"));
                        ps6.setString(2, rs.getString("tgl_periksa"));
                        ps6.setString(3, rs.getString("jam"));
                        rs6 = ps6.executeQuery();
                        while (rs6.next()) {
                            tabMode.addRow(new Object[]{"", "", "Judul :", rs6.getString("judul"), "", "", "", "", ""});
                        }
                    } catch (Exception e) {
                        System.out.println("simrskhanza.DlgCariPeriksaRadiologi.tampil() ps6 : " + e);
                    } finally {
                        if (rs6 != null) {
                            rs6.close();
                        }
                        if (ps6 != null) {
                            ps6.close();
                        }
                    }

                    ps7 = koneksi.prepareStatement(
                            "select saran from saran_kesan_rad where no_rawat=? and tgl_periksa=? and jam=?");
                    try {
                        ps7.setString(1, rs.getString("no_rawat"));
                        ps7.setString(2, rs.getString("tgl_periksa"));
                        ps7.setString(3, rs.getString("jam"));
                        rs7 = ps7.executeQuery();
                        while (rs7.next()) {
                            tabMode.addRow(new Object[]{"", "", "Saran :", rs7.getString("saran"), "", "", "", "", ""});
                        }
                    } catch (Exception e) {
                        System.out.println("simrskhanza.DlgCariPeriksaRadiologi.tampil() ps7 : " + e);
                    } finally {
                        if (rs7 != null) {
                            rs7.close();
                        }
                        if (ps7 != null) {
                            ps7.close();
                        }
                    }

                    ps8 = koneksi.prepareStatement(
                            "select kesan from saran_kesan_rad where no_rawat=? and tgl_periksa=? and jam=?");
                    try {
                        ps8.setString(1, rs.getString("no_rawat"));
                        ps8.setString(2, rs.getString("tgl_periksa"));
                        ps8.setString(3, rs.getString("jam"));
                        rs8 = ps8.executeQuery();
                        while (rs8.next()) {
                            tabMode.addRow(new Object[]{"", "", "Kesan :", rs8.getString("kesan"), "", "", "", "", ""});
                        }
                    } catch (Exception e) {
                        System.out.println("simrskhanza.DlgCariPeriksaRadiologi.tampil() ps8 : " + e);
                    } finally {
                        if (rs8 != null) {
                            rs8.close();
                        }
                        if (ps8 != null) {
                            ps8.close();
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("simrskhanza.DlgCariPeriksaRadiologi.tampil() PS : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
            if (ttl > 0) {
                tabMode.addRow(new Object[]{">>", "Total : " + Valid.SetAngka(ttl), "", "", "", "", "", "", ""});
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    public void SetNoRw(String norw) {
        NoRawat.setText(norw);
        tampil();
        Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='" + norw + "'", Tgl1);
    }

    private void getData() {
        Kd2.setText("");
        if (tbDokter.getSelectedRow() != -1) {
            Kd2.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString());
            tglPeriksa.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString());//added
            jamPeriksa.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString());//added
            Sequel.cariIsi("select noorder from permintaan_radiologi where no_rawat=? AND tgl_hasil='"+tglPeriksa.getText()+"' AND jam_hasil='"+jamPeriksa.getText()+"'", NoOrder, Kd2.getText());
            Petugas.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(), 6).toString());
        }
    }

    public void isCek() {
//        MnCetakNota.setEnabled(var.getperiksa_radiologi());
//        BtnHapus.setEnabled(var.getperiksa_radiologi());
//        MnUbahDokterPetugas.setEnabled(var.getperiksa_radiologi());
//        MnLihatGambar.setEnabled(var.getperiksa_radiologi());
//        MnLihatHasil.setEnabled(var.getperiksa_radiologi());
//        BtnPrint.setEnabled(var.getperiksa_radiologi());
        if (var.getkode().equals("Admin Utama") || var.getkode().equals("D0000132") || var.getkode().equals("DR00019") || var.getkode().equals("rad1")) {
            BtnHapus.setEnabled(true);
            MnCetakNota.setEnabled(true);
            MnLihatGambar.setEnabled(true);
            MnLihatHasil.setEnabled(true);
            MnUbahDokterPetugas.setEnabled(true);
            MnTarikGambar.setEnabled(true);
            MnTarikHasil.setEnabled(true);
        } else {
            BtnHapus.setEnabled(false);
            MnCetakNota.setEnabled(false);
            MnLihatGambar.setEnabled(false);
            MnLihatHasil.setEnabled(false);
            MnUbahDokterPetugas.setEnabled(false);
            MnTarikGambar.setEnabled(false);
            MnTarikHasil.setEnabled(false);
        }
//        label22.setVisible(false);
//        TNoRw2.setVisible(false);
    }

    public void setPasien(String pasien) {
        NoRawat.setText(pasien);
    }

    private void panggilMedis() {
        try {
            ps5 = koneksi.prepareStatement(
                    "select periksa_radiologi.nip,petugas.nama,periksa_radiologi.dokter_perujuk,"
                    + "periksa_radiologi.kd_dokter,dokter.nm_dokter from periksa_radiologi "
                    + "inner join petugas inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter "
                    + "and periksa_radiologi.nip=petugas.nip where periksa_radiologi.no_rawat=? "
                    + "and periksa_radiologi.tgl_periksa=? and periksa_radiologi.jam=?");
            try {
                ps5.setString(1, tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString());
                ps5.setString(2, tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString());
                ps5.setString(3, tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString());
                rs5 = ps5.executeQuery();
                if (rs5.next()) {
                    KodePerujuk.setText(rs5.getString("dokter_perujuk"));
                    NmPerujuk.setText(Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", rs5.getString("dokter_perujuk")));
                    if (rs5.getString("dokter_perujuk").equals("-")) {
                        NmPerujuk.setText(Sequel.cariIsi("select perujuk from rujuk_masuk where no_rawat='" + tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString() + "'"));
//                        System.out.println("norawat "+tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString());
                    }
                    KodePj.setText(rs5.getString("kd_dokter"));
                    NmDokterPj.setText(rs5.getString("nm_dokter"));
                    KdPtgUbah.setText(rs5.getString("nip"));
                    NmPtgUbah.setText(rs5.getString("nama"));
                    WindowGantiDokterParamedis.setSize(600, 130);
                    WindowGantiDokterParamedis.setLocationRelativeTo(internalFrame1);
                    WindowGantiDokterParamedis.setVisible(true);
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            } finally {
                if (rs5 != null) {
                    rs5.close();
                }
                if (ps5 != null) {
                    ps5.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }
    }

    private void isPhoto() {
//        if (ChkAccor.isSelected() == true) {
//            ChkAccor.setVisible(false);
//            PanelAccor.setPreferredSize(new Dimension(internalFrame1.getWidth() - 300, HEIGHT));
//            TabData.setVisible(true);
//            ChkAccor.setVisible(true);
//        } else if (ChkAccor.isSelected() == false) {
//            ChkAccor.setVisible(false);
//            PanelAccor.setPreferredSize(new Dimension(15, HEIGHT));
//            TabData.setVisible(false);
//            ChkAccor.setVisible(true);
//        }
    }

    private void panggilPhoto() {
//        if(TabData.isVisible()==true){
//            if(tbDokter.getSelectedRow()!= -1){
//                if((!Kd2.getText().equals(""))&&(!Petugas.getText().equals(""))){
//                     try {
//                        ps=koneksi.prepareStatement("select gambar_radiologi.lokasi_gambar from gambar_radiologi where gambar_radiologi.no_rawat=? and gambar_radiologi.tgl_periksa=? and gambar_radiologi.jam=? ");
//                        try {
//                            ps.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
//                            ps.setString(2,tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
//                            ps.setString(3,tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
//                            rs=ps.executeQuery();
//                            htmlContent = new StringBuilder();
//                            while(rs.next()){
////                                htmlContent.append("<tr><td border='0' align='center'><a href='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/radiologi/"+rs.getString("lokasi_gambar")+"'><img src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/radiologi/"+rs.getString("lokasi_gambar")+"' alt='photo' width='"+(internalFrame1.getWidth()-370)+"' height='"+(internalFrame1.getWidth()-370)+"'/></a></td></tr>");
//                            }
//                            LoadHTML.setText(
//                                "<html>"+
//                                  "<table width='100%' border='0' align='center' cellpadding='1px' cellspacing='1px' class='tbl_form'>"+
//                                    htmlContent.toString()+
//                                  "</table>"+
//                                "</html>"
//                            );
//                        } catch (Exception e) {
//                            System.out.println("Notif : "+e);
//                        } finally{
//                            if(rs!=null){
//                                rs.close();
//                            }
//                            if(ps!=null){
//                                ps.close();
//                            }
//                        }
//                        
//                        ps5=koneksi.prepareStatement("select hasil_radiologi.hasil from hasil_radiologi where hasil_radiologi.no_rawat=? and hasil_radiologi.tgl_periksa=? and hasil_radiologi.jam=?");  
//                        try {
//                            ps5.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
//                            ps5.setString(2,tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
//                            ps5.setString(3,tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
//                            rs5=ps5.executeQuery();
//                            if(rs5.next()){  
//                                HasilPeriksa.setText(rs5.getString("hasil"));
//                            }else{
//                                HasilPeriksa.setText("");
//                            }
//                        } catch (Exception e) {
//                            System.out.println("Notif ps5 : "+e);
//                        } finally{
//                            if(rs5!=null){
//                                rs5.close();
//                            }
//                            if(ps5!=null){
//                                ps5.close();
//                            }
//                        }
//                    } catch (Exception e) {
//                        System.out.println("Notif : "+e);
//                    } 
//                }else{
//                    LoadHTML.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'></font></center></body></html>");
//                    HasilPeriksa.setText("");
//                }
//            }
//        }
    }

    private void tampilGambar(String viewer) {
        if (tabModeDicom.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else {
            if (tbListDicom.getSelectedRow() != -1) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                url_orthanc = "";
                if (NoRawat.getText().equals("")) {
                    if (viewer.equals("dicom")) {
                        url_orthanc = koneksiDB.URLORTHANC() + "/web-viewer/app/viewer.html?series=" + tbListDicom.getValueAt(tbListDicom.getSelectedRow(), 3).toString();
                        OrthancDICOM orthan = new OrthancDICOM(null, false);
                        orthan.setJudul("::[ DICOM PACS Pasien " + tbDokter.getValueAt(tbDokter.getSelectedRow(), 1).toString() + ", Series " + tbListDicom.getValueAt(tbListDicom.getSelectedRow(), 3).toString() + " ]::", tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString().replaceAll("/", ""), tbListDicom.getValueAt(tbListDicom.getSelectedRow(), 3).toString());
                        try {
                            System.out.println("URL : " + url_orthanc);
                            orthan.loadURL(url_orthanc);
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex);
                        }
                        orthan.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                        orthan.setLocationRelativeTo(internalFrame1);
                        orthan.setVisible(true);
                    }
                    if (viewer.equals("stone")) {
                        seriesbyid = orthanc.AmbilSeriesById(tbListDicom.getValueAt(tbListDicom.getSelectedRow(), 3).toString());
                        JsonNode listSeries = seriesbyid;
                        url_orthanc = koneksiDB.URLORTHANC() + "/stone-webviewer/index.html?study=" + tbListDicom.getValueAt(tbListDicom.getSelectedRow(), 4).toString() + "&series=" + listSeries.path("MainDicomTags").path("SeriesInstanceUID").asText();
                        try {
                            //Set your page url in this string. For eg, I m using URL for Google Search engine
                            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_orthanc));
                        } catch (java.io.IOException e) {
                            System.out.println(e.getMessage());
                        }

                    }
                }
                if (!NoRawat.getText().equals("")) {
                    if (viewer.equals("dicom")) {
                        String nm_pasien = Sequel.cariIsi("SELECT nm_pasien FROM pasien JOIN reg_periksa ON pasien.no_rkm_medis = reg_periksa.no_rkm_medis WHERE reg_periksa.no_rawat=?", NoRawat.getText());
                        url_orthanc = koneksiDB.URLORTHANC() + "/web-viewer/app/viewer.html?series=" + tbListDicom.getValueAt(tbListDicom.getSelectedRow(), 3).toString();
                        OrthancDICOM orthan = new OrthancDICOM(null, false);
                        orthan.setJudul("::[ DICOM PACS Pasien " + nm_pasien + ", Series " + tbListDicom.getValueAt(tbListDicom.getSelectedRow(), 3).toString() + " ]::", NoRawat.getText(), tbListDicom.getValueAt(tbListDicom.getSelectedRow(), 3).toString());
                        try {
                            System.out.println("URL : " + url_orthanc);
                            orthan.loadURL(url_orthanc);
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex);
                        }
                        orthan.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                        orthan.setLocationRelativeTo(internalFrame1);
                        orthan.setVisible(true);
                    }
                    if (viewer.equals("stone")) {
                        seriesbyid = orthanc.AmbilSeriesById(tbListDicom.getValueAt(tbListDicom.getSelectedRow(), 3).toString());
                        JsonNode listSeries = seriesbyid;
                        url_orthanc = koneksiDB.URLORTHANC() + "/stone-webviewer/index.html?study=" + tbListDicom.getValueAt(tbListDicom.getSelectedRow(), 4).toString() + "&series=" + listSeries.path("MainDicomTags").path("SeriesInstanceUID").asText();
                        try {
                            //Set your page url in this string. For eg, I m using URL for Google Search engine
                            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_orthanc));
                        } catch (java.io.IOException e) {
                            System.out.println(e.getMessage());
                        }

                    }
                }

                this.setCursor(Cursor.getDefaultCursor());
            } else {
                JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data..!!");
            }
        }
    }

    private void tampilOrthanc() {
        if (NoRawat.getText().equals("")) {
//        if(TabData.isVisible()==true){
            if (tbDokter.getSelectedRow() != -1) {
                if ((!Kd2.getText().equals("")) && (!Petugas.getText().equals(""))) {
//                     if(TabData.getSelectedIndex()==2){
                    try {
                        Valid.tabelKosong(tabModeDicom);
                        root = orthanc.AmbilSeries(Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat=?", tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString()), Valid.SetTgl(Tgl1.getSelectedItem() + "").replaceAll("-", ""), Valid.SetTgl(Tgl2.getSelectedItem() + "").replaceAll("-", ""));
                        for (JsonNode list : root) {
                            for (JsonNode sublist : list.path("Series")) {
                                String tgl = list.path("MainDicomTags").path("StudyDate").asText().substring(6, 8) + "-" + list.path("MainDicomTags").path("StudyDate").asText().substring(4, 6) + "-" + list.path("MainDicomTags").path("StudyDate").asText().substring(0, 4);
                                tabModeDicom.addRow(new Object[]{
                                    tgl, list.path("PatientMainDicomTags").path("PatientID").asText(), list.path("ID").asText(), sublist.asText(), list.path("MainDicomTags").path("StudyInstanceUID").asText()
                                });
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : " + e);
                    }
//                     }
                }
            }
//        }

        }

        if (!NoRawat.getText().equals("")) {
            try {
                Valid.tabelKosong(tabModeDicom);
                root = orthanc.AmbilSeries(Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat=?", NoRawat.getText()), Valid.SetTgl(Tgl1.getSelectedItem() + "").replaceAll("-", ""), Valid.SetTgl(Tgl2.getSelectedItem() + "").replaceAll("-", ""));
                for (JsonNode list : root) {
                    for (JsonNode sublist : list.path("Series")) {
                        String tgl = list.path("MainDicomTags").path("StudyDate").asText().substring(6, 8) + "-" + list.path("MainDicomTags").path("StudyDate").asText().substring(4, 6) + "-" + list.path("MainDicomTags").path("StudyDate").asText().substring(0, 4);
                        tabModeDicom.addRow(new Object[]{
                            tgl, list.path("PatientMainDicomTags").path("PatientID").asText(), list.path("ID").asText(), sublist.asText(), list.path("MainDicomTags").path("StudyInstanceUID").asText()
                        });
                    }
                }
            } catch (Exception e) {
                System.out.println("Notif : " + e);
            }
        }
    }

    public void tampilOrthanc2() {
        tampil();
        tampilOrthanc();
    }
    
    private void tampilRiwayat() {
        try {
            Valid.tabelKosong(tabModeRiwayat);
            queryriwayat = "select periksa_radiologi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,petugas.nama,periksa_radiologi.tgl_periksa,periksa_radiologi.jam,"
                    + "periksa_radiologi.dokter_perujuk,periksa_radiologi.kd_dokter,dokter.nm_dokter,diagnosa_pasien_klinis.klinis from periksa_radiologi inner join reg_periksa inner join pasien inner join petugas inner join dokter "
                    + "on periksa_radiologi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_radiologi.kd_dokter=dokter.kd_dokter and periksa_radiologi.nip=petugas.nip "
                    + "left join permintaan_radiologi on periksa_radiologi.no_rawat=permintaan_radiologi.no_rawat left join diagnosa_pasien_klinis on diagnosa_pasien_klinis.noorder=permintaan_radiologi.noorder where "
                    + "reg_periksa.no_rkm_medis='"+TNoRw.getText()+"' group by concat(periksa_radiologi.no_rawat,periksa_radiologi.tgl_periksa,periksa_radiologi.jam) "
                    + "order by periksa_radiologi.tgl_periksa desc,periksa_radiologi.jam ";
            switch (cmbHlm.getSelectedItem().toString()) {
                case "3":
                    queryriwayat += " DESC limit 3 ";
                    break;
                case "5":
                    queryriwayat += " DESC limit 5 ";
                    break;
                case "7":
                    queryriwayat += " DESC limit 7 ";
                    break;
                case "10":
                    queryriwayat += " DESC limit 10 ";
                    break;
                default:
                    break;
            }
            ps = koneksi.prepareStatement(queryriwayat);
            try {
                rs = ps.executeQuery();
                ttl = 0;
                while (rs.next()) {
                    kamar = Sequel.cariIsi("select ifnull(kd_kamar,'') from kamar_inap where no_rawat='" + rs.getString("no_rawat") + "' order by tgl_masuk desc limit 1");
                    if (!kamar.equals("")) {
                        namakamar = kamar + ", " + Sequel.cariIsi("select nm_bangsal from bangsal inner join kamar on bangsal.kd_bangsal=kamar.kd_bangsal "
                                + " where kamar.kd_kamar='" + kamar + "' ");
                        kamar = "Kamar";
                    } else if (kamar.equals("")) {
                        kamar = "Poli";
                        namakamar = Sequel.cariIsi("select nm_poli from poliklinik inner join reg_periksa on poliklinik.kd_poli=reg_periksa.kd_poli "
                                + "where reg_periksa.no_rawat='" + rs.getString("no_rawat") + "'");
                    }
                    tabModeRiwayat.addRow(new Object[]{
                        rs.getString("no_rawat"), rs.getString("no_rkm_medis") + " " + rs.getString("nm_pasien") + " (" + kamar + " : " + namakamar + ")", rs.getString("nama"),
                        rs.getString("tgl_periksa"), rs.getString("jam"), Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", rs.getString("dokter_perujuk")), rs.getString("nm_dokter"), rs.getString("klinis"),
                         Sequel.cariIsi("select perujuk from rujuk_masuk where no_rawat='" + rs.getString("no_rawat") + "'")
                    });
                    tabModeRiwayat.addRow(new Object[]{"", "", "Kode Periksa", "Nama Pemeriksaan", "Biaya Pemeriksaan", "", "", "", ""});
                    ps2 = koneksi.prepareStatement(
                            "select jns_perawatan_radiologi.kd_jenis_prw,jns_perawatan_radiologi.nm_perawatan,periksa_radiologi.biaya from periksa_radiologi inner join jns_perawatan_radiologi "
                            + "on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw where periksa_radiologi.no_rawat=? and periksa_radiologi.tgl_periksa=? "
                            + "and periksa_radiologi.jam=?");
                    try {
                        ps2.setString(1, rs.getString("no_rawat"));
                        ps2.setString(2, rs.getString("tgl_periksa"));
                        ps2.setString(3, rs.getString("jam"));
                        rs2 = ps2.executeQuery();
                        while (rs2.next()) {
                            ttl = ttl + rs2.getDouble("biaya");
                            tabModeRiwayat.addRow(new Object[]{"", "", rs2.getString("kd_jenis_prw"), rs2.getString("nm_perawatan"), Valid.SetAngka(rs2.getDouble("biaya")), "", "", "", ""});
                        }
                    } catch (Exception e) {
                        System.out.println("simrskhanza.DlgCariPeriksaRadiologi.tampil() ps2 : " + e);
                    } finally {
                        if (rs2 != null) {
                            rs2.close();
                        }
                        if (ps2 != null) {
                            ps2.close();
                        }
                    }

                    tabModeRiwayat.addRow(new Object[]{"", "", "Kode BHP", "Nama BHP", "Satuan", "Jumlah", "", "", ""});
                    ps3 = koneksi.prepareStatement(
                            "select beri_bhp_radiologi.kode_brng,ipsrsbarang.nama_brng,beri_bhp_radiologi.kode_sat,beri_bhp_radiologi.jumlah, "
                            + "beri_bhp_radiologi.total from beri_bhp_radiologi inner join ipsrsbarang on ipsrsbarang.kode_brng=beri_bhp_radiologi.kode_brng "
                            + "where beri_bhp_radiologi.no_rawat=? and beri_bhp_radiologi.tgl_periksa=? and beri_bhp_radiologi.jam=?");
                    try {
                        ps3.setString(1, rs.getString("no_rawat"));
                        ps3.setString(2, rs.getString("tgl_periksa"));
                        ps3.setString(3, rs.getString("jam"));
                        rs3 = ps3.executeQuery();
                        while (rs3.next()) {
                            tabModeRiwayat.addRow(new Object[]{"", "", rs3.getString("kode_brng"), rs3.getString("nama_brng"), rs3.getString("kode_sat"), rs3.getString("jumlah"), "", "", ""});
                        }
                    } catch (Exception e) {
                        System.out.println("simrskhanza.DlgCariPeriksaRadiologi.tampil() ps3 : " + e);
                    } finally {
                        if (rs3 != null) {
                            rs3.close();
                        }
                        if (ps3 != null) {
                            ps3.close();
                        }
                    }
                    ps5 = koneksi.prepareStatement(
                            "select hasil from hasil_radiologi where hasil_radiologi.no_rawat=? and hasil_radiologi.tgl_periksa=? and hasil_radiologi.jam=?");
                    try {
                        ps5.setString(1, rs.getString("no_rawat"));
                        ps5.setString(2, rs.getString("tgl_periksa"));
                        ps5.setString(3, rs.getString("jam"));
                        rs5 = ps5.executeQuery();
                        while (rs5.next()) {
                            tabModeRiwayat.addRow(new Object[]{"", "", "Hasil Pemeriksaan :", rs5.getString("hasil"), "", "", "", "", ""});
                        }
                    } catch (Exception e) {
                        System.out.println("simrskhanza.DlgCariPeriksaRadiologi.tampil() ps5 : " + e);
                    } finally {
                        if (rs5 != null) {
                            rs5.close();
                        }
                        if (ps5 != null) {
                            ps5.close();
                        }
                    }

                    ps6 = koneksi.prepareStatement(
                            "select judul from saran_kesan_rad where no_rawat=? and tgl_periksa=? and jam=?");
                    try {
                        ps6.setString(1, rs.getString("no_rawat"));
                        ps6.setString(2, rs.getString("tgl_periksa"));
                        ps6.setString(3, rs.getString("jam"));
                        rs6 = ps6.executeQuery();
                        while (rs6.next()) {
                            tabModeRiwayat.addRow(new Object[]{"", "", "Judul :", rs6.getString("judul"), "", "", "", "", ""});
                        }
                    } catch (Exception e) {
                        System.out.println("simrskhanza.DlgCariPeriksaRadiologi.tampil() ps6 : " + e);
                    } finally {
                        if (rs6 != null) {
                            rs6.close();
                        }
                        if (ps6 != null) {
                            ps6.close();
                        }
                    }

                    ps7 = koneksi.prepareStatement(
                            "select saran from saran_kesan_rad where no_rawat=? and tgl_periksa=? and jam=?");
                    try {
                        ps7.setString(1, rs.getString("no_rawat"));
                        ps7.setString(2, rs.getString("tgl_periksa"));
                        ps7.setString(3, rs.getString("jam"));
                        rs7 = ps7.executeQuery();
                        while (rs7.next()) {
                            tabModeRiwayat.addRow(new Object[]{"", "", "Saran :", rs7.getString("saran"), "", "", "", "", ""});
                        }
                    } catch (Exception e) {
                        System.out.println("simrskhanza.DlgCariPeriksaRadiologi.tampil() ps7 : " + e);
                    } finally {
                        if (rs7 != null) {
                            rs7.close();
                        }
                        if (ps7 != null) {
                            ps7.close();
                        }
                    }

                    ps8 = koneksi.prepareStatement(
                            "select kesan from saran_kesan_rad where no_rawat=? and tgl_periksa=? and jam=?");
                    try {
                        ps8.setString(1, rs.getString("no_rawat"));
                        ps8.setString(2, rs.getString("tgl_periksa"));
                        ps8.setString(3, rs.getString("jam"));
                        rs8 = ps8.executeQuery();
                        while (rs8.next()) {
                            tabModeRiwayat.addRow(new Object[]{"", "", "Kesan :", rs8.getString("kesan"), "", "", "", "", ""});
                        }
                    } catch (Exception e) {
                        System.out.println("simrskhanza.DlgCariPeriksaRadiologi.tampil() ps8 : " + e);
                    } finally {
                        if (rs8 != null) {
                            rs8.close();
                        }
                        if (ps8 != null) {
                            ps8.close();
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("simrskhanza.DlgCariPeriksaRadiologi.tampil() PS : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
//            if (ttl > 0) {
//                tabModeRiwayat.addRow(new Object[]{">>", "Total : " + Valid.SetAngka(ttl), "", "", "", "", "", "", ""});
//            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    private void tampilLab() {
        try {
            Valid.tabelKosong(tabModeLab);  
            ps=koneksi.prepareStatement("select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,petugas.nama,periksa_lab.tgl_periksa,periksa_lab.jam,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,dokter.nm_dokter from periksa_lab inner join reg_periksa inner join pasien inner join petugas inner join dokter "+
                    "on periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.kd_dokter=dokter.kd_dokter and periksa_lab.nip=petugas.nip where "+
                    "periksa_lab.no_rawat like '"+TNoRw2.getText()+"' group by concat(periksa_lab.no_rawat,periksa_lab.tgl_periksa,periksa_lab.jam) "+
                    "order by periksa_lab.tgl_periksa desc,periksa_lab.jam desc");
            try {
                rs=ps.executeQuery();
                ttl=0;
                while(rs.next()){
                    kamar=Sequel.cariIsi("select ifnull(kd_kamar,'') from kamar_inap where no_rawat='"+rs.getString("no_rawat")+"' order by tgl_masuk desc limit 1");
                    if(!kamar.equals("")){
                        namakamar=kamar+", "+Sequel.cariIsi("select nm_bangsal from bangsal inner join kamar on bangsal.kd_bangsal=kamar.kd_bangsal "+
                                    " where kamar.kd_kamar='"+kamar+"' ");            
                        kamar="Kamar";
                    }else if(kamar.equals("")){
                        kamar="Poli";
                        namakamar=Sequel.cariIsi("select nm_poli from poliklinik inner join reg_periksa on poliklinik.kd_poli=reg_periksa.kd_poli "+
                                    "where reg_periksa.no_rawat='"+rs.getString("no_rawat")+"'");
                    }
                    tabModeLab.addRow(new Object[]{rs.getString("nama"),rs.getString("tgl_periksa"),rs.getString("jam"),
                                                Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs.getString("dokter_perujuk")),rs.getString("nm_dokter")});
                    tabModeLab.addRow(new Object[]{"Pemeriksaan","Hasil","Satuan","Nilai Rujukan","Keterangan"});
                    ps2=koneksi.prepareStatement(
                        "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "+
                        "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
                        "and periksa_lab.jam=? order by jns_perawatan_lab.kd_jenis_prw");
                    try {
                        ps2.setString(1,rs.getString("no_rawat"));
                        ps2.setString(2,rs.getString("tgl_periksa"));
                        ps2.setString(3,rs.getString("jam"));
                        rs2=ps2.executeQuery();
                        item=0;
                        while(rs2.next()){     
                           item=item+rs2.getDouble("biaya");
                           ttl=ttl+rs2.getDouble("biaya");
                           tabMode.addRow(new Object[]{rs2.getString("kd_jenis_prw")+" "+rs2.getString("nm_perawatan")+" "+Valid.SetAngka(rs2.getDouble("biaya")),"","","",""});
                           ps3=koneksi.prepareStatement(
                                "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                "detail_periksa_lab.keterangan,detail_periksa_lab.kd_jenis_prw from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=? order by template_laboratorium.urut");
                           try {
                                ps3.setString(1,rs.getString("no_rawat"));
                                ps3.setString(2,rs2.getString("kd_jenis_prw"));
                                ps3.setString(3,rs.getString("tgl_periksa"));
                                ps3.setString(4,rs.getString("jam"));
                                rs3=ps3.executeQuery();
                                while(rs3.next()){
                                    item=item+rs3.getDouble("biaya_item");
                                    ttl=ttl+rs3.getDouble("biaya_item");
                                    tabModeLab.addRow(new Object[]{"  "+rs3.getString("Pemeriksaan")+" "+Valid.SetAngka(rs3.getDouble("biaya_item")),rs3.getString("nilai"),
                                                                rs3.getString("satuan"),rs3.getString("nilai_rujukan"),rs3.getString("keterangan")});
                                }
                           } catch (Exception e) {
                               System.out.println("Notif ps3 : "+e);
                           } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                                if(ps3!=null){
                                    ps3.close();
                                }
                           }                                
                        }
                    } catch (Exception e) {
                        System.out.println("Notif ps2 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }                        

                    saran="";kesan="";
                    ps5=koneksi.prepareStatement(
                        "select saran,kesan from saran_kesan_lab where no_rawat=? and tgl_periksa=? and jam=?");  
                    try {
                        ps5.setString(1,rs.getString("no_rawat"));
                        ps5.setString(2,rs.getString("tgl_periksa"));
                        ps5.setString(3,rs.getString("jam"));
                        rs5=ps5.executeQuery();
                        if(rs5.next()){      
                            kesan=rs5.getString("kesan");saran=rs5.getString("saran");
                        } 
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rs5!=null){
                            rs5.close();
                        }
                        if(ps5!=null){
                            ps5.close();
                        }
                    }   
                    if(item>0){
                        tabModeLab.addRow(new Object[]{"Biaya Periksa : "+Valid.SetAngka(item),"","","Kesan : "+kesan,"Saran : "+saran});
                    }
                }
            } catch (Exception e) {
                System.out.println("Notif ps : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            } 
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    private void isUpdate() {
        Sequel.queryu2("update hasil_radiologi set hasil=? where no_rawat=? and tgl_periksa=? and jam=?", 4, new String[]{
            HasilPeriksa.getText(), tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString(),
            tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString(), tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString()
        });
        Sequel.queryu2("update saran_kesan_rad set judul=?, saran=?, kesan=? where no_rawat=? and tgl_periksa=? and jam=?", 6, new String[]{
            TJudul.getText(), TSaran.getText(), TKesan.getText(), tbDokter.getValueAt(tbDokter.getSelectedRow(), 0).toString(),
            tbDokter.getValueAt(tbDokter.getSelectedRow(), 3).toString(), tbDokter.getValueAt(tbDokter.getSelectedRow(), 4).toString()
        });
        Sequel.queryu2("update diagnosa_pasien_klinis set klinis=? where noorder=?", 2, new String[]{
            TKlinis.getText(), NoOrder.getText()
        });
    }
    
    private void isLog(String action) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String requestJson = "";
        try {
            ps = koneksi.prepareStatement("SELECT judul, saran, kesan, hasil FROM saran_kesan_rad INNER JOIN hasil_radiologi ON saran_kesan_rad.no_rawat=hasil_radiologi.no_rawat "
                    + "WHERE hasil_radiologi.no_rawat='" + TNoRw2.getText() + "' AND hasil_radiologi.tgl_periksa='" + tglPeriksa.getText() + "' AND hasil_radiologi.jam='" + jamPeriksa.getText() + "'");
            ps2 = koneksi.prepareStatement("SELECT klinis FROM diagnosa_pasien_klinis WHERE noorder='" + NoOrder.getText() + "'");
            rs = ps.executeQuery();
            if (rs.next()) {
                JsonObject dataJson = new JsonObject();
                dataJson.addProperty("no_rawat", TNoRw2.getText());
                dataJson.addProperty("tgl_periksa", tglPeriksa.getText());
                dataJson.addProperty("jam", jamPeriksa.getText());
                dataJson.addProperty("judul", rs.getString("judul"));
                dataJson.addProperty("saran", rs.getString("saran"));
                dataJson.addProperty("kesan", rs.getString("kesan"));
                dataJson.addProperty("hasil", rs.getString("hasil"));

                if (ps2 != null) {
                    ResultSet rs2 = ps2.executeQuery();
                    if (rs2.next()) {
                        dataJson.addProperty("klinis", rs2.getString("klinis"));
                    }
                    rs2.close();
                }

                JsonObject requestObject = new JsonObject();
                requestObject.add("data", dataJson);
                requestObject.addProperty("action", action);
                requestJson = requestObject.toString();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi Log Rad 1 : " + e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (ps2 != null) {
                    ps2.close();
                }
            } catch (Exception ex) {
                System.out.println("Notifikasi Log Rad 2: " + ex);
            }
        }
        Sequel.menyimpan("mlite_log", "null,'" + var.getkode() + "','hasil_radiologi','" + requestJson + "','" + dtf.format(now) + "'");
    }
}
