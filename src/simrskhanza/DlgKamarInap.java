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

import permintaan.DlgBookingOperasi;
import inventory.DlgResepObat;
import laporan.DlgDataHAIs;
import laporan.DlgDataIDO;
import bridging.BPJSDataSEP;
import bridging.BPJSNik;
import bridging.BPJSPeserta;
import bridging.DlgDataTB;
import bridging.DlgSKDPBPJS;
import bridging.SisruteRujukanKeluar;
import laporan.DlgDiagnosaPenyakit;
import informasi.InformasiAnalisaKamin;
import keuangan.DlgKamar;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import inventory.DlgInputStokPasien;
import inventory.DlgPeresepanDokter;
import inventory.DlgReturJual;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.DlgBilingRanap;
import keuangan.DlgLhtPiutang;
import laporan.DlgBerkasRawat;
import laporan.DlgDataInsidenKeselamatan;
import permintaan.DlgPermintaanLaboratorium;
import permintaan.DlgPermintaanRadiologi;
import askep.DlgAskep;
import bridging.BPJSSPRI;
import bridging.BPJSSuratKontrol;
import bridging.SirsApi;
import bridging.SirsLaporanCovid19V3;
import java.awt.Color;
import java.awt.Component;
import javax.swing.table.DefaultTableCellRenderer;
import mpp.DlgSkriningMPP;
import permintaan.DlgPermintaanKerohanian;
import permintaan.DlgPermintaanUTD;
import rekammedis.RMPenilaianAwalKeperawatanRanap;
import rekammedis.RMTransferPasienAntarRuang;
import surat.SuratKeluar;

/**
 *
 * @author perpustakaan
 */
public class DlgKamarInap extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private final Properties prop = new Properties();
    public DlgIKBBayi ikb = new DlgIKBBayi(null, false);

    public DlgKamar kamar = new DlgKamar(null, false);
    public DlgReg reg = new DlgReg(null, false);
    public DlgBilingRanap billing = new DlgBilingRanap(null, false);
    public DlgDiagnosaPenyakit diagnosa = new DlgDiagnosaPenyakit(null, false);
    public DlgDiagnosaPenyakit diagnosa1 = new DlgDiagnosaPenyakit(null, false);
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Date date = new Date();
    private String now = dateFormat.format(date), kmr = "", key = "", tglmasuk, jammasuk, kd_pj,
            hariawal = "", pilihancetak = "", nonota = "", aktifkan_hapus_data_salah = "";
    private PreparedStatement ps, ps1, pssetjam, pscaripiutang, psdiagnosa, psibu, psanak, pstarif, psdpjp, pscariumur,ps2,ps3,ps4;
    private ResultSet rs, rs1, rs2, rssetjam,rs3,rs4;
    private int i, sudah = 0, row = 0;
    private double lama = 0, persenbayi = 0;
    private String gabungkan = "", norawatgabung = "", kamaryangdigabung = "", dokterranap = "", bangsal = "", diagnosa_akhir = "", namakamar = "", umur = "0", sttsumur = "Th", dx1 = "";

    /**
     * Creates new form DlgKamarInap
     *
     * @param parent
     * @param modal
     */
    public DlgKamarInap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        WindowCetakBerkas.setSize(670, 350);
        tabMode = new DefaultTableModel(null, new Object[]{
            "No.Rawat", "Nomer RM", "Nama Pasien", "Alamat Pasien", "No. Telp", "Penanggung Jawab", "Hubungan P.J.", "Jenis Bayar", "Kamar", "Tarif Kamar",
            "Diagnosa Awal", "Diagnosa Akhir", "Tgl.Masuk", "Jam Masuk", "Tgl.Keluar", "Jam Keluar",
            "Ttl.Biaya", "Stts.Pulang", "Lama", "Dokter P.J.", "Kamar", "Status Bayar","Kode Pj"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbKamIn.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbKamIn.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbKamIn.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 23; i++) {
            TableColumn column = tbKamIn.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(105);
            } else if (i == 1) {
                column.setPreferredWidth(70);
            } else if (i == 2) {
                column.setPreferredWidth(170);
            } else if (i == 3) {
                column.setPreferredWidth(150);
            } else if (i == 4) {
                column.setPreferredWidth(80);
            } else if (i == 5) {
                column.setPreferredWidth(120);
            } else if (i == 6) {
                column.setPreferredWidth(80);
            } else if (i == 7) {
                column.setPreferredWidth(160);
            } else if (i == 8) {
                column.setPreferredWidth(100);
            } else if (i == 9) {
                column.setPreferredWidth(70);
            } else if (i == 10) {
                column.setPreferredWidth(90);
            } else if (i == 11) {
                column.setPreferredWidth(70);
            } else if (i == 12) {
                column.setPreferredWidth(60);
            } else if (i == 13) {
                column.setPreferredWidth(70);
            } else if (i == 14) {
                column.setPreferredWidth(60);
            } else if (i == 15) {
                column.setPreferredWidth(80);
            } else if (i == 16) {
                column.setPreferredWidth(75);
            } else if (i == 17) {
                column.setPreferredWidth(100);
            } else if (i == 18) {
                column.setPreferredWidth(40);
            } else if (i == 19) {
                column.setPreferredWidth(140);
            } else if (i == 21) {
                column.setPreferredWidth(70);
            } else {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbKamIn.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

                String sep = (String) table.getModel().getValueAt(row, 7);
                String norawat = (String) table.getModel().getValueAt(row, 0);
                String kdpj = (String) table.getModel().getValueAt(row, 22);
                String kamar = (String) table.getModel().getValueAt(row, 20);
                String stts_pulang = (String) table.getModel().getValueAt(row, 17);                
                int hitungkamar = Sequel.cariInteger("SELECT count(kamar_inap.kd_kamar) FROM kamar_inap,kamar,bangsal where kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal and kamar_inap.kd_kamar='"+kamar+"' and kamar_inap.stts_pulang='-'");
                if (var.getkode().equals("unit28")) {
                    if (!sep.contains("1708") && "BPJ".equals(kdpj) && !"".equals(norawat)) {
                        setBackground(new Color(153, 0, 76));
                        setForeground(Color.WHITE);
                        if (isSelected) {
                            setForeground(Color.CYAN);
                        }                    
                    } else {
                        if (row % 2 == 1) {
                            setForeground(Color.BLACK);
                            setBackground(new Color(226, 234, 248));
                        } else {
                            setForeground(Color.BLACK);
                            setBackground(new Color(255, 255, 255));
                        }
                        if (isSelected) {
                            setForeground(Color.RED);
                        }
                    }
                } else {
                    if (hitungkamar > 1 && !sep.contains("1708") && "BPJ".equals(kdpj) && !"".equals(norawat)) {
                        setBackground(new Color(242, 141, 195));
                        setForeground(Color.WHITE);
                        if (isSelected) {
                            setForeground(Color.MAGENTA);
                        }
                    } else if (!sep.contains("1708") && "BPJ".equals(kdpj) && !"".equals(norawat)) {
                        setBackground(new Color(153, 0, 76));
                        setForeground(Color.WHITE);
                        if (isSelected) {
                            setForeground(Color.CYAN);
                        }
                    } else if (hitungkamar > 1) {
                        setBackground(new Color(94, 93, 93));
                        setForeground(Color.WHITE);
                        if (isSelected) {
                            setForeground(Color.CYAN);
                        }
                    } else {
                        if (row % 2 == 1) {
                            setForeground(Color.BLACK);
                            setBackground(new Color(226, 234, 248));
                        } else {
                            setForeground(Color.BLACK);
                            setBackground(new Color(255, 255, 255));
                        }
                        if (isSelected) {
                            setForeground(Color.RED);
                        }
                    }
                }                
                return this;
            }
        });

        norawat.setDocument(new batasInput((byte) 17).getKata(norawat));
        kdkamar.setDocument(new batasInput((byte) 15).getKata(kdkamar));
        kdkamarpindah.setDocument(new batasInput((byte) 15).getKata(kdkamarpindah));
        ttlbiaya.setDocument(new batasInput((byte) 25).getKata(ttlbiaya));
        diagnosaawal.setDocument(new batasInput((byte) 100).getKata(diagnosaawal));
        diagnosaakhir.setDocument(new batasInput((byte) 100).getKata(diagnosaakhir));
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

        TJmlHari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isjml();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isjml();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                isjml();
            }
        });

        TTarif.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isjml();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isjml();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                isjml();
            }
        });

        TTarifpindah.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isjml();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isjml();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                isjml();
            }
        });

        Valid.LoadTahun(CmbTahun);
        Valid.LoadTahun(CmbTahunpindah);

        WindowInputKamar.setSize(675, 275);
        WindowInputKamar.setLocationRelativeTo(null);
        WindowPindahKamar.setSize(675, 285);
        WindowCaraBayar.setSize(630, 80);
        WindowRanapGabung.setSize(630, 120);

        CmbTahun.setSelectedItem(now.substring(0, 4));
        CmbBln.setSelectedItem(now.substring(5, 7));
        CmbTgl.setSelectedItem(now.substring(8, 10));
        cmbJam.setSelectedItem(now.substring(11, 13));
        cmbMnt.setSelectedItem(now.substring(14, 16));
        cmbDtk.setSelectedItem(now.substring(17, 19));

        reg.pasien.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("DlgKamarInap")) {
                    if (reg.pasien.getTable().getSelectedRow() != -1) {
                        NoRmBayi.setText(reg.pasien.getTable().getValueAt(reg.pasien.getTable().getSelectedRow(), 1).toString());
                        NmBayi.setText(reg.pasien.getTable().getValueAt(reg.pasien.getTable().getSelectedRow(), 2).toString());
                    }
                    NoRmBayi.requestFocus();
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

        reg.pasien.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (var.getform().equals("DlgKamarInap")) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        reg.pasien.dispose();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        ikb.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("DlgKamarInap")) {
                    if (ikb.getTable().getSelectedRow() != -1) {
                        NoRmBayi.setText(ikb.getTable().getValueAt(ikb.getTable().getSelectedRow(), 0).toString());
                        NmBayi.setText(ikb.getTable().getValueAt(ikb.getTable().getSelectedRow(), 1).toString());
                    }
                    NoRmBayi.requestFocus();
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

        ikb.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (var.getform().equals("DlgKamarInap")) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        ikb.dispose();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        diagnosa1.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("DlgKamarInap")) {
                    String dxa = "";
                    for (i = 0; i < diagnosa1.panelDiagnosa1.getTable().getRowCount(); i++) {
                        if (diagnosa1.panelDiagnosa1.getTable().getValueAt(i, 0).toString().equals("true")) {
                            dxa = diagnosa1.panelDiagnosa1.getTable().getValueAt(i, 1).toString() + ", " + dxa;
                        }
                    }
                    diagnosaawal.setText(dxa);
                    diagnosaawal.requestFocus();
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

        kamar.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("DlgKamarInap")) {
                    if (kamar.getTable().getSelectedRow() != -1) {
                        kdkamar.setText(kamar.getTable().getValueAt(kamar.getTable().getSelectedRow(), 1).toString());
                        kdkamarpindah.setText(kamar.getTable().getValueAt(kamar.getTable().getSelectedRow(), 1).toString());
                        isKmr();
                        if ((WindowInputKamar.isVisible() == true) && (!TBangsal.getText().equals("")) && (!norawat.getText().equals(""))) {
                            if (TIn.getText().equals("")) {
                                tglmasuk = CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem();
                                jammasuk = cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem();
                            } else {
                                tglmasuk = TIn.getText();
                                jammasuk = JamMasuk.getText();
                            }
                            if (hariawal.equals("Yes")) {
                                Sequel.cariIsi("select (if(to_days('" + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem() + " " + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "')-to_days('" + tglmasuk + " " + jammasuk + "')=0,if(time_to_sec('" + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem() + " " + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "')-time_to_sec('" + tglmasuk + " " + jammasuk + "')>(3600*" + lama + "),1,0),to_days('" + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem() + " " + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "')-to_days('" + tglmasuk + " " + jammasuk + "'))+1) as lama", TJmlHari);
                            } else {
                                Sequel.cariIsi("select if(to_days('" + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem() + " " + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "')-to_days('" + tglmasuk + " " + jammasuk + "')=0,if(time_to_sec('" + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem() + " " + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "')-time_to_sec('" + tglmasuk + " " + jammasuk + "')>(3600*" + lama + "),1,0),to_days('" + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem() + " " + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "')-to_days('" + tglmasuk + " " + jammasuk + "')) as lama", TJmlHari);
                            }
                        }
                    }
                    kdkamar.requestFocus();
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

        diagnosa.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("DlgKamarInap")) {
//                    try {
//                        key = "";
//                        psdiagnosa = koneksi.prepareStatement("select kd_penyakit from diagnosa_pasien where no_rawat=? order by prioritas asc");
//                        try {
//                            psdiagnosa.setString(1, norawat.getText());
//                            rs = psdiagnosa.executeQuery();
//                            while (rs.next()) {
//                                key = rs.getString(1) + ", " + key;
//                            }
//                        } catch (Exception ex) {
//                            System.out.println("Notifikasi : " + ex);
//                        } finally {
//                            if (rs != null) {
//                                rs.close();
//                            }
//                            if (psdiagnosa != null) {
//                                psdiagnosa.close();
//                            }
//                        }
//                    } catch (Exception ex) {
//                        System.out.println(ex);
//                    }

                    if (WindowInputKamar.isVisible() == true) {                        
//                        diagnosaakhir.setText(diagnosa.panelDiagnosa1.getTable().getValueAt(diagnosa.panelDiagnosa1.getTable().getSelectedRow(), 11).toString()+" - "+diagnosa.panelDiagnosa1.getTable().getValueAt(diagnosa.panelDiagnosa1.getTable().getSelectedRow(), 12).toString());
                        diagnosaakhir.setText(diagnosa.panelDiagnosa1.tbDiagnosaPasien.getValueAt(diagnosa.panelDiagnosa1.tbDiagnosaPasien.getSelectedRow(), 11).toString()+" - "+diagnosa.panelDiagnosa1.tbDiagnosaPasien.getValueAt(diagnosa.panelDiagnosa1.tbDiagnosaPasien.getSelectedRow(), 12).toString());
                        diagnosaakhir.requestFocus();
                    } else if (WindowInputKamar.isVisible() == false) {
//                        Sequel.mengedit("kamar_inap", "no_rawat='" + norawat.getText() + "' and kd_kamar='" + kdkamar.getText() + "' and tgl_masuk='" + TIn.getText() + "' and jam_masuk='" + JamMasuk.getText() + "'", "diagnosa_akhir='" + key + "'");
                        tampil();
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

        kamar.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (var.getform().equals("DlgKamarInap")) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        kamar.dispose();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        kamar.bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("DlgKamarInap")) {
                    if (kamar.bangsal.getTable().getSelectedRow() != -1) {
                        BangsalCari.setText(kamar.bangsal.getTable().getValueAt(kamar.bangsal.getTable().getSelectedRow(), 1).toString());
                    }
                    BangsalCari.requestFocus();
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

        reg.pasien.penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("DlgKamarInap")) {
                    if (reg.pasien.penjab.getTable().getSelectedRow() != -1) {
                        kdpenjab.setText(reg.pasien.penjab.getTable().getValueAt(reg.pasien.penjab.getTable().getSelectedRow(), 1).toString());
                        nmpenjab.setText(reg.pasien.penjab.getTable().getValueAt(reg.pasien.penjab.getTable().getSelectedRow(), 2).toString());
                    }
                    kdpenjab.requestFocus();
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

        reg.pasien.penjab.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (var.getform().equals("DlgKamarInap")) {
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        reg.pasien.penjab.dispose();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        billing.rawatinap.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("DlgKamarInap")) {
                    tbKamIn.requestFocus();
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

        reg.dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("DlgKamarInap")) {
                    if (reg.dokter.getTable().getSelectedRow() != -1) {
                        CrDokter3.setText(reg.dokter.getTable().getValueAt(reg.dokter.getTable().getSelectedRow(), 1).toString());
                        CrDokter3.requestFocus();
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

        reg.getButton().addActionListener((ActionEvent e) -> {
            if (var.getform().equals("DlgKamarInap")) {
                norawat.setText(reg.getTextField().getText());
                Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ", TNoRM, norawat.getText());
                Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ", TPasien, TNoRM.getText());
            }
        });

        try {
            pssetjam = koneksi.prepareStatement("select * from set_jam_minimal");
            try {
                rssetjam = pssetjam.executeQuery();
                while (rssetjam.next()) {
                    lama = rssetjam.getDouble("lamajam");
                    persenbayi = rssetjam.getDouble("bayi");
                    diagnosa_akhir = rssetjam.getString("diagnosaakhir");
                    hariawal = rssetjam.getString("hariawal");
                    aktifkan_hapus_data_salah = rssetjam.getString("aktifkan_hapus_data_salah");
                }
            } catch (Exception e) {
                System.out.println("Set Kamar Inap : " + e);
            } finally {
                if (rssetjam != null) {
                    rssetjam.close();
                }
                if (pssetjam != null) {
                    pssetjam.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Set Kamar Inap : " + e);
        }

        try {
            if (diagnosa_akhir.equals("Yes")) {
                diagnosaakhir.setEditable(true);
            } else {
                diagnosaakhir.setEditable(false);
            }
        } catch (Exception e) {
            diagnosaakhir.setEditable(false);
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

        WindowInputKamar = new javax.swing.JDialog();
        internalFrame2 = new widget.InternalFrame();
        norawat = new widget.TextBox();
        TPasien = new widget.TextBox();
        kdkamar = new widget.TextBox();
        jLabel10 = new widget.Label();
        ttlbiaya = new widget.TextBox();
        jLabel3 = new widget.Label();
        jLabel12 = new widget.Label();
        btnReg = new widget.Button();
        TNoRM = new widget.TextBox();
        btnKamar = new widget.Button();
        TKdBngsal = new widget.TextBox();
        jLabel13 = new widget.Label();
        cmbDtk = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbJam = new widget.ComboBox();
        TBangsal = new widget.TextBox();
        jLabel11 = new widget.Label();
        TJmlHari = new widget.TextBox();
        jLabel15 = new widget.Label();
        TSttsKamar = new widget.TextBox();
        BtnCloseIn = new widget.Button();
        jLabel19 = new widget.Label();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        jLabel14 = new widget.Label();
        jLabel16 = new widget.Label();
        TTarif = new widget.TextBox();
        LblStts = new widget.Label();
        jLabel18 = new widget.Label();
        diagnosaawal = new widget.TextBox();
        diagnosaakhir = new widget.TextBox();
        jLabel23 = new widget.Label();
        CmbTahun = new widget.ComboBox();
        CmbBln = new widget.ComboBox();
        CmbTgl = new widget.ComboBox();
        jLabel24 = new widget.Label();
        cmbStatus = new widget.ComboBox();
        jLabel26 = new widget.Label();
        btnDiagnosa = new widget.Button();
        TOut = new widget.TextBox();
        TIn = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnAssesment = new javax.swing.JMenu();
        MnAssesmentAwal = new javax.swing.JMenuItem();
        MnTransferPasien = new javax.swing.JMenuItem();
        MnPermintaan = new javax.swing.JMenu();
        MnJadwalOperasi = new javax.swing.JMenuItem();
        MnSKDPBPJS = new javax.swing.JMenuItem();
        MnPermintaanLab = new javax.swing.JMenuItem();
        MnPermintaanRadiologi = new javax.swing.JMenuItem();
        MnPermintaanKerohanian = new javax.swing.JMenuItem();
        MnPermintaanUTD = new javax.swing.JMenuItem();
        MnPermintaanMPP = new javax.swing.JMenuItem();
        MnTindakan = new javax.swing.JMenu();
        MnRawatJalan = new javax.swing.JMenuItem();
        MnRawatInap = new javax.swing.JMenuItem();
        MnPeriksaLab = new javax.swing.JMenuItem();
        MnPeriksaRadiologi = new javax.swing.JMenuItem();
        MnOperasi = new javax.swing.JMenuItem();
        MnDiet = new javax.swing.JMenuItem();
        MnObat = new javax.swing.JMenu();
        MnPemberianObat = new javax.swing.JMenuItem();
        MnInputResep = new javax.swing.JMenuItem();
        MnNoResep = new javax.swing.JMenuItem();
        MnResepDOkter = new javax.swing.JMenuItem();
        MnStokObatPasien = new javax.swing.JMenuItem();
        MnReturJual = new javax.swing.JMenuItem();
        MnResepPulang = new javax.swing.JMenuItem();
        MnDeposit = new javax.swing.JMenuItem();
        MnBilling = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        MnCetakBerkas = new javax.swing.JMenuItem();
        MnLaporan = new javax.swing.JMenu();
        MnRincianObat = new javax.swing.JMenuItem();
        MnRM2D = new javax.swing.JMenuItem();
        MnSensusRanap = new javax.swing.JMenuItem();
        MnRekapitulasiRanap = new javax.swing.JMenuItem();
        MnTilikBedah = new javax.swing.JMenuItem();
        MnAsuhanGizi = new javax.swing.JMenuItem();
        MnPenggunaanKamar = new javax.swing.JMenuItem();
        MnPengantarPulang = new javax.swing.JMenuItem();
        MnFormulirPenerimaan = new javax.swing.JMenuItem();
        MnFormulirPenerimaan1 = new javax.swing.JMenuItem();
        MnCetakSuratOpname = new javax.swing.JMenuItem();
        MnCetakSuratResumeMedis = new javax.swing.JMenuItem();
        MnCetakSuratIstirahat = new javax.swing.JMenuItem();
        MnCetakSuratOperasi = new javax.swing.JMenuItem();
        MnAsuhanKeperawatan = new javax.swing.JMenuItem();
        MnSoapResume = new javax.swing.JMenuItem();
        MnGelang = new javax.swing.JMenu();
        MnLabelTracker = new javax.swing.JMenuItem();
        MnBarcodeRM9 = new javax.swing.JMenuItem();
        MnBarcodeRM10 = new javax.swing.JMenuItem();
        MnGelang3 = new javax.swing.JMenuItem();
        MnGelang5 = new javax.swing.JMenuItem();
        MnGelang4 = new javax.swing.JMenuItem();
        MnLabelSyringpump = new javax.swing.JMenuItem();
        MnRanapGabung = new javax.swing.JMenuItem();
        MnGabungkanRanap = new javax.swing.JMenuItem();
        MnDPJP = new javax.swing.JMenuItem();
        MnDPJPRanap = new javax.swing.JMenuItem();
        jSeparator13 = new javax.swing.JPopupMenu.Separator();
        MnRujukan = new javax.swing.JMenu();
        MnRujuk = new javax.swing.JMenuItem();
        MnRujukMasuk = new javax.swing.JMenuItem();
        MenuBPJS = new javax.swing.JMenu();
        MnCekKepesertaan = new javax.swing.JMenuItem();
        MnCekNIK = new javax.swing.JMenuItem();
        MnSEP = new javax.swing.JMenuItem();
        MnSEPNoCek = new javax.swing.JMenuItem();
        ppSuratKontrol = new javax.swing.JMenuItem();
        ppSuratPRI = new javax.swing.JMenuItem();
        MnRujukSisrute = new javax.swing.JMenuItem();
        MnLapCov19V3 = new javax.swing.JMenuItem();
        MnPACS = new javax.swing.JMenuItem();
        MnHasilLab = new javax.swing.JMenuItem();
        MenuInputData = new javax.swing.JMenu();
        MnDiagnosa = new javax.swing.JMenuItem();
        ppCatatanPasien = new javax.swing.JMenuItem();
        ppDataHAIs = new javax.swing.JMenuItem();
        ppDataIdo = new javax.swing.JMenuItem();
        ppBerkasDigital = new javax.swing.JMenuItem();
        ppIKP = new javax.swing.JMenuItem();
        MnTeridentifikasiTB = new javax.swing.JMenuItem();
        MnInputHP = new javax.swing.JMenuItem();
        MnKelahiranBayi = new javax.swing.JMenuItem();
        SetStatus = new javax.swing.JMenu();
        MnStatusRujuk = new javax.swing.JMenuItem();
        MnStatusAPS = new javax.swing.JMenuItem();
        MnStatusMeninggal = new javax.swing.JMenuItem();
        MnStatusMembaik = new javax.swing.JMenuItem();
        MnStatusBelumPulang = new javax.swing.JMenuItem();
        ppRiwayat = new javax.swing.JMenuItem();
        MnUpdateHari = new javax.swing.JMenuItem();
        MnPenjab = new javax.swing.JMenuItem();
        MnHapusData = new javax.swing.JMenu();
        MnHapusTagihanOperasi = new javax.swing.JMenuItem();
        MnHapusObatOperasi = new javax.swing.JMenuItem();
        MnHapusDataSalah = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        JamMasuk = new widget.TextBox();
        WindowPindahKamar = new javax.swing.JDialog();
        internalFrame3 = new widget.InternalFrame();
        norawatpindah = new widget.TextBox();
        TPasienpindah = new widget.TextBox();
        kdkamarpindah = new widget.TextBox();
        ttlbiayapindah = new widget.TextBox();
        jLabel4 = new widget.Label();
        jLabel20 = new widget.Label();
        TNoRMpindah = new widget.TextBox();
        btnKamar2 = new widget.Button();
        TKdBngsalpindah = new widget.TextBox();
        jLabel27 = new widget.Label();
        cmbDtkpindah = new widget.ComboBox();
        cmbMntpindah = new widget.ComboBox();
        cmbJampindah = new widget.ComboBox();
        TBangsalpindah = new widget.TextBox();
        jLabel28 = new widget.Label();
        TJmlHaripindah = new widget.TextBox();
        jLabel29 = new widget.Label();
        TSttsKamarpindah = new widget.TextBox();
        BtnCloseInpindah = new widget.Button();
        jLabel30 = new widget.Label();
        BtnSimpanpindah = new widget.Button();
        jLabel31 = new widget.Label();
        jLabel32 = new widget.Label();
        TTarifpindah = new widget.TextBox();
        CmbTahunpindah = new widget.ComboBox();
        CmbBlnpindah = new widget.ComboBox();
        CmbTglpindah = new widget.ComboBox();
        jLabel35 = new widget.Label();
        Rganti3 = new widget.RadioButton();
        jLabel33 = new widget.Label();
        Rganti2 = new widget.RadioButton();
        Rganti1 = new widget.RadioButton();
        Rganti4 = new widget.RadioButton();
        buttonGroup2 = new javax.swing.ButtonGroup();
        WindowCaraBayar = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        BtnCloseIn4 = new widget.Button();
        BtnSimpan4 = new widget.Button();
        jLabel17 = new widget.Label();
        kdpenjab = new widget.TextBox();
        nmpenjab = new widget.TextBox();
        btnBayar = new widget.Button();
        WindowRanapGabung = new javax.swing.JDialog();
        internalFrame6 = new widget.InternalFrame();
        BtnCloseGabung = new widget.Button();
        BtnSimpanGabung = new widget.Button();
        jLabel34 = new widget.Label();
        NoRmBayi = new widget.TextBox();
        NmBayi = new widget.TextBox();
        btnPasienRanapGabung = new widget.Button();
        BtnHapusGabung = new widget.Button();
        NoRawatGabung = new widget.TextBox();
        btnPasienRanapGabung1 = new widget.Button();
        DlgSakit2 = new javax.swing.JDialog();
        internalFrame7 = new widget.InternalFrame();
        panelBiasa4 = new widget.PanelBiasa();
        jLabel38 = new widget.Label();
        BtnPrint5 = new widget.Button();
        BtnKeluar4 = new widget.Button();
        NomorSurat = new widget.TextBox();
        BtnSeek5 = new widget.Button();
        CrDokter3 = new widget.TextBox();
        jLabel36 = new widget.Label();
        WindowCetakBerkas = new javax.swing.JDialog();
        internalFrame10 = new widget.InternalFrame();
        panelisi2 = new widget.panelisi();
        label17 = new widget.Label();
        BtnCetak4 = new widget.Button();
        BtnKeluar5 = new widget.Button();
        FormInput13 = new widget.PanelBiasa();
        chkIdentitasPasien = new widget.CekBox();
        ChkGeneralConsent = new widget.CekBox();
        chkRM23 = new widget.CekBox();
        chkIdentifikasiBayi = new widget.CekBox();
        TPasien2 = new widget.TextBox();
        TNoRM2 = new widget.TextBox();
        TNoRw2 = new widget.TextBox();
        jLabel55 = new widget.Label();
        chkSPMBiayaObat = new widget.CekBox();
        chkTransferPasien = new widget.CekBox();
        chkCover = new widget.CekBox();
        internalFrame1 = new widget.InternalFrame();
        PanelCariUtama = new javax.swing.JPanel();
        panelGlass10 = new widget.panelisi();
        BtnIn = new widget.Button();
        BtnOut = new widget.Button();
        btnPindah = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass11 = new widget.panelisi();
        jLabel21 = new widget.Label();
        BangsalCari = new widget.TextBox();
        btnBangsalCari = new widget.Button();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        panelCari = new widget.panelisi();
        R1 = new widget.RadioButton();
        R2 = new widget.RadioButton();
        DTPCari1 = new widget.Tanggal();
        jLabel22 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        R3 = new widget.RadioButton();
        DTPCari3 = new widget.Tanggal();
        jLabel25 = new widget.Label();
        DTPCari4 = new widget.Tanggal();
        Scroll = new widget.ScrollPane();
        tbKamIn = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel5 = new widget.Label();
        TNoRw1 = new widget.TextBox();
        TNoRM1 = new widget.TextBox();
        TPasien1 = new widget.TextBox();
        jLabel37 = new widget.Label();
        cmbStatusBayar = new widget.ComboBox();

        WindowInputKamar.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInputKamar.setName("WindowInputKamar"); // NOI18N
        WindowInputKamar.setUndecorated(true);
        WindowInputKamar.setResizable(false);
        WindowInputKamar.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                WindowInputKamarWindowActivated(evt);
            }
        });

        internalFrame2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(215, 225, 205)), "::[ Input Kamar Inap Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(null);

        norawat.setHighlighter(null);
        norawat.setName("norawat"); // NOI18N
        norawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                norawatKeyPressed(evt);
            }
        });
        internalFrame2.add(norawat);
        norawat.setBounds(75, 25, 150, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        internalFrame2.add(TPasien);
        TPasien.setBounds(359, 25, 269, 23);

        kdkamar.setHighlighter(null);
        kdkamar.setName("kdkamar"); // NOI18N
        kdkamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdkamarKeyPressed(evt);
            }
        });
        internalFrame2.add(kdkamar);
        kdkamar.setBounds(75, 55, 95, 23);

        jLabel10.setText("Proses :");
        jLabel10.setName("jLabel10"); // NOI18N
        internalFrame2.add(jLabel10);
        jLabel10.setBounds(0, 175, 72, 23);

        ttlbiaya.setEditable(false);
        ttlbiaya.setText("0");
        ttlbiaya.setHighlighter(null);
        ttlbiaya.setName("ttlbiaya"); // NOI18N
        ttlbiaya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ttlbiayaKeyPressed(evt);
            }
        });
        internalFrame2.add(ttlbiaya);
        ttlbiaya.setBounds(368, 145, 290, 23);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        internalFrame2.add(jLabel3);
        jLabel3.setBounds(0, 25, 72, 23);

        jLabel12.setText("Kamar :");
        jLabel12.setName("jLabel12"); // NOI18N
        internalFrame2.add(jLabel12);
        jLabel12.setBounds(0, 55, 72, 23);

        btnReg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnReg.setMnemonic('1');
        btnReg.setToolTipText("Alt+1");
        btnReg.setName("btnReg"); // NOI18N
        btnReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegActionPerformed(evt);
            }
        });
        btnReg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnRegKeyPressed(evt);
            }
        });
        internalFrame2.add(btnReg);
        btnReg.setBounds(630, 25, 28, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        internalFrame2.add(TNoRM);
        TNoRM.setBounds(227, 25, 130, 23);

        btnKamar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKamar.setMnemonic('2');
        btnKamar.setToolTipText("Alt+2");
        btnKamar.setName("btnKamar"); // NOI18N
        btnKamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKamarActionPerformed(evt);
            }
        });
        btnKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnKamarKeyPressed(evt);
            }
        });
        internalFrame2.add(btnKamar);
        btnKamar.setBounds(428, 55, 28, 23);

        TKdBngsal.setEditable(false);
        TKdBngsal.setName("TKdBngsal"); // NOI18N
        internalFrame2.add(TKdBngsal);
        TKdBngsal.setBounds(172, 55, 82, 23);

        jLabel13.setText("Tanggal :");
        jLabel13.setName("jLabel13"); // NOI18N
        internalFrame2.add(jLabel13);
        jLabel13.setBounds(0, 85, 72, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbTahunItemStateChanged(evt);
            }
        });
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        internalFrame2.add(cmbDtk);
        cmbDtk.setBounds(207, 115, 62, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbTahunItemStateChanged(evt);
            }
        });
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        internalFrame2.add(cmbMnt);
        cmbMnt.setBounds(141, 115, 62, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbTahunItemStateChanged(evt);
            }
        });
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        internalFrame2.add(cmbJam);
        cmbJam.setBounds(75, 115, 62, 23);

        TBangsal.setEditable(false);
        TBangsal.setHighlighter(null);
        TBangsal.setName("TBangsal"); // NOI18N
        TBangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBangsalKeyPressed(evt);
            }
        });
        internalFrame2.add(TBangsal);
        TBangsal.setBounds(256, 55, 170, 23);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("X");
        jLabel11.setName("jLabel11"); // NOI18N
        internalFrame2.add(jLabel11);
        jLabel11.setBounds(173, 145, 15, 23);

        TJmlHari.setEditable(false);
        TJmlHari.setText("0");
        TJmlHari.setHighlighter(null);
        TJmlHari.setName("TJmlHari"); // NOI18N
        internalFrame2.add(TJmlHari);
        TJmlHari.setBounds(75, 145, 96, 23);

        jLabel15.setText("=");
        jLabel15.setName("jLabel15"); // NOI18N
        internalFrame2.add(jLabel15);
        jLabel15.setBounds(342, 145, 20, 23);

        TSttsKamar.setEditable(false);
        TSttsKamar.setName("TSttsKamar"); // NOI18N
        internalFrame2.add(TSttsKamar);
        TSttsKamar.setBounds(548, 55, 110, 23);

        BtnCloseIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn.setMnemonic('U');
        BtnCloseIn.setText("Tutup");
        BtnCloseIn.setToolTipText("Alt+U");
        BtnCloseIn.setName("BtnCloseIn"); // NOI18N
        BtnCloseIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseInActionPerformed(evt);
            }
        });
        BtnCloseIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCloseInKeyPressed(evt);
            }
        });
        internalFrame2.add(BtnCloseIn);
        BtnCloseIn.setBounds(560, 225, 100, 30);

        jLabel19.setText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        jLabel19.setName("jLabel19"); // NOI18N
        internalFrame2.add(jLabel19);
        jLabel19.setBounds(-10, 200, 850, 14);

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
        internalFrame2.add(BtnSimpan);
        BtnSimpan.setBounds(14, 225, 100, 30);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Batal");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
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
        internalFrame2.add(BtnBatal);
        BtnBatal.setBounds(117, 225, 100, 30);

        jLabel14.setText("Stts.Kamar :");
        jLabel14.setName("jLabel14"); // NOI18N
        internalFrame2.add(jLabel14);
        jLabel14.setBounds(444, 55, 100, 23);

        jLabel16.setText("Biaya :");
        jLabel16.setName("jLabel16"); // NOI18N
        internalFrame2.add(jLabel16);
        jLabel16.setBounds(0, 145, 72, 23);

        TTarif.setEditable(false);
        TTarif.setText("0");
        TTarif.setHighlighter(null);
        TTarif.setName("TTarif"); // NOI18N
        internalFrame2.add(TTarif);
        TTarif.setBounds(188, 145, 160, 23);

        LblStts.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LblStts.setText("Check In");
        LblStts.setName("LblStts"); // NOI18N
        internalFrame2.add(LblStts);
        LblStts.setBounds(75, 175, 180, 23);

        jLabel18.setText("Diagnosa Awal Masuk :");
        jLabel18.setName("jLabel18"); // NOI18N
        internalFrame2.add(jLabel18);
        jLabel18.setBounds(295, 85, 140, 23);

        diagnosaawal.setHighlighter(null);
        diagnosaawal.setName("diagnosaawal"); // NOI18N
        diagnosaawal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                diagnosaawalKeyPressed(evt);
            }
        });
        internalFrame2.add(diagnosaawal);
        diagnosaawal.setBounds(438, 85, 190, 23);

        diagnosaakhir.setHighlighter(null);
        diagnosaakhir.setName("diagnosaakhir"); // NOI18N
        diagnosaakhir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                diagnosaakhirKeyPressed(evt);
            }
        });
        internalFrame2.add(diagnosaakhir);
        diagnosaakhir.setBounds(438, 115, 190, 23);

        jLabel23.setText("Diagnosa Akhir Keluar :");
        jLabel23.setName("jLabel23"); // NOI18N
        internalFrame2.add(jLabel23);
        jLabel23.setBounds(295, 115, 140, 23);

        CmbTahun.setName("CmbTahun"); // NOI18N
        CmbTahun.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbTahunItemStateChanged(evt);
            }
        });
        CmbTahun.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbTahunKeyPressed(evt);
            }
        });
        internalFrame2.add(CmbTahun);
        CmbTahun.setBounds(207, 85, 90, 23);

        CmbBln.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        CmbBln.setName("CmbBln"); // NOI18N
        CmbBln.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbTahunItemStateChanged(evt);
            }
        });
        CmbBln.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbBlnKeyPressed(evt);
            }
        });
        internalFrame2.add(CmbBln);
        CmbBln.setBounds(141, 85, 62, 23);

        CmbTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        CmbTgl.setName("CmbTgl"); // NOI18N
        CmbTgl.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CmbTahunItemStateChanged(evt);
            }
        });
        CmbTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbTglKeyPressed(evt);
            }
        });
        internalFrame2.add(CmbTgl);
        CmbTgl.setBounds(75, 85, 62, 23);

        jLabel24.setText("Jam :");
        jLabel24.setName("jLabel24"); // NOI18N
        internalFrame2.add(jLabel24);
        jLabel24.setBounds(0, 115, 72, 23);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Rujuk", "APS", "Meninggal", "Membaik" }));
        cmbStatus.setName("cmbStatus"); // NOI18N
        cmbStatus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbStatusKeyPressed(evt);
            }
        });
        internalFrame2.add(cmbStatus);
        cmbStatus.setBounds(418, 175, 240, 23);

        jLabel26.setText("Status Pulang/Keluar :");
        jLabel26.setName("jLabel26"); // NOI18N
        internalFrame2.add(jLabel26);
        jLabel26.setBounds(275, 175, 140, 23);

        btnDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDiagnosa.setMnemonic('3');
        btnDiagnosa.setToolTipText("Alt+3");
        btnDiagnosa.setName("btnDiagnosa"); // NOI18N
        btnDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiagnosaActionPerformed(evt);
            }
        });
        btnDiagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDiagnosaKeyPressed(evt);
            }
        });
        internalFrame2.add(btnDiagnosa);
        btnDiagnosa.setBounds(630, 115, 28, 23);

        WindowInputKamar.getContentPane().add(internalFrame2, java.awt.BorderLayout.CENTER);

        TOut.setEditable(false);
        TOut.setForeground(new java.awt.Color(255, 255, 255));
        TOut.setHighlighter(null);
        TOut.setName("TOut"); // NOI18N
        TOut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TOutKeyPressed(evt);
            }
        });

        TIn.setEditable(false);
        TIn.setForeground(new java.awt.Color(255, 255, 255));
        TIn.setHighlighter(null);
        TIn.setName("TIn"); // NOI18N
        TIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TInKeyPressed(evt);
            }
        });

        jPopupMenu1.setForeground(new java.awt.Color(70, 70, 70));
        jPopupMenu1.setAutoscrolls(true);
        jPopupMenu1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPopupMenu1.setFocusTraversalPolicyProvider(true);
        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnAssesment.setBackground(new java.awt.Color(250, 255, 245));
        MnAssesment.setForeground(new java.awt.Color(70, 70, 70));
        MnAssesment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnAssesment.setText("Assesment");
        MnAssesment.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnAssesment.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnAssesment.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnAssesment.setIconTextGap(5);
        MnAssesment.setName("MnAssesment"); // NOI18N
        MnAssesment.setPreferredSize(new java.awt.Dimension(250, 26));

        MnAssesmentAwal.setBackground(new java.awt.Color(255, 255, 254));
        MnAssesmentAwal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnAssesmentAwal.setForeground(new java.awt.Color(70, 70, 70));
        MnAssesmentAwal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnAssesmentAwal.setText("Assessment Awal Keperawatan Ranap");
        MnAssesmentAwal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnAssesmentAwal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnAssesmentAwal.setName("MnAssesmentAwal"); // NOI18N
        MnAssesmentAwal.setPreferredSize(new java.awt.Dimension(240, 26));
        MnAssesmentAwal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnAssesmentAwalActionPerformed(evt);
            }
        });
        MnAssesment.add(MnAssesmentAwal);

        MnTransferPasien.setBackground(new java.awt.Color(255, 255, 254));
        MnTransferPasien.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTransferPasien.setForeground(new java.awt.Color(70, 70, 70));
        MnTransferPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTransferPasien.setText("Transfer Pasien");
        MnTransferPasien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTransferPasien.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTransferPasien.setName("MnTransferPasien"); // NOI18N
        MnTransferPasien.setPreferredSize(new java.awt.Dimension(240, 26));
        MnTransferPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnTransferPasienActionPerformed(evt);
            }
        });
        MnAssesment.add(MnTransferPasien);

        jPopupMenu1.add(MnAssesment);

        MnPermintaan.setBackground(new java.awt.Color(250, 255, 245));
        MnPermintaan.setForeground(new java.awt.Color(70, 70, 70));
        MnPermintaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaan.setText("Permintaan");
        MnPermintaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaan.setIconTextGap(5);
        MnPermintaan.setName("MnPermintaan"); // NOI18N
        MnPermintaan.setPreferredSize(new java.awt.Dimension(210, 26));

        MnJadwalOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnJadwalOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnJadwalOperasi.setForeground(new java.awt.Color(70, 70, 70));
        MnJadwalOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnJadwalOperasi.setText("Jadwal Operasi");
        MnJadwalOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnJadwalOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnJadwalOperasi.setName("MnJadwalOperasi"); // NOI18N
        MnJadwalOperasi.setPreferredSize(new java.awt.Dimension(170, 26));
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
        MnSKDPBPJS.setPreferredSize(new java.awt.Dimension(170, 26));
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
        MnPermintaanLab.setPreferredSize(new java.awt.Dimension(170, 26));
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
        MnPermintaanRadiologi.setPreferredSize(new java.awt.Dimension(170, 26));
        MnPermintaanRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPermintaanRadiologiActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnPermintaanRadiologi);

        MnPermintaanKerohanian.setBackground(new java.awt.Color(255, 255, 254));
        MnPermintaanKerohanian.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaanKerohanian.setForeground(new java.awt.Color(70, 70, 70));
        MnPermintaanKerohanian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaanKerohanian.setText("Permintaan Kerohanian");
        MnPermintaanKerohanian.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaanKerohanian.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaanKerohanian.setName("MnPermintaanKerohanian"); // NOI18N
        MnPermintaanKerohanian.setPreferredSize(new java.awt.Dimension(170, 26));
        MnPermintaanKerohanian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPermintaanKerohanianActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnPermintaanKerohanian);

        MnPermintaanUTD.setBackground(new java.awt.Color(255, 255, 254));
        MnPermintaanUTD.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaanUTD.setForeground(new java.awt.Color(70, 70, 70));
        MnPermintaanUTD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaanUTD.setText("Permintaan UTD");
        MnPermintaanUTD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaanUTD.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaanUTD.setName("MnPermintaanUTD"); // NOI18N
        MnPermintaanUTD.setPreferredSize(new java.awt.Dimension(170, 26));
        MnPermintaanUTD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPermintaanUTDActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnPermintaanUTD);

        MnPermintaanMPP.setBackground(new java.awt.Color(255, 255, 254));
        MnPermintaanMPP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaanMPP.setForeground(new java.awt.Color(70, 70, 70));
        MnPermintaanMPP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPermintaanMPP.setText("Form MPP");
        MnPermintaanMPP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaanMPP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaanMPP.setName("MnPermintaanMPP"); // NOI18N
        MnPermintaanMPP.setPreferredSize(new java.awt.Dimension(170, 26));
        MnPermintaanMPP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPermintaanMPPActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnPermintaanMPP);

        jPopupMenu1.add(MnPermintaan);

        MnTindakan.setBackground(new java.awt.Color(250, 255, 245));
        MnTindakan.setForeground(new java.awt.Color(70, 70, 70));
        MnTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTindakan.setText("Tindakan & Pemeriksaan");
        MnTindakan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTindakan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTindakan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTindakan.setIconTextGap(5);
        MnTindakan.setName("MnTindakan"); // NOI18N
        MnTindakan.setPreferredSize(new java.awt.Dimension(220, 26));

        MnRawatJalan.setBackground(new java.awt.Color(255, 255, 254));
        MnRawatJalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRawatJalan.setForeground(new java.awt.Color(70, 70, 70));
        MnRawatJalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRawatJalan.setText("Data Tagihan/Tindakan Rawat Jalan");
        MnRawatJalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRawatJalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRawatJalan.setName("MnRawatJalan"); // NOI18N
        MnRawatJalan.setPreferredSize(new java.awt.Dimension(250, 28));
        MnRawatJalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRawatJalanActionPerformed(evt);
            }
        });
        MnTindakan.add(MnRawatJalan);

        MnRawatInap.setBackground(new java.awt.Color(255, 255, 254));
        MnRawatInap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRawatInap.setForeground(new java.awt.Color(70, 70, 70));
        MnRawatInap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRawatInap.setText("Data Tagihan/Tindakan Rawat Inap");
        MnRawatInap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRawatInap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRawatInap.setName("MnRawatInap"); // NOI18N
        MnRawatInap.setPreferredSize(new java.awt.Dimension(220, 26));
        MnRawatInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRawatInapActionPerformed(evt);
            }
        });
        MnTindakan.add(MnRawatInap);

        MnPeriksaLab.setBackground(new java.awt.Color(255, 255, 254));
        MnPeriksaLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaLab.setForeground(new java.awt.Color(70, 70, 70));
        MnPeriksaLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaLab.setText("Periksa Lab");
        MnPeriksaLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaLab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaLab.setName("MnPeriksaLab"); // NOI18N
        MnPeriksaLab.setPreferredSize(new java.awt.Dimension(220, 26));
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
        MnPeriksaRadiologi.setPreferredSize(new java.awt.Dimension(220, 26));
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
        MnOperasi.setPreferredSize(new java.awt.Dimension(220, 26));
        MnOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnOperasiActionPerformed(evt);
            }
        });
        MnTindakan.add(MnOperasi);

        MnDiet.setBackground(new java.awt.Color(255, 255, 254));
        MnDiet.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDiet.setForeground(new java.awt.Color(70, 70, 70));
        MnDiet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDiet.setText("Diet Pasien");
        MnDiet.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDiet.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDiet.setName("MnDiet"); // NOI18N
        MnDiet.setPreferredSize(new java.awt.Dimension(220, 26));
        MnDiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDietActionPerformed(evt);
            }
        });
        MnTindakan.add(MnDiet);

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
        MnObat.setPreferredSize(new java.awt.Dimension(220, 26));

        MnPemberianObat.setBackground(new java.awt.Color(255, 255, 254));
        MnPemberianObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPemberianObat.setForeground(new java.awt.Color(70, 70, 70));
        MnPemberianObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPemberianObat.setText("Data Pemberian Obat/BHP");
        MnPemberianObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPemberianObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPemberianObat.setName("MnPemberianObat"); // NOI18N
        MnPemberianObat.setPreferredSize(new java.awt.Dimension(200, 26));
        MnPemberianObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPemberianObatActionPerformed(evt);
            }
        });
        MnObat.add(MnPemberianObat);

        MnInputResep.setBackground(new java.awt.Color(255, 255, 254));
        MnInputResep.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnInputResep.setForeground(new java.awt.Color(70, 70, 70));
        MnInputResep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnInputResep.setText("Input Resep Pulang");
        MnInputResep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnInputResep.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnInputResep.setName("MnInputResep"); // NOI18N
        MnInputResep.setPreferredSize(new java.awt.Dimension(200, 26));
        MnInputResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnInputResepActionPerformed(evt);
            }
        });
        MnObat.add(MnInputResep);

        MnNoResep.setBackground(new java.awt.Color(255, 255, 254));
        MnNoResep.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnNoResep.setForeground(new java.awt.Color(70, 70, 70));
        MnNoResep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnNoResep.setText("Input No.Resep");
        MnNoResep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnNoResep.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnNoResep.setName("MnNoResep"); // NOI18N
        MnNoResep.setPreferredSize(new java.awt.Dimension(200, 26));
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
        MnResepDOkter.setPreferredSize(new java.awt.Dimension(190, 26));
        MnResepDOkter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnResepDOkterActionPerformed(evt);
            }
        });
        MnObat.add(MnResepDOkter);

        MnStokObatPasien.setBackground(new java.awt.Color(255, 255, 254));
        MnStokObatPasien.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStokObatPasien.setForeground(new java.awt.Color(70, 70, 70));
        MnStokObatPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnStokObatPasien.setText("Stok Obat Pasien Ranap");
        MnStokObatPasien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStokObatPasien.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStokObatPasien.setName("MnStokObatPasien"); // NOI18N
        MnStokObatPasien.setPreferredSize(new java.awt.Dimension(200, 26));
        MnStokObatPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnStokObatPasienActionPerformed(evt);
            }
        });
        MnObat.add(MnStokObatPasien);

        MnReturJual.setBackground(new java.awt.Color(255, 255, 254));
        MnReturJual.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnReturJual.setForeground(new java.awt.Color(70, 70, 70));
        MnReturJual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnReturJual.setText("Retur Obat/Barang/Alkes");
        MnReturJual.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnReturJual.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnReturJual.setName("MnReturJual"); // NOI18N
        MnReturJual.setPreferredSize(new java.awt.Dimension(200, 26));
        MnReturJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnReturJualActionPerformed(evt);
            }
        });
        MnObat.add(MnReturJual);

        MnResepPulang.setBackground(new java.awt.Color(255, 255, 254));
        MnResepPulang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnResepPulang.setForeground(new java.awt.Color(70, 70, 70));
        MnResepPulang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnResepPulang.setText("Data Resep Pulang");
        MnResepPulang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnResepPulang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnResepPulang.setName("MnResepPulang"); // NOI18N
        MnResepPulang.setPreferredSize(new java.awt.Dimension(200, 26));
        MnResepPulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnResepPulangActionPerformed(evt);
            }
        });
        MnObat.add(MnResepPulang);

        jPopupMenu1.add(MnObat);

        MnDeposit.setBackground(new java.awt.Color(255, 255, 254));
        MnDeposit.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDeposit.setForeground(new java.awt.Color(70, 70, 70));
        MnDeposit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDeposit.setText("Deposit/Titipan Pasien");
        MnDeposit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDeposit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDeposit.setName("MnDeposit"); // NOI18N
        MnDeposit.setPreferredSize(new java.awt.Dimension(220, 26));
        MnDeposit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDepositActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnDeposit);

        MnBilling.setBackground(new java.awt.Color(255, 255, 254));
        MnBilling.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBilling.setForeground(new java.awt.Color(70, 70, 70));
        MnBilling.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBilling.setText("Billing/Pembayaran Pasien");
        MnBilling.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBilling.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBilling.setName("MnBilling"); // NOI18N
        MnBilling.setPreferredSize(new java.awt.Dimension(220, 26));
        MnBilling.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBillingActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnBilling);

        jSeparator12.setBackground(new java.awt.Color(190, 220, 180));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(190, 220, 180)));
        jSeparator12.setForeground(new java.awt.Color(190, 220, 180));
        jSeparator12.setName("jSeparator12"); // NOI18N
        jSeparator12.setPreferredSize(new java.awt.Dimension(220, 1));
        jPopupMenu1.add(jSeparator12);

        MnCetakBerkas.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakBerkas.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakBerkas.setForeground(new java.awt.Color(70, 70, 70));
        MnCetakBerkas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakBerkas.setText("Cetak Berkas RM");
        MnCetakBerkas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakBerkas.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakBerkas.setName("MnCetakBerkas"); // NOI18N
        MnCetakBerkas.setPreferredSize(new java.awt.Dimension(310, 26));
        MnCetakBerkas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakBerkasActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakBerkas);

        MnLaporan.setBackground(new java.awt.Color(250, 255, 245));
        MnLaporan.setForeground(new java.awt.Color(70, 70, 70));
        MnLaporan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporan.setText("Laporan & Surat");
        MnLaporan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLaporan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLaporan.setIconTextGap(5);
        MnLaporan.setName("MnLaporan"); // NOI18N
        MnLaporan.setPreferredSize(new java.awt.Dimension(220, 26));

        MnRincianObat.setBackground(new java.awt.Color(255, 255, 254));
        MnRincianObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRincianObat.setForeground(new java.awt.Color(70, 70, 70));
        MnRincianObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRincianObat.setText("Rincian Penggunaan Obat");
        MnRincianObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRincianObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRincianObat.setName("MnRincianObat"); // NOI18N
        MnRincianObat.setPreferredSize(new java.awt.Dimension(210, 26));
        MnRincianObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRincianObatActionPerformed(evt);
            }
        });
        MnLaporan.add(MnRincianObat);

        MnRM2D.setBackground(new java.awt.Color(255, 255, 254));
        MnRM2D.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRM2D.setForeground(new java.awt.Color(70, 70, 70));
        MnRM2D.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRM2D.setText("Asesment Pasien IGD");
        MnRM2D.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRM2D.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRM2D.setName("MnRM2D"); // NOI18N
        MnRM2D.setPreferredSize(new java.awt.Dimension(210, 26));
        MnRM2D.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRM2DActionPerformed(evt);
            }
        });
        MnLaporan.add(MnRM2D);

        MnSensusRanap.setBackground(new java.awt.Color(255, 255, 254));
        MnSensusRanap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSensusRanap.setForeground(new java.awt.Color(70, 70, 70));
        MnSensusRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSensusRanap.setText("Sensus Harian Ranap");
        MnSensusRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSensusRanap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSensusRanap.setName("MnSensusRanap"); // NOI18N
        MnSensusRanap.setPreferredSize(new java.awt.Dimension(210, 26));
        MnSensusRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSensusRanapActionPerformed(evt);
            }
        });
        MnLaporan.add(MnSensusRanap);

        MnRekapitulasiRanap.setBackground(new java.awt.Color(255, 255, 254));
        MnRekapitulasiRanap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapitulasiRanap.setForeground(new java.awt.Color(70, 70, 70));
        MnRekapitulasiRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekapitulasiRanap.setText("Rekapitulasi Sensus Ranap");
        MnRekapitulasiRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRekapitulasiRanap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRekapitulasiRanap.setName("MnRekapitulasiRanap"); // NOI18N
        MnRekapitulasiRanap.setPreferredSize(new java.awt.Dimension(210, 26));
        MnRekapitulasiRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapitulasiRanapActionPerformed(evt);
            }
        });
        MnLaporan.add(MnRekapitulasiRanap);

        MnTilikBedah.setBackground(new java.awt.Color(255, 255, 254));
        MnTilikBedah.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTilikBedah.setForeground(new java.awt.Color(70, 70, 70));
        MnTilikBedah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnTilikBedah.setText("Daftar Tilik Keselamatan Bedah");
        MnTilikBedah.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTilikBedah.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTilikBedah.setName("MnTilikBedah"); // NOI18N
        MnTilikBedah.setPreferredSize(new java.awt.Dimension(210, 26));
        MnTilikBedah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnTilikBedahActionPerformed(evt);
            }
        });
        MnLaporan.add(MnTilikBedah);

        MnAsuhanGizi.setBackground(new java.awt.Color(255, 255, 254));
        MnAsuhanGizi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnAsuhanGizi.setForeground(new java.awt.Color(70, 70, 70));
        MnAsuhanGizi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnAsuhanGizi.setText("Assesment Gizi");
        MnAsuhanGizi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnAsuhanGizi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnAsuhanGizi.setName("MnAsuhanGizi"); // NOI18N
        MnAsuhanGizi.setPreferredSize(new java.awt.Dimension(210, 26));
        MnAsuhanGizi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnAsuhanGiziActionPerformed(evt);
            }
        });
        MnLaporan.add(MnAsuhanGizi);

        MnPenggunaanKamar.setBackground(new java.awt.Color(255, 255, 254));
        MnPenggunaanKamar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenggunaanKamar.setForeground(new java.awt.Color(70, 70, 70));
        MnPenggunaanKamar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenggunaanKamar.setText("Penggunaan Kamar");
        MnPenggunaanKamar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenggunaanKamar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenggunaanKamar.setName("MnPenggunaanKamar"); // NOI18N
        MnPenggunaanKamar.setPreferredSize(new java.awt.Dimension(210, 26));
        MnPenggunaanKamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenggunaanKamarActionPerformed(evt);
            }
        });
        MnLaporan.add(MnPenggunaanKamar);

        MnPengantarPulang.setBackground(new java.awt.Color(255, 255, 254));
        MnPengantarPulang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPengantarPulang.setForeground(new java.awt.Color(70, 70, 70));
        MnPengantarPulang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPengantarPulang.setText("Surat Pengantar Pulang");
        MnPengantarPulang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPengantarPulang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPengantarPulang.setName("MnPengantarPulang"); // NOI18N
        MnPengantarPulang.setPreferredSize(new java.awt.Dimension(210, 26));
        MnPengantarPulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPengantarPulangActionPerformed(evt);
            }
        });
        MnLaporan.add(MnPengantarPulang);

        MnFormulirPenerimaan.setBackground(new java.awt.Color(255, 255, 254));
        MnFormulirPenerimaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnFormulirPenerimaan.setForeground(new java.awt.Color(70, 70, 70));
        MnFormulirPenerimaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnFormulirPenerimaan.setText("Formulir Penerimaan Pasien 1");
        MnFormulirPenerimaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnFormulirPenerimaan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnFormulirPenerimaan.setName("MnFormulirPenerimaan"); // NOI18N
        MnFormulirPenerimaan.setPreferredSize(new java.awt.Dimension(210, 26));
        MnFormulirPenerimaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnFormulirPenerimaanActionPerformed(evt);
            }
        });
        MnLaporan.add(MnFormulirPenerimaan);

        MnFormulirPenerimaan1.setBackground(new java.awt.Color(255, 255, 254));
        MnFormulirPenerimaan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnFormulirPenerimaan1.setForeground(new java.awt.Color(70, 70, 70));
        MnFormulirPenerimaan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnFormulirPenerimaan1.setText("Formulir Penerimaan Pasien 2");
        MnFormulirPenerimaan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnFormulirPenerimaan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnFormulirPenerimaan1.setName("MnFormulirPenerimaan1"); // NOI18N
        MnFormulirPenerimaan1.setPreferredSize(new java.awt.Dimension(210, 26));
        MnFormulirPenerimaan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnFormulirPenerimaan1ActionPerformed(evt);
            }
        });
        MnLaporan.add(MnFormulirPenerimaan1);

        MnCetakSuratOpname.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratOpname.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratOpname.setForeground(new java.awt.Color(70, 70, 70));
        MnCetakSuratOpname.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratOpname.setText("Surat Keterangan Opname");
        MnCetakSuratOpname.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratOpname.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratOpname.setName("MnCetakSuratOpname"); // NOI18N
        MnCetakSuratOpname.setPreferredSize(new java.awt.Dimension(210, 26));
        MnCetakSuratOpname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratOpnameActionPerformed(evt);
            }
        });
        MnLaporan.add(MnCetakSuratOpname);

        MnCetakSuratResumeMedis.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratResumeMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratResumeMedis.setForeground(new java.awt.Color(70, 70, 70));
        MnCetakSuratResumeMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratResumeMedis.setText("Surat Resume Medis");
        MnCetakSuratResumeMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratResumeMedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratResumeMedis.setName("MnCetakSuratResumeMedis"); // NOI18N
        MnCetakSuratResumeMedis.setPreferredSize(new java.awt.Dimension(210, 26));
        MnCetakSuratResumeMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratResumeMedisActionPerformed(evt);
            }
        });
        MnLaporan.add(MnCetakSuratResumeMedis);

        MnCetakSuratIstirahat.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratIstirahat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratIstirahat.setForeground(new java.awt.Color(70, 70, 70));
        MnCetakSuratIstirahat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratIstirahat.setText("Surat Keterangan Istirahat");
        MnCetakSuratIstirahat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratIstirahat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratIstirahat.setName("MnCetakSuratIstirahat"); // NOI18N
        MnCetakSuratIstirahat.setPreferredSize(new java.awt.Dimension(210, 26));
        MnCetakSuratIstirahat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratIstirahatActionPerformed(evt);
            }
        });
        MnLaporan.add(MnCetakSuratIstirahat);

        MnCetakSuratOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSuratOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratOperasi.setForeground(new java.awt.Color(70, 70, 70));
        MnCetakSuratOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratOperasi.setText("Surat Keterangan Pasca Operasi");
        MnCetakSuratOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratOperasi.setName("MnCetakSuratOperasi"); // NOI18N
        MnCetakSuratOperasi.setPreferredSize(new java.awt.Dimension(210, 26));
        MnCetakSuratOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratOperasiActionPerformed(evt);
            }
        });
        MnLaporan.add(MnCetakSuratOperasi);

        MnAsuhanKeperawatan.setBackground(new java.awt.Color(255, 255, 254));
        MnAsuhanKeperawatan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnAsuhanKeperawatan.setForeground(new java.awt.Color(70, 70, 70));
        MnAsuhanKeperawatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnAsuhanKeperawatan.setText("Asuhan Keperawatan");
        MnAsuhanKeperawatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnAsuhanKeperawatan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnAsuhanKeperawatan.setName("MnAsuhanKeperawatan"); // NOI18N
        MnAsuhanKeperawatan.setPreferredSize(new java.awt.Dimension(210, 26));
        MnAsuhanKeperawatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnAsuhanKeperawatanActionPerformed(evt);
            }
        });
        MnLaporan.add(MnAsuhanKeperawatan);

        MnSoapResume.setBackground(new java.awt.Color(255, 255, 254));
        MnSoapResume.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSoapResume.setForeground(new java.awt.Color(70, 70, 70));
        MnSoapResume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSoapResume.setText("Laporan SOAP");
        MnSoapResume.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSoapResume.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSoapResume.setName("MnSoapResume"); // NOI18N
        MnSoapResume.setPreferredSize(new java.awt.Dimension(210, 26));
        MnSoapResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSoapResumeActionPerformed(evt);
            }
        });
        MnLaporan.add(MnSoapResume);

        jPopupMenu1.add(MnLaporan);

        MnGelang.setBackground(new java.awt.Color(250, 255, 245));
        MnGelang.setForeground(new java.awt.Color(70, 70, 70));
        MnGelang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang.setText("Label & Gelang Pasien");
        MnGelang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang.setIconTextGap(5);
        MnGelang.setName("MnGelang"); // NOI18N
        MnGelang.setPreferredSize(new java.awt.Dimension(220, 26));

        MnLabelTracker.setBackground(new java.awt.Color(255, 255, 254));
        MnLabelTracker.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelTracker.setForeground(new java.awt.Color(70, 70, 70));
        MnLabelTracker.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLabelTracker.setText("Label Tracker");
        MnLabelTracker.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLabelTracker.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLabelTracker.setName("MnLabelTracker"); // NOI18N
        MnLabelTracker.setPreferredSize(new java.awt.Dimension(180, 26));
        MnLabelTracker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLabelTrackerActionPerformed(evt);
            }
        });
        MnGelang.add(MnLabelTracker);

        MnBarcodeRM9.setBackground(new java.awt.Color(255, 255, 254));
        MnBarcodeRM9.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcodeRM9.setForeground(new java.awt.Color(70, 70, 70));
        MnBarcodeRM9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcodeRM9.setMnemonic('L');
        MnBarcodeRM9.setText("Label RM isi 14");
        MnBarcodeRM9.setToolTipText("L");
        MnBarcodeRM9.setName("MnBarcodeRM9"); // NOI18N
        MnBarcodeRM9.setPreferredSize(new java.awt.Dimension(180, 26));
        MnBarcodeRM9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcodeRM9ActionPerformed(evt);
            }
        });
        MnGelang.add(MnBarcodeRM9);

        MnBarcodeRM10.setBackground(new java.awt.Color(255, 255, 254));
        MnBarcodeRM10.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcodeRM10.setForeground(new java.awt.Color(70, 70, 70));
        MnBarcodeRM10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBarcodeRM10.setMnemonic('L');
        MnBarcodeRM10.setText("Label RM isi 16");
        MnBarcodeRM10.setToolTipText("L");
        MnBarcodeRM10.setName("MnBarcodeRM10"); // NOI18N
        MnBarcodeRM10.setPreferredSize(new java.awt.Dimension(180, 26));
        MnBarcodeRM10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcodeRM10ActionPerformed(evt);
            }
        });
        MnGelang.add(MnBarcodeRM10);

        MnGelang3.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang3.setForeground(new java.awt.Color(70, 70, 70));
        MnGelang3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang3.setText("Gelang Pasien Anak");
        MnGelang3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang3.setName("MnGelang3"); // NOI18N
        MnGelang3.setPreferredSize(new java.awt.Dimension(200, 26));
        MnGelang3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang3ActionPerformed(evt);
            }
        });
        MnGelang.add(MnGelang3);

        MnGelang5.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang5.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang5.setForeground(new java.awt.Color(70, 70, 70));
        MnGelang5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang5.setText("Gelang Pasien Bayi");
        MnGelang5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang5.setName("MnGelang5"); // NOI18N
        MnGelang5.setPreferredSize(new java.awt.Dimension(200, 26));
        MnGelang5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang5ActionPerformed(evt);
            }
        });
        MnGelang.add(MnGelang5);

        MnGelang4.setBackground(new java.awt.Color(255, 255, 254));
        MnGelang4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang4.setForeground(new java.awt.Color(70, 70, 70));
        MnGelang4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGelang4.setText("Gelang Pasien Dewasa");
        MnGelang4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang4.setName("MnGelang4"); // NOI18N
        MnGelang4.setPreferredSize(new java.awt.Dimension(200, 26));
        MnGelang4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang4ActionPerformed(evt);
            }
        });
        MnGelang.add(MnGelang4);

        MnLabelSyringpump.setBackground(new java.awt.Color(255, 255, 254));
        MnLabelSyringpump.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelSyringpump.setForeground(new java.awt.Color(70, 70, 70));
        MnLabelSyringpump.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLabelSyringpump.setText("Label Syring Pump");
        MnLabelSyringpump.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLabelSyringpump.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLabelSyringpump.setName("MnLabelSyringpump"); // NOI18N
        MnLabelSyringpump.setPreferredSize(new java.awt.Dimension(200, 26));
        MnLabelSyringpump.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLabelSyringpumpActionPerformed(evt);
            }
        });
        MnGelang.add(MnLabelSyringpump);

        jPopupMenu1.add(MnGelang);

        MnRanapGabung.setBackground(new java.awt.Color(255, 255, 254));
        MnRanapGabung.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRanapGabung.setForeground(new java.awt.Color(70, 70, 70));
        MnRanapGabung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRanapGabung.setText("Ranap Gabung Ibu & Bayi");
        MnRanapGabung.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRanapGabung.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRanapGabung.setName("MnRanapGabung"); // NOI18N
        MnRanapGabung.setPreferredSize(new java.awt.Dimension(220, 26));
        MnRanapGabung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRanapGabungActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnRanapGabung);

        MnGabungkanRanap.setBackground(new java.awt.Color(255, 255, 254));
        MnGabungkanRanap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGabungkanRanap.setForeground(new java.awt.Color(70, 70, 70));
        MnGabungkanRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnGabungkanRanap.setText("Gabungkan Ke Kamar Ibu");
        MnGabungkanRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGabungkanRanap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGabungkanRanap.setName("MnGabungkanRanap"); // NOI18N
        MnGabungkanRanap.setPreferredSize(new java.awt.Dimension(220, 26));
        MnGabungkanRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGabungkanRanapActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnGabungkanRanap);

        MnDPJP.setBackground(new java.awt.Color(255, 255, 254));
        MnDPJP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDPJP.setForeground(new java.awt.Color(70, 70, 70));
        MnDPJP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDPJP.setText("Input Dokter P.J. Ranap");
        MnDPJP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDPJP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDPJP.setName("MnDPJP"); // NOI18N
        MnDPJP.setPreferredSize(new java.awt.Dimension(220, 26));
        MnDPJP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDPJPActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnDPJP);

        MnDPJPRanap.setBackground(new java.awt.Color(255, 255, 254));
        MnDPJPRanap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDPJPRanap.setForeground(new java.awt.Color(70, 70, 70));
        MnDPJPRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDPJPRanap.setText("Tampilkan Dokter P.J. Ranap");
        MnDPJPRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDPJPRanap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDPJPRanap.setName("MnDPJPRanap"); // NOI18N
        MnDPJPRanap.setPreferredSize(new java.awt.Dimension(220, 26));
        MnDPJPRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDPJPRanapActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnDPJPRanap);

        jSeparator13.setBackground(new java.awt.Color(190, 220, 180));
        jSeparator13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(190, 220, 180)));
        jSeparator13.setForeground(new java.awt.Color(190, 220, 180));
        jSeparator13.setName("jSeparator13"); // NOI18N
        jSeparator13.setPreferredSize(new java.awt.Dimension(220, 1));
        jPopupMenu1.add(jSeparator13);

        MnRujukan.setBackground(new java.awt.Color(250, 255, 245));
        MnRujukan.setForeground(new java.awt.Color(70, 70, 70));
        MnRujukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRujukan.setText("Rujukan");
        MnRujukan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujukan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRujukan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRujukan.setIconTextGap(5);
        MnRujukan.setName("MnRujukan"); // NOI18N
        MnRujukan.setPreferredSize(new java.awt.Dimension(210, 26));

        MnRujuk.setBackground(new java.awt.Color(255, 255, 254));
        MnRujuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujuk.setForeground(new java.awt.Color(70, 70, 70));
        MnRujuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRujuk.setText("Rujukan Keluar");
        MnRujuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRujuk.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRujuk.setName("MnRujuk"); // NOI18N
        MnRujuk.setPreferredSize(new java.awt.Dimension(150, 26));
        MnRujuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRujukActionPerformed(evt);
            }
        });
        MnRujukan.add(MnRujuk);

        MnRujukMasuk.setBackground(new java.awt.Color(255, 255, 254));
        MnRujukMasuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujukMasuk.setForeground(new java.awt.Color(70, 70, 70));
        MnRujukMasuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRujukMasuk.setText("Rujukan Masuk");
        MnRujukMasuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRujukMasuk.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRujukMasuk.setName("MnRujukMasuk"); // NOI18N
        MnRujukMasuk.setPreferredSize(new java.awt.Dimension(150, 26));
        MnRujukMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRujukMasukActionPerformed(evt);
            }
        });
        MnRujukan.add(MnRujukMasuk);

        jPopupMenu1.add(MnRujukan);

        MenuBPJS.setBackground(new java.awt.Color(250, 255, 245));
        MenuBPJS.setForeground(new java.awt.Color(70, 70, 70));
        MenuBPJS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MenuBPJS.setText("Bridging");
        MenuBPJS.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MenuBPJS.setIconTextGap(5);
        MenuBPJS.setName("MenuBPJS"); // NOI18N
        MenuBPJS.setPreferredSize(new java.awt.Dimension(220, 26));

        MnCekKepesertaan.setBackground(new java.awt.Color(255, 255, 254));
        MnCekKepesertaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCekKepesertaan.setForeground(new java.awt.Color(70, 70, 70));
        MnCekKepesertaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCekKepesertaan.setText("Pencarian Peserta BPJS Berdasarkan Nomor Kepesertaan");
        MnCekKepesertaan.setName("MnCekKepesertaan"); // NOI18N
        MnCekKepesertaan.setPreferredSize(new java.awt.Dimension(330, 26));
        MnCekKepesertaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCekKepesertaanActionPerformed(evt);
            }
        });
        MenuBPJS.add(MnCekKepesertaan);

        MnCekNIK.setBackground(new java.awt.Color(255, 255, 254));
        MnCekNIK.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCekNIK.setForeground(new java.awt.Color(70, 70, 70));
        MnCekNIK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCekNIK.setText("Pencarian Peserta BPJS Berdasarkan NIK/No.KTP");
        MnCekNIK.setName("MnCekNIK"); // NOI18N
        MnCekNIK.setPreferredSize(new java.awt.Dimension(330, 26));
        MnCekNIK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCekNIKActionPerformed(evt);
            }
        });
        MenuBPJS.add(MnCekNIK);

        MnSEP.setBackground(new java.awt.Color(255, 255, 254));
        MnSEP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSEP.setForeground(new java.awt.Color(70, 70, 70));
        MnSEP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSEP.setText("Bridging SEP VClaim");
        MnSEP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSEP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSEP.setName("MnSEP"); // NOI18N
        MnSEP.setPreferredSize(new java.awt.Dimension(330, 26));
        MnSEP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSEPActionPerformed(evt);
            }
        });
        MenuBPJS.add(MnSEP);

        MnSEPNoCek.setBackground(new java.awt.Color(255, 255, 254));
        MnSEPNoCek.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSEPNoCek.setForeground(new java.awt.Color(70, 70, 70));
        MnSEPNoCek.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSEPNoCek.setText("Tarik SEP VClaim");
        MnSEPNoCek.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSEPNoCek.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSEPNoCek.setName("MnSEPNoCek"); // NOI18N
        MnSEPNoCek.setPreferredSize(new java.awt.Dimension(330, 26));
        MnSEPNoCek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSEPNoCekActionPerformed(evt);
            }
        });
        MenuBPJS.add(MnSEPNoCek);

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
        MenuBPJS.add(ppSuratKontrol);

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
        MenuBPJS.add(ppSuratPRI);

        MnRujukSisrute.setBackground(new java.awt.Color(255, 255, 254));
        MnRujukSisrute.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujukSisrute.setForeground(new java.awt.Color(70, 70, 70));
        MnRujukSisrute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRujukSisrute.setText("Rujuk Keluar Via Sisrute");
        MnRujukSisrute.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRujukSisrute.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRujukSisrute.setName("MnRujukSisrute"); // NOI18N
        MnRujukSisrute.setPreferredSize(new java.awt.Dimension(330, 26));
        MnRujukSisrute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRujukSisruteActionPerformed(evt);
            }
        });
        MenuBPJS.add(MnRujukSisrute);

        MnLapCov19V3.setBackground(new java.awt.Color(255, 255, 254));
        MnLapCov19V3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLapCov19V3.setForeground(new java.awt.Color(70, 70, 70));
        MnLapCov19V3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLapCov19V3.setText("Bridging Covid 19 V3");
        MnLapCov19V3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLapCov19V3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLapCov19V3.setName("MnLapCov19V3"); // NOI18N
        MnLapCov19V3.setPreferredSize(new java.awt.Dimension(330, 26));
        MnLapCov19V3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLapCov19V3ActionPerformed(evt);
            }
        });
        MenuBPJS.add(MnLapCov19V3);

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
        MenuBPJS.add(MnPACS);

        MnHasilLab.setBackground(new java.awt.Color(255, 255, 254));
        MnHasilLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHasilLab.setForeground(new java.awt.Color(70, 70, 70));
        MnHasilLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHasilLab.setText("Hasil Lab");
        MnHasilLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHasilLab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHasilLab.setName("MnHasilLab"); // NOI18N
        MnHasilLab.setPreferredSize(new java.awt.Dimension(180, 26));
        MnHasilLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHasilLabActionPerformed(evt);
            }
        });
        MenuBPJS.add(MnHasilLab);

        jPopupMenu1.add(MenuBPJS);

        MenuInputData.setBackground(new java.awt.Color(250, 255, 245));
        MenuInputData.setForeground(new java.awt.Color(70, 70, 70));
        MenuInputData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MenuInputData.setText("Input Data");
        MenuInputData.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MenuInputData.setIconTextGap(5);
        MenuInputData.setName("MenuInputData"); // NOI18N
        MenuInputData.setPreferredSize(new java.awt.Dimension(220, 26));

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

        ppDataHAIs.setBackground(new java.awt.Color(255, 255, 254));
        ppDataHAIs.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppDataHAIs.setForeground(new java.awt.Color(70, 70, 70));
        ppDataHAIs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppDataHAIs.setText("Data HAIs");
        ppDataHAIs.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDataHAIs.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDataHAIs.setName("ppDataHAIs"); // NOI18N
        ppDataHAIs.setPreferredSize(new java.awt.Dimension(200, 26));
        ppDataHAIs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDataHAIsBtnPrintActionPerformed(evt);
            }
        });
        MenuInputData.add(ppDataHAIs);

        ppDataIdo.setBackground(new java.awt.Color(255, 255, 254));
        ppDataIdo.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppDataIdo.setForeground(new java.awt.Color(70, 70, 70));
        ppDataIdo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppDataIdo.setText("Data IDO");
        ppDataIdo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDataIdo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDataIdo.setName("ppDataIdo"); // NOI18N
        ppDataIdo.setPreferredSize(new java.awt.Dimension(200, 26));
        ppDataIdo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDataIdoBtnPrintActionPerformed(evt);
            }
        });
        MenuInputData.add(ppDataIdo);

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

        MnKelahiranBayi.setBackground(new java.awt.Color(255, 255, 254));
        MnKelahiranBayi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKelahiranBayi.setForeground(new java.awt.Color(70, 70, 70));
        MnKelahiranBayi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKelahiranBayi.setText("Data Kelahiran Bayi");
        MnKelahiranBayi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnKelahiranBayi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnKelahiranBayi.setName("MnKelahiranBayi"); // NOI18N
        MnKelahiranBayi.setPreferredSize(new java.awt.Dimension(200, 26));
        MnKelahiranBayi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKelahiranBayiActionPerformed(evt);
            }
        });
        MenuInputData.add(MnKelahiranBayi);

        jPopupMenu1.add(MenuInputData);

        SetStatus.setBackground(new java.awt.Color(250, 255, 245));
        SetStatus.setForeground(new java.awt.Color(70, 70, 70));
        SetStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        SetStatus.setText("Set Status Pulang");
        SetStatus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        SetStatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        SetStatus.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        SetStatus.setIconTextGap(5);
        SetStatus.setName("SetStatus"); // NOI18N
        SetStatus.setPreferredSize(new java.awt.Dimension(220, 26));

        MnStatusRujuk.setBackground(new java.awt.Color(255, 255, 254));
        MnStatusRujuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStatusRujuk.setForeground(new java.awt.Color(70, 70, 70));
        MnStatusRujuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnStatusRujuk.setText("Rujuk");
        MnStatusRujuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStatusRujuk.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStatusRujuk.setName("MnStatusRujuk"); // NOI18N
        MnStatusRujuk.setPreferredSize(new java.awt.Dimension(200, 26));
        MnStatusRujuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnStatusRujukActionPerformed(evt);
            }
        });
        SetStatus.add(MnStatusRujuk);

        MnStatusAPS.setBackground(new java.awt.Color(255, 255, 254));
        MnStatusAPS.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStatusAPS.setForeground(new java.awt.Color(70, 70, 70));
        MnStatusAPS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnStatusAPS.setText("APS");
        MnStatusAPS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStatusAPS.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStatusAPS.setName("MnStatusAPS"); // NOI18N
        MnStatusAPS.setPreferredSize(new java.awt.Dimension(200, 26));
        MnStatusAPS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnStatusAPSActionPerformed(evt);
            }
        });
        SetStatus.add(MnStatusAPS);

        MnStatusMeninggal.setBackground(new java.awt.Color(255, 255, 254));
        MnStatusMeninggal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStatusMeninggal.setForeground(new java.awt.Color(70, 70, 70));
        MnStatusMeninggal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnStatusMeninggal.setText("Meninggal");
        MnStatusMeninggal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStatusMeninggal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStatusMeninggal.setName("MnStatusMeninggal"); // NOI18N
        MnStatusMeninggal.setPreferredSize(new java.awt.Dimension(200, 26));
        MnStatusMeninggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnStatusMeninggalActionPerformed(evt);
            }
        });
        SetStatus.add(MnStatusMeninggal);

        MnStatusMembaik.setBackground(new java.awt.Color(255, 255, 254));
        MnStatusMembaik.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStatusMembaik.setForeground(new java.awt.Color(70, 70, 70));
        MnStatusMembaik.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnStatusMembaik.setText("Membaik");
        MnStatusMembaik.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStatusMembaik.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStatusMembaik.setName("MnStatusMembaik"); // NOI18N
        MnStatusMembaik.setPreferredSize(new java.awt.Dimension(200, 26));
        MnStatusMembaik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnStatusMembaikActionPerformed(evt);
            }
        });
        SetStatus.add(MnStatusMembaik);

        MnStatusBelumPulang.setBackground(new java.awt.Color(255, 255, 254));
        MnStatusBelumPulang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStatusBelumPulang.setForeground(new java.awt.Color(70, 70, 70));
        MnStatusBelumPulang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnStatusBelumPulang.setText("Belum Pulang");
        MnStatusBelumPulang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStatusBelumPulang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStatusBelumPulang.setName("MnStatusBelumPulang"); // NOI18N
        MnStatusBelumPulang.setPreferredSize(new java.awt.Dimension(200, 26));
        MnStatusBelumPulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnStatusBelumPulangActionPerformed(evt);
            }
        });
        SetStatus.add(MnStatusBelumPulang);

        jPopupMenu1.add(SetStatus);

        ppRiwayat.setBackground(new java.awt.Color(255, 255, 254));
        ppRiwayat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRiwayat.setForeground(new java.awt.Color(70, 70, 70));
        ppRiwayat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRiwayat.setText("Riwayat Perawatan");
        ppRiwayat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRiwayat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRiwayat.setName("ppRiwayat"); // NOI18N
        ppRiwayat.setPreferredSize(new java.awt.Dimension(220, 26));
        ppRiwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRiwayatBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppRiwayat);

        MnUpdateHari.setBackground(new java.awt.Color(255, 255, 254));
        MnUpdateHari.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUpdateHari.setForeground(new java.awt.Color(70, 70, 70));
        MnUpdateHari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUpdateHari.setText("Update Hari Perawatan");
        MnUpdateHari.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUpdateHari.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUpdateHari.setName("MnUpdateHari"); // NOI18N
        MnUpdateHari.setPreferredSize(new java.awt.Dimension(220, 26));
        MnUpdateHari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUpdateHariActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnUpdateHari);

        MnPenjab.setBackground(new java.awt.Color(255, 255, 254));
        MnPenjab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenjab.setForeground(new java.awt.Color(70, 70, 70));
        MnPenjab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenjab.setText("Ganti Jenis Bayar");
        MnPenjab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenjab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenjab.setName("MnPenjab"); // NOI18N
        MnPenjab.setPreferredSize(new java.awt.Dimension(220, 26));
        MnPenjab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenjabActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenjab);

        MnHapusData.setBackground(new java.awt.Color(250, 255, 245));
        MnHapusData.setForeground(new java.awt.Color(70, 70, 70));
        MnHapusData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusData.setText("Hapus Data");
        MnHapusData.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusData.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusData.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusData.setIconTextGap(5);
        MnHapusData.setName("MnHapusData"); // NOI18N
        MnHapusData.setPreferredSize(new java.awt.Dimension(220, 26));

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

        MnHapusDataSalah.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusDataSalah.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusDataSalah.setForeground(new java.awt.Color(70, 70, 70));
        MnHapusDataSalah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusDataSalah.setText("Hapus Data Salah");
        MnHapusDataSalah.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusDataSalah.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusDataSalah.setName("MnHapusDataSalah"); // NOI18N
        MnHapusDataSalah.setPreferredSize(new java.awt.Dimension(200, 26));
        MnHapusDataSalah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusDataSalahActionPerformed(evt);
            }
        });
        MnHapusData.add(MnHapusDataSalah);

        jPopupMenu1.add(MnHapusData);

        JamMasuk.setEditable(false);
        JamMasuk.setForeground(new java.awt.Color(255, 255, 255));
        JamMasuk.setHighlighter(null);
        JamMasuk.setName("JamMasuk"); // NOI18N
        JamMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamMasukKeyPressed(evt);
            }
        });

        WindowPindahKamar.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowPindahKamar.setName("WindowPindahKamar"); // NOI18N
        WindowPindahKamar.setUndecorated(true);
        WindowPindahKamar.setResizable(false);
        WindowPindahKamar.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                WindowPindahKamarWindowActivated(evt);
            }
        });

        internalFrame3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(215, 225, 205)), "::[ Pindah Kamar Inap Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(null);

        norawatpindah.setEditable(false);
        norawatpindah.setHighlighter(null);
        norawatpindah.setName("norawatpindah"); // NOI18N
        internalFrame3.add(norawatpindah);
        norawatpindah.setBounds(75, 25, 150, 23);

        TPasienpindah.setEditable(false);
        TPasienpindah.setHighlighter(null);
        TPasienpindah.setName("TPasienpindah"); // NOI18N
        internalFrame3.add(TPasienpindah);
        TPasienpindah.setBounds(359, 25, 299, 23);

        kdkamarpindah.setHighlighter(null);
        kdkamarpindah.setName("kdkamarpindah"); // NOI18N
        kdkamarpindah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdkamarpindahKeyPressed(evt);
            }
        });
        internalFrame3.add(kdkamarpindah);
        kdkamarpindah.setBounds(75, 55, 95, 23);

        ttlbiayapindah.setEditable(false);
        ttlbiayapindah.setText("0");
        ttlbiayapindah.setHighlighter(null);
        ttlbiayapindah.setName("ttlbiayapindah"); // NOI18N
        internalFrame3.add(ttlbiayapindah);
        ttlbiayapindah.setBounds(368, 115, 290, 23);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        internalFrame3.add(jLabel4);
        jLabel4.setBounds(0, 25, 72, 23);

        jLabel20.setText("Kamar :");
        jLabel20.setName("jLabel20"); // NOI18N
        internalFrame3.add(jLabel20);
        jLabel20.setBounds(0, 55, 72, 23);

        TNoRMpindah.setEditable(false);
        TNoRMpindah.setHighlighter(null);
        TNoRMpindah.setName("TNoRMpindah"); // NOI18N
        internalFrame3.add(TNoRMpindah);
        TNoRMpindah.setBounds(227, 25, 130, 23);

        btnKamar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKamar2.setMnemonic('2');
        btnKamar2.setToolTipText("Alt+2");
        btnKamar2.setName("btnKamar2"); // NOI18N
        btnKamar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKamar2ActionPerformed(evt);
            }
        });
        internalFrame3.add(btnKamar2);
        btnKamar2.setBounds(428, 55, 28, 23);

        TKdBngsalpindah.setEditable(false);
        TKdBngsalpindah.setName("TKdBngsalpindah"); // NOI18N
        internalFrame3.add(TKdBngsalpindah);
        TKdBngsalpindah.setBounds(172, 55, 82, 23);

        jLabel27.setText("Tanggal :");
        jLabel27.setName("jLabel27"); // NOI18N
        internalFrame3.add(jLabel27);
        jLabel27.setBounds(0, 85, 72, 23);

        cmbDtkpindah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtkpindah.setName("cmbDtkpindah"); // NOI18N
        internalFrame3.add(cmbDtkpindah);
        cmbDtkpindah.setBounds(508, 85, 62, 23);

        cmbMntpindah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMntpindah.setName("cmbMntpindah"); // NOI18N
        internalFrame3.add(cmbMntpindah);
        cmbMntpindah.setBounds(441, 85, 62, 23);

        cmbJampindah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJampindah.setName("cmbJampindah"); // NOI18N
        internalFrame3.add(cmbJampindah);
        cmbJampindah.setBounds(374, 85, 62, 23);

        TBangsalpindah.setEditable(false);
        TBangsalpindah.setHighlighter(null);
        TBangsalpindah.setName("TBangsalpindah"); // NOI18N
        TBangsalpindah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBangsalpindahKeyPressed(evt);
            }
        });
        internalFrame3.add(TBangsalpindah);
        TBangsalpindah.setBounds(256, 55, 170, 23);

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("X");
        jLabel28.setName("jLabel28"); // NOI18N
        internalFrame3.add(jLabel28);
        jLabel28.setBounds(173, 115, 15, 23);

        TJmlHaripindah.setText("1");
        TJmlHaripindah.setHighlighter(null);
        TJmlHaripindah.setName("TJmlHaripindah"); // NOI18N
        internalFrame3.add(TJmlHaripindah);
        TJmlHaripindah.setBounds(75, 115, 96, 23);

        jLabel29.setText("=");
        jLabel29.setName("jLabel29"); // NOI18N
        internalFrame3.add(jLabel29);
        jLabel29.setBounds(342, 115, 20, 23);

        TSttsKamarpindah.setEditable(false);
        TSttsKamarpindah.setName("TSttsKamarpindah"); // NOI18N
        internalFrame3.add(TSttsKamarpindah);
        TSttsKamarpindah.setBounds(548, 55, 110, 23);

        BtnCloseInpindah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseInpindah.setMnemonic('U');
        BtnCloseInpindah.setText("Tutup");
        BtnCloseInpindah.setToolTipText("Alt+U");
        BtnCloseInpindah.setName("BtnCloseInpindah"); // NOI18N
        BtnCloseInpindah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseInpindahActionPerformed(evt);
            }
        });
        BtnCloseInpindah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCloseInpindahKeyPressed(evt);
            }
        });
        internalFrame3.add(BtnCloseInpindah);
        BtnCloseInpindah.setBounds(560, 235, 100, 30);

        jLabel30.setText("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        jLabel30.setName("jLabel30"); // NOI18N
        internalFrame3.add(jLabel30);
        jLabel30.setBounds(-10, 215, 850, 14);

        BtnSimpanpindah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpanpindah.setMnemonic('S');
        BtnSimpanpindah.setText("Simpan");
        BtnSimpanpindah.setToolTipText("Alt+S");
        BtnSimpanpindah.setName("BtnSimpanpindah"); // NOI18N
        BtnSimpanpindah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanpindahActionPerformed(evt);
            }
        });
        BtnSimpanpindah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanpindahKeyPressed(evt);
            }
        });
        internalFrame3.add(BtnSimpanpindah);
        BtnSimpanpindah.setBounds(14, 235, 100, 30);

        jLabel31.setText("Stts.Kamar :");
        jLabel31.setName("jLabel31"); // NOI18N
        internalFrame3.add(jLabel31);
        jLabel31.setBounds(444, 55, 100, 23);

        jLabel32.setText("Pilihan :");
        jLabel32.setName("jLabel32"); // NOI18N
        internalFrame3.add(jLabel32);
        jLabel32.setBounds(0, 145, 72, 23);

        TTarifpindah.setEditable(false);
        TTarifpindah.setText("0");
        TTarifpindah.setHighlighter(null);
        TTarifpindah.setName("TTarifpindah"); // NOI18N
        internalFrame3.add(TTarifpindah);
        TTarifpindah.setBounds(188, 115, 160, 23);

        CmbTahunpindah.setName("CmbTahunpindah"); // NOI18N
        internalFrame3.add(CmbTahunpindah);
        CmbTahunpindah.setBounds(209, 85, 100, 23);

        CmbBlnpindah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        CmbBlnpindah.setName("CmbBlnpindah"); // NOI18N
        internalFrame3.add(CmbBlnpindah);
        CmbBlnpindah.setBounds(142, 85, 62, 23);

        CmbTglpindah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        CmbTglpindah.setName("CmbTglpindah"); // NOI18N
        internalFrame3.add(CmbTglpindah);
        CmbTglpindah.setBounds(75, 85, 62, 23);

        jLabel35.setText("Jam :");
        jLabel35.setName("jLabel35"); // NOI18N
        internalFrame3.add(jLabel35);
        jLabel35.setBounds(304, 85, 67, 23);

        buttonGroup2.add(Rganti3);
        Rganti3.setSelected(true);
        Rganti3.setText("2. Pilih yang ini jika ingin MEMINDAH PASIEN");
        Rganti3.setName("Rganti3"); // NOI18N
        internalFrame3.add(Rganti3);
        Rganti3.setBounds(75, 159, 620, 20);

        jLabel33.setText("Biaya :");
        jLabel33.setName("jLabel33"); // NOI18N
        internalFrame3.add(jLabel33);
        jLabel33.setBounds(0, 115, 72, 23);

        buttonGroup2.add(Rganti2);
        Rganti2.setText("1.Pilih yang ini jika SALAH KAMAR");
        Rganti2.setName("Rganti2"); // NOI18N
        internalFrame3.add(Rganti2);
        Rganti2.setBounds(75, 142, 620, 20);

        buttonGroup2.add(Rganti1);
        Rganti1.setText("1. Kamar Inap sebelumnya dihapus dan pasien dihitung menginap mulai saat ini (Kamar Inap lama dihapus dari billing)");
        Rganti1.setName("Rganti1"); // NOI18N
        internalFrame3.add(Rganti1);
        Rganti1.setBounds(75, 176, 620, 20);

        buttonGroup2.add(Rganti4);
        Rganti4.setText("4. Seperti nomer 3, Kamar Inap sebelumnya mengikuti harga tertinggi");
        Rganti4.setName("Rganti4"); // NOI18N
        internalFrame3.add(Rganti4);
        Rganti4.setBounds(75, 193, 620, 20);

        WindowPindahKamar.getContentPane().add(internalFrame3, java.awt.BorderLayout.CENTER);

        WindowCaraBayar.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowCaraBayar.setName("WindowCaraBayar"); // NOI18N
        WindowCaraBayar.setUndecorated(true);
        WindowCaraBayar.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ganti Jenis Bayar ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(null);

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
        internalFrame5.add(BtnCloseIn4);
        BtnCloseIn4.setBounds(510, 30, 100, 30);

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
        internalFrame5.add(BtnSimpan4);
        BtnSimpan4.setBounds(405, 30, 100, 30);

        jLabel17.setText("Jenis Bayar :");
        jLabel17.setName("jLabel17"); // NOI18N
        internalFrame5.add(jLabel17);
        jLabel17.setBounds(0, 32, 77, 23);

        kdpenjab.setHighlighter(null);
        kdpenjab.setName("kdpenjab"); // NOI18N
        kdpenjab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpenjabKeyPressed(evt);
            }
        });
        internalFrame5.add(kdpenjab);
        kdpenjab.setBounds(81, 32, 100, 23);

        nmpenjab.setEditable(false);
        nmpenjab.setName("nmpenjab"); // NOI18N
        internalFrame5.add(nmpenjab);
        nmpenjab.setBounds(183, 32, 181, 23);

        btnBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBayar.setMnemonic('7');
        btnBayar.setToolTipText("ALt+7");
        btnBayar.setName("btnBayar"); // NOI18N
        btnBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBayarActionPerformed(evt);
            }
        });
        internalFrame5.add(btnBayar);
        btnBayar.setBounds(366, 32, 28, 23);

        WindowCaraBayar.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        WindowRanapGabung.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowRanapGabung.setName("WindowRanapGabung"); // NOI18N
        WindowRanapGabung.setUndecorated(true);
        WindowRanapGabung.setResizable(false);

        internalFrame6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ranap Gabung Ibu & Bayi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setLayout(null);

        BtnCloseGabung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseGabung.setMnemonic('U');
        BtnCloseGabung.setText("Tutup");
        BtnCloseGabung.setToolTipText("Alt+U");
        BtnCloseGabung.setName("BtnCloseGabung"); // NOI18N
        BtnCloseGabung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseGabungActionPerformed(evt);
            }
        });
        internalFrame6.add(BtnCloseGabung);
        BtnCloseGabung.setBounds(510, 70, 100, 30);

        BtnSimpanGabung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpanGabung.setMnemonic('S');
        BtnSimpanGabung.setText("Simpan");
        BtnSimpanGabung.setToolTipText("Alt+S");
        BtnSimpanGabung.setName("BtnSimpanGabung"); // NOI18N
        BtnSimpanGabung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanGabungActionPerformed(evt);
            }
        });
        internalFrame6.add(BtnSimpanGabung);
        BtnSimpanGabung.setBounds(17, 70, 100, 30);

        jLabel34.setText("No.R.M.Bayi :");
        jLabel34.setName("jLabel34"); // NOI18N
        internalFrame6.add(jLabel34);
        jLabel34.setBounds(2, 30, 87, 23);

        NoRmBayi.setHighlighter(null);
        NoRmBayi.setName("NoRmBayi"); // NOI18N
        NoRmBayi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRmBayiKeyPressed(evt);
            }
        });
        internalFrame6.add(NoRmBayi);
        NoRmBayi.setBounds(92, 30, 100, 23);

        NmBayi.setEditable(false);
        NmBayi.setName("NmBayi"); // NOI18N
        internalFrame6.add(NmBayi);
        NmBayi.setBounds(193, 30, 350, 23);

        btnPasienRanapGabung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPasienRanapGabung.setMnemonic('7');
        btnPasienRanapGabung.setToolTipText("ALt+7");
        btnPasienRanapGabung.setName("btnPasienRanapGabung"); // NOI18N
        btnPasienRanapGabung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasienRanapGabungActionPerformed(evt);
            }
        });
        internalFrame6.add(btnPasienRanapGabung);
        btnPasienRanapGabung.setBounds(546, 30, 28, 23);

        BtnHapusGabung.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapusGabung.setMnemonic('H');
        BtnHapusGabung.setText("Hapus");
        BtnHapusGabung.setToolTipText("Alt+H");
        BtnHapusGabung.setName("BtnHapusGabung"); // NOI18N
        BtnHapusGabung.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapusGabung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusGabungActionPerformed(evt);
            }
        });
        internalFrame6.add(BtnHapusGabung);
        BtnHapusGabung.setBounds(123, 70, 100, 30);

        NoRawatGabung.setHighlighter(null);
        NoRawatGabung.setName("NoRawatGabung"); // NOI18N
        internalFrame6.add(NoRawatGabung);
        NoRawatGabung.setBounds(230, 220, 190, 23);

        btnPasienRanapGabung1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPasienRanapGabung1.setMnemonic('7');
        btnPasienRanapGabung1.setToolTipText("ALt+7");
        btnPasienRanapGabung1.setName("btnPasienRanapGabung1"); // NOI18N
        btnPasienRanapGabung1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasienRanapGabung1ActionPerformed(evt);
            }
        });
        internalFrame6.add(btnPasienRanapGabung1);
        btnPasienRanapGabung1.setBounds(576, 30, 28, 23);

        WindowRanapGabung.getContentPane().add(internalFrame6, java.awt.BorderLayout.CENTER);

        DlgSakit2.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgSakit2.setName("DlgSakit2"); // NOI18N
        DlgSakit2.setUndecorated(true);
        DlgSakit2.setResizable(false);

        internalFrame7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Cetak Surat Keterangan Rawat Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame7.setName("internalFrame7"); // NOI18N
        internalFrame7.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa4.setName("panelBiasa4"); // NOI18N
        panelBiasa4.setLayout(null);

        jLabel38.setText("Nomor Surat Keterangan :");
        jLabel38.setName("jLabel38"); // NOI18N
        panelBiasa4.add(jLabel38);
        jLabel38.setBounds(7, 10, 140, 23);

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

        jLabel36.setText("Dokter Png. Jawab :");
        jLabel36.setName("jLabel36"); // NOI18N
        jLabel36.setPreferredSize(new java.awt.Dimension(60, 23));
        panelBiasa4.add(jLabel36);
        jLabel36.setBounds(7, 40, 140, 23);

        internalFrame7.add(panelBiasa4, java.awt.BorderLayout.CENTER);

        DlgSakit2.getContentPane().add(internalFrame7, java.awt.BorderLayout.CENTER);

        WindowCetakBerkas.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowCetakBerkas.setName("WindowCetakBerkas"); // NOI18N
        WindowCetakBerkas.setUndecorated(true);
        WindowCetakBerkas.setResizable(false);

        internalFrame10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Cetak Berkas Rekam Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame10.setName("internalFrame10"); // NOI18N
        internalFrame10.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi2.setName("panelisi2"); // NOI18N
        panelisi2.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(350, 23));
        panelisi2.add(label17);

        BtnCetak4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnCetak4.setMnemonic('P');
        BtnCetak4.setText("Cetak");
        BtnCetak4.setToolTipText("Alt+P");
        BtnCetak4.setName("BtnCetak4"); // NOI18N
        BtnCetak4.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnCetak4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCetak4ActionPerformed(evt);
            }
        });
        panelisi2.add(BtnCetak4);

        BtnKeluar5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar5.setMnemonic('K');
        BtnKeluar5.setText("Keluar");
        BtnKeluar5.setToolTipText("Alt+K");
        BtnKeluar5.setName("BtnKeluar5"); // NOI18N
        BtnKeluar5.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar5ActionPerformed(evt);
            }
        });
        panelisi2.add(BtnKeluar5);

        internalFrame10.add(panelisi2, java.awt.BorderLayout.PAGE_END);

        FormInput13.setName("FormInput13"); // NOI18N
        FormInput13.setPreferredSize(new java.awt.Dimension(265, 100));
        FormInput13.setLayout(null);

        chkIdentitasPasien.setBorder(null);
        chkIdentitasPasien.setText("Identitas Pasien");
        chkIdentitasPasien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkIdentitasPasien.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        chkIdentitasPasien.setName("chkIdentitasPasien"); // NOI18N
        FormInput13.add(chkIdentitasPasien);
        chkIdentitasPasien.setBounds(70, 50, 250, 23);

        ChkGeneralConsent.setBorder(null);
        ChkGeneralConsent.setText("Persetujuan Umum / General Consent");
        ChkGeneralConsent.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkGeneralConsent.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkGeneralConsent.setName("ChkGeneralConsent"); // NOI18N
        FormInput13.add(ChkGeneralConsent);
        ChkGeneralConsent.setBounds(70, 80, 250, 23);

        chkRM23.setBorder(null);
        chkRM23.setText("RM 23 Catatan Edukasi");
        chkRM23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkRM23.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        chkRM23.setName("chkRM23"); // NOI18N
        FormInput13.add(chkRM23);
        chkRM23.setBounds(70, 140, 250, 23);

        chkIdentifikasiBayi.setBorder(null);
        chkIdentifikasiBayi.setText("Identifikasi Bayi");
        chkIdentifikasiBayi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkIdentifikasiBayi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        chkIdentifikasiBayi.setName("chkIdentifikasiBayi"); // NOI18N
        FormInput13.add(chkIdentifikasiBayi);
        chkIdentifikasiBayi.setBounds(70, 200, 250, 23);

        TPasien2.setEditable(false);
        TPasien2.setHighlighter(null);
        TPasien2.setName("TPasien2"); // NOI18N
        FormInput13.add(TPasien2);
        TPasien2.setBounds(283, 10, 260, 23);

        TNoRM2.setEditable(false);
        TNoRM2.setHighlighter(null);
        TNoRM2.setName("TNoRM2"); // NOI18N
        FormInput13.add(TNoRM2);
        TNoRM2.setBounds(201, 10, 80, 23);

        TNoRw2.setHighlighter(null);
        TNoRw2.setName("TNoRw2"); // NOI18N
        FormInput13.add(TNoRw2);
        TNoRw2.setBounds(74, 10, 125, 23);

        jLabel55.setText("No.Rawat :");
        jLabel55.setName("jLabel55"); // NOI18N
        jLabel55.setPreferredSize(null);
        FormInput13.add(jLabel55);
        jLabel55.setBounds(0, 10, 70, 23);

        chkSPMBiayaObat.setBorder(null);
        chkSPMBiayaObat.setText("Surat Pernyataan (Menanggung Biaya Pengobatan)");
        chkSPMBiayaObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkSPMBiayaObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        chkSPMBiayaObat.setName("chkSPMBiayaObat"); // NOI18N
        FormInput13.add(chkSPMBiayaObat);
        chkSPMBiayaObat.setBounds(70, 170, 290, 23);

        chkTransferPasien.setBorder(null);
        chkTransferPasien.setText("Formulir Transfer Pasien IGD");
        chkTransferPasien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkTransferPasien.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        chkTransferPasien.setName("chkTransferPasien"); // NOI18N
        FormInput13.add(chkTransferPasien);
        chkTransferPasien.setBounds(70, 110, 250, 23);

        chkCover.setBorder(null);
        chkCover.setText("Cover Rekam Medis");
        chkCover.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkCover.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        chkCover.setName("chkCover"); // NOI18N
        chkCover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCoverActionPerformed(evt);
            }
        });
        FormInput13.add(chkCover);
        chkCover.setBounds(70, 230, 250, 23);

        internalFrame10.add(FormInput13, java.awt.BorderLayout.CENTER);

        WindowCetakBerkas.getContentPane().add(internalFrame10, java.awt.BorderLayout.CENTER);

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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Kamar Inap Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        PanelCariUtama.setName("PanelCariUtama"); // NOI18N
        PanelCariUtama.setOpaque(false);
        PanelCariUtama.setPreferredSize(new java.awt.Dimension(100, 143));
        PanelCariUtama.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Agenda-1-16x16.png"))); // NOI18N
        BtnIn.setMnemonic('M');
        BtnIn.setText("Masuk");
        BtnIn.setToolTipText("Alt+M");
        BtnIn.setName("BtnIn"); // NOI18N
        BtnIn.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnInActionPerformed(evt);
            }
        });
        BtnIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnInKeyPressed(evt);
            }
        });
        panelGlass10.add(BtnIn);

        BtnOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/add-file-16x16.png"))); // NOI18N
        BtnOut.setMnemonic('U');
        BtnOut.setText("Pulang");
        BtnOut.setToolTipText("Alt+U");
        BtnOut.setName("BtnOut"); // NOI18N
        BtnOut.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOutActionPerformed(evt);
            }
        });
        BtnOut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnOutKeyPressed(evt);
            }
        });
        panelGlass10.add(BtnOut);

        btnPindah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        btnPindah.setMnemonic('P');
        btnPindah.setText("Pindah");
        btnPindah.setToolTipText("Alt+P");
        btnPindah.setName("btnPindah"); // NOI18N
        btnPindah.setPreferredSize(new java.awt.Dimension(100, 30));
        btnPindah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPindahActionPerformed(evt);
            }
        });
        btnPindah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPindahKeyPressed(evt);
            }
        });
        panelGlass10.add(btnPindah);

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
        panelGlass10.add(BtnHapus);

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
        panelGlass10.add(BtnPrint);

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
        panelGlass10.add(BtnAll);

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
        panelGlass10.add(BtnKeluar);

        PanelCariUtama.add(panelGlass10, java.awt.BorderLayout.PAGE_END);

        panelGlass11.setName("panelGlass11"); // NOI18N
        panelGlass11.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 9));

        jLabel21.setText("Kamar :");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass11.add(jLabel21);

        BangsalCari.setName("BangsalCari"); // NOI18N
        BangsalCari.setPreferredSize(new java.awt.Dimension(200, 23));
        BangsalCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BangsalCariKeyPressed(evt);
            }
        });
        panelGlass11.add(BangsalCari);

        btnBangsalCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBangsalCari.setMnemonic('3');
        btnBangsalCari.setToolTipText("Alt+3");
        btnBangsalCari.setName("btnBangsalCari"); // NOI18N
        btnBangsalCari.setPreferredSize(new java.awt.Dimension(28, 23));
        btnBangsalCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBangsalCariActionPerformed(evt);
            }
        });
        btnBangsalCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnBangsalCariKeyPressed(evt);
            }
        });
        panelGlass11.add(btnBangsalCari);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass11.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(230, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass11.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('4');
        BtnCari.setToolTipText("Alt+4");
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
        panelGlass11.add(BtnCari);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass11.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(75, 23));
        panelGlass11.add(LCount);

        PanelCariUtama.add(panelGlass11, java.awt.BorderLayout.CENTER);

        panelCari.setName("panelCari"); // NOI18N
        panelCari.setPreferredSize(new java.awt.Dimension(44, 43));
        panelCari.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 9));

        R1.setBackground(new java.awt.Color(240, 250, 230));
        R1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.pink));
        buttonGroup1.add(R1);
        R1.setSelected(true);
        R1.setText("Belum Pulang");
        R1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R1.setName("R1"); // NOI18N
        R1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelCari.add(R1);

        R2.setBackground(new java.awt.Color(240, 250, 230));
        R2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.pink));
        buttonGroup1.add(R2);
        R2.setText("Tgl.Masuk :");
        R2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R2.setName("R2"); // NOI18N
        R2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelCari.add(R2);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-04-2024" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
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

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-04-2024" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DTPCari1ItemStateChanged(evt);
            }
        });
        DTPCari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPCari2KeyPressed(evt);
            }
        });
        panelCari.add(DTPCari2);

        R3.setBackground(new java.awt.Color(240, 250, 230));
        R3.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.pink));
        buttonGroup1.add(R3);
        R3.setText("Pulang :");
        R3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R3.setName("R3"); // NOI18N
        R3.setPreferredSize(new java.awt.Dimension(75, 23));
        panelCari.add(R3);

        DTPCari3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-04-2024" }));
        DTPCari3.setDisplayFormat("dd-MM-yyyy");
        DTPCari3.setName("DTPCari3"); // NOI18N
        DTPCari3.setOpaque(false);
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

        DTPCari4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-04-2024" }));
        DTPCari4.setDisplayFormat("dd-MM-yyyy");
        DTPCari4.setName("DTPCari4"); // NOI18N
        DTPCari4.setOpaque(false);
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

        PanelCariUtama.add(panelCari, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(PanelCariUtama, java.awt.BorderLayout.PAGE_END);

        Scroll.setToolTipText("Klik data di table, kemudian klik kanan untuk memilih menu yang diinginkan");
        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamIn.setToolTipText("<html>Ungu: SEP pasien belum tersedia di SIMRS.<br>" +
            "Abu: Bed pasien terisi lebih dari 1.<br>" +
            "Pink: SEP pasien belum tersedia di SIMRS dan bed pasien terisi lebih dari 1.</html>");
        tbKamIn.setComponentPopupMenu(jPopupMenu1);
        tbKamIn.setName("tbKamIn"); // NOI18N
        tbKamIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKamInMouseClicked(evt);
            }
        });
        tbKamIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKamInKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbKamIn);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 9));

        jLabel5.setText("No. Rawat :");
        jLabel5.setName("jLabel5"); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel5);

        TNoRw1.setEditable(false);
        TNoRw1.setHighlighter(null);
        TNoRw1.setName("TNoRw1"); // NOI18N
        TNoRw1.setPreferredSize(new java.awt.Dimension(130, 23));
        panelGlass9.add(TNoRw1);

        TNoRM1.setEditable(false);
        TNoRM1.setHighlighter(null);
        TNoRM1.setName("TNoRM1"); // NOI18N
        TNoRM1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(TNoRM1);

        TPasien1.setEditable(false);
        TPasien1.setHighlighter(null);
        TPasien1.setName("TPasien1"); // NOI18N
        TPasien1.setPreferredSize(new java.awt.Dimension(215, 23));
        panelGlass9.add(TPasien1);

        jLabel37.setText("Stts.Bayar :");
        jLabel37.setName("jLabel37"); // NOI18N
        jLabel37.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(jLabel37);

        cmbStatusBayar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Sudah Bayar", "Belum Bayar" }));
        cmbStatusBayar.setName("cmbStatusBayar"); // NOI18N
        cmbStatusBayar.setPreferredSize(new java.awt.Dimension(120, 23));
        panelGlass9.add(cmbStatusBayar);

        internalFrame1.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void norawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_norawatKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ", TNoRM, norawat.getText());
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ", TPasien, TNoRM.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnRegActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnCloseIn, kdkamar);
        }
}//GEN-LAST:event_norawatKeyPressed

    private void kdkamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdkamarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            i = 1;
            isKmr();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            CmbTahun.requestFocus();
            isKmr();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            norawat.requestFocus();
            isKmr();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnKamarActionPerformed(null);
        }
}//GEN-LAST:event_kdkamarKeyPressed

    private void ttlbiayaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ttlbiayaKeyPressed
        // Valid.pindah(evt,TKdOb,BtnSimpan);
}//GEN-LAST:event_ttlbiayaKeyPressed

    private void btnRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegActionPerformed
        var.setform("DlgKamarInap");
        reg.emptTeks();
        reg.isCek();
        reg.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        reg.setLocationRelativeTo(internalFrame1);
        reg.setVisible(true);
}//GEN-LAST:event_btnRegActionPerformed

    private void btnRegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnRegKeyPressed
        Valid.pindah(evt, norawat, kdkamar);
}//GEN-LAST:event_btnRegKeyPressed

    private void btnKamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKamarActionPerformed
        var.setform("DlgKamarInap");
        i = 1;
        kamar.load();
        kamar.isCek();
        kamar.emptTeks();
        kamar.tampil();
        kamar.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        kamar.setLocationRelativeTo(internalFrame1);
        kamar.setVisible(true);
}//GEN-LAST:event_btnKamarActionPerformed

    private void btnKamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnKamarKeyPressed
        Valid.pindah(evt, kdkamar, CmbTahun);
}//GEN-LAST:event_btnKamarKeyPressed

    private void BtnInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnInActionPerformed
        norawat.setEditable(true);
        kdkamar.setEditable(true);
        diagnosaawal.setEditable(true);
        diagnosaakhir.setVisible(false);
        btnDiagnosa.setVisible(false);
        cmbStatus.setVisible(false);
        jLabel26.setVisible(false);
        jLabel23.setVisible(false);
        LblStts.setText("Masuk/Check In");
        btnReg.setEnabled(true);
        btnKamar.setEnabled(true);
        emptTeks();
        lama = Sequel.cariIsiAngka("select lamajam from set_jam_minimal");
        hariawal = Sequel.cariIsi("select hariawal from set_jam_minimal");

        WindowInputKamar.setLocationRelativeTo(internalFrame1);
        WindowInputKamar.setVisible(true);
}//GEN-LAST:event_BtnInActionPerformed

    private void BtnInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnInKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnInActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnOut);
        }
}//GEN-LAST:event_BtnInKeyPressed

    private void BtnOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOutActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data kamar inap pasien sudah habis...!!!!");
            BtnIn.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu data inap pasien yang mau pulang dengan menklik data pada table...!!!");
            tbKamIn.requestFocus();
        } else if (TOut.getText().trim().length() > 0) {
            JOptionPane.showMessageDialog(null, "Maaf, pasien ini sudah pulang pada tanggal " + TOut.getText() + " ...!!!");
            emptTeks();
            tbKamIn.requestFocus();
        } else if ((TOut.getText().length() == 0) && (norawat.getText().length() > 0)) {
            norawat.setEditable(false);
            kdkamar.setEditable(false);
            i = 1;
            isKmr();
            diagnosaawal.setEditable(false);
            diagnosaakhir.setVisible(true);
            btnDiagnosa.setVisible(true);
            jLabel23.setVisible(true);
            diagnosaakhir.setEditable(false);
            diagnosaakhir.setText("");
            cmbStatus.setVisible(true);
            jLabel26.setVisible(true);
            lama = Sequel.cariIsiAngka("select lamajam from set_jam_minimal");
            hariawal = Sequel.cariIsi("select hariawal from set_jam_minimal");
            LblStts.setText("Pulang/Check Out");

            btnReg.setEnabled(false);
            btnKamar.setEnabled(false);
            date = new Date();
            now = dateFormat.format(date);
            CmbTahun.setSelectedItem(now.substring(0, 4));
            CmbBln.setSelectedItem(now.substring(5, 7));
            CmbTgl.setSelectedItem(now.substring(8, 10));
            cmbJam.setSelectedItem(now.substring(11, 13));
            cmbMnt.setSelectedItem(now.substring(14, 16));
            cmbDtk.setSelectedItem(now.substring(17, 19));
            tglmasuk = TIn.getText();
            jammasuk = JamMasuk.getText();
            if (hariawal.equals("Yes")) {
                Sequel.cariIsi("select (if(to_days('" + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem() + " " + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "')-to_days('" + tglmasuk + " " + jammasuk + "')=0,if(time_to_sec('" + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem() + " " + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "')-time_to_sec('" + tglmasuk + " " + jammasuk + "')>(3600*" + lama + "),1,0),to_days('" + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem() + " " + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "')-to_days('" + tglmasuk + " " + jammasuk + "'))+1) as lama", TJmlHari);
            } else {
                Sequel.cariIsi("select (if(to_days('" + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem() + " " + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "')-to_days('" + tglmasuk + " " + jammasuk + "')=0,if(time_to_sec('" + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem() + " " + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "')-time_to_sec('" + tglmasuk + " " + jammasuk + "')>(3600*" + lama + "),1,0),to_days('" + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem() + " " + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "')-to_days('" + tglmasuk + " " + jammasuk + "'))) as lama", TJmlHari);
            }

            //Sequel.cariIsi("select (if(to_days('"+CmbTahun.getSelectedItem()+"-"+CmbBln.getSelectedItem()+"-"+CmbTgl.getSelectedItem()+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"')-to_days('"+tglmasuk+" "+jammasuk+"')=0,if(time_to_sec('"+CmbTahun.getSelectedItem()+"-"+CmbBln.getSelectedItem()+"-"+CmbTgl.getSelectedItem()+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"')-time_to_sec('"+tglmasuk+" "+jammasuk+"')>(3600*"+lama+"),1,0),to_days('"+CmbTahun.getSelectedItem()+"-"+CmbBln.getSelectedItem()+"-"+CmbTgl.getSelectedItem()+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"')-to_days('"+tglmasuk+" "+jammasuk+"'))+1) as lama",TJmlHari);             
            norawat.requestFocus();
            isjml();
            WindowInputKamar.setLocationRelativeTo(internalFrame1);
            WindowInputKamar.setVisible(true);
        }
}//GEN-LAST:event_BtnOutActionPerformed

    private void BtnOutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnOutKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnOutActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnIn, btnPindah);
        }
}//GEN-LAST:event_BtnOutKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            norawat.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
        } else if (TOut.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Data pasien dengan No.Rawat " + norawat.getText() + " belum pulang/check out. Data belum bisa anda hapus...!!!!");
        } else if (!(norawat.getText().trim().equals(""))) {
            if (Sequel.cariRegistrasi(norawat.getText().trim()) > 0) {
                JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                TCari.requestFocus();
            } else {
                try {
                    Sequel.queryu("delete from kamar_inap where no_rawat='" + norawat.getText() + "' and kd_kamar='" + kdkamar.getText() + "' and tgl_masuk='" + TIn.getText() + "' and jam_masuk='" + JamMasuk.getText() + "'");
                    if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", norawat.getText()) == 0) {
                        Sequel.mengedit("reg_periksa", "no_rawat='" + norawat.getText() + "'", "status_lanjut='Ralan'");
                    }

                    tampil();
                } catch (Exception e) {
                    System.out.println("Notifikasi : " + e);
                    JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih terlebih dulu data yang mau anda hapus...\n Klik data pada table untuk memilih data...!!!!");
                }
            }
        }

        emptTeks();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, btnPindah, BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        WindowInputKamar.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            WindowInputKamar.dispose();
            dispose();
        } else {
            Valid.pindah(evt, BtnPrint, TCari);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if (!TCari.getText().trim().equals("")) {
            BtnCariActionPerformed(evt);
        }
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
            if (R1.isSelected() == true) {
                kmr = " stts_pulang='-' and reg_periksa.status_bayar like '%" + cmbStatusBayar.getSelectedItem().toString().replaceAll("Semua", "") + "%' ";
                if (!BangsalCari.getText().equals("")) {
                    kmr = " stts_pulang='-' and bangsal.nm_bangsal='" + BangsalCari.getText() + "' and reg_periksa.status_bayar like '%" + cmbStatusBayar.getSelectedItem().toString().replaceAll("Semua", "") + "%' ";
                }
            } else if (R2.isSelected() == true) {
                kmr = " kamar_inap.tgl_masuk between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and reg_periksa.status_bayar like '%" + cmbStatusBayar.getSelectedItem().toString().replaceAll("Semua", "") + "%' ";
                if (!BangsalCari.getText().equals("")) {
                    kmr = " kamar_inap.tgl_masuk between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and bangsal.nm_bangsal='" + BangsalCari.getText() + "' and reg_periksa.status_bayar like '%" + cmbStatusBayar.getSelectedItem().toString().replaceAll("Semua", "") + "%' ";
                }
            } else if (R3.isSelected() == true) {
                kmr = " kamar_inap.tgl_keluar between '" + Valid.SetTgl(DTPCari3.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari4.getSelectedItem() + "") + "' and reg_periksa.status_bayar like '%" + cmbStatusBayar.getSelectedItem().toString().replaceAll("Semua", "") + "%' ";
                if (!BangsalCari.getText().equals("")) {
                    kmr = " kamar_inap.tgl_keluar between '" + Valid.SetTgl(DTPCari3.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari4.getSelectedItem() + "") + "' and bangsal.nm_bangsal='" + BangsalCari.getText() + "' and reg_periksa.status_bayar like '%" + cmbStatusBayar.getSelectedItem().toString().replaceAll("Semua", "") + "%' ";
                }
            }

            key = kmr + " ";
            if (!TCari.getText().equals("")) {
                key = kmr + "and kamar_inap.no_rawat like '%" + TCari.getText().trim() + "%' or "
                        + kmr + "and reg_periksa.no_rkm_medis like '%" + TCari.getText().trim() + "%' or "
                        + kmr + "and pasien.nm_pasien like '%" + TCari.getText().trim() + "%' or "
                        + kmr + "and concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%" + TCari.getText().trim() + "%' or "
                        + kmr + "and kamar_inap.kd_kamar like '%" + TCari.getText().trim() + "%' or "
                        + kmr + "and bangsal.nm_bangsal like '%" + TCari.getText().trim() + "%' or "
                        + kmr + "and kamar_inap.diagnosa_awal like '%" + TCari.getText().trim() + "%' or "
                        + kmr + "and kamar_inap.diagnosa_akhir like '%" + TCari.getText().trim() + "%' or "
                        + kmr + "and kamar_inap.trf_kamar like '%" + TCari.getText().trim() + "%' or "
                        + kmr + "and kamar_inap.tgl_masuk like '%" + TCari.getText().trim() + "%' or "
                        + kmr + "and dokter.nm_dokter like '%" + TCari.getText().trim() + "%' or "
                        + kmr + "and kamar_inap.stts_pulang like '%" + TCari.getText().trim() + "%' or "
                        + kmr + "and kamar_inap.tgl_keluar like '%" + TCari.getText().trim() + "%' or "
                        + kmr + "and penjab.png_jawab like '%" + TCari.getText().trim() + "%' or "
                        + kmr + "and kamar_inap.ttl_biaya like '%" + TCari.getText().trim() + "%' ";
            }

            try {
                pilihancetak = (String) JOptionPane.showInputDialog(null, "Silahkan pilih laporan yang mau dicetak!", "Laporan", JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Laporan 1", "Laporan 2", "Lembar Bimbingan Rohani"}, "Laporan 1");
                switch (pilihancetak) {
                    case "Laporan 1":
                        Map<String, Object> param = new HashMap<>();
                        param.put("namars", var.getnamars());
                        param.put("alamatrs", var.getalamatrs());
                        param.put("kotars", var.getkabupatenrs());
                        param.put("propinsirs", var.getpropinsirs());
                        param.put("kontakrs", var.getkontakrs());
                        param.put("emailrs", var.getemailrs());
                        param.put("logo", Sequel.cariGambar("select logo from setting"));
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        Valid.MyReport("rptKamarInap.jrxml", "report", "::[ Data Kamar Inap Pasien ]::", "select kamar_inap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab),"
                                + "penjab.png_jawab,kamar_inap.kd_kamar,bangsal.nm_bangsal,kamar_inap.trf_kamar,kamar_inap.diagnosa_awal,kamar_inap.diagnosa_akhir,"
                                + "kamar_inap.tgl_masuk,kamar_inap.jam_masuk,if(kamar_inap.tgl_keluar='0000-00-00','',kamar_inap.tgl_keluar) as tgl_keluar,"
                                + "if(kamar_inap.jam_keluar='00:00:00','',kamar_inap.jam_keluar) as jam_keluar,kamar_inap.ttl_biaya,kamar_inap.stts_pulang, lama,dokter.nm_dokter "
                                + "from kamar_inap inner join reg_periksa inner join pasien inner join kamar inner join bangsal inner join kelurahan inner join kecamatan inner join kabupaten inner join dokter inner join penjab "
                                + "on kamar_inap.no_rawat=reg_periksa.no_rawat "
                                + "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                                + "and reg_periksa.kd_dokter=dokter.kd_dokter "
                                + "and reg_periksa.kd_pj=penjab.kd_pj "
                                + "and kamar_inap.kd_kamar=kamar.kd_kamar "
                                + "and kamar.kd_bangsal=bangsal.kd_bangsal and pasien.kd_kel=kelurahan.kd_kel "
                                + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "
                                + "where  " + key + " order by bangsal.nm_bangsal,kamar_inap.tgl_masuk,kamar_inap.jam_masuk", param);
                        this.setCursor(Cursor.getDefaultCursor());
                        break;
                    case "Laporan 2":
                        Map<String, Object> param2 = new HashMap<>();
                        param2.put("namars", var.getnamars());
                        param2.put("alamatrs", var.getalamatrs());
                        param2.put("kotars", var.getkabupatenrs());
                        param2.put("propinsirs", var.getpropinsirs());
                        param2.put("kontakrs", var.getkontakrs());
                        param2.put("emailrs", var.getemailrs());
                        param2.put("logo", Sequel.cariGambar("select logo from setting"));
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        Valid.MyReport("rptKamarInap2.jrxml", "report", "::[ Data Kamar Inap Pasien ]::", "select kamar_inap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,"
                                + "penjab.png_jawab,kamar_inap.kd_kamar,bangsal.nm_bangsal,kamar_inap.trf_kamar,kamar_inap.diagnosa_awal,kamar_inap.diagnosa_akhir,"
                                + "kamar_inap.tgl_masuk,kamar_inap.jam_masuk,if(kamar_inap.tgl_keluar='0000-00-00','',kamar_inap.tgl_keluar) as tgl_keluar,"
                                + "ifnull((select perujuk from rujuk_masuk where rujuk_masuk.no_rawat=reg_periksa.no_rawat),'') as perujuk,"
                                + "ifnull((select dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat=kamar_inap.no_rawat limit 1),'') as dpjp,"
                                + "if(kamar_inap.jam_keluar='00:00:00','',kamar_inap.jam_keluar) as jam_keluar,kamar_inap.ttl_biaya,kamar_inap.stts_pulang, lama,dokter.nm_dokter "
                                + "from kamar_inap inner join reg_periksa inner join pasien inner join kamar inner join bangsal inner join kelurahan inner join kecamatan inner join kabupaten inner join dokter inner join penjab "
                                + "on kamar_inap.no_rawat=reg_periksa.no_rawat "
                                + "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                                + "and reg_periksa.kd_dokter=dokter.kd_dokter "
                                + "and reg_periksa.kd_pj=penjab.kd_pj "
                                + "and kamar_inap.kd_kamar=kamar.kd_kamar "
                                + "and kamar.kd_bangsal=bangsal.kd_bangsal and pasien.kd_kel=kelurahan.kd_kel "
                                + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "
                                + "where  " + key + " order by bangsal.nm_bangsal,kamar_inap.tgl_masuk,kamar_inap.jam_masuk", param2);
                        this.setCursor(Cursor.getDefaultCursor());
                        break;
                    case "Lembar Bimbingan Rohani":
                        Map<String, Object> param3 = new HashMap<>();
                        param3.put("namars", var.getnamars());
                        param3.put("alamatrs", var.getalamatrs());
                        param3.put("kotars", var.getkabupatenrs());
                        param3.put("propinsirs", var.getpropinsirs());
                        param3.put("kontakrs", var.getkontakrs());
                        param3.put("emailrs", var.getemailrs());
                        param3.put("logo", Sequel.cariGambar("select logo from setting"));
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        Valid.MyReport("rptKamarInap3.jrxml", "report", "::[ Data Kamar Inap Pasien ]::", "select kamar_inap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,"
                                + "penjab.png_jawab,kamar_inap.kd_kamar,bangsal.nm_bangsal,kamar_inap.trf_kamar,kamar_inap.diagnosa_awal,kamar_inap.diagnosa_akhir,"
                                + "kamar_inap.tgl_masuk,kamar_inap.jam_masuk,"
                                + "ifnull((select perujuk from rujuk_masuk where rujuk_masuk.no_rawat=reg_periksa.no_rawat),'') as perujuk,"
                                + "ifnull((select dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat=kamar_inap.no_rawat limit 1),'') as dpjp,"
                                + "kamar_inap.ttl_biaya,kamar_inap.stts_pulang, lama,dokter.nm_dokter "
                                + "from kamar_inap inner join reg_periksa inner join pasien inner join kamar inner join bangsal inner join kelurahan inner join kecamatan inner join kabupaten inner join dokter inner join penjab "
                                + "on kamar_inap.no_rawat=reg_periksa.no_rawat "
                                + "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                                + "and reg_periksa.kd_dokter=dokter.kd_dokter "
                                + "and reg_periksa.kd_pj=penjab.kd_pj "
                                + "and kamar_inap.kd_kamar=kamar.kd_kamar "
                                + "and kamar.kd_bangsal=bangsal.kd_bangsal and pasien.kd_kel=kelurahan.kd_kel "
                                + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "
                                + "where  " + key + " order by bangsal.nm_bangsal,kamar_inap.tgl_masuk,kamar_inap.jam_masuk", param3);
                        this.setCursor(Cursor.getDefaultCursor());
                        break;
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        }
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            tbKamIn.requestFocus();
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
        if (namakamar.equals("")) {
            BangsalCari.setText("");
        }

        cmbStatusBayar.setSelectedItem("Semua");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnAllActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnCari, BtnIn);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
        Valid.pindah(evt, kdkamar, cmbJam);
}//GEN-LAST:event_DTPTglKeyPressed

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
        Valid.pindah(evt, cmbMnt, diagnosaawal);
    }//GEN-LAST:event_cmbDtkKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt, cmbJam, cmbDtk);
    }//GEN-LAST:event_cmbMntKeyPressed

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt, CmbTgl, cmbMnt);
    }//GEN-LAST:event_cmbJamKeyPressed

    private void TBangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBangsalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TBangsalKeyPressed

    private void BtnCloseInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseInActionPerformed
        TOut.setText("");
        var.setstatus(false);
        WindowInputKamar.dispose();
    }//GEN-LAST:event_BtnCloseInActionPerformed

    private void BtnCloseInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCloseInKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            WindowInputKamar.dispose();
        } else {
            Valid.pindah(evt, BtnBatal, norawat);
        }
    }//GEN-LAST:event_BtnCloseInKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (TPasien.getText().trim().equals("")) {
            Valid.textKosong(norawat, "pasien");
        } else if (TKdBngsal.getText().trim().equals("")) {
            Valid.textKosong(kdkamar, "kamar");
        } else if (diagnosaawal.getText().trim().equals("")) {
            Valid.textKosong(diagnosaawal, "Diagnosa awal");
        } else {
            if (norawat.isEditable() == true) {
                switch (TSttsKamar.getText().trim()) {
                    case "ISI":
                        JOptionPane.showMessageDialog(null, "Maaf, Status kamar sudah terisi silahkan pilih kamar kosong..!!!!");
                        kdkamar.requestFocus();
                        break;
                    case "KOSONG":
                        if (Sequel.menyimpantf("kamar_inap", "'" + norawat.getText() + "','"
                                + kdkamar.getText() + "','" + TTarif.getText() + "','"
                                + diagnosaawal.getText() + "','"
                                + diagnosaakhir.getText() + "','"
                                + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem() + "','"
                                + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "','0000-00-00','00:00:00','" + TJmlHari.getText() + "','"
                                + ttlbiaya.getText() + "','-'", "No.Rawat") == true) {
                            Sequel.mengedit("reg_periksa", "no_rawat='" + norawat.getText() + "'", "status_lanjut='Ranap',stts='Dirawat'");
                            Sequel.mengedit("kamar", "kd_kamar='" + kdkamar.getText() + "'", "status='ISI'");
                            emptTeks();
                        }

                        break;
                }
                norawat.requestFocus();
            } else if (norawat.isEditable() == false) {
                if (cmbStatus.getSelectedItem().equals("-")) {
                    Valid.textKosong(cmbStatus, "Status Pulang");
                } else if (diagnosaakhir.getText().equals("")) {
                    Valid.textKosong(diagnosaakhir, "Diagnosa Akhir");
                } else {
                    Sequel.mengedit("kamar_inap", "no_rawat='" + norawat.getText() + "' and kd_kamar='" + kdkamar.getText() + "' and tgl_masuk='" + TIn.getText() + "' and jam_masuk='" + JamMasuk.getText() + "'",
                            "tgl_keluar='" + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem()
                            + "',trf_kamar='" + TTarif.getText() + "',jam_keluar='" + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem()
                            + "',ttl_biaya='" + ttlbiaya.getText() + "',stts_pulang='" + cmbStatus.getSelectedItem() + "',diagnosa_akhir='" + diagnosaakhir.getText() + "',lama='" + TJmlHari.getText() + "'");
                    if (cmbStatus.getSelectedItem().equals("Meninggal")) {
                        DlgPasienMati dlgPasienMati = new DlgPasienMati(null, false);
                        dlgPasienMati.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                        dlgPasienMati.setLocationRelativeTo(internalFrame1);
                        dlgPasienMati.emptTeks();
                        dlgPasienMati.setNoRm(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 1).toString());
                        dlgPasienMati.setNoRw(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString());
                        dlgPasienMati.isCek();
                        dlgPasienMati.setVisible(true);
                    } else if (cmbStatus.getSelectedItem().equals("Rujuk")) {
                        DlgRujuk dlgrjk = new DlgRujuk(null, false);
                        dlgrjk.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                        dlgrjk.setLocationRelativeTo(internalFrame1);
                        dlgrjk.emptTeks();
                        dlgrjk.isCek();
                        dlgrjk.setNoRm(norawat.getText(), DTPCari1.getDate(), DTPCari2.getDate());
                        dlgrjk.tampil();
                        dlgrjk.setVisible(true);
                    }
                    Sequel.mengedit("kamar", "kd_kamar='" + kdkamar.getText() + "'", "status='KOSONG'");
                    WindowInputKamar.dispose();
                    emptTeks();
                }
            }
            tampil();
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        Valid.pindah(evt, cmbDtk, BtnBatal);
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        if (norawat.isEditable() == true) {
            emptTeks();
        } else if (norawat.isEditable() == false) {
            emptTeks();
            WindowInputKamar.dispose();
        }
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            emptTeks();
        } else {
            Valid.pindah(evt, BtnSimpan, BtnCloseIn);
        }
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void TInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TInKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TInKeyPressed

    private void DTPTglItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPTglItemStateChanged

    }//GEN-LAST:event_DTPTglItemStateChanged

    private void DTPCari1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPCari1ItemStateChanged
        R2.setSelected(true);

}//GEN-LAST:event_DTPCari1ItemStateChanged

    private void DTPCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPCari1KeyPressed
        Valid.pindah(evt, BangsalCari, DTPCari2);
}//GEN-LAST:event_DTPCari1KeyPressed

    private void DTPCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPCari2KeyPressed
        Valid.pindah(evt, DTPCari1, BangsalCari);
}//GEN-LAST:event_DTPCari2KeyPressed

    private void diagnosaawalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_diagnosaawalKeyPressed
        Valid.pindah(evt, cmbDtk, diagnosaakhir);
    }//GEN-LAST:event_diagnosaawalKeyPressed

    private void diagnosaakhirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_diagnosaakhirKeyPressed
        Valid.pindah(evt, diagnosaawal, BtnSimpan);
    }//GEN-LAST:event_diagnosaakhirKeyPressed

    private void CmbTahunItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CmbTahunItemStateChanged
        if ((WindowInputKamar.isVisible() == true) && (!TBangsal.getText().equals("")) && (!norawat.getText().equals(""))) {
            if (TIn.getText().equals("")) {
                tglmasuk = CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem();
                jammasuk = cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem();
            } else {
                tglmasuk = TIn.getText();
                jammasuk = JamMasuk.getText();
            }
            if (hariawal.equals("Yes")) {
                Sequel.cariIsi("select (if(to_days('" + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem() + " " + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "')-to_days('" + tglmasuk + " " + jammasuk + "')=0,if(time_to_sec('" + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem() + " " + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "')-time_to_sec('" + tglmasuk + " " + jammasuk + "')>(3600*" + lama + "),1,0),to_days('" + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem() + " " + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "')-to_days('" + tglmasuk + " " + jammasuk + "'))+1) as lama", TJmlHari);
            } else {
                Sequel.cariIsi("select (if(to_days('" + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem() + " " + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "')-to_days('" + tglmasuk + " " + jammasuk + "')=0,if(time_to_sec('" + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem() + " " + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "')-time_to_sec('" + tglmasuk + " " + jammasuk + "')>(3600*" + lama + "),1,0),to_days('" + CmbTahun.getSelectedItem() + "-" + CmbBln.getSelectedItem() + "-" + CmbTgl.getSelectedItem() + " " + cmbJam.getSelectedItem() + ":" + cmbMnt.getSelectedItem() + ":" + cmbDtk.getSelectedItem() + "')-to_days('" + tglmasuk + " " + jammasuk + "'))) as lama", TJmlHari);
            }
            //Sequel.cariIsi("select (if(to_days('"+CmbTahun.getSelectedItem()+"-"+CmbBln.getSelectedItem()+"-"+CmbTgl.getSelectedItem()+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"')-to_days('"+tglmasuk+" "+jammasuk+"')=0,if(time_to_sec('"+CmbTahun.getSelectedItem()+"-"+CmbBln.getSelectedItem()+"-"+CmbTgl.getSelectedItem()+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"')-time_to_sec('"+tglmasuk+" "+jammasuk+"')>(3600*"+lama+"),1,0),to_days('"+CmbTahun.getSelectedItem()+"-"+CmbBln.getSelectedItem()+"-"+CmbTgl.getSelectedItem()+" "+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"')-to_days('"+tglmasuk+" "+jammasuk+"'))+1) as lama",TJmlHari);             
        }
    }//GEN-LAST:event_CmbTahunItemStateChanged

    private void CmbTahunKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbTahunKeyPressed
        Valid.pindah(evt, kdkamar, CmbBln);
    }//GEN-LAST:event_CmbTahunKeyPressed

    private void CmbBlnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbBlnKeyPressed
        Valid.pindah(evt, CmbTahun, CmbTgl);
    }//GEN-LAST:event_CmbBlnKeyPressed

    private void CmbTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbTglKeyPressed
        Valid.pindah(evt, CmbBln, cmbJam);
    }//GEN-LAST:event_CmbTglKeyPressed

    private void TOutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TOutKeyPressed
        // TODO add your handling code here:
}//GEN-LAST:event_TOutKeyPressed

    private void tbKamInMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKamInMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }

            if (evt.getClickCount() == 1) {
                if (gabungkan.equals("gabung")) {
                    if (norawat.getText().equals(norawatgabung)) {
                        JOptionPane.showMessageDialog(null, "Gabungkan ke ranap ibu gagal karena no perawatan ibu dan bayi yang dipilih sama..!!");
                        gabungkan = "";
                        norawatgabung = "";
                    } else {
                        int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, udah bener belum data yang mau digabung..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            if (Sequel.menyimpantf("ranap_gabung", "?,?", "Data Ranap Gabung", 2, new String[]{
                                norawat.getText(), norawatgabung
                            }) == true) {
                                Sequel.queryu("delete from kamar_inap where no_rawat='" + norawatgabung + "'");
                                Sequel.mengedit("kamar", "kd_kamar='" + kamaryangdigabung + "'", "status='KOSONG'");
                                Sequel.mengedit("kamar_inap", "no_rawat='" + norawatgabung + "'", "no_rawat='" + norawat.getText() + "'");
                                Sequel.mengedit("reg_periksa", "no_rawat='" + norawatgabung + "'", "status_bayar='Sudah Bayar'");
                                gabungkan = "";
                                norawatgabung = "";
                                tampil();
                            }
                        } else {
                            gabungkan = "";
                            norawatgabung = "";
                        }
                    }
                }
            }
            if (evt.getClickCount() == 2) {
                i = tbKamIn.getSelectedColumn();
                if (i == 0) {
                    if (var.gettindakan_ranap() == true) {
                        MnRawatInapActionPerformed(null);
                    }
                } else if (i == 1) {
                    if (var.getberi_obat() == true) {
                        //MnPemberianObatActionPerformed(null);
                        if (tabMode.getRowCount() == 0) {
                            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
                            TCari.requestFocus();
                        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                            try {
                                psanak = koneksi.prepareStatement(
                                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                                try {
                                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                                    rs2 = psanak.executeQuery();
                                    if (rs2.next()) {
                                        if (Sequel.cariRegistrasi(tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString()) > 0) {
                                            JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                            TCari.requestFocus();
                                        } else {
                                            var.setform("DlgKamarInap");
                                            bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?", Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                                            if (bangsal.equals("")) {
                                                if (Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")) {
                                                    var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                                                } else {
                                                    var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                                                }
                                            } else {
                                                var.setkdbangsal(bangsal);
                                            }
                                            panggilobat(rs2.getString("no_rawat2"));
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                        tbKamIn.requestFocus();
                                    }
                                } catch (Exception ex) {
                                    System.out.println("Notifikasi : " + ex);
                                } finally {
                                    if (rs2 != null) {
                                        rs2.close();
                                    }
                                    if (psanak != null) {
                                        psanak.close();
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        } else {
                            if (Sequel.cariRegistrasi(norawat.getText()) > 0) {
                                JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                TCari.requestFocus();
                            } else {
                                var.setform("DlgKamarInap");
                                bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?", Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                                if (bangsal.equals("")) {
                                    if (Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")) {
                                        var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                                    } else {
                                        var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                                    }
                                } else {
                                    var.setkdbangsal(bangsal);
                                }
                                panggilobat(norawat.getText());
                            }
                        }
                    }
                } else if (i == 2) {
                    //if(var.getbilling_ranap()==true){
                    MnBillingActionPerformed(null);
                    //}                    
                } else if (i == 3) {
                    if (var.getresep_pulang() == true) {
                        MnInputResepActionPerformed(null);
                    }
                } else if (i == 18) {
                    if (var.getdpjp_ranap() == true) {
                        MnDPJPActionPerformed(null);
                    }
                }
            }
        }
}//GEN-LAST:event_tbKamInMouseClicked

    private void tbKamInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamInKeyPressed
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }

            if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
                i = tbKamIn.getSelectedColumn();
                if (i == 0) {
                    if (var.gettindakan_ranap() == true) {
                        MnRawatInapActionPerformed(null);
                    }
                } else if (i == 1) {
                    if (var.getberi_obat() == true) {
                        //MnPemberianObatActionPerformed(null);
                        if (tabMode.getRowCount() == 0) {
                            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
                            TCari.requestFocus();
                        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
                            try {
                                psanak = koneksi.prepareStatement(
                                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                                try {
                                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                                    rs2 = psanak.executeQuery();
                                    if (rs2.next()) {
                                        var.setform("DlgKamarInap");
                                        bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?", Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                                        if (bangsal.equals("")) {
                                            if (Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")) {
                                                var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                                            } else {
                                                var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                                            }
                                        } else {
                                            var.setkdbangsal(bangsal);
                                        }

                                        if (Sequel.cariRegistrasi(tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString()) > 0) {
                                            JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                            TCari.requestFocus();
                                        } else {
                                            panggilobat(rs2.getString("no_rawat2"));
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                                        tbKamIn.requestFocus();
                                    }
                                } catch (Exception ex) {
                                    System.out.println("Notifikasi : " + ex);
                                } finally {
                                    if (rs2 != null) {
                                        rs2.close();
                                    }
                                    if (psanak != null) {
                                        psanak.close();
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        } else {
                            var.setform("DlgKamarInap");
                            bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?", Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                            if (bangsal.equals("")) {
                                if (Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")) {
                                    var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                                } else {
                                    var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                                }
                            } else {
                                var.setkdbangsal(bangsal);
                            }

                            if (Sequel.cariRegistrasi(norawat.getText()) > 0) {
                                JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                                TCari.requestFocus();
                            } else {
                                panggilobat(norawat.getText());
                            }
                            //this.dispose();
                        }
                    }
                } else if (i == 2) {
                    //if(var.getbilling_ranap()==true){
                    MnBillingActionPerformed(null);
                    //}                    
                } else if (i == 3) {
                    if (var.getresep_pulang() == true) {
                        MnInputResepActionPerformed(null);
                    }
                } else if (i == 18) {
                    if (var.getdpjp_ranap() == true) {
                        MnDPJPActionPerformed(null);
                    }
                }
            } else if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
                TCari.setText("");
                TCari.requestFocus();
            }
        }
}//GEN-LAST:event_tbKamInKeyPressed

private void btnBangsalCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBangsalCariActionPerformed
    var.setform("DlgKamarInap");
    kamar.bangsal.isCek();
    kamar.bangsal.emptTeks();
    kamar.bangsal.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
    kamar.bangsal.setLocationRelativeTo(internalFrame1);
    kamar.bangsal.setVisible(true);
}//GEN-LAST:event_btnBangsalCariActionPerformed

private void btnBangsalCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnBangsalCariKeyPressed
    Valid.pindah(evt, DTPCari2, TCari);
}//GEN-LAST:event_btnBangsalCariKeyPressed

private void BangsalCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BangsalCariKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        tampil();
    } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
        btnBangsalCariActionPerformed(null);
    } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
        DTPCari3.requestFocus();
    } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
        TCari.requestFocus();
    }
}//GEN-LAST:event_BangsalCariKeyPressed

private void MnRawatInapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRawatInapActionPerformed
    if (tabMode.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
        TCari.requestFocus();
    } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
        try {
            psanak = koneksi.prepareStatement(
                    "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                    + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                    + "from reg_periksa inner join pasien inner join ranap_gabung on "
                    + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
            try {
                psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                rs2 = psanak.executeQuery();
                if (rs2.next()) {
                    var.setform("DlgKamarInap");
                    bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?", Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                    if (bangsal.equals("")) {
                        if (Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")) {
                            var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                        } else {
                            var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                        }
                    } else {
                        var.setkdbangsal(bangsal);
                    }
                    billing.rawatinap.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                    billing.rawatinap.setLocationRelativeTo(internalFrame1);
                    billing.rawatinap.isCek();
                    billing.rawatinap.setNoRm(rs2.getString("no_rawat2"), DTPCari1.getDate(), DTPCari2.getDate());
                    billing.rawatinap.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                    tbKamIn.requestFocus();
                }
            } catch (Exception ex) {
                System.out.println("Notifikasi : " + ex);
            } finally {
                if (rs2 != null) {
                    rs2.close();
                }
                if (psanak != null) {
                    psanak.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    } else {
        var.setform("DlgKamarInap");
        bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?", Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
        if (bangsal.equals("")) {
            if (Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")) {
                var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
            } else {
                var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
            }
        } else {
            var.setkdbangsal(bangsal);
        }
        billing.rawatinap.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        billing.rawatinap.setLocationRelativeTo(internalFrame1);
        billing.rawatinap.isCek();
        billing.rawatinap.setNoRm(norawat.getText(), DTPCari1.getDate(), DTPCari2.getDate());
        billing.rawatinap.setVisible(true);
    }
}//GEN-LAST:event_MnRawatInapActionPerformed

private void MnResepPulangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnResepPulangActionPerformed
    if (tabMode.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
        TCari.requestFocus();
    } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
        try {
            psanak = koneksi.prepareStatement(
                    "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                    + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                    + "from reg_periksa inner join pasien inner join ranap_gabung on "
                    + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
            try {
                psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                rs2 = psanak.executeQuery();
                if (rs2.next()) {
                    var.setform("DlgKamarInap");
                    bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?", Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                    if (bangsal.equals("")) {
                        if (Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")) {
                            var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                        } else {
                            var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                        }
                    } else {
                        var.setkdbangsal(bangsal);
                    }
                    billing.reseppulang.isCek();
                    billing.reseppulang.setNoRm(rs2.getString("no_rawat2"), DTPCari1.getDate(), DTPCari2.getDate(), tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString());
                    billing.reseppulang.tampil();
                    billing.reseppulang.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                    billing.reseppulang.setLocationRelativeTo(internalFrame1);
                    billing.reseppulang.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                    tbKamIn.requestFocus();
                }
            } catch (Exception ex) {
                System.out.println("Notifikasi : " + ex);
            } finally {
                if (rs2 != null) {
                    rs2.close();
                }
                if (psanak != null) {
                    psanak.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    } else {
        var.setform("DlgKamarInap");
        bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?", Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
        if (bangsal.equals("")) {
            if (Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")) {
                var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
            } else {
                var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
            }
        } else {
            var.setkdbangsal(bangsal);
        }
        billing.reseppulang.isCek();
        billing.reseppulang.setNoRm(norawat.getText(), DTPCari1.getDate(), DTPCari2.getDate(), tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString());
        billing.reseppulang.tampil();
        billing.reseppulang.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        billing.reseppulang.setLocationRelativeTo(internalFrame1);
        billing.reseppulang.setVisible(true);
        //this.dispose();
    }
}//GEN-LAST:event_MnResepPulangActionPerformed

private void MnRujukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRujukActionPerformed
    if (tabMode.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
        TCari.requestFocus();
    } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
        try {
            psanak = koneksi.prepareStatement(
                    "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                    + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                    + "from reg_periksa inner join pasien inner join ranap_gabung on "
                    + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
            try {
                psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                rs2 = psanak.executeQuery();
                if (rs2.next()) {
                    DlgRujuk dlgrjk = new DlgRujuk(null, false);
                    dlgrjk.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                    dlgrjk.setLocationRelativeTo(internalFrame1);
                    dlgrjk.emptTeks();
                    dlgrjk.isCek();
                    dlgrjk.setNoRm(rs2.getString("no_rawat2"), DTPCari1.getDate(), DTPCari2.getDate());
                    dlgrjk.tampil();
                    dlgrjk.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                    tbKamIn.requestFocus();
                }
            } catch (Exception ex) {
                System.out.println("Notifikasi : " + ex);
            } finally {
                if (rs2 != null) {
                    rs2.close();
                }
                if (psanak != null) {
                    psanak.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    } else {
        DlgRujuk dlgrjk = new DlgRujuk(null, false);
        dlgrjk.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        dlgrjk.setLocationRelativeTo(internalFrame1);
        dlgrjk.emptTeks();
        dlgrjk.isCek();
        dlgrjk.setNoRm(norawat.getText(), DTPCari1.getDate(), DTPCari2.getDate());
        dlgrjk.tampil();
        dlgrjk.setVisible(true);
        //this.dispose();
    }
}//GEN-LAST:event_MnRujukActionPerformed

private void MnPemberianObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPemberianObatActionPerformed
    if (tabMode.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
        TCari.requestFocus();
    } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
        try {
            psanak = koneksi.prepareStatement(
                    "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                    + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                    + "from reg_periksa inner join pasien inner join ranap_gabung on "
                    + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
            try {
                psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                rs2 = psanak.executeQuery();
                if (rs2.next()) {
                    var.setform("DlgKamarInap");
                    bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?", Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                    if (bangsal.equals("")) {
                        if (Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")) {
                            var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                        } else {
                            var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                        }
                    } else {
                        var.setkdbangsal(bangsal);
                    }

                    billing.beriobat.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                    billing.beriobat.setLocationRelativeTo(internalFrame1);
                    billing.beriobat.setNoRm(rs2.getString("no_rawat2"), DTPCari1.getDate(), DTPCari2.getDate(), "ranap");
                    billing.beriobat.isCek();
                    billing.beriobat.tampilPO();
                    billing.beriobat.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                    tbKamIn.requestFocus();
                }
            } catch (Exception ex) {
                System.out.println("Notifikasi : " + ex);
            } finally {
                if (rs2 != null) {
                    rs2.close();
                }
                if (psanak != null) {
                    psanak.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    } else {
        var.setform("DlgKamarInap");
        bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?", Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
        if (bangsal.equals("")) {
            if (Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")) {
                var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
            } else {
                var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
            }
        } else {
            var.setkdbangsal(bangsal);
        }

        billing.beriobat.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        billing.beriobat.setLocationRelativeTo(internalFrame1);
        billing.beriobat.setNoRm(norawat.getText(), DTPCari1.getDate(), DTPCari2.getDate(), "ranap");
        billing.beriobat.isCek();
        billing.beriobat.tampilPO();
        billing.beriobat.setVisible(true);
        //this.dispose();
    }
}//GEN-LAST:event_MnPemberianObatActionPerformed

private void MnBillingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBillingActionPerformed
    if (tabMode.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
        TCari.requestFocus();
    } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
        tbKamIn.requestFocus();
    } else {
        try {
            pscaripiutang = koneksi.prepareStatement("select tgl_piutang from piutang_pasien where no_rkm_medis=? and status='Belum Lunas' order by tgl_piutang asc limit 1");
            try {
                pscaripiutang.setString(1, TNoRM.getText());
                rs = pscaripiutang.executeQuery();
                if (rs.next()) {
                    i = JOptionPane.showConfirmDialog(null, "Masih ada tunggakan pembayaran, apa mau bayar sekarang ?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (i == JOptionPane.YES_OPTION) {
                        DlgLhtPiutang piutang = new DlgLhtPiutang(null, false);
                        piutang.setNoRm(TNoRM.getText(), rs.getDate(1));
                        piutang.tampil();
                        piutang.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                        piutang.setLocationRelativeTo(internalFrame1);
                        piutang.setVisible(true);
                    } else {
                        bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?", Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                        if (bangsal.equals("")) {
                            if (Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")) {
                                var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                            } else {
                                var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                            }
                        } else {
                            var.setkdbangsal(bangsal);
                        }

                        billing.TNoRw.setText(norawat.getText());
                        billing.isCek();
                        billing.isRawat();
                        billing.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                        billing.setLocationRelativeTo(internalFrame1);
                        billing.setVisible(true);
                    }
                } else {
                    bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?", Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                    if (bangsal.equals("")) {
                        if (Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")) {
                            var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                        } else {
                            var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                        }
                    } else {
                        var.setkdbangsal(bangsal);
                    }

                    billing.TNoRw.setText(norawat.getText());
                    billing.isCek();
                    billing.isRawat();
                    billing.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                    billing.setLocationRelativeTo(internalFrame1);
                    billing.setVisible(true);
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
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
}//GEN-LAST:event_MnBillingActionPerformed

private void MnDietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDietActionPerformed
    if (tabMode.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
        TCari.requestFocus();
    } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
        try {
            psanak = koneksi.prepareStatement(
                    "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                    + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                    + "from reg_periksa inner join pasien inner join ranap_gabung on "
                    + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
            try {
                psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                rs2 = psanak.executeQuery();
                if (rs2.next()) {
                    DlgPemberianDiet rawatinap = new DlgPemberianDiet(null, false);
                    rawatinap.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                    rawatinap.setLocationRelativeTo(internalFrame1);
                    rawatinap.emptTeks();
                    rawatinap.isCek();
                    rawatinap.setNoRm(rs2.getString("no_rawat2"), DTPCari1.getDate(), DTPCari2.getDate());
                    rawatinap.tampil();
                    rawatinap.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                    tbKamIn.requestFocus();
                }
            } catch (Exception ex) {
                System.out.println("Notifikasi : " + ex);
            } finally {
                if (rs2 != null) {
                    rs2.close();
                }
                if (psanak != null) {
                    psanak.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    } else {
        DlgPemberianDiet rawatinap = new DlgPemberianDiet(null, false);
        rawatinap.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        rawatinap.setLocationRelativeTo(internalFrame1);
        rawatinap.emptTeks();
        rawatinap.isCek();
        rawatinap.setNoRm(norawat.getText(), DTPCari1.getDate(), DTPCari2.getDate());
        rawatinap.tampil();
        rawatinap.setVisible(true);
        //this.dispose();
    }
}//GEN-LAST:event_MnDietActionPerformed

private void DTPCari3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPCari3ItemStateChanged
    R3.setSelected(true);
}//GEN-LAST:event_DTPCari3ItemStateChanged

private void DTPCari3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPCari3KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_DTPCari3KeyPressed

private void cmbStatusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbStatusKeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_cmbStatusKeyPressed

private void MnPeriksaLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaLabActionPerformed
    if (tabMode.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
        TCari.requestFocus();
    } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
        try {
            psanak = koneksi.prepareStatement(
                    "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                    + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                    + "from reg_periksa inner join pasien inner join ranap_gabung on "
                    + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
            try {
                psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                rs2 = psanak.executeQuery();
                if (rs2.next()) {
                    var.setform("DlgKamarInap");
                    billing.periksalab.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                    billing.periksalab.setLocationRelativeTo(internalFrame1);
                    billing.periksalab.emptTeks();
                    billing.periksalab.setNoRm(rs2.getString("no_rawat2"), "Ranap");
                    billing.periksalab.isCek();
                    billing.periksalab.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                    tbKamIn.requestFocus();
                }
            } catch (Exception ex) {
                System.out.println("Notifikasi : " + ex);
            } finally {
                if (rs2 != null) {
                    rs2.close();
                }
                if (psanak != null) {
                    psanak.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    } else {
        var.setform("DlgKamarInap");
        billing.periksalab.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        billing.periksalab.setLocationRelativeTo(internalFrame1);
        billing.periksalab.emptTeks();
        billing.periksalab.setNoRm(norawat.getText(), "Ranap");
        billing.periksalab.isCek();
        billing.periksalab.setVisible(true);
    }
}//GEN-LAST:event_MnPeriksaLabActionPerformed

private void JamMasukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamMasukKeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_JamMasukKeyPressed

private void MnOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnOperasiActionPerformed
    if (tabMode.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
        TCari.requestFocus();
    } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
        try {
            psanak = koneksi.prepareStatement(
                    "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                    + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                    + "from reg_periksa inner join pasien inner join ranap_gabung on "
                    + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
            try {
                psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                rs2 = psanak.executeQuery();
                if (rs2.next()) {
                    DlgTagihanOperasi dlgro = new DlgTagihanOperasi(null, false);
                    dlgro.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                    dlgro.setLocationRelativeTo(internalFrame1);
                    dlgro.setNoRm(rs2.getString("no_rawat2"), rs2.getString("no_rkm_medis") + ", " + rs2.getString("nm_pasien"), "Ranap");
                    dlgro.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                    tbKamIn.requestFocus();
                }
            } catch (Exception ex) {
                System.out.println("Notifikasi : " + ex);
            } finally {
                if (rs2 != null) {
                    rs2.close();
                }
                if (psanak != null) {
                    psanak.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    } else {
        DlgTagihanOperasi dlgro = new DlgTagihanOperasi(null, false);
        dlgro.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        dlgro.setLocationRelativeTo(internalFrame1);
        dlgro.setNoRm(norawat.getText(), TNoRM.getText() + ", " + TPasien.getText(), "Ranap");
        dlgro.setVisible(true);
    }
}//GEN-LAST:event_MnOperasiActionPerformed

private void MnHapusTagihanOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTagihanOperasiActionPerformed
    if (tabMode.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
        TCari.requestFocus();
    } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
        try {
            psanak = koneksi.prepareStatement(
                    "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                    + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                    + "from reg_periksa inner join pasien inner join ranap_gabung on "
                    + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
            try {
                psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                rs2 = psanak.executeQuery();
                if (rs2.next()) {
                    if (Sequel.cariRegistrasi(tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString()) > 0) {
                        JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                        TCari.requestFocus();
                    } else {
                        Sequel.queryu("delete from operasi where no_rawat='" + rs2.getString("no_rawat2") + "'");
                        Sequel.queryu("delete from beri_obat_operasi where no_rawat='" + rs2.getString("no_rawat2") + "'");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                    tbKamIn.requestFocus();
                }
            } catch (Exception ex) {
                System.out.println("Notifikasi : " + ex);
            } finally {
                if (rs2 != null) {
                    rs2.close();
                }
                if (psanak != null) {
                    psanak.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    } else {
        if (Sequel.cariRegistrasi(norawat.getText().trim()) > 0) {
            JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
            TCari.requestFocus();
        } else {
            Sequel.queryu("delete from operasi where no_rawat='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0) + "'");
            Sequel.queryu("delete from beri_obat_operasi where no_rawat='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0) + "'");
        }

    }
}//GEN-LAST:event_MnHapusTagihanOperasiActionPerformed

private void MnHapusObatOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusObatOperasiActionPerformed
    if (tabMode.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
        TCari.requestFocus();
    } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
        try {
            psanak = koneksi.prepareStatement(
                    "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                    + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                    + "from reg_periksa inner join pasien inner join ranap_gabung on "
                    + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
            try {
                psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                rs2 = psanak.executeQuery();
                if (rs2.next()) {
                    if (Sequel.cariRegistrasi(tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString()) > 0) {
                        JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                        TCari.requestFocus();
                    } else {
                        Sequel.queryu("delete from beri_obat_operasi where no_rawat='" + rs2.getString("no_rawat2") + "'");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                    tbKamIn.requestFocus();
                }
            } catch (Exception ex) {
                System.out.println("Notifikasi : " + ex);
            } finally {
                if (rs2 != null) {
                    rs2.close();
                }
                if (psanak != null) {
                    psanak.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    } else {
        if (Sequel.cariRegistrasi(norawat.getText().trim()) > 0) {
            JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
            TCari.requestFocus();
        } else {
            Sequel.queryu("delete from beri_obat_operasi where no_rawat='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0) + "'");
        }

    }
}//GEN-LAST:event_MnHapusObatOperasiActionPerformed

private void MnRujukMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRujukMasukActionPerformed
    if (tabMode.getRowCount() == 0) {
        JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
        TCari.requestFocus();
    } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
        try {
            psanak = koneksi.prepareStatement(
                    "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                    + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                    + "from reg_periksa inner join pasien inner join ranap_gabung on "
                    + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");

            try {
                psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                rs2 = psanak.executeQuery();
                if (rs2.next()) {
                    var.setform("DlgKamarInap");
                    DlgRujukMasuk rujukmasuk = new DlgRujukMasuk(null, false);
                    rujukmasuk.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                    rujukmasuk.setLocationRelativeTo(internalFrame1);
                    rujukmasuk.emptTeks();
                    rujukmasuk.isCek();
                    rujukmasuk.setNoRm(rs2.getString("no_rawat2"), DTPCari1.getDate(), DTPCari2.getDate());
                    rujukmasuk.tampil();
                    rujukmasuk.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                    tbKamIn.requestFocus();
                }
            } catch (Exception ex) {
                System.out.println("Notifikasi : " + ex);
            } finally {
                if (rs2 != null) {
                    rs2.close();
                }
                if (psanak != null) {
                    psanak.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    } else {
        var.setform("DlgKamarInap");
        DlgRujukMasuk rujukmasuk = new DlgRujukMasuk(null, false);
        rujukmasuk.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        rujukmasuk.setLocationRelativeTo(internalFrame1);
        rujukmasuk.emptTeks();
        rujukmasuk.isCek();
        rujukmasuk.setNoRm(norawat.getText(), DTPCari1.getDate(), DTPCari2.getDate());
        rujukmasuk.tampil();
        rujukmasuk.setVisible(true);
        //this.dispose();
    }
}//GEN-LAST:event_MnRujukMasukActionPerformed

    private void btnPindahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPindahActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data kamar inap pasien sudah habis...!!!!");
            BtnIn.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu data inap pasien yang mau pindah dengan menklik data pada table...!!!");
            tbKamIn.requestFocus();
        } else if (TOut.getText().trim().length() > 0) {
            JOptionPane.showMessageDialog(null, "Maaf, pasien ini sudah pulang pada tanggal " + TOut.getText() + " ...!!!");
            emptTeks();
            tbKamIn.requestFocus();
        } else if ((TOut.getText().length() == 0) && (norawat.getText().length() > 0)) {
            kdkamarpindah.setText("");
            TKdBngsalpindah.setText("");
            TBangsalpindah.setText("");
            TJmlHaripindah.setText("1");
            TTarifpindah.setText("0");
            ttlbiayapindah.setText("0");
            date = new Date();
            now = dateFormat.format(date);
            CmbTahunpindah.setSelectedItem(now.substring(0, 4));
            CmbBlnpindah.setSelectedItem(now.substring(5, 7));
            CmbTglpindah.setSelectedItem(now.substring(8, 10));
            cmbJampindah.setSelectedItem(now.substring(11, 13));
            cmbMntpindah.setSelectedItem(now.substring(14, 16));
            cmbDtkpindah.setSelectedItem(now.substring(17, 19));
            norawat.requestFocus();
            WindowPindahKamar.setLocationRelativeTo(internalFrame1);
            WindowPindahKamar.setVisible(true);
            lama = Sequel.cariIsiAngka("select lamajam from set_jam_minimal");
            i = 2;
            isKmr();
            isjml();
        }
    }//GEN-LAST:event_btnPindahActionPerformed

    private void btnPindahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPindahKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            btnPindahActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnOut, BtnHapus);
        }
    }//GEN-LAST:event_btnPindahKeyPressed

    private void kdkamarpindahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdkamarpindahKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            i = 2;
            isKmr();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            CmbTahun.requestFocus();
            i = 2;
            isKmr();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnSimpanpindah.requestFocus();
            i = 2;
            isKmr();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnKamar2ActionPerformed(null);
        }
    }//GEN-LAST:event_kdkamarpindahKeyPressed

    private void btnKamar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKamar2ActionPerformed
        var.setform("DlgKamarInap");
        i = 2;
        kamar.load();
        kamar.isCek();
        kamar.emptTeks();
        kamar.tampil();
        kamar.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        kamar.setLocationRelativeTo(internalFrame1);
        kamar.setVisible(true);
    }//GEN-LAST:event_btnKamar2ActionPerformed

    private void TBangsalpindahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBangsalpindahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TBangsalpindahKeyPressed

    private void BtnCloseInpindahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseInpindahActionPerformed
        TOut.setText("");
        WindowPindahKamar.dispose();
    }//GEN-LAST:event_BtnCloseInpindahActionPerformed

    private void BtnCloseInpindahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCloseInpindahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCloseInpindahKeyPressed

    private void BtnSimpanpindahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanpindahActionPerformed
        if (TPasienpindah.getText().trim().equals("")) {
            Valid.textKosong(norawatpindah, "pasien");
        } else if (TKdBngsalpindah.getText().trim().equals("")) {
            Valid.textKosong(kdkamarpindah, "kamar");
        } else {
            switch (TSttsKamarpindah.getText().trim()) {
                case "ISI":
                    JOptionPane.showMessageDialog(null, "Maaf, Status kamar sudah terisi silahkan pilih kamar kosong..!!!!");
                    kdkamar.requestFocus();
                    break;
                case "KOSONG":
                    if (Rganti1.isSelected() == true) {
                        Sequel.menyimpan("kamar_inap", "'" + norawatpindah.getText() + "','"
                                + kdkamarpindah.getText() + "','" + TTarifpindah.getText() + "','"
                                + diagnosaawal.getText() + "','"
                                + diagnosaakhir.getText() + "','"
                                + CmbTahunpindah.getSelectedItem() + "-" + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem() + "','"
                                + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem() + ":" + cmbDtkpindah.getSelectedItem() + "','0000-00-00','00:00:00','"
                                + TJmlHaripindah.getText() + "','" + ttlbiayapindah.getText() + "','-'", "No.Rawat");
                        Sequel.mengedit("kamar", "kd_kamar='" + kdkamarpindah.getText() + "'", "status='ISI'");
                        Sequel.queryu("delete from kamar_inap where no_rawat='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString()
                                + "' and kd_kamar='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 20).toString()
                                + "' and tgl_masuk='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString()
                                + "' and jam_masuk='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 13).toString() + "'");
                        Sequel.mengedit("kamar", "kd_kamar='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 20).toString() + "'", "status='KOSONG'");
                    } else if (Rganti2.isSelected() == true) {
                        Sequel.queryu("update kamar_inap set kd_kamar='" + kdkamarpindah.getText() + "',trf_kamar='" + TTarifpindah.getText() + "',"
                                + "lama='" + TJmlHaripindah.getText() + "',ttl_biaya='" + ttlbiayapindah.getText()
                                + "' where no_rawat='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString()
                                + "' and kd_kamar='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 20).toString()
                                + "' and tgl_masuk='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString()
                                + "' and jam_masuk='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 13).toString() + "'");
                        Sequel.mengedit("kamar", "kd_kamar='" + kdkamarpindah.getText() + "'", "status='ISI'");
                        Sequel.mengedit("kamar", "kd_kamar='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 20).toString() + "'", "status='KOSONG'");
                    } else if (Rganti3.isSelected() == true) {
                        i = 1;
                        kdkamar.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 20).toString());
                        isKmr();
                        if (hariawal.equals("Yes")) {
                            Sequel.cariIsi("select (if(to_days('" + CmbTahunpindah.getSelectedItem()
                                    + "-" + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem()
                                    + " " + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem()
                                    + ":" + cmbDtkpindah.getSelectedItem() + "')-to_days('" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString()
                                    + " " + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString()
                                    + "')=0,if(time_to_sec('" + CmbTahunpindah.getSelectedItem()
                                    + "-" + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem()
                                    + " " + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem()
                                    + ":" + cmbDtkpindah.getSelectedItem() + "')-time_to_sec('" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString()
                                    + " " + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString() + "')>(3600*"
                                    + lama + "),1,0),to_days('" + CmbTahunpindah.getSelectedItem()
                                    + "-" + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem()
                                    + " " + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem() + ":" + cmbDtkpindah.getSelectedItem()
                                    + "')-to_days('" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString()
                                    + " " + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString() + "'))+1) as lama", TJmlHari);
                        } else {
                            Sequel.cariIsi("select if(to_days('" + CmbTahunpindah.getSelectedItem()
                                    + "-" + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem()
                                    + " " + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem()
                                    + ":" + cmbDtkpindah.getSelectedItem() + "')-to_days('" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString()
                                    + " " + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 13).toString()
                                    + "')=0,if(time_to_sec('" + CmbTahunpindah.getSelectedItem()
                                    + "-" + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem()
                                    + " " + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem()
                                    + ":" + cmbDtkpindah.getSelectedItem() + "')-time_to_sec('" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString()
                                    + " " + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 13).toString() + "')>(3600*"
                                    + lama + "),1,0),to_days('" + CmbTahunpindah.getSelectedItem()
                                    + "-" + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem()
                                    + " " + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem() + ":" + cmbDtkpindah.getSelectedItem()
                                    + "')-to_days('" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString()
                                    + " " + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 13).toString() + "')) as lama", TJmlHari);
                        }

                        isjml();
                        Sequel.mengedit("kamar_inap", "no_rawat='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString()
                                + "' and kd_kamar='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 20).toString()
                                + "' and tgl_masuk='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString()
                                + "' and jam_masuk='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 13).toString() + "'",
                                "trf_kamar='" + TTarif.getText() + "',tgl_keluar='" + CmbTahunpindah.getSelectedItem() + "-" + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem()
                                + "',jam_keluar='" + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem() + ":" + cmbDtkpindah.getSelectedItem()
                                + "',lama='" + TJmlHari.getText() + "',ttl_biaya='" + ttlbiaya.getText() + "',stts_pulang='Pindah Kamar'");
                        Sequel.mengedit("kamar", "kd_kamar='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 20).toString() + "'", "status='KOSONG'");
                        Sequel.menyimpan("kamar_inap", "'" + norawatpindah.getText() + "','"
                                + kdkamarpindah.getText() + "','" + TTarifpindah.getText() + "','"
                                + diagnosaawal.getText() + "','" + diagnosaakhir.getText() + "','"
                                + CmbTahunpindah.getSelectedItem() + "-" + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem() + "','"
                                + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem() + ":" + cmbDtkpindah.getSelectedItem() + "','0000-00-00','00:00:00','"
                                + TJmlHaripindah.getText() + "','" + ttlbiayapindah.getText() + "','-'", "No.Rawat");
                        Sequel.mengedit("kamar", "kd_kamar='" + kdkamarpindah.getText() + "'", "status='ISI'");
                    } else if (Rganti4.isSelected() == true) {
                        i = 1;
                        kdkamar.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString());
                        isKmr();
                        if (hariawal.equals("Yes")) {
                            Sequel.cariIsi("select (if(to_days('" + CmbTahunpindah.getSelectedItem()
                                    + "-" + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem()
                                    + " " + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem()
                                    + ":" + cmbDtkpindah.getSelectedItem() + "')-to_days('" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString()
                                    + " " + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString()
                                    + "')=0,if(time_to_sec('" + CmbTahunpindah.getSelectedItem()
                                    + "-" + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem()
                                    + " " + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem()
                                    + ":" + cmbDtkpindah.getSelectedItem() + "')-time_to_sec('" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString()
                                    + " " + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString() + "')>(3600*"
                                    + lama + "),1,0),to_days('" + CmbTahunpindah.getSelectedItem()
                                    + "-" + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem()
                                    + " " + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem() + ":" + cmbDtkpindah.getSelectedItem()
                                    + "')-to_days('" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString()
                                    + " " + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString() + "'))+1) as lama", TJmlHari);
                        } else {
                            Sequel.cariIsi("select if(to_days('" + CmbTahunpindah.getSelectedItem()
                                    + "-" + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem()
                                    + " " + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem()
                                    + ":" + cmbDtkpindah.getSelectedItem() + "')-to_days('" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString()
                                    + " " + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString()
                                    + "')=0,if(time_to_sec('" + CmbTahunpindah.getSelectedItem()
                                    + "-" + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem()
                                    + " " + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem()
                                    + ":" + cmbDtkpindah.getSelectedItem() + "')-time_to_sec('" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString()
                                    + " " + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString() + "')>(3600*"
                                    + lama + "),1,0),to_days('" + CmbTahunpindah.getSelectedItem()
                                    + "-" + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem()
                                    + " " + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem() + ":" + cmbDtkpindah.getSelectedItem()
                                    + "')-to_days('" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString()
                                    + " " + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString() + "')) as lama", TJmlHari);
                        }

                        DecimalFormat df2 = new DecimalFormat("####");
                        if ((!TJmlHari.getText().equals("")) && (!TTarif.getText().equals(""))) {
                            double x = Double.parseDouble(TJmlHari.getText().trim());
                            double y = 0;
                            if (Double.parseDouble(TTarif.getText().trim()) > Double.parseDouble(TTarifpindah.getText().trim())) {
                                y = Double.parseDouble(TTarif.getText().trim());
                            } else if (Double.parseDouble(TTarif.getText().trim()) < Double.parseDouble(TTarifpindah.getText().trim())) {
                                y = Double.parseDouble(TTarifpindah.getText().trim());
                            }
                            ttlbiaya.setText(df2.format(x * y));
                        }
                        Sequel.mengedit("kamar_inap", "no_rawat='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString()
                                + "' and kd_kamar='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString()
                                + "' and tgl_masuk='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString()
                                + "' and jam_masuk='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString() + "'",
                                "trf_kamar='" + TTarifpindah.getText() + "',tgl_keluar='" + CmbTahunpindah.getSelectedItem() + "-" + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem()
                                + "',jam_keluar='" + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem() + ":" + cmbDtkpindah.getSelectedItem()
                                + "',ttl_biaya='" + ttlbiaya.getText() + "',lama='" + TJmlHari.getText() + "',stts_pulang='Pindah Kamar'");
                        Sequel.mengedit("kamar", "kd_kamar='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString() + "'", "status='KOSONG'");
                        Sequel.menyimpan("kamar_inap", "'" + norawatpindah.getText() + "','"
                                + kdkamarpindah.getText() + "','" + TTarifpindah.getText() + "','"
                                + diagnosaawal.getText() + "','"
                                + diagnosaakhir.getText() + "','"
                                + CmbTahunpindah.getSelectedItem() + "-" + CmbBlnpindah.getSelectedItem() + "-" + CmbTglpindah.getSelectedItem() + "','"
                                + cmbJampindah.getSelectedItem() + ":" + cmbMntpindah.getSelectedItem() + ":" + cmbDtkpindah.getSelectedItem() + "','0000-00-00','00:00:00','" + TJmlHaripindah.getText() + "','"
                                + ttlbiayapindah.getText() + "','-'", "No.Rawat");
                        Sequel.mengedit("kamar", "kd_kamar='" + kdkamarpindah.getText() + "'", "status='ISI'");
                    }
                    tampil();
                    WindowPindahKamar.dispose();
                    break;
            }
        }
    }//GEN-LAST:event_BtnSimpanpindahActionPerformed

    private void BtnSimpanpindahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanpindahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSimpanpindahKeyPressed

    private void MnHapusDataSalahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusDataSalahActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            norawat.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus. Klik data pada table untuk memilih.\nUntuk menghapus pasien bayi lewat ranap gabung!!!!");
        } else if (!(norawat.getText().trim().equals(""))) {
            if (Sequel.cariRegistrasi(norawat.getText()) > 0) {
                JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                TCari.requestFocus();
            } else {
                i = JOptionPane.showConfirmDialog(rootPane, "Yakin data mau dihapus..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (i == JOptionPane.YES_OPTION) {
                    Sequel.queryu("delete from kamar_inap where no_rawat='" + norawat.getText() + "' and kd_kamar='" + kdkamar.getText() + "' and tgl_masuk='" + TIn.getText() + "' and jam_masuk='" + JamMasuk.getText() + "'");
                    Sequel.mengedit("kamar", "kd_kamar='" + kdkamar.getText() + "'", "status='KOSONG'");
                    if (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?", norawat.getText()) == 0) {
                        Sequel.mengedit("reg_periksa", "no_rawat='" + norawat.getText() + "'", "status_lanjut='Ralan'");
                    }
                    tampil();
                }
            }
        }
        emptTeks();
    }//GEN-LAST:event_MnHapusDataSalahActionPerformed

    private void MnStokObatPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnStokObatPasienActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        DlgInputStokPasien dlgrjk = new DlgInputStokPasien(null, false);
                        dlgrjk.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                        dlgrjk.setLocationRelativeTo(internalFrame1);
                        dlgrjk.isCek();
                        dlgrjk.setNoRm(rs2.getString("no_rawat2"), TNoRM.getText() + " " + TPasien.getText());
                        dlgrjk.tampil();
                        dlgrjk.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            DlgInputStokPasien dlgrjk = new DlgInputStokPasien(null, false);
            dlgrjk.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            dlgrjk.setLocationRelativeTo(internalFrame1);
            dlgrjk.isCek();
            dlgrjk.setNoRm(norawat.getText(), TNoRM.getText() + " " + TPasien.getText());
            dlgrjk.tampil();
            dlgrjk.setVisible(true);
            //this.dispose();
        }
    }//GEN-LAST:event_MnStokObatPasienActionPerformed

    private void BtnCloseIn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn4ActionPerformed
        WindowCaraBayar.dispose();
    }//GEN-LAST:event_BtnCloseIn4ActionPerformed

    private void BtnSimpan4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan4ActionPerformed
        if (norawat.getText().trim().equals("")) {
            Valid.textKosong(norawat, "No.Rawat");
        }
        if (kdpenjab.getText().trim().equals("") || nmpenjab.getText().trim().equals("")) {
            Valid.textKosong(kdpenjab, "Jenis Bayar");
        } else {
            //String kdpj=Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",norawat.getText());

            //Sequel.meghapus("ubah_penjab","no_rawat",norawat.getText());
            Sequel.mengedit("reg_periksa", "no_rawat=?", " kd_pj=?", 2, new String[]{kdpenjab.getText(), norawat.getText()});
            //Sequel.menyimpan("ubah_penjab","?,?,?,?","Ubah Jenis Bayar",4,new String[]{norawat.getText(),now,kdpj,kdpenjab.getText()});

            tampil();
            WindowCaraBayar.dispose();
        }
    }//GEN-LAST:event_BtnSimpan4ActionPerformed

    private void kdpenjabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpenjabKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?", nmpenjab, kdpenjab.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            btnBayarActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnCloseIn4, BtnSimpan4);
        }
    }//GEN-LAST:event_kdpenjabKeyPressed

    private void btnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBayarActionPerformed
        var.setform("DlgKamarInap");
        reg.pasien.penjab.emptTeks();
        reg.pasien.penjab.isCek();
        reg.pasien.penjab.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        reg.pasien.penjab.setLocationRelativeTo(internalFrame1);
        reg.pasien.penjab.setVisible(true);
    }//GEN-LAST:event_btnBayarActionPerformed

    private void MnPenjabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenjabActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbKamIn.requestFocus();
        } else {
            WindowCaraBayar.setLocationRelativeTo(internalFrame1);
            WindowCaraBayar.setVisible(true);
        }
    }//GEN-LAST:event_MnPenjabActionPerformed

    private void MnStatusRujukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnStatusRujukActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbKamIn.requestFocus();
        } else if (TOut.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, pasien ini belum dipulangkan ...!!!");
            emptTeks();
            tbKamIn.requestFocus();
        } else {
            Sequel.mengedit("kamar_inap", "no_rawat='" + norawat.getText() + "' and kd_kamar='" + kdkamar.getText() + "' and tgl_masuk='" + TIn.getText() + "' and jam_masuk='" + JamMasuk.getText() + "'", "stts_pulang='Rujuk'");
            Sequel.mengedit("kamar", "kd_kamar='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString() + "'", "status='KOSONG'");
            tampil();
        }

    }//GEN-LAST:event_MnStatusRujukActionPerformed

    private void MnStatusAPSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnStatusAPSActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbKamIn.requestFocus();
        } else if (TOut.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, pasien ini belum dipulangkan ...!!!");
            emptTeks();
            tbKamIn.requestFocus();
        } else {
            Sequel.mengedit("kamar_inap", "no_rawat='" + norawat.getText() + "' and kd_kamar='" + kdkamar.getText() + "' and tgl_masuk='" + TIn.getText() + "' and jam_masuk='" + JamMasuk.getText() + "'", "stts_pulang='APS'");
            Sequel.mengedit("kamar", "kd_kamar='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString() + "'", "status='KOSONG'");
            tampil();
        }
    }//GEN-LAST:event_MnStatusAPSActionPerformed

    private void MnStatusMeninggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnStatusMeninggalActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbKamIn.requestFocus();
        } else if (TOut.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, pasien ini belum dipulangkan ...!!!");
            emptTeks();
            tbKamIn.requestFocus();
        } else {
            Sequel.mengedit("kamar_inap", "no_rawat='" + norawat.getText() + "' and kd_kamar='" + kdkamar.getText() + "' and tgl_masuk='" + TIn.getText() + "' and jam_masuk='" + JamMasuk.getText() + "'", "stts_pulang='Meninggal'");
            Sequel.mengedit("kamar", "kd_kamar='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString() + "'", "status='KOSONG'");
            tampil();
        }

    }//GEN-LAST:event_MnStatusMeninggalActionPerformed

    private void MnStatusMembaikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnStatusMembaikActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbKamIn.requestFocus();
        } else if (TOut.getText().trim().length() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, pasien ini belum dipulangkan ...!!!");
            emptTeks();
            tbKamIn.requestFocus();
        } else {
            Sequel.mengedit("kamar_inap", "no_rawat='" + norawat.getText() + "' and kd_kamar='" + kdkamar.getText() + "' and tgl_masuk='" + TIn.getText() + "' and jam_masuk='" + JamMasuk.getText() + "'", "stts_pulang='Membaik'");
            Sequel.mengedit("kamar", "kd_kamar='" + tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString() + "'", "status='KOSONG'");
            tampil();
        }
    }//GEN-LAST:event_MnStatusMembaikActionPerformed

    private void MnSensusRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSensusRanapActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else {
            Valid.panggilUrl("billing/LaporanSensusHarian.php?tanggal1=" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "&tanggal2=" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "&kamar=" + BangsalCari.getText().replaceAll(" ", "_"));
        }
    }//GEN-LAST:event_MnSensusRanapActionPerformed

    private void MnRekapitulasiRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekapitulasiRanapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MnRekapitulasiRanapActionPerformed

    private void MnDepositActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDepositActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");

                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        billing.deposit.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                        billing.deposit.setLocationRelativeTo(internalFrame1);
                        billing.deposit.setNoRm(rs2.getString("no_rawat2"), DTPCari1.getDate(), DTPCari2.getDate());
                        billing.deposit.tampil();
                        billing.deposit.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            billing.deposit.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            billing.deposit.setLocationRelativeTo(internalFrame1);
            billing.deposit.setNoRm(norawat.getText(), DTPCari1.getDate(), DTPCari2.getDate());
            billing.deposit.tampil();
            billing.deposit.setVisible(true);
        }
    }//GEN-LAST:event_MnDepositActionPerformed

    private void MnNoResepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnNoResepActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur) as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        DlgResepObat resep = new DlgResepObat(null, false);
                        resep.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                        resep.setLocationRelativeTo(internalFrame1);
                        resep.emptTeks();
                        resep.isCek();
                        resep.setNoRm(rs2.getString("no_rawat2"), DTPCari1.getDate(), DTPCari2.getDate(), now.substring(11, 13), now.substring(14, 16), now.substring(17, 19), "ranap");
                        resep.tampil();
                        resep.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            DlgResepObat resep = new DlgResepObat(null, false);
            resep.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            resep.setLocationRelativeTo(internalFrame1);
            resep.emptTeks();
            resep.isCek();
            resep.setNoRm(norawat.getText(), DTPCari1.getDate(), DTPCari2.getDate(), now.substring(11, 13), now.substring(14, 16), now.substring(17, 19), "ranap");
            resep.tampil();
            resep.setVisible(true);
        }
    }//GEN-LAST:event_MnNoResepActionPerformed

    private void MnRM2DActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRM2DActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");

                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        Map<String, Object> param = new HashMap<>();
                        param.put("nama", rs2.getString("nm_pasien"));
                        param.put("jkel", Sequel.cariIsi("select if(pasien.jk='L','Laki-Laki','Perempuan') from pasien where pasien.no_rkm_medis=?", rs2.getString("no_rkm_medis")));
                        param.put("lahir", Sequel.cariIsi("select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=?", rs2.getString("no_rkm_medis")));
                        param.put("norm", rs2.getString("no_rkm_medis"));
                        param.put("ruang", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString().replaceAll("  ", " "));
                        param.put("kelas", Sequel.cariIsi("select kamar.kelas from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where kamar.kd_kamar=? ", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString()));
                        param.put("tanggaldaftar", Sequel.cariIsi("select DATE_FORMAT(reg_periksa.tgl_registrasi,'%d-%m-%Y') from reg_periksa where reg_periksa.no_rawat=?", rs2.getString("no_rawat2")));
                        param.put("jamdaftar", Sequel.cariIsi("select reg_periksa.jam_reg from reg_periksa where reg_periksa.no_rawat=?", rs2.getString("no_rawat2")));
                        param.put("noreg", Sequel.cariIsi("select reg_periksa.no_reg from reg_periksa where reg_periksa.no_rawat=?", rs2.getString("no_rawat2")));
                        param.put("pendidikan", "-");
                        param.put("agama", Sequel.cariIsi("select pasien.agama from pasien where pasien.no_rkm_medis=?", rs2.getString("no_rkm_medis")));
                        param.put("bangsa", "Jawa/Indonesia");
                        param.put("pekerjaan", Sequel.cariIsi("select pasien.agama from pasien where pasien.no_rkm_medis=?", rs2.getString("no_rkm_medis")));
                        param.put("status", "Single");
                        param.put("alamat", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 3).toString());
                        param.put("keluarga", Sequel.cariIsi("select pasien.namakeluarga from pasien where pasien.no_rkm_medis=?", rs2.getString("no_rkm_medis")));
                        param.put("telp", Sequel.cariIsi("select pasien.no_tlp from pasien where pasien.no_rkm_medis=?", rs2.getString("no_rkm_medis")));
                        param.put("rujukandari", Sequel.cariIsi("select rujuk_masuk.perujuk from rujuk_masuk where rujuk_masuk.no_rawat=?", rs2.getString("no_rawat2")));
                        param.put("chkri", Sequel.cariIsi("select if(count(kamar_inap.no_rawat)>1,'V','') from reg_periksa inner join pasien inner join kamar_inap "
                                + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat where reg_periksa.no_rkm_medis=? ", rs2.getString("no_rkm_medis")));
                        param.put("chkrj", Sequel.cariIsi("select if(count(reg_periksa.no_rawat)>1,'V','') from reg_periksa inner join pasien "
                                + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rkm_medis=? ", rs2.getString("no_rkm_medis")));
                        param.put("riterakhir", Sequel.cariIsi("select if(count(kamar_inap.no_rawat)>1,DATE_FORMAT(kamar_inap.tgl_keluar,'%d-%m-%Y'),'') from reg_periksa inner join pasien inner join kamar_inap "
                                + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat where reg_periksa.no_rkm_medis=? order by kamar_inap.tgl_masuk desc limit 1", rs2.getString("no_rkm_medis")));
                        param.put("rjterakhir", Sequel.cariIsi("select if(count(reg_periksa.no_rawat)>1,DATE_FORMAT(reg_periksa.tgl_registrasi,'%d-%m-%Y'),'') from reg_periksa inner join pasien "
                                + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rkm_medis=? order by reg_periksa.tgl_registrasi desc ", rs2.getString("no_rkm_medis")));
                        param.put("rike", Sequel.cariIsi("select if(count(kamar_inap.no_rawat)>1,(count(kamar_inap.no_rawat)-1),'') from reg_periksa inner join pasien inner join kamar_inap "
                                + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat where reg_periksa.no_rkm_medis=? ", rs2.getString("no_rkm_medis")));
                        param.put("rjke", Sequel.cariIsi("select if(count(reg_periksa.no_rawat)>1,(count(reg_periksa.no_rawat)-1),'') from reg_periksa inner join pasien "
                                + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rkm_medis=? ", rs2.getString("no_rkm_medis")));
                        param.put("riruang", Sequel.cariIsi("select if(count(kamar_inap.no_rawat)>1,kamar_inap.kd_kamar,'') from reg_periksa inner join pasien inner join kamar_inap "
                                + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat where reg_periksa.no_rkm_medis=? order by kamar_inap.tgl_masuk desc limit 1", rs2.getString("no_rkm_medis")));
                        param.put("rjruang", Sequel.cariIsi("select if(count(reg_periksa.no_rawat)>1,kd_poli,'') from reg_periksa inner join pasien "
                                + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rkm_medis=? order by reg_periksa.tgl_registrasi desc ", rs2.getString("no_rkm_medis")));
                        param.put("chkruang", Sequel.cariIsi("select if(count(kamar_inap.no_rawat)>1,'V','') from reg_periksa inner join pasien inner join kamar_inap "
                                + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat where reg_periksa.no_rkm_medis=? order by kamar_inap.tgl_masuk desc limit 1", rs2.getString("no_rkm_medis")));
                        param.put("chkbangsal", Sequel.cariIsi("select if(count(reg_periksa.no_rawat)>1,'V','') from reg_periksa inner join pasien "
                                + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rkm_medis=? order by reg_periksa.tgl_registrasi desc ", rs2.getString("no_rkm_medis")));
                        param.put("chkkelri", Sequel.cariIsi("select if(count(kamar_inap.no_rawat)>1,'V','') from reg_periksa inner join pasien inner join kamar_inap "
                                + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat where reg_periksa.no_rkm_medis=? order by kamar_inap.tgl_masuk desc limit 1", rs2.getString("no_rkm_medis")));
                        param.put("chkkelrj", Sequel.cariIsi("select if(count(reg_periksa.no_rawat)>1,'V','') from reg_periksa inner join pasien "
                                + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rkm_medis=? order by reg_periksa.tgl_registrasi desc ", rs2.getString("no_rkm_medis")));
                        param.put("petugas", Sequel.cariIsi("select nama from petugas where nip=?", var.getkode()));

                        param.put("namars", var.getnamars());
                        param.put("alamatrs", var.getalamatrs());
                        param.put("kotars", var.getkabupatenrs());
                        param.put("propinsirs", var.getpropinsirs());
                        param.put("kontakrs", var.getkontakrs());
                        param.put("emailrs", var.getemailrs());
                        param.put("logo", Sequel.cariGambar("select logo from setting"));
                        Valid.MyReport("rptRM2D.jrxml", "report", "::[ Lembar Assasmen ]::",
                                "select current_date() as tanggal, current_time() as jam", param);
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            Map<String, Object> param = new HashMap<>();
            param.put("nama", TPasien.getText());
            param.put("jkel", Sequel.cariIsi("select if(pasien.jk='L','Laki-Laki','Perempuan') from pasien where pasien.no_rkm_medis=?", TNoRM.getText()));
            param.put("lahir", Sequel.cariIsi("select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=?", TNoRM.getText()));
            param.put("norm", TNoRM.getText());
            param.put("ruang", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString().replaceAll("  ", " "));
            param.put("kelas", Sequel.cariIsi("select kamar.kelas from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where kamar.kd_kamar=? ", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString()));
            param.put("tanggaldaftar", Sequel.cariIsi("select DATE_FORMAT(reg_periksa.tgl_registrasi,'%d-%m-%Y') from reg_periksa where reg_periksa.no_rawat=?", norawat.getText()));
            param.put("jamdaftar", Sequel.cariIsi("select reg_periksa.jam_reg from reg_periksa where reg_periksa.no_rawat=?", norawat.getText()));
            param.put("noreg", Sequel.cariIsi("select reg_periksa.no_reg from reg_periksa where reg_periksa.no_rawat=?", norawat.getText()));
            param.put("pendidikan", Sequel.cariIsi("select pasien.pnd from pasien where pasien.no_rkm_medis=?", TNoRM.getText()));
            param.put("agama", Sequel.cariIsi("select pasien.agama from pasien where pasien.no_rkm_medis=?", TNoRM.getText()));
            param.put("bangsa", "Jawa/Indonesia");
            param.put("pekerjaan", Sequel.cariIsi("select pasien.agama from pasien where pasien.no_rkm_medis=?", TNoRM.getText()));
            param.put("status", Sequel.cariIsi("select pasien.stts_nikah from pasien where pasien.no_rkm_medis=?", TNoRM.getText()));
            param.put("alamat", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 3).toString());
            param.put("keluarga", Sequel.cariIsi("select pasien.namakeluarga from pasien where pasien.no_rkm_medis=?", TNoRM.getText()));
            param.put("telp", Sequel.cariIsi("select pasien.no_tlp from pasien where pasien.no_rkm_medis=?", TNoRM.getText()));
            param.put("rujukandari", Sequel.cariIsi("select rujuk_masuk.perujuk from rujuk_masuk where rujuk_masuk.no_rawat=?", norawat.getText()));
            param.put("chkri", Sequel.cariIsi("select if(count(kamar_inap.no_rawat)>1,'V','') from reg_periksa inner join pasien inner join kamar_inap "
                    + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat where reg_periksa.no_rkm_medis=? ", TNoRM.getText()));
            param.put("chkrj", Sequel.cariIsi("select if(count(reg_periksa.no_rawat)>1,'V','') from reg_periksa inner join pasien "
                    + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rkm_medis=? ", TNoRM.getText()));
            param.put("riterakhir", Sequel.cariIsi("select if(count(kamar_inap.no_rawat)>1,DATE_FORMAT(kamar_inap.tgl_keluar,'%d-%m-%Y'),'') from reg_periksa inner join pasien inner join kamar_inap "
                    + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat where reg_periksa.no_rkm_medis=? order by kamar_inap.tgl_masuk desc limit 1", TNoRM.getText()));
            param.put("rjterakhir", Sequel.cariIsi("select if(count(reg_periksa.no_rawat)>1,DATE_FORMAT(reg_periksa.tgl_registrasi,'%d-%m-%Y'),'') from reg_periksa inner join pasien "
                    + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rkm_medis=? order by reg_periksa.tgl_registrasi desc ", TNoRM.getText()));
            param.put("rike", Sequel.cariIsi("select if(count(kamar_inap.no_rawat)>1,(count(kamar_inap.no_rawat)-1),'') from reg_periksa inner join pasien inner join kamar_inap "
                    + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat where reg_periksa.no_rkm_medis=? ", TNoRM.getText()));
            param.put("rjke", Sequel.cariIsi("select if(count(reg_periksa.no_rawat)>1,(count(reg_periksa.no_rawat)-1),'') from reg_periksa inner join pasien "
                    + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rkm_medis=? ", TNoRM.getText()));
            param.put("riruang", Sequel.cariIsi("select if(count(kamar_inap.no_rawat)>1,kamar_inap.kd_kamar,'') from reg_periksa inner join pasien inner join kamar_inap "
                    + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat where reg_periksa.no_rkm_medis=? order by kamar_inap.tgl_masuk desc limit 1", TNoRM.getText()));
            param.put("rjruang", Sequel.cariIsi("select if(count(reg_periksa.no_rawat)>1,kd_poli,'') from reg_periksa inner join pasien "
                    + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rkm_medis=? order by reg_periksa.tgl_registrasi desc ", TNoRM.getText()));
            param.put("chkruang", Sequel.cariIsi("select if(count(kamar_inap.no_rawat)>1,'V','') from reg_periksa inner join pasien inner join kamar_inap "
                    + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat where reg_periksa.no_rkm_medis=? order by kamar_inap.tgl_masuk desc limit 1", TNoRM.getText()));
            param.put("chkbangsal", Sequel.cariIsi("select if(count(reg_periksa.no_rawat)>1,'V','') from reg_periksa inner join pasien "
                    + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rkm_medis=? order by reg_periksa.tgl_registrasi desc ", TNoRM.getText()));
            param.put("chkkelri", Sequel.cariIsi("select if(count(kamar_inap.no_rawat)>1,'V','') from reg_periksa inner join pasien inner join kamar_inap "
                    + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat where reg_periksa.no_rkm_medis=? order by kamar_inap.tgl_masuk desc limit 1", TNoRM.getText()));
            param.put("chkkelrj", Sequel.cariIsi("select if(count(reg_periksa.no_rawat)>1,'V','') from reg_periksa inner join pasien "
                    + "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rkm_medis=? order by reg_periksa.tgl_registrasi desc ", TNoRM.getText()));
            param.put("petugas", Sequel.cariIsi("select nama from petugas where nip=?", var.getkode()));

            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptRM2D.jrxml", "report", "::[ Lembar Assasmen ]::",
                    "select current_date() as tanggal, current_time() as jam", param);
        }
    }//GEN-LAST:event_MnRM2DActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        if ((!(TOut.getText().trim().length() > 0)) && (var.getstatus() == true)) {
            WindowInputKamar.setVisible(true);
        }
    }//GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void MnInputResepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnInputResepActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");

                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?", Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                        if (bangsal.equals("")) {
                            if (Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")) {
                                var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                            } else {
                                var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                            }
                        } else {
                            var.setkdbangsal(bangsal);
                        }
                        billing.reseppulang.inputresep.isCek();
                        billing.reseppulang.inputresep.setNoRm(rs2.getString("no_rawat2"), "-", Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat=?", norawat.getText()),
                                Sequel.cariIsi("select jam_reg from reg_periksa where no_rawat=?", norawat.getText()));
                        billing.reseppulang.inputresep.tampil();
                        billing.reseppulang.inputresep.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                        billing.reseppulang.inputresep.setLocationRelativeTo(internalFrame1);
                        billing.reseppulang.inputresep.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?", Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
            if (bangsal.equals("")) {
                if (Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")) {
                    var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                } else {
                    var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                }
            } else {
                var.setkdbangsal(bangsal);
            }
            billing.reseppulang.inputresep.isCek();
            billing.reseppulang.inputresep.setNoRm(norawat.getText(), "-", Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat=?", norawat.getText()),
                    Sequel.cariIsi("select jam_reg from reg_periksa where no_rawat=?", norawat.getText()));
            billing.reseppulang.inputresep.tampil();
            billing.reseppulang.inputresep.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            billing.reseppulang.inputresep.setLocationRelativeTo(internalFrame1);
            billing.reseppulang.inputresep.setVisible(true);
        }
    }//GEN-LAST:event_MnInputResepActionPerformed

    private void MnPeriksaRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaRadiologiActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        billing.periksarad.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                        billing.periksarad.setLocationRelativeTo(internalFrame1);
                        billing.periksarad.emptTeks();
                        billing.periksarad.setNoRm(rs2.getString("no_rawat2"), "Ranap");
                        billing.periksarad.tampil();
                        billing.periksarad.isCek();
                        billing.periksarad.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            billing.periksarad.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            billing.periksarad.setLocationRelativeTo(internalFrame1);
            billing.periksarad.emptTeks();
            billing.periksarad.setNoRm(norawat.getText(), "Ranap");
            billing.periksarad.tampil();
            billing.periksarad.isCek();
            billing.periksarad.setVisible(true);
        }
    }//GEN-LAST:event_MnPeriksaRadiologiActionPerformed

    private void WindowInputKamarWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_WindowInputKamarWindowActivated
        var.setstatus(false);
    }//GEN-LAST:event_WindowInputKamarWindowActivated

    private void WindowPindahKamarWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_WindowPindahKamarWindowActivated
        var.setstatus(false);
    }//GEN-LAST:event_WindowPindahKamarWindowActivated

    private void MnTilikBedahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnTilikBedahActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        Map<String, Object> param = new HashMap<>();
                        param.put("namars", var.getnamars());
                        param.put("alamatrs", var.getalamatrs());
                        param.put("kotars", var.getkabupatenrs());
                        param.put("propinsirs", var.getpropinsirs());
                        param.put("kontakrs", var.getkontakrs());
                        param.put("emailrs", var.getemailrs());
                        param.put("logo", Sequel.cariGambar("select logo from setting"));
                        param.put("nama", rs2.getString("nm_pasien"));
                        param.put("jkel", Sequel.cariIsi("select if(pasien.jk='L','Laki-Laki','Perempuan') from pasien where pasien.no_rkm_medis=?", rs2.getString("no_rkm_medis")));
                        param.put("lahir", Sequel.cariIsi("select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=?", rs2.getString("no_rkm_medis")));
                        param.put("norm", rs2.getString("no_rkm_medis"));
                        param.put("ruang", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString().replaceAll("  ", " "));
                        param.put("kelas", Sequel.cariIsi("select kamar.kelas from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where kamar.kd_kamar=? ", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString()));

                        Valid.MyReport("rptTilikBedah.jrxml", "report", "::[ Lembar Tilik Bedah ]::",
                                "select current_date() as tanggal, current_time() as jam", param);
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            param.put("nama", TPasien.getText());
            param.put("jkel", Sequel.cariIsi("select if(pasien.jk='L','Laki-Laki','Perempuan') from pasien where pasien.no_rkm_medis=?", TNoRM.getText()));
            param.put("lahir", Sequel.cariIsi("select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=?", TNoRM.getText()));
            param.put("norm", TNoRM.getText());
            param.put("ruang", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString().replaceAll("  ", " "));
            param.put("kelas", Sequel.cariIsi("select kamar.kelas from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where kamar.kd_kamar=? ", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString()));

            Valid.MyReport("rptTilikBedah.jrxml", "report", "::[ Lembar Tilik Bedah ]::",
                    "select current_date() as tanggal, current_time() as jam", param);
        }
    }//GEN-LAST:event_MnTilikBedahActionPerformed

    private void MnUpdateHariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUpdateHariActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (R1.isSelected() == false) {
            JOptionPane.showMessageDialog(rootPane, "Tampilkan data yang belum pulang terlebih dahulu");
        } else {
            updateHari();
        }
    }//GEN-LAST:event_MnUpdateHariActionPerformed

    private void MnAsuhanGiziActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnAsuhanGiziActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");

                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        Map<String, Object> param = new HashMap<>();
                        param.put("nama", rs2.getString("nm_pasien"));
                        param.put("jkel", Sequel.cariIsi("select if(pasien.jk='L','Laki-Laki','Perempuan') from pasien where pasien.no_rkm_medis=?", rs2.getString("no_rkm_medis")));
                        param.put("lahir", Sequel.cariIsi("select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=?", rs2.getString("no_rkm_medis")));
                        param.put("norm", rs2.getString("no_rkm_medis"));
                        param.put("ruang", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString().replaceAll("  ", " "));
                        param.put("namars", var.getnamars());
                        param.put("alamatrs", var.getalamatrs());
                        param.put("kotars", var.getkabupatenrs());
                        param.put("propinsirs", var.getpropinsirs());
                        param.put("kontakrs", var.getkontakrs());
                        param.put("emailrs", var.getemailrs());
                        param.put("logo", Sequel.cariGambar("select logo from setting"));
                        param.put("kelas", Sequel.cariIsi("select kamar.kelas from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where kamar.kd_kamar=? ", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString()));
                        Valid.MyReport("rptAssesmentGizi.jrxml", "report", "::[ Lembar Asuhan Gizi ]::",
                                "select current_date() as tanggal, current_time() as jam", param);
                        this.setCursor(Cursor.getDefaultCursor());
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("nama", TPasien.getText());
            param.put("jkel", Sequel.cariIsi("select if(pasien.jk='L','Laki-Laki','Perempuan') from pasien where pasien.no_rkm_medis=?", TNoRM.getText()));
            param.put("lahir", Sequel.cariIsi("select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=?", TNoRM.getText()));
            param.put("norm", TNoRM.getText());
            param.put("ruang", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString().replaceAll("  ", " "));
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            param.put("kelas", Sequel.cariIsi("select kamar.kelas from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where kamar.kd_kamar=? ", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 19).toString()));
            Valid.MyReport("rptAssesmentGizi.jrxml", "report", "::[ Lembar Asuhan Gizi ]::",
                    "select current_date() as tanggal, current_time() as jam", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnAsuhanGiziActionPerformed

    private void btnDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiagnosaActionPerformed
        if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbKamIn.requestFocus();
        } else {
            var.setform("DlgKamarInap");
            diagnosa.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            diagnosa.setLocationRelativeTo(internalFrame1);
            diagnosa.isCek();
            diagnosa.setNoRm(norawat.getText(), Valid.SetTgl2(Sequel.cariIsi("SELECT tgl_registrasi FROM reg_periksa WHERE no_rawat = ?",norawat.getText())), DTPCari2.getDate(), "Ranap",1);
            diagnosa.panelDiagnosa1.tampil();
            diagnosa.setVisible(true);
        }
    }//GEN-LAST:event_btnDiagnosaActionPerformed

    private void btnDiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDiagnosaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDiagnosaKeyPressed

    private void BtnCloseGabungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseGabungActionPerformed
        NoRawatGabung.setText("");
        NoRmBayi.setText("");
        NmBayi.setText("");
        WindowRanapGabung.dispose();
    }//GEN-LAST:event_BtnCloseGabungActionPerformed

    private void BtnSimpanGabungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanGabungActionPerformed
        if (norawat.getText().trim().equals("")) {
            Valid.textKosong(NoRmBayi, "Pasien");
        } else {
            try {
                psibu = koneksi.prepareStatement("select no_reg,tgl_registrasi,jam_reg,kd_dokter,no_rkm_medis,kd_poli,p_jawab,"
                        + "almt_pj,hubunganpj,biaya_reg,stts,stts_daftar,status_lanjut,kd_pj from reg_periksa where no_rawat=?");
                try {
                    psibu.setString(1, norawat.getText());
                    rs = psibu.executeQuery();
                    if (rs.next()) {
                        pscariumur = koneksi.prepareStatement(
                                "select TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()) as tahun, "
                                + "(TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12)) as bulan, "
                                + "TIMESTAMPDIFF(DAY, DATE_ADD(DATE_ADD(tgl_lahir,INTERVAL TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()) YEAR), INTERVAL TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12) MONTH), CURDATE()) as hari "
                                + "from pasien where no_rkm_medis=?");
                        try {
                            pscariumur.setString(1, NoRmBayi.getText());
                            rs2 = pscariumur.executeQuery();
                            if (rs2.next()) {
                                umur = "0";
                                sttsumur = "Th";
                                if (rs2.getInt("tahun") > 0) {
                                    umur = rs2.getString("tahun");
                                    sttsumur = "Th";
                                } else if (rs2.getInt("tahun") == 0) {
                                    if (rs2.getInt("bulan") > 0) {
                                        umur = rs2.getString("bulan");
                                        sttsumur = "Bl";
                                    } else if (rs2.getInt("bulan") == 0) {
                                        umur = rs2.getString("hari");
                                        sttsumur = "Hr";
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi Umur : " + e);
                        } finally {
                            if (rs2 != null) {
                                rs2.close();
                            }
                            if (pscariumur != null) {
                                pscariumur.close();
                            }
                        }
                        Valid.autoNomer3("select (ifnull(MAX(CONVERT(RIGHT(no_rawat,6),signed)),0)+1) from reg_periksa where tgl_registrasi='" + rs.getString("tgl_registrasi") + "' ", dateformat.format(rs.getDate("tgl_registrasi")) + "/", 6, NoRawatGabung);
                        if (Sequel.menyimpantf2("reg_periksa", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Reg Periksa", 19,
                                new String[]{rs.getString("no_reg"), NoRawatGabung.getText(), rs.getString("tgl_registrasi"), rs.getString("jam_reg"),
                                    rs.getString("kd_dokter"), NoRmBayi.getText(), rs.getString("kd_poli"), rs.getString("p_jawab"),
                                    rs.getString("almt_pj"), rs.getString("hubunganpj"), rs.getString("biaya_reg"), "Belum", "Baru", "Ranap", rs.getString("kd_pj"), umur, sttsumur, "Sudah Bayar", "Baru"}) == true) {
                            Sequel.menyimpan("ranap_gabung", "?,?", "Data Ranap Gabung", 2, new String[]{
                                norawat.getText(), NoRawatGabung.getText()
                            });
                            if (rs.getString("kd_poli").equals("IGD01")) {
                                Sequel.menyimpan("kategori_pasien_igd", "?,?", "Kategori", 2, new String[]{
                                    NoRawatGabung.getText(), "KEBIDANAN"
                                });
                            }
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (psibu != null) {
                        psibu.close();
                    }
                }

                tampil();
            } catch (Exception e) {
                System.out.println(e);
            }
            NoRawatGabung.setText("");
            NoRmBayi.setText("");
            NmBayi.setText("");
            WindowRanapGabung.dispose();
        }
    }//GEN-LAST:event_BtnSimpanGabungActionPerformed

    private void NoRmBayiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRmBayiKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", NmBayi, NoRmBayi.getText());
        }
    }//GEN-LAST:event_NoRmBayiKeyPressed

    private void btnPasienRanapGabungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasienRanapGabungActionPerformed
        var.setform("DlgKamarInap");
        reg.pasien.emptTeks();
        reg.pasien.isCek();
        reg.pasien.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        reg.pasien.setLocationRelativeTo(internalFrame1);
        reg.pasien.setVisible(true);
    }//GEN-LAST:event_btnPasienRanapGabungActionPerformed

    private void BtnHapusGabungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusGabungActionPerformed
        Sequel.meghapus("ranap_gabung", "no_rawat", norawat.getText());
        NoRawatGabung.setText("");
        NoRmBayi.setText("");
        NmBayi.setText("");
        tampil();
    }//GEN-LAST:event_BtnHapusGabungActionPerformed

    private void MnRanapGabungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRanapGabungActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbKamIn.requestFocus();
        } else {
            NoRawatGabung.setText("");
            Sequel.cariIsi("select no_rawat2 from ranap_gabung where no_rawat=?", NoRawatGabung, norawat.getText());
            Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=?", NoRmBayi, NoRawatGabung.getText());
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", NmBayi, NoRmBayi.getText());
            WindowRanapGabung.setLocationRelativeTo(internalFrame1);
            WindowRanapGabung.setVisible(true);
        }
    }//GEN-LAST:event_MnRanapGabungActionPerformed

    private void MnStatusBelumPulangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnStatusBelumPulangActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbKamIn.requestFocus();
        } else {
            if (Sequel.cariRegistrasi(norawat.getText()) > 0) {
                JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                TCari.requestFocus();
            } else {
                Sequel.mengedit("kamar_inap", "no_rawat='" + norawat.getText() + "' and kd_kamar='" + kdkamar.getText() + "' and tgl_masuk='" + TIn.getText() + "' and jam_masuk='" + JamMasuk.getText() + "'", "stts_pulang='-',tgl_keluar='0000-00-00',jam_keluar='00:00:00'");
                tampil();
            }
        }
    }//GEN-LAST:event_MnStatusBelumPulangActionPerformed

    private void MnDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDiagnosaActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");

                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        var.setform("DlgKamarInap");
                        diagnosa.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                        diagnosa.setLocationRelativeTo(internalFrame1);
                        diagnosa.isCek();
                        try {
                            date = new SimpleDateFormat("yyyy-MM-dd").parse(TIn.getText());
                        } catch (Exception e) {
                            date = DTPCari2.getDate();
                        }
                        diagnosa.setNoRm(rs2.getString("no_rawat2"), date, DTPCari2.getDate(), "Ranap");
                        diagnosa.panelDiagnosa1.tampil();
                        diagnosa.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            var.setform("DlgKamarInap");
            diagnosa.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            diagnosa.setLocationRelativeTo(internalFrame1);
            diagnosa.isCek();
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(TIn.getText());
            } catch (Exception e) {
                date = DTPCari2.getDate();
            }
            diagnosa.setNoRm(norawat.getText(), date, DTPCari2.getDate(), "Ranap");
            diagnosa.panelDiagnosa1.tampil();
            diagnosa.setVisible(true);
        }
    }//GEN-LAST:event_MnDiagnosaActionPerformed

    private void DTPCari4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DTPCari4ItemStateChanged
        R3.setSelected(true);
    }//GEN-LAST:event_DTPCari4ItemStateChanged

    private void DTPCari4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPCari4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DTPCari4KeyPressed

    private void MnDPJPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDPJPActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");

                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        DlgDpjp dpjp = new DlgDpjp(null, false);
                        dpjp.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                        dpjp.setLocationRelativeTo(internalFrame1);
                        dpjp.isCek();
                        try {
//                            date = new SimpleDateFormat("yyyy-MM-dd").parse(TIn.getText());
                            date = new SimpleDateFormat("yyyy-MM-dd").parse(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().substring(0,10).replace("/", "-"));
                        } catch (Exception e) {
                            date = DTPCari2.getDate();
                        }

                        dpjp.setNoRm(rs2.getString("no_rawat2"), date, DTPCari2.getDate(), TIn.getText());
                        dpjp.tampil();
                        dpjp.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            DlgDpjp dpjp = new DlgDpjp(null, false);
            dpjp.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            dpjp.setLocationRelativeTo(internalFrame1);
            dpjp.isCek();
            try {
//                date = new SimpleDateFormat("yyyy-MM-dd").parse(TIn.getText());
                date = new SimpleDateFormat("yyyy-MM-dd").parse(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().substring(0,10).replace("/", "-"));
            } catch (Exception e) {
                date = DTPCari2.getDate();
            }

            dpjp.setNoRm(norawat.getText(), date, DTPCari2.getDate(), TIn.getText());
            dpjp.tampil();
            dpjp.setVisible(true);
            //this.dispose();
        }
    }//GEN-LAST:event_MnDPJPActionPerformed

    private void MnPenggunaanKamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenggunaanKamarActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        InformasiAnalisaKamin analisakamin = new InformasiAnalisaKamin(null, false);
        analisakamin.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        analisakamin.setLocationRelativeTo(internalFrame1);
        analisakamin.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnPenggunaanKamarActionPerformed

    private void MnReturJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnReturJualActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");

                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        var.setform("DlgKamarInap");
                        DlgReturJual returjual = new DlgReturJual(null, false);
                        returjual.emptTeks();
                        returjual.isCek();
                        returjual.setPasien(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 1).toString(), rs2.getString("no_rawat2"));
                        returjual.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                        returjual.setLocationRelativeTo(internalFrame1);
                        returjual.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            var.setform("DlgKamarInap");
            DlgReturJual returjual = new DlgReturJual(null, false);
            returjual.emptTeks();
            returjual.isCek();
            returjual.setPasien(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 1).toString(), norawat.getText());
            returjual.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            returjual.setLocationRelativeTo(internalFrame1);
            returjual.setVisible(true);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnReturJualActionPerformed

    private void MnRincianObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRincianObatActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");

                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        Map<String, Object> param = new HashMap<>();
                        param.put("namapasien", rs2.getString("nm_pasien"));
                        param.put("norm", rs2.getString("no_rkm_medis"));
                        param.put("nokartu", rs2.getString("no_peserta"));
                        param.put("umur", rs2.getString("umur"));
                        param.put("alamatpasien", rs2.getString("alamat"));
                        param.put("ruang", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString().replaceAll("  ", " "));
                        param.put("namars", var.getnamars());
                        param.put("alamatrs", var.getalamatrs());
                        param.put("kotars", var.getkabupatenrs());
                        param.put("propinsirs", var.getpropinsirs());
                        param.put("kontakrs", var.getkontakrs());
                        param.put("emailrs", var.getemailrs());
                        param.put("logo", Sequel.cariGambar("select logo from setting"));
                        Valid.MyReport("rptRincianRiwayatObat.jrxml", "report", "::[ Rincian Penggunaan Obat ]::",
                                "select * from temporary", param);
                        this.setCursor(Cursor.getDefaultCursor());
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namapasien", TPasien.getText());
            param.put("norm", TNoRM.getText());
            param.put("nokartu", Sequel.cariIsi("select no_peserta from pasien where no_rkm_medis=?", TNoRM.getText()));
            param.put("umur", Sequel.cariIsi("select umur from pasien where no_rkm_medis=?", TNoRM.getText()));
            param.put("alamatpasien", Sequel.cariIsi("select concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) from pasien where no_rkm_medis=?", TNoRM.getText()));
            param.put("ruang", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString().replaceAll("  ", " "));
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptRincianRiwayatObat.jrxml", "report", "::[ Rincian Penggunaan Obat ]::",
                    "select * from temporary", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnRincianObatActionPerformed

    private void MnDPJPRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDPJPRanapActionPerformed
        row = tbKamIn.getRowCount();
        for (i = 0; i < row; i++) {
            try {
                psdpjp = koneksi.prepareStatement("select dokter.nm_dokter from dpjp_ranap inner join dokter "
                        + "on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat=? ");
                dokterranap = "";
                try {
                    psdpjp.setString(1, tbKamIn.getValueAt(i, 0).toString());
                    rs = psdpjp.executeQuery();
                    while (rs.next()) {
                        dokterranap = rs.getString("nm_dokter") + ", " + dokterranap;
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : " + e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (psdpjp != null) {
                        psdpjp.close();
                    }
                }
                tbKamIn.setValueAt(dokterranap, i, 19);
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        }
    }//GEN-LAST:event_MnDPJPRanapActionPerformed

    private void MnSEPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSEPActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        var.setform("DlgKamarInap");
                        BPJSDataSEP dlgki = new BPJSDataSEP(null, false);
                        dlgki.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                        dlgki.setLocationRelativeTo(internalFrame1);
                        dlgki.isCek();
                        dlgki.setNoRm(rs2.getString("no_rawat2"), Valid.SetTgl2(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString()), "1. Ranap", "", "");
                        dlgki.setVisible(true);
                        if ((Sequel.cariInteger("select count(no_rawat) from bridging_sep where no_rawat=?", norawat.getText()) > 0)) {
                            dlgki.getTab(norawat.getText());
                        }
                        this.setCursor(Cursor.getDefaultCursor());
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            var.setform("DlgKamarInap");
            BPJSDataSEP dlgki = new BPJSDataSEP(null, false);
            dlgki.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            dlgki.setLocationRelativeTo(internalFrame1);
            dlgki.isCek();
            dlgki.setNoRm(norawat.getText(), Valid.SetTgl2(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString()), "1. Ranap", "", "");
            dlgki.setVisible(true);
            if ((Sequel.cariInteger("select count(no_rawat) from bridging_sep where no_rawat=?", norawat.getText()) > 0)) {
                dlgki.getTab(norawat.getText());
            }
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnSEPActionPerformed

    private void ppRiwayatBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppRiwayatBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data pasien sudah habis...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu data kamar inap pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgResumePerawatan resume = new DlgResumePerawatan(null, true);
            resume.setNoRm(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 1).toString(), tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 2).toString());
            resume.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppRiwayatBtnPrintActionPerformed

    private void ppCatatanPasienBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppCatatanPasienBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data pasien sudah habis...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu data kamar inap pada table...!!!");
            TCari.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgCatatan catatan = new DlgCatatan(null, true);
            catatan.setNoRm(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 1).toString());
            catatan.setSize(720, 330);
            catatan.setLocationRelativeTo(internalFrame1);
            catatan.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppCatatanPasienBtnPrintActionPerformed

    private void ppDataHAIsBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppDataHAIsBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        DlgDataHAIs hais = new DlgDataHAIs(null, false);
                        hais.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                        hais.setLocationRelativeTo(internalFrame1);
                        hais.emptTeks();
                        hais.isCek();
                        hais.setNoRm(rs2.getString("no_rawat2"), DTPCari1.getDate(), DTPCari2.getDate());
                        hais.tampil();
                        hais.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            DlgDataHAIs hais = new DlgDataHAIs(null, false);
            hais.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            hais.setLocationRelativeTo(internalFrame1);
            hais.emptTeks();
            hais.isCek();
            hais.setNoRm(norawat.getText(), DTPCari1.getDate(), DTPCari2.getDate());
            hais.tampil();
            hais.setVisible(true);
            //this.dispose();
        }
    }//GEN-LAST:event_ppDataHAIsBtnPrintActionPerformed

    private void btnPasienRanapGabung1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasienRanapGabung1ActionPerformed
        var.setform("DlgKamarInap");
        ikb.emptTeks();
        ikb.isCek();
        ikb.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        ikb.setLocationRelativeTo(internalFrame1);
        ikb.setVisible(true);
    }//GEN-LAST:event_btnPasienRanapGabung1ActionPerformed

    private void MnPengantarPulangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPengantarPulangActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");

                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        Map<String, Object> param = new HashMap<>();
                        param.put("nama", rs2.getString("nm_pasien"));
                        param.put("jkel", Sequel.cariIsi("select if(pasien.jk='L','Laki-Laki','Perempuan') from pasien where pasien.no_rkm_medis=?", rs2.getString("no_rkm_medis")));
                        param.put("lahir", Sequel.cariIsi("select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=?", rs2.getString("no_rkm_medis")));
                        param.put("norm", rs2.getString("no_rkm_medis"));
                        param.put("namars", var.getnamars());
                        param.put("alamatrs", var.getalamatrs());
                        param.put("kotars", var.getkabupatenrs());
                        param.put("propinsirs", var.getpropinsirs());
                        param.put("kontakrs", var.getkontakrs());
                        param.put("tanggalmasuk", Sequel.cariIsi("select tgl_masuk from kamar_inap where no_rawat=? order by tgl_masuk asc limit 1", tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString()));
                        param.put("kamar", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString());
                        param.put("carabayar", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 6).toString());
                        param.put("emailrs", var.getemailrs());
                        param.put("logo", Sequel.cariGambar("select logo from setting"));
                        Valid.MyReport("rptSuratPengantarPulang.jrxml", param, "::[ Surat Pengantar Pulang ]::");
                        this.setCursor(Cursor.getDefaultCursor());
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("nama", TPasien.getText());
            param.put("jkel", Sequel.cariIsi("select if(pasien.jk='L','Laki-Laki','Perempuan') from pasien where pasien.no_rkm_medis=?", TNoRM.getText()));
            param.put("lahir", Sequel.cariIsi("select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=?", TNoRM.getText()));
            param.put("norm", TNoRM.getText());
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("tanggalmasuk", Sequel.cariIsi("select tgl_masuk from kamar_inap where no_rawat=? order by tgl_masuk asc limit 1", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString()));
            param.put("kamar", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString());
            param.put("carabayar", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 6).toString());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptSuratPengantarPulang.jrxml", param, "::[ Surat Pengantar Pulang ]::");
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnPengantarPulangActionPerformed

    private void MnGabungkanRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGabungkanRanapActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data kamar inap pasien sudah habis...!!!!");
            BtnIn.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu data pasien yang mau digabung...!!!");
            tbKamIn.requestFocus();
        } else {
            gabungkan = "gabung";
            norawatgabung = norawat.getText();
            kamaryangdigabung = kdkamar.getText();
            JOptionPane.showMessageDialog(null, "Silahkan pilih No.Rawat Ibu/No.Rawat Tujuan");
        }

    }//GEN-LAST:event_MnGabungkanRanapActionPerformed

    private void ppBerkasDigitalBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBerkasDigitalBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        DlgBerkasRawat berkas = new DlgBerkasRawat(null, true);
                        berkas.setJudul("::[ Berkas Digital Perawatan ]::", "berkasrawat/pages");
                        try {
                            berkas.loadURL("http://" + koneksiDB.HOST() + ":" + prop.getProperty("PORTWEB") + "/" + prop.getProperty("HYBRIDWEB") + "/" + "berkasrawat/login2.php?act=login&usere=admin&passwordte=akusayangsamakamu&no_rawat=" + rs2.getString("no_rawat2"));
                        } catch (Exception ex) {
                            System.out.println("Notifikasi : " + ex);
                        }

                        berkas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                        berkas.setLocationRelativeTo(internalFrame1);
                        berkas.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgBerkasRawat berkas = new DlgBerkasRawat(null, true);
            berkas.setJudul("::[ Berkas Digital Perawatan ]::", "berkasrawat/pages");
            try {
                berkas.loadURL("http://" + koneksiDB.HOST() + ":" + prop.getProperty("PORTWEB") + "/" + prop.getProperty("HYBRIDWEB") + "/" + "berkasrawat/login2.php?act=login&usere=admin&passwordte=akusayangsamakamu&no_rawat=" + norawat.getText());
            } catch (Exception ex) {
                System.out.println("Notifikasi : " + ex);
            }

            berkas.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            berkas.setLocationRelativeTo(internalFrame1);
            berkas.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppBerkasDigitalBtnPrintActionPerformed

    private void MnRawatJalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRawatJalanActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        DlgRawatJalan dlgrwjl = new DlgRawatJalan(null, false);
                        dlgrwjl.isCek();
                        dlgrwjl.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                        dlgrwjl.setLocationRelativeTo(internalFrame1);
                        dlgrwjl.SetPoli(Sequel.cariIsi("select kd_poli from reg_periksa where no_rawat=?", rs2.getString("no_rawat2")));
                        dlgrwjl.SetPj(Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?", rs2.getString("no_rawat2")));
                        dlgrwjl.setNoRm(rs2.getString("no_rawat2"), DTPCari1.getDate(), new Date());
                        dlgrwjl.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            DlgRawatJalan dlgrwjl = new DlgRawatJalan(null, false);
            dlgrwjl.isCek();
            dlgrwjl.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            dlgrwjl.setLocationRelativeTo(internalFrame1);
            dlgrwjl.SetPoli(Sequel.cariIsi("select kd_poli from reg_periksa where no_rawat=?", norawat.getText()));
            dlgrwjl.SetPj(Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?", norawat.getText()));
            dlgrwjl.setNoRm(norawat.getText(), DTPCari1.getDate(), new Date());
            dlgrwjl.setVisible(true);
        }

    }//GEN-LAST:event_MnRawatJalanActionPerformed

    private void MnGelang3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang3ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data kamar inap pasien sudah habis...!!!!");
            BtnIn.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu data pada table...!!!");
            tbKamIn.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("tanggal", TIn.getText());
            param.put("kamar", kdkamar.getText() + " " + TBangsal.getText());
            param.put("dpjp", Sequel.cariIsi("select dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat=? ", TNoRw1.getText()));
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptGelangPasienAnak.jrxml", "report", "::[ Gelang Pasien ]::", "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "
                    + "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"
                    + "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"
                    + "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"
                    + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "
                    + "inner join kelurahan inner join kecamatan inner join kabupaten "
                    + "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "
                    + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='" + TNoRM.getText() + "' ", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang3ActionPerformed

    private void MnGelang4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang4ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data kamar inap pasien sudah habis...!!!!");
            BtnIn.requestFocus();
        } else if (norawat.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu data pada table...!!!");
            tbKamIn.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("tanggal", TIn.getText());
            param.put("kamar", kdkamar.getText() + " " + TBangsal.getText());
            param.put("dpjp", Sequel.cariIsi("select dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat=? ", TNoRw1.getText()));
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptGelangPasienDewasa.jrxml", "report", "::[ Gelang Pasien ]::", "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "
                    + "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"
                    + "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"
                    + "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"
                    + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "
                    + "inner join kelurahan inner join kecamatan inner join kabupaten "
                    + "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "
                    + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='" + TNoRM.getText() + "' ", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang4ActionPerformed

    private void MnCekKepesertaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCekKepesertaanActionPerformed
        if (!TNoRM1.getText().equals("")) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            BPJSPeserta form = new BPJSPeserta(null, true);
            form.tampil(Sequel.cariIsi("select no_peserta from pasien where no_rkm_medis=?", TNoRM.getText()));
            form.setSize(640, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        } else {
            JOptionPane.showMessageDialog(null, "Maaf, Nomor kepesertaan kosong...!!!");
        }

    }//GEN-LAST:event_MnCekKepesertaanActionPerformed

    private void MnCekNIKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCekNIKActionPerformed
        if (!TNoRM1.getText().equals("")) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            BPJSNik form = new BPJSNik(null, true);
            form.tampil(Sequel.cariIsi("select no_ktp from pasien where no_rkm_medis=?", TNoRM.getText()));
            form.setSize(640, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        } else {
            JOptionPane.showMessageDialog(null, "Maaf, NIK KTP kosong...!!!");
        }
    }//GEN-LAST:event_MnCekNIKActionPerformed

    private void MnFormulirPenerimaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnFormulirPenerimaanActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");

                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        Map<String, Object> param = new HashMap<>();
                        param.put("nama", rs2.getString("nm_pasien"));
                        param.put("jkel", Sequel.cariIsi("select if(pasien.jk='L','Laki-Laki','Perempuan') from pasien where pasien.no_rkm_medis=?", rs2.getString("no_rkm_medis")));
                        param.put("lahir", Sequel.cariIsi("select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=?", rs2.getString("no_rkm_medis")));
                        param.put("norm", rs2.getString("no_rkm_medis"));
                        param.put("namars", var.getnamars());
                        param.put("alamatrs", var.getalamatrs());
                        param.put("kotars", var.getkabupatenrs());
                        param.put("propinsirs", var.getpropinsirs());
                        param.put("kontakrs", var.getkontakrs());
                        param.put("tanggalmasuk", Sequel.cariIsi("select tgl_masuk from kamar_inap where no_rawat=? order by tgl_masuk asc limit 1", tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString()));
                        param.put("kamar", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString());
                        param.put("norawat", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString());
                        param.put("carabayar", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 6).toString());
                        param.put("emailrs", var.getemailrs());
                        param.put("logo", Sequel.cariGambar("select logo from setting"));
                        Valid.MyReport("rptFormulirPenerimaanPasien.jrxml", param, "::[ Formulir Penerimaan Pasien ]::");
                        this.setCursor(Cursor.getDefaultCursor());
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("nama", TPasien.getText());
            param.put("jkel", Sequel.cariIsi("select if(pasien.jk='L','Laki-Laki','Perempuan') from pasien where pasien.no_rkm_medis=?", TNoRM.getText()));
            param.put("lahir", Sequel.cariIsi("select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=?", TNoRM.getText()));
            param.put("norm", TNoRM.getText());
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("tanggalmasuk", Sequel.cariIsi("select tgl_masuk from kamar_inap where no_rawat=? order by tgl_masuk asc limit 1", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString()));
            param.put("kamar", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString());
            param.put("norawat", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString());
            param.put("carabayar", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 6).toString());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptFormulirPenerimaanPasien.jrxml", param, "::[ Formulir Penerimaan Pasien ]::");
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnFormulirPenerimaanActionPerformed

    private void ppIKPBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppIKPBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        DlgDataInsidenKeselamatan aplikasi = new DlgDataInsidenKeselamatan(null, false);
                        aplikasi.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                        aplikasi.setLocationRelativeTo(internalFrame1);
                        aplikasi.isCek();
                        aplikasi.setNoRm(rs2.getString("no_rawat2"), DTPCari1.getDate(), DTPCari2.getDate(), tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString());
                        aplikasi.tampil();
                        aplikasi.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgDataInsidenKeselamatan aplikasi = new DlgDataInsidenKeselamatan(null, false);
            aplikasi.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            aplikasi.setLocationRelativeTo(internalFrame1);
            aplikasi.isCek();
            aplikasi.setNoRm(norawat.getText(), DTPCari1.getDate(), DTPCari2.getDate(), tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString());
            aplikasi.tampil();
            aplikasi.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppIKPBtnPrintActionPerformed

    private void MnJadwalOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnJadwalOperasiActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        DlgBookingOperasi form = new DlgBookingOperasi(null, false);
                        form.isCek();
                        form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                        form.setLocationRelativeTo(internalFrame1);
                        form.setNoRm(rs2.getString("no_rawat2"), rs2.getString("no_rkm_medis"), rs2.getString("nm_pasien"), tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString(), "Ranap");
                        form.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            DlgBookingOperasi form = new DlgBookingOperasi(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.setNoRm(norawat.getText(), TNoRM.getText(), TPasien.getText(), tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString(), "Ranap");
            form.setVisible(true);
        }

    }//GEN-LAST:event_MnJadwalOperasiActionPerformed

    private void MnLabelTrackerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLabelTrackerActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        Map<String, Object> param = new HashMap<>();
                        param.put("namars", var.getnamars());
                        param.put("alamatrs", var.getalamatrs());
                        param.put("kotars", var.getkabupatenrs());
                        param.put("propinsirs", var.getpropinsirs());
                        param.put("kontakrs", var.getkontakrs());
                        param.put("emailrs", var.getemailrs());
                        param.put("no_rawat", rs2.getString("no_rawat2"));
                        param.put("logo", Sequel.cariGambar("select logo from setting"));
                        Valid.MyReport("rptLabelTracker.jrxml", param, "::[ Label Tracker ]::");
                        this.setCursor(Cursor.getDefaultCursor());
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("no_rawat", norawat.getText());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptLabelTracker.jrxml", param, "::[ Label Tracker ]::");
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLabelTrackerActionPerformed

    private void MnBarcodeRM9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcodeRM9ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data pasien sudah habis...!!!!");
            TNoRM.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbKamIn.requestFocus();
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

    private void MnSKDPBPJSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSKDPBPJSActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        DlgSKDPBPJS form = new DlgSKDPBPJS(null, false);
                        form.isCek();
                        form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                        form.setLocationRelativeTo(internalFrame1);
                        form.emptTeks();
                        form.setNoRm(rs2.getString("no_rkm_medis"), rs2.getString("nm_pasien"), rs2.getString("no_rawat2"), "Ranap");
                        form.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            DlgSKDPBPJS form = new DlgSKDPBPJS(null, false);
            form.isCek();
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.emptTeks();
            form.setNoRm(TNoRM.getText(), TPasien.getText(), TNoRw1.getText(), "Ranap");
            form.setVisible(true);
            kd_pj = Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?", norawat.getText());            
            if ((kd_pj.equals("BPJ") || kd_pj.contains("A02")) && (Sequel.cariInteger("select count(no_rawat) from bridging_sep where no_rawat=?", TNoRw1.getText()) == 0)) {
                form.getTab(TNoRw1.getText());
            }
        }
    }//GEN-LAST:event_MnSKDPBPJSActionPerformed

    private void MnPermintaanLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPermintaanLabActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        DlgPermintaanLaboratorium dlgro = new DlgPermintaanLaboratorium(null, false);
                        dlgro.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                        dlgro.setLocationRelativeTo(internalFrame1);
                        dlgro.emptTeks();
                        dlgro.isCek();
                        dlgro.setNoRm(rs2.getString("no_rawat2"), "Ranap");
//                        dlgro.setDokterPerujuk(Sequel.cariIsi("select kd_dokter from dpjp_ranap where no_rawat = ?", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString()));
                        dlgro.setDokterPerujuk(Sequel.cariIsi("select kd_dokter from dpjp_ranap where no_rawat = ?", rs2.getString("no_rawat2")));
                        dlgro.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgPermintaanLaboratorium dlgro = new DlgPermintaanLaboratorium(null, false);
            dlgro.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            dlgro.setLocationRelativeTo(internalFrame1);
            dlgro.emptTeks();
            dlgro.isCek();
            dlgro.setNoRm(norawat.getText(), "Ranap");
            dlgro.setDokterPerujuk(Sequel.cariIsi("select kd_dokter from dpjp_ranap where no_rawat = ?", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString()));
            dlgro.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnPermintaanLabActionPerformed

    private void MnPermintaanRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPermintaanRadiologiActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        DlgPermintaanRadiologi dlgro = new DlgPermintaanRadiologi(null, false);
                        dlgro.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                        dlgro.setLocationRelativeTo(internalFrame1);
                        dlgro.emptTeks();
                        dlgro.isCek();
                        dlgro.setNoRm(rs2.getString("no_rawat2"), "Ranap");
                        dlgro.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgPermintaanRadiologi dlgro = new DlgPermintaanRadiologi(null, false);
            dlgro.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            dlgro.setLocationRelativeTo(internalFrame1);
            dlgro.emptTeks();
            dlgro.isCek();
            dlgro.setNoRm(norawat.getText(), "Ranap");
            dlgro.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnPermintaanRadiologiActionPerformed

    private void MnResepDOkterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnResepDOkterActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?", Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                        if (bangsal.equals("")) {
                            if (Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")) {
                                var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                            } else {
                                var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                            }
                        } else {
                            var.setkdbangsal(bangsal);
                        }
                        DlgPeresepanDokter resep = new DlgPeresepanDokter(null, false);
                        resep.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                        resep.setLocationRelativeTo(internalFrame1);
                        resep.setNoRm(rs2.getString("no_rawat2"), DTPCari1.getDate(), "ranap");
                        resep.isCek();
                        resep.tampilobat();
                        resep.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?", Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
            if (bangsal.equals("")) {
                if (Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")) {
                    var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=?", kdkamar.getText()));
                } else {
                    var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                }
            } else {
                var.setkdbangsal(bangsal);
            }
            DlgPeresepanDokter resep = new DlgPeresepanDokter(null, false);
            resep.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            resep.setLocationRelativeTo(internalFrame1);
            resep.setNoRm(norawat.getText(), DTPCari1.getDate(), "ranap");
            resep.isCek();
            resep.tampilobat();
            resep.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnResepDOkterActionPerformed

    private void MnRujukSisruteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRujukSisruteActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        var.setform("DlgKamarInap");
                        SisruteRujukanKeluar dlgki = new SisruteRujukanKeluar(null, false);
                        dlgki.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                        dlgki.setLocationRelativeTo(internalFrame1);
                        dlgki.isCek();
                        dlgki.setPasien2(rs2.getString("no_rawat2"));
                        dlgki.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            var.setform("DlgKamarInap");
            SisruteRujukanKeluar dlgki = new SisruteRujukanKeluar(null, false);
            dlgki.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            dlgki.setLocationRelativeTo(internalFrame1);
            dlgki.isCek();
            dlgki.setPasien2(norawat.getText());
            dlgki.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnRujukSisruteActionPerformed

    private void MnFormulirPenerimaan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnFormulirPenerimaan1ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");

                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        Map<String, Object> param = new HashMap<>();
                        param.put("nama", rs2.getString("nm_pasien"));
                        param.put("jkel", Sequel.cariIsi("select if(pasien.jk='L','Laki-Laki','Perempuan') from pasien where pasien.no_rkm_medis=?", rs2.getString("no_rkm_medis")));
                        param.put("lahir", Sequel.cariIsi("select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=?", rs2.getString("no_rkm_medis")));
                        param.put("norm", rs2.getString("no_rkm_medis"));
                        param.put("namars", var.getnamars());
                        param.put("alamatrs", var.getalamatrs());
                        param.put("kotars", var.getkabupatenrs());
                        param.put("propinsirs", var.getpropinsirs());
                        param.put("kontakrs", var.getkontakrs());
                        param.put("tanggalmasuk", Sequel.cariIsi("select tgl_masuk from kamar_inap where no_rawat=? order by tgl_masuk asc limit 1", tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString()));
                        param.put("jammasuk", Sequel.cariIsi("select jam_masuk from kamar_inap where no_rawat=? order by tgl_masuk asc limit 1", tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString()));
                        param.put("kamar", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString());
                        param.put("norawat", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString());
                        param.put("carabayar", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 6).toString());
                        param.put("emailrs", var.getemailrs());
                        param.put("logo", Sequel.cariGambar("select logo from setting"));
                        Valid.MyReport("rptFormulirPenerimaanPasien2.jrxml", param, "::[ Formulir Penerimaan Pasien ]::");
                        this.setCursor(Cursor.getDefaultCursor());
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("nama", TPasien.getText());
            param.put("jkel", Sequel.cariIsi("select if(pasien.jk='L','Laki-Laki','Perempuan') from pasien where pasien.no_rkm_medis=?", TNoRM.getText()));
            param.put("lahir", Sequel.cariIsi("select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=?", TNoRM.getText()));
            param.put("norm", TNoRM.getText());
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("tanggalmasuk", Sequel.cariIsi("select tgl_masuk from kamar_inap where no_rawat=? order by tgl_masuk asc limit 1", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString()));
            param.put("jammasuk", Sequel.cariIsi("select jam_masuk from kamar_inap where no_rawat=? order by tgl_masuk asc limit 1", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString()));
            param.put("kamar", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 7).toString());
            param.put("norawat", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString());
            param.put("carabayar", tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 6).toString());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptFormulirPenerimaanPasien2.jrxml", param, "::[ Formulir Penerimaan Pasien ]::");
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnFormulirPenerimaan1ActionPerformed

    private void MnCetakSuratOpnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratOpnameActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoRw1.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKamIn.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            var.setform("DlgKamarInap");
            SuratKeluar dlgki = new SuratKeluar(null, false);
            dlgki.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            dlgki.setLocationRelativeTo(internalFrame1);
            dlgki.emptTeks();
            dlgki.setPasien(TNoRw1.getText(),TNoRM1.getText(),TPasien1.getText(), "Opname","Ranap");
            dlgki.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
//        DlgSakit2.setSize(550, 151);
//        DlgSakit2.setLocationRelativeTo(internalFrame1);
//        DlgSakit2.setVisible(true);
    }//GEN-LAST:event_MnCetakSuratOpnameActionPerformed

    private void BtnPrint5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint5ActionPerformed
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
            param.put("nomersurat", NomorSurat.getText());
            param.put("dokterpj", CrDokter3.getText());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptSuratSakit2.jrxml", "report", "::[ Surat Sakit ]::",
                    "select reg_periksa.no_rkm_medis,dokter.nm_dokter,pasien.keluarga,pasien.namakeluarga,pasien.tgl_lahir,pasien.jk,"
                    + " pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.pekerjaan,pasien.alamat"
                    + " from reg_periksa inner join pasien inner join dokter"
                    + " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_dokter=dokter.kd_dokter  "
                    + "where reg_periksa.no_rawat='" + TNoRw1.getText() + "' ", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPrint5ActionPerformed

    private void BtnKeluar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar4ActionPerformed
        DlgSakit2.dispose();
    }//GEN-LAST:event_BtnKeluar4ActionPerformed

    private void BtnSeek5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek5ActionPerformed
        var.setform("DlgKamarInap");
        reg.dokter.isCek();
        reg.dokter.TCari.requestFocus();
        reg.dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        reg.dokter.setLocationRelativeTo(internalFrame1);
        reg.dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeek5ActionPerformed

    private void MnTeridentifikasiTBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnTeridentifikasiTBActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        DlgDataTB resep = new DlgDataTB(null, false);
                        resep.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                        resep.setLocationRelativeTo(internalFrame1);
                        resep.isCek();
                        resep.emptTeks();
                        resep.setNoRM(rs2.getString("no_rawat2"));
                        resep.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgDataTB resep = new DlgDataTB(null, false);
            resep.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            resep.setLocationRelativeTo(internalFrame1);
            resep.isCek();
            resep.emptTeks();
            resep.setNoRM(norawat.getText());
            resep.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnTeridentifikasiTBActionPerformed

    private void ppDataIdoBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppDataIdoBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        DlgDataIDO ido = new DlgDataIDO(null, false);
                        //  ido.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                        ido.setLocationRelativeTo(internalFrame1);
                        //ido.emptTeks();
                        //hais.isCek();
                        //hais.setNoRm(rs2.getString("no_rawat2"),DTPCari1.getDate(),DTPCari2.getDate()); 
                        //hais.tampil();
                        ido.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            DlgDataIDO ido = new DlgDataIDO(null, false);
            //ido.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            ido.setLocationRelativeTo(internalFrame1);
            //ido.emptTeks();
            //hais.isCek();
            //hais.setNoRm(rs2.getString("no_rawat2"),DTPCari1.getDate(),DTPCari2.getDate()); 
            //hais.tampil();
            ido.setVisible(true);
        }
    }//GEN-LAST:event_ppDataIdoBtnPrintActionPerformed

    private void MnAsuhanKeperawatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnAsuhanKeperawatanActionPerformed
        DlgAskep askep = new DlgAskep(null, false);
        askep.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        askep.setLocationRelativeTo(internalFrame1);
        askep.setpasien(TNoRw1.getText(), TNoRM1.getText(), TPasien1.getText(), Sequel.cariIsi("select pasien.alamat from reg_periksa, pasien where reg_periksa.no_rkm_medis = pasien.no_rkm_medis and no_rawat = '" + TNoRw1.getText() + "'"), Sequel.cariIsi("select c.nm_bangsal FROM kamar_inap as a, kamar as b, bangsal as c WHERE a.kd_kamar=b.kd_kamar AND b.kd_bangsal = c.kd_bangsal AND a.no_rawat='" + TNoRw1.getText() + "'"));
        askep.setVisible(true);
    }//GEN-LAST:event_MnAsuhanKeperawatanActionPerformed

    private void ppSuratKontrolBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSuratKontrolBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (TNoRw1.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKamIn.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() != -1) {
                try {
                    String date = "";
                    ps = koneksi.prepareStatement("select * from bridging_sep where no_rawat=?");
                    try {
                        ps.setString(1, TNoRw1.getText());
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            BPJSSuratKontrol form = new BPJSSuratKontrol(null, false);
                            date = rs.getString("tglsep");
                            form.setNoRm(rs.getString("no_rawat"), rs.getString("no_sep"), rs.getString("no_kartu"), rs.getString("nomr"), rs.getString("nama_pasien"), rs.getString("tanggal_lahir"), rs.getString("jkel"), rs.getString("nmdiagnosaawal"), rs.getString("kdpolitujuan"), date);
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

    private void MnLapCov19V3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLapCov19V3ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (TNoRw1.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKamIn.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() != -1) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                SirsApi apiSirs = new SirsApi();
                String token = apiSirs.getToken();
                if (token == "") {
                    JOptionPane.showMessageDialog(null, "Gagal Mendapatkan Token");
                } else {
                    SirsLaporanCovid19V3 form = new SirsLaporanCovid19V3(null, false);
                    form.setNoRm(TNoRw1.getText(), TNoRM1.getText(), TPasien1.getText(), token);
                    form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                    form.setLocationRelativeTo(internalFrame1);
                    form.setVisible(true);
                }
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnLapCov19V3ActionPerformed

    private void ppSuratPRIBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppSuratPRIBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (TNoRw1.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKamIn.requestFocus();
        } else {
            if (tbKamIn.getSelectedRow() != -1) {
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
                                form.setNoRm(TNoRw1.getText(), rs.getString("no_peserta"), TNoRM.getText(), TPasien.getText(), rs.getString("tgl_lahir"), rs.getString("jk"), "-");
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

    private void MnInputHPBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnInputHPBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKamIn.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            var.setstatus(true);
            DlgNomorTelp dlgki = new DlgNomorTelp(null, false);
            dlgki.setLocationRelativeTo(internalFrame1);
            dlgki.setPasien(TNoRM1.getText(), TPasien1.getText());
            dlgki.setVisible(true);

            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnInputHPBtnPrintActionPerformed
    private void MnPermintaanKerohanianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPermintaanKerohanianActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "");
                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        DlgPermintaanKerohanian dlgro = new DlgPermintaanKerohanian(null, false);
                        dlgro.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                        dlgro.setLocationRelativeTo(internalFrame1);
                        dlgro.emptTeks();
                        dlgro.isCek();
                        dlgro.setNoRm(rs2.getString("no_rawat2"), "Ranap");
                        dlgro.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgPermintaanKerohanian dlgro = new DlgPermintaanKerohanian(null, false);
            dlgro.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            dlgro.setLocationRelativeTo(internalFrame1);
            dlgro.emptTeks();
            dlgro.isCek();
            dlgro.setNoRm(norawat.getText(), "Ranap");
            dlgro.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnPermintaanKerohanianActionPerformed

    private void MnLabelSyringpumpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLabelSyringpumpActionPerformed
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
            param.put("tanggal", Valid.SetTgl3(TIn.getText()));
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptBarcodeRM7.jrxml", "report", "::[ Label Syring Pump ]::", "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk,"
                    + "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,',',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"
                    + "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"
                    + "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"
                    + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "
                    + "inner join kelurahan inner join kecamatan inner join kabupaten "
                    + "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "
                    + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='" + TNoRM.getText() + "' ", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLabelSyringpumpActionPerformed

    private void MnPermintaanUTDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPermintaanUTDActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        DlgPermintaanUTD dlgro = new DlgPermintaanUTD(null, false);
                        dlgro.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                        dlgro.setLocationRelativeTo(internalFrame1);
                        dlgro.emptTeks();
                        dlgro.isCek();
                        dlgro.setNoRm(rs2.getString("no_rawat2"), "Ranap");
                        dlgro.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgPermintaanUTD dlgro = new DlgPermintaanUTD(null, false);
            dlgro.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            dlgro.setLocationRelativeTo(internalFrame1);
            dlgro.emptTeks();
            dlgro.isCek();
            dlgro.setNoRm(norawat.getText(), "Ranap");
            dlgro.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnPermintaanUTDActionPerformed

    private void MnSEPNoCekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSEPNoCekActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        var.setform("DlgKamarInap");
        BPJSDataSEP dlgki = new BPJSDataSEP(null, false);
        dlgki.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
        dlgki.setLocationRelativeTo(internalFrame1);
        dlgki.isCek();
        dlgki.setNoRmNonCek(norawat.getText(), Valid.SetTgl2(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString()), "1. Ranap", "", "", "No");
        dlgki.setVisible(true);
        if ((Sequel.cariInteger("select count(no_rawat) from bridging_sep where no_rawat=?", norawat.getText()) > 0)) {
            dlgki.getTab(norawat.getText());
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnSEPNoCekActionPerformed

    private void MnPermintaanMPPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPermintaanMPPActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "");
                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        DlgSkriningMPP dlgro = new DlgSkriningMPP(null, false);
                        dlgro.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                        dlgro.setLocationRelativeTo(internalFrame1);
                        dlgro.emptTeks();
                        dlgro.isCek();
                        dlgro.setNoRm(rs2.getString("no_rawat2"), "Ranap");
                        dlgro.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgSkriningMPP dlgro = new DlgSkriningMPP(null, false);
            dlgro.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            dlgro.setLocationRelativeTo(internalFrame1);
            dlgro.emptTeks();
            dlgro.isCek();
            dlgro.setNoRm(norawat.getText(), "Ranap");
            dlgro.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnPermintaanMPPActionPerformed

    private void MnBarcodeRM10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcodeRM10ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data pasien sudah habis...!!!!");
            TNoRM.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbKamIn.requestFocus();
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

    private void MnPACSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPACSActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoRw1.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKamIn.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            var.setform("DlgKamarInap");
            DlgCariPeriksaRadiologi dlgki = new DlgCariPeriksaRadiologi(null, false);
            dlgki.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            dlgki.setLocationRelativeTo(internalFrame1);
            dlgki.isCek();
            dlgki.SetNoRw(TNoRw1.getText());
            dlgki.tampilOrthanc2();
            dlgki.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnPACSActionPerformed

    private void MnSoapResumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSoapResumeActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoRw1.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKamIn.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            var.setform("DlgKamarInap");
            DlgSoapResume dlgki = new DlgSoapResume(null, false);
            dlgki.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            dlgki.setLocationRelativeTo(internalFrame1);
            dlgki.isCek();
            dlgki.setDataPasien(TNoRw1.getText(),TNoRM1.getText(),TPasien1.getText(), tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 8).toString());
            dlgki.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnSoapResumeActionPerformed

    private void MnHasilLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHasilLabActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoRw1.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKamIn.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select ranap_gabung.no_rawat2 from reg_periksa inner join ranap_gabung on "
                        + "ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        var.setform("DlgKamarInap");
                        DlgCariPeriksaLab dlgki = new DlgCariPeriksaLab(null, false);
                        dlgki.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                        dlgki.setLocationRelativeTo(internalFrame1);
                        dlgki.isCek();
                        dlgki.SetNoRw(rs2.getString("no_rawat2"));
                        dlgki.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            var.setform("DlgKamarInap");
            DlgCariPeriksaLab dlgki = new DlgCariPeriksaLab(null, false);
            dlgki.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            dlgki.setLocationRelativeTo(internalFrame1);
            dlgki.isCek();
            dlgki.SetNoRw(TNoRw1.getText());
            dlgki.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnHasilLabActionPerformed

    private void MnKelahiranBayiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKelahiranBayiActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat, pasien.tgl_lahir, pasien.tgl_daftar "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        DlgIKBBayi resume = new DlgIKBBayi(null, false);
                        resume.setNoRM(TNoRM.getText(), TPasien.getText(), rs2.getString("alamat"));
                        resume.tampil();
                        resume.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                        resume.setLocationRelativeTo(internalFrame1);
                        resume.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgIKBBayi resume = new DlgIKBBayi(null, false);
            resume.setNoRM(TNoRM.getText(), TPasien.getText(), Sequel.cariIsi("select concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat from reg_periksa inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis where reg_periksa.no_rawat=?",norawat.getText()));
            resume.tampil();
            resume.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnKelahiranBayiActionPerformed

    private void MnAssesmentAwalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnAssesmentAwalActionPerformed
     if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            if(tbKamIn.getSelectedRow()>-1){
               
            }
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                         this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianAwalKeperawatanRanap form=new RMPenilaianAwalKeperawatanRanap(null,false);
                form.isCek();
                form.emptTeks();
                if(R1.isSelected()==true){
                    form.setNoRm(TNoRw1.getText(),new Date(),tbKamIn.getValueAt(tbKamIn.getSelectedRow(),6).toString(),TNoRM.getText());
                }else if(R2.isSelected()==true){
                    form.setNoRm(TNoRw1.getText(),DTPCari2.getDate(),tbKamIn.getValueAt(tbKamIn.getSelectedRow(),6).toString(),TNoRM.getText());
                }else if(R3.isSelected()==true){
                    form.setNoRm(TNoRw1.getText(),DTPCari4.getDate(),tbKamIn.getValueAt(tbKamIn.getSelectedRow(),6).toString(),TNoRM.getText());
                }
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
             this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMPenilaianAwalKeperawatanRanap form=new RMPenilaianAwalKeperawatanRanap(null,false);
                form.isCek();
                form.emptTeks();
                if(R1.isSelected()==true){
                    form.setNoRm(TNoRw1.getText(),new Date(),tbKamIn.getValueAt(tbKamIn.getSelectedRow(),6).toString(),TNoRM.getText());
                }else if(R2.isSelected()==true){
                    form.setNoRm(TNoRw1.getText(),DTPCari2.getDate(),tbKamIn.getValueAt(tbKamIn.getSelectedRow(),6).toString(),TNoRM.getText());
                }else if(R3.isSelected()==true){
                    form.setNoRm(TNoRw1.getText(),DTPCari4.getDate(),tbKamIn.getValueAt(tbKamIn.getSelectedRow(),6).toString(),TNoRM.getText());
                }
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnAssesmentAwalActionPerformed

    private void MnCetakBerkasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakBerkasActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbKamIn.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            TNoRw2.setText(TNoRw1.getText());
            TNoRM2.setText(TNoRM1.getText());
            TPasien2.setText(TPasien1.getText());
            WindowCetakBerkas.setLocationRelativeTo(internalFrame1);
            WindowCetakBerkas.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnCetakBerkasActionPerformed

    private void BtnCetak4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCetak4ActionPerformed
        if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            String user = Sequel.cariIsi("select nama from pegawai where nik=?", var.getkode());
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            param.put("dpjp", Sequel.cariIsi("SELECT dokter.nm_dokter FROM dpjp_ranap join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.jenis_dpjp='Utama' and dpjp_ranap.no_rawat='" + TNoRw1.getText() + "' LIMIT 1"));
            param.put("petugas", user);
            param.put("jamlahir", Sequel.cariIsi("SELECT jam_lahir FROM pasien_bayi WHERE no_rkm_medis='" + TNoRM1.getText() + "'"));
            param.put("beratbadan", Sequel.cariIsi("SELECT berat_badan FROM pasien_bayi WHERE no_rkm_medis='" + TNoRM1.getText() + "'"));
            param.put("panjangbadan", Sequel.cariIsi("SELECT panjang_badan FROM pasien_bayi WHERE no_rkm_medis='" + TNoRM1.getText() + "'"));
            param.put("penolong", Sequel.cariIsi("SELECT dokter.nm_dokter FROM pasien_bayi join dokter on pasien_bayi.penolong=dokter.kd_dokter where pasien_bayi.no_rkm_medis='" + TNoRM1.getText() + "'"));
            param.put("warnakulit", Sequel.cariIsi("SELECT warnakulit FROM pasien_bayi WHERE no_rkm_medis='" + TNoRM1.getText() + "'"));            
            param.put("kamar", Sequel.cariIsi("SELECT bangsal.nm_bangsal FROM kamar_inap,kamar,bangsal WHERE kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal and kamar_inap.no_rawat='" + TNoRw1.getText() + "'"));
            param.put("tglreg", Sequel.cariIsi("SELECT DATE_FORMAT(tgl_registrasi,'%d-%m-%Y') FROM reg_periksa WHERE no_rawat='" + TNoRw1.getText() + "'"));
            param.put("jamreg", Sequel.cariIsi("SELECT jam_reg FROM reg_periksa WHERE no_rawat='" + TNoRw1.getText() + "'"));
            if (chkIdentitasPasien.isSelected()) {
                Valid.MyReport("rptRM1.jrxml", "report", "::[ Identitas Pasien ]::", isReport(TNoRM1.getText()) , param);
                this.setCursor(Cursor.getDefaultCursor());
            }
            if (chkCover.isSelected()) {
                Valid.MyReport("rptCoverMap.jrxml", "report", "::[ Cover Rekam Medis ]::", isReport(TNoRM1.getText()), param);
                this.setCursor(Cursor.getDefaultCursor());
            }
            if (chkIdentifikasiBayi.isSelected()) {
                Valid.MyReport("rptIdentifikasiBayi.jrxml", "report", "::[ Identifikasi Bayi ]::",
                    "SELECT pasien.no_rkm_medis, pasien.nm_pasien,pasien.tgl_lahir, pasien.tmp_lahir, pasien.no_ktp, pasien.jk, pasien.alamat, pasien.namakeluarga, pasien.nm_ibu "
                    + "FROM pasien inner join kelurahan inner join  kecamatan inner join kabupaten "
                    + "WHERE pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec AND pasien.kd_kab=kabupaten.kd_kab AND pasien.no_rkm_medis='"+TNoRM1.getText()+"'", param);
                this.setCursor(Cursor.getDefaultCursor());
            }
            if (ChkGeneralConsent.isSelected()) {
                Valid.MyReport("rptPersetujuanUmum.jrxml", "report", "::[ Persetujuan Umum / General Consent ]::",
                    "SELECT pasien.no_rkm_medis, pasien.nm_pasien,pasien.tmp_lahir, pasien.tgl_lahir, pasien.no_ktp, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.namakeluarga,"
                    + "pasien.alamatpj, penjab.png_jawab, pasien.keluarga, pasien.no_tlp FROM reg_periksa,pasien, penjab,kelurahan, kecamatan, kabupaten "
                    + "WHERE reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel AND "
                    + "pasien.kd_kec=kecamatan.kd_kec AND pasien.kd_kab=kabupaten.kd_kab AND reg_periksa.no_rawat='"+TNoRw1.getText()+"'", param);
                Valid.MyReport("rptPersetujuanUmum2.jrxml", "report", "::[ Persetujuan Umum / General Consent ]::",
                    "SELECT pasien.no_rkm_medis, pasien.nm_pasien,pasien.tmp_lahir, pasien.tgl_lahir, pasien.no_ktp, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.namakeluarga,"
                    + "pasien.alamatpj, penjab.png_jawab, pasien.keluarga, pasien.no_tlp FROM reg_periksa,pasien, penjab,kelurahan, kecamatan, kabupaten "
                    + "WHERE reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel AND "
                    + "pasien.kd_kec=kecamatan.kd_kec AND pasien.kd_kab=kabupaten.kd_kab AND reg_periksa.no_rawat='"+TNoRw1.getText()+"'", param);
                this.setCursor(Cursor.getDefaultCursor());
            }
            if (chkRM23.isSelected()) {
                Valid.MyReport("rptRM23_1.jrxml", "report", "::[ RM 23.1 Catatan Edukasi ]::",
                    "SELECT pasien.no_rkm_medis, pasien.nm_pasien,pasien.tgl_lahir, pasien.tmp_lahir, pasien.no_ktp, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.namakeluarga, "
                    + "pasien.keluarga, pasien.no_tlp, pasien.alamatpj, suku_bangsa.nama_suku_bangsa FROM pasien inner join kelurahan inner join  kecamatan inner join kabupaten inner join suku_bangsa "
                    + "WHERE pasien.kd_kel=kelurahan.kd_kel AND pasien.kd_kec=kecamatan.kd_kec AND pasien.kd_kab=kabupaten.kd_kab and pasien.suku_bangsa=suku_bangsa.id "
                    + "and pasien.no_rkm_medis='" + TNoRM1.getText() + "'", param);
                this.setCursor(Cursor.getDefaultCursor());
                Valid.MyReport("rptRM23_2.jrxml", "report", "::[ RM 23.2 Catatan Edukasi ]::",
                    "SELECT pasien.no_rkm_medis, pasien.nm_pasien,pasien.tgl_lahir, pasien.tmp_lahir, pasien.no_ktp, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.namakeluarga, "
                    + "pasien.keluarga, pasien.no_tlp, pasien.alamatpj, suku_bangsa.nama_suku_bangsa FROM pasien inner join kelurahan inner join  kecamatan inner join kabupaten inner join suku_bangsa "
                    + "WHERE pasien.kd_kel=kelurahan.kd_kel AND pasien.kd_kec=kecamatan.kd_kec AND pasien.kd_kab=kabupaten.kd_kab and pasien.suku_bangsa=suku_bangsa.id "
                    + "and pasien.no_rkm_medis='" + TNoRM1.getText() + "'", param);
                this.setCursor(Cursor.getDefaultCursor());
            }
            if (chkSPMBiayaObat.isSelected()) {
                Valid.MyReport("rptSPMBiayaObat.jrxml", "report", "::[ Surat Pernyataan (Menanggung Biaya Pengobatan) ]::",
                    "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, pasien.tmp_lahir, pasien.tgl_lahir, pasien.namakeluarga,"
                    + "pasien.keluarga,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.no_tlp,pasien.umur, "
                    + "penjab.png_jawab,concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj "
                    + "from reg_periksa  inner join pasien inner join kelurahan inner join kecamatan inner join kabupaten inner join penjab "
                    + "on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and "
                    + "pasien.kd_kab=kabupaten.kd_kab where reg_periksa.no_rawat='"+TNoRw1.getText()+"'", param);
                this.setCursor(Cursor.getDefaultCursor());
            }
            if (chkTransferPasien.isSelected()) {
                if (Sequel.cariInteger("SELECT COUNT(no_rawat) from transfer_pasien where no_rawat=?", TNoRw2.getText()) > 0
                        && Sequel.cariInteger("SELECT COUNT(no_rawat) from transfer_pasien_detail where no_rawat=?", TNoRw2.getText()) > 0) {
                try {
                    ps = koneksi.prepareStatement(
                            "SELECT a.id,a.tanggal, a.jam, a.no_rawat, c.no_rkm_medis, c.nm_pasien,c.jk, DATE_FORMAT(c.tgl_lahir,'%d-%m-%Y') as tgl_lahir,  DATE_FORMAT(b.tgl_registrasi,'%d-%m-%Y') as tgl_registrasi, b.jam_reg, d.nm_dokter, e.nama, f.nm_bangsal, a.alasan_pindah, a.hasil_penunjang, a.keterangan,a.dokter_awal "
                            + "FROM transfer_pasien as a,reg_periksa as b,pasien as c ,dokter as d ,petugas as e ,bangsal as f "
                            + "where a.dokter_awal=d.kd_dokter and a.no_rawat=b.no_rawat and b.no_rkm_medis=c.no_rkm_medis and  a.petugas_awal=e.nip and a.ruang_awal=f.kd_bangsal "
                            + "and a.no_rawat like ? ");
                    try {
                        ps.setString(1, "%" + TNoRw2.getText().trim() + "%");
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            String tglpindah = "";
                            String jampindah = "";
                            String ruangpindah = "";
                            String dokterpindah = "";
                            String petugaspindah = "";
                            String suhu = "";
                            String nadi = "";
                            String darah = "";
                            String respirasi = "";
                            String nyeri = "";
                            String dx = "";
                            String dxkep = "";
                            ps2 = koneksi.prepareStatement(
                                    "SELECT DATE_FORMAT(inap.tgl_masuk,'%d-%m-%Y') as tgl_masuk , inap.jam_masuk, bangsal.nm_bangsal, dokter.nm_dokter, petugas.nama "
                                    + "FROM transfer_pasien as tf,kamar_inap as inap ,dokter ,petugas ,bangsal "
                                    + "where tf.no_rawat=inap.no_rawat and tf.dokter_pindah=dokter.kd_dokter and tf.petugas_pindah=petugas.nip and tf.ruang_pindah=bangsal.kd_bangsal "
                                    + "and tf.no_rawat=? ORDER BY inap.tgl_masuk DESC, inap.jam_masuk DESC LIMIT 1");
                            try {
                                ps2.setString(1, rs.getString("no_rawat"));
                                rs2 = ps2.executeQuery();
                                if (rs2.next()) {
                                    tglpindah = rs2.getString("tgl_masuk");
                                    jampindah = rs2.getString("jam_masuk");
                                    ruangpindah = rs2.getString("nm_bangsal");
                                    dokterpindah = rs2.getString("nm_dokter");
                                    petugaspindah = rs2.getString("nama");
                                }
                            } catch (Exception e) {
                                System.out.println("Notif : " + e);
                            } finally {
                                if (rs2 != null) {
                                    rs2.close();
                                }
                                if (ps2 != null) {
                                    ps2.close();
                                }
                            }

                            ps3 = koneksi.prepareStatement(
                                    "SELECT triase.skala_nyeri, triase.diagnosis, triase.diagnosa_keperawatan, ralan.suhu_tubuh, ralan.tensi, ralan.nadi, ralan.respirasi "
                                    + "FROM data_triase_igd as triase ,pemeriksaan_ralan as ralan where triase.no_rawat=ralan.no_rawat and triase.no_rawat=? ORDER BY ralan.tgl_perawatan DESC, ralan.jam_rawat DESC limit 1");
                            try {
                                ps3.setString(1, rs.getString("no_rawat"));
                                rs3 = ps3.executeQuery();
                                if (rs3.next()) {
                                    suhu = rs3.getString("suhu_tubuh");
                                    nadi = rs3.getString("nadi");
                                    darah = rs3.getString("tensi");
                                    respirasi = rs3.getString("respirasi");
                                    nyeri = rs3.getString("skala_nyeri");
                                    dx = rs3.getString("diagnosis");
                                    dxkep = rs3.getString("diagnosa_keperawatan");
                                }
                            } catch (Exception e) {
                                System.out.println("Notif : " + e);
                            } finally {
                                if (rs3 != null) {
                                    rs3.close();
                                }
                                if (ps3 != null) {
                                    ps3.close();
                                }
                            }

                            Map<String, Object> param2 = new HashMap<>();
                            param2.put("namars", var.getnamars());
                            param2.put("alamatrs", var.getalamatrs());
                            param2.put("kotars", var.getkabupatenrs());
                            param2.put("propinsirs", var.getpropinsirs());
                            param2.put("kontakrs", var.getkontakrs());
                            param2.put("emailrs", var.getemailrs());
                            param2.put("logo", Sequel.cariGambar("select logo from setting"));
                            param2.put("norm", TNoRM2.getText());
                            param2.put("nmpasien", TPasien2.getText());
                            param2.put("nik", Sequel.cariIsi("SELECT no_ktp from pasien where no_rkm_medis=?", TNoRM2.getText()));
                            param2.put("jk", rs.getString("jk"));
                            param2.put("tgllahir", rs.getString("tgl_lahir"));
                            param2.put("ruangawal", rs.getString("nm_bangsal"));
                            param2.put("dokterawal", rs.getString("nm_dokter"));
                            param2.put("petugasawal", rs.getString("nama"));
                            param2.put("tglmasuk",rs.getString("tgl_registrasi"));
                            param2.put("jammasuk", rs.getString("jam_reg"));
                            param2.put("ruangpindah", ruangpindah);
                            param2.put("dokterpindah", dokterpindah);
                            param2.put("petugaspindah", petugaspindah);
                            param2.put("tglpindah", tglpindah);
                            param2.put("jampindah", jampindah);
                            param2.put("suhu", suhu);
                            param2.put("nadi", nadi);
                            param2.put("tensi", darah);
                            param2.put("respirasi", respirasi);
                            param2.put("nyeri", nyeri);
                            param2.put("dx", dx);
                            param2.put("dxkep", dxkep);
                            param2.put("kateter", Sequel.cariIsi("SELECT tgl_perawatan FROM rawat_jl_pr WHERE kd_jenis_prw='J000319' and no_rawat=?", TNoRw2.getText()));
                            param2.put("oksigen", Sequel.cariIsi("SELECT tgl_perawatan FROM rawat_jl_pr WHERE kd_jenis_prw in ('J000322','J000323','J000324','J000325','J000326','J000327') and no_rawat=? limit 1", TNoRw2.getText()));
                            param2.put("ngt", Sequel.cariIsi("SELECT tgl_perawatan FROM rawat_jl_pr WHERE kd_jenis_prw='J000320' and no_rawat=?", TNoRw2.getText()));
                            param2.put("infus", Sequel.cariIsi("SELECT tgl_perawatan FROM rawat_jl_pr WHERE kd_jenis_prw='J000317' and no_rawat=?", TNoRw2.getText()));
                            param2.put("alasan", rs.getString("alasan_pindah"));
                            param2.put("alergi", Sequel.cariIsi("SELECT alergi FROM pemeriksaan_ralan WHERE no_rawat=? ORDER BY tgl_perawatan DESC, jam_rawat DESC LIMIT 1", TNoRw2.getText()));
                            param2.put("rekomendasi", rs.getString("keterangan"));
                            param2.put("jk_dokterawal", Sequel.cariIsi("SELECT jk FROM dokter WHERE kd_dokter=?", rs.getString("dokter_awal")));
                            param2.put("penunjang", Sequel.cariIsi("SELECT hasil_penunjang FROM transfer_pasien WHERE no_rawat=?", TNoRw2.getText()));

                            Sequel.queryu("truncate table temporary");
                            try {
                                ps4 = koneksi.prepareStatement(
                                        "SELECT transfer_pasien_detail.id, transfer_pasien_detail.tanggal, transfer_pasien_detail.jam, databarang.nama_brng, transfer_pasien_detail.dosis, transfer_pasien_detail.cara_pemberian "
                                        + "FROM transfer_pasien_detail, databarang WHERE transfer_pasien_detail.kd_barang=databarang.kode_brng and transfer_pasien_detail.no_rawat=?");
                                try {
                                    ps4.setString(1, TNoRw2.getText());
                                    rs4 = ps4.executeQuery();
                                    while (rs4.next()) {
                                        Sequel.menyimpan("temporary", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", 38, new String[]{
                                            "0", rs4.getString("id"), rs4.getString("tanggal"), rs4.getString("jam"), rs4.getString("nama_brng"), rs4.getString("dosis"), rs4.getString("cara_pemberian"), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
                                        });
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif 2 : " + e);
                                } finally {
                                    if (rs4 != null) {
                                        rs4.close();
                                    }
                                    if (ps4 != null) {
                                        ps4.close();
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Notif : " + e);
                            }
                            Valid.MyReport("rptTransferPasien.jrxml", "report", "::[ Transfer Pasien Antar Ruang ]::",
                                    "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14, temp14, temp15, temp16 from temporary order by no asc", param2);

                        }
                    } catch (Exception e) {
                        System.out.println("Notif ps4 : " + e);
                    } finally {
                        if (rs != null) {
                            rs.close();
                        }
                        if (ps != null) {
                            ps.close();
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
            }
        }
    }//GEN-LAST:event_BtnCetak4ActionPerformed

    private void BtnKeluar5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar5ActionPerformed
        WindowCetakBerkas.dispose();
    }//GEN-LAST:event_BtnKeluar5ActionPerformed

    private void MnCetakSuratIstirahatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratIstirahatActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoRw1.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKamIn.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            var.setform("DlgKamarInap");
            SuratKeluar dlgki = new SuratKeluar(null, false);
            dlgki.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            dlgki.setLocationRelativeTo(internalFrame1);
            dlgki.emptTeks();
            dlgki.setPasien(TNoRw1.getText(), TNoRM1.getText(), TPasien1.getText(), "Istirahat","Ranap");
            dlgki.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnCetakSuratIstirahatActionPerformed

    private void MnCetakSuratResumeMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratResumeMedisActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoRw1.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKamIn.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            var.setform("DlgKamarInap");
            SuratKeluar dlgki = new SuratKeluar(null, false);
            dlgki.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            dlgki.setLocationRelativeTo(internalFrame1);
            dlgki.emptTeks();
            dlgki.setPasien(TNoRw1.getText(), TNoRM1.getText(), TPasien1.getText(), "Resume", "Ranap");
            dlgki.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnCetakSuratResumeMedisActionPerformed

    private void MnTransferPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnTransferPasienActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        } else if (tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString().equals("")) {
            try {
                psanak = koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                        + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                        + "from reg_periksa inner join pasien inner join ranap_gabung on "
                        + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                try {
                    psanak.setString(1, tbKamIn.getValueAt(tbKamIn.getSelectedRow() - 1, 0).toString());
                    rs2 = psanak.executeQuery();
                    if (rs2.next()) {
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        RMTransferPasienAntarRuang form = new RMTransferPasienAntarRuang(null, false);
                        form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                        form.setLocationRelativeTo(internalFrame1);
                        form.emptTeks();
                        form.setNoRm(rs2.getString("no_rawat2"),tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString(),tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 13).toString(),"Ranap");
                        form.tampilResep();
                        form.setDokter(rs2.getString("no_rawat2"),"Ranap");
                        form.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    } else {
                        JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu pasien...!!!");
                        tbKamIn.requestFocus();
                    }
                } catch (Exception ex) {
                    System.out.println("Notifikasi : " + ex);
                } finally {
                    if (rs2 != null) {
                        rs2.close();
                    }
                    if (psanak != null) {
                        psanak.close();
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            RMTransferPasienAntarRuang form = new RMTransferPasienAntarRuang(null, false);
            form.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            form.setLocationRelativeTo(internalFrame1);
            form.emptTeks();
            form.setNoRm(TNoRw1.getText(),tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString(),tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 13).toString(),"Ranap");
            form.tampilResep();
            form.setDokter(TNoRw1.getText(),"Ranap");
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnTransferPasienActionPerformed

    private void MnCetakSuratOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratOperasiActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            TNoRw1.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKamIn.requestFocus();
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            var.setform("DlgKamarInap");
            SuratKeluar dlgki = new SuratKeluar(null, false);
            dlgki.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            dlgki.setLocationRelativeTo(internalFrame1);
            dlgki.emptTeks();
            dlgki.setPasien(TNoRw1.getText(), TNoRM1.getText(), TPasien1.getText(), "Operasi", "Ranap");            
            dlgki.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnCetakSuratOperasiActionPerformed

    private void MnGelang5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang5ActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data kamar inap pasien sudah habis...!!!!");
            BtnIn.requestFocus();       
        } else {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("tanggal", TIn.getText());
            param.put("kamar", kdkamar.getText() + " " + TBangsal.getText());
            param.put("dpjp", Sequel.cariIsi("select dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat=? ", TNoRw1.getText()));
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptGelangPasienBayi.jrxml", "report", "::[ Gelang Pasien Bayi ]::", "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "
                    + "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"
                    + "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"
                    + "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"
                    + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "
                    + "inner join kelurahan inner join kecamatan inner join kabupaten "
                    + "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "
                    + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='" + TNoRM.getText() + "' ", param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang5ActionPerformed

    private void chkCoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCoverActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkCoverActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgKamarInap dialog = new DlgKamarInap(new javax.swing.JFrame(), true);
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
    private widget.TextBox BangsalCari;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCetak4;
    private widget.Button BtnCloseGabung;
    private widget.Button BtnCloseIn;
    private widget.Button BtnCloseIn4;
    private widget.Button BtnCloseInpindah;
    private widget.Button BtnHapus;
    private widget.Button BtnHapusGabung;
    private widget.Button BtnIn;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluar4;
    private widget.Button BtnKeluar5;
    private widget.Button BtnOut;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint5;
    private widget.Button BtnSeek5;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpan4;
    private widget.Button BtnSimpanGabung;
    private widget.Button BtnSimpanpindah;
    private widget.CekBox ChkGeneralConsent;
    private widget.ComboBox CmbBln;
    private widget.ComboBox CmbBlnpindah;
    private widget.ComboBox CmbTahun;
    private widget.ComboBox CmbTahunpindah;
    private widget.ComboBox CmbTgl;
    private widget.ComboBox CmbTglpindah;
    private widget.TextBox CrDokter3;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPCari3;
    private widget.Tanggal DTPCari4;
    private javax.swing.JDialog DlgSakit2;
    private widget.PanelBiasa FormInput13;
    private widget.TextBox JamMasuk;
    private widget.Label LCount;
    private widget.Label LblStts;
    private javax.swing.JMenu MenuBPJS;
    private javax.swing.JMenu MenuInputData;
    private javax.swing.JMenu MnAssesment;
    private javax.swing.JMenuItem MnAssesmentAwal;
    private javax.swing.JMenuItem MnAsuhanGizi;
    private javax.swing.JMenuItem MnAsuhanKeperawatan;
    private javax.swing.JMenuItem MnBarcodeRM10;
    private javax.swing.JMenuItem MnBarcodeRM9;
    private javax.swing.JMenuItem MnBilling;
    private javax.swing.JMenuItem MnCekKepesertaan;
    private javax.swing.JMenuItem MnCekNIK;
    private javax.swing.JMenuItem MnCetakBerkas;
    private javax.swing.JMenuItem MnCetakSuratIstirahat;
    private javax.swing.JMenuItem MnCetakSuratOperasi;
    private javax.swing.JMenuItem MnCetakSuratOpname;
    private javax.swing.JMenuItem MnCetakSuratResumeMedis;
    private javax.swing.JMenuItem MnDPJP;
    private javax.swing.JMenuItem MnDPJPRanap;
    private javax.swing.JMenuItem MnDeposit;
    private javax.swing.JMenuItem MnDiagnosa;
    private javax.swing.JMenuItem MnDiet;
    private javax.swing.JMenuItem MnFormulirPenerimaan;
    private javax.swing.JMenuItem MnFormulirPenerimaan1;
    private javax.swing.JMenuItem MnGabungkanRanap;
    private javax.swing.JMenu MnGelang;
    private javax.swing.JMenuItem MnGelang3;
    private javax.swing.JMenuItem MnGelang4;
    private javax.swing.JMenuItem MnGelang5;
    private javax.swing.JMenu MnHapusData;
    private javax.swing.JMenuItem MnHapusDataSalah;
    private javax.swing.JMenuItem MnHapusObatOperasi;
    private javax.swing.JMenuItem MnHapusTagihanOperasi;
    private javax.swing.JMenuItem MnHasilLab;
    private javax.swing.JMenuItem MnInputHP;
    private javax.swing.JMenuItem MnInputResep;
    private javax.swing.JMenuItem MnJadwalOperasi;
    private javax.swing.JMenuItem MnKelahiranBayi;
    private javax.swing.JMenuItem MnLabelSyringpump;
    private javax.swing.JMenuItem MnLabelTracker;
    private javax.swing.JMenuItem MnLapCov19V3;
    private javax.swing.JMenu MnLaporan;
    private javax.swing.JMenuItem MnNoResep;
    private javax.swing.JMenu MnObat;
    private javax.swing.JMenuItem MnOperasi;
    private javax.swing.JMenuItem MnPACS;
    private javax.swing.JMenuItem MnPemberianObat;
    private javax.swing.JMenuItem MnPengantarPulang;
    private javax.swing.JMenuItem MnPenggunaanKamar;
    private javax.swing.JMenuItem MnPenjab;
    private javax.swing.JMenuItem MnPeriksaLab;
    private javax.swing.JMenuItem MnPeriksaRadiologi;
    private javax.swing.JMenu MnPermintaan;
    private javax.swing.JMenuItem MnPermintaanKerohanian;
    private javax.swing.JMenuItem MnPermintaanLab;
    private javax.swing.JMenuItem MnPermintaanMPP;
    private javax.swing.JMenuItem MnPermintaanRadiologi;
    private javax.swing.JMenuItem MnPermintaanUTD;
    private javax.swing.JMenuItem MnRM2D;
    private javax.swing.JMenuItem MnRanapGabung;
    private javax.swing.JMenuItem MnRawatInap;
    private javax.swing.JMenuItem MnRawatJalan;
    private javax.swing.JMenuItem MnRekapitulasiRanap;
    private javax.swing.JMenuItem MnResepDOkter;
    private javax.swing.JMenuItem MnResepPulang;
    private javax.swing.JMenuItem MnReturJual;
    private javax.swing.JMenuItem MnRincianObat;
    private javax.swing.JMenuItem MnRujuk;
    private javax.swing.JMenuItem MnRujukMasuk;
    private javax.swing.JMenuItem MnRujukSisrute;
    private javax.swing.JMenu MnRujukan;
    private javax.swing.JMenuItem MnSEP;
    private javax.swing.JMenuItem MnSEPNoCek;
    private javax.swing.JMenuItem MnSKDPBPJS;
    private javax.swing.JMenuItem MnSensusRanap;
    private javax.swing.JMenuItem MnSoapResume;
    private javax.swing.JMenuItem MnStatusAPS;
    private javax.swing.JMenuItem MnStatusBelumPulang;
    private javax.swing.JMenuItem MnStatusMembaik;
    private javax.swing.JMenuItem MnStatusMeninggal;
    private javax.swing.JMenuItem MnStatusRujuk;
    private javax.swing.JMenuItem MnStokObatPasien;
    private javax.swing.JMenuItem MnTeridentifikasiTB;
    private javax.swing.JMenuItem MnTilikBedah;
    private javax.swing.JMenu MnTindakan;
    private javax.swing.JMenuItem MnTransferPasien;
    private javax.swing.JMenuItem MnUpdateHari;
    private widget.TextBox NmBayi;
    private widget.TextBox NoRawatGabung;
    private widget.TextBox NoRmBayi;
    private widget.TextBox NomorSurat;
    private javax.swing.JPanel PanelCariUtama;
    private widget.RadioButton R1;
    private widget.RadioButton R2;
    private widget.RadioButton R3;
    private widget.RadioButton Rganti1;
    private widget.RadioButton Rganti2;
    private widget.RadioButton Rganti3;
    private widget.RadioButton Rganti4;
    private widget.ScrollPane Scroll;
    private javax.swing.JMenu SetStatus;
    private widget.TextBox TBangsal;
    private widget.TextBox TBangsalpindah;
    private widget.TextBox TCari;
    private widget.TextBox TIn;
    private widget.TextBox TJmlHari;
    private widget.TextBox TJmlHaripindah;
    private widget.TextBox TKdBngsal;
    private widget.TextBox TKdBngsalpindah;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRM1;
    private widget.TextBox TNoRM2;
    private widget.TextBox TNoRMpindah;
    private widget.TextBox TNoRw1;
    private widget.TextBox TNoRw2;
    private widget.TextBox TOut;
    private widget.TextBox TPasien;
    private widget.TextBox TPasien1;
    private widget.TextBox TPasien2;
    private widget.TextBox TPasienpindah;
    private widget.TextBox TSttsKamar;
    private widget.TextBox TSttsKamarpindah;
    private widget.TextBox TTarif;
    private widget.TextBox TTarifpindah;
    private javax.swing.JDialog WindowCaraBayar;
    private javax.swing.JDialog WindowCetakBerkas;
    private javax.swing.JDialog WindowInputKamar;
    private javax.swing.JDialog WindowPindahKamar;
    private javax.swing.JDialog WindowRanapGabung;
    private widget.Button btnBangsalCari;
    private widget.Button btnBayar;
    private widget.Button btnDiagnosa;
    private widget.Button btnKamar;
    private widget.Button btnKamar2;
    private widget.Button btnPasienRanapGabung;
    private widget.Button btnPasienRanapGabung1;
    private widget.Button btnPindah;
    private widget.Button btnReg;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private widget.CekBox chkCover;
    private widget.CekBox chkIdentifikasiBayi;
    private widget.CekBox chkIdentitasPasien;
    private widget.CekBox chkRM23;
    private widget.CekBox chkSPMBiayaObat;
    private widget.CekBox chkTransferPasien;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbDtkpindah;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbJampindah;
    private widget.ComboBox cmbMnt;
    private widget.ComboBox cmbMntpindah;
    private widget.ComboBox cmbStatus;
    private widget.ComboBox cmbStatusBayar;
    private widget.TextBox diagnosaakhir;
    private widget.TextBox diagnosaawal;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame10;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
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
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel55;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator13;
    private widget.TextBox kdkamar;
    private widget.TextBox kdkamarpindah;
    private widget.TextBox kdpenjab;
    private widget.Label label17;
    private widget.TextBox nmpenjab;
    private widget.TextBox norawat;
    private widget.TextBox norawatpindah;
    private widget.PanelBiasa panelBiasa4;
    private widget.panelisi panelCari;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass11;
    private widget.panelisi panelGlass9;
    private widget.panelisi panelisi2;
    private javax.swing.JMenuItem ppBerkasDigital;
    private javax.swing.JMenuItem ppCatatanPasien;
    private javax.swing.JMenuItem ppDataHAIs;
    private javax.swing.JMenuItem ppDataIdo;
    private javax.swing.JMenuItem ppIKP;
    private javax.swing.JMenuItem ppRiwayat;
    private javax.swing.JMenuItem ppSuratKontrol;
    private javax.swing.JMenuItem ppSuratPRI;
    private widget.Table tbKamIn;
    private widget.TextBox ttlbiaya;
    private widget.TextBox ttlbiayapindah;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        if (R1.isSelected() == true) {
            kmr = " stts_pulang='-' and reg_periksa.status_bayar like '%" + cmbStatusBayar.getSelectedItem().toString().replaceAll("Semua", "") + "%' ";
            if (!BangsalCari.getText().equals("")) {
                kmr = " stts_pulang='-' and bangsal.nm_bangsal='" + BangsalCari.getText() + "' and reg_periksa.status_bayar like '%" + cmbStatusBayar.getSelectedItem().toString().replaceAll("Semua", "") + "%' ";
            }
        } else if (R2.isSelected() == true) {
            kmr = " kamar_inap.tgl_masuk between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and reg_periksa.status_bayar like '%" + cmbStatusBayar.getSelectedItem().toString().replaceAll("Semua", "") + "%' ";
            if (!BangsalCari.getText().equals("")) {
                kmr = " kamar_inap.tgl_masuk between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' and bangsal.nm_bangsal='" + BangsalCari.getText() + "' and reg_periksa.status_bayar like '%" + cmbStatusBayar.getSelectedItem().toString().replaceAll("Semua", "") + "%' ";
            }
        } else if (R3.isSelected() == true) {
            kmr = " kamar_inap.tgl_keluar between '" + Valid.SetTgl(DTPCari3.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari4.getSelectedItem() + "") + "' and reg_periksa.status_bayar like '%" + cmbStatusBayar.getSelectedItem().toString().replaceAll("Semua", "") + "%' ";
            if (!BangsalCari.getText().equals("")) {
                kmr = " kamar_inap.tgl_keluar between '" + Valid.SetTgl(DTPCari3.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari4.getSelectedItem() + "") + "' and bangsal.nm_bangsal='" + BangsalCari.getText() + "' and reg_periksa.status_bayar like '%" + cmbStatusBayar.getSelectedItem().toString().replaceAll("Semua", "") + "%' ";
            }
        }

        key = kmr + " ";
        if (!TCari.getText().equals("")) {
            key = kmr + "and ( kamar_inap.no_rawat like '%" + TCari.getText().trim() + "%' or "
                    + "reg_periksa.no_rkm_medis like '%" + TCari.getText().trim() + "%' or "
                    + "pasien.nm_pasien like '%" + TCari.getText().trim() + "%' or "
                    + "concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) like '%" + TCari.getText().trim() + "%' or "
                    + "pasien.no_tlp like '%" + TCari.getText().trim() + "%' or "
                    + "kamar_inap.kd_kamar like '%" + TCari.getText().trim() + "%' or "
                    + "bangsal.nm_bangsal like '%" + TCari.getText().trim() + "%' or "
                    + "kamar_inap.diagnosa_awal like '%" + TCari.getText().trim() + "%' or "
                    + "kamar_inap.diagnosa_akhir like '%" + TCari.getText().trim() + "%' or "
                    + "kamar_inap.trf_kamar like '%" + TCari.getText().trim() + "%' or "
                    + "kamar_inap.tgl_masuk like '%" + TCari.getText().trim() + "%' or "
                    + "dokter.nm_dokter like '%" + TCari.getText().trim() + "%' or "
                    + "kamar_inap.stts_pulang like '%" + TCari.getText().trim() + "%' or "
                    + "kamar_inap.tgl_keluar like '%" + TCari.getText().trim() + "%' or "
                    + "penjab.png_jawab like '%" + TCari.getText().trim() + "%' or "
                    + "kamar_inap.ttl_biaya like '%" + TCari.getText().trim() + "%' ) ";
        }

        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement(
                    "select kamar_inap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,reg_periksa.p_jawab,reg_periksa.hubunganpj,"
                    + "penjab.png_jawab,concat(kamar_inap.kd_kamar,' ',bangsal.nm_bangsal) as kamar,pasien.no_tlp, kamar_inap.trf_kamar,kamar_inap.diagnosa_awal,kamar_inap.diagnosa_akhir,"
                    + "kamar_inap.tgl_masuk,kamar_inap.jam_masuk,if(kamar_inap.tgl_keluar='0000-00-00','',kamar_inap.tgl_keluar) as tgl_keluar,if(kamar_inap.jam_keluar='00:00:00','',kamar_inap.jam_keluar) as jam_keluar,"
                    + "kamar_inap.ttl_biaya,kamar_inap.stts_pulang,kamar_inap.lama,dokter.nm_dokter,kamar_inap.kd_kamar,reg_periksa.kd_pj,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,reg_periksa.status_bayar "
                    + "from kamar_inap , reg_periksa , pasien , kamar , bangsal , kelurahan , kecamatan , kabupaten , dokter , penjab "
                    + "where kamar_inap.no_rawat=reg_periksa.no_rawat "
                    + "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "
                    + "and reg_periksa.kd_dokter=dokter.kd_dokter "
                    + "and reg_periksa.kd_pj=penjab.kd_pj "
                    + "and kamar_inap.kd_kamar=kamar.kd_kamar "
                    + "and kamar.kd_bangsal=bangsal.kd_bangsal and pasien.kd_kel=kelurahan.kd_kel "
                    + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "
                    + "and " + key + " order by bangsal.nm_bangsal,kamar_inap.tgl_masuk,kamar_inap.jam_masuk");
            try {
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien") + " (" + rs.getString("umur") + ")",
                        rs.getString("alamat"), rs.getString("no_tlp"), rs.getString("p_jawab"), rs.getString("hubunganpj"), rs.getString("png_jawab") + ": " + Sequel.cariIsi("select no_sep from bridging_sep where jnsPelayanan='1' and no_rawat='" +rs.getString("no_rawat") + "'") ,
                        rs.getString("kamar"), Valid.SetAngka(rs.getDouble("trf_kamar")), rs.getString("diagnosa_awal"),
                        rs.getString("diagnosa_akhir"), rs.getString("tgl_masuk"), rs.getString("jam_masuk"), rs.getString("tgl_keluar"),
                        rs.getString("jam_keluar"), Valid.SetAngka(rs.getDouble("ttl_biaya")), rs.getString("stts_pulang"),
                        rs.getString("lama"), rs.getString("nm_dokter"), rs.getString("kd_kamar"), rs.getString("status_bayar"),rs.getString("kd_pj")
                    });
                    psanak = koneksi.prepareStatement(
                            "select pasien.no_rkm_medis,pasien.nm_pasien,ranap_gabung.no_rawat2,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.no_peserta, "
                            + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamat "
                            + "from reg_periksa inner join pasien inner join ranap_gabung on "
                            + "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and ranap_gabung.no_rawat2=reg_periksa.no_rawat where ranap_gabung.no_rawat=?");
                    try {
                        psanak.setString(1, rs.getString(1));
                        rs2 = psanak.executeQuery();
                        if (rs2.next()) {
                            tabMode.addRow(new String[]{
                                "", rs2.getString("no_rkm_medis"), rs2.getString("nm_pasien") + " (" + rs2.getString("umur") + ")",
                                rs.getString("alamat"), "", rs.getString("p_jawab"), rs.getString("hubunganpj"), rs.getString("png_jawab"),
                                rs.getString("kamar"), Valid.SetAngka(rs.getDouble("trf_kamar") * (persenbayi / 100)), "",
                                "", rs.getString("tgl_masuk"), rs.getString("jam_masuk"), rs.getString("tgl_keluar"),
                                rs.getString("jam_keluar"), Valid.SetAngka(rs.getDouble("ttl_biaya") * (persenbayi / 100)), rs.getString("stts_pulang"),
                                rs.getString("lama"), rs.getString("nm_dokter"), rs.getString("kd_kamar"), rs.getString("status_bayar"),rs.getString("kd_pj")
                            });
                        }
                    } catch (Exception ex) {
                        System.out.println("Notifikasi : " + ex);
                    } finally {
                        if (rs2 != null) {
                            rs2.close();
                        }
                        if (psanak != null) {
                            psanak.close();
                        }
                    }
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

        dpjp_oto();
    }

    public void emptTeks() {
        norawat.setText("");
        TNoRM.setText("");
        TPasien.setText("");
        kdkamar.setText("");
        TKdBngsal.setText("");
        TBangsal.setText("");
        diagnosaawal.setText("");
        diagnosaakhir.setText("");
        date = new Date();
        now = dateFormat.format(date);
        CmbTahun.setSelectedItem(now.substring(0, 4));
        CmbBln.setSelectedItem(now.substring(5, 7));
        CmbTgl.setSelectedItem(now.substring(8, 10));
        cmbJam.setSelectedItem(now.substring(11, 13));
        cmbMnt.setSelectedItem(now.substring(14, 16));
        cmbDtk.setSelectedItem(now.substring(17, 19));

        TOut.setText("");
        TIn.setText("");
        JamMasuk.setText("");
        TTarif.setText("0");
        TJmlHari.setText("0");
        ttlbiaya.setText("0");
        norawat.requestFocus();
    }

    private void getData() {
        TOut.setText("");
        TIn.setText("");
        JamMasuk.setText("");
        if (tbKamIn.getSelectedRow() != -1) {
            norawat.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString());
            TNoRM.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 1).toString());
            TPasien.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 2).toString());
            TNoRw1.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString());
            TNoRM1.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 1).toString());
            TPasien1.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 2).toString());
            norawatpindah.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 0).toString());
            TNoRMpindah.setText(TNoRM.getText());
            TPasienpindah.setText(TPasien.getText());
            kdkamar.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 20).toString());
            diagnosaawal.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 10).toString());
            diagnosaakhir.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 11).toString());
            TIn.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 12).toString());
            JamMasuk.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 13).toString());
            TOut.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 14).toString());
            ttlbiaya.setText(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 16).toString());
            cmbStatus.setSelectedItem(tbKamIn.getValueAt(tbKamIn.getSelectedRow(), 17).toString());
        }
    }

    private void isKmr() {
        if (i == 1) {
            kd_pj = Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?", norawat.getText());
            Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=? ", TKdBngsal, kdkamar.getText());
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=? ", TBangsal, TKdBngsal.getText());
            Sequel.cariIsi("select status from kamar where kd_kamar=? ", TSttsKamar, kdkamar.getText());
            try {
                pstarif = koneksi.prepareStatement("select tarif from set_harga_kamar where kd_kamar=? and kd_pj=?");
                try {
                    pstarif.setString(1, kdkamar.getText());
                    pstarif.setString(2, kd_pj);
                    rs = pstarif.executeQuery();
                    if (rs.next()) {
                        TTarif.setText(rs.getString(1));
                    } else {
                        Sequel.cariIsi("select trf_kamar from kamar where kd_kamar=? ", TTarif, kdkamar.getText());
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : " + e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (pstarif != null) {
                        pstarif.close();
                    }
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        } else {
            kd_pj = Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?", norawatpindah.getText());
            Sequel.cariIsi("select kd_bangsal from kamar where kd_kamar=? ", TKdBngsalpindah, kdkamarpindah.getText());
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=? ", TBangsalpindah, TKdBngsalpindah.getText());
            Sequel.cariIsi("select status from kamar where kd_kamar=? ", TSttsKamarpindah, kdkamarpindah.getText());
            Sequel.cariIsi("select trf_kamar from kamar where kd_kamar=? ", TTarifpindah, kdkamarpindah.getText());
            try {
                pstarif = koneksi.prepareStatement("select tarif from set_harga_kamar where kd_kamar=? and kd_pj=?");
                try {
                    pstarif.setString(1, kdkamarpindah.getText());
                    pstarif.setString(2, kd_pj);
                    rs = pstarif.executeQuery();
                    if (rs.next()) {
                        TTarifpindah.setText(rs.getString(1));
                    } else {
                        Sequel.cariIsi("select trf_kamar from kamar where kd_kamar=? ", TTarifpindah, kdkamar.getText());
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : " + e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (pstarif != null) {
                        pstarif.close();
                    }
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        }
    }

    private void isjml() {
        DecimalFormat df2 = new DecimalFormat("####");
        if ((!TJmlHari.getText().equals("")) && (!TTarif.getText().equals(""))) {
            double x = Double.parseDouble(TJmlHari.getText().trim());
            double y = Double.parseDouble(TTarif.getText().trim());
            ttlbiaya.setText(df2.format(x * y));
        }

        if ((!TJmlHaripindah.getText().equals("")) && (!TTarifpindah.getText().equals(""))) {
            double x = Double.parseDouble(TJmlHaripindah.getText().trim());
            double y = Double.parseDouble(TTarifpindah.getText().trim());
            ttlbiayapindah.setText(df2.format(x * y));
        }
    }

    public void setNoRm(String norwt) {
        norawat.setText(norwt);
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ", TNoRM, norawat.getText());
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ", TPasien, TNoRM.getText());        
        R1.setSelected(true);
        TCari.setText(norawat.getText());
        String dxAwal = Sequel.cariIsi("SELECT diagnosa from bridging_surat_pri_bpjs where no_rawat=?",norawat.getText());
        try {
            ps = koneksi.prepareStatement("select no_rawat, kd_kamar, diagnosa_awal, diagnosa_akhir, tgl_masuk, jam_masuk, tgl_keluar, jam_keluar, ttl_biaya "
                    + "from kamar_inap where no_rawat=? order by tgl_masuk,jam_masuk desc limit 1 ");
//            ps1 = koneksi.prepareStatement("select CONCAT(diagnosa_pasien.kd_penyakit ,' - ',penyakit.nm_penyakit ) as diagnosa "
//                    + "from diagnosa_pasien join penyakit on diagnosa_pasien.kd_penyakit = penyakit.kd_penyakit where no_rawat=? and prioritas='1' and status='Ralan' ");
            String dxAwal2 = Sequel.cariIsi("select CONCAT(diagnosa_pasien.kd_penyakit ,' - ',penyakit.nm_penyakit ) as diagnosa "
                    + "from diagnosa_pasien join penyakit on diagnosa_pasien.kd_penyakit = penyakit.kd_penyakit where no_rawat=? and prioritas='1' and diagnosa_pasien.status='Ralan' ",norawat.getText());
            try {
                ps.setString(1, norawat.getText());
//                ps1.setString(1, norawat.getText());
                rs = ps.executeQuery();
//                rs1 = ps1.executeQuery();
//                while (rs1.next()) {
//                    dx1 = rs1.getString(1) + " , " + dx1;
//                }
                if (dxAwal.equals("")) {
                    dxAwal = dxAwal2;
                }
                if (rs.next()) {
                    norawat.setEditable(false);
                    norawat.setText(rs.getString(1));
                    kdkamar.setText(rs.getString(2));
//                    diagnosaawal.setText(rs.getString(3));
                    diagnosaawal.setText(dxAwal);
                    diagnosaakhir.setText(rs.getString(4));
                    TIn.setText(rs.getString(5));
                    JamMasuk.setText(rs.getString(6));
                    TOut.setText(rs.getString(7));
                    ttlbiaya.setText(rs.getString(8));

                    kdkamar.setEditable(false);
                    diagnosaawal.setEditable(false);
                    diagnosaakhir.setVisible(true);
                    btnDiagnosa.setVisible(true);
                    jLabel23.setVisible(true);
                    cmbStatus.setVisible(true);
                    jLabel26.setVisible(true);
                    diagnosaakhir.setText("");
                    LblStts.setText("Pulang/Check Out");
                    i = 1;
                    btnReg.setEnabled(false);
                    btnKamar.setEnabled(false);
                    CmbTahun.setSelectedItem(now.substring(0, 4));
                    CmbBln.setSelectedItem(now.substring(5, 7));
                    CmbTgl.setSelectedItem(now.substring(8, 10));
                } else {
                    norawat.setEditable(true);
                    kdkamar.setEditable(true);
                    diagnosaawal.setEditable(false);
                    diagnosaawal.setText(dxAwal);
                    diagnosaakhir.setVisible(false);
                    btnDiagnosa.setVisible(false);
                    TIn.setText("");
                    JamMasuk.setText("");
                    TOut.setText("");
                    ttlbiaya.setText("0");
                    jLabel23.setVisible(false);
                    cmbStatus.setVisible(false);
                    jLabel26.setVisible(false);
                    diagnosaakhir.setText("-");
                    LblStts.setText("Masuk/Check In");
                    btnReg.setEnabled(true);
                    btnKamar.setEnabled(true);
                    CmbTahun.setSelectedItem(now.substring(0, 4));
                    CmbBln.setSelectedItem(now.substring(5, 7));
                    CmbTgl.setSelectedItem(now.substring(8, 10));
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

            if (kdkamar.isEditable() == false) {
                isKmr();
                isjml();
            }
            CmbTahunItemStateChanged(null);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void isCek() {
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            namakamar = prop.getProperty("KAMARAKTIFRANAP");
        } catch (Exception ex) {
            namakamar = "";
        }

        if (!namakamar.equals("")) {
            if (var.getkode().equals("Admin Utama")) {
                BangsalCari.setText("");
                btnBangsalCari.setEnabled(true);
                BangsalCari.setEditable(true);
            } else {
                BangsalCari.setText(namakamar);
                btnBangsalCari.setEnabled(true);
                BangsalCari.setEditable(true);
            }
        } else {
            btnBangsalCari.setEnabled(true);
            BangsalCari.setEditable(true);
        }

        BtnSimpan.setEnabled(var.getkamar_inap());
        BtnSimpanpindah.setEnabled(var.getkamar_inap());
        BtnHapus.setEnabled(var.getkamar_inap());
        BtnPrint.setEnabled(var.getkamar_inap());
        MnRawatInap.setEnabled(var.gettindakan_ranap());
        MnRawatJalan.setEnabled(var.gettindakan_ralan());
        MnPemberianObat.setEnabled(var.getberi_obat());
        MnResepDOkter.setEnabled(var.getberi_obat());
        MnReturJual.setEnabled(var.getretur_dari_pembeli());
        MnInputResep.setEnabled(var.getresep_pulang());
        MnNoResep.setEnabled(var.getresep_obat());
        MnDiet.setEnabled(var.getdiet_pasien());
        MnPeriksaLab.setEnabled(var.getperiksa_lab());
        MnPeriksaRadiologi.setEnabled(var.getperiksa_radiologi());
        MnOperasi.setEnabled(var.getoperasi());
        MnDeposit.setEnabled(var.getdeposit_pasien());
        MnStokObatPasien.setEnabled(var.getstok_obat_pasien());
        MnResepPulang.setEnabled(var.getresep_pulang());
        MnRujuk.setEnabled(var.getrujukan_keluar());
        MnRujukMasuk.setEnabled(var.getrujukan_masuk());
        MnHapusTagihanOperasi.setEnabled(var.getoperasi());
        MnHapusObatOperasi.setEnabled(var.getoperasi());
        MnPenjab.setEnabled(var.getkamar_inap());
        MnStatusRujuk.setEnabled(var.getkamar_inap());
        MnStatusAPS.setEnabled(var.getkamar_inap());
        MnRanapGabung.setEnabled(var.getkamar_inap());
        MnStatusMeninggal.setEnabled(var.getkamar_inap());
        MnStatusMembaik.setEnabled(var.getkamar_inap());
        MnUpdateHari.setEnabled(var.getkamar_inap());
        MnDiagnosa.setEnabled(var.getdiagnosa_pasien());
        MnDPJP.setEnabled(var.getdpjp_ranap());
        ppRiwayat.setEnabled(var.getresume_pasien());
        ppCatatanPasien.setEnabled(var.getcatatan_pasien());
        ppDataHAIs.setEnabled(var.getdata_HAIs());
        MnSEP.setEnabled(var.getbpjs_sep());
        ppBerkasDigital.setEnabled(var.getberkas_digital_perawatan());
        ppIKP.setEnabled(var.getinsiden_keselamatan_pasien());
        MnJadwalOperasi.setEnabled(var.getbooking_operasi());
        MnSKDPBPJS.setEnabled(var.getskdp_bpjs());
        MnPermintaanLab.setEnabled(var.getpermintaan_lab());
        MnPermintaanRadiologi.setEnabled(var.getpermintaan_radiologi());
        MnPermintaanKerohanian.setEnabled(var.getpasien_meninggal());
        MnPermintaanMPP.setEnabled(var.getpasien_meninggal());
        MnPermintaanUTD.setEnabled(var.getpermintaan_lab());
        if (var.getkode().equals("Admin Utama")) {
            MnHapusDataSalah.setEnabled(true);
            MnLapCov19V3.setVisible(true);  
        } else { 
            if (aktifkan_hapus_data_salah.equals("Yes")) {
                MnHapusDataSalah.setEnabled(true);
            } else {
                MnHapusDataSalah.setEnabled(false);
            }
        }
        Rganti1.setVisible(false);
        Rganti4.setVisible(false);
        MnAssesmentAwal.setVisible(false);
    }

    private void updateHari() {
        if ((R1.isSelected() == true) && (var.getstatus() == false)) {
            for (i = 0; i < tbKamIn.getRowCount(); i++) {
                if (tbKamIn.getValueAt(i, 14).toString().equals("")) {
                    if (hariawal.equals("Yes")) {
                        Sequel.mengedit(" kamar_inap ", " no_rawat='" + tbKamIn.getValueAt(i, 0).toString() + "' and "
                                + " kd_kamar='" + Sequel.cariIsi("select kd_kamar from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where concat(kamar.kd_kamar,' ',bangsal.nm_bangsal)=? ", tbKamIn.getValueAt(i, 8).toString()) + "' "
                                + " and tgl_masuk='" + tbKamIn.getValueAt(i, 12).toString() + "' and jam_masuk='" + tbKamIn.getValueAt(i, 13).toString() + "'",
                                " lama=if(to_days(NOW())-to_days(concat(tgl_masuk,' ',jam_masuk))=0,if(time_to_sec(NOW())-time_to_sec(concat(tgl_masuk,' ',jam_masuk))>(3600*" + lama + "),1,0),to_days(NOW())-to_days(concat(tgl_masuk,' ',jam_masuk)))+1,"
                                + " ttl_biaya=(if(to_days(NOW())-to_days(concat(tgl_masuk,' ',jam_masuk))=0,if(time_to_sec(NOW())-time_to_sec(concat(tgl_masuk,' ',jam_masuk))>(3600*" + lama + "),1,0),to_days(NOW())-to_days(concat(tgl_masuk,' ',jam_masuk)))+1)*trf_kamar");
                    } else {
                        Sequel.mengedit(" kamar_inap ", " no_rawat='" + tbKamIn.getValueAt(i, 0).toString() + "' and "
                                + " kd_kamar='" + Sequel.cariIsi("select kd_kamar from kamar inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal where concat(kamar.kd_kamar,' ',bangsal.nm_bangsal)=? ", tbKamIn.getValueAt(i, 8).toString()) + "' "
                                + " and tgl_masuk='" + tbKamIn.getValueAt(i, 12).toString() + "' and jam_masuk='" + tbKamIn.getValueAt(i, 13).toString() + "'",
                                " lama=if(to_days(NOW())-to_days(concat(tgl_masuk,' ',jam_masuk))=0,if(time_to_sec(NOW())-time_to_sec(concat(tgl_masuk,' ',jam_masuk))>(3600*" + lama + "),1,0),to_days(NOW())-to_days(concat(tgl_masuk,' ',jam_masuk))),"
                                + " ttl_biaya=if(to_days(NOW())-to_days(concat(tgl_masuk,' ',jam_masuk))=0,if(time_to_sec(NOW())-time_to_sec(concat(tgl_masuk,' ',jam_masuk))>(3600*" + lama + "),1,0),to_days(NOW())-to_days(concat(tgl_masuk,' ',jam_masuk)))*trf_kamar");
                    }
                }
            }
        }
        tampil();
    }

    public void dpjp_oto() {
        row = tbKamIn.getRowCount();
        for (i = 0; i < row; i++) {
            try {
                psdpjp = koneksi.prepareStatement("select dokter.nm_dokter from dpjp_ranap inner join dokter "
                        + "on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat=? ");
                dokterranap = "";
                try {
                    psdpjp.setString(1, tbKamIn.getValueAt(i, 0).toString());
                    rs = psdpjp.executeQuery();
                    while (rs.next()) {
                        dokterranap = rs.getString("nm_dokter") + ", " + dokterranap;
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : " + e);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (psdpjp != null) {
                        psdpjp.close();
                    }
                }
                tbKamIn.setValueAt(dokterranap, i, 19);
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        }
    }

    public void setCariKosong() {
        TCari.setText("");
    }

    public JTable getTable() {
        return tbKamIn;
    }

    private void panggilobat(String norawat) {
        if (Sequel.cariInteger("select count(no_rawat) from stok_obat_pasien where no_rawat=? ", norawat) > 0) {
            billing.beriobat.dlgobt2.setNoRm(norawat, DTPCari1.getDate());
            billing.beriobat.dlgobt2.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
            billing.beriobat.dlgobt2.setLocationRelativeTo(internalFrame1);
            billing.beriobat.dlgobt2.setVisible(true);
        } else {
            billing.beriobat.dlgobt.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
            billing.beriobat.dlgobt.setLocationRelativeTo(internalFrame1);
            billing.beriobat.dlgobt.setNoRm(norawat, DTPCari1.getDate(), cmbJam.getSelectedItem().toString(), cmbMnt.getSelectedItem().toString(), cmbDtk.getSelectedItem().toString(), false);
            billing.beriobat.dlgobt.isCek();
            billing.beriobat.dlgobt.tampil();
            billing.beriobat.dlgobt.setVisible(true);
        }
    }
    
    private String isReport(String value) {
        String query = "select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "
                + "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) as alamat, pasien.gol_darah, pasien.pekerjaan,"
                + "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur, pasien.no_peserta,"
                + "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj, "
                + "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj, suku_bangsa.nama_suku_bangsa from pasien "
                + "inner join suku_bangsa inner join kelurahan inner join kecamatan inner join kabupaten "
                + "inner join penjab inner join propinsi on pasien.kd_prop=propinsi.kd_prop and pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "
                + "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab and suku_bangsa.id=pasien.suku_bangsa where pasien.no_rkm_medis='" + value + "' ";
        return query;
    }
}
