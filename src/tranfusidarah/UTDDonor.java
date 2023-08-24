/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * DlgPenyakit.java
 *
 * Created on May 23, 2010, 12:57:16 AM
 */
package tranfusidarah;

import bridging.BPJSSPRI;
import bridging.BridgingWA;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.Year;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import static java.time.temporal.ChronoUnit.DAYS;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;
import simrskhanza.DlgKabupaten;
import simrskhanza.DlgKecamatan;
import simrskhanza.DlgKelurahan;
import simrskhanza.DlgPropinsi;

/**
 *
 * @author dosen
 */
public final class UTDDonor extends javax.swing.JDialog {

    private final DefaultTableModel tabModeMedis, tabModeNonMedis, tabModeTranfusi, tabModeTotalDonor, tabModePendonor;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps, ps2, pstranfusi, pscekmedis, psceknonmedis, psTotal, psTotalLk, psTotalPr, psUmur1, psUmur2, psUmur3, psUmur4, psUmur5,
            psOpos, psOneg, psApos, psAneg, psBpos, psBneg, psABpos, psABneg, ps3, psjenisdb, psjenisdp, psjenisds;
    private ResultSet rs, rs2, rstranfusi, rsTotal, rsTotalLk, rsTotalPr, rsUmur1, rsUmur2, rsUmur3, rsUmur4, rsUmur5,
            rsOpos, rsOneg, rsApos, rsAneg, rsBpos, rsBneg, rsABpos, rsABneg, rs3, rsjenisdb, rsjenisdp, rsjenisds;
    private Connection koneksi = koneksiDB.condb();
    private DlgCariPetugas petugas = new DlgCariPetugas(null, false);
    private int jml = 0, i = 0, row = 0, index = 0, pilih = 0, inthari = 0;
    private String[] kodebarang, namabarang, jumlah, satuan, stokasal, hbeli, total, printTotal;
    private String totaldonor, totallk, totalpr, umur1, umur2, umur3, umur4, umur5,
            opos, oneg, apos, aneg, bpos, bneg, abpos, abneg, jenisdb, jenisdp, jenisds;
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private String aktifkan = "",
            sqlpscekmedis = "select utd_penggunaan_medis_donor.kode_brng,databarang.nama_brng,utd_penggunaan_medis_donor.jml,utd_penggunaan_medis_donor.harga,"
            + "utd_penggunaan_medis_donor.total,databarang.kode_sat from utd_penggunaan_medis_donor inner join databarang "
            + "on utd_penggunaan_medis_donor.kode_brng=databarang.kode_brng where utd_penggunaan_medis_donor.no_donor=?",
            sqlpsceknonmedis = "select utd_penggunaan_penunjang_donor.kode_brng,ipsrsbarang.nama_brng,utd_penggunaan_penunjang_donor.jml,utd_penggunaan_penunjang_donor.harga,"
            + "utd_penggunaan_penunjang_donor.total,ipsrsbarang.kode_sat from utd_penggunaan_penunjang_donor inner join ipsrsbarang "
            + "on utd_penggunaan_penunjang_donor.kode_brng=ipsrsbarang.kode_brng where utd_penggunaan_penunjang_donor.no_donor=?",
            hari = "", kdkel = "", kdkec = "", kdkab = "", kdprop = "", nodonor = "", dinas = "",umur="",
            sqltotaldonor = "select COUNT(utd_pendonor.no_ktp) as jumlah from utd_donor, utd_pendonor where utd_donor.no_pendonor=utd_pendonor.no_pendonor ";
    private BridgingWA kirimwa = new BridgingWA();
    public DlgKabupaten kab = new DlgKabupaten(null, false);
    public DlgPropinsi prop = new DlgPropinsi(null, false);
    public DlgKecamatan kec = new DlgKecamatan(null, false);
    public DlgKelurahan kel = new DlgKelurahan(null, false);
//    private int hari;

    /**
     * Creates new form DlgPenyakit
     *
     * @param parent
     * @param modal
     */
    public UTDDonor(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10, 2);
        setSize(628, 674);

        tabModeMedis = new DefaultTableModel(null, new Object[]{"Jml", "Kode Barang", "Nama Barang", "Harga", "Subtotal", "Satuan", "Stok"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
        };
        tbMedis.setModel(tabModeMedis);
        tbMedis.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbMedis.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbMedis.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(35);
            } else if (i == 1) {
                column.setPreferredWidth(80);
            } else if (i == 2) {
                column.setPreferredWidth(200);
            } else if (i == 3) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 4) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 5) {
                column.setPreferredWidth(50);
            } else if (i == 6) {
                column.setPreferredWidth(40);
            }
        }
        tbMedis.setDefaultRenderer(Object.class, new WarnaTable());
        //non medis
        tabModeNonMedis = new DefaultTableModel(null, new Object[]{"Jml", "Kode Barang", "Nama Barang", "Harga", "Subtotal", "Satuan", "Stok"}) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
        };
        tbNonMedis.setModel(tabModeNonMedis);
        tbNonMedis.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbNonMedis.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbNonMedis.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(35);
            } else if (i == 1) {
                column.setPreferredWidth(80);
            } else if (i == 2) {
                column.setPreferredWidth(200);
            } else if (i == 3) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 4) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 5) {
                column.setPreferredWidth(50);
            } else if (i == 6) {
                column.setPreferredWidth(40);
            }
        }
        tbNonMedis.setDefaultRenderer(Object.class, new WarnaTable());

        tabModeTranfusi = new DefaultTableModel(null, new Object[]{
            "Nomor Donor", "No. Pendonor", "Nama Pendonor", "NIK", "Tanggal", "Dinas", "J.K.", "Tgl.Lahir", "Umur", "Alamat", "G.D.", "Resus", "Tensi",
            "No. Kantong", "No.Telp", "J.B.", "J.D.", "Pengambilan", "Tempat Aftap", "Petugas Aftap", "HBSAg", "HCV", "HIV",
            "Spilis", "Malaria", "Petugas U.Saring"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbTranfusiDarah.setModel(tabModeTranfusi);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbTranfusiDarah.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbTranfusiDarah.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 26; i++) {
            TableColumn column = tbTranfusiDarah.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(93);
            } else if (i == 1) {
                column.setPreferredWidth(93);
            } else if (i == 2) {
                column.setPreferredWidth(150);
            } else if (i == 3) {
                column.setPreferredWidth(150);
            } else if (i == 4) {
                column.setPreferredWidth(70);
            } else if (i == 5) {
                column.setPreferredWidth(40);
            } else if (i == 6) {
                column.setPreferredWidth(30);
            } else if (i == 7) {
                column.setPreferredWidth(70);
            } else if (i == 8) {
                column.setPreferredWidth(37);
//                column.setMinWidth(0);
//                column.setMaxWidth(0);
            } else if (i == 9) {
                column.setPreferredWidth(210);
            } else if (i == 10) {
                column.setPreferredWidth(30);
            } else if (i == 11) {
                column.setPreferredWidth(40);
            } else if (i == 12) {
                column.setPreferredWidth(46);
            } else if (i == 13) {
                column.setPreferredWidth(80);
            } else if (i == 14) {
                column.setPreferredWidth(80);
            } else if (i == 15) {
                column.setPreferredWidth(27);
            } else if (i == 16) {
                column.setPreferredWidth(27);
            } else if (i == 17) {
                column.setPreferredWidth(88);
            } else if (i == 18) {
                column.setPreferredWidth(88);
            } else if (i == 19) {
                column.setPreferredWidth(140);
            } else if (i == 20) {
                column.setPreferredWidth(53);
            } else if (i == 21) {
                column.setPreferredWidth(53);
            } else if (i == 22) {
                column.setPreferredWidth(53);
            } else if (i == 23) {
                column.setPreferredWidth(53);
            } else if (i == 24) {
                column.setPreferredWidth(53);
            } else if (i == 25) {
                column.setPreferredWidth(140);
            }
        }
        tbTranfusiDarah.setDefaultRenderer(Object.class, new WarnaTable());

        //Total Donor
        tabModeTotalDonor = new DefaultTableModel(null, new Object[]{
            "Total Donasi Darah", "Donasi Darah (lk)", "Donasi Darah (pr)", "DB", "DP", "DS", "17 Tahun", "18-24 Tahun",
            "25-44 Tahun", "45-64 Tahun", ">= 65 Tahun",
            "O resus (+)", "O resus (-)", "A resus (+)", "A resus (-)", "B resus (+)", "B resus (-)",
            "AB resus (+)", "AB resus (-)"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbTotalDonor.setModel(tabModeTotalDonor);
        tbTotalDonor.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbTotalDonor.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 17; i++) {
            TableColumn column = tbTotalDonor.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(100);
            } else if (i == 1) {
                column.setPreferredWidth(100);
            } else if (i == 2) {
                column.setPreferredWidth(100);
            } else if (i == 3) {
                column.setPreferredWidth(45);
            } else if (i == 4) {
                column.setPreferredWidth(45);
            } else if (i == 5) {
                column.setPreferredWidth(45);
            } else {
                column.setPreferredWidth(70);
            }
        }
        tbTotalDonor.setDefaultRenderer(Object.class, new WarnaTable());

        tabModePendonor = new DefaultTableModel(null, new Object[]{
            "No. Pendonor", "Nama", "NIK", "J.K.", "G.D", "Resus", "Tempat Lahir",
            "Tgl. Lahir", "No. Telp", "Alamat", "Kelurahan", "Kecamatan", "Kabupaten", "Provinsi"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbData.setModel(tabModePendonor);
        tbData.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 14; i++) {
            TableColumn column = tbData.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(85);
            } else if (i == 1) {
                column.setPreferredWidth(150);
            } else if (i == 2) {
                column.setPreferredWidth(110);
            } else if (i == 3) {
                column.setPreferredWidth(35);
            } else if (i == 4) {
                column.setPreferredWidth(35);
            } else if (i == 5) {
                column.setPreferredWidth(45);
            } else if (i == 6) {
                column.setPreferredWidth(75);
            } else if (i == 7) {
                column.setPreferredWidth(75);
            } else if (i == 8) {
                column.setPreferredWidth(110);
            } else if (i == 9) {
                column.setPreferredWidth(110);
            } else {
                column.setPreferredWidth(100);
            }
        }
        tbData.setDefaultRenderer(Object.class, new WarnaTable());

        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (petugas.getTable().getSelectedRow() != -1) {
                    if (pilih == 1) {
                        KodePetugasAftap.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                        NamaPetugasAftap.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                        KodePetugasAftap.requestFocus();
                    } else if (pilih == 2) {
                        KodePetugasUSaring.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 0).toString());
                        NamaPetugasUSaring.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(), 1).toString());
                        KodePetugasUSaring.requestFocus();
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

        prop.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("UTDDonor")) {
                    if (prop.getTable().getSelectedRow() != -1) {
                        Propinsi.setText(prop.getTable().getValueAt(prop.getTable().getSelectedRow(), 0).toString());
                        kdprop = prop.getTable().getValueAt(prop.getTable().getSelectedRow(), 1).toString();
                        Propinsi.requestFocus();
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

        kab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("UTDDonor")) {
                    if (kab.getTable().getSelectedRow() != -1) {
                        Kabupaten.setText(kab.getTable().getValueAt(kab.getTable().getSelectedRow(), 0).toString());
                        kdkab = kab.getTable().getValueAt(kab.getTable().getSelectedRow(), 1).toString();
                        Kabupaten.requestFocus();
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

        kec.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("UTDDonor")) {
                    if (kec.getTable().getSelectedRow() != -1) {
                        Kecamatan.setText(kec.getTable().getValueAt(kec.getTable().getSelectedRow(), 0).toString());
                        kdkec = kec.getTable().getValueAt(kec.getTable().getSelectedRow(), 1).toString();
                        Kecamatan.requestFocus();
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

        kel.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("UTDDonor")) {
                    if (kel.getTable().getSelectedRow() != -1) {
                        Kelurahan.setText(kel.getTable().getValueAt(kel.getTable().getSelectedRow(), 0).toString());
                        kdkel = kel.getTable().getValueAt(kel.getTable().getSelectedRow(), 1).toString();
                        Kelurahan.requestFocus();
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

        TCari.setDocument(new batasInput((byte) 100).getKata(TCari));
        TCariMedis.setDocument(new batasInput((byte) 100).getKata(TCariMedis));
        TCariNonMedis.setDocument(new batasInput((byte) 100).getKata(TCariNonMedis));

        NomorDonor.setDocument(new batasInput((byte) 15).getKata(NomorDonor));
        NamaPendonor.setDocument(new batasInput((byte) 60).getKata(NamaPendonor));
        Umur.setDocument(new batasInput((byte) 4).getOnlyAngka(Umur));
        Alamat.setDocument(new batasInput((byte) 100).getKata(Alamat));
        Tensi.setDocument(new batasInput((byte) 7).getKata(Tensi));
//        NomorBag.setDocument(new batasInput((byte)8).getOnlyAngka(NomorBag));
        nik.setDocument(new batasInput((byte) 20).getOnlyAngka(nik));
        NomorTelp.setDocument(new batasInput((byte) 13).getOnlyAngka(NomorTelp));
        KodePetugasAftap.setDocument(new batasInput((byte) 20).getKata(KodePetugasAftap));
        KodePetugasUSaring.setDocument(new batasInput((byte) 20).getKata(KodePetugasUSaring));
        NamaPendonor1.setDocument(new batasInput((byte) 60).getKata(NamaPendonor1));
        nik1.setDocument(new batasInput((byte) 20).getOnlyAngka(nik1));
        NomorTelp1.setDocument(new batasInput((byte) 13).getOnlyAngka(NomorTelp1));
        tempatlahir.setDocument(new batasInput((byte) 15).getKata(tempatlahir));
        Alamat1.setDocument(new batasInput((byte) 100).getKata(Alamat1));
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
            TCariMedis.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCariMedis.getText().length() > 2) {
                        tampilMedis();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCariMedis.getText().length() > 2) {
                        tampilMedis();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCariMedis.getText().length() > 2) {
                        tampilMedis();
                    }
                }
            });
            TCariNonMedis.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (TCariNonMedis.getText().length() > 2) {
                        tampilNonMedis();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    if (TCariNonMedis.getText().length() > 2) {
                        tampilNonMedis();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    if (TCariNonMedis.getText().length() > 2) {
                        tampilNonMedis();
                    }
                }
            });
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        NomorPendonor1 = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppBaru = new javax.swing.JMenuItem();
        ppUbah = new javax.swing.JMenuItem();
        ppSaring = new javax.swing.JMenuItem();
        ppTampilkanBHPMedis = new javax.swing.JMenuItem();
        ppTampilkanBHPPenunjang = new javax.swing.JMenuItem();
        ppTampilkanBHPPenunjangDanMedis = new javax.swing.JMenuItem();
        ppHapusBHPMedis = new javax.swing.JMenuItem();
        ppHapusBHPNonMedis = new javax.swing.JMenuItem();
        ppHapusBHPMedisDanNonMedis = new javax.swing.JMenuItem();
        ppCekal = new javax.swing.JMenuItem();
        printWB = new javax.swing.JMenuItem();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        MnDaftarBaru = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame5 = new widget.InternalFrame();
        Scroll5 = new widget.ScrollPane();
        tbData = new widget.Table();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        panelGlass12 = new widget.panelisi();
        NomorPendonor = new widget.TextBox();
        label43 = new widget.Label();
        NamaPendonor1 = new widget.TextBox();
        Gol = new widget.Label();
        GolDarah = new widget.ComboBox();
        label47 = new widget.Label();
        Alamat1 = new widget.TextBox();
        jLabel23 = new widget.Label();
        label48 = new widget.Label();
        label49 = new widget.Label();
        NomorTelp1 = new widget.TextBox();
        tgl_lahir1 = new widget.Tanggal();
        tempatlahir = new widget.TextBox();
        label51 = new widget.Label();
        Resus1 = new widget.ComboBox();
        Gol1 = new widget.Label();
        nik1 = new widget.TextBox();
        label52 = new widget.Label();
        Kelurahan = new widget.TextBox();
        BtnKelurahan = new widget.Button();
        Kecamatan = new widget.TextBox();
        BtnKecamatan = new widget.Button();
        BtnKabupaten = new widget.Button();
        Kabupaten = new widget.TextBox();
        BtnPropinsi = new widget.Button();
        Propinsi = new widget.TextBox();
        JK1 = new widget.ComboBox();
        internalFrame2 = new widget.InternalFrame();
        panelisi5 = new widget.panelisi();
        jPanel3 = new javax.swing.JPanel();
        panelisi6 = new widget.panelisi();
        label10 = new widget.Label();
        TCariMedis = new widget.TextBox();
        BtnCari2 = new widget.Button();
        BtnAll1 = new widget.Button();
        Scroll1 = new widget.ScrollPane();
        tbMedis = new widget.Table();
        jPanel4 = new javax.swing.JPanel();
        panelisi7 = new widget.panelisi();
        label11 = new widget.Label();
        TCariNonMedis = new widget.TextBox();
        BtnCari3 = new widget.Button();
        BtnAll2 = new widget.Button();
        Scroll2 = new widget.ScrollPane();
        tbNonMedis = new widget.Table();
        scrollPane1 = new widget.ScrollPane();
        panelisi4 = new widget.panelisi();
        label34 = new widget.Label();
        NomorDonor = new widget.TextBox();
        label36 = new widget.Label();
        NamaPendonor = new widget.TextBox();
        label32 = new widget.Label();
        Tanggal = new widget.Tanggal();
        jLabel8 = new widget.Label();
        Dinas = new widget.ComboBox();
        label35 = new widget.Label();
        Umur = new widget.TextBox();
        label37 = new widget.Label();
        label38 = new widget.Label();
        Alamat = new widget.TextBox();
        jLabel9 = new widget.Label();
        JK = new widget.ComboBox();
        jLabel10 = new widget.Label();
        GolonganDarah = new widget.ComboBox();
        jLabel11 = new widget.Label();
        Resus = new widget.ComboBox();
        label39 = new widget.Label();
        Tensi = new widget.TextBox();
        label40 = new widget.Label();
        NomorBag = new widget.TextBox();
        label41 = new widget.Label();
        NomorTelp = new widget.TextBox();
        jLabel12 = new widget.Label();
        JenisBag = new widget.ComboBox();
        jLabel13 = new widget.Label();
        JenisDonor = new widget.ComboBox();
        jLabel14 = new widget.Label();
        TempatAftap = new widget.ComboBox();
        label17 = new widget.Label();
        KodePetugasAftap = new widget.TextBox();
        NamaPetugasAftap = new widget.TextBox();
        btnPetugasAftap = new widget.Button();
        label18 = new widget.Label();
        jLabel15 = new widget.Label();
        HBSAg = new widget.ComboBox();
        HCV = new widget.ComboBox();
        jLabel16 = new widget.Label();
        jLabel17 = new widget.Label();
        HIV = new widget.ComboBox();
        jLabel18 = new widget.Label();
        Spilis = new widget.ComboBox();
        label19 = new widget.Label();
        KodePetugasUSaring = new widget.TextBox();
        NamaPetugasUSaring = new widget.TextBox();
        btnPetugasUSaring = new widget.Button();
        jLabel19 = new widget.Label();
        Malaria = new widget.ComboBox();
        label33 = new widget.Label();
        tgl_lahir = new widget.Tanggal();
        nik = new widget.TextBox();
        label42 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        Pengambilan = new widget.ComboBox();
        jLabel22 = new widget.Label();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbTranfusiDarah = new widget.Table();
        internalFrame4 = new widget.InternalFrame();
        Scroll3 = new widget.ScrollPane();
        tbTotalDonor = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        Ganti = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnEdit1 = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        BtnWA = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel20 = new widget.Label();
        TanggalCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        TanggalCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll3 = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();

        NomorPendonor1.setHighlighter(null);
        NomorPendonor1.setName("NomorPendonor1"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppBaru.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBaru.setForeground(new java.awt.Color(70, 70, 70));
        ppBaru.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Add patient.png"))); // NOI18N
        ppBaru.setText("Daftar Baru");
        ppBaru.setName("ppBaru"); // NOI18N
        ppBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBaruActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppBaru);

        ppUbah.setBackground(new java.awt.Color(255, 255, 254));
        ppUbah.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppUbah.setForeground(new java.awt.Color(70, 70, 70));
        ppUbah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppUbah.setText("Input Aftap");
        ppUbah.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppUbah.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppUbah.setIconTextGap(8);
        ppUbah.setName("ppUbah"); // NOI18N
        ppUbah.setPreferredSize(new java.awt.Dimension(280, 25));
        ppUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppUbahActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppUbah);

        ppSaring.setBackground(new java.awt.Color(255, 255, 254));
        ppSaring.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSaring.setForeground(new java.awt.Color(70, 70, 70));
        ppSaring.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSaring.setText("Input Uji Saring");
        ppSaring.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSaring.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSaring.setIconTextGap(8);
        ppSaring.setName("ppSaring"); // NOI18N
        ppSaring.setPreferredSize(new java.awt.Dimension(280, 25));
        ppSaring.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSaringActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppSaring);

        ppTampilkanBHPMedis.setBackground(new java.awt.Color(255, 255, 254));
        ppTampilkanBHPMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTampilkanBHPMedis.setForeground(new java.awt.Color(70, 70, 70));
        ppTampilkanBHPMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTampilkanBHPMedis.setText("Tampilkan Penggunaan BHP Medis");
        ppTampilkanBHPMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTampilkanBHPMedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTampilkanBHPMedis.setIconTextGap(8);
        ppTampilkanBHPMedis.setName("ppTampilkanBHPMedis"); // NOI18N
        ppTampilkanBHPMedis.setPreferredSize(new java.awt.Dimension(280, 25));
        ppTampilkanBHPMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTampilkanBHPMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTampilkanBHPMedis);

        ppTampilkanBHPPenunjang.setBackground(new java.awt.Color(255, 255, 254));
        ppTampilkanBHPPenunjang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTampilkanBHPPenunjang.setForeground(new java.awt.Color(70, 70, 70));
        ppTampilkanBHPPenunjang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTampilkanBHPPenunjang.setText("Tampilkan Penggunaan BHP Non Medis");
        ppTampilkanBHPPenunjang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTampilkanBHPPenunjang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTampilkanBHPPenunjang.setIconTextGap(8);
        ppTampilkanBHPPenunjang.setName("ppTampilkanBHPPenunjang"); // NOI18N
        ppTampilkanBHPPenunjang.setPreferredSize(new java.awt.Dimension(280, 25));
        ppTampilkanBHPPenunjang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTampilkanBHPPenunjangActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTampilkanBHPPenunjang);

        ppTampilkanBHPPenunjangDanMedis.setBackground(new java.awt.Color(255, 255, 254));
        ppTampilkanBHPPenunjangDanMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTampilkanBHPPenunjangDanMedis.setForeground(new java.awt.Color(70, 70, 70));
        ppTampilkanBHPPenunjangDanMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTampilkanBHPPenunjangDanMedis.setText("Tampilkan Penggunaan BHP Medis & Non Medis");
        ppTampilkanBHPPenunjangDanMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTampilkanBHPPenunjangDanMedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTampilkanBHPPenunjangDanMedis.setIconTextGap(8);
        ppTampilkanBHPPenunjangDanMedis.setName("ppTampilkanBHPPenunjangDanMedis"); // NOI18N
        ppTampilkanBHPPenunjangDanMedis.setPreferredSize(new java.awt.Dimension(280, 25));
        ppTampilkanBHPPenunjangDanMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTampilkanBHPPenunjangDanMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTampilkanBHPPenunjangDanMedis);

        ppHapusBHPMedis.setBackground(new java.awt.Color(255, 255, 254));
        ppHapusBHPMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapusBHPMedis.setForeground(new java.awt.Color(70, 70, 70));
        ppHapusBHPMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppHapusBHPMedis.setText("Hapus Penggunaan BHP Medis");
        ppHapusBHPMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapusBHPMedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapusBHPMedis.setIconTextGap(8);
        ppHapusBHPMedis.setName("ppHapusBHPMedis"); // NOI18N
        ppHapusBHPMedis.setPreferredSize(new java.awt.Dimension(280, 25));
        ppHapusBHPMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHapusBHPMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppHapusBHPMedis);

        ppHapusBHPNonMedis.setBackground(new java.awt.Color(255, 255, 254));
        ppHapusBHPNonMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapusBHPNonMedis.setForeground(new java.awt.Color(70, 70, 70));
        ppHapusBHPNonMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppHapusBHPNonMedis.setText("Hapus Penggunaan BHP Non Medis");
        ppHapusBHPNonMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapusBHPNonMedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapusBHPNonMedis.setIconTextGap(8);
        ppHapusBHPNonMedis.setName("ppHapusBHPNonMedis"); // NOI18N
        ppHapusBHPNonMedis.setPreferredSize(new java.awt.Dimension(280, 25));
        ppHapusBHPNonMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHapusBHPNonMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppHapusBHPNonMedis);

        ppHapusBHPMedisDanNonMedis.setBackground(new java.awt.Color(255, 255, 254));
        ppHapusBHPMedisDanNonMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapusBHPMedisDanNonMedis.setForeground(new java.awt.Color(70, 70, 70));
        ppHapusBHPMedisDanNonMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppHapusBHPMedisDanNonMedis.setText("Hapus Penggunaan BHP Medis & Non Medis");
        ppHapusBHPMedisDanNonMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapusBHPMedisDanNonMedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapusBHPMedisDanNonMedis.setIconTextGap(8);
        ppHapusBHPMedisDanNonMedis.setName("ppHapusBHPMedisDanNonMedis"); // NOI18N
        ppHapusBHPMedisDanNonMedis.setPreferredSize(new java.awt.Dimension(280, 25));
        ppHapusBHPMedisDanNonMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHapusBHPMedisDanNonMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppHapusBHPMedisDanNonMedis);

        ppCekal.setBackground(new java.awt.Color(255, 255, 254));
        ppCekal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppCekal.setForeground(new java.awt.Color(70, 70, 70));
        ppCekal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppCekal.setText("Cekal Darah");
        ppCekal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppCekal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppCekal.setIconTextGap(8);
        ppCekal.setName("ppCekal"); // NOI18N
        ppCekal.setPreferredSize(new java.awt.Dimension(280, 25));
        ppCekal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppCekalActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppCekal);

        printWB.setBackground(new java.awt.Color(255, 255, 254));
        printWB.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        printWB.setForeground(new java.awt.Color(70, 70, 70));
        printWB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        printWB.setText("printWB");
        printWB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        printWB.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        printWB.setIconTextGap(8);
        printWB.setName("printWB"); // NOI18N
        printWB.setPreferredSize(new java.awt.Dimension(280, 25));
        printWB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printWBActionPerformed(evt);
            }
        });
        jPopupMenu1.add(printWB);

        jPopupMenu2.setName("jPopupMenu2"); // NOI18N

        MnDaftarBaru.setBackground(new java.awt.Color(255, 255, 254));
        MnDaftarBaru.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDaftarBaru.setForeground(new java.awt.Color(70, 70, 70));
        MnDaftarBaru.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDaftarBaru.setText("Daftar Baru");
        MnDaftarBaru.setName("MnDaftarBaru"); // NOI18N
        MnDaftarBaru.setPreferredSize(new java.awt.Dimension(200, 28));
        MnDaftarBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDaftarBaruActionPerformed(evt);
            }
        });
        jPopupMenu2.add(MnDaftarBaru);

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Donor Darah ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(70, 70, 70));
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame5.setBorder(null);
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout());

        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);

        tbData.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbData.setName("tbData"); // NOI18N
        tbData.setComponentPopupMenu(jPopupMenu2);
        tbData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbDataMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataMouseClicked(evt);
            }
        });
        tbData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDataKeyPressed(evt);
            }
        });
        Scroll5.setViewportView(tbData);

        internalFrame5.add(Scroll5, java.awt.BorderLayout.CENTER);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 160));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
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

        panelGlass12.setName("panelGlass12"); // NOI18N
        panelGlass12.setPreferredSize(new java.awt.Dimension(104, 304));
        panelGlass12.setLayout(null);

        NomorPendonor.setEditable(false);
        NomorPendonor.setHighlighter(null);
        NomorPendonor.setName("NomorPendonor"); // NOI18N
        NomorPendonor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NomorPendonorActionPerformed(evt);
            }
        });
        panelGlass12.add(NomorPendonor);
        NomorPendonor.setBounds(105, 10, 160, 23);

        label43.setText("Nama Pendonor :");
        label43.setName("label43"); // NOI18N
        label43.setPreferredSize(new java.awt.Dimension(35, 23));
        panelGlass12.add(label43);
        label43.setBounds(0, 40, 102, 23);

        NamaPendonor1.setHighlighter(null);
        NamaPendonor1.setName("NamaPendonor1"); // NOI18N
        NamaPendonor1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaPendonor1KeyPressed(evt);
            }
        });
        panelGlass12.add(NamaPendonor1);
        NamaPendonor1.setBounds(105, 40, 270, 23);

        Gol.setText("Gol. Darah :");
        Gol.setName("Gol"); // NOI18N
        panelGlass12.add(Gol);
        Gol.setBounds(280, 100, 70, 23);

        GolDarah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A", "AB", "B", "O" }));
        GolDarah.setName("GolDarah"); // NOI18N
        GolDarah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GolDarahKeyPressed(evt);
            }
        });
        panelGlass12.add(GolDarah);
        GolDarah.setBounds(355, 100, 70, 23);

        label47.setText("Alamat :");
        label47.setName("label47"); // NOI18N
        label47.setPreferredSize(new java.awt.Dimension(35, 23));
        panelGlass12.add(label47);
        label47.setBounds(540, 40, 102, 23);

        Alamat1.setHighlighter(null);
        Alamat1.setName("Alamat1"); // NOI18N
        panelGlass12.add(Alamat1);
        Alamat1.setBounds(650, 40, 370, 23);

        jLabel23.setText("J.K. :");
        jLabel23.setName("jLabel23"); // NOI18N
        panelGlass12.add(jLabel23);
        jLabel23.setBounds(375, 70, 40, 23);

        label48.setText("Nomor Pendonor :");
        label48.setName("label48"); // NOI18N
        label48.setPreferredSize(new java.awt.Dimension(35, 23));
        panelGlass12.add(label48);
        label48.setBounds(0, 10, 102, 23);

        label49.setText("No.Telp :");
        label49.setName("label49"); // NOI18N
        label49.setPreferredSize(new java.awt.Dimension(35, 23));
        panelGlass12.add(label49);
        label49.setBounds(50, 100, 50, 23);

        NomorTelp1.setHighlighter(null);
        NomorTelp1.setName("NomorTelp1"); // NOI18N
        NomorTelp1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NomorTelp1KeyPressed(evt);
            }
        });
        panelGlass12.add(NomorTelp1);
        NomorTelp1.setBounds(105, 100, 160, 23);

        tgl_lahir1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "22-08-2023" }));
        tgl_lahir1.setDisplayFormat("dd-MM-yyyy");
        tgl_lahir1.setName("tgl_lahir1"); // NOI18N
        tgl_lahir1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                tgl_lahir1ItemStateChanged(evt);
            }
        });
        panelGlass12.add(tgl_lahir1);
        tgl_lahir1.setBounds(270, 70, 100, 23);

        tempatlahir.setHighlighter(null);
        tempatlahir.setName("tempatlahir"); // NOI18N
        tempatlahir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tempatlahirActionPerformed(evt);
            }
        });
        panelGlass12.add(tempatlahir);
        tempatlahir.setBounds(105, 70, 160, 23);

        label51.setText("NIK : ");
        label51.setName("label51"); // NOI18N
        label51.setPreferredSize(new java.awt.Dimension(33, 23));
        panelGlass12.add(label51);
        label51.setBounds(375, 40, 40, 23);

        Resus1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "(-)", "(+)" }));
        Resus1.setName("Resus1"); // NOI18N
        Resus1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Resus1KeyPressed(evt);
            }
        });
        panelGlass12.add(Resus1);
        Resus1.setBounds(490, 100, 70, 23);

        Gol1.setText("Resus :");
        Gol1.setName("Gol1"); // NOI18N
        panelGlass12.add(Gol1);
        Gol1.setBounds(415, 100, 70, 23);

        nik1.setHighlighter(null);
        nik1.setName("nik1"); // NOI18N
        nik1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nik1ActionPerformed(evt);
            }
        });
        panelGlass12.add(nik1);
        nik1.setBounds(420, 40, 140, 23);

        label52.setText("Tmp / Tgl. Lahir :");
        label52.setName("label52"); // NOI18N
        label52.setPreferredSize(new java.awt.Dimension(35, 23));
        panelGlass12.add(label52);
        label52.setBounds(12, 70, 90, 23);

        Kelurahan.setText("KELURAHAN");
        Kelurahan.setHighlighter(null);
        Kelurahan.setName("Kelurahan"); // NOI18N
        Kelurahan.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                KelurahanMouseMoved(evt);
            }
        });
        Kelurahan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                KelurahanMouseExited(evt);
            }
        });
        Kelurahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelurahanKeyPressed(evt);
            }
        });
        panelGlass12.add(Kelurahan);
        Kelurahan.setBounds(845, 100, 152, 23);

        BtnKelurahan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKelurahan.setMnemonic('2');
        BtnKelurahan.setToolTipText("ALt+2");
        BtnKelurahan.setName("BtnKelurahan"); // NOI18N
        BtnKelurahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKelurahanActionPerformed(evt);
            }
        });
        panelGlass12.add(BtnKelurahan);
        BtnKelurahan.setBounds(1000, 100, 28, 23);

        Kecamatan.setText("KECAMATAN");
        Kecamatan.setHighlighter(null);
        Kecamatan.setName("Kecamatan"); // NOI18N
        Kecamatan.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                KecamatanMouseMoved(evt);
            }
        });
        Kecamatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                KecamatanMouseExited(evt);
            }
        });
        Kecamatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KecamatanKeyPressed(evt);
            }
        });
        panelGlass12.add(Kecamatan);
        Kecamatan.setBounds(845, 70, 152, 23);

        BtnKecamatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKecamatan.setMnemonic('3');
        BtnKecamatan.setToolTipText("ALt+3");
        BtnKecamatan.setName("BtnKecamatan"); // NOI18N
        BtnKecamatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKecamatanActionPerformed(evt);
            }
        });
        panelGlass12.add(BtnKecamatan);
        BtnKecamatan.setBounds(1000, 70, 28, 23);

        BtnKabupaten.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKabupaten.setMnemonic('4');
        BtnKabupaten.setToolTipText("ALt+4");
        BtnKabupaten.setName("BtnKabupaten"); // NOI18N
        BtnKabupaten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKabupatenActionPerformed(evt);
            }
        });
        panelGlass12.add(BtnKabupaten);
        BtnKabupaten.setBounds(805, 100, 28, 23);

        Kabupaten.setText("KABUPATEN");
        Kabupaten.setHighlighter(null);
        Kabupaten.setName("Kabupaten"); // NOI18N
        Kabupaten.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                KabupatenMouseMoved(evt);
            }
        });
        Kabupaten.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                KabupatenMouseExited(evt);
            }
        });
        Kabupaten.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KabupatenKeyPressed(evt);
            }
        });
        panelGlass12.add(Kabupaten);
        Kabupaten.setBounds(650, 100, 152, 23);

        BtnPropinsi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPropinsi.setMnemonic('4');
        BtnPropinsi.setToolTipText("ALt+4");
        BtnPropinsi.setName("BtnPropinsi"); // NOI18N
        BtnPropinsi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPropinsiActionPerformed(evt);
            }
        });
        panelGlass12.add(BtnPropinsi);
        BtnPropinsi.setBounds(805, 70, 28, 23);

        Propinsi.setText("PROPINSI");
        Propinsi.setHighlighter(null);
        Propinsi.setName("Propinsi"); // NOI18N
        Propinsi.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                PropinsiMouseMoved(evt);
            }
        });
        Propinsi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                PropinsiMouseExited(evt);
            }
        });
        Propinsi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PropinsiKeyPressed(evt);
            }
        });
        panelGlass12.add(Propinsi);
        Propinsi.setBounds(650, 70, 152, 23);

        JK1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "LAKI-LAKI", "PEREMPUAN" }));
        JK1.setName("JK1"); // NOI18N
        panelGlass12.add(JK1);
        JK1.setBounds(420, 70, 140, 23);

        PanelInput.add(panelGlass12, java.awt.BorderLayout.CENTER);

        internalFrame5.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Data Pendonor", internalFrame5);

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi5.setBorder(null);
        panelisi5.setName("panelisi5"); // NOI18N
        panelisi5.setPreferredSize(new java.awt.Dimension(450, 77));
        panelisi5.setLayout(new java.awt.GridLayout(2, 0));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(237, 242, 232)), ".: Penggunaan BHP Medis ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 102));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi6.setBorder(null);
        panelisi6.setName("panelisi6"); // NOI18N
        panelisi6.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi6.add(label10);

        TCariMedis.setToolTipText("Alt+C");
        TCariMedis.setName("TCariMedis"); // NOI18N
        TCariMedis.setPreferredSize(new java.awt.Dimension(290, 23));
        TCariMedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariMedisKeyPressed(evt);
            }
        });
        panelisi6.add(TCariMedis);

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
        panelisi6.add(BtnCari2);

        BtnAll1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll1.setMnemonic('2');
        BtnAll1.setToolTipText("Alt+2");
        BtnAll1.setName("BtnAll1"); // NOI18N
        BtnAll1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAll1ActionPerformed(evt);
            }
        });
        BtnAll1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAll1KeyPressed(evt);
            }
        });
        panelisi6.add(BtnAll1);

        jPanel3.add(panelisi6, java.awt.BorderLayout.PAGE_END);

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 255, 235)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbMedis.setToolTipText("Klik pada cell Jml untuk memasukkan jumlah penggunaan");
        tbMedis.setName("tbMedis"); // NOI18N
        tbMedis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMedisMouseClicked(evt);
            }
        });
        tbMedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbMedisKeyPressed(evt);
            }
        });
        Scroll1.setViewportView(tbMedis);

        jPanel3.add(Scroll1, java.awt.BorderLayout.CENTER);

        panelisi5.add(jPanel3);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(237, 242, 232)), ".: Penggunaan BHP Non Medis ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(300, 202));
        jPanel4.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi7.setBorder(null);
        panelisi7.setName("panelisi7"); // NOI18N
        panelisi7.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label11.setText("Key Word :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi7.add(label11);

        TCariNonMedis.setToolTipText("Alt+C");
        TCariNonMedis.setName("TCariNonMedis"); // NOI18N
        TCariNonMedis.setPreferredSize(new java.awt.Dimension(290, 23));
        TCariNonMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TCariNonMedisActionPerformed(evt);
            }
        });
        TCariNonMedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariNonMedisKeyPressed(evt);
            }
        });
        panelisi7.add(TCariNonMedis);

        BtnCari3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari3.setMnemonic('1');
        BtnCari3.setToolTipText("Alt+1");
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
        panelisi7.add(BtnCari3);

        BtnAll2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll2.setMnemonic('2');
        BtnAll2.setToolTipText("Alt+2");
        BtnAll2.setName("BtnAll2"); // NOI18N
        BtnAll2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAll2ActionPerformed(evt);
            }
        });
        BtnAll2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAll2KeyPressed(evt);
            }
        });
        panelisi7.add(BtnAll2);

        jPanel4.add(panelisi7, java.awt.BorderLayout.PAGE_END);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 255, 235)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbNonMedis.setToolTipText("Klik pada cell Jml untuk memasukkan jumlah penggunaan");
        tbNonMedis.setName("tbNonMedis"); // NOI18N
        tbNonMedis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbNonMedisMouseClicked(evt);
            }
        });
        tbNonMedis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbNonMedisKeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbNonMedis);

        jPanel4.add(Scroll2, java.awt.BorderLayout.CENTER);

        panelisi5.add(jPanel4);

        internalFrame2.add(panelisi5, java.awt.BorderLayout.EAST);

        scrollPane1.setName("scrollPane1"); // NOI18N

        panelisi4.setBorder(null);
        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 77));
        panelisi4.setLayout(null);

        label34.setText("Nomor Kantong :");
        label34.setName("label34"); // NOI18N
        label34.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label34);
        label34.setBounds(0, 170, 102, 23);

        NomorDonor.setEditable(false);
        NomorDonor.setHighlighter(null);
        NomorDonor.setName("NomorDonor"); // NOI18N
        NomorDonor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NomorDonorActionPerformed(evt);
            }
        });
        panelisi4.add(NomorDonor);
        NomorDonor.setBounds(110, 10, 140, 23);

        label36.setText("Nama Pendonor :");
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label36);
        label36.setBounds(0, 40, 102, 23);

        NamaPendonor.setHighlighter(null);
        NamaPendonor.setName("NamaPendonor"); // NOI18N
        NamaPendonor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaPendonorKeyPressed(evt);
            }
        });
        panelisi4.add(NamaPendonor);
        NamaPendonor.setBounds(110, 40, 270, 23);

        label32.setText("Tanggal :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label32);
        label32.setBounds(260, 10, 57, 23);

        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "22-08-2023" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TanggalItemStateChanged(evt);
            }
        });
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        panelisi4.add(Tanggal);
        Tanggal.setBounds(320, 10, 90, 23);

        jLabel8.setText("Dinas :");
        jLabel8.setName("jLabel8"); // NOI18N
        panelisi4.add(jLabel8);
        jLabel8.setBounds(430, 10, 50, 23);

        Dinas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pagi", "Siang", "Malam" }));
        Dinas.setName("Dinas"); // NOI18N
        Dinas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DinasKeyPressed(evt);
            }
        });
        panelisi4.add(Dinas);
        Dinas.setBounds(480, 10, 86, 23);

        label35.setText("Tahun");
        label35.setName("label35"); // NOI18N
        label35.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label35);
        label35.setBounds(320, 70, 40, 23);

        Umur.setEditable(false);
        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N
        panelisi4.add(Umur);
        Umur.setBounds(286, 70, 40, 23);

        label37.setText("Umur :");
        label37.setName("label37"); // NOI18N
        label37.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label37);
        label37.setBounds(240, 70, 40, 23);

        label38.setText("Alamat :");
        label38.setName("label38"); // NOI18N
        label38.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label38);
        label38.setBounds(0, 130, 102, 23);

        Alamat.setHighlighter(null);
        Alamat.setName("Alamat"); // NOI18N
        Alamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlamatKeyPressed(evt);
            }
        });
        panelisi4.add(Alamat);
        Alamat.setBounds(110, 130, 450, 23);

        jLabel9.setText("J.K. :");
        jLabel9.setName("jLabel9"); // NOI18N
        panelisi4.add(jLabel9);
        jLabel9.setBounds(0, 100, 102, 23);

        JK.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "LAKI-LAKI", "PEREMPUAN" }));
        JK.setName("JK"); // NOI18N
        JK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JKKeyPressed(evt);
            }
        });
        panelisi4.add(JK);
        JK.setBounds(110, 100, 110, 23);

        jLabel10.setText("Golongan Darah :");
        jLabel10.setName("jLabel10"); // NOI18N
        panelisi4.add(jLabel10);
        jLabel10.setBounds(0, 200, 102, 23);

        GolonganDarah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A", "AB", "B", "O" }));
        GolonganDarah.setName("GolonganDarah"); // NOI18N
        GolonganDarah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GolonganDarahKeyPressed(evt);
            }
        });
        panelisi4.add(GolonganDarah);
        GolonganDarah.setBounds(110, 200, 70, 23);

        jLabel11.setText("Resus :");
        jLabel11.setName("jLabel11"); // NOI18N
        panelisi4.add(jLabel11);
        jLabel11.setBounds(210, 200, 50, 23);

        Resus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "(-)", "(+)" }));
        Resus.setName("Resus"); // NOI18N
        panelisi4.add(Resus);
        Resus.setBounds(260, 200, 70, 23);

        label39.setText("Tensi :");
        label39.setName("label39"); // NOI18N
        label39.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label39);
        label39.setBounds(350, 200, 70, 23);

        Tensi.setHighlighter(null);
        Tensi.setName("Tensi"); // NOI18N
        Tensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TensiKeyPressed(evt);
            }
        });
        panelisi4.add(Tensi);
        Tensi.setBounds(425, 200, 80, 23);

        label40.setText("Nomor Donor :");
        label40.setName("label40"); // NOI18N
        label40.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label40);
        label40.setBounds(0, 10, 102, 23);

        NomorBag.setHighlighter(null);
        NomorBag.setName("NomorBag"); // NOI18N
        panelisi4.add(NomorBag);
        NomorBag.setBounds(110, 170, 140, 23);

        label41.setText("No.Telp :");
        label41.setName("label41"); // NOI18N
        label41.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label41);
        label41.setBounds(230, 100, 50, 23);

        NomorTelp.setHighlighter(null);
        NomorTelp.setName("NomorTelp"); // NOI18N
        NomorTelp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NomorTelpKeyPressed(evt);
            }
        });
        panelisi4.add(NomorTelp);
        NomorTelp.setBounds(290, 100, 110, 23);

        jLabel12.setText("Jenis Bag :");
        jLabel12.setName("jLabel12"); // NOI18N
        panelisi4.add(jLabel12);
        jLabel12.setBounds(190, 230, 70, 23);

        JenisBag.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SB", "DB", "TB", "QB", "P" }));
        JenisBag.setName("JenisBag"); // NOI18N
        JenisBag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JenisBagActionPerformed(evt);
            }
        });
        JenisBag.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisBagKeyPressed(evt);
            }
        });
        panelisi4.add(JenisBag);
        JenisBag.setBounds(260, 230, 70, 23);

        jLabel13.setText("Jenis Donor :");
        jLabel13.setName("jLabel13"); // NOI18N
        panelisi4.add(jLabel13);
        jLabel13.setBounds(0, 230, 102, 23);

        JenisDonor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "DB", "DP", "DS" }));
        JenisDonor.setName("JenisDonor"); // NOI18N
        panelisi4.add(JenisDonor);
        JenisDonor.setBounds(110, 230, 70, 23);

        jLabel14.setText("Tempat Aftap :");
        jLabel14.setName("jLabel14"); // NOI18N
        panelisi4.add(jLabel14);
        jLabel14.setBounds(280, 170, 80, 23);

        TempatAftap.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dalam Gedung", "Luar Gedung" }));
        TempatAftap.setName("TempatAftap"); // NOI18N
        TempatAftap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TempatAftapKeyPressed(evt);
            }
        });
        panelisi4.add(TempatAftap);
        TempatAftap.setBounds(360, 170, 150, 23);

        label17.setText("Hasil Uji Saring :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label17);
        label17.setBounds(0, 290, 102, 23);

        KodePetugasAftap.setName("KodePetugasAftap"); // NOI18N
        KodePetugasAftap.setPreferredSize(new java.awt.Dimension(80, 23));
        KodePetugasAftap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodePetugasAftapKeyPressed(evt);
            }
        });
        panelisi4.add(KodePetugasAftap);
        KodePetugasAftap.setBounds(110, 260, 90, 23);

        NamaPetugasAftap.setEditable(false);
        NamaPetugasAftap.setName("NamaPetugasAftap"); // NOI18N
        NamaPetugasAftap.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(NamaPetugasAftap);
        NamaPetugasAftap.setBounds(200, 260, 286, 23);

        btnPetugasAftap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugasAftap.setMnemonic('1');
        btnPetugasAftap.setToolTipText("Alt+1");
        btnPetugasAftap.setName("btnPetugasAftap"); // NOI18N
        btnPetugasAftap.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugasAftap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasAftapActionPerformed(evt);
            }
        });
        panelisi4.add(btnPetugasAftap);
        btnPetugasAftap.setBounds(490, 260, 28, 23);

        label18.setText("Petugas Aftap :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label18);
        label18.setBounds(0, 260, 102, 23);

        jLabel15.setText("HBSAg :");
        jLabel15.setName("jLabel15"); // NOI18N
        panelisi4.add(jLabel15);
        jLabel15.setBounds(40, 310, 97, 23);

        HBSAg.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Non-Reaktif", "Reaktif" }));
        HBSAg.setName("HBSAg"); // NOI18N
        HBSAg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HBSAgActionPerformed(evt);
            }
        });
        HBSAg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HBSAgKeyPressed(evt);
            }
        });
        panelisi4.add(HBSAg);
        HBSAg.setBounds(140, 310, 110, 23);

        HCV.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Non-Reaktif", "Reaktif" }));
        HCV.setName("HCV"); // NOI18N
        HCV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HCVKeyPressed(evt);
            }
        });
        panelisi4.add(HCV);
        HCV.setBounds(340, 310, 110, 23);

        jLabel16.setText("HCV :");
        jLabel16.setName("jLabel16"); // NOI18N
        panelisi4.add(jLabel16);
        jLabel16.setBounds(270, 310, 70, 23);

        jLabel17.setText("HIV :");
        jLabel17.setName("jLabel17"); // NOI18N
        panelisi4.add(jLabel17);
        jLabel17.setBounds(40, 340, 97, 23);

        HIV.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Non-Reaktif", "Reaktif" }));
        HIV.setName("HIV"); // NOI18N
        HIV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HIVKeyPressed(evt);
            }
        });
        panelisi4.add(HIV);
        HIV.setBounds(140, 340, 110, 23);

        jLabel18.setText("Spilis :");
        jLabel18.setName("jLabel18"); // NOI18N
        panelisi4.add(jLabel18);
        jLabel18.setBounds(270, 340, 70, 23);

        Spilis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Non-Reaktif", "Reaktif" }));
        Spilis.setName("Spilis"); // NOI18N
        Spilis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SpilisKeyPressed(evt);
            }
        });
        panelisi4.add(Spilis);
        Spilis.setBounds(340, 340, 110, 23);

        label19.setText("Petugas U.Saring :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label19);
        label19.setBounds(0, 400, 102, 23);

        KodePetugasUSaring.setName("KodePetugasUSaring"); // NOI18N
        KodePetugasUSaring.setPreferredSize(new java.awt.Dimension(80, 23));
        KodePetugasUSaring.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodePetugasUSaringKeyPressed(evt);
            }
        });
        panelisi4.add(KodePetugasUSaring);
        KodePetugasUSaring.setBounds(110, 400, 90, 23);

        NamaPetugasUSaring.setEditable(false);
        NamaPetugasUSaring.setName("NamaPetugasUSaring"); // NOI18N
        NamaPetugasUSaring.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(NamaPetugasUSaring);
        NamaPetugasUSaring.setBounds(200, 400, 286, 23);

        btnPetugasUSaring.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugasUSaring.setMnemonic('1');
        btnPetugasUSaring.setToolTipText("Alt+1");
        btnPetugasUSaring.setName("btnPetugasUSaring"); // NOI18N
        btnPetugasUSaring.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugasUSaring.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasUSaringActionPerformed(evt);
            }
        });
        panelisi4.add(btnPetugasUSaring);
        btnPetugasUSaring.setBounds(490, 400, 28, 23);

        jLabel19.setText("Malaria :");
        jLabel19.setName("jLabel19"); // NOI18N
        panelisi4.add(jLabel19);
        jLabel19.setBounds(40, 370, 97, 23);

        Malaria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Non-Reaktif", "Reaktif" }));
        Malaria.setName("Malaria"); // NOI18N
        Malaria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MalariaKeyPressed(evt);
            }
        });
        panelisi4.add(Malaria);
        Malaria.setBounds(140, 370, 110, 23);

        label33.setText("Tanggal Lahir :");
        label33.setName("label33"); // NOI18N
        label33.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi4.add(label33);
        label33.setBounds(12, 70, 90, 23);

        tgl_lahir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "22-08-2023" }));
        tgl_lahir.setDisplayFormat("dd-MM-yyyy");
        tgl_lahir.setName("tgl_lahir"); // NOI18N
        tgl_lahir.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                tgl_lahirItemStateChanged(evt);
            }
        });
        panelisi4.add(tgl_lahir);
        tgl_lahir.setBounds(110, 70, 90, 23);

        nik.setHighlighter(null);
        nik.setName("nik"); // NOI18N
        nik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nikActionPerformed(evt);
            }
        });
        panelisi4.add(nik);
        nik.setBounds(420, 40, 140, 23);

        label42.setText("NIK : ");
        label42.setName("label42"); // NOI18N
        label42.setPreferredSize(new java.awt.Dimension(33, 23));
        panelisi4.add(label42);
        label42.setBounds(310, 40, 105, 23);

        jSeparator1.setName("jSeparator1"); // NOI18N
        panelisi4.add(jSeparator1);
        jSeparator1.setBounds(0, 285, 780, 2);

        jSeparator2.setName("jSeparator2"); // NOI18N
        panelisi4.add(jSeparator2);
        jSeparator2.setBounds(0, 156, 780, 2);

        Pengambilan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Baik", "Gagal" }));
        Pengambilan.setName("Pengambilan"); // NOI18N
        panelisi4.add(Pengambilan);
        Pengambilan.setBounds(425, 230, 80, 23);

        jLabel22.setText("Pengambilan :");
        jLabel22.setName("jLabel22"); // NOI18N
        panelisi4.add(jLabel22);
        jLabel22.setBounds(350, 230, 70, 23);

        scrollPane1.setViewportView(panelisi4);

        internalFrame2.add(scrollPane1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Pendonor Darah", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ScrollMouseClicked(evt);
            }
        });

        tbTranfusiDarah.setAutoCreateRowSorter(true);
        tbTranfusiDarah.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbTranfusiDarah.setComponentPopupMenu(jPopupMenu1);
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
        Scroll.setViewportView(tbTranfusiDarah);

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Daftar Pendonor Darah", internalFrame3);

        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll3.setComponentPopupMenu(jPopupMenu1);
        Scroll3.setName("Scroll"); // NOI18N
        Scroll3.setOpaque(true);
        Scroll3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Scroll3MouseClicked(evt);
            }
        });

        tbTotalDonor.setAutoCreateRowSorter(true);
        tbTotalDonor.setToolTipText("");
        tbTotalDonor.setComponentPopupMenu(jPopupMenu1);
        tbTotalDonor.setName("tbTotalDonor"); // NOI18N
        tbTotalDonor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTotalDonorMouseClicked(evt);
            }
        });
        tbTotalDonor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTotalDonorKeyPressed(evt);
            }
        });
        Scroll3.setViewportView(tbTotalDonor);

        internalFrame4.add(Scroll3, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Laporan Total Donor Darah", internalFrame4);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

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

        Ganti.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        Ganti.setMnemonic('S');
        Ganti.setText("Ganti");
        Ganti.setToolTipText("Alt+S");
        Ganti.setName("Ganti"); // NOI18N
        Ganti.setPreferredSize(new java.awt.Dimension(100, 30));
        Ganti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GantiActionPerformed(evt);
            }
        });
        Ganti.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GantiKeyPressed(evt);
            }
        });
        panelGlass8.add(Ganti);

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
        BtnEdit.setText("Aftap");
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

        BtnEdit1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit1.setMnemonic('G');
        BtnEdit1.setText("Saring");
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

        BtnWA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/edit_f2.png"))); // NOI18N
        BtnWA.setMnemonic('G');
        BtnWA.setText("Kirim WA");
        BtnWA.setToolTipText("Alt+G");
        BtnWA.setName("BtnWA"); // NOI18N
        BtnWA.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnWA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnWAActionPerformed(evt);
            }
        });
        BtnWA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnWAKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnWA);

        jPanel1.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel20.setText("Tanggal :");
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(jLabel20);

        TanggalCari1.setForeground(new java.awt.Color(50, 70, 50));
        TanggalCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "22-08-2023" }));
        TanggalCari1.setDisplayFormat("dd-MM-yyyy");
        TanggalCari1.setName("TanggalCari1"); // NOI18N
        TanggalCari1.setOpaque(false);
        TanggalCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(TanggalCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(24, 23));
        panelGlass9.add(jLabel21);

        TanggalCari2.setForeground(new java.awt.Color(50, 70, 50));
        TanggalCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "22-08-2023" }));
        TanggalCari2.setDisplayFormat("dd-MM-yyyy");
        TanggalCari2.setName("TanggalCari2"); // NOI18N
        TanggalCari2.setOpaque(false);
        TanggalCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        TanggalCari2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TanggalCari2ActionPerformed(evt);
            }
        });
        panelGlass9.add(TanggalCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(200, 23));
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

        BtnAll3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll3.setMnemonic('M');
        BtnAll3.setText("Cari");
        BtnAll3.setToolTipText("Alt+M");
        BtnAll3.setName("BtnAll3"); // NOI18N
        BtnAll3.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnAll3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAll3ActionPerformed(evt);
            }
        });
        panelGlass9.add(BtnAll3);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(75, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(LCount);

        jPanel1.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void NamaPendonorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaPendonorKeyPressed
    Valid.pindah(evt, Dinas, JK);
}//GEN-LAST:event_NamaPendonorKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        emptTeks();
        tampilMedis();
        tampilNonMedis();
        tampilPendonor();
//        BtnBatal.hide();
//        tampilTotalDonor();
    }//GEN-LAST:event_formWindowOpened

    private void TCariMedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariMedisKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCari2ActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            TCariNonMedis.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            KodePetugasUSaring.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            tbMedis.requestFocus();
        }
    }//GEN-LAST:event_TCariMedisKeyPressed

    private void BtnCari2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari2ActionPerformed
        tampilMedis();
    }//GEN-LAST:event_BtnCari2ActionPerformed

    private void BtnCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCari2KeyPressed

    private void BtnAll1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAll1ActionPerformed
        TCariMedis.setText("");
        tampilMedis();
    }//GEN-LAST:event_BtnAll1ActionPerformed

    private void BtnAll1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAll1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnAll1KeyPressed

    private void tbMedisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMedisMouseClicked

    }//GEN-LAST:event_tbMedisMouseClicked

    private void tbMedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbMedisKeyPressed
        if (tbMedis.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                try {
                    i = tbMedis.getSelectedColumn();
                    if (i == 1) {
                        TCariMedis.setText("");
                        TCariMedis.requestFocus();
                    }
                } catch (java.lang.NullPointerException e) {
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
                TCariMedis.setText("");
                TCariMedis.requestFocus();
            }
        }
    }//GEN-LAST:event_tbMedisKeyPressed

    private void TCariNonMedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariNonMedisKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCari3ActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnSimpan.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            TCariMedis.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            tbNonMedis.requestFocus();
        }
    }//GEN-LAST:event_TCariNonMedisKeyPressed

    private void BtnCari3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari3ActionPerformed
        tampilNonMedis();
    }//GEN-LAST:event_BtnCari3ActionPerformed

    private void BtnCari3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCari3KeyPressed

    private void BtnAll2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAll2ActionPerformed
        TCariNonMedis.setText("");
        tampilNonMedis();
    }//GEN-LAST:event_BtnAll2ActionPerformed

    private void BtnAll2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAll2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnAll2KeyPressed

    private void tbNonMedisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbNonMedisMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbNonMedisMouseClicked

    private void tbNonMedisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbNonMedisKeyPressed
        if (tbNonMedis.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                try {
                    i = tbNonMedis.getSelectedColumn();
                    if (i == 1) {
                        TCariNonMedis.setText("");
                        TCariNonMedis.requestFocus();
                    }
                } catch (java.lang.NullPointerException e) {
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
                TCariNonMedis.setText("");
                TCariNonMedis.requestFocus();
            }
        }
    }//GEN-LAST:event_tbNonMedisKeyPressed

    private void tbTranfusiDarahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTranfusiDarahMouseClicked
        /*if(tabMode.getRowCount()!=0){
            try {
                getData();
                if(evt.getClickCount()==2){
                    TabRawat.setSelectedIndex(0);
                }
            } catch (java.lang.NullPointerException e) {
            }
        }*/
    }//GEN-LAST:event_tbTranfusiDarahMouseClicked

    private void tbTranfusiDarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTranfusiDarahKeyPressed
        /*if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }*/
    }//GEN-LAST:event_tbTranfusiDarahKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if (TabRawat.getSelectedIndex() == 0) {
            tampilPendonor();
        }
        if (TabRawat.getSelectedIndex() == 2) {
            tampil();
        } else if (TabRawat.getSelectedIndex() == 1) {
            tampilMedis();
            tampilNonMedis();
        } else {
            tampilTotalDonor();
        };
    }//GEN-LAST:event_TabRawatMouseClicked

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (TabRawat.getSelectedIndex() == 0) {
            if (NomorPendonor.getText().trim().equals("")) {
                Valid.textKosong(NomorPendonor, "Nomor Donor");
            } else if (NamaPendonor1.getText().trim().equals("")) {
                Valid.textKosong(NamaPendonor1, "Nama Donor");
            } else if (nik1.getText().trim().equals("")) {
                Valid.textKosong(nik1, "NIK");
            } else if (tempatlahir.getText().trim().equals("")) {
                Valid.textKosong(tempatlahir, "Tempat Lahir");
            } else if (Alamat1.getText().trim().equals("")) {
                Valid.textKosong(Alamat1, "Alamat");
            } else if (Propinsi.getText().trim().equals("")) {
                Valid.textKosong(Propinsi, "Provinsi");
            } else if (Kabupaten.getText().trim().equals("")) {
                Valid.textKosong(Kabupaten, "Kabupaten");
            } else if (Kecamatan.getText().trim().equals("")) {
                Valid.textKosong(Kecamatan, "Kecamatan");
            } else if (Kelurahan.getText().trim().equals("")) {
                Valid.textKosong(Kelurahan, "Kelurahan");
            } else {
                isNumber2();
                if (Sequel.menyimpantf2("utd_pendonor", "?,?,?,?,?,?,?,?,?,?,?,?,?,?", "No.Donor", 14, new String[]{
                    NomorPendonor.getText(), NamaPendonor1.getText().toUpperCase(), nik1.getText(), JK1.getSelectedItem().toString().substring(0, 1), tempatlahir.getText().toUpperCase(), Valid.SetTgl(tgl_lahir1.getSelectedItem() + ""),
                    Alamat1.getText().toUpperCase(), kdkel, kdkec, kdkab, kdprop, GolDarah.getSelectedItem().toString(), Resus1.getSelectedItem().toString(), NomorTelp1.getText()
                }) == true) {
                    if (var.getform().equals("UTDDonor")) {
                        TCari.setText(NomorPendonor.getText());
                    }
                    emptTeks();
                    tampilPendonor();
                }
            }
        }
//        if (TabRawat.getSelectedIndex() != 0) {
//            if (NamaPendonor.getText().trim().equals("")) {
//                Valid.textKosong(NamaPendonor, "Nama Pendonor");
//            } else if (Umur.getText().trim().equals("")) {
//                Valid.textKosong(Umur, "Umur");
//            } else if (nik.getText().trim().equals("")) {
//                Valid.textKosong(nik, "NIK");
////        }else if(Tensi.getText().trim().equals("")){
////            Valid.textKosong(Tensi,"Tensi");
//////        }else if(NomorBag.getText().trim().equals("")){
//////            Valid.textKosong(NomorBag,"Nomor Bag");
////        }else if(NomorTelp.getText().trim().equals("")){
////            Valid.textKosong(NomorTelp,"Nomor Selang");
////        }else if(KodePetugasAftap.getText().trim().equals("")||NamaPetugasAftap.getText().trim().equals("")){
////            Valid.textKosong(KodePetugasAftap,"Petugas Aftap");
////        }else if(KodePetugasUSaring.getText().trim().equals("")||NamaPetugasUSaring.getText().trim().equals("")){
////            Valid.textKosong(KodePetugasUSaring,"Petugas Uji Saring");
//            } else {
//                String user = var.getkode();
//                if (Sequel.menyimpantf("utd_donor", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Nomor Donor", 25, new String[]{
//                    NomorDonor.getText(), NamaPendonor.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""),
//                    Dinas.getSelectedItem().toString(), JK.getSelectedItem().toString().substring(0, 1), Umur.getText(),
//                    Alamat.getText(), null, null, "", "",
//                    //                    GolonganDarah.getSelectedItem().toString(),Resus.getSelectedItem().toString(), 
//                    //                    Tensi.getText(),NomorBag.getText(),
//                    NomorTelp.getText(), null, null, null,
//                    //                    JenisBag.getSelectedItem().toString(),JenisDonor.getSelectedItem().toString(),TempatAftap.getSelectedItem().toString(), 
//                    user, null, null, null, null, null,
//                    //                    HBSAg.getSelectedItem().toString(),HCV.getSelectedItem().toString(), 
//                    //                    HIV.getSelectedItem().toString(),Spilis.getSelectedItem().toString(),Malaria.getSelectedItem().toString(),
//                    user, "Aman", Valid.SetTgl(tgl_lahir.getSelectedItem() + ""), nik.getText()
//                }) == true) {
//                    for (i = 0; i < tbMedis.getRowCount(); i++) {
//                        try {
//                            if (Valid.SetAngka(tbMedis.getValueAt(i, 0).toString()) > 0) {
//                                if (Sequel.menyimpantf2("utd_penggunaan_medis_donor", "?,?,?,?,?", "BHP Medis", 5, new String[]{
//                                    NomorDonor.getText(), tbMedis.getValueAt(i, 1).toString(), tbMedis.getValueAt(i, 0).toString(), tbMedis.getValueAt(i, 3).toString(),
//                                    Double.toString(Double.parseDouble(tbMedis.getValueAt(i, 0).toString()) * Double.parseDouble(tbMedis.getValueAt(i, 3).toString()))
//                                }) == true) {
//                                    Sequel.menyimpan("utd_stok_medis", "'" + tbMedis.getValueAt(i, 1).toString() + "','-" + tbMedis.getValueAt(i, 0).toString() + "','" + tbMedis.getValueAt(i, 3).toString() + "'",
//                                            "stok=stok-" + tbMedis.getValueAt(i, 0).toString() + "", "kode_brng='" + tbMedis.getValueAt(i, 1).toString() + "'");
//                                    tbMedis.setValueAt(null, i, 0);
//                                    tbMedis.setValueAt(0, i, 4);
//                                }
//                            }
//                        } catch (Exception e) {
//                        }
//                    }
//                    for (i = 0; i < tbNonMedis.getRowCount(); i++) {
//                        try {
//                            if (Valid.SetAngka(tbNonMedis.getValueAt(i, 0).toString()) > 0) {
//                                if (Sequel.menyimpantf2("utd_penggunaan_penunjang_donor", "?,?,?,?,?", "BHP Non Medis", 5, new String[]{
//                                    NomorDonor.getText(), tbNonMedis.getValueAt(i, 1).toString(), tbNonMedis.getValueAt(i, 0).toString(), tbNonMedis.getValueAt(i, 3).toString(),
//                                    Double.toString(Double.parseDouble(tbNonMedis.getValueAt(i, 0).toString()) * Double.parseDouble(tbNonMedis.getValueAt(i, 3).toString()))
//                                }) == true) {
//                                    Sequel.menyimpan("utd_stok_penunjang", "'" + tbNonMedis.getValueAt(i, 1).toString() + "','-" + tbNonMedis.getValueAt(i, 0).toString() + "','" + tbNonMedis.getValueAt(i, 3).toString() + "'",
//                                            "stok=stok-" + tbNonMedis.getValueAt(i, 0).toString() + "", "kode_brng='" + tbNonMedis.getValueAt(i, 1).toString() + "'");
//                                    tbNonMedis.setValueAt(null, i, 0);
//                                    tbNonMedis.setValueAt(0, i, 4);
//                                }
//                            }
//                        } catch (Exception e) {
//                        }
//                    }
//                    JOptionPane.showMessageDialog(null, "Berhasil Simpan.");
//                    tampil();
//                    emptTeks();
//                } else {
////                autoNomer();
//                    isNumber();
//                    if (Sequel.menyimpantf("utd_donor", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Nomor Donor", 25, new String[]{
//                        NomorDonor.getText(), NamaPendonor.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""),
//                        Dinas.getSelectedItem().toString(), JK.getSelectedItem().toString().substring(0, 1), Umur.getText(),
//                        Alamat.getText(), null, null, "", "",
//                        //                    GolonganDarah.getSelectedItem().toString(),Resus.getSelectedItem().toString(), 
//                        //                    Tensi.getText(),NomorBag.getText(),
//                        NomorTelp.getText(), null, null, null,
//                        //                    JenisBag.getSelectedItem().toString(),JenisDonor.getSelectedItem().toString(),TempatAftap.getSelectedItem().toString(), 
//                        user, null, null, null, null, null,
//                        //                    HBSAg.getSelectedItem().toString(),HCV.getSelectedItem().toString(), 
//                        //                    HIV.getSelectedItem().toString(),Spilis.getSelectedItem().toString(),Malaria.getSelectedItem().toString(),
//                        user, "Aman", Valid.SetTgl(tgl_lahir.getSelectedItem() + ""), nik.getText()
//                    }) == true) {
//                        for (i = 0; i < tbMedis.getRowCount(); i++) {
//                            try {
//                                if (Valid.SetAngka(tbMedis.getValueAt(i, 0).toString()) > 0) {
//                                    if (Sequel.menyimpantf2("utd_penggunaan_medis_donor", "?,?,?,?,?", "BHP Medis", 5, new String[]{
//                                        NomorDonor.getText(), tbMedis.getValueAt(i, 1).toString(), tbMedis.getValueAt(i, 0).toString(), tbMedis.getValueAt(i, 3).toString(),
//                                        Double.toString(Double.parseDouble(tbMedis.getValueAt(i, 0).toString()) * Double.parseDouble(tbMedis.getValueAt(i, 3).toString()))
//                                    }) == true) {
//                                        Sequel.menyimpan("utd_stok_medis", "'" + tbMedis.getValueAt(i, 1).toString() + "','-" + tbMedis.getValueAt(i, 0).toString() + "','" + tbMedis.getValueAt(i, 3).toString() + "'",
//                                                "stok=stok-" + tbMedis.getValueAt(i, 0).toString() + "", "kode_brng='" + tbMedis.getValueAt(i, 1).toString() + "'");
//                                        tbMedis.setValueAt(null, i, 0);
//                                        tbMedis.setValueAt(0, i, 4);
//                                    }
//                                }
//                            } catch (Exception e) {
//                            }
//                        }
//                        for (i = 0; i < tbNonMedis.getRowCount(); i++) {
//                            try {
//                                if (Valid.SetAngka(tbNonMedis.getValueAt(i, 0).toString()) > 0) {
//                                    if (Sequel.menyimpantf2("utd_penggunaan_penunjang_donor", "?,?,?,?,?", "BHP Non Medis", 5, new String[]{
//                                        NomorDonor.getText(), tbNonMedis.getValueAt(i, 1).toString(), tbNonMedis.getValueAt(i, 0).toString(), tbNonMedis.getValueAt(i, 3).toString(),
//                                        Double.toString(Double.parseDouble(tbNonMedis.getValueAt(i, 0).toString()) * Double.parseDouble(tbNonMedis.getValueAt(i, 3).toString()))
//                                    }) == true) {
//                                        Sequel.menyimpan("utd_stok_penunjang", "'" + tbNonMedis.getValueAt(i, 1).toString() + "','-" + tbNonMedis.getValueAt(i, 0).toString() + "','" + tbNonMedis.getValueAt(i, 3).toString() + "'",
//                                                "stok=stok-" + tbNonMedis.getValueAt(i, 0).toString() + "", "kode_brng='" + tbNonMedis.getValueAt(i, 1).toString() + "'");
//                                        tbNonMedis.setValueAt(null, i, 0);
//                                        tbNonMedis.setValueAt(0, i, 4);
//                                    }
//                                }
//                            } catch (Exception e) {
//                            }
//                        }
//                        JOptionPane.showMessageDialog(null, "Berhasil Simpan.");
//                        tampil();
//                        emptTeks();
//                    }
//                }
//            }
//        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, KodePetugasUSaring, BtnBatal);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            emptTeks();
        } else {
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if (TabRawat.getSelectedIndex() == 0) {
            if (tabModePendonor.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                TCari.requestFocus();
            } else if (tbData.getSelectedRow() <= -1) {
                JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data yang mau dihapus..!!");
            } else {
                int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, Yakin mau dihapus..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    Sequel.meghapus("utd_pendonor", "no_pendonor", tbData.getValueAt(tbData.getSelectedRow(), 0).toString());
                    emptTeks();
                    tampilPendonor();
                }
            }
        }

        if (TabRawat.getSelectedIndex() == 2) {
            if (tabModeTranfusi.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                TCari.requestFocus();
            } else if (tbTranfusiDarah.getSelectedRow() <= -1) {
                JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data yang mau dihapus..!!");
            } else {
                if (!tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString().equals("")) {
                    int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, Yakin mau dihapus..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    try {

                        pscekmedis = koneksi.prepareStatement(sqlpscekmedis);
                        try {
                            pscekmedis.setString(1, tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());
                            rs = pscekmedis.executeQuery();
                            while (rs.next()) {
                                Sequel.menyimpan("utd_stok_medis", "'" + rs.getString("kode_brng") + "','" + rs.getString("jml") + "','" + rs.getDouble("harga") + "'",
                                        "stok=stok+" + rs.getString("jml") + "", "kode_brng='" + rs.getString("kode_brng") + "'");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs != null) {
                                rs.close();
                            }
                            if (pscekmedis != null) {
                                pscekmedis.close();
                            }
                        }

                        psceknonmedis = koneksi.prepareStatement(sqlpsceknonmedis);
                        try {
                            psceknonmedis.setString(1, tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());
                            rs2 = psceknonmedis.executeQuery();
                            while (rs2.next()) {
                                Sequel.menyimpan("utd_stok_penunjang", "'" + rs2.getString("kode_brng") + "','" + rs2.getString("jml") + "','" + rs2.getDouble("harga") + "'",
                                        "stok=stok+" + rs2.getString("jml") + "", "kode_brng='" + rs2.getString("kode_brng") + "'");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psceknonmedis != null) {
                                psceknonmedis.close();
                            }
                        }

                        Sequel.meghapus("utd_donor", "no_donor", tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());
//                        Sequel.meghapus("utd_donor_coba", "no_donor", tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());

                        tampil();
                        emptTeks();
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    }}
                } else {
                    JOptionPane.showMessageDialog(null, "Maaf,Pilih pada nomor donor...!!!!");
                }
            }
        }
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if (NomorDonor.getText().trim().equals("")) {
            Valid.textKosong(NomorDonor, "Nomor Donor");
        } else if (NamaPendonor.getText().trim().equals("")) {
            Valid.textKosong(NamaPendonor, "Nama Pendonor");
//        } else if (Umur.getText().trim().equals("")) {
//            Valid.textKosong(Umur, "Umur");
        } else if (Tensi.getText().trim().equals("")) {
            Valid.textKosong(Tensi, "Tensi");
//        }else if(NomorBag.getText().trim().equals("")){
//            Valid.textKosong(NomorBag,"Nomor Bag");
        } else if (NomorTelp.getText().trim().equals("")) {
            Valid.textKosong(NomorTelp, "Nomor Telepon");
//        } else if (KodePetugasAftap.getText().trim().equals("") || NamaPetugasAftap.getText().trim().equals("")) {
//            Valid.textKosong(KodePetugasAftap, "Petugas Aftap");
//        } else if (KodePetugasUSaring.getText().trim().equals("") || NamaPetugasUSaring.getText().trim().equals("")) {
//            Valid.textKosong(KodePetugasUSaring, "Petugas Uji Saring");
        } else if (tabModeTranfusi.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else if (tbTranfusiDarah.getSelectedRow() <= -1) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data yang mau dihapus..!!");
        } else {
            if (!tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString().equals("")) {
                String hbs = null;
                String hcv = null;
                String hiv = null;
                String spilis = null;
                String malar = null;
                if (HBSAg.getSelectedItem().toString() == "-") {
                    hbs = hbs;
                } else {
                    hbs = HBSAg.getSelectedItem().toString();
                }
                if (HCV.getSelectedItem().toString() == "-") {
                    hcv = hcv;
                } else {
                    hcv = HCV.getSelectedItem().toString();
                }
                if (HIV.getSelectedItem().toString() == "-") {
                    hiv = hiv;
                } else {
                    hiv = HIV.getSelectedItem().toString();
                }
                if (Spilis.getSelectedItem().toString() == "-") {
                    spilis = spilis;
                } else {
                    spilis = Spilis.getSelectedItem().toString();
                }
                if (Malaria.getSelectedItem().toString() == "-") {
                    malar = malar;
                } else {
                    malar = Malaria.getSelectedItem().toString();
                }
//                if (Sequel.mengedittf("utd_donor", "no_donor=?", "no_donor=?,nama=?,tanggal=?,dinas=?,jk=?,"
//                        + "umur=?,alamat=?,golongan_darah=?,resus=?,tensi=?,no_bag=?,no_telp=?,jenis_bag=?,"
//                        + "jenis_donor=?,tempat_aftap=?,petugas_aftap=?,hbsag=?,hcv=?,hiv=?,spilis=?,malaria=?,"
//                        + "petugas_u_saring=?,tgl_lahir=?,nik=?", 25, new String[]{
//                            NomorDonor.getText(), NamaPendonor.getText(), Valid.SetTgl(Tanggal.getSelectedItem() + ""),
//                            Dinas.getSelectedItem().toString(), JK.getSelectedItem().toString().substring(0, 1), Umur.getText(),
//                            Alamat.getText(), GolonganDarah.getSelectedItem().toString(), Resus.getSelectedItem().toString(),
//                            Tensi.getText(), NomorBag.getText(), NomorTelp.getText(), JenisBag.getSelectedItem().toString(),
//                            JenisDonor.getSelectedItem().toString(), TempatAftap.getSelectedItem().toString(),
//                            KodePetugasAftap.getText(), hbs, hcv,
//                            hiv, spilis,
//                            malar, KodePetugasUSaring.getText(),
//                            Valid.SetTgl(tgl_lahir.getSelectedItem() + ""), nik.getText(), tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString()
//                        }) == true) {
                if (Sequel.mengedittf("utd_donor", "no_donor=?", "tanggal=?,dinas=?,"
                        + "tensi=?,no_bag=?,jenis_bag=?,"
                        + "jenis_donor=?,tempat_aftap=?,petugas_aftap=?,hbsag=?,hcv=?,hiv=?,spilis=?,malaria=?,"
                        + "stts_pengambilan=?,petugas_u_saring=?", 16, new String[]{
                            Valid.SetTgl(Tanggal.getSelectedItem() + ""),
                            Dinas.getSelectedItem().toString(),
                            Tensi.getText(), NomorBag.getText(), JenisBag.getSelectedItem().toString(),
                            JenisDonor.getSelectedItem().toString(), TempatAftap.getSelectedItem().toString(),
                            KodePetugasAftap.getText(), hbs, hcv,
                            hiv, spilis,
                            malar, Pengambilan.getSelectedItem().toString(), KodePetugasUSaring.getText(),
                            tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString()
                        }) == true) {
                    Sequel.queryu2("update utd_pendonor set nama=?,jk=?,alamat=?,golongan_darah=?,resus=?,no_telp=?,tgl_lahir=?,no_ktp=? where no_pendonor=?", 9, new String[]{
                        NamaPendonor.getText().toUpperCase(), JK.getSelectedItem().toString().substring(0, 1), Alamat.getText().toUpperCase(), GolonganDarah.getSelectedItem().toString(), Resus.getSelectedItem().toString(),
                        NomorTelp.getText(), Valid.SetTgl(tgl_lahir.getSelectedItem() + ""), nik.getText(), NomorPendonor1.getText()

                    });
                    try {
                        pscekmedis = koneksi.prepareStatement(sqlpscekmedis);
                        try {
                            pscekmedis.setString(1, tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());
                            rs = pscekmedis.executeQuery();
                            while (rs.next()) {
                                Sequel.menyimpan("utd_stok_medis", "'" + rs.getString("kode_brng") + "','" + rs.getString("jml") + "','" + rs.getDouble("harga") + "'",
                                        "stok=stok+" + rs.getString("jml") + "", "kode_brng='" + rs.getString("kode_brng") + "'");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs != null) {
                                rs.close();
                            }
                            if (pscekmedis != null) {
                                pscekmedis.close();
                            }
                        }
                        Sequel.meghapus("utd_penggunaan_medis_donor", "no_donor", tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());

                        psceknonmedis = koneksi.prepareStatement(sqlpsceknonmedis);
                        try {
                            psceknonmedis.setString(1, tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());
                            rs2 = psceknonmedis.executeQuery();
                            while (rs2.next()) {
                                Sequel.menyimpan("utd_stok_penunjang", "'" + rs2.getString("kode_brng") + "','" + rs2.getString("jml") + "','" + rs2.getDouble("harga") + "'",
                                        "stok=stok+" + rs2.getString("jml") + "", "kode_brng='" + rs2.getString("kode_brng") + "'");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psceknonmedis != null) {
                                psceknonmedis.close();
                            }
                        }
                        Sequel.meghapus("utd_penggunaan_penunjang_donor", "no_donor", tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());

                        for (i = 0; i < tbMedis.getRowCount(); i++) {
                            try {
                                if (Valid.SetAngka(tbMedis.getValueAt(i, 0).toString()) > 0) {
                                    if (Sequel.menyimpantf2("utd_penggunaan_medis_donor", "?,?,?,?,?", "BHP Medis", 5, new String[]{
                                        NomorDonor.getText(), tbMedis.getValueAt(i, 1).toString(), tbMedis.getValueAt(i, 0).toString(), tbMedis.getValueAt(i, 3).toString(),
                                        Double.toString(Double.parseDouble(tbMedis.getValueAt(i, 0).toString()) * Double.parseDouble(tbMedis.getValueAt(i, 3).toString()))
                                    }) == true) {
                                        Sequel.menyimpan("utd_stok_medis", "'" + tbMedis.getValueAt(i, 1).toString() + "','-" + tbMedis.getValueAt(i, 0).toString() + "','" + tbMedis.getValueAt(i, 3).toString() + "'",
                                                "stok=stok-" + tbMedis.getValueAt(i, 0).toString() + "", "kode_brng='" + tbMedis.getValueAt(i, 1).toString() + "'");
                                        tbMedis.setValueAt(null, i, 0);
                                        tbMedis.setValueAt(0, i, 4);
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                        for (i = 0; i < tbNonMedis.getRowCount(); i++) {
                            try {
                                if (Valid.SetAngka(tbNonMedis.getValueAt(i, 0).toString()) > 0) {
                                    if (Sequel.menyimpantf2("utd_penggunaan_penunjang_donor", "?,?,?,?,?", "BHP Non Medis", 5, new String[]{
                                        NomorDonor.getText(), tbNonMedis.getValueAt(i, 1).toString(), tbNonMedis.getValueAt(i, 0).toString(), tbNonMedis.getValueAt(i, 3).toString(),
                                        Double.toString(Double.parseDouble(tbNonMedis.getValueAt(i, 0).toString()) * Double.parseDouble(tbNonMedis.getValueAt(i, 3).toString()))
                                    }) == true) {
                                        Sequel.menyimpan("utd_stok_penunjang", "'" + tbNonMedis.getValueAt(i, 1).toString() + "','-" + tbNonMedis.getValueAt(i, 0).toString() + "','" + tbNonMedis.getValueAt(i, 3).toString() + "'",
                                                "stok=stok-" + tbNonMedis.getValueAt(i, 0).toString() + "", "kode_brng='" + tbNonMedis.getValueAt(i, 1).toString() + "'");
                                        tbNonMedis.setValueAt(null, i, 0);
                                        tbNonMedis.setValueAt(0, i, 4);
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    }
                    emptTeks();
                    TabRawat.setSelectedIndex(2);
                    JOptionPane.showMessageDialog(null, "Proses input selesai..");
                }
                tampil();
            } else {
                JOptionPane.showMessageDialog(null, "Maaf,Pilih pada nomor donor...!!!!");
            }
        }
    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabModeTranfusi.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        } else if (tabModeTranfusi.getRowCount() != 0) {
            Sequel.queryu("truncate table temporary");
            jml = tabModeTranfusi.getRowCount();
            for (i = 0; i < jml; i++) {
                Sequel.menyimpan("temporary", "'0','"
                        + tabModeTranfusi.getValueAt(i, 0).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 1).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 2).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 3).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 4).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 5).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 6).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 7).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 8).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 9).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 10).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 11).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 12).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 13).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 14).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 15).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 16).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 17).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 18).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 19).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 20).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 21).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 22).toString() + "','"
                        + tabModeTranfusi.getValueAt(i, 23).toString() + "','','','','','','','','','','','','',''", "Transaksi Pembelian");
            }

            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptDonorDarah.jrxml", "report", "::[ Data Donor Darah ]::",
                    "select * from temporary order by no asc", param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        aktifkan = "";
        if (TabRawat.getSelectedIndex() == 0) {
            tampilPendonor();
        }
        if (TabRawat.getSelectedIndex() == 2) {
            tampil();
        }
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            tampil();
            TCari.setText("");
        } else {
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnAll, TCari);
        }
    }//GEN-LAST:event_BtnKeluarKeyPressed

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
        if (TabRawat.getSelectedIndex() == 0) {
            tampilPendonor();
        }
        if (TabRawat.getSelectedIndex() == 2) {
            tampil();
        } else if (TabRawat.getSelectedIndex() == 1) {
            tampilMedis();
            tampilNonMedis();
        } else {
            tampilTotalDonor();
        };
//       // aktifkan = "";
//        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//        if (TabRawat.getSelectedIndex()==1){
//            tampil();
////            System.out.println(TabRawat.getSelectedIndex());
//        } else if(TabRawat.getSelectedIndex()==2){
//            tampilTotalDonor();
////            System.out.println("Ini Tab Daftar Donor");
//        }
//        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt, NomorDonor, Dinas);
//        autoNomer();
        isNumber();
    }//GEN-LAST:event_TanggalKeyPressed

    private void AlamatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlamatKeyPressed
        Valid.pindah(evt, Umur, GolonganDarah);
    }//GEN-LAST:event_AlamatKeyPressed

    private void TensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TensiKeyPressed
        Valid.pindah(evt, Resus, NomorBag);
    }//GEN-LAST:event_TensiKeyPressed

    private void NomorTelpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NomorTelpKeyPressed
        Valid.pindah(evt, JenisBag, JenisDonor);
    }//GEN-LAST:event_NomorTelpKeyPressed

    private void KodePetugasAftapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodePetugasAftapKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select nama from petugas where nip=?", NamaPetugasAftap, KodePetugasAftap.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnPetugasAftapActionPerformed(null);
        } else {
            Valid.pindah(evt, TempatAftap, HBSAg);
        }
    }//GEN-LAST:event_KodePetugasAftapKeyPressed

    private void btnPetugasAftapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasAftapActionPerformed
        pilih = 1;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasAftapActionPerformed

    private void HBSAgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HBSAgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HBSAgActionPerformed

    private void KodePetugasUSaringKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodePetugasUSaringKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select nama from petugas where nip=?", NamaPetugasUSaring, KodePetugasUSaring.getText());
            TCariMedis.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnPetugasUSaringActionPerformed(null);
        } else {
            Valid.pindah(evt, Malaria, BtnSimpan);
        }
    }//GEN-LAST:event_KodePetugasUSaringKeyPressed

    private void btnPetugasUSaringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasUSaringActionPerformed
        pilih = 2;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasUSaringActionPerformed

    private void DinasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DinasKeyPressed
        Valid.pindah(evt, Tanggal, NamaPendonor);
    }//GEN-LAST:event_DinasKeyPressed

    private void JKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JKKeyPressed
        Valid.pindah(evt, NamaPendonor, Umur);
    }//GEN-LAST:event_JKKeyPressed

    private void GolonganDarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GolonganDarahKeyPressed
        Valid.pindah(evt, Alamat, Resus);
    }//GEN-LAST:event_GolonganDarahKeyPressed

    private void JenisBagKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisBagKeyPressed
        Valid.pindah(evt, NomorBag, NomorTelp);
    }//GEN-LAST:event_JenisBagKeyPressed

    private void TempatAftapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TempatAftapKeyPressed
        Valid.pindah(evt, JenisDonor, KodePetugasAftap);
    }//GEN-LAST:event_TempatAftapKeyPressed

    private void HBSAgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HBSAgKeyPressed
        Valid.pindah(evt, KodePetugasAftap, HCV);
    }//GEN-LAST:event_HBSAgKeyPressed

    private void HCVKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HCVKeyPressed
        Valid.pindah(evt, HBSAg, HIV);
    }//GEN-LAST:event_HCVKeyPressed

    private void HIVKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HIVKeyPressed
        Valid.pindah(evt, HCV, Spilis);
    }//GEN-LAST:event_HIVKeyPressed

    private void SpilisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SpilisKeyPressed
        Valid.pindah(evt, HIV, Malaria);
    }//GEN-LAST:event_SpilisKeyPressed

    private void ppTampilkanBHPMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTampilkanBHPMedisActionPerformed
        aktifkan = "medis";
        tampil();
    }//GEN-LAST:event_ppTampilkanBHPMedisActionPerformed

    private void ppTampilkanBHPPenunjangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTampilkanBHPPenunjangActionPerformed
        aktifkan = "nonmedis";
        tampil();
    }//GEN-LAST:event_ppTampilkanBHPPenunjangActionPerformed

    private void ppTampilkanBHPPenunjangDanMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTampilkanBHPPenunjangDanMedisActionPerformed
        aktifkan = "medis&nonmedis";
        tampil();
    }//GEN-LAST:event_ppTampilkanBHPPenunjangDanMedisActionPerformed

    private void ppHapusBHPMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusBHPMedisActionPerformed
        if (tabModeTranfusi.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else if (tbTranfusiDarah.getSelectedRow() <= -1) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data yang mau dihapus..!!");
        } else {
            if (!tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString().equals("")) {
                int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, Yakin mau dihapus..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    try {

                        pscekmedis = koneksi.prepareStatement(sqlpscekmedis);
                        try {
                            pscekmedis.setString(1, tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());
                            rs = pscekmedis.executeQuery();
                            while (rs.next()) {
                                Sequel.menyimpan("utd_stok_medis", "'" + rs.getString("kode_brng") + "','" + rs.getString("jml") + "','" + rs.getDouble("harga") + "'",
                                        "stok=stok+" + rs.getString("jml") + "", "kode_brng='" + rs.getString("kode_brng") + "'");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs != null) {
                                rs.close();
                            }
                            if (pscekmedis != null) {
                                pscekmedis.close();
                            }
                        }
                        Sequel.meghapus("utd_penggunaan_medis_donor", "no_donor", tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());

                        tampil();
                        emptTeks();
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Maaf,Pilih pada nomor donor...!!!!");
            }
        }
    }//GEN-LAST:event_ppHapusBHPMedisActionPerformed

    private void ppHapusBHPNonMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusBHPNonMedisActionPerformed
        if (tabModeTranfusi.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else if (tbTranfusiDarah.getSelectedRow() <= -1) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data yang mau dihapus..!!");
        } else {
            if (!tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString().equals("")) {
                int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, Yakin mau dihapus..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    try {

                        psceknonmedis = koneksi.prepareStatement(sqlpsceknonmedis);
                        try {
                            psceknonmedis.setString(1, tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());
                            rs2 = psceknonmedis.executeQuery();
                            while (rs2.next()) {
                                Sequel.menyimpan("utd_stok_penunjang", "'" + rs2.getString("kode_brng") + "','" + rs2.getString("jml") + "','" + rs2.getDouble("harga") + "'",
                                        "stok=stok+" + rs2.getString("jml") + "", "kode_brng='" + rs2.getString("kode_brng") + "'");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psceknonmedis != null) {
                                psceknonmedis.close();
                            }
                        }
                        Sequel.meghapus("utd_penggunaan_penunjang_donor", "no_donor", tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());

                        tampil();
                        emptTeks();
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Maaf,Pilih pada nomor donor...!!!!");
            }
        }
    }//GEN-LAST:event_ppHapusBHPNonMedisActionPerformed

    private void ppHapusBHPMedisDanNonMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusBHPMedisDanNonMedisActionPerformed
        if (tabModeTranfusi.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else if (tbTranfusiDarah.getSelectedRow() <= -1) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data yang mau dihapus..!!");
        } else {
            if (!tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString().equals("")) {
                int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, Yakin mau dihapus..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    try {

                        pscekmedis = koneksi.prepareStatement(sqlpscekmedis);
                        try {
                            pscekmedis.setString(1, tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());
                            rs = pscekmedis.executeQuery();
                            while (rs.next()) {
                                Sequel.menyimpan("utd_stok_medis", "'" + rs.getString("kode_brng") + "','" + rs.getString("jml") + "','" + rs.getDouble("harga") + "'",
                                        "stok=stok+" + rs.getString("jml") + "", "kode_brng='" + rs.getString("kode_brng") + "'");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs != null) {
                                rs.close();
                            }
                            if (pscekmedis != null) {
                                pscekmedis.close();
                            }
                        }
                        Sequel.meghapus("utd_penggunaan_medis_donor", "no_donor", tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());
                        psceknonmedis = koneksi.prepareStatement(sqlpsceknonmedis);
                        try {
                            psceknonmedis.setString(1, tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());
                            rs2 = psceknonmedis.executeQuery();
                            while (rs2.next()) {
                                Sequel.menyimpan("utd_stok_penunjang", "'" + rs2.getString("kode_brng") + "','" + rs2.getString("jml") + "','" + rs2.getDouble("harga") + "'",
                                        "stok=stok+" + rs2.getString("jml") + "", "kode_brng='" + rs2.getString("kode_brng") + "'");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psceknonmedis != null) {
                                psceknonmedis.close();
                            }
                        }
                        Sequel.meghapus("utd_penggunaan_penunjang_donor", "no_donor", tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());

                        tampil();
                        emptTeks();
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Maaf,Pilih pada nomor donor...!!!!");
            }
        }
    }//GEN-LAST:event_ppHapusBHPMedisDanNonMedisActionPerformed

    private void ppUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppUbahActionPerformed
        if (tabModeTranfusi.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else if (tbTranfusiDarah.getSelectedRow() <= -1) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data yang mau diubah..!!");
        } else {
            NomorDonor.setText("");
            if (!tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString().equals("")) {
                if (tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 13).toString().equals("")) {
                    TabRawat.setSelectedIndex(1);

                    TempatAftap.setVisible(true);
                    jLabel14.setVisible(true);
                    NomorBag.setVisible(true);
                    label34.setVisible(true);
                    GolonganDarah.setVisible(true);
                    jLabel10.setVisible(true);
                    Resus.setVisible(true);
                    jLabel11.setVisible(true);
                    Tensi.setVisible(true);
                    label39.setVisible(true);
                    jLabel12.setVisible(true);
                    JenisBag.setVisible(true);
                    JenisDonor.setVisible(true);
                    jLabel13.setVisible(true);
                    NamaPetugasAftap.setVisible(true);
                    KodePetugasAftap.setVisible(true);
                    label18.setVisible(true);
                    btnPetugasAftap.setVisible(true);
                    label17.setVisible(false);
                    HBSAg.setVisible(false);
                    jLabel15.setVisible(false);
                    HIV.setVisible(false);
                    jLabel17.setVisible(false);
                    Malaria.setVisible(false);
                    jLabel19.setVisible(false);
                    HCV.setVisible(false);
                    jLabel16.setVisible(false);
                    Spilis.setVisible(false);
                    jLabel18.setVisible(false);
                    KodePetugasUSaring.setVisible(false);
                    NamaPetugasUSaring.setVisible(false);
                    label19.setVisible(false);
                    btnPetugasUSaring.setVisible(false);
                    jLabel22.setVisible(true);
                    Pengambilan.setVisible(true);
                    NomorDonor.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());
                    NomorPendonor1.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 1).toString());
                    NamaPendonor.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 2).toString());
                    Valid.SetTgl(Tanggal, tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 4).toString());
                    Dinas.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 5).toString());
                    if (tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 6).toString().equals("L")) {
                        JK.setSelectedIndex(0);
                    } else {
                        JK.setSelectedIndex(1);
                    }
                    nik.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 3).toString());
                    Valid.SetTgl(tgl_lahir, tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 7).toString());
                    Umur.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 8).toString());
                    Alamat.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 9).toString());
                    GolonganDarah.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 10).toString());
                    Resus.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 11).toString());
//                    Tensi.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 12).toString());
//                    NomorBag.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 13).toString());
                    NomorTelp.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 14).toString());
//                    JenisBag.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(),14).toString());
//                    JenisDonor.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(),15).toString());
//                    TempatAftap.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(),16).toString());
                    NamaPetugasAftap.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 19).toString());
                    KodePetugasAftap.setText(Sequel.cariIsi("select petugas_aftap from utd_donor where no_donor=?", tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString()));
//                    HBSAg.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(),18).toString());
//                    HCV.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(),19).toString());
//                    HIV.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(),20).toString());
//                    Spilis.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(),21).toString());
//                    Malaria.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(),22).toString());
                    NamaPetugasUSaring.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 25).toString());
                    KodePetugasUSaring.setText(Sequel.cariIsi("select petugas_u_saring from utd_donor where no_donor=?", tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString()));
                    try {
                        Valid.tabelKosong(tabModeMedis);
                        pscekmedis = koneksi.prepareStatement(sqlpscekmedis);
                        try {
                            pscekmedis.setString(1, NomorDonor.getText());
                            rs = pscekmedis.executeQuery();
                            while (rs.next()) {
                                tabModeMedis.addRow(new String[]{
                                    rs.getString("jml"), rs.getString("kode_brng"), rs.getString("nama_brng"),
                                    rs.getString("harga"), rs.getString("total"), rs.getString("kode_sat"), "0"
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs != null) {
                                rs.close();
                            }
                            if (pscekmedis != null) {
                                pscekmedis.close();
                            }
                        }

                        Valid.tabelKosong(tabModeNonMedis);
                        psceknonmedis = koneksi.prepareStatement(sqlpsceknonmedis);
                        try {
                            psceknonmedis.setString(1, NomorDonor.getText());
                            rs2 = psceknonmedis.executeQuery();
                            while (rs2.next()) {
                                tabModeNonMedis.addRow(new String[]{
                                    rs2.getString("jml"), rs2.getString("kode_brng"), rs2.getString("nama_brng"),
                                    rs2.getString("harga"), rs2.getString("total"), rs2.getString("kode_sat"), "0"
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psceknonmedis != null) {
                                psceknonmedis.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Sudah Dilakukan Penginputan Aftap");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Maaf,Pilih pada nomor donor...!!!!");
            }
        }
    }//GEN-LAST:event_ppUbahActionPerformed

    private void ppCekalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppCekalActionPerformed
        if (tabModeTranfusi.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else if (tbTranfusiDarah.getSelectedRow() <= -1) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data darah yang mau dimusnahkan..!!");
        } else {
            if (!tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString().equals("")) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                UTDCekalDarah pemusnahan = new UTDCekalDarah(null, false);
                pemusnahan.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                pemusnahan.setLocationRelativeTo(internalFrame1);
                pemusnahan.emptTeks();
                pemusnahan.isCek();
                pemusnahan.setDarah(
                        tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString(),
                        tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 2).toString(),
                        tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 9).toString(),
                        tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 14).toString()
                );
                pemusnahan.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            } else {
                JOptionPane.showMessageDialog(null, "Maaf,Pilih pada nomor donor...!!!!");
            }
        }
    }//GEN-LAST:event_ppCekalActionPerformed

    private void MalariaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MalariaKeyPressed
        Valid.pindah(evt, Spilis, KodePetugasUSaring);
    }//GEN-LAST:event_MalariaKeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        if (panelisi4.getHeight() < 400) {
            scrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            panelisi4.setPreferredSize(new Dimension(panelisi4.WIDTH, 400));
            if (panelisi4.getWidth() < 530) {
                scrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                panelisi4.setPreferredSize(new Dimension(530, 400));
            } else {
                scrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            }
        } else {
            scrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
            if (panelisi4.getWidth() < 530) {
                scrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                panelisi4.setPreferredSize(new Dimension(530, panelisi4.WIDTH));
            } else {
                scrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            }
        }
    }//GEN-LAST:event_formWindowActivated

    private void tgl_lahirItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_tgl_lahirItemStateChanged
        // TODO add your handling code here:
        Date lahir = tgl_lahir.getDate();
        LocalDate today = LocalDate.now();
        LocalDate birthday = lahir.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period p = Period.between(birthday, today);
        long p2 = ChronoUnit.DAYS.between(birthday, today);
        Umur.setText(String.valueOf(p.getYears()));
    }//GEN-LAST:event_tgl_lahirItemStateChanged

    private void ppBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBaruActionPerformed
        if (tabModeTranfusi.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else if (tbTranfusiDarah.getSelectedRow() <= -1) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data yang mau diubah..!!");
        } else {
            if (!tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString().equals("")) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String inputString1 = tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 4).toString();
                LocalDate month = LocalDate.now();
                String inputString2 = month.format(dtf);
                System.out.println(inputString1);
                System.out.println(inputString2);
                try {
                    LocalDate date1 = LocalDate.parse(inputString1);
                    LocalDate date2 = LocalDate.parse(inputString2);
                    long daysBetween = DAYS.between(date1, date2);
                    System.out.println("Days: " + daysBetween);
                    if (daysBetween < 76) {
                        JOptionPane.showMessageDialog(null, "Maaf,Tidak Bisa Donor Sebelum Lewat 2,5 Bulan");
                    } else {
                        TabRawat.setSelectedIndex(1);

                        NamaPendonor.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 1).toString());
                        if (tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 6).toString().equals("L")) {
                            JK.setSelectedIndex(0);
                        } else {
                            JK.setSelectedIndex(1);
                        }
                        nik.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 3).toString());
                        Valid.SetTgl(tgl_lahir, tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 7).toString());
                        Umur.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 8).toString());
                        Alamat.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 9).toString());
                        GolonganDarah.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 10).toString());
                        NomorTelp.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 14).toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Maaf,Pilih pada nomor donor...!!!!");
            }
        }
    }//GEN-LAST:event_ppBaruActionPerformed

    private void BtnAll3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAll3ActionPerformed
        if (TabRawat.getSelectedIndex() == 0) {
            tampilPendonor();
        } else if (TabRawat.getSelectedIndex() == 2) {
            tampilCari();
        }
    }//GEN-LAST:event_BtnAll3ActionPerformed

    private void BtnEdit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEdit1ActionPerformed
        // TODO add your handling code here:
        BtnEditActionPerformed(evt);
    }//GEN-LAST:event_BtnEdit1ActionPerformed

    private void BtnEdit1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEdit1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnEdit1KeyPressed

    private void ppSaringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSaringActionPerformed
        // TODO add your handling code here:
        if (tabModeTranfusi.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else if (tbTranfusiDarah.getSelectedRow() <= -1) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data yang mau diubah..!!");
        } else {
            if (!tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString().equals("")) {
                if (!tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 13).toString().equals("") && tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 20).toString().equals("-")) {
                    TabRawat.setSelectedIndex(1);

                    TempatAftap.setVisible(true);
                    jLabel14.setVisible(true);
                    NomorBag.setVisible(true);
                    label34.setVisible(true);
                    GolonganDarah.setVisible(true);
                    jLabel10.setVisible(true);
                    Resus.setVisible(true);
                    jLabel11.setVisible(true);
                    Tensi.setVisible(true);
                    label39.setVisible(true);
                    jLabel12.setVisible(true);
                    JenisBag.setVisible(true);
                    JenisDonor.setVisible(true);
                    jLabel13.setVisible(true);
                    NamaPetugasAftap.setVisible(true);
                    KodePetugasAftap.setVisible(true);
                    label18.setVisible(true);
                    btnPetugasAftap.setVisible(true);
                    label17.setVisible(true);
                    HBSAg.setVisible(true);
                    jLabel15.setVisible(true);
                    HIV.setVisible(true);
                    jLabel17.setVisible(true);
                    Malaria.setVisible(true);
                    jLabel19.setVisible(true);
                    HCV.setVisible(true);
                    jLabel16.setVisible(true);
                    Spilis.setVisible(true);
                    jLabel18.setVisible(true);
                    KodePetugasUSaring.setVisible(true);
                    NamaPetugasUSaring.setVisible(true);
                    label19.setVisible(true);
                    btnPetugasUSaring.setVisible(true);
                    jLabel22.setVisible(true);
                    Pengambilan.setVisible(true);
                    NomorPendonor1.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 1).toString());
                    Pengambilan.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 17).toString());
                    NomorDonor.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString());
                    NamaPendonor.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 2).toString());
                    Valid.SetTgl(Tanggal, tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 4).toString());
                    Dinas.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 5).toString());
                    if (tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 6).toString().equals("L")) {
                        JK.setSelectedIndex(0);
                    } else {
                        JK.setSelectedIndex(1);
                    }
                    nik.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 3).toString());
                    Valid.SetTgl(tgl_lahir, tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 7).toString());
                    Umur.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 8).toString());
                    Alamat.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 9).toString());
                    GolonganDarah.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 10).toString());
                    Resus.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 11).toString());
                    Tensi.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 12).toString());
                    NomorBag.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 13).toString());
                    NomorTelp.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 14).toString());
                    JenisBag.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 15).toString());
                    JenisDonor.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 16).toString());
                    TempatAftap.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 18).toString());
                    NamaPetugasAftap.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 19).toString());
                    KodePetugasAftap.setText(Sequel.cariIsi("select petugas_aftap from utd_donor where no_donor=?", tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString()));
//                    HBSAg.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(),18).toString());
//                    HCV.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(),19).toString());
//                    HIV.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(),20).toString());
//                    Spilis.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(),21).toString());
//                    Malaria.setSelectedItem(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(),22).toString());
                    NamaPetugasUSaring.setText(tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 25).toString());
                    KodePetugasUSaring.setText(Sequel.cariIsi("select petugas_u_saring from utd_donor where no_donor=?", tbTranfusiDarah.getValueAt(tbTranfusiDarah.getSelectedRow(), 0).toString()));
                    try {
                        Valid.tabelKosong(tabModeMedis);
                        pscekmedis = koneksi.prepareStatement(sqlpscekmedis);
                        try {
                            pscekmedis.setString(1, NomorDonor.getText());
                            rs = pscekmedis.executeQuery();
                            while (rs.next()) {
                                tabModeMedis.addRow(new String[]{
                                    rs.getString("jml"), rs.getString("kode_brng"), rs.getString("nama_brng"),
                                    rs.getString("harga"), rs.getString("total"), rs.getString("kode_sat"), "0"
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs != null) {
                                rs.close();
                            }
                            if (pscekmedis != null) {
                                pscekmedis.close();
                            }
                        }

                        Valid.tabelKosong(tabModeNonMedis);
                        psceknonmedis = koneksi.prepareStatement(sqlpsceknonmedis);
                        try {
                            psceknonmedis.setString(1, NomorDonor.getText());
                            rs2 = psceknonmedis.executeQuery();
                            while (rs2.next()) {
                                tabModeNonMedis.addRow(new String[]{
                                    rs2.getString("jml"), rs2.getString("kode_brng"), rs2.getString("nama_brng"),
                                    rs2.getString("harga"), rs2.getString("total"), rs2.getString("kode_sat"), "0"
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psceknonmedis != null) {
                                psceknonmedis.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Sudah Dilakukan Penginputan Uji Saring / Belum Dilakukan Aftap");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Maaf,Pilih pada nomor donor...!!!!");
            }
        }
    }//GEN-LAST:event_ppSaringActionPerformed

    private void NomorDonorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NomorDonorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NomorDonorActionPerformed

    private void printWBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printWBActionPerformed
        if (tabModeTranfusi.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else if (tbTranfusiDarah.getSelectedRow() <= -1) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data yang mau diubah..!!");
        } else {
            TabRawat.setSelectedIndex(3);
            tampilTotalDonor();
        }
    }//GEN-LAST:event_printWBActionPerformed

    private void ScrollMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ScrollMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_ScrollMouseClicked

    private void tbTotalDonorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTotalDonorMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbTotalDonorMouseClicked

    private void tbTotalDonorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTotalDonorKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbTotalDonorKeyPressed

    private void Scroll3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Scroll3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Scroll3MouseClicked

    private void JenisBagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JenisBagActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JenisBagActionPerformed

    private void TCariNonMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TCariNonMedisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TCariNonMedisActionPerformed

    private void TanggalCari2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TanggalCari2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalCari2ActionPerformed

    private void nikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nikActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nikActionPerformed

    private void BtnWAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnWAActionPerformed
        try {
            System.out.println("Mulai Mencari Data Pasien Yang Akan Dikirim Pesan WA .........");
            LocalDate d = LocalDate.now().minusMonths(3);
            LocalDate d2 = LocalDate.now().minusMonths(4);
            String tgl = d.toString();
            String tgl2 = d2.toString();
            String query = "select nama, no_telp, jk, tanggal, datediff(now(),tanggal) as hari from utd_donor where tanggal < '" + tgl + "' and tanggal > '" + tgl2 + "' order by tanggal ASC";
            ps3 = koneksi.prepareStatement(query);
            try {
                rs3 = ps3.executeQuery();
                while (rs3.next()) {
                    System.out.println("Mulai Memilah Data .........");
                    if (rs3.getString("jk").equals("L")) {
                        if (rs3.getInt("hari") >= 76) {
                            if (rs3.getString("no_telp").length() > 11) {
                                System.out.println("Mulai Mengirim WA");
                                kirimwa.sendwaUTD(rs3.getString("nama"), rs3.getString("no_telp"), rs3.getString("tanggal"));
                            }
                        }
                    }
                    if (rs3.getString("jk").equals("P")) {
                        if (rs3.getInt("hari") >= 90) {
                            if (rs3.getString("no_telp").length() > 11) {
                                System.out.println("Mulai Mengirim WA");
                                kirimwa.sendwaUTD(rs3.getString("nama"), rs3.getString("no_telp"), rs3.getString("tanggal"));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs3 != null) {
                    rs3.close();
                }
                if (ps3 != null) {
                    ps3.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : " + e);
        }
        JOptionPane.showMessageDialog(null, "Proses kirim whatsapp selesai...");
    }//GEN-LAST:event_BtnWAActionPerformed

    private void BtnWAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnWAKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnWAKeyPressed

    private void TanggalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TanggalItemStateChanged
        isNumber();
    }//GEN-LAST:event_TanggalItemStateChanged

    private void tbDataMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataMouseReleased
    }//GEN-LAST:event_tbDataMouseReleased

    private void tbDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataMouseClicked
        if (tabModePendonor.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbDataMouseClicked

    private void tbDataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDataKeyPressed
        if (tabModePendonor.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbDataKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void NomorPendonorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NomorPendonorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NomorPendonorActionPerformed

    private void NamaPendonor1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaPendonor1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NamaPendonor1KeyPressed

    private void GolDarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GolDarahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GolDarahKeyPressed

    private void NomorTelp1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NomorTelp1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NomorTelp1KeyPressed

    private void tgl_lahir1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_tgl_lahir1ItemStateChanged

    }//GEN-LAST:event_tgl_lahir1ItemStateChanged

    private void tempatlahirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tempatlahirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tempatlahirActionPerformed

    private void Resus1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Resus1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Resus1KeyPressed

    private void nik1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nik1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nik1ActionPerformed

    private void KelurahanMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KelurahanMouseMoved
        if (Kelurahan.getText().equals("KELURAHAN")) {
            Kelurahan.setText("");
        }
    }//GEN-LAST:event_KelurahanMouseMoved

    private void KelurahanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KelurahanMouseExited
        if (Kelurahan.getText().equals("")) {
            Kelurahan.setText("KELURAHAN");
        }
    }//GEN-LAST:event_KelurahanMouseExited

    private void KelurahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelurahanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (Kelurahan.getText().equals("")) {
                Kelurahan.setText("KELURAHAN");
            }
            if (Kecamatan.getText().equals("KECAMATAN")) {
                Kecamatan.setText("");
            }
            Kecamatan.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            if (Kelurahan.getText().equals("")) {
                Kelurahan.setText("KELURAHAN");
            }
            if (Alamat.getText().equals("ALAMAT")) {
                Alamat.setText("");
            }
            Alamat.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnKelurahanActionPerformed(null);
        }
    }//GEN-LAST:event_KelurahanKeyPressed

    private void BtnKelurahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKelurahanActionPerformed
        var.setform("UTDDonor");
        pilih = 1;
        kel.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        kel.setLocationRelativeTo(internalFrame1);
        kel.setVisible(true);
    }//GEN-LAST:event_BtnKelurahanActionPerformed

    private void KecamatanMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KecamatanMouseMoved
        if (Kecamatan.getText().equals("KECAMATAN")) {
            Kecamatan.setText("");
        }
    }//GEN-LAST:event_KecamatanMouseMoved

    private void KecamatanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KecamatanMouseExited
        if (Kecamatan.getText().equals("")) {
            Kecamatan.setText("KECAMATAN");
        }
    }//GEN-LAST:event_KecamatanMouseExited

    private void KecamatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KecamatanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (Kecamatan.getText().equals("")) {
                Kecamatan.setText("KECAMATAN");
            }
            if (Kabupaten.getText().equals("KABUPATEN")) {
                Kabupaten.setText("");
            }
            Kabupaten.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            if (Kecamatan.getText().equals("")) {
                Kecamatan.setText("KECAMATAN");
            }
            if (Kelurahan.getText().equals("KELURAHAN")) {
                Kelurahan.setText("");
            }
            Kelurahan.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnKecamatanActionPerformed(null);
        }
    }//GEN-LAST:event_KecamatanKeyPressed

    private void BtnKecamatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKecamatanActionPerformed
        var.setform("UTDDonor");
        pilih = 1;
        kec.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        kec.setLocationRelativeTo(internalFrame1);
        kec.setVisible(true);
    }//GEN-LAST:event_BtnKecamatanActionPerformed

    private void BtnKabupatenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKabupatenActionPerformed
        var.setform("UTDDonor");
        pilih = 1;
        kab.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        kab.setLocationRelativeTo(internalFrame1);
        kab.setVisible(true);
    }//GEN-LAST:event_BtnKabupatenActionPerformed

    private void KabupatenMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KabupatenMouseMoved
        if (Kabupaten.getText().equals("KABUPATEN")) {
            Kabupaten.setText("");
        }
    }//GEN-LAST:event_KabupatenMouseMoved

    private void KabupatenMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_KabupatenMouseExited
        if (Kabupaten.getText().equals("")) {
            Kabupaten.setText("KABUPATEN");
        }
    }//GEN-LAST:event_KabupatenMouseExited

    private void KabupatenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KabupatenKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (Kabupaten.getText().equals("")) {
                Kabupaten.setText("KABUPATEN");
            }
            if (Propinsi.getText().equals("PROPINSI")) {
                Propinsi.setText("");
            }
            Propinsi.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            if (Kabupaten.getText().equals("")) {
                Kabupaten.setText("KABUPATEN");
            }
            if (Kecamatan.getText().equals("KECAMATAN")) {
                Kecamatan.setText("");
            }
            Kecamatan.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnKabupatenActionPerformed(null);
        }
    }//GEN-LAST:event_KabupatenKeyPressed

    private void BtnPropinsiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPropinsiActionPerformed
        var.setform("UTDDonor");
        pilih = 1;
        prop.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        prop.setLocationRelativeTo(internalFrame1);
        prop.setVisible(true);
    }//GEN-LAST:event_BtnPropinsiActionPerformed

    private void PropinsiMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PropinsiMouseMoved
        if (Propinsi.getText().equals("PROPINSI")) {
            Propinsi.setText("");
        }
    }//GEN-LAST:event_PropinsiMouseMoved

    private void PropinsiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PropinsiMouseExited
        if (Propinsi.getText().equals("")) {
            Propinsi.setText("PROPINSI");
        }
    }//GEN-LAST:event_PropinsiMouseExited

    private void PropinsiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PropinsiKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (Propinsi.getText().equals("")) {
                Propinsi.setText("PROPINSI");
            }
            if (Alamat1.getText().equals("ALAMAT")) {
                Alamat1.setText("");
            }
            Alamat1.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            if (Propinsi.getText().equals("")) {
                Propinsi.setText("PROPINSI");
            }
            if (Kabupaten.getText().equals("KABUPATEN")) {
                Kabupaten.setText("");
            }
            Kabupaten.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnPropinsiActionPerformed(null);
        }
    }//GEN-LAST:event_PropinsiKeyPressed

    private void MnDaftarBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDaftarBaruActionPerformed
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String inputString1 = Sequel.cariIsi("select tanggal from utd_donor where no_pendonor='" + NomorPendonor.getText() + "' order by tanggal desc");
        LocalDate month = LocalDate.now();
        String inputString2 = month.format(dtf);
        System.out.println(inputString1);
        System.out.println(inputString2);
        if (tabModePendonor.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        } else if (tbData.getSelectedRow() <= -1) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data yang mau diubah..!!");
        } else if (NamaPendonor1.getText().trim().equals("")) {
            Valid.textKosong(NamaPendonor1, "Nama Pendonor");
        } else if (nik1.getText().trim().equals("")) {
            Valid.textKosong(nik1, "NIK");
        } else {
            if (inputString1.equals("")) {
                insertData();
            }
            if (!inputString1.equals("")) {
                try {
                    LocalDate date1 = LocalDate.parse(inputString1);
                    LocalDate date2 = LocalDate.parse(inputString2);
                    long daysBetween = DAYS.between(date1, date2);
                    System.out.println("Days: " + daysBetween);
                    if (daysBetween < 76) {
                        JOptionPane.showMessageDialog(null, "Maaf,Tidak Bisa Donor Sebelum Lewat 2,5 Bulan");
                    } else {
                        insertData();
                    }
                } catch (Exception e) {
                }
            }
            
        }
    }//GEN-LAST:event_MnDaftarBaruActionPerformed

    private void GantiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GantiActionPerformed
        if (TabRawat.getSelectedIndex() == 0) {
            if (NomorPendonor.getText().trim().equals("")) {
                Valid.textKosong(NomorPendonor, "Nomor Donor");
            } else if (NamaPendonor1.getText().trim().equals("")) {
                Valid.textKosong(NamaPendonor1, "Nama Donor");
            } else if (nik1.getText().trim().equals("")) {
                Valid.textKosong(nik1, "NIK");
            } else if (tempatlahir.getText().trim().equals("")) {
                Valid.textKosong(tempatlahir, "Tempat Lahir");
            } else if (Alamat1.getText().trim().equals("")) {
                Valid.textKosong(Alamat1, "Alamat");
            } else if (Propinsi.getText().trim().equals("")) {
                Valid.textKosong(Propinsi, "Provinsi");
            } else if (Kabupaten.getText().trim().equals("")) {
                Valid.textKosong(Kabupaten, "Kabupaten");
            } else if (Kecamatan.getText().trim().equals("")) {
                Valid.textKosong(Kecamatan, "Kecamatan");
            } else if (Kelurahan.getText().trim().equals("")) {
                Valid.textKosong(Kelurahan, "Kelurahan");
            } else {
//                isNumber2();
            int reply = JOptionPane.showConfirmDialog(rootPane, "Yakin ingin mengedit data pendonor..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    if (Sequel.mengedittf("utd_pendonor", "no_pendonor=?", "nama=?,no_ktp=?,jk=?,tmp_lahir=?,tgl_lahir=?,alamat=?,kd_kel=?,kd_kec=?,kd_kab=?,kd_prop=?,golongan_darah=?,resus=?,no_telp=?", 14,
                            new String[]{
                                NamaPendonor1.getText().toUpperCase(), nik1.getText(), JK1.getSelectedItem().toString().substring(0, 1), tempatlahir.getText().toUpperCase(), Valid.SetTgl(tgl_lahir1.getSelectedItem() + ""),
                                Alamat1.getText().toUpperCase(), kdkel, kdkec, kdkab, kdprop, GolDarah.getSelectedItem().toString(), Resus1.getSelectedItem().toString(), NomorTelp1.getText(), NomorPendonor.getText()
                            }) == true) {
                        if (var.getform().equals("UTDDonor")) {
                            TCari.setText(NomorPendonor.getText());
                        }
                        emptTeks();
                        tampilPendonor();
                    }
                }                
            }
        }
    }//GEN-LAST:event_GantiActionPerformed

    private void GantiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GantiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GantiKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            UTDDonor dialog = new UTDDonor(new javax.swing.JFrame(), true);
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
    private widget.TextBox Alamat1;
    private widget.Button BtnAll;
    private widget.Button BtnAll1;
    private widget.Button BtnAll2;
    private widget.Button BtnAll3;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCari2;
    private widget.Button BtnCari3;
    private widget.Button BtnEdit;
    private widget.Button BtnEdit1;
    private widget.Button BtnHapus;
    private widget.Button BtnKabupaten;
    private widget.Button BtnKecamatan;
    private widget.Button BtnKeluar;
    private widget.Button BtnKelurahan;
    private widget.Button BtnPrint;
    private widget.Button BtnPropinsi;
    private widget.Button BtnSimpan;
    private widget.Button BtnWA;
    private widget.CekBox ChkInput;
    private widget.ComboBox Dinas;
    private widget.Button Ganti;
    private widget.Label Gol;
    private widget.Label Gol1;
    private widget.ComboBox GolDarah;
    private widget.ComboBox GolonganDarah;
    private widget.ComboBox HBSAg;
    private widget.ComboBox HCV;
    private widget.ComboBox HIV;
    private widget.ComboBox JK;
    private widget.ComboBox JK1;
    private widget.ComboBox JenisBag;
    private widget.ComboBox JenisDonor;
    private widget.TextBox Kabupaten;
    private widget.TextBox Kecamatan;
    private widget.TextBox Kelurahan;
    private widget.TextBox KodePetugasAftap;
    private widget.TextBox KodePetugasUSaring;
    private widget.Label LCount;
    private widget.ComboBox Malaria;
    private javax.swing.JMenuItem MnDaftarBaru;
    private widget.TextBox NamaPendonor;
    private widget.TextBox NamaPendonor1;
    private widget.TextBox NamaPetugasAftap;
    private widget.TextBox NamaPetugasUSaring;
    private widget.TextBox NomorBag;
    private widget.TextBox NomorDonor;
    private widget.TextBox NomorPendonor;
    private widget.TextBox NomorPendonor1;
    private widget.TextBox NomorTelp;
    private widget.TextBox NomorTelp1;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox Pengambilan;
    private widget.TextBox Propinsi;
    private widget.ComboBox Resus;
    private widget.ComboBox Resus1;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll5;
    private widget.ComboBox Spilis;
    private widget.TextBox TCari;
    private widget.TextBox TCariMedis;
    private widget.TextBox TCariNonMedis;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tanggal;
    private widget.Tanggal TanggalCari1;
    private widget.Tanggal TanggalCari2;
    private widget.ComboBox TempatAftap;
    private widget.TextBox Tensi;
    private widget.TextBox Umur;
    private widget.Button btnPetugasAftap;
    private widget.Button btnPetugasUSaring;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
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
    private widget.Label jLabel23;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label32;
    private widget.Label label33;
    private widget.Label label34;
    private widget.Label label35;
    private widget.Label label36;
    private widget.Label label37;
    private widget.Label label38;
    private widget.Label label39;
    private widget.Label label40;
    private widget.Label label41;
    private widget.Label label42;
    private widget.Label label43;
    private widget.Label label47;
    private widget.Label label48;
    private widget.Label label49;
    private widget.Label label51;
    private widget.Label label52;
    private widget.TextBox nik;
    private widget.TextBox nik1;
    private widget.panelisi panelGlass12;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.panelisi panelisi4;
    private widget.panelisi panelisi5;
    private widget.panelisi panelisi6;
    private widget.panelisi panelisi7;
    private javax.swing.JMenuItem ppBaru;
    private javax.swing.JMenuItem ppCekal;
    private javax.swing.JMenuItem ppHapusBHPMedis;
    private javax.swing.JMenuItem ppHapusBHPMedisDanNonMedis;
    private javax.swing.JMenuItem ppHapusBHPNonMedis;
    private javax.swing.JMenuItem ppSaring;
    private javax.swing.JMenuItem ppTampilkanBHPMedis;
    private javax.swing.JMenuItem ppTampilkanBHPPenunjang;
    private javax.swing.JMenuItem ppTampilkanBHPPenunjangDanMedis;
    private javax.swing.JMenuItem ppUbah;
    private javax.swing.JMenuItem printWB;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbData;
    private widget.Table tbMedis;
    private widget.Table tbNonMedis;
    private widget.Table tbTotalDonor;
    private widget.Table tbTranfusiDarah;
    private widget.TextBox tempatlahir;
    private widget.Tanggal tgl_lahir;
    private widget.Tanggal tgl_lahir1;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabModeTranfusi);
        try {
            pstranfusi = koneksi.prepareStatement(
                    "select * from utd_donor inner join utd_pendonor on utd_donor.no_pendonor=utd_pendonor.no_pendonor "
                    + "where utd_donor.tanggal between ? and ? and (utd_donor.no_donor like ? or "
                    + "utd_pendonor.no_pendonor like ? or "
                    + "utd_pendonor.nama like ? or "
                    + "utd_pendonor.alamat like ? or "
                    + "utd_donor.jenis_donor like ? or "
                    + "utd_donor.tempat_aftap like ? or "
                    + "utd_donor.jenis_bag like ? or "
                    + "utd_pendonor.no_ktp like ? or "
                    + "utd_pendonor.tgl_lahir like ? or "
                    + "utd_donor.dinas like ?) order by utd_donor.tanggal,utd_donor.no_donor "
            );
            try {
                pstranfusi.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                pstranfusi.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                pstranfusi.setString(3, "%" + TCari.getText().trim() + "%");
                pstranfusi.setString(4, "%" + TCari.getText().trim() + "%");
                pstranfusi.setString(5, "%" + TCari.getText().trim() + "%");
                pstranfusi.setString(6, "%" + TCari.getText().trim() + "%");
                pstranfusi.setString(7, "%" + TCari.getText().trim() + "%");
                pstranfusi.setString(8, "%" + TCari.getText().trim() + "%");
                pstranfusi.setString(9, "%" + TCari.getText().trim() + "%");
                pstranfusi.setString(10, "%" + TCari.getText().trim() + "%");
                pstranfusi.setString(11, "%" + TCari.getText().trim() + "%");
                pstranfusi.setString(12, "%" + TCari.getText().trim() + "%");
                rstranfusi = pstranfusi.executeQuery();
                while (rstranfusi.next()) {
                    String hbsag = "-";
                    if (rstranfusi.getString("hbsag") == null) {
                        hbsag = "-";
                    } else {
                        hbsag = rstranfusi.getString("hbsag");
                    }
                    tabModeTranfusi.addRow(new Object[]{
                        rstranfusi.getString("no_donor"), rstranfusi.getString("no_pendonor"), rstranfusi.getString("nama"), rstranfusi.getString("no_ktp"), rstranfusi.getString("tanggal"),
                        rstranfusi.getString("dinas"), rstranfusi.getString("jk"), rstranfusi.getString("tgl_lahir"), "",
                        rstranfusi.getString("alamat"),
                        rstranfusi.getString("golongan_darah"), rstranfusi.getString("resus"),
                        rstranfusi.getString("tensi"), rstranfusi.getString("no_bag"), rstranfusi.getString("no_telp"),
                        rstranfusi.getString("jenis_bag"), rstranfusi.getString("jenis_donor"), rstranfusi.getString("stts_pengambilan"), rstranfusi.getString("tempat_aftap"),
                        Sequel.cariIsi("select nama from petugas where nip=?", rstranfusi.getString("petugas_aftap")), hbsag, rstranfusi.getString("hcv"),
                        rstranfusi.getString("hiv"), rstranfusi.getString("spilis"), rstranfusi.getString("malaria"),
                        Sequel.cariIsi("select nama from petugas where nip=?", rstranfusi.getString("petugas_u_saring"))
                    });
                    if (aktifkan.equals("medis")) {
                        pscekmedis = koneksi.prepareStatement(sqlpscekmedis);
                        try {
                            pscekmedis.setString(1, rstranfusi.getString("no_donor"));
                            rs = pscekmedis.executeQuery();
                            if (rs.next()) {
                                i = 1;
                                tabModeTranfusi.addRow(new String[]{
                                    "", "", "", "", "", "", "", "", "Penggunaan BHP Medis :", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
                                });
                            }
                            rs.beforeFirst();
                            while (rs.next()) {
                                tabModeTranfusi.addRow(new String[]{
                                    "", "", "", "", "", "", "", "", i + ". " + rs.getString("nama_brng") + " (" + rs.getString("jml") + " " + rs.getString("kode_sat") + " X " + Valid.SetAngka(rs.getDouble("harga")) + ") = " + Valid.SetAngka(rs.getDouble("total")), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
                                });
                                i++;
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs != null) {
                                rs.close();
                            }
                            if (pscekmedis != null) {
                                pscekmedis.close();
                            }
                        }
                    } else if (aktifkan.equals("nonmedis")) {
                        psceknonmedis = koneksi.prepareStatement(sqlpsceknonmedis);
                        try {
                            psceknonmedis.setString(1, rstranfusi.getString("no_donor"));
                            rs2 = psceknonmedis.executeQuery();
                            if (rs2.next()) {
                                tabModeTranfusi.addRow(new String[]{
                                    "", "", "", "", "", "", "", "", "Penggunaan BHP Non Medis :", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
                                });
                                i = 1;
                            }
                            rs2.beforeFirst();
                            while (rs2.next()) {
                                tabModeTranfusi.addRow(new String[]{
                                    "", "", "", "", "", "", "", "", i + ". " + rs2.getString("nama_brng") + " (" + rs2.getString("jml") + " " + rs2.getString("kode_sat") + " X " + Valid.SetAngka(rs2.getDouble("harga")) + ") = " + Valid.SetAngka(rs2.getDouble("total")), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
                                });
                                i++;
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psceknonmedis != null) {
                                psceknonmedis.close();
                            }
                        }
                    } else if (aktifkan.equals("medis&nonmedis")) {
                        pscekmedis = koneksi.prepareStatement(sqlpscekmedis);
                        try {
                            pscekmedis.setString(1, rstranfusi.getString("no_donor"));
                            rs = pscekmedis.executeQuery();
                            if (rs.next()) {
                                tabModeTranfusi.addRow(new String[]{
                                    "", "", "", "", "", "", "", "", "Penggunaan BHP Medis :", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
                                });
                                i = 1;
                            }
                            rs.beforeFirst();
                            while (rs.next()) {
                                tabModeTranfusi.addRow(new String[]{
                                    "", "", "", "", "", "", "", "", i + ". " + rs.getString("nama_brng") + " (" + rs.getString("jml") + " " + rs.getString("kode_sat") + " X " + Valid.SetAngka(rs.getDouble("harga")) + ") = " + Valid.SetAngka(rs.getDouble("total")), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
                                });
                                i++;
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs != null) {
                                rs.close();
                            }
                            if (pscekmedis != null) {
                                pscekmedis.close();
                            }
                        }

                        psceknonmedis = koneksi.prepareStatement(sqlpsceknonmedis);
                        try {
                            psceknonmedis.setString(1, rstranfusi.getString("no_donor"));
                            rs2 = psceknonmedis.executeQuery();
                            if (rs2.next()) {
                                tabModeTranfusi.addRow(new String[]{
                                    "", "", "", "", "", "", "", "", "Penggunaan BHP Non Medis :", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
                                });
                                i = 1;
                            }
                            rs2.beforeFirst();
                            while (rs2.next()) {
                                tabModeTranfusi.addRow(new String[]{
                                    "", "", "", "", "", "", "", "", i + ". " + rs2.getString("nama_brng") + " (" + rs2.getString("jml") + " " + rs2.getString("kode_sat") + " X " + Valid.SetAngka(rs2.getDouble("harga")) + ") = " + Valid.SetAngka(rs2.getDouble("total")), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
                                });
                                i++;
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : " + e);
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (psceknonmedis != null) {
                                psceknonmedis.close();
                            }
                        }
                    }
                }

            } catch (Exception e) {
                System.out.println(e);
            } finally {
                if (rstranfusi != null) {
                    rstranfusi.close();
                }
                if (pstranfusi != null) {
                    pstranfusi.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabModeTranfusi.getRowCount());
    cek_umur();
    }

    private void tampilCari() {
        Valid.tabelKosong(tabModeTranfusi);
        try {
            pstranfusi = koneksi.prepareStatement(
                    "select * from utd_donor where no_donor like ? or "
                    + "nama like ? or nik like ? or "
                    + "alamat like ? or "
                    + "jenis_donor like ? or "
                    + "tempat_aftap like ? or "
                    + "jenis_bag like ? or "
                    + "dinas like ? GROUP BY nik order by tanggal,no_donor "
            );
            try {
                pstranfusi.setString(1, "%" + TCari.getText().trim() + "%");
                pstranfusi.setString(2, "%" + TCari.getText().trim() + "%");
                pstranfusi.setString(3, "%" + TCari.getText().trim() + "%");
                pstranfusi.setString(4, "%" + TCari.getText().trim() + "%");
                pstranfusi.setString(5, "%" + TCari.getText().trim() + "%");
                pstranfusi.setString(6, "%" + TCari.getText().trim() + "%");
                pstranfusi.setString(7, "%" + TCari.getText().trim() + "%");
                pstranfusi.setString(8, "%" + TCari.getText().trim() + "%");
                rstranfusi = pstranfusi.executeQuery();
                while (rstranfusi.next()) {
                    tabModeTranfusi.addRow(new Object[]{
                        rstranfusi.getString("no_donor"), rstranfusi.getString("nama"), rstranfusi.getString("nik"), rstranfusi.getString("tanggal"),
                        rstranfusi.getString("dinas"), rstranfusi.getString("jk"), rstranfusi.getString("tgl_lahir"), rstranfusi.getString("umur"),
                        rstranfusi.getString("alamat"), rstranfusi.getString("golongan_darah"), rstranfusi.getString("resus"),
                        rstranfusi.getString("tensi"), rstranfusi.getString("no_bag"), rstranfusi.getString("no_telp"),
                        rstranfusi.getString("jenis_bag"), rstranfusi.getString("jenis_donor"), rstranfusi.getString("tempat_aftap"),
                        Sequel.cariIsi("select nama from petugas where nip=?", rstranfusi.getString("petugas_aftap")), rstranfusi.getString("hbsag"), rstranfusi.getString("hcv"),
                        rstranfusi.getString("hiv"), rstranfusi.getString("spilis"), rstranfusi.getString("malaria"),
                        Sequel.cariIsi("select nama from petugas where nip=?", rstranfusi.getString("petugas_u_saring"))
                    });
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                if (rstranfusi != null) {
                    rstranfusi.close();
                }
                if (pstranfusi != null) {
                    pstranfusi.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabModeTranfusi.getRowCount());
    }

    private void tampilMedis() {
        row = tbMedis.getRowCount();
        jml = 0;
        for (i = 0; i < row; i++) {
            try {
                if (Double.parseDouble(tbMedis.getValueAt(i, 0).toString()) > 0) {
                    jml++;
                }
            } catch (Exception e) {
                jml = jml + 0;
            }
        }

        kodebarang = null;
        namabarang = null;
        satuan = null;
        hbeli = null;
        total = null;
        jumlah = null;
        stokasal = null;

        kodebarang = new String[jml];
        namabarang = new String[jml];
        satuan = new String[jml];
        hbeli = new String[jml];
        total = new String[jml];
        jumlah = new String[jml];
        stokasal = new String[jml];
        index = 0;
        for (i = 0; i < row; i++) {
            try {
                if (Double.parseDouble(tbMedis.getValueAt(i, 0).toString()) > 0) {
                    jumlah[index] = tbMedis.getValueAt(i, 0).toString();
                    kodebarang[index] = tbMedis.getValueAt(i, 1).toString();
                    namabarang[index] = tbMedis.getValueAt(i, 2).toString();
                    hbeli[index] = tbMedis.getValueAt(i, 3).toString();
                    total[index] = tbMedis.getValueAt(i, 4).toString();
                    satuan[index] = tbMedis.getValueAt(i, 5).toString();
                    stokasal[index] = tbMedis.getValueAt(i, 6).toString();
                    index++;
                }
            } catch (Exception e) {
            }
        }
        Valid.tabelKosong(tabModeMedis);
        for (i = 0; i < jml; i++) {
            tabModeMedis.addRow(new Object[]{jumlah[i], kodebarang[i], namabarang[i], hbeli[i], total[i], satuan[i], stokasal[i]});
        }

        try {
            ps = koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,utd_stok_medis.hargaterakhir,databarang.kode_sat, "
                    + " utd_stok_medis.stok from databarang inner join utd_stok_medis on databarang.kode_brng=utd_stok_medis.kode_brng "
                    + " where databarang.status='1' and databarang.kode_brng like ? or "
                    + " databarang.status='1' and databarang.nama_brng like ? order by databarang.nama_brng");
            try {
                ps.setString(1, "%" + TCariMedis.getText().trim() + "%");
                ps.setString(2, "%" + TCariMedis.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModeMedis.addRow(new Object[]{null, rs.getString(1), rs.getString(2), rs.getString(3), 0, rs.getString(4), rs.getString(5)});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    private void tampilNonMedis() {
        row = tbNonMedis.getRowCount();
        jml = 0;
        for (i = 0; i < row; i++) {
            try {
                if (Double.parseDouble(tbNonMedis.getValueAt(i, 0).toString()) > 0) {
                    jml++;
                }
            } catch (Exception e) {
                jml = jml + 0;
            }
        }

        kodebarang = null;
        namabarang = null;
        satuan = null;
        hbeli = null;
        total = null;
        jumlah = null;
        stokasal = null;

        kodebarang = new String[jml];
        namabarang = new String[jml];
        satuan = new String[jml];
        hbeli = new String[jml];
        total = new String[jml];
        jumlah = new String[jml];
        stokasal = new String[jml];
        index = 0;
        for (i = 0; i < row; i++) {
            try {
                if (Double.parseDouble(tbNonMedis.getValueAt(i, 0).toString()) > 0) {
                    jumlah[index] = tbNonMedis.getValueAt(i, 0).toString();
                    kodebarang[index] = tbNonMedis.getValueAt(i, 1).toString();
                    namabarang[index] = tbNonMedis.getValueAt(i, 2).toString();
                    hbeli[index] = tbNonMedis.getValueAt(i, 3).toString();
                    total[index] = tbNonMedis.getValueAt(i, 4).toString();
                    satuan[index] = tbNonMedis.getValueAt(i, 5).toString();
                    stokasal[index] = tbNonMedis.getValueAt(i, 6).toString();
                    index++;
                }
            } catch (Exception e) {
            }
        }
        Valid.tabelKosong(tabModeNonMedis);
        for (i = 0; i < jml; i++) {
            tabModeNonMedis.addRow(new Object[]{jumlah[i], kodebarang[i], namabarang[i], hbeli[i], total[i], satuan[i], stokasal[i]});
        }

        try {
            ps2 = koneksi.prepareStatement("select ipsrsbarang.kode_brng, ipsrsbarang.nama_brng,utd_stok_penunjang.hargaterakhir,ipsrsbarang.kode_sat, "
                    + " utd_stok_penunjang.stok from ipsrsbarang inner join utd_stok_penunjang on ipsrsbarang.kode_brng=utd_stok_penunjang.kode_brng "
                    + " where ipsrsbarang.kode_brng like ? or ipsrsbarang.nama_brng like ? order by ipsrsbarang.nama_brng");
            try {
                ps2.setString(1, "%" + TCariNonMedis.getText().trim() + "%");
                ps2.setString(2, "%" + TCariNonMedis.getText().trim() + "%");
                rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    tabModeNonMedis.addRow(new Object[]{null, rs2.getString(1), rs2.getString(2), rs2.getString(3), 0, rs2.getString(4), rs2.getString(5)});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally {
                if (rs2 != null) {
                    rs2.close();
                }
                if (ps2 != null) {
                    ps2.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    private void tampilTotalDonor() {
        Valid.tabelKosong(tabModeTotalDonor);
        try {
            psTotal = koneksi.prepareStatement(""+sqltotaldonor+" and utd_donor.tanggal BETWEEN ? AND ?");
            psTotalLk = koneksi.prepareStatement(""+sqltotaldonor+" and utd_donor.tanggal BETWEEN ? AND ? AND utd_pendonor.jk='L'");
            psTotalPr = koneksi.prepareStatement(""+sqltotaldonor+" and utd_donor.tanggal BETWEEN ? AND ? AND utd_pendonor.jk='P'");
            psUmur1 = koneksi.prepareStatement(""+sqltotaldonor+" and utd_donor.tanggal and TIMESTAMPDIFF(YEAR, utd_pendonor.tgl_lahir, utd_donor.tanggal)=17 AND utd_donor.tanggal BETWEEN ? AND ?");
            psUmur2 = koneksi.prepareStatement(""+sqltotaldonor+" and utd_donor.tanggal and TIMESTAMPDIFF(YEAR, utd_pendonor.tgl_lahir, utd_donor.tanggal) BETWEEN 18 AND 24 AND utd_donor.tanggal BETWEEN ? AND ?");
            psUmur3 = koneksi.prepareStatement(""+sqltotaldonor+" and utd_donor.tanggal and TIMESTAMPDIFF(YEAR, utd_pendonor.tgl_lahir, utd_donor.tanggal) BETWEEN 25 AND 44 AND utd_donor.tanggal BETWEEN ? AND ?");
            psUmur4 = koneksi.prepareStatement(""+sqltotaldonor+" and utd_donor.tanggal and TIMESTAMPDIFF(YEAR, utd_pendonor.tgl_lahir, utd_donor.tanggal) BETWEEN 45 AND 64 AND utd_donor.tanggal BETWEEN ? AND ?");
            psUmur5 = koneksi.prepareStatement(""+sqltotaldonor+" and utd_donor.tanggal and TIMESTAMPDIFF(YEAR, utd_pendonor.tgl_lahir, utd_donor.tanggal)>=65 AND utd_donor.tanggal BETWEEN ? AND ?");
            psOpos = koneksi.prepareStatement(""+sqltotaldonor+" and utd_donor.tanggal BETWEEN ? AND ? AND utd_pendonor.golongan_darah='O' AND utd_pendonor.resus='(+)'");
            psOneg = koneksi.prepareStatement(""+sqltotaldonor+" and utd_donor.tanggal BETWEEN ? AND ? AND utd_pendonor.golongan_darah='O' AND utd_pendonor.resus='(-)'");
            psApos = koneksi.prepareStatement(""+sqltotaldonor+" and utd_donor.tanggal BETWEEN ? AND ? AND utd_pendonor.golongan_darah='A' AND utd_pendonor.resus='(+)'");
            psAneg = koneksi.prepareStatement(""+sqltotaldonor+" and utd_donor.tanggal BETWEEN ? AND ? AND utd_pendonor.golongan_darah='A' AND utd_pendonor.resus='(-)'");
            psBpos = koneksi.prepareStatement(""+sqltotaldonor+" and utd_donor.tanggal BETWEEN ? AND ? AND utd_pendonor.golongan_darah='B' AND utd_pendonor.resus='(+)'");
            psBneg = koneksi.prepareStatement(""+sqltotaldonor+" and utd_donor.tanggal BETWEEN ? AND ? AND utd_pendonor.golongan_darah='B' AND utd_pendonor.resus='(-)'");
            psABpos = koneksi.prepareStatement(""+sqltotaldonor+" and utd_donor.tanggal BETWEEN ? AND ? AND utd_pendonor.golongan_darah='AB' AND utd_pendonor.resus='(+)'");
            psABneg = koneksi.prepareStatement(""+sqltotaldonor+" and utd_donor.tanggal BETWEEN ? AND ? AND utd_pendonor.golongan_darah='AB' AND utd_pendonor.resus='(-)'");
            psjenisdb = koneksi.prepareStatement(""+sqltotaldonor+" and utd_donor.jenis_donor='DB' and utd_donor.tanggal BETWEEN ? AND ?");
            psjenisdp = koneksi.prepareStatement(""+sqltotaldonor+" and utd_donor.jenis_donor='DP' and utd_donor.tanggal BETWEEN ? AND ?");
            psjenisds = koneksi.prepareStatement(""+sqltotaldonor+" and utd_donor.jenis_donor='DS' and utd_donor.tanggal BETWEEN ? AND ?");
            try {
                psTotal.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psTotal.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psTotalLk.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psTotalLk.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psTotalPr.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psTotalPr.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psUmur1.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psUmur1.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psUmur2.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psUmur2.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psUmur3.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psUmur3.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psUmur4.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psUmur4.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psUmur5.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psUmur5.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psOpos.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psOpos.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psOneg.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psOneg.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psApos.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psApos.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psAneg.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psAneg.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psBpos.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psBpos.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psBneg.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psBneg.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psABpos.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psABpos.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psABneg.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psABneg.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psjenisdb.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psjenisdb.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psjenisdp.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psjenisdp.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));
                psjenisds.setString(1, Valid.SetTgl(TanggalCari1.getSelectedItem() + ""));
                psjenisds.setString(2, Valid.SetTgl(TanggalCari2.getSelectedItem() + ""));

                rsTotal = psTotal.executeQuery();
                rsTotalLk = psTotalLk.executeQuery();
                rsTotalPr = psTotalPr.executeQuery();
                rsUmur1 = psUmur1.executeQuery();
                rsUmur2 = psUmur2.executeQuery();
                rsUmur3 = psUmur3.executeQuery();
                rsUmur4 = psUmur4.executeQuery();
                rsUmur5 = psUmur5.executeQuery();
                rsOpos = psOpos.executeQuery();
                rsOneg = psOneg.executeQuery();
                rsApos = psApos.executeQuery();
                rsAneg = psAneg.executeQuery();
                rsBpos = psBpos.executeQuery();
                rsBneg = psBneg.executeQuery();
                rsABpos = psABpos.executeQuery();
                rsABneg = psABneg.executeQuery();
                rsjenisdb = psjenisdb.executeQuery();
                rsjenisdp = psjenisdp.executeQuery();
                rsjenisds = psjenisds.executeQuery();
                while (rsTotal.next()) {
                    totaldonor = rsTotal.getString("jumlah");
                }
                while (rsTotalLk.next()) {
                    totallk = rsTotalLk.getString("jumlah");
                }
                while (rsTotalPr.next()) {
                    totalpr = rsTotalPr.getString("jumlah");
                }
                while (rsUmur1.next()) {
                    umur1 = rsUmur1.getString("jumlah");
                }
                while (rsUmur2.next()) {
                    umur2 = rsUmur2.getString("jumlah");
                }
                while (rsUmur3.next()) {
                    umur3 = rsUmur3.getString("jumlah");
                }
                while (rsUmur4.next()) {
                    umur4 = rsUmur4.getString("jumlah");
                }
                while (rsUmur5.next()) {
                    umur5 = rsUmur5.getString("jumlah");
                }
                while (rsOpos.next()) {
                    opos = rsOpos.getString("jumlah");
                }
                while (rsOneg.next()) {
                    oneg = rsOneg.getString("jumlah");
                }
                while (rsApos.next()) {
                    apos = rsApos.getString("jumlah");
                }
                while (rsAneg.next()) {
                    aneg = rsAneg.getString("jumlah");
                }
                while (rsBpos.next()) {
                    bpos = rsBpos.getString("jumlah");
                }
                while (rsBneg.next()) {
                    bneg = rsBneg.getString("jumlah");
                }
                while (rsABpos.next()) {
                    abpos = rsABpos.getString("jumlah");
                }
                while (rsABneg.next()) {
                    abneg = rsABneg.getString("jumlah");
                }
                while (rsjenisdb.next()) {
                    jenisdb = rsjenisdb.getString("jumlah");
                }
                while (rsjenisdp.next()) {
                    jenisdp = rsjenisdp.getString("jumlah");
                }
                while (rsjenisds.next()) {
                    jenisds = rsjenisds.getString("jumlah");
                }
            } catch (Exception e) {
            }
            tabModeTotalDonor.addRow(new Object[]{
                totaldonor, totallk, totalpr, jenisdb, jenisdp, jenisds, umur1, umur2, umur3, umur4, umur5, opos, oneg, apos, aneg, bpos, bneg, abpos, abneg
            });
        } catch (Exception e) {
        }
        LCount.setText("0");
    }

    private void tampilPendonor() {
        Valid.tabelKosong(tabModePendonor);
        try {
            ps = koneksi.prepareStatement(
                    "SELECT utd_pendonor.no_pendonor, utd_pendonor.nama, utd_pendonor.no_ktp, utd_pendonor.jk, utd_pendonor.tmp_lahir, utd_pendonor.tgl_lahir, utd_pendonor.alamat, "
                    + "kelurahan.nm_kel, kecamatan.nm_kec, kabupaten.nm_kab, propinsi.nm_prop, utd_pendonor.golongan_darah, utd_pendonor.resus, utd_pendonor.no_telp "
                    + "FROM utd_pendonor inner join propinsi inner join kabupaten inner join kecamatan inner join kelurahan on utd_pendonor.kd_prop=propinsi.kd_prop "
                    + "and utd_pendonor.kd_kab=kabupaten.kd_kab and utd_pendonor.kd_kec=kecamatan.kd_kec and utd_pendonor.kd_kel=kelurahan.kd_kel "
                    + "and (utd_pendonor.no_pendonor like ? or utd_pendonor.nama like ? or utd_pendonor.no_ktp like ? or utd_pendonor.tmp_lahir like ? "
                    + "or utd_pendonor.tgl_lahir like ? or utd_pendonor.alamat like ? or utd_pendonor.no_telp like ?) order by utd_pendonor.no_pendonor desc"
            );
            try {
                ps.setString(1, "%" + TCari.getText().trim() + "%");
                ps.setString(2, "%" + TCari.getText().trim() + "%");
                ps.setString(3, "%" + TCari.getText().trim() + "%");
                ps.setString(4, "%" + TCari.getText().trim() + "%");
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                ps.setString(6, "%" + TCari.getText().trim() + "%");
                ps.setString(7, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabModePendonor.addRow(new Object[]{
                        rs.getString("no_pendonor"), rs.getString("nama"), rs.getString("no_ktp"), rs.getString("jk"),
                        rs.getString("golongan_darah"), rs.getString("resus"), rs.getString("tmp_lahir"),
                        rs.getString("tgl_lahir"), rs.getString("no_telp"), rs.getString("alamat"), rs.getString("nm_kel"),
                        rs.getString("nm_kec"), rs.getString("nm_kab"), rs.getString("nm_prop")
                    });
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        LCount.setText("" + tabModePendonor.getRowCount());
        isNumber2();
    }

    public void emptTeks() {
//        autoNomer();
        isNumber();
        NamaPendonor.setText("");
        Umur.setText("");
        Alamat.setText("");
        Tensi.setText("");
        NomorBag.setText("");
        NomorTelp.setText("");
        nik.setText("");
        KodePetugasAftap.setText("");
        NamaPetugasAftap.setText("");
        KodePetugasUSaring.setText("");
        NamaPetugasUSaring.setText("");
//        NomorDonor.requestFocus(); 
        TempatAftap.setVisible(false);
        jLabel14.setVisible(false);
        NomorBag.setVisible(false);
        label34.setVisible(false);
        GolonganDarah.setVisible(false);
        jLabel10.setVisible(false);
        Resus.setVisible(false);
        jLabel11.setVisible(false);
        Tensi.setVisible(false);
        label39.setVisible(false);
        jLabel12.setVisible(false);
        JenisBag.setVisible(false);
        JenisDonor.setVisible(false);
        jLabel13.setVisible(false);
        NamaPetugasAftap.setVisible(false);
        KodePetugasAftap.setVisible(false);
        label18.setVisible(false);
        btnPetugasAftap.setVisible(false);
        label17.setVisible(false);
        HBSAg.setVisible(false);
        jLabel15.setVisible(false);
        HIV.setVisible(false);
        jLabel17.setVisible(false);
        Malaria.setVisible(false);
        jLabel19.setVisible(false);
        HCV.setVisible(false);
        jLabel16.setVisible(false);
        Spilis.setVisible(false);
        jLabel18.setVisible(false);
        KodePetugasUSaring.setVisible(false);
        NamaPetugasUSaring.setVisible(false);
        label19.setVisible(false);
        btnPetugasUSaring.setVisible(false);
        jLabel22.setVisible(false);
        Pengambilan.setVisible(false);
        isNumber2();
        NamaPendonor1.setText("");
        nik1.setText("");
        tempatlahir.setText("");
        tgl_lahir1.setDate(new Date());
        NomorTelp1.setText("");
        Alamat1.setText("");
        Propinsi.setText("PROPINSI");
        Kelurahan.setText("KELURAHAN");
        Kecamatan.setText("KECAMATAN");
        Kabupaten.setText("KABUPATEN");
        JK1.setSelectedIndex(0);
        GolDarah.setSelectedIndex(0);
        Resus1.setSelectedIndex(0);
        HBSAg.setSelectedIndex(0);
        HCV.setSelectedIndex(0);
        HIV.setSelectedIndex(0);
        Spilis.setSelectedIndex(0);
        Malaria.setSelectedIndex(0);
        //Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_donor,4),signed)),0) from utd_donor where tanggal like '%"+Valid.SetTgl(Tanggal.getSelectedItem()+"").substring(0,7)+"%'",dateformat.format(Tanggal.getDate()).substring(0,7)+"/UTD",4,NomorDonor); 
    }

    private void autoNomer() {
//        String nomer_string;
////        int nomer;
//        int norut = 0;
//        String sql;
//        try {
//            sql = "select ifnull(MAX(CONVERT(RIGHT(no_donor,3),signed)),0) from utd_donor where tanggal='" + Valid.SetTgl(Tanggal.getSelectedItem() + "") + "'";
//            ps = koneksi.prepareStatement(sql);
//            try {
//                rs = ps.executeQuery();
//                while (rs.next()) {
//                    norut = Integer.parseInt(rs.getString(1)) + 1;
//                }
//            } catch (SQLException e) {
//                System.out.println("Notifikasi : " + e);
//            } finally {
//                if (rs != null) {
//                    rs.close();
//                }
//
//                if (ps != null) {
//                    ps.close();
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println("Notifikasi : " + e);
//        }
//        String str = String.format("%03d", norut);
//        DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
//        String year = df.format(new Date());
//        DateFormat mm = new SimpleDateFormat("MM"); // Just the year, with 2 digits
//        String month = mm.format(new Date());
//        DateFormat dd = new SimpleDateFormat("dd"); // Just the year, with 2 digits
//        String day = dd.format(new Date());
//        nomer_string = year + month + day + str;
////        nomer = Integer.parseInt(nomer_string);
//        NomorDonor.setText(nomer_string);
////        return nomer;
    }

    public JTextField getTextField() {
        return NomorDonor;
    }

    public JButton getButton() {
        return BtnKeluar;
    }

    public JTable getTable() {
        TabRawat.setSelectedIndex(2);
        return tbTranfusiDarah;
    }

    public void isCek() {
        BtnSimpan.setEnabled(var.getutd_donor());
        BtnHapus.setEnabled(var.getutd_donor());
        BtnEdit.setEnabled(var.getutd_donor());
        BtnPrint.setEnabled(var.getutd_donor());
        ppUbah.setEnabled(var.getutd_donor());
        ppHapusBHPMedis.setEnabled(var.getutd_donor());
        ppHapusBHPMedisDanNonMedis.setEnabled(var.getutd_donor());
        ppHapusBHPNonMedis.setEnabled(var.getutd_donor());
        ppCekal.setEnabled(var.getutd_cekal_darah());
        printWB.setEnabled(var.getutd_cekal_darah());
        if (var.getkode().equals("Admin Utama")) {
            BtnWA.setEnabled(true);
        } else {
            BtnWA.setEnabled(false);
        }
        MnDaftarBaru.setEnabled(var.getutd_donor());
        ppBaru.setVisible(false);
        label37.setVisible(false);
        label35.setVisible(false);
        Umur.setVisible(false);        
    }

    private void isNumber() {
    LocalDate date = LocalDate.now();
    String formattedDate = date.format(dtf);
    Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_donor,3),signed)),0) from utd_donor where tanggal like '%" + formattedDate + "%'", 
            formattedDate.substring(2, 4)+formattedDate.substring(5, 7)+formattedDate.substring(8, 10), 3, NomorDonor);
//        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_donor,3),signed)),0) from utd_donor where tanggal like '%" + Valid.SetTgl(Tanggal.getSelectedItem() + "") + "%'", dateformat.format(Tanggal.getDate()).substring(2, 4)
//                + dateformat.format(Tanggal.getDate()).substring(5, 7) + dateformat.format(Tanggal.getDate()).substring(8, 10), 3, NomorDonor);
    }

    private void isNumber2() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_pendonor,6),signed)),0) from utd_pendonor", "DO", 6, NomorPendonor);
    }

    private void getData() {
        if (tbData.getSelectedRow() != -1) {
            try {
                NomorPendonor.setText(tbData.getValueAt(tbData.getSelectedRow(), 0).toString());
                NamaPendonor1.setText(tbData.getValueAt(tbData.getSelectedRow(), 1).toString());
                nik1.setText(tbData.getValueAt(tbData.getSelectedRow(), 2).toString());
                if (tbData.getValueAt(tbData.getSelectedRow(), 3).toString().equals("L")) {
                    JK1.setSelectedIndex(0);
                } else {
                    JK1.setSelectedIndex(1);
                }
                GolDarah.setSelectedItem(tbData.getValueAt(tbData.getSelectedRow(), 4).toString());
                Resus1.setSelectedItem(tbData.getValueAt(tbData.getSelectedRow(), 5).toString());
                tempatlahir.setText(tbData.getValueAt(tbData.getSelectedRow(), 6).toString());
                Valid.SetTgl(tgl_lahir1, tbData.getValueAt(tbData.getSelectedRow(), 7).toString().substring(0, 10));
                NomorTelp1.setText(tbData.getValueAt(tbData.getSelectedRow(), 8).toString());
                Alamat1.setText(tbData.getValueAt(tbData.getSelectedRow(), 9).toString());
                kdkel = Sequel.cariIsi("select kd_kel from utd_pendonor where no_pendonor=?",NomorPendonor.getText());
                kdkec = Sequel.cariIsi("select kd_kec from utd_pendonor where no_pendonor=?",NomorPendonor.getText());
                kdkab = Sequel.cariIsi("select kd_kab from utd_pendonor where no_pendonor=?",NomorPendonor.getText());
                kdprop = Sequel.cariIsi("select kd_prop from utd_pendonor where no_pendonor=?",NomorPendonor.getText());
                ps = koneksi.prepareStatement(
                        "select kelurahan.nm_kel,kecamatan.nm_kec,kabupaten.nm_kab,propinsi.nm_prop from utd_pendonor "
                        + "inner join kelurahan inner join kecamatan inner join kabupaten inner join propinsi on utd_pendonor.kd_kel=kelurahan.kd_kel "
                        + "and utd_pendonor.kd_kec=kecamatan.kd_kec and utd_pendonor.kd_kab=kabupaten.kd_kab and utd_pendonor.kd_prop=propinsi.kd_prop "
                        + "where utd_pendonor.no_pendonor=?");
                try {
                    ps.setString(1, tbData.getValueAt(tbData.getSelectedRow(), 0).toString());
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        Propinsi.setText(rs.getString("nm_prop"));
                        Kabupaten.setText(rs.getString("nm_kab"));
                        Kecamatan.setText(rs.getString("nm_kec"));
                        Kelurahan.setText(rs.getString("nm_kel"));
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : " + e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }

                    if (ps != null) {
                        ps.close();
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    public static String getShift(LocalTime time) {
        if (time.isAfter(LocalTime.of(6, 0)) && time.isBefore(LocalTime.of(15, 0))) {
            return "Pagi";
        } else if (time.isAfter(LocalTime.of(15, 0)) && time.isBefore(LocalTime.of(21, 0))) {
            return "Siang";
        } else {
            return "Malam";
        }
    }

    private void isForm() {
        if (ChkInput.isSelected() == true) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 160));
            panelGlass12.setVisible(true);
            ChkInput.setVisible(true);
        } else if (ChkInput.isSelected() == false) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 20));
            panelGlass12.setVisible(false);
            ChkInput.setVisible(true);
        }
    }
    
    private void insertData() {
        System.out.println("insert");
        LocalTime currentTime = LocalTime.now();
        String shift = getShift(currentTime);
        String user = var.getkode();
        if (Sequel.menyimpantf("utd_donor", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Nomor Donor", 18, new String[]{
            NomorDonor.getText(), NomorPendonor.getText(), Sequel.cariIsi("select current_date()"),
            shift, "", "", null, null, null, user, null, null, null, null, null, "", user, "Aman"
        }) == true) {
            for (i = 0; i < tbMedis.getRowCount(); i++) {
                try {
                    if (Valid.SetAngka(tbMedis.getValueAt(i, 0).toString()) > 0) {
                        if (Sequel.menyimpantf2("utd_penggunaan_medis_donor", "?,?,?,?,?", "BHP Medis", 5, new String[]{
                            NomorDonor.getText(), tbMedis.getValueAt(i, 1).toString(), tbMedis.getValueAt(i, 0).toString(), tbMedis.getValueAt(i, 3).toString(),
                            Double.toString(Double.parseDouble(tbMedis.getValueAt(i, 0).toString()) * Double.parseDouble(tbMedis.getValueAt(i, 3).toString()))
                        }) == true) {
                            Sequel.menyimpan("utd_stok_medis", "'" + tbMedis.getValueAt(i, 1).toString() + "','-" + tbMedis.getValueAt(i, 0).toString() + "','" + tbMedis.getValueAt(i, 3).toString() + "'",
                                    "stok=stok-" + tbMedis.getValueAt(i, 0).toString() + "", "kode_brng='" + tbMedis.getValueAt(i, 1).toString() + "'");
                            tbMedis.setValueAt(null, i, 0);
                            tbMedis.setValueAt(0, i, 4);
                        }
                    }
                } catch (Exception e) {
                }
            }
            for (i = 0; i < tbNonMedis.getRowCount(); i++) {
                try {
                    if (Valid.SetAngka(tbNonMedis.getValueAt(i, 0).toString()) > 0) {
                        if (Sequel.menyimpantf2("utd_penggunaan_penunjang_donor", "?,?,?,?,?", "BHP Non Medis", 5, new String[]{
                            NomorDonor.getText(), tbNonMedis.getValueAt(i, 1).toString(), tbNonMedis.getValueAt(i, 0).toString(), tbNonMedis.getValueAt(i, 3).toString(),
                            Double.toString(Double.parseDouble(tbNonMedis.getValueAt(i, 0).toString()) * Double.parseDouble(tbNonMedis.getValueAt(i, 3).toString()))
                        }) == true) {
                            Sequel.menyimpan("utd_stok_penunjang", "'" + tbNonMedis.getValueAt(i, 1).toString() + "','-" + tbNonMedis.getValueAt(i, 0).toString() + "','" + tbNonMedis.getValueAt(i, 3).toString() + "'",
                                    "stok=stok-" + tbNonMedis.getValueAt(i, 0).toString() + "", "kode_brng='" + tbNonMedis.getValueAt(i, 1).toString() + "'");
                            tbNonMedis.setValueAt(null, i, 0);
                            tbNonMedis.setValueAt(0, i, 4);
                        }
                    }
                } catch (Exception e) {
                }
            }
            JOptionPane.showMessageDialog(null, "Berhasil Simpan.");
            TabRawat.setSelectedIndex(2);
            tampil();
            emptTeks();
        } else {
//                autoNomer();
            isNumber();
            if (Sequel.menyimpantf("utd_donor", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Nomor Donor", 18, new String[]{
                NomorDonor.getText(), NomorPendonor.getText(), Sequel.cariIsi("select current_date()"),
                shift, "", "", null, null, null, user, null, null, null, null, null, "", user, "Aman"
            }) == true) {
                for (i = 0; i < tbMedis.getRowCount(); i++) {
                    try {
                        if (Valid.SetAngka(tbMedis.getValueAt(i, 0).toString()) > 0) {
                            if (Sequel.menyimpantf2("utd_penggunaan_medis_donor", "?,?,?,?,?", "BHP Medis", 5, new String[]{
                                NomorDonor.getText(), tbMedis.getValueAt(i, 1).toString(), tbMedis.getValueAt(i, 0).toString(), tbMedis.getValueAt(i, 3).toString(),
                                Double.toString(Double.parseDouble(tbMedis.getValueAt(i, 0).toString()) * Double.parseDouble(tbMedis.getValueAt(i, 3).toString()))
                            }) == true) {
                                Sequel.menyimpan("utd_stok_medis", "'" + tbMedis.getValueAt(i, 1).toString() + "','-" + tbMedis.getValueAt(i, 0).toString() + "','" + tbMedis.getValueAt(i, 3).toString() + "'",
                                        "stok=stok-" + tbMedis.getValueAt(i, 0).toString() + "", "kode_brng='" + tbMedis.getValueAt(i, 1).toString() + "'");
                                tbMedis.setValueAt(null, i, 0);
                                tbMedis.setValueAt(0, i, 4);
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                for (i = 0; i < tbNonMedis.getRowCount(); i++) {
                    try {
                        if (Valid.SetAngka(tbNonMedis.getValueAt(i, 0).toString()) > 0) {
                            if (Sequel.menyimpantf2("utd_penggunaan_penunjang_donor", "?,?,?,?,?", "BHP Non Medis", 5, new String[]{
                                NomorDonor.getText(), tbNonMedis.getValueAt(i, 1).toString(), tbNonMedis.getValueAt(i, 0).toString(), tbNonMedis.getValueAt(i, 3).toString(),
                                Double.toString(Double.parseDouble(tbNonMedis.getValueAt(i, 0).toString()) * Double.parseDouble(tbNonMedis.getValueAt(i, 3).toString()))
                            }) == true) {
                                Sequel.menyimpan("utd_stok_penunjang", "'" + tbNonMedis.getValueAt(i, 1).toString() + "','-" + tbNonMedis.getValueAt(i, 0).toString() + "','" + tbNonMedis.getValueAt(i, 3).toString() + "'",
                                        "stok=stok-" + tbNonMedis.getValueAt(i, 0).toString() + "", "kode_brng='" + tbNonMedis.getValueAt(i, 1).toString() + "'");
                                tbNonMedis.setValueAt(null, i, 0);
                                tbNonMedis.setValueAt(0, i, 4);
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                JOptionPane.showMessageDialog(null, "Berhasil Simpan.");
                TabRawat.setSelectedIndex(2);
                tampil();
                emptTeks();
            }
        }
    }
    
    public void cek_umur() {
        row = tbTranfusiDarah.getRowCount();
        for (i = 0; i < row; i++) {
            try {
                ps = koneksi.prepareStatement("SELECT DATEDIFF(?, ?) DIV 365 AS age FROM utd_donor WHERE no_donor = ?");
                umur = "";
                try {
                    ps.setString(1, tbTranfusiDarah.getValueAt(i, 4).toString());
                    ps.setString(2, tbTranfusiDarah.getValueAt(i, 7).toString());
                    ps.setString(3, tbTranfusiDarah.getValueAt(i, 0).toString());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        umur = rs.getString("age");
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : " + e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }
                }
                tbTranfusiDarah.setValueAt(umur, i, 8);
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        }
    }

}
