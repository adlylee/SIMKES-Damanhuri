package bridging;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgJadwal;
import kepegawaian.DlgJadwalPegawai;
import simrskhanza.DlgCariPoli;

/**
 *
 * @author dosen
 */
public class DlgSKDPBPJS extends javax.swing.JDialog {

    private final DefaultTableModel tabMode;
    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Properties prop = new Properties();
    private PreparedStatement ps, ps2;
    private ResultSet rs, rs2;
    private int i = 0, seppost=0;
    private DlgCariDokter dokter = new DlgCariDokter(null, false);
//    private DlgCariPoli poli = new DlgCariPoli(null, false);
    private DlgJadwal poli = new DlgJadwal(null, false);
    private BPJSSuratKontrol kontrol = new BPJSSuratKontrol(null, false);
    private String URUTNOREG = "",tglSetelah85Hari = "", status = "",set_status_rawat = "", kdpoli = "", nmpoli = "", noantri = "", antrian = "", user = "", nosep = "", nosepCari = "", penjab = "", diag, kddokter = "", norujuk = "", norujukCari = "",tglrujukan="",hari="";

    /**
     * Creates new form DlgPemberianInfus
     *
     * @param parent
     * @param modal
     */
    public DlgSKDPBPJS(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode = new DefaultTableModel(null, new Object[]{
            "Tahun", "No.RM", "Nama Pasien", "Diagnosa", "Terapi", "Alasan 1", "Alasan 2",
            "Rencana Tindak Lanjut 1", "Rencana Tindak Lanjut 2", "Periksa Kembali",
            "Tanggal Surat", "No.Surat", "No.Reg", "Kode Dokter", "Nama Dokter",
            "Kode Poli", "Nama Poli", "Status"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 18; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 1) {
                column.setPreferredWidth(80);
            } else if (i == 2) {
                column.setPreferredWidth(150);
            } else if (i == 3) {
                column.setPreferredWidth(150);
            } else if (i == 4) {
                column.setPreferredWidth(150);
            } else if (i == 5) {
                column.setPreferredWidth(150);
            } else if (i == 6) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 7) {
                column.setPreferredWidth(150);
            } else if (i == 8) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 9) {
                column.setPreferredWidth(82);
            } else if (i == 10) {
                column.setPreferredWidth(75);
            } else if (i == 11) {
                column.setPreferredWidth(52);
            } else if (i == 12) {
                column.setPreferredWidth(45);
            } else if (i == 13) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 14) {
                column.setPreferredWidth(150);
            } else if (i == 15) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 16) {
                column.setPreferredWidth(150);
            } else if (i == 17) {
                column.setPreferredWidth(90);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRM.setDocument(new batasInput((byte) 15).getKata(TNoRM));
        TCari.setDocument(new batasInput((byte) 100).getKata(TCari));
        Diagnosa.setDocument(new batasInput((int) 50).getKata(Diagnosa));
//        Terapi.setDocument(new batasInput((int) 50).getKata(Terapi));
        Alasan1.setDocument(new batasInput((int) 50).getKata(Alasan1));
        Alasan2.setDocument(new batasInput((int) 50).getKata(Alasan2));
        Rtl1.setDocument(new batasInput((int) 50).getKata(Rtl1));
        Rtl2.setDocument(new batasInput((int) 50).getKata(Rtl2));
        NoReg.setDocument(new batasInput((byte) 6).getKata(NoReg));
        KdDokter.setDocument(new batasInput((byte) 20).getKata(KdDokter));
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

        ChkInput.setSelected(false);
        isForm();

        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (dokter.getTable().getSelectedRow() != -1) {
                    KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                    NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                    isNomer();
                    cekHfisDokter(KdDokter.getText());
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

        dokter.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    dokter.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
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
                if (poli.getTable().getSelectedRow() != -1) {
//                    KdPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(), 0).toString());
                    NmPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(), 6).toString());
                    isNomer();
                    KdPoli.setText(Sequel.cariIsi("select kd_poli from poliklinik where nm_poli=?",NmPoli.getText()));
                    cekHfisPoli(KdPoli.getText());
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

        poli.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    poli.dispose();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            URUTNOREG = prop.getProperty("URUTNOREG");
        } catch (Exception ex) {
            URUTNOREG = "";
        }

        try {
            user = var.getkode().replace(" ", "").substring(0, 9);
        } catch (Exception e) {
            user = var.getkode();
        }

        jLabel8.setVisible(false);
        Alasan2.setVisible(false);
        jLabel13.setVisible(false);
        Rtl2.setVisible(false);
        KdDokter1.setVisible(false);
        NmDokter1.setVisible(false);
        KdPoli1.setVisible(false);
        NmPoli1.setVisible(false);

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
        MnSurat = new javax.swing.JMenuItem();
        NoSEP = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass10 = new widget.panelisi();
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
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRM = new widget.TextBox();
        jLabel9 = new widget.Label();
        NmDokter = new widget.TextBox();
        TPasien = new widget.TextBox();
        TanggalSurat = new widget.Tanggal();
        Status = new widget.ComboBox();
        jLabel10 = new widget.Label();
        KdDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel37 = new widget.Label();
        jLabel11 = new widget.Label();
        KdPoli = new widget.TextBox();
        NmPoli = new widget.TextBox();
        BtnPoli = new widget.Button();
        jLabel5 = new widget.Label();
        Alasan1 = new widget.TextBox();
        Alasan2 = new widget.TextBox();
        jLabel8 = new widget.Label();
        jLabel12 = new widget.Label();
        Rtl1 = new widget.TextBox();
        jLabel13 = new widget.Label();
        Rtl2 = new widget.TextBox();
        jLabel14 = new widget.Label();
        TanggalPeriksa = new widget.Tanggal();
        jLabel15 = new widget.Label();
        NoSurat = new widget.TextBox();
        Diagnosa = new widget.TextBox();
        jLabel16 = new widget.Label();
        jLabel17 = new widget.Label();
        Terapi = new widget.TextBox();
        NoReg = new widget.TextBox();
        jLabel18 = new widget.Label();
        KdDokter1 = new widget.TextBox();
        NmDokter1 = new widget.TextBox();
        KdPoli1 = new widget.TextBox();
        NmPoli1 = new widget.TextBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnSurat.setBackground(new java.awt.Color(255, 255, 254));
        MnSurat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSurat.setForeground(new java.awt.Color(70, 70, 70));
        MnSurat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSurat.setText("Surat SKDP BPJS");
        MnSurat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSurat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSurat.setName("MnSurat"); // NOI18N
        MnSurat.setPreferredSize(new java.awt.Dimension(180, 26));
        MnSurat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSuratActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSurat);

        NoSEP.setName("NoSEP"); // NOI18N
        NoSEP.setPreferredSize(new java.awt.Dimension(207, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ SKDP BPJS ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setAutoCreateRowSorter(true);
        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(jPopupMenu1);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbObatKeyReleased(evt);
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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass10.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(450, 23));
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

        R1.setBackground(new java.awt.Color(240, 250, 230));
        R1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.pink));
        buttonGroup1.add(R1);
        R1.setSelected(true);
        R1.setText("Menunggu");
        R1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R1.setName("R1"); // NOI18N
        R1.setPreferredSize(new java.awt.Dimension(85, 23));
        panelCari.add(R1);

        R2.setBackground(new java.awt.Color(240, 250, 230));
        R2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.pink));
        buttonGroup1.add(R2);
        R2.setText("Tanggal :");
        R2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R2.setName("R2"); // NOI18N
        R2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelCari.add(R2);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-10-2023" }));
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

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-10-2023" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPCari2KeyPressed(evt);
            }
        });
        panelCari.add(DTPCari2);

        R3.setBackground(new java.awt.Color(240, 250, 230));
        R3.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.pink));
        buttonGroup1.add(R3);
        R3.setText("Selesai :");
        R3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        R3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        R3.setName("R3"); // NOI18N
        R3.setPreferredSize(new java.awt.Dimension(85, 23));
        panelCari.add(R3);

        DTPCari3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-10-2023" }));
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

        DTPCari4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-10-2023" }));
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

        jPanel3.add(panelCari, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 216));
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
        jLabel4.setBounds(0, 10, 92, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(96, 10, 87, 23);

        jLabel9.setText("Dokter :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 130, 92, 23);

        NmDokter.setEditable(false);
        NmDokter.setHighlighter(null);
        NmDokter.setName("NmDokter"); // NOI18N
        FormInput.add(NmDokter);
        NmDokter.setBounds(185, 130, 160, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TPasienActionPerformed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(185, 10, 190, 23);

        TanggalSurat.setEditable(false);
        TanggalSurat.setForeground(new java.awt.Color(50, 70, 50));
        TanggalSurat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-10-2023 10:09:56" }));
        TanggalSurat.setDisplayFormat("dd-MM-yyyy hh:mm:ss");
        TanggalSurat.setName("TanggalSurat"); // NOI18N
        TanggalSurat.setOpaque(false);
        TanggalSurat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalSuratKeyPressed(evt);
            }
        });
        FormInput.add(TanggalSurat);
        TanggalSurat.setBounds(490, 10, 132, 23);

        Status.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Menunggu", "Sudah Periksa", "Batal Periksa" }));
        Status.setName("Status"); // NOI18N
        Status.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StatusKeyPressed(evt);
            }
        });
        FormInput.add(Status);
        Status.setBounds(620, 100, 130, 23);

        jLabel10.setText("Tanggal Surat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(390, 10, 95, 23);

        KdDokter.setEditable(false);
        KdDokter.setHighlighter(null);
        KdDokter.setName("KdDokter"); // NOI18N
        FormInput.add(KdDokter);
        KdDokter.setBounds(96, 130, 87, 23);

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
        BtnDokter.setBounds(347, 130, 28, 23);

        jLabel37.setText("Status :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(550, 100, 60, 23);

        jLabel11.setText("Unit/Poli :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(390, 130, 95, 23);

        KdPoli.setEditable(false);
        KdPoli.setHighlighter(null);
        KdPoli.setName("KdPoli"); // NOI18N
        FormInput.add(KdPoli);
        KdPoli.setBounds(490, 130, 70, 23);

        NmPoli.setEditable(false);
        NmPoli.setHighlighter(null);
        NmPoli.setName("NmPoli"); // NOI18N
        FormInput.add(NmPoli);
        NmPoli.setBounds(560, 130, 165, 23);

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
        BtnPoli.setBounds(730, 130, 28, 23);

        jLabel5.setText("Alasan :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 70, 92, 23);

        Alasan1.setHighlighter(null);
        Alasan1.setName("Alasan1"); // NOI18N
        Alasan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Alasan1KeyPressed(evt);
            }
        });
        FormInput.add(Alasan1);
        Alasan1.setBounds(96, 70, 279, 23);

        Alasan2.setHighlighter(null);
        Alasan2.setName("Alasan2"); // NOI18N
        Alasan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Alasan2KeyPressed(evt);
            }
        });
        FormInput.add(Alasan2);
        Alasan2.setBounds(96, 160, 279, 23);

        jLabel8.setText("Alasan 2 :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(10, 160, 80, 23);

        jLabel12.setText("Tindak Lanjut :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(390, 70, 92, 23);

        Rtl1.setHighlighter(null);
        Rtl1.setName("Rtl1"); // NOI18N
        Rtl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Rtl1KeyPressed(evt);
            }
        });
        FormInput.add(Rtl1);
        Rtl1.setBounds(490, 70, 270, 23);

        jLabel13.setText("Tindak Lanjut 2 :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(390, 160, 95, 23);

        Rtl2.setHighlighter(null);
        Rtl2.setName("Rtl2"); // NOI18N
        Rtl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Rtl2KeyPressed(evt);
            }
        });
        FormInput.add(Rtl2);
        Rtl2.setBounds(490, 160, 266, 23);

        jLabel14.setText("Periksa Kembali :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(0, 100, 92, 23);

        TanggalPeriksa.setForeground(new java.awt.Color(50, 70, 50));
        TanggalPeriksa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "25-10-2023 10:09:56" }));
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
        TanggalPeriksa.setBounds(96, 100, 132, 23);

        jLabel15.setText("No.Surat :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(226, 100, 60, 23);

        NoSurat.setEditable(false);
        NoSurat.setEnabled(false);
        NoSurat.setHighlighter(null);
        NoSurat.setName("NoSurat"); // NOI18N
        NoSurat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoSuratKeyPressed(evt);
            }
        });
        FormInput.add(NoSurat);
        NoSurat.setBounds(290, 100, 85, 23);

        Diagnosa.setEditable(false);
        Diagnosa.setHighlighter(null);
        Diagnosa.setName("Diagnosa"); // NOI18N
        Diagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaKeyPressed(evt);
            }
        });
        FormInput.add(Diagnosa);
        Diagnosa.setBounds(96, 40, 279, 23);

        jLabel16.setText("Diagnosa :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 92, 23);

        jLabel17.setText("Terapi :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(390, 40, 95, 23);

        Terapi.setHighlighter(null);
        Terapi.setName("Terapi"); // NOI18N
        Terapi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TerapiKeyPressed(evt);
            }
        });
        FormInput.add(Terapi);
        Terapi.setBounds(490, 40, 266, 23);

        NoReg.setHighlighter(null);
        NoReg.setName("NoReg"); // NOI18N
        NoReg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRegKeyPressed(evt);
            }
        });
        FormInput.add(NoReg);
        NoReg.setBounds(685, 10, 70, 23);

        jLabel18.setText("No.Reg :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(620, 10, 60, 23);

        KdDokter1.setEditable(false);
        KdDokter1.setHighlighter(null);
        KdDokter1.setName("KdDokter1"); // NOI18N
        FormInput.add(KdDokter1);
        KdDokter1.setBounds(96, 130, 87, 23);

        NmDokter1.setEditable(false);
        NmDokter1.setHighlighter(null);
        NmDokter1.setName("NmDokter1"); // NOI18N
        FormInput.add(NmDokter1);
        NmDokter1.setBounds(185, 130, 160, 23);

        KdPoli1.setEditable(false);
        KdPoli1.setHighlighter(null);
        KdPoli1.setName("KdPoli1"); // NOI18N
        FormInput.add(KdPoli1);
        KdPoli1.setBounds(490, 130, 70, 23);

        NmPoli1.setEditable(false);
        NmPoli1.setHighlighter(null);
        NmPoli1.setName("NmPoli1"); // NOI18N
        FormInput.add(NmPoli1);
        NmPoli1.setBounds(560, 130, 165, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        Valid.pindah(evt, Status, KdDokter);

}//GEN-LAST:event_TNoRMKeyPressed

    private void TanggalSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalSuratKeyPressed
        Valid.pindah(evt, TCari, Status);
}//GEN-LAST:event_TanggalSuratKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        antrian = NoSurat.getText();
        String nomer = "",jns_pelayanan = "2";
        tglrujukan="";
        if (penjab.equals("BPJ") || penjab.equals("A02")) {
            if (set_status_rawat.equals("Ranap")) {
                jns_pelayanan = "1";
            }
            seppost = Sequel.cariInteger("select count(bridging_sep.no_sep) from bridging_sep, (select no_rujukan from bridging_sep where no_sep='" + nosep + "' and jnspelayanan='"+jns_pelayanan+"') as nocarirujuk where bridging_sep.no_sep=nocarirujuk.no_rujukan");
            if (seppost >= 1) {
                tglrujukan = Sequel.cariIsi("SELECT tglrujukan FROM bridging_sep WHERE jnspelayanan = '"+jns_pelayanan+"' and nomr = '" + TNoRM.getText() + "' AND tglsep < (SELECT MAX(tglsep) FROM bridging_sep WHERE nomr = '" + TNoRM.getText() + "' AND jnspelayanan = '"+jns_pelayanan+"') ORDER BY tglsep DESC LIMIT 1");
            } else {
                tglrujukan = Sequel.cariIsi("select tglrujukan from bridging_sep where no_sep='" + nosep + "'");
            }
            System.out.println("tglrujukan "+tglrujukan);
            LocalDate tglRujukanmulai = LocalDate.parse(tglrujukan);
            LocalDate tglRujukanakhir = tglRujukanmulai.plusDays(89);
            tglSetelah85Hari = tglRujukanakhir.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
        int cari = Sequel.cariInteger("select DATEDIFF('" + Valid.SetTgl(TanggalPeriksa.getSelectedItem() + "") + "','" + tglrujukan + "')");
        int caribooking = Sequel.cariInteger("select count(no_rkm_medis) from booking_registrasi where kd_pj='BPJ' and no_rkm_medis='" + TNoRM.getText() + "' and tanggal_periksa='" + Valid.SetTgl(TanggalPeriksa.getSelectedItem() + "") + "'");
        if (TNoRM.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRM, "pasien");
        } else if (NmDokter.getText().trim().equals("") || KdDokter.getText().trim().equals("")) {
            Valid.textKosong(KdDokter, "Operator");
        } else if (NmPoli.getText().trim().equals("") || KdPoli.getText().trim().equals("")) {
            Valid.textKosong(KdPoli, "Unit/Poli");
        } else if (NoSurat.getText().trim().equals("")) {
            Valid.textKosong(NoSurat, "No.Surat");
        } else if (NoReg.getText().trim().equals("")) {
            Valid.textKosong(NoReg, "No.Antri");
        } else if (Terapi.getText().trim().equals("")) {
            Valid.textKosong(Terapi, "Terapi");
        } else if (Diagnosa.getText().trim().equals("")) {
            Valid.textKosong(Diagnosa, "Diagnosa");
        } else if (compareDates(Valid.SetTgl(TanggalPeriksa.getSelectedItem() + ""), Valid.SetTgl(TanggalSurat.getSelectedItem() + "")) == false) {
            JOptionPane.showMessageDialog(null, "Tanggal Periksa Tidak Boleh Kurang Dari Atau Sama Dengan Hari Ini");
        } else {
            while (true) {
                if (Sequel.cariInteger("select count(no_antrian) from skdp_bpjs where tahun='" + TanggalPeriksa.getSelectedItem().toString().substring(6, 10) + "' and no_antrian = '" + antrian + "'") > 0) {
                    antrian = Integer.toString(Integer.parseInt(antrian) + 1);
                    for (int k = 1; k <= 6 - antrian.length(); k++) {
                        nomer = nomer + "0";
                    }
                    antrian = nomer + antrian;
                } else {
                    antrian = antrian;
                    break;
                }
            }
            
            if (set_status_rawat.equals("Ranap")) {
                if (penjab.equals("BPJ") || penjab.equals("A02")) {
                    nomer = kontrol.simpanSurat(nosep, Valid.SetTgl(TanggalPeriksa.getSelectedItem() + ""), Valid.SetTgl(TanggalSurat.getSelectedItem() + ""), KdDokter1.getText(), NmDokter1.getText(), KdPoli1.getText(), NmPoli1.getText(), user);
                    if (!nomer.equals("")) {
                        if (Sequel.menyimpantf("skdp_bpjs", "?,?,?,?,?,?,?,?,?,?,?,?,?", "Tahun dan nomor surat", 13, new String[]{
                            TanggalPeriksa.getSelectedItem().toString().substring(6, 10), TNoRM.getText(), Diagnosa.getText(), Terapi.getText(),
                            Alasan1.getText(), nomer, Rtl1.getText(), nosep, Valid.SetTgl(TanggalPeriksa.getSelectedItem() + ""),
                            Valid.SetTgl(TanggalSurat.getSelectedItem() + ""), antrian, KdDokter.getText(), Status.getSelectedItem().toString()
                        }) == true) {
                            System.out.println("Simpan SKDP Berhasil");
                            Sequel.menyimpan2("booking_registrasi", "?,?,?,?,?,?,?,?,?,?,?", "Pasien dan Tanggal", 11, new String[]{
                                Valid.SetTgl(TanggalSurat.getSelectedItem() + ""), TanggalSurat.getSelectedItem().toString().substring(11, 19), TNoRM.getText(),
                                Valid.SetTgl(TanggalPeriksa.getSelectedItem() + ""), KdDokter.getText(),
                                KdPoli.getText(), NoReg.getText(), Sequel.cariIsi("select kd_pj from pasien where no_rkm_medis=?", TNoRM.getText()), "0",
                                Valid.SetTgl(TanggalPeriksa.getSelectedItem() + "") + " " + TanggalPeriksa.getSelectedItem().toString().substring(11, 19),
                                "belum"
                            });
                            JOptionPane.showMessageDialog(null, "Berhasil Simpan");
                            emptTeks();
                            tampil();
                        }
                        }
                    if (nomer.equals("201")) {
                        if (Sequel.menyimpantf("skdp_bpjs", "?,?,?,?,?,?,?,?,?,?,?,?,?", "Tahun dan nomor surat", 13, new String[]{
                            TanggalPeriksa.getSelectedItem().toString().substring(6, 10), TNoRM.getText(), Diagnosa.getText(), Terapi.getText(),
                            Alasan1.getText(), nomer, Rtl1.getText(), nosep, Valid.SetTgl(TanggalPeriksa.getSelectedItem() + ""),
                            Valid.SetTgl(TanggalSurat.getSelectedItem() + ""), antrian, KdDokter.getText(), Status.getSelectedItem().toString()
                        }) == true) {
                            System.out.println("Simpan SKDP Berhasil-");
                            Sequel.menyimpan2("booking_registrasi", "?,?,?,?,?,?,?,?,?,?,?", "Pasien dan Tanggal", 11, new String[]{
                                Valid.SetTgl(TanggalSurat.getSelectedItem() + ""), TanggalSurat.getSelectedItem().toString().substring(11, 19), TNoRM.getText(),
                                Valid.SetTgl(TanggalPeriksa.getSelectedItem() + ""), KdDokter.getText(),
                                KdPoli.getText(), NoReg.getText(), Sequel.cariIsi("select kd_pj from pasien where no_rkm_medis=?", TNoRM.getText()), "0",
                                Valid.SetTgl(TanggalPeriksa.getSelectedItem() + "") + " " + TanggalPeriksa.getSelectedItem().toString().substring(11, 19),
                                "belum"
                            });
                            JOptionPane.showMessageDialog(null, "Berhasil Simpan-");
                            emptTeks();
                            tampil();
                        }
                        }
                    if (nomer.equals("203")) {
                        String tgl = Sequel.cariIsi("SELECT tgl_rencana FROM bridging_surat_kontrol_bpjs where no_sep = ?",nosep);
                        if (tgl == TanggalPeriksa.getSelectedItem().toString().substring(6, 10)) {
                            if (Sequel.menyimpantf("skdp_bpjs", "?,?,?,?,?,?,?,?,?,?,?,?,?", "Tahun dan nomor surat", 13, new String[]{
                            TanggalPeriksa.getSelectedItem().toString().substring(6, 10), TNoRM.getText(), Diagnosa.getText(), Terapi.getText(),
                            Alasan1.getText(), nomer, Rtl1.getText(), nosep, Valid.SetTgl(TanggalPeriksa.getSelectedItem() + ""),
                            Valid.SetTgl(TanggalSurat.getSelectedItem() + ""), antrian, KdDokter.getText(), Status.getSelectedItem().toString()
                            }) == true) {
                                System.out.println("Simpan SKDP Berhasil-");
                                Sequel.menyimpan2("booking_registrasi", "?,?,?,?,?,?,?,?,?,?,?", "Pasien dan Tanggal", 11, new String[]{
                                    Valid.SetTgl(TanggalSurat.getSelectedItem() + ""), TanggalSurat.getSelectedItem().toString().substring(11, 19), TNoRM.getText(),
                                    Valid.SetTgl(TanggalPeriksa.getSelectedItem() + ""), KdDokter.getText(),
                                    KdPoli.getText(), NoReg.getText(), Sequel.cariIsi("select kd_pj from pasien where no_rkm_medis=?", TNoRM.getText()), "0",
                                    Valid.SetTgl(TanggalPeriksa.getSelectedItem() + "") + " " + TanggalPeriksa.getSelectedItem().toString().substring(11, 19),
                                    "belum"
                                });
                                JOptionPane.showMessageDialog(null, "Berhasil Simpan-");
                                emptTeks();
                                tampil();
                            }
                        }
                    }
                } else {
                    if (Sequel.menyimpantf("skdp_bpjs", "?,?,?,?,?,?,?,?,?,?,?,?,?", "Tahun dan nomor surat", 13, new String[]{
                            TanggalPeriksa.getSelectedItem().toString().substring(6, 10), TNoRM.getText(), Diagnosa.getText(), Terapi.getText(),
                            Alasan1.getText(), nomer, Rtl1.getText(), nosep, Valid.SetTgl(TanggalPeriksa.getSelectedItem() + ""),
                            Valid.SetTgl(TanggalSurat.getSelectedItem() + ""), antrian, KdDokter.getText(), Status.getSelectedItem().toString()
                        }) == true) {
                            System.out.println("Simpan SKDP Berhasil");
                            Sequel.menyimpan2("booking_registrasi", "?,?,?,?,?,?,?,?,?,?,?", "Pasien dan Tanggal", 11, new String[]{
                                Valid.SetTgl(TanggalSurat.getSelectedItem() + ""), TanggalSurat.getSelectedItem().toString().substring(11, 19), TNoRM.getText(),
                                Valid.SetTgl(TanggalPeriksa.getSelectedItem() + ""), KdDokter.getText(),
                                KdPoli.getText(), NoReg.getText(), Sequel.cariIsi("select kd_pj from pasien where no_rkm_medis=?", TNoRM.getText()), "0",
                                Valid.SetTgl(TanggalPeriksa.getSelectedItem() + "") + " " + TanggalPeriksa.getSelectedItem().toString().substring(11, 19),
                                "belum"
                            });
                            JOptionPane.showMessageDialog(null, "Berhasil Simpan");
                            emptTeks();
                            tampil();
                        }
                }
            } else {
                if (KdPoli.getText().equals("U0019")) {
                    if (Sequel.menyimpantf("skdp_bpjs", "?,?,?,?,?,?,?,?,?,?,?,?,?", "Tahun dan nomor surat", 13, new String[]{
                        TanggalPeriksa.getSelectedItem().toString().substring(6, 10), TNoRM.getText(), Diagnosa.getText(), Terapi.getText(),
                        Alasan1.getText(), nomer, Rtl1.getText(), nosep, Valid.SetTgl(TanggalPeriksa.getSelectedItem() + ""),
                        Valid.SetTgl(TanggalSurat.getSelectedItem() + ""), antrian, KdDokter.getText(), Status.getSelectedItem().toString()
                    }) == true) {
                        System.out.println("Simpan SKDP Berhasil");
                        Sequel.menyimpan2("booking_registrasi", "?,?,?,?,?,?,?,?,?,?,?", "Pasien dan Tanggal", 11, new String[]{
                            Valid.SetTgl(TanggalSurat.getSelectedItem() + ""), TanggalSurat.getSelectedItem().toString().substring(11, 19), TNoRM.getText(),
                            Valid.SetTgl(TanggalPeriksa.getSelectedItem() + ""), KdDokter.getText(),
                            KdPoli.getText(), NoReg.getText(), Sequel.cariIsi("select kd_pj from pasien where no_rkm_medis=?", TNoRM.getText()), "0",
                            Valid.SetTgl(TanggalPeriksa.getSelectedItem() + "") + " " + TanggalPeriksa.getSelectedItem().toString().substring(11, 19),
                            "belum"
                        });
                        JOptionPane.showMessageDialog(null, "Berhasil Simpan");
                        emptTeks();
                        tampil();
                    }
                }
                if (!KdPoli.getText().equals("U0019")) {
                    LocalDate today = LocalDate.now();
                    if (!norujuk.contains("1708R008")) {
                        System.out.println("!ini");
                        if (tglrujukan.isEmpty() || tglrujukan == null) {
                            JOptionPane.showMessageDialog(null, "Maaf, SEP pasien belum tersedia...!!");
                        } else {
                            if (seppost >= 1) {
                                JOptionPane.showMessageDialog(rootPane, "Maaf, Rujukan pasien belum tersedia. \nSilahkan meminta rujukan ke Faskes..!!");
                            } else {
                                if (cari > 89) {
                                    JOptionPane.showMessageDialog(rootPane, "Maaf, masa rujukan pasien telah habis. Masa berlaku rujukan " + tglrujukan + " s/d " + tglSetelah85Hari + ". \nSilahkan meminta rujukan kembali untuk berobat ke Rumah Sakit.. !!");
                                } else {
                                    if (caribooking > 0) {
                                        String tglperiksa = Sequel.cariIsi("SELECT tanggal_periksa FROM booking_registrasi WHERE no_rkm_medis=? order by tanggal_periksa DESC LIMIT 1", TNoRM.getText());
                                        String poli = Sequel.cariIsi("SELECT poliklinik.nm_poli FROM booking_registrasi inner join poliklinik on booking_registrasi.kd_poli=poliklinik.kd_poli where booking_registrasi.no_rkm_medis='" + TNoRM.getText() + "' and booking_registrasi.tanggal_periksa='" + Valid.SetTgl(TanggalPeriksa.getSelectedItem() + "") + "'");
                                        JOptionPane.showMessageDialog(rootPane, "Maaf, pasien telah memiliki jadwal periksa " + poli + " di tanggal " + Valid.SetTgl3(tglperiksa) + ".\nSilahkan pilih tanggal periksa lain.. !!");
                                    } else {
                                        if (Sequel.menyimpantf("skdp_bpjs", "?,?,?,?,?,?,?,?,?,?,?,?,?", "Tahun dan nomor surat", 13, new String[]{
                                            TanggalPeriksa.getSelectedItem().toString().substring(6, 10), TNoRM.getText(), Diagnosa.getText(), Terapi.getText(),
                                            Alasan1.getText(), nomer, Rtl1.getText(), nosep, Valid.SetTgl(TanggalPeriksa.getSelectedItem() + ""),
                                            Valid.SetTgl(TanggalSurat.getSelectedItem() + ""), antrian, KdDokter.getText(), Status.getSelectedItem().toString()
                                        }) == true) {
                                            Sequel.menyimpan2("booking_registrasi", "?,?,?,?,?,?,?,?,?,?,?", "Pasien dan Tanggal", 11, new String[]{
                                                Valid.SetTgl(TanggalSurat.getSelectedItem() + ""), TanggalSurat.getSelectedItem().toString().substring(11, 19), TNoRM.getText(),
                                                Valid.SetTgl(TanggalPeriksa.getSelectedItem() + ""), KdDokter.getText(),
                                                KdPoli.getText(), NoReg.getText(), Sequel.cariIsi("select kd_pj from pasien where no_rkm_medis=?", TNoRM.getText()), "0",
                                                Valid.SetTgl(TanggalPeriksa.getSelectedItem() + "") + " " + TanggalPeriksa.getSelectedItem().toString().substring(11, 19),
                                                "belum"
                                            });
                                            JOptionPane.showMessageDialog(null, "Berhasil Simpan");
                                            emptTeks();
                                            tampil();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (norujuk.contains("1708R008")) {
                        System.out.println("ini");
                        if (cari > 89) {
                            JOptionPane.showMessageDialog(rootPane, "Maaf, masa rujukan pasien telah habis. Masa berlaku rujukan " + tglrujukan + " s/d " + tglSetelah85Hari + ". \nSilahkan meminta rujukan kembali untuk berobat ke Rumah Sakit.. !!");
                        } else {
                            if (caribooking > 0) {
                                String tglperiksa = Sequel.cariIsi("SELECT tanggal_periksa FROM booking_registrasi WHERE no_rkm_medis=? order by tanggal_periksa DESC LIMIT 1", TNoRM.getText());
                                String poli = Sequel.cariIsi("SELECT poliklinik.nm_poli FROM booking_registrasi inner join poliklinik on booking_registrasi.kd_poli=poliklinik.kd_poli where booking_registrasi.no_rkm_medis='" + TNoRM.getText() + "' and booking_registrasi.tanggal_periksa='" + Valid.SetTgl(TanggalPeriksa.getSelectedItem() + "") + "'");
                                JOptionPane.showMessageDialog(rootPane, "Maaf, pasien telah memiliki jadwal periksa " + poli + " di tanggal " + Valid.SetTgl3(tglperiksa) + ".\nSilahkan pilih tanggal periksa lain.. !!");
                            } else {
                                if (Sequel.menyimpantf("skdp_bpjs", "?,?,?,?,?,?,?,?,?,?,?,?,?", "Tahun dan nomor surat", 13, new String[]{
                                    TanggalPeriksa.getSelectedItem().toString().substring(6, 10), TNoRM.getText(), Diagnosa.getText(), Terapi.getText(),
                                    Alasan1.getText(), nomer, Rtl1.getText(), nosep, Valid.SetTgl(TanggalPeriksa.getSelectedItem() + ""),
                                    Valid.SetTgl(TanggalSurat.getSelectedItem() + ""), antrian, KdDokter.getText(), Status.getSelectedItem().toString()
                                }) == true) {
                                    Sequel.menyimpan2("booking_registrasi", "?,?,?,?,?,?,?,?,?,?,?", "Pasien dan Tanggal", 11, new String[]{
                                        Valid.SetTgl(TanggalSurat.getSelectedItem() + ""), TanggalSurat.getSelectedItem().toString().substring(11, 19), TNoRM.getText(),
                                        Valid.SetTgl(TanggalPeriksa.getSelectedItem() + ""), KdDokter.getText(),
                                        KdPoli.getText(), NoReg.getText(), Sequel.cariIsi("select kd_pj from pasien where no_rkm_medis=?", TNoRM.getText()), "0",
                                        Valid.SetTgl(TanggalPeriksa.getSelectedItem() + "") + " " + TanggalPeriksa.getSelectedItem().toString().substring(11, 19),
                                        "belum"
                                    });
                                    JOptionPane.showMessageDialog(null, "Berhasil Simpan");
                                    emptTeks();
                                    tampil();
                                }
                            }
                        }
                    }
                }
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, NoReg, BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            emptTeks();
        } else {
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TanggalSurat.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
        } else if (!(TPasien.getText().trim().equals(""))) {
            if (tbObat.getSelectedRow() != -1) {
                int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, Yakin mau dihapus..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    String surat = tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString();
                    kontrol.setNoSurat(surat);
                    if (Sequel.queryu2tf("delete from skdp_bpjs where tahun=? and no_antrian=?", 2, new String[]{
                        tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString(), tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString()
                    }) == true) {
                        Sequel.queryu2("delete from booking_registrasi where no_rkm_medis=? and tanggal_periksa=?", 2, new String[]{
                            tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString(), tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString()
                        });
                        tampil();
                        emptTeks();
                    }
                }                
            } else {
                JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih terlebih dulu data yang mau anda hapus...\n Klik data pada table untuk memilih data...!!!!");
            }
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnBatal, BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnPrint, TCari);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

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

            Sequel.queryu("truncate table temporary_booking_registrasi");
            for (i = 0; i < tabMode.getRowCount(); i++) {
                Sequel.menyimpan("temporary_booking_registrasi", "'0','"
                        + tabMode.getValueAt(i, 0).toString() + "','"
                        + tabMode.getValueAt(i, 1).toString() + "','"
                        + tabMode.getValueAt(i, 2).toString() + "','"
                        + tabMode.getValueAt(i, 3).toString() + "','"
                        + tabMode.getValueAt(i, 4).toString() + "','"
                        + tabMode.getValueAt(i, 5).toString() + "','"
                        + tabMode.getValueAt(i, 6).toString() + "','"
                        + tabMode.getValueAt(i, 7).toString() + "','"
                        + tabMode.getValueAt(i, 8).toString() + "','"
                        + tabMode.getValueAt(i, 9).toString() + "','"
                        + tabMode.getValueAt(i, 10).toString() + "','"
                        + tabMode.getValueAt(i, 11).toString() + "','"
                        + tabMode.getValueAt(i, 12).toString() + "','"
                        + tabMode.getValueAt(i, 13).toString() + "','"
                        + tabMode.getValueAt(i, 14).toString() + "','"
                        + tabMode.getValueAt(i, 15).toString() + "','"
                        + tabMode.getValueAt(i, 16).toString() + "','"
                        + tabMode.getValueAt(i, 17).toString() + "','','','','','','','','','','','','','','','','','','',''", "Rekap Nota Pembayaran");
            }

            Valid.MyReport("rptSKDPBPJS.jrxml", "report", "::[ Laporan Daftar SKDP BPJS ]::",
                    "select * from temporary_booking_registrasi order by no asc", param);
        }
        this.setCursor(Cursor.getDefaultCursor());
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
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            tampil();
            TCari.setText("");
        } else {
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
    dokter.isCek();
    dokter.TCari.requestFocus();
    dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
    dokter.setLocationRelativeTo(internalFrame1);
    dokter.setVisible(true);
}//GEN-LAST:event_BtnDokterActionPerformed

private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
        BtnDokterActionPerformed(null);
    } else {
        Valid.pindah(evt, Rtl2, BtnPoli);
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
        String nomer = "";
        if (TNoRM.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRM, "pasien");
        } else if (NmDokter.getText().trim().equals("") || KdDokter.getText().trim().equals("")) {
            Valid.textKosong(KdDokter, "Operator");
        } else if (NmPoli.getText().trim().equals("") || NmPoli.getText().trim().equals("")) {
            Valid.textKosong(KdPoli, "Operasi");
        } else if (NoSurat.getText().trim().equals("")) {
            Valid.textKosong(NoSurat, "No.Surat");
        } else if (NoReg.getText().trim().equals("")) {
            Valid.textKosong(NoReg, "No.Antri");
        } else if (Terapi.getText().trim().equals("")) {
            Valid.textKosong(Terapi, "Terapi");
        } else if (Diagnosa.getText().trim().equals("")) {
            Valid.textKosong(Diagnosa, "Diagnosa");
        } else {
            if (tbObat.getSelectedRow() != -1) {
                if (set_status_rawat.equals("Ranap")) {
                    if (penjab.equals("BPJ") || penjab.equals("A02")) {
                        nomer = kontrol.editSurat(Alasan2.getText(), Rtl2.getText(), KdDokter1.getText(), NmDokter1.getText(), KdPoli1.getText(), NmPoli1.getText(), Valid.SetTgl(TanggalSurat.getSelectedItem() + ""), Valid.SetTgl(TanggalPeriksa.getSelectedItem() + ""), user);
                        if (!nomer.equals("")) {
                            if (Sequel.mengedittf("skdp_bpjs", "tahun=? and no_antrian=?", "tahun=?,no_rkm_medis=?,diagnosa=?,terapi=?,alasan1=?,alasan2=?,rtl1=?,rtl2=?,tanggal_datang=?,tanggal_rujukan=?,no_antrian=?,kd_dokter=?,status=?", 15, new String[]{
                                TanggalPeriksa.getSelectedItem().toString().substring(6, 10), TNoRM.getText(), Diagnosa.getText(), Terapi.getText(),
                                Alasan1.getText(), Alasan2.getText(), Rtl1.getText(), Rtl2.getText(), Valid.SetTgl(TanggalPeriksa.getSelectedItem() + ""),
                                Valid.SetTgl(TanggalSurat.getSelectedItem() + ""), NoSurat.getText(), KdDokter.getText(), Status.getSelectedItem().toString(),
                                tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString(), tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString()
                            }) == true) {
                                Sequel.mengedit3("booking_registrasi", "no_rkm_medis=? and tanggal_periksa=?", "tanggal_booking=?,no_rkm_medis=?,tanggal_periksa=?,kd_dokter=?,kd_poli=?,no_reg=?", 8, new String[]{
                                    Valid.SetTgl(TanggalSurat.getSelectedItem() + ""), TNoRM.getText(),
                                    Valid.SetTgl(TanggalPeriksa.getSelectedItem() + ""), KdDokter.getText(),
                                    KdPoli.getText(), NoReg.getText(), tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString(),
                                    tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString()
                                });
                                emptTeks();
                                tampil();
                            }
                        }
                        if (nomer.equals("201")) {
                            if (Sequel.mengedittf("skdp_bpjs", "tahun=? and no_antrian=?", "tahun=?,no_rkm_medis=?,diagnosa=?,terapi=?,alasan1=?,alasan2=?,rtl1=?,rtl2=?,tanggal_datang=?,tanggal_rujukan=?,no_antrian=?,kd_dokter=?,status=?", 15, new String[]{
                                TanggalPeriksa.getSelectedItem().toString().substring(6, 10), TNoRM.getText(), Diagnosa.getText(), Terapi.getText(),
                                Alasan1.getText(), Alasan2.getText(), Rtl1.getText(), Rtl2.getText(), Valid.SetTgl(TanggalPeriksa.getSelectedItem() + ""),
                                Valid.SetTgl(TanggalSurat.getSelectedItem() + ""), NoSurat.getText(), KdDokter.getText(), Status.getSelectedItem().toString(),
                                tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString(), tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString()
                            }) == true) {
                                Sequel.mengedit3("booking_registrasi", "no_rkm_medis=? and tanggal_periksa=?", "tanggal_booking=?,no_rkm_medis=?,tanggal_periksa=?,kd_dokter=?,kd_poli=?,no_reg=?", 8, new String[]{
                                    Valid.SetTgl(TanggalSurat.getSelectedItem() + ""), TNoRM.getText(),
                                    Valid.SetTgl(TanggalPeriksa.getSelectedItem() + ""), KdDokter.getText(),
                                    KdPoli.getText(), NoReg.getText(), tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString(),
                                    tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString()
                                });
                                emptTeks();
                                tampil();
                            }
                        }
                        if (nomer.equals("203")) {
                            if (Sequel.mengedittf("skdp_bpjs", "tahun=? and no_antrian=?", "tahun=?,no_rkm_medis=?,diagnosa=?,terapi=?,alasan1=?,alasan2=?,rtl1=?,rtl2=?,tanggal_datang=?,tanggal_rujukan=?,no_antrian=?,kd_dokter=?,status=?", 15, new String[]{
                                TanggalPeriksa.getSelectedItem().toString().substring(6, 10), TNoRM.getText(), Diagnosa.getText(), Terapi.getText(),
                                Alasan1.getText(), Alasan2.getText(), Rtl1.getText(), Rtl2.getText(), Valid.SetTgl(TanggalPeriksa.getSelectedItem() + ""),
                                Valid.SetTgl(TanggalSurat.getSelectedItem() + ""), NoSurat.getText(), KdDokter.getText(), Status.getSelectedItem().toString(),
                                tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString(), tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString()
                            }) == true) {
                                Sequel.mengedit3("booking_registrasi", "no_rkm_medis=? and tanggal_periksa=?", "tanggal_booking=?,no_rkm_medis=?,tanggal_periksa=?,kd_dokter=?,kd_poli=?,no_reg=?", 8, new String[]{
                                    Valid.SetTgl(TanggalSurat.getSelectedItem() + ""), TNoRM.getText(),
                                    Valid.SetTgl(TanggalPeriksa.getSelectedItem() + ""), KdDokter.getText(),
                                    KdPoli.getText(), NoReg.getText(), tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString(),
                                    tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString()
                                });
                                emptTeks();
                                tampil();
                            }
                        }
                    }
                } else {
                    if (Sequel.mengedittf("skdp_bpjs", "tahun=? and no_antrian=?", "tahun=?,no_rkm_medis=?,diagnosa=?,terapi=?,alasan1=?,alasan2=?,rtl1=?,rtl2=?,tanggal_datang=?,tanggal_rujukan=?,no_antrian=?,kd_dokter=?,status=?", 15, new String[]{
                        TanggalPeriksa.getSelectedItem().toString().substring(6, 10), TNoRM.getText(), Diagnosa.getText(), Terapi.getText(),
                        Alasan1.getText(), Alasan2.getText(), Rtl1.getText(), Rtl2.getText(), Valid.SetTgl(TanggalPeriksa.getSelectedItem() + ""),
                        Valid.SetTgl(TanggalSurat.getSelectedItem() + ""), NoSurat.getText(), KdDokter.getText(), Status.getSelectedItem().toString(),
                        tbObat.getValueAt(tbObat.getSelectedRow(), 0).toString(), tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString()
                    }) == true) {
                        Sequel.mengedit3("booking_registrasi", "no_rkm_medis=? and tanggal_periksa=?", "tanggal_booking=?,no_rkm_medis=?,tanggal_periksa=?,kd_dokter=?,kd_poli=?,no_reg=?", 8, new String[]{
                            Valid.SetTgl(TanggalSurat.getSelectedItem() + ""), TNoRM.getText(),
                            Valid.SetTgl(TanggalPeriksa.getSelectedItem() + ""), KdDokter.getText(),
                            KdPoli.getText(), NoReg.getText(), tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString(),
                            tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString()
                        });
                        emptTeks();
                        tampil();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih terlebih dulu data yang mau anda ganti...\n Klik data pada table untuk memilih data...!!!!");
            }
        }
    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void StatusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StatusKeyPressed
        Valid.pindah(evt, TanggalSurat, Diagnosa);
    }//GEN-LAST:event_StatusKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
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

    private void Alasan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Alasan1KeyPressed
        Valid.pindah(evt, Terapi, Alasan2);
    }//GEN-LAST:event_Alasan1KeyPressed

    private void Alasan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Alasan2KeyPressed
        Valid.pindah(evt, Alasan1, Rtl1);
    }//GEN-LAST:event_Alasan2KeyPressed

    private void Rtl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Rtl1KeyPressed
        Valid.pindah(evt, Alasan2, Rtl2);
    }//GEN-LAST:event_Rtl1KeyPressed

    private void Rtl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Rtl2KeyPressed
        Valid.pindah(evt, Rtl1, BtnDokter);
    }//GEN-LAST:event_Rtl2KeyPressed

    private void TanggalPeriksaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalPeriksaKeyPressed
        Valid.pindah(evt, BtnPoli, NoSurat);
    }//GEN-LAST:event_TanggalPeriksaKeyPressed

    private void NoSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoSuratKeyPressed
        Valid.pindah(evt, TanggalPeriksa, NoReg);
    }//GEN-LAST:event_NoSuratKeyPressed

    private void TanggalPeriksaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TanggalPeriksaItemStateChanged
        try {    
            isNomer();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_TanggalPeriksaItemStateChanged

    private void DiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKeyPressed
        Valid.pindah(evt, Status, Terapi);
    }//GEN-LAST:event_DiagnosaKeyPressed

    private void TerapiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TerapiKeyPressed
        Valid.pindah(evt, Diagnosa, Alasan1);
    }//GEN-LAST:event_TerapiKeyPressed

    private void BtnPoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPoliKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPoliActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnDokter, TanggalPeriksa);
        }
    }//GEN-LAST:event_BtnPoliKeyPressed

    private void BtnPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPoliActionPerformed
        poli.isCek();
        poli.setNoRm(KdDokter.getText(), konversi(TanggalPeriksa.getSelectedItem()+""));
        poli.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setVisible(true);
    }//GEN-LAST:event_BtnPoliActionPerformed

    private void NoRegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRegKeyPressed
        Valid.pindah(evt, NoSurat, BtnSimpan);
    }//GEN-LAST:event_NoRegKeyPressed

    private void MnSuratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSuratActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
            TanggalSurat.requestFocus();
        } else if (TPasien.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
        } else if (!(TPasien.getText().trim().equals(""))) {
            if (tbObat.getSelectedRow() != -1) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();
                param.put("namars", var.getnamars());
                param.put("alamatrs", var.getalamatrs());
                param.put("kotars", var.getkabupatenrs());
                param.put("propinsirs", var.getpropinsirs());
                param.put("kontakrs", var.getkontakrs());
                param.put("emailrs", var.getemailrs());
                param.put("logo", Sequel.cariGambar("select logo from setting"));
                Sequel.queryu("truncate table temporary_booking_registrasi");
                Sequel.menyimpan("temporary_booking_registrasi", "'0','"
                        + tabMode.getValueAt(tbObat.getSelectedRow(), 0).toString() + "','"
                        + tabMode.getValueAt(tbObat.getSelectedRow(), 1).toString() + "','"
                        + tabMode.getValueAt(tbObat.getSelectedRow(), 2).toString() + "','"
                        + tabMode.getValueAt(tbObat.getSelectedRow(), 3).toString() + "','"
                        + tabMode.getValueAt(tbObat.getSelectedRow(), 4).toString() + "','"
                        + tabMode.getValueAt(tbObat.getSelectedRow(), 5).toString() + "','"
                        + tabMode.getValueAt(tbObat.getSelectedRow(), 6).toString() + "','"
                        + tabMode.getValueAt(tbObat.getSelectedRow(), 7).toString() + "','"
                        + tabMode.getValueAt(tbObat.getSelectedRow(), 8).toString() + "','"
                        + TanggalPeriksa.getSelectedItem() + "','"
                        + TanggalSurat.getSelectedItem() + "','"
                        + tabMode.getValueAt(tbObat.getSelectedRow(), 11).toString() + "','"
                        + tabMode.getValueAt(tbObat.getSelectedRow(), 12).toString() + "','"
                        + tabMode.getValueAt(tbObat.getSelectedRow(), 13).toString() + "','"
                        + tabMode.getValueAt(tbObat.getSelectedRow(), 14).toString() + "','"
                        + tabMode.getValueAt(tbObat.getSelectedRow(), 15).toString() + "','"
                        + tabMode.getValueAt(tbObat.getSelectedRow(), 16).toString() + "','"
                        + tabMode.getValueAt(tbObat.getSelectedRow(), 17).toString() + "','','','','','','','','','','','','','','','','','','',''", "Rekap Nota Pembayaran");

                Valid.MyReport("rptSuratSKDPBPJS.jrxml", "report", "::[ Surat SKDP BPJS ]::",
                        "select * from temporary_booking_registrasi order by no asc", param);
                this.setCursor(Cursor.getDefaultCursor());
            } else {
                JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih terlebih dulu data yang mau anda hapus...\n Klik data pada table untuk memilih data...!!!!");
            }
        }
    }//GEN-LAST:event_MnSuratActionPerformed

    private void tbObatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyReleased
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbObatKeyReleased

    private void TPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TPasienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TPasienActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgSKDPBPJS dialog = new DlgSKDPBPJS(new javax.swing.JFrame(), true);
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
    private widget.TextBox Alasan1;
    private widget.TextBox Alasan2;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPoli;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPCari3;
    private widget.Tanggal DTPCari4;
    private widget.TextBox Diagnosa;
    private widget.PanelBiasa FormInput;
    private widget.TextBox KdDokter;
    private widget.TextBox KdDokter1;
    private widget.TextBox KdPoli;
    private widget.TextBox KdPoli1;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnSurat;
    private widget.TextBox NmDokter;
    private widget.TextBox NmDokter1;
    private widget.TextBox NmPoli;
    private widget.TextBox NmPoli1;
    private widget.TextBox NoReg;
    private widget.TextBox NoSEP;
    private widget.TextBox NoSurat;
    private javax.swing.JPanel PanelInput;
    private widget.RadioButton R1;
    private widget.RadioButton R2;
    private widget.RadioButton R3;
    private widget.TextBox Rtl1;
    private widget.TextBox Rtl2;
    private widget.ScrollPane Scroll;
    private widget.ComboBox Status;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TPasien;
    private widget.Tanggal TanggalPeriksa;
    private widget.Tanggal TanggalSurat;
    private widget.TextBox Terapi;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel22;
    private widget.Label jLabel25;
    private widget.Label jLabel37;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelCari;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass8;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        if (R1.isSelected() == true) {
            status = " skdp_bpjs.status='Menunggu' ";
        } else if (R2.isSelected() == true) {
            status = " skdp_bpjs.tanggal_datang between '" + Valid.SetTgl(DTPCari1.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari2.getSelectedItem() + "") + "' ";
        } else if (R3.isSelected() == true) {
            status = " skdp_bpjs.status<>'Menunggu' and skdp_bpjs.tanggal_rujukan between '" + Valid.SetTgl(DTPCari3.getSelectedItem() + "") + "' and '" + Valid.SetTgl(DTPCari4.getSelectedItem() + "") + "' ";
        }
        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement(
                    "select skdp_bpjs.tahun,skdp_bpjs.no_rkm_medis,pasien.nm_pasien,"
                    + "skdp_bpjs.diagnosa,skdp_bpjs.terapi,skdp_bpjs.alasan1,skdp_bpjs.alasan2,"
                    + "skdp_bpjs.rtl1,skdp_bpjs.rtl2,skdp_bpjs.tanggal_datang,skdp_bpjs.tanggal_rujukan,"
                    + "skdp_bpjs.no_antrian,skdp_bpjs.kd_dokter,dokter.nm_dokter,skdp_bpjs.status "
                    + "from skdp_bpjs inner join pasien inner join dokter on "
                    + "skdp_bpjs.no_rkm_medis=pasien.no_rkm_medis and skdp_bpjs.kd_dokter=dokter.kd_dokter "
                    + "where " + status + " and skdp_bpjs.no_rkm_medis like ? or "
                    + status + " and pasien.nm_pasien like ? or "
                    + status + " and skdp_bpjs.diagnosa like ? or "
                    + status + " and skdp_bpjs.terapi like ? or "
                    + status + " and skdp_bpjs.no_antrian like ? or "
                    + status + " and dokter.nm_dokter like ? order by skdp_bpjs.tanggal_rujukan,skdp_bpjs.no_antrian");
            try {
                ps.setString(1, "%" + TCari.getText().trim() + "%");
                ps.setString(2, "%" + TCari.getText().trim() + "%");
                ps.setString(3, "%" + TCari.getText().trim() + "%");
                ps.setString(4, "%" + TCari.getText().trim() + "%");
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                ps.setString(6, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    kdpoli = "";
                    nmpoli = "";
                    noantri = "";
                    ps2 = koneksi.prepareStatement(
                            "select booking_registrasi.kd_poli,poliklinik.nm_poli,booking_registrasi.no_reg "
                            + "from booking_registrasi inner join poliklinik on booking_registrasi.kd_poli=poliklinik.kd_poli "
                            + "where booking_registrasi.kd_dokter=? and booking_registrasi.tanggal_periksa=? and booking_registrasi.no_rkm_medis=?");
                    try {
                        ps2.setString(1, rs.getString("kd_dokter"));
                        ps2.setString(2, rs.getString("tanggal_datang"));
                        ps2.setString(3, rs.getString("no_rkm_medis"));
                        rs2 = ps2.executeQuery();
                        if (rs2.next()) {
                            kdpoli = rs2.getString("kd_poli");
                            nmpoli = rs2.getString("nm_poli");
                            noantri = rs2.getString("no_reg");
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
                    tabMode.addRow(new Object[]{
                        rs.getInt("tahun"), rs.getString("no_rkm_medis"), rs.getString("nm_pasien"),
                        rs.getString("diagnosa"), rs.getString("terapi"), rs.getString("alasan1"),
                        rs.getString("alasan2"), rs.getString("rtl1"), rs.getString("rtl2"),
                        rs.getString("tanggal_datang"), rs.getString("tanggal_rujukan"), rs.getString("no_antrian"),
                        noantri, rs.getString("kd_dokter"), rs.getString("nm_dokter"), kdpoli,
                        nmpoli, rs.getString("status")
                    });
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
        LCount.setText("" + tabMode.getRowCount());
    }

    public void emptTeks() {
        KdDokter.setText("");
        NmDokter.setText("");
        KdPoli.setText("");
        NmPoli.setText("");
        Alasan1.setText("");
        Alasan2.setText("");
        Rtl1.setText("");
        Rtl2.setText("");
        Terapi.setText("");
        Diagnosa.setText("");
        TanggalSurat.setDate(new Date());
        TanggalPeriksa.setDate(new Date());
        TanggalSurat.requestFocus();
        isNomer();
    }

    private void isNomer() {
        switch (URUTNOREG) {
            case "poli":
                Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_poli='" + KdPoli.getText() + "' and tanggal_periksa='" + Valid.SetTgl(TanggalPeriksa.getSelectedItem() + "") + "'", "", 3, NoReg);
                break;
            case "dokter":
                Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_dokter='" + KdDokter.getText() + "' and tanggal_periksa='" + Valid.SetTgl(TanggalPeriksa.getSelectedItem() + "") + "'", "", 3, NoReg);
                break;
            case "dokter + poli":
                Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_dokter='" + KdDokter.getText() + "' and kd_poli='" + KdPoli.getText() + "' and tanggal_periksa='" + Valid.SetTgl(TanggalPeriksa.getSelectedItem() + "") + "'", "", 3, NoReg);
                break;
            default:
                Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from booking_registrasi where kd_dokter='" + KdDokter.getText() + "' and tanggal_periksa='" + Valid.SetTgl(TanggalPeriksa.getSelectedItem() + "") + "'", "", 3, NoReg);
                break;
        }
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_antrian,6),signed)),0) from skdp_bpjs where tahun='" + TanggalPeriksa.getSelectedItem().toString().substring(6, 10) + "' ", "", 6, NoSurat);
    }

    private void getData() {
        if (tbObat.getSelectedRow() != -1) {
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 2).toString());
            Diagnosa.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 3).toString());
            Terapi.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 4).toString());
            Alasan1.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 5).toString());
            Alasan2.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 6).toString());
            Rtl1.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 7).toString());
            Rtl2.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 8).toString());
            Valid.SetTgl(TanggalPeriksa, tbObat.getValueAt(tbObat.getSelectedRow(), 9).toString());
            Valid.SetTgl(TanggalSurat, tbObat.getValueAt(tbObat.getSelectedRow(), 10).toString());
            NoSurat.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 11).toString());
            NoReg.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 12).toString());
            KdDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString());
            NmDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 14).toString());
            KdPoli.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 15).toString());
            NmPoli.setText(tbObat.getValueAt(tbObat.getSelectedRow(), 16).toString());
            Status.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(), 17).toString());
            KdPoli1.setText(Sequel.cariIsi("select kd_poli_bpjs from maping_poli_bpjs where kd_poli_rs=?", tbObat.getValueAt(tbObat.getSelectedRow(), 15).toString()));
            NmPoli1.setText(Sequel.cariIsi("select nm_poli_bpjs from maping_poli_bpjs where kd_poli_bpjs=?", KdPoli1.getText()));
            KdDokter1.setText(Sequel.cariIsi("select kd_dokter_bpjs from maping_dokter_dpjpvclaim where kd_dokter=?", tbObat.getValueAt(tbObat.getSelectedRow(), 13).toString()));
            NmDokter1.setText(Sequel.cariIsi("select nm_dokter_bpjs from maping_dokter_dpjpvclaim where kd_dokter=?", tbObat.getValueAt(tbObat.getSelectedRow(), 14).toString()));
        }
    }

    public void setNoRm(String norm, String nama) {
        TNoRM.setText(norm);
        TPasien.setText(nama);
        TCari.setText(norm);
        ChkInput.setSelected(true);
        isForm();
        tampil();
    }

    public void setNoRm(String norm, String nama, String kodepoli, String namapoli, String kodedokter, String namadokter, String norawat, String status_rawat) {
        penjab = "";
        diag = "";
        TNoRM.setText(norm);
        TPasien.setText(nama);
        penjab = Sequel.cariIsi("SELECT kd_pj FROM reg_periksa WHERE no_rawat=?", norawat);
        diag = Sequel.cariIsi("SELECT penyakit.nm_penyakit FROM diagnosa_pasien JOIN penyakit ON diagnosa_pasien.kd_penyakit = penyakit.kd_penyakit WHERE no_rawat=? AND diagnosa_pasien.status='" + status_rawat + "' AND prioritas='1' LIMIT 1", norawat);
        if (diag.equals("")) {
            diag = Sequel.cariIsi("SELECT penyakit.nm_penyakit FROM diagnosa_pasien JOIN penyakit ON diagnosa_pasien.kd_penyakit = penyakit.kd_penyakit WHERE no_rawat=? AND diagnosa_pasien.status='" + status_rawat + "' AND prioritas IN ('2','3','4','5','6','7') LIMIT 1", norawat);
        }
        Diagnosa.setText(diag);
        nosepCari = Sequel.cariIsi("SELECT no_sep FROM bridging_sep WHERE no_rawat=?", norawat);
        if (nosepCari.equals("")) {
            nosep = Sequel.cariIsi("SELECT no_sep FROM bridging_sep_internal WHERE no_rawat=?", norawat);
        } else {
            nosep = nosepCari;
        }
        KdPoli.setText(kodepoli);
        NmPoli.setText(namapoli);
        KdDokter.setText(kodedokter);
        NmDokter.setText(namadokter);
        KdPoli1.setText(Sequel.cariIsi("select kd_poli_bpjs from maping_poli_bpjs where kd_poli_rs=?", kodepoli));
        NmPoli1.setText(Sequel.cariIsi("select nm_poli_bpjs from maping_poli_bpjs where kd_poli_bpjs=?", KdPoli1.getText()));
        KdDokter1.setText(Sequel.cariIsi("select kd_dokter_bpjs from maping_dokter_dpjpvclaim where kd_dokter=?", kodedokter));
        NmDokter1.setText(Sequel.cariIsi("select nm_dokter_bpjs from maping_dokter_dpjpvclaim where kd_dokter=?", kodedokter));
        TCari.setText(norm);
        ChkInput.setSelected(true);
        isForm();
        tampil();
        norujukCari = Sequel.cariIsi("select no_rujukan from bridging_sep where no_rawat=?", norawat);
        if (norujukCari.equals("")) {
            norujuk = Sequel.cariIsi("select no_rujukan from bridging_sep_internal where no_rawat=?", norawat);
        } else {
            norujuk = norujukCari;
        }
    }

    public void setNoRm(String norm, String nama, String norawat, String status_rawat) {
        penjab = "";
        set_status_rawat = "";
        diag = "";
        TNoRM.setText(norm);
        TPasien.setText(nama);
        penjab = Sequel.cariIsi("SELECT kd_pj FROM reg_periksa WHERE no_rawat=?", norawat);
        diag = Sequel.cariIsi("SELECT penyakit.nm_penyakit FROM diagnosa_pasien JOIN penyakit ON diagnosa_pasien.kd_penyakit = penyakit.kd_penyakit WHERE no_rawat=? AND diagnosa_pasien.status='" + status_rawat + "' AND prioritas='1' LIMIT 1", norawat);
        if (diag.equals("")) {
            diag = Sequel.cariIsi("SELECT penyakit.nm_penyakit FROM diagnosa_pasien JOIN penyakit ON diagnosa_pasien.kd_penyakit = penyakit.kd_penyakit WHERE no_rawat=? AND diagnosa_pasien.status='" + status_rawat + "' AND prioritas IN ('2','3','4','5','6','7') LIMIT 1", norawat);
        }
        Diagnosa.setText(diag);
        nosepCari = Sequel.cariIsi("SELECT no_sep FROM bridging_sep WHERE no_rawat=?", norawat);
        if (nosepCari.equals("")) {
            nosep = Sequel.cariIsi("SELECT no_sep FROM bridging_sep_internal WHERE no_rawat=?", norawat);
        } else {
            nosep = nosepCari;
        }
        KdDokter.setText(Sequel.cariIsi("select kd_dokter from dpjp_ranap where no_rawat=? and jenis_dpjp='Utama'", norawat));
        NmDokter.setText(Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=? ", KdDokter.getText()));        
//        KdPoli.setText(Sequel.cariIsi("select kd_poli from jadwal where kd_dokter=?", KdDokter.getText()));
//        NmPoli.setText(Sequel.cariIsi("select poliklinik.nm_poli from jadwal join poliklinik on jadwal.kd_poli=poliklinik.kd_poli where poliklinik.kd_poli=?", KdPoli.getText()));
        TCari.setText(norm);
        ChkInput.setSelected(true);
        isForm();
        tampil();
        norujukCari = Sequel.cariIsi("select no_rujukan from bridging_sep where no_rawat=?", norawat);
        if (norujukCari.equals("")) {
            norujuk = Sequel.cariIsi("select no_rujukan from bridging_sep_internal where no_rawat=?", norawat);
        } else {
            norujuk = norujukCari;
        }
//        KdPoli1.setText(Sequel.cariIsi("select kd_poli_bpjs from maping_poli_bpjs where kd_poli_rs=?", KdPoli.getText()));
//        NmPoli1.setText(Sequel.cariIsi("select nm_poli_bpjs from maping_poli_bpjs where kd_poli_bpjs=?", KdPoli1.getText()));
        KdDokter1.setText(Sequel.cariIsi("select kd_dokter_bpjs from maping_dokter_dpjpvclaim where kd_dokter=?", KdDokter.getText()));
        NmDokter1.setText(Sequel.cariIsi("select nm_dokter_bpjs from maping_dokter_dpjpvclaim where kd_dokter=?", KdDokter1.getText()));
        set_status_rawat = status_rawat;
        if (status_rawat.equals("Ranap")) {
            Terapi.setText(Sequel.cariIsi("SELECT GROUP_CONCAT(databarang.nama_brng) FROM detail_pemberian_obat INNER JOIN databarang ON detail_pemberian_obat.kode_brng=databarang.kode_brng WHERE detail_pemberian_obat.status='Pulang' and detail_pemberian_obat.no_rawat='"+norawat+"'"));
        }
    }

    public boolean compareDates(String d1, String d2) {
        Boolean status = false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(d1);
            Date date2 = sdf.parse(d2);
            if (date1.after(date2)) {
                status = true;
            }
            if (date1.before(date2)) {
                status = false;
            }

            if (date1.equals(date2)) {
                status = false;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return status;
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

    public void cekHfisDokter(String kodedokter) {
        KdDokter1.setText(Sequel.cariIsi("select kd_dokter_bpjs from maping_dokter_dpjpvclaim where kd_dokter=?", kodedokter));
        NmDokter1.setText(Sequel.cariIsi("select nm_dokter_bpjs from maping_dokter_dpjpvclaim where kd_dokter=?", kodedokter));
        System.out.println(KdDokter1.getText());
    }

    public void cekHfisPoli(String kodepoli) {
        KdPoli1.setText(Sequel.cariIsi("select kd_poli_bpjs from maping_poli_bpjs where kd_poli_rs=?", kodepoli));
        NmPoli1.setText(Sequel.cariIsi("select nm_poli_bpjs from maping_poli_bpjs where kd_poli_bpjs=?", KdPoli1.getText()));
        System.out.println(KdPoli1.getText());
    }

    public void isCek() {
        BtnSimpan.setEnabled(var.getskdp_bpjs());
        BtnHapus.setEnabled(var.getskdp_bpjs());
        BtnPrint.setEnabled(var.getskdp_bpjs());
        BtnEdit.setEnabled(var.getskdp_bpjs());
    }

    public JTable getTable() {
        return tbObat;
    }

    public void getTab(String norawat) {
        tampil();        
        JOptionPane.showMessageDialog(null, "Maaf, SEP pasien belum tersedia...!!");
    }

    String konversi(String tanggal) {
        String dateString = Valid.SetTgl(tanggal+"");
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            String dayOfWeek = new SimpleDateFormat("EEEE").format(date);
            switch (dayOfWeek) {
                case "Monday":
                    hari = "SENIN";
                    break;
                case "Tuesday":
                    hari = "SELASA";
                    break;
                case "Wednesday":
                    hari = "RABU";
                    break;
                case "Thursday":
                    hari = "KAMIS";
                    break;
                case "Friday":
                    hari = "JUMAT";
                    break;
                case "Saturday":
                    hari = "SABTU";
                    break;
                case "Sunday":
                    hari = "AKHAD";
                    break;
            }
       } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return hari;
    }

}