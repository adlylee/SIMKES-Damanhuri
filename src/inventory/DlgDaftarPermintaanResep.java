package inventory;

import bridging.BPJSProgramPRB;
import fungsi.BackgroundMusic;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import simrskhanza.DlgCariBangsal;
import simrskhanza.DlgCariPoli;

public class DlgDaftarPermintaanResep extends javax.swing.JDialog {

    private final DefaultTableModel tabMode, tabMode2, tabMode3, tabMode4, tabMode5, tabMode6, tabMode7, tabMode8;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private PreparedStatement ps, ps2, ps3;
    private ResultSet rs, rs2, rs3;
    private DlgCariObat dlgobt = new DlgCariObat(null, false);
    private DlgCariObat2 dlgobt2 = new DlgCariObat2(null, false);
    private DlgCariObat4 dlgobt4 = new DlgCariObat4(null, false);
    private String bangsal = "", aktifkanparsial = "no", kamar = "", alarm = "",
            formalarm = "", nol_detik, detik, NoResep = "", TglPeresepan = "", JamPeresepan = "",
            NoRawat = "", NoRM = "", Pasien = "", DokterPeresep = "", Status = "", KodeDokter = "", Ruang = "", KodeRuang = "", JnsBayar = "",sttsPRB="";
    private final Properties prop = new Properties();
    private DlgCariDokter dokter = new DlgCariDokter(null, false);
    private DlgCariPoli poli = new DlgCariPoli(null, false);
    private DlgCariBangsal ruang = new DlgCariBangsal(null, false);
    private int jmlparsial = 0, nilai_detik, resepbaru = 0, i = 0;
    private BackgroundMusic music;
    private boolean aktif = false;

    /**
     * Creates new form
     *
     * @param parent
     * @param modal
     */
    public DlgDaftarPermintaanResep(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode = new DefaultTableModel(null, new Object[]{
            "No.Resep", "Tgl.Peresepan", "Jam Peresepan", "No.Rawat", "No.RM",
            "Pasien", "Dokter Peresep", "Status", "Kode Dokter", "Poli/Unit", "Kode Poli","Jenis Bayar","PRB"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        tbResepRalan.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbResepRalan.setPreferredScrollableViewportSize(new Dimension(500, 500));
        tbResepRalan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 13; i++) {
            TableColumn column = tbResepRalan.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(75);
            } else if (i == 1) {
                column.setPreferredWidth(80);
            } else if (i == 2) {
                column.setPreferredWidth(85);
            } else if (i == 3) {
                column.setPreferredWidth(105);
            } else if (i == 4) {
                column.setPreferredWidth(70);
            } else if (i == 5) {
                column.setPreferredWidth(190);
            } else if (i == 6) {
                column.setPreferredWidth(190);
            } else if (i == 7) {
                column.setPreferredWidth(100);
            } else if (i == 8) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 9) {
                column.setPreferredWidth(140);
            } else if (i == 10) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if (i == 11) {
                column.setPreferredWidth(80);
            }else if (i == 12) {
                column.setPreferredWidth(35);
            }
        }

        tbResepRalan.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

                String stts_rawat = (String) table.getModel().getValueAt(row, 7);
                String norawat = (String) table.getModel().getValueAt(row, 3);
                String poli = (String) table.getModel().getValueAt(row, 9);
                String tglresep = (String) table.getModel().getValueAt(row, 1);
                int hitung = Sequel.cariInteger("SELECT COUNT('" + norawat + "') FROM resep_obat WHERE no_rawat='" + norawat + "' AND tgl_peresepan='" + tglresep + "'");
              
                if ("Sudah Terlayani".equals(stts_rawat)) {
                    setBackground(new Color(255, 178, 102));
                    setForeground(Color.BLACK);
                } else if ("Belum Terlayani".equals(stts_rawat) && poli.startsWith("POLI") && norawat.equals(norawat) && hitung > 1) {
                    setBackground(new Color(161, 242, 148));
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

                if (isSelected) {
                    setForeground(Color.RED);
                }
                return this;
            }
        });

        tabMode2 = new DefaultTableModel(null, new Object[]{
            "No.Resep", "Tgl.Resep", "Poli/Unit", "Status", "Pasien", "Dokter Peresep"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbDetailResepRalan.setModel(tabMode2);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbDetailResepRalan.setPreferredScrollableViewportSize(
                new Dimension(500, 500));
        tbDetailResepRalan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0;
                i < 6; i++) {
            TableColumn column = tbDetailResepRalan.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(75);
            } else if (i == 1) {
                column.setPreferredWidth(110);
            } else if (i == 2) {
                column.setPreferredWidth(140);
            } else if (i == 3) {
                column.setPreferredWidth(100);
            } else if (i == 4) {
                column.setPreferredWidth(350);
            } else if (i == 5) {
                column.setPreferredWidth(190);
            }
        }

        tbDetailResepRalan.setDefaultRenderer(Object.class,
                new WarnaTable());

        tabMode3 = new DefaultTableModel(null, new Object[]{
            "No.Resep", "Tgl.Peresepan", "Jam Peresepan", "No.Rawat", "No.RM",
            "Pasien", "Dokter Peresep", "Status", "Kode Dokter", "Ruang/Kamar", "Kode Bangsal","Jenis Bayar"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbResepRanap.setModel(tabMode3);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbResepRanap.setPreferredScrollableViewportSize(
                new Dimension(500, 500));
        tbResepRanap.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0;
                i < 12; i++) {
            TableColumn column = tbResepRanap.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(75);
            } else if (i == 1) {
                column.setPreferredWidth(80);
            } else if (i == 2) {
                column.setPreferredWidth(85);
            } else if (i == 3) {
                column.setPreferredWidth(105);
            } else if (i == 4) {
                column.setPreferredWidth(70);
            } else if (i == 5) {
                column.setPreferredWidth(190);
            } else if (i == 6) {
                column.setPreferredWidth(190);
            } else if (i == 7) {
                column.setPreferredWidth(100);
            } else if (i == 8) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 9) {
                column.setPreferredWidth(140);
            } else if (i == 10) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 11) {
                column.setPreferredWidth(80);
            }
        }

        tbResepRanap.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

                String stts_rawat = (String) table.getModel().getValueAt(row, 7);
                String norawat = (String) table.getModel().getValueAt(row, 3);
              
                if ("Sudah Terlayani".equals(stts_rawat)) {
                    setBackground(new Color(255, 178, 102));
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

                if (isSelected) {
                    setForeground(Color.RED);
                }
                return this;
            }
        });

        tabMode4 = new DefaultTableModel(null, new Object[]{
            "No.Resep", "Tgl.Resep", "Ruang/Kamar", "Status", "Pasien", "Dokter Peresep"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbDetailResepRanap.setModel(tabMode4);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbDetailResepRanap.setPreferredScrollableViewportSize(
                new Dimension(500, 500));
        tbDetailResepRanap.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0;
                i < 6; i++) {
            TableColumn column = tbDetailResepRanap.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(75);
            } else if (i == 1) {
                column.setPreferredWidth(110);
            } else if (i == 2) {
                column.setPreferredWidth(140);
            } else if (i == 3) {
                column.setPreferredWidth(100);
            } else if (i == 4) {
                column.setPreferredWidth(350);
            } else if (i == 5) {
                column.setPreferredWidth(190);
            }
        }

        tbDetailResepRanap.setDefaultRenderer(Object.class,
                new WarnaTable());

        tabMode5 = new DefaultTableModel(null, new Object[]{
            "No.Resep", "Tgl.Peresepan", "Jam Peresepan", "No.Rawat", "No.RM",
            "Pasien", "Dokter Peresep", "Status", "Kode Dokter", "Ruang/Kamar", "Kode Bangsal","Jenis Bayar"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbResepPulang.setModel(tabMode5);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbResepPulang.setPreferredScrollableViewportSize(
                new Dimension(500, 500));
        tbResepPulang.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0;
                i < 12; i++) {
            TableColumn column = tbResepPulang.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(75);
            } else if ((i == 1) || (i==11)) {
                column.setPreferredWidth(80);
            } else if (i == 2) {
                column.setPreferredWidth(85);
            } else if (i == 3) {
                column.setPreferredWidth(105);
            } else if (i == 4) {
                column.setPreferredWidth(70);
            } else if (i == 5) {
                column.setPreferredWidth(190);
            } else if (i == 6) {
                column.setPreferredWidth(190);
            } else if (i == 7) {
                column.setPreferredWidth(100);
            } else if (i == 8) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 9) {
                column.setPreferredWidth(140);
            } else if (i == 10) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }

        tbResepPulang.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

                String stts_rawat = (String) table.getModel().getValueAt(row, 7);
                String norawat = (String) table.getModel().getValueAt(row, 3);
              
                if ("Sudah Terlayani".equals(stts_rawat)) {
                    setBackground(new Color(255, 178, 102));
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

                if (isSelected) {
                    setForeground(Color.RED);
                }
                return this;
            }
        });

        tabMode6 = new DefaultTableModel(null, new Object[]{
            "No.Resep", "Tgl.Resep", "Ruang/Kamar", "Status", "Pasien", "Dokter Peresep"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbDetailResepPulang.setModel(tabMode6);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbDetailResepPulang.setPreferredScrollableViewportSize(
                new Dimension(500, 500));
        tbDetailResepPulang.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0;
                i < 6; i++) {
            TableColumn column = tbDetailResepPulang.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(75);
            } else if (i == 1) {
                column.setPreferredWidth(110);
            } else if (i == 2) {
                column.setPreferredWidth(140);
            } else if (i == 3) {
                column.setPreferredWidth(100);
            } else if (i == 4) {
                column.setPreferredWidth(350);
            } else if (i == 5) {
                column.setPreferredWidth(190);
            }
        }

        tbDetailResepPulang.setDefaultRenderer(Object.class,
                new WarnaTable());

        tabMode7 = new DefaultTableModel(null, new Object[]{
            "No.Resep", "Tgl.Peresepan", "Jam Peresepan", "No.Rawat", "No.RM",
            "Pasien", "Dokter Peresep", "Status", "Kode Dokter", "Ruang/Kamar", "Kode Bangsal"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbResepGabung.setModel(tabMode7);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbResepGabung.setPreferredScrollableViewportSize(
                new Dimension(500, 500));
        tbResepGabung.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0;
                i < 11; i++) {
            TableColumn column = tbResepGabung.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(75);
            } else if (i == 1) {
                column.setPreferredWidth(80);
            } else if (i == 2) {
                column.setPreferredWidth(85);
            } else if (i == 3) {
                column.setPreferredWidth(105);
            } else if (i == 4) {
                column.setPreferredWidth(70);
            } else if (i == 5) {
                column.setPreferredWidth(190);
            } else if (i == 6) {
                column.setPreferredWidth(190);
            } else if (i == 7) {
                column.setPreferredWidth(100);
            } else if (i == 8) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 9) {
                column.setPreferredWidth(140);
            } else if (i == 10) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }

        tbResepGabung.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus, int row, int col) {

                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

                String stts_rawat = (String) table.getModel().getValueAt(row, 7);
                String norawat = (String) table.getModel().getValueAt(row, 3);
              
                if ("Sudah Terlayani".equals(stts_rawat)) {
                    setBackground(new Color(255, 178, 102));
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

                if (isSelected) {
                    setForeground(Color.RED);
                }
                return this;
            }
        });

        tabMode8 = new DefaultTableModel(null, new Object[]{
            "No.Resep", "Tgl.Resep", "Ruang/Kamar", "Status", "Pasien", "Dokter Peresep"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };

        tbDetailResepGabung.setModel(tabMode8);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbDetailResepGabung.setPreferredScrollableViewportSize(
                new Dimension(500, 500));
        tbDetailResepGabung.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0;
                i < 6; i++) {
            TableColumn column = tbDetailResepGabung.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(75);
            } else if (i == 1) {
                column.setPreferredWidth(110);
            } else if (i == 2) {
                column.setPreferredWidth(140);
            } else if (i == 3) {
                column.setPreferredWidth(100);
            } else if (i == 4) {
                column.setPreferredWidth(350);
            } else if (i == 5) {
                column.setPreferredWidth(190);
            }
        }

        tbDetailResepGabung.setDefaultRenderer(Object.class,
                new WarnaTable());

        TCari.setDocument(
                new batasInput((byte) 100).getKata(TCari)
        );
        if (koneksiDB.cariCepat()
                .equals("aktif")) {
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

        dlgobt.addWindowListener(
                new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e
            ) {
            }

            @Override
            public void windowClosing(WindowEvent e
            ) {
            }

            @Override
            public void windowClosed(WindowEvent e
            ) {
                tampil();
            }

            @Override
            public void windowIconified(WindowEvent e
            ) {
            }

            @Override
            public void windowDeiconified(WindowEvent e
            ) {
            }

            @Override
            public void windowActivated(WindowEvent e
            ) {
            }

            @Override
            public void windowDeactivated(WindowEvent e
            ) {
            }
        });

        dokter.addWindowListener(
                new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e
            ) {;
            }

            @Override
            public void windowClosing(WindowEvent e
            ) {
            }

            @Override
            public void windowClosed(WindowEvent e
            ) {
                if (dokter.getTable().getSelectedRow() != -1) {
                    if (TabPilihRawat.getSelectedIndex() == 0) {
                        CrDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                        CrDokter.requestFocus();
                    } else if (TabPilihRawat.getSelectedIndex() == 1) {
                        CrDokter2.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(), 1).toString());
                        CrDokter2.requestFocus();
                    }
                }
            }

            @Override
            public void windowIconified(WindowEvent e
            ) {
            }

            @Override
            public void windowDeiconified(WindowEvent e
            ) {
            }

            @Override
            public void windowActivated(WindowEvent e
            ) {
            }

            @Override
            public void windowDeactivated(WindowEvent e
            ) {
            }
        });

        poli.addWindowListener(
                new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e
            ) {
            }

            @Override
            public void windowClosing(WindowEvent e
            ) {
            }

            @Override
            public void windowClosed(WindowEvent e
            ) {
                if (poli.getTable().getSelectedRow() != -1) {
                    CrPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(), 1).toString());
                    CrPoli.requestFocus();
                }
            }

            @Override
            public void windowIconified(WindowEvent e
            ) {
            }

            @Override
            public void windowDeiconified(WindowEvent e
            ) {
            }

            @Override
            public void windowActivated(WindowEvent e
            ) {
            }

            @Override
            public void windowDeactivated(WindowEvent e
            ) {
            }
        });

        ruang.addWindowListener(
                new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e
            ) {
            }

            @Override
            public void windowClosing(WindowEvent e
            ) {
            }

            @Override
            public void windowClosed(WindowEvent e
            ) {
                if (ruang.getTable().getSelectedRow() != -1) {
                    Kamar.setText(ruang.getTable().getValueAt(ruang.getTable().getSelectedRow(), 1).toString());
                    Kamar.requestFocus();
                }
            }

            @Override
            public void windowIconified(WindowEvent e
            ) {
            }

            @Override
            public void windowDeiconified(WindowEvent e
            ) {
            }

            @Override
            public void windowActivated(WindowEvent e
            ) {
            }

            @Override
            public void windowDeactivated(WindowEvent e
            ) {
            }
        });

        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            aktifkanparsial = prop.getProperty("AKTIFKANBILLINGPARSIAL");
            alarm = prop.getProperty("ALARMAPOTEK");
            formalarm = prop.getProperty("FORMALARMAPOTEK");
        } catch (Exception ex) {
            aktifkanparsial = "no";
            alarm = "no";
            formalarm = "ralan + ranap";
        }

        if (alarm.equals(
                "yes")) {
            jam();
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

        Popup = new javax.swing.JPopupMenu();
        MnSetKronis = new javax.swing.JMenuItem();
        MnSetPRB = new javax.swing.JMenuItem();
        MnBridgingSRB = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        jPanel2 = new javax.swing.JPanel();
        panelisi2 = new widget.panelisi();
        jLabel20 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        panelisi1 = new widget.panelisi();
        BtnTambah = new widget.Button();
        BtnEdit = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnRekap = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        TabPilihRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        TabRawatJalan = new javax.swing.JTabbedPane();
        scrollPane1 = new widget.ScrollPane();
        tbResepRalan = new widget.Table();
        scrollPane2 = new widget.ScrollPane();
        tbDetailResepRalan = new widget.Table();
        panelGlass8 = new widget.panelisi();
        jLabel14 = new widget.Label();
        CrDokter = new widget.TextBox();
        BtnSeek3 = new widget.Button();
        jLabel16 = new widget.Label();
        CrPoli = new widget.TextBox();
        BtnSeek4 = new widget.Button();
        internalFrame3 = new widget.InternalFrame();
        TabRawatInap = new javax.swing.JTabbedPane();
        scrollPane3 = new widget.ScrollPane();
        tbResepRanap = new widget.Table();
        scrollPane4 = new widget.ScrollPane();
        tbDetailResepRanap = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel15 = new widget.Label();
        CrDokter2 = new widget.TextBox();
        BtnSeek5 = new widget.Button();
        jLabel17 = new widget.Label();
        Kamar = new widget.TextBox();
        BtnSeek6 = new widget.Button();
        internalFrame4 = new widget.InternalFrame();
        TabRawatPulang = new javax.swing.JTabbedPane();
        scrollPane5 = new widget.ScrollPane();
        tbResepPulang = new widget.Table();
        scrollPane6 = new widget.ScrollPane();
        tbDetailResepPulang = new widget.Table();
        panelGlass10 = new widget.panelisi();
        jLabel18 = new widget.Label();
        CrDokter3 = new widget.TextBox();
        BtnSeek7 = new widget.Button();
        jLabel19 = new widget.Label();
        Kamar1 = new widget.TextBox();
        BtnSeek8 = new widget.Button();
        internalFrame5 = new widget.InternalFrame();
        TabRawatGabung = new javax.swing.JTabbedPane();
        scrollPane7 = new widget.ScrollPane();
        tbResepGabung = new widget.Table();
        scrollPane8 = new widget.ScrollPane();
        tbDetailResepGabung = new widget.Table();
        panelGlass11 = new widget.panelisi();
        jLabel22 = new widget.Label();
        CrDokter4 = new widget.TextBox();
        BtnSeek9 = new widget.Button();
        jLabel23 = new widget.Label();
        Kamar2 = new widget.TextBox();
        BtnSeek10 = new widget.Button();

        Popup.setName("Popup"); // NOI18N

        MnSetKronis.setBackground(new java.awt.Color(255, 255, 254));
        MnSetKronis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSetKronis.setForeground(new java.awt.Color(70, 70, 70));
        MnSetKronis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSetKronis.setText("Set Status Obat Kronis");
        MnSetKronis.setToolTipText("");
        MnSetKronis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSetKronis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSetKronis.setIconTextGap(8);
        MnSetKronis.setName("MnSetKronis"); // NOI18N
        MnSetKronis.setPreferredSize(new java.awt.Dimension(170, 25));
        MnSetKronis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSetKronisActionPerformed(evt);
            }
        });
        Popup.add(MnSetKronis);

        MnSetPRB.setBackground(new java.awt.Color(255, 255, 254));
        MnSetPRB.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSetPRB.setForeground(new java.awt.Color(70, 70, 70));
        MnSetPRB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSetPRB.setText("Set PRB");
        MnSetPRB.setToolTipText("");
        MnSetPRB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSetPRB.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSetPRB.setIconTextGap(8);
        MnSetPRB.setName("MnSetPRB"); // NOI18N
        MnSetPRB.setPreferredSize(new java.awt.Dimension(170, 25));
        MnSetPRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSetPRBActionPerformed(evt);
            }
        });
        Popup.add(MnSetPRB);

        MnBridgingSRB.setBackground(new java.awt.Color(255, 255, 254));
        MnBridgingSRB.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBridgingSRB.setForeground(new java.awt.Color(70, 70, 70));
        MnBridgingSRB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBridgingSRB.setText("Bridging PRB BPJS");
        MnBridgingSRB.setToolTipText("");
        MnBridgingSRB.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBridgingSRB.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBridgingSRB.setIconTextGap(8);
        MnBridgingSRB.setName("MnBridgingSRB"); // NOI18N
        MnBridgingSRB.setPreferredSize(new java.awt.Dimension(170, 25));
        MnBridgingSRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBridgingSRBActionPerformed(evt);
            }
        });
        Popup.add(MnBridgingSRB);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowDeactivated(java.awt.event.WindowEvent evt) {
                formWindowDeactivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Peresepan Obat Oleh Dokter ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi2.setBackground(new java.awt.Color(255, 150, 255));
        panelisi2.setName("panelisi2"); // NOI18N
        panelisi2.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        jLabel20.setText("Tgl.Peresepan :");
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setPreferredSize(new java.awt.Dimension(85, 23));
        panelisi2.add(jLabel20);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-12-2023" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelisi2.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(24, 23));
        panelisi2.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-12-2023" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelisi2.add(DTPCari2);

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi2.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(290, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi2.add(TCari);

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
        panelisi2.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
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
        panelisi2.add(BtnAll);

        jPanel2.add(panelisi2, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambah.setMnemonic('S');
        BtnTambah.setText("Validasi");
        BtnTambah.setToolTipText("Alt+S");
        BtnTambah.setName("BtnTambah"); // NOI18N
        BtnTambah.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        BtnTambah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnTambahKeyPressed(evt);
            }
        });
        panelisi1.add(BtnTambah);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('U');
        BtnEdit.setText("Ubah");
        BtnEdit.setToolTipText("Alt+U");
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
        panelisi1.add(BtnEdit);

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

        BtnRekap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/preview-16x16.png"))); // NOI18N
        BtnRekap.setMnemonic('R');
        BtnRekap.setText("Rekap");
        BtnRekap.setToolTipText("Alt+R");
        BtnRekap.setName("BtnRekap"); // NOI18N
        BtnRekap.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRekap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRekapActionPerformed(evt);
            }
        });
        BtnRekap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnRekapKeyPressed(evt);
            }
        });
        panelisi1.add(BtnRekap);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi1.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(53, 23));
        panelisi1.add(LCount);

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

        jPanel2.add(panelisi1, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        TabPilihRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabPilihRawat.setForeground(new java.awt.Color(70, 70, 70));
        TabPilihRawat.setName("TabPilihRawat"); // NOI18N
        TabPilihRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabPilihRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout());

        TabRawatJalan.setBackground(new java.awt.Color(255, 255, 253));
        TabRawatJalan.setForeground(new java.awt.Color(70, 70, 70));
        TabRawatJalan.setName("TabRawatJalan"); // NOI18N
        TabRawatJalan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatJalanMouseClicked(evt);
            }
        });

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbResepRalan.setComponentPopupMenu(Popup);
        tbResepRalan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbResepRalan.setName("tbResepRalan"); // NOI18N
        tbResepRalan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbResepRalanMouseClicked(evt);
            }
        });
        tbResepRalan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbResepRalanKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbResepRalan);

        TabRawatJalan.addTab("Daftar Resep Rawat Jalan", scrollPane1);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setOpaque(true);

        tbDetailResepRalan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDetailResepRalan.setName("tbDetailResepRalan"); // NOI18N
        tbDetailResepRalan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDetailResepRalanMouseClicked(evt);
            }
        });
        tbDetailResepRalan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDetailResepRalanKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(tbDetailResepRalan);

        TabRawatJalan.addTab("Detail Resep Rawat Jalan", scrollPane2);

        internalFrame2.add(TabRawatJalan, java.awt.BorderLayout.CENTER);

        panelGlass8.setBorder(null);
        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 41));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel14.setText("Dokter :");
        jLabel14.setName("jLabel14"); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass8.add(jLabel14);

        CrDokter.setEditable(false);
        CrDokter.setName("CrDokter"); // NOI18N
        CrDokter.setPreferredSize(new java.awt.Dimension(257, 23));
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
        jLabel16.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass8.add(jLabel16);

        CrPoli.setEditable(false);
        CrPoli.setName("CrPoli"); // NOI18N
        CrPoli.setPreferredSize(new java.awt.Dimension(257, 23));
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

        internalFrame2.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        TabPilihRawat.addTab("Rawat Jalan", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout());

        TabRawatInap.setBackground(new java.awt.Color(255, 255, 253));
        TabRawatInap.setForeground(new java.awt.Color(70, 70, 70));
        TabRawatInap.setName("TabRawatInap"); // NOI18N
        TabRawatInap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatInapMouseClicked(evt);
            }
        });

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane3.setName("scrollPane3"); // NOI18N
        scrollPane3.setOpaque(true);

        tbResepRanap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbResepRanap.setName("tbResepRanap"); // NOI18N
        tbResepRanap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbResepRanapMouseClicked(evt);
            }
        });
        tbResepRanap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbResepRanapKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(tbResepRanap);

        TabRawatInap.addTab("Daftar Resep Rawat Inap", scrollPane3);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane4.setName("scrollPane4"); // NOI18N
        scrollPane4.setOpaque(true);

        tbDetailResepRanap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDetailResepRanap.setName("tbDetailResepRanap"); // NOI18N
        tbDetailResepRanap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDetailResepRanapMouseClicked(evt);
            }
        });
        tbDetailResepRanap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDetailResepRanapKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(tbDetailResepRanap);

        TabRawatInap.addTab("Detail Resep Rawat Inap", scrollPane4);

        internalFrame3.add(TabRawatInap, java.awt.BorderLayout.CENTER);

        panelGlass9.setBorder(null);
        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 41));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel15.setText("Dokter :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass9.add(jLabel15);

        CrDokter2.setEditable(false);
        CrDokter2.setName("CrDokter2"); // NOI18N
        CrDokter2.setPreferredSize(new java.awt.Dimension(250, 23));
        panelGlass9.add(CrDokter2);

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
        panelGlass9.add(BtnSeek5);

        jLabel17.setText("Ruang/Kamar :");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(104, 23));
        panelGlass9.add(jLabel17);

        Kamar.setEditable(false);
        Kamar.setName("Kamar"); // NOI18N
        Kamar.setPreferredSize(new java.awt.Dimension(250, 23));
        panelGlass9.add(Kamar);

        BtnSeek6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek6.setMnemonic('5');
        BtnSeek6.setToolTipText("ALt+5");
        BtnSeek6.setName("BtnSeek6"); // NOI18N
        BtnSeek6.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek6ActionPerformed(evt);
            }
        });
        panelGlass9.add(BtnSeek6);

        internalFrame3.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        TabPilihRawat.addTab("Rawat Inap", internalFrame3);

        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout());

        TabRawatPulang.setBackground(new java.awt.Color(255, 255, 253));
        TabRawatPulang.setForeground(new java.awt.Color(70, 70, 70));
        TabRawatPulang.setName("TabRawatPulang"); // NOI18N
        TabRawatPulang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatPulangMouseClicked(evt);
            }
        });

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane5.setName("scrollPane5"); // NOI18N
        scrollPane5.setOpaque(true);

        tbResepPulang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbResepPulang.setName("tbResepPulang"); // NOI18N
        tbResepPulang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbResepPulangMouseClicked(evt);
            }
        });
        tbResepPulang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbResepPulangKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(tbResepPulang);

        TabRawatPulang.addTab("Daftar Resep Pulang", scrollPane5);

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane6.setName("scrollPane6"); // NOI18N
        scrollPane6.setOpaque(true);

        tbDetailResepPulang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDetailResepPulang.setName("tbDetailResepPulang"); // NOI18N
        tbDetailResepPulang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDetailResepPulangMouseClicked(evt);
            }
        });
        tbDetailResepPulang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDetailResepPulangKeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(tbDetailResepPulang);

        TabRawatPulang.addTab("Detail Resep Pulang", scrollPane6);

        internalFrame4.add(TabRawatPulang, java.awt.BorderLayout.CENTER);
        TabRawatPulang.getAccessibleContext().setAccessibleName("Daftar Resep Pulang");

        panelGlass10.setBorder(null);
        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 41));
        panelGlass10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel18.setText("Dokter :");
        jLabel18.setName("jLabel18"); // NOI18N
        jLabel18.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass10.add(jLabel18);

        CrDokter3.setEditable(false);
        CrDokter3.setName("CrDokter3"); // NOI18N
        CrDokter3.setPreferredSize(new java.awt.Dimension(250, 23));
        panelGlass10.add(CrDokter3);

        BtnSeek7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek7.setMnemonic('6');
        BtnSeek7.setToolTipText("ALt+6");
        BtnSeek7.setName("BtnSeek7"); // NOI18N
        BtnSeek7.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek7ActionPerformed(evt);
            }
        });
        panelGlass10.add(BtnSeek7);

        jLabel19.setText("Ruang/Kamar :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(104, 23));
        panelGlass10.add(jLabel19);

        Kamar1.setEditable(false);
        Kamar1.setName("Kamar1"); // NOI18N
        Kamar1.setPreferredSize(new java.awt.Dimension(250, 23));
        panelGlass10.add(Kamar1);

        BtnSeek8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek8.setMnemonic('5');
        BtnSeek8.setToolTipText("ALt+5");
        BtnSeek8.setName("BtnSeek8"); // NOI18N
        BtnSeek8.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek8ActionPerformed(evt);
            }
        });
        panelGlass10.add(BtnSeek8);

        internalFrame4.add(panelGlass10, java.awt.BorderLayout.PAGE_END);

        TabPilihRawat.addTab("Resep Pulang", internalFrame4);

        internalFrame5.setBorder(null);
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout());

        TabRawatGabung.setBackground(new java.awt.Color(255, 255, 253));
        TabRawatGabung.setForeground(new java.awt.Color(70, 70, 70));
        TabRawatGabung.setName("TabRawatGabung"); // NOI18N
        TabRawatGabung.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatGabungMouseClicked(evt);
            }
        });

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane7.setName("scrollPane7"); // NOI18N
        scrollPane7.setOpaque(true);

        tbResepGabung.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbResepGabung.setName("tbResepGabung"); // NOI18N
        tbResepGabung.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbResepGabungMouseClicked(evt);
            }
        });
        tbResepGabung.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbResepGabungKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(tbResepGabung);

        TabRawatGabung.addTab("Daftar Resep Rawat Gabung", scrollPane7);

        scrollPane8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane8.setName("scrollPane8"); // NOI18N
        scrollPane8.setOpaque(true);

        tbDetailResepGabung.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDetailResepGabung.setName("tbDetailResepGabung"); // NOI18N
        tbDetailResepGabung.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDetailResepGabungMouseClicked(evt);
            }
        });
        tbDetailResepGabung.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDetailResepGabungKeyPressed(evt);
            }
        });
        scrollPane8.setViewportView(tbDetailResepGabung);

        TabRawatGabung.addTab("Detail Resep Rawat Gabung", scrollPane8);

        internalFrame5.add(TabRawatGabung, java.awt.BorderLayout.CENTER);

        panelGlass11.setBorder(null);
        panelGlass11.setName("panelGlass11"); // NOI18N
        panelGlass11.setPreferredSize(new java.awt.Dimension(44, 41));
        panelGlass11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel22.setText("Dokter :");
        jLabel22.setName("jLabel22"); // NOI18N
        jLabel22.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass11.add(jLabel22);

        CrDokter4.setEditable(false);
        CrDokter4.setName("CrDokter4"); // NOI18N
        CrDokter4.setPreferredSize(new java.awt.Dimension(250, 23));
        panelGlass11.add(CrDokter4);

        BtnSeek9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek9.setMnemonic('6');
        BtnSeek9.setToolTipText("ALt+6");
        BtnSeek9.setName("BtnSeek9"); // NOI18N
        BtnSeek9.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek9ActionPerformed(evt);
            }
        });
        panelGlass11.add(BtnSeek9);

        jLabel23.setText("Ruang/Kamar :");
        jLabel23.setName("jLabel23"); // NOI18N
        jLabel23.setPreferredSize(new java.awt.Dimension(104, 23));
        panelGlass11.add(jLabel23);

        Kamar2.setEditable(false);
        Kamar2.setName("Kamar2"); // NOI18N
        Kamar2.setPreferredSize(new java.awt.Dimension(250, 23));
        panelGlass11.add(Kamar2);

        BtnSeek10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek10.setMnemonic('5');
        BtnSeek10.setToolTipText("ALt+5");
        BtnSeek10.setName("BtnSeek10"); // NOI18N
        BtnSeek10.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek10ActionPerformed(evt);
            }
        });
        panelGlass11.add(BtnSeek10);

        internalFrame5.add(panelGlass11, java.awt.BorderLayout.PAGE_END);

        TabPilihRawat.addTab("Rawat Gabung", internalFrame5);

        internalFrame1.add(TabPilihRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            //TabRawatMouseClicked(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            tbResepRalan.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        pilihTab();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void tbResepRalanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbResepRalanMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            if (evt.getClickCount() == 2) {
                if (var.getberi_obat() == true) {
                    BtnTambahActionPerformed(null);
                }
            }
        }
}//GEN-LAST:event_tbResepRalanMouseClicked

    private void tbResepRalanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbResepRalanKeyPressed
        if (tabMode.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
                if (var.getberi_obat() == true) {
                    BtnTambahActionPerformed(null);
                }
            }
        }
}//GEN-LAST:event_tbResepRalanKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if (TabPilihRawat.getSelectedIndex() == 0) {
            if (TabRawatJalan.getSelectedIndex() == 0) {
                if (tabMode.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    // BtnBatal.requestFocus();
                } else if (tabMode.getRowCount() != 0) {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Map<String, Object> param = new HashMap<>();
                    param.put("parameter", "%" + TCari.getText().trim() + "%");
                    param.put("dokter", "%" + CrDokter.getText().trim() + "%");
                    param.put("poli", "%" + CrPoli.getText().trim() + "%");
                    param.put("tanggal1", Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                    param.put("tanggal2", Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                    param.put("namars", var.getnamars());
                    param.put("alamatrs", var.getalamatrs());
                    param.put("kotars", var.getkabupatenrs());
                    param.put("propinsirs", var.getpropinsirs());
                    param.put("kontakrs", var.getkontakrs());
                    param.put("emailrs", var.getemailrs());
                    param.put("logo", Sequel.cariGambar("select logo from setting"));
                    Valid.MyReport("rptDaftarPermintaanResep.jrxml", param, "::[ Laporan Daftar Permintaan Resep ]::");
                    this.setCursor(Cursor.getDefaultCursor());
                }
            } else if (TabRawatJalan.getSelectedIndex() == 1) {
                if (tabMode2.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                } else if (tabMode2.getRowCount() != 0) {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Sequel.queryu("delete from temporary_resep");

                    for (int i = 0; i < tabMode2.getRowCount(); i++) {
                        Sequel.menyimpan("temporary_resep", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", 38, new String[]{
                            "0", tabMode2.getValueAt(i, 0).toString(), tabMode2.getValueAt(i, 1).toString(), tabMode2.getValueAt(i, 2).toString(),
                            tabMode2.getValueAt(i, 3).toString(), tabMode2.getValueAt(i, 4).toString(), tabMode2.getValueAt(i, 5).toString(), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
                        });
                    }

                    Map<String, Object> param = new HashMap<>();
                    param.put("namars", var.getnamars());
                    param.put("alamatrs", var.getalamatrs());
                    param.put("kotars", var.getkabupatenrs());
                    param.put("propinsirs", var.getpropinsirs());
                    param.put("kontakrs", var.getkontakrs());
                    param.put("emailrs", var.getemailrs());
                    param.put("logo", Sequel.cariGambar("select logo from setting"));
                    Valid.MyReport2("rptDaftarResep.jrxml", "report", "::[ Daftar Resep Obat ]::",
                            "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14 from temporary_resep order by no asc", param);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        } else if (TabPilihRawat.getSelectedIndex() == 1) {
            if (TabRawatInap.getSelectedIndex() == 0) {
                if (tabMode3.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    // BtnBatal.requestFocus();
                } else if (tabMode3.getRowCount() != 0) {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Map<String, Object> param = new HashMap<>();
                    param.put("parameter", "%" + TCari.getText().trim() + "%");
                    param.put("dokter", "%" + CrDokter2.getText().trim() + "%");
                    param.put("poli", "%" + Kamar.getText().trim() + "%");
                    param.put("tanggal1", Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                    param.put("tanggal2", Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                    param.put("namars", var.getnamars());
                    param.put("alamatrs", var.getalamatrs());
                    param.put("kotars", var.getkabupatenrs());
                    param.put("propinsirs", var.getpropinsirs());
                    param.put("kontakrs", var.getkontakrs());
                    param.put("emailrs", var.getemailrs());
                    param.put("logo", Sequel.cariGambar("select logo from setting"));
                    Valid.MyReport("rptDaftarPermintaanResep2.jrxml", param, "::[ Laporan Daftar Permintaan Resep ]::");
                    this.setCursor(Cursor.getDefaultCursor());
                }
            } else if (TabRawatInap.getSelectedIndex() == 1) {
                if (tabMode4.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                } else if (tabMode4.getRowCount() != 0) {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    Sequel.queryu("delete from temporary_resep");

                    for (int i = 0; i < tabMode4.getRowCount(); i++) {
                        Sequel.menyimpan("temporary_resep", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", 38, new String[]{
                            "0", tabMode4.getValueAt(i, 0).toString(), tabMode4.getValueAt(i, 1).toString(), tabMode4.getValueAt(i, 2).toString(),
                            tabMode4.getValueAt(i, 3).toString(), tabMode4.getValueAt(i, 4).toString(), tabMode4.getValueAt(i, 5).toString(), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""
                        });
                    }

                    Map<String, Object> param = new HashMap<>();
                    param.put("namars", var.getnamars());
                    param.put("alamatrs", var.getalamatrs());
                    param.put("kotars", var.getkabupatenrs());
                    param.put("propinsirs", var.getpropinsirs());
                    param.put("kontakrs", var.getkontakrs());
                    param.put("emailrs", var.getemailrs());
                    param.put("logo", Sequel.cariGambar("select logo from setting"));
                    Valid.MyReport2("rptDaftarResep2.jrxml", "report", "::[ Daftar Resep Obat ]::",
                            "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14 from temporary_resep order by no asc", param);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            //Valid.pindah(evt,BtnEdit,BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        if (TabPilihRawat.getSelectedIndex() == 0) {
            CrDokter.setText("");
            CrPoli.setText("");
            pilihRalan();
        } else if (TabPilihRawat.getSelectedIndex() == 0) {
            CrDokter2.setText("");
            Kamar.setText("");
            pilihRanap();
        }
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnAllActionPerformed(null);
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

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        if (TabPilihRawat.getSelectedIndex() == 0) {
            if (TabRawatJalan.getSelectedIndex() == 0) {
                if (tabMode.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                } else if (NoRawat.equals("")) {
                    JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data resep dokter yang mau divalidasi..!!");
                } else {
                    if (Status.equals("Sudah Terlayani")) {
                        JOptionPane.showMessageDialog(rootPane, "Resep sudah tervalidasi ..!!");
                    } else {
                        jmlparsial = 0;
                        if (aktifkanparsial.equals("yes")) {
                            jmlparsial = Sequel.cariInteger("select count(kd_pj) from set_input_parsial where kd_pj=?", Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?", NoRawat));
                        }
                        if (jmlparsial > 0) {
                            panggilform();
                        } else {
                            if (Sequel.cariRegistrasi(NoRawat) > 0) {
                                JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi ..!!");
                            } else {
                                panggilform();
                            }
                        }
                    }
                }
            } else if (TabRawatJalan.getSelectedIndex() == 1) {
                JOptionPane.showMessageDialog(null, "Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }
        } else if (TabPilihRawat.getSelectedIndex() == 1) {
            if (TabRawatInap.getSelectedIndex() == 0) {
                if (tabMode3.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                } else if (NoRawat.equals("")) {
                    JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data resep dokter yang mau divalidasi..!!");
                } else {
                    if (Status.equals("Sudah Terlayani")) {
                        JOptionPane.showMessageDialog(rootPane, "Resep sudah tervalidasi ..!!");
                    } else {
                        if (Sequel.cariRegistrasi(NoRawat) > 0) {
                            JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi ..!!");
                        } else {
                            panggilform2();
                        }
                    }
                }
            } else if (TabRawatInap.getSelectedIndex() == 1) {
                JOptionPane.showMessageDialog(null, "Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }
        } else if (TabPilihRawat.getSelectedIndex() == 2) {
            if (TabRawatPulang.getSelectedIndex() == 0) {
                if (tabMode5.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                } else if (NoRawat.equals("")) {
                    JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data resep dokter yang mau divalidasi..!!");
                } else {
                    if (Status.equals("Sudah Terlayani")) {
                        JOptionPane.showMessageDialog(rootPane, "Resep sudah tervalidasi ..!!");
                    } else {
                        if (Sequel.cariRegistrasi(NoRawat) > 0) {
                            JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi ..!!");
                        } else {
                            panggilform3();
                        }
                    }
                }
            } else if (TabRawatPulang.getSelectedIndex() == 1) {
                JOptionPane.showMessageDialog(null, "Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }
        } else if (TabPilihRawat.getSelectedIndex() == 3) {
            if (TabRawatGabung.getSelectedIndex() == 0) {
                if (tabMode7.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                } else if (NoRawat.equals("")) {
                    JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data resep dokter yang mau divalidasi..!!");
                } else {
                    if (Status.equals("Sudah Terlayani")) {
                        JOptionPane.showMessageDialog(rootPane, "Resep sudah tervalidasi ..!!");
                    } else {
                        if (Sequel.cariRegistrasi(NoRawat) > 0) {
                            JOptionPane.showMessageDialog(rootPane, "Data billing sudah terverifikasi ..!!");
                        } else {
                            panggilform2();
                        }
                    }
                }
            } else if (TabRawatGabung.getSelectedIndex() == 1) {
                JOptionPane.showMessageDialog(null, "Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }
        }
}//GEN-LAST:event_BtnTambahActionPerformed

    private void BtnTambahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnTambahKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnTambahActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnTambahKeyPressed
    /*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        pilihTab();
    }//GEN-LAST:event_formWindowOpened

    private void TabRawatJalanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatJalanMouseClicked
        pilihRalan();
    }//GEN-LAST:event_TabRawatJalanMouseClicked

    private void tbDetailResepRalanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDetailResepRalanMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDetailResepRalanMouseClicked

    private void tbDetailResepRalanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDetailResepRalanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDetailResepRalanKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if (TabPilihRawat.getSelectedIndex() == 0) {
            if (TabRawatJalan.getSelectedIndex() == 0) {
                if (tabMode.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                } else if (NoRawat.equals("")) {
                    JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data resep dokter yang mau divalidasi..!!");
                } else {
                    int reply = JOptionPane.showConfirmDialog(rootPane, "Apakah yakin ingin mengubah data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {                        
                        if (Status.equals("Sudah Terlayani")) {
                            JOptionPane.showMessageDialog(rootPane, "Resep sudah tervalidasi ..!!");
                        } else {
                            DlgPeresepanDokter resep = new DlgPeresepanDokter(null, false);
                            resep.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
                            resep.setLocationRelativeTo(internalFrame1);
                            resep.MatikanJam();
                            resep.setNoRm(NoRawat, Valid.SetTgl2(TglPeresepan), JamPeresepan.substring(0, 2), JamPeresepan.substring(3, 5), JamPeresepan.substring(6, 8), KodeDokter, DokterPeresep, "ralan");
                            resep.isCek();
                            resep.tampilobat(NoResep);
                            TeksKosong();
                            resep.setVisible(true);
                        }
                    }
                }
            } else if (TabRawatJalan.getSelectedIndex() == 1) {
                JOptionPane.showMessageDialog(null, "Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }
        } else if (TabPilihRawat.getSelectedIndex() == 1) {
            if (TabRawatInap.getSelectedIndex() == 0) {
                if (tabMode3.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                } else if (NoRawat.equals("")) {
                    JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data resep dokter yang mau divalidasi..!!");
                } else {
                    int reply = JOptionPane.showConfirmDialog(rootPane, "Apakah yakin ingin mengubah data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {                        
                        if (Status.equals("Sudah Terlayani")) {
                            JOptionPane.showMessageDialog(rootPane, "Resep sudah tervalidasi ..!!");
                        } else {
                            kamar = Sequel.cariIsi("select kd_bangsal from kamar inner join kamar_inap on kamar_inap.kd_kamar=kamar.kd_kamar "
                                    + "where kamar_inap.no_rawat=? order by kamar_inap.tgl_masuk desc limit 1 ", NoRawat);
                            bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?", kamar);
                            if (bangsal.equals("")) {
                                if (Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")) {
                                    var.setkdbangsal(kamar);
                                } else {
                                    var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
                                }
                            } else {
                                var.setkdbangsal(bangsal);
                            }
                            DlgPeresepanDokter resep = new DlgPeresepanDokter(null, false);
                            resep.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                            resep.setLocationRelativeTo(internalFrame1);
                            resep.MatikanJam();
                            resep.setNoRm(NoRawat, Valid.SetTgl2(TglPeresepan), JamPeresepan.substring(0, 2), JamPeresepan.substring(3, 5), JamPeresepan.substring(6, 8), KodeDokter, DokterPeresep, "ranap");
                            resep.isCek();
                            resep.tampilobat(NoResep);
                            TeksKosong();
                            resep.setVisible(true);
                        }
                    }
                }
            } else if (TabRawatInap.getSelectedIndex() == 1) {
                JOptionPane.showMessageDialog(null, "Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }
        } else if (TabPilihRawat.getSelectedIndex() == 2) {
            if (TabRawatPulang.getSelectedIndex() == 0) {
                if (tabMode5.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                } else if (NoRawat.equals("")) {
                    JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data resep dokter yang mau divalidasi..!!");
                } else {
                    int reply = JOptionPane.showConfirmDialog(rootPane, "Apakah yakin ingin mengubah data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {                        
                        if (Status.equals("Sudah Terlayani")) {
                            JOptionPane.showMessageDialog(rootPane, "Resep sudah tervalidasi ..!!");
                        } else {
    //                        kamar = Sequel.cariIsi("select kd_bangsal from kamar inner join kamar_inap on kamar_inap.kd_kamar=kamar.kd_kamar "
    //                                + "where kamar_inap.no_rawat=? order by kamar_inap.tgl_masuk desc limit 1 ", NoRawat);
    //                        bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?", kamar);
    //                        if (bangsal.equals("")) {
    //                            if (Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")) {
    //                                var.setkdbangsal(kamar);
    //                            } else {
    //                                var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
    //                            }
    //                        } else {
    //                            var.setkdbangsal(bangsal);
    //                        }
    //                        DlgPeresepanDokter resep = new DlgPeresepanDokter(null, false);
    //                        resep.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
    //                        resep.setLocationRelativeTo(internalFrame1);
    //                        resep.MatikanJam();
    //                        resep.setNoRm(NoRawat, Valid.SetTgl2(TglPeresepan), JamPeresepan.substring(0, 2), JamPeresepan.substring(3, 5), JamPeresepan.substring(6, 8), KodeDokter, DokterPeresep, "ranap");
    //                        resep.isCek();
    //                        resep.tampilobat(NoResep);
    //                        TeksKosong();
    //                        resep.setVisible(true);
                            JOptionPane.showMessageDialog(null, "Masih Dalam Tahap Pengembangan");
                        }
                    }
                }
            } else if (TabRawatPulang.getSelectedIndex() == 1) {
                JOptionPane.showMessageDialog(null, "Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }
        }

    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnTambah, BtnPrint);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void BtnRekapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRekapActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgResepObat resep = new DlgResepObat(null, false);
        resep.tampil();
        resep.emptTeks();
        resep.isCek();
        resep.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        resep.setLocationRelativeTo(internalFrame1);
        resep.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnRekapActionPerformed

    private void BtnRekapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnRekapKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnRekapKeyPressed

    private void TabPilihRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabPilihRawatMouseClicked
        pilihTab();
    }//GEN-LAST:event_TabPilihRawatMouseClicked

    private void tbResepRanapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbResepRanapMouseClicked
        if (tabMode3.getRowCount() != 0) {
            try {
                getData2();
            } catch (java.lang.NullPointerException e) {
            }
            if (evt.getClickCount() == 2) {
                if (var.getberi_obat() == true) {
                    BtnTambahActionPerformed(null);
                }
            }
        }
    }//GEN-LAST:event_tbResepRanapMouseClicked

    private void tbResepRanapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbResepRanapKeyPressed
        if (tabMode3.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
                try {
                    getData2();
                } catch (java.lang.NullPointerException e) {
                }
                if (var.getberi_obat() == true) {
                    BtnTambahActionPerformed(null);
                }
            }
        }
    }//GEN-LAST:event_tbResepRanapKeyPressed

    private void tbDetailResepRanapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDetailResepRanapMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDetailResepRanapMouseClicked

    private void tbDetailResepRanapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDetailResepRanapKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDetailResepRanapKeyPressed

    private void TabRawatInapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatInapMouseClicked
        pilihRanap();
    }//GEN-LAST:event_TabRawatInapMouseClicked

    private void BtnSeek3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek3ActionPerformed
        dokter.isCek();
        dokter.TCari.requestFocus();
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeek3ActionPerformed

    private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
        poli.isCek();
        poli.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setVisible(true);
    }//GEN-LAST:event_BtnSeek4ActionPerformed

    private void BtnSeek5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek5ActionPerformed
        dokter.isCek();
        dokter.TCari.requestFocus();
        dokter.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeek5ActionPerformed

    private void BtnSeek6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek6ActionPerformed
        ruang.isCek();
        ruang.emptTeks();
        ruang.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        ruang.setLocationRelativeTo(internalFrame1);
        ruang.setVisible(true);
    }//GEN-LAST:event_BtnSeek6ActionPerformed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if (TabPilihRawat.getSelectedIndex() == 0) {
            if (TabRawatJalan.getSelectedIndex() == 0) {
                if (tabMode.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                } else if (NoRawat.equals("")) {
                    JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data resep dokter yang mau divalidasi..!!");
                } else {
                    int reply = JOptionPane.showConfirmDialog(rootPane, "Apakah yakin ingin menghapus data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        if (Status.equals("Sudah Terlayani")) {
                            JOptionPane.showMessageDialog(rootPane, "Resep sudah tervalidasi ..!!");
                        } else {
                            Sequel.meghapus("resep_obat", "no_resep", NoResep);
                            TeksKosong();
                            tampil();
                        }
                    }
                }
            } else if (TabRawatJalan.getSelectedIndex() == 1) {
                JOptionPane.showMessageDialog(null, "Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }
        } else if (TabPilihRawat.getSelectedIndex() == 1) {
            if (TabRawatInap.getSelectedIndex() == 0) {
                if (tabMode3.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                } else if (NoRawat.equals("")) {
                    JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data resep dokter yang mau divalidasi..!!");
                } else {
                    int reply = JOptionPane.showConfirmDialog(rootPane, "Apakah yakin ingin menghapus data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {                        
                        if (Status.equals("Sudah Terlayani")) {
                            JOptionPane.showMessageDialog(rootPane, "Resep sudah tervalidasi ..!!");
                        } else {
                            Sequel.meghapus("resep_obat", "no_resep", NoResep);
                            TeksKosong();
                            tampil3();
                        }
                    }
                }
            } else if (TabRawatInap.getSelectedIndex() == 1) {
                JOptionPane.showMessageDialog(null, "Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }
        } else if (TabPilihRawat.getSelectedIndex() == 2) {
            if (TabRawatPulang.getSelectedIndex() == 0) {
                if (tabMode5.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Maaf, data sudah habis...!!!!");
                    TCari.requestFocus();
                } else if (NoRawat.equals("")) {
                    JOptionPane.showMessageDialog(null, "Maaf, Silahkan pilih data resep dokter yang mau divalidasi..!!");
                } else {
                    int reply = JOptionPane.showConfirmDialog(rootPane, "Apakah yakin ingin menghapus data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {                        
                        if (Status.equals("Sudah Terlayani")) {
                            JOptionPane.showMessageDialog(rootPane, "Resep sudah tervalidasi ..!!");
                        } else {
                            Sequel.meghapus("resep_pulang", "no_resep", NoResep);
                            TeksKosong();
                            tampil5();
                        }
                    }
                }
            } else if (TabRawatPulang.getSelectedIndex() == 1) {
                JOptionPane.showMessageDialog(null, "Maaf, silahkan buka Daftar Resep...!!!!");
                TCari.requestFocus();
            }
        }
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnEdit);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        aktif = true;
    }//GEN-LAST:event_formWindowActivated

    private void formWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeactivated
        aktif = false;
    }//GEN-LAST:event_formWindowDeactivated

    private void tbResepPulangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbResepPulangMouseClicked
        if (tabMode5.getRowCount() != 0) {
            try {
                getData3();
            } catch (java.lang.NullPointerException e) {
            }
            if (evt.getClickCount() == 2) {
                if (var.getberi_obat() == true) {
                    BtnTambahActionPerformed(null);
                }
            }
        }
    }//GEN-LAST:event_tbResepPulangMouseClicked

    private void tbResepPulangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbResepPulangKeyPressed
        if (tabMode5.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
                try {
                    getData3();
                } catch (java.lang.NullPointerException e) {
                }
                if (var.getberi_obat() == true) {
                    BtnTambahActionPerformed(null);
                }
            }
        }
    }//GEN-LAST:event_tbResepPulangKeyPressed

    private void tbDetailResepPulangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDetailResepPulangMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDetailResepPulangMouseClicked

    private void tbDetailResepPulangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDetailResepPulangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDetailResepPulangKeyPressed

    private void TabRawatPulangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatPulangMouseClicked
        pilihPulang();
    }//GEN-LAST:event_TabRawatPulangMouseClicked

    private void BtnSeek7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek7ActionPerformed

    private void BtnSeek8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek8ActionPerformed

    private void tbResepGabungMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbResepGabungMouseClicked
        if (tabMode7.getRowCount() != 0) {
            try {
                getData4();
            } catch (java.lang.NullPointerException e) {
            }
            if (evt.getClickCount() == 2) {
                if (var.getberi_obat() == true) {
                    BtnTambahActionPerformed(null);
                }
            }
        }
    }//GEN-LAST:event_tbResepGabungMouseClicked

    private void tbResepGabungKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbResepGabungKeyPressed
         if (tabMode7.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
                try {
                    getData4();
                } catch (java.lang.NullPointerException e) {
                }
                if (var.getberi_obat() == true) {
                    BtnTambahActionPerformed(null);
                }
            }
        }
    }//GEN-LAST:event_tbResepGabungKeyPressed

    private void tbDetailResepGabungMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDetailResepGabungMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDetailResepGabungMouseClicked

    private void tbDetailResepGabungKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDetailResepGabungKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDetailResepGabungKeyPressed

    private void TabRawatGabungMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatGabungMouseClicked
        pilihGabung();
    }//GEN-LAST:event_TabRawatGabungMouseClicked

    private void BtnSeek9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek9ActionPerformed

    private void BtnSeek10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek10ActionPerformed

    private void MnSetKronisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSetKronisActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        } else if (!JnsBayar.contains("BPJS")) {
            JOptionPane.showMessageDialog(null, "Maaf, cara bayar tidak sesuai...!!!");
        } else if (NoRawat.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbResepRalan.requestFocus();
        } else {
            try {
                if (Sequel.cariInteger("SELECT count(no_rawat) FROM mlite_veronisa WHERE no_rawat = '" + NoRawat + "'") > 0) {
                    int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiits, Yakin ingin hapus status obat kronis..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        Sequel.meghapus("mlite_veronisa", "no_rawat", tbResepRalan.getValueAt(tbResepRalan.getSelectedRow(), 3).toString());
                        tampil();
                        emptTeks();
                    }
                } else {
                    String kronis;
                    kronis = "insert into mlite_veronisa (id, tanggal, no_rkm_medis, no_rawat, tgl_registrasi, nosep, status, username) "
                            + "values ('0','" + Sequel.cariIsi("select current_date()") + "','" + NoRM + "','" + NoRawat + "','" + Sequel.cariIsi("SELECT tgl_registrasi FROM reg_periksa WHERE no_rawat=?",NoRawat) + "','" + Sequel.cariIsi("SELECT no_sep FROM bridging_sep WHERE no_rawat=?",NoRawat) + "','Belum','" + var.getkode() + "')";
                    PreparedStatement ps_krns = koneksiDB.condb().prepareStatement(kronis);
                    ps_krns.execute();
                    JOptionPane.showMessageDialog(rootPane, "Berhasil set status obat kronis..");
                    tampil();
                }
            } catch (Exception e) {
                System.out.println("Gagal menyimpan " + e);
            }
        }
    }//GEN-LAST:event_MnSetKronisActionPerformed

    private void MnSetPRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSetPRBActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        } else if (!JnsBayar.contains("BPJS")) {
            JOptionPane.showMessageDialog(null, "Maaf, cara bayar tidak sesuai...!!!");
        } else if (NoRawat.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbResepRalan.requestFocus();
        } else {
            try {
                if (Sequel.cariInteger("SELECT count(no_rawat) FROM mlite_srb WHERE no_rawat = '" + NoRawat + "'") > 0) {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    int reply = JOptionPane.showConfirmDialog(rootPane, "Eeiiits, Yakin ingin hapus status pasien PRB..??", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        Sequel.meghapus("mlite_srb", "no_rawat", tbResepRalan.getValueAt(tbResepRalan.getSelectedRow(), 3).toString());
                        tampil();
                        emptTeks();
                        JOptionPane.showMessageDialog(rootPane, "Berhasil hapus set status PRB..");
                    }
                    this.setCursor(Cursor.getDefaultCursor());
                } else {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate today = LocalDate.now();
                    String kronis = "insert into mlite_srb (id, tanggal, no_rkm_medis, no_rawat, tgl_registrasi, nosep, status, username) "
                            + "values ('0','" + today + "','" + NoRM + "','" + NoRawat + "','" + Sequel.cariIsi("SELECT tgl_registrasi FROM reg_periksa WHERE no_rawat=?",NoRawat) + "','" + Sequel.cariIsi("SELECT no_sep FROM bridging_sep WHERE no_rawat=?",NoRawat) + "','Belum','" + var.getkode() + "')";
                    PreparedStatement ps_krns = koneksiDB.condb().prepareStatement(kronis);
                    ps_krns.execute();
                    tampil();
                    JOptionPane.showMessageDialog(rootPane, "Berhasil set status PRB..");
                    this.setCursor(Cursor.getDefaultCursor());
                }
            } catch (Exception e) {
                System.out.println("Gagal menyimpan " + e);
            }
        }
    }//GEN-LAST:event_MnSetPRBActionPerformed

    private void MnBridgingSRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBridgingSRBActionPerformed
        String nosep = Sequel.cariIsi("select no_sep from bridging_sep where no_rawat=?",NoRawat); 
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        } else if (!JnsBayar.contains("BPJS")) {
            JOptionPane.showMessageDialog(null, "Maaf, cara bayar tidak sesuai...!!!");
        } else if (!sttsPRB.trim().equals("Ya")) {
            JOptionPane.showMessageDialog(null, "Maaf, Pasien belum dilakukan set PRB...!!!");
        } else if (nosep.equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, SEP Pasien belum tersedia...!!!");
        } else if (NoRawat.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbResepRalan.requestFocus();
        } else {
            if (tbResepRalan.getSelectedRow() != -1) {
                try {
                    ps = koneksi.prepareStatement("select no_peserta,tgl_lahir,jk,alamat,email from pasien where no_rkm_medis=?");
                    try {
                        ps.setString(1, NoRM);
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            if (rs.getString("no_peserta").length() < 13) {
                                JOptionPane.showMessageDialog(null, "Kartu BPJS Pasien tidak valid, silahkan hubungi bagian terkait..!!");
                            } else {
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                BPJSProgramPRB form = new BPJSProgramPRB(null, false);
                                form.setNoRm(NoRawat, nosep, rs.getString("no_peserta"), NoRM, Pasien, rs.getString("alamat"), rs.getString("email"), KodeDokter, tbResepRalan.getValueAt(tbResepRalan.getSelectedRow(), 6).toString());
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
    }//GEN-LAST:event_MnBridgingSRBActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgDaftarPermintaanResep dialog = new DlgDaftarPermintaanResep(new javax.swing.JFrame(), true);
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
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnRekap;
    private widget.Button BtnSeek10;
    private widget.Button BtnSeek3;
    private widget.Button BtnSeek4;
    private widget.Button BtnSeek5;
    private widget.Button BtnSeek6;
    private widget.Button BtnSeek7;
    private widget.Button BtnSeek8;
    private widget.Button BtnSeek9;
    private widget.Button BtnTambah;
    private widget.TextBox CrDokter;
    private widget.TextBox CrDokter2;
    private widget.TextBox CrDokter3;
    private widget.TextBox CrDokter4;
    private widget.TextBox CrPoli;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox Kamar;
    private widget.TextBox Kamar1;
    private widget.TextBox Kamar2;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnBridgingSRB;
    private javax.swing.JMenuItem MnSetKronis;
    private javax.swing.JMenuItem MnSetPRB;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabPilihRawat;
    private javax.swing.JTabbedPane TabRawatGabung;
    private javax.swing.JTabbedPane TabRawatInap;
    private javax.swing.JTabbedPane TabRawatJalan;
    private javax.swing.JTabbedPane TabRawatPulang;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
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
    private javax.swing.JPanel jPanel2;
    private widget.Label label10;
    private widget.Label label9;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass11;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi2;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.ScrollPane scrollPane7;
    private widget.ScrollPane scrollPane8;
    private widget.Table tbDetailResepGabung;
    private widget.Table tbDetailResepPulang;
    private widget.Table tbDetailResepRalan;
    private widget.Table tbDetailResepRanap;
    private widget.Table tbResepGabung;
    private widget.Table tbResepPulang;
    private widget.Table tbResepRalan;
    private widget.Table tbResepRanap;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            ps = koneksi.prepareStatement("select resep_obat.no_resep,resep_obat.tgl_peresepan,resep_obat.jam_peresepan,"
                    + " resep_obat.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,resep_obat.kd_dokter,dokter.nm_dokter, "
                    + " if(resep_obat.jam_peresepan=resep_obat.jam,'Belum Terlayani','Sudah Terlayani') as status,poliklinik.nm_poli,  "
                    + " reg_periksa.kd_poli,penjab.png_jawab from resep_obat inner join reg_periksa inner join pasien inner join penjab inner join dokter inner join poliklinik "
                    + " on resep_obat.no_rawat=reg_periksa.no_rawat and reg_periksa.kd_poli=poliklinik.kd_poli "
                    + " and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and resep_obat.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj where "
                    + " resep_obat.status='ralan' and resep_obat.tgl_perawatan between ? and ? and dokter.nm_dokter like ? and poliklinik.nm_poli like ? and resep_obat.no_resep like ? or "
                    + " resep_obat.status='ralan' and resep_obat.tgl_perawatan between ? and ? and dokter.nm_dokter like ? and poliklinik.nm_poli like ? and resep_obat.no_rawat like ? or "
                    + " resep_obat.status='ralan' and resep_obat.tgl_perawatan between ? and ? and dokter.nm_dokter like ? and poliklinik.nm_poli like ? and pasien.no_rkm_medis like ? or "
                    + " resep_obat.status='ralan' and resep_obat.tgl_perawatan between ? and ? and dokter.nm_dokter like ? and poliklinik.nm_poli like ? and pasien.nm_pasien like ? or "
                    + " resep_obat.status='ralan' and resep_obat.tgl_perawatan between ? and ? and dokter.nm_dokter like ? and poliklinik.nm_poli like ? and dokter.nm_dokter like ? order by resep_obat.tgl_perawatan desc,resep_obat.jam desc");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, "%" + CrDokter.getText().trim() + "%");
                ps.setString(4, "%" + CrPoli.getText().trim() + "%");
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                ps.setString(6, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(7, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(8, "%" + CrDokter.getText().trim() + "%");
                ps.setString(9, "%" + CrPoli.getText().trim() + "%");
                ps.setString(10, "%" + TCari.getText().trim() + "%");
                ps.setString(11, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(12, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(13, "%" + CrDokter.getText().trim() + "%");
                ps.setString(14, "%" + CrPoli.getText().trim() + "%");
                ps.setString(15, "%" + TCari.getText().trim() + "%");
                ps.setString(16, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(17, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(18, "%" + CrDokter.getText().trim() + "%");
                ps.setString(19, "%" + CrPoli.getText().trim() + "%");
                ps.setString(20, "%" + TCari.getText().trim() + "%");
                ps.setString(21, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(22, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(23, "%" + CrDokter.getText().trim() + "%");
                ps.setString(24, "%" + CrPoli.getText().trim() + "%");
                ps.setString(25, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new String[]{
                        rs.getString("no_resep"), rs.getString("tgl_peresepan"), rs.getString("jam_peresepan"), rs.getString("no_rawat"),
                        rs.getString("no_rkm_medis"), rs.getString("nm_pasien"), rs.getString("nm_dokter"), rs.getString("status"),
                        rs.getString("kd_dokter"), rs.getString("nm_poli"), rs.getString("kd_poli"),rs.getString("png_jawab")
                    });
                }

                LCount.setText("" + tabMode.getRowCount());
            } catch (Exception ex) {
                System.out.println("Notifikasi : " + ex);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (SQLException e) {
            System.out.println("Notifikasi : " + e);
        }
        cekKronis();
        cekPRB();
    }

    public void emptTeks() {
        TCari.setText("");
        TCari.requestFocus();
    }

    private void getData() {
        if (tbResepRalan.getSelectedRow() != -1) {
            NoResep = tbResepRalan.getValueAt(tbResepRalan.getSelectedRow(), 0).toString();
            TglPeresepan = tbResepRalan.getValueAt(tbResepRalan.getSelectedRow(), 1).toString();
            JamPeresepan = tbResepRalan.getValueAt(tbResepRalan.getSelectedRow(), 2).toString();
            NoRawat = tbResepRalan.getValueAt(tbResepRalan.getSelectedRow(), 3).toString();
            NoRM = tbResepRalan.getValueAt(tbResepRalan.getSelectedRow(), 4).toString();
            Pasien = tbResepRalan.getValueAt(tbResepRalan.getSelectedRow(), 5).toString();
            DokterPeresep = tbResepRalan.getValueAt(tbResepRalan.getSelectedRow(), 6).toString();
            Status = tbResepRalan.getValueAt(tbResepRalan.getSelectedRow(), 7).toString();
            KodeDokter = tbResepRalan.getValueAt(tbResepRalan.getSelectedRow(), 8).toString();
            Ruang = tbResepRalan.getValueAt(tbResepRalan.getSelectedRow(), 9).toString();
            KodeRuang = tbResepRalan.getValueAt(tbResepRalan.getSelectedRow(), 10).toString();
            JnsBayar = tbResepRalan.getValueAt(tbResepRalan.getSelectedRow(), 11).toString();            
            sttsPRB = tbResepRalan.getValueAt(tbResepRalan.getSelectedRow(), 12).toString();            
        }
    }

    private void getData2() {
        if (tbResepRanap.getSelectedRow() != -1) {
            NoResep = tbResepRanap.getValueAt(tbResepRanap.getSelectedRow(), 0).toString();
            TglPeresepan = tbResepRanap.getValueAt(tbResepRanap.getSelectedRow(), 1).toString();
            JamPeresepan = tbResepRanap.getValueAt(tbResepRanap.getSelectedRow(), 2).toString();
            NoRawat = tbResepRanap.getValueAt(tbResepRanap.getSelectedRow(), 3).toString();
            NoRM = tbResepRanap.getValueAt(tbResepRanap.getSelectedRow(), 4).toString();
            Pasien = tbResepRanap.getValueAt(tbResepRanap.getSelectedRow(), 5).toString();
            DokterPeresep = tbResepRanap.getValueAt(tbResepRanap.getSelectedRow(), 6).toString();
            Status = tbResepRanap.getValueAt(tbResepRanap.getSelectedRow(), 7).toString();
            KodeDokter = tbResepRanap.getValueAt(tbResepRanap.getSelectedRow(), 8).toString();
            Ruang = tbResepRanap.getValueAt(tbResepRanap.getSelectedRow(), 9).toString();
            KodeRuang = tbResepRanap.getValueAt(tbResepRanap.getSelectedRow(), 10).toString();
            JnsBayar = tbResepRanap.getValueAt(tbResepRanap.getSelectedRow(), 11).toString();
        }
    }

    private void getData3() {
        if (tbResepPulang.getSelectedRow() != -1) {
            NoResep = tbResepPulang.getValueAt(tbResepPulang.getSelectedRow(), 0).toString();
            TglPeresepan = tbResepPulang.getValueAt(tbResepPulang.getSelectedRow(), 1).toString();
            JamPeresepan = tbResepPulang.getValueAt(tbResepPulang.getSelectedRow(), 2).toString();
            NoRawat = tbResepPulang.getValueAt(tbResepPulang.getSelectedRow(), 3).toString();
            NoRM = tbResepPulang.getValueAt(tbResepPulang.getSelectedRow(), 4).toString();
            Pasien = tbResepPulang.getValueAt(tbResepPulang.getSelectedRow(), 5).toString();
            DokterPeresep = tbResepPulang.getValueAt(tbResepPulang.getSelectedRow(), 6).toString();
            Status = tbResepPulang.getValueAt(tbResepPulang.getSelectedRow(), 7).toString();
            KodeDokter = tbResepPulang.getValueAt(tbResepPulang.getSelectedRow(), 8).toString();
            Ruang = tbResepPulang.getValueAt(tbResepPulang.getSelectedRow(), 9).toString();
            KodeRuang = tbResepPulang.getValueAt(tbResepPulang.getSelectedRow(), 10).toString();
        }
    }
    
    private void getData4() {
        if (tbResepGabung.getSelectedRow() != -1) {
            NoResep = tbResepGabung.getValueAt(tbResepGabung.getSelectedRow(), 0).toString();
            TglPeresepan = tbResepGabung.getValueAt(tbResepGabung.getSelectedRow(), 1).toString();
            JamPeresepan = tbResepGabung.getValueAt(tbResepGabung.getSelectedRow(), 2).toString();
            NoRawat = tbResepGabung.getValueAt(tbResepGabung.getSelectedRow(), 3).toString();
            NoRM = tbResepGabung.getValueAt(tbResepGabung.getSelectedRow(), 4).toString();
            Pasien = tbResepGabung.getValueAt(tbResepGabung.getSelectedRow(), 5).toString();
            DokterPeresep = tbResepGabung.getValueAt(tbResepGabung.getSelectedRow(), 6).toString();
            Status = tbResepGabung.getValueAt(tbResepGabung.getSelectedRow(), 7).toString();
            KodeDokter = tbResepGabung.getValueAt(tbResepGabung.getSelectedRow(), 8).toString();
            Ruang = tbResepGabung.getValueAt(tbResepGabung.getSelectedRow(), 9).toString();
            KodeRuang = tbResepGabung.getValueAt(tbResepGabung.getSelectedRow(), 10).toString();
        }
    }

    public JTable getTable() {
        return tbResepRalan;
    }

    public void isCek() {
        BtnTambah.setEnabled(var.getberi_obat());
        BtnEdit.setEnabled(var.getresep_dokter());
        BtnPrint.setEnabled(var.getresep_dokter());
        BtnRekap.setEnabled(var.getresep_obat());
    }

    public void setCari(String cari) {
        TCari.setText(cari);
    }

    private void tampil2() {
        Valid.tabelKosong(tabMode2);
        try {
            ps = koneksi.prepareStatement("select resep_obat.no_resep,resep_obat.tgl_perawatan,resep_obat.jam,resep_obat.no_rawat,pasien.no_rkm_medis,"
                    + " pasien.nm_pasien,resep_obat.kd_dokter,dokter.nm_dokter,if(resep_obat.jam_peresepan=resep_obat.jam,'Belum Terlayani','Sudah Terlayani') as status,"
                    + " poliklinik.nm_poli,resep_obat.status as status_asal from resep_obat inner join reg_periksa inner join pasien inner join dokter "
                    + " inner join poliklinik on resep_obat.no_rawat=reg_periksa.no_rawat and reg_periksa.kd_poli=poliklinik.kd_poli "
                    + " and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and resep_obat.kd_dokter=dokter.kd_dokter where "
                    + " resep_obat.status='ralan' and resep_obat.tgl_perawatan between ? and ? and dokter.nm_dokter like ? and poliklinik.nm_poli like ? and resep_obat.no_resep like ? or "
                    + " resep_obat.status='ralan' and resep_obat.tgl_perawatan between ? and ? and dokter.nm_dokter like ? and poliklinik.nm_poli like ? and resep_obat.no_rawat like ? or "
                    + " resep_obat.status='ralan' and resep_obat.tgl_perawatan between ? and ? and dokter.nm_dokter like ? and poliklinik.nm_poli like ? and pasien.no_rkm_medis like ? or "
                    + " resep_obat.status='ralan' and resep_obat.tgl_perawatan between ? and ? and dokter.nm_dokter like ? and poliklinik.nm_poli like ? and pasien.nm_pasien like ? or "
                    + " resep_obat.status='ralan' and resep_obat.tgl_perawatan between ? and ? and dokter.nm_dokter like ? and poliklinik.nm_poli like ? and dokter.nm_dokter like ? order by resep_obat.tgl_perawatan desc,resep_obat.jam desc");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, "%" + CrDokter.getText().trim() + "%");
                ps.setString(4, "%" + CrPoli.getText().trim() + "%");
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                ps.setString(6, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(7, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(8, "%" + CrDokter.getText().trim() + "%");
                ps.setString(9, "%" + CrPoli.getText().trim() + "%");
                ps.setString(10, "%" + TCari.getText().trim() + "%");
                ps.setString(11, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(12, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(13, "%" + CrDokter.getText().trim() + "%");
                ps.setString(14, "%" + CrPoli.getText().trim() + "%");
                ps.setString(15, "%" + TCari.getText().trim() + "%");
                ps.setString(16, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(17, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(18, "%" + CrDokter.getText().trim() + "%");
                ps.setString(19, "%" + CrPoli.getText().trim() + "%");
                ps.setString(20, "%" + TCari.getText().trim() + "%");
                ps.setString(21, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(22, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(23, "%" + CrDokter.getText().trim() + "%");
                ps.setString(24, "%" + CrPoli.getText().trim() + "%");
                ps.setString(25, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode2.addRow(new String[]{
                        rs.getString("no_resep"), rs.getString("tgl_perawatan") + " " + rs.getString("jam"),
                        rs.getString("nm_poli"), rs.getString("status"),
                        rs.getString("no_rawat") + " " + rs.getString("no_rkm_medis") + " " + rs.getString("nm_pasien"),
                        rs.getString("nm_dokter")
                    });
                    tabMode2.addRow(new String[]{"", "", "Jumlah", "Kode Obat", "Nama Obat", "Aturan Pakai"});
                    ps2 = koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter.jml,"
                            + "databarang.kode_sat,resep_dokter.aturan_pakai from resep_dokter inner join databarang on "
                            + "resep_dokter.kode_brng=databarang.kode_brng where resep_dokter.no_resep=? order by databarang.kode_brng");
                    try {
                        ps2.setString(1, rs.getString("no_resep"));
                        rs2 = ps2.executeQuery();
                        while (rs2.next()) {
                            tabMode2.addRow(new String[]{
                                "", "", rs2.getString("jml") + " " + rs2.getString("kode_sat"), rs2.getString("kode_brng"), rs2.getString("nama_brng"), rs2.getString("aturan_pakai")
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi 2 : " + e);
                    } finally {
                        if (rs2 != null) {
                            rs2.close();
                        }
                        if (ps2 != null) {
                            ps2.close();
                        }
                    }
                    ps2 = koneksi.prepareStatement(
                            "select resep_dokter_racikan.no_racik,resep_dokter_racikan.nama_racik,"
                            + "resep_dokter_racikan.kd_racik,metode_racik.nm_racik as metode,"
                            + "resep_dokter_racikan.jml_dr,resep_dokter_racikan.aturan_pakai,"
                            + "resep_dokter_racikan.keterangan from resep_dokter_racikan inner join metode_racik "
                            + "on resep_dokter_racikan.kd_racik=metode_racik.kd_racik where "
                            + "resep_dokter_racikan.no_resep=? ");
                    try {
                        ps2.setString(1, rs.getString("no_resep"));
                        rs2 = ps2.executeQuery();
                        while (rs2.next()) {
                            tabMode2.addRow(new String[]{
                                "", "", rs2.getString("jml_dr") + " " + rs2.getString("metode"), "No.Racik : " + rs2.getString("no_racik"), rs2.getString("nama_racik"), rs2.getString("aturan_pakai")
                            });
                            ps3 = koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter_racikan_detail.jml,"
                                    + "databarang.kode_sat from resep_dokter_racikan_detail inner join databarang on resep_dokter_racikan_detail.kode_brng=databarang.kode_brng "
                                    + "where resep_dokter_racikan_detail.no_resep=? and resep_dokter_racikan_detail.no_racik=? order by databarang.kode_brng");
                            try {
                                ps3.setString(1, rs.getString("no_resep"));
                                ps3.setString(2, rs2.getString("no_racik"));
                                rs3 = ps3.executeQuery();
                                while (rs3.next()) {
                                    tabMode2.addRow(new String[]{
                                        "", "", "   " + rs3.getString("jml") + " " + rs3.getString("kode_sat"), "   " + rs3.getString("kode_brng"), "   " + rs3.getString("nama_brng"), ""
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi 3 : " + e);
                            } finally {
                                if (rs3 != null) {
                                    rs3.close();
                                }
                                if (ps3 != null) {
                                    ps3.close();
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi 2 : " + e);
                    } finally {
                        if (rs2 != null) {
                            rs2.close();
                        }
                        if (ps2 != null) {
                            ps2.close();
                        }
                    }
                }
                rs.last();
                LCount.setText("" + rs.getRow());
            } catch (Exception ex) {
                System.out.println("Notifikasi : " + ex);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (SQLException e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    private void panggilform() {
        dlgobt.setNoRm(NoRawat, NoRM, Pasien, TglPeresepan, JamPeresepan);
        dlgobt.isCek();
        dlgobt.tampilobat2(NoResep);
        dlgobt.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dlgobt.setLocationRelativeTo(internalFrame1);
        TeksKosong();
        dlgobt.setVisible(true);
    }

    private void TeksKosong() {
        NoResep = "";
        TglPeresepan = "";
        JamPeresepan = "";
        NoRawat = "";
        NoRM = "";
        Pasien = "";
        DokterPeresep = "";
        Status = "";
        KodeDokter = "";
        Ruang = "";
        KodeRuang = "";
    }

    private void panggilform2() {
        kamar = KodeRuang;
        bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?", kamar);
        if (bangsal.equals("")) {
            if (Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")) {
                var.setkdbangsal(kamar);
            } else {
                var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
            }
        } else {
            var.setkdbangsal(bangsal);
        }
        dlgobt2.setNoRm(NoRawat, Valid.SetTgl2(TglPeresepan));
        dlgobt2.isCek();
        dlgobt2.tampilobat2(NoResep);
        dlgobt2.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dlgobt2.setLocationRelativeTo(internalFrame1);
        TeksKosong();
        dlgobt2.setVisible(true);
    }

    private void panggilform3() {
        kamar = KodeRuang;
        bangsal = Sequel.cariIsi("select kd_depo from set_depo_ranap where kd_bangsal=?", kamar);
        if (bangsal.equals("")) {
            if (Sequel.cariIsi("select asal_stok from set_lokasi").equals("Gunakan Stok Bangsal")) {
                var.setkdbangsal(kamar);
            } else {
                var.setkdbangsal(Sequel.cariIsi("select kd_bangsal from set_lokasi"));
            }
        } else {
            var.setkdbangsal(bangsal);
        }
        dlgobt4.setNoRm(NoRawat, Valid.SetTgl2(TglPeresepan));
        dlgobt4.isCek();
        dlgobt4.tampilobat2(NoResep);
        dlgobt4.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        dlgobt4.setLocationRelativeTo(internalFrame1);
        TeksKosong();
        dlgobt4.setVisible(true);
    }

    public void pilihTab() {
        if (TabPilihRawat.getSelectedIndex() == 0) {
            pilihRalan();
        } else if (TabPilihRawat.getSelectedIndex() == 1) {
            pilihRanap();
        } else if (TabPilihRawat.getSelectedIndex() == 2) {
            pilihPulang();
        } else if (TabPilihRawat.getSelectedIndex() == 3) {
            pilihGabung();
        }
    }

    public void pilihRalan() {
        if (TabRawatJalan.getSelectedIndex() == 0) {
            tampil();
        } else if (TabRawatJalan.getSelectedIndex() == 1) {
            tampil2();
        }
    }

    public void pilihRanap() {
        if (TabRawatInap.getSelectedIndex() == 0) {
            tampil3();
        } else if (TabRawatInap.getSelectedIndex() == 1) {
            tampil4();
        }
    }

    public void pilihPulang() {
        if (TabRawatPulang.getSelectedIndex() == 0) {
            tampil5();
        } else if (TabRawatPulang.getSelectedIndex() == 1) {
            tampil6();
        }
    }
    
    public void pilihGabung() {
        if (TabRawatGabung.getSelectedIndex() == 0) {
            tampil7();
        } else if (TabRawatGabung.getSelectedIndex() == 1) {
            tampil8();
        }
    }

    public void tampil3() {
        Valid.tabelKosong(tabMode3);
        try {
            ps = koneksi.prepareStatement("select resep_obat.no_resep,resep_obat.tgl_peresepan,resep_obat.jam_peresepan,"
                    + " resep_obat.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,resep_obat.kd_dokter,dokter.nm_dokter, "
                    + " if(resep_obat.jam_peresepan=resep_obat.jam,'Belum Terlayani','Sudah Terlayani') as status,bangsal.nm_bangsal,  "
                    + " kamar.kd_bangsal,penjab.png_jawab from resep_obat inner join reg_periksa inner join pasien inner join dokter inner join bangsal inner join kamar inner join kamar_inap inner join penjab "
                    + " on resep_obat.no_rawat=reg_periksa.no_rawat and kamar.kd_bangsal=bangsal.kd_bangsal and reg_periksa.no_rawat=kamar_inap.no_rawat and kamar_inap.kd_kamar=kamar.kd_kamar "
                    + " and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and resep_obat.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj where "
                    + " resep_obat.status='ranap' and resep_obat.tgl_perawatan between ? and ? and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and resep_obat.no_resep like ? or "
                    + " resep_obat.status='ranap' and resep_obat.tgl_perawatan between ? and ? and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and resep_obat.no_rawat like ? or "
                    + " resep_obat.status='ranap' and resep_obat.tgl_perawatan between ? and ? and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and pasien.no_rkm_medis like ? or "
                    + " resep_obat.status='ranap' and resep_obat.tgl_perawatan between ? and ? and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and pasien.nm_pasien like ? or "
                        + " resep_obat.status='ranap' and resep_obat.tgl_perawatan between ? and ? and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and dokter.nm_dokter like ? group by resep_obat.no_resep order by resep_obat.tgl_perawatan desc,resep_obat.jam desc");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, "%" + CrDokter2.getText().trim() + "%");
                ps.setString(4, "%" + Kamar.getText().trim() + "%");
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                ps.setString(6, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(7, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(8, "%" + CrDokter2.getText().trim() + "%");
                ps.setString(9, "%" + Kamar.getText().trim() + "%");
                ps.setString(10, "%" + TCari.getText().trim() + "%");
                ps.setString(11, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(12, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(13, "%" + CrDokter2.getText().trim() + "%");
                ps.setString(14, "%" + Kamar.getText().trim() + "%");
                ps.setString(15, "%" + TCari.getText().trim() + "%");
                ps.setString(16, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(17, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(18, "%" + CrDokter2.getText().trim() + "%");
                ps.setString(19, "%" + Kamar.getText().trim() + "%");
                ps.setString(20, "%" + TCari.getText().trim() + "%");
                ps.setString(21, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(22, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(23, "%" + CrDokter2.getText().trim() + "%");
                ps.setString(24, "%" + Kamar.getText().trim() + "%");
                ps.setString(25, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode3.addRow(new String[]{
                        rs.getString("no_resep"), rs.getString("tgl_peresepan"), rs.getString("jam_peresepan"), rs.getString("no_rawat"),
                        rs.getString("no_rkm_medis"), rs.getString("nm_pasien"), rs.getString("nm_dokter"), rs.getString("status"),
                        rs.getString("kd_dokter"), rs.getString("nm_bangsal"), rs.getString("kd_bangsal"), rs.getString("png_jawab")
                    });
                }

                LCount.setText("" + tabMode3.getRowCount());
            } catch (Exception ex) {
                System.out.println("Notifikasi : " + ex);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (SQLException e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    public void tampil4() {
        Valid.tabelKosong(tabMode4);
        try {
            ps = koneksi.prepareStatement("select resep_obat.no_resep,resep_obat.tgl_perawatan,resep_obat.jam,resep_obat.no_rawat,pasien.no_rkm_medis,"
                    + " pasien.nm_pasien,resep_obat.kd_dokter,dokter.nm_dokter,if(resep_obat.jam_peresepan=resep_obat.jam,'Belum Terlayani','Sudah Terlayani') as status,"
                    + " bangsal.nm_bangsal,resep_obat.status as status_asal from resep_obat inner join reg_periksa inner join pasien inner join dokter "
                    + " inner join bangsal inner join kamar inner join kamar_inap on resep_obat.no_rawat=reg_periksa.no_rawat and kamar.kd_bangsal=bangsal.kd_bangsal "
                    + " and reg_periksa.no_rawat=kamar_inap.no_rawat and kamar_inap.kd_kamar=kamar.kd_kamar and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and resep_obat.kd_dokter=dokter.kd_dokter where "
                    + " resep_obat.status='ranap' and resep_obat.tgl_perawatan between ? and ? and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and resep_obat.no_resep like ? or "
                    + " resep_obat.status='ranap' and resep_obat.tgl_perawatan between ? and ? and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and resep_obat.no_rawat like ? or "
                    + " resep_obat.status='ranap' and resep_obat.tgl_perawatan between ? and ? and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and pasien.no_rkm_medis like ? or "
                    + " resep_obat.status='ranap' and resep_obat.tgl_perawatan between ? and ? and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and pasien.nm_pasien like ? or "
                    + " resep_obat.status='ranap' and resep_obat.tgl_perawatan between ? and ? and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and dokter.nm_dokter like ? group by resep_obat.no_resep order by resep_obat.tgl_perawatan desc,resep_obat.jam desc");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, "%" + CrDokter2.getText().trim() + "%");
                ps.setString(4, "%" + Kamar.getText().trim() + "%");
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                ps.setString(6, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(7, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(8, "%" + CrDokter2.getText().trim() + "%");
                ps.setString(9, "%" + Kamar.getText().trim() + "%");
                ps.setString(10, "%" + TCari.getText().trim() + "%");
                ps.setString(11, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(12, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(13, "%" + CrDokter2.getText().trim() + "%");
                ps.setString(14, "%" + Kamar.getText().trim() + "%");
                ps.setString(15, "%" + TCari.getText().trim() + "%");
                ps.setString(16, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(17, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(18, "%" + CrDokter2.getText().trim() + "%");
                ps.setString(19, "%" + Kamar.getText().trim() + "%");
                ps.setString(20, "%" + TCari.getText().trim() + "%");
                ps.setString(21, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(22, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(23, "%" + CrDokter2.getText().trim() + "%");
                ps.setString(24, "%" + Kamar.getText().trim() + "%");
                ps.setString(25, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode4.addRow(new String[]{
                        rs.getString("no_resep"), rs.getString("tgl_perawatan") + " " + rs.getString("jam"),
                        rs.getString("nm_bangsal"), rs.getString("status"),
                        rs.getString("no_rawat") + " " + rs.getString("no_rkm_medis") + " " + rs.getString("nm_pasien"),
                        rs.getString("nm_dokter")
                    });
                    tabMode4.addRow(new String[]{"", "", "Jumlah", "Kode Obat", "Nama Obat", "Aturan Pakai"});
                    ps2 = koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter.jml,"
                            + "databarang.kode_sat,resep_dokter.aturan_pakai from resep_dokter inner join databarang on "
                            + "resep_dokter.kode_brng=databarang.kode_brng where resep_dokter.no_resep=? order by databarang.kode_brng");
                    try {
                        ps2.setString(1, rs.getString("no_resep"));
                        rs2 = ps2.executeQuery();
                        while (rs2.next()) {
                            tabMode4.addRow(new String[]{
                                "", "", rs2.getString("jml") + " " + rs2.getString("kode_sat"), rs2.getString("kode_brng"), rs2.getString("nama_brng"), rs2.getString("aturan_pakai")
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi 2 : " + e);
                    } finally {
                        if (rs2 != null) {
                            rs2.close();
                        }
                        if (ps2 != null) {
                            ps2.close();
                        }
                    }
                    ps2 = koneksi.prepareStatement(
                            "select resep_dokter_racikan.no_racik,resep_dokter_racikan.nama_racik,"
                            + "resep_dokter_racikan.kd_racik,metode_racik.nm_racik as metode,"
                            + "resep_dokter_racikan.jml_dr,resep_dokter_racikan.aturan_pakai,"
                            + "resep_dokter_racikan.keterangan from resep_dokter_racikan inner join metode_racik "
                            + "on resep_dokter_racikan.kd_racik=metode_racik.kd_racik where "
                            + "resep_dokter_racikan.no_resep=? ");
                    try {
                        ps2.setString(1, rs.getString("no_resep"));
                        rs2 = ps2.executeQuery();
                        while (rs2.next()) {
                            tabMode4.addRow(new String[]{
                                "", "", rs2.getString("jml_dr") + " " + rs2.getString("metode"), "No.Racik : " + rs2.getString("no_racik"), rs2.getString("nama_racik"), rs2.getString("aturan_pakai")
                            });
                            ps3 = koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter_racikan_detail.jml,"
                                    + "databarang.kode_sat from resep_dokter_racikan_detail inner join databarang on resep_dokter_racikan_detail.kode_brng=databarang.kode_brng "
                                    + "where resep_dokter_racikan_detail.no_resep=? and resep_dokter_racikan_detail.no_racik=? order by databarang.kode_brng");
                            try {
                                ps3.setString(1, rs.getString("no_resep"));
                                ps3.setString(2, rs2.getString("no_racik"));
                                rs3 = ps3.executeQuery();
                                while (rs3.next()) {
                                    tabMode4.addRow(new String[]{
                                        "", "", "   " + rs3.getString("jml") + " " + rs3.getString("kode_sat"), "   " + rs3.getString("kode_brng"), "   " + rs3.getString("nama_brng"), ""
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi 3 : " + e);
                            } finally {
                                if (rs3 != null) {
                                    rs3.close();
                                }
                                if (ps3 != null) {
                                    ps3.close();
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi 2 : " + e);
                    } finally {
                        if (rs2 != null) {
                            rs2.close();
                        }
                        if (ps2 != null) {
                            ps2.close();
                        }
                    }
                }
                rs.last();
                LCount.setText("" + rs.getRow());
            } catch (Exception ex) {
                System.out.println("Notifikasi : " + ex);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (SQLException e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    public void tampil5() {
        Valid.tabelKosong(tabMode5);
        try {
            ps = koneksi.prepareStatement("select resep_pulang.no_resep,resep_dokter_pulang.tgl_peresepan,resep_dokter_pulang.jam_peresepan,"
                    + " resep_pulang.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,resep_dokter_pulang.kd_dokter,dokter.nm_dokter, "
                    + " if(resep_dokter_pulang.jam_peresepan=resep_dokter_pulang.jam_perawatan,'Belum Terlayani','Sudah Terlayani') as status,bangsal.nm_bangsal,  "
                    + " kamar.kd_bangsal,penjab.png_jawab from resep_dokter_pulang inner join resep_pulang inner join reg_periksa inner join pasien inner join dokter inner join bangsal inner join kamar inner join kamar_inap inner join penjab "
                    + " on resep_pulang.no_rawat=reg_periksa.no_rawat and resep_pulang.no_resep=resep_dokter_pulang.no_resep and kamar.kd_bangsal=bangsal.kd_bangsal and reg_periksa.no_rawat=kamar_inap.no_rawat and kamar_inap.kd_kamar=kamar.kd_kamar "
                    + " and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and resep_dokter_pulang.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj where "
                    + " resep_dokter_pulang.tgl_perawatan between ? and ? and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and ( resep_pulang.no_resep like ? or "
                    + " resep_pulang.no_rawat like ? or "
                    + " pasien.no_rkm_medis like ? or "
                    + " pasien.nm_pasien like ? or "
                    + " dokter.nm_dokter like ? ) group by resep_dokter_pulang.no_resep order by resep_dokter_pulang.tgl_perawatan desc,resep_dokter_pulang.jam_perawatan desc");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, "%" + CrDokter2.getText().trim() + "%");
                ps.setString(4, "%" + Kamar.getText().trim() + "%");
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                ps.setString(6, "%" + TCari.getText().trim() + "%");
                ps.setString(7, "%" + TCari.getText().trim() + "%");
                ps.setString(8, "%" + TCari.getText().trim() + "%");
                ps.setString(9, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode5.addRow(new String[]{
                        rs.getString("no_resep"), rs.getString("tgl_peresepan"), rs.getString("jam_peresepan"), rs.getString("no_rawat"),
                        rs.getString("no_rkm_medis"), rs.getString("nm_pasien"), rs.getString("nm_dokter"), rs.getString("status"),
                        rs.getString("kd_dokter"), rs.getString("nm_bangsal"), rs.getString("kd_bangsal"),rs.getString("png_jawab")
                    });
                }

                LCount.setText("" + tabMode5.getRowCount());
            } catch (Exception ex) {
                System.out.println("Notifikasi : " + ex);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (SQLException e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    public void tampil6() {
        Valid.tabelKosong(tabMode6);
        try {
            ps = koneksi.prepareStatement("select resep_pulang.no_resep,resep_dokter_pulang.tgl_perawatan,resep_dokter_pulang.jam_perawatan,resep_pulang.no_rawat,pasien.no_rkm_medis,"
                    + " pasien.nm_pasien,resep_dokter_pulang.kd_dokter,dokter.nm_dokter,if(resep_dokter_pulang.jam_peresepan=resep_dokter_pulang.jam_perawatan,'Belum Terlayani','Sudah Terlayani') as status,"
                    + " bangsal.nm_bangsal from resep_pulang inner join resep_dokter_pulang inner join reg_periksa inner join pasien inner join dokter "
                    + " inner join bangsal inner join kamar inner join kamar_inap on resep_pulang.no_rawat=reg_periksa.no_rawat and resep_pulang.no_resep=resep_dokter_pulang.no_resep and kamar.kd_bangsal=bangsal.kd_bangsal "
                    + " and reg_periksa.no_rawat=kamar_inap.no_rawat and kamar_inap.kd_kamar=kamar.kd_kamar and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and resep_dokter_pulang.kd_dokter=dokter.kd_dokter where "
                    + " resep_dokter_pulang.tgl_perawatan between ? and ? and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and ( resep_pulang.no_resep like ? or "
                    + " resep_pulang.no_rawat like ? or "
                    + " pasien.no_rkm_medis like ? or "
                    + " pasien.nm_pasien like ? or "
                    + " dokter.nm_dokter like ? ) group by resep_dokter_pulang.no_resep order by resep_dokter_pulang.tgl_perawatan desc,resep_dokter_pulang.jam_perawatan desc");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, "%" + CrDokter2.getText().trim() + "%");
                ps.setString(4, "%" + Kamar.getText().trim() + "%");
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                ps.setString(6, "%" + TCari.getText().trim() + "%");
                ps.setString(7, "%" + TCari.getText().trim() + "%");
                ps.setString(8, "%" + TCari.getText().trim() + "%");
                ps.setString(9, "%" + TCari.getText().trim() + "%");

                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode6.addRow(new String[]{
                        rs.getString("no_resep"), rs.getString("tgl_perawatan") + " " + rs.getString("jam_perawatan"),
                        rs.getString("nm_bangsal"), rs.getString("status"),
                        rs.getString("no_rawat") + " " + rs.getString("no_rkm_medis") + " " + rs.getString("nm_pasien"),
                        rs.getString("nm_dokter")
                    });
                    tabMode6.addRow(new String[]{"", "", "Jumlah", "Kode Obat", "Nama Obat", "Aturan Pakai"});
                    ps2 = koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_pulang.jml_barang,"
                            + "databarang.kode_sat,resep_pulang.dosis from resep_pulang inner join databarang on "
                            + "resep_pulang.kode_brng=databarang.kode_brng where resep_pulang.no_resep=? order by databarang.kode_brng");
                    try {
                        ps2.setString(1, rs.getString("no_resep"));
                        rs2 = ps2.executeQuery();
                        while (rs2.next()) {
                            tabMode6.addRow(new String[]{
                                "", "", rs2.getString("jml_barang") + " " + rs2.getString("kode_sat"), rs2.getString("kode_brng"), rs2.getString("nama_brng"), rs2.getString("dosis")
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi 2 : " + e);
                    } finally {
                        if (rs2 != null) {
                            rs2.close();
                        }
                        if (ps2 != null) {
                            ps2.close();
                        }
                    }

                }
                rs.last();
                LCount.setText("" + rs.getRow());
            } catch (Exception ex) {
                System.out.println("Notifikasi : " + ex);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (SQLException e) {
            System.out.println("Notifikasi : " + e);
        }
    }
    
    public void tampil7() {
        Valid.tabelKosong(tabMode7);
        try {
            ps = koneksi.prepareStatement("select resep_obat.no_resep,resep_obat.tgl_peresepan,resep_obat.jam_peresepan,"
                    + " resep_obat.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,resep_obat.kd_dokter,dokter.nm_dokter, "
                    + " if(resep_obat.jam_peresepan=resep_obat.jam,'Belum Terlayani','Sudah Terlayani') as status,bangsal.nm_bangsal,  "
                    + " kamar.kd_bangsal,penjab.png_jawab from resep_obat inner join ranap_gabung inner join reg_periksa inner join pasien  inner join dokter inner join penjab inner join kamar_inap inner join kamar inner join bangsal "
                    + " on ranap_gabung.no_rawat2=resep_obat.no_rawat and resep_obat.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and resep_obat.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "
                    + " and ranap_gabung.no_rawat=kamar_inap.no_rawat and kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal where "
                    + " kamar_inap.stts_pulang <> 'Pindah Kamar' and resep_obat.status='ranap' and resep_obat.tgl_perawatan between ? and ? and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and (resep_obat.no_resep like ? or "
                    + " resep_obat.no_rawat like ? or "
                    + " pasien.no_rkm_medis like ? or "
                    + " pasien.nm_pasien like ? or "
                    + " dokter.nm_dokter like ?) group by resep_obat.no_resep order by resep_obat.tgl_perawatan desc,resep_obat.jam desc");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, "%" + CrDokter2.getText().trim() + "%");
                ps.setString(4, "%" + Kamar.getText().trim() + "%");
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                ps.setString(6, "%" + TCari.getText().trim() + "%");
                ps.setString(7, "%" + TCari.getText().trim() + "%");
                ps.setString(8, "%" + TCari.getText().trim() + "%");
                ps.setString(9, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode7.addRow(new String[]{
                        rs.getString("no_resep"), rs.getString("tgl_peresepan"), rs.getString("jam_peresepan"), rs.getString("no_rawat"),
                        rs.getString("no_rkm_medis"), rs.getString("nm_pasien"), rs.getString("nm_dokter"), rs.getString("status"),
                        rs.getString("kd_dokter"), rs.getString("nm_bangsal"), rs.getString("kd_bangsal"), rs.getString("png_jawab")
                    });
                }
                LCount.setText("" + tabMode7.getRowCount());
            } catch (Exception ex) {
                System.out.println("Notifikasi : " + ex);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (SQLException e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    public void tampil8() {
        Valid.tabelKosong(tabMode8);
        try {
            ps = koneksi.prepareStatement("select resep_obat.no_resep,resep_obat.tgl_perawatan,resep_obat.jam,resep_obat.no_rawat,pasien.no_rkm_medis,"
                    + " pasien.nm_pasien,resep_obat.kd_dokter,dokter.nm_dokter,if(resep_obat.jam_peresepan=resep_obat.jam,'Belum Terlayani','Sudah Terlayani') as status,"
                    + " bangsal.nm_bangsal,resep_obat.status as status_asal from resep_obat inner join ranap_gabung inner join reg_periksa inner join pasien  inner join "
                    + " dokter inner join penjab inner join kamar_inap inner join kamar inner join bangsal  on ranap_gabung.no_rawat2=resep_obat.no_rawat and resep_obat.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and resep_obat.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "
                    + " and ranap_gabung.no_rawat=kamar_inap.no_rawat and kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal where "
                    + " kamar_inap.stts_pulang <> 'Pindah Kamar' and resep_obat.status='ranap' and resep_obat.tgl_perawatan between ? and ? and dokter.nm_dokter like ? and bangsal.nm_bangsal like ? and (resep_obat.no_resep like ? or "
                    + " resep_obat.no_rawat like ? or "
                    + " pasien.no_rkm_medis like ? or "
                    + " pasien.nm_pasien like ? or "
                    + " dokter.nm_dokter like ?) group by resep_obat.no_resep order by resep_obat.tgl_perawatan desc,resep_obat.jam desc");
            try {
                ps.setString(1, Valid.SetTgl(DTPCari1.getSelectedItem() + ""));
                ps.setString(2, Valid.SetTgl(DTPCari2.getSelectedItem() + ""));
                ps.setString(3, "%" + CrDokter2.getText().trim() + "%");
                ps.setString(4, "%" + Kamar.getText().trim() + "%");
                ps.setString(5, "%" + TCari.getText().trim() + "%");
                ps.setString(6, "%" + TCari.getText().trim() + "%");
                ps.setString(7, "%" + TCari.getText().trim() + "%");
                ps.setString(8, "%" + TCari.getText().trim() + "%");
                ps.setString(9, "%" + TCari.getText().trim() + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode8.addRow(new String[]{
                        rs.getString("no_resep"), rs.getString("tgl_perawatan") + " " + rs.getString("jam"),
                        rs.getString("nm_bangsal"), rs.getString("status"),
                        rs.getString("no_rawat") + " " + rs.getString("no_rkm_medis") + " " + rs.getString("nm_pasien"),
                        rs.getString("nm_dokter")
                    });
                    tabMode8.addRow(new String[]{"", "", "Jumlah", "Kode Obat", "Nama Obat", "Aturan Pakai"});
                    ps2 = koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter.jml,"
                            + "databarang.kode_sat,resep_dokter.aturan_pakai from resep_dokter inner join databarang on "
                            + "resep_dokter.kode_brng=databarang.kode_brng where resep_dokter.no_resep=? order by databarang.kode_brng");
                    try {
                        ps2.setString(1, rs.getString("no_resep"));
                        rs2 = ps2.executeQuery();
                        while (rs2.next()) {
                            tabMode8.addRow(new String[]{
                                "", "", rs2.getString("jml") + " " + rs2.getString("kode_sat"), rs2.getString("kode_brng"), rs2.getString("nama_brng"), rs2.getString("aturan_pakai")
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi 2 : " + e);
                    } finally {
                        if (rs2 != null) {
                            rs2.close();
                        }
                        if (ps2 != null) {
                            ps2.close();
                        }
                    }
                    ps2 = koneksi.prepareStatement(
                            "select resep_dokter_racikan.no_racik,resep_dokter_racikan.nama_racik,"
                            + "resep_dokter_racikan.kd_racik,metode_racik.nm_racik as metode,"
                            + "resep_dokter_racikan.jml_dr,resep_dokter_racikan.aturan_pakai,"
                            + "resep_dokter_racikan.keterangan from resep_dokter_racikan inner join metode_racik "
                            + "on resep_dokter_racikan.kd_racik=metode_racik.kd_racik where "
                            + "resep_dokter_racikan.no_resep=? ");
                    try {
                        ps2.setString(1, rs.getString("no_resep"));
                        rs2 = ps2.executeQuery();
                        while (rs2.next()) {
                            tabMode8.addRow(new String[]{
                                "", "", rs2.getString("jml_dr") + " " + rs2.getString("metode"), "No.Racik : " + rs2.getString("no_racik"), rs2.getString("nama_racik"), rs2.getString("aturan_pakai")
                            });
                            ps3 = koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter_racikan_detail.jml,"
                                    + "databarang.kode_sat from resep_dokter_racikan_detail inner join databarang on resep_dokter_racikan_detail.kode_brng=databarang.kode_brng "
                                    + "where resep_dokter_racikan_detail.no_resep=? and resep_dokter_racikan_detail.no_racik=? order by databarang.kode_brng");
                            try {
                                ps3.setString(1, rs.getString("no_resep"));
                                ps3.setString(2, rs2.getString("no_racik"));
                                rs3 = ps3.executeQuery();
                                while (rs3.next()) {
                                    tabMode8.addRow(new String[]{
                                        "", "", "   " + rs3.getString("jml") + " " + rs3.getString("kode_sat"), "   " + rs3.getString("kode_brng"), "   " + rs3.getString("nama_brng"), ""
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi 3 : " + e);
                            } finally {
                                if (rs3 != null) {
                                    rs3.close();
                                }
                                if (ps3 != null) {
                                    ps3.close();
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi 2 : " + e);
                    } finally {
                        if (rs2 != null) {
                            rs2.close();
                        }
                        if (ps2 != null) {
                            ps2.close();
                        }
                    }
                }
                rs.last();
                LCount.setText("" + rs.getRow());
            } catch (Exception ex) {
                System.out.println("Notifikasi : " + ex);
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
        } catch (SQLException e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    private void jam() {
        ActionListener taskPerformer = (ActionEvent e) -> {
            if (aktif == true) {
                nol_detik = "";
                Date now = Calendar.getInstance().getTime();
                nilai_detik = now.getSeconds();
                if (nilai_detik <= 9) {
                    nol_detik = "0";
                }

                detik = nol_detik + Integer.toString(nilai_detik);
                if (detik.equals("05")) {
                    resepbaru = 0;
                    if (formalarm.contains("ralan")) {
                        tampil();
                        for (i = 0; i < tbResepRalan.getRowCount(); i++) {
                            if (tbResepRalan.getValueAt(i, 7).toString().equals("Belum Terlayani")) {
                                resepbaru++;
                            }
                        }
                    }

                    if (formalarm.contains("ranap")) {
                        tampil3();
                        for (i = 0; i < tbResepRanap.getRowCount(); i++) {
                            if (tbResepRanap.getValueAt(i, 7).toString().equals("Belum Terlayani")) {
                                resepbaru++;
                            }
                        }
                    }

                    if (resepbaru > 0) {
                        try {
                            music = new BackgroundMusic("./suara/alarm.mp3");
                            music.start();
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }
                    }
                }
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }
    
    private void cekKronis() {
        for (int j = 0; j < tbResepRalan.getRowCount(); j++) {
            int cari = Sequel.cariInteger("SELECT count(no_rawat) from mlite_veronisa where no_rawat=?", tbResepRalan.getValueAt(j, 3).toString());
            try {
                if (tbResepRalan.getValueAt(j, 11).toString().equals("BPJS") && (cari > 0)) {
                    tbResepRalan.setValueAt(tbResepRalan.getValueAt(j, 11).toString() + " (KRONIS)", j, 11);
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        }
    }
    private void cekPRB() {
        for (int j = 0; j < tbResepRalan.getRowCount(); j++) {
            int cari = Sequel.cariInteger("SELECT count(no_rawat) from mlite_srb where no_rawat=?", tbResepRalan.getValueAt(j, 3).toString());
            try {
                if (tbResepRalan.getValueAt(j, 11).toString().contains("BPJS") && (cari > 0)) {
                    tbResepRalan.setValueAt("Ya", j, 12);
                }else{
                    tbResepRalan.setValueAt("", j, 12);
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        }
    }
}
