/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * DlgReg.java
 *
 * Created on Jun 8, 2010, choose Tools | Templates
 * and open the template in10:28:56 PM
 */
package simrskhanza;

import permintaan.DlgBookingOperasi;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariDokter2;
import inventory.DlgResepObat;
import inventory.DlgPemberianObat;
import bridging.BPJSDataSEP;
import bridging.BPJSSPRI;
import bridging.BPJSSuratKontrol;
import bridging.DlgSKDPBPJS;
import bridging.InhealthDataSJP;
import bridging.SisruteRujukanKeluar;
import laporan.DlgDiagnosaPenyakit;
import laporan.DlgFrekuensiPenyakitRalan;
import keuangan.DlgBilingRalan;
import fungsi.WarnaTable;
import fungsi.batasInput;
import surat.DlgSuratSehat;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import inventory.DlgPeresepanDokter;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.DlgBilingParsialRalan;
import keuangan.DlgLhtPiutang;
import laporan.DlgBerkasRawat;
import laporan.DlgDataInsidenKeselamatan;
import bridging.DlgDataTB;
import java.awt.Color;
import java.awt.Component;
import permintaan.DlgPermintaanLaboratorium;
import permintaan.DlgPermintaanRadiologi;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.DAYS;
import javax.swing.table.DefaultTableCellRenderer;
import surat.SuratNapza;

/**
 *
 * @author dosen
 */
public final class DlgReg extends javax.swing.JDialog {

    private final DefaultTableModel tabMode, tabMode2, tabMode3, tabMode4;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    public DlgPasien pasien = new DlgPasien(null, false);
    public DlgCariDokter dokter = new DlgCariDokter(null, false);
    public DlgCariDokter2 dokter2 = new DlgCariDokter2(null, false);
    private DlgCariPoli poli = new DlgCariPoli(null, false);
    private DlgCariPoli2 poli2 = new DlgCariPoli2(null, false);
    private DlgRujukanPoliInternal dlgrjk = new DlgRujukanPoliInternal(null, false);
    public DlgRujukMasuk rujukmasuk = new DlgRujukMasuk(null, false);
    private DlgCatatan catatan = new DlgCatatan(null, false);
    private PreparedStatement ps, ps2, ps3, pscaripiutang, pshajilab, pshajirad, pshajidr, pshajipr, ps4;
    private Properties prop = new Properties();
    private ResultSet rs, rs2, rs3, rs4;
    private int pilihan = 0, i = 0, kuota = 0, jmlparsial = 0;
    private Date date, date1;
    private boolean ceksukses = false;
    private String nosisrute = "", aktifkanparsial = "no", BASENOREG = "",user="",limit="",
            URUTNOREG = "", status = "Baru", order = "reg_periksa.tgl_registrasi,reg_periksa.jam_reg desc", alamatperujuk = "-", aktifjadwal = "", IPPRINTERTRACER = "", umur = "0", sttsumur = "Th",
            validasiregistrasi = Sequel.cariIsi("select wajib_closing_kasir from set_validasi_registrasi"),
            validasicatatan = Sequel.cariIsi("select tampilkan_catatan from set_validasi_catatan"), nm_pasien = "", no_reg = "", nm_poli = "", url = "", bidang = "";
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
    
    private DlgCariPoli poli3 = new DlgCariPoli(null, false);
    private DlgCariPoli2 poli5 = new DlgCariPoli2(null, false);
    private DlgCariPoli poli6 = new DlgCariPoli(null, false);
    private DlgCariPoli2 poli7 = new DlgCariPoli2(null, false);

    /**
     * Creates new form DlgReg
     *
     * @param parent
     * @param modal
     */
    public DlgReg(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8, 1);
        setSize(885, 674);

        WindowRunningText.setSize(500,160);
        tabMode = new DefaultTableModel(null, new Object[]{
            "P", "No. Reg", "No.Rawat", "Tanggal", "Jam", "Kd.Dokter", "Dokter Dituju", "No. RM",
            "Pasien", "J.K.", "Umur", "Alamat", "Poliklinik", "Jenis Bayar", "Penanggung Jawab", "Alamat P.J.", "Hubungan P.J.",
            "Biaya Reg", "Status", "No.Telp", "Stts Rawat", "Stts Poli", "Kode Poli", "Kode PJ", "No. Sep", "Asal Booking", "Kategori","Stts. Obat"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tabMode.fireTableDataChanged();
        tbPetugas.setModel(tabMode);
        tbPetugas.setRowHeight(40);
        tbPetugas.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbPetugas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 28; i++) {
            TableColumn column = tbPetugas.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(35);
            } else if (i == 2) {
                column.setPreferredWidth(125);
            } else if (i == 3) {
                column.setPreferredWidth(70);
            } else if (i == 4) {
                column.setPreferredWidth(60);
            } else if (i == 5) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 6) {
                column.setPreferredWidth(160);
            } else if (i == 7) {
                column.setPreferredWidth(60);
            } else if (i == 8) {
                column.setPreferredWidth(170);
            } else if (i == 9) {
                column.setPreferredWidth(27);
            } else if (i == 10) {
                column.setPreferredWidth(40);
            } else if (i == 11) {
                column.setPreferredWidth(170);
            } else if (i == 12) {
                column.setPreferredWidth(145);
            } else if (i == 13) {
                column.setPreferredWidth(50);
            } else if (i == 14) {
                column.setPreferredWidth(100);
            } else if (i == 15) {
                column.setPreferredWidth(90);
            } else if (i == 16) {
                column.setPreferredWidth(70);
            } else if (i == 17) {
                column.setPreferredWidth(45);
            } else if (i == 18) {
                column.setPreferredWidth(40);
            } else if (i == 19) {
                column.setPreferredWidth(88);
            } else if (i == 20) {
                column.setPreferredWidth(60);
            } else if (i == 21) {
                column.setPreferredWidth(60);
            } else if (i == 22) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 23) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 24) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 25) {
//                column.setPreferredWidth(60);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 26) {
                column.setPreferredWidth(80);
            } else if (i == 27) {
                column.setPreferredWidth(80);
            }
        }

        tbPetugas.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

                String stts_rawat = (String) table.getModel().getValueAt(row, 20);
                String sep = (String) table.getModel().getValueAt(row, 24);
                String pj_bayar = (String) table.getModel().getValueAt(row, 13);
                String jam = (String) table.getModel().getValueAt(row, 4);
                String limit_reg = (String) table.getModel().getValueAt(row, 25);
                String poli = (String) table.getModel().getValueAt(row, 12);
                if (var.getkode().equals("DR00019") || var.getkode().equals("D0000132")) {
                    if ("POLI MCU".equals(poli)) {
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
                    if ("Sudah".equals(stts_rawat)) {
                        setBackground(new Color(204, 255, 152));
                        setForeground(Color.BLACK);
                    } else if ("Diperiksa".equals(stts_rawat)) {
                        setBackground(new Color(247, 202, 40));
                        setForeground(Color.BLACK);
                    } else if ("Batal".equals(stts_rawat) && !"".equals(sep) && "BPJS".equals(pj_bayar)) {
                        setBackground(new Color(53, 153, 255));
                        setForeground(Color.BLACK);
                    } else if (!"".equals(sep) && !"".equals(sep) && "BPJS".equals(pj_bayar) && ("0".equals(limit_reg) || "1".equals(limit_reg))) {
                        setBackground(new Color(166, 108, 255));
                        setForeground(Color.BLACK);
                    } else if ("Batal".equals(stts_rawat)) {
                        setBackground(new Color(153, 0, 76));
                        setForeground(Color.WHITE);
                    } else if (!"".equals(sep) && !"".equals(sep) && "BPJS".equals(pj_bayar) && (!"0".equals(limit_reg) || !"1".equals(limit_reg))) {
                        setBackground(new Color(242, 141, 195));
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
                }
                if (isSelected) {
                    setForeground(Color.RED);
                }
                return this;
            }
        });
        //tab mode 3
        tabMode3 = new DefaultTableModel(null, new Object[]{
            "P", "No.Reg", "No.Rawat", "Tanggal", "Jam", "Kd.Dokter", "Dokter Dituju", "Nomer RM",
            "Pasien", "J.K.", "Umur", "Poliklinik", "Jenis Bayar", "Penanggung Jawab", "Alamat P.J.", "Hubungan P.J.",
            "Biaya Registrasi", "Status", "No.Telp", "Stts Rawat", "Stts Poli", "Kode Poli", "Kode PJ"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbPetugas1.setModel(tabMode3);

        tbPetugas1.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbPetugas1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 23; i++) {
            TableColumn column = tbPetugas1.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(50);
            } else if (i == 2) {
                column.setPreferredWidth(120);
            } else if (i == 3) {
                column.setPreferredWidth(70);
            } else if (i == 4) {
                column.setPreferredWidth(50);
            } else if (i == 5) {
                column.setPreferredWidth(70);
            } else if (i == 6) {
                column.setPreferredWidth(200);
            } else if (i == 7) {
                column.setPreferredWidth(70);
            } else if (i == 8) {
                column.setPreferredWidth(200);
            } else if (i == 9) {
                column.setPreferredWidth(30);
            } else if (i == 10) {
                column.setPreferredWidth(50);
            } else if (i == 11) {
                column.setPreferredWidth(140);
            } else if (i == 12) {
                column.setPreferredWidth(140);
            } else if (i == 13) {
                column.setPreferredWidth(200);
            } else if (i == 14) {
                column.setPreferredWidth(140);
            } else if (i == 15) {
                column.setPreferredWidth(90);
            } else if (i == 16) {
                column.setPreferredWidth(100);
            } else if (i == 17) {
                column.setPreferredWidth(80);
            } else if (i == 18) {
                column.setPreferredWidth(80);
            } else if (i == 19) {
                column.setPreferredWidth(70);
            } else if (i == 20) {
                column.setPreferredWidth(60);
            } else if (i == 21) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 22) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }

        tbPetugas1.setDefaultRenderer(Object.class, new WarnaTable());
        //akhir tab mode 3
        tabMode2 = new DefaultTableModel(null, new Object[]{
            "P", "No.Rawat", "Tanggal", "Jam", "Kd.Dokter", "Dokter Rujukan", "Nomer RM",
            "Pasien", "J.K.", "Umur", "Poliklinik Rujukan", "Poliklinik Perujuk", "Jenis Bayar", "Penanggung Jawab",
            "Alamat P.J.", "Hubungan P.J.",
            "Status", "No.Telp", "Stts Rawat", "Kode Poli", "Kode PJ"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            //
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbPetugas2.setModel(tabMode2);

        tbPetugas2.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbPetugas2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 21; i++) {
            TableColumn column = tbPetugas2.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(120);
            } else if (i == 2) {
                column.setPreferredWidth(70);
            } else if (i == 3) {
                column.setPreferredWidth(50);
            } else if (i == 4) {
                column.setPreferredWidth(70);
            } else if (i == 5) {
                column.setPreferredWidth(200);
            } else if (i == 6) {
                column.setPreferredWidth(70);
            } else if (i == 7) {
                column.setPreferredWidth(200);
            } else if (i == 8) {
                column.setPreferredWidth(30);
            } else if (i == 9) {
                column.setPreferredWidth(50);
            } else if (i == 10) {
                column.setPreferredWidth(140);
            } else if (i == 11) {
                column.setPreferredWidth(140);
            } else if (i == 12) {
                column.setPreferredWidth(200);
            } else if (i == 13) {
                column.setPreferredWidth(140);
            } else if (i == 14) {
                column.setPreferredWidth(140);
            } else if (i == 15) {
                column.setPreferredWidth(140);
            } else if (i == 16) {
                column.setPreferredWidth(90);
            } else if (i == 17) {
                column.setPreferredWidth(80);
            } else if (i == 18) {
                column.setPreferredWidth(70);
            } else if (i == 19) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 20) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbPetugas2.setDefaultRenderer(Object.class, new WarnaTable());

        tabMode4 = new DefaultTableModel(null, new Object[]{
            "P", "No.Rawat", "Tanggal", "Jam", "Kd.Dokter", "Dokter Rujukan", "Nomer RM",
            "Pasien", "J.K.", "Umur", "Poliklinik Rujukan", "Poliklinik Perujuk", "Jenis Bayar", "Penanggung Jawab",
            "Alamat P.J.", "Hubungan P.J.",
            "Status", "No.Telp", "Stts Rawat", "Kode Poli", "Kode PJ"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            //
            Class[] types = new Class[]{
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbPetugas3.setModel(tabMode4);

        tbPetugas3.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbPetugas3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 21; i++) {
            TableColumn column = tbPetugas3.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(120);
            } else if (i == 2) {
                column.setPreferredWidth(70);
            } else if (i == 3) {
                column.setPreferredWidth(50);
            } else if (i == 4) {
                column.setPreferredWidth(70);
            } else if (i == 5) {
                column.setPreferredWidth(200);
            } else if (i == 6) {
                column.setPreferredWidth(70);
            } else if (i == 7) {
                column.setPreferredWidth(200);
            } else if (i == 8) {
                column.setPreferredWidth(30);
            } else if (i == 9) {
                column.setPreferredWidth(50);
            } else if (i == 10) {
                column.setPreferredWidth(140);
            } else if (i == 11) {
                column.setPreferredWidth(140);
            } else if (i == 12) {
                column.setPreferredWidth(200);
            } else if (i == 13) {
                column.setPreferredWidth(140);
            } else if (i == 14) {
                column.setPreferredWidth(140);
            } else if (i == 15) {
                column.setPreferredWidth(140);
            } else if (i == 16) {
                column.setPreferredWidth(90);
            } else if (i == 17) {
                column.setPreferredWidth(80);
            } else if (i == 18) {
                column.setPreferredWidth(70);
            } else if (i == 19) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 20) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbPetugas3.setDefaultRenderer(Object.class, new WarnaTable());

        TNoReg.setDocument(new batasInput((byte) 8).getKata(TNoReg));
        TNoRw.setDocument(new batasInput((byte) 17).getKata(TNoRw));
        TNoRM.setDocument(new batasInput((byte) 15).getKata(TNoRM));
        AsalRujukan.setDocument(new batasInput((byte) 60).getKata(AsalRujukan));
        kddokter.setDocument(new batasInput((byte) 20).getKata(kddokter));
        kdpnj.setDocument(new batasInput((byte) 3).getKata(kdpnj));
        TPngJwb.setDocument(new batasInput((byte) 30).getKata(TPngJwb));
        kdpoli.setDocument(new batasInput((byte) 5).getKata(kdpoli));
        THbngn.setDocument(new batasInput((byte) 20).getKata(THbngn));
        TAlmt.setDocument(new batasInput((byte) 60).getKata(TAlmt));
        TBiaya.setDocument(new batasInput((byte) 13).getOnlyAngka(TBiaya));
        TCari.setDocument(new batasInput((byte) 100).getKata(TCari));
        CrPoli.setDocument(new batasInput((byte) 100).getKata(CrPoli));
        CrDokter.setDocument(new batasInput((byte) 100).getKata(CrDokter));
        TRunningText.setDocument(new batasInput((byte) 100).getKata(TRunningText));
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
        jam();

        pasien.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("DlgReg")) {
                    if (pasien.getTable().getSelectedRow() != -1) {
                        TNoRM.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(), 1).toString());
                        isPas();
                        isNumber();
                    }
                    TNoRM.requestFocus();
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

        pasien.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (var.getform().equals("DlgReg")) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        pasien.dispose();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("DlgReg")) {
                    if (dokter.getTable().getSelectedRow() != -1) {
                        if (pilihan == 1) {
                            kddokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                            TDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                            isNumber();
                            kddokter.requestFocus();
                        } else if (pilihan == 2) {
                            CrDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                            CrDokter.requestFocus();
                            tampil();
                        } else if (pilihan == 3) {
                            CrDokter3.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                            CrDokter3.requestFocus();
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

        dokter2.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("DlgReg")) {
                    if (dokter2.getTable().getSelectedRow() != -1) {
                        if (pilihan == 1) {
                            kddokter.setText(dokter2.getTable().getValueAt(dokter2.getTable().getSelectedRow(), 0).toString());
                            TDokter.setText(dokter2.getTable().getValueAt(dokter2.getTable().getSelectedRow(), 1).toString());
                            if (aktifjadwal.equals("aktif")) {
                                kuota = Integer.parseInt(dokter2.getTable().getValueAt(dokter2.getTable().getSelectedRow(), 13).toString());
                            }
                            isNumber();
                            kddokter.requestFocus();
                        } else if (pilihan == 2) {
                            CrDokter.setText(dokter2.getTable().getValueAt(dokter2.getTable().getSelectedRow(), 1).toString());
                            CrDokter.requestFocus();
                            tampil();
                        } else if (pilihan == 3) {
                            CrDokter3.setText(dokter2.getTable().getValueAt(dokter2.getTable().getSelectedRow(), 1).toString());
                            CrDokter3.requestFocus();
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

        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("DlgReg")) {
                    if (poli.getTable().getSelectedRow() != -1) {
                        if (pilihan == 1) {
                            kdpoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(), 0).toString());
                            TPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(), 1).toString());
                            switch (TStatus.getText()) {
                                case "Baru":
                                    TBiaya.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(), 2).toString());
                                    break;
                                case "Lama":
                                    TBiaya.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(), 3).toString());
                                    break;
                                default:
                                    TBiaya.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(), 2).toString());
                                    break;
                            }
                            isNumber();
                            kdpoli.requestFocus();
                        } else if (pilihan == 2) {
                            CrPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(), 1).toString());
                            CrPoli.requestFocus();
                            order = "reg_periksa.no_reg asc";
                            tampil();
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

        poli2.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("DlgReg")) {
                    if (poli2.getTable().getSelectedRow() != -1) {
                        if (pilihan == 1) {
                            kdpoli.setText(poli2.getTable().getValueAt(poli2.getTable().getSelectedRow(), 0).toString());
                            TPoli.setText(poli2.getTable().getValueAt(poli2.getTable().getSelectedRow(), 1).toString());
                            switch (TStatus.getText()) {
                                case "Baru":
                                    TBiaya.setText(poli2.getTable().getValueAt(poli2.getTable().getSelectedRow(), 2).toString());
                                    break;
                                case "Lama":
                                    TBiaya.setText(poli2.getTable().getValueAt(poli2.getTable().getSelectedRow(), 3).toString());
                                    break;
                                default:
                                    TBiaya.setText(poli2.getTable().getValueAt(poli2.getTable().getSelectedRow(), 2).toString());
                                    break;
                            }
                            isNumber();
                            kdpoli.requestFocus();
                        } else if (pilihan == 2) {
                            CrPoli.setText(poli2.getTable().getValueAt(poli2.getTable().getSelectedRow(), 1).toString());
                            CrPoli.requestFocus();
                            tampil();
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
        
        pasien.penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("DlgReg")) {
                    if (pasien.penjab.getTable().getSelectedRow() != -1) {
                        kdpnj.setText(pasien.penjab.getTable().getValueAt(pasien.penjab.getTable().getSelectedRow(), 1).toString());
                        nmpnj.setText(pasien.penjab.getTable().getValueAt(pasien.penjab.getTable().getSelectedRow(), 2).toString());
                    }
                    kdpnj.requestFocus();
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
                if (var.getform().equals("DlgReg")) {
                    if (rujukmasuk.tbPerujuk.getSelectedRow() != -1) {
                        AsalRujukan.setText(rujukmasuk.tbPerujuk.getValueAt(rujukmasuk.tbPerujuk.getSelectedRow(), 0).toString());
                        alamatperujuk = rujukmasuk.tbPerujuk.getValueAt(rujukmasuk.tbPerujuk.getSelectedRow(), 1).toString();
                    }
                    AsalRujukan.requestFocus();
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

        poli3.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (poli3.getTable().getSelectedRow() != -1) {
                    KdPoli1.setText(poli3.getTable().getValueAt(poli3.getTable().getSelectedRow(), 0).toString());
                    NmPoli1.setText(poli3.getTable().getValueAt(poli3.getTable().getSelectedRow(), 1).toString());
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

        poli5.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (poli5.getTable().getSelectedRow() != -1) {
                    KdPoli1.setText(poli5.getTable().getValueAt(poli5.getTable().getSelectedRow(), 0).toString());
                    NmPoli1.setText(poli5.getTable().getValueAt(poli5.getTable().getSelectedRow(), 1).toString());
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
        
        poli6.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (poli6.getTable().getSelectedRow() != -1) {
                    KdPoli2.setText(poli6.getTable().getValueAt(poli6.getTable().getSelectedRow(), 0).toString());
                    NmPoli2.setText(poli6.getTable().getValueAt(poli6.getTable().getSelectedRow(), 1).toString());
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

        poli7.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (poli7.getTable().getSelectedRow() != -1) {
                    KdPoli2.setText(poli7.getTable().getValueAt(poli7.getTable().getSelectedRow(), 0).toString());
                    NmPoli2.setText(poli7.getTable().getValueAt(poli7.getTable().getSelectedRow(), 1).toString());
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

        pasien.penjab.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (var.getform().equals("DlgReg")) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        pasien.penjab.dispose();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        DlgCatatan.setSize(595, 34);

        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            aktifjadwal = prop.getProperty("JADWALDOKTERDIREGISTRASI");
            IPPRINTERTRACER = prop.getProperty("IPPRINTERTRACER");
            URUTNOREG = prop.getProperty("URUTNOREG");
            BASENOREG = prop.getProperty("BASENOREG");
            aktifkanparsial = prop.getProperty("AKTIFKANBILLINGPARSIAL");
        } catch (Exception ex) {
            aktifjadwal = "";
            IPPRINTERTRACER = "";
            URUTNOREG = "";
            BASENOREG = "";
        }

        ChkInput.setSelected(false);
        isForm();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnPermintaan = new javax.swing.JMenu();
        MnJadwalOperasi = new javax.swing.JMenuItem();
        MnSKDPBPJS = new javax.swing.JMenuItem();
        MnPermintaanLab = new javax.swing.JMenuItem();
        MnPermintaanRadiologi = new javax.swing.JMenuItem();
        MnSetPasien = new javax.swing.JMenu();
        MnPrioritas = new javax.swing.JMenuItem();
        MnKronis = new javax.swing.JMenuItem();
        MnKamarInap = new javax.swing.JMenuItem();
        MnTindakan = new javax.swing.JMenu();
        MnRawatJalan = new javax.swing.JMenuItem();
        MnPeriksaLab = new javax.swing.JMenuItem();
        MnPeriksaRadiologi = new javax.swing.JMenuItem();
        MnOperasi = new javax.swing.JMenuItem();
        MnObat = new javax.swing.JMenu();
        MnPemberianObat = new javax.swing.JMenuItem();
        MnNoResep = new javax.swing.JMenuItem();
        MnResepDOkter = new javax.swing.JMenuItem();
        MnPilihBilling = new javax.swing.JMenu();
        MnBillingParsial = new javax.swing.JMenuItem();
        MnBilling = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        jMenu1 = new javax.swing.JMenu();
        MnLaporanRekapKunjunganPoli = new javax.swing.JMenuItem();
        MnLaporanRekapKunjunganDokter = new javax.swing.JMenuItem();
        MnLaporanRekapJenisBayar = new javax.swing.JMenuItem();
        MnLaporanRekapRawatDarurat = new javax.swing.JMenuItem();
        MnLaporanRekapKunjunganBulanan = new javax.swing.JMenuItem();
        MnLaporanRekapKunjunganBulananPoli = new javax.swing.JMenuItem();
        MnLaporanRekapPenyakitRalan = new javax.swing.JMenuItem();
        MnLaporanRekapPerujuk = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        MnCetakSuratSehat = new javax.swing.JMenuItem();
        MnCetakSuratSehat1 = new javax.swing.JMenuItem();
        MnCetakBebasNarkoba = new javax.swing.JMenuItem();
        MnCetakSuratSakit = new javax.swing.JMenuItem();
        MnCetakSuratSakit2 = new javax.swing.JMenuItem();
        MnCetakSuratSakit3 = new javax.swing.JMenuItem();
        MnCetakSuratSakit1 = new javax.swing.JMenuItem();
        MnCetakRegister = new javax.swing.JMenuItem();
        MnPersetujuanMedis = new javax.swing.JMenuItem();
        MnBuktiPelayananRalan = new javax.swing.JMenuItem();
        MnLembarCasemix = new javax.swing.JMenuItem();
        MnLembarCasemix1 = new javax.swing.JMenuItem();
        MnLembarCasemix2 = new javax.swing.JMenuItem();
        MnSPBK = new javax.swing.JMenuItem();
        MnSPBK1 = new javax.swing.JMenuItem();
        MnJKRA = new javax.swing.JMenuItem();
        MnLembarRalan = new javax.swing.JMenuItem();
        MnBlangkoResep = new javax.swing.JMenuItem();
        MnCetakSuratSehat2 = new javax.swing.JMenuItem();
        MnSuratNapza = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        MnCheckList = new javax.swing.JMenuItem();
        MnCheckList1 = new javax.swing.JMenuItem();
        MnCheckList2 = new javax.swing.JMenuItem();
        MnCheckList3 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        MnCheckList4 = new javax.swing.JMenuItem();
        MnCheckList5 = new javax.swing.JMenuItem();
        MnCheckList6 = new javax.swing.JMenuItem();
        MnCheckList7 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        MnLabelTracker = new javax.swing.JMenuItem();
        MnLabelTracker1 = new javax.swing.JMenuItem();
        MnLabelTracker2 = new javax.swing.JMenuItem();
        MnLabelTracker3 = new javax.swing.JMenuItem();
        MnBarcode = new javax.swing.JMenuItem();
        MnBarcode1 = new javax.swing.JMenuItem();
        MnBarcodeRM9 = new javax.swing.JMenuItem();
        MnBarcodeRM10 = new javax.swing.JMenuItem();
        MnGelang1 = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JPopupMenu.Separator();
        MnRujukan = new javax.swing.JMenu();
        MnRujukMasuk = new javax.swing.JMenuItem();
        MnRujuk = new javax.swing.JMenuItem();
        MnPoliInternal = new javax.swing.JMenuItem();
        MnJawabanKonsul = new javax.swing.JMenuItem();
        MnBridging = new javax.swing.JMenu();
        MnSEP = new javax.swing.JMenuItem();
        ppSuratKontrol = new javax.swing.JMenuItem();
        ppSuratPRI = new javax.swing.JMenuItem();
        MnSJP = new javax.swing.JMenuItem();
        MnRujukSisrute = new javax.swing.JMenuItem();
        MnPACS = new javax.swing.JMenuItem();
        MenuInputData = new javax.swing.JMenu();
        MnDiagnosa = new javax.swing.JMenuItem();
        ppCatatanPasien = new javax.swing.JMenuItem();
        ppBerkasDigital = new javax.swing.JMenuItem();
        ppIKP = new javax.swing.JMenuItem();
        MnTeridentifikasiTB = new javax.swing.JMenuItem();
        ppDPJP = new javax.swing.JMenuItem();
        MnInputHP = new javax.swing.JMenuItem();
        MnInterpretasiEkg = new javax.swing.JMenuItem();
        MnStatus = new javax.swing.JMenu();
        ppBerkas = new javax.swing.JMenuItem();
        ppDiperiksa = new javax.swing.JMenuItem();
        ppDilayani = new javax.swing.JMenuItem();
        MnSudah = new javax.swing.JMenuItem();
        MnBelum = new javax.swing.JMenuItem();
        MnBatal = new javax.swing.JMenuItem();
        MnDirujuk = new javax.swing.JMenuItem();
        MnDIrawat = new javax.swing.JMenuItem();
        MnMeninggal = new javax.swing.JMenuItem();
        MnPulangPaksa = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        MnStatusBaru = new javax.swing.JMenuItem();
        MnStatusLama = new javax.swing.JMenuItem();
        MnUrut = new javax.swing.JMenu();
        MnUrutNoRawatDesc = new javax.swing.JMenuItem();
        MnUrutNoRawatAsc = new javax.swing.JMenuItem();
        MnUrutTanggalDesc = new javax.swing.JMenuItem();
        MnUrutTanggalAsc = new javax.swing.JMenuItem();
        MnUrutDokterDesc = new javax.swing.JMenuItem();
        MnUrutDokterAsc = new javax.swing.JMenuItem();
        MnUrutPoliklinikDesc = new javax.swing.JMenuItem();
        MnUrutPoliklinikAsc = new javax.swing.JMenuItem();
        MnUrutPenjabDesc = new javax.swing.JMenuItem();
        MnUrutPenjabAsc = new javax.swing.JMenuItem();
        MnUrutStatusDesc = new javax.swing.JMenuItem();
        MnUrutStatusAsc = new javax.swing.JMenuItem();
        MnUrutRegDesc1 = new javax.swing.JMenuItem();
        MnUrutRegAsc1 = new javax.swing.JMenuItem();
        MnHemodialisa = new javax.swing.JMenuItem();
        ppRiwayat = new javax.swing.JMenuItem();
        MnRiwayatObatRacikan = new javax.swing.JMenuItem();
        MnHapusData = new javax.swing.JMenu();
        MnHapusTagihanOperasi = new javax.swing.JMenuItem();
        MnHapusObatOperasi = new javax.swing.JMenuItem();
        MnMCU = new javax.swing.JMenu();
        MnPaketMCU = new javax.swing.JMenuItem();
        MnPaketNapza = new javax.swing.JMenuItem();
        MnPaketMMPI = new javax.swing.JMenuItem();
        MnPaketHajiLaki = new javax.swing.JMenuItem();
        MnPaketHajiPerempuan = new javax.swing.JMenuItem();
        MnNapza6Param = new javax.swing.JMenuItem();
        Kd2 = new widget.TextBox();
        DlgSakit = new javax.swing.JDialog();
        internalFrame3 = new widget.InternalFrame();
        panelBiasa2 = new widget.PanelBiasa();
        TglSakit1 = new widget.Tanggal();
        jLabel31 = new widget.Label();
        BtnPrint2 = new widget.Button();
        BtnKeluar2 = new widget.Button();
        jLabel32 = new widget.Label();
        TglSakit2 = new widget.Tanggal();
        jLabel33 = new widget.Label();
        lmsakit = new widget.TextBox();
        DlgSakit2 = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        panelBiasa4 = new widget.PanelBiasa();
        jLabel37 = new widget.Label();
        BtnPrint5 = new widget.Button();
        BtnKeluar4 = new widget.Button();
        NomorSurat = new widget.TextBox();
        BtnSeek5 = new widget.Button();
        CrDokter3 = new widget.TextBox();
        jLabel24 = new widget.Label();
        NoBalasan = new widget.TextBox();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        MnPermintaan1 = new javax.swing.JMenu();
        MnJadwalOperasi1 = new javax.swing.JMenuItem();
        MnSKDPBPJS1 = new javax.swing.JMenuItem();
        MnPermintaanLab1 = new javax.swing.JMenuItem();
        MnPermintaanRadiologi1 = new javax.swing.JMenuItem();
        MnKamarInap1 = new javax.swing.JMenuItem();
        MnTindakan1 = new javax.swing.JMenu();
        MnRawatJalan1 = new javax.swing.JMenuItem();
        MnPeriksaLab1 = new javax.swing.JMenuItem();
        MnPeriksaRadiologi1 = new javax.swing.JMenuItem();
        MnOperasi1 = new javax.swing.JMenuItem();
        MnObat1 = new javax.swing.JMenu();
        MnPemberianObat1 = new javax.swing.JMenuItem();
        MnNoResep1 = new javax.swing.JMenuItem();
        MnResepDOkter1 = new javax.swing.JMenuItem();
        MnBilling1 = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        MenuInputData1 = new javax.swing.JMenu();
        ppBerkasDigital1 = new javax.swing.JMenuItem();
        ppIKP1 = new javax.swing.JMenuItem();
        MnDiagnosa1 = new javax.swing.JMenuItem();
        MnTeridentifikasiTB1 = new javax.swing.JMenuItem();
        MnUrut1 = new javax.swing.JMenu();
        MnUrutNoRawatDesc1 = new javax.swing.JMenuItem();
        MnUrutNoRawatAsc1 = new javax.swing.JMenuItem();
        MnUrutTanggalDesc1 = new javax.swing.JMenuItem();
        MnUrutTanggalAsc1 = new javax.swing.JMenuItem();
        MnUrutDokterDesc1 = new javax.swing.JMenuItem();
        MnUrutDokterAsc1 = new javax.swing.JMenuItem();
        MnUrutPoliklinikDesc1 = new javax.swing.JMenuItem();
        MnUrutPoliklinikAsc1 = new javax.swing.JMenuItem();
        MnUrutPenjabDesc1 = new javax.swing.JMenuItem();
        MnUrutPenjabAsc1 = new javax.swing.JMenuItem();
        MnUrutStatusDesc1 = new javax.swing.JMenuItem();
        MnUrutStatusAsc1 = new javax.swing.JMenuItem();
        ppRiwayat1 = new javax.swing.JMenuItem();
        MnHapusRujukan = new javax.swing.JMenuItem();
        MnInterpretasiEkg2 = new javax.swing.JMenuItem();
        MnUbahRujukan = new javax.swing.JMenuItem();
        MnJawabKonsul = new javax.swing.JMenuItem();
        DlgCatatan = new javax.swing.JDialog();
        internalFrame6 = new widget.InternalFrame();
        LabelCatatan = new widget.Label();
        DlgInterpretasiEKG = new javax.swing.JDialog();
        internalFrame7 = new widget.InternalFrame();
        TNoRMint = new widget.TextBox();
        TNoRwint = new widget.TextBox();
        jLabel12 = new widget.Label();
        TPasienint = new widget.TextBox();
        jLabel25 = new widget.Label();
        BtnSimpan2 = new widget.Button();
        BtnKeluar5 = new widget.Button();
        scrollPane1 = new widget.ScrollPane();
        TKesan = new widget.TextArea();
        BtnPrint1 = new widget.Button();
        Tind = new widget.TextBox();
        DlgJawabanKonsul = new javax.swing.JDialog();
        internalFrame8 = new widget.InternalFrame();
        jPanel1 = new javax.swing.JPanel();
        jLabel26 = new widget.Label();
        jLabel27 = new widget.Label();
        LabelNama = new widget.Label();
        jLabel28 = new widget.Label();
        LabelNoRM = new widget.Label();
        jLabel29 = new widget.Label();
        LabelNoRW = new widget.Label();
        jLabel38 = new widget.Label();
        LabelDokter = new widget.Label();
        jLabel39 = new widget.Label();
        LabelPoliTujuan = new widget.Label();
        jLabel40 = new widget.Label();
        jLabel41 = new widget.Label();
        LabelDokterTujuan = new widget.Label();
        jLabel42 = new widget.Label();
        LabelPoliPerujuk = new widget.Label();
        jLabel44 = new widget.Label();
        jLabel45 = new widget.Label();
        LabelDiagnosa = new widget.Label();
        jLabel46 = new widget.Label();
        TSaranJawab = new widget.TextArea();
        TKonsulJawab = new widget.TextArea();
        TPemeriksaanJawab = new widget.TextArea();
        BtnKeluar6 = new widget.Button();
        jLabel47 = new widget.Label();
        WindowUbahRujukan = new javax.swing.JDialog();
        internalFrame9 = new widget.InternalFrame();
        BtnCloseIn6 = new widget.Button();
        BtnSimpan6 = new widget.Button();
        jLabel34 = new widget.Label();
        KdPoli1 = new widget.TextBox();
        NmPoli1 = new widget.TextBox();
        BtnPoli1 = new widget.Button();
        Kategori = new widget.TextBox();
        WindowRunningText = new javax.swing.JDialog();
        internalFrame11 = new widget.InternalFrame();
        BtnCloseIn7 = new widget.Button();
        BtnSimpan7 = new widget.Button();
        jLabel36 = new widget.Label();
        KdPoli2 = new widget.TextBox();
        NmPoli2 = new widget.TextBox();
        BtnPoli2 = new widget.Button();
        TRunningText = new widget.TextBox();
        jLabel43 = new widget.Label();
        ObatKronis = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        jPanel2 = new javax.swing.JPanel();
        panelGlass6 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnEdit = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        BtnPanggil = new widget.Button();
        BtnText = new widget.Button();
        panelGlass7 = new widget.panelisi();
        jLabel15 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel17 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        ChkSemua = new widget.CekBox();
        panelGlass8 = new widget.panelisi();
        jLabel14 = new widget.Label();
        CrDokter = new widget.TextBox();
        BtnSeek3 = new widget.Button();
        jLabel16 = new widget.Label();
        CrPoli = new widget.TextBox();
        BtnSeek4 = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        jLabel4 = new widget.Label();
        TDokter = new widget.TextBox();
        TNoRw = new widget.TextBox();
        jLabel8 = new widget.Label();
        jLabel13 = new widget.Label();
        jLabel9 = new widget.Label();
        DTPReg = new widget.Tanggal();
        jLabel20 = new widget.Label();
        TPngJwb = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TNoReg = new widget.TextBox();
        jLabel21 = new widget.Label();
        CmbJam = new widget.ComboBox();
        CmbMenit = new widget.ComboBox();
        CmbDetik = new widget.ComboBox();
        jLabel7 = new widget.Label();
        TAlmt = new widget.TextBox();
        BtnPasien = new widget.Button();
        TPasien = new widget.TextBox();
        jLabel22 = new widget.Label();
        THbngn = new widget.TextBox();
        ChkJln = new widget.CekBox();
        jLabel19 = new widget.Label();
        TPoli = new widget.TextBox();
        TBiaya = new widget.TextBox();
        kddokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        kdpoli = new widget.TextBox();
        BtnUnit = new widget.Button();
        jLabel30 = new widget.Label();
        TStatus = new widget.TextBox();
        jLabel18 = new widget.Label();
        kdpnj = new widget.TextBox();
        nmpnj = new widget.TextBox();
        btnPenjab = new widget.Button();
        jLabel23 = new widget.Label();
        AsalRujukan = new widget.TextBox();
        btnPenjab1 = new widget.Button();
        ChkTracker = new widget.CekBox();
        ChkInput = new widget.CekBox();
        TabRawat = new javax.swing.JTabbedPane();
        Scroll = new widget.ScrollPane();
        tbPetugas = new widget.Table();
        Scroll1 = new widget.ScrollPane();
        tbPetugas2 = new widget.Table();
        Scroll2 = new widget.ScrollPane();
        tbPetugas1 = new widget.Table();
        Scroll3 = new widget.ScrollPane();
        tbPetugas3 = new widget.Table();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnPermintaan.setBackground(new java.awt.Color(250, 255, 245));
        MnPermintaan.setForeground(new java.awt.Color(70, 70, 70));
        MnPermintaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaan.setText("Permintaan");
        MnPermintaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaan.setIconTextGap(5);
        MnPermintaan.setName("MnPermintaan"); // NOI18N
        MnPermintaan.setPreferredSize(new java.awt.Dimension(260, 26));

        MnJadwalOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnJadwalOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnJadwalOperasi.setForeground(new java.awt.Color(70, 70, 70));
        MnJadwalOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnJadwalOperasi.setText("Jadwal Operasi");
        MnJadwalOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnJadwalOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnJadwalOperasi.setName("MnJadwalOperasi"); // NOI18N
        MnJadwalOperasi.setPreferredSize(new java.awt.Dimension(180, 26));
        MnJadwalOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnJadwalOperasiActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnJadwalOperasi);

        MnSKDPBPJS.setBackground(new java.awt.Color(255, 255, 254));
        MnSKDPBPJS.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSKDPBPJS.setForeground(new java.awt.Color(70, 70, 70));
        MnSKDPBPJS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSKDPBPJS.setText("SKDP BPJS");
        MnSKDPBPJS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSKDPBPJS.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSKDPBPJS.setName("MnSKDPBPJS"); // NOI18N
        MnSKDPBPJS.setPreferredSize(new java.awt.Dimension(180, 26));
        MnSKDPBPJS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSKDPBPJSActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnSKDPBPJS);

        MnPermintaanLab.setBackground(new java.awt.Color(255, 255, 254));
        MnPermintaanLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaanLab.setForeground(new java.awt.Color(70, 70, 70));
        MnPermintaanLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaanLab.setText("Permintaan Lab");
        MnPermintaanLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaanLab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaanLab.setName("MnPermintaanLab"); // NOI18N
        MnPermintaanLab.setPreferredSize(new java.awt.Dimension(180, 26));
        MnPermintaanLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPermintaanLabActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnPermintaanLab);

        MnPermintaanRadiologi.setBackground(new java.awt.Color(255, 255, 254));
        MnPermintaanRadiologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaanRadiologi.setForeground(new java.awt.Color(70, 70, 70));
        MnPermintaanRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaanRadiologi.setText("Permintaan Radiologi");
        MnPermintaanRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaanRadiologi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaanRadiologi.setName("MnPermintaanRadiologi"); // NOI18N
        MnPermintaanRadiologi.setPreferredSize(new java.awt.Dimension(180, 26));
        MnPermintaanRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPermintaanRadiologiActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnPermintaanRadiologi);

        jPopupMenu1.add(MnPermintaan);

        MnSetPasien.setBackground(new java.awt.Color(250, 255, 245));
        MnSetPasien.setForeground(new java.awt.Color(70, 70, 70));
        MnSetPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSetPasien.setText("Set Pasien");
        MnSetPasien.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSetPasien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSetPasien.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSetPasien.setIconTextGap(5);
        MnSetPasien.setName("MnSetPasien"); // NOI18N
        MnSetPasien.setPreferredSize(new java.awt.Dimension(250, 26));

        MnPrioritas.setBackground(new java.awt.Color(255, 255, 254));
        MnPrioritas.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPrioritas.setForeground(new java.awt.Color(70, 70, 70));
        MnPrioritas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPrioritas.setText("Set Kategori Pasien Prioritas");
        MnPrioritas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPrioritas.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPrioritas.setName("MnPrioritas"); // NOI18N
        MnPrioritas.setPreferredSize(new java.awt.Dimension(170, 26));
        MnPrioritas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPrioritasActionPerformed(evt);
            }
        });
        MnSetPasien.add(MnPrioritas);

        MnKronis.setBackground(new java.awt.Color(255, 255, 254));
        MnKronis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKronis.setForeground(new java.awt.Color(70, 70, 70));
        MnKronis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKronis.setText("Set Status Obat Kronis");
        MnKronis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnKronis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnKronis.setName("MnKronis"); // NOI18N
        MnKronis.setPreferredSize(new java.awt.Dimension(170, 26));
        MnKronis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKronisActionPerformed(evt);
            }
        });
        MnSetPasien.add(MnKronis);

        jPopupMenu1.add(MnSetPasien);

        MnKamarInap.setBackground(new java.awt.Color(255, 255, 254));
        MnKamarInap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKamarInap.setForeground(new java.awt.Color(70, 70, 70));
        MnKamarInap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKamarInap.setText("Kamar Inap");
        MnKamarInap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnKamarInap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnKamarInap.setName("MnKamarInap"); // NOI18N
        MnKamarInap.setPreferredSize(new java.awt.Dimension(260, 26));
        MnKamarInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKamarInapActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnKamarInap);

        MnTindakan.setBackground(new java.awt.Color(250, 255, 245));
        MnTindakan.setForeground(new java.awt.Color(70, 70, 70));
        MnTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTindakan.setText("Tindakan & Pemeriksaan");
        MnTindakan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTindakan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTindakan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTindakan.setIconTextGap(5);
        MnTindakan.setName("MnTindakan"); // NOI18N
        MnTindakan.setPreferredSize(new java.awt.Dimension(260, 26));

        MnRawatJalan.setBackground(new java.awt.Color(255, 255, 254));
        MnRawatJalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRawatJalan.setForeground(new java.awt.Color(70, 70, 70));
        MnRawatJalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRawatJalan.setText("Tagihan/Tindakan Rawat Jalan");
        MnRawatJalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRawatJalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRawatJalan.setName("MnRawatJalan"); // NOI18N
        MnRawatJalan.setPreferredSize(new java.awt.Dimension(240, 26));
        MnRawatJalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRawatJalanActionPerformed(evt);
            }
        });
        MnTindakan.add(MnRawatJalan);

        MnPeriksaLab.setBackground(new java.awt.Color(255, 255, 254));
        MnPeriksaLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaLab.setForeground(new java.awt.Color(70, 70, 70));
        MnPeriksaLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaLab.setText("Periksa Laborat");
        MnPeriksaLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaLab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaLab.setName("MnPeriksaLab"); // NOI18N
        MnPeriksaLab.setPreferredSize(new java.awt.Dimension(240, 26));
        MnPeriksaLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaLabActionPerformed(evt);
            }
        });
        MnTindakan.add(MnPeriksaLab);

        MnPeriksaRadiologi.setBackground(new java.awt.Color(255, 255, 254));
        MnPeriksaRadiologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaRadiologi.setForeground(new java.awt.Color(70, 70, 70));
        MnPeriksaRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaRadiologi.setText("Periksa Radiologi");
        MnPeriksaRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaRadiologi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaRadiologi.setName("MnPeriksaRadiologi"); // NOI18N
        MnPeriksaRadiologi.setPreferredSize(new java.awt.Dimension(240, 26));
        MnPeriksaRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaRadiologiActionPerformed(evt);
            }
        });
        MnTindakan.add(MnPeriksaRadiologi);

        MnOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnOperasi.setForeground(new java.awt.Color(70, 70, 70));
        MnOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnOperasi.setText("Tagihan Operasi/VK");
        MnOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnOperasi.setName("MnOperasi"); // NOI18N
        MnOperasi.setPreferredSize(new java.awt.Dimension(240, 26));
        MnOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnOperasiActionPerformed(evt);
            }
        });
        MnTindakan.add(MnOperasi);

        jPopupMenu1.add(MnTindakan);

        MnObat.setBackground(new java.awt.Color(250, 255, 245));
        MnObat.setForeground(new java.awt.Color(70, 70, 70));
        MnObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnObat.setText("Obat");
        MnObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnObat.setIconTextGap(5);
        MnObat.setName("MnObat"); // NOI18N
        MnObat.setPreferredSize(new java.awt.Dimension(260, 26));

        MnPemberianObat.setBackground(new java.awt.Color(255, 255, 254));
        MnPemberianObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPemberianObat.setForeground(new java.awt.Color(70, 70, 70));
        MnPemberianObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPemberianObat.setText("Pemberian Obat/BHP");
        MnPemberianObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPemberianObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPemberianObat.setName("MnPemberianObat"); // NOI18N
        MnPemberianObat.setPreferredSize(new java.awt.Dimension(160, 26));
        MnPemberianObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPemberianObatActionPerformed(evt);
            }
        });
        MnObat.add(MnPemberianObat);

        MnNoResep.setBackground(new java.awt.Color(255, 255, 254));
        MnNoResep.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnNoResep.setForeground(new java.awt.Color(70, 70, 70));
        MnNoResep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnNoResep.setText("Input No.Resep");
        MnNoResep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnNoResep.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnNoResep.setName("MnNoResep"); // NOI18N
        MnNoResep.setPreferredSize(new java.awt.Dimension(160, 26));
        MnNoResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnNoResepActionPerformed(evt);
            }
        });
        MnObat.add(MnNoResep);

        MnResepDOkter.setBackground(new java.awt.Color(255, 255, 254));
        MnResepDOkter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnResepDOkter.setForeground(new java.awt.Color(70, 70, 70));
        MnResepDOkter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnResepDOkter.setText("Input Resep Dokter");
        MnResepDOkter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnResepDOkter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnResepDOkter.setName("MnResepDOkter"); // NOI18N
        MnResepDOkter.setPreferredSize(new java.awt.Dimension(160, 26));
        MnResepDOkter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnResepDOkterActionPerformed(evt);
            }
        });
        MnObat.add(MnResepDOkter);

        jPopupMenu1.add(MnObat);

        MnPilihBilling.setBackground(new java.awt.Color(250, 255, 245));
        MnPilihBilling.setForeground(new java.awt.Color(70, 70, 70));
        MnPilihBilling.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPilihBilling.setText("Billing/Pembayaran Pasien");
        MnPilihBilling.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPilihBilling.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPilihBilling.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPilihBilling.setIconTextGap(5);
        MnPilihBilling.setName("MnPilihBilling"); // NOI18N
        MnPilihBilling.setPreferredSize(new java.awt.Dimension(260, 26));

        MnBillingParsial.setBackground(new java.awt.Color(255, 255, 254));
        MnBillingParsial.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBillingParsial.setForeground(new java.awt.Color(70, 70, 70));
        MnBillingParsial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBillingParsial.setText("Billing Parsial");
        MnBillingParsial.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBillingParsial.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBillingParsial.setName("MnBillingParsial"); // NOI18N
        MnBillingParsial.setPreferredSize(new java.awt.Dimension(130, 26));
        MnBillingParsial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBillingParsialActionPerformed(evt);
            }
        });
        MnPilihBilling.add(MnBillingParsial);

        MnBilling.setBackground(new java.awt.Color(255, 255, 254));
        MnBilling.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBilling.setForeground(new java.awt.Color(70, 70, 70));
        MnBilling.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBilling.setText("Billing Total");
        MnBilling.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBilling.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBilling.setName("MnBilling"); // NOI18N
        MnBilling.setPreferredSize(new java.awt.Dimension(130, 26));
        MnBilling.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBillingActionPerformed(evt);
            }
        });
        MnPilihBilling.add(MnBilling);

        jPopupMenu1.add(MnPilihBilling);

        jSeparator10.setBackground(new java.awt.Color(190, 220, 180));
        jSeparator10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(190, 220, 180)));
        jSeparator10.setForeground(new java.awt.Color(190, 220, 180));
        jSeparator10.setName("jSeparator10"); // NOI18N
        jSeparator10.setPreferredSize(new java.awt.Dimension(260, 1));
        jPopupMenu1.add(jSeparator10);

        jMenu1.setBackground(new java.awt.Color(250, 255, 245));
        jMenu1.setForeground(new java.awt.Color(70, 70, 70));
        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        jMenu1.setText("Laporan");
        jMenu1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenu1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jMenu1.setIconTextGap(5);
        jMenu1.setName("jMenu1"); // NOI18N
        jMenu1.setPreferredSize(new java.awt.Dimension(260, 26));

        MnLaporanRekapKunjunganPoli.setBackground(new java.awt.Color(255, 255, 254));
        MnLaporanRekapKunjunganPoli.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanRekapKunjunganPoli.setForeground(new java.awt.Color(70, 70, 70));
        MnLaporanRekapKunjunganPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanRekapKunjunganPoli.setText("Laporan Rekap Kunjungan Per Poli");
        MnLaporanRekapKunjunganPoli.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLaporanRekapKunjunganPoli.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLaporanRekapKunjunganPoli.setName("MnLaporanRekapKunjunganPoli"); // NOI18N
        MnLaporanRekapKunjunganPoli.setPreferredSize(new java.awt.Dimension(240, 26));
        MnLaporanRekapKunjunganPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanRekapKunjunganPoliActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanRekapKunjunganPoli);

        MnLaporanRekapKunjunganDokter.setBackground(new java.awt.Color(255, 255, 254));
        MnLaporanRekapKunjunganDokter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanRekapKunjunganDokter.setForeground(new java.awt.Color(70, 70, 70));
        MnLaporanRekapKunjunganDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanRekapKunjunganDokter.setText("Laporan Rekap Kunjungan Per Dokter");
        MnLaporanRekapKunjunganDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLaporanRekapKunjunganDokter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLaporanRekapKunjunganDokter.setName("MnLaporanRekapKunjunganDokter"); // NOI18N
        MnLaporanRekapKunjunganDokter.setPreferredSize(new java.awt.Dimension(240, 26));
        MnLaporanRekapKunjunganDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanRekapKunjunganDokterActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanRekapKunjunganDokter);

        MnLaporanRekapJenisBayar.setBackground(new java.awt.Color(255, 255, 254));
        MnLaporanRekapJenisBayar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanRekapJenisBayar.setForeground(new java.awt.Color(70, 70, 70));
        MnLaporanRekapJenisBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanRekapJenisBayar.setText("Laporan RL 315 Cara bayar");
        MnLaporanRekapJenisBayar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLaporanRekapJenisBayar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLaporanRekapJenisBayar.setName("MnLaporanRekapJenisBayar"); // NOI18N
        MnLaporanRekapJenisBayar.setPreferredSize(new java.awt.Dimension(240, 26));
        MnLaporanRekapJenisBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanRekapJenisBayarActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanRekapJenisBayar);

        MnLaporanRekapRawatDarurat.setBackground(new java.awt.Color(255, 255, 254));
        MnLaporanRekapRawatDarurat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanRekapRawatDarurat.setForeground(new java.awt.Color(70, 70, 70));
        MnLaporanRekapRawatDarurat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanRekapRawatDarurat.setText("Laporan RL 32 Rawat Darurat");
        MnLaporanRekapRawatDarurat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLaporanRekapRawatDarurat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLaporanRekapRawatDarurat.setName("MnLaporanRekapRawatDarurat"); // NOI18N
        MnLaporanRekapRawatDarurat.setPreferredSize(new java.awt.Dimension(240, 26));
        MnLaporanRekapRawatDarurat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanRekapRawatDaruratActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanRekapRawatDarurat);

        MnLaporanRekapKunjunganBulanan.setBackground(new java.awt.Color(255, 255, 254));
        MnLaporanRekapKunjunganBulanan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanRekapKunjunganBulanan.setForeground(new java.awt.Color(70, 70, 70));
        MnLaporanRekapKunjunganBulanan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanRekapKunjunganBulanan.setText("Laporan RL 51");
        MnLaporanRekapKunjunganBulanan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLaporanRekapKunjunganBulanan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLaporanRekapKunjunganBulanan.setName("MnLaporanRekapKunjunganBulanan"); // NOI18N
        MnLaporanRekapKunjunganBulanan.setPreferredSize(new java.awt.Dimension(240, 26));
        MnLaporanRekapKunjunganBulanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanRekapKunjunganBulananActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanRekapKunjunganBulanan);

        MnLaporanRekapKunjunganBulananPoli.setBackground(new java.awt.Color(255, 255, 254));
        MnLaporanRekapKunjunganBulananPoli.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanRekapKunjunganBulananPoli.setForeground(new java.awt.Color(70, 70, 70));
        MnLaporanRekapKunjunganBulananPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanRekapKunjunganBulananPoli.setText("Laporan RL 52");
        MnLaporanRekapKunjunganBulananPoli.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLaporanRekapKunjunganBulananPoli.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLaporanRekapKunjunganBulananPoli.setName("MnLaporanRekapKunjunganBulananPoli"); // NOI18N
        MnLaporanRekapKunjunganBulananPoli.setPreferredSize(new java.awt.Dimension(240, 26));
        MnLaporanRekapKunjunganBulananPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanRekapKunjunganBulananPoliActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanRekapKunjunganBulananPoli);

        MnLaporanRekapPenyakitRalan.setBackground(new java.awt.Color(255, 255, 254));
        MnLaporanRekapPenyakitRalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanRekapPenyakitRalan.setForeground(new java.awt.Color(70, 70, 70));
        MnLaporanRekapPenyakitRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanRekapPenyakitRalan.setText("Laporan Frekuensi Penyakit Ralan");
        MnLaporanRekapPenyakitRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLaporanRekapPenyakitRalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLaporanRekapPenyakitRalan.setName("MnLaporanRekapPenyakitRalan"); // NOI18N
        MnLaporanRekapPenyakitRalan.setPreferredSize(new java.awt.Dimension(240, 26));
        MnLaporanRekapPenyakitRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanRekapPenyakitRalanActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanRekapPenyakitRalan);

        MnLaporanRekapPerujuk.setBackground(new java.awt.Color(255, 255, 254));
        MnLaporanRekapPerujuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanRekapPerujuk.setForeground(new java.awt.Color(70, 70, 70));
        MnLaporanRekapPerujuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanRekapPerujuk.setText("Laporan Rekap Perperujuk");
        MnLaporanRekapPerujuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLaporanRekapPerujuk.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLaporanRekapPerujuk.setName("MnLaporanRekapPerujuk"); // NOI18N
        MnLaporanRekapPerujuk.setPreferredSize(new java.awt.Dimension(240, 26));
        MnLaporanRekapPerujuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanRekapPerujukActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanRekapPerujuk);

        jPopupMenu1.add(jMenu1);

        jMenu4.setBackground(new java.awt.Color(250, 255, 245));
        jMenu4.setForeground(new java.awt.Color(70, 70, 70));
        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        jMenu4.setText("Surat-Surat");
        jMenu4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenu4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jMenu4.setIconTextGap(5);
        jMenu4.setName("jMenu4"); // NOI18N
        jMenu4.setPreferredSize(new java.awt.Dimension(260, 26));

        MnCetakSuratSehat.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratSehat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratSehat.setForeground(new java.awt.Color(70, 70, 70));
        MnCetakSuratSehat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratSehat.setText("Surat Keterangan Sehat 1");
        MnCetakSuratSehat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratSehat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratSehat.setName("MnCetakSuratSehat"); // NOI18N
        MnCetakSuratSehat.setPreferredSize(new java.awt.Dimension(320, 26));
        MnCetakSuratSehat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratSehatActionPerformed(evt);
            }
        });
        jMenu4.add(MnCetakSuratSehat);

        MnCetakSuratSehat1.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratSehat1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratSehat1.setForeground(new java.awt.Color(70, 70, 70));
        MnCetakSuratSehat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratSehat1.setText("Surat Keterangan Sehat 2");
        MnCetakSuratSehat1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratSehat1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratSehat1.setName("MnCetakSuratSehat1"); // NOI18N
        MnCetakSuratSehat1.setPreferredSize(new java.awt.Dimension(320, 26));
        MnCetakSuratSehat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratSehat1ActionPerformed(evt);
            }
        });
        jMenu4.add(MnCetakSuratSehat1);

        MnCetakBebasNarkoba.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakBebasNarkoba.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakBebasNarkoba.setForeground(new java.awt.Color(70, 70, 70));
        MnCetakBebasNarkoba.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakBebasNarkoba.setText("Surat Keterangan Bebas Narkoba");
        MnCetakBebasNarkoba.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakBebasNarkoba.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakBebasNarkoba.setName("MnCetakBebasNarkoba"); // NOI18N
        MnCetakBebasNarkoba.setPreferredSize(new java.awt.Dimension(320, 26));
        MnCetakBebasNarkoba.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakBebasNarkobaActionPerformed(evt);
            }
        });
        jMenu4.add(MnCetakBebasNarkoba);

        MnCetakSuratSakit.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratSakit.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratSakit.setForeground(new java.awt.Color(70, 70, 70));
        MnCetakSuratSakit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratSakit.setText("Surat Cuti Sakit 1");
        MnCetakSuratSakit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratSakit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratSakit.setName("MnCetakSuratSakit"); // NOI18N
        MnCetakSuratSakit.setPreferredSize(new java.awt.Dimension(320, 26));
        MnCetakSuratSakit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratSakitActionPerformed(evt);
            }
        });
        jMenu4.add(MnCetakSuratSakit);

        MnCetakSuratSakit2.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratSakit2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratSakit2.setForeground(new java.awt.Color(70, 70, 70));
        MnCetakSuratSakit2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratSakit2.setText("Surat Cuti Sakit 2");
        MnCetakSuratSakit2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratSakit2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratSakit2.setName("MnCetakSuratSakit2"); // NOI18N
        MnCetakSuratSakit2.setPreferredSize(new java.awt.Dimension(320, 26));
        MnCetakSuratSakit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratSakit2ActionPerformed(evt);
            }
        });
        jMenu4.add(MnCetakSuratSakit2);

        MnCetakSuratSakit3.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratSakit3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratSakit3.setForeground(new java.awt.Color(70, 70, 70));
        MnCetakSuratSakit3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratSakit3.setText("Surat Cuti Sakit 3");
        MnCetakSuratSakit3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratSakit3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratSakit3.setName("MnCetakSuratSakit3"); // NOI18N
        MnCetakSuratSakit3.setPreferredSize(new java.awt.Dimension(320, 26));
        MnCetakSuratSakit3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratSakit3ActionPerformed(evt);
            }
        });
        jMenu4.add(MnCetakSuratSakit3);

        MnCetakSuratSakit1.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratSakit1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratSakit1.setForeground(new java.awt.Color(70, 70, 70));
        MnCetakSuratSakit1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratSakit1.setText("Surat Keterangan Rawat Inap");
        MnCetakSuratSakit1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratSakit1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratSakit1.setName("MnCetakSuratSakit1"); // NOI18N
        MnCetakSuratSakit1.setPreferredSize(new java.awt.Dimension(320, 26));
        MnCetakSuratSakit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratSakit1ActionPerformed(evt);
            }
        });
        jMenu4.add(MnCetakSuratSakit1);

        MnCetakRegister.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakRegister.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakRegister.setForeground(new java.awt.Color(70, 70, 70));
        MnCetakRegister.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakRegister.setText("Bukti Register");
        MnCetakRegister.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakRegister.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakRegister.setName("MnCetakRegister"); // NOI18N
        MnCetakRegister.setPreferredSize(new java.awt.Dimension(320, 26));
        MnCetakRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakRegisterActionPerformed(evt);
            }
        });
        jMenu4.add(MnCetakRegister);

        MnPersetujuanMedis.setBackground(new java.awt.Color(255, 255, 254));
        MnPersetujuanMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPersetujuanMedis.setForeground(new java.awt.Color(70, 70, 70));
        MnPersetujuanMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPersetujuanMedis.setText("Persetujuan Medis");
        MnPersetujuanMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPersetujuanMedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPersetujuanMedis.setName("MnPersetujuanMedis"); // NOI18N
        MnPersetujuanMedis.setPreferredSize(new java.awt.Dimension(320, 26));
        MnPersetujuanMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPersetujuanMedisActionPerformed(evt);
            }
        });
        jMenu4.add(MnPersetujuanMedis);

        MnBuktiPelayananRalan.setBackground(new java.awt.Color(255, 255, 254));
        MnBuktiPelayananRalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBuktiPelayananRalan.setForeground(new java.awt.Color(70, 70, 70));
        MnBuktiPelayananRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBuktiPelayananRalan.setText("Surat Jaminan & Bukti Pelayanan Ralan");
        MnBuktiPelayananRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBuktiPelayananRalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBuktiPelayananRalan.setName("MnBuktiPelayananRalan"); // NOI18N
        MnBuktiPelayananRalan.setPreferredSize(new java.awt.Dimension(320, 26));
        MnBuktiPelayananRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBuktiPelayananRalanActionPerformed(evt);
            }
        });
        jMenu4.add(MnBuktiPelayananRalan);

        MnLembarCasemix.setBackground(new java.awt.Color(255, 255, 254));
        MnLembarCasemix.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLembarCasemix.setForeground(new java.awt.Color(70, 70, 70));
        MnLembarCasemix.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLembarCasemix.setText("Lembar Casemix 1");
        MnLembarCasemix.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLembarCasemix.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLembarCasemix.setName("MnLembarCasemix"); // NOI18N
        MnLembarCasemix.setPreferredSize(new java.awt.Dimension(320, 26));
        MnLembarCasemix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLembarCasemixActionPerformed(evt);
            }
        });
        jMenu4.add(MnLembarCasemix);

        MnLembarCasemix1.setBackground(new java.awt.Color(255, 255, 254));
        MnLembarCasemix1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLembarCasemix1.setForeground(new java.awt.Color(70, 70, 70));
        MnLembarCasemix1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLembarCasemix1.setText("Lembar Casemix 2");
        MnLembarCasemix1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLembarCasemix1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLembarCasemix1.setName("MnLembarCasemix1"); // NOI18N
        MnLembarCasemix1.setPreferredSize(new java.awt.Dimension(320, 26));
        MnLembarCasemix1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLembarCasemix1ActionPerformed(evt);
            }
        });
        jMenu4.add(MnLembarCasemix1);

        MnLembarCasemix2.setBackground(new java.awt.Color(255, 255, 254));
        MnLembarCasemix2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLembarCasemix2.setForeground(new java.awt.Color(70, 70, 70));
        MnLembarCasemix2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLembarCasemix2.setText("Lembar Casemix 3");
        MnLembarCasemix2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLembarCasemix2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLembarCasemix2.setName("MnLembarCasemix2"); // NOI18N
        MnLembarCasemix2.setPreferredSize(new java.awt.Dimension(320, 26));
        MnLembarCasemix2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLembarCasemix2ActionPerformed(evt);
            }
        });
        jMenu4.add(MnLembarCasemix2);

        MnSPBK.setBackground(new java.awt.Color(255, 255, 254));
        MnSPBK.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSPBK.setForeground(new java.awt.Color(70, 70, 70));
        MnSPBK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSPBK.setText("Surat Bukti Pelayanan Kesehatan (SBPK) 1");
        MnSPBK.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSPBK.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSPBK.setName("MnSPBK"); // NOI18N
        MnSPBK.setPreferredSize(new java.awt.Dimension(320, 26));
        MnSPBK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSPBKActionPerformed(evt);
            }
        });
        jMenu4.add(MnSPBK);

        MnSPBK1.setBackground(new java.awt.Color(255, 255, 254));
        MnSPBK1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSPBK1.setForeground(new java.awt.Color(70, 70, 70));
        MnSPBK1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSPBK1.setMnemonic('z');
        MnSPBK1.setText("Surat Bukti Pelayanan Kesehatan (SBPK) 2");
        MnSPBK1.setToolTipText("Z");
        MnSPBK1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSPBK1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSPBK1.setName("MnSPBK1"); // NOI18N
        MnSPBK1.setPreferredSize(new java.awt.Dimension(320, 26));
        MnSPBK1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSPBK1ActionPerformed(evt);
            }
        });
        jMenu4.add(MnSPBK1);

        MnJKRA.setBackground(new java.awt.Color(255, 255, 254));
        MnJKRA.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnJKRA.setForeground(new java.awt.Color(70, 70, 70));
        MnJKRA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnJKRA.setText("Surat Jaminan Kesehatan Nasional (JKRA) Rawat Jalan");
        MnJKRA.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnJKRA.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnJKRA.setName("MnJKRA"); // NOI18N
        MnJKRA.setPreferredSize(new java.awt.Dimension(320, 26));
        MnJKRA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnJKRAActionPerformed(evt);
            }
        });
        jMenu4.add(MnJKRA);

        MnLembarRalan.setBackground(new java.awt.Color(255, 255, 254));
        MnLembarRalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLembarRalan.setForeground(new java.awt.Color(70, 70, 70));
        MnLembarRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLembarRalan.setMnemonic('w');
        MnLembarRalan.setText("Lembar Rawat Jalan");
        MnLembarRalan.setToolTipText("W");
        MnLembarRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLembarRalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLembarRalan.setName("MnLembarRalan"); // NOI18N
        MnLembarRalan.setPreferredSize(new java.awt.Dimension(320, 26));
        MnLembarRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLembarRalanActionPerformed(evt);
            }
        });
        jMenu4.add(MnLembarRalan);

        MnBlangkoResep.setBackground(new java.awt.Color(255, 255, 254));
        MnBlangkoResep.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBlangkoResep.setForeground(new java.awt.Color(70, 70, 70));
        MnBlangkoResep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBlangkoResep.setMnemonic('w');
        MnBlangkoResep.setText("Blanko Resep");
        MnBlangkoResep.setToolTipText("W");
        MnBlangkoResep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBlangkoResep.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBlangkoResep.setName("MnBlangkoResep"); // NOI18N
        MnBlangkoResep.setPreferredSize(new java.awt.Dimension(320, 26));
        MnBlangkoResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBlangkoResepActionPerformed(evt);
            }
        });
        jMenu4.add(MnBlangkoResep);

        MnCetakSuratSehat2.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratSehat2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratSehat2.setForeground(new java.awt.Color(70, 70, 70));
        MnCetakSuratSehat2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratSehat2.setText("Data Surat Sehat");
        MnCetakSuratSehat2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratSehat2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratSehat2.setName("MnDataSuratSehat1"); // NOI18N
        MnCetakSuratSehat2.setPreferredSize(new java.awt.Dimension(320, 26));
        MnCetakSuratSehat2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratSehat2ActionPerformed(evt);
            }
        });
        jMenu4.add(MnCetakSuratSehat2);

        MnSuratNapza.setBackground(new java.awt.Color(255, 255, 254));
        MnSuratNapza.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSuratNapza.setForeground(new java.awt.Color(70, 70, 70));
        MnSuratNapza.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSuratNapza.setText("Data Surat Napza");
        MnSuratNapza.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSuratNapza.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSuratNapza.setName("MnSuratNapza"); // NOI18N
        MnSuratNapza.setPreferredSize(new java.awt.Dimension(320, 26));
        MnSuratNapza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSuratNapzaActionPerformed(evt);
            }
        });
        jMenu4.add(MnSuratNapza);

        jPopupMenu1.add(jMenu4);

        jMenu3.setBackground(new java.awt.Color(250, 255, 245));
        jMenu3.setForeground(new java.awt.Color(70, 70, 70));
        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        jMenu3.setText("Check List Kelengkapan Pendaftaran");
        jMenu3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenu3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jMenu3.setIconTextGap(5);
        jMenu3.setName("jMenu3"); // NOI18N
        jMenu3.setPreferredSize(new java.awt.Dimension(260, 26));

        MnCheckList.setBackground(new java.awt.Color(255, 255, 254));
        MnCheckList.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCheckList.setForeground(new java.awt.Color(70, 70, 70));
        MnCheckList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCheckList.setText("Chekc List Kelengkapan Pendaftaran Kanan");
        MnCheckList.setToolTipText("");
        MnCheckList.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCheckList.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCheckList.setName("MnCheckList"); // NOI18N
        MnCheckList.setPreferredSize(new java.awt.Dimension(310, 26));
        MnCheckList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCheckListActionPerformed(evt);
            }
        });
        jMenu3.add(MnCheckList);

        MnCheckList1.setBackground(new java.awt.Color(255, 255, 254));
        MnCheckList1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCheckList1.setForeground(new java.awt.Color(70, 70, 70));
        MnCheckList1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCheckList1.setText("Chekc List Kelengkapan Pendaftaran Kiri");
        MnCheckList1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCheckList1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCheckList1.setName("MnCheckList1"); // NOI18N
        MnCheckList1.setPreferredSize(new java.awt.Dimension(310, 26));
        MnCheckList1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCheckList1ActionPerformed(evt);
            }
        });
        jMenu3.add(MnCheckList1);

        MnCheckList2.setBackground(new java.awt.Color(255, 255, 254));
        MnCheckList2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCheckList2.setForeground(new java.awt.Color(70, 70, 70));
        MnCheckList2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCheckList2.setText("Chekc List Kelengkapan Pendaftaran Kanan+Tracker");
        MnCheckList2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCheckList2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCheckList2.setName("MnCheckList2"); // NOI18N
        MnCheckList2.setPreferredSize(new java.awt.Dimension(310, 26));
        MnCheckList2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCheckList2ActionPerformed(evt);
            }
        });
        jMenu3.add(MnCheckList2);

        MnCheckList3.setBackground(new java.awt.Color(255, 255, 254));
        MnCheckList3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCheckList3.setForeground(new java.awt.Color(70, 70, 70));
        MnCheckList3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCheckList3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCheckList3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCheckList3.setLabel("Chekc List Kelengkapan Pendaftaran Kiri+Tracker");
        MnCheckList3.setName("MnCheckList3"); // NOI18N
        MnCheckList3.setPreferredSize(new java.awt.Dimension(310, 26));
        MnCheckList3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCheckList3ActionPerformed(evt);
            }
        });
        jMenu3.add(MnCheckList3);

        jPopupMenu1.add(jMenu3);

        jMenu5.setBackground(new java.awt.Color(250, 255, 245));
        jMenu5.setForeground(new java.awt.Color(70, 70, 70));
        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        jMenu5.setText("Lembar Periksa Pasien");
        jMenu5.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenu5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jMenu5.setIconTextGap(5);
        jMenu5.setName("jMenu5"); // NOI18N
        jMenu5.setPreferredSize(new java.awt.Dimension(260, 26));

        MnCheckList4.setBackground(new java.awt.Color(255, 255, 254));
        MnCheckList4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCheckList4.setForeground(new java.awt.Color(70, 70, 70));
        MnCheckList4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCheckList4.setText("Lembar Periksa Pasien Kanan");
        MnCheckList4.setToolTipText("");
        MnCheckList4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCheckList4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCheckList4.setName("MnCheckList4"); // NOI18N
        MnCheckList4.setPreferredSize(new java.awt.Dimension(240, 26));
        MnCheckList4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCheckList4ActionPerformed(evt);
            }
        });
        jMenu5.add(MnCheckList4);

        MnCheckList5.setBackground(new java.awt.Color(255, 255, 254));
        MnCheckList5.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCheckList5.setForeground(new java.awt.Color(70, 70, 70));
        MnCheckList5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCheckList5.setText("Lembar Periksa Pasien Kiri");
        MnCheckList5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCheckList5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCheckList5.setName("MnCheckList5"); // NOI18N
        MnCheckList5.setPreferredSize(new java.awt.Dimension(240, 26));
        MnCheckList5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCheckList5ActionPerformed(evt);
            }
        });
        jMenu5.add(MnCheckList5);

        MnCheckList6.setBackground(new java.awt.Color(255, 255, 254));
        MnCheckList6.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCheckList6.setForeground(new java.awt.Color(70, 70, 70));
        MnCheckList6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCheckList6.setText("Lembar Periksa Pasien Kanan 2");
        MnCheckList6.setToolTipText("");
        MnCheckList6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCheckList6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCheckList6.setName("MnCheckList6"); // NOI18N
        MnCheckList6.setPreferredSize(new java.awt.Dimension(240, 26));
        MnCheckList6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCheckList6ActionPerformed(evt);
            }
        });
        jMenu5.add(MnCheckList6);

        MnCheckList7.setBackground(new java.awt.Color(255, 255, 254));
        MnCheckList7.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCheckList7.setForeground(new java.awt.Color(70, 70, 70));
        MnCheckList7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCheckList7.setText("Lembar Periksa Pasien Kiri 2");
        MnCheckList7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCheckList7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCheckList7.setName("MnCheckList7"); // NOI18N
        MnCheckList7.setPreferredSize(new java.awt.Dimension(240, 26));
        MnCheckList7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCheckList7ActionPerformed(evt);
            }
        });
        jMenu5.add(MnCheckList7);

        jPopupMenu1.add(jMenu5);

        jMenu6.setBackground(new java.awt.Color(250, 255, 245));
        jMenu6.setForeground(new java.awt.Color(70, 70, 70));
        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        jMenu6.setText("Label & Barcode");
        jMenu6.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenu6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jMenu6.setIconTextGap(5);
        jMenu6.setName("jMenu6"); // NOI18N
        jMenu6.setPreferredSize(new java.awt.Dimension(260, 26));

        MnLabelTracker.setBackground(new java.awt.Color(255, 255, 254));
        MnLabelTracker.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelTracker.setForeground(new java.awt.Color(70, 70, 70));
        MnLabelTracker.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLabelTracker.setText("Label Tracker 1");
        MnLabelTracker.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLabelTracker.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLabelTracker.setName("MnLabelTracker"); // NOI18N
        MnLabelTracker.setPreferredSize(new java.awt.Dimension(200, 26));
        MnLabelTracker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLabelTrackerActionPerformed(evt);
            }
        });
        jMenu6.add(MnLabelTracker);

        MnLabelTracker1.setBackground(new java.awt.Color(255, 255, 254));
        MnLabelTracker1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelTracker1.setForeground(new java.awt.Color(70, 70, 70));
        MnLabelTracker1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLabelTracker1.setText("Label Tracker 2");
        MnLabelTracker1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLabelTracker1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLabelTracker1.setName("MnLabelTracker1"); // NOI18N
        MnLabelTracker1.setPreferredSize(new java.awt.Dimension(200, 26));
        MnLabelTracker1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLabelTracker1ActionPerformed(evt);
            }
        });
        jMenu6.add(MnLabelTracker1);

        MnLabelTracker2.setBackground(new java.awt.Color(255, 255, 254));
        MnLabelTracker2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelTracker2.setForeground(new java.awt.Color(70, 70, 70));
        MnLabelTracker2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLabelTracker2.setText("Label Tracker 3");
        MnLabelTracker2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLabelTracker2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLabelTracker2.setName("MnLabelTracker2"); // NOI18N
        MnLabelTracker2.setPreferredSize(new java.awt.Dimension(200, 26));
        MnLabelTracker2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLabelTracker2ActionPerformed(evt);
            }
        });
        jMenu6.add(MnLabelTracker2);

        MnLabelTracker3.setBackground(new java.awt.Color(255, 255, 254));
        MnLabelTracker3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelTracker3.setForeground(new java.awt.Color(70, 70, 70));
        MnLabelTracker3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLabelTracker3.setText("Label Tracker 4");
        MnLabelTracker3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLabelTracker3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLabelTracker3.setName("MnLabelTracker3"); // NOI18N
        MnLabelTracker3.setPreferredSize(new java.awt.Dimension(200, 26));
        MnLabelTracker3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLabelTracker3ActionPerformed(evt);
            }
        });
        jMenu6.add(MnLabelTracker3);

        MnBarcode.setBackground(new java.awt.Color(255, 255, 254));
        MnBarcode.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcode.setForeground(new java.awt.Color(70, 70, 70));
        MnBarcode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcode.setText("Barcode Perawatan 1");
        MnBarcode.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBarcode.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBarcode.setName("MnBarcode"); // NOI18N
        MnBarcode.setPreferredSize(new java.awt.Dimension(200, 26));
        MnBarcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcodeActionPerformed(evt);
            }
        });
        jMenu6.add(MnBarcode);

        MnBarcode1.setBackground(new java.awt.Color(255, 255, 254));
        MnBarcode1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcode1.setForeground(new java.awt.Color(70, 70, 70));
        MnBarcode1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcode1.setText("Barcode Perawatan 2");
        MnBarcode1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBarcode1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBarcode1.setName("MnBarcode1"); // NOI18N
        MnBarcode1.setPreferredSize(new java.awt.Dimension(200, 26));
        MnBarcode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcode1ActionPerformed(evt);
            }
        });
        jMenu6.add(MnBarcode1);

        MnBarcodeRM9.setBackground(new java.awt.Color(255, 255, 254));
        MnBarcodeRM9.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcodeRM9.setForeground(new java.awt.Color(70, 70, 70));
        MnBarcodeRM9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcodeRM9.setMnemonic('L');
        MnBarcodeRM9.setText("Label RM isi 14");
        MnBarcodeRM9.setToolTipText("L");
        MnBarcodeRM9.setName("MnBarcodeRM9"); // NOI18N
        MnBarcodeRM9.setPreferredSize(new java.awt.Dimension(200, 26));
        MnBarcodeRM9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcodeRM9ActionPerformed(evt);
            }
        });
        jMenu6.add(MnBarcodeRM9);

        MnBarcodeRM10.setBackground(new java.awt.Color(255, 255, 254));
        MnBarcodeRM10.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcodeRM10.setForeground(new java.awt.Color(70, 70, 70));
        MnBarcodeRM10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcodeRM10.setMnemonic('L');
        MnBarcodeRM10.setText("Label RM isi 16");
        MnBarcodeRM10.setToolTipText("L");
        MnBarcodeRM10.setName("MnBarcodeRM10"); // NOI18N
        MnBarcodeRM10.setPreferredSize(new java.awt.Dimension(200, 26));
        MnBarcodeRM10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcodeRM10ActionPerformed(evt);
            }
        });
        jMenu6.add(MnBarcodeRM10);

        MnGelang1.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang1.setForeground(new java.awt.Color(70, 70, 70));
        MnGelang1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang1.setText("Gelang Pasien");
        MnGelang1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang1.setName("MnGelang1"); // NOI18N
        MnGelang1.setPreferredSize(new java.awt.Dimension(200, 26));
        MnGelang1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang1ActionPerformed(evt);
            }
        });
        jMenu6.add(MnGelang1);

        jPopupMenu1.add(jMenu6);

        jSeparator11.setBackground(new java.awt.Color(190, 220, 180));
        jSeparator11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(190, 220, 180)));
        jSeparator11.setForeground(new java.awt.Color(190, 220, 180));
        jSeparator11.setName("jSeparator11"); // NOI18N
        jSeparator11.setPreferredSize(new java.awt.Dimension(260, 1));
        jPopupMenu1.add(jSeparator11);

        MnRujukan.setBackground(new java.awt.Color(250, 255, 245));
        MnRujukan.setForeground(new java.awt.Color(70, 70, 70));
        MnRujukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRujukan.setText("Rujukan");
        MnRujukan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujukan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRujukan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRujukan.setIconTextGap(5);
        MnRujukan.setName("MnRujukan"); // NOI18N
        MnRujukan.setPreferredSize(new java.awt.Dimension(260, 26));

        MnRujukMasuk.setBackground(new java.awt.Color(255, 255, 254));
        MnRujukMasuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujukMasuk.setForeground(new java.awt.Color(70, 70, 70));
        MnRujukMasuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRujukMasuk.setText("Masuk");
        MnRujukMasuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRujukMasuk.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRujukMasuk.setName("MnRujukMasuk"); // NOI18N
        MnRujukMasuk.setPreferredSize(new java.awt.Dimension(160, 26));
        MnRujukMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRujukMasukActionPerformed(evt);
            }
        });
        MnRujukan.add(MnRujukMasuk);

        MnRujuk.setBackground(new java.awt.Color(255, 255, 254));
        MnRujuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujuk.setForeground(new java.awt.Color(70, 70, 70));
        MnRujuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRujuk.setText("Keluar");
        MnRujuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRujuk.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRujuk.setName("MnRujuk"); // NOI18N
        MnRujuk.setPreferredSize(new java.awt.Dimension(160, 26));
        MnRujuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRujukActionPerformed(evt);
            }
        });
        MnRujukan.add(MnRujuk);

        MnPoliInternal.setBackground(new java.awt.Color(255, 255, 254));
        MnPoliInternal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPoliInternal.setForeground(new java.awt.Color(70, 70, 70));
        MnPoliInternal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPoliInternal.setText("Poli Internal");
        MnPoliInternal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPoliInternal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPoliInternal.setName("MnPoliInternal"); // NOI18N
        MnPoliInternal.setPreferredSize(new java.awt.Dimension(160, 26));
        MnPoliInternal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPoliInternalActionPerformed(evt);
            }
        });
        MnRujukan.add(MnPoliInternal);

        MnJawabanKonsul.setBackground(new java.awt.Color(255, 255, 254));
        MnJawabanKonsul.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnJawabanKonsul.setForeground(new java.awt.Color(70, 70, 70));
        MnJawabanKonsul.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnJawabanKonsul.setText("Jawaban Konsul");
        MnJawabanKonsul.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnJawabanKonsul.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnJawabanKonsul.setName("MnJawabanKonsul"); // NOI18N
        MnJawabanKonsul.setPreferredSize(new java.awt.Dimension(160, 26));
        MnJawabanKonsul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnJawabanKonsulActionPerformed(evt);
            }
        });
        MnRujukan.add(MnJawabanKonsul);

        jPopupMenu1.add(MnRujukan);

        MnBridging.setBackground(new java.awt.Color(250, 255, 245));
        MnBridging.setForeground(new java.awt.Color(70, 70, 70));
        MnBridging.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBridging.setText("Bridging");
        MnBridging.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBridging.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBridging.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBridging.setIconTextGap(5);
        MnBridging.setName("MnBridging"); // NOI18N
        MnBridging.setPreferredSize(new java.awt.Dimension(260, 26));

        MnSEP.setBackground(new java.awt.Color(255, 255, 254));
        MnSEP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSEP.setForeground(new java.awt.Color(70, 70, 70));
        MnSEP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSEP.setText("SEP BPJS");
        MnSEP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSEP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSEP.setName("MnSEP"); // NOI18N
        MnSEP.setPreferredSize(new java.awt.Dimension(180, 26));
        MnSEP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSEPActionPerformed(evt);
            }
        });
        MnBridging.add(MnSEP);

        ppSuratKontrol.setBackground(new java.awt.Color(255, 255, 254));
        ppSuratKontrol.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSuratKontrol.setForeground(new java.awt.Color(50, 50, 50));
        ppSuratKontrol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSuratKontrol.setText("Rencana Kontrol BPJS");
        ppSuratKontrol.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSuratKontrol.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSuratKontrol.setName("ppSuratKontrol"); // NOI18N
        ppSuratKontrol.setPreferredSize(new java.awt.Dimension(230, 26));
        ppSuratKontrol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSuratKontrolBtnPrintActionPerformed(evt);
            }
        });
        MnBridging.add(ppSuratKontrol);

        ppSuratPRI.setBackground(new java.awt.Color(255, 255, 254));
        ppSuratPRI.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppSuratPRI.setForeground(new java.awt.Color(50, 50, 50));
        ppSuratPRI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppSuratPRI.setText("Perintah Rawat Inap BPJS");
        ppSuratPRI.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppSuratPRI.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppSuratPRI.setName("ppSuratPRI"); // NOI18N
        ppSuratPRI.setPreferredSize(new java.awt.Dimension(230, 26));
        ppSuratPRI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppSuratPRIBtnPrintActionPerformed(evt);
            }
        });
        MnBridging.add(ppSuratPRI);

        MnSJP.setBackground(new java.awt.Color(255, 255, 254));
        MnSJP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSJP.setForeground(new java.awt.Color(70, 70, 70));
        MnSJP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSJP.setText("SJP Inhealth");
        MnSJP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSJP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSJP.setName("MnSJP"); // NOI18N
        MnSJP.setPreferredSize(new java.awt.Dimension(180, 26));
        MnSJP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSJPActionPerformed(evt);
            }
        });
        MnBridging.add(MnSJP);

        MnRujukSisrute.setBackground(new java.awt.Color(255, 255, 254));
        MnRujukSisrute.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujukSisrute.setForeground(new java.awt.Color(70, 70, 70));
        MnRujukSisrute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRujukSisrute.setText("Rujuk Keluar Via Sisrute");
        MnRujukSisrute.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRujukSisrute.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRujukSisrute.setName("MnRujukSisrute"); // NOI18N
        MnRujukSisrute.setPreferredSize(new java.awt.Dimension(180, 26));
        MnRujukSisrute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRujukSisruteActionPerformed(evt);
            }
        });
        MnBridging.add(MnRujukSisrute);

        MnPACS.setBackground(new java.awt.Color(255, 255, 254));
        MnPACS.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPACS.setForeground(new java.awt.Color(70, 70, 70));
        MnPACS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPACS.setText("PACS");
        MnPACS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPACS.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPACS.setName("MnPACS"); // NOI18N
        MnPACS.setPreferredSize(new java.awt.Dimension(180, 26));
        MnPACS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPACSActionPerformed(evt);
            }
        });
        MnBridging.add(MnPACS);

        jPopupMenu1.add(MnBridging);

        MenuInputData.setBackground(new java.awt.Color(250, 255, 245));
        MenuInputData.setForeground(new java.awt.Color(70, 70, 70));
        MenuInputData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MenuInputData.setText("Input Data");
        MenuInputData.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MenuInputData.setIconTextGap(5);
        MenuInputData.setName("MenuInputData"); // NOI18N
        MenuInputData.setPreferredSize(new java.awt.Dimension(260, 26));

        MnDiagnosa.setBackground(new java.awt.Color(255, 255, 254));
        MnDiagnosa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDiagnosa.setForeground(new java.awt.Color(70, 70, 70));
        MnDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDiagnosa.setText("Diagnosa Pasien");
        MnDiagnosa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDiagnosa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDiagnosa.setName("MnDiagnosa"); // NOI18N
        MnDiagnosa.setPreferredSize(new java.awt.Dimension(200, 26));
        MnDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDiagnosaActionPerformed(evt);
            }
        });
        MenuInputData.add(MnDiagnosa);

        ppCatatanPasien.setBackground(new java.awt.Color(255, 255, 254));
        ppCatatanPasien.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppCatatanPasien.setForeground(new java.awt.Color(70, 70, 70));
        ppCatatanPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppCatatanPasien.setText("Catatan Untuk Pasien");
        ppCatatanPasien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppCatatanPasien.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppCatatanPasien.setName("ppCatatanPasien"); // NOI18N
        ppCatatanPasien.setPreferredSize(new java.awt.Dimension(200, 26));
        ppCatatanPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppCatatanPasienBtnPrintActionPerformed(evt);
            }
        });
        MenuInputData.add(ppCatatanPasien);

        ppBerkasDigital.setBackground(new java.awt.Color(255, 255, 254));
        ppBerkasDigital.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBerkasDigital.setForeground(new java.awt.Color(70, 70, 70));
        ppBerkasDigital.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBerkasDigital.setText("Berkas Digital Perawatan");
        ppBerkasDigital.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBerkasDigital.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBerkasDigital.setName("ppBerkasDigital"); // NOI18N
        ppBerkasDigital.setPreferredSize(new java.awt.Dimension(200, 26));
        ppBerkasDigital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBerkasDigitalBtnPrintActionPerformed(evt);
            }
        });
        MenuInputData.add(ppBerkasDigital);

        ppIKP.setBackground(new java.awt.Color(255, 255, 254));
        ppIKP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppIKP.setForeground(new java.awt.Color(70, 70, 70));
        ppIKP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppIKP.setText("Insiden Keselamatan Pasien");
        ppIKP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppIKP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppIKP.setName("ppIKP"); // NOI18N
        ppIKP.setPreferredSize(new java.awt.Dimension(200, 26));
        ppIKP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppIKPBtnPrintActionPerformed(evt);
            }
        });
        MenuInputData.add(ppIKP);

        MnTeridentifikasiTB.setBackground(new java.awt.Color(255, 255, 254));
        MnTeridentifikasiTB.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTeridentifikasiTB.setForeground(new java.awt.Color(70, 70, 70));
        MnTeridentifikasiTB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTeridentifikasiTB.setText("Teridentifikasi TB");
        MnTeridentifikasiTB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTeridentifikasiTB.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTeridentifikasiTB.setName("MnTeridentifikasiTB"); // NOI18N
        MnTeridentifikasiTB.setPreferredSize(new java.awt.Dimension(200, 26));
        MnTeridentifikasiTB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnTeridentifikasiTBActionPerformed(evt);
            }
        });
        MenuInputData.add(MnTeridentifikasiTB);

        ppDPJP.setBackground(new java.awt.Color(255, 255, 254));
        ppDPJP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppDPJP.setForeground(new java.awt.Color(70, 70, 70));
        ppDPJP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppDPJP.setText("Input DPJP");
        ppDPJP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDPJP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDPJP.setName("ppDPJP"); // NOI18N
        ppDPJP.setPreferredSize(new java.awt.Dimension(250, 26));
        ppDPJP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDPJPBtnPrintActionPerformed(evt);
            }
        });
        MenuInputData.add(ppDPJP);

        MnInputHP.setBackground(new java.awt.Color(255, 255, 254));
        MnInputHP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnInputHP.setForeground(new java.awt.Color(70, 70, 70));
        MnInputHP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnInputHP.setText("Input No. Hp Pasien");
        MnInputHP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnInputHP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnInputHP.setName("MnInputHP"); // NOI18N
        MnInputHP.setPreferredSize(new java.awt.Dimension(260, 26));
        MnInputHP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnInputHPBtnPrintActionPerformed(evt);
            }
        });
        MenuInputData.add(MnInputHP);

        MnInterpretasiEkg.setBackground(new java.awt.Color(255, 255, 254));
        MnInterpretasiEkg.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnInterpretasiEkg.setForeground(new java.awt.Color(70, 70, 70));
        MnInterpretasiEkg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnInterpretasiEkg.setText("Interpretasi EKG");
        MnInterpretasiEkg.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnInterpretasiEkg.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnInterpretasiEkg.setName("MnInterpretasiEkg"); // NOI18N
        MnInterpretasiEkg.setPreferredSize(new java.awt.Dimension(260, 26));
        MnInterpretasiEkg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnInterpretasiEkgBtnPrintActionPerformed(evt);
            }
        });
        MenuInputData.add(MnInterpretasiEkg);

        jPopupMenu1.add(MenuInputData);

        MnStatus.setBackground(new java.awt.Color(250, 255, 245));
        MnStatus.setForeground(new java.awt.Color(70, 70, 70));
        MnStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnStatus.setText("Set Status");
        MnStatus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStatus.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStatus.setIconTextGap(5);
        MnStatus.setName("MnStatus"); // NOI18N
        MnStatus.setPreferredSize(new java.awt.Dimension(260, 26));

        ppBerkas.setBackground(new java.awt.Color(255, 255, 254));
        ppBerkas.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBerkas.setForeground(new java.awt.Color(70, 70, 70));
        ppBerkas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBerkas.setText("Berkas R.M. Diterima");
        ppBerkas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBerkas.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBerkas.setName("ppBerkas"); // NOI18N
        ppBerkas.setPreferredSize(new java.awt.Dimension(180, 26));
        ppBerkas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBerkasBtnPrintActionPerformed(evt);
            }
        });
        MnStatus.add(ppBerkas);

        ppDiperiksa.setBackground(new java.awt.Color(255, 255, 254));
        ppDiperiksa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppDiperiksa.setForeground(new java.awt.Color(70, 70, 70));
        ppDiperiksa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppDiperiksa.setText("Diperiksa");
        ppDiperiksa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDiperiksa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDiperiksa.setName("ppDiperiksa"); // NOI18N
        ppDiperiksa.setPreferredSize(new java.awt.Dimension(180, 26));
        ppDiperiksa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDiperiksaBtnPrintActionPerformed(evt);
            }
        });
        MnStatus.add(ppDiperiksa);

        ppDilayani.setBackground(new java.awt.Color(255, 255, 254));
        ppDilayani.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppDilayani.setForeground(new java.awt.Color(70, 70, 70));
        ppDilayani.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppDilayani.setText("Dilayani");
        ppDilayani.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDilayani.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDilayani.setName("ppDilayani"); // NOI18N
        ppDilayani.setPreferredSize(new java.awt.Dimension(180, 26));
        ppDilayani.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDilayaniBtnPrintActionPerformed(evt);
            }
        });
        MnStatus.add(ppDilayani);

        MnSudah.setBackground(new java.awt.Color(255, 255, 254));
        MnSudah.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSudah.setForeground(new java.awt.Color(70, 70, 70));
        MnSudah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSudah.setText("Sudah Periksa");
        MnSudah.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSudah.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSudah.setName("MnSudah"); // NOI18N
        MnSudah.setPreferredSize(new java.awt.Dimension(180, 26));
        MnSudah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSudahActionPerformed(evt);
            }
        });
        MnStatus.add(MnSudah);

        MnBelum.setBackground(new java.awt.Color(255, 255, 254));
        MnBelum.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBelum.setForeground(new java.awt.Color(70, 70, 70));
        MnBelum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBelum.setText("Belum Periksa");
        MnBelum.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBelum.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBelum.setName("MnBelum"); // NOI18N
        MnBelum.setPreferredSize(new java.awt.Dimension(180, 26));
        MnBelum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBelumActionPerformed(evt);
            }
        });
        MnStatus.add(MnBelum);

        MnBatal.setBackground(new java.awt.Color(255, 255, 254));
        MnBatal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBatal.setForeground(new java.awt.Color(70, 70, 70));
        MnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBatal.setText("Batal Periksa");
        MnBatal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBatal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBatal.setName("MnBatal"); // NOI18N
        MnBatal.setPreferredSize(new java.awt.Dimension(180, 26));
        MnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBatalActionPerformed(evt);
            }
        });
        MnStatus.add(MnBatal);

        MnDirujuk.setBackground(new java.awt.Color(255, 255, 254));
        MnDirujuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDirujuk.setForeground(new java.awt.Color(70, 70, 70));
        MnDirujuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDirujuk.setText("Dirujuk");
        MnDirujuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDirujuk.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDirujuk.setName("MnDirujuk"); // NOI18N
        MnDirujuk.setPreferredSize(new java.awt.Dimension(180, 26));
        MnDirujuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDirujukActionPerformed(evt);
            }
        });
        MnStatus.add(MnDirujuk);

        MnDIrawat.setBackground(new java.awt.Color(255, 255, 254));
        MnDIrawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDIrawat.setForeground(new java.awt.Color(70, 70, 70));
        MnDIrawat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDIrawat.setText("Dirawat");
        MnDIrawat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDIrawat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDIrawat.setName("MnDIrawat"); // NOI18N
        MnDIrawat.setPreferredSize(new java.awt.Dimension(180, 26));
        MnDIrawat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDIrawatActionPerformed(evt);
            }
        });
        MnStatus.add(MnDIrawat);

        MnMeninggal.setBackground(new java.awt.Color(255, 255, 254));
        MnMeninggal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnMeninggal.setForeground(new java.awt.Color(70, 70, 70));
        MnMeninggal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnMeninggal.setText("Meninggal");
        MnMeninggal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnMeninggal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnMeninggal.setName("MnMeninggal"); // NOI18N
        MnMeninggal.setPreferredSize(new java.awt.Dimension(180, 26));
        MnMeninggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnMeninggalActionPerformed(evt);
            }
        });
        MnStatus.add(MnMeninggal);

        MnPulangPaksa.setBackground(new java.awt.Color(255, 255, 254));
        MnPulangPaksa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPulangPaksa.setForeground(new java.awt.Color(70, 70, 70));
        MnPulangPaksa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPulangPaksa.setText("Pulang Paksa");
        MnPulangPaksa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPulangPaksa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPulangPaksa.setName("MnPulangPaksa"); // NOI18N
        MnPulangPaksa.setPreferredSize(new java.awt.Dimension(180, 26));
        MnPulangPaksa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPulangPaksaBtnPrintActionPerformed(evt);
            }
        });
        MnStatus.add(MnPulangPaksa);

        jMenu7.setBackground(new java.awt.Color(255, 255, 254));
        jMenu7.setForeground(new java.awt.Color(70, 70, 70));
        jMenu7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        jMenu7.setText("Status Poli");
        jMenu7.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenu7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jMenu7.setIconTextGap(5);
        jMenu7.setName("jMenu7"); // NOI18N
        jMenu7.setPreferredSize(new java.awt.Dimension(240, 26));

        MnStatusBaru.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStatusBaru.setForeground(new java.awt.Color(70, 70, 70));
        MnStatusBaru.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnStatusBaru.setText("Status Poli Baru");
        MnStatusBaru.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStatusBaru.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStatusBaru.setName("MnStatusBaru"); // NOI18N
        MnStatusBaru.setPreferredSize(new java.awt.Dimension(170, 26));
        MnStatusBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnStatusBaruActionPerformed(evt);
            }
        });
        jMenu7.add(MnStatusBaru);

        MnStatusLama.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStatusLama.setForeground(new java.awt.Color(70, 70, 70));
        MnStatusLama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnStatusLama.setText("Status Poli Lama");
        MnStatusLama.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStatusLama.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStatusLama.setName("MnStatusLama"); // NOI18N
        MnStatusLama.setPreferredSize(new java.awt.Dimension(170, 26));
        MnStatusLama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnStatusLamaActionPerformed(evt);
            }
        });
        jMenu7.add(MnStatusLama);

        MnStatus.add(jMenu7);

        jPopupMenu1.add(MnStatus);

        MnUrut.setBackground(new java.awt.Color(250, 255, 245));
        MnUrut.setForeground(new java.awt.Color(70, 70, 70));
        MnUrut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrut.setText("Urutkan Data Berdasar");
        MnUrut.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrut.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrut.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrut.setIconTextGap(5);
        MnUrut.setName("MnUrut"); // NOI18N
        MnUrut.setPreferredSize(new java.awt.Dimension(260, 26));

        MnUrutNoRawatDesc.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutNoRawatDesc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutNoRawatDesc.setForeground(new java.awt.Color(70, 70, 70));
        MnUrutNoRawatDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutNoRawatDesc.setText("No.Rawat Descending");
        MnUrutNoRawatDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutNoRawatDesc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutNoRawatDesc.setName("MnUrutNoRawatDesc"); // NOI18N
        MnUrutNoRawatDesc.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutNoRawatDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutNoRawatDescActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutNoRawatDesc);

        MnUrutNoRawatAsc.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutNoRawatAsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutNoRawatAsc.setForeground(new java.awt.Color(70, 70, 70));
        MnUrutNoRawatAsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutNoRawatAsc.setText("No.Rawat Ascending");
        MnUrutNoRawatAsc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutNoRawatAsc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutNoRawatAsc.setName("MnUrutNoRawatAsc"); // NOI18N
        MnUrutNoRawatAsc.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutNoRawatAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutNoRawatAscActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutNoRawatAsc);

        MnUrutTanggalDesc.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutTanggalDesc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutTanggalDesc.setForeground(new java.awt.Color(70, 70, 70));
        MnUrutTanggalDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutTanggalDesc.setText("Tanggal Descending");
        MnUrutTanggalDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutTanggalDesc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutTanggalDesc.setName("MnUrutTanggalDesc"); // NOI18N
        MnUrutTanggalDesc.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutTanggalDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutTanggalDescActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutTanggalDesc);

        MnUrutTanggalAsc.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutTanggalAsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutTanggalAsc.setForeground(new java.awt.Color(70, 70, 70));
        MnUrutTanggalAsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutTanggalAsc.setText("Tanggal Ascending");
        MnUrutTanggalAsc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutTanggalAsc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutTanggalAsc.setName("MnUrutTanggalAsc"); // NOI18N
        MnUrutTanggalAsc.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutTanggalAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutTanggalAscActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutTanggalAsc);

        MnUrutDokterDesc.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutDokterDesc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutDokterDesc.setForeground(new java.awt.Color(70, 70, 70));
        MnUrutDokterDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutDokterDesc.setText("Dokter Descending");
        MnUrutDokterDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutDokterDesc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutDokterDesc.setName("MnUrutDokterDesc"); // NOI18N
        MnUrutDokterDesc.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutDokterDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutDokterDescActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutDokterDesc);

        MnUrutDokterAsc.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutDokterAsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutDokterAsc.setForeground(new java.awt.Color(70, 70, 70));
        MnUrutDokterAsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutDokterAsc.setText("Dokter Ascending");
        MnUrutDokterAsc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutDokterAsc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutDokterAsc.setName("MnUrutDokterAsc"); // NOI18N
        MnUrutDokterAsc.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutDokterAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutDokterAscActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutDokterAsc);

        MnUrutPoliklinikDesc.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutPoliklinikDesc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutPoliklinikDesc.setForeground(new java.awt.Color(70, 70, 70));
        MnUrutPoliklinikDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutPoliklinikDesc.setText("Poli/Unit Descending");
        MnUrutPoliklinikDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutPoliklinikDesc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutPoliklinikDesc.setName("MnUrutPoliklinikDesc"); // NOI18N
        MnUrutPoliklinikDesc.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutPoliklinikDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutPoliklinikDescActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutPoliklinikDesc);

        MnUrutPoliklinikAsc.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutPoliklinikAsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutPoliklinikAsc.setForeground(new java.awt.Color(70, 70, 70));
        MnUrutPoliklinikAsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutPoliklinikAsc.setText("Poli/Unit Ascending");
        MnUrutPoliklinikAsc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutPoliklinikAsc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutPoliklinikAsc.setName("MnUrutPoliklinikAsc"); // NOI18N
        MnUrutPoliklinikAsc.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutPoliklinikAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutPoliklinikAscActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutPoliklinikAsc);

        MnUrutPenjabDesc.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutPenjabDesc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutPenjabDesc.setForeground(new java.awt.Color(70, 70, 70));
        MnUrutPenjabDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutPenjabDesc.setText("Cara Bayar Descending");
        MnUrutPenjabDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutPenjabDesc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutPenjabDesc.setName("MnUrutPenjabDesc"); // NOI18N
        MnUrutPenjabDesc.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutPenjabDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutPenjabDescActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutPenjabDesc);

        MnUrutPenjabAsc.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutPenjabAsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutPenjabAsc.setForeground(new java.awt.Color(70, 70, 70));
        MnUrutPenjabAsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutPenjabAsc.setText("Cara Bayar Ascending");
        MnUrutPenjabAsc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutPenjabAsc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutPenjabAsc.setName("MnUrutPenjabAsc"); // NOI18N
        MnUrutPenjabAsc.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutPenjabAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutPenjabAscActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutPenjabAsc);

        MnUrutStatusDesc.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutStatusDesc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutStatusDesc.setForeground(new java.awt.Color(70, 70, 70));
        MnUrutStatusDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutStatusDesc.setText("Status Descending");
        MnUrutStatusDesc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutStatusDesc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutStatusDesc.setName("MnUrutStatusDesc"); // NOI18N
        MnUrutStatusDesc.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutStatusDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutStatusDescActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutStatusDesc);

        MnUrutStatusAsc.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutStatusAsc.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutStatusAsc.setForeground(new java.awt.Color(70, 70, 70));
        MnUrutStatusAsc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutStatusAsc.setText("Status Ascending");
        MnUrutStatusAsc.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutStatusAsc.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutStatusAsc.setName("MnUrutStatusAsc"); // NOI18N
        MnUrutStatusAsc.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutStatusAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutStatusAscActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutStatusAsc);

        MnUrutRegDesc1.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutRegDesc1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutRegDesc1.setForeground(new java.awt.Color(70, 70, 70));
        MnUrutRegDesc1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutRegDesc1.setText("No. Reg. Descending");
        MnUrutRegDesc1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutRegDesc1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutRegDesc1.setName("MnUrutRegDesc1"); // NOI18N
        MnUrutRegDesc1.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutRegDesc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutRegDesc1ActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutRegDesc1);

        MnUrutRegAsc1.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutRegAsc1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutRegAsc1.setForeground(new java.awt.Color(70, 70, 70));
        MnUrutRegAsc1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutRegAsc1.setText("No. Reg. Ascending");
        MnUrutRegAsc1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutRegAsc1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutRegAsc1.setName("MnUrutRegAsc1"); // NOI18N
        MnUrutRegAsc1.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutRegAsc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutRegAsc1ActionPerformed(evt);
            }
        });
        MnUrut.add(MnUrutRegAsc1);

        jPopupMenu1.add(MnUrut);

        MnHemodialisa.setBackground(new java.awt.Color(255, 255, 254));
        MnHemodialisa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHemodialisa.setForeground(new java.awt.Color(70, 70, 70));
        MnHemodialisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHemodialisa.setText("Hemodialisa");
        MnHemodialisa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHemodialisa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHemodialisa.setName("MnHemodialisa"); // NOI18N
        MnHemodialisa.setPreferredSize(new java.awt.Dimension(260, 26));
        MnHemodialisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHemodialisaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnHemodialisa);

        ppRiwayat.setBackground(new java.awt.Color(255, 255, 254));
        ppRiwayat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRiwayat.setForeground(new java.awt.Color(70, 70, 70));
        ppRiwayat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRiwayat.setText("Riwayat Perawatan");
        ppRiwayat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRiwayat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRiwayat.setName("ppRiwayat"); // NOI18N
        ppRiwayat.setPreferredSize(new java.awt.Dimension(260, 26));
        ppRiwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRiwayatBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppRiwayat);

        MnRiwayatObatRacikan.setBackground(new java.awt.Color(255, 255, 254));
        MnRiwayatObatRacikan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRiwayatObatRacikan.setForeground(new java.awt.Color(70, 70, 70));
        MnRiwayatObatRacikan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRiwayatObatRacikan.setText("Riwayat Obat Racikan");
        MnRiwayatObatRacikan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRiwayatObatRacikan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRiwayatObatRacikan.setName("MnRiwayatObatRacikan"); // NOI18N
        MnRiwayatObatRacikan.setPreferredSize(new java.awt.Dimension(260, 26));
        MnRiwayatObatRacikan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRiwayatObatRacikanBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnRiwayatObatRacikan);

        MnHapusData.setBackground(new java.awt.Color(250, 255, 245));
        MnHapusData.setForeground(new java.awt.Color(70, 70, 70));
        MnHapusData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusData.setText("Hapus Data");
        MnHapusData.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusData.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusData.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusData.setIconTextGap(5);
        MnHapusData.setName("MnHapusData"); // NOI18N
        MnHapusData.setPreferredSize(new java.awt.Dimension(260, 26));

        MnHapusTagihanOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusTagihanOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTagihanOperasi.setForeground(new java.awt.Color(70, 70, 70));
        MnHapusTagihanOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTagihanOperasi.setText("Hapus Tagihan Operasi");
        MnHapusTagihanOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTagihanOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTagihanOperasi.setName("MnHapusTagihanOperasi"); // NOI18N
        MnHapusTagihanOperasi.setPreferredSize(new java.awt.Dimension(200, 26));
        MnHapusTagihanOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTagihanOperasiActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusTagihanOperasi);

        MnHapusObatOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusObatOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusObatOperasi.setForeground(new java.awt.Color(70, 70, 70));
        MnHapusObatOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusObatOperasi.setText("Hapus Obat Operasi");
        MnHapusObatOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusObatOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusObatOperasi.setName("MnHapusObatOperasi"); // NOI18N
        MnHapusObatOperasi.setPreferredSize(new java.awt.Dimension(200, 26));
        MnHapusObatOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusObatOperasiActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusObatOperasi);

        jPopupMenu1.add(MnHapusData);

        MnMCU.setBackground(new java.awt.Color(250, 255, 245));
        MnMCU.setForeground(new java.awt.Color(70, 70, 70));
        MnMCU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnMCU.setText("Menu MCU");
        MnMCU.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnMCU.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnMCU.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnMCU.setIconTextGap(5);
        MnMCU.setName("MnMCU"); // NOI18N
        MnMCU.setPreferredSize(new java.awt.Dimension(260, 26));

        MnPaketMCU.setBackground(new java.awt.Color(255, 255, 254));
        MnPaketMCU.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPaketMCU.setForeground(new java.awt.Color(70, 70, 70));
        MnPaketMCU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPaketMCU.setText("Paket MCU");
        MnPaketMCU.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPaketMCU.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPaketMCU.setName("MnPaketMCU"); // NOI18N
        MnPaketMCU.setPreferredSize(new java.awt.Dimension(200, 26));
        MnPaketMCU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPaketMCUActionPerformed(evt);
            }
        });
        MnMCU.add(MnPaketMCU);

        MnPaketNapza.setBackground(new java.awt.Color(255, 255, 254));
        MnPaketNapza.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPaketNapza.setForeground(new java.awt.Color(70, 70, 70));
        MnPaketNapza.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPaketNapza.setText("Paket Napza");
        MnPaketNapza.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPaketNapza.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPaketNapza.setName("MnPaketNapza"); // NOI18N
        MnPaketNapza.setPreferredSize(new java.awt.Dimension(200, 26));
        MnPaketNapza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPaketNapzaActionPerformed(evt);
            }
        });
        MnMCU.add(MnPaketNapza);

        MnPaketMMPI.setBackground(new java.awt.Color(255, 255, 254));
        MnPaketMMPI.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPaketMMPI.setForeground(new java.awt.Color(70, 70, 70));
        MnPaketMMPI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPaketMMPI.setText("Paket MMPI");
        MnPaketMMPI.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPaketMMPI.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPaketMMPI.setName("MnPaketMMPI"); // NOI18N
        MnPaketMMPI.setPreferredSize(new java.awt.Dimension(200, 26));
        MnPaketMMPI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPaketMMPIActionPerformed(evt);
            }
        });
        MnMCU.add(MnPaketMMPI);

        MnPaketHajiLaki.setBackground(new java.awt.Color(255, 255, 254));
        MnPaketHajiLaki.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPaketHajiLaki.setForeground(new java.awt.Color(70, 70, 70));
        MnPaketHajiLaki.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPaketHajiLaki.setText("Paket CJH Laki-laki");
        MnPaketHajiLaki.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPaketHajiLaki.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPaketHajiLaki.setName("MnPaketHajiLaki"); // NOI18N
        MnPaketHajiLaki.setPreferredSize(new java.awt.Dimension(200, 26));
        MnPaketHajiLaki.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPaketHajiLakiActionPerformed(evt);
            }
        });
        MnMCU.add(MnPaketHajiLaki);

        MnPaketHajiPerempuan.setBackground(new java.awt.Color(255, 255, 254));
        MnPaketHajiPerempuan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPaketHajiPerempuan.setForeground(new java.awt.Color(70, 70, 70));
        MnPaketHajiPerempuan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPaketHajiPerempuan.setText("Paket CJH Perempuan");
        MnPaketHajiPerempuan.setToolTipText("");
        MnPaketHajiPerempuan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPaketHajiPerempuan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPaketHajiPerempuan.setName("MnPaketHajiPerempuan"); // NOI18N
        MnPaketHajiPerempuan.setPreferredSize(new java.awt.Dimension(200, 26));
        MnPaketHajiPerempuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPaketHajiPerempuanActionPerformed(evt);
            }
        });
        MnMCU.add(MnPaketHajiPerempuan);

        MnNapza6Param.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnNapza6Param.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnNapza6Param.setText("Paket Napza 6 Parameter");
        MnNapza6Param.setName("MnNapza6Param"); // NOI18N
        MnNapza6Param.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnNapza6ParamActionPerformed(evt);
            }
        });
        MnMCU.add(MnNapza6Param);

        jPopupMenu1.add(MnMCU);

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        DlgSakit.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgSakit.setName("DlgSakit"); // NOI18N
        DlgSakit.setUndecorated(true);
        DlgSakit.setResizable(false);

        internalFrame3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Cetak Surat Cuti Sakit ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa2.setName("panelBiasa2"); // NOI18N
        panelBiasa2.setLayout(null);

        TglSakit1.setForeground(new java.awt.Color(50, 70, 50));
        TglSakit1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-08-2023" }));
        TglSakit1.setDisplayFormat("dd-MM-yyyy");
        TglSakit1.setName("TglSakit1"); // NOI18N
        TglSakit1.setOpaque(false);
        panelBiasa2.add(TglSakit1);
        TglSakit1.setBounds(70, 10, 100, 23);

        jLabel31.setText("Lama Sakit :");
        jLabel31.setName("jLabel31"); // NOI18N
        panelBiasa2.add(jLabel31);
        jLabel31.setBounds(297, 10, 110, 23);

        BtnPrint2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint2.setMnemonic('T');
        BtnPrint2.setText("Cetak");
        BtnPrint2.setToolTipText("Alt+T");
        BtnPrint2.setName("BtnPrint2"); // NOI18N
        BtnPrint2.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint2ActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnPrint2);
        BtnPrint2.setBounds(10, 50, 100, 30);

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
        panelBiasa2.add(BtnKeluar2);
        BtnKeluar2.setBounds(430, 50, 100, 30);

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("s/d");
        jLabel32.setName("jLabel32"); // NOI18N
        panelBiasa2.add(jLabel32);
        jLabel32.setBounds(176, 10, 20, 23);

        TglSakit2.setForeground(new java.awt.Color(50, 70, 50));
        TglSakit2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-08-2023" }));
        TglSakit2.setDisplayFormat("dd-MM-yyyy");
        TglSakit2.setName("TglSakit2"); // NOI18N
        TglSakit2.setOpaque(false);
        panelBiasa2.add(TglSakit2);
        TglSakit2.setBounds(200, 10, 100, 23);

        jLabel33.setText("Tanggal :");
        jLabel33.setName("jLabel33"); // NOI18N
        panelBiasa2.add(jLabel33);
        jLabel33.setBounds(0, 10, 66, 23);

        lmsakit.setHighlighter(null);
        lmsakit.setName("lmsakit"); // NOI18N
        panelBiasa2.add(lmsakit);
        lmsakit.setBounds(410, 10, 110, 23);

        internalFrame3.add(panelBiasa2, java.awt.BorderLayout.CENTER);

        DlgSakit.getContentPane().add(internalFrame3, java.awt.BorderLayout.CENTER);

        DlgSakit2.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgSakit2.setName("DlgSakit2"); // NOI18N
        DlgSakit2.setUndecorated(true);
        DlgSakit2.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Cetak Surat Keterangan Rawat Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa4.setName("panelBiasa4"); // NOI18N
        panelBiasa4.setLayout(null);

        jLabel37.setText("Nomor Surat Keterangan :");
        jLabel37.setName("jLabel37"); // NOI18N
        panelBiasa4.add(jLabel37);
        jLabel37.setBounds(7, 10, 140, 23);

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
        BtnPrint5.setBounds(10, 80, 100, 30);

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
        BtnKeluar4.setBounds(430, 80, 100, 30);

        NomorSurat.setHighlighter(null);
        NomorSurat.setName("NomorSurat"); // NOI18N
        panelBiasa4.add(NomorSurat);
        NomorSurat.setBounds(150, 10, 370, 23);

        BtnSeek5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek5.setMnemonic('6');
        BtnSeek5.setToolTipText("ALt+6");
        BtnSeek5.setName("BtnSeek5"); // NOI18N
        BtnSeek5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek5ActionPerformed(evt);
            }
        });
        panelBiasa4.add(BtnSeek5);
        BtnSeek5.setBounds(492, 40, 28, 23);

        CrDokter3.setEditable(false);
        CrDokter3.setName("CrDokter3"); // NOI18N
        CrDokter3.setPreferredSize(new java.awt.Dimension(300, 23));
        panelBiasa4.add(CrDokter3);
        CrDokter3.setBounds(150, 40, 340, 23);

        jLabel24.setText("Dokter Png. Jawab :");
        jLabel24.setName("jLabel24"); // NOI18N
        jLabel24.setPreferredSize(new java.awt.Dimension(60, 23));
        panelBiasa4.add(jLabel24);
        jLabel24.setBounds(7, 40, 140, 23);

        internalFrame5.add(panelBiasa4, java.awt.BorderLayout.CENTER);

        DlgSakit2.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        NoBalasan.setHighlighter(null);
        NoBalasan.setName("NoBalasan"); // NOI18N

        jPopupMenu2.setName("jPopupMenu2"); // NOI18N

        MnPermintaan1.setBackground(new java.awt.Color(250, 255, 245));
        MnPermintaan1.setForeground(new java.awt.Color(70, 70, 70));
        MnPermintaan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaan1.setText("Permintaan");
        MnPermintaan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaan1.setIconTextGap(5);
        MnPermintaan1.setName("MnPermintaan1"); // NOI18N
        MnPermintaan1.setPreferredSize(new java.awt.Dimension(210, 26));

        MnJadwalOperasi1.setBackground(new java.awt.Color(255, 255, 254));
        MnJadwalOperasi1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnJadwalOperasi1.setForeground(new java.awt.Color(70, 70, 70));
        MnJadwalOperasi1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnJadwalOperasi1.setText("Jadwal Operasi");
        MnJadwalOperasi1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnJadwalOperasi1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnJadwalOperasi1.setName("MnJadwalOperasi1"); // NOI18N
        MnJadwalOperasi1.setPreferredSize(new java.awt.Dimension(170, 26));
        MnJadwalOperasi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnJadwalOperasi1ActionPerformed(evt);
            }
        });
        MnPermintaan1.add(MnJadwalOperasi1);

        MnSKDPBPJS1.setBackground(new java.awt.Color(255, 255, 254));
        MnSKDPBPJS1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSKDPBPJS1.setForeground(new java.awt.Color(70, 70, 70));
        MnSKDPBPJS1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSKDPBPJS1.setText("SKDP BPJS");
        MnSKDPBPJS1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSKDPBPJS1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSKDPBPJS1.setName("MnSKDPBPJS1"); // NOI18N
        MnSKDPBPJS1.setPreferredSize(new java.awt.Dimension(170, 26));
        MnSKDPBPJS1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSKDPBPJS1ActionPerformed(evt);
            }
        });
        MnPermintaan1.add(MnSKDPBPJS1);

        MnPermintaanLab1.setBackground(new java.awt.Color(255, 255, 254));
        MnPermintaanLab1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaanLab1.setForeground(new java.awt.Color(70, 70, 70));
        MnPermintaanLab1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaanLab1.setText("Pemeriksaan Lab");
        MnPermintaanLab1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaanLab1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaanLab1.setName("MnPermintaanLab1"); // NOI18N
        MnPermintaanLab1.setPreferredSize(new java.awt.Dimension(170, 26));
        MnPermintaanLab1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPermintaanLab1ActionPerformed(evt);
            }
        });
        MnPermintaan1.add(MnPermintaanLab1);

        MnPermintaanRadiologi1.setBackground(new java.awt.Color(255, 255, 254));
        MnPermintaanRadiologi1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaanRadiologi1.setForeground(new java.awt.Color(70, 70, 70));
        MnPermintaanRadiologi1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaanRadiologi1.setText("Pemeriksaan Radiologi");
        MnPermintaanRadiologi1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaanRadiologi1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaanRadiologi1.setName("MnPermintaanRadiologi1"); // NOI18N
        MnPermintaanRadiologi1.setPreferredSize(new java.awt.Dimension(170, 26));
        MnPermintaanRadiologi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPermintaanRadiologi1ActionPerformed(evt);
            }
        });
        MnPermintaan1.add(MnPermintaanRadiologi1);

        jPopupMenu2.add(MnPermintaan1);

        MnKamarInap1.setBackground(new java.awt.Color(255, 255, 254));
        MnKamarInap1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKamarInap1.setForeground(new java.awt.Color(70, 70, 70));
        MnKamarInap1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKamarInap1.setText("Kamar Inap");
        MnKamarInap1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnKamarInap1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnKamarInap1.setName("MnKamarInap1"); // NOI18N
        MnKamarInap1.setPreferredSize(new java.awt.Dimension(210, 26));
        MnKamarInap1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKamarInap1ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(MnKamarInap1);

        MnTindakan1.setBackground(new java.awt.Color(250, 255, 245));
        MnTindakan1.setForeground(new java.awt.Color(70, 70, 70));
        MnTindakan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTindakan1.setText("Tindakan & Pemeriksaan");
        MnTindakan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTindakan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTindakan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTindakan1.setIconTextGap(5);
        MnTindakan1.setName("MnTindakan1"); // NOI18N
        MnTindakan1.setPreferredSize(new java.awt.Dimension(210, 26));

        MnRawatJalan1.setBackground(new java.awt.Color(255, 255, 254));
        MnRawatJalan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRawatJalan1.setForeground(new java.awt.Color(70, 70, 70));
        MnRawatJalan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRawatJalan1.setText("Tagihan/Tindakan Rawat Jalan");
        MnRawatJalan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRawatJalan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRawatJalan1.setName("MnRawatJalan1"); // NOI18N
        MnRawatJalan1.setPreferredSize(new java.awt.Dimension(240, 26));
        MnRawatJalan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRawatJalan1ActionPerformed(evt);
            }
        });
        MnTindakan1.add(MnRawatJalan1);

        MnPeriksaLab1.setBackground(new java.awt.Color(255, 255, 254));
        MnPeriksaLab1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaLab1.setForeground(new java.awt.Color(70, 70, 70));
        MnPeriksaLab1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaLab1.setText("Periksa Laborat");
        MnPeriksaLab1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaLab1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaLab1.setName("MnPeriksaLab1"); // NOI18N
        MnPeriksaLab1.setPreferredSize(new java.awt.Dimension(240, 26));
        MnPeriksaLab1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaLab1ActionPerformed(evt);
            }
        });
        MnTindakan1.add(MnPeriksaLab1);

        MnPeriksaRadiologi1.setBackground(new java.awt.Color(255, 255, 254));
        MnPeriksaRadiologi1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaRadiologi1.setForeground(new java.awt.Color(70, 70, 70));
        MnPeriksaRadiologi1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaRadiologi1.setText("Periksa Radiologi");
        MnPeriksaRadiologi1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaRadiologi1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaRadiologi1.setName("MnPeriksaRadiologi1"); // NOI18N
        MnPeriksaRadiologi1.setPreferredSize(new java.awt.Dimension(240, 26));
        MnPeriksaRadiologi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaRadiologi1ActionPerformed(evt);
            }
        });
        MnTindakan1.add(MnPeriksaRadiologi1);

        MnOperasi1.setBackground(new java.awt.Color(255, 255, 254));
        MnOperasi1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnOperasi1.setForeground(new java.awt.Color(70, 70, 70));
        MnOperasi1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnOperasi1.setText("Tagihan Operasi/VK");
        MnOperasi1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnOperasi1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnOperasi1.setName("MnOperasi1"); // NOI18N
        MnOperasi1.setPreferredSize(new java.awt.Dimension(240, 26));
        MnOperasi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnOperasi1ActionPerformed(evt);
            }
        });
        MnTindakan1.add(MnOperasi1);

        jPopupMenu2.add(MnTindakan1);

        MnObat1.setBackground(new java.awt.Color(250, 255, 245));
        MnObat1.setForeground(new java.awt.Color(70, 70, 70));
        MnObat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnObat1.setText("Obat");
        MnObat1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnObat1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnObat1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnObat1.setIconTextGap(5);
        MnObat1.setName("MnObat1"); // NOI18N
        MnObat1.setPreferredSize(new java.awt.Dimension(210, 26));

        MnPemberianObat1.setBackground(new java.awt.Color(255, 255, 254));
        MnPemberianObat1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPemberianObat1.setForeground(new java.awt.Color(70, 70, 70));
        MnPemberianObat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPemberianObat1.setText("Pemberian Obat/BHP");
        MnPemberianObat1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPemberianObat1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPemberianObat1.setName("MnPemberianObat1"); // NOI18N
        MnPemberianObat1.setPreferredSize(new java.awt.Dimension(160, 26));
        MnPemberianObat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPemberianObat1ActionPerformed(evt);
            }
        });
        MnObat1.add(MnPemberianObat1);

        MnNoResep1.setBackground(new java.awt.Color(255, 255, 254));
        MnNoResep1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnNoResep1.setForeground(new java.awt.Color(70, 70, 70));
        MnNoResep1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnNoResep1.setText("Input No.Resep");
        MnNoResep1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnNoResep1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnNoResep1.setName("MnNoResep1"); // NOI18N
        MnNoResep1.setPreferredSize(new java.awt.Dimension(160, 26));
        MnNoResep1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnNoResep1ActionPerformed(evt);
            }
        });
        MnObat1.add(MnNoResep1);

        MnResepDOkter1.setBackground(new java.awt.Color(255, 255, 254));
        MnResepDOkter1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnResepDOkter1.setForeground(new java.awt.Color(70, 70, 70));
        MnResepDOkter1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnResepDOkter1.setText("Input Resep Dokter");
        MnResepDOkter1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnResepDOkter1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnResepDOkter1.setName("MnResepDOkter1"); // NOI18N
        MnResepDOkter1.setPreferredSize(new java.awt.Dimension(160, 26));
        MnResepDOkter1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnResepDOkter1ActionPerformed(evt);
            }
        });
        MnObat1.add(MnResepDOkter1);

        jPopupMenu2.add(MnObat1);

        MnBilling1.setBackground(new java.awt.Color(255, 255, 254));
        MnBilling1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBilling1.setForeground(new java.awt.Color(70, 70, 70));
        MnBilling1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBilling1.setText("Billing/Pembayaran Pasien");
        MnBilling1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBilling1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBilling1.setName("MnBilling1"); // NOI18N
        MnBilling1.setPreferredSize(new java.awt.Dimension(210, 26));
        MnBilling1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBilling1ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(MnBilling1);

        jSeparator12.setBackground(new java.awt.Color(190, 220, 180));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(190, 220, 180)));
        jSeparator12.setForeground(new java.awt.Color(190, 220, 180));
        jSeparator12.setName("jSeparator12"); // NOI18N
        jSeparator12.setPreferredSize(new java.awt.Dimension(210, 1));
        jPopupMenu2.add(jSeparator12);

        MenuInputData1.setBackground(new java.awt.Color(250, 255, 245));
        MenuInputData1.setForeground(new java.awt.Color(70, 70, 70));
        MenuInputData1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MenuInputData1.setText("Input Data");
        MenuInputData1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MenuInputData1.setIconTextGap(5);
        MenuInputData1.setName("MenuInputData1"); // NOI18N
        MenuInputData1.setPreferredSize(new java.awt.Dimension(210, 26));

        ppBerkasDigital1.setBackground(new java.awt.Color(255, 255, 254));
        ppBerkasDigital1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBerkasDigital1.setForeground(new java.awt.Color(70, 70, 70));
        ppBerkasDigital1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBerkasDigital1.setText("Berkas Digital Perawatan");
        ppBerkasDigital1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBerkasDigital1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBerkasDigital1.setName("ppBerkasDigital1"); // NOI18N
        ppBerkasDigital1.setPreferredSize(new java.awt.Dimension(200, 26));
        ppBerkasDigital1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBerkasDigital1BtnPrintActionPerformed(evt);
            }
        });
        MenuInputData1.add(ppBerkasDigital1);

        ppIKP1.setBackground(new java.awt.Color(255, 255, 254));
        ppIKP1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppIKP1.setForeground(new java.awt.Color(70, 70, 70));
        ppIKP1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppIKP1.setText("Insiden Keselamatan Pasien");
        ppIKP1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppIKP1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppIKP1.setName("ppIKP1"); // NOI18N
        ppIKP1.setPreferredSize(new java.awt.Dimension(200, 26));
        ppIKP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppIKP1BtnPrintActionPerformed(evt);
            }
        });
        MenuInputData1.add(ppIKP1);

        MnDiagnosa1.setBackground(new java.awt.Color(255, 255, 254));
        MnDiagnosa1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDiagnosa1.setForeground(new java.awt.Color(70, 70, 70));
        MnDiagnosa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDiagnosa1.setText("Diagnosa Pasien");
        MnDiagnosa1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDiagnosa1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDiagnosa1.setName("MnDiagnosa1"); // NOI18N
        MnDiagnosa1.setPreferredSize(new java.awt.Dimension(200, 26));
        MnDiagnosa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDiagnosa1ActionPerformed(evt);
            }
        });
        MenuInputData1.add(MnDiagnosa1);

        MnTeridentifikasiTB1.setBackground(new java.awt.Color(255, 255, 254));
        MnTeridentifikasiTB1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTeridentifikasiTB1.setForeground(new java.awt.Color(70, 70, 70));
        MnTeridentifikasiTB1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTeridentifikasiTB1.setText("Teridentifikasi TB");
        MnTeridentifikasiTB1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTeridentifikasiTB1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTeridentifikasiTB1.setName("MnTeridentifikasiTB1"); // NOI18N
        MnTeridentifikasiTB1.setPreferredSize(new java.awt.Dimension(200, 26));
        MnTeridentifikasiTB1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnTeridentifikasiTB1ActionPerformed(evt);
            }
        });
        MenuInputData1.add(MnTeridentifikasiTB1);

        jPopupMenu2.add(MenuInputData1);

        MnUrut1.setBackground(new java.awt.Color(250, 255, 245));
        MnUrut1.setForeground(new java.awt.Color(70, 70, 70));
        MnUrut1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrut1.setText("Urutkan Data Berdasar");
        MnUrut1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrut1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrut1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrut1.setIconTextGap(5);
        MnUrut1.setName("MnUrut1"); // NOI18N
        MnUrut1.setPreferredSize(new java.awt.Dimension(210, 26));

        MnUrutNoRawatDesc1.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutNoRawatDesc1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutNoRawatDesc1.setForeground(new java.awt.Color(70, 70, 70));
        MnUrutNoRawatDesc1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutNoRawatDesc1.setText("No.Rawat Descending");
        MnUrutNoRawatDesc1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutNoRawatDesc1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutNoRawatDesc1.setName("MnUrutNoRawatDesc1"); // NOI18N
        MnUrutNoRawatDesc1.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutNoRawatDesc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutNoRawatDesc1ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutNoRawatDesc1);

        MnUrutNoRawatAsc1.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutNoRawatAsc1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutNoRawatAsc1.setForeground(new java.awt.Color(70, 70, 70));
        MnUrutNoRawatAsc1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutNoRawatAsc1.setText("No.Rawat Ascending");
        MnUrutNoRawatAsc1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutNoRawatAsc1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutNoRawatAsc1.setName("MnUrutNoRawatAsc1"); // NOI18N
        MnUrutNoRawatAsc1.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutNoRawatAsc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutNoRawatAsc1ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutNoRawatAsc1);

        MnUrutTanggalDesc1.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutTanggalDesc1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutTanggalDesc1.setForeground(new java.awt.Color(70, 70, 70));
        MnUrutTanggalDesc1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutTanggalDesc1.setText("Tanggal Descending");
        MnUrutTanggalDesc1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutTanggalDesc1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutTanggalDesc1.setName("MnUrutTanggalDesc1"); // NOI18N
        MnUrutTanggalDesc1.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutTanggalDesc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutTanggalDesc1ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutTanggalDesc1);

        MnUrutTanggalAsc1.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutTanggalAsc1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutTanggalAsc1.setForeground(new java.awt.Color(70, 70, 70));
        MnUrutTanggalAsc1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutTanggalAsc1.setText("Tanggal Ascending");
        MnUrutTanggalAsc1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutTanggalAsc1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutTanggalAsc1.setName("MnUrutTanggalAsc1"); // NOI18N
        MnUrutTanggalAsc1.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutTanggalAsc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutTanggalAsc1ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutTanggalAsc1);

        MnUrutDokterDesc1.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutDokterDesc1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutDokterDesc1.setForeground(new java.awt.Color(70, 70, 70));
        MnUrutDokterDesc1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutDokterDesc1.setText("Dokter Descending");
        MnUrutDokterDesc1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutDokterDesc1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutDokterDesc1.setName("MnUrutDokterDesc1"); // NOI18N
        MnUrutDokterDesc1.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutDokterDesc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutDokterDesc1ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutDokterDesc1);

        MnUrutDokterAsc1.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutDokterAsc1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutDokterAsc1.setForeground(new java.awt.Color(70, 70, 70));
        MnUrutDokterAsc1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutDokterAsc1.setText("Dokter Ascending");
        MnUrutDokterAsc1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutDokterAsc1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutDokterAsc1.setName("MnUrutDokterAsc1"); // NOI18N
        MnUrutDokterAsc1.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutDokterAsc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutDokterAsc1ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutDokterAsc1);

        MnUrutPoliklinikDesc1.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutPoliklinikDesc1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutPoliklinikDesc1.setForeground(new java.awt.Color(70, 70, 70));
        MnUrutPoliklinikDesc1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutPoliklinikDesc1.setText("Poli/Unit Descending");
        MnUrutPoliklinikDesc1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutPoliklinikDesc1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutPoliklinikDesc1.setName("MnUrutPoliklinikDesc1"); // NOI18N
        MnUrutPoliklinikDesc1.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutPoliklinikDesc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutPoliklinikDesc1ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutPoliklinikDesc1);

        MnUrutPoliklinikAsc1.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutPoliklinikAsc1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutPoliklinikAsc1.setForeground(new java.awt.Color(70, 70, 70));
        MnUrutPoliklinikAsc1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutPoliklinikAsc1.setText("Poli/Unit Ascending");
        MnUrutPoliklinikAsc1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutPoliklinikAsc1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutPoliklinikAsc1.setName("MnUrutPoliklinikAsc1"); // NOI18N
        MnUrutPoliklinikAsc1.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutPoliklinikAsc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutPoliklinikAsc1ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutPoliklinikAsc1);

        MnUrutPenjabDesc1.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutPenjabDesc1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutPenjabDesc1.setForeground(new java.awt.Color(70, 70, 70));
        MnUrutPenjabDesc1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutPenjabDesc1.setText("Cara Bayar Descending");
        MnUrutPenjabDesc1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutPenjabDesc1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutPenjabDesc1.setName("MnUrutPenjabDesc1"); // NOI18N
        MnUrutPenjabDesc1.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutPenjabDesc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutPenjabDesc1ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutPenjabDesc1);

        MnUrutPenjabAsc1.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutPenjabAsc1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutPenjabAsc1.setForeground(new java.awt.Color(70, 70, 70));
        MnUrutPenjabAsc1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutPenjabAsc1.setText("Cara Bayar Ascending");
        MnUrutPenjabAsc1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutPenjabAsc1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutPenjabAsc1.setName("MnUrutPenjabAsc1"); // NOI18N
        MnUrutPenjabAsc1.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutPenjabAsc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutPenjabAsc1ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutPenjabAsc1);

        MnUrutStatusDesc1.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutStatusDesc1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutStatusDesc1.setForeground(new java.awt.Color(70, 70, 70));
        MnUrutStatusDesc1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutStatusDesc1.setText("Status Descending");
        MnUrutStatusDesc1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutStatusDesc1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutStatusDesc1.setName("MnUrutStatusDesc1"); // NOI18N
        MnUrutStatusDesc1.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutStatusDesc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutStatusDesc1ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutStatusDesc1);

        MnUrutStatusAsc1.setBackground(new java.awt.Color(255, 255, 254));
        MnUrutStatusAsc1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUrutStatusAsc1.setForeground(new java.awt.Color(70, 70, 70));
        MnUrutStatusAsc1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUrutStatusAsc1.setText("Status Ascending");
        MnUrutStatusAsc1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUrutStatusAsc1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUrutStatusAsc1.setName("MnUrutStatusAsc1"); // NOI18N
        MnUrutStatusAsc1.setPreferredSize(new java.awt.Dimension(180, 26));
        MnUrutStatusAsc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUrutStatusAsc1ActionPerformed(evt);
            }
        });
        MnUrut1.add(MnUrutStatusAsc1);

        jPopupMenu2.add(MnUrut1);

        ppRiwayat1.setBackground(new java.awt.Color(255, 255, 254));
        ppRiwayat1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRiwayat1.setForeground(new java.awt.Color(70, 70, 70));
        ppRiwayat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRiwayat1.setText("Riwayat Perawatan");
        ppRiwayat1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRiwayat1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRiwayat1.setName("ppRiwayat1"); // NOI18N
        ppRiwayat1.setPreferredSize(new java.awt.Dimension(210, 26));
        ppRiwayat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRiwayat1BtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu2.add(ppRiwayat1);

        MnHapusRujukan.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusRujukan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusRujukan.setForeground(new java.awt.Color(70, 70, 70));
        MnHapusRujukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusRujukan.setText("Hapus Rujukan Poli Internal");
        MnHapusRujukan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusRujukan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusRujukan.setName("MnHapusRujukan"); // NOI18N
        MnHapusRujukan.setPreferredSize(new java.awt.Dimension(210, 26));
        MnHapusRujukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusRujukanActionPerformed(evt);
            }
        });
        jPopupMenu2.add(MnHapusRujukan);

        MnInterpretasiEkg2.setBackground(new java.awt.Color(255, 255, 254));
        MnInterpretasiEkg2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnInterpretasiEkg2.setForeground(new java.awt.Color(70, 70, 70));
        MnInterpretasiEkg2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnInterpretasiEkg2.setText("Interpretasi EKG");
        MnInterpretasiEkg2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnInterpretasiEkg2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnInterpretasiEkg2.setName("MnInterpretasiEkg2"); // NOI18N
        MnInterpretasiEkg2.setPreferredSize(new java.awt.Dimension(210, 26));
        MnInterpretasiEkg2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnInterpretasiEkg2ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(MnInterpretasiEkg2);

        MnUbahRujukan.setBackground(new java.awt.Color(255, 255, 254));
        MnUbahRujukan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUbahRujukan.setForeground(new java.awt.Color(70, 70, 70));
        MnUbahRujukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUbahRujukan.setText("Ubah Poli Rujukan");
        MnUbahRujukan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUbahRujukan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUbahRujukan.setName("MnUbahRujukan"); // NOI18N
        MnUbahRujukan.setPreferredSize(new java.awt.Dimension(210, 26));
        MnUbahRujukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUbahRujukanActionPerformed(evt);
            }
        });
        jPopupMenu2.add(MnUbahRujukan);

        MnJawabKonsul.setBackground(new java.awt.Color(255, 255, 254));
        MnJawabKonsul.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnJawabKonsul.setForeground(new java.awt.Color(70, 70, 70));
        MnJawabKonsul.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnJawabKonsul.setText("Jawab Konsul");
        MnJawabKonsul.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnJawabKonsul.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnJawabKonsul.setName("MnJawabKonsul"); // NOI18N
        MnJawabKonsul.setPreferredSize(new java.awt.Dimension(210, 26));
        MnJawabKonsul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnJawabKonsulActionPerformed(evt);
            }
        });
        jPopupMenu2.add(MnJawabKonsul);

        DlgCatatan.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgCatatan.setName("DlgCatatan"); // NOI18N
        DlgCatatan.setUndecorated(true);
        DlgCatatan.setResizable(false);

        internalFrame6.setBorder(null);
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setWarnaAtas(new java.awt.Color(100, 100, 10));
        internalFrame6.setWarnaBawah(new java.awt.Color(100, 100, 10));
        internalFrame6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                internalFrame6MouseClicked(evt);
            }
        });
        internalFrame6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        LabelCatatan.setForeground(new java.awt.Color(255, 255, 255));
        LabelCatatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelCatatan.setText("Catatan");
        LabelCatatan.setName("LabelCatatan"); // NOI18N
        LabelCatatan.setPreferredSize(new java.awt.Dimension(580, 23));
        LabelCatatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LabelCatatanMouseClicked(evt);
            }
        });
        internalFrame6.add(LabelCatatan);

        DlgCatatan.getContentPane().add(internalFrame6, java.awt.BorderLayout.CENTER);

        DlgInterpretasiEKG.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgInterpretasiEKG.setName("DlgInterpretasiEKG"); // NOI18N
        DlgInterpretasiEKG.setUndecorated(true);
        DlgInterpretasiEKG.setResizable(false);
        DlgInterpretasiEKG.setSize(new java.awt.Dimension(1200, 200));

        internalFrame7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Interpretasi EKG ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame7.setName("internalFrame7"); // NOI18N

        TNoRMint.setEditable(false);
        TNoRMint.setHighlighter(null);
        TNoRMint.setName("TNoRMint"); // NOI18N

        TNoRwint.setHighlighter(null);
        TNoRwint.setName("TNoRwint"); // NOI18N
        TNoRwint.setPreferredSize(new java.awt.Dimension(25, 28));

        jLabel12.setText("Pasien :");
        jLabel12.setName("jLabel12"); // NOI18N

        TPasienint.setEditable(false);
        TPasienint.setHighlighter(null);
        TPasienint.setName("TPasienint"); // NOI18N
        TPasienint.setPreferredSize(new java.awt.Dimension(25, 28));

        jLabel25.setText("Interpretasi :");
        jLabel25.setName("jLabel25"); // NOI18N

        BtnSimpan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan2.setMnemonic('S');
        BtnSimpan2.setText("Simpan");
        BtnSimpan2.setToolTipText("Alt+S");
        BtnSimpan2.setName("BtnSimpan2"); // NOI18N
        BtnSimpan2.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan2ActionPerformed(evt);
            }
        });
        BtnSimpan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpan2KeyPressed(evt);
            }
        });

        BtnKeluar5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnKeluar5.setMnemonic('T');
        BtnKeluar5.setText("Tutup");
        BtnKeluar5.setToolTipText("Alt+T");
        BtnKeluar5.setName("BtnKeluar5"); // NOI18N
        BtnKeluar5.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar5ActionPerformed(evt);
            }
        });
        BtnKeluar5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluar5KeyPressed(evt);
            }
        });

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        TKesan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TKesan.setColumns(20);
        TKesan.setRows(5);
        TKesan.setName("TKesan"); // NOI18N
        TKesan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKesanKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(TKesan);

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

        Tind.setEditable(false);
        Tind.setHighlighter(null);
        Tind.setName("Tind"); // NOI18N

        javax.swing.GroupLayout internalFrame7Layout = new javax.swing.GroupLayout(internalFrame7);
        internalFrame7.setLayout(internalFrame7Layout);
        internalFrame7Layout.setHorizontalGroup(
            internalFrame7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(internalFrame7Layout.createSequentialGroup()
                .addGroup(internalFrame7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(internalFrame7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(internalFrame7Layout.createSequentialGroup()
                        .addComponent(TNoRwint, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TNoRMint, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TPasienint, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(382, Short.MAX_VALUE))
                    .addGroup(internalFrame7Layout.createSequentialGroup()
                        .addGroup(internalFrame7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(internalFrame7Layout.createSequentialGroup()
                                .addComponent(BtnSimpan2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtnPrint1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)
                                .addComponent(BtnKeluar5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(Tind, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(scrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 667, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        internalFrame7Layout.setVerticalGroup(
            internalFrame7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(internalFrame7Layout.createSequentialGroup()
                .addGroup(internalFrame7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(internalFrame7Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, internalFrame7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(internalFrame7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TNoRwint, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TPasienint, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TNoRMint, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(internalFrame7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(internalFrame7Layout.createSequentialGroup()
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(scrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
                .addGap(52, 52, 52)
                .addGroup(internalFrame7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(internalFrame7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(BtnKeluar5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Tind, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(internalFrame7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(BtnSimpan2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BtnPrint1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        DlgInterpretasiEKG.getContentPane().add(internalFrame7, java.awt.BorderLayout.CENTER);

        DlgJawabanKonsul.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgJawabanKonsul.setName("DlgJawabanKonsul"); // NOI18N
        DlgJawabanKonsul.setResizable(false);

        internalFrame8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Jawaban Konsul ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame8.setName("internalFrame8"); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel26.setText("DETAIL KONSUL / RUJUKAN INTERNAL :");
        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setName("jLabel26"); // NOI18N

        jLabel27.setText("Nama Lengkap :");
        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel27.setName("jLabel27"); // NOI18N

        LabelNama.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelNama.setText("Nama");
        LabelNama.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LabelNama.setName("LabelNama"); // NOI18N

        jLabel28.setText("No. RM :");
        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel28.setName("jLabel28"); // NOI18N

        LabelNoRM.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelNoRM.setText("NoRM");
        LabelNoRM.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LabelNoRM.setName("LabelNoRM"); // NOI18N

        jLabel29.setText("No. Rawat :");
        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel29.setName("jLabel29"); // NOI18N

        LabelNoRW.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelNoRW.setText("NoRW");
        LabelNoRW.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LabelNoRW.setName("LabelNoRW"); // NOI18N

        jLabel38.setText("Dokter Perujuk :");
        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel38.setName("jLabel38"); // NOI18N

        LabelDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelDokter.setText("Nama Dokter");
        LabelDokter.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LabelDokter.setName("LabelDokter"); // NOI18N

        jLabel39.setText("Poli Tujuan :");
        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel39.setName("jLabel39"); // NOI18N

        LabelPoliTujuan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelPoliTujuan.setText("NamaPoli");
        LabelPoliTujuan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LabelPoliTujuan.setName("LabelPoliTujuan"); // NOI18N

        jLabel40.setText("DETAIL PASIEN :");
        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel40.setName("jLabel40"); // NOI18N

        jLabel41.setText("Dokter Tujuan :");
        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel41.setName("jLabel41"); // NOI18N

        LabelDokterTujuan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelDokterTujuan.setText("NamaDokterTujuan");
        LabelDokterTujuan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LabelDokterTujuan.setName("LabelDokterTujuan"); // NOI18N

        jLabel42.setText("Poli Perujuk :");
        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel42.setName("jLabel42"); // NOI18N

        LabelPoliPerujuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelPoliPerujuk.setText("NamaPoli");
        LabelPoliPerujuk.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LabelPoliPerujuk.setName("LabelPoliPerujuk"); // NOI18N

        jLabel44.setText("Pemeriksaan :");
        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel44.setName("jLabel44"); // NOI18N

        jLabel45.setText("Diagnosa :");
        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel45.setName("jLabel45"); // NOI18N

        LabelDiagnosa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelDiagnosa.setText("Diagnosa");
        LabelDiagnosa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        LabelDiagnosa.setName("LabelDiagnosa"); // NOI18N

        jLabel46.setText("Saran :");
        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel46.setName("jLabel46"); // NOI18N

        TSaranJawab.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TSaranJawab.setColumns(20);
        TSaranJawab.setRows(5);
        TSaranJawab.setText("Saran\n");
        TSaranJawab.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        TSaranJawab.setName("TSaranJawab"); // NOI18N
        TSaranJawab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TSaranJawabKeyPressed(evt);
            }
        });

        TKonsulJawab.setEditable(false);
        TKonsulJawab.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TKonsulJawab.setColumns(20);
        TKonsulJawab.setRows(5);
        TKonsulJawab.setText("Konsul\n");
        TKonsulJawab.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        TKonsulJawab.setName("TKonsulJawab"); // NOI18N
        TKonsulJawab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKonsulJawabKeyPressed(evt);
            }
        });

        TPemeriksaanJawab.setEditable(false);
        TPemeriksaanJawab.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TPemeriksaanJawab.setColumns(20);
        TPemeriksaanJawab.setRows(5);
        TPemeriksaanJawab.setText("Pemeriksaan\n");
        TPemeriksaanJawab.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        TPemeriksaanJawab.setName("TPemeriksaanJawab"); // NOI18N
        TPemeriksaanJawab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPemeriksaanJawabKeyPressed(evt);
            }
        });

        BtnKeluar6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnKeluar6.setMnemonic('K');
        BtnKeluar6.setText("Tutup");
        BtnKeluar6.setToolTipText("Alt+K");
        BtnKeluar6.setName("BtnKeluar6"); // NOI18N
        BtnKeluar6.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar6ActionPerformed(evt);
            }
        });
        BtnKeluar6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluar6KeyPressed(evt);
            }
        });

        jLabel47.setText("Catatan Konsul :");
        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel47.setName("jLabel47"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(LabelNama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGap(6, 6, 6)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(TSaranJawab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(6, 6, 6)
                                                        .addComponent(LabelDiagnosa, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                            .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addGap(6, 6, 6)
                                                            .addComponent(LabelDokterTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addGap(6, 6, 6)
                                                            .addComponent(LabelPoliTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                            .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addGap(8, 8, 8)
                                                            .addComponent(TPemeriksaanJawab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(LabelNoRM, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(LabelNoRW, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(LabelDokter, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(LabelPoliPerujuk, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TKonsulJawab, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(BtnKeluar6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(107, 107, 107))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelNoRM, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelNoRW, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelDokter, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelPoliPerujuk, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelDokterTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LabelPoliTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TPemeriksaanJawab, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LabelNama, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TKonsulJawab, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelDiagnosa, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TSaranJawab, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(BtnKeluar6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout internalFrame8Layout = new javax.swing.GroupLayout(internalFrame8);
        internalFrame8.setLayout(internalFrame8Layout);
        internalFrame8Layout.setHorizontalGroup(
            internalFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        internalFrame8Layout.setVerticalGroup(
            internalFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout DlgJawabanKonsulLayout = new javax.swing.GroupLayout(DlgJawabanKonsul.getContentPane());
        DlgJawabanKonsul.getContentPane().setLayout(DlgJawabanKonsulLayout);
        DlgJawabanKonsulLayout.setHorizontalGroup(
            DlgJawabanKonsulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DlgJawabanKonsulLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(internalFrame8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        DlgJawabanKonsulLayout.setVerticalGroup(
            DlgJawabanKonsulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DlgJawabanKonsulLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(internalFrame8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        WindowUbahRujukan.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowUbahRujukan.setName("WindowUbahRujukan"); // NOI18N
        WindowUbahRujukan.setUndecorated(true);
        WindowUbahRujukan.setResizable(false);

        internalFrame9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Perubahan Poliklinik Rujukan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame9.setName("internalFrame9"); // NOI18N
        internalFrame9.setLayout(null);

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
        internalFrame9.add(BtnCloseIn6);
        BtnCloseIn6.setBounds(310, 70, 100, 30);

        BtnSimpan6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan6.setMnemonic('S');
        BtnSimpan6.setText("Simpan");
        BtnSimpan6.setToolTipText("Alt+S");
        BtnSimpan6.setName("BtnSimpan6"); // NOI18N
        BtnSimpan6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan6ActionPerformed(evt);
            }
        });
        internalFrame9.add(BtnSimpan6);
        BtnSimpan6.setBounds(70, 70, 150, 30);

        jLabel34.setText("Unit/Poli :");
        jLabel34.setName("jLabel34"); // NOI18N
        internalFrame9.add(jLabel34);
        jLabel34.setBounds(0, 30, 90, 23);

        KdPoli1.setEditable(false);
        KdPoli1.setHighlighter(null);
        KdPoli1.setName("KdPoli1"); // NOI18N
        internalFrame9.add(KdPoli1);
        KdPoli1.setBounds(110, 30, 70, 23);

        NmPoli1.setEditable(false);
        NmPoli1.setHighlighter(null);
        NmPoli1.setName("NmPoli1"); // NOI18N
        internalFrame9.add(NmPoli1);
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
        internalFrame9.add(BtnPoli1);
        BtnPoli1.setBounds(440, 30, 28, 23);

        javax.swing.GroupLayout WindowUbahRujukanLayout = new javax.swing.GroupLayout(WindowUbahRujukan.getContentPane());
        WindowUbahRujukan.getContentPane().setLayout(WindowUbahRujukanLayout);
        WindowUbahRujukanLayout.setHorizontalGroup(
            WindowUbahRujukanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(internalFrame9, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        WindowUbahRujukanLayout.setVerticalGroup(
            WindowUbahRujukanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(internalFrame9, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        Kategori.setHighlighter(null);
        Kategori.setName("Kategori"); // NOI18N

        WindowRunningText.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowRunningText.setName("WindowRunningText"); // NOI18N
        WindowRunningText.setUndecorated(true);
        WindowRunningText.setResizable(false);

        internalFrame11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Running Text Poliklinik ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame11.setName("internalFrame11"); // NOI18N
        internalFrame11.setLayout(null);

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
        internalFrame11.add(BtnCloseIn7);
        BtnCloseIn7.setBounds(350, 120, 100, 30);

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
        internalFrame11.add(BtnSimpan7);
        BtnSimpan7.setBounds(200, 120, 150, 30);

        jLabel36.setText("Unit/Poli :");
        jLabel36.setName("jLabel36"); // NOI18N
        internalFrame11.add(jLabel36);
        jLabel36.setBounds(0, 30, 90, 23);

        KdPoli2.setEditable(false);
        KdPoli2.setHighlighter(null);
        KdPoli2.setName("KdPoli2"); // NOI18N
        internalFrame11.add(KdPoli2);
        KdPoli2.setBounds(100, 30, 70, 23);

        NmPoli2.setEditable(false);
        NmPoli2.setHighlighter(null);
        NmPoli2.setName("NmPoli2"); // NOI18N
        internalFrame11.add(NmPoli2);
        NmPoli2.setBounds(170, 30, 263, 23);

        BtnPoli2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPoli2.setMnemonic('X');
        BtnPoli2.setToolTipText("Alt+X");
        BtnPoli2.setName("BtnPoli2"); // NOI18N
        BtnPoli2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPoli2ActionPerformed(evt);
            }
        });
        internalFrame11.add(BtnPoli2);
        BtnPoli2.setBounds(430, 30, 28, 23);

        TRunningText.setHighlighter(null);
        TRunningText.setName("TRunningText"); // NOI18N
        internalFrame11.add(TRunningText);
        TRunningText.setBounds(100, 60, 350, 40);

        jLabel43.setText("Running Text :");
        jLabel43.setName("jLabel43"); // NOI18N
        internalFrame11.add(jLabel43);
        jLabel43.setBounds(0, 60, 90, 23);

        javax.swing.GroupLayout WindowRunningTextLayout = new javax.swing.GroupLayout(WindowRunningText.getContentPane());
        WindowRunningText.getContentPane().setLayout(WindowRunningTextLayout);
        WindowRunningTextLayout.setHorizontalGroup(
            WindowRunningTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(internalFrame11, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        WindowRunningTextLayout.setVerticalGroup(
            WindowRunningTextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(internalFrame11, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        ObatKronis.setHighlighter(null);
        ObatKronis.setName("ObatKronis"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Registrasi Periksa Hari Ini ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelGlass6.add(BtnSimpan);

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
        panelGlass6.add(BtnBatal);

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
        panelGlass6.add(BtnEdit);

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
        panelGlass6.add(BtnHapus);

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
        panelGlass6.add(BtnPrint);

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
        panelGlass6.add(BtnAll);

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(70, 30));
        panelGlass6.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(72, 30));
        panelGlass6.add(LCount);

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
        panelGlass6.add(BtnKeluar);

        BtnPanggil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        BtnPanggil.setMnemonic('K');
        BtnPanggil.setText("Panggil");
        BtnPanggil.setToolTipText("Alt+K");
        BtnPanggil.setName("BtnPanggil"); // NOI18N
        BtnPanggil.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPanggil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPanggilActionPerformed(evt);
            }
        });
        BtnPanggil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPanggilKeyPressed(evt);
            }
        });
        panelGlass6.add(BtnPanggil);

        BtnText.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        BtnText.setMnemonic('K');
        BtnText.setText("Running Text");
        BtnText.setToolTipText("Alt+K");
        BtnText.setName("BtnText"); // NOI18N
        BtnText.setPreferredSize(new java.awt.Dimension(120, 30));
        BtnText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTextActionPerformed(evt);
            }
        });
        panelGlass6.add(BtnText);

        jPanel2.add(panelGlass6, java.awt.BorderLayout.PAGE_END);

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel15.setText("Periode :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass7.add(jLabel15);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-08-2023" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(133, 23));
        panelGlass7.add(DTPCari1);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("s.d");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(24, 23));
        panelGlass7.add(jLabel17);

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-08-2023" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(133, 23));
        panelGlass7.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(158, 23));
        panelGlass7.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(300, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass7.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('7');
        BtnCari.setToolTipText("Alt+7");
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
        panelGlass7.add(BtnCari);

        ChkSemua.setBorder(null);
        ChkSemua.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkSemua.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkSemua.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkSemua.setName("ChkSemua"); // NOI18N
        panelGlass7.add(ChkSemua);

        jPanel2.add(panelGlass7, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel14.setText("Dokter :");
        jLabel14.setName("jLabel14"); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass8.add(jLabel14);

        CrDokter.setEditable(false);
        CrDokter.setName("CrDokter"); // NOI18N
        CrDokter.setPreferredSize(new java.awt.Dimension(300, 23));
        panelGlass8.add(CrDokter);

        BtnSeek3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek3.setMnemonic('6');
        BtnSeek3.setToolTipText("ALt+6");
        BtnSeek3.setName("BtnSeek3"); // NOI18N
        BtnSeek3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek3ActionPerformed(evt);
            }
        });
        panelGlass8.add(BtnSeek3);

        jLabel16.setText("Unit :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(125, 23));
        panelGlass8.add(jLabel16);

        CrPoli.setEditable(false);
        CrPoli.setName("CrPoli"); // NOI18N
        CrPoli.setPreferredSize(new java.awt.Dimension(300, 23));
        panelGlass8.add(CrPoli);

        BtnSeek4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek4.setMnemonic('5');
        BtnSeek4.setToolTipText("ALt+5");
        BtnSeek4.setName("BtnSeek4"); // NOI18N
        BtnSeek4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek4ActionPerformed(evt);
            }
        });
        panelGlass8.add(BtnSeek4);

        jPanel2.add(panelGlass8, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(490, 167));
        FormInput.setLayout(null);

        jLabel3.setText("No. Reg. :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 12, 70, 23);

        jLabel4.setText("No. Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 42, 70, 23);

        TDokter.setEditable(false);
        TDokter.setName("TDokter"); // NOI18N
        FormInput.add(TDokter);
        TDokter.setBounds(183, 102, 209, 23);

        TNoRw.setEditable(false);
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 42, 220, 23);

        jLabel8.setText("Tgl. Reg. :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 72, 70, 23);

        jLabel13.setText("Dr Dituju :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 102, 70, 23);

        jLabel9.setText("Jam :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(165, 72, 36, 23);

        DTPReg.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-08-2023" }));
        DTPReg.setDisplayFormat("dd-MM-yyyy");
        DTPReg.setName("DTPReg"); // NOI18N
        DTPReg.setOpaque(false);
        DTPReg.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPRegItemStateChanged(evt);
            }
        });
        DTPReg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPRegKeyPressed(evt);
            }
        });
        FormInput.add(DTPReg);
        DTPReg.setBounds(74, 72, 90, 23);

        jLabel20.setText("Png. Jawab :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(416, 42, 100, 23);

        TPngJwb.setHighlighter(null);
        TPngJwb.setName("TPngJwb"); // NOI18N
        TPngJwb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPngJwbKeyPressed(evt);
            }
        });
        FormInput.add(TPngJwb);
        TPngJwb.setBounds(520, 42, 150, 23);

        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(520, 12, 110, 23);

        TNoReg.setHighlighter(null);
        TNoReg.setName("TNoReg"); // NOI18N
        TNoReg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRegKeyPressed(evt);
            }
        });
        FormInput.add(TNoReg);
        TNoReg.setBounds(74, 12, 120, 23);

        jLabel21.setText("Almt Png. Jwb :");
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput.add(jLabel21);
        jLabel21.setBounds(426, 72, 90, 23);

        CmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        CmbJam.setName("CmbJam"); // NOI18N
        CmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJamKeyPressed(evt);
            }
        });
        FormInput.add(CmbJam);
        CmbJam.setBounds(205, 72, 62, 23);

        CmbMenit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbMenit.setName("CmbMenit"); // NOI18N
        CmbMenit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbMenitKeyPressed(evt);
            }
        });
        FormInput.add(CmbMenit);
        CmbMenit.setBounds(270, 72, 62, 23);

        CmbDetik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbDetik.setName("CmbDetik"); // NOI18N
        CmbDetik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbDetikKeyPressed(evt);
            }
        });
        FormInput.add(CmbDetik);
        CmbDetik.setBounds(335, 72, 62, 23);

        jLabel7.setText("No.Rekam Medik :");
        jLabel7.setName("jLabel7"); // NOI18N
        FormInput.add(jLabel7);
        jLabel7.setBounds(416, 12, 100, 23);

        TAlmt.setHighlighter(null);
        TAlmt.setName("TAlmt"); // NOI18N
        TAlmt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAlmtKeyPressed(evt);
            }
        });
        FormInput.add(TAlmt);
        TAlmt.setBounds(520, 72, 170, 23);

        BtnPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPasien.setMnemonic('1');
        BtnPasien.setToolTipText("ALt+1");
        BtnPasien.setName("BtnPasien"); // NOI18N
        BtnPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPasienActionPerformed(evt);
            }
        });
        FormInput.add(BtnPasien);
        BtnPasien.setBounds(852, 12, 28, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(632, 12, 218, 23);

        jLabel22.setText("Hubungan :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(648, 42, 98, 23);

        THbngn.setHighlighter(null);
        THbngn.setName("THbngn"); // NOI18N
        THbngn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                THbngnKeyPressed(evt);
            }
        });
        FormInput.add(THbngn);
        THbngn.setBounds(750, 42, 130, 23);

        ChkJln.setBorder(null);
        ChkJln.setSelected(true);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        FormInput.add(ChkJln);
        ChkJln.setBounds(400, 72, 23, 23);

        jLabel19.setText("Unit :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(0, 132, 70, 23);

        TPoli.setEditable(false);
        TPoli.setName("TPoli"); // NOI18N
        FormInput.add(TPoli);
        TPoli.setBounds(141, 132, 156, 23);

        TBiaya.setName("TBiaya"); // NOI18N
        TBiaya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBiayaKeyPressed(evt);
            }
        });
        FormInput.add(TBiaya);
        TBiaya.setBounds(298, 132, 94, 23);

        kddokter.setHighlighter(null);
        kddokter.setName("kddokter"); // NOI18N
        kddokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddokterKeyPressed(evt);
            }
        });
        FormInput.add(kddokter);
        kddokter.setBounds(74, 102, 107, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('3');
        BtnDokter.setToolTipText("ALt+3");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokter);
        BtnDokter.setBounds(395, 102, 28, 23);

        kdpoli.setHighlighter(null);
        kdpoli.setName("kdpoli"); // NOI18N
        kdpoli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpoliKeyPressed(evt);
            }
        });
        FormInput.add(kdpoli);
        kdpoli.setBounds(74, 132, 66, 23);

        BtnUnit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnUnit.setMnemonic('4');
        BtnUnit.setToolTipText("ALt+4");
        BtnUnit.setName("BtnUnit"); // NOI18N
        BtnUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnUnitActionPerformed(evt);
            }
        });
        FormInput.add(BtnUnit);
        BtnUnit.setBounds(395, 132, 28, 23);

        jLabel30.setText("Status :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(648, 72, 98, 23);

        TStatus.setEditable(false);
        TStatus.setHighlighter(null);
        TStatus.setName("TStatus"); // NOI18N
        FormInput.add(TStatus);
        TStatus.setBounds(750, 72, 130, 23);

        jLabel18.setText("Jenis Bayar :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(426, 102, 90, 23);

        kdpnj.setHighlighter(null);
        kdpnj.setName("kdpnj"); // NOI18N
        kdpnj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpnjKeyPressed(evt);
            }
        });
        FormInput.add(kdpnj);
        kdpnj.setBounds(520, 102, 70, 23);

        nmpnj.setEditable(false);
        nmpnj.setName("nmpnj"); // NOI18N
        FormInput.add(nmpnj);
        nmpnj.setBounds(592, 102, 270, 23);

        btnPenjab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPenjab.setMnemonic('2');
        btnPenjab.setToolTipText("ALt+2");
        btnPenjab.setName("btnPenjab"); // NOI18N
        btnPenjab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPenjabActionPerformed(evt);
            }
        });
        FormInput.add(btnPenjab);
        btnPenjab.setBounds(860, 102, 28, 23);

        jLabel23.setText("Asal Rujukan :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(426, 132, 90, 23);

        AsalRujukan.setEditable(false);
        AsalRujukan.setName("AsalRujukan"); // NOI18N
        AsalRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsalRujukanKeyPressed(evt);
            }
        });
        FormInput.add(AsalRujukan);
        AsalRujukan.setBounds(520, 132, 340, 23);

        btnPenjab1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPenjab1.setMnemonic('2');
        btnPenjab1.setToolTipText("ALt+2");
        btnPenjab1.setName("btnPenjab1"); // NOI18N
        btnPenjab1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPenjab1ActionPerformed(evt);
            }
        });
        FormInput.add(btnPenjab1);
        btnPenjab1.setBounds(860, 132, 28, 23);

        ChkTracker.setBorder(null);
        ChkTracker.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkTracker.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkTracker.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkTracker.setName("ChkTracker"); // NOI18N
        ChkTracker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkTrackerActionPerformed(evt);
            }
        });
        FormInput.add(ChkTracker);
        ChkTracker.setBounds(196, 12, 23, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

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

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        TabRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(70, 70, 70));
        TabRawat.setInheritsPopupMenu(true);
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setToolTipText("* Klik data di table, kemudian klik kanan untuk memilih menu yang diinginkan");
        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbPetugas.setToolTipText("<html>Hijau: Pasien telah selesai dilayani.<br>"+
            "Ungu: Pasien BPJS terbit SEP dengan pendaftaran online.<br>"+
            "Pink: Pasien BPJS terbit SEP dengan pendaftaran offline/onsite.<br>"+
            "Kuning: Pasien telah dilakukan pemeriksaan CPPT.<br>"+
            "Biru: Pasien BPJS terbit SEP namun batal berobat.<br>"+
            "Merah: Pasien batal berobat.</html>");
        tbPetugas.setComponentPopupMenu(jPopupMenu1);
        tbPetugas.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbPetugas.setName("tbPetugas"); // NOI18N
        tbPetugas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPetugasMouseClicked(evt);
            }
        });
        tbPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPetugasKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPetugasKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbPetugas);

        TabRawat.addTab("Registrasi Awal", Scroll);

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll1.setToolTipText("Klik data di table, kemudian klik kanan untuk memilih menu yang diinginkan");
        Scroll1.setComponentPopupMenu(jPopupMenu2);
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbPetugas2.setToolTipText("Klik data di table, kemudian klik kanan untuk memilih menu yang diinginkan");
        tbPetugas2.setComponentPopupMenu(jPopupMenu2);
        tbPetugas2.setName("tbPetugas2"); // NOI18N
        tbPetugas2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPetugas2MouseClicked(evt);
            }
        });
        tbPetugas2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPetugas2KeyPressed(evt);
            }
        });
        Scroll1.setViewportView(tbPetugas2);

        TabRawat.addTab("Rujukan Internal Poli", Scroll1);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll2.setToolTipText("Klik data di table, kemudian klik kanan untuk memilih menu yang diinginkan");
        Scroll2.setComponentPopupMenu(jPopupMenu1);
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbPetugas1.setToolTipText("Klik data di table, kemudian klik kanan untuk memilih menu yang diinginkan");
        tbPetugas1.setComponentPopupMenu(jPopupMenu1);
        tbPetugas1.setName("tbPetugas1"); // NOI18N
        tbPetugas1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPetugas1MouseClicked(evt);
            }
        });
        tbPetugas1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPetugas1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPetugas1KeyReleased(evt);
            }
        });
        Scroll2.setViewportView(tbPetugas1);

        TabRawat.addTab("Pasien Lanjutan", Scroll2);

        Scroll3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll3.setToolTipText("Klik data di table, kemudian klik kanan untuk memilih menu yang diinginkan");
        Scroll3.setComponentPopupMenu(jPopupMenu1);
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbPetugas3.setToolTipText("Klik data di table, kemudian klik kanan untuk memilih menu yang diinginkan");
        tbPetugas3.setComponentPopupMenu(jPopupMenu1);
        tbPetugas3.setName("tbPetugas3"); // NOI18N
        Scroll3.setViewportView(tbPetugas3);

        TabRawat.addTab("Internal Lanjutan", Scroll3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        Valid.pindah(evt, TNoReg, DTPReg);
}//GEN-LAST:event_TNoRwKeyPressed

    private void DTPRegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPRegKeyPressed
        Valid.pindah(evt, TNoRw, CmbJam);
}//GEN-LAST:event_DTPRegKeyPressed

    private void TPngJwbKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPngJwbKeyPressed
        Valid.pindah(evt, TNoRM, THbngn);
}//GEN-LAST:event_TPngJwbKeyPressed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            isPas();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnPasienActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (!TPasien.getText().equals("")) {
                Map<String, Object> param = new HashMap<>();
                param.put("poli", TPoli.getText());
                param.put("antrian", TNoReg.getText());
                param.put("nama", TPasien.getText());
                param.put("norm", TNoRM.getText());
                param.put("bayar", nmpnj.getText());
                param.put("namars", var.getnamars());
                param.put("alamatrs", var.getalamatrs());
                param.put("kotars", var.getkabupatenrs());
                param.put("propinsirs", var.getpropinsirs());
                param.put("kontakrs", var.getkontakrs());
                param.put("emailrs", var.getemailrs());
                param.put("logo", Sequel.cariGambar("select logo from setting"));
                Valid.MyReport("rptCheckList.jrxml", "report", "::[ Check List ]::",
                        "select current_date() as sekarang", param);
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            isPas();
            TPngJwb.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            kdpoli.requestFocus();
        }
}//GEN-LAST:event_TNoRMKeyPressed

    private void TNoRegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRegKeyPressed
        Valid.pindah(evt, TCari, TNoRw);
}//GEN-LAST:event_TNoRegKeyPressed

    private void CmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJamKeyPressed
        Valid.pindah(evt, DTPReg, CmbMenit);
}//GEN-LAST:event_CmbJamKeyPressed

    private void CmbMenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbMenitKeyPressed
        Valid.pindah(evt, CmbJam, CmbDetik);
}//GEN-LAST:event_CmbMenitKeyPressed

    private void CmbDetikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbDetikKeyPressed
        Valid.pindah(evt, CmbMenit, kddokter);
}//GEN-LAST:event_CmbDetikKeyPressed

    private void TAlmtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAlmtKeyPressed
        Valid.pindah(evt, THbngn, kdpnj);
}//GEN-LAST:event_TAlmtKeyPressed

    private void BtnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPasienActionPerformed
        var.setform("DlgReg");
        //pasien.penjab.removeWindowListener(null);
        //pasien.penjab.getTable().removeKeyListener(null);
        pasien.emptTeks();
        pasien.isCek();
        pasien.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        pasien.setLocationRelativeTo(internalFrame1);
        pasien.setVisible(true);
}//GEN-LAST:event_BtnPasienActionPerformed

    private void THbngnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_THbngnKeyPressed
        Valid.pindah(evt, TPngJwb, TAlmt);
}//GEN-LAST:event_THbngnKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        bidang = cekBidang(var.getkode());
        if (var.getkode().equals("Admin Utama") || bidang.equals("Instalasi Rekam Medis (Amanah)") || var.getkode().equals("unit20")) {
            trySimpan();
        } else {
            setStatusPasien("Diterima","");
        }
        
//        if (var.getkode().equals("unit20") || var.getkode().equals("Admin Utama")
//                || var.getkode().equals("07012012017113007") || var.getkode().equals("07012062014313059") || var.getkode().equals("07012062019213080") || var.getkode().equals("07012062019213082") || var.getkode().equals("07012082019413099")
//                || var.getkode().equals("07012082019413103") || var.getkode().equals("07012102014413067") || var.getkode().equals("07012102014413068") || var.getkode().equals("07012102016113021") || var.getkode().equals("07012102018213015")
//                || var.getkode().equals("07012032011313006") || var.getkode().equals("07012102009213002")) {
//            trySimpan();
//        } else {
//            setStatusPasien("Diterima","");
//        }
//        } else if (var.getkode().contains("unit")) {
//            setStatusPasien("Diterima");
//        } else {
//            trySimpan();
//        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, kdpnj, BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
//        if (var.getkode().equals("unit20")|| var.getkode().equals("Admin Utama")
//                || var.getkode().equals("07012012017113007") || var.getkode().equals("07012062014313059") || var.getkode().equals("07012062019213080") || var.getkode().equals("07012062019213082") || var.getkode().equals("07012082019413099")
//                || var.getkode().equals("07012082019413103") || var.getkode().equals("07012102014413067") || var.getkode().equals("07012102014413068") || var.getkode().equals("07012102016113021") || var.getkode().equals("07012102018213015")
//                || var.getkode().equals("07012032011313006") || var.getkode().equals("07012102009213002")) {
        bidang = cekBidang(var.getkode());
        if (var.getkode().equals("Admin Utama") || bidang.equals("Instalasi Rekam Medis (Amanah)") || var.getkode().equals("unit20")) {
            ChkInput.setSelected(true);
            isForm();
            emptTeks();
        } else{
            bukaPeriksa();
        }
//        }else if (var.getkode().contains("unit")) {
//            bukaPeriksa();
//        } else {
//            ChkInput.setSelected(true);
//            isForm();
//            emptTeks();
//        }
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            emptTeks();
        } else {
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
//        if (var.getkode().equals("unit20")|| var.getkode().equals("Admin Utama")
//                || var.getkode().equals("07012012017113007") || var.getkode().equals("07012062014313059") || var.getkode().equals("07012062019213080") || var.getkode().equals("07012062019213082") || var.getkode().equals("07012082019413099")
//                || var.getkode().equals("07012082019413103") || var.getkode().equals("07012102014413067") || var.getkode().equals("07012102014413068") || var.getkode().equals("07012102016113021") || var.getkode().equals("07012102018213015")
//                || var.getkode().equals("07012032011313006") || var.getkode().equals("07012102009213002")) {
        bidang = cekBidang(var.getkode());
        if (var.getkode().equals("Admin Utama") || bidang.equals("Instalasi Rekam Medis (Amanah)") || var.getkode().equals("unit20")) {
            hapusRow();
        } else {
            setStatusPasien("Sudah","");
        }
//        } else if (var.getkode().contains("unit")) {
//            setStatusPasien("Sudah");
//        } else {
//            hapusRow();
//        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptReg.jrxml", "report", "::[ Data Registrasi Periksa ]::", "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"
                    + "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli,ifnull((select perujuk from rujuk_masuk where rujuk_masuk.no_rawat=reg_periksa.no_rawat),'') as perujuk,"
                    + "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab,pasien.no_tlp "
                    + "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab  "
                    + "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "
                    + "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_poli=poliklinik.kd_poli  "
                    + "where poliklinik.kd_poli<>'IGDK' and dokter.nm_dokter like '%" + CrDokter.getText() + "%' and poliklinik.nm_poli like '%" + CrPoli.getText() + "%' and tgl_registrasi between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and reg_periksa.no_reg like '%" + TCari.getText().trim() + "%' or "
                    + "poliklinik.kd_poli<>'IGDK' and dokter.nm_dokter like '%" + CrDokter.getText() + "%' and poliklinik.nm_poli like '%" + CrPoli.getText() + "%' and tgl_registrasi between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and reg_periksa.no_rawat like '%" + TCari.getText().trim() + "%' or "
                    + "poliklinik.kd_poli<>'IGDK' and dokter.nm_dokter like '%" + CrDokter.getText() + "%' and poliklinik.nm_poli like '%" + CrPoli.getText() + "%' and tgl_registrasi between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and reg_periksa.tgl_registrasi like '%" + TCari.getText().trim() + "%' or "
                    + "poliklinik.kd_poli<>'IGDK' and dokter.nm_dokter like '%" + CrDokter.getText() + "%' and poliklinik.nm_poli like '%" + CrPoli.getText() + "%' and tgl_registrasi between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and reg_periksa.kd_dokter like '%" + TCari.getText().trim() + "%' or "
                    + "poliklinik.kd_poli<>'IGDK' and dokter.nm_dokter like '%" + CrDokter.getText() + "%' and poliklinik.nm_poli like '%" + CrPoli.getText() + "%' and tgl_registrasi between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and dokter.nm_dokter like '%" + TCari.getText().trim() + "%' or "
                    + "poliklinik.kd_poli<>'IGDK' and dokter.nm_dokter like '%" + CrDokter.getText() + "%' and poliklinik.nm_poli like '%" + CrPoli.getText() + "%' and tgl_registrasi between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and reg_periksa.no_rkm_medis like '%" + TCari.getText().trim() + "%' or "
                    + "poliklinik.kd_poli<>'IGDK' and dokter.nm_dokter like '%" + CrDokter.getText() + "%' and poliklinik.nm_poli like '%" + CrPoli.getText() + "%' and tgl_registrasi between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and reg_periksa.stts_daftar like '%" + TCari.getText().trim() + "%' or "
                    + "poliklinik.kd_poli<>'IGDK' and dokter.nm_dokter like '%" + CrDokter.getText() + "%' and poliklinik.nm_poli like '%" + CrPoli.getText() + "%' and tgl_registrasi between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and pasien.nm_pasien like '%" + TCari.getText().trim() + "%' or "
                    + "poliklinik.kd_poli<>'IGDK' and dokter.nm_dokter like '%" + CrDokter.getText() + "%' and poliklinik.nm_poli like '%" + CrPoli.getText() + "%' and tgl_registrasi between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and poliklinik.nm_poli like '%" + TCari.getText().trim() + "%' or "
                    + "poliklinik.kd_poli<>'IGDK' and dokter.nm_dokter like '%" + CrDokter.getText() + "%' and poliklinik.nm_poli like '%" + CrPoli.getText() + "%' and tgl_registrasi between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and reg_periksa.p_jawab like '%" + TCari.getText().trim() + "%' or "
                    + "poliklinik.kd_poli<>'IGDK' and dokter.nm_dokter like '%" + CrDokter.getText() + "%' and poliklinik.nm_poli like '%" + CrPoli.getText() + "%' and tgl_registrasi between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and reg_periksa.almt_pj like '%" + TCari.getText().trim() + "%' or "
                    + "poliklinik.kd_poli<>'IGDK' and dokter.nm_dokter like '%" + CrDokter.getText() + "%' and poliklinik.nm_poli like '%" + CrPoli.getText() + "%' and tgl_registrasi between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and penjab.png_jawab like '%" + TCari.getText().trim() + "%' or "
                    + "poliklinik.kd_poli<>'IGDK' and dokter.nm_dokter like '%" + CrDokter.getText() + "%' and poliklinik.nm_poli like '%" + CrPoli.getText() + "%' and tgl_registrasi between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and reg_periksa.hubunganpj like '%" + TCari.getText().trim() + "%' order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg desc", param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        pasien.dispose();
        pasien.bahasa.dispose();
        pasien.cacat.dispose();
        pasien.kab.dispose();
        pasien.kec.dispose();
        pasien.kel.dispose();
        pasien.penjab.dispose();
        pasien.perusahaan.dispose();
        pasien.prop.dispose();
        pasien.suku.dispose();
        dokter.dispose();
        dokter.dokter.dispose();
        dokter2.dokter.dispose();
        poli.dispose();
        poli2.dispose();
        dlgrjk.dispose();
        rujukmasuk.WindowPerujuk.dispose();
        DlgSakit.dispose();
        DlgSakit2.dispose();
        DlgCatatan.dispose();
        var.setAktif(false);
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnKeluarActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnPrint, TCari);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        CrPoli.setText("");
        CrDokter.setText("");
        TCari.setText("");
        if (TabRawat.getSelectedIndex() == 0) {
            tampil();
        } else if (TabRawat.getSelectedIndex() == 1) {
            tampil2();
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

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
//        if (var.getkode().equals("unit20")|| var.getkode().equals("Admin Utama")
//                || var.getkode().equals("07012012017113007") || var.getkode().equals("07012062014313059") || var.getkode().equals("07012062019213080") || var.getkode().equals("07012062019213082") || var.getkode().equals("07012082019413099")
//                || var.getkode().equals("07012082019413103") || var.getkode().equals("07012102014413067") || var.getkode().equals("07012102014413068") || var.getkode().equals("07012102016113021") || var.getkode().equals("07012102018213015")
//                || var.getkode().equals("07012032011313006") || var.getkode().equals("07012102009213002")) {
        bidang = cekBidang(var.getkode());
        if (var.getkode().equals("Admin Utama") || bidang.equals("Instalasi Rekam Medis (Amanah)") || var.getkode().equals("unit20")) {
            editRow();
        } else {
            setStatusPasien("Dilayani",tbPetugas.getModel().getValueAt(tbPetugas.getSelectedRow(), 22).toString());
        }
//        }else if (var.getkode().contains("unit")) {
//            setStatusPasien("Dilayani");
//        } else {
//            editRow();
//        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

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
            tampil();
        } else if (TabRawat.getSelectedIndex() == 1) {
            tampil2();
        } else if (TabRawat.getSelectedIndex() == 2) {
            tampil3();
        } else if (TabRawat.getSelectedIndex() == 3) {
            tampil4();
        }
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void TBiayaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBiayaKeyPressed
        Valid.pindah(evt, kdpoli, BtnSimpan);
}//GEN-LAST:event_TBiayaKeyPressed

private void kddokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddokterKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
        isNumber();
        Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", TDokter, kddokter.getText());
    } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
        BtnDokterActionPerformed(null);
    } else {
        Valid.pindah(evt, CmbDetik, kdpoli);
    }
}//GEN-LAST:event_kddokterKeyPressed

private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
    pilihan = 1;
    var.setform("DlgReg");
    if (aktifjadwal.equals("aktif")) {
        if (var.getkode().equals("Admin Utama")) {
            dokter.isCek();
            dokter.TCari.requestFocus();
            dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            dokter.setLocationRelativeTo(internalFrame1);
            dokter.setVisible(true);
        } else {
            dokter2.setPoli(TPoli.getText());
            dokter2.isCek();
            dokter2.SetHari(DTPReg.getDate());
            dokter2.tampil();
            dokter2.TCari.requestFocus();
            dokter2.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            dokter2.setLocationRelativeTo(internalFrame1);
            dokter2.setVisible(true);
        }
    } else {
        dokter.isCek();
        dokter.TCari.requestFocus();
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }
}//GEN-LAST:event_BtnDokterActionPerformed

private void kdpoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpoliKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
        Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?", TPoli, kdpoli.getText());
    } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
        BtnUnitActionPerformed(null);
    } else {
        Valid.pindah(evt, kddokter, TNoRM);
    }
}//GEN-LAST:event_kdpoliKeyPressed

private void BtnUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnUnitActionPerformed
    var.setform("DlgReg");
    pilihan = 1;

    if (aktifjadwal.equals("aktif")) {
        if (var.getkode().equals("Admin Utama")) {
            poli.isCek();
            poli.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            poli.setLocationRelativeTo(internalFrame1);
            poli.setVisible(true);
        } else {
            poli2.isCek();
            poli2.SetHari(DTPReg.getDate());
            poli2.tampil();
            poli2.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            poli2.setLocationRelativeTo(internalFrame1);
            poli2.setVisible(true);
        }
    } else {
        poli.isCek();
        poli.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setVisible(true);
    }
}//GEN-LAST:event_BtnUnitActionPerformed

private void BtnSeek3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek3ActionPerformed
    var.setform("DlgReg");
    pilihan = 2;
    dokter.isCek();
    dokter.TCari.requestFocus();
    dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
    dokter.setLocationRelativeTo(internalFrame1);
    dokter.setVisible(true);
}//GEN-LAST:event_BtnSeek3ActionPerformed

private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
    var.setform("DlgReg");
    pilihan = 2;
    poli.isCek();
    poli.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
    poli.setLocationRelativeTo(internalFrame1);
    poli.setVisible(true);
}//GEN-LAST:event_BtnSeek4ActionPerformed

private void MnKamarInapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKamarInapActionPerformed
    if (tabMode.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
        TNoReg.requestFocus();
    } else if (TPasien.getText().trim().equals("")) {
        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
        tbPetugas.requestFocus();
    } else {
        if (Sequel.cariRegistrasi(TNoRw.getText()) > 0) {
            JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi..!!");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            var.setstatus(true);
            DlgKamarInap dlgki = new DlgKamarInap(null, false);
            dlgki.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            dlgki.setLocationRelativeTo(internalFrame1);
            dlgki.emptTeks();
            dlgki.isCek();
            dlgki.setNoRm(TNoRw.getText());
            dlgki.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }

    }
}//GEN-LAST:event_MnKamarInapActionPerformed

private void MnRawatJalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRawatJalanActionPerformed
    bukaPeriksa();
}//GEN-LAST:event_MnRawatJalanActionPerformed

private void MnRujukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRujukActionPerformed
    if (tabMode.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
        TNoReg.requestFocus();
    } else if (TPasien.getText().trim().equals("")) {
        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
        tbPetugas.requestFocus();
    } else {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRujuk form = new DlgRujuk(null, false);
        form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        form.setLocationRelativeTo(internalFrame1);
        form.emptTeks();
        form.isCek();
        form.setNoRm(TNoRw.getText(), DTPCari1.getDate(), DTPCari2.getDate());
        form.tampil();
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }
}//GEN-LAST:event_MnRujukActionPerformed

private void MnPemberianObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPemberianObatActionPerformed
    if (tabMode.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
        TNoReg.requestFocus();
    } else if (TPasien.getText().trim().equals("")) {
        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
        tbPetugas.requestFocus();
    } else {
        if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", TNoRw.getText()) > 0) {
            JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgPemberianObat dlgrwinap = new DlgPemberianObat(null, false);
            dlgrwinap.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            dlgrwinap.setLocationRelativeTo(internalFrame1);
            dlgrwinap.isCek();
            dlgrwinap.setNoRm(TNoRw.getText(), DTPCari1.getDate(), DTPCari2.getDate(), "ralan");
            dlgrwinap.tampilPO();
            dlgrwinap.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
}//GEN-LAST:event_MnPemberianObatActionPerformed

private void MnBillingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBillingActionPerformed
    if (tabMode.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
        TNoReg.requestFocus();
    } else if (TPasien.getText().trim().equals("")) {
        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
        tbPetugas.requestFocus();
    } else {
        if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", TNoRw.getText()) > 0) {
            JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
        } else {
            try {
                pscaripiutang = koneksi.prepareStatement("select tgl_piutang from piutang_pasien where no_rkm_medis=? and status='Belum Lunas' order by tgl_piutang asc limit 1");
                try {
                    pscaripiutang.setString(1, TNoRM.getText());
                    rs = pscaripiutang.executeQuery();
                    if (rs.next()) {
                        i = JOptionPane.showConfirmDialog(null, "Masih ada tunggakan pembayaran, apa mau bayar sekarang ?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                        if (i == JOptionPane.YES_OPTION) {
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            DlgLhtPiutang piutang = new DlgLhtPiutang(null, false);
                            piutang.setNoRm(TNoRM.getText(), rs.getDate(1));
                            piutang.tampil();
                            piutang.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                            piutang.setLocationRelativeTo(internalFrame1);
                            piutang.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        } else {
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            DlgBilingRalan dlgbil = new DlgBilingRalan(null, false);
                            dlgbil.TNoRw.setText(TNoRw.getText());
                            dlgbil.isCek();
                            dlgbil.isRawat();
                            dlgbil.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                            dlgbil.setLocationRelativeTo(internalFrame1);
                            dlgbil.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        }
                    } else {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        DlgBilingRalan dlgbil = new DlgBilingRalan(null, false);
                        dlgbil.TNoRw.setText(TNoRw.getText());
                        dlgbil.isCek();
                        dlgbil.isRawat();
                        dlgbil.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                        dlgbil.setLocationRelativeTo(internalFrame1);
                        dlgbil.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                } catch (Exception e) {
                    System.out.println(e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }

                    if (pscaripiutang != null) {
                        pscaripiutang.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}//GEN-LAST:event_MnBillingActionPerformed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
    isForm();
}//GEN-LAST:event_ChkInputActionPerformed

private void MnPeriksaLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaLabActionPerformed
    if (tabMode.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
        TNoReg.requestFocus();
    } else if (TPasien.getText().trim().equals("")) {
        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
        tbPetugas.requestFocus();
    } else {
        if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", TNoRw.getText()) > 0) {
            JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgPeriksaLaboratorium dlgro = new DlgPeriksaLaboratorium(null, false);
            dlgro.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            dlgro.setLocationRelativeTo(internalFrame1);
            dlgro.emptTeks();
            dlgro.isCek();
            dlgro.setNoRm(TNoRw.getText(), "Ralan");
            dlgro.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
}//GEN-LAST:event_MnPeriksaLabActionPerformed

private void MnCetakSuratSakitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratSakitActionPerformed
    pilihan = 1;
    DlgSakit.setSize(550, 121);
    DlgSakit.setLocationRelativeTo(internalFrame1);
    DlgSakit.setVisible(true);
}//GEN-LAST:event_MnCetakSuratSakitActionPerformed

private void BtnPrint2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint2ActionPerformed
    if (TPasien.getText().trim().equals("")) {
        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
    } else {
        if (pilihan == 1) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("hari", lmsakit.getText());
            param.put("TanggalAwal", TglSakit1.getSelectedItem().toString());
            param.put("TanggalAkhir", TglSakit2.getSelectedItem().toString());
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptSuratSakit.jrxml", "report", "::[ Surat Sakit ]::",
                    "select reg_periksa.no_rawat,dokter.nm_dokter,pasien.keluarga,pasien.namakeluarga,pasien.tgl_lahir,pasien.jk,"
                    + " pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.pekerjaan,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat"
                    + " from reg_periksa inner join pasien inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten"
                    + " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_dokter=dokter.kd_dokter and pasien.kd_kel=kelurahan.kd_kel "
                    + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "
                    + "where reg_periksa.no_rawat='" + TNoRw.getText() + "' ", param);
            this.setCursor(Cursor.getDefaultCursor());
        } else if (pilihan == 2) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("hari", lmsakit.getText());
            param.put("TanggalAwal", TglSakit1.getSelectedItem().toString());
            param.put("TanggalAkhir", TglSakit2.getSelectedItem().toString());
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptSuratSakit3.jrxml", "report", "::[ Surat Sakit ]::",
                    "select reg_periksa.no_rawat,dokter.nm_dokter,pasien.keluarga,pasien.namakeluarga,pasien.tgl_lahir,pasien.jk,"
                    + " pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.pekerjaan,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat"
                    + " from reg_periksa inner join pasien inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten"
                    + " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_dokter=dokter.kd_dokter and pasien.kd_kel=kelurahan.kd_kel "
                    + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "
                    + "where reg_periksa.no_rawat='" + TNoRw.getText() + "' ", param);
            this.setCursor(Cursor.getDefaultCursor());

        } else if (pilihan == 3) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("hari", lmsakit.getText());
            param.put("TanggalAwal", TglSakit1.getSelectedItem().toString());
            param.put("TanggalAkhir", TglSakit2.getSelectedItem().toString());
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("penyakit", Sequel.cariIsi("select concat(diagnosa_pasien.kd_penyakit,' ',penyakit.nm_penyakit) from diagnosa_pasien inner join reg_periksa inner join penyakit "
                    + "on diagnosa_pasien.no_rawat=reg_periksa.no_rawat and diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "
                    + "where diagnosa_pasien.no_rawat=? and diagnosa_pasien.prioritas='1'", TNoRw.getText()));
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptSuratSakit4.jrxml", "report", "::[ Surat Sakit ]::",
                    "select reg_periksa.no_rawat,dokter.nm_dokter,pasien.keluarga,pasien.namakeluarga,pasien.tgl_lahir,pasien.jk,"
                    + " pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.pekerjaan,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat"
                    + " from reg_periksa inner join pasien inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten"
                    + " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_dokter=dokter.kd_dokter and pasien.kd_kel=kelurahan.kd_kel "
                    + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "
                    + "where reg_periksa.no_rawat='" + TNoRw.getText() + "' ", param);
            this.setCursor(Cursor.getDefaultCursor());

        }

    }
}//GEN-LAST:event_BtnPrint2ActionPerformed

private void BtnKeluar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar2ActionPerformed
    DlgSakit.dispose();
}//GEN-LAST:event_BtnKeluar2ActionPerformed

private void MnRujukMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRujukMasukActionPerformed
    if (TPasien.getText().trim().equals("")) {
        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
        tbPetugas.requestFocus();
    } else {
        rujukmasuk.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        rujukmasuk.setLocationRelativeTo(internalFrame1);
        rujukmasuk.emptTeks();
        rujukmasuk.isCek();
        rujukmasuk.setNoRm(TNoRw.getText(), DTPCari1.getDate(), DTPCari2.getDate());
        rujukmasuk.tampil();
        rujukmasuk.setVisible(true);
        //this.dispose();
    }
}//GEN-LAST:event_MnRujukMasukActionPerformed

private void kdpnjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpnjKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
        Sequel.cariIsi("select png_jawab from penjab where kd_pj=?", nmpnj, kdpnj.getText());
    } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
        TAlmt.requestFocus();
    } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        AsalRujukan.requestFocus();
    } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
        btnPenjabActionPerformed(null);
    }
}//GEN-LAST:event_kdpnjKeyPressed

private void btnPenjabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPenjabActionPerformed
    var.setform("DlgReg");
    pasien.penjab.onCari();
    pasien.penjab.isCek();
    pasien.penjab.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
    pasien.penjab.setLocationRelativeTo(internalFrame1);
    pasien.penjab.setVisible(true);
}//GEN-LAST:event_btnPenjabActionPerformed

private void MnLaporanRekapKunjunganPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanRekapKunjunganPoliActionPerformed
    if (tabMode.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
        TNoReg.requestFocus();
    } else {
        Valid.panggilUrl("billing/LaporanKunjunganPoli.php?tanggal1=" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "&tanggal2=" + Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
    }
}//GEN-LAST:event_MnLaporanRekapKunjunganPoliActionPerformed

private void MnLaporanRekapKunjunganDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanRekapKunjunganDokterActionPerformed
    if (tabMode.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
        TNoReg.requestFocus();
    } else {
        Valid.panggilUrl("billing/LaporanKunjunganDokter.php?tanggal1=" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "&tanggal2=" + Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
    }
}//GEN-LAST:event_MnLaporanRekapKunjunganDokterActionPerformed

private void MnLaporanRekapJenisBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanRekapJenisBayarActionPerformed
    if (tabMode.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
        TNoReg.requestFocus();
    } else {
        Valid.panggilUrl("billing/LaporanKunjunganJenisBayar.php?tanggal1=" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "&tanggal2=" + Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
    }
}//GEN-LAST:event_MnLaporanRekapJenisBayarActionPerformed

private void MnLaporanRekapRawatDaruratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanRekapRawatDaruratActionPerformed
    if (tabMode.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
        TNoReg.requestFocus();
    } else {
        Valid.panggilUrl("billing/LaporanRawatDarurat.php?tanggal1=" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "&tanggal2=" + Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
    }
}//GEN-LAST:event_MnLaporanRekapRawatDaruratActionPerformed

private void MnLaporanRekapKunjunganBulananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanRekapKunjunganBulananActionPerformed
    if (tabMode.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
        TNoReg.requestFocus();
    } else {
        Valid.panggilUrl("billing/LaporanKunjunganBulanan.php?tanggal1=" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "&tanggal2=" + Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
    }
}//GEN-LAST:event_MnLaporanRekapKunjunganBulananActionPerformed

private void MnLaporanRekapKunjunganBulananPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanRekapKunjunganBulananPoliActionPerformed
    if (tabMode.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
        TNoReg.requestFocus();
    } else {
        Valid.panggilUrl("billing/LaporanKunjunganBulananPoli.php?tanggal1=" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "&tanggal2=" + Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
    }
}//GEN-LAST:event_MnLaporanRekapKunjunganBulananPoliActionPerformed

    private void MnLaporanRekapPenyakitRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanRekapPenyakitRalanActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgFrekuensiPenyakitRalan ktginventaris = new DlgFrekuensiPenyakitRalan(null, false);
        ktginventaris.isCek();
        ktginventaris.setSize(this.getWidth() - 20, this.getHeight() - 20);
        ktginventaris.setLocationRelativeTo(this);
        ktginventaris.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnLaporanRekapPenyakitRalanActionPerformed

    private void MnNoResepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnNoResepActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbPetugas.requestFocus();
        } else {
            if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgResepObat resep = new DlgResepObat(null, false);
                resep.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                resep.setLocationRelativeTo(internalFrame1);
                resep.emptTeks();
                resep.isCek();
                resep.setNoRm(TNoRw.getText(), DTPCari1.getDate(), DTPCari2.getDate(), CmbJam.getSelectedItem().toString(), CmbMenit.getSelectedItem().toString(), CmbDetik.getSelectedItem().toString(), "ralan");
                resep.tampil();
                resep.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnNoResepActionPerformed

    private void MnCheckListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCheckListActionPerformed
        if (!TPasien.getText().equals("")) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("poli", TPoli.getText());
            param.put("antrian", TNoReg.getText());
            param.put("nama", TPasien.getText());
            param.put("norm", TNoRM.getText());
            param.put("bayar", nmpnj.getText());
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptCheckList.jrxml", "report", "::[ Check List ]::",
                    "select current_date() as sekarang", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnCheckListActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void MnPeriksaRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaRadiologiActionPerformed
        if (tbPetugas.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgPeriksaRadiologi dlgro = new DlgPeriksaRadiologi(null, false);
                dlgro.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                dlgro.setLocationRelativeTo(internalFrame1);
                dlgro.emptTeks();
                dlgro.isCek();
                dlgro.setNoRm(TNoRw.getText(), "Ralan");
                dlgro.tampil();
                dlgro.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPeriksaRadiologiActionPerformed

    private void MnBarcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcodeActionPerformed
        if (!TPasien.getText().equals("")) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("nama", TPasien.getText());
            param.put("alamat", Sequel.cariIsi("select date_format(tgl_lahir,'%d/%m/%Y') from pasien where no_rkm_medis=?", TNoRM.getText()));
            param.put("norm", TNoRM.getText());
            param.put("parameter", "%" + TCari.getText().trim() + "%");
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            Valid.MyReport("rptBarcodeRawat.jrxml", "report", "::[ Barcode No.Rawat ]::",
                    "select reg_periksa.no_rawat from reg_periksa where no_rawat='" + TNoRw.getText() + "'", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBarcodeActionPerformed

    private void MnCheckList1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCheckList1ActionPerformed
        if (!TPasien.getText().equals("")) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("poli", TPoli.getText());
            param.put("antrian", TNoReg.getText());
            param.put("nama", TPasien.getText());
            param.put("norm", TNoRM.getText());
            param.put("bayar", nmpnj.getText());
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptCheckList2.jrxml", "report", "::[ Check List ]::",
                    "select current_date() as sekarang", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnCheckList1ActionPerformed

    private void MnDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDiagnosaActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbPetugas.requestFocus();
        } else {
            if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Masukkan diagnosa lewat kamar inap..!!!");
            } else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgDiagnosaPenyakit resep = new DlgDiagnosaPenyakit(null, false);
                resep.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                resep.setLocationRelativeTo(internalFrame1);
                resep.isCek();
                resep.setNoRm(TNoRw.getText(), DTPCari1.getDate(), DTPCari2.getDate(), "Ralan");
                resep.panelDiagnosa1.tampil();
                resep.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnDiagnosaActionPerformed

    private void MnHemodialisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHemodialisaActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgHemodialisa resume = new DlgHemodialisa(null, false);
            resume.setNoRm(TNoRw.getText(), DTPCari1.getDate(), DTPCari2.getDate());
            resume.isCek();
            resume.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnHemodialisaActionPerformed

    private void MnLabelTrackerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLabelTrackerActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("no_rawat", TNoRw.getText());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptLabelTracker.jrxml", param, "::[ Label Tracker ]::");
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLabelTrackerActionPerformed

    private void MnCetakSuratSakit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratSakit1ActionPerformed
        DlgSakit2.setSize(550, 151);
        DlgSakit2.setLocationRelativeTo(internalFrame1);
        DlgSakit2.setVisible(true);
    }//GEN-LAST:event_MnCetakSuratSakit1ActionPerformed

    private void MnCheckList2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCheckList2ActionPerformed
        if (!TPasien.getText().equals("")) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("poli", TPoli.getText());
            param.put("antrian", TNoReg.getText());
            param.put("nama", TPasien.getText());
            param.put("norm", TNoRM.getText());
            param.put("bayar", nmpnj.getText());
            param.put("dokter", TDokter.getText());
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("alamat", TAlmt.getText());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptCheckList4.jrxml", "report", "::[ Check List ]::",
                    "select current_date() as sekarang", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnCheckList2ActionPerformed

    private void MnCheckList3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCheckList3ActionPerformed
        if (!TPasien.getText().equals("")) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("poli", TPoli.getText());
            param.put("antrian", TNoReg.getText());
            param.put("nama", TPasien.getText());
            param.put("norm", TNoRM.getText());
            param.put("dokter", TDokter.getText());
            param.put("bayar", nmpnj.getText());
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("alamat", TAlmt.getText());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptCheckList3.jrxml", "report", "::[ Check List ]::",
                    "select current_date() as sekarang", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnCheckList3ActionPerformed

    private void BtnPrint5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint5ActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("hari", lmsakit.getText());
            param.put("TanggalAwal", TglSakit1.getSelectedItem().toString());
            param.put("TanggalAkhir", TglSakit2.getSelectedItem().toString());
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("nomersurat", NomorSurat.getText());
            param.put("dokterpj", CrDokter3.getText());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptSuratSakit2.jrxml", "report", "::[ Surat Sakit ]::",
                    "select reg_periksa.no_rkm_medis,dokter.nm_dokter,pasien.keluarga,pasien.namakeluarga,pasien.tgl_lahir,pasien.jk,"
                    + " pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.pekerjaan,pasien.alamat"
                    + " from reg_periksa inner join pasien inner join dokter"
                    + " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_dokter=dokter.kd_dokter  "
                    + "where reg_periksa.no_rawat='" + TNoRw.getText() + "' ", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPrint5ActionPerformed

    private void BtnKeluar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar4ActionPerformed
        DlgSakit2.dispose();
    }//GEN-LAST:event_BtnKeluar4ActionPerformed

    private void btnPenjab1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPenjab1ActionPerformed
        var.setform("DlgReg");
        rujukmasuk.tampil2();
        rujukmasuk.TCariPerujuk.requestFocus();
        rujukmasuk.WindowPerujuk.setSize(this.getWidth() - 20, this.getHeight() - 20);
        rujukmasuk.WindowPerujuk.setLocationRelativeTo(internalFrame1);
        rujukmasuk.WindowPerujuk.setVisible(true);
    }//GEN-LAST:event_btnPenjab1ActionPerformed

    private void AsalRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsalRujukanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            kdpnj.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnSimpan.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnPenjabActionPerformed(null);
        }
    }//GEN-LAST:event_AsalRujukanKeyPressed

    private void MnLaporanRekapPerujukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanRekapPerujukActionPerformed

        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptRegPerPerujuk.jrxml", "report", "::[ Data Registrasi Per Perujuk ]::",
                    "select reg_periksa.tgl_registrasi,rujuk_masuk.perujuk,count(reg_periksa.no_rawat) as jumlah,"
                    + "sum(reg_periksa.biaya_reg) as jasarujuk "
                    + "from reg_periksa inner join rujuk_masuk on reg_periksa.no_rawat=rujuk_masuk.no_rawat "
                    + "where reg_periksa.tgl_registrasi between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' "
                    + "group by rujuk_masuk.perujuk order by count(reg_periksa.no_rawat) desc ", param);
            this.setCursor(Cursor.getDefaultCursor());
        }

    }//GEN-LAST:event_MnLaporanRekapPerujukActionPerformed

    private void MnCheckList4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCheckList4ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (!TPasien.getText().equals("")) {
            Map<String, Object> param = new HashMap<>();
            param.put("poli", TPoli.getText());
            param.put("antrian", TNoReg.getText());
            param.put("nama", TPasien.getText());
            param.put("norm", TNoRM.getText());
            param.put("dokter", TDokter.getText());
            param.put("no_rawat", TNoRw.getText());
            param.put("bayar", nmpnj.getText());
            param.put("penjab", TPngJwb.getText());
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptLembarPeriksa.jrxml", "report", "::[ Lembar Periksa ]::",
                    "select date_format(current_date(),'%d/%m/%Y') as sekarang", param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnCheckList4ActionPerformed

    private void MnCheckList5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCheckList5ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (!TPasien.getText().equals("")) {
            Map<String, Object> param = new HashMap<>();
            param.put("poli", TPoli.getText());
            param.put("antrian", TNoReg.getText());
            param.put("nama", TPasien.getText());
            param.put("norm", TNoRM.getText());
            param.put("dokter", TDokter.getText());
            param.put("no_rawat", TNoRw.getText());
            param.put("bayar", nmpnj.getText());
            param.put("penjab", TPngJwb.getText());
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptLembarPeriksa2.jrxml", "report", "::[ Lembar Periksa ]::",
                    "select date_format(current_date(),'%d/%m/%Y') as sekarang", param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnCheckList5ActionPerformed

    private void BtnSeek5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek5ActionPerformed
        var.setform("DlgReg");
        pilihan = 3;
        dokter.isCek();
        dokter.TCari.requestFocus();
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeek5ActionPerformed

    private void MnLabelTracker1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLabelTracker1ActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("no_rawat", TNoRw.getText());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptLabelTracker2.jrxml", param, "::[ Label Tracker ]::");
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLabelTracker1ActionPerformed

    private void MnCetakRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakRegisterActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            param.put("nosep", Sequel.cariIsi("select no_sep from bridging_sep where no_rawat=?",TNoRw.getText()));
            Valid.MyReport("rptBuktiRegister.jrxml", "report", "::[ Bukti Register ]::",
                    "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,pasien.no_tlp,"
                    + "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur as umur,poliklinik.nm_poli,"
                    + "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab,pasien.alamat "
                    + "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "
                    + "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='" + TNoRw.getText() + "' ", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnCetakRegisterActionPerformed

    private void DTPRegItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPRegItemStateChanged
        isNumber();
    }//GEN-LAST:event_DTPRegItemStateChanged

    private void MnPersetujuanMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPersetujuanMedisActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("persetujuantindakanmedis.jrxml", "report", "::[ Persetujuan Tindakan ]::", "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "
                    + "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"
                    + "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"
                    + "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"
                    + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "
                    + "inner join kelurahan inner join kecamatan inner join kabupaten "
                    + "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "
                    + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='" + TNoRM.getText() + "' ", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnPersetujuanMedisActionPerformed

    private void MnOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnOperasiActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgTagihanOperasi dlgro = new DlgTagihanOperasi(null, false);
            dlgro.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            dlgro.setLocationRelativeTo(internalFrame1);
            dlgro.setNoRm(TNoRw.getText(), TNoRM.getText() + ", " + TPasien.getText(), "Ralan");
            dlgro.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnOperasiActionPerformed

    private void MnGelang1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang1ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data registrasi sudah habis...!!!!");
            TCari.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("tanggal", DTPReg.getSelectedItem().toString());
            param.put("dokter", Sequel.cariIsi("select dokter.nm_dokter from dokter JOIN dpjp_ranap ON dokter.kd_dokter = dpjp_ranap.kd_dokter where dpjp_ranap.no_rawat=?", TNoRw.getText()));
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptBarcodeRM6.jrxml", "report", "::[ Gelang Pasien ]::", "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "
                    + "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"
                    + "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"
                    + "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"
                    + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "
                    + "inner join kelurahan inner join kecamatan inner join kabupaten "
                    + "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "
                    + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='" + TNoRM.getText() + "' ", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang1ActionPerformed

    private void MnCheckList6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCheckList6ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (!TPasien.getText().equals("")) {
            Map<String, Object> param = new HashMap<>();
            param.put("poli", TPoli.getText());
            param.put("antrian", TNoReg.getText());
            param.put("nama", TPasien.getText());
            param.put("norm", TNoRM.getText());
            param.put("lahir", Sequel.cariIsi("select DATE_FORMAT(tgl_lahir,'%d-%m-%Y') from pasien where no_rkm_medis=? ", TNoRM.getText()));
            param.put("dokter", TDokter.getText());
            param.put("no_rawat", TNoRw.getText());
            param.put("bayar", nmpnj.getText());
            param.put("penjab", TPngJwb.getText());
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptLembarPeriksa3.jrxml", "report", "::[ Lembar Periksa]::",
                    "select date_format(current_date(),'%d/%m/%Y') as sekarang", param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnCheckList6ActionPerformed

    private void MnCheckList7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCheckList7ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (!TPasien.getText().equals("")) {
            Map<String, Object> param = new HashMap<>();
            param.put("poli", TPoli.getText());
            param.put("antrian", TNoReg.getText());
            param.put("nama", TPasien.getText());
            param.put("norm", TNoRM.getText());
            param.put("lahir", Sequel.cariIsi("select DATE_FORMAT(tgl_lahir,'%d-%m-%Y') from pasien where no_rkm_medis=? ", TNoRM.getText()));
            param.put("dokter", TDokter.getText());
            param.put("no_rawat", TNoRw.getText());
            param.put("bayar", nmpnj.getText());
            param.put("penjab", TPngJwb.getText());
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptLembarPeriksa4.jrxml", "report", "::[ Lembar Periksa]::",
                    "select date_format(current_date(),'%d/%m/%Y') as sekarang", param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnCheckList7ActionPerformed

    private void MnHapusTagihanOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTagihanOperasiActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        } else {
            Sequel.queryu("delete from operasi where no_rawat='" + TNoRw.getText() + "'");
            Sequel.queryu("delete from beri_obat_operasi where no_rawat='" + TNoRw.getText() + "'");
        }
    }//GEN-LAST:event_MnHapusTagihanOperasiActionPerformed

    private void MnHapusObatOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusObatOperasiActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            TCari.requestFocus();
        } else {
            Sequel.queryu("delete from beri_obat_operasi where no_rawat='" + TNoRw.getText() + "'");
        }
    }//GEN-LAST:event_MnHapusObatOperasiActionPerformed

    private void MnBuktiPelayananRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBuktiPelayananRalanActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptBuktiPelayananRalan.jrxml", "report", "::[ Surat Jaminan & Bukti Pelayanan Rawat Jalan ]::",
                    "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,"
                    + "reg_periksa.jam_reg, reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli,"
                    + "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,"
                    + "reg_periksa.stts_daftar,penjab.png_jawab,pasien.no_peserta,pasien.tgl_lahir "
                    + "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "
                    + "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli "
                    + "where reg_periksa.no_rawat='" + TNoRw.getText() + "' ", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBuktiPelayananRalanActionPerformed

    private void MnSEPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSEPActionPerformed
        bukaBridging();
    }//GEN-LAST:event_MnSEPActionPerformed

    private void MnLabelTracker2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLabelTracker2ActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptLabelTracker3.jrxml", "report", "::[ Label Tracker ]::",
                    "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"
                    + "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli,"
                    + "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab "
                    + "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "
                    + "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='" + TNoRw.getText() + "' ", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLabelTracker2ActionPerformed

    private void MnLabelTracker3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLabelTracker3ActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptLabelTracker4.jrxml", "report", "::[ Label Tracker ]::",
                    "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"
                    + "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli,"
                    + "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab "
                    + "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "
                    + "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='" + TNoRw.getText() + "' ", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLabelTracker3ActionPerformed

    private void MnCetakSuratSakit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratSakit2ActionPerformed
        pilihan = 2;
        DlgSakit.setSize(550, 121);
        DlgSakit.setLocationRelativeTo(internalFrame1);
        DlgSakit.setVisible(true);
    }//GEN-LAST:event_MnCetakSuratSakit2ActionPerformed

    private void ppRiwayatBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppRiwayatBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgResumePerawatan resume = new DlgResumePerawatan(null, true);
            resume.setNoRm(TNoRM.getText(), TPasien.getText());
            resume.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppRiwayatBtnPrintActionPerformed

    private void MnCetakSuratSehatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratSehatActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptSuratSehat.jrxml", "report", "::[ Surat Keterangan Sehat ]::",
                    "select reg_periksa.no_rawat,dokter.nm_dokter,pasien.tgl_lahir,pasien.jk,"
                    + " pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.pekerjaan,pasien.alamat "
                    + " from reg_periksa inner join pasien inner join dokter "
                    + " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_dokter=dokter.kd_dokter  "
                    + "where pasien.no_rkm_medis='" + TNoRM.getText() + "' ", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnCetakSuratSehatActionPerformed

    private void MnCetakBebasNarkobaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakBebasNarkobaActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptBebasNarkoba.jrxml", "report", "::[ Surat Keterangan Bebas Narkoba ]::",
                    "select reg_periksa.no_rawat,dokter.nm_dokter,pasien.tgl_lahir,pasien.jk,"
                    + " pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.pekerjaan,pasien.alamat "
                    + " from reg_periksa inner join pasien inner join dokter "
                    + " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_dokter=dokter.kd_dokter  "
                    + "where reg_periksa.no_rawat='" + TNoRw.getText() + "' ", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnCetakBebasNarkobaActionPerformed

    private void MnSJPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSJPActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            var.setform("DlgReg");
            InhealthDataSJP dlgki = new InhealthDataSJP(null, false);
            dlgki.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            dlgki.setLocationRelativeTo(internalFrame1);
            dlgki.isCek();
            dlgki.setNoRm(TNoRw.getText(), DTPReg.getDate(), "1 RJTP RAWAT JALAN TINGKAT PERTAMA", kdpoli.getText(), TPoli.getText());
            dlgki.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnSJPActionPerformed

    private void MnLembarCasemixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLembarCasemixActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptLembarCasemix.jrxml", "report", "::[ Surat Jaminan & Bukti Pelayanan Rawat Jalan ]::",
                    "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,"
                    + "reg_periksa.jam_reg, reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli,"
                    + "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,"
                    + "reg_periksa.stts_daftar,penjab.png_jawab,pasien.no_peserta,pasien.tgl_lahir "
                    + "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "
                    + "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli "
                    + "where reg_periksa.no_rawat='" + TNoRw.getText() + "' ", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLembarCasemixActionPerformed

    private void ppCatatanPasienBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppCatatanPasienBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            catatan.setNoRm(TNoRM.getText());
            catatan.setSize(720, 330);
            catatan.setLocationRelativeTo(internalFrame1);
            catatan.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppCatatanPasienBtnPrintActionPerformed

    private void MnSPBKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSPBKActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptSBPK.jrxml", "report", "::[ Surat Bukti Pelayanan Kesehatan ]::",
                    "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,"
                    + "reg_periksa.jam_reg, reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli,"
                    + "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,"
                    + "reg_periksa.stts_daftar,penjab.png_jawab,pasien.no_peserta,pasien.tgl_lahir "
                    + "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "
                    + "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli "
                    + "where reg_periksa.no_rawat='" + TNoRw.getText() + "' ", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnSPBKActionPerformed

    private void MnResepDOkterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnResepDOkterActionPerformed
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbPetugas.requestFocus();
        } else {
            if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                DlgPeresepanDokter resep = new DlgPeresepanDokter(null, false);
                resep.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                resep.setLocationRelativeTo(internalFrame1);
                resep.setNoRm(TNoRw.getText(), DTPCari1.getDate(), CmbJam.getSelectedItem().toString(), CmbMenit.getSelectedItem().toString(), CmbDetik.getSelectedItem().toString(),
                        kddokter.getText(), TDokter.getText(), "ralan");
                resep.isCek();
                resep.tampilobat();
                resep.setVisible(true);
            }
        }
    }//GEN-LAST:event_MnResepDOkterActionPerformed

    private void ppBerkasBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBerkasBtnPrintActionPerformed
        setStatusPasien("Diterima","");
    }//GEN-LAST:event_ppBerkasBtnPrintActionPerformed

    private void MnSudahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSudahActionPerformed
        setStatusPasien("Sudah","");
    }//GEN-LAST:event_MnSudahActionPerformed

    private void MnBelumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBelumActionPerformed
        if (TNoRw.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
        } else {
            if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                Valid.editTable(tabMode, "reg_periksa", "no_rawat", TNoRw, "stts='Belum'");
                if (tabMode.getRowCount() != 0) {
                    tampil();
                }
            }

        }
    }//GEN-LAST:event_MnBelumActionPerformed

    private void MnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBatalActionPerformed
        if (TNoRw.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
        } else {
            if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                int jawab = JOptionPane.showConfirmDialog(null, "Apa anda mau membatalkan pemeriksaan pasien ini ?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (jawab == JOptionPane.YES_OPTION) {
                    Valid.editTable(tabMode, "reg_periksa", "no_rawat", TNoRw, "stts='Batal',biaya_reg='0'");
                    if (tabMode.getRowCount() != 0) {
                        tampil();
                    }
                    tampil();
                } else {
                    tampil();
                }
            }
        }
    }//GEN-LAST:event_MnBatalActionPerformed

    private void MnDirujukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDirujukActionPerformed
        if (TNoRw.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
        } else {
            if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                Valid.editTable(tabMode, "reg_periksa", "no_rawat", TNoRw, "stts='Dirujuk'");
                MnRujukActionPerformed(evt);
                if (tabMode.getRowCount() != 0) {
                    tampil();
                }
            }

        }
    }//GEN-LAST:event_MnDirujukActionPerformed

    private void MnDIrawatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDIrawatActionPerformed
        if (TNoRw.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
        } else {
            if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                Valid.editTable(tabMode, "reg_periksa", "no_rawat", TNoRw, "stts='Dirawat'");
                MnKamarInapActionPerformed(evt);
                if (tabMode.getRowCount() != 0) {
                    tampil();
                }
            }

        }
    }//GEN-LAST:event_MnDIrawatActionPerformed

    private void MnMeninggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnMeninggalActionPerformed
        if (TNoRw.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
        } else {
            if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                Valid.editTable(tabMode, "reg_periksa", "no_rawat", TNoRw, "stts='Meninggal'");
                DlgPasienMati dlgPasienMati = new DlgPasienMati(null, false);
                dlgPasienMati.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                dlgPasienMati.setLocationRelativeTo(internalFrame1);
                dlgPasienMati.emptTeks();
                dlgPasienMati.setNoRm(TNoRM.getText());
                dlgPasienMati.isCek();
                dlgPasienMati.setVisible(true);
                if (tabMode.getRowCount() != 0) {
                    tampil();
                }
            }

        }
    }//GEN-LAST:event_MnMeninggalActionPerformed

    private void MnUrutNoRawatDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutNoRawatDescActionPerformed
        order = "reg_periksa.no_rawat desc";
        tampil();
    }//GEN-LAST:event_MnUrutNoRawatDescActionPerformed

    private void MnUrutNoRawatAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutNoRawatAscActionPerformed
        order = "reg_periksa.no_rawat asc";
        tampil();
    }//GEN-LAST:event_MnUrutNoRawatAscActionPerformed

    private void MnUrutTanggalDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutTanggalDescActionPerformed
        order = "reg_periksa.tgl_registrasi,reg_periksa.jam_reg desc";
        tampil();
    }//GEN-LAST:event_MnUrutTanggalDescActionPerformed

    private void MnUrutTanggalAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutTanggalAscActionPerformed
        order = "reg_periksa.tgl_registrasi,reg_periksa.jam_reg asc";
        tampil();
    }//GEN-LAST:event_MnUrutTanggalAscActionPerformed

    private void MnUrutDokterDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutDokterDescActionPerformed
        order = "dokter.nm_dokter desc";
        tampil();
    }//GEN-LAST:event_MnUrutDokterDescActionPerformed

    private void MnUrutDokterAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutDokterAscActionPerformed
        order = "dokter.nm_dokter asc";
        tampil();
    }//GEN-LAST:event_MnUrutDokterAscActionPerformed

    private void MnUrutPoliklinikDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutPoliklinikDescActionPerformed
        order = "poliklinik.nm_poli desc";
        tampil();
    }//GEN-LAST:event_MnUrutPoliklinikDescActionPerformed

    private void MnUrutPoliklinikAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutPoliklinikAscActionPerformed
        order = "poliklinik.nm_poli asc";
        tampil();
    }//GEN-LAST:event_MnUrutPoliklinikAscActionPerformed

    private void MnUrutPenjabDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutPenjabDescActionPerformed
        order = "penjab.png_jawab desc";
        tampil();
    }//GEN-LAST:event_MnUrutPenjabDescActionPerformed

    private void MnUrutPenjabAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutPenjabAscActionPerformed
        order = "penjab.png_jawab asc";
        tampil();
    }//GEN-LAST:event_MnUrutPenjabAscActionPerformed

    private void MnUrutStatusDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutStatusDescActionPerformed
        order = "reg_periksa.stts desc";
        tampil();
    }//GEN-LAST:event_MnUrutStatusDescActionPerformed

    private void MnUrutStatusAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutStatusAscActionPerformed
        order = "reg_periksa.stts asc";
        tampil();
    }//GEN-LAST:event_MnUrutStatusAscActionPerformed

    private void MnCetakSuratSakit3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratSakit3ActionPerformed
        pilihan = 3;
        DlgSakit.setSize(550, 121);
        DlgSakit.setLocationRelativeTo(internalFrame1);
        DlgSakit.setVisible(true);
    }//GEN-LAST:event_MnCetakSuratSakit3ActionPerformed

    private void MnJKRAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnJKRAActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("perujuk", Sequel.cariIsi("select perujuk from rujuk_masuk where no_rawat=?", TNoRw.getText()));
            param.put("no_rawat", TNoRw.getText());
            param.put("petugas", Sequel.cariIsi("select nama from petugas where nip=?", var.getkode()));
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptJkra.jrxml", param, "::[ SURAT JAMINAN KESEHATAN NASIONAL (JKRA) RAWAT JALAN ]::");
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnJKRAActionPerformed

    private void MnPoliInternalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPoliInternalActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            dlgrjk.setLocationRelativeTo(internalFrame1);
            dlgrjk.isCek();
            dlgrjk.setNoRm(TNoRw.getText(), TNoRM.getText(), TPasien.getText(), internalFrame1.getWidth(), internalFrame1.getHeight());
            //dlgrjk.setNoRm2(TNoRw.getText(),TNoRM.getText(),TPasien.getText()); 
            dlgrjk.cekRujukanInternal(TNoRw.getText());
            dlgrjk.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnPoliInternalActionPerformed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if (TabRawat.getSelectedIndex() == 0) {
            tampil();
        } else if (TabRawat.getSelectedIndex() == 1) {
            tampil2();
        } else if (TabRawat.getSelectedIndex() == 2) {
            tampil3();
        }


    }//GEN-LAST:event_TabRawatMouseClicked

    private void tbPetugas2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPetugas2MouseClicked
        TPasien.setText(tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 7).toString());
        if (tabMode2.getRowCount() != 0) {
            if (evt.getClickCount() == 1) {
                i = tbPetugas2.getSelectedColumn();
                if (i == 7) {
                    if (validasicatatan.equals("Yes")) {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        LabelCatatan.setText(Sequel.cariIsi("select catatan from catatan_pasien where no_rkm_medis=?", tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 6).toString()));
                        if (!LabelCatatan.getText().equals("")) {
                            DlgCatatan.setLocationRelativeTo(TabRawat);
                            DlgCatatan.setVisible(true);
                        } else {
                            DlgCatatan.setVisible(false);
                        }
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                }
            } else if (evt.getClickCount() == 2) {
                i = tbPetugas2.getSelectedColumn();
                if (i == 1) {
                    if (MnKamarInap1.isEnabled() == true) {
                        MnKamarInap1ActionPerformed(null);
                    }
                } else if (i == 2) {
                    if (var.gettindakan_ralan() == true) {
                        MnRawatJalan1ActionPerformed(null);
                    }
                } else if (i == 3) {
                    if (var.getberi_obat() == true) {
                        MnPemberianObat1ActionPerformed(null);
                    }
                } else if (i == 4) {
                    if (var.getperiksa_lab() == true) {
                        MnPeriksaLab1ActionPerformed(null);
                    }
                }

            }

        }
    }//GEN-LAST:event_tbPetugas2MouseClicked

    private void tbPetugas2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPetugas2KeyPressed
        if (tabMode2.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
                i = tbPetugas2.getSelectedColumn();
                if (i == 1) {
                    if (MnKamarInap1.isEnabled() == true) {
                        MnKamarInap1ActionPerformed(null);
                    }
                } else if (i == 2) {
                    if (var.gettindakan_ralan() == true) {
                        MnRawatJalan1ActionPerformed(null);
                    }
                } else if (i == 3) {
                    if (var.getberi_obat() == true) {
                        MnPemberianObat1ActionPerformed(null);
                    }
                } else if (i == 4) {
                    if (var.getperiksa_lab() == true) {
                        MnPeriksaLab1ActionPerformed(null);
                    }
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_A) {
                for (i = 0; i < tbPetugas2.getRowCount(); i++) {
                    tbPetugas2.setValueAt(true, i, 0);
                }
            }
        }
    }//GEN-LAST:event_tbPetugas2KeyPressed

    private void MnKamarInap1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKamarInap1ActionPerformed
        if (tabMode2.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else {
            if (tbPetugas2.getSelectedRow() != -1) {
                if (Sequel.cariRegistrasi(tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString()) > 0) {
                    JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi..!!");
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    var.setstatus(true);
                    DlgKamarInap dlgki = new DlgKamarInap(null, false);
                    dlgki.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    dlgki.setLocationRelativeTo(internalFrame1);
                    dlgki.emptTeks();
                    dlgki.isCek();
                    dlgki.setNoRm(tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString());
                    dlgki.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }//GEN-LAST:event_MnKamarInap1ActionPerformed

    private void MnHapusRujukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusRujukanActionPerformed
        for (i = 0; i < tbPetugas2.getRowCount(); i++) {
            if (tbPetugas2.getValueAt(i, 0).toString().equals("true")) {
                Sequel.queryu2("delete from rujukan_internal_poli where no_rawat=? and kd_dokter=?", 2, new String[]{
                    tbPetugas2.getValueAt(i, 1).toString(), tbPetugas2.getValueAt(i, 4).toString()
                });
                //hapus ditabel detail interal poli
                Sequel.queryu2("delete from rujukan_internal_poli_detail where no_rawat=? ", 1, new String[]{
                    tbPetugas2.getValueAt(i, 1).toString()
                });
            }
        }
        tampil2();
    }//GEN-LAST:event_MnHapusRujukanActionPerformed

    private void MnRawatJalan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRawatJalan1ActionPerformed
        if (tabMode2.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else {
            if (tbPetugas2.getSelectedRow() != -1) {
                if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString()) > 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgRawatJalan dlgrwjl = new DlgRawatJalan(null, false);
                    dlgrwjl.isCek();
                    dlgrwjl.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    dlgrwjl.setLocationRelativeTo(internalFrame1);
                    dlgrwjl.SetPoli(tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 18).toString());
                    dlgrwjl.SetPj(tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 19).toString());
                    dlgrwjl.setNoRm(tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString(),
                            DTPCari1.getDate(), DTPCari2.getDate(),
                            tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 4).toString(),
                            tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 5).toString()
                    );
                    dlgrwjl.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }//GEN-LAST:event_MnRawatJalan1ActionPerformed

    private void MnPeriksaLab1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaLab1ActionPerformed
        if (tabMode2.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else {
            if (tbPetugas2.getSelectedRow() != -1) {
                if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString()) > 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgPeriksaLaboratorium dlgro = new DlgPeriksaLaboratorium(null, false);
                    dlgro.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    dlgro.setLocationRelativeTo(internalFrame1);
                    dlgro.emptTeks();
                    dlgro.isCek();
                    dlgro.setNoRm(tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString(), "Ralan");
                    dlgro.setDokterPerujuk(
                            tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 4).toString(),
                            tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 5).toString()
                    );
                    dlgro.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }//GEN-LAST:event_MnPeriksaLab1ActionPerformed

    private void MnPeriksaRadiologi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaRadiologi1ActionPerformed
        if (tabMode2.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else {
            if (tbPetugas2.getSelectedRow() != -1) {
                if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString()) > 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgPeriksaRadiologi dlgro = new DlgPeriksaRadiologi(null, false);
                    dlgro.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    dlgro.setLocationRelativeTo(internalFrame1);
                    dlgro.emptTeks();
                    dlgro.isCek();
                    dlgro.setNoRm(tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString(), "Ralan");
                    dlgro.setDokterPerujuk(
                            tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 4).toString(),
                            tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 5).toString()
                    );
                    dlgro.tampil();
                    dlgro.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }//GEN-LAST:event_MnPeriksaRadiologi1ActionPerformed

    private void MnOperasi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnOperasi1ActionPerformed
        if (tabMode2.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else {
            if (tbPetugas2.getSelectedRow() != -1) {
                if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString()) > 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgTagihanOperasi dlgro = new DlgTagihanOperasi(null, false);
                    dlgro.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    dlgro.setLocationRelativeTo(internalFrame1);
                    dlgro.setNoRm(tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString(), tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 6).toString() + ", " + tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 7).toString(), "Ralan");
                    dlgro.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }//GEN-LAST:event_MnOperasi1ActionPerformed

    private void MnPemberianObat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPemberianObat1ActionPerformed
        if (tabMode2.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else {
            if (tbPetugas2.getSelectedRow() != -1) {
                if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString()) > 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgPemberianObat dlgrwinap = new DlgPemberianObat(null, false);
                    dlgrwinap.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    dlgrwinap.setLocationRelativeTo(internalFrame1);
                    dlgrwinap.isCek();
                    dlgrwinap.setNoRm(tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString(), DTPCari1.getDate(), DTPCari2.getDate(), "ralan");
                    dlgrwinap.setDokter(
                            tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 4).toString(),
                            tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 5).toString()
                    );
                    dlgrwinap.tampilPO();
                    dlgrwinap.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }//GEN-LAST:event_MnPemberianObat1ActionPerformed

    private void MnResepDOkter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnResepDOkter1ActionPerformed
        if (tabMode2.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else {
            if (tbPetugas2.getSelectedRow() != -1) {
                if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString()) > 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgPeresepanDokter resep = new DlgPeresepanDokter(null, false);
                    resep.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    resep.setLocationRelativeTo(internalFrame1);
                    resep.setNoRm(tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString(),
                            DTPCari1.getDate(), CmbJam.getSelectedItem().toString(), CmbMenit.getSelectedItem().toString(), CmbDetik.getSelectedItem().toString(),
                            tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 4).toString(),
                            tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 5).toString(), "ralan"
                    );
                    resep.isCek();
                    resep.tampilobat();
                    resep.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }//GEN-LAST:event_MnResepDOkter1ActionPerformed

    private void MnNoResep1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnNoResep1ActionPerformed
        if (tabMode2.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else {
            if (tbPetugas2.getSelectedRow() != -1) {
                if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString()) > 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgResepObat resep = new DlgResepObat(null, false);
                    resep.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    resep.setLocationRelativeTo(internalFrame1);
                    resep.emptTeks();
                    resep.isCek();
                    resep.setNoRm(tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString(),
                            DTPCari1.getDate(), DTPCari2.getDate(), CmbJam.getSelectedItem().toString(),
                            CmbMenit.getSelectedItem().toString(), CmbDetik.getSelectedItem().toString(),
                            tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 4).toString(),
                            tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 5).toString(), "ralan"
                    );
                    resep.tampil();
                    resep.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }//GEN-LAST:event_MnNoResep1ActionPerformed

    private void MnBilling1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBilling1ActionPerformed
        if (tabMode2.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else {
            if (tbPetugas2.getSelectedRow() != -1) {
                if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString()) > 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                } else {
                    try {
                        pscaripiutang = koneksi.prepareStatement("select tgl_piutang from piutang_pasien where no_rkm_medis=? and status='Belum Lunas' order by tgl_piutang asc limit 1");
                        try {
                            pscaripiutang.setString(1, tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 6).toString());
                            rs = pscaripiutang.executeQuery();
                            if (rs.next()) {
                                i = JOptionPane.showConfirmDialog(null, "Masih ada tunggakan pembayaran, apa mau bayar sekarang ?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                                if (i == JOptionPane.YES_OPTION) {
                                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                    DlgLhtPiutang piutang = new DlgLhtPiutang(null, false);
                                    piutang.setNoRm(tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 6).toString(), rs.getDate(1));
                                    piutang.tampil();
                                    piutang.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                    piutang.setLocationRelativeTo(internalFrame1);
                                    piutang.setVisible(true);
                                    this.setCursor(Cursor.getDefaultCursor());
                                } else {
                                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                    DlgBilingRalan dlgbil = new DlgBilingRalan(null, false);
                                    dlgbil.TNoRw.setText(tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString());
                                    dlgbil.isCek();
                                    dlgbil.isRawat();
                                    dlgbil.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                    dlgbil.setLocationRelativeTo(internalFrame1);
                                    dlgbil.setVisible(true);
                                    this.setCursor(Cursor.getDefaultCursor());
                                }
                            } else {
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                DlgBilingRalan dlgbil = new DlgBilingRalan(null, false);
                                dlgbil.TNoRw.setText(tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString());
                                dlgbil.isCek();
                                dlgbil.isRawat();
                                dlgbil.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                dlgbil.setLocationRelativeTo(internalFrame1);
                                dlgbil.setVisible(true);
                                this.setCursor(Cursor.getDefaultCursor());
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        } finally {
                            if (rs != null) {
                                rs.close();
                            }

                            if (pscaripiutang != null) {
                                pscaripiutang.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        }
    }//GEN-LAST:event_MnBilling1ActionPerformed

    private void MnDiagnosa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDiagnosa1ActionPerformed
        if (tabMode2.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else {
            if (tbPetugas2.getSelectedRow() != -1) {
                if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString()) > 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgDiagnosaPenyakit resep = new DlgDiagnosaPenyakit(null, false);
                    resep.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    resep.setLocationRelativeTo(internalFrame1);
                    resep.isCek();
                    resep.setNoRm(tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString(),
                            DTPCari1.getDate(), DTPCari2.getDate(), "Ralan"
                    );
                    resep.panelDiagnosa1.tampil();
                    resep.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }//GEN-LAST:event_MnDiagnosa1ActionPerformed

    private void ppRiwayat1BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppRiwayat1BtnPrintActionPerformed
        if (tabMode2.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else {
            if (tbPetugas2.getSelectedRow() != -1) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgResumePerawatan resume = new DlgResumePerawatan(null, true);
                resume.setNoRm(tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 6).toString(),
                        tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 7).toString()
                );
                resume.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                resume.setLocationRelativeTo(internalFrame1);
                resume.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_ppRiwayat1BtnPrintActionPerformed

    private void MnUrutNoRawatDesc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutNoRawatDesc1ActionPerformed
        order = "reg_periksa.no_rawat desc";
        tampil2();
    }//GEN-LAST:event_MnUrutNoRawatDesc1ActionPerformed

    private void MnUrutNoRawatAsc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutNoRawatAsc1ActionPerformed
        order = "reg_periksa.no_rawat asc";
        tampil2();
    }//GEN-LAST:event_MnUrutNoRawatAsc1ActionPerformed

    private void MnUrutTanggalDesc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutTanggalDesc1ActionPerformed
        order = "reg_periksa.tgl_registrasi,reg_periksa.jam_reg desc";
        tampil2();
    }//GEN-LAST:event_MnUrutTanggalDesc1ActionPerformed

    private void MnUrutTanggalAsc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutTanggalAsc1ActionPerformed
        order = "reg_periksa.tgl_registrasi,reg_periksa.jam_reg asc";
        tampil2();
    }//GEN-LAST:event_MnUrutTanggalAsc1ActionPerformed

    private void MnUrutDokterDesc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutDokterDesc1ActionPerformed
        order = "dokter.nm_dokter desc";
        tampil2();
    }//GEN-LAST:event_MnUrutDokterDesc1ActionPerformed

    private void MnUrutDokterAsc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutDokterAsc1ActionPerformed
        order = "dokter.nm_dokter asc";
        tampil2();
    }//GEN-LAST:event_MnUrutDokterAsc1ActionPerformed

    private void MnUrutPoliklinikDesc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutPoliklinikDesc1ActionPerformed
        order = "poliklinik.nm_poli desc";
        tampil2();
    }//GEN-LAST:event_MnUrutPoliklinikDesc1ActionPerformed

    private void MnUrutPoliklinikAsc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutPoliklinikAsc1ActionPerformed
        order = "poliklinik.nm_poli asc";
        tampil2();
    }//GEN-LAST:event_MnUrutPoliklinikAsc1ActionPerformed

    private void MnUrutPenjabDesc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutPenjabDesc1ActionPerformed
        order = "penjab.png_jawab desc";
        tampil2();
    }//GEN-LAST:event_MnUrutPenjabDesc1ActionPerformed

    private void MnUrutPenjabAsc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutPenjabAsc1ActionPerformed
        order = "penjab.png_jawab asc";
        tampil2();
    }//GEN-LAST:event_MnUrutPenjabAsc1ActionPerformed

    private void MnUrutStatusDesc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutStatusDesc1ActionPerformed
        order = "reg_periksa.stts desc";
        tampil2();
    }//GEN-LAST:event_MnUrutStatusDesc1ActionPerformed

    private void MnUrutStatusAsc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutStatusAsc1ActionPerformed
        order = "reg_periksa.stts asc";
        tampil2();
    }//GEN-LAST:event_MnUrutStatusAsc1ActionPerformed

    private void MnCetakSuratSehat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratSehat1ActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("norawat", TNoRw.getText());
            param.put("bb", Sequel.cariIsi("select berat from pemeriksaan_ralan where no_rawat=?", TNoRw.getText()));
            param.put("td", Sequel.cariIsi("select tensi from pemeriksaan_ralan where no_rawat=?", TNoRw.getText()));
            param.put("tb", Sequel.cariIsi("select tinggi from pemeriksaan_ralan where no_rawat=?", TNoRw.getText()));
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptSuratSehat2.jrxml", param, "::[ Surat Keterangan Sehat ]::");
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnCetakSuratSehat1ActionPerformed

    private void MnUrutRegDesc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutRegDesc1ActionPerformed
        order = "reg_periksa.no_reg desc";
        tampil();
    }//GEN-LAST:event_MnUrutRegDesc1ActionPerformed

    private void MnUrutRegAsc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUrutRegAsc1ActionPerformed
        order = "reg_periksa.no_reg asc";
        tampil();
    }//GEN-LAST:event_MnUrutRegAsc1ActionPerformed

    private void LabelCatatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LabelCatatanMouseClicked
        DlgCatatan.dispose();
    }//GEN-LAST:event_LabelCatatanMouseClicked

    private void internalFrame6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_internalFrame6MouseClicked
        DlgCatatan.dispose();
    }//GEN-LAST:event_internalFrame6MouseClicked

    private void MnLembarCasemix1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLembarCasemix1ActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptLembarCasemix2.jrxml", "report", "::[ Surat Jaminan & Bukti Pelayanan Rawat Jalan ]::",
                    "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,"
                    + "reg_periksa.jam_reg, reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli,"
                    + "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,"
                    + "reg_periksa.stts_daftar,penjab.png_jawab,pasien.no_peserta,pasien.tgl_lahir "
                    + "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "
                    + "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli "
                    + "where reg_periksa.no_rawat='" + TNoRw.getText() + "' ", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLembarCasemix1ActionPerformed

    private void MnBarcode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcode1ActionPerformed
        if (!TPasien.getText().equals("")) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("nama", TPasien.getText());
            param.put("alamat", Sequel.cariIsi("select date_format(tgl_lahir,'%d/%m/%Y') from pasien where no_rkm_medis=?", TNoRM.getText()));
            param.put("norm", TNoRM.getText());
            param.put("parameter", "%" + TCari.getText().trim() + "%");
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            Valid.MyReport("rptBarcodeRawat2.jrxml", "report", "::[ Barcode No.Rawat ]::",
                    "select reg_periksa.no_rawat from reg_periksa where no_rawat='" + TNoRw.getText() + "'", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBarcode1ActionPerformed

    private void MnLembarCasemix2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLembarCasemix2ActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptLembarCasemix3.jrxml", "report", "::[ Surat Jaminan & Bukti Pelayanan Rawat Jalan ]::",
                    "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,"
                    + "reg_periksa.jam_reg, reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli,"
                    + "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,"
                    + "reg_periksa.stts_daftar,penjab.png_jawab,pasien.no_peserta,pasien.tgl_lahir "
                    + "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "
                    + "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli "
                    + "where reg_periksa.no_rawat='" + TNoRw.getText() + "' ", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLembarCasemix2ActionPerformed

    private void MnBarcodeRM9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcodeRM9ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data pasien sudah habis...!!!!");
            TNoRM.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptBarcodeRM18.jrxml", "report", "::[ Label Rekam Medis ]::", "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "
                    + "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"
                    + "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"
                    + "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"
                    + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "
                    + "inner join kelurahan inner join kecamatan inner join kabupaten "
                    + "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "
                    + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='" + TNoRM.getText() + "' ", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBarcodeRM9ActionPerformed

    private void MnLembarRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLembarRalanActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data pasien sudah habis...!!!!");
            TNoRM.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptRM11.jrxml", "report", "::[ Lembar Rawat Jalan ]::",
                    "SELECT reg_periksa.tgl_registrasi, reg_periksa.jam_reg, "
                    + "poliklinik.nm_poli, pasien.no_rkm_medis, pasien.nm_pasien, "
                    + "pasien.no_ktp, pasien.jk, pasien.tmp_lahir, pasien.tgl_lahir,"
                    + "pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel"
                    + ",', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) AS alamat,"
                    + "pasien.gol_darah,pasien.pekerjaan,pasien.stts_nikah,"
                    + "pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"
                    + "pasien.pnd,pasien.keluarga,pasien.namakeluarga,"
                    + "penjab.png_jawab,pasien.pekerjaanpj,concat(pasien.alamatpj,"
                    + "', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',"
                    + "pasien.kabupatenpj) AS alamatpj FROM pasien INNER JOIN "
                    + "kelurahan INNER JOIN kecamatan INNER JOIN kabupaten "
                    + "INNER JOIN penjab ON pasien.kd_kel = kelurahan.kd_kel "
                    + "AND pasien.kd_kec = kecamatan.kd_kec AND pasien.kd_kab = kabupaten.kd_kab "
                    + "INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis "
                    + "AND reg_periksa.kd_pj = penjab.kd_pj INNER JOIN poliklinik "
                    + "ON poliklinik.kd_poli = reg_periksa.kd_poli WHERE "
                    + "reg_periksa.no_rawat ='" + TNoRw.getText() + "' ", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLembarRalanActionPerformed

    private void MnSPBK1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSPBK1ActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            param.put("logo2", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptSBPK2.jrxml", "report", "::[ Surat Bukti Pelayanan Kesehatan ]::",
                    "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,"
                    + "reg_periksa.jam_reg, reg_periksa.kd_dokter,dokter.nm_dokter,"
                    + "reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,"
                    + "pasien.no_tlp,poliklinik.nm_poli,reg_periksa.p_jawab,"
                    + "reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,"
                    + "reg_periksa.stts_daftar,penjab.png_jawab,pasien.no_peserta,"
                    + "pasien.tgl_lahir from reg_periksa inner join dokter inner join "
                    + "pasien inner join poliklinik inner join penjab on "
                    + "reg_periksa.kd_dokter=dokter.kd_dokter and "
                    + "reg_periksa.no_rkm_medis=pasien.no_rkm_medis and "
                    + "reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli "
                    + "where reg_periksa.no_rawat='" + TNoRw.getText() + "' ", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnSPBK1ActionPerformed

    private void ppBerkasDigitalBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBerkasDigitalBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgBerkasRawat berkas = new DlgBerkasRawat(null, true);
            berkas.setJudul("::[ Berkas Digital Perawatan ]::", "berkasrawat/pages");
            try {
                berkas.loadURL("http://" + koneksiDB.HOST() + ":" + prop.getProperty("PORTWEB") + "/" + prop.getProperty("HYBRIDWEB") + "/" + "berkasrawat/login2.php?act=login&usere=admin&passwordte=akusayangsamakamu&no_rawat=" + TNoRw.getText());
            } catch (Exception ex) {
                System.out.println("Notifikasi : " + ex);
            }

            berkas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            berkas.setLocationRelativeTo(internalFrame1);
            berkas.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppBerkasDigitalBtnPrintActionPerformed

    private void ppBerkasDigital1BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBerkasDigital1BtnPrintActionPerformed
        if (tabMode2.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else {
            if (tbPetugas2.getSelectedRow() != -1) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgBerkasRawat berkas = new DlgBerkasRawat(null, true);
                berkas.setJudul("::[ Berkas Digital Perawatan ]::", "berkasrawat/pages");
                try {
                    berkas.loadURL("http://" + koneksiDB.HOST() + ":" + prop.getProperty("PORTWEB") + "/" + prop.getProperty("HYBRIDWEB") + "/" + "berkasrawat/login2.php?act=login&usere=admin&passwordte=akusayangsamakamu&no_rawat=" + tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString());
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                }

                berkas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                berkas.setLocationRelativeTo(internalFrame1);
                berkas.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_ppBerkasDigital1BtnPrintActionPerformed

    private void MnStatusBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnStatusBaruActionPerformed
        if (TNoRw.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
        } else {
            Valid.editTable(tabMode, "reg_periksa", "no_rawat", TNoRw, "status_poli='Baru'");
            if (tabMode.getRowCount() != 0) {
                tampil();
            }
        }
    }//GEN-LAST:event_MnStatusBaruActionPerformed

    private void MnStatusLamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnStatusLamaActionPerformed
        if (TNoRw.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
        } else {
            Valid.editTable(tabMode, "reg_periksa", "no_rawat", TNoRw, "status_poli='Lama'");
            if (tabMode.getRowCount() != 0) {
                tampil();
            }
        }
    }//GEN-LAST:event_MnStatusLamaActionPerformed

    private void ppIKPBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppIKPBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgDataInsidenKeselamatan aplikasi = new DlgDataInsidenKeselamatan(null, false);
            aplikasi.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            aplikasi.setLocationRelativeTo(internalFrame1);
            aplikasi.isCek();
            aplikasi.setNoRm(TNoRw.getText(), DTPCari1.getDate(), DTPCari2.getDate(), TPoli.getText());
            aplikasi.tampil();
            aplikasi.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppIKPBtnPrintActionPerformed

    private void ppIKP1BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppIKP1BtnPrintActionPerformed
        if (tabMode2.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else {
            if (tbPetugas2.getSelectedRow() != -1) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgDataInsidenKeselamatan aplikasi = new DlgDataInsidenKeselamatan(null, false);
                aplikasi.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                aplikasi.setLocationRelativeTo(internalFrame1);
                aplikasi.isCek();
                aplikasi.setNoRm(tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString(), DTPCari1.getDate(), DTPCari2.getDate(), tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 10).toString());
                aplikasi.tampil();
                aplikasi.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_ppIKP1BtnPrintActionPerformed

    private void MnJadwalOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnJadwalOperasiActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                DlgBookingOperasi form = new DlgBookingOperasi(null, false);
                form.isCek();
                form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                form.setLocationRelativeTo(internalFrame1);
                form.setNoRm(TNoRw.getText(), TNoRM.getText(), TPasien.getText(), TPoli.getText(), "Ralan");
                form.setVisible(true);
            }
        }
    }//GEN-LAST:event_MnJadwalOperasiActionPerformed

    private void MnSKDPBPJSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSKDPBPJSActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                DlgSKDPBPJS form = new DlgSKDPBPJS(null, false);
                form.isCek();
                form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                form.setLocationRelativeTo(internalFrame1);
                form.emptTeks();
                form.setNoRm(TNoRM.getText(), TPasien.getText(), kdpoli.getText(), TPoli.getText(), kddokter.getText(), TDokter.getText(), TNoRw.getText(), "Ralan");
                form.setVisible(true);
            }
        }
    }//GEN-LAST:event_MnSKDPBPJSActionPerformed

    private void MnPermintaanLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPermintaanLabActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgPermintaanLaboratorium dlgro = new DlgPermintaanLaboratorium(null, false);
                dlgro.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                dlgro.setLocationRelativeTo(internalFrame1);
                dlgro.emptTeks();
                dlgro.isCek();
                dlgro.setNoRm(TNoRw.getText(), "Ralan");
                dlgro.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPermintaanLabActionPerformed

    private void MnPermintaanRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPermintaanRadiologiActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgPermintaanRadiologi dlgro = new DlgPermintaanRadiologi(null, false);
                dlgro.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                dlgro.setLocationRelativeTo(internalFrame1);
                dlgro.emptTeks();
                dlgro.isCek();
                dlgro.setNoRm(TNoRw.getText(), "Ralan");
                dlgro.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPermintaanRadiologiActionPerformed

    private void MnPulangPaksaBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPulangPaksaBtnPrintActionPerformed
        if (TNoRw.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
        } else {
            if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                Valid.editTable(tabMode, "reg_periksa", "no_rawat", TNoRw, "stts='Pulang Paksa'");
                if (tabMode.getRowCount() != 0) {
                    tampil();
                }
            }

        }
    }//GEN-LAST:event_MnPulangPaksaBtnPrintActionPerformed

    private void MnJadwalOperasi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnJadwalOperasi1ActionPerformed
        if (tabMode2.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbPetugas2.getSelectedRow() > -1) {
                if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString()) > 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgBookingOperasi form = new DlgBookingOperasi(null, false);
                    form.isCek();
                    form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    form.setLocationRelativeTo(internalFrame1);
                    form.setNoRm(tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString(), tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 5).toString(), tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 6).toString(), tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 10).toString(), "Ralan");
                    form.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Maaf, Pilih pasien terlebih dahulu..!!!");
            }
        }
    }//GEN-LAST:event_MnJadwalOperasi1ActionPerformed

    private void MnSKDPBPJS1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSKDPBPJS1ActionPerformed
        if (tabMode2.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbPetugas2.getSelectedRow() > -1) {
                if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString()) > 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgSKDPBPJS form = new DlgSKDPBPJS(null, false);
                    form.isCek();
                    form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    form.setLocationRelativeTo(internalFrame1);
                    form.emptTeks();
                    form.setNoRm(tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 6).toString(), tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 7).toString(),
                            tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 19).toString(), tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 10).toString(),
                            tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 4).toString(), tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 5).toString(), tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString(), "Ralan");
                    form.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Maaf, Pilih pasien terlebih dahulu..!!!");
            }
        }
    }//GEN-LAST:event_MnSKDPBPJS1ActionPerformed

    private void MnPermintaanLab1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPermintaanLab1ActionPerformed
        if (tabMode2.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbPetugas2.getSelectedRow() > -1) {
                if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString()) > 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgPermintaanLaboratorium dlgro = new DlgPermintaanLaboratorium(null, false);
                    dlgro.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    dlgro.setLocationRelativeTo(internalFrame1);
                    dlgro.emptTeks();
                    dlgro.isCek();
                    dlgro.setNoRm(tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString(), "Ralan",
                            tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 4).toString(),
                            tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 5).toString());
                    dlgro.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Maaf, Pilih pasien terlebih dahulu..!!!");
            }
        }
    }//GEN-LAST:event_MnPermintaanLab1ActionPerformed

    private void MnPermintaanRadiologi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPermintaanRadiologi1ActionPerformed
        if (tabMode2.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            if (tbPetugas2.getSelectedRow() > -1) {
                if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString()) > 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgPermintaanRadiologi dlgro = new DlgPermintaanRadiologi(null, false);
                    dlgro.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    dlgro.setLocationRelativeTo(internalFrame1);
                    dlgro.emptTeks();
                    dlgro.isCek();
                    dlgro.setNoRm(tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString(), "Ralan",
                            tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 4).toString(),
                            tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 5).toString());
                    dlgro.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Maaf, Pilih pasien terlebih dahulu..!!!");
            }
        }
    }//GEN-LAST:event_MnPermintaanRadiologi1ActionPerformed

    private void MnBillingParsialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBillingParsialActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                try {
                    pscaripiutang = koneksi.prepareStatement("select tgl_piutang from piutang_pasien where no_rkm_medis=? and status='Belum Lunas' order by tgl_piutang asc limit 1");
                    try {
                        pscaripiutang.setString(1, TNoRM.getText());
                        rs = pscaripiutang.executeQuery();
                        if (rs.next()) {
                            i = JOptionPane.showConfirmDialog(null, "Masih ada tunggakan pembayaran, apa mau bayar sekarang ?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                            if (i == JOptionPane.YES_OPTION) {
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                DlgLhtPiutang piutang = new DlgLhtPiutang(null, false);
                                piutang.setNoRm(TNoRM.getText(), rs.getDate(1));
                                piutang.tampil();
                                piutang.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                piutang.setLocationRelativeTo(internalFrame1);
                                piutang.setVisible(true);
                                this.setCursor(Cursor.getDefaultCursor());
                            } else {
                                billingprasial();
                            }
                        } else {
                            billingprasial();
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    } finally {
                        if (rs != null) {
                            rs.close();
                        }

                        if (pscaripiutang != null) {
                            pscaripiutang.close();
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }

    }//GEN-LAST:event_MnBillingParsialActionPerformed

    private void MnBlangkoResepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBlangkoResepActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptBlankoResep.jrxml", "report", "::[ Bukti Register ]::",
                    "select pasien.no_ktp,pasien.no_peserta,reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,pasien.no_tlp,"
                    + "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur as umur,poliklinik.nm_poli,"
                    + "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab "
                    + "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "
                    + "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='" + TNoRw.getText() + "' ", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBlangkoResepActionPerformed

    private void MnRujukSisruteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRujukSisruteActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            var.setform("DlgReg");
            SisruteRujukanKeluar dlgki = new SisruteRujukanKeluar(null, false);
            dlgki.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            dlgki.setLocationRelativeTo(internalFrame1);
            dlgki.isCek();
            dlgki.setPasien(TNoRw.getText());
            dlgki.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnRujukSisruteActionPerformed

    private void MnTeridentifikasiTBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnTeridentifikasiTBActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbPetugas.requestFocus();
        } else {
            if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Masukkan diagnosa lewat kamar inap..!!!");
            } else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgDataTB resep = new DlgDataTB(null, false);
                resep.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                resep.setLocationRelativeTo(internalFrame1);
                resep.isCek();
                resep.emptTeks();
                resep.setNoRM(TNoRw.getText());
                resep.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnTeridentifikasiTBActionPerformed

    private void MnTeridentifikasiTB1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnTeridentifikasiTB1ActionPerformed
        if (tabMode2.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else {
            if (tbPetugas2.getSelectedRow() != -1) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgDataTB resep = new DlgDataTB(null, false);
                resep.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                resep.setLocationRelativeTo(internalFrame1);
                resep.isCek();
                resep.emptTeks();
                resep.setNoRM(tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString());
                resep.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnTeridentifikasiTB1ActionPerformed

    private void MnRiwayatObatRacikanBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRiwayatObatRacikanBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            //this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            // DlgRawatJalan dlgrwjl=new DlgRawatJalan(null,false);
            //DlgRiwayatObatRacikan.isCek();
            DlgRiwayatObatRacikan dlgRwtRacikan = new DlgRiwayatObatRacikan(null, false);
            dlgRwtRacikan.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            dlgRwtRacikan.setLocationRelativeTo(internalFrame1);
            dlgRwtRacikan.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnRiwayatObatRacikanBtnPrintActionPerformed

    private void tbPetugas1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPetugas1MouseClicked
        if (tabMode3.getRowCount() != 0) {
            try {
                getDataPL();
            } catch (java.lang.NullPointerException e) {
            }
            if (evt.getClickCount() == 1) {
                i = tbPetugas1.getSelectedColumn();
                if (i == 7) {
                    if (validasicatatan.equals("Yes")) {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        LabelCatatan.setText(Sequel.cariIsi("select catatan from catatan_pasien where no_rkm_medis=?", tbPetugas1.getValueAt(tbPetugas1.getSelectedRow(), 6).toString()));
                        if (!LabelCatatan.getText().equals("")) {
                            DlgCatatan.setLocationRelativeTo(TabRawat);
                            DlgCatatan.setVisible(true);
                        } else {
                            DlgCatatan.setVisible(false);
                        }
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                }
            } else if (evt.getClickCount() == 2) {
                i = tbPetugas1.getSelectedColumn();
                if (i == 1) {
                    if (MnKamarInap1.isEnabled() == true) {
                        MnKamarInap1ActionPerformed(null);
                    }
                } else if (i == 2) {
                    if (var.gettindakan_ralan() == true) {
                        MnRawatJalan1ActionPerformed(null);
                    }
                } else if (i == 3) {
                    if (var.getberi_obat() == true) {
                        MnPemberianObat1ActionPerformed(null);
                    }
                } else if (i == 4) {
                    if (var.getperiksa_lab() == true) {
                        MnPeriksaLab1ActionPerformed(null);
                    }
                }

            }

        }
    }//GEN-LAST:event_tbPetugas1MouseClicked

    private void tbPetugas1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPetugas1KeyPressed
        if (tabMode.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
                try {
//                    getData();
                    getDataPL();
                } catch (java.lang.NullPointerException e) {
                }
                i = tbPetugas1.getSelectedColumn();
                if (i == 1) {
                    if (MnKamarInap.isEnabled() == true) {
                        MnKamarInapActionPerformed(null);
                    }
                } else if (i == 2) {
                    if (var.gettindakan_ralan() == true) {
                        MnRawatJalanActionPerformed(null);
                    }
                } else if (i == 3) {
                    if (var.getberi_obat() == true) {
                        MnPemberianObatActionPerformed(null);
                    }
                } else if (i == 4) {
                    if (var.getperiksa_lab() == true) {
                        MnPeriksaLabActionPerformed(null);
                    }
                } else if (i == 5) {
                    if (var.getrujukan_masuk() == true) {
                        MnRujukMasukActionPerformed(null);
                    }
                } else if (i == 6) {
                    MnCetakRegisterActionPerformed(null);
                } else if (i == 7) {
                    MnBuktiPelayananRalanActionPerformed(null);
                } else if (i == 11) {
                    MnSEPActionPerformed(null);
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_A) {
                for (i = 0; i < tbPetugas.getRowCount(); i++) {
                    tbPetugas.setValueAt(true, i, 0);
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_L) {
                MnBarcodeRM9ActionPerformed(null);
            } else if (evt.getKeyCode() == KeyEvent.VK_X) {
                MnSEPActionPerformed(null);
            } else if (evt.getKeyCode() == KeyEvent.VK_Z) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
                i = tbPetugas1.getSelectedColumn();
                if (i == 1) {
                    MnSEPActionPerformed(null);
                } else if (i == 2) {
                    MnSPBK1ActionPerformed(null);
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_W) {
                MnLembarRalanActionPerformed(null);
            }
        }
    }//GEN-LAST:event_tbPetugas1KeyPressed

    private void tbPetugas1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPetugas1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPetugas1KeyReleased

    private void MnPaketMCUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPaketMCUActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            try {
                String lab, rad, polidalam, ekg;
                lab = "insert into periksa_lab (no_rawat,nip,kd_jenis_prw,tgl_periksa,jam,dokter_perujuk,biaya,kd_dokter,bagian_rs,bhp,tarif_perujuk,tarif_tindakan_dokter,tarif_tindakan_petugas,status) values ('" + TNoRw.getText() + "','lab1','J001000','" + Valid.SetTgl((String) DTPReg.getSelectedItem()) + "','" + CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem() + "','DR00017','330500','DR00017','0','0','0','0','0','Ralan')";
                rad = "insert into periksa_radiologi (no_rawat,nip,kd_jenis_prw,tgl_periksa,jam,dokter_perujuk,biaya,kd_dokter,bagian_rs,bhp,tarif_perujuk,tarif_tindakan_dokter,tarif_tindakan_petugas) values ('" + TNoRw.getText() + "','rad1','J000010','" + Valid.SetTgl((String) DTPReg.getSelectedItem()) + "','" + CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem() + "','DR00019','77000','DR00019','0','0','0','0','0')";
                polidalam = "insert into rawat_jl_pr (no_rawat,kd_jenis_prw,nip,tgl_perawatan,jam_rawat,material,bhp,tarif_tindakanpr,biaya_rawat) VALUES ('" + TNoRw.getText() + "','RJ100000','Surat Keterangan','" + Valid.SetTgl((String) DTPReg.getSelectedItem()) + "','" + CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem() + "','0','0','30000','30000')";
                ekg = "insert into rawat_jl_pr (no_rawat,kd_jenis_prw,nip,tgl_perawatan,jam_rawat,material,bhp,tarif_tindakanpr,biaya_rawat) VALUES ('" + TNoRw.getText() + "','J000301','Surat Keterangan','" + Valid.SetTgl((String) DTPReg.getSelectedItem()) + "','" + CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem() + "','0','0','40000','40000')";

                PreparedStatement pst_lab = koneksiDB.condb().prepareStatement(lab);
                pst_lab.execute();
                PreparedStatement pst_rad = koneksiDB.condb().prepareStatement(rad);
                pst_rad.execute();
                PreparedStatement pst_dalam = koneksiDB.condb().prepareStatement(polidalam);
                pst_dalam.execute();
                PreparedStatement pst_ekg = koneksiDB.condb().prepareStatement(ekg);
                pst_ekg.execute();
                JOptionPane.showMessageDialog(rootPane, "Pian telah berhasil menyimpan paket MCU..");

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_MnPaketMCUActionPerformed

    private void MnPaketNapzaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPaketNapzaActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            try {

                String lab, mop, amp, thc, suratnapza;
                mop = " insert into periksa_lab (no_rawat,nip,kd_jenis_prw,tgl_periksa,jam,dokter_perujuk,biaya,kd_dokter,bagian_rs,bhp,tarif_perujuk,tarif_tindakan_dokter,tarif_tindakan_petugas,status) values ('" + TNoRw.getText() + "','lab1','J000086','" + Valid.SetTgl((String) DTPReg.getSelectedItem()) + "','" + CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem() + "','DR00017','35000','DR00017','0','0','0','0','0','Ralan')";
                amp = " insert into periksa_lab (no_rawat,nip,kd_jenis_prw,tgl_periksa,jam,dokter_perujuk,biaya,kd_dokter,bagian_rs,bhp,tarif_perujuk,tarif_tindakan_dokter,tarif_tindakan_petugas,status) values ('" + TNoRw.getText() + "','lab1','J000087','" + Valid.SetTgl((String) DTPReg.getSelectedItem()) + "','" + CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem() + "','DR00017','35000','DR00017','0','0','0','0','0','Ralan')";
                thc = " insert into periksa_lab (no_rawat,nip,kd_jenis_prw,tgl_periksa,jam,dokter_perujuk,biaya,kd_dokter,bagian_rs,bhp,tarif_perujuk,tarif_tindakan_dokter,tarif_tindakan_petugas,status) values ('" + TNoRw.getText() + "','lab1','J000088','" + Valid.SetTgl((String) DTPReg.getSelectedItem()) + "','" + CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem() + "','DR00017','35000','DR00017','0','0','0','0','0','Ralan')";
                suratnapza = "insert into rawat_jl_pr (no_rawat,kd_jenis_prw,nip,tgl_perawatan,jam_rawat,material,bhp,tarif_tindakanpr,biaya_rawat) VALUES ('" + TNoRw.getText() + "','J000469','Surat Keterangan','" + Valid.SetTgl((String) DTPReg.getSelectedItem()) + "','" + CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem() + "','0','0','50000','50000')";
                PreparedStatement pst_mop = koneksiDB.condb().prepareStatement(mop);
                pst_mop.execute();
                PreparedStatement pst_amp = koneksiDB.condb().prepareStatement(amp);
                pst_amp.execute();
                PreparedStatement pst_thc = koneksiDB.condb().prepareStatement(thc);
                pst_thc.execute();

                PreparedStatement pst_surat = koneksiDB.condb().prepareStatement(suratnapza);
                pst_surat.execute();
                JOptionPane.showMessageDialog(rootPane, "Pian telah berhasil menyimpan paket NAPZA..");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_MnPaketNapzaActionPerformed

    private void MnPaketMMPIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPaketMMPIActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            try {

                String suratmmpi, tesmmpi;
                suratmmpi = "insert into rawat_jl_pr (no_rawat,kd_jenis_prw,nip,tgl_perawatan,jam_rawat,material,bhp,tarif_tindakanpr,biaya_rawat) VALUES ('" + TNoRw.getText() + "','J000999998','Surat Keterangan','" + Valid.SetTgl((String) DTPReg.getSelectedItem()) + "','" + CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem() + "','0','0','50000','50000')";
                tesmmpi = "insert into rawat_jl_dr (no_rawat,kd_jenis_prw,kd_dokter,tgl_perawatan,jam_rawat,material,bhp,tarif_tindakandr,biaya_rawat) VALUES ('" + TNoRw.getText() + "','J000597','D0000046','" + Valid.SetTgl((String) DTPReg.getSelectedItem()) + "','" + CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem() + "','0','0','210000','210000')";

                PreparedStatement pst_surat = koneksiDB.condb().prepareStatement(suratmmpi);
                pst_surat.execute();
                PreparedStatement pst_tes = koneksiDB.condb().prepareStatement(tesmmpi);
                pst_tes.execute();
                JOptionPane.showMessageDialog(rootPane, "Pian telah berhasil menyimpan paket MMPI..");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_MnPaketMMPIActionPerformed

    private void MnCetakSuratSehat2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratSehat2ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                DlgSuratSehat dlgro = new DlgSuratSehat(null, false);
                dlgro.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                dlgro.setLocationRelativeTo(internalFrame1);
                //dlgro.emptTeks();
                //dlgro.isCek();
                dlgro.setPasien(TNoRM.getText(), TNoRw.getText(), TPasien.getText(), TDokter.getText(), kddokter.getText());
                //dlgro.get
                // dlgro.
                dlgro.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnCetakSuratSehat2ActionPerformed

    private void MnInputHPBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnInputHPBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            var.setstatus(true);
            DlgNomorTelp dlgki = new DlgNomorTelp(null, false);
            dlgki.setLocationRelativeTo(internalFrame1);
            dlgki.setPasien(TNoRM.getText(), TPasien.getText());
            dlgki.setVisible(true);

            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnInputHPBtnPrintActionPerformed

    private void MnPaketHajiLakiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPaketHajiLakiActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            try {
                ps = koneksi.prepareStatement("SELECT * FROM jns_perawatan_lab where kd_jenis_prw in ('J000001','J000022','J000029','J000030','J000033','J000049','J000050','J000052','J000053','J000055','J000056','J000057','J000059','J000060','J000061','J000065','J000372')");
                try {
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        pshajilab = koneksi.prepareStatement("insert into periksa_lab values(?,'lab1',?,?,?,'DR00017','0','0','0','0','0','0','0',?,'DR00017','Ralan')");
                        try {
                            pshajilab.setString(1, TNoRw.getText());
                            pshajilab.setString(2, rs.getString("kd_jenis_prw"));
                            pshajilab.setString(3, Valid.SetTgl(DTPReg.getSelectedItem() + ""));
                            pshajilab.setString(4, CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem());
                            pshajilab.setString(5, rs.getString("total_byr"));
                            pshajilab.executeUpdate();
                        } catch (Exception e) {
                            System.out.println("Notifikasi 1: " + e);
                        } finally {
                            if (pshajilab != null) {
                                pshajilab.close();
                            }
                        }
                    }
                    rs.last();
                    LCount.setText("" + rs.getRow());
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

                ps2 = koneksi.prepareStatement("SELECT * FROM jns_perawatan where kd_jenis_prw in ('J000180','J000189')");
                try {
                    rs2 = ps2.executeQuery();
                    String biaya = "0";
                    String kode1 = "";
                    while (rs2.next()) {
                        if (rs2.getString("kd_jenis_prw").equals("J000189")) {
                            kode1 = Sequel.cariIsi("select kd_dokter from jadwal where hari_kerja ='" + Sequel.cariIsi("SELECT CASE  DAYNAME(tgl_registrasi) WHEN 'Sunday' THEN 'MINGGU' "
                                    + "WHEN 'Monday' THEN 'SENIN' WHEN 'Tuesday' THEN 'SELASA' "
                                    + "WHEN 'Wednesday' THEN 'RABU' WHEN 'Thursday' THEN 'KAMIS' "
                                    + "WHEN 'Friday' THEN 'JUMAT' WHEN 'Saturday' THEN 'SABTU' END as hari "
                                    + "FROM reg_periksa WHERE tgl_registrasi = ? ", Valid.SetTgl((String) DTPReg.getSelectedItem())) + "' and kd_poli ='U0012' ");
                        } else {
                            kode1 = "DR00019";
                        }
                        pshajidr = koneksi.prepareStatement("insert into rawat_jl_dr values(?,?,?,?,?,?,?,?,?,?,?,'Belum')");
                        try {
                            pshajidr.setString(1, TNoRw.getText());
                            pshajidr.setString(2, rs2.getString("kd_jenis_prw"));
                            pshajidr.setString(3, kode1);
                            pshajidr.setString(4, Valid.SetTgl(DTPReg.getSelectedItem() + ""));
                            pshajidr.setString(5, CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem());
                            pshajidr.setString(6, biaya);
                            pshajidr.setString(7, biaya);
                            pshajidr.setString(8, rs2.getString("total_byrdr"));
                            pshajidr.setString(9, biaya);
                            pshajidr.setString(10, biaya);
                            pshajidr.setString(11, rs2.getString("total_byrdr"));
                            pshajidr.executeUpdate();
                        } catch (Exception e) {
                            System.out.println("Notifikasi 2: " + e);
                        } finally {
                            if (pshajidr != null) {
                                pshajidr.close();
                            }
                        }
                    }
                    rs2.last();
                    LCount.setText("" + rs2.getRow());
                } catch (Exception e) {
                    System.out.println(e);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (ps2 != null) {
                        ps2.close();
                    }
                }

                ps3 = koneksi.prepareStatement("SELECT * FROM jns_perawatan where kd_jenis_prw in ('J000301')");
                try {
                    rs3 = ps3.executeQuery();
                    String biaya = "0";
                    while (rs3.next()) {
                        pshajipr = koneksi.prepareStatement("insert into rawat_jl_pr values(?,?,'unit42',?,?,?,?,?,?,?,?,'Belum')");
                        try {
                            pshajipr.setString(1, TNoRw.getText());
                            pshajipr.setString(2, rs3.getString("kd_jenis_prw"));
                            pshajipr.setString(3, Valid.SetTgl(DTPReg.getSelectedItem() + ""));
                            pshajipr.setString(4, CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem());
                            pshajipr.setString(5, biaya);
                            pshajipr.setString(6, biaya);
                            pshajipr.setString(7, rs3.getString("total_byrpr"));
                            pshajipr.setString(8, biaya);
                            pshajipr.setString(9, biaya);
                            pshajipr.setString(10, rs3.getString("total_byrpr"));
                            pshajipr.executeUpdate();
                        } catch (Exception e) {
                            System.out.println("Notifikasi 3: " + e);
                        } finally {
                            if (pshajipr != null) {
                                pshajipr.close();
                            }
                        }
                    }
                    rs3.last();
                    LCount.setText("" + rs3.getRow());
                } catch (Exception e) {
                    System.out.println(e);
                } finally {
                    if (rs3 != null) {
                        rs3.close();
                    }
                    if (ps3 != null) {
                        ps3.close();
                    }
                }

                ps4 = koneksi.prepareStatement("SELECT * FROM jns_perawatan_radiologi where kd_jenis_prw ='J000010'");
                try {
                    rs4 = ps4.executeQuery();
                    while (rs4.next()) {
                        pshajirad = koneksi.prepareStatement("insert into periksa_radiologi values(?,'rad1','J000010',?,?,'DR00019','0','0','0','0','0','0','0',?,'DR00019','Ralan')");
                        try {
                            pshajirad.setString(1, TNoRw.getText());
                            pshajirad.setString(2, Valid.SetTgl(DTPReg.getSelectedItem() + ""));
                            pshajirad.setString(3, CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem());
                            pshajirad.setString(4, rs4.getString("total_byr"));
                            pshajirad.executeUpdate();
                        } catch (Exception e) {
                            System.out.println("Notifikasi 4: " + e);
                        } finally {
                            if (pshajirad != null) {
                                pshajirad.close();
                            }
                        }
                    }
                    rs4.last();
                    LCount.setText("" + rs4.getRow());
                } catch (Exception e) {
                    System.out.println(e);
                } finally {
                    if (rs4 != null) {
                        rs4.close();
                    }
                    if (ps4 != null) {
                        ps4.close();
                    }
                }

                JOptionPane.showMessageDialog(rootPane, "Pian sukses menyimpan paket CJH Laki-laki..");
            } catch (Exception e) {
                System.out.println("Notifikasi 5: " + e);
            }
        }

    }//GEN-LAST:event_MnPaketHajiLakiActionPerformed

    private void MnPaketHajiPerempuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPaketHajiPerempuanActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            try {
                ps = koneksi.prepareStatement("SELECT * FROM jns_perawatan_lab where kd_jenis_prw in ('J000001','J000022','J000029','J000030','J000033','J000049','J000050','J000052','J000053','J000055','J000056','J000057','J000059','J000060','J000061','J000065','J000372','J000002')");
                try {
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        pshajilab = koneksi.prepareStatement("insert into periksa_lab values(?,'lab1',?,?,?,'DR00017','0','0','0','0','0','0','0',?,'DR00017','Ralan')");
                        try {
                            pshajilab.setString(1, TNoRw.getText());
                            pshajilab.setString(2, rs.getString("kd_jenis_prw"));
                            pshajilab.setString(3, Valid.SetTgl(DTPReg.getSelectedItem() + ""));
                            pshajilab.setString(4, CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem());
                            pshajilab.setString(5, rs.getString("total_byr"));
                            pshajilab.executeUpdate();
                        } catch (Exception e) {
                            System.out.println("Notifikasi 1: " + e);
                        } finally {
                            if (pshajilab != null) {
                                pshajilab.close();
                            }
                        }
                    }
                    rs.last();
                    LCount.setText("" + rs.getRow());
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

                ps2 = koneksi.prepareStatement("SELECT * FROM jns_perawatan where kd_jenis_prw in ('J000180','J000189')");
                try {
                    rs2 = ps2.executeQuery();
                    String biaya = "0";
                    String kode1 = "";
                    while (rs2.next()) {
                        if (rs2.getString("kd_jenis_prw").equals("J000189")) {
                            kode1 = Sequel.cariIsi("select kd_dokter from jadwal where hari_kerja ='" + Sequel.cariIsi("SELECT CASE  DAYNAME(tgl_registrasi) WHEN 'Sunday' THEN 'MINGGU' "
                                    + "WHEN 'Monday' THEN 'SENIN' WHEN 'Tuesday' THEN 'SELASA' "
                                    + "WHEN 'Wednesday' THEN 'RABU' WHEN 'Thursday' THEN 'KAMIS' "
                                    + "WHEN 'Friday' THEN 'JUMAT' WHEN 'Saturday' THEN 'SABTU' END as hari "
                                    + "FROM reg_periksa WHERE tgl_registrasi = ? ", Valid.SetTgl((String) DTPReg.getSelectedItem())) + "' and kd_poli ='U0012' ");
                        } else {
                            kode1 = "DR00019";
                        }
                        pshajidr = koneksi.prepareStatement("insert into rawat_jl_dr values(?,?,?,?,?,?,?,?,?,?,?,'Belum')");
                        try {
                            pshajidr.setString(1, TNoRw.getText());
                            pshajidr.setString(2, rs2.getString("kd_jenis_prw"));
                            pshajidr.setString(3, kode1);
                            pshajidr.setString(4, Valid.SetTgl(DTPReg.getSelectedItem() + ""));
                            pshajidr.setString(5, CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem());
                            pshajidr.setString(6, biaya);
                            pshajidr.setString(7, biaya);
                            pshajidr.setString(8, rs2.getString("total_byrdr"));
                            pshajidr.setString(9, biaya);
                            pshajidr.setString(10, biaya);
                            pshajidr.setString(11, rs2.getString("total_byrdr"));
                            pshajidr.executeUpdate();

                        } catch (Exception e) {
                            System.out.println("Notifikasi 2: " + e);
                        } finally {
                            if (pshajidr != null) {
                                pshajidr.close();
                            }
                        }
                    }
                    rs2.last();
                    LCount.setText("" + rs2.getRow());
                } catch (Exception e) {
                    System.out.println(e);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (ps2 != null) {
                        ps2.close();
                    }
                }

                ps3 = koneksi.prepareStatement("SELECT * FROM jns_perawatan where kd_jenis_prw in ('J000301')");
                try {
                    rs3 = ps3.executeQuery();
                    String biaya = "0";
                    while (rs3.next()) {
                        pshajipr = koneksi.prepareStatement("insert into rawat_jl_pr values(?,?,'unit42',?,?,?,?,?,?,?,?,'Belum')");
                        try {
                            pshajipr.setString(1, TNoRw.getText());
                            pshajipr.setString(2, rs3.getString("kd_jenis_prw"));
                            pshajipr.setString(3, Valid.SetTgl(DTPReg.getSelectedItem() + ""));
                            pshajipr.setString(4, CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem());
                            pshajipr.setString(5, biaya);
                            pshajipr.setString(6, biaya);
                            pshajipr.setString(7, rs3.getString("total_byrpr"));
                            pshajipr.setString(8, biaya);
                            pshajipr.setString(9, biaya);
                            pshajipr.setString(10, rs3.getString("total_byrpr"));
                            pshajipr.executeUpdate();
                        } catch (Exception e) {
                            System.out.println("Notifikasi 3: " + e);
                        } finally {
                            if (pshajipr != null) {
                                pshajipr.close();
                            }
                        }
                    }
                    rs3.last();
                    LCount.setText("" + rs3.getRow());
                } catch (Exception e) {
                    System.out.println(e);
                } finally {
                    if (rs3 != null) {
                        rs3.close();
                    }
                    if (ps3 != null) {
                        ps3.close();
                    }
                }

                ps4 = koneksi.prepareStatement("SELECT * FROM jns_perawatan_radiologi where kd_jenis_prw ='J000010'");
                try {
                    rs4 = ps4.executeQuery();
                    while (rs4.next()) {
                        pshajirad = koneksi.prepareStatement("insert into periksa_radiologi values(?,'rad1','J000010',?,?,'DR00019','0','0','0','0','0','0','0',?,'DR00019','Ralan')");
                        try {
                            pshajirad.setString(1, TNoRw.getText());
                            pshajirad.setString(2, Valid.SetTgl(DTPReg.getSelectedItem() + ""));
                            pshajirad.setString(3, CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem());
                            pshajirad.setString(4, rs4.getString("total_byr"));
                            pshajirad.executeUpdate();
                        } catch (Exception e) {
                            System.out.println("Notifikasi 4: " + e);
                        } finally {
                            if (pshajirad != null) {
                                pshajirad.close();
                            }
                        }
                    }
                    rs4.last();
                    LCount.setText("" + rs4.getRow());
                } catch (Exception e) {
                    System.out.println(e);
                } finally {
                    if (rs4 != null) {
                        rs4.close();
                    }
                    if (ps4 != null) {
                        ps4.close();
                    }
                }

                JOptionPane.showMessageDialog(rootPane, "Pian sukses menyimpan paket CJH Perempuan..");
            } catch (Exception e) {
                System.out.println("Notifikasi 5: " + e);
            }
        }
    }//GEN-LAST:event_MnPaketHajiPerempuanActionPerformed

    private void BtnSimpan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan2ActionPerformed
        String a;
        a = Sequel.cariIsi("select interpretasi from interpretasi_ekg where no_rawat=" + TNoRwint.getText().toString());
        if (TNoRwint.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");

        } else if (Tind.getText().trim().equals("")) {

            try {
                String interpretasi;
                interpretasi = "insert into interpretasi_ekg values ('" + TNoRwint.getText().toString() + "','" + TKesan.getText().toString() + "')";
                PreparedStatement pst_int = koneksiDB.condb().prepareStatement(interpretasi);
                pst_int.execute();
                Tind.setText(Sequel.cariIsi("select interpretasi from interpretasi_ekg where no_rawat= '" + TNoRwint.getText() + "'"));
                JOptionPane.showMessageDialog(rootPane, "Pian sukses menyimpan data..");
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            try {
                String interpretasi;
                interpretasi = "update interpretasi_ekg set interpretasi = '" + TKesan.getText() + "' where no_rawat = '" + TNoRwint.getText() + "'";
                PreparedStatement pst_int = koneksiDB.condb().prepareStatement(interpretasi);
                pst_int.execute();
                JOptionPane.showMessageDialog(rootPane, "Pian sukses mengubah data..");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_BtnSimpan2ActionPerformed

    private void BtnSimpan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpan2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSimpan2KeyPressed

    private void BtnKeluar5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar5ActionPerformed
        //TKesan.setText("");
        //dispose();
        DlgInterpretasiEKG.dispose();

    }//GEN-LAST:event_BtnKeluar5ActionPerformed

    private void BtnKeluar5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluar5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnKeluar5KeyPressed

    private void TNoRMEkgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TNoRMEkgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNoRMEkgActionPerformed

    private void TKesanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKesanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TKesanKeyPressed

    private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint1ActionPerformed
        // gasan cetak interpretasi
        Map<String, Object> param = new HashMap<>();
        param.put("norm", Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=?", TNoRwint.getText()));
        param.put("nama", Sequel.cariIsi("select nm_pasien from reg_periksa,pasien where reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.no_rawat=?", TNoRwint.getText()));
        param.put("umur", Sequel.cariIsi("select umur from pasien where no_rkm_medis=?", TNoRMint.getText()) + " / " + Sequel.cariIsi("select tmp_lahir from reg_periksa, pasien where reg_periksa.no_rkm_medis = pasien.no_rkm_medis and reg_periksa.no_rawat =  ?", TNoRwint.getText()) + ", " + Sequel.cariIsi("select  DATE_FORMAT(pasien.tgl_lahir, '%d-%m-%Y') from reg_periksa, pasien where reg_periksa.no_rkm_medis = pasien.no_rkm_medis and reg_periksa.no_rawat =  ?", TNoRwint.getText()));
        param.put("alamat", Sequel.cariIsi("SELECT concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat FROM reg_periksa INNER JOIN pasien INNER JOIN kelurahan INNER JOIN kecamatan INNER JOIN kabupaten ON "
                + "reg_periksa.no_rkm_medis = pasien.no_rkm_medis and pasien.kd_kel=kelurahan.kd_kel and "
                + "kecamatan.kd_kec=pasien.kd_kec AND pasien.kd_kab=kabupaten.kd_kab WHERE "
                + "	reg_periksa.no_rawat=?", TNoRwint.getText()));
        param.put("interpretasi", TKesan.getText());

        param.put("namars", var.getnamars());
        param.put("alamatrs", var.getalamatrs());
        param.put("kotars", var.getkabupatenrs());
        param.put("propinsirs", var.getpropinsirs());
        param.put("kontakrs", var.getkontakrs());
        param.put("emailrs", var.getemailrs());
        param.put("logo", Sequel.cariGambar("select logo from setting"));

        Valid.MyReport("rptInterpretasiEKG1.jrxml", "report", "::[ Interpretasi EKG ]::", "select * from interpretasi_ekg limit 1", param);
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    private void BtnPrint1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrint1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPrint1KeyPressed

    private void MnInterpretasiEkgBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnInterpretasiEkgBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            DlgInterpretasiEKG.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            DlgInterpretasiEKG.setLocationRelativeTo(internalFrame1);
            DlgInterpretasiEKG.setVisible(true);
            TNoRwint.setText(TNoRw.getText());
            TNoRMint.setText(TNoRM.getText());
            TPasienint.setText(TPasien.getText());
            TKesan.setText(Sequel.cariIsi("select interpretasi from interpretasi_ekg where no_rawat= '" + TNoRwint.getText() + "'"));
            // ini gasan pengkondisian di form interpretasi, jika isian kosong maka simpan, jika tidak ubah
            Tind.setVisible(false);
            Tind.setText(Sequel.cariIsi("select interpretasi from interpretasi_ekg where no_rawat= '" + TNoRwint.getText() + "'"));
            // ini gasan pengkondisian di form interpretasi, jika isian kosong maka simpan, jika tidak ubah

        }
    }//GEN-LAST:event_MnInterpretasiEkgBtnPrintActionPerformed

    private void MnInterpretasiEkg2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnInterpretasiEkg2ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            DlgInterpretasiEKG.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            DlgInterpretasiEKG.setLocationRelativeTo(internalFrame1);
            DlgInterpretasiEKG.setVisible(true);
            TNoRwint.setText(tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString());
            TNoRMint.setText(tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 6).toString());
            TPasienint.setText(tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 7).toString());
            TKesan.setText(Sequel.cariIsi("select interpretasi from interpretasi_ekg where no_rawat= '" + TNoRwint.getText() + "'"));
            // ini gasan pengkondisian di form interpretasi, jika isian kosong maka simpan, jika tidak ubah
            Tind.setVisible(false);
            Tind.setText(Sequel.cariIsi("select interpretasi from interpretasi_ekg where no_rawat= '" + TNoRwint.getText() + "'"));
            // ini gasan pengkondisian di form interpretasi, jika isian kosong maka simpan, jika tidak ubah

    }//GEN-LAST:event_MnInterpretasiEkg2ActionPerformed
    }
    private void MnJawabKonsulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnJawabKonsulActionPerformed
        DlgJawabKonsul dlgjk = new DlgJawabKonsul(null, false);
        dlgjk.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dlgjk.setLocationRelativeTo(internalFrame1);
        dlgjk.setDataPasien(tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString(), tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 6).toString(), tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 7).toString(), tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 4).toString(), Sequel.cariIsi("select nm_dokter from reg_periksa, dokter WHERE reg_periksa.kd_dokter = dokter.kd_dokter AND reg_periksa.no_rawat='" + tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString() + "'"), tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 11).toString(), Sequel.cariIsi("select konsul from rujukan_internal_poli_detail where no_rawat= '" + tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString() + "'"));
        dlgjk.setVisible(true);

    }//GEN-LAST:event_MnJawabKonsulActionPerformed

    private void MnJawabanKonsulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnJawabanKonsulActionPerformed
        DlgJawabanKonsul.setSize(790, 650);
        DlgJawabanKonsul.setLocationRelativeTo(internalFrame1);
        //DlgJawabanKonsul.setDataPasien(tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(),1).toString(), tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(),6).toString(), tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(),7).toString(), tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(),4).toString(),Sequel.cariIsi("select nm_dokter from reg_periksa, dokter WHERE reg_periksa.kd_dokter = dokter.kd_dokter AND reg_periksa.no_rawat='"+tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(),1).toString()+"'"),tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(),11).toString(),Sequel.cariIsi("select konsul from rujukan_internal_poli_detail where no_rawat= '"+tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(),1).toString()+"'"));
        LabelNama.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(), 8).toString());
        LabelNoRM.setText(TNoRM.getText());
        LabelNoRW.setText(TNoRw.getText());
        LabelDokter.setText(TDokter.getText());
        LabelPoliPerujuk.setText(TPoli.getText());
        LabelDokterTujuan.setText(Sequel.cariIsi("select dokter.nm_dokter from rujukan_internal_poli, dokter where rujukan_internal_poli.kd_dokter = dokter.kd_dokter AND no_rawat = '" + TNoRw.getText() + "'"));
        LabelPoliTujuan.setText(Sequel.cariIsi("select poliklinik.nm_poli from rujukan_internal_poli, poliklinik where rujukan_internal_poli.kd_poli = poliklinik.kd_poli AND no_rawat = '" + TNoRw.getText() + "'"));
        TKonsulJawab.setText(Sequel.cariIsi("select konsul from rujukan_internal_poli_detail where no_rawat= '" + TNoRw.getText() + "'"));
        TPemeriksaanJawab.setText(Sequel.cariIsi("select pemeriksaan from rujukan_internal_poli_detail where no_rawat= '" + TNoRw.getText() + "'"));
        LabelDiagnosa.setText(Sequel.cariIsi("select diagnosa from rujukan_internal_poli_detail where no_rawat= '" + TNoRw.getText() + "'"));
        TSaranJawab.setText(Sequel.cariIsi("select saran from rujukan_internal_poli_detail where no_rawat= '" + TNoRw.getText() + "'"));
        DlgJawabanKonsul.setVisible(true);
    }//GEN-LAST:event_MnJawabanKonsulActionPerformed

    private void ppSuratKontrolBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSuratKontrolBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            if (tbPetugas.getSelectedRow() != -1) {
                try {
                    String dateku = "";
                    ps = koneksi.prepareStatement("select * from bridging_sep where no_rawat=?");
                    try {
                        ps.setString(1, TNoRw.getText());
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            BPJSSuratKontrol form = new BPJSSuratKontrol(null, false);
                            dateku = rs.getString("tglsep");
                            form.setNoRm(rs.getString("no_rawat"), rs.getString("no_sep"), rs.getString("no_kartu"), rs.getString("nomr"), rs.getString("nama_pasien"), rs.getString("tanggal_lahir"), rs.getString("jkel"), rs.getString("nmdiagnosaawal"), rs.getString("kdpolitujuan"), dateku);
                            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                            form.setLocationRelativeTo(internalFrame1);
                            form.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        } else {
                            JOptionPane.showMessageDialog(null, "Pasien tersebut belum terbit SEP, silahkan hubungi bagian terkait..!!");
                            TCari.requestFocus();
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : " + e);
                    } finally {
                        if (rs != null) {
                            rs.close();
                        }
                        if (ps != null) {
                            ps.close();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Notif : " + e);
                }
            }
        }
    }//GEN-LAST:event_ppSuratKontrolBtnPrintActionPerformed

    private void ppSuratPRIBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSuratPRIBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            if (tbPetugas.getSelectedRow() != -1) {
                try {
                    ps = koneksi.prepareStatement("select no_peserta,tgl_lahir,jk from pasien where no_rkm_medis=?");
                    try {
                        ps.setString(1, TNoRM.getText());
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            if (rs.getString("no_peserta").length() < 13) {
                                JOptionPane.showMessageDialog(null, "Kartu BPJS Pasien tidak valid, silahkan hubungi bagian terkait..!!");
                            } else {
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                BPJSSPRI form = new BPJSSPRI(null, false);
                                form.setNoRm(TNoRw.getText(), rs.getString("no_peserta"), TNoRM.getText(), TPasien.getText(), rs.getString("tgl_lahir"), rs.getString("jk"), "-");
                                form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                                form.setLocationRelativeTo(internalFrame1);
                                form.setVisible(true);
                                this.setCursor(Cursor.getDefaultCursor());
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : " + e);
                    } finally {
                        if (rs != null) {
                            rs.close();
                        }
                        if (ps != null) {
                            ps.close();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Notif : " + e);
                }
            }
        }
    }//GEN-LAST:event_ppSuratPRIBtnPrintActionPerformed

    private void MnNapza6ParamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnNapza6ParamActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            try {
                String user = "199207102011033003";
                String kd_jenis_prw_lab = "J003852";
                String kd_jenis_prw_ralan = "J000469";
                String biaya_lab = Sequel.cariIsi("SELECT total_byr FROM jns_perawatan_lab WHERE kd_jenis_prw = '" + kd_jenis_prw_lab + "'");
                String biaya_ralan = Sequel.cariIsi("SELECT total_byrpr FROM jns_perawatan WHERE kd_jenis_prw = '" + kd_jenis_prw_ralan + "'");
                if (Sequel.menyimpantf("periksa_lab", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "data", 16, new String[]{
                    TNoRw.getText(), "Lab1", kd_jenis_prw_lab, Valid.SetTgl(DTPReg.getSelectedItem() + ""), CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem(),
                    "DR00017", "0", "0", "0", "0", "0", "0", "0", biaya_lab, "DR00017", "Ralan"
                }) == true) {
                    if (Sequel.menyimpantf("rawat_jl_pr", "?,?,?,?,?,?,?,?,?,?,?,?", "data", 12, new String[]{
                        TNoRw.getText(), kd_jenis_prw_ralan, user, Valid.SetTgl(DTPReg.getSelectedItem() + ""), CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem(),
                        "0", "0", biaya_ralan, "0", "0", biaya_ralan, "Belum"
                    }) == true) {
                        JOptionPane.showMessageDialog(rootPane, "Pian telah berhasil menyimpan paket NAPZA..");
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_MnNapza6ParamActionPerformed

    private void ChkTrackerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkTrackerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkTrackerActionPerformed

    private void ppDPJPBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppDPJPBtnPrintActionPerformed
        if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
        } else {
            DlgDpjp dpjp = new DlgDpjp(null, false);
            dpjp.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            dpjp.setLocationRelativeTo(internalFrame1);
            dpjp.isCek();
            dpjp.setdariIGD(TNoRw.getText());
            dpjp.tampil();
            dpjp.setVisible(true);
        }
    }//GEN-LAST:event_ppDPJPBtnPrintActionPerformed

    private void MnSuratNapzaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSuratNapzaActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                SuratNapza napza = new SuratNapza(null, false);
                napza.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                napza.setLocationRelativeTo(internalFrame1);
                napza.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnSuratNapzaActionPerformed

    private void tbPetugasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPetugasKeyReleased
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbPetugasKeyReleased

    private void tbPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPetugasKeyPressed
        if (tabMode.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
                i = tbPetugas.getSelectedColumn();
                if (i == 1) {
                    if (MnKamarInap.isEnabled() == true) {
                        MnKamarInapActionPerformed(null);
                    }
                } else if (i == 2) {
                    if (var.gettindakan_ralan() == true) {
                        MnRawatJalanActionPerformed(null);
                    }
                } else if (i == 3) {
                    if (var.getberi_obat() == true) {
                        MnPemberianObatActionPerformed(null);
                    }
                } else if (i == 4) {
                    if (var.getperiksa_lab() == true) {
                        MnPeriksaLabActionPerformed(null);
                    }
                } else if (i == 5) {
                    if (var.getrujukan_masuk() == true) {
                        MnRujukMasukActionPerformed(null);
                    }
                } else if (i == 6) {
                    MnCetakRegisterActionPerformed(null);
                } else if (i == 7) {
                    MnBuktiPelayananRalanActionPerformed(null);
                } else if (i == 13) {
                    if (MnSEP.isEnabled() == true) {
                        bukaBridging();
                    }
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_A) {
                for (i = 0; i < tbPetugas.getRowCount(); i++) {
                    tbPetugas.setValueAt(true, i, 0);
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_L) {
                MnBarcodeRM9ActionPerformed(null);
            } else if (evt.getKeyCode() == KeyEvent.VK_Z) {
                MnSPBK1ActionPerformed(null);
            } else if (evt.getKeyCode() == KeyEvent.VK_W) {
                MnLembarRalanActionPerformed(null);
            }
        }
    }//GEN-LAST:event_tbPetugasKeyPressed

    private void tbPetugasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPetugasMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            if (evt.getClickCount() == 1) {
                i = tbPetugas.getSelectedColumn();
                if (i == 8) {
                    if (validasicatatan.equals("Yes")) {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        LabelCatatan.setText(Sequel.cariIsi("select catatan from catatan_pasien where no_rkm_medis=?", TNoRM.getText()));
                        if (!LabelCatatan.getText().equals("")) {
                            DlgCatatan.setLocationRelativeTo(TabRawat);
                            DlgCatatan.setVisible(true);
                        } else {
                            DlgCatatan.setVisible(false);
                        }
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                }
            } else if (evt.getClickCount() == 2) {
                i = tbPetugas.getSelectedColumn();
                if (i == 1) {
                    if (MnKamarInap.isEnabled() == true) {
                        MnKamarInapActionPerformed(null);
                    }
                } else if (i == 2) {
                    if (var.gettindakan_ralan() == true) {
                        MnRawatJalanActionPerformed(null);
                    }
                } else if (i == 3) {
                    if (var.getberi_obat() == true) {
                        MnPemberianObatActionPerformed(null);
                    }
                } else if (i == 4) {
                    if (var.getperiksa_lab() == true) {
                        MnPeriksaLabActionPerformed(null);
                    }
                } else if (i == 5) {
                    if (var.getrujukan_masuk() == true) {
                        MnRujukMasukActionPerformed(null);
                    }
                } else if (i == 6) {
                    MnCetakRegisterActionPerformed(null);
                } else if (i == 13) {
                    if (MnSEP.isEnabled() == true) {
                        bukaBridging();
                    }
                }
            }

        }
    }//GEN-LAST:event_tbPetugasMouseClicked

    private void BtnPanggilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPanggilActionPerformed
        try {
            nm_pasien = Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", TNoRM.getText());
            nm_pasien = nm_pasien.replace(" ", "%20");
            no_reg = TNoReg.getText();
            nm_poli = Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?", kdpoli.getText());
            nm_poli = nm_poli.replace(" ", "%20");
            url = "panggil.php?text=" + nm_pasien + ",%20nomor%20antrian%20" + no_reg + ",%20ke%20" + nm_poli;
            Valid.panggilUrl(url);
        } catch (Exception ex) {
            System.out.println("Notifikasi : " + ex);
            System.out.println(url);
            if (ex.toString().contains("UnknownHostException")) {
                JOptionPane.showMessageDialog(null, "Koneksi ke server text to speech terputus...!");
            }
        }
    }//GEN-LAST:event_BtnPanggilActionPerformed

    private void BtnPanggilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPanggilKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnPanggilKeyPressed

    private void BtnKeluar6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar6ActionPerformed
        DlgJawabanKonsul.dispose();
    }//GEN-LAST:event_BtnKeluar6ActionPerformed

    private void TPemeriksaanJawabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPemeriksaanJawabKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TPemeriksaanJawabKeyPressed

    private void TKonsulJawabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKonsulJawabKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TKonsulJawabKeyPressed

    private void TSaranJawabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TSaranJawabKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TSaranJawabKeyPressed

    private void MnUbahRujukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUbahRujukanActionPerformed
        if (tabMode2.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            WindowUbahRujukan.setSize(500, 114);
            WindowUbahRujukan.setLocationRelativeTo(internalFrame1);
            WindowUbahRujukan.setVisible(true);
        }
    }//GEN-LAST:event_MnUbahRujukanActionPerformed

    private void BtnPoli1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPoli1ActionPerformed
        if (aktifjadwal.equals("aktif")) {
            if (var.getkode().equals("Admin Utama")) {
                poli3.isCek();
                poli3.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                poli3.setLocationRelativeTo(internalFrame1);
                poli3.setVisible(true);
            } else {
                poli5.isCek();
//                poli5.SetHari(TanggalPeriksa.getDate());
                poli5.tampil();
                poli5.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                poli5.setLocationRelativeTo(internalFrame1);
                poli5.setVisible(true);
            }
        } else {
            poli3.isCek();
            poli3.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            poli3.setLocationRelativeTo(internalFrame1);
            poli3.setVisible(true);
        }
    }//GEN-LAST:event_BtnPoli1ActionPerformed

    private void BtnSimpan6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan6ActionPerformed
        if (tbPetugas2.getSelectedRow() != -1) {
            if (tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString().trim().equals("")) {
                Valid.textKosong(TCari, "No.Rawat");
            } else {
                Sequel.mengedit("rujukan_internal_poli", "no_rawat=?", "kd_poli=?", 2, new String[]{
                    KdPoli1.getText(), tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(), 1).toString()
                });
                KdPoli1.setText("");
                NmPoli1.setText("");
                tampil2();
                WindowUbahRujukan.dispose();
            }
        }
    }//GEN-LAST:event_BtnSimpan6ActionPerformed

    private void BtnCloseIn6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn6ActionPerformed
        WindowUbahRujukan.dispose();
    }//GEN-LAST:event_BtnCloseIn6ActionPerformed

    private void MnPrioritasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPrioritasActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
        } else if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            try {
                if (Sequel.cariInteger("SELECT count(no_rawat) FROM kategori_pasien_igd WHERE no_rawat = '" + TNoRw.getText() + "'") > 0) {
                    int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiits, Yakin ingin hapus kategori PRIORITAS..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            Sequel.meghapus("kategori_pasien_igd","no_rawat",tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString());
                            tampil();
                            emptTeks();
                        }
                } else {                                        
                    String ktg;
                    ktg = "insert into kategori_pasien_igd (no_rawat, kategori) values ('" + TNoRw.getText() + "','PRIORITAS')";
                    PreparedStatement ps_ktg = koneksiDB.condb().prepareStatement(ktg);
                    ps_ktg.execute();
                    tampil();
                }
            } catch (Exception e) {
                System.out.println("Gagal menyimpan " + e);
            }
        }
    }//GEN-LAST:event_MnPrioritasActionPerformed

    private void BtnTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTextActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
        WindowRunningText.setLocationRelativeTo(internalFrame1);
        WindowRunningText.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnTextActionPerformed

    private void BtnCloseIn7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn7ActionPerformed
        WindowRunningText.dispose();
    }//GEN-LAST:event_BtnCloseIn7ActionPerformed

    private void BtnSimpan7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan7ActionPerformed
        if (KdPoli2.getText().trim().equals("")) {
            Valid.textKosong(KdPoli2, "Poli");
        } else if (TRunningText.getText().trim().equals("")){
            Valid.textKosong(TRunningText, "Running Text");
        } else {
            try {
                if (Sequel.cariInteger("SELECT kd_poli FROM maping_video_poli ") > 0) {
                    Sequel.mengedit("maping_video_poli", "kd_poli='" + KdPoli2.getText() + "'", "running_text='" +TRunningText.getText()+ "'");
                    KdPoli2.setText("");
                    NmPoli2.setText("");
                    TRunningText.setText("");
                    WindowRunningText.dispose();
                } else {
                    String ktg;
                    ktg = "insert into maping_video_poli (id, kd_poli, nm_poli, running_text, tanggal, user) values "+
                          "('0','" + KdPoli2.getText() + "','"+NmPoli2.getText()+"','"+TRunningText.getText()+"',"+
                          "'"+Valid.SetTgl(DTPReg.getSelectedItem() + "")+" "+CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem()+"','"+var.getkode()+"')";
                    PreparedStatement ps_ktg = koneksiDB.condb().prepareStatement(ktg);
                    ps_ktg.execute();
                    KdPoli2.setText("");
                    NmPoli2.setText("");
                    TRunningText.setText("");
                    WindowRunningText.dispose();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }//GEN-LAST:event_BtnSimpan7ActionPerformed

    private void BtnPoli2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPoli2ActionPerformed
        if (aktifjadwal.equals("aktif")) {
            if (var.getkode().equals("Admin Utama")) {
                poli6.isCek();
                poli6.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                poli6.setLocationRelativeTo(internalFrame1);
                poli6.setVisible(true);
            } else {
                poli7.isCek();
//                poli5.SetHari(TanggalPeriksa.getDate());
                poli7.tampil();
                poli7.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                poli7.setLocationRelativeTo(internalFrame1);
                poli7.setVisible(true);
            }
        } else {
            poli6.isCek();
            poli6.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            poli6.setLocationRelativeTo(internalFrame1);
            poli6.setVisible(true);
        }
    }//GEN-LAST:event_BtnPoli2ActionPerformed

    private void MnKronisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKronisActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        } else if (!nmpnj.getText().trim().equals("BPJS")) {
            JOptionPane.showMessageDialog(null, "Maaf, cara bayar tidak sesuai...!!!");
        } else if (TNoRw.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            try {
                if (Sequel.cariInteger("SELECT count(no_rawat) FROM mlite_veronisa WHERE no_rawat = '" + TNoRw.getText() + "'") > 0) {
                    int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiits, Yakin ingin hapus status obat kronis..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                             Sequel.meghapus("mlite_veronisa","no_rawat",tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString());
                             tampil();
                             emptTeks();
                        }
                } else {
                    String kronis;
                    kronis = "insert into mlite_veronisa (id, tanggal, no_rkm_medis, no_rawat, tgl_registrasi, nosep, status, username) "+
                          "values ('0','"+Sequel.cariIsi("select current_date()")+"','"+TNoRM.getText()+"','" + TNoRw.getText() + "','"+Valid.SetTgl(DTPReg.getSelectedItem() + "")+"','"+tbPetugas.getValueAt(tbPetugas.getSelectedRow(), 24).toString()+"','Belum','"+var.getkode()+"')";
                    PreparedStatement ps_krns = koneksiDB.condb().prepareStatement(kronis);
                    ps_krns.execute();
                    JOptionPane.showMessageDialog(rootPane, "Berhasil set status obat kronis..");
                    tampil();                
                }
            } catch (Exception e) {
                System.out.println("Gagal menyimpan " + e);
            }
        }
    }//GEN-LAST:event_MnKronisActionPerformed

    private void MnBarcodeRM10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcodeRM10ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data pasien sudah habis...!!!!");
            TNoRM.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptBarcodeRM20.jrxml", "report", "::[ Label Rekam Medis ]::", "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "
                    + "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"
                    + "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"
                    + "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"
                    + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "
                    + "inner join kelurahan inner join kecamatan inner join kabupaten "
                    + "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "
                    + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='" + TNoRM.getText() + "' ", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBarcodeRM10ActionPerformed

    private void ppDilayaniBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppDilayaniBtnPrintActionPerformed
        setStatusPasien("Dilayani",tbPetugas.getModel().getValueAt(tbPetugas.getSelectedRow(), 22).toString());
    }//GEN-LAST:event_ppDilayaniBtnPrintActionPerformed

    private void BtnKeluar6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluar6KeyPressed

    }//GEN-LAST:event_BtnKeluar6KeyPressed

    private void ppDiperiksaBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppDiperiksaBtnPrintActionPerformed
        setStatusPasien("Diperiksa","");
    }//GEN-LAST:event_ppDiperiksaBtnPrintActionPerformed

    private void MnPACSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPACSActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            var.setform("DlgReg");
            DlgCariPeriksaRadiologi dlgki = new DlgCariPeriksaRadiologi(null, false);
            dlgki.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            dlgki.setLocationRelativeTo(internalFrame1);
            dlgki.isCek();
            dlgki.SetNoRw(TNoRw.getText());
            dlgki.tampilOrthanc2();
            dlgki.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnPACSActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgReg dialog = new DlgReg(new javax.swing.JFrame(), true);
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
    private widget.TextBox AsalRujukan;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    public widget.Button BtnCari;
    private widget.Button BtnCloseIn6;
    private widget.Button BtnCloseIn7;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluar2;
    private widget.Button BtnKeluar4;
    private widget.Button BtnKeluar5;
    private widget.Button BtnKeluar6;
    private widget.Button BtnPanggil;
    private widget.Button BtnPasien;
    private widget.Button BtnPoli1;
    private widget.Button BtnPoli2;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint1;
    private widget.Button BtnPrint2;
    private widget.Button BtnPrint5;
    private widget.Button BtnSeek3;
    private widget.Button BtnSeek4;
    private widget.Button BtnSeek5;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpan2;
    private widget.Button BtnSimpan6;
    private widget.Button BtnSimpan7;
    private widget.Button BtnText;
    private widget.Button BtnUnit;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkJln;
    private widget.CekBox ChkSemua;
    private widget.CekBox ChkTracker;
    private widget.ComboBox CmbDetik;
    private widget.ComboBox CmbJam;
    private widget.ComboBox CmbMenit;
    private widget.TextBox CrDokter;
    private widget.TextBox CrDokter3;
    private widget.TextBox CrPoli;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPReg;
    private javax.swing.JDialog DlgCatatan;
    private javax.swing.JDialog DlgInterpretasiEKG;
    private javax.swing.JDialog DlgJawabanKonsul;
    private javax.swing.JDialog DlgSakit;
    private javax.swing.JDialog DlgSakit2;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Kategori;
    private widget.TextBox Kd2;
    private widget.TextBox KdPoli1;
    private widget.TextBox KdPoli2;
    private widget.Label LCount;
    private widget.Label LabelCatatan;
    private widget.Label LabelDiagnosa;
    private widget.Label LabelDokter;
    private widget.Label LabelDokterTujuan;
    public widget.Label LabelNama;
    private widget.Label LabelNoRM;
    private widget.Label LabelNoRW;
    private widget.Label LabelPoliPerujuk;
    private widget.Label LabelPoliTujuan;
    private javax.swing.JMenu MenuInputData;
    private javax.swing.JMenu MenuInputData1;
    private javax.swing.JMenuItem MnBarcode;
    private javax.swing.JMenuItem MnBarcode1;
    private javax.swing.JMenuItem MnBarcodeRM10;
    private javax.swing.JMenuItem MnBarcodeRM9;
    private javax.swing.JMenuItem MnBatal;
    private javax.swing.JMenuItem MnBelum;
    private javax.swing.JMenuItem MnBilling;
    private javax.swing.JMenuItem MnBilling1;
    private javax.swing.JMenuItem MnBillingParsial;
    private javax.swing.JMenuItem MnBlangkoResep;
    private javax.swing.JMenu MnBridging;
    private javax.swing.JMenuItem MnBuktiPelayananRalan;
    private javax.swing.JMenuItem MnCetakBebasNarkoba;
    private javax.swing.JMenuItem MnCetakRegister;
    private javax.swing.JMenuItem MnCetakSuratSakit;
    private javax.swing.JMenuItem MnCetakSuratSakit1;
    private javax.swing.JMenuItem MnCetakSuratSakit2;
    private javax.swing.JMenuItem MnCetakSuratSakit3;
    private javax.swing.JMenuItem MnCetakSuratSehat;
    private javax.swing.JMenuItem MnCetakSuratSehat1;
    private javax.swing.JMenuItem MnCetakSuratSehat2;
    private javax.swing.JMenuItem MnCheckList;
    private javax.swing.JMenuItem MnCheckList1;
    private javax.swing.JMenuItem MnCheckList2;
    private javax.swing.JMenuItem MnCheckList3;
    private javax.swing.JMenuItem MnCheckList4;
    private javax.swing.JMenuItem MnCheckList5;
    private javax.swing.JMenuItem MnCheckList6;
    private javax.swing.JMenuItem MnCheckList7;
    private javax.swing.JMenuItem MnDIrawat;
    private javax.swing.JMenuItem MnDiagnosa;
    private javax.swing.JMenuItem MnDiagnosa1;
    private javax.swing.JMenuItem MnDirujuk;
    private javax.swing.JMenuItem MnGelang1;
    private javax.swing.JMenu MnHapusData;
    private javax.swing.JMenuItem MnHapusObatOperasi;
    private javax.swing.JMenuItem MnHapusRujukan;
    private javax.swing.JMenuItem MnHapusTagihanOperasi;
    private javax.swing.JMenuItem MnHemodialisa;
    private javax.swing.JMenuItem MnInputHP;
    private javax.swing.JMenuItem MnInterpretasiEkg;
    private javax.swing.JMenuItem MnInterpretasiEkg2;
    private javax.swing.JMenuItem MnJKRA;
    private javax.swing.JMenuItem MnJadwalOperasi;
    private javax.swing.JMenuItem MnJadwalOperasi1;
    private javax.swing.JMenuItem MnJawabKonsul;
    private javax.swing.JMenuItem MnJawabanKonsul;
    private javax.swing.JMenuItem MnKamarInap;
    private javax.swing.JMenuItem MnKamarInap1;
    private javax.swing.JMenuItem MnKronis;
    private javax.swing.JMenuItem MnLabelTracker;
    private javax.swing.JMenuItem MnLabelTracker1;
    private javax.swing.JMenuItem MnLabelTracker2;
    private javax.swing.JMenuItem MnLabelTracker3;
    private javax.swing.JMenuItem MnLaporanRekapJenisBayar;
    private javax.swing.JMenuItem MnLaporanRekapKunjunganBulanan;
    private javax.swing.JMenuItem MnLaporanRekapKunjunganBulananPoli;
    private javax.swing.JMenuItem MnLaporanRekapKunjunganDokter;
    private javax.swing.JMenuItem MnLaporanRekapKunjunganPoli;
    private javax.swing.JMenuItem MnLaporanRekapPenyakitRalan;
    private javax.swing.JMenuItem MnLaporanRekapPerujuk;
    private javax.swing.JMenuItem MnLaporanRekapRawatDarurat;
    private javax.swing.JMenuItem MnLembarCasemix;
    private javax.swing.JMenuItem MnLembarCasemix1;
    private javax.swing.JMenuItem MnLembarCasemix2;
    private javax.swing.JMenuItem MnLembarRalan;
    private javax.swing.JMenu MnMCU;
    private javax.swing.JMenuItem MnMeninggal;
    private javax.swing.JMenuItem MnNapza6Param;
    private javax.swing.JMenuItem MnNoResep;
    private javax.swing.JMenuItem MnNoResep1;
    private javax.swing.JMenu MnObat;
    private javax.swing.JMenu MnObat1;
    private javax.swing.JMenuItem MnOperasi;
    private javax.swing.JMenuItem MnOperasi1;
    private javax.swing.JMenuItem MnPACS;
    private javax.swing.JMenuItem MnPaketHajiLaki;
    private javax.swing.JMenuItem MnPaketHajiPerempuan;
    private javax.swing.JMenuItem MnPaketMCU;
    private javax.swing.JMenuItem MnPaketMMPI;
    private javax.swing.JMenuItem MnPaketNapza;
    private javax.swing.JMenuItem MnPemberianObat;
    private javax.swing.JMenuItem MnPemberianObat1;
    private javax.swing.JMenuItem MnPeriksaLab;
    private javax.swing.JMenuItem MnPeriksaLab1;
    private javax.swing.JMenuItem MnPeriksaRadiologi;
    private javax.swing.JMenuItem MnPeriksaRadiologi1;
    private javax.swing.JMenu MnPermintaan;
    private javax.swing.JMenu MnPermintaan1;
    private javax.swing.JMenuItem MnPermintaanLab;
    private javax.swing.JMenuItem MnPermintaanLab1;
    private javax.swing.JMenuItem MnPermintaanRadiologi;
    private javax.swing.JMenuItem MnPermintaanRadiologi1;
    private javax.swing.JMenuItem MnPersetujuanMedis;
    private javax.swing.JMenu MnPilihBilling;
    private javax.swing.JMenuItem MnPoliInternal;
    private javax.swing.JMenuItem MnPrioritas;
    private javax.swing.JMenuItem MnPulangPaksa;
    private javax.swing.JMenuItem MnRawatJalan;
    private javax.swing.JMenuItem MnRawatJalan1;
    private javax.swing.JMenuItem MnResepDOkter;
    private javax.swing.JMenuItem MnResepDOkter1;
    private javax.swing.JMenuItem MnRiwayatObatRacikan;
    private javax.swing.JMenuItem MnRujuk;
    private javax.swing.JMenuItem MnRujukMasuk;
    private javax.swing.JMenuItem MnRujukSisrute;
    private javax.swing.JMenu MnRujukan;
    private javax.swing.JMenuItem MnSEP;
    private javax.swing.JMenuItem MnSJP;
    private javax.swing.JMenuItem MnSKDPBPJS;
    private javax.swing.JMenuItem MnSKDPBPJS1;
    private javax.swing.JMenuItem MnSPBK;
    private javax.swing.JMenuItem MnSPBK1;
    private javax.swing.JMenu MnSetPasien;
    private javax.swing.JMenu MnStatus;
    private javax.swing.JMenuItem MnStatusBaru;
    private javax.swing.JMenuItem MnStatusLama;
    private javax.swing.JMenuItem MnSudah;
    private javax.swing.JMenuItem MnSuratNapza;
    private javax.swing.JMenuItem MnTeridentifikasiTB;
    private javax.swing.JMenuItem MnTeridentifikasiTB1;
    private javax.swing.JMenu MnTindakan;
    private javax.swing.JMenu MnTindakan1;
    private javax.swing.JMenuItem MnUbahRujukan;
    private javax.swing.JMenu MnUrut;
    private javax.swing.JMenu MnUrut1;
    private javax.swing.JMenuItem MnUrutDokterAsc;
    private javax.swing.JMenuItem MnUrutDokterAsc1;
    private javax.swing.JMenuItem MnUrutDokterDesc;
    private javax.swing.JMenuItem MnUrutDokterDesc1;
    private javax.swing.JMenuItem MnUrutNoRawatAsc;
    private javax.swing.JMenuItem MnUrutNoRawatAsc1;
    private javax.swing.JMenuItem MnUrutNoRawatDesc;
    private javax.swing.JMenuItem MnUrutNoRawatDesc1;
    private javax.swing.JMenuItem MnUrutPenjabAsc;
    private javax.swing.JMenuItem MnUrutPenjabAsc1;
    private javax.swing.JMenuItem MnUrutPenjabDesc;
    private javax.swing.JMenuItem MnUrutPenjabDesc1;
    private javax.swing.JMenuItem MnUrutPoliklinikAsc;
    private javax.swing.JMenuItem MnUrutPoliklinikAsc1;
    private javax.swing.JMenuItem MnUrutPoliklinikDesc;
    private javax.swing.JMenuItem MnUrutPoliklinikDesc1;
    private javax.swing.JMenuItem MnUrutRegAsc1;
    private javax.swing.JMenuItem MnUrutRegDesc1;
    private javax.swing.JMenuItem MnUrutStatusAsc;
    private javax.swing.JMenuItem MnUrutStatusAsc1;
    private javax.swing.JMenuItem MnUrutStatusDesc;
    private javax.swing.JMenuItem MnUrutStatusDesc1;
    private javax.swing.JMenuItem MnUrutTanggalAsc;
    private javax.swing.JMenuItem MnUrutTanggalAsc1;
    private javax.swing.JMenuItem MnUrutTanggalDesc;
    private javax.swing.JMenuItem MnUrutTanggalDesc1;
    private widget.TextBox NmPoli1;
    private widget.TextBox NmPoli2;
    private widget.TextBox NoBalasan;
    private widget.TextBox NomorSurat;
    private widget.TextBox ObatKronis;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.TextBox TAlmt;
    private widget.TextBox TBiaya;
    private widget.TextBox TCari;
    private widget.TextBox TDokter;
    private widget.TextBox THbngn;
    private widget.TextArea TKesan;
    private widget.TextArea TKonsulJawab;
    private widget.TextBox TNoRM;
    public widget.TextBox TNoRMint;
    private widget.TextBox TNoReg;
    private widget.TextBox TNoRw;
    private widget.TextBox TNoRwint;
    private widget.TextBox TPasien;
    private widget.TextBox TPasienint;
    private widget.TextArea TPemeriksaanJawab;
    private widget.TextBox TPngJwb;
    private widget.TextBox TPoli;
    private widget.TextBox TRunningText;
    private widget.TextArea TSaranJawab;
    private widget.TextBox TStatus;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TglSakit1;
    private widget.Tanggal TglSakit2;
    public widget.TextBox Tind;
    private javax.swing.JDialog WindowRunningText;
    private javax.swing.JDialog WindowUbahRujukan;
    private widget.Button btnPenjab;
    private widget.Button btnPenjab1;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame11;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
    private widget.InternalFrame internalFrame7;
    private widget.InternalFrame internalFrame8;
    private widget.InternalFrame internalFrame9;
    private widget.Label jLabel10;
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
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel3;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel4;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator11;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private widget.TextBox kddokter;
    private widget.TextBox kdpnj;
    private widget.TextBox kdpoli;
    private widget.TextBox lmsakit;
    private widget.TextBox nmpnj;
    private widget.PanelBiasa panelBiasa2;
    private widget.PanelBiasa panelBiasa4;
    private widget.panelisi panelGlass6;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private javax.swing.JMenuItem ppBerkas;
    private javax.swing.JMenuItem ppBerkasDigital;
    private javax.swing.JMenuItem ppBerkasDigital1;
    private javax.swing.JMenuItem ppCatatanPasien;
    private javax.swing.JMenuItem ppDPJP;
    private javax.swing.JMenuItem ppDilayani;
    private javax.swing.JMenuItem ppDiperiksa;
    private javax.swing.JMenuItem ppIKP;
    private javax.swing.JMenuItem ppIKP1;
    private javax.swing.JMenuItem ppRiwayat;
    private javax.swing.JMenuItem ppRiwayat1;
    private javax.swing.JMenuItem ppSuratKontrol;
    private javax.swing.JMenuItem ppSuratPRI;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbPetugas;
    private widget.Table tbPetugas1;
    private widget.Table tbPetugas2;
    private widget.Table tbPetugas3;
    // End of variables declaration//GEN-END:variables
    public void tampil_ambil() {
        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement("select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"
                    + "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli,"
                    + "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab,pasien.no_tlp,reg_periksa.stts,reg_periksa.status_poli, "
                    + "reg_periksa.kd_poli,reg_periksa.kd_pj from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "
                    + "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli  where  "
                    + " poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  reg_periksa.no_reg like ? or "
                    + " poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  reg_periksa.no_rawat like ? or "
                    + " poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  reg_periksa.tgl_registrasi like ? or "
                    + " poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  reg_periksa.kd_dokter like ? or "
                    + " poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  dokter.nm_dokter like ? or "
                    + " poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  reg_periksa.no_rkm_medis like ? or "
                    + " poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  reg_periksa.stts_daftar like ? or "
                    + " poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  pasien.nm_pasien like ? or "
                    + " poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  poliklinik.nm_poli like ? or "
                    + " poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  reg_periksa.p_jawab like ? or "
                    + " poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  reg_periksa.almt_pj like ? or "
                    + " poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  reg_periksa.hubunganpj like ? or "
                    + " poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  penjab.png_jawab like ? order by " + order);
            try {
                ps.setString(1, "%" + CrPoli.getText() + "%");
                ps.setString(2, "%" + CrDokter.getText() + "%");
                ps.setString(3, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(4, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                ps.setString(6, "%" + CrPoli.getText() + "%");
                ps.setString(7, "%" + CrDokter.getText() + "%");
                ps.setString(8, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(9, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(10, "%" + TCari.getText().trim() + "%");
                ps.setString(11, "%" + CrPoli.getText() + "%");
                ps.setString(12, "%" + CrDokter.getText() + "%");
                ps.setString(13, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(14, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(15, "%" + TCari.getText().trim() + "%");
                ps.setString(16, "%" + CrPoli.getText() + "%");
                ps.setString(17, "%" + CrDokter.getText() + "%");
                ps.setString(18, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(19, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(20, "%" + TCari.getText().trim() + "%");
                ps.setString(21, "%" + CrPoli.getText() + "%");
                ps.setString(22, "%" + CrDokter.getText() + "%");
                ps.setString(23, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(24, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(25, "%" + TCari.getText().trim() + "%");
                ps.setString(26, "%" + CrPoli.getText() + "%");
                ps.setString(27, "%" + CrDokter.getText() + "%");
                ps.setString(28, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(29, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(30, "%" + TCari.getText().trim() + "%");
                ps.setString(31, "%" + CrPoli.getText() + "%");
                ps.setString(32, "%" + CrDokter.getText() + "%");
                ps.setString(33, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(34, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(35, "%" + TCari.getText().trim() + "%");
                ps.setString(36, "%" + CrPoli.getText() + "%");
                ps.setString(37, "%" + CrDokter.getText() + "%");
                ps.setString(38, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(39, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(40, "%" + TCari.getText().trim() + "%");
                ps.setString(41, "%" + CrPoli.getText() + "%");
                ps.setString(42, "%" + CrDokter.getText() + "%");
                ps.setString(43, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(44, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(45, "%" + TCari.getText().trim() + "%");
                ps.setString(46, "%" + CrPoli.getText() + "%");
                ps.setString(47, "%" + CrDokter.getText() + "%");
                ps.setString(48, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(49, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(50, "%" + TCari.getText().trim() + "%");
                ps.setString(51, "%" + CrPoli.getText() + "%");
                ps.setString(52, "%" + CrDokter.getText() + "%");
                ps.setString(53, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(54, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(55, "%" + TCari.getText().trim() + "%");
                ps.setString(56, "%" + CrPoli.getText() + "%");
                ps.setString(57, "%" + CrDokter.getText() + "%");
                ps.setString(58, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(59, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(60, "%" + TCari.getText().trim() + "%");
                ps.setString(61, "%" + CrPoli.getText() + "%");
                ps.setString(62, "%" + CrDokter.getText() + "%");
                ps.setString(63, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(64, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(65, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new Object[]{false, rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(17),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getString(14),
                        Valid.SetAngka(rs.getDouble(15)),
                        rs.getString(16),
                        rs.getString("no_tlp"),
                        rs.getString("stts"), rs.getString("status_poli"),
                        rs.getString("kd_poli"), rs.getString("kd_pj")
                    });
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

        LCount.setText("" + tabMode.getRowCount());
    }

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            limit = "";
            if (var.getkode().contains("unit")) {
                if (CrPoli.getText().equals("")) {
                    limit = " LIMIT 10";
                }
            } else if (var.getkode().equals("Admin Utama")) {
                if (CrPoli.getText().equals("")) {
                    limit = " LIMIT 10";
                    if (ChkSemua.isSelected() == true) {
                        limit = "";
                    }
                }
            } else {
                limit = "";
            }
            ps = koneksi.prepareStatement("select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"
                    + "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli,"
                    + "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab,pasien.no_tlp,reg_periksa.stts,reg_periksa.status_poli, "
                    + "reg_periksa.kd_poli,reg_periksa.kd_pj,pasien.alamat,"
                    + "booking_registrasi.limit_reg from booking_registrasi right join reg_periksa on booking_registrasi.no_rkm_medis=reg_periksa.no_rkm_medis AND booking_registrasi.tanggal_periksa=reg_periksa.tgl_registrasi "
                    + "inner join dokter inner join pasien inner join poliklinik inner join penjab "
                    + "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli  where  "
                    + " poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and "
                    + " (reg_periksa.no_reg like ? or reg_periksa.no_rawat like ? or reg_periksa.tgl_registrasi like ? or reg_periksa.kd_dokter like ? or dokter.nm_dokter like ? or "
                    + " reg_periksa.no_rkm_medis like ? or reg_periksa.stts_daftar like ? or pasien.nm_pasien like ? or poliklinik.nm_poli like ? or "
                    + " penjab.png_jawab like ?) order by " + order + limit);
            try {
                ps.setString(1, "%" + CrPoli.getText() + "%");
                ps.setString(2, "%" + CrDokter.getText() + "%");
                ps.setString(3, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(4, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                ps.setString(6, "%" + TCari.getText().trim() + "%");
                ps.setString(7, "%" + TCari.getText().trim() + "%");
                ps.setString(8, "%" + TCari.getText().trim() + "%");
                ps.setString(9, "%" + TCari.getText().trim() + "%");
                ps.setString(10, "%" + TCari.getText().trim() + "%");
                ps.setString(11, "%" + TCari.getText().trim() + "%");
                ps.setString(12, "%" + TCari.getText().trim() + "%");
                ps.setString(13, "%" + TCari.getText().trim() + "%");
                ps.setString(14, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new Object[]{false, rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10), rs.getString("alamat"),
                        rs.getString(11),
                        rs.getString(17),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getString(14),
                        Valid.SetAngka(rs.getDouble(15)),
                        rs.getString(16),
                        rs.getString("no_tlp"),
                        rs.getString("stts"), rs.getString("status_poli"),
                        rs.getString("kd_poli"), rs.getString("kd_pj"), "", rs.getString("limit_reg"), "",""
                    });
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

        LCount.setText("" + tabMode.getRowCount());
        cekSep();
        setketegori();
        setkronis();
    }

    private void tampil3() {
        // DefaultTableModel tabMode3;
        Valid.tabelKosong(tabMode3);
        try {
            ps = koneksi.prepareStatement("select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"
                    + "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli,"
                    + "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab,pasien.no_tlp,reg_periksa.stts,reg_periksa.status_poli, "
                    + "reg_periksa.kd_poli,reg_periksa.kd_pj from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "
                    + "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli  where   "
                    + " reg_periksa.stts='Belum' and reg_periksa.tgl_registrasi BETWEEN date_sub(?, INTERVAL 1 day) and date_sub(?, INTERVAL 1 day) and poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and  reg_periksa.no_reg like ? or "
                    + " reg_periksa.stts='Belum' and reg_periksa.tgl_registrasi BETWEEN date_sub(?, INTERVAL 1 day) and date_sub(?, INTERVAL 1 day) and poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and  reg_periksa.no_rawat like ? or "
                    + " reg_periksa.stts='Belum' and reg_periksa.tgl_registrasi BETWEEN date_sub(?, INTERVAL 1 day) and date_sub(?, INTERVAL 1 day) and poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and  reg_periksa.tgl_registrasi like ? or "
                    + " reg_periksa.stts='Belum' and reg_periksa.tgl_registrasi BETWEEN date_sub(?, INTERVAL 1 day) and date_sub(?, INTERVAL 1 day) and poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and  reg_periksa.kd_dokter like ? or "
                    + " reg_periksa.stts='Belum' and reg_periksa.tgl_registrasi BETWEEN date_sub(?, INTERVAL 1 day) and date_sub(?, INTERVAL 1 day) and poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and  dokter.nm_dokter like ? or "
                    + " reg_periksa.stts='Belum' and reg_periksa.tgl_registrasi BETWEEN date_sub(?, INTERVAL 1 day) and date_sub(?, INTERVAL 1 day) and poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and  reg_periksa.no_rkm_medis like ? or "
                    + " reg_periksa.stts='Belum' and reg_periksa.tgl_registrasi BETWEEN date_sub(?, INTERVAL 1 day) and date_sub(?, INTERVAL 1 day) and poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and  reg_periksa.stts_daftar like ? or "
                    + " reg_periksa.stts='Belum' and reg_periksa.tgl_registrasi BETWEEN date_sub(?, INTERVAL 1 day) and date_sub(?, INTERVAL 1 day) and poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and  pasien.nm_pasien like ? or "
                    + " reg_periksa.stts='Belum' and reg_periksa.tgl_registrasi BETWEEN date_sub(?, INTERVAL 1 day) and date_sub(?, INTERVAL 1 day) and poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and  poliklinik.nm_poli like ? or "
                    + " reg_periksa.stts='Belum' and reg_periksa.tgl_registrasi BETWEEN date_sub(?, INTERVAL 1 day) and date_sub(?, INTERVAL 1 day) and poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and  reg_periksa.p_jawab like ? or "
                    + " reg_periksa.stts='Belum' and reg_periksa.tgl_registrasi BETWEEN date_sub(?, INTERVAL 1 day) and date_sub(?, INTERVAL 1 day) and poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and  reg_periksa.almt_pj like ? or "
                    + " reg_periksa.stts='Belum' and reg_periksa.tgl_registrasi BETWEEN date_sub(?, INTERVAL 1 day) and date_sub(?, INTERVAL 1 day) and poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and  reg_periksa.hubunganpj like ? or "
                    + " reg_periksa.stts='Belum' and reg_periksa.tgl_registrasi BETWEEN date_sub(?, INTERVAL 1 day) and date_sub(?, INTERVAL 1 day) and poliklinik.kd_poli<>'IGDK' and poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and  penjab.png_jawab like ? order by " + order);
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, "%" + CrPoli.getText() + "%");
                ps.setString(4, "%" + CrDokter.getText() + "%");

                ps.setString(5, "%" + TCari.getText().trim() + "%");
                ps.setString(6, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(7, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));

                ps.setString(8, "%" + CrPoli.getText() + "%");
                ps.setString(9, "%" + CrDokter.getText() + "%");
                ps.setString(10, "%" + TCari.getText().trim() + "%");
                ps.setString(11, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(12, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));

                ps.setString(13, "%" + CrPoli.getText() + "%");
                ps.setString(14, "%" + CrDokter.getText() + "%");
                ps.setString(15, "%" + TCari.getText().trim() + "%");
                ps.setString(16, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(17, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));

                ps.setString(18, "%" + CrPoli.getText() + "%");
                ps.setString(19, "%" + CrDokter.getText() + "%");
                ps.setString(20, "%" + TCari.getText().trim() + "%");
                ps.setString(21, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(22, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));

                ps.setString(23, "%" + CrPoli.getText() + "%");
                ps.setString(24, "%" + CrDokter.getText() + "%");
                ps.setString(25, "%" + TCari.getText().trim() + "%");
                ps.setString(26, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(27, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));

                ps.setString(28, "%" + CrPoli.getText() + "%");
                ps.setString(29, "%" + CrDokter.getText() + "%");
                ps.setString(30, "%" + TCari.getText().trim() + "%");
                ps.setString(31, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(32, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));

                ps.setString(33, "%" + CrPoli.getText() + "%");
                ps.setString(34, "%" + CrDokter.getText() + "%");
                ps.setString(35, "%" + TCari.getText().trim() + "%");
                ps.setString(36, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(37, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));

                ps.setString(38, "%" + CrPoli.getText() + "%");
                ps.setString(39, "%" + CrDokter.getText() + "%");
                ps.setString(40, "%" + TCari.getText().trim() + "%");
                ps.setString(41, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(42, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));

                ps.setString(43, "%" + CrPoli.getText() + "%");
                ps.setString(44, "%" + CrDokter.getText() + "%");
                ps.setString(45, "%" + TCari.getText().trim() + "%");
                ps.setString(46, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(47, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));

                ps.setString(48, "%" + CrPoli.getText() + "%");
                ps.setString(49, "%" + CrDokter.getText() + "%");
                ps.setString(50, "%" + TCari.getText().trim() + "%");
                ps.setString(51, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(52, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));

                ps.setString(53, "%" + CrPoli.getText() + "%");
                ps.setString(54, "%" + CrDokter.getText() + "%");
                ps.setString(55, "%" + TCari.getText().trim() + "%");
                ps.setString(56, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(57, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));

                ps.setString(58, "%" + CrPoli.getText() + "%");
                ps.setString(59, "%" + CrDokter.getText() + "%");
                ps.setString(60, "%" + TCari.getText().trim() + "%");
                ps.setString(61, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(62, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));

                ps.setString(63, "%" + CrPoli.getText() + "%");
                ps.setString(64, "%" + CrDokter.getText() + "%");
                ps.setString(65, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode3.addRow(new Object[]{false, rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(17),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getString(14),
                        Valid.SetAngka(rs.getDouble(15)),
                        rs.getString(16),
                        rs.getString("no_tlp"),
                        rs.getString("stts"), rs.getString("status_poli"),
                        rs.getString("kd_poli"), rs.getString("kd_pj")
                    });
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

        LCount.setText("" + tabMode3.getRowCount());
    }

    private void tampil2() {
        Valid.tabelKosong(tabMode2);
        try {
            ps = koneksi.prepareStatement("select reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,a.nm_poli as poli_awal, "
                    + "rujukan_internal_poli.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli, "
                    + "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.stts_daftar,penjab.png_jawab,pasien.no_tlp,reg_periksa.stts,rujukan_internal_poli.kd_poli,reg_periksa.kd_pj "
                    + "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join poliklinik as a inner join penjab inner join rujukan_internal_poli "
                    + "on rujukan_internal_poli.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "and rujukan_internal_poli.no_rawat=reg_periksa.no_rawat "
                    + "and reg_periksa.kd_pj=penjab.kd_pj and rujukan_internal_poli.kd_poli=poliklinik.kd_poli and reg_periksa.kd_poli=a.kd_poli where  "
                    + " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  reg_periksa.no_reg like ? or "
                    + " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  reg_periksa.no_rawat like ? or "
                    + " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  reg_periksa.tgl_registrasi like ? or "
                    + " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  rujukan_internal_poli.kd_dokter like ? or "
                    + " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  dokter.nm_dokter like ? or "
                    + " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  reg_periksa.no_rkm_medis like ? or "
                    + " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  reg_periksa.stts_daftar like ? or "
                    + " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  pasien.nm_pasien like ? or "
                    + " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  poliklinik.nm_poli like ? or "
                    + " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  reg_periksa.p_jawab like ? or "
                    + " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  reg_periksa.almt_pj like ? or "
                    + " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  reg_periksa.hubunganpj like ? or "
                    + " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  penjab.png_jawab like ? order by " + order);
            try {
                ps.setString(1, "%" + CrPoli.getText() + "%");
                ps.setString(2, "%" + CrDokter.getText() + "%");
                ps.setString(3, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(4, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                ps.setString(6, "%" + CrPoli.getText() + "%");
                ps.setString(7, "%" + CrDokter.getText() + "%");
                ps.setString(8, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(9, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(10, "%" + TCari.getText().trim() + "%");
                ps.setString(11, "%" + CrPoli.getText() + "%");
                ps.setString(12, "%" + CrDokter.getText() + "%");
                ps.setString(13, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(14, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(15, "%" + TCari.getText().trim() + "%");
                ps.setString(16, "%" + CrPoli.getText() + "%");
                ps.setString(17, "%" + CrDokter.getText() + "%");
                ps.setString(18, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(19, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(20, "%" + TCari.getText().trim() + "%");
                ps.setString(21, "%" + CrPoli.getText() + "%");
                ps.setString(22, "%" + CrDokter.getText() + "%");
                ps.setString(23, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(24, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(25, "%" + TCari.getText().trim() + "%");
                ps.setString(26, "%" + CrPoli.getText() + "%");
                ps.setString(27, "%" + CrDokter.getText() + "%");
                ps.setString(28, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(29, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(30, "%" + TCari.getText().trim() + "%");
                ps.setString(31, "%" + CrPoli.getText() + "%");
                ps.setString(32, "%" + CrDokter.getText() + "%");
                ps.setString(33, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(34, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(35, "%" + TCari.getText().trim() + "%");
                ps.setString(36, "%" + CrPoli.getText() + "%");
                ps.setString(37, "%" + CrDokter.getText() + "%");
                ps.setString(38, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(39, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(40, "%" + TCari.getText().trim() + "%");
                ps.setString(41, "%" + CrPoli.getText() + "%");
                ps.setString(42, "%" + CrDokter.getText() + "%");
                ps.setString(43, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(44, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(45, "%" + TCari.getText().trim() + "%");
                ps.setString(46, "%" + CrPoli.getText() + "%");
                ps.setString(47, "%" + CrDokter.getText() + "%");
                ps.setString(48, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(49, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(50, "%" + TCari.getText().trim() + "%");
                ps.setString(51, "%" + CrPoli.getText() + "%");
                ps.setString(52, "%" + CrDokter.getText() + "%");
                ps.setString(53, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(54, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(55, "%" + TCari.getText().trim() + "%");
                ps.setString(56, "%" + CrPoli.getText() + "%");
                ps.setString(57, "%" + CrDokter.getText() + "%");
                ps.setString(58, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(59, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(60, "%" + TCari.getText().trim() + "%");
                ps.setString(61, "%" + CrPoli.getText() + "%");
                ps.setString(62, "%" + CrDokter.getText() + "%");
                ps.setString(63, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(64, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(65, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode2.addRow(new Object[]{false,
                        rs.getString("no_rawat"), rs.getString("tgl_registrasi"),
                        rs.getString("jam_reg"), rs.getString("kd_dokter"),
                        rs.getString("nm_dokter"), rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien"), rs.getString("jk"),
                        rs.getString("umur"), rs.getString("nm_poli"), rs.getString("poli_awal"),
                        rs.getString("png_jawab"), rs.getString("p_jawab"),
                        rs.getString("almt_pj"), rs.getString("hubunganpj"),
                        rs.getString("stts_daftar"), rs.getString("no_tlp"),
                        rs.getString("stts"), rs.getString("kd_poli"), rs.getString("kd_pj")
                    });
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

        LCount.setText("" + tabMode2.getRowCount());
    }

    private void tampil4() {
        Valid.tabelKosong(tabMode4);
        try {
            ps = koneksi.prepareStatement("select reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,a.nm_poli as poli_awal, "
                    + "rujukan_internal_poli.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli, "
                    + "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.stts_daftar,penjab.png_jawab,pasien.no_tlp,reg_periksa.stts,rujukan_internal_poli.kd_poli,reg_periksa.kd_pj "
                    + "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join poliklinik as a inner join penjab inner join rujukan_internal_poli "
                    + "on rujukan_internal_poli.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "and rujukan_internal_poli.no_rawat=reg_periksa.no_rawat "
                    + "and reg_periksa.kd_pj=penjab.kd_pj and rujukan_internal_poli.kd_poli=poliklinik.kd_poli and reg_periksa.kd_poli=a.kd_poli where  "
                    + " reg_periksa.stts='Belum' AND poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi BETWEEN date_sub(?, INTERVAL 1 day) and date_sub(?, INTERVAL 1 day) and  reg_periksa.no_reg like ? or "
                    + " reg_periksa.stts='Belum' AND poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi BETWEEN date_sub(?, INTERVAL 1 day) and date_sub(?, INTERVAL 1 day) and  reg_periksa.no_rawat like ? or "
                    + " reg_periksa.stts='Belum' AND poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi BETWEEN date_sub(?, INTERVAL 1 day) and date_sub(?, INTERVAL 1 day) and  reg_periksa.tgl_registrasi like ? or "
                    + " reg_periksa.stts='Belum' AND poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi BETWEEN date_sub(?, INTERVAL 1 day) and date_sub(?, INTERVAL 1 day) and  rujukan_internal_poli.kd_dokter like ? or "
                    + " reg_periksa.stts='Belum' AND poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi BETWEEN date_sub(?, INTERVAL 1 day) and date_sub(?, INTERVAL 1 day) and  dokter.nm_dokter like ? or "
                    + " reg_periksa.stts='Belum' AND poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi BETWEEN date_sub(?, INTERVAL 1 day) and date_sub(?, INTERVAL 1 day) and  reg_periksa.no_rkm_medis like ? or "
                    + " reg_periksa.stts='Belum' AND poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi BETWEEN date_sub(?, INTERVAL 1 day) and date_sub(?, INTERVAL 1 day) and  reg_periksa.stts_daftar like ? or "
                    + " reg_periksa.stts='Belum' AND poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi BETWEEN date_sub(?, INTERVAL 1 day) and date_sub(?, INTERVAL 1 day) and  pasien.nm_pasien like ? or "
                    + " reg_periksa.stts='Belum' AND poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi BETWEEN date_sub(?, INTERVAL 1 day) and date_sub(?, INTERVAL 1 day) and  poliklinik.nm_poli like ? or "
                    + " reg_periksa.stts='Belum' AND poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi BETWEEN date_sub(?, INTERVAL 1 day) and date_sub(?, INTERVAL 1 day) and  reg_periksa.p_jawab like ? or "
                    + " reg_periksa.stts='Belum' AND poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi BETWEEN date_sub(?, INTERVAL 1 day) and date_sub(?, INTERVAL 1 day) and  reg_periksa.almt_pj like ? or "
                    + " reg_periksa.stts='Belum' AND poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi BETWEEN date_sub(?, INTERVAL 1 day) and date_sub(?, INTERVAL 1 day) and  reg_periksa.hubunganpj like ? or "
                    + " reg_periksa.stts='Belum' AND poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi BETWEEN date_sub(?, INTERVAL 1 day) and date_sub(?, INTERVAL 1 day) and  penjab.png_jawab like ? order by " + order);
            try {
                ps.setString(1, "%" + CrPoli.getText() + "%");
                ps.setString(2, "%" + CrDokter.getText() + "%");
                ps.setString(3, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(4, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                ps.setString(6, "%" + CrPoli.getText() + "%");
                ps.setString(7, "%" + CrDokter.getText() + "%");
                ps.setString(8, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(9, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(10, "%" + TCari.getText().trim() + "%");
                ps.setString(11, "%" + CrPoli.getText() + "%");
                ps.setString(12, "%" + CrDokter.getText() + "%");
                ps.setString(13, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(14, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(15, "%" + TCari.getText().trim() + "%");
                ps.setString(16, "%" + CrPoli.getText() + "%");
                ps.setString(17, "%" + CrDokter.getText() + "%");
                ps.setString(18, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(19, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(20, "%" + TCari.getText().trim() + "%");
                ps.setString(21, "%" + CrPoli.getText() + "%");
                ps.setString(22, "%" + CrDokter.getText() + "%");
                ps.setString(23, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(24, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(25, "%" + TCari.getText().trim() + "%");
                ps.setString(26, "%" + CrPoli.getText() + "%");
                ps.setString(27, "%" + CrDokter.getText() + "%");
                ps.setString(28, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(29, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(30, "%" + TCari.getText().trim() + "%");
                ps.setString(31, "%" + CrPoli.getText() + "%");
                ps.setString(32, "%" + CrDokter.getText() + "%");
                ps.setString(33, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(34, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(35, "%" + TCari.getText().trim() + "%");
                ps.setString(36, "%" + CrPoli.getText() + "%");
                ps.setString(37, "%" + CrDokter.getText() + "%");
                ps.setString(38, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(39, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(40, "%" + TCari.getText().trim() + "%");
                ps.setString(41, "%" + CrPoli.getText() + "%");
                ps.setString(42, "%" + CrDokter.getText() + "%");
                ps.setString(43, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(44, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(45, "%" + TCari.getText().trim() + "%");
                ps.setString(46, "%" + CrPoli.getText() + "%");
                ps.setString(47, "%" + CrDokter.getText() + "%");
                ps.setString(48, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(49, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(50, "%" + TCari.getText().trim() + "%");
                ps.setString(51, "%" + CrPoli.getText() + "%");
                ps.setString(52, "%" + CrDokter.getText() + "%");
                ps.setString(53, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(54, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(55, "%" + TCari.getText().trim() + "%");
                ps.setString(56, "%" + CrPoli.getText() + "%");
                ps.setString(57, "%" + CrDokter.getText() + "%");
                ps.setString(58, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(59, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(60, "%" + TCari.getText().trim() + "%");
                ps.setString(61, "%" + CrPoli.getText() + "%");
                ps.setString(62, "%" + CrDokter.getText() + "%");
                ps.setString(63, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(64, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(65, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode4.addRow(new Object[]{false,
                        rs.getString("no_rawat"), rs.getString("tgl_registrasi"),
                        rs.getString("jam_reg"), rs.getString("kd_dokter"),
                        rs.getString("nm_dokter"), rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien"), rs.getString("jk"),
                        rs.getString("umur"), rs.getString("nm_poli"), rs.getString("poli_awal"),
                        rs.getString("png_jawab"), rs.getString("p_jawab"),
                        rs.getString("almt_pj"), rs.getString("hubunganpj"),
                        rs.getString("stts_daftar"), rs.getString("no_tlp"),
                        rs.getString("stts"), rs.getString("kd_poli"), rs.getString("kd_pj")
                    });
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

        LCount.setText("" + tabMode4.getRowCount());
    }

    public void emptTeks() {
        TNoReg.setText("");
        TNoRw.setText("");
        Kd2.setText("");
        //DTPReg.setDate(new Date());
        TNoRM.setText("");
        TPasien.setText("");
        TPngJwb.setText("");
        THbngn.setText("");
        TAlmt.setText("");
        TStatus.setText("");
        AsalRujukan.setText("");
        alamatperujuk = "";
        isNumber();
        TNoRM.requestFocus();
        kdpnj.setText("");
        nmpnj.setText("");
    }

    private void getData() {
        if (tbPetugas.getSelectedRow() != -1) {
            Kd2.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(), 2).toString());
            Valid.SetTgl(DTPReg, tbPetugas.getValueAt(tbPetugas.getSelectedRow(), 3).toString());
            CmbJam.setSelectedItem(tbPetugas.getValueAt(tbPetugas.getSelectedRow(), 4).toString().substring(0, 2));
            CmbMenit.setSelectedItem(tbPetugas.getValueAt(tbPetugas.getSelectedRow(), 4).toString().substring(3, 5));
            CmbDetik.setSelectedItem(tbPetugas.getValueAt(tbPetugas.getSelectedRow(), 4).toString().substring(6, 8));
            kddokter.setText(tbPetugas.getModel().getValueAt(tbPetugas.getSelectedRow(), 5).toString());
            TDokter.setText(tbPetugas.getModel().getValueAt(tbPetugas.getSelectedRow(), 6).toString());
            TNoRM.setText(tbPetugas.getModel().getValueAt(tbPetugas.getSelectedRow(), 7).toString());
            isCekPasien();
            TPoli.setText(tbPetugas.getModel().getValueAt(tbPetugas.getSelectedRow(), 12).toString());
            nmpnj.setText(tbPetugas.getModel().getValueAt(tbPetugas.getSelectedRow(), 13).toString());
            TPngJwb.setText(tbPetugas.getModel().getValueAt(tbPetugas.getSelectedRow(), 14).toString());
            TAlmt.setText(tbPetugas.getModel().getValueAt(tbPetugas.getSelectedRow(), 15).toString());
            THbngn.setText(tbPetugas.getModel().getValueAt(tbPetugas.getSelectedRow(), 16).toString());
            TBiaya.setText(tbPetugas.getModel().getValueAt(tbPetugas.getSelectedRow(), 17).toString());
            TStatus.setText(tbPetugas.getModel().getValueAt(tbPetugas.getSelectedRow(), 18).toString());
            kdpoli.setText(tbPetugas.getModel().getValueAt(tbPetugas.getSelectedRow(), 22).toString());
            kdpnj.setText(tbPetugas.getModel().getValueAt(tbPetugas.getSelectedRow(), 23).toString());
            Sequel.cariIsi("select perujuk from rujuk_masuk where no_rawat=?", AsalRujukan, tbPetugas.getModel().getValueAt(tbPetugas.getSelectedRow(), 2).toString());
            TNoRw.setText(tbPetugas.getModel().getValueAt(tbPetugas.getSelectedRow(), 2).toString());
            TNoReg.setText(tbPetugas.getModel().getValueAt(tbPetugas.getSelectedRow(), 1).toString());
            Kategori.setText(tbPetugas.getModel().getValueAt(tbPetugas.getSelectedRow(), 26).toString());
            ObatKronis.getText();
        }
    }

    private void jam() {
        ActionListener taskPerformer = new ActionListener() {
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;

            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";

                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if (ChkJln.isSelected() == true) {
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                } else if (ChkJln.isSelected() == false) {
                    nilai_jam = CmbJam.getSelectedIndex();
                    nilai_menit = CmbMenit.getSelectedIndex();
                    nilai_detik = CmbDetik.getSelectedIndex();
                }

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
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                CmbJam.setSelectedItem(jam);
                CmbMenit.setSelectedItem(menit);
                CmbDetik.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }

    private void isPas() {
        if (validasiregistrasi.equals("Yes")) {
            if (Sequel.cariInteger("select count(no_rkm_medis) from reg_periksa where no_rkm_medis=? and status_bayar='Belum Bayar' and stts<>'Batal'", TNoRM.getText()) > 0) {
                String tgl = Sequel.cariIsi("SELECT tgl_registrasi FROM reg_periksa WHERE no_rkm_medis=? and status_bayar='Belum Bayar' and stts<>'Batal' LIMIT 1", TNoRM.getText());
                JOptionPane.showMessageDialog(rootPane, "Maaf, pasien pada kunjungan sebelumnya memiliki tagihan yang belum di closing di tanggal " + Valid.SetTgl3(tgl) + ".\nSilahkan konfirmasi dengan pihak kasir.. !!");
            } else {
                if (validasicatatan.equals("Yes")) {
                    if (Sequel.cariInteger("select count(no_rkm_medis) from catatan_pasien where no_rkm_medis=?", TNoRM.getText()) > 0) {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        catatan.setNoRm(TNoRM.getText());
                        catatan.setSize(720, 330);
                        catatan.setLocationRelativeTo(internalFrame1);
                        catatan.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                }
                isCekPasien();
            }
        } else {
            if (validasicatatan.equals("Yes")) {
                if (Sequel.cariInteger("select count(no_rkm_medis) from catatan_pasien where no_rkm_medis=?", TNoRM.getText()) > 0) {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    catatan.setNoRm(TNoRM.getText());
                    catatan.setSize(720, 330);
                    catatan.setLocationRelativeTo(internalFrame1);
                    catatan.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
            isCekPasien();
        }
    }

    private void isCekPasien() {
        try {
            ps3 = koneksi.prepareStatement("select nm_pasien,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) asal,"
                    + "namakeluarga,keluarga,pasien.kd_pj,penjab.png_jawab,if(tgl_daftar=?,'Baru','Lama') as daftar, no_peserta, "
                    + "TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()) as tahun, "
                    + "(TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12)) as bulan, "
                    + "TIMESTAMPDIFF(DAY, DATE_ADD(DATE_ADD(tgl_lahir,INTERVAL TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()) YEAR), INTERVAL TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12) MONTH), CURDATE()) as hari from pasien "
                    + "inner join kelurahan inner join kecamatan inner join kabupaten inner join penjab "
                    + "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_pj=penjab.kd_pj "
                    + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "
                    + "where pasien.no_rkm_medis=?");
            try {
                ps3.setString(1, Valid.SetTgl(DTPReg.getSelectedItem() + ""));
                ps3.setString(2, TNoRM.getText());
                rs = ps3.executeQuery();
                while (rs.next()) {
                    TAlmt.setText(rs.getString("asal"));
                    TPngJwb.setText(rs.getString("namakeluarga"));
                    THbngn.setText(rs.getString("keluarga"));
                    kdpnj.setText(rs.getString("kd_pj"));
                    nmpnj.setText(rs.getString("png_jawab"));
                    TStatus.setText(rs.getString("daftar"));
                    umur = "0";
                    sttsumur = "Th";
                    if (rs.getInt("tahun") > 0) {
                        umur = rs.getString("tahun");
                        sttsumur = "Th";
                    } else if (rs.getInt("tahun") == 0) {
                        if (rs.getInt("bulan") > 0) {
                            umur = rs.getString("bulan");
                            sttsumur = "Bl";
                        } else if (rs.getInt("bulan") == 0) {
                            umur = rs.getString("hari");
                            sttsumur = "Hr";
                        }
                    }

                    TPasien.setText(rs.getString("nm_pasien") + " (" + umur + " " + sttsumur + ")");
                    switch (rs.getString("daftar")) {
                        case "Baru":
                            Sequel.cariIsi("select registrasi from poliklinik where kd_poli=?", TBiaya, kdpoli.getText());
                            break;
                        case "Lama":
                            Sequel.cariIsi("select registrasilama from poliklinik where kd_poli=?", TBiaya, kdpoli.getText());
                            break;
                        default:
                            Sequel.cariIsi("select registrasi from poliklinik where kd_poli=?", TBiaya, kdpoli.getText());
                            break;
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex);
            } finally {
                if (rs != null) {
                    rs.close();
                }

                if (ps3 != null) {
                    ps3.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public JTextField getTextField() {
        return TNoRw;
    }

    public JButton getButton() {
        return BtnKeluar;
    }

    private void isForm() {
        if (ChkInput.isSelected() == true) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 188));
            FormInput.setVisible(true);
            ChkInput.setVisible(true);
        } else if (ChkInput.isSelected() == false) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 20));
            FormInput.setVisible(false);
            ChkInput.setVisible(true);
        }
    }

    public void isCek() {
        DTPReg.setDate(new Date());
        DTPCari1.setDate(new Date());
        DTPCari2.setDate(new Date());
        BtnPrint.setEnabled(var.getregistrasi());
        MnOperasi.setEnabled(var.getoperasi());
        MnOperasi1.setEnabled(var.getoperasi());
        MnKamarInap.setEnabled(var.getkamar_inap());
        MnKamarInap1.setEnabled(var.getkamar_inap());
        MnRawatJalan.setEnabled(var.gettindakan_ralan());
        MnRawatJalan1.setEnabled(var.gettindakan_ralan());
        MnPemberianObat.setEnabled(var.getberi_obat());
        MnPemberianObat1.setEnabled(var.getberi_obat());
        MnBilling.setEnabled(var.getbilling_ralan());
        MnPeriksaLab.setEnabled(var.getperiksa_lab());
        MnPeriksaLab1.setEnabled(var.getperiksa_lab());
        MnPeriksaRadiologi.setEnabled(var.getperiksa_radiologi());
        MnPeriksaRadiologi1.setEnabled(var.getperiksa_radiologi());
        MnNoResep.setEnabled(var.getresep_obat());
        MnRujuk.setEnabled(var.getrujukan_keluar());
        MnRujukMasuk.setEnabled(var.getrujukan_masuk());
        MnDiagnosa.setEnabled(var.getdiagnosa_pasien());
        MnHapusTagihanOperasi.setEnabled(var.getoperasi());
        MnHapusObatOperasi.setEnabled(var.getoperasi());
        MnSEP.setEnabled(var.getbpjs_sep());
        MnSJP.setEnabled(var.getinhealth_sjp());
        ppRiwayat.setEnabled(var.getresume_pasien());
        ppCatatanPasien.setEnabled(var.getcatatan_pasien());
        MnPoliInternal.setEnabled(var.getrujukan_poli_internal());
        MnHapusRujukan.setEnabled(var.getrujukan_poli_internal());
        ppIKP.setEnabled(var.getinsiden_keselamatan_pasien());
        ppIKP1.setEnabled(var.getinsiden_keselamatan_pasien());
        ppBerkasDigital.setEnabled(var.getberkas_digital_perawatan());
        ppBerkasDigital1.setEnabled(var.getberkas_digital_perawatan());
        MnJadwalOperasi.setEnabled(var.getbooking_operasi());
        MnBillingParsial.setEnabled(var.getbilling_parsial());
        MnSKDPBPJS.setEnabled(var.getskdp_bpjs());
        MnPermintaanLab.setEnabled(var.getpermintaan_lab());
        MnPermintaanRadiologi.setEnabled(var.getpermintaan_radiologi());
        MnJadwalOperasi1.setEnabled(var.getbooking_operasi());
        MnSKDPBPJS1.setEnabled(var.getskdp_bpjs());
        MnPermintaanLab1.setEnabled(var.getpermintaan_lab());
        MnPermintaanRadiologi1.setEnabled(var.getpermintaan_radiologi());
        MnTeridentifikasiTB.setEnabled(var.getkemenkes_sitt());
        ChkSemua.setVisible(false);
        if (var.getkode().equals("Admin Utama") || var.getkode().equals("unit20")) {
            ChkSemua.setVisible(true);
            MnPrioritas.setVisible(true);
            MnKronis.setVisible(true);
            BtnHapus.setText("Hapus");
            BtnHapus.setEnabled(true);
            BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png")));
            
            BtnEdit.setText("Ganti");
            BtnEdit.setEnabled(true);
            BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png")));
            
            BtnBatal.setEnabled(true);
            BtnBatal.setText("Baru");
            BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
            
            BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png")));
            BtnSimpan.setEnabled(true);
            BtnSimpan.setText("Simpan");
            
        }else if (var.getkode().contains("unit")) {
            MnPrioritas.setVisible(false);
            MnKronis.setVisible(true);
            
            BtnHapus.setText("Sudah");
            BtnHapus.setEnabled(true);
            BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png")));
            
            BtnEdit.setText("Dilayani");
            BtnEdit.setEnabled(true);
            BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png")));
            
            BtnBatal.setText("CPPT");
            BtnBatal.setEnabled(true);
            BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png")));
            
            BtnSimpan.setEnabled(true);
            BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png")));
            BtnSimpan.setText("Diterima");
            
        } else {
            MnPrioritas.setVisible(true);
            MnKronis.setVisible(false);
            
            BtnHapus.setText("Hapus");
            BtnHapus.setEnabled(var.getregistrasi());
            BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png")));
            
            BtnEdit.setText("Ganti");
            BtnEdit.setEnabled(var.getregistrasi());
            BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png")));
            
            BtnBatal.setText("Baru");
            BtnBatal.setEnabled(var.getregistrasi());
            BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N

            BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png")));
            BtnSimpan.setText("Simpan");
            BtnSimpan.setEnabled(var.getregistrasi());
        }
    }

    private void isNumber() {
        if (BASENOREG.equals("booking")) {
            switch (URUTNOREG) {
                case "poli":
                    if (Sequel.cariInteger("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_poli='" + kdpoli.getText() + "' and tanggal_periksa='" + Valid.SetTgl(DTPReg.getSelectedItem() + "") + "'")
                            >= Sequel.cariInteger("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_poli='" + kdpoli.getText() + "' and tgl_registrasi='" + Valid.SetTgl(DTPReg.getSelectedItem() + "") + "'")) {
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_poli='" + kdpoli.getText() + "' and tanggal_periksa='" + Valid.SetTgl(DTPReg.getSelectedItem() + "") + "'", "", 3, TNoReg);
                    } else {
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_poli='" + kdpoli.getText() + "' and tgl_registrasi='" + Valid.SetTgl(DTPReg.getSelectedItem() + "") + "'", "", 3, TNoReg);
                    }
                    break;
                case "dokter":
                    if (Sequel.cariInteger("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_dokter='" + kddokter.getText() + "' and tanggal_periksa='" + Valid.SetTgl(DTPReg.getSelectedItem() + "") + "'")
                            >= Sequel.cariInteger("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_dokter='" + kddokter.getText() + "' and tgl_registrasi='" + Valid.SetTgl(DTPReg.getSelectedItem() + "") + "'")) {
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_dokter='" + kddokter.getText() + "' and tanggal_periksa='" + Valid.SetTgl(DTPReg.getSelectedItem() + "") + "'", "", 3, TNoReg);
                    } else {
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_dokter='" + kddokter.getText() + "' and tgl_registrasi='" + Valid.SetTgl(DTPReg.getSelectedItem() + "") + "'", "", 3, TNoReg);
                    }
                    break;
                case "dokter + poli":
                    if (Sequel.cariInteger("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_dokter='" + kddokter.getText() + "' and kd_poli='" + kdpoli.getText() + "' and tanggal_periksa='" + Valid.SetTgl(DTPReg.getSelectedItem() + "") + "'")
                            >= Sequel.cariInteger("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_dokter='" + kddokter.getText() + "' and kd_poli='" + kdpoli.getText() + "' and tgl_registrasi='" + Valid.SetTgl(DTPReg.getSelectedItem() + "") + "'")) {
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_dokter='" + kddokter.getText() + "' and kd_poli='" + kdpoli.getText() + "' and tanggal_periksa='" + Valid.SetTgl(DTPReg.getSelectedItem() + "") + "'", "", 3, TNoReg);
                    } else {
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_dokter='" + kddokter.getText() + "' and kd_poli='" + kdpoli.getText() + "' and tgl_registrasi='" + Valid.SetTgl(DTPReg.getSelectedItem() + "") + "'", "", 3, TNoReg);
                    }
                    break;
                default:
                    if (Sequel.cariInteger("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_poli='" + kdpoli.getText() + "' and tanggal_periksa='" + Valid.SetTgl(DTPReg.getSelectedItem() + "") + "'")
                            >= Sequel.cariInteger("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_poli='" + kdpoli.getText() + "' and tgl_registrasi='" + Valid.SetTgl(DTPReg.getSelectedItem() + "") + "'")) {
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_poli='" + kdpoli.getText() + "' and tanggal_periksa='" + Valid.SetTgl(DTPReg.getSelectedItem() + "") + "'", "", 3, TNoReg);
                    } else {
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_poli='" + kdpoli.getText() + "' and tgl_registrasi='" + Valid.SetTgl(DTPReg.getSelectedItem() + "") + "'", "", 3, TNoReg);
                    }
                    break;
            }
        } else {
            switch (URUTNOREG) {
                case "poli":
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_poli='" + kdpoli.getText() + "' and tgl_registrasi='" + Valid.SetTgl(DTPReg.getSelectedItem() + "") + "'", "", 3, TNoReg);
                    break;
                case "dokter":
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_dokter='" + kddokter.getText() + "' and tgl_registrasi='" + Valid.SetTgl(DTPReg.getSelectedItem() + "") + "'", "", 3, TNoReg);
                    break;
                case "dokter + poli":
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_dokter='" + kddokter.getText() + "' and kd_poli='" + kdpoli.getText() + "' and tgl_registrasi='" + Valid.SetTgl(DTPReg.getSelectedItem() + "") + "'", "", 3, TNoReg);
                    break;
                default:
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_dokter='" + kddokter.getText() + "' and tgl_registrasi='" + Valid.SetTgl(DTPReg.getSelectedItem() + "") + "'", "", 3, TNoReg);
                    break;
            }
        }
        if (var.getkode().equals("Admin Utama")) {
            Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_rawat,6),signed)),0) from reg_periksa where tgl_registrasi='" + Valid.SetTgl(DTPReg.getSelectedItem() + "") + "' ", dateformat.format(DTPReg.getDate()) + "/", 6, TNoRw);
        } else {
            if (Kd2.getText().equals("")) {
                Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_rawat,6),signed)),0) from reg_periksa where tgl_registrasi='" + Valid.SetTgl(DTPReg.getSelectedItem() + "") + "' ", dateformat.format(DTPReg.getDate()) + "/", 6, TNoRw);
            }
        }
    }

    private void UpdateUmur() {
        Sequel.mengedit("pasien", "no_rkm_medis=?", "umur=CONCAT(CONCAT(CONCAT(TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()), ' Th '),CONCAT(TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12), ' Bl ')),CONCAT(TIMESTAMPDIFF(DAY, DATE_ADD(DATE_ADD(tgl_lahir,INTERVAL TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()) YEAR), INTERVAL TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12) MONTH), CURDATE()), ' Hr'))", 1, new String[]{TNoRM.getText()});
    }
    
    private void trySimpan(){
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();
        LocalDate dateAfter = LocalDate.parse(Valid.SetTgl(DTPReg.getSelectedItem() + ""));
        long daysBetween = DAYS.between(today, dateAfter);
        int day = 0;
        long days = new Long(day);
        int ob = Long.compare(days, daysBetween);
//        System.out.println(ob);
        if (TNoReg.getText().trim().equals("")) {
            Valid.textKosong(TNoReg, "No.Registrasi");
        } else if (TNoRw.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
        } else if (TDokter.getText().trim().equals("")) {
            Valid.textKosong(kddokter, "dokter");
        } else if (TNoRM.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRM, "pasien");
        } else if (TPoli.getText().trim().equals("")) {
            Valid.textKosong(kdpoli, "poliklinik");
        } else if (TBiaya.getText().trim().equals("")) {
            Valid.textKosong(TBiaya, "biaya registrasi");
        } else if (kdpnj.getText().trim().equals("") || nmpnj.getText().trim().equals("")) {
            Valid.textKosong(kdpnj, "Jenis Bayar");
        } else if (Sequel.cariInteger("select count(pasien.no_rkm_medis) from pasien inner join reg_periksa inner join kamar_inap "
                + "on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat "
                + "where kamar_inap.stts_pulang='-' and pasien.no_rkm_medis=?", TNoRM.getText()) > 0) {
            JOptionPane.showMessageDialog(null, "Pasien sedang dalam masa perawatan di kamar inap..!!");
            TNoRM.requestFocus();
        } else {
//            if (var.getkode().equals("Admin Utama")) {
//                isRegistrasi();
//            } else {
            if (aktifjadwal.equals("aktif")) {
                if (Sequel.cariInteger("select count(no_rawat) from reg_periksa where kd_dokter='" + kddokter.getText() + "' and tgl_registrasi='" + Valid.SetTgl(DTPReg.getSelectedItem() + "") + "' ") >= kuota) {
                    JOptionPane.showMessageDialog(null, "Eiiits, Kuota registrasi penuh..!!!");
                    TCari.requestFocus();
                } else {
                    isRegistrasi();
                }
            } else {
                if (ob == -1) {
                    JOptionPane.showMessageDialog(null, "Mohon maaf, tidak bisa dilakukan pendaftaran selain hari ini .");
                    System.out.println("Gagal Simpan Tanggal Lebih Dari Hari Ini");
                } else {
                    isRegistrasi();
                }
            }
//            }
        }
    }
    
    private void setStatusPasien(String status,String kd_poli){
        if (TNoRw.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
        } else {
            if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", TNoRw.getText()) > 0) {
                JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            } else {
                if (status.equals("Sudah")) {
                    Valid.editTable(tabMode, "reg_periksa", "no_rawat", TNoRw, "stts='"+status+"'");
                    if (tabMode.getRowCount() != 0) {
                        simpanBerkas("");
                        tampil();
                    }
                }
                if (status.equals("Dilayani")) {
                    String no_rawatcari = "",no_rkm_mediscari = "";
                    no_rawatcari = Sequel.cariIsi("SELECT no_rawat FROM reg_periksa WHERE tgl_registrasi = ? AND kd_poli = '"+kd_poli+"' AND stts = 'Dilayani'", Valid.SetTgl(DTPReg.getSelectedItem() + ""));
                    no_rkm_mediscari = Sequel.cariIsi("SELECT no_rkm_medis FROM reg_periksa WHERE tgl_registrasi = ? AND kd_poli = '"+kd_poli+"' AND stts = 'Dilayani'", Valid.SetTgl(DTPReg.getSelectedItem() + ""));
                    Sequel.mengedit("reg_periksa", "no_rawat = '"+no_rawatcari+"'", "stts='Sudah'");

                    Valid.editTable(tabMode, "reg_periksa", "no_rawat", TNoRw, "stts='"+status+"'");
                    if (tabMode.getRowCount() != 0) {
                        simpanBerkas(no_rkm_mediscari);
                        tampil();
                    }
                }
                if (status.equals("Diterima")) {
                    Sequel.menyimpan("mutasi_berkas", "'" + TNoRw.getText() + "','Sudah Diterima',now(),now(),'0000-00-00 00:00:00','0000-00-00 00:00:00'", "status='Sudah Diterima',diterima=now()", "no_rawat='" + TNoRw.getText() + "'");
                    Valid.editTable(tabMode, "reg_periksa", "no_rawat", TNoRw, "stts='Berkas Diterima'");
                    if (tabMode.getRowCount() != 0) {
                        tampil();
                    }
                }
                if (status.equals("Diperiksa")) {
                    Valid.editTable(tabMode, "reg_periksa", "no_rawat", TNoRw, "stts='Diperiksa'");
                    if (tabMode.getRowCount() != 0) {
                        tampil();
                    }
                }
            }

        }
    }

    private void isRegistrasi() {
        ceksukses = false;
        status = "Baru";
        if (Sequel.cariInteger("select count(no_rkm_medis) from reg_periksa where no_rkm_medis=? and kd_poli=?", TNoRM.getText(), kdpoli.getText()) > 0) {
            status = "Lama";
        }

        if (Sequel.menyimpantf2("reg_periksa", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "No.Rawat", 19,
                new String[]{TNoReg.getText(), TNoRw.getText(), Valid.SetTgl(DTPReg.getSelectedItem() + ""), CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem(),
                    kddokter.getText(), TNoRM.getText(), kdpoli.getText(), TPngJwb.getText(), TAlmt.getText(), THbngn.getText(), TBiaya.getText(), "Belum",
                    TStatus.getText(), "Ralan", kdpnj.getText(), umur, sttsumur, "Belum Bayar", status}) == true) {
            ceksukses = true;
        } else {
            isNumber();
            if (Sequel.menyimpantf2("reg_periksa", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "No.Rawat", 19,
                    new String[]{TNoReg.getText(), TNoRw.getText(), Valid.SetTgl(DTPReg.getSelectedItem() + ""), CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem(),
                        kddokter.getText(), TNoRM.getText(), kdpoli.getText(), TPngJwb.getText(), TAlmt.getText(), THbngn.getText(), TBiaya.getText(), "Belum",
                        TStatus.getText(), "Ralan", kdpnj.getText(), umur, sttsumur, "Belum Bayar", status}) == true) {
                ceksukses = true;
            } else {
                isNumber();
                if (Sequel.menyimpantf2("reg_periksa", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "No.Rawat", 19,
                        new String[]{TNoReg.getText(), TNoRw.getText(), Valid.SetTgl(DTPReg.getSelectedItem() + ""), CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem(),
                            kddokter.getText(), TNoRM.getText(), kdpoli.getText(), TPngJwb.getText(), TAlmt.getText(), THbngn.getText(), TBiaya.getText(), "Belum",
                            TStatus.getText(), "Ralan", kdpnj.getText(), umur, sttsumur, "Belum Bayar", status}) == true) {
                    ceksukses = true;
                } else {
                    isNumber();
                    if (Sequel.menyimpantf2("reg_periksa", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "No.Rawat", 19,
                            new String[]{TNoReg.getText(), TNoRw.getText(), Valid.SetTgl(DTPReg.getSelectedItem() + ""), CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem(),
                                kddokter.getText(), TNoRM.getText(), kdpoli.getText(), TPngJwb.getText(), TAlmt.getText(), THbngn.getText(), TBiaya.getText(), "Belum",
                                TStatus.getText(), "Ralan", kdpnj.getText(), umur, sttsumur, "Belum Bayar", status}) == true) {
                        ceksukses = true;
                    } else {
                        isNumber();
                        if (Sequel.menyimpantf("reg_periksa", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "No.Rawat", 19,
                                new String[]{TNoReg.getText(), TNoRw.getText(), Valid.SetTgl(DTPReg.getSelectedItem() + ""), CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem(),
                                    kddokter.getText(), TNoRM.getText(), kdpoli.getText(), TPngJwb.getText(), TAlmt.getText(), THbngn.getText(), TBiaya.getText(), "Belum",
                                    TStatus.getText(), "Ralan", kdpnj.getText(), umur, sttsumur, "Belum Bayar", status}) == true) {
                            ceksukses = true;
                        } else {
                            TNoRM.requestFocus();
                            isNumber();
                        }
                    }
                }
            }
        }

        if (ceksukses == true) {
            UpdateUmur();
            if (!AsalRujukan.getText().equals("")) {
                Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(rujuk_masuk.no_rawat,4),signed)),0) from reg_periksa inner join rujuk_masuk on reg_periksa.no_rawat=rujuk_masuk.no_rawat where reg_periksa.tgl_registrasi='" + Valid.SetTgl(DTPReg.getSelectedItem() + "") + "' ", "BR/" + dateformat.format(DTPReg.getDate()) + "/", 4, NoBalasan);

                if (nosisrute.equals("")) {
                    Sequel.menyimpan2("rujuk_masuk", "'" + TNoRw.getText() + "','" + AsalRujukan.getText() + "','" + alamatperujuk + "','-','0','" + AsalRujukan.getText() + "','-','-','-','" + NoBalasan.getText() + "'", "No.Rujuk");
                } else {
                    Sequel.menyimpan2("rujuk_masuk", "'" + TNoRw.getText() + "','" + AsalRujukan.getText() + "','" + alamatperujuk + "','" + nosisrute + "','0','" + AsalRujukan.getText() + "','-','-','-','" + NoBalasan.getText() + "'", "No.Rujuk");
                    nosisrute = "";
                }
            }
            if (ChkTracker.isSelected() == true) {

            }
            emptTeks();
            if (TabRawat.getSelectedIndex() == 0) {
                tampil();
            }
        }
    }

    private void billingprasial() {
        if (tbPetugas.getSelectedRow() != -1) {
            jmlparsial = 0;
            if (aktifkanparsial.equals("yes")) {
                jmlparsial = Sequel.cariInteger("select count(kd_pj) from set_input_parsial where kd_pj=?", tbPetugas.getValueAt(tbPetugas.getSelectedRow(), 22).toString());
            }
            if (jmlparsial > 0) {
                DlgBilingParsialRalan parsialralan = new DlgBilingParsialRalan(null, false);
                parsialralan.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                parsialralan.setLocationRelativeTo(internalFrame1);
                //parsialralan.emptTeks();
                parsialralan.isCek();
                parsialralan.setNoRm(TNoRw.getText(), kddokter.getText(), TDokter.getText(), kdpoli.getText());
                parsialralan.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Maaf, Cara bayar " + tbPetugas.getValueAt(tbPetugas.getSelectedRow(), 12).toString() + " tidak diijinkan menggunakan Billing Parsial...!!!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }
    }

    public void SetPasien(String norm, String nosisrute, String FaskesAsal) {
        ChkInput.setSelected(true);
        isForm();
        TNoRM.setText(norm);
        this.nosisrute = nosisrute;
        AsalRujukan.setText(FaskesAsal);
        isPas();
    }

    public void setPasien(String NamaPasien, String Kontak, String Alamat, String TempatLahir, String TglLahir,
            String JK, String NoKartuJKN, String NIK, String nosisrute, String FaskesAsal) {
        var.setform("DlgReg");
        ChkInput.setSelected(true);
        isForm();
        pasien.emptTeks();
        pasien.isCek();
        this.nosisrute = nosisrute;
        AsalRujukan.setText(FaskesAsal);
        pasien.setPasien(NamaPasien, Kontak, Alamat, TempatLahir, TglLahir, JK, NoKartuJKN, NIK);
        pasien.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        pasien.setLocationRelativeTo(internalFrame1);
        pasien.setVisible(true);
    }

    private void simpanBerkas(String no_rkm_medis) {
        if (no_rkm_medis.equals("")) {
            no_rkm_medis = tbPetugas.getValueAt(tbPetugas.getSelectedRow(), 7).toString();
        }
        Sequel.mengedit("peminjaman_berkas", "no_rkm_medis=? and tgl_pinjam=? and id_ruang=?", "tgl_kembali=?,status_pinjam=?", 5, new String[]{
            tbPetugas.getValueAt(tbPetugas.getSelectedRow(), 3).toString(), "Sudah Kembali",
            no_rkm_medis, tbPetugas.getValueAt(tbPetugas.getSelectedRow(), 3).toString(), "RI033"
        });
    }
    
    private void bukaPeriksa(){
        if (TabRawat.getSelectedIndex() == 0) {
            if (tabMode.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
                TNoReg.requestFocus();
            } else if (TPasien.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
                tbPetugas.requestFocus();
            } else {
                if (tbPetugas.getSelectedRow() != -1) {
                    if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", TNoRw.getText()) > 0) {
                        JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                    } else {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        DlgRawatJalan dlgrwjl = new DlgRawatJalan(null, false);
                        dlgrwjl.isCek();
                        dlgrwjl.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                        dlgrwjl.setLocationRelativeTo(internalFrame1);
                        dlgrwjl.SetPoli(tbPetugas.getModel().getValueAt(tbPetugas.getSelectedRow(), 21).toString());
                        dlgrwjl.SetPj(tbPetugas.getModel().getValueAt(tbPetugas.getSelectedRow(), 22).toString());
                        dlgrwjl.setNoRm(TNoRw.getText(), DTPCari1.getDate(), DTPCari2.getDate());
                        dlgrwjl.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                }
            }
        } else if (TabRawat.getSelectedIndex() == 2) {
            if (tabMode3.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
                TNoReg.requestFocus();
            } else if (TPasien.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
                tbPetugas1.requestFocus();
            } else {
                if (tbPetugas1.getSelectedRow() != -1) {
                    if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", TNoRw.getText()) > 0) {
                        JOptionPane.showMessageDialog(null, "Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                    } else {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        DlgRawatJalan dlgrwjl = new DlgRawatJalan(null, false);
                        dlgrwjl.isCek();
                        dlgrwjl.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                        dlgrwjl.setLocationRelativeTo(internalFrame1);
                        dlgrwjl.SetPoli(tbPetugas1.getModel().getValueAt(tbPetugas1.getSelectedRow(), 21).toString());
                        dlgrwjl.SetPj(tbPetugas1.getModel().getValueAt(tbPetugas1.getSelectedRow(), 22).toString());
                        dlgrwjl.setNoRm(TNoRw.getText(), DTPCari1.getDate(), DTPCari2.getDate());
                        dlgrwjl.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                }
            }
        }
    }
    
    private void editRow(){
        if (TNoReg.getText().trim().equals("")) {
            Valid.textKosong(TNoReg, "No.Regristrasi");
        } else if (TNoRw.getText().trim().equals("")) {
            Valid.textKosong(TNoRw, "No.Rawat");
        } else if (TDokter.getText().trim().equals("")) {
            Valid.textKosong(kddokter, "dokter");
        } else if (TNoRM.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRM, "pasien");
        } else if (TPoli.getText().trim().equals("")) {
            Valid.textKosong(kdpoli, "poliklinik");
        } else if (TBiaya.getText().trim().equals("")) {
            Valid.textKosong(TBiaya, "biaya regristrasi");
        } else {
            if (tbPetugas.getSelectedRow() > -1) {
                if (var.getedit_registrasi() == true) {
                    Sequel.queryu2("update reg_periksa set no_rawat=?,no_reg=?,tgl_registrasi=?,jam_reg=?,kd_dokter=?,no_rkm_medis=?,kd_poli=?,"
                            + "p_jawab=?,almt_pj=?,biaya_reg=?,hubunganpj=?,stts_daftar=?,kd_pj=?,umurdaftar=?,sttsumur=? where no_rawat=?", 16,
                            new String[]{TNoRw.getText(), TNoReg.getText(), Valid.SetTgl(DTPReg.getSelectedItem() + ""), CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem(),
                                kddokter.getText(), TNoRM.getText(), kdpoli.getText(), TPngJwb.getText(), TAlmt.getText(), TBiaya.getText(), THbngn.getText(),
                                TStatus.getText(), kdpnj.getText(), umur, sttsumur, tbPetugas.getValueAt(tbPetugas.getSelectedRow(), 2).toString()
                            });
                } else {
                    if ((Sequel.cariInteger("select count(no_rawat) from rawat_jl_dr where no_rawat=?", TNoRw.getText()) > 0)
                            || (Sequel.cariInteger("select count(no_rawat) from rawat_jl_pr where no_rawat=?", TNoRw.getText()) > 0)
                            || (Sequel.cariInteger("select count(no_rawat) from rawat_jl_drpr where no_rawat=?", TNoRw.getText()) > 0)
                            || (Sequel.cariInteger("select count(no_rawat) from periksa_lab where no_rawat=?", TNoRw.getText()) > 0)
                            || (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", TNoRw.getText()) > 0)) {
                        JOptionPane.showMessageDialog(rootPane, "Maaf pasien sudah ada transaksi sebelumnya & tidak bisa diedit..!!! ");
                        TCari.requestFocus();
                    } else {
                        Sequel.queryu2("update reg_periksa set no_rawat=?,no_reg=?,tgl_registrasi=?,jam_reg=?,kd_dokter=?,no_rkm_medis=?,kd_poli=?,"
                                + "p_jawab=?,almt_pj=?,biaya_reg=?,hubunganpj=?,stts_daftar=?,kd_pj=?,umurdaftar=?,sttsumur=? where no_rawat=?", 16,
                                new String[]{TNoRw.getText(), TNoReg.getText(), Valid.SetTgl(DTPReg.getSelectedItem() + ""), CmbJam.getSelectedItem() + ":" + CmbMenit.getSelectedItem() + ":" + CmbDetik.getSelectedItem(),
                                    kddokter.getText(), TNoRM.getText(), kdpoli.getText(), TPngJwb.getText(), TAlmt.getText(), TBiaya.getText(), THbngn.getText(),
                                    TStatus.getText(), kdpnj.getText(), umur, sttsumur, tbPetugas.getValueAt(tbPetugas.getSelectedRow(), 2).toString()
                                });
                    }
                }

                tampil();
                emptTeks();
            }
        }
    }
    
    private void hapusRow(){
        for (i = 0; i < tbPetugas.getRowCount(); i++) {
            if (tbPetugas.getValueAt(i, 0).toString().equals("true")) {
                Sequel.meghapus("reg_periksa", "no_rawat", tbPetugas.getValueAt(i, 2).toString());
                if (var.getkode().equals("Admin Utama")) {
                    Sequel.meghapus("nota_inap", "no_rawat", tbPetugas.getValueAt(i, 2).toString());
                    Sequel.meghapus("nota_jalan", "no_rawat", tbPetugas.getValueAt(i, 2).toString());
                }
            }
        }
        tampil();
        emptTeks();
    }
    
    private void bukaBridging(){
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            var.setform("DlgReg");
            BPJSDataSEP dlgki = new BPJSDataSEP(null, false);
            dlgki.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            dlgki.setLocationRelativeTo(internalFrame1);
            dlgki.isCek();
            dlgki.setNoRm2(TNoRw.getText(), DTPReg.getDate(), "2. Ralan", kdpoli.getText(), TPoli.getText(), kddokter.getText());
            dlgki.setVisible(true);
            if ((Sequel.cariInteger("select count(no_rawat) from bridging_sep where no_rawat=?", TNoRw.getText()) > 0)) {
                dlgki.getTab(TNoRw.getText());
            }
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void cekSep() {
        String sep = "";
        for (int j = 0; j < tbPetugas.getRowCount(); j++) {
            try {
                if (tbPetugas.getValueAt(j, 13).toString().equals("BPJS")) {
                    sep = Sequel.cariIsi("SELECT no_sep FROM bridging_sep WHERE no_rawat = ?", tbPetugas.getValueAt(j, 2).toString());
                    tbPetugas.setValueAt(sep, j, 24);
                } else {
                    sep = "-";
                    tbPetugas.setValueAt(sep, j, 24);
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        }
    }

    private void getDataPL() {
        if (tbPetugas1.getSelectedRow() != -1) {
            Kd2.setText(tbPetugas1.getValueAt(tbPetugas1.getSelectedRow(), 2).toString());
            Valid.SetTgl(DTPReg, tbPetugas1.getValueAt(tbPetugas1.getSelectedRow(), 3).toString());
            CmbJam.setSelectedItem(tbPetugas1.getValueAt(tbPetugas1.getSelectedRow(), 4).toString().substring(0, 2));
            CmbMenit.setSelectedItem(tbPetugas1.getValueAt(tbPetugas1.getSelectedRow(), 4).toString().substring(3, 5));
            CmbDetik.setSelectedItem(tbPetugas1.getValueAt(tbPetugas1.getSelectedRow(), 4).toString().substring(6, 8));
            kddokter.setText(tbPetugas1.getModel().getValueAt(tbPetugas1.getSelectedRow(), 5).toString());
            TDokter.setText(tbPetugas1.getModel().getValueAt(tbPetugas1.getSelectedRow(), 6).toString());
            TNoRM.setText(tbPetugas1.getModel().getValueAt(tbPetugas1.getSelectedRow(), 7).toString());
            isCekPasien();
            TPoli.setText(tbPetugas1.getModel().getValueAt(tbPetugas1.getSelectedRow(), 11).toString());
            nmpnj.setText(tbPetugas1.getModel().getValueAt(tbPetugas1.getSelectedRow(), 12).toString());
            TPngJwb.setText(tbPetugas1.getModel().getValueAt(tbPetugas1.getSelectedRow(), 13).toString());
            TAlmt.setText(tbPetugas1.getModel().getValueAt(tbPetugas1.getSelectedRow(), 14).toString());
            THbngn.setText(tbPetugas1.getModel().getValueAt(tbPetugas1.getSelectedRow(), 15).toString());
            TBiaya.setText(tbPetugas1.getModel().getValueAt(tbPetugas1.getSelectedRow(), 16).toString());
            TStatus.setText(tbPetugas1.getModel().getValueAt(tbPetugas1.getSelectedRow(), 17).toString());
            kdpoli.setText(tbPetugas1.getModel().getValueAt(tbPetugas1.getSelectedRow(), 21).toString());
            kdpnj.setText(tbPetugas1.getModel().getValueAt(tbPetugas1.getSelectedRow(), 22).toString());
            Sequel.cariIsi("select perujuk from rujuk_masuk where no_rawat=?", AsalRujukan, tbPetugas1.getModel().getValueAt(tbPetugas1.getSelectedRow(), 2).toString());
            TNoRw.setText(tbPetugas1.getModel().getValueAt(tbPetugas1.getSelectedRow(), 2).toString());
            TNoReg.setText(tbPetugas1.getModel().getValueAt(tbPetugas1.getSelectedRow(), 1).toString());
        }
    }
    
    private void setketegori() {
        int row = 0;
        int j;
        String ktg;
        row = tbPetugas.getRowCount();
        for (j = 0; j < row; j++) {
            try {
                ps = koneksi.prepareStatement("select kategori from kategori_pasien_igd where no_rawat=? ");
                ktg = "";
                try {
                    ps.setString(1, tbPetugas.getValueAt(j, 2).toString());
                    rs3 = ps.executeQuery();
                    while (rs3.next()) {
                        ktg = rs3.getString("kategori");
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : " + e);
                } finally {
                    if (rs3 != null) {
                        rs3.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }
                }
                tbPetugas.setValueAt(ktg, j, 26);
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        }
    }
    
    private void setkronis() {
        int row = 0;
        int j;
        String ktg;
        row = tbPetugas.getRowCount();
        for (j = 0; j < row; j++) {
            try {
                ps = koneksi.prepareStatement("select no_rawat from mlite_veronisa where no_rawat=? ");
                ktg = "";
                try {
                    ps.setString(1, tbPetugas.getValueAt(j, 2).toString());
                    rs3 = ps.executeQuery();
                    while (rs3.next()) {
                        ktg ="KRONIS";
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : " + e);
                } finally {
                    if (rs3 != null) {
                        rs3.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }
                }
                tbPetugas.setValueAt(ktg, j, 27);
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        }
    }
    
    private String cekBidang(String nik){
        String cari = Sequel.cariIsi("SELECT bidang FROM pegawai WHERE nik = ?",nik);
        return cari;
    }
}
