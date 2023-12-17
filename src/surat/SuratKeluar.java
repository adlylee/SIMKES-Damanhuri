package surat;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.BorderLayout;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import static javafx.concurrent.Worker.State.FAILED;
import javafx.embed.swing.JFXPanel;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.transform.Scale;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import keuangan.DlgKamar;
import laporan.DlgDiagnosaPenyakit;
import simrskhanza.DlgCariPoli;
import simrskhanza.DlgKamarInap2;
import simrskhanza.DlgPasien;

/**
 *
 * @author perpustakaan
 */
public final class SuratKeluar extends javax.swing.JDialog {

    private Connection koneksi = koneksiDB.condb();
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i = 0;
    public DlgCariDokter dokter = new DlgCariDokter(null, false);
    public DlgKamar kamar = new DlgKamar(null, false);
    private DlgKamarInap2 kamar2 = new DlgKamarInap2(null, false);
    private DefaultTableModel tabMode, tabMode2,tabMode3,tabMode4,tabMode5,tabMode6,tabMode7;
    private String no = "",nm_sps="";    
    private DlgCariPoli poli = new DlgCariPoli(null, false);

    
    /**
     * Creates new form DlgLhtBiaya
     *
     * @param parent
     * @param modal
     */
    public SuratKeluar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
                
        TKesan.setDocument(new batasInput((byte) 100).getKata(TKesan));
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

        kamar2.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (kamar2.getTable().getSelectedRow() != -1) {
                    TNoRw.setText(kamar2.getTable().getValueAt(kamar2.getTable().getSelectedRow(), 0).toString());
                    TNoRM.setText(kamar2.getTable().getValueAt(kamar2.getTable().getSelectedRow(), 1).toString());
                    TPasien.setText(kamar2.getTable().getValueAt(kamar2.getTable().getSelectedRow(), 2).toString());
                    Valid.SetTgl(TglMasuk,Sequel.cariIsi("SELECT tgl_masuk FROM kamar_inap WHERE no_rawat=? order by tgl_masuk asc limit 1",TNoRw.getText()));
                    Valid.SetTgl(TglKeluar,Sequel.cariIsi("SELECT tgl_keluar FROM kamar_inap WHERE no_rawat=? order by tgl_masuk desc limit 1",TNoRw.getText()));
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

        kamar2.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    kamar2.dispose();
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
                if (dokter.getTable().getSelectedRow() != -1) {
                    kddokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 0).toString());
                    TDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                    kddokter.requestFocus();
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

        kamar.bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (var.getform().equals("SuratKeluar")) {
                    if (kamar.bangsal.getTable().getSelectedRow() != -1) {
                        kdBangsal.setText(kamar.bangsal.getTable().getValueAt(kamar.bangsal.getTable().getSelectedRow(), 0).toString());
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
                    kdBangsal.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(), 0).toString());
                    BangsalCari.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(), 1).toString());
                    BangsalCari.requestFocus();
                    tampil();
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
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCetakSurat = new javax.swing.JMenuItem();
        MnCetakSurat1 = new javax.swing.JMenu();
        MnSuratOpname1 = new javax.swing.JMenuItem();
        MnSuratOpname2 = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        internalFrame2 = new widget.InternalFrame();
        internalFrame5 = new widget.InternalFrame();
        TabSurat = new javax.swing.JTabbedPane();
        Scroll3 = new widget.ScrollPane();
        tbData = new widget.Table();
        Scroll = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        jLabel9 = new widget.Label();
        TglMasuk = new widget.Tanggal();
        jLabel19 = new widget.Label();
        jLabel4 = new widget.Label();
        TNoSurat = new widget.TextBox();
        kddokter = new widget.TextBox();
        TDokter = new widget.TextBox();
        jLabel20 = new widget.Label();
        TglKeluar = new widget.Tanggal();
        kdBangsal = new widget.TextBox();
        BangsalCari = new widget.TextBox();
        jLabel21 = new widget.Label();
        jLabel31 = new widget.Label();
        cmbJenisSurat1 = new widget.ComboBox();
        TglSurat = new widget.Tanggal();
        jLabel22 = new widget.Label();
        btnPasien = new widget.Button();
        BtnDokter = new widget.Button();
        btnBangsalCari = new widget.Button();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel7 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        TKesan = new widget.TextArea();
        jLabel8 = new widget.Label();
        jLabel10 = new widget.Label();
        AP = new widget.TextBox();
        NoDST = new widget.TextBox();
        jLabel25 = new widget.Label();
        DST = new widget.Tanggal();
        jPanel3 = new javax.swing.JPanel();
        panelGlass10 = new widget.panelisi();
        jLabel23 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel24 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        cmbJenisSurat = new widget.ComboBox();
        panelGlass5 = new widget.panelisi();
        BtnSimpan1 = new widget.Button();
        BtnBatal1 = new widget.Button();
        BtnEdit = new widget.Button();
        BtnHapus = new widget.Button();
        BtnAll = new widget.Button();
        jLabel11 = new widget.Label();
        LCount = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCetakSurat.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakSurat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSurat.setForeground(new java.awt.Color(70, 70, 70));
        MnCetakSurat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSurat.setText("Cetak Surat");
        MnCetakSurat.setName("MnCetakSurat"); // NOI18N
        MnCetakSurat.setPreferredSize(new java.awt.Dimension(200, 28));
        MnCetakSurat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakSurat);

        MnCetakSurat1.setBackground(new java.awt.Color(250, 255, 245));
        MnCetakSurat1.setForeground(new java.awt.Color(70, 70, 70));
        MnCetakSurat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSurat1.setText("Cetak Surat Opname");
        MnCetakSurat1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSurat1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSurat1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSurat1.setIconTextGap(5);
        MnCetakSurat1.setName("MnCetakSurat1"); // NOI18N
        MnCetakSurat1.setPreferredSize(new java.awt.Dimension(260, 26));

        MnSuratOpname1.setBackground(new java.awt.Color(255, 255, 254));
        MnSuratOpname1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSuratOpname1.setForeground(new java.awt.Color(70, 70, 70));
        MnSuratOpname1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSuratOpname1.setText("Surat Opname 1");
        MnSuratOpname1.setName("MnSuratOpname1"); // NOI18N
        MnSuratOpname1.setPreferredSize(new java.awt.Dimension(200, 28));
        MnSuratOpname1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSuratOpname1ActionPerformed(evt);
            }
        });
        MnCetakSurat1.add(MnSuratOpname1);

        MnSuratOpname2.setBackground(new java.awt.Color(255, 255, 254));
        MnSuratOpname2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSuratOpname2.setForeground(new java.awt.Color(70, 70, 70));
        MnSuratOpname2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSuratOpname2.setText("Surat Opname 2");
        MnSuratOpname2.setName("MnSuratOpname2"); // NOI18N
        MnSuratOpname2.setPreferredSize(new java.awt.Dimension(200, 28));
        MnSuratOpname2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSuratOpname2ActionPerformed(evt);
            }
        });
        MnCetakSurat1.add(MnSuratOpname2);

        jPopupMenu1.add(MnCetakSurat1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Surat Keluar ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame5.setBackground(new java.awt.Color(254, 254, 254));
        internalFrame5.setBorder(null);
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        TabSurat.setBackground(new java.awt.Color(255, 255, 253));
        TabSurat.setForeground(new java.awt.Color(70, 70, 70));
        TabSurat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabSurat.setName("TabSurat"); // NOI18N
        internalFrame5.add(TabSurat, java.awt.BorderLayout.CENTER);

        Scroll3.setBorder(javax.swing.BorderFactory.createTitledBorder(".: Data Surat"));
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbData.setAutoCreateRowSorter(true);
        tbData.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbData.setName("tbData"); // NOI18N
        tbData.setComponentPopupMenu(jPopupMenu1);
        tbData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataMouseClicked(evt);
            }
        });
        tbData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDataKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbDataKeyReleased(evt);
            }
        });
        Scroll3.setViewportView(tbData);

        internalFrame5.add(Scroll3, java.awt.BorderLayout.CENTER);

        internalFrame2.add(internalFrame5, java.awt.BorderLayout.CENTER);

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setToolTipText("");
        Scroll.setViewportBorder(javax.swing.BorderFactory.createTitledBorder(".: Input Data"));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(560, 238));
        FormInput.setLayout(null);

        jLabel3.setText("No. RM :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 70, 90, 23);

        jLabel9.setText("Dokter :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 100, 90, 23);

        TglMasuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06-12-2023" }));
        TglMasuk.setDisplayFormat("dd-MM-yyyy");
        TglMasuk.setName("TglMasuk"); // NOI18N
        TglMasuk.setOpaque(false);
        TglMasuk.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglMasukItemStateChanged(evt);
            }
        });
        TglMasuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglMasukKeyPressed(evt);
            }
        });
        FormInput.add(TglMasuk);
        TglMasuk.setBounds(95, 160, 90, 23);

        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(20, 160, 70, 23);

        jLabel4.setText("No. Surat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 90, 23);

        TNoSurat.setEditable(false);
        TNoSurat.setHighlighter(null);
        TNoSurat.setName("TNoSurat"); // NOI18N
        FormInput.add(TNoSurat);
        TNoSurat.setBounds(95, 10, 205, 23);

        kddokter.setEditable(false);
        kddokter.setHighlighter(null);
        kddokter.setName("kddokter"); // NOI18N
        kddokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddokterKeyPressed(evt);
            }
        });
        FormInput.add(kddokter);
        kddokter.setBounds(95, 100, 125, 23);

        TDokter.setEditable(false);
        TDokter.setName("TDokter"); // NOI18N
        FormInput.add(TDokter);
        TDokter.setBounds(220, 100, 280, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Tgl. Keluar :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(190, 160, 70, 23);

        TglKeluar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06-12-2023" }));
        TglKeluar.setDisplayFormat("dd-MM-yyyy");
        TglKeluar.setName("TglKeluar"); // NOI18N
        TglKeluar.setOpaque(false);
        FormInput.add(TglKeluar);
        TglKeluar.setBounds(260, 160, 90, 23);

        kdBangsal.setEditable(false);
        kdBangsal.setHighlighter(null);
        kdBangsal.setName("kdBangsal"); // NOI18N
        FormInput.add(kdBangsal);
        kdBangsal.setBounds(95, 130, 65, 23);

        BangsalCari.setEditable(false);
        BangsalCari.setName("BangsalCari"); // NOI18N
        BangsalCari.setPreferredSize(new java.awt.Dimension(200, 23));
        BangsalCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BangsalCariKeyPressed(evt);
            }
        });
        FormInput.add(BangsalCari);
        BangsalCari.setBounds(160, 130, 170, 23);

        jLabel21.setText("Ruang/Poli :");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput.add(jLabel21);
        jLabel21.setBounds(10, 130, 80, 23);

        jLabel31.setText("Jenis Surat :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(200, 40, 90, 23);

        cmbJenisSurat1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Surat Keterangan Opname", "Surat Keterangan Tidak Buta Warna", "Surat Keterangan Sakit", "Surat Resume Medis", "Surat Keterangan Istirahat", "Surat Sehat", "Surat Napza", "Surat Keterangan Dokter", "Surat Keterangan Pasca Operasi" }));
        cmbJenisSurat1.setName("cmbJenisSurat1"); // NOI18N
        cmbJenisSurat1.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbJenisSurat1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbJenisSurat1ItemStateChanged(evt);
            }
        });
        cmbJenisSurat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbJenisSurat1ActionPerformed(evt);
            }
        });
        cmbJenisSurat1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJenisSurat1KeyPressed(evt);
            }
        });
        FormInput.add(cmbJenisSurat1);
        cmbJenisSurat1.setBounds(295, 40, 230, 23);

        TglSurat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06-12-2023" }));
        TglSurat.setDisplayFormat("dd-MM-yyyy");
        TglSurat.setName("TglSurat"); // NOI18N
        TglSurat.setOpaque(false);
        TglSurat.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglSuratItemStateChanged(evt);
            }
        });
        TglSurat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglSuratKeyPressed(evt);
            }
        });
        FormInput.add(TglSurat);
        TglSurat.setBounds(95, 40, 120, 23);

        jLabel22.setText("Tgl. Surat :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(0, 40, 90, 23);

        btnPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPasien.setMnemonic('6');
        btnPasien.setToolTipText("Alt+6");
        btnPasien.setName("btnPasien"); // NOI18N
        btnPasien.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasienActionPerformed(evt);
            }
        });
        btnPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPasienKeyPressed(evt);
            }
        });
        FormInput.add(btnPasien);
        btnPasien.setBounds(500, 70, 28, 23);

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
        BtnDokter.setBounds(500, 100, 28, 23);

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
        FormInput.add(btnBangsalCari);
        btnBangsalCari.setBounds(330, 130, 28, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        FormInput.add(TNoRw);
        TNoRw.setBounds(95, 70, 125, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(220, 70, 80, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(300, 70, 200, 23);

        jLabel7.setText("Keperluan :");
        jLabel7.setName("jLabel7"); // NOI18N
        FormInput.add(jLabel7);
        jLabel7.setBounds(0, 190, 90, 23);

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

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(95, 190, 255, 53);

        jLabel8.setText("Atas Permintaan :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 250, 90, 23);

        jLabel10.setText("Dengan surat tgl :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 280, 90, 23);

        AP.setHighlighter(null);
        AP.setName("AP"); // NOI18N
        FormInput.add(AP);
        AP.setBounds(95, 250, 255, 23);

        NoDST.setHighlighter(null);
        NoDST.setName("NoDST"); // NOI18N
        FormInput.add(NoDST);
        NoDST.setBounds(260, 280, 90, 23);

        jLabel25.setText("No :");
        jLabel25.setName("jLabel25"); // NOI18N
        jLabel25.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput.add(jLabel25);
        jLabel25.setBounds(215, 280, 40, 23);

        DST.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "12-12-2023" }));
        DST.setDisplayFormat("dd-MM-yyyy");
        DST.setName("DST"); // NOI18N
        DST.setOpaque(false);
        FormInput.add(DST);
        DST.setBounds(95, 280, 90, 23);

        Scroll.setViewportView(FormInput);

        internalFrame2.add(Scroll, java.awt.BorderLayout.WEST);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel23.setText("Tgl.Rawat :");
        jLabel23.setName("jLabel23"); // NOI18N
        jLabel23.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass10.add(jLabel23);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06-12-2023" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass10.add(DTPCari1);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("s.d.");
        jLabel24.setName("jLabel24"); // NOI18N
        jLabel24.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass10.add(jLabel24);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "06-12-2023" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass10.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
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
        panelGlass10.add(BtnCari);

        cmbJenisSurat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Opname", "Buta Warna", "Sakit", "Resume Medis", "Istirahat", "Dokter", "Operasi" }));
        cmbJenisSurat.setName("cmbJenisSurat"); // NOI18N
        cmbJenisSurat.setPreferredSize(new java.awt.Dimension(130, 28));
        cmbJenisSurat.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbJenisSuratItemStateChanged(evt);
            }
        });
        cmbJenisSurat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbJenisSuratActionPerformed(evt);
            }
        });
        cmbJenisSurat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJenisSuratKeyPressed(evt);
            }
        });
        panelGlass10.add(cmbJenisSurat);

        jPanel3.add(panelGlass10, java.awt.BorderLayout.PAGE_START);

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan1.setMnemonic('S');
        BtnSimpan1.setText("Simpan");
        BtnSimpan1.setToolTipText("Alt+S");
        BtnSimpan1.setName("BtnSimpan1"); // NOI18N
        BtnSimpan1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan1ActionPerformed(evt);
            }
        });
        BtnSimpan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpan1KeyPressed(evt);
            }
        });
        panelGlass5.add(BtnSimpan1);

        BtnBatal1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal1.setMnemonic('B');
        BtnBatal1.setText("Baru");
        BtnBatal1.setToolTipText("Alt+B");
        BtnBatal1.setName("BtnBatal1"); // NOI18N
        BtnBatal1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatal1ActionPerformed(evt);
            }
        });
        BtnBatal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatal1KeyPressed(evt);
            }
        });
        panelGlass5.add(BtnBatal1);

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
        panelGlass5.add(BtnEdit);

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
        panelGlass5.add(BtnHapus);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 23));
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
        panelGlass5.add(BtnAll);

        jLabel11.setText("Record :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(55, 30));
        panelGlass5.add(jLabel11);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(52, 30));
        panelGlass5.add(LCount);

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
        panelGlass5.add(BtnPrint);

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
        panelGlass5.add(BtnKeluar);

        jPanel3.add(panelGlass5, java.awt.BorderLayout.CENTER);

        internalFrame2.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(internalFrame2, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed

}//GEN-LAST:event_BtnKeluarKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
//        autoNomor();
    }//GEN-LAST:event_formWindowOpened

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        getTampilMaster();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnAllActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void TglMasukItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglMasukItemStateChanged

    }//GEN-LAST:event_TglMasukItemStateChanged

    private void TglMasukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglMasukKeyPressed
        Valid.pindah(evt, BtnCari, BtnCari);
    }//GEN-LAST:event_TglMasukKeyPressed

    private void kddokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddokterKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", TDokter, kddokter.getText());
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            BtnDokterActionPerformed(null);
        } else {
            Valid.pindah(evt, btnPasien, cmbJenisSurat1);
        }
    }//GEN-LAST:event_kddokterKeyPressed

    private void BangsalCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BangsalCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//            TabSuratMouseClicked(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
//            btnBangsalCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
//            DTPCari3.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            TCari.requestFocus();
        }
    }//GEN-LAST:event_BangsalCariKeyPressed

    private void TglSuratItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglSuratItemStateChanged
        try {
            autoNomor();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_TglSuratItemStateChanged

    private void TglSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglSuratKeyPressed
        Valid.pindah(evt, BangsalCari, TglMasuk);
    }//GEN-LAST:event_TglSuratKeyPressed

    private void btnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasienActionPerformed
        kamar2.emptTeks();
        kamar2.isCek();
        kamar2.setNoRM("Surat");
        kamar2.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        kamar2.setLocationRelativeTo(internalFrame1);
        kamar2.setVisible(true);
    }//GEN-LAST:event_btnPasienActionPerformed

    private void btnPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPasienKeyPressed
        Valid.pindah(evt, TNoRM, DTPCari1);
    }//GEN-LAST:event_btnPasienKeyPressed

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
            Valid.pindah(evt, btnPasien, TNoSurat);
        }
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void btnBangsalCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBangsalCariActionPerformed
        switch (cmbJenisSurat1.getSelectedIndex()) {
            case 1:
                var.setform("SuratKeluar");
                kamar.bangsal.isCek();
                kamar.bangsal.emptTeks();
                kamar.bangsal.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                kamar.bangsal.setLocationRelativeTo(internalFrame1);
                kamar.bangsal.setVisible(true);
                break;
            case 3:
                poli.isCek();
                poli.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                poli.setLocationRelativeTo(internalFrame1);
                poli.setVisible(true);
                break;
            case 4:
                var.setform("SuratKeluar");
                kamar.bangsal.isCek();
                kamar.bangsal.emptTeks();
                kamar.bangsal.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                kamar.bangsal.setLocationRelativeTo(internalFrame1);
                kamar.bangsal.setVisible(true);
            case 5:
                var.setform("SuratKeluar");
                kamar.bangsal.isCek();
                kamar.bangsal.emptTeks();
                kamar.bangsal.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                kamar.bangsal.setLocationRelativeTo(internalFrame1);
                kamar.bangsal.setVisible(true);
                break;
            case 8:
                poli.isCek();
                poli.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                poli.setLocationRelativeTo(internalFrame1);
                poli.setVisible(true);
                break;
            case 9:
                var.setform("SuratKeluar");
                kamar.bangsal.isCek();
                kamar.bangsal.emptTeks();
                kamar.bangsal.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                kamar.bangsal.setLocationRelativeTo(internalFrame1);
                kamar.bangsal.setVisible(true);
                break;
            default:
                break;
        }       
    }//GEN-LAST:event_btnBangsalCariActionPerformed

    private void btnBangsalCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnBangsalCariKeyPressed
        Valid.pindah(evt, DTPCari2, TCari);
    }//GEN-LAST:event_btnBangsalCariKeyPressed

    private void BtnSimpan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (TNoRM.getText().trim().equals("") || TPasien.getText().trim().equals("")) {
            Valid.textKosong(TNoRM, "Pasien");
        } else if (TNoSurat.getText().trim().equals("")) {
            Valid.textKosong(TNoSurat, "No. Surat");
        } else if (kddokter.getText().trim().equals("") || TDokter.getText().trim().equals("")) {
            Valid.textKosong(kddokter, "Dokter");
        } else if (cmbJenisSurat1.getSelectedItem().toString().trim().equals("-")) {
            Valid.textKosong(cmbJenisSurat1, "Jenis Surat");
        } else {
            switch (cmbJenisSurat1.getSelectedIndex()) {
                case 1:
                    isSimpanSurat("Opname");
                    cmbJenisSurat.setSelectedIndex(1);
                    break;
                case 2:
                    int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, udah bener belum data yang mau disimpan..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        if (Sequel.menyimpantf("datasurat", "?,?,?,?,?,?,?,?,?,?,?,?", "Data", 12, new String[]{
                            "0", TNoSurat.getText(), TNoRw.getText(), kddokter.getText(), Valid.SetTgl(TglSurat.getSelectedItem() + ""), "Buta Warna",
                            TKesan.getText(), AP.getText(), Valid.SetTgl(DST.getSelectedItem() + ""), NoDST.getText(), "", var.getkode()
                        }) == true) {
                            emptTeks();
                            JOptionPane.showMessageDialog(null, "Berhasil Simpan Surat Keterangan Tidak Buta Warna...");
                        }
                    }
                    cmbJenisSurat.setSelectedIndex(2);
                    break;
                case 3:
                    isSimpanSurat("Sakit");
                    cmbJenisSurat.setSelectedIndex(3);
                    break;
                case 4:
                    isSimpanSurat("Resume Medis");
                    cmbJenisSurat.setSelectedIndex(4);
                    break;
                case 5:
                    isSimpanSurat("Istirahat");
                    cmbJenisSurat.setSelectedIndex(5);
                    break;
                case 8:
                    isSimpanSurat2("Dokter");
                    cmbJenisSurat.setSelectedIndex(6);
                    break;
                case 9:
                    isSimpanSurat2("Operasi");
                    cmbJenisSurat.setSelectedIndex(7);
                    break;
                default:
                    break;
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnSimpan1ActionPerformed

    private void BtnSimpan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpan1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpan1ActionPerformed(null);
        } else {
            Valid.pindah(evt, TNoSurat, BtnBatal1);
        }
    }//GEN-LAST:event_BtnSimpan1KeyPressed

    private void BtnBatal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatal1ActionPerformed
        emptTeks();
    }//GEN-LAST:event_BtnBatal1ActionPerformed

    private void BtnBatal1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatal1KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            emptTeks();
        } else {
            Valid.pindah(evt, BtnSimpan1, BtnBatal1);
        }
    }//GEN-LAST:event_BtnBatal1KeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if (TNoSurat.getText().trim().equals("")) {
            Valid.textKosong(TNoSurat, "no surat");
        } else {
            switch (cmbJenisSurat.getSelectedItem().toString()) {
                case "Opname":
                    isEdit("Opname");
                    break;
                case "Buta Warna":
                    if (Sequel.mengedittf("datasurat", "id=? and jns_surat=?", "kd_dokter=?,data1=?,data2=?,data3=?,data4=?", 7, new String[]{
                        kddokter.getText(), TKesan.getText(), AP.getText(), Valid.SetTgl(DST.getSelectedItem() + ""),NoDST.getText(), no, "Buta Warna"
                    }) == true) {
                        getTampilMaster();
                        emptTeks();
                        JOptionPane.showMessageDialog(null, "Berhasil edit data surat buta warna...");
                    }
                    break;
                case "Sakit":
                    isEdit("Sakit");
                    break;
                case "Resume Medis":
                    isEdit("Resume Medis");
                    break;
                case "Istirahat":
                    isEdit("Istirahat");
                    break;
                case "Dokter":
                    isEdit2("Dokter");
                    break;
                case "Operasi":
                    isEdit2("Operasi");
                    break;
                default:
                    break;
            }
        }
    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnBatal1, BtnKeluar);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed

    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnBatal1, BtnKeluar);
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
        getTampilMaster();
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void MnCetakSuratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (no.equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Gagal mencetak. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        } else if (!(no.equals(""))) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            String dxAkhir = Sequel.cariIsi("SELECT IFNULL(diagnosa_utama,'') as diagnosa_utama FROM resume_pasien_ranap where no_rawat=?", TNoRw.getText());
            String terapi = Sequel.cariIsi("SELECT IFNULL(GROUP_CONCAT(databarang.nama_brng),'') as nama_brng FROM detail_pemberian_obat,databarang where detail_pemberian_obat.kode_brng=databarang.kode_brng and detail_pemberian_obat.status='Pulang' and detail_pemberian_obat.no_rawat='" + TNoRw.getText() + "'");
            String riwayatmasuk = Sequel.cariIsi("SELECT IFNULL(keluhan_utama,'') FROM resume_pasien_ranap where no_rawat=?", TNoRw.getText());
            String dxRalan = Sequel.cariIsi("select CONCAT(d.kd_penyakit,' ', p.nm_penyakit) as dx from diagnosa_pasien d join penyakit p on d.kd_penyakit=p.kd_penyakit where d.prioritas='1' and d.status='Ralan' and d.no_rawat=?", TNoRw.getText());
            getSpesialis();
            param.put("namasps", nm_sps);
            param.put("dxAkhir", dxAkhir);
            param.put("riwayatmasuk", riwayatmasuk);
            param.put("terapi", terapi);
            param.put("dxRalan", dxRalan);
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            switch (cmbJenisSurat.getSelectedItem().toString()) {
                case "Buta Warna":
                    Valid.MyReport("rptSuratButaWarna.jrxml", "report", "::[ Surat Keterangan Tidak Buta Warna ]::",
                            getQueryReport("Buta Warna", no), param);
                    break;
                case "Sakit":
                    Valid.MyReport("rptSuratSakit.jrxml", "report", "::[ Surat Keterangan Sakit ]::",
                            getQueryReport("Sakit", no), param);
                    break;
                case "Resume Medis":
                    Valid.MyReport("rptSuratResumeMedis.jrxml", "report", "::[ Surat Resume Medis ]::",
                            getQueryReport("Resume Medis", no), param);
                    break;
                case "Istirahat":
                    Valid.MyReport("rptSuratIstirahat.jrxml", "report", "::[ Surat Keterangan Istirahat ]::",
                            getQueryReport("Istirahat", no), param);
                    break;
                case "Dokter":
                    Valid.MyReport("rptSuratDokter.jrxml", "report", "::[ Surat Keterangan Dokter ]::",
                            getQueryReport("Dokter", no), param);
                    break;
                case "Operasi":
                    Valid.MyReport("rptSuratPascaOp.jrxml", "report", "::[ Surat Keterangan Pasca Operasi ]::",
                            getQueryReport("Operasi", no), param);
                    break;
                default:
                    break;
            }
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnCetakSuratActionPerformed

    private void cmbJenisSurat1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJenisSurat1KeyPressed

    }//GEN-LAST:event_cmbJenisSurat1KeyPressed

    private void cmbJenisSurat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbJenisSurat1ActionPerformed
        switch (cmbJenisSurat1.getSelectedIndex()) {
            case 0:
                jLabel7.setVisible(false);
                scrollPane1.setVisible(false);
                jLabel19.setVisible(false);
                jLabel20.setVisible(false);
                jLabel21.setVisible(false);
                kdBangsal.setVisible(false);
                BangsalCari.setVisible(false);
                btnBangsalCari.setVisible(false);
                TglMasuk.setVisible(false);
                TglKeluar.setVisible(false);
                jLabel8.setVisible(false);
                AP.setVisible(false);
                jLabel10.setVisible(false);
                DST.setVisible(false);
                jLabel25.setVisible(false);
                NoDST.setVisible(false);                
                break;
            case 1:
                getJenisSurat();
                break;
            case 2:
                jLabel7.setVisible(true);
                scrollPane1.setVisible(true);
                jLabel19.setVisible(false);
                jLabel20.setVisible(false);
                jLabel21.setVisible(false);
                kdBangsal.setVisible(false);
                BangsalCari.setVisible(false);
                btnBangsalCari.setVisible(false);
                TglMasuk.setVisible(false);
                TglKeluar.setVisible(false);
                jLabel8.setVisible(true);
                DST.setVisible(true);
                jLabel10.setVisible(true);
                AP.setVisible(true);
                jLabel25.setVisible(true);
                NoDST.setVisible(true);                
                break;
            case 3:
                getJenisSurat();
                jLabel20.setVisible(true);
                TglKeluar.setVisible(true);
                break;
            case 4:
                getJenisSurat();
                break;      
            case 5:
                getJenisSurat();
                break;
            case 6:
                DlgSuratSehat sehat = new DlgSuratSehat(null, false);
                sehat.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
                sehat.setLocationRelativeTo(internalFrame1);
                sehat.emptTeks();
                sehat.setVisible(true);
                break;                
            case 7:
                SuratNapza napza = new SuratNapza(null, false);
                napza.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
                napza.setLocationRelativeTo(internalFrame1);
                napza.emptTeks();
                napza.setVisible(true);
                break;
            case 8:
                getJenisSurat();
                jLabel7.setVisible(true);
                scrollPane1.setVisible(true);
                jLabel19.setText("Tanggal :");                
                jLabel20.setVisible(false);
                TglKeluar.setVisible(false);
                break;
            case 9:
                getJenisSurat();
                jLabel20.setVisible(false);
                TglKeluar.setVisible(false);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_cmbJenisSurat1ActionPerformed

    private void cmbJenisSurat1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbJenisSurat1ItemStateChanged
        
    }//GEN-LAST:event_cmbJenisSurat1ItemStateChanged

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if (TNoSurat.getText().trim().equals("")) {
            Valid.textKosong(TNoSurat, "no surat");
        } else {
            int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, Yakin mau dihapus..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                Sequel.meghapus("datasurat", "id", tbData.getValueAt(tbData.getSelectedRow(), 0).toString());
                getTampilMaster();
            }
        }
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnBatal1, BtnEdit);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed

    private void TKesanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKesanKeyPressed

    }//GEN-LAST:event_TKesanKeyPressed

    private void tbDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataMouseClicked
        if (tbData.getRowCount() != 0) {
            try {
                getDataMaster();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbDataMouseClicked

    private void tbDataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDataKeyPressed
        if (tbData.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
                try {
                    getDataMaster();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbDataKeyPressed

    private void tbDataKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDataKeyReleased
        if (tbData.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getDataMaster();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbDataKeyReleased

    private void cmbJenisSuratItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbJenisSuratItemStateChanged
        getTampilMaster();
    }//GEN-LAST:event_cmbJenisSuratItemStateChanged

    private void cmbJenisSuratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbJenisSuratActionPerformed

    }//GEN-LAST:event_cmbJenisSuratActionPerformed

    private void cmbJenisSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJenisSuratKeyPressed

    }//GEN-LAST:event_cmbJenisSuratKeyPressed

    private void MnSuratOpname1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSuratOpname1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (no.equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Gagal mencetak. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        } else if (!(no.equals(""))) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            getSpesialis();
            param.put("namasps", nm_sps);
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));            
            Valid.MyReport("rptSuratOpname.jrxml", "report", "::[ Surat Keterangan Opname ]::",
                    getQueryReport("Opname", no), param);            
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnSuratOpname1ActionPerformed

    private void MnSuratOpname2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSuratOpname2ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if (no.equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Gagal mencetak. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        } else if (!(no.equals(""))) {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            getSpesialis();
            param.put("namasps", nm_sps);
            param.put("namars", var.getnamars());
            param.put("alamatrs", var.getalamatrs());
            param.put("kotars", var.getkabupatenrs());
            param.put("propinsirs", var.getpropinsirs());
            param.put("kontakrs", var.getkontakrs());
            param.put("emailrs", var.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));            
            Valid.MyReport("rptSuratOpname1.jrxml", "report", "::[ Surat Keterangan Opname ]::",
                    getQueryReport("Opname", no), param);            
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnSuratOpname2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            SuratKeluar dialog = new SuratKeluar(new javax.swing.JFrame(), true);
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
    private widget.TextBox AP;
    private widget.TextBox BangsalCari;
    private widget.Button BtnAll;
    private widget.Button BtnBatal1;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan1;
    private widget.Tanggal DST;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnCetakSurat;
    private javax.swing.JMenu MnCetakSurat1;
    private javax.swing.JMenuItem MnSuratOpname1;
    private javax.swing.JMenuItem MnSuratOpname2;
    private widget.TextBox NoDST;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll3;
    private widget.TextBox TCari;
    private widget.TextBox TDokter;
    private widget.TextArea TKesan;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TNoSurat;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabSurat;
    private widget.Tanggal TglKeluar;
    private widget.Tanggal TglMasuk;
    private widget.Tanggal TglSurat;
    private widget.Button btnBangsalCari;
    private widget.Button btnPasien;
    private widget.ComboBox cmbJenisSurat;
    private widget.ComboBox cmbJenisSurat1;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame5;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel3;
    private widget.Label jLabel31;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdBangsal;
    private widget.TextBox kddokter;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass5;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbData;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        tabMode=new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tabMode.setColumnIdentifiers(new Object[]{"No", "No. Surat", "Tgl. Surat", "No.Rawat", "No.RM", "Pasien", "Dokter", "Ruangan", "Tgl. Masuk", "Tgl. Keluar", "Jenis Surat"});
        tbData.setModel(tabMode);
        tbData.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 11; i++) {
            TableColumn column = tbData.getColumnModel().getColumn(i);
            if ((i == 0) || (i == 10)) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if ((i == 2) || (i == 8) || (i == 9)) {
                column.setPreferredWidth(75);
            } else if (i == 3) {
                column.setPreferredWidth(110);
            } else if (i == 4) {
                column.setPreferredWidth(65);
            } else if ((i == 5) || (i == 7)) {
                column.setPreferredWidth(180);
            } else if ((i == 1) || (i == 6)) {
                column.setPreferredWidth(150);
            }
        }
        tbData.setDefaultRenderer(Object.class, new WarnaTable());
        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement(
                    "SELECT datasurat.id, datasurat.tgl_surat,datasurat.no_rawat, pasien.no_rkm_medis, pasien.nm_pasien, dokter.nm_dokter,datasurat.jns_surat, datasurat.no_surat, "
                    + "datasurat.data1, datasurat.data2, bangsal.nm_bangsal FROM datasurat,reg_periksa,pasien,dokter,bangsal WHERE datasurat.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and datasurat.kd_dokter=dokter.kd_dokter and datasurat.data3=bangsal.kd_bangsal "
                    + "and datasurat.jns_surat='Opname' and datasurat.tgl_surat BETWEEN ? and ? and "
                    + "(datasurat.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or dokter.nm_dokter like ?) ORDER BY datasurat.tgl_surat DESC");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, "%" + TCari.getText().trim() + "%");
                ps.setString(4, "%" + TCari.getText().trim() + "%");
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                ps.setString(6, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new Object[]{rs.getString("id"), rs.getString("no_surat"), rs.getString("tgl_surat"), rs.getString("no_rawat"), rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien"), rs.getString("nm_dokter"), rs.getString("nm_bangsal"), rs.getString("data1"), rs.getString("data2"), rs.getString("jns_surat")});
                }
                rs.last();
                LCount.setText("" + rs.getRow());
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

    private void tampil2() {
        tabMode2=new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tabMode2.setColumnIdentifiers(new Object[]{"No", "No. Surat", "Tgl. Surat", "No.Rawat", "No.RM", "Pasien", "Dokter", "Keterangan","Atas Permintaan","Dengan Surat Tgl"});
        tbData.setModel(tabMode2);
        tbData.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 10; i++) {
            TableColumn column = tbData.getColumnModel().getColumn(i);
            if ((i == 0)) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if ((i == 2) ) {
                column.setPreferredWidth(75);
            } else if ((i == 3)|| (i == 7)||(i == 8)|| (i == 9)) {
                column.setPreferredWidth(110);
            } else if (i == 4) {
                column.setPreferredWidth(65);
            } else if ((i == 5)) {
                column.setPreferredWidth(180);
            } else if ((i == 1) || (i == 6)) {
                column.setPreferredWidth(150);
            }
        }
        tbData.setDefaultRenderer(Object.class, new WarnaTable());
        Valid.tabelKosong(tabMode2);
        try {
            ps = koneksi.prepareStatement(
                    "SELECT datasurat.id, datasurat.tgl_surat,datasurat.no_rawat, pasien.no_rkm_medis, pasien.nm_pasien, dokter.nm_dokter,datasurat.jns_surat, datasurat.no_surat, "
                    + "datasurat.data1, datasurat.data2, datasurat.data3 FROM datasurat,reg_periksa,pasien,dokter WHERE datasurat.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and datasurat.kd_dokter=dokter.kd_dokter "
                    + "and datasurat.jns_surat='Buta Warna' and datasurat.tgl_surat BETWEEN ? and ? and "
                    + "(datasurat.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or dokter.nm_dokter like ?) ORDER BY datasurat.tgl_surat DESC");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, "%" + TCari.getText().trim() + "%");
                ps.setString(4, "%" + TCari.getText().trim() + "%");
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                ps.setString(6, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode2.addRow(new Object[]{rs.getString("id"), rs.getString("no_surat"), rs.getString("tgl_surat"), rs.getString("no_rawat"), rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien"), rs.getString("nm_dokter"), rs.getString("data1"), rs.getString("data2"), rs.getString("data3")});
                }
                rs.last();
                LCount.setText("" + rs.getRow());
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
    
    private void tampil3() {
        tabMode3=new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tabMode3.setColumnIdentifiers(new Object[]{"No", "No. Surat", "Tgl. Surat", "No.Rawat", "No.RM", "Pasien", "Dokter", "Poliklinik", "Tgl. Masuk", "Tgl. Keluar","Lama", "Jenis Surat"});
        tbData.setModel(tabMode3);
        tbData.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 12; i++) {
            TableColumn column = tbData.getColumnModel().getColumn(i);
            if ((i == 0) || (i == 11)) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if ((i == 2) || (i == 8) || (i == 9)) {
                column.setPreferredWidth(75);
            } else if (i == 3) {
                column.setPreferredWidth(110);
            } else if (i == 4) {
                column.setPreferredWidth(65);
            } else if ((i == 5) || (i == 7)) {
                column.setPreferredWidth(180);
            } else if ((i == 1) || (i == 6)) {
                column.setPreferredWidth(150);
            } else if (i == 10) {
                column.setPreferredWidth(40);
            }
        }
        tbData.setDefaultRenderer(Object.class, new WarnaTable());
        Valid.tabelKosong(tabMode3);
        try {
            ps = koneksi.prepareStatement(
                    "SELECT datasurat.id, datasurat.tgl_surat,datasurat.no_rawat, pasien.no_rkm_medis, pasien.nm_pasien, dokter.nm_dokter,datasurat.jns_surat, datasurat.no_surat, "
                    + "datasurat.data1,datasurat.data2, datasurat.data4, REPLACE(REPLACE(poliklinik.nm_poli,'SORE',''),'PAGI','') as nm_poli,DATEDIFF(data2,data1)+1 as lama FROM datasurat,reg_periksa,pasien,dokter,poliklinik WHERE datasurat.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and datasurat.kd_dokter=dokter.kd_dokter and datasurat.data3=poliklinik.kd_poli "
                    + "and datasurat.jns_surat='Sakit' and datasurat.tgl_surat BETWEEN ? and ? and "
                    + "(datasurat.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or dokter.nm_dokter like ?) ORDER BY datasurat.tgl_surat DESC");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, "%" + TCari.getText().trim() + "%");
                ps.setString(4, "%" + TCari.getText().trim() + "%");
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                ps.setString(6, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode3.addRow(new String[]{
                        rs.getString("id"), rs.getString("no_surat"), rs.getString("tgl_surat"), rs.getString("no_rawat"), rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien"), rs.getString("nm_dokter"), rs.getString("nm_poli"), rs.getString("data1"), rs.getString("data2"), rs.getString("lama"), rs.getString("jns_surat")
                    });
                }
                rs.last();
                LCount.setText("" + rs.getRow());
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
    
    private void tampil4() {
        tabMode4=new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tabMode4.setColumnIdentifiers(new Object[]{"No", "No. Surat", "Tgl. Surat", "No.Rawat", "No.RM", "Pasien", "Dokter", "Ruangan", "Tgl. Masuk", "Tgl. Keluar", "Jenis Surat"});
        tbData.setModel(tabMode4);
        tbData.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 11; i++) {
            TableColumn column = tbData.getColumnModel().getColumn(i);
            if ((i == 0) || (i == 10)) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if ((i == 2) || (i == 8) || (i == 9)) {
                column.setPreferredWidth(75);
            } else if (i == 3) {
                column.setPreferredWidth(110);
            } else if (i == 4) {
                column.setPreferredWidth(65);
            } else if ((i == 5) || (i == 7)) {
                column.setPreferredWidth(180);
            } else if ((i == 1) || (i == 6)) {
                column.setPreferredWidth(150);
            }
        }
        tbData.setDefaultRenderer(Object.class, new WarnaTable());
        Valid.tabelKosong(tabMode4);
        try {
            ps = koneksi.prepareStatement(
                    "SELECT datasurat.id, datasurat.tgl_surat,datasurat.no_rawat, pasien.no_rkm_medis, pasien.nm_pasien, dokter.nm_dokter,datasurat.jns_surat, datasurat.no_surat, "
                    + "datasurat.data1, datasurat.data2, bangsal.nm_bangsal FROM datasurat,reg_periksa,pasien,dokter,bangsal WHERE datasurat.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and datasurat.kd_dokter=dokter.kd_dokter and datasurat.data3=bangsal.kd_bangsal "
                    + "and datasurat.jns_surat='Resume Medis' and datasurat.tgl_surat BETWEEN ? and ? and "
                    + "(datasurat.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or dokter.nm_dokter like ?) ORDER BY datasurat.tgl_surat DESC");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, "%" + TCari.getText().trim() + "%");
                ps.setString(4, "%" + TCari.getText().trim() + "%");
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                ps.setString(6, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode4.addRow(new String[]{
                        rs.getString("id"), rs.getString("no_surat"), rs.getString("tgl_surat"), rs.getString("no_rawat"), rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien"), rs.getString("nm_dokter"), rs.getString("nm_bangsal"), rs.getString("data1"), rs.getString("data2"), rs.getString("jns_surat")
                    });
                }
                rs.last();
                LCount.setText("" + rs.getRow());
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
    
    private void tampil5() {
        tabMode5=new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tabMode5.setColumnIdentifiers(new Object[]{"No", "No. Surat", "Tgl. Surat", "No.Rawat", "No.RM", "Pasien", "Dokter", "Ruangan", "Tgl. Masuk", "Tgl. Keluar", "Lama", "Jenis Surat"});
        tbData.setModel(tabMode5);
        tbData.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 12; i++) {
            TableColumn column = tbData.getColumnModel().getColumn(i);
            if ((i == 0) || (i == 11)) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if ((i == 2) || (i == 8) || (i == 9)) {
                column.setPreferredWidth(75);
            } else if (i == 3) {
                column.setPreferredWidth(110);
            } else if (i == 4) {
                column.setPreferredWidth(65);
            } else if ((i == 5) || (i == 7)) {
                column.setPreferredWidth(180);
            } else if ((i == 1) || (i == 6)) {
                column.setPreferredWidth(150);
            } else if (i == 10) {
                column.setPreferredWidth(40);
            }
        }
        tbData.setDefaultRenderer(Object.class, new WarnaTable());
        Valid.tabelKosong(tabMode5);
        try {
            ps = koneksi.prepareStatement(
                    "SELECT datasurat.id, datasurat.tgl_surat,datasurat.no_rawat, pasien.no_rkm_medis, pasien.nm_pasien, dokter.nm_dokter,datasurat.jns_surat, datasurat.no_surat, "
                    + "datasurat.data1, datasurat.data2, bangsal.nm_bangsal, DATEDIFF(data2,data1)+1 as lama FROM datasurat,reg_periksa,pasien,dokter,bangsal WHERE datasurat.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and datasurat.kd_dokter=dokter.kd_dokter and datasurat.data3=bangsal.kd_bangsal "
                    + "and datasurat.jns_surat='Istirahat' and datasurat.tgl_surat BETWEEN ? and ? and "
                    + "(datasurat.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or dokter.nm_dokter like ?) ORDER BY datasurat.tgl_surat DESC");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, "%" + TCari.getText().trim() + "%");
                ps.setString(4, "%" + TCari.getText().trim() + "%");
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                ps.setString(6, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode5.addRow(new String[]{
                        rs.getString("id"), rs.getString("no_surat"), rs.getString("tgl_surat"), rs.getString("no_rawat"), rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien"), rs.getString("nm_dokter"), rs.getString("nm_bangsal"), rs.getString("data1"), rs.getString("data2"), rs.getString("lama"), rs.getString("jns_surat")

                    });
                }
                rs.last();
                LCount.setText("" + rs.getRow());
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

    private void tampil6() {
        tabMode6=new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tabMode6.setColumnIdentifiers(new Object[]{"No", "No. Surat", "Tgl. Surat", "No.Rawat", "No.RM", "Pasien", "Dokter", "Poliklinik", "Tgl. Masuk", "Jenis Surat"});
        tbData.setModel(tabMode6);
        tbData.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 10; i++) {
            TableColumn column = tbData.getColumnModel().getColumn(i);
            if ((i == 0) || (i == 9)) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if ((i == 2) || (i == 8)) {
                column.setPreferredWidth(75);
            } else if (i == 3) {
                column.setPreferredWidth(110);
            } else if (i == 4) {
                column.setPreferredWidth(65);
            } else if ((i == 5) || (i == 7)) {
                column.setPreferredWidth(180);
            } else if ((i == 1) || (i == 6)) {
                column.setPreferredWidth(150);
            }
        }
        tbData.setDefaultRenderer(Object.class, new WarnaTable());
        Valid.tabelKosong(tabMode6);
        try {
            ps = koneksi.prepareStatement(
                    "SELECT datasurat.id, datasurat.tgl_surat,datasurat.no_rawat, pasien.no_rkm_medis, pasien.nm_pasien, dokter.nm_dokter,datasurat.jns_surat, datasurat.no_surat, "
                    + "datasurat.data1,datasurat.data4, REPLACE(REPLACE(poliklinik.nm_poli,'SORE',''),'PAGI','') as nm_poli FROM datasurat,reg_periksa,pasien,dokter,poliklinik WHERE datasurat.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and datasurat.kd_dokter=dokter.kd_dokter and datasurat.data3=poliklinik.kd_poli "
                    + "and datasurat.jns_surat='Dokter' and datasurat.tgl_surat BETWEEN ? and ? and "
                    + "(datasurat.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or dokter.nm_dokter like ?) ORDER BY datasurat.tgl_surat DESC");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, "%" + TCari.getText().trim() + "%");
                ps.setString(4, "%" + TCari.getText().trim() + "%");
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                ps.setString(6, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode6.addRow(new String[]{
                        rs.getString("id"), rs.getString("no_surat"), rs.getString("tgl_surat"), rs.getString("no_rawat"), rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien"), rs.getString("nm_dokter"), rs.getString("nm_poli"), rs.getString("data1"),rs.getString("jns_surat")
                    });
                }
                rs.last();
                LCount.setText("" + rs.getRow());
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
    
    private void tampil7() {
        tabMode7=new DefaultTableModel(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tabMode7.setColumnIdentifiers(new Object[]{"No", "No. Surat", "Tgl. Surat", "No.Rawat", "No.RM", "Pasien", "Dokter", "Poliklinik", "Tgl. Masuk", "Jenis Surat"});
        tbData.setModel(tabMode7);
        tbData.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 10; i++) {
            TableColumn column = tbData.getColumnModel().getColumn(i);
            if ((i == 0) || (i == 9)) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if ((i == 2) || (i == 8)) {
                column.setPreferredWidth(75);
            } else if (i == 3) {
                column.setPreferredWidth(110);
            } else if (i == 4) {
                column.setPreferredWidth(65);
            } else if ((i == 5) || (i == 7)) {
                column.setPreferredWidth(180);
            } else if ((i == 1) || (i == 6)) {
                column.setPreferredWidth(150);
            }
        }
        tbData.setDefaultRenderer(Object.class, new WarnaTable());
        Valid.tabelKosong(tabMode7);
        try {
            ps = koneksi.prepareStatement(
                    "SELECT datasurat.id, datasurat.tgl_surat,datasurat.no_rawat, pasien.no_rkm_medis, pasien.nm_pasien, dokter.nm_dokter,datasurat.jns_surat, datasurat.no_surat, "
                    + "datasurat.data1, bangsal.nm_bangsal FROM datasurat,reg_periksa,pasien,dokter,bangsal WHERE datasurat.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and datasurat.kd_dokter=dokter.kd_dokter and datasurat.data3=bangsal.kd_bangsal "
                    + "and datasurat.jns_surat='Operasi' and datasurat.tgl_surat BETWEEN ? and ? and "
                    + "(datasurat.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or dokter.nm_dokter like ?) ORDER BY datasurat.tgl_surat DESC");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, "%" + TCari.getText().trim() + "%");
                ps.setString(4, "%" + TCari.getText().trim() + "%");
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                ps.setString(6, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode7.addRow(new String[]{
                        rs.getString("id"), rs.getString("no_surat"), rs.getString("tgl_surat"), rs.getString("no_rawat"), rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien"), rs.getString("nm_dokter"), rs.getString("nm_bangsal"), rs.getString("data1"),rs.getString("jns_surat")
                    });
                }
                rs.last();
                LCount.setText("" + rs.getRow());
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
        
    private void autoNomor() {
        Valid.autoNomer6("select ifnull(MAX(CONVERT(LEFT(no_surat,4),signed)),0) from datasurat where "
                + "tgl_surat like '" + TglSurat.getSelectedItem().toString().substring(6, 10) + "%' ", "/RSUD/-Yan Kes/" + TglSurat.getSelectedItem().toString().substring(6, 10), 4, TNoSurat);
    }
    
    private void isSimpanSurat(String jnssurat){
        if (kdBangsal.getText().trim().equals("")) {
            Valid.textKosong(kdBangsal, "Ruangan/Poli");
        } else if (TglMasuk.getSelectedItem().toString().equals("")) {
            Valid.textKosong(TglMasuk, "Tanggal Masuk");
        } else if (TglKeluar.getSelectedItem().toString().equals("")) {
            Valid.textKosong(TglKeluar, "Tanggal Keluar");
        } else {
            int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, udah bener belum data yang mau disimpan..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                if (Sequel.menyimpantf("datasurat", "?,?,?,?,?,?,?,?,?,?,?,?", "Data", 12, new String[]{
                    "0", TNoSurat.getText(), TNoRw.getText(), kddokter.getText(), Valid.SetTgl(TglSurat.getSelectedItem() + ""), jnssurat,
                    Valid.SetTgl(TglMasuk.getSelectedItem() + ""), Valid.SetTgl(TglKeluar.getSelectedItem() + ""), kdBangsal.getText(), "", "", var.getkode()
                }) == true) {
                    emptTeks();
                    JOptionPane.showMessageDialog(null, "Berhasil Simpan Surat " + jnssurat + "...");
                }
            }
        }
    }
    
    private void isSimpanSurat2(String jnssurat){
        if (kdBangsal.getText().trim().equals("")) {
            Valid.textKosong(kdBangsal, "Ruangan/Poli");
        } else if (TglMasuk.getSelectedItem().toString().equals("")) {
            Valid.textKosong(TglMasuk, "Tanggal Masuk");
        } else if (TglKeluar.getSelectedItem().toString().equals("")) {
            Valid.textKosong(TglKeluar, "Tanggal Keluar");
        } else {
            int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiiiiits, udah bener belum data yang mau disimpan..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                if (Sequel.menyimpantf("datasurat", "?,?,?,?,?,?,?,?,?,?,?,?", "Data", 12, new String[]{
                    "0", TNoSurat.getText(), TNoRw.getText(), kddokter.getText(), Valid.SetTgl(TglSurat.getSelectedItem() + ""), jnssurat,
                    Valid.SetTgl(TglMasuk.getSelectedItem() + ""), "", kdBangsal.getText(), TKesan.getText(), "", var.getkode()
                }) == true) {
                    emptTeks();
                    JOptionPane.showMessageDialog(null, "Berhasil Simpan Surat " + jnssurat + "...");
                }
            }
        }
    }

    private void getData() {
        if (tbData.getSelectedRow() != -1) {
            no = tbData.getValueAt(tbData.getSelectedRow(), 0).toString();
            Valid.SetTgl(TglSurat, tbData.getValueAt(tbData.getSelectedRow(), 2).toString());
            TNoRw.setText(tbData.getValueAt(tbData.getSelectedRow(), 3).toString());
            TNoRM.setText(tbData.getValueAt(tbData.getSelectedRow(), 4).toString());
            TPasien.setText(tbData.getValueAt(tbData.getSelectedRow(), 5).toString());
            kdBangsal.setText(Sequel.cariIsi("SELECT data3 FROM datasurat where id=?", no));
            BangsalCari.setText(tbData.getValueAt(tbData.getSelectedRow(), 7).toString());
            kddokter.setText(Sequel.cariIsi("SELECT kd_dokter FROM datasurat where id=?", no));
            TDokter.setText(tbData.getValueAt(tbData.getSelectedRow(), 6).toString());
            Valid.SetTgl(TglMasuk, tbData.getValueAt(tbData.getSelectedRow(), 8).toString());
            Valid.SetTgl(TglKeluar, tbData.getValueAt(tbData.getSelectedRow(), 9).toString());
            TNoSurat.setText(tbData.getValueAt(tbData.getSelectedRow(), 1).toString());
            if (cmbJenisSurat.getSelectedItem().toString().equals("Opname")) {                
                cmbJenisSurat1.setSelectedIndex(1);
            }
            if (cmbJenisSurat.getSelectedItem().toString().equals("Resume Medis")) {                
                cmbJenisSurat1.setSelectedIndex(4);
            }
            if (cmbJenisSurat.getSelectedItem().toString().equals("Sakit")) {
                cmbJenisSurat1.setSelectedIndex(3);
            }
            if (cmbJenisSurat.getSelectedItem().toString().equals("Istirahat")) {
                cmbJenisSurat1.setSelectedIndex(5);
            }
            if (cmbJenisSurat.getSelectedItem().toString().equals("Dokter")) {
                cmbJenisSurat1.setSelectedIndex(8);
            }
            if (cmbJenisSurat.getSelectedItem().toString().equals("Operasi")) {
                cmbJenisSurat1.setSelectedIndex(9);
            }
        }
    }

    private void getData2() {
        if (tbData.getSelectedRow() != -1) {
            no = tbData.getValueAt(tbData.getSelectedRow(), 0).toString();
            TNoSurat.setText(tbData.getValueAt(tbData.getSelectedRow(), 1).toString());
            Valid.SetTgl(TglSurat, tbData.getValueAt(tbData.getSelectedRow(), 2).toString());
            TNoRw.setText(tbData.getValueAt(tbData.getSelectedRow(), 3).toString());
            TNoRM.setText(tbData.getValueAt(tbData.getSelectedRow(), 4).toString());
            TPasien.setText(tbData.getValueAt(tbData.getSelectedRow(), 5).toString());
            kddokter.setText(Sequel.cariIsi("SELECT kd_dokter FROM datasurat where id=?", no));
            TDokter.setText(tbData.getValueAt(tbData.getSelectedRow(), 6).toString());
            TKesan.setText(tbData.getValueAt(tbData.getSelectedRow(), 7).toString());
            AP.setText(tbData.getValueAt(tbData.getSelectedRow(), 8).toString());
            Valid.SetTgl(DST, tbData.getValueAt(tbData.getSelectedRow(), 9).toString());    
            NoDST.setText(Sequel.cariIsi("SELECT data4 FROM datasurat where id=?", no));
            cmbJenisSurat1.setSelectedIndex(2);
        }
    }
    
    private void getData3() {
        if (tbData.getSelectedRow() != -1) {
            no = tbData.getValueAt(tbData.getSelectedRow(), 0).toString();
            Valid.SetTgl(TglSurat, tbData.getValueAt(tbData.getSelectedRow(), 2).toString());
            TNoRw.setText(tbData.getValueAt(tbData.getSelectedRow(), 3).toString());
            TNoRM.setText(tbData.getValueAt(tbData.getSelectedRow(), 4).toString());
            TPasien.setText(tbData.getValueAt(tbData.getSelectedRow(), 5).toString());
            kdBangsal.setText(Sequel.cariIsi("SELECT data3 FROM datasurat where id=?", no));
            BangsalCari.setText(tbData.getValueAt(tbData.getSelectedRow(), 7).toString());
            kddokter.setText(Sequel.cariIsi("SELECT kd_dokter FROM datasurat where id=?", no));
            TDokter.setText(tbData.getValueAt(tbData.getSelectedRow(), 6).toString());
            Valid.SetTgl(TglMasuk, tbData.getValueAt(tbData.getSelectedRow(), 8).toString());
            Valid.SetTgl(TglKeluar, tbData.getValueAt(tbData.getSelectedRow(), 9).toString());
            TNoSurat.setText(tbData.getValueAt(tbData.getSelectedRow(), 1).toString());
        }
    }

    public void emptTeks() {
        TNoRw.setText("");
        TNoRM.setText("");
        TPasien.setText("");
        kddokter.setText("");
        TDokter.setText("");
        BangsalCari.setText("");
        kdBangsal.setText("");
        TglSurat.setDate(new Date());
        TglMasuk.setDate(new Date());
        TglKeluar.setDate(new Date());
        TKesan.setText("");
        AP.setText("");
        DST.setDate(new Date());
        cmbJenisSurat1.setSelectedIndex(0);
        autoNomor();
    }

    public void setPasien(String norawat, String norm, String nama, String jenis, String status) {
        TCari.setText(norawat);
        cmbJenisSurat1.setEnabled(false);
        TNoRw.setText(norawat);
        TNoRM.setText(norm);
        TPasien.setText(nama);
        if (status.equals("Ralan")) {
            kddokter.setText(Sequel.cariIsi("SELECT kd_dokter FROM reg_periksa where no_rawat=?", norawat));
            TDokter.setText(Sequel.cariIsi("SELECT nm_dokter FROM dokter where kd_dokter=?", kddokter.getText()));
            kdBangsal.setText(Sequel.cariIsi("SELECT kd_poli FROM reg_periksa where no_rawat=?", norawat));
            BangsalCari.setText(Sequel.cariIsi("SELECT nm_poli FROM poliklinik where kd_poli=?", kdBangsal.getText()));            
        }
        if (status.equals("Ranap") ) {
            kddokter.setText(Sequel.cariIsi("SELECT kd_dokter FROM dpjp_ranap where no_rawat=? and jenis_dpjp='Utama'", norawat));
            TDokter.setText(Sequel.cariIsi("SELECT nm_dokter FROM dokter WHERE kd_dokter=?", kddokter.getText()));
            if (!jenis.equals("Istirahat")) {
                Valid.SetTgl(TglMasuk, Sequel.cariIsi("SELECT tgl_masuk FROM kamar_inap WHERE no_rawat=? order by tgl_masuk asc limit 1", norawat));
                Valid.SetTgl(TglKeluar,Sequel.cariIsi("SELECT tgl_keluar FROM kamar_inap WHERE no_rawat=? order by tgl_masuk desc limit 1",TNoRw.getText()));
            }
        }
        switch (jenis) {
            case "Opname":
                cmbJenisSurat1.setSelectedIndex(1);
                cmbJenisSurat1ActionPerformed(null);
                break;
            case "Buta Warna":
                cmbJenisSurat1.setSelectedIndex(2);
                cmbJenisSurat1ActionPerformed(null);
                break;
            case "Sakit":
                cmbJenisSurat1.setSelectedIndex(3);
                cmbJenisSurat1ActionPerformed(null);
                break;
            case "Resume":
                cmbJenisSurat1.setSelectedIndex(4);
                cmbJenisSurat1ActionPerformed(null);
                break;
            case "Istirahat":
                cmbJenisSurat1.setSelectedIndex(5);
                cmbJenisSurat1ActionPerformed(null);
                break;    
            case "Dokter":
                cmbJenisSurat1.setSelectedIndex(8);
                cmbJenisSurat1ActionPerformed(null);
                break;
            case "Operasi":
                cmbJenisSurat1.setSelectedIndex(9);
                cmbJenisSurat1ActionPerformed(null);
//                String tgl_operasi = Sequel.cariIsi("SELECT tgl_operasi FROM operasi WHERE no_rawat=? limit 1", norawat);               
//                Valid.SetTgl(TglMasuk, tgl_operasi.substring(0,10));
                break;
            default:
                break;
        }
    }   
    
    private void getJenisSurat(){
        jLabel7.setVisible(false);
        scrollPane1.setVisible(false);
        jLabel19.setVisible(true);
        jLabel19.setText("Tgl. Masuk :");
        jLabel20.setVisible(true);
        jLabel21.setVisible(true);
        kdBangsal.setVisible(true);
        BangsalCari.setVisible(true);
        btnBangsalCari.setVisible(true);
        TglMasuk.setVisible(true);
        TglKeluar.setVisible(true);    
        jLabel8.setVisible(false);
        jLabel10.setVisible(false);
        DST.setVisible(false);
        AP.setVisible(false);
        jLabel25.setVisible(false);
        NoDST.setVisible(false);
    }
    
    public String getQueryReport(String jns, String no) {
        String poli = "", select = "", where="";
        if (jns.equals("Sakit") || jns.equals("Dokter")) {
            poli = " ,poliklinik ";
            select = " ,REPLACE(REPLACE(poliklinik.nm_poli,'SORE',''),'PAGI','') as nm_poli ,DATEDIFF(data2,data1)+1 as lama ";
            where = " and datasurat.data3=poliklinik.kd_poli ";
        }
        if (jns.equals("Istirahat") || jns.equals("Opname") || jns.equals("Resume Medis") || jns.equals("Operasi")) {
            poli = " ,bangsal ";
            select = " ,dokter.no_ijn_praktek,bangsal.nm_bangsal,DATEDIFF(data2,data1)+1 as lama ";
            where = " and datasurat.data3=bangsal.kd_bangsal ";
        }
        if (jns.equals("Buta Warna")) {
            poli = ""; select = ""; where="";
        }
        String query = "SELECT datasurat.id, datasurat.tgl_surat,datasurat.no_rawat, pasien.no_rkm_medis, pasien.nm_pasien,CONCAT(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur) as umur, pasien.pekerjaan,pasien.jk,pasien.tmp_lahir,pasien.tgl_lahir, "
                + "dokter.nm_dokter,datasurat.jns_surat, datasurat.no_surat, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,"
                + "datasurat.data1, datasurat.data2,datasurat.data3,datasurat.data4,datasurat.data5,spesialis.nm_sps,dokter.nip " + select + " "
                + "FROM datasurat,reg_periksa,pasien,kelurahan,kecamatan,kabupaten,dokter,spesialis " + poli + " "
                + "WHERE datasurat.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and datasurat.kd_dokter=dokter.kd_dokter "
                + "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab and dokter.kd_sps=spesialis.kd_sps " + where + " "
                + "and datasurat.jns_surat='" + jns + "' and datasurat.id='" + no + "'";
        return query;
    }
    
    private void getDataMaster() {
        switch (cmbJenisSurat.getSelectedItem().toString()) {
            case "Opname":
            case "Resume Medis":
                getData();
                break;
            case "Buta Warna":
                getData2();
                break;
            case "Sakit":
            case "Istirahat":
            case "Dokter":
            case "Operasi":
                getData();
                break;
            default:
                break;
        }
    }
    
    private void getTampilMaster() {
        switch (cmbJenisSurat.getSelectedItem().toString()) {
            case "Opname":
                tampil();
                break;
            case "Buta Warna":
                tampil2();
                break;
            case "Sakit":
                tampil3();
                break;
            case "Resume Medis":
                tampil4();
                break;
            case "Istirahat":
                tampil5();
                break;
            case "Dokter":
                tampil6();
                break;
            case "Operasi":
                tampil7();
                break;
            default:
                break;
        }
    }
    
    private void getSpesialis() {
        String cari = Sequel.cariIsi("SELECT kd_sps FROM datasurat join dokter on datasurat.kd_dokter=dokter.kd_dokter WHERE datasurat.no_rawat=? limit 1", TNoRw.getText());
        switch (cari) {
            case "-":
            case "S0001":
            case "S0025":
            case "S0029":
            case "S0030":
            case "UMUM":
            case "S0015":
            case "S0016":
                nm_sps = "";
                break;
            default:
                nm_sps = "Dokter Spesialis";
                break;
        }
    }

    private void isEdit(String jns) {
        if (Sequel.mengedittf("datasurat", "id=? and jns_surat=?", "kd_dokter=?,data1=?,data2=?,data3=?", 6, new String[]{
            kddokter.getText(), Valid.SetTgl(TglMasuk.getSelectedItem() + "").toString(), Valid.SetTgl(TglKeluar.getSelectedItem() + "").toString(), kdBangsal.getText(), no, jns
        }) == true) {
            getTampilMaster();
            emptTeks();
            JOptionPane.showMessageDialog(null, "Berhasil edit data surat " + jns + "...");
        }
    }

    private void isEdit2(String jns) {
        if (Sequel.mengedittf("datasurat", "id=? and jns_surat=?", "kd_dokter=?,data1=?,data3=?", 5, new String[]{
            kddokter.getText(), Valid.SetTgl(TglMasuk.getSelectedItem() + "").toString(), kdBangsal.getText(), no, jns
        }) == true) {
            getTampilMaster();
            emptTeks();
            JOptionPane.showMessageDialog(null, "Berhasil edit data surat " + jns + "...");
        }
    }
}